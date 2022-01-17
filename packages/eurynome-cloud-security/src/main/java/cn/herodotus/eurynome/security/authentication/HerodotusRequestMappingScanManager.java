package cn.herodotus.eurynome.security.authentication;

import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import cn.herodotus.engine.web.core.support.ServiceContext;
import cn.herodotus.eurynome.security.service.RequestMappingGatherService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * <p>Description: 自定义 RequestMappingScanManager </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/17 14:56
 */
public class HerodotusRequestMappingScanManager implements RequestMappingScanManager {

    private final RequestMappingGatherService requestMappingGatherService;

    public HerodotusRequestMappingScanManager(RequestMappingGatherService requestMappingGatherService) {
        this.requestMappingGatherService = requestMappingGatherService;
    }

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableResourceServer.class;
    }

    @Override
    public boolean isDistributedArchitecture() {
        return ServiceContext.getInstance().isDistributedArchitecture();
    }

    @Override
    public void postProcess(List<RequestMapping> requestMappings, ApplicationContext applicationContext, String serviceId, boolean isDistributedArchitecture) {
        requestMappingGatherService.postProcess(requestMappings, applicationContext, serviceId, isDistributedArchitecture);
    }
}
