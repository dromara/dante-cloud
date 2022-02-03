package cn.herodotus.eurynome.module.security.processor;

import cn.herodotus.engine.event.core.local.LocalRequestMappingGatherEvent;
import cn.herodotus.engine.event.security.remote.RemoteRequestMappingGatherEvent;
import cn.herodotus.engine.message.core.processor.DestinationResolver;
import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import cn.herodotus.engine.web.core.support.ServiceContext;
import cn.herodotus.eurynome.assistant.constant.ServiceConstants;
import org.springframework.context.ApplicationEvent;
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
        return ServiceConstants.SERVICE_NAME_UPMS;
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
