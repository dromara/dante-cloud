package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.SyncTask;
import cn.herodotus.eurynome.integration.content.domain.aliyun.common.HitLibInfo;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 阿里图片审核任务实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:46
 */
@ApiModel(value = "阿里云图片审核通用请求任务参数实体")
public class ImageTask extends SyncTask {

    private HitLibInfo extras;

    /**
     * 截帧频率，GIF图、长图检测专用。
     * GIF图可理解为图片数组，interval参数指定了每隔多少张图片抽取一帧进行检测。只有该值存在时，才会对GIF进行截帧。
     * 长图分为长竖图和长横图。
     * 对长竖图（高大于400像素，高宽比大于2.5），按照（高：宽）取整来计算总图数，并进行切割。
     * 对长横图（宽大于400像素，宽高比大于2.5），按照（宽：高）取整来计算总图数，并进行切割。
     * 默认只会检测GIF图、长图的第一帧，interval参数用于指示后台在检测时可按照该间隔跳着检测，以节省检测成本。
     */
    @ApiModelProperty(name = "截帧频率", notes = "GIF图、长图检测专用")
    private Integer interval;

    @ApiModelProperty(name = "最大截帧数量", notes = "GIF图、长图检测专用，默认值为1")
    private Integer maxFrames;

    public HitLibInfo getExtras() {
        return extras;
    }

    public void setExtras(HitLibInfo extras) {
        this.extras = extras;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(Integer maxFrames) {
        this.maxFrames = maxFrames;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("extras", extras)
                .add("interval", interval)
                .add("maxFrames", maxFrames)
                .toString();
    }
}
