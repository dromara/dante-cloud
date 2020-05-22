package cn.herodotus.eurynome.bpmn.logic.dto;

import cn.herodotus.eurynome.data.base.dto.BaseDTO;

import java.util.Date;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 16:52
 */
public class TaskApprover extends BaseDTO {

    /**
     * 节点id
     */
    private String activityId;
    /**
     * 节点名称
     */
    private String activityName;
    /**
     * 执行人的code
     */
    private String userId;
    /**
     * 执行人姓名
     */
    private String userName;

    /**
     * 任务节点结束时间
     */
    private Date endTime;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TaskApprover{" +
                "activityId='" + activityId + '\'' +
                ", activityName='" + activityName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", endTime=" + endTime +
                '}';
    }
}
