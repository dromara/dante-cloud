package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里视频异步检测结果查询返回数据实体对应Details属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/19 16:23
 */
public class Detail implements Serializable {

    /**
     * 句子开始的时间，单位为秒
     */
    private Integer startTime;
    /**
     * 句子结束的时间，单位为秒。
     */
    private Integer endTime;
    /**
     * 语音转换成文本的结果
     */
    private String text;
    /**
     * 该句语音的检测结果分类。取值：
     * normal：正常
     * spam：包含垃圾信息
     * ad：广告
     * politics：涉政
     * terrorism：暴恐
     * abuse：辱骂
     * porn：色情
     * flood：灌水
     * contraband：违禁
     * customized：自定义（例如命中自定义关键词）
     */
    private String label;
    /**
     * 如果命中了用户自定义关键词，返回命中的关键词
     */
    private String keyword;
    /**
     * 如果命中了用户自定义关键词，返回关键词所在词库
     */
    private String libName;

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("text", text)
                .add("label", label)
                .add("keyword", keyword)
                .add("libName", libName)
                .toString();
    }
}
