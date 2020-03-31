package cn.herodotus.eurynome.platform.gateway.entity.jpa;

import cn.herodotus.eurynome.component.data.jpa.BaseJpaListJsonConverter;
import cn.herodotus.eurynome.platform.gateway.entity.GatewayFilterDefinition;

import javax.persistence.Converter;

/**
 * <p> Description : GatewayFilterDefinitionJsonListConverter </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/31 11:26
 */
@Converter(autoApply = true)
public class GatewayFilterDefinitionJsonListConverter extends BaseJpaListJsonConverter<GatewayFilterDefinition> {
}
