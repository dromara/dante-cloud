/*
 * Copyright (c) 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-integration
 * File Name: TxcloudSmsService.java
 * Author: gengwei.zheng
 * Date: 2021/4/17 上午4:07
 * LastModified: 2021/4/17 上午4:07
 */

package cn.herodotus.eurynome.integration.sms.service.txcloud;

import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.integration.sms.properties.TxcloudSmsProperties;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>File: TxcloudSmsService </p>
 *
 * <p>Description: 腾讯短信服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/17 4:07
 */
@Slf4j
@Service
public class TxcloudSmsService {

    @Autowired
    private TxcloudSmsProperties txcloudSmsProperties;

    /**
     * 发送消息，使用默认模版，需要在TxcloudSmsProperties中，设置defaultTemplateId属性
     *
     * @param phoneNumbers   电话号码
     * @param templateParams 模版参数
     * @return SendSmsResponse
     */
    public SendSmsResponse send(List<String> phoneNumbers, List<String> templateParams) {
        String defaultTemplateId = txcloudSmsProperties.getDefaultTemplateId();
        if (StringUtils.isBlank(defaultTemplateId)) {
            log.error("[Eurynome] |- Can not found default templateId in Tencent sms property, please check!");
        }
        return this.send(phoneNumbers, defaultTemplateId, templateParams);
    }

    /**
     * 发送消息，使用默认模版，需要在TxcloudSmsProperties中，设置defaultTemplateId属性
     *
     * @param phoneNumbers   电话号码字符串，可以是多个以英文“,”分割
     * @param templateParams 模板参数: 若无模板参数，则设置为空，多个参数以英文“,”分割
     * @return SendSmsResponse
     */
    public SendSmsResponse send(String phoneNumbers, String templateParams) {
        String defaultTemplateId = txcloudSmsProperties.getDefaultTemplateId();
        if (StringUtils.isBlank(defaultTemplateId)) {
            log.error("[Eurynome] |- Can not found default templateId in Tencent sms property, please check!");
        }
        return this.send(phoneNumbers, defaultTemplateId, templateParams);
    }

    /**
     * 发送消息
     *
     * @param phoneNumbers   电话号码
     * @param temlateId      自定义模版的ID，用于区分多个模版，该temlateId不是腾讯短信的模版id。
     * @param templateParams 模板参数: 若无模板参数，则设置为空
     * @return SendSmsResponse
     */
    public SendSmsResponse send(List<String> phoneNumbers, String temlateId, List<String> templateParams) {
        String phoneNumberString = StringUtils.join(phoneNumbers, SymbolConstants.COMMA);
        String templateParamString = StringUtils.join(templateParams, SymbolConstants.COMMA);
        return this.send(phoneNumberString, temlateId, templateParamString);
    }

    /**
     * 发送消息
     *
     * @param phoneNumbers   电话号码字符串，可以是多个以英文“,”分割
     * @param temlateId      自定义模版的ID，用于区分多个模版，该temlateId不是腾讯短信的模版id。
     * @param templateParams 模板参数: 若无模板参数，则设置为空，多个参数以英文“,”分割
     * @return SendSmsResponse
     */
    public SendSmsResponse send(String phoneNumbers, String temlateId, String templateParams) {
        String[] phoneNumberArray = StringUtils.split(phoneNumbers, SymbolConstants.COMMA);
        String[] templateParamArray = StringUtils.split(templateParams, SymbolConstants.COMMA);
        String templateCode = txcloudSmsProperties.getTemplates().get(temlateId);
        if (StringUtils.isBlank(templateCode)) {
            log.error("[Eurynome] |- Can not found templateId in Tencent sms templates property, please check!");
        }
        return this.send(phoneNumberArray, templateCode, templateParamArray);
    }

    /**
     * 发送消息
     *
     * @param phoneNumbers   下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
     * @param templateCode   模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID
     * @param templateParams 模板参数: 若无模板参数，则设置为空
     * @return SendSmsResponse
     */
    public SendSmsResponse send(String[] phoneNumbers, String templateCode, String[] templateParams) {

        Credential credential = new Credential(txcloudSmsProperties.getAppId(), txcloudSmsProperties.getAppKey());
        ClientProfile clientProfile = new ClientProfile();
        /**
         * SDK 默认用 TC3-HMAC-SHA256 进行签名
         * 非必要请不要修改该字段
         */
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);

        SmsClient smsClient = new SmsClient(credential, txcloudSmsProperties.getRegionId(), clientProfile);

        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setSmsSdkAppid(txcloudSmsProperties.getAppId());
        sendSmsRequest.setSign(txcloudSmsProperties.getSignName());
        sendSmsRequest.setTemplateID(templateCode);
        sendSmsRequest.setPhoneNumberSet(phoneNumbers);
        sendSmsRequest.setTemplateParamSet(templateParams);

        try {
            SendSmsResponse sendSmsResponse = smsClient.SendSms(sendSmsRequest);
            log.debug("[Eurynome] |- Tencent Cloud send message successful! result is : {}", sendSmsResponse.toString());
            return sendSmsResponse;
        } catch (TencentCloudSDKException e) {
            log.error("[Eurynome] |- Tencent Cloud send message catch TencentCloudSDKException: {}", e.getMessage());
            return null;
        }
    }
}
