package cn.herodotus.eurynome.integration.social.domain.easemob;

/**
 * <p>Description: 环信 TargetType 枚举</p>
 *
 * 使用枚举减少类型手动输入带来的错误
 *
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/2 11:33
 */
public enum TargetType {

    /**
     * 给用户发消息
     */
    users,

    /**
     * 给群发消息
     */
    chatgroups,

    /**
     * 给聊天室发消息
     */
    chatrooms;
}
