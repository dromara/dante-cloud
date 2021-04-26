package cn.herodotus.eurynome.integration.social.domain.easemob.messages;

/**
 * <p>Description: 环信消息类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/2 11:42
 */
public enum MessageType {

    /**
     * 文本消息
     */
    txt,

    /**
     * 图片消息
     */
    img,

    /**
     * 位置消息
     */
    loc,

    /**
     *
     */
    audio,

    /**
     * 视频消息
     */
    video,

    /**
     * 文件消息
     */
    file,

    /**
     * 透传消息
     */
    cmd;
}
