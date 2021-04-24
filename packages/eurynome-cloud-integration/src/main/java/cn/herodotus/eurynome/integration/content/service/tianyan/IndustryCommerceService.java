package cn.herodotus.eurynome.integration.content.service.tianyan;

import cn.herodotus.eurynome.integration.content.domain.tianyan.BaseInfo;
import cn.herodotus.eurynome.integration.content.domain.tianyan.Response;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.OkHttps;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 天眼查企业工商信息服务 </p>
 * <p>
 * 目前命名为IndustryCommerceService，主要目的是与天眼查接口的分类对应
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 16:53
 */
@Service
public class IndustryCommerceService extends AbstractTianyanService {

    /**
     * 查询企业基本信息
	 * @param keyword 企业名称
	 * @return Response<BaseInfo>
	 */
	public Response<BaseInfo> getBaseInfo(String keyword) {
		return this.http().sync("/ic/baseinfo/2.0")
				.bodyType(OkHttps.JSON)
				.addHeader(this.getTokenHeader())
				.addUrlPara("keyword", keyword)
				.get()
				.getBody()
				.toBean(new TypeRef<Response<BaseInfo>>() {
				});
	}
}
