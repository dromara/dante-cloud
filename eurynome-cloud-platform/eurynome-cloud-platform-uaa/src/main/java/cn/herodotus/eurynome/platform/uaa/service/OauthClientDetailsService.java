package cn.herodotus.eurynome.platform.uaa.service;

import cn.herodotus.eurynome.component.common.enums.StatusEnum;
import cn.herodotus.eurynome.component.security.domain.HerodotusApplication;
import cn.herodotus.eurynome.component.security.domain.HerodotusClientDetails;
import cn.herodotus.eurynome.upms.api.service.remote.RemoteSysApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author gengwei.zheng
 */

@Slf4j
@Service
public class OauthClientDetailsService implements ClientDetailsService {

    @Autowired
    private RemoteSysApplicationService remoteSysApplicationService;


    /**
     * 这里AdditionalInformation的用途：
     * Oauth2自带表结构，只能满足Oauth2的基本使用，但是如果要实际应用，还需要进一步扩展。
     * 优雅的方式肯定是在不改动原表和代码的情况下，自己扩展数据表。同时，为了保证自己扩展内容可以和原表进行交互，所以提供的AdditionalInformation信息，进行处理。
     * Open-Cloud是把自己扩展表的信息，以JSON的格式存入到AdditionalInformation，取出以后在进行处理和应用。
     *
     * @param clientId
     * @return
     * @throws ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        ClientDetails clientDetails = getRemoteOAuth2ClientDetails(clientId);
        if (clientDetails != null && clientDetails.getAdditionalInformation() != null) {
            String status = clientDetails.getAdditionalInformation().getOrDefault("status", "1").toString();

            if (String.valueOf(StatusEnum.FORBIDDEN.getStatus()).equals(status)) {
                log.warn("[Herodotus] |- Client [{}] has been Forbidden! ", clientDetails.getClientId());
                throw new ClientRegistrationException("客户端已被禁用");
            }
        }
        return clientDetails;
    }

    /**
     * 2019.09.01
     * 由于Oauth2自身的查询使用原生SQL，目前还不知道如何进行缓存处理，为了减少以后的性能问题，所以将oauth_client_details增加了jpa的处理。
     * 同时，为了方便SysApplication和oauth_client_details的联动，将两者作为@OneToOne处理，并将oauth_client_details操作移动到了Upms中。
     *
     * 在处理的过程中，OAuth2ClientDetails需要set 相关的权限。一种解决办法就是需要远程查询两次，第二种办法就是在服务端一次查询完成后返回。
     * 第一种方法感觉太low，所以采取的是第二种方法。
     *
     * 第二种方法在实现过程中比较曲折，最早是在Upms端就直接把值set好，然后远程返回给OAuth。但是在这个过程中出现了Jackson多态问题。
     * 经过查询使用@JsonTypeInfo是Jackson处理多态的方式。逻辑上在GrantedAuthority接口上设置就行，但是TMD这个OAuth2的东西，动不了。
     * 所以最后采取了一种“绕”的方式，重新拼凑一个OAuth2Application对象，装载相关的值，然后拿到OAuth2端进行拼装。
     *
     * @param clientId
     * @return
     */
    public HerodotusClientDetails getRemoteOAuth2ClientDetails(String clientId) {
        HerodotusApplication oauthApplication = remoteSysApplicationService.findByClientId(clientId).getData();

        if (oauthApplication == null) {
            log.error("[Herodotus] |- Can not Fetch the Remote Client Details!");
            return null;
        } else {
            HerodotusClientDetails herodotusClientDetails = oauthApplication.getHerodotusClientDetails();
            herodotusClientDetails.setAuthorities(oauthApplication.getArtisanAuthorities());

            log.debug("[Herodotus] |- Fetch Remote Client Details Successfully! [{}]", herodotusClientDetails.getClientId());
            return herodotusClientDetails;
        }
    }
}
