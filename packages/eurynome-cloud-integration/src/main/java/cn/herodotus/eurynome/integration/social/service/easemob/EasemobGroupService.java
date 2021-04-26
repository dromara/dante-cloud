package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.social.domain.easemob.common.Response;
import cn.herodotus.eurynome.integration.social.domain.easemob.groups.GroupResult;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.OkHttps;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 环信Group相关操作服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/25 17:59
 */
@Service
public class EasemobGroupService extends AbstractEasemobService {

    public Response<String, GroupResult> findGroups() {
        return http().sync("/chatgroups")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .get()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupResult>>() {
                });
    }

    public Response<String, GroupResult> findUserGroups(String username) {
        String url = getUserUrl(username) + "/joined_chatgroups";
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .get()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupResult>>() {
                });
    }


}
