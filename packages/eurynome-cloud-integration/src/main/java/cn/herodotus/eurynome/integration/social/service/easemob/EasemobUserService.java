package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.social.domain.easemob.common.Response;
import cn.herodotus.eurynome.integration.social.domain.easemob.users.UserEntity;
import cn.herodotus.eurynome.integration.social.domain.easemob.users.UserRegisterDTO;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.OkHttps;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 环信用户管理Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/25 14:59
 */
@Service
public class EasemobUserService extends AbstractEasemobService {

    public Response<UserEntity, String> createUser(UserRegisterDTO userRegisterDTO) {
        return http().sync("/users")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(userRegisterDTO)
                .post()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }

    public Response<UserEntity, String> createUser(List<UserRegisterDTO> users) {
        return http().sync("/users")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(users)
                .post()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }

    public Response<UserEntity, String> resetNickname(String username, String nickname) {
        String url = this.getUserUrl(username);
        Map<String, String> body = ImmutableMap.of("nickname", nickname);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(body)
                .put()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }

    public Response<UserEntity, String> resetPassword(String username, String password) {
        String url = this.getUserUrl(username) + "/password";
        Map<String, String> body = ImmutableMap.of("newpassword", password);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(body)
                .put()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }


    public Response<UserEntity, String> deleteUser(String username) {
        String url = getUserUrl(username);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .delete()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }

    public Response<UserEntity, String> findUser(String username) {
        String url = getUserUrl(username);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .get()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }

    public Response<UserEntity, String> findUsers(int limit) {
        return http().sync("/users")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .addUrlPara("limit", limit)
                .get()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }


    public Response<UserEntity, String> addContact(String owner, String friend) {
        String url = getContactUrl(owner, friend);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .post()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }

    public Response<UserEntity, String> deleteContact(String owner, String friend) {
        String url = getContactUrl(owner, friend);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .delete()
                .getBody()
                .toBean(new TypeRef<Response<UserEntity, String>>() {
                });
    }

    public Response<String, List<String>> findFriends(String owner) {
        String url = getContactUrl(owner);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .get()
                .getBody()
                .toBean(new TypeRef<Response<String, List<String>>>() {
                });
    }

}
