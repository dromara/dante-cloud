package cn.herodotus.eurynome.upms.api.constants.enums;

/**
 * <p> Description : 应用技术类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:17
 */
public enum TechnologyType {

    /**
     * enum
     */
    JAVA(0, "Java PC网页应用"),
    NET(1, ".Net PC网页应用"),
    PHP(2, "PHP PC网页应用"),
    NODE(3, "前后端分离 PC网页应用"),
    IOS(4, "苹果手机应用"),
    ANDROID(4, "安卓手机应用"),
    WEAPP(4, "微信小程序应用"),
    ALIAPP(4, "支付宝小程序应用"),
    DUAPP(4, "百度小程序应用");

    private Integer type;
    private String description;

    TechnologyType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
