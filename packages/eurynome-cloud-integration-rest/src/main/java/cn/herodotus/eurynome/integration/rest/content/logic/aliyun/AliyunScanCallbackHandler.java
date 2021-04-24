package cn.herodotus.eurynome.integration.rest.content.logic.aliyun;

import cn.herodotus.eurynome.integration.content.service.aliyun.oss.AliyunOssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 异步删除文件操作 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 14:29
 */
@Slf4j
@Component
public class AliyunScanCallbackHandler  {

    @Autowired
    private AliyunOssService aliyunOssService;

    @Async
    public void deleteIllegalMedia(String name) {
        if (StringUtils.isNotEmpty(name)) {
            log.debug("[Eurynome] |- Found Illegal Media [{}] Prepare to delete in aliyun server！", name);
            aliyunOssService.delete(name);
            log.debug("[Eurynome] |- Aliyun OSS file delete finished！");
        }
    }
}
