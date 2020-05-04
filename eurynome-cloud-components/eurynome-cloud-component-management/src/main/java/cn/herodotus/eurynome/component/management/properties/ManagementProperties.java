package cn.herodotus.eurynome.component.management.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * <p> Description : 平台管理相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 16:01
 */
@Slf4j
@ConfigurationProperties(prefix = "herodotus.platform.management")
public class ManagementProperties {

    private Initialization initialization = new Initialization();

    public Initialization getInitialization() {
        return initialization;
    }

    public void setInitialization(Initialization initialization) {
        this.initialization = initialization;
    }

    public static class Initialization implements Serializable {

        /**
         * 初始化Nacos的配置，默认为false
         */
        private boolean initializeNacosConfig = false;

        public boolean isInitializeNacosConfig() {
            return initializeNacosConfig;
        }

        public void setInitializeNacosConfig(boolean initializeNacosConfig) {
            this.initializeNacosConfig = initializeNacosConfig;
        }
    }
}
