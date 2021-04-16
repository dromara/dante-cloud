package cn.herodotus.eurynome.integration.content.configuration;

import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.*;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: 阿里云内容管理配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 10:26
 */
@Slf4j
@EnableConfigurationProperties(AliyunProperties.class)
@ComponentScan(basePackages = {
		"cn.herodotus.eurynome.integration.content.service.aliyun.scan"
})
public class AliyunScanConfiguration {

	@PostConstruct
	public void init() {
		log.info("[Braineex] |- Bean [Aliyun Scan] Auto Configure.");
	}

	@Autowired
	private AliyunProperties aliyunProperties;

	@Bean
	@ConditionalOnMissingBean(IAcsClient.class)
	public IAcsClient iAcsClient() {
		IClientProfile iClientProfile = DefaultProfile.getProfile(aliyunProperties.getRegionId(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
		IAcsClient iAcsClient = new DefaultAcsClient(iClientProfile);
		log.debug("[Braineex] |- Bean [iAcsClient] Auto Configure.");
		return iAcsClient;
	}

	/**
	 * 文本识别请求
	 *
	 * @return {@link TextScanRequest}
	 */
	@Bean
	public TextScanRequest textScanRequest() {
		TextScanRequest textScanRequest = new TextScanRequest();
		//指定api返回格式
		textScanRequest.setSysAcceptFormat(FormatType.JSON);
		//设定请求内容格式
		textScanRequest.setHttpContentType(FormatType.JSON);
		//指定请求方法
		textScanRequest.setSysMethod(MethodType.POST);
		textScanRequest.setSysEncoding(StandardCharsets.UTF_8.name());
		textScanRequest.setSysRegionId(aliyunProperties.getRegionId());
		// 请务必设置超时时间
		textScanRequest.setSysConnectTimeout(aliyunProperties.getScan().getText().getConnectTimeout());
		textScanRequest.setSysReadTimeout(aliyunProperties.getScan().getText().getReadTimeout());

		log.debug("[Braineex] |- Bean [Text Scan Request] Auto Configure.");
		return textScanRequest;
	}

	/**
	 * 文本识别请求
	 *
	 * @return {@link TextScanRequest}
	 */
	@Bean
	public TextFeedbackRequest textFeedbackRequest() {
		TextFeedbackRequest textFeedbackRequest = new TextFeedbackRequest();
		//指定api返回格式
		textFeedbackRequest.setSysAcceptFormat(FormatType.JSON);
		//设定请求内容格式
		textFeedbackRequest.setHttpContentType(FormatType.JSON);
		//指定请求方法
		textFeedbackRequest.setSysMethod(MethodType.POST);
		textFeedbackRequest.setSysEncoding(StandardCharsets.UTF_8.name());
		textFeedbackRequest.setSysRegionId(aliyunProperties.getRegionId());
		// 请务必设置超时时间
		textFeedbackRequest.setSysConnectTimeout(aliyunProperties.getScan().getText().getConnectTimeout());
		textFeedbackRequest.setSysReadTimeout(aliyunProperties.getScan().getText().getReadTimeout());

		log.debug("[Braineex] |- Bean [Text Feedback Request] Auto Configure.");
		return textFeedbackRequest;
	}

	/**
	 * 图片同步请求（推荐使用同步）
	 *
	 * @return
	 */
	@Bean
	public ImageSyncScanRequest imageSyncScanRequest() {
		ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
		// 指定api返回格式
		imageSyncScanRequest.setSysAcceptFormat(FormatType.JSON);
		// 指定请求方法
		imageSyncScanRequest.setSysMethod(MethodType.POST);
		imageSyncScanRequest.setSysEncoding(StandardCharsets.UTF_8.name());
		//支持http和https
		imageSyncScanRequest.setSysProtocol(ProtocolType.HTTP);

		/**
		 * 请设置超时时间, 服务端全链路处理超时时间为10秒，请做相应设置
		 * 如果您设置的ReadTimeout小于服务端处理的时间，程序中会获得一个read timeout异常
		 */
		imageSyncScanRequest.setSysConnectTimeout(aliyunProperties.getScan().getImage().getConnectTimeout());
		imageSyncScanRequest.setSysReadTimeout(aliyunProperties.getScan().getImage().getReadTimeout());

		log.debug("[Braineex] |- Bean [Image Sync Scan Request] Auto Configure.");
		return imageSyncScanRequest;
	}


	/**
	 * 图片异步检测请求
	 *
	 * @return
	 */
	@Bean
	public ImageAsyncScanRequest imageAsyncScanRequest() {
		ImageAsyncScanRequest imageAsyncScanRequest = new ImageAsyncScanRequest();
		// 指定api返回格式
		imageAsyncScanRequest.setSysAcceptFormat(FormatType.JSON);
		// 指定请求方法
		imageAsyncScanRequest.setSysMethod(MethodType.POST);
		imageAsyncScanRequest.setSysEncoding(StandardCharsets.UTF_8.name());
		//支持http和https
		imageAsyncScanRequest.setSysProtocol(ProtocolType.HTTP);
		/**
		 * 请设置超时时间, 服务端全链路处理超时时间为10秒，请做相应设置
		 * 如果您设置的ReadTimeout小于服务端处理的时间，程序中会获得一个read timeout异常
		 */
		imageAsyncScanRequest.setSysConnectTimeout(aliyunProperties.getScan().getImage().getConnectTimeout());
		imageAsyncScanRequest.setSysReadTimeout(aliyunProperties.getScan().getImage().getReadTimeout());

		log.debug("[Braineex] |- Bean [Image Async Scan Request] Auto Configure.");
		return imageAsyncScanRequest;
	}


	/**
	 * 图片异步请求结果查询
	 *
	 * @return
	 */
	@Bean
	public ImageAsyncScanResultsRequest imageAsyncSacnResultsRequest() {
		ImageAsyncScanResultsRequest imageAsyncScanResultsRequest = new ImageAsyncScanResultsRequest();
		// 指定api返回格式
		imageAsyncScanResultsRequest.setSysAcceptFormat(FormatType.JSON);
		// 指定请求方法
		imageAsyncScanResultsRequest.setSysMethod(MethodType.POST);
		imageAsyncScanResultsRequest.setSysEncoding(StandardCharsets.UTF_8.name());
		//支持http和https
		imageAsyncScanResultsRequest.setSysProtocol(ProtocolType.HTTP);

		/**
		 * 请设置超时时间, 服务端全链路处理超时时间为10秒，请做相应设置
		 * 如果您设置的ReadTimeout小于服务端处理的时间，程序中会获得一个read timeout异常
		 */
		imageAsyncScanResultsRequest.setSysConnectTimeout(aliyunProperties.getScan().getImage().getConnectTimeout());
		imageAsyncScanResultsRequest.setSysReadTimeout(aliyunProperties.getScan().getImage().getReadTimeout());

		log.debug("[Braineex] |- Bean [Image Async Scan Results Request] Auto Configure.");
		return imageAsyncScanResultsRequest;
	}


	/**
	 * 视频同步请求
	 *
	 * @return
	 */
	@Bean
	public VideoSyncScanRequest videoSyncScanRequest() {
		VideoSyncScanRequest videoSyncScanRequest = new VideoSyncScanRequest();
		//指定api返回格式
		videoSyncScanRequest.setSysAcceptFormat(FormatType.JSON);
		//指定请求方法
		videoSyncScanRequest.setSysMethod(MethodType.POST);
		/**
		 * 请务必设置超时时间
		 */
		videoSyncScanRequest.setSysConnectTimeout(aliyunProperties.getScan().getVideo().getConnectTimeout());
		videoSyncScanRequest.setSysReadTimeout(aliyunProperties.getScan().getVideo().getReadTimeout());

		log.debug("[Braineex] |- Bean [Video Sync Scan Request] Auto Configure.");
		return videoSyncScanRequest;
	}


	/**
	 * 视频异步请求（推荐使用异步）
	 *
	 * @return
	 */
	@Bean
	public VideoAsyncScanRequest videoAsyncScanRequest() {
		VideoAsyncScanRequest videoAsyncScanRequest = new VideoAsyncScanRequest();
		//指定api返回格式
		videoAsyncScanRequest.setSysAcceptFormat(FormatType.JSON);
		//指定请求方法
		videoAsyncScanRequest.setSysMethod(MethodType.POST);
		/**
		 * 请务必设置超时时间
		 */
		videoAsyncScanRequest.setSysConnectTimeout(aliyunProperties.getScan().getVideo().getConnectTimeout());
		videoAsyncScanRequest.setSysReadTimeout(aliyunProperties.getScan().getVideo().getReadTimeout());

		log.debug("[Braineex] |- Bean [Video Async Scan Request] Auto Configure.");
		return videoAsyncScanRequest;
	}

	/**
	 * 视频异步请求结果查询请求
	 *
	 * @return
	 */
	@Bean
	public VideoAsyncScanResultsRequest videoAsyncResultRequest() {
		VideoAsyncScanResultsRequest videoAsyncResultRequest = new VideoAsyncScanResultsRequest();
		// 指定api返回格式
		videoAsyncResultRequest.setSysAcceptFormat(FormatType.JSON);
		/**
		 * 请务必设置超时时间
		 */
		videoAsyncResultRequest.setSysConnectTimeout(aliyunProperties.getScan().getVideo().getConnectTimeout());
		videoAsyncResultRequest.setSysReadTimeout(aliyunProperties.getScan().getVideo().getReadTimeout());

		log.debug("[Braineex] |- Bean [Video Async Scan Results Request] Auto Configure.");
		return videoAsyncResultRequest;
	}

	/**
	 * 语音检测请求(语音识别仅支持异步)
	 *
	 * @return
	 */
	@Bean
	public VoiceSyncScanRequest voiceSyncScanRequest() {
		VoiceSyncScanRequest voiceSyncScanRequest = new VoiceSyncScanRequest();
		//指定api返回格式
		voiceSyncScanRequest.setSysAcceptFormat(FormatType.JSON);
		//指定请求方法
		voiceSyncScanRequest.setSysMethod(MethodType.POST);
		voiceSyncScanRequest.setSysRegionId(aliyunProperties.getRegionId());
		voiceSyncScanRequest.setSysConnectTimeout(aliyunProperties.getScan().getVoice().getConnectTimeout());
		voiceSyncScanRequest.setSysReadTimeout(aliyunProperties.getScan().getVoice().getReadTimeout());

		log.debug("[Braineex] |- Bean [Voice Sync Scan Request] Auto Configure.");
		return voiceSyncScanRequest;
	}

	/**
	 * 语音检测请求(语音识别仅支持异步)
	 *
	 * @return
	 */
	@Bean
	public VoiceAsyncScanRequest voiceAsyncScanRequest() {
		VoiceAsyncScanRequest voiceAsyncScanRequest = new VoiceAsyncScanRequest();
		//指定api返回格式
		voiceAsyncScanRequest.setSysAcceptFormat(FormatType.JSON);
		//指定请求方法
		voiceAsyncScanRequest.setSysMethod(MethodType.POST);
		voiceAsyncScanRequest.setSysRegionId(aliyunProperties.getRegionId());
		voiceAsyncScanRequest.setSysConnectTimeout(aliyunProperties.getScan().getVoice().getConnectTimeout());
		voiceAsyncScanRequest.setSysReadTimeout(aliyunProperties.getScan().getVoice().getReadTimeout());

		log.debug("[Braineex] |- Bean [Voice Async Scan Request] Auto Configure.");
		return voiceAsyncScanRequest;
	}


	/**
	 * 语音异步检测结果查询
	 *
	 * @return
	 */
	@Bean
	public VoiceAsyncScanResultsRequest voiceAsyncResultsRequest() {
		VoiceAsyncScanResultsRequest voiceAsyncScanResultsRequest = new VoiceAsyncScanResultsRequest();
		// 指定api返回格式
		voiceAsyncScanResultsRequest.setSysAcceptFormat(FormatType.JSON);
		// 指定请求方法
		voiceAsyncScanResultsRequest.setSysMethod(MethodType.POST);
		voiceAsyncScanResultsRequest.setSysEncoding(StandardCharsets.UTF_8.name());
		voiceAsyncScanResultsRequest.setSysRegionId(aliyunProperties.getRegionId());
		/**
		 * 请务必设置超时时间
		 */
		voiceAsyncScanResultsRequest.setSysConnectTimeout(aliyunProperties.getScan().getVoice().getConnectTimeout());
		voiceAsyncScanResultsRequest.setSysReadTimeout(aliyunProperties.getScan().getVoice().getReadTimeout());

		log.debug("[Braineex] |- Bean [Voice Async Scan Results Request] Auto Configure.");
		return voiceAsyncScanResultsRequest;
	}
}
