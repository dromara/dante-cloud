package cn.herodotus.eurynome.integration.content.domain.tianyan;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 天眼查统一返回对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 17:18
 */
@ApiModel(description = "天眼查统一返回值对象")
public class Response<T> {

	@ApiModelProperty(value = "错误信息")
	private String reason;

	@ApiModelProperty(value = "状态码")
	@JSONField(name = "error_code")
	@JsonProperty("error_code")
	private Integer errorCode;

	@ApiModelProperty(value = "返回结果")
	private T result;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("reason", reason)
				.add("errorCode", errorCode)
				.add("result", result)
				.toString();
	}
}
