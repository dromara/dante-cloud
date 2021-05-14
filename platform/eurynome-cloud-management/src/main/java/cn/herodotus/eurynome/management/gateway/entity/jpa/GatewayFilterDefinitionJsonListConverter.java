/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-management
 * File Name: GatewayFilterDefinitionJsonListConverter.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.management.gateway.entity.jpa;

import cn.herodotus.eurynome.data.jpa.BaseJpaListJsonConverter;
import cn.herodotus.eurynome.management.gateway.entity.GatewayFilterDefinition;

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
