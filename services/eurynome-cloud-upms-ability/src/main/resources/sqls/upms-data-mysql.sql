/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the `License`);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an `AS IS` BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-upms-ability
 * File Name: upms-data-postgresql.sql
 * Author: gengwei.zheng
 * Date: 2021/05/13 15:48:13
 */

-- ----------------------------
-- Table data for oauth_applications
-- ----------------------------
INSERT INTO `oauth_applications`(`app_key`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `app_icon`, `app_name`, `app_name_en`, `app_secret`, `app_type`, `app_tech`, `website`, `app_code`) VALUES ('010e659a-4005-4610-98f6-00b822f4758e', '2020-04-21 19:00:19.197', 0, '2020-05-05 18:02:48.367', '', b'1', 0, 0, NULL, '业务中台管理端', 'Eurynome', '04165a07-cffd-45cf-a20a-1c2a69f65fb1', 0, 3, 'http://localhost:8080', '');

-- ----------------------------
-- Table data for oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('004b87d8-0a18-4e62-a35c-f2f123713349', '$2a$10$LZW/i7hHD3K6bNMLNMAgsu14avbR7cC0Ibw3cHj6P/NNjUqZSk3ou', NULL, NULL, 'client_credentials,password', NULL, NULL, NULL, NULL, '{\"appCode\":\"eurynome-cloud-upms-ability\",\"appName\":\"业务中台用户中心服务\",\"appSecret\":\"2bda7d3a-dba1-45a4-b08e-cbd731a0418e\",\"applicationType\":\"SERVICE\",\"createTime\":1588735446377,\"description\":\"\",\"id\":\"004b87d8-0a18-4e62-a35c-f2f123713349\",\"ranking\":0,\"reserved\":true,\"reversion\":0,\"serviceId\":\"004b87d8-0a18-4e62-a35c-f2f123713349\",\"status\":\"ENABLE\",\"supplier\":{\"createTime\":1588669980067,\"description\":\"\",\"id\":\"067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e\",\"linkedProperty\":\"HERODOTUS\",\"ranking\":0,\"reserved\":true,\"reversion\":0,\"status\":\"ENABLE\",\"supplierCode\":\"HERODOTUS\",\"supplierId\":\"067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e\",\"supplierName\":\"业务中台架构及开发团队\",\"supplierType\":\"CORE\",\"updateTime\":1588669980067},\"updateTime\":1588735446377}', NULL);
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('010e659a-4005-4610-98f6-00b822f4758e', '$2a$10$XxKxGWDlqgdKhcvIBvhx9u7hVLpVKfVlsG.d2AoLIGFYugz0dZf2i', NULL, 'all', 'password,authorization_code,client_credentials,refresh_token,implicit', 'http://localhost:9999/passport/login,http://www.baidu.com', NULL, 43200, 2592000, '{\"appCode\":\"\",\"appKey\":\"010e659a-4005-4610-98f6-00b822f4758e\",\"appName\":\"业务中台管理端\",\"appNameEn\":\"Eurynome\",\"appSecret\":\"04165a07-cffd-45cf-a20a-1c2a69f65fb1\",\"applicationType\":\"WEB\",\"createTime\":1587466819000,\"description\":\"\",\"id\":\"010e659a-4005-4610-98f6-00b822f4758e\",\"ranking\":0,\"reserved\":true,\"reversion\":0,\"scopes\":[{\"authorities\":[],\"createTime\":1586851572000,\"description\":\"中台全部服务权限\",\"id\":\"c153737a-5234-11ea-ae28-14cf92c9b916\",\"linkedProperty\":\"all\",\"ranking\":1,\"reserved\":true,\"reversion\":0,\"scopeCode\":\"all\",\"scopeId\":\"c153737a-5234-11ea-ae28-14cf92c9b916\",\"scopeName\":\"全部权限\",\"status\":\"ENABLE\",\"updateTime\":1587081166481}],\"status\":\"ENABLE\",\"technologyType\":\"NODE\",\"updateTime\":1588672968367,\"website\":\"http://localhost:8080\"}', NULL);
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('a7e91f49-ab28-4f55-81c4-973449cfe1eb', '$2a$10$fi5ecIcM3hy9RQwE0x78oeyNecPFiUgi0PnhESeENjX3G4CBvYOLO', '', 'all', 'authorization_code,client_credentials', 'http://localhost:9999/passport/login', '', 43200, 2592000, '{\"status\":\"ENABLE\",\"website\":\"http://localhsot:9999\",\"appName\":\"运营后台\"}', '');
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('e2a746fc-cb1a-49c3-9436-67004117b039', '$2a$10$KE23iMm9qHciIzIog8FHQOMjtJ4LVVtGia4NpaS6Mhk3qQlqReU7.', '', '', 'client_credentials,password', '', '', NULL, NULL, '{\"reversion\":0,\"applicationType\":\"SERVICE\",\"appName\":\"业务中台流程中心服务\",\"description\":\"\",\"updateTime\":1592557079565,\"appCode\":\"eurynome-cloud-bpmn-ability\",\"createTime\":1592557079565,\"reserved\":true,\"supplier\":{\"reversion\":0,\"supplierName\":\"业务中台架构及开发团队\",\"supplierId\":\"067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e\",\"description\":\"\",\"updateTime\":1588669980067,\"supplierCode\":\"HERODOTUS\",\"createTime\":1588669980067,\"reserved\":true,\"linkedProperty\":\"HERODOTUS\",\"ranking\":0,\"id\":\"067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e\",\"supplierType\":\"CORE\",\"status\":\"ENABLE\"},\"appSecret\":\"25c9a32b-45a9-447a-a7e3-3a28d6a6834e\",\"ranking\":2,\"id\":\"e2a746fc-cb1a-49c3-9436-67004117b039\",\"scopes\":[],\"serviceId\":\"e2a746fc-cb1a-49c3-9436-67004117b039\",\"status\":\"ENABLE\"}', NULL);
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('f8e3f156-2cf4-449c-926d-d1377fe82c86', '$2a$10$S.cbWjrli7DJ34UQayozBOj07th.o4xAoW6pLEoAMM8Hd3YGh4y82', NULL, 'read_client_detail,read_user_detail', 'client_credentials,password', NULL, NULL, NULL, NULL, '{\"appCode\":\"eurynome-cloud-platform-uaa\",\"appName\":\"业务中台认证中心服务\",\"appSecret\":\"067e9d1c-12ed-4400-92ce-97281ddd71ff\",\"applicationType\":\"SERVICE\",\"createTime\":1588698889183,\"description\":\"\",\"id\":\"f8e3f156-2cf4-449c-926d-d1377fe82c86\",\"ranking\":0,\"reserved\":true,\"reversion\":0,\"scopes\":[{\"authorities\":[{\"authorityCode\":\"OP_0C40DF5087487901D7289D070BE6A5F3\",\"authorityId\":\"0C40DF5087487901D7289D070BE6A5F3\",\"authorityName\":\"获取终端信息\",\"authorityType\":\"API\",\"className\":\"cn.herodotus.eurynome.upms.rest.controller.oauth.OauthClientDetailsController\",\"createTime\":1589024686153,\"id\":\"0C40DF5087487901D7289D070BE6A5F3\",\"menuClass\":\"fa fa-caret-right\",\"methodName\":\"findByClientId\",\"parentId\":\"eurynome-cloud-upms-ability\",\"ranking\":0,\"requestMethod\":\"GET\",\"reserved\":false,\"reversion\":0,\"serviceId\":\"eurynome-cloud-upms-ability\",\"status\":\"ENABLE\",\"updateTime\":1591447593097,\"url\":\"/oauth/client_details\"}],\"createTime\":1591461064569,\"description\":\"\",\"id\":\"5589940e-ec75-4de4-9995-11a2090bf617\",\"linkedProperty\":\"read_client_detail\",\"ranking\":1,\"reserved\":true,\"reversion\":0,\"scopeCode\":\"read_client_detail\",\"scopeId\":\"5589940e-ec75-4de4-9995-11a2090bf617\",\"scopeName\":\"获取客户端信息\",\"status\":\"ENABLE\",\"updateTime\":1591513318119},{\"authorities\":[{\"authorityCode\":\"OP_B9F9A62E9E00E743A832436519AC3E97\",\"authorityId\":\"B9F9A62E9E00E743A832436519AC3E97\",\"authorityName\":\"根据用户ID查询用户\",\"authorityType\":\"API\",\"className\":\"cn.herodotus.eurynome.upms.rest.controller.system.SysUserController\",\"createTime\":1589024686189,\"id\":\"B9F9A62E9E00E743A832436519AC3E97\",\"menuClass\":\"fa fa-caret-right\",\"methodName\":\"findById\",\"parentId\":\"eurynome-cloud-upms-ability\",\"ranking\":0,\"requestMethod\":\"POST\",\"reserved\":false,\"reversion\":0,\"serviceId\":\"eurynome-cloud-upms-ability\",\"status\":\"ENABLE\",\"updateTime\":1591447593112,\"url\":\"/user/findById\"},{\"authorityCode\":\"OP_AF75A8DE6B2F439FC486A9CC142A5F5D\",\"authorityId\":\"AF75A8DE6B2F439FC486A9CC142A5F5D\",\"authorityName\":\"根据用户名查询用户\",\"authorityType\":\"API\",\"className\":\"cn.herodotus.eurynome.upms.rest.controller.system.SysUserController\",\"createTime\":1589024686192,\"id\":\"AF75A8DE6B2F439FC486A9CC142A5F5D\",\"menuClass\":\"fa fa-caret-right\",\"methodName\":\"findByUsername\",\"parentId\":\"eurynome-cloud-upms-ability\",\"ranking\":0,\"requestMethod\":\"POST\",\"reserved\":false,\"reversion\":0,\"serviceId\":\"eurynome-cloud-upms-ability\",\"status\":\"ENABLE\",\"updateTime\":1591447593114,\"url\":\"/user/findByUsername\"}],\"createTime\":1591461326815,\"description\":\"\",\"id\":\"911b0419-78d6-4775-824b-c0b4f84d6ddd\",\"linkedProperty\":\"read_user_detail\",\"ranking\":2,\"reserved\":true,\"reversion\":0,\"scopeCode\":\"read_user_detail\",\"scopeId\":\"911b0419-78d6-4775-824b-c0b4f84d6ddd\",\"scopeName\":\"获取用户信息\",\"status\":\"ENABLE\",\"updateTime\":1591513359089}],\"serviceId\":\"f8e3f156-2cf4-449c-926d-d1377fe82c86\",\"status\":\"ENABLE\",\"supplier\":{\"createTime\":1588698780067,\"description\":\"\",\"id\":\"067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e\",\"linkedProperty\":\"HERODOTUS\",\"ranking\":0,\"reserved\":true,\"reversion\":0,\"status\":\"ENABLE\",\"supplierCode\":\"HERODOTUS\",\"supplierId\":\"067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e\",\"supplierName\":\"业务中台架构及开发团队\",\"supplierType\":\"CORE\",\"updateTime\":1588698780067},\"updateTime\":1588698889183}', NULL);

