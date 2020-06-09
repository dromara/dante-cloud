package cn.herodotus.eurynome.management.listener;

import cn.herodotus.eurynome.kernel.properties.ManagementProperties;
import cn.herodotus.eurynome.operation.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p> Description : InitializationListener </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 15:46
 */
@Component
public class InitializationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ConfigService configService;
    @Autowired
    private ManagementProperties managementProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (managementProperties.getConfigCenter().isInitialize()) {
            configService.initialize();
        }
    }
}
