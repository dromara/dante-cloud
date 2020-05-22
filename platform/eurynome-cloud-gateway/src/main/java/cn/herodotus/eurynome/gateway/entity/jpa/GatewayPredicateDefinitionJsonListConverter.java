package cn.herodotus.eurynome.gateway.entity.jpa;

import cn.herodotus.eurynome.data.jpa.BaseJpaListJsonConverter;
import cn.herodotus.eurynome.gateway.entity.GatewayPredicateDefinition;

import javax.persistence.Converter;

/**
 * <p> Description : GatewayPredicateDefinitionJsonListConverter </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/31 11:19
 */
@Converter(autoApply = true)
public class GatewayPredicateDefinitionJsonListConverter extends BaseJpaListJsonConverter<GatewayPredicateDefinition> {

}
