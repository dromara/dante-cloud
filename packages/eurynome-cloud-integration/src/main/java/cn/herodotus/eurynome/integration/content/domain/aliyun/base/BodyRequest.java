package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 阿里内容检测，以body为主要参数的通用请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/19 14:53
 */
public class BodyRequest<T extends Serializable> implements CoreRequest {

    private List<T> body;

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }
}
