package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

/**
 * <p>Description: 环信创建群组返回数据中Data对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/27 11:02
 */
public class GroupDeleteResult extends GroupCreateResult{

    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
