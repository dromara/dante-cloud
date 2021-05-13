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

create extension "uuid-ossp";
-- ----------------------------
-- 初始化用户信息
-- ----------------------------
INSERT INTO sys_user (user_id, create_time, update_time, ranking, is_reserved, status, employee_id, user_name,
                      nick_name, password)
SELECT uuid_generate_v4()                                             AS user_id,
       se.create_time,
       se.update_time,
       se.ranking,
       TRUE                                                           AS is_reserved,
       se.status,
       se.employee_id,
       se.employee_name                                               AS user_name,
       se.mobile_phone_number                                         AS nick_name,
       '$2a$10$fi5ecIcM3hy9RQwE0x78oeyNecPFiUgi0PnhESeENjX3G4CBvYOLO' AS password
FROM "sys_employee" se