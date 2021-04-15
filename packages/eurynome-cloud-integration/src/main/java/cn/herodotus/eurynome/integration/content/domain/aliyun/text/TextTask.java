package cn.herodotus.eurynome.integration.content.domain.aliyun.text;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractClientTask;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里文本检查任务实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 15:33
 */
public class TextTask extends AbstractClientTask {

    /**
     * 待检测文本，最长10000个中文字符（包含标点）。
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", content)
                .toString();
    }
}
