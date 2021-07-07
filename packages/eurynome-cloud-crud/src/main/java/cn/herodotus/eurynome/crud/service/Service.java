/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-crud
 * File Name: Service.java
 * Author: gengwei.zheng
 * Date: 2021/07/07 16:45:07
 */

package cn.herodotus.eurynome.crud.service;

import cn.herodotus.eurynome.common.definition.entity.Entity;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;

import java.io.Serializable;

/**
 * <p>Description: 服务基础定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 16:45
 */
public interface Service <E extends Entity, ID extends Serializable>{

    BaseRepository<E, ID> getRepository();
}