-- ----------------------------
-- Table data for oauth_scopes
-- ----------------------------
INSERT INTO `oauth_scopes`(`scope_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scope_code`, `scope_name`) VALUES ('c153737a-5234-11ea-ae28-14cf92c9b916', '2020-04-14 16:06:12', 1, '2020-04-17 07:52:46.481', '中台全部服务权限', b'1', 0, 0, 'all', '全部权限');

-- ----------------------------
-- Table data for sys_user
-- ----------------------------
INSERT INTO `sys_user`(`user_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `status`, `nick_name`, `password`, `user_name`, `reversion`) VALUES ('1', '2019-07-23 07:19:50', 1, '2019-07-23 07:19:52', '平台管理员', b'1', 0, 'Hades', '$2a$10$fi5ecIcM3hy9RQwE0x78oeyNecPFiUgi0PnhESeENjX3G4CBvYOLO', 'system', NULL);

-- ----------------------------
-- Table data for sys_role
-- ----------------------------
INSERT INTO `sys_role`(`role_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `status`, `role_code`, `role_name`, `reversion`) VALUES ('1', '2019-07-23 07:22:27', 1, '2019-09-11 12:04:52', '中台管理员角色', b'1', 0, 'ROLE_ADMINISTRATOR', '平台管理员角色', NULL);

-- ----------------------------
-- Table data for sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`(`user_id`, `role_id`) VALUES ('1', '1');

-- ----------------------------
-- 如果要重新生成权限数据，可以用下面脚本生成角色1 与权限的关系
-- ----------------------------
INSERT INTO sys_role_authority (role_id, authority_id)
SELECT '1' role_id,
       sa.authority_id
FROM "sys_authority" sa





