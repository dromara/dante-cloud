package cn.herodotus.eurynome.integration.compliance.domain.aliyun.image;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractResult;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.HintWordsInfo;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.LogoData;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.SFaceData;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里图片审核返回值：result对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:50
 */
public class ImageResult extends AbstractResult {
	/**
	 * 如果检测场景包含智能鉴黄（porn）和暴恐涉政（terrorism），则该字段可以返回检测结果的细分类标签。
	 * 该字段默认不会返回。如果有需要，您可以提交工单联系我们进行配置，配置后才会返回。
	 */
	private String sublabel;
	/**
	 * 如果待检测图片因为过长被截断，该参数返回截断后的每一帧图像的临时访问地址。具体结构描述请参见frame。
	 */
	private List<Frame> frames;
	/**
	 * 图片中含有广告或文字违规信息时，返回图片中广告文字命中的风险关键词信息。具体结构描述，请参见hintWordsInfo。
	 */
	private List<HintWordsInfo> hintWordsInfo;
	/**
	 * 图片中含有二维码时，返回图片中所有二维码包含的文本信息。
	 */
	private List<String> qrcodeData;
	/**
	 * 返回图片中识别到的二维码的坐标信息，关于具体的结构描述，请参见qrcodeLocation。
	 */
	private List<QrodeLocation> qrcodeLocations;
	/**
	 * 图片中含有小程序码时，返回小程序码的位置信息。关于具体结构的描述，请参见programCodeData。
	 */
	private List<ProgramCodeData> programCodeData;
	/**
	 * 图片中含有logo时，返回识别出来的logo信息。关于具体结构的描述，请参见logoData。
	 */
	private List<LogoData> logoData;
	/**
	 * 图片中包含暴恐识涉政内容时，返回识别出来的暴恐涉政信息。关于具体结构的描述，请参见sfaceData。
	 */
	private List<SFaceData> sFaceData;
	/**
	 * 识别到的图片中的完整文字信息。
	 */
	private List<String> ocrData;


	public String getSublabel() {
		return sublabel;
	}

	public void setSublabel(String sublabel) {
		this.sublabel = sublabel;
	}

	public List<Frame> getFrames() {
		return frames;
	}

	public void setFrames(List<Frame> frames) {
		this.frames = frames;
	}

	public List<HintWordsInfo> getHintWordsInfo() {
		return hintWordsInfo;
	}

	public void setHintWordsInfo(List<HintWordsInfo> hintWordsInfo) {
		this.hintWordsInfo = hintWordsInfo;
	}

	public List<String> getQrcodeData() {
		return qrcodeData;
	}

	public void setQrcodeData(List<String> qrcodeData) {
		this.qrcodeData = qrcodeData;
	}

	public List<QrodeLocation> getQrcodeLocations() {
		return qrcodeLocations;
	}

	public void setQrcodeLocations(List<QrodeLocation> qrcodeLocations) {
		this.qrcodeLocations = qrcodeLocations;
	}

	public List<ProgramCodeData> getProgramCodeData() {
		return programCodeData;
	}

	public void setProgramCodeData(List<ProgramCodeData> programCodeData) {
		this.programCodeData = programCodeData;
	}

	public List<LogoData> getLogoData() {
		return logoData;
	}

	public void setLogoData(List<LogoData> logoData) {
		this.logoData = logoData;
	}

	public List<SFaceData> getsFaceData() {
		return sFaceData;
	}

	public void setsFaceData(List<SFaceData> sFaceData) {
		this.sFaceData = sFaceData;
	}

	public List<String> getOcrData() {
		return ocrData;
	}

	public void setOcrData(List<String> ocrData) {
		this.ocrData = ocrData;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("sublabel", sublabel)
				.toString();
	}
}
