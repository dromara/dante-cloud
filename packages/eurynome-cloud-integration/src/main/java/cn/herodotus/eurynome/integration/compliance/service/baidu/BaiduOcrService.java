package cn.herodotus.eurynome.integration.compliance.service.baidu;

import cn.herodotus.eurynome.common.utils.Base64ImageUtils;
import cn.herodotus.eurynome.common.utils.JacksonUtils;
import cn.herodotus.eurynome.integration.compliance.domain.baidu.OcrResult;
import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>Description: 百度图像识别服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 14:31
 */
@Slf4j
@Service
public class BaiduOcrService {

	@Autowired
	private AipOcr aipOcr;

	private OcrResult jsonObjectToBean(JSONObject jsonObject) {
		if (ObjectUtils.isNotEmpty(jsonObject)) {
			return JacksonUtils.toObject(jsonObject.toString(), OcrResult.class);
		} else {
			log.error("[Braineex] |- JSONObject convert to Bean error!");
			return null;
		}
	}

	/**
	 * 图片识别转换文字
	 *
	 * @param image   二进制图像
	 * @param options 可选参数对象，key: value都为string类型 options - options列表: detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括: - true：检测朝向； - false：不检测朝向。 probability 是否返回识别结果中每一行的置信度
	 * @return {@link OcrResult}
	 */
	public OcrResult pictureRecognition(byte[] image, HashMap<String, String> options) {
		// 通用版 50000次/天免费,这个版本不行，标点符号识别不清楚
		// org.json.JSONObject jsonObject = client.basicGeneral(bytesBs64, options);
		// 高精度版 500次/天免费
		JSONObject jsonObject = aipOcr.basicAccurateGeneral(image, options);
		log.debug("[Braineex] |- Picture recognition result is: {}", jsonObject.toString());
		return this.jsonObjectToBean(jsonObject);
	}

	/**
	 * 图片识别转换文字
	 *
	 * @param url 图片URL
	 * @return {@link OcrResult}
	 */
	public OcrResult pictureRecognition(String url) {
		HashMap<String, String> options = new HashMap<>();
		byte[] base64Image = Base64ImageUtils.onlineImageToBase64(url);

		return this.pictureRecognition(base64Image, options);
	}

	/**
	 * 营业执照识别，照片必须是正的，否则无法识别
	 *
	 * @param image   二进制图像
	 * @param options 可选参数对象，key: value都为string类型 options - options列表:
	 * @return {@link OcrResult}
	 */
	public OcrResult businessLicense(byte[] image, HashMap<String, String> options) {
		JSONObject jsonObject = aipOcr.businessLicense(image, options);
		log.debug("[Braineex] |- Business License result is: {}", jsonObject.toString());
		return this.jsonObjectToBean(jsonObject);
	}

	/**
	 * 营业执照识别，照片必须是正的，否则无法识别
	 *
	 * @param url 图片URL
	 * @return {@link OcrResult}
	 */
	public OcrResult businessLicense(String url) {
		HashMap<String, String> options = new HashMap<>();
		byte[] base64Image = Base64ImageUtils.localImageToBase64(url);
		return this.businessLicense(base64Image, options);
	}

	/**
	 * 身份证识别
	 *
	 * @param image      二进制图像
	 * @param idCardSide front：身份证含照片的一面；back：身份证带国徽的一面
	 * @param options    可选参数对象，key: value都为string类型 options - options列表: detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括: - true：检测朝向； - false：不检测朝向。 detect_risk 是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。可选值:true-开启；false-不开启
	 * @return {@link OcrResult}
	 */
	public OcrResult idCard(byte[] image, String idCardSide, HashMap<String, String> options) {
		JSONObject jsonObject = aipOcr.idcard(image, idCardSide, options);
		log.debug("[Braineex] |- IDCard result is: {}", jsonObject.toString());
		return this.jsonObjectToBean(jsonObject);
	}

	/**
	 * 身份证识别
	 *
	 * @param url        图片URL
	 * @param idCardSide front：身份证含照片的一面；back：身份证带国徽的一面
	 * @return
	 */
	public OcrResult idCard(String url, String idCardSide) {
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<>();
		//保护隐私，身份证号码不显示
		options.put("detect_risk", "true");
		byte[] base64Image = Base64ImageUtils.onlineImageToBase64(url);
		return this.idCard(base64Image, idCardSide, options);
	}
}
