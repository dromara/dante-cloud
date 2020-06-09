package cn.herodotus.eurynome.upms.api.entity.social;

import cn.herodotus.eurynome.crud.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.enums.Gender;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "social_weapp_user", indexes = {@Index(name = "weapp_user_id_idx", columnList = "openid")})
public class WeappUser extends BaseSysEntity {

    @Id
    @Column(name = "openid", length = 64)
    private String openId;

    @Column(name = "nick_name", length = 64)
    private String nickName;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(name = "language")
    private String language;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "avatar_url", length = 2000)
    private String avatarUrl;

    @Column(name = "unionid", length = 64)
    private String unionId;

    @Column(name = "appid", length = 64)
    private String appid;

    @Column(name = "phone_number", length = 512)
    private String phoneNumber;

    @Column(name = "pure_phone_number", length = 512)
    private String purePhoneNumber;

    @Column(name = "country_code", length = 10)
    private String countryCode;

    @Override
    public String getId() {
        return getOpenId();
    }

    @Override
    public String getLinkedProperty() {
        return null;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("openId", openId)
                .append("nickName", nickName)
                .append("gender", gender)
                .append("language", language)
                .append("city", city)
                .append("province", province)
                .append("country", country)
                .append("avatarUrl", avatarUrl)
                .append("unionId", unionId)
                .append("appid", appid)
                .append("phoneNumber", phoneNumber)
                .append("purePhoneNumber", purePhoneNumber)
                .append("countryCode", countryCode)
                .toString();
    }
}
