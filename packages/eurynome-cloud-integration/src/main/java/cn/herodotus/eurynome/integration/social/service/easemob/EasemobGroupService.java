package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.social.domain.easemob.common.Response;
import cn.herodotus.eurynome.integration.social.domain.easemob.groups.*;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.OkHttps;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 环信Group相关操作服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/25 17:59
 */
@Service
public class EasemobGroupService extends AbstractEasemobService {

    public Response<String, GroupCreateResult> createGroup(GroupCreateDTO groupCreateDTO) {
        return http().sync("/chatgroups")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(groupCreateDTO)
                .post()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupCreateResult>>() {
                });
    }

    public Response<String, GroupDeleteResult> deleteGroup(String groupId) {
        String url = getGroupUrl(groupId);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .delete()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupDeleteResult>>() {
                });
    }

    /**
     * 管理群组成员, 添加单个群组成员
     *
     * @param groupId  群组ID
     * @param username 群组成员用户名
     * @return Response<String, GroupActionResult>
     */
    public Response<String, GroupActionResult> createGroupMember(String groupId, String username) {
        String url = getGroupMemberUrl(groupId, username);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .post()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupActionResult>>() {
                });
    }

    public Response<String, GroupActionResult> deleteGroupMember(String groupId, String username) {
        String url = getGroupMemberUrl(groupId, username);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .delete()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupActionResult>>() {
                });
    }

    public Response<String, GroupUpdateResult> updateGroup(String groupId, GroupUpdateDTO groupUpdateDTO) {
        String url = getGroupUrl(groupId);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(groupUpdateDTO)
                .put()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupUpdateResult>>() {
                });
    }

    public Response<String, List<GroupUserResult>> findUserGroups(String username) {
        String url = getUserUrl(username) + "/joined_chatgroups";
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .get()
                .getBody()
                .toBean(new TypeRef<Response<String, List<GroupUserResult>>>() {
                });
    }

    public Response<String, List<GroupSimpleResult>> findGroups() {
        return http().sync("/chatgroups")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .get()
                .getBody()
                .toBean(new TypeRef<Response<String, List<GroupSimpleResult>>>() {
                });
    }

    public Response<String, GroupDetailResult> findGroup(String groupId) {
        String url = getGroupUrl(groupId);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .get()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupDetailResult>>() {
                });
    }

    public Response<String, GroupActionResult> addGroupBlockUser(String groupId, String username) {
        String url = getGroupBlockUrl(groupId, username);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .post()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupActionResult>>() {
                });
    }

    public Response<String, GroupActionResult> deleteGroupBlockUser(String groupId, String username) {
        String url = getGroupBlockUrl(groupId, username);
        return http().sync(url)
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .delete()
                .getBody()
                .toBean(new TypeRef<Response<String, GroupActionResult>>() {
                });
    }
}
