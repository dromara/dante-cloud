package cn.herodotus.eurynome.integration.content.domain.baidu;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 百度识别位置对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 14:17
 */
public class Location implements Serializable {

    private Integer left;
    private Integer top;
    private Integer width;
    private Integer height;

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("left", left)
                .add("top", top)
                .add("width", width)
                .add("height", height)
                .toString();
    }
}
