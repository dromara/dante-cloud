package cn.herodotus.eurynome.platform.uaa.domain;

import java.io.Serializable;

/**
 * 用于控制返回Token中，额外的用户信息。将没有必要的信息进行屏蔽。
 * @author gengwei.zheng
 */
public class OauthUserExtra implements Serializable {

    private String userName;
    private String userId;
    private String nickName;
    private String employeeId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
