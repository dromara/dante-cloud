package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractTask;

/**
 * <p>Description: WebpageTask </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:23
 */
public class WebpageTask extends AbstractTask {

    /**
     * 网页链接，支持HTTP、HTTP协议的网页检测。
     * 说明 url与content字段二选一。
     */
    private String url;

    /**
     * 检测网页对应的HTML纯文本。
     * 说明 url与content字段二选一。
     */
    private String content;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
