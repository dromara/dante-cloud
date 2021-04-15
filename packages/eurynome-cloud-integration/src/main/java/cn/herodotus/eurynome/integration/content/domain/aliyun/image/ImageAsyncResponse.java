package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.BaseResponse;

/**
 * <p>Description: 阿里图片审核异步返回值对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:23
 */
public class ImageAsyncResponse extends BaseResponse {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
