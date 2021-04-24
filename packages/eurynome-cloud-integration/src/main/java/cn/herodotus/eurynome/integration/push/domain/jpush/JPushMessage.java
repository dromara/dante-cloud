package cn.herodotus.eurynome.integration.push.domain.jpush;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/20 9:49
 */
public class JPushMessage implements Serializable {

    private String title;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 角标
     */
    private Integer badge = 1;

    /**
     * 优先级
     */
    private Integer priority = 0;

    /**
     * 附加业务参数
     */
    private Map<String, String> extras;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
