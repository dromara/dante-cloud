package cn.herodotus.eurynome.upms.ability.processor;

import cn.herodotus.eurynome.security.definition.RequestMapping;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 对包含RequestMapping信息的消息，进行存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/26 10:10
 */
@Slf4j
@Service
public class SecurityMetadataAsyncStorage extends AbstractSecurityMetadataStorage {

    private final SysAuthorityService sysAuthorityService;

    @Autowired
    public SecurityMetadataAsyncStorage(SysAuthorityService sysAuthorityService) {
        super(sysAuthorityService);
        this.sysAuthorityService = sysAuthorityService;
    }

    @Async
    public void store(String message) {
        log.debug("[Eurynome] |- Received Service Resources Message: [{}]", message);

        List<RequestMapping> requestMappings = JSON.parseArray(message, RequestMapping.class);
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            store(requestMappings);
        }
    }
}
