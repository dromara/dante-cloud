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
 * Module Name: eurynome-cloud-upms-ability
 * File Name: data-process.sql
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

-- ----------------------------
-- 如果要重新生成权限数据，可以用下面脚本生成角色1 与权限的关系
-- ----------------------------
INSERT INTO sys_role_authority (role_id, authority_id)
SELECT '1' role_id,
       sa.authority_id
FROM "sys_authority" sa