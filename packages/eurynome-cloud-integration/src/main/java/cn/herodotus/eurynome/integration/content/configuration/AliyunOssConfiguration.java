package cn.herodotus.eurynome.integration.content.configuration;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 阿里云对象存储配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 11:41
 */
@Slf4j
public class AliyunOssConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Eurynome] |- Bean [Aliyun Oss] Auto Configure.");
    }
}
