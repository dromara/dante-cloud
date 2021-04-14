package cn.herodotus.eurynome.integration.compliance.domain.aliyun.base;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里审核返回结果通用对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:42
 */
public class BaseResponse extends CoreResponse {

    /**
     * 错误码，和HTTP状态码一致。
     */
    private Integer code;
    /**
     * 请求信息的响应信息。
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("msg", msg)
                .toString();
    }
}
