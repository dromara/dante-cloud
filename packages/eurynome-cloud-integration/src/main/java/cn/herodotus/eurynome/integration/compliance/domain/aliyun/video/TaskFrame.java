package cn.herodotus.eurynome.integration.compliance.domain.aliyun.video;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里视频检测任务相关frame实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:55
 */
public class TaskFrame implements Serializable {

    /**
     * 视频截帧的URL，与framePrefix一起组成截帧的完整地址。视频截帧的完整地址格式为framePrefix + frame.url。
     */
    private String url;

    /**
     * 截帧距离片头的时间戳，单位为秒。
     */
    private Integer offset;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("offset", offset)
                .toString();
    }
}
