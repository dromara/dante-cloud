package cn.herodotus.eurynome.integration.rest.content.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 天眼查REST配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 10:32
 */
@Slf4j
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.rest.content.controller.tianyan"
})
public class TianyanRestConfiguration {

    @PostConstruct
    public void init() {
        log.debug("[Eurynome] |- Bean [Tianyan Rest] Auto Configure.");
    }
}
