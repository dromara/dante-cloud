/*
 * Copyright (c) 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-management
 * File Name: GatewayPredicateDefinitionJsonListConverter.java
 * Author: gengwei.zheng
 * Date: 2020/6/9 下午2:18
 * LastModified: 2020/5/23 上午9:32
 */

package cn.herodotus.eurynome.management.gateway.entity.jpa;

import cn.herodotus.eurynome.data.jpa.BaseJpaListJsonConverter;
import cn.herodotus.eurynome.management.gateway.entity.GatewayPredicateDefinition;

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
