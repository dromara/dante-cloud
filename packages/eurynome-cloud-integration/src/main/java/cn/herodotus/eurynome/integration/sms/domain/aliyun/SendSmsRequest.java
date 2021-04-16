package cn.herodotus.eurynome.integration.sms.domain.aliyun;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 阿里SendSms请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 13:58
 */
@ApiModel(value = "阿里SendSms请求参数实体")
public class SendSmsRequest extends AbstractMessage {

	/**
	 * 格式：
	 * · 国内短信：+/+86/0086/86或无任何前缀的11位手机号码，例如1381111****。
	 * · 国际/港澳台消息：国际区号+号码，例如852000012****。
	 */
	@ApiModelProperty(name = "接收短信的手机号码", required = true, notes = "支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。")
	@JSONField(name = "PhoneNumbers")
	@JsonProperty("PhoneNumbers")
	private String phoneNumbers;

	/**
	 * 请在控制台国内消息或国际/港澳台消息页面中的签名管理页签下签名名称一列查看。
	 */
	@ApiModelProperty(name = "短信签名名称", notes = "必须是已添加、并通过审核的短信签名")
	@JSONField(name = "SignName")
	@JsonProperty("SignName")
	private String signName;

	/**
	 * 请在控制台国内消息或国际/港澳台消息页面中的模板管理页签下模板CODE一列查看。
	 */
	@ApiModelProperty(name = "短信模板ID", notes = "必须是已添加、并通过审核的短信签名；且发送国际/港澳台消息时，请使用国际/港澳台短信模版。")
	@JSONField(name = "TemplateCode")
	@JsonProperty("TemplateCode")
	private String templateCode;

	@ApiModelProperty(name = "短信模板变量对应的实际值", notes = "JSON格式")
	@JSONField(name = "TemplateParam")
	@JsonProperty("TemplateParam")
	private String templateParam;

	@ApiModelProperty(hidden = true)
	private final Map<String, String> parameters = new HashMap<>();

	public String getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
		this.parameters.put("PhoneNumbers", phoneNumbers);
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
		this.parameters.put("SignName", signName);
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
		this.parameters.put("TemplateCode", templateCode);
	}

	public String getTemplateParam() {
		return templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
		this.parameters.put("TemplateParam", templateParam);
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("phoneNumbers", phoneNumbers)
				.add("signName", signName)
				.add("templateCode", templateCode)
				.add("templateParam", templateParam)
				.toString();
	}
}
