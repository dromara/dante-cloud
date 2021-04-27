package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.social.domain.easemob.common.Response;
import cn.herodotus.eurynome.integration.social.domain.easemob.messages.MessageResult;
import cn.herodotus.eurynome.integration.social.domain.easemob.messages.MessageDTO;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.OkHttps;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 环信消息API封装服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 11:44
 */
@Service
public class EasemobMessageService extends AbstractEasemobService {

    public Response<String, MessageResult> sendMessage(MessageDTO messageDTO) {
        return http().sync("/messages")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(messageDTO)
                .post()
                .getBody()
                .toBean(new TypeRef<Response<String, MessageResult>>() {
                });
    }
}
