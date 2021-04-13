package cn.herodotus.eurynome.integration.compliance.domain.baidu;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

/**
 * <p>Description: Ocr结果对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 14:21
 */
public class OcrResult implements Serializable {

    /**
     * 图像方向，当图像旋转时，返回该参数。
     * - -1:未定义，
     * - 0:正向，
     * - 1: 逆时针90度，
     * - 2:逆时针180度，
     * - 3:逆时针270度
     */
    private Integer direction;
    /**
     * normal-识别正常
     * reversed_side-身份证正反面颠倒
     * non_idcard-上传的图片中不包含身份证
     * blurred-身份证模糊
     * other_type_card-其他类型证照
     * over_exposure-身份证关键字段反光或过曝
     * over_dark-身份证欠曝（亮度过低）
     * unknown-未知状态
     */
    @JsonProperty("image_status")
    @JSONField(name = "image_status")
    private String imageStatus;
    /**
     * 输入参数 detect_risk = true 时，则返回该字段识别身份证类型:
     * normal-正常身份证；
     * copy-复印件；
     * temporary-临时身份证；
     * screen-翻拍；
     * unknown-其他未知情况
     */
    @JsonProperty("risk_type")
    @JSONField(name = "risk_type")
    private String riskType;
    /**
     * 如果参数 detect_risk = true 时，则返回此字段。如果检测身份证被编辑过，该字段指定编辑软件名称，如:Adobe Photoshop CC 2014 (Macintosh),如果没有被编辑过则返回值无此参数
     */
    @JsonProperty("edit_tool")
    @JSONField(name = "edit_tool")
    private String editTool;
    /**
     * 唯一的log id，用于问题定位
     */
    @JsonProperty("log_id")
    @JSONField(name = "log_id")
    private BigInteger logId;

    private String photo;

    @JsonProperty("photo_location")
    @JSONField(name = "photo_location")
    private Location photoLocation;

    /**
     * 用于校验身份证号码、性别、出生是否一致，输出结果及其对应关系如下：
     * -1: 身份证正面所有字段全为空
     * 0: 身份证证号不合法，此情况下不返回身份证证号
     * 1: 身份证证号和性别、出生信息一致
     * 2: 身份证证号和性别、出生信息都不一致
     * 3: 身份证证号和出生信息不一致
     * 4: 身份证证号和性别信息不一致
     */
    @JsonProperty("idcard_number_type")
    @JSONField(name = "idcard_number_type")
    private String idcardNumberType;

    @JsonProperty("words_result")
    @JSONField(name = "words_result")
    private Map<String, Word> wordsResult;

    @JsonProperty("words_result_num")
    @JSONField(name = "words_result_num")
    private Integer wordsResultNum;

    @JsonProperty("error_code")
    @JSONField(name = "error_code")
    private Integer errorCode;

    @JsonProperty("error_message")
    @JSONField(name = "error_message")
    private String errorMessage;

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getEditTool() {
        return editTool;
    }

    public void setEditTool(String editTool) {
        this.editTool = editTool;
    }

    public BigInteger getLogId() {
        return logId;
    }

    public void setLogId(BigInteger logId) {
        this.logId = logId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Location getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(Location photoLocation) {
        this.photoLocation = photoLocation;
    }

    public String getIdcardNumberType() {
        return idcardNumberType;
    }

    public void setIdcardNumberType(String idcardNumberType) {
        this.idcardNumberType = idcardNumberType;
    }

    public Map<String, Word> getWordsResult() {
        return wordsResult;
    }

    public void setWordsResult(Map<String, Word> wordsResult) {
        this.wordsResult = wordsResult;
    }

    public Integer getWordsResultNum() {
        return wordsResultNum;
    }

    public void setWordsResultNum(Integer wordsResultNum) {
        this.wordsResultNum = wordsResultNum;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("direction", direction)
                .add("imageStatus", imageStatus)
                .add("riskType", riskType)
                .add("editTool", editTool)
                .add("logId", logId)
                .add("photo", photo)
                .add("photoLocation", photoLocation)
                .add("idcardNumberType", idcardNumberType)
                .add("wordsResult", wordsResult)
                .add("wordsResultNum", wordsResultNum)
                .add("errorCode", errorCode)
                .add("errorMessage", errorMessage)
                .toString();
    }
}
