package cn.herodotus.eurynome.platform.uaa.domain;

import cn.herodotus.eurynome.component.security.domain.ArtisanAuthority;
import cn.herodotus.eurynome.component.security.domain.ArtisanRole;
import cn.herodotus.eurynome.component.security.domain.ArtisanUserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gengwei.zheng
 */
public class OauthTokenExtra extends HashMap<String, Object> {

    private OauthUserExtra user = new OauthUserExtra();

    private List<ArtisanRole> roles = new ArrayList<>();

    private List<ArtisanAuthority> resources = new ArrayList<>();

    public OauthTokenExtra(ArtisanUserDetails artisanUserDetails) {
        OauthUserExtra userInformation = new OauthUserExtra();
        userInformation.setUserName(artisanUserDetails.getUsername());
        userInformation.setUserId(artisanUserDetails.getUserId());
        userInformation.setNickName(artisanUserDetails.getNickName());

        this.setUser(userInformation);
        this.setRoles(artisanUserDetails.getRoles());
    }

    public void setUser(OauthUserExtra user) {
        this.user = user;
        this.put("user", this.user);
    }

    public void setRoles(List<ArtisanRole> roles) {
        this.roles = roles;
        this.put("roles", this.roles);
    }

    public void setResources(List<ArtisanAuthority> resources) {
        this.resources = resources;
        this.put("resources", this.resources);
    }
}
