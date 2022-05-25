/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

-- ----------------------------
-- Table data for oauth2_application
-- ----------------------------
INSERT INTO `oauth2_application` (`application_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `abbreviation`, `access_token_format`, `access_token_validity`, `application_name`, `application_type`, `signing_algorithm`, `authorization_grant_types`, `client_authentication_methods`, `client_id`, `client_secret`, `client_secret_expires_at`, `homepage`, `signature_algorithm`, `jwk_set_url`, `logo`, `redirect_uris`, `refresh_token_validity`, `require_authorization_consent`, `require_proof_key`, `reuse_refresh_tokens`) VALUES ('422baa83-505c-436f-87b5-2a599e6d2d28', '2022-04-02 16:26:26', 0, '2022-04-02 16:26:26', NULL, 1, 0, 0, 'Eurynome Cloud UI（PKCE）', 0, 7200000000000, '业务中台管理系统（PKCE）', 0, NULL, 'refresh_token,password,client_credentials,authorization_code,social_credentials', 'client_secret_post,client_secret_basic', '67601992f3574c75809a3d79888bf16e', '3e62c1f9678a4dba81b31ad9f6ab8940', NULL, NULL, 0, NULL, NULL, 'http://192.168.101.10:8847/eurynome-cloud-upms/open/authorized', 864000000000000, 1, 1, 1);
INSERT INTO `oauth2_application` (`application_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `abbreviation`, `access_token_format`, `access_token_validity`, `application_name`, `application_type`, `signing_algorithm`, `authorization_grant_types`, `client_authentication_methods`, `client_id`, `client_secret`, `client_secret_expires_at`, `homepage`, `signature_algorithm`, `jwk_set_url`, `logo`, `redirect_uris`, `refresh_token_validity`, `require_authorization_consent`, `require_proof_key`, `reuse_refresh_tokens`) VALUES ('dfe3e7bc-22e9-443f-a874-947494e20174', '2022-03-18 18:51:00', 1, '2022-04-02 16:27:04', NULL, 1, 0, 0, 'Eurynome Cloud UI', 0, 7200000000000, '业务中台管理系统', 0, 0, 'refresh_token,password,client_credentials,authorization_code,social_credentials', 'client_secret_post,client_secret_basic', '14a9cf797931430896ad13a6b1855611', 'a05fe1fc50ed42a4990c6c6fc4bec398', '2023-12-31 00:00:00', NULL, 0, NULL, NULL, 'http://192.168.101.10:8847/eurynome-cloud-upms/open/authorized', 86400000000000, 1, 0, 1);

