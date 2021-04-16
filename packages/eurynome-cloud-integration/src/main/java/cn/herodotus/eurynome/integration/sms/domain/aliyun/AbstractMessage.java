package cn.herodotus.eurynome.integration.sms.domain.aliyun;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 阿里SMS发送短信抽象请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 11:24
 */
@ApiModel(description = "阿里SMS发送短信抽象请求参数实体")
public abstract class AbstractMessage {

	protected static final String SEND_SMS = "SendSms";
	protected static final String SEND_BATCH_SMS = "SendBatchSms";

	/**
	 * 操作接口名，系统规定参数
	 */
	@ApiModelProperty(name = "系统规定参数")
	@JSONField(name = "Action")
	@JsonProperty("Action")
	private String action;

	public String getAction() {
		return action;
	}

	protected void setAction(String action) {
		this.action = action;
	}
}
