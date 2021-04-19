package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.SyncRequest;
import io.swagger.annotations.ApiModel;

/**
 * <p>Description: 阿里图片同步检测请求实体 </p>
 *
 * 了减少参数使用出错，所以根据检测内容类型、同步或异步进行命名
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 12:46
 */
@ApiModel(value = "阿里云图片审核同步请求参数实体")
public class ImageSyncRequest extends SyncRequest<ImageTask> {

}
