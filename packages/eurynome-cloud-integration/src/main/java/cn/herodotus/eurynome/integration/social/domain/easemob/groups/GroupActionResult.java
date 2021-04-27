package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: Group辅助操作返回对象data对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/27 8:53
 */
public class GroupActionResult extends GroupCreateResult{

    /**
     * 执行的操作
     */
    private String action;
    /**
     * 被添加的用户 ID
     */
    private String user;
    /**
     * 添加结果，true表示添加成功，false表示添加失败
     */
    private Boolean result;

    private String reason;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("action", action)
                .add("user", user)
                .add("result", result)
                .add("reason", reason)
                .toString();
    }
}
