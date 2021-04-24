package cn.herodotus.eurynome.integration.content.domain.aliyun.common;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: x,y,w,h通用基础属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:05
 */
public class Coordinate implements Serializable {

	/**
	 * 以图片左上角为坐标原点，小程序码区域左上角到y轴距离，单位：像素。
	 */
	private Float x;
	/**
	 * 以图片左上角为坐标原点，小程序码区域左上角到x轴距离，单位：像素。
	 */
	private Float y;
	/**
	 * 小程序码区域宽度，单位：像素。
	 */
	private Float w;
	/**
	 * 小程序码区域高度，单位：像素。
	 */
	private Float h;

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getW() {
		return w;
	}

	public void setW(Float w) {
		this.w = w;
	}

	public Float getH() {
		return h;
	}

	public void setH(Float h) {
		this.h = h;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("x", x)
				.add("y", y)
				.add("w", w)
				.add("h", h)
				.toString();
	}
}
