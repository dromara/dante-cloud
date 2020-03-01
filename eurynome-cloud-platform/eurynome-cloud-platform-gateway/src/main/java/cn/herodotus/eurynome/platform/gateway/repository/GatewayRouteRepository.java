package cn.herodotus.eurynome.platform.gateway.repository;

import cn.herodotus.eurynome.platform.gateway.entity.GatewayRouteDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRouteRepository extends JpaRepository<GatewayRouteDefinition, String> {

}
