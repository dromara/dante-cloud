package cn.herodotus.eurynome.integration.content.domain.aliyun.text;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractSyncResponse;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里文本审核返回值对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 16:41
 */
public class TextSyncResponse extends AbstractSyncResponse<TextResult> {

    /**
     * 被检测文本，和调用请求中的待检测文本对应。
     */
    private String content;
    /**
     * 如果被检测文本命中了自定义关键词词库中的关键词，则会返回当前字段，并将命中的关键词替换为星号（*）。
     */
    private String filteredContent;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilteredContent() {
        return filteredContent;
    }

    public void setFilteredContent(String filteredContent) {
        this.filteredContent = filteredContent;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", content)
                .add("filteredContent", filteredContent)
                .toString();
    }
}
