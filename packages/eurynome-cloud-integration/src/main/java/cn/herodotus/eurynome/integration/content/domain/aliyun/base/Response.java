package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里内容检测统一返回实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 9:20
 */
public class Response<T> implements Serializable {

    /**
     * 错误码，和HTTP状态码一致（但有扩展）
     */
    private Integer code;
    /**
     * 错误的详细描述
     */
    private String msg;
    /**
     * 唯一标识该请求的ID，可用于定位问题
     */
    private String requestId;
    /**
     * API（业务）相关的返回数据。出错情况下，该字段可能为空。一般来说，该字段为一个JSON结构体或数组。
     */
    private T data;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("msg", msg)
                .add("requestId", requestId)
                .add("data", data)
                .toString();
    }
}
