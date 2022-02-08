package cn.herodotus.eurynome.module.security.processor;

import cn.herodotus.engine.event.core.local.event.LocalRequestMappingGatherEvent;
import cn.herodotus.engine.event.core.remote.processor.DestinationResolver;
import cn.herodotus.engine.event.security.event.RemoteRequestMappingGatherEvent;
import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import cn.herodotus.engine.web.core.support.ServiceContext;
import cn.herodotus.eurynome.module.common.ServiceNameConstants;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * <p>Description: 自定义 RequestMappingScanManager </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/17 14:56
 */
@Component
public class HerodotusRequestMappingScanManager implements RequestMappingScanManager {

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableResourceServer.class;
    }

    @Override
    public boolean isDistributedArchitecture() {
        return ServiceContext.getInstance().isDistributedArchitecture();
    }

    @Override
    public String getDestinationServiceName() {
        return ServiceNameConstants.SERVICE_NAME_UPMS;
    }

    @Override
    public void postLocalStorage(List<RequestMapping> requestMappings) {

    }

    @Override
    public ApplicationEvent createLocalGatherEvent(List<RequestMapping> requestMappings) {
        return new LocalRequestMappingGatherEvent(requestMappings);
    }

    @Override
    public ApplicationEvent createRemoteGatherEvent(String source, String originService, String destinationService) {
        return new RemoteRequestMappingGatherEvent(source, originService, DestinationResolver.create(destinationService));
    }
}
