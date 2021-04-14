package cn.herodotus.eurynome.integration.compliance.domain.aliyun.voice;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里音频同步检测返回结果： person 对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 15:18
 */
public class Person implements Serializable {
    /**
     * 敏感人名A
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}
