package cn.herodotus.eurynome.integration.compliance.domain.aliyun.text;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractFeedbackRequest;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description:  阿里文本检测内容反馈请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:07
 */
public class TextFeedbackRequest extends AbstractFeedbackRequest {

    /**
     * 对应的请求中的dataId。
     */
    private String dataId;
    /**
     * 被检测的内容，最长10,000个字符。
     */
    private String content;


    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dataId", dataId)
                .add("content", content)
                .toString();
    }
}