-- ----------------------------
-- Table data for oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client` (`id`, `authorization_grant_types`, `client_authentication_methods`, `client_id`, `client_id_issued_at`, `client_name`, `client_secret`, `client_secret_expires_at`, `client_settings`, `redirect_uris`, `scopes`, `token_settings`) VALUES ('422baa83-505c-436f-87b5-2a599e6d2d28', 'refresh_token,password,client_credentials,authorization_code,social_credentials', 'client_secret_post,client_secret_basic', '67601992f3574c75809a3d79888bf16e', '2022-04-02 16:26:27', '422baa83-505c-436f-87b5-2a599e6d2d28', '{bcrypt}$2a$10$G78qeNTKc3jIpMfrMvLaNe6uy3tVI3pUOkJxku3XIKV3GeVY.HYtG', NULL, '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":true,\"settings.client.require-authorization-consent\":true}', 'http://192.168.101.10:8847/eurynome-cloud-upms/open/authorized', 'read-user-by-page,openid', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",7200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",864000.000000000]}');
INSERT INTO `oauth2_registered_client` (`id`, `authorization_grant_types`, `client_authentication_methods`, `client_id`, `client_id_issued_at`, `client_name`, `client_secret`, `client_secret_expires_at`, `client_settings`, `redirect_uris`, `scopes`, `token_settings`) VALUES ('dfe3e7bc-22e9-443f-a874-947494e20174', 'refresh_token,password,client_credentials,authorization_code,social_credentials', 'client_secret_post,client_secret_basic', '14a9cf797931430896ad13a6b1855611', '2022-03-18 18:51:00', 'dfe3e7bc-22e9-443f-a874-947494e20174', '{bcrypt}$2a$10$yMB9ze2Ny2xMa/xHSFX4H.DQkYH5gbZNRXFaPdv.MIhBbWPntZ382', '2023-12-31 00:00:00', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-authorization-consent\":true,\"settings.client.token-endpoint-authentication-signing-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.client.require-proof-key\":false}', 'http://192.168.101.10:8847/eurynome-cloud-upms/open/authorized', 'read-user-by-page,openid', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",7200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",86400.000000000]}');

-- ----------------------------
-- Table data for oauth2_scope
-- ----------------------------
INSERT INTO `oauth2_scope` (`scope_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scope_code`, `scope_name`) VALUES ('bd344a2e-47d6-4b45-b77f-50ca8623f09f', '2022-03-24 11:58:20', 1, '2022-03-24 11:58:20', NULL, 1, 0, 0, 'openid', 'OIDC OpenId');
INSERT INTO `oauth2_scope` (`scope_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scope_code`, `scope_name`) VALUES ('bfc6105d9f0285e9550ef7aade04a2d3', '2022-04-01 22:07:23', 2, '2022-04-01 22:07:28', NULL, 1, 0, 0, 'read-user-by-page', '获取用户信息');

-- ----------------------------
-- Table data for oauth2_authority
-- ----------------------------
INSERT INTO `oauth2_authority` (`authority_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `authority_code`, `request_method`, `service_id`, `url`) VALUES ('79dd7faa3a18616ce32fa450ba32eedd', '2022-04-01 22:10:19', NULL, '2022-04-01 22:10:22', NULL, NULL, 0, 0, 'OP_835620758', 'GET', 'eurynome-cloud-upms', '/user');

-- ----------------------------
-- Table data for oauth2_application_scope
-- ----------------------------
INSERT INTO `oauth2_application_scope` (`application_id`, `scope_id`) VALUES ('422baa83-505c-436f-87b5-2a599e6d2d28', 'bd344a2e-47d6-4b45-b77f-50ca8623f09f');
INSERT INTO `oauth2_application_scope` (`application_id`, `scope_id`) VALUES ('422baa83-505c-436f-87b5-2a599e6d2d28', 'bfc6105d9f0285e9550ef7aade04a2d3');
INSERT INTO `oauth2_application_scope` (`application_id`, `scope_id`) VALUES ('dfe3e7bc-22e9-443f-a874-947494e20174', 'bd344a2e-47d6-4b45-b77f-50ca8623f09f');
INSERT INTO `oauth2_application_scope` (`application_id`, `scope_id`) VALUES ('dfe3e7bc-22e9-443f-a874-947494e20174', 'bfc6105d9f0285e9550ef7aade04a2d3');

-- ----------------------------
-- Table data for oauth2_scope_authority
-- ----------------------------
INSERT INTO `oauth2_scope_authority` (`scope_id`, `authority_id`) VALUES ('bfc6105d9f0285e9550ef7aade04a2d3', '79dd7faa3a18616ce32fa450ba32eedd');

-- ----------------------------
-- Table data for sys_user
-- ----------------------------
INSERT INTO `sys_user` (`user_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `avatar`, `email`, `nick_name`, `password`, `phone_number`, `user_name`) VALUES ('1', '2019-07-23 07:19:50', 1, '2019-07-23 07:19:52', '平台管理员', 1, NULL, 0, NULL, NULL, 'Hades', '{bcrypt}$2a$10$q4t3wbkoCVAtCMwVsVFjveLF7O6QScD2/vmqgGbdPuZRw0U1.blhq', NULL, 'system');

-- ----------------------------
-- Table data for sys_role
-- ----------------------------
INSERT INTO `sys_role` (`role_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `role_code`, `role_name`) VALUES ('1', '2019-07-23 07:22:27', 1, '2019-09-11 12:04:52', '中台管理员角色', 1, 0, 0, 'ROLE_ADMINISTRATOR', '平台管理员角色');

