package cn.herodotus.eurynome.integration.content.domain.aliyun.text;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里文本审核返回值：positions对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 16:30
 */
public class Position implements Serializable {

    private Integer startPos;
    private Integer endPos;

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startPos", startPos)
                .add("endPos", endPos)
                .toString();
    }
}
