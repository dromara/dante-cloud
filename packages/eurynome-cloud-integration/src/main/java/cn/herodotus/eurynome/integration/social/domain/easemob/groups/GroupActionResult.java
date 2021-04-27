package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

/**
 * <p>Description: TODO </p>
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
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
