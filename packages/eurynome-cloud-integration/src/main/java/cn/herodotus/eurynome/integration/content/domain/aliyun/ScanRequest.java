package cn.herodotus.eurynome.integration.content.domain.aliyun;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 9:48
 */
public class ScanRequest {

    @ApiModelProperty(value = "检测返回id，用做异步检测查询")
    private List<String> taskIds;
    @ApiModelProperty(value = "检测图片URL集合")
    private List<String> imageUrls;
    @ApiModelProperty(value = "检测视频URL集合")
    private List<String> videoUrls;
    @ApiModelProperty(value = "检测语音URL集合")
    private List<String> voiceUrls;
    @ApiModelProperty(value = "检测文字内容(单次最多一万字)")
    private String text;

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public List<String> getVoiceUrls() {
        return voiceUrls;
    }

    public void setVoiceUrls(List<String> voiceUrls) {
        this.voiceUrls = voiceUrls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
