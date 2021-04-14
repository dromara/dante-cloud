package cn.herodotus.eurynome.integration.compliance.domain.aliyun.voice;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里音频同步检测返回结果： detail 对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:56
 */
public class Detail {
    /**
     * 句子开始的时间戳，单位：秒。
     */
    private Integer startTime;
    /**
     * 句子结束的时间戳，单位：秒。
     */
    private Integer endTime;
    /**
     * 语音转换成文本的结果。
     */
    private String text;
    /**
     * 检测结果的分类。取值：
     * normal：正常文本
     * spam：含垃圾信息
     * ad：广告
     * politics：涉政
     * terrorism：暴恐
     * abuse：辱骂
     * porn：色情
     * flood：灌水
     * contraband：违禁
     * meaningless：无意义
     * customized：自定义（例如命中自定义关键词）
     */
    private String label;
    /**
     * 声纹识别结果，如果命中了敏感人物的声纹，则会返回该字段。
     * 具体结构如下：
     * name：字符串类型，通过语音识别的敏感人物信息。
     * 说明 默认不返回该字段。如果有需要，请提交工单联系我们配置。
     */
    private List<Person> persons;
    /**
     * 如果了命中用户自定义关键词，返回命中的关键词。
     */
    private String keyword;
    /**
     * 如果了命中用户自定义关键词，返回关键词所在的词库。
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

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
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
