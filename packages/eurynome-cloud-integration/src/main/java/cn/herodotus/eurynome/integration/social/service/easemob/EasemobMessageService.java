package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.social.domain.easemob.common.Response;
import cn.herodotus.eurynome.integration.social.domain.easemob.messages.Extend;
import cn.herodotus.eurynome.integration.social.domain.easemob.messages.Message;
import cn.herodotus.eurynome.integration.social.domain.easemob.messages.MessageDTO;
import cn.herodotus.eurynome.integration.social.domain.easemob.messages.TargetType;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.OkHttps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 环信消息API封装服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 11:44
 */
@Service
public class EasemobMessageService extends AbstractEasemobService {

    public Response<String, Map<String, String>> sendMessage(MessageDTO messageDTO) {
        return http().sync("/messages")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(messageDTO)
                .post()
                .getBody()
                .toBean(new TypeRef<Response<String, Map<String, String>>>() {
                });
    }

    public Response<String, Map<String, String>> sendMessage(String userId, List<String> toUsers, String type, String content, String avatarUrl, String nickName) {
        Message message = new Message();
        message.setType(type);
        message.setMsg(content);

        Extend extend = new Extend(avatarUrl, nickName);

        String[] users = new String[toUsers.size()];

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTargetType(TargetType.users);
        messageDTO.setTarget(toUsers.toArray(users));
        messageDTO.setMsg(message);
        messageDTO.setFrom(userId);
        messageDTO.setExtend(extend);

        return sendMessage(messageDTO);
    }
}
