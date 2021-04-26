package cn.herodotus.eurynome.integration.social.domain.easemob.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: Easemob响应返回对象 </p>
 * <p>
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 13:05
 */
public class Response<E extends Serializable, D extends Serializable> implements Serializable {

	private String path;

	private String uri;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timestamp = new Date();

	private String organization;

	private String application;

	private List<E> entities;

	private String action;

	private List<D> data;

	private Integer duration;

	private String applicationName;

	private String cursor;

	private Integer count;

	private String error;

	private String exception;

	@JsonProperty("error_description")
	@JSONField(name = "error_description")
	private String errorDescription;

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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public List<E> getEntities() {
		return entities;
	}

	public void setEntities(List<E> entities) {
		this.entities = entities;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<D> getData() {
		return data;
	}

	public void setData(List<D> data) {
		this.data = data;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("path", path)
				.add("uri", uri)
				.add("timestamp", timestamp)
				.add("organization", organization)
				.add("application", application)
				.add("entities", entities)
				.add("action", action)
				.add("data", data)
				.add("duration", duration)
				.add("applicationName", applicationName)
				.add("cursor", cursor)
				.add("count", count)
				.add("error", error)
				.add("exception", exception)
				.add("errorDescription", errorDescription)
				.toString();
	}
}
