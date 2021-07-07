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
 * Module Name: eurynome-cloud-data
 * File Name: BaseViewEntity.java
 * Author: gengwei.zheng
 * Date: 2021/07/07 16:36:07
 */

package cn.herodotus.eurynome.data.base.entity;

import javax.persistence.MappedSuperclass;

/**
 * <p>Description: 视图类型实体基础类 </p>
 * <p>
 * 视图只能支持读取，而不支持新增、修改、删除等操作。因此，为了产生比必要的隐患，规避@EntityListeners(AuditingEntityListener.class)注解可能会出现的修改操作。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 16:36
 */
@MappedSuperclass
public abstract class BaseViewEntity extends CommonEntity {
}
