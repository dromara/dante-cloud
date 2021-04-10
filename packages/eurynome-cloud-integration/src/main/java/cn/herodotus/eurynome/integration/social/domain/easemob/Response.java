package cn.herodotus.eurynome.integration.social.domain.easemob;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>Description: Easemob响应返回对象 </p>
 * <p>
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 13:05
 */
public class Response implements Serializable {

	private String action;

	private String application;

	private String path;

	private String uri;

	private Map<String, String> data;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timestamp = new Date();

	private Integer duration;

	private String organization;

	private String applicationName;

	private String error;

	private String exception;

	@JsonProperty("error_description")
	@JSONField(name = "error_description")
	private String errorDescription;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("action", action)
				.add("application", application)
				.add("path", path)
				.add("uri", uri)
				.add("data", data)
				.add("timestamp", timestamp)
				.add("duration", duration)
				.add("organization", organization)
				.add("applicationName", applicationName)
				.add("error", error)
				.add("exception", exception)
				.add("errorDescription", errorDescription)
				.toString();
	}
}
