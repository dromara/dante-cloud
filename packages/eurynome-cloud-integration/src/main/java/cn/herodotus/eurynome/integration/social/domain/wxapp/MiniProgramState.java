package cn.herodotus.eurynome.integration.social.domain.wxapp;

/**
 * <p>Description: 跳转小程序类型 </p>
 *
 * developer为开发版；trial为体验版；formal为正式版；默认为正式版
 *
 * @author : gengwei.zheng
 * @date : 2021/4/9 16:09
 */
public enum MiniProgramState {

	/**
	 * 开发版
	 */
	developer,

	/**
	 * 体验版
	 */
	trial,

	/**
	 * 正式版
	 */
	formal;
}
