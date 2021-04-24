package cn.herodotus.eurynome.integration.social.domain.easemob;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 图片尺寸；height：高度，width：宽度 </p>
 *
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/2 11:39
 */
public class Size implements Serializable {

    private Integer height;
    private Integer width;

    public Size() {
    }

    public Size(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("height", height)
                .add("width", width)
                .toString();
    }
}
