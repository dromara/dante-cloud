package cn.herodotus.eurynome.integration.sms.service.aliyun;

import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import cn.herodotus.eurynome.integration.sms.domain.aliyun.SendSmsRequest;
import cn.herodotus.eurynome.integration.sms.domain.aliyun.SmsResponse;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 阿里发送短信服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 15:01
 */
@Slf4j
@Service
public class AliyunSendSmsService {

    @Autowired
    private AliyunProperties aliyunProperties;
    @Autowired
    private IAcsClient iAcsClient;

    /**
     * 发送短信 使用默认模版，需要在AliyunProperties中，设置defaultTemplateId属性
     *
     * @param phoneNumbers  电话号码
     * @param templateParam 短信模版中涉及的参数
     * @return 自定义短信响应实体 SmsResponse {@link SmsResponse}
     */
    public SmsResponse send(List<String> phoneNumbers, Map<String, Object> templateParam) {
        String phoneNumberString = StringUtils.join(phoneNumbers, ",");
        String defaultTemplateId = aliyunProperties.getSms().getDefaultTemplateId();
        if (StringUtils.isBlank(defaultTemplateId)) {
            log.error("[Eurynome] |- Can not found default templateId in Aliyun sms property, please check!");
        }
        return this.send(phoneNumberString, templateParam);
    }

    /**
     * 发送短信 使用默认模版，需要在AliyunProperties中，设置defaultTemplateId属性
     *
     * @param phoneNumbers  电话号码字符串，可以是多个电话号码以英文“,”分割
     * @param templateParam 短信模版中涉及的参数
     * @return 自定义短信响应实体 SmsResponse {@link SmsResponse}
     */
    public SmsResponse send(String phoneNumbers, Map<String, Object> templateParam) {
        String defaultTemplateId = aliyunProperties.getSms().getDefaultTemplateId();
        if (StringUtils.isBlank(defaultTemplateId)) {
            log.error("[Eurynome] |- Can not found default templateId in Aliyun sms property, please check!");
        }
        return this.send(phoneNumbers, defaultTemplateId, templateParam);
    }

    /**
     * 发送短信
     *
     * @param phoneNumbers  电话号码
     * @param templateId    自定义模版的ID，用于区分多个模版，该temlateId不是阿里短信的模版id。而是自己在配置文件中配置的、与阿里模版id对应的key值
     * @param templateParam 短信模版中涉及的参数
     * @return 自定义短信响应实体 SmsResponse {@link SmsResponse}
     */
    public SmsResponse send(List<String> phoneNumbers, String templateId, Map<String, Object> templateParam) {
        String phoneNumberString = StringUtils.join(phoneNumbers, ",");
        return this.send(phoneNumberString, templateId, templateParam);
    }

    public SmsResponse send(String phoneNumbers, String templateId, Map<String, Object> templateParam) {
        String templateParamString = JSON.toJSONString(templateParam);
        String templateCode = aliyunProperties.getSms().getTemplates().get(templateId);
        if (StringUtils.isBlank(templateCode)) {
            log.error("[Eurynome] |- Can not found templateId in Aliyun sms templates property, please check!");
        }
        return this.send(phoneNumbers, templateCode, templateParamString);
    }

    private SmsResponse send(String phoneNumbers, String templateCode, String templateParam) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phoneNumbers);
        sendSmsRequest.setSignName(aliyunProperties.getSms().getSignName());
        sendSmsRequest.setTemplateCode(templateCode);
        sendSmsRequest.setTemplateParam(templateParam);
        return this.send(sendSmsRequest);
    }


    private SmsResponse send(SendSmsRequest sendSmsRequest) {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysMethod(MethodType.POST);
        commonRequest.setSysDomain(aliyunProperties.getSms().getDomain());
        commonRequest.setSysVersion(aliyunProperties.getSms().getVersion());
        commonRequest.setSysAction(sendSmsRequest.getAction());
        sendSmsRequest.getParameters().forEach(commonRequest::putQueryParameter);

        try {
            CommonResponse commonResponse = iAcsClient.getCommonResponse(commonRequest);
            if (ObjectUtils.isNotEmpty(commonResponse) && commonResponse.getHttpStatus() == 200) {
                SmsResponse smsResponse = JSON.parseObject(commonResponse.getData(), SmsResponse.class);
                log.debug("[Eurynome] |- Aliyun send message result success. result is: {}", smsResponse.toString());
                return smsResponse;
            } else {
                log.error("[Eurynome] |- Aliyun send message result convert to entity error!");
                return null;
            }
        } catch (ClientException e) {
            log.error("[Eurynome] |- Aliyun send message catch ClientException: {}", e.getMessage());
            return null;
        }
    }
}
