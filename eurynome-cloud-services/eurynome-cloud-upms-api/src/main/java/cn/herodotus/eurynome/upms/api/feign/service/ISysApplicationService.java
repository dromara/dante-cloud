package cn.herodotus.eurynome.upms.api.feign.service;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.security.domain.HerodotusApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface ISysApplicationService {

    @GetMapping("/application/clientdetails")
    Result<HerodotusApplication> findByClientId(@RequestParam(value = "clientId") String clientId);
}
