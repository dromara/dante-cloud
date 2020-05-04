package cn.herodotus.eurynome.component.management.domain;

import java.io.Serializable;

/**
 * <p> Description : Config </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/4 14:42
 */
public class Config implements Serializable {

    public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    /**
     * the data id of extended configuration.
     */
    private String dataId;

    /**
     * the group of extended configuration, the default value is DEFAULT_GROUP.
     */
    private String group = "DEFAULT_GROUP";

    private String content;

    public Config() {
    }

    public Config(String dataId) {
        this.dataId = dataId;
    }

    public Config(String dataId, String group) {
        this.dataId = dataId;
        this.group = group;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
