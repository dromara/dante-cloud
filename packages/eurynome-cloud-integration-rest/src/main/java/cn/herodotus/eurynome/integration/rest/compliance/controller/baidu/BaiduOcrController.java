package cn.herodotus.eurynome.integration.rest.compliance.controller.baidu;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.integration.content.domain.baidu.OcrResult;
import cn.herodotus.eurynome.integration.content.service.baidu.BaiduOcrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: Baidu OCR 接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 15:56
 */
@Slf4j
@RestController
@RequestMapping("/integration/rest/baidu")
@Api(tags = {"第三方集成接口", "百度图像识别接口"})
public class BaiduOcrController {

    @Autowired
    private BaiduOcrService baiduOCRService;

    @ApiOperation(value = "图片识别转换文字", notes = "图片识别转换文字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "picUrl", required = true, value = "待审核图片url", dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping("/pictureRecognition")
    public Result<OcrResult> pictureRecognition(@RequestParam("picUrl") String picUrl) {
        OcrResult result = baiduOCRService.pictureRecognition(picUrl);
        if (ObjectUtils.isNotEmpty(result) && StringUtils.isEmpty(result.getErrorMessage())) {
            return new Result<OcrResult>().ok().data(result);
        } else {
            return new Result<OcrResult>().failed().message("图片识别出错").data(result);
        }
    }

    @ApiOperation(value = "营业执照识别", notes = "营业执照识别，照片必须是正的，否则无法识别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "picUrl", required = true, value = "待审核图片url", dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping("/businessLicense")
    public Result<OcrResult> businessLicense(@RequestParam("picUrl") String picUrl) {
        OcrResult result = baiduOCRService.businessLicense(picUrl);
        if (ObjectUtils.isNotEmpty(result) && StringUtils.isEmpty(result.getErrorMessage())) {
            return new Result<OcrResult>().ok().data(result);
        } else {
            return new Result<OcrResult>().failed().message("营业执照识别出错").data(result);
        }
    }

    @ApiOperation(value = "身份证识别", notes = "身份证识别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "picUrl", required = true, value = "待审核图片url", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "side", required = true, value = "身份证照片面标签：front：身份证含照片的一面；back：身份证带国徽的一面", dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping("/idCard")
    public Result<OcrResult> IDCard(@RequestParam("picUrl") String picUrl, @RequestParam("side") String side) {
        OcrResult result = baiduOCRService.idCard(picUrl, side);
        if (ObjectUtils.isNotEmpty(result) && StringUtils.isEmpty(result.getErrorMessage())){
            return new Result<OcrResult>().ok().data(result);
        } else {
            return new Result<OcrResult>().failed().message("身份证识别出错").data(result);
        }
    }
}
