package cn.herodotus.eurynome.platform.uaa.domain;

import cn.herodotus.eurynome.component.security.domain.HerodotusAuthority;
import cn.herodotus.eurynome.component.security.domain.HerodotusRole;
import cn.herodotus.eurynome.component.security.domain.HerodotusUserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gengwei.zheng
 */
public class OauthTokenExtra extends HashMap<String, Object> {

    private OauthUserExtra user = new OauthUserExtra();

    private List<HerodotusRole> roles = new ArrayList<>();

    private List<HerodotusAuthority> resources = new ArrayList<>();

    public OauthTokenExtra(HerodotusUserDetails herodotusUserDetails) {
        OauthUserExtra userInformation = new OauthUserExtra();
        userInformation.setUserName(herodotusUserDetails.getUsername());
        userInformation.setUserId(herodotusUserDetails.getUserId());
        userInformation.setNickName(herodotusUserDetails.getNickName());

        this.setUser(userInformation);
        this.setRoles(herodotusUserDetails.getRoles());
    }

    public void setUser(OauthUserExtra user) {
        this.user = user;
        this.put("user", this.user);
    }

    public void setRoles(List<HerodotusRole> roles) {
        this.roles = roles;
        this.put("roles", this.roles);
    }

    public void setResources(List<HerodotusAuthority> resources) {
        this.resources = resources;
        this.put("resources", this.resources);
    }
}
