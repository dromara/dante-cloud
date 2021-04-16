package cn.herodotus.eurynome.integration.sms.domain.aliyun;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 阿里发送短信通用返回实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 14:08
 */
@ApiModel(value = "阿里发送短信通用返回实体")
public class SmsResponse {

	@ApiModelProperty(name = "发送回执ID", notes = "可根据该ID在接口QuerySendDetails中查询具体的发送状态")
	@JSONField(name = "BizId")
	@JsonProperty("BizId")
	private String BizId;

	@ApiModelProperty(name = "请求状态码", notes = "返回OK代表请求成功, 其他错误码，请参见错误码列表")
	@JSONField(name = "Code")
	@JsonProperty("Code")
	private String code;

	@ApiModelProperty(name = "状态码的描述")
	@JSONField(name = "Message")
	@JsonProperty("Message")
	private String message;

	@ApiModelProperty(name = "请求ID")
	@JSONField(name = "RequestId")
	@JsonProperty("RequestId")
	private String requestId;

	public String getBizId() {
		return BizId;
	}

	public void setBizId(String bizId) {
		BizId = bizId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("BizId", BizId)
				.add("code", code)
				.add("message", message)
				.add("requestId", requestId)
				.toString();
	}
}
