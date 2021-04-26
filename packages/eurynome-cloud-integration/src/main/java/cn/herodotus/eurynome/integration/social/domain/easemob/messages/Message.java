package cn.herodotus.eurynome.integration.social.domain.easemob.messages;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 环信请求对象Msg参数封装实体 </p>
 * <p>
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/2 11:38
 */
public class Message {

	/**
	 * 消息类型；txt:文本消息，img：图片消息，loc：位置消息，audio：语音消息，video：视频消息，file：文件消息，cmd：透传消息
	 */
	private MessageType type;
	/**
	 * 消息内容
	 */
	private String msg;
	/**
	 * 图片宽高
	 */
	private Size size;
	/**
	 * 成功上传文件返回的UUID
	 */
	private String url;
	/**
	 * 上传的文件（如：图片名称）
	 */
	private String filename;
	/**
	 * 成功上传文件后返回的secret秘钥
	 */
	private String secret;

	/**
	 * 语音时间（单位：秒），视频播放长度
	 */
	private Integer length;
	/**
	 * 视频文件大小（单位：字节）
	 */
	private Integer fileLength;
	/**
	 * 成功上传视频缩略图后返回的secret
	 */
	private String thumbSecret;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 经度
	 */
	private String lng;
	/**
	 * 地址
	 */
	private String addr;

	public Message() {
	}

	public Message(MessageType type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public Message(MessageType type, String msg, Size size, String url, String filename, String secret, Integer length, Integer fileLength, String thumbSecret, String lat, String lng, String addr) {
		this.type = type;
		this.msg = msg;
		this.size = size;
		this.url = url;
		this.filename = filename;
		this.secret = secret;
		this.length = length;
		this.fileLength = fileLength;
		this.thumbSecret = thumbSecret;
		this.lat = lat;
		this.lng = lng;
		this.addr = addr;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getFileLength() {
		return fileLength;
	}

	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}

	public String getThumbSecret() {
		return thumbSecret;
	}

	public void setThumbSecret(String thumbSecret) {
		this.thumbSecret = thumbSecret;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("type", type)
				.add("msg", msg)
				.add("size", size)
				.add("url", url)
				.add("filename", filename)
				.add("secret", secret)
				.add("length", length)
				.add("fileLength", fileLength)
				.add("thumbSecret", thumbSecret)
				.add("lat", lat)
				.add("lng", lng)
				.add("addr", addr)
				.toString();
	}
}
