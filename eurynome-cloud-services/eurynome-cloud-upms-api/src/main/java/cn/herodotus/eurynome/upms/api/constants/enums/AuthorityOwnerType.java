package cn.herodotus.eurynome.upms.api.constants.enums;

public enum AuthorityOwnerType {

    APPLICATION(0, "application", "应用系统的"),
    USER(1, "user","系统用户的");

    private Integer code;
    private String type;
    private String description;

    AuthorityOwnerType(Integer code, String type, String description) {
        this.code = code;
        this.type = type;
        this.description = description;
    }
    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
