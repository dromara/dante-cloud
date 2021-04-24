package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 阿里视频检测内容反馈请求参数Frames实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 12:39
 */
@ApiModel(value = "阿里视频检测内容反馈请求参数Frames实体")
public class FeedbackFrame {

    @ApiModelProperty(name = "截帧地址", required = true)
    private String url;

    @ApiModelProperty(name = "该截帧距离片头的时间戳", required = true, notes = "单位为秒")
    private Integer offset;

    @ApiModelProperty(name = "备注")
    private String note;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("offset", offset)
                .add("note", note)
                .toString();
    }
}
