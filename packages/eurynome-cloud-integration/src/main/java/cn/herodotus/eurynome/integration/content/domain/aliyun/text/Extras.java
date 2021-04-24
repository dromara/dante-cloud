package cn.herodotus.eurynome.integration.content.domain.aliyun.text;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里文本审核返回值：extras对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 16:27
 */
public class Extras implements Serializable {
    /**
     * 附加信息，扩展字段
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .toString();
    }
}
