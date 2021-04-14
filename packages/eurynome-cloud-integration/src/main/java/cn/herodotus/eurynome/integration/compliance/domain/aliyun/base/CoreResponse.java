package cn.herodotus.eurynome.integration.compliance.domain.aliyun.base;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里审核返回结果通用对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:42
 */
public class CoreResponse implements Serializable {

    /**
     * 检测对象对应的数据ID。
     */
    private String dataId;
    /**
     * 检测任务的ID。
     */
    private String taskId;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dataId", dataId)
                .add("taskId", taskId)
                .toString();
    }
}
