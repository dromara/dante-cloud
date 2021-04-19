package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>Description: 抽象的返回请求参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:48
 */
public class AbstractFeedbackBody implements CoreRequest {

    /**
     * 内容安全服务器返回的，唯一标识该检测任务的ID。
     */
    @ApiModelProperty(name = "内容安全服务器返回的，唯一标识该检测任务的ID", required = true)
    private String taskId;

    /**
     * 对应的请求中的dataId
     */
    private String dataId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("taskId", taskId)
                .add("dataId", dataId)
                .toString();
    }
}
