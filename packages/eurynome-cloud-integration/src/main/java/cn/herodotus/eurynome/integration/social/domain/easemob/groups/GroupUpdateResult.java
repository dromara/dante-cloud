package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import java.io.Serializable;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/27 9:04
 */
public class GroupUpdateResult implements Serializable {

    /**
     * 群组描述，true表示修改成功，false表示修改失败
     */
    private Boolean description;
    /**
     * 群组成员最大数，true表示修改成功，false表示修改失败
     */
    private Boolean maxusers;
    /**
     * 群组名称，true表示修改成功，false表示修改失败
     */
    private Boolean groupname;
    /**
     * 群组名称，true表示修改成功，false表示修改失败
     */
    private Boolean membersonly;
    /**
     * 是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主才可以往群里加人。
     */
    private Boolean allowinvites;

}
