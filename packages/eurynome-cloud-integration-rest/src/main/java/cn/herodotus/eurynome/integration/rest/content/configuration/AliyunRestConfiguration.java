package cn.herodotus.eurynome.integration.rest.content.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 阿里云REST配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 11:21
 */
@Slf4j
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.rest.content.controller.aliyun"
})
public class AliyunRestConfiguration {

    @PostConstruct
    public void init() {
        log.debug("[Eurynome] |- Bean [Baidu OCR Rest] Auto Configure.");
    }
}
