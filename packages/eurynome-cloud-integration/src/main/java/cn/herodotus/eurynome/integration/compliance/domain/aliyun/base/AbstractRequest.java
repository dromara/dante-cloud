package cn.herodotus.eurynome.integration.compliance.domain.aliyun.base;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 请求基础抽象类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:00
 */
public abstract class AbstractRequest<T extends AbstractTask> extends CoreRequest {
    /**
     * 该字段用于标识您的业务场景。您可以通过内容安全控制台创建业务场景（具体操作，请参见自定义机审标准），或者提交工单联系我们帮助您创建业务场景。
     */
    private String bizType;

    /**
     * 指定检测对象，JSON数组中的每个元素是一个检测任务结构体。最多支持100个元素，即每次提交100条内容进行检测，支持100个元素的前提是需要将并发任务调整到100个以上。关于每个元素的具体结构描述，请参见task。
     */
    private List<T> tasks;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public List<T> getTasks() {
        return tasks;
    }

    public void setTasks(List<T> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bizType", bizType)
                .toString();
    }
}
