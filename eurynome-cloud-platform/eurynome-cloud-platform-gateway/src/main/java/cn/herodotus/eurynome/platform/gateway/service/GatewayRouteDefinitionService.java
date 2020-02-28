package cn.herodotus.eurynome.platform.gateway.service;

import cn.herodotus.eurynome.platform.gateway.entity.GatewayRouteDefinition;
import cn.herodotus.eurynome.platform.gateway.repository.GatewayRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GatewayRouteDefinitionService {

    private final GatewayRouteRepository gatewayRouteRepository;

    @Autowired
    public GatewayRouteDefinitionService(GatewayRouteRepository gatewayRouteRepository) {
        this.gatewayRouteRepository = gatewayRouteRepository;
    }

    public GatewayRouteDefinition saveOrUpdate(GatewayRouteDefinition gatewayRouteDefinition) {
        return gatewayRouteRepository.save(gatewayRouteDefinition);
    }

    public Page<GatewayRouteDefinition> findByPage(Integer pageNumber, Integer pageSize) {
        return gatewayRouteRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id"));
    }

    public void delete(String id) {
        gatewayRouteRepository.deleteById(id);
    }
}