-- ----------------------------
-- Table data for sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES ('1', '1');

-- ----------------------------
-- Table data for sys_default_role
-- ----------------------------
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('1', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '机构人员', 1, 0, 0, 'INSTITUTION', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('10', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '企业微信网页', 1, 0, 0, 'WECHAT_ENTERPRISE_WEB', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('11', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '钉钉', 1, 0, 0, 'DINGTALK', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('12', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '钉钉账号', 1, 0, 0, 'DINGTALK_ACCOUNT', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('13', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '阿里云', 1, 0, 0, 'ALIYUN', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('14', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '淘宝', 1, 0, 0, 'TAOBAO', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('15', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '支付宝', 1, 0, 0, 'ALIPAY', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('16', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Teambition', 1, 0, 0, 'TEAMBITION', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('17', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '华为', 1, 0, 0, 'HUAWEI', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('18', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '飞书', 1, 0, 0, 'FEISHU', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('19', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '京东', 1, 0, 0, 'JD', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('2', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '手机验证码', 1, 0, 0, 'SMS', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('20', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '抖音', 1, 0, 0, 'DOUYIN', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('21', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '今日头条', 1, 0, 0, 'TOUTIAO', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('22', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '小米', 1, 0, 0, 'MI', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('23', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '人人', 1, 0, 0, 'RENREN', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('24', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '美团', 1, 0, 0, 'MEITUAN', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('25', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '饿了么', 1, 0, 0, 'ELEME', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('26', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '酷家乐', 1, 0, 0, 'KUJIALE', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('27', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '喜马拉雅', 1, 0, 0, 'XMLY', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('28', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '码云', 1, 0, 0, 'GITEE', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('29', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '开源中国', 1, 0, 0, 'OSCHINA', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('3', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微信小程序', 1, 0, 0, 'WXAPP', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('30', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'CSDN', 1, 0, 0, 'CSDN', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('31', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Github', 1, 0, 0, 'GITHUB', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('32', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Gitlab', 1, 0, 0, 'GITLAB', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('33', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Stackoverflow', 1, 0, 0, 'STACK_OVERFLOW', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('34', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Coding', 1, 0, 0, 'CODING', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('35', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '谷歌', 1, 0, 0, 'GOOGLE', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('36', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微软', 1, 0, 0, 'MICROSOFT', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('37', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '脸书', 1, 0, 0, 'FACEBOOK', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('38', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '领英', 1, 0, 0, 'LINKEDIN', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('39', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '推特', 1, 0, 0, 'TWITTER', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('4', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'QQ', 1, 0, 0, 'QQ', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('40', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '亚马逊', 1, 0, 0, 'AMAZON', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('41', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Slack', 1, 0, 0, 'SLACK', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('42', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Line', 1, 0, 0, 'LINE', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('43', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Okta', 1, 0, 0, 'OKTA', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('44', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Pinterest', 1, 0, 0, 'PINTEREST', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('5', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微博', 1, 0, 0, 'WEIBO', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('6', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '百度', 1, 0, 0, 'BAIDU', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('7', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微信开放平台', 1, 0, 0, 'WECHAT_OPEN', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('8', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微信公众号', 1, 0, 0, 'WECHAT_MP', '1');
INSERT INTO `sys_default_role` (`default_id`, `create_time`, `ranking`, `update_time`, `description`, `is_reserved`, `reversion`, `status`, `scene`, `role_id`) VALUES ('9', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '企业微信二维码', 1, 0, 0, 'WECHAT_ENTERPRISE', '1');

INSERT INTO `sys_role_authority` (`role_id`, `authority_id`)
SELECT '1' role_id,
       sa.authority_id
FROM `sys_authority` sa
WHERE NOT EXISTS(SELECT sa.authority_id FROM `sys_role_authority` sra WHERE sra.authority_id = sa.authority_id)