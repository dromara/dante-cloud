
-- ----------------------------
-- Table data for oauth2_application
-- ----------------------------
INSERT INTO "oauth2_application" ("application_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "abbreviation", "access_token_validity", "application_name", "application_type", "authorization_grant_types", "client_authentication_methods", "client_id", "client_secret", "homepage", "jwk_set_url", "logo", "redirect_uris", "refresh_token_validity", "require_authorization_consent", "require_proof_key", "reuse_refresh_tokens", "signature_algorithm", "client_secret_expires_at") VALUES ('dfe3e7bc-22e9-443f-a874-947494e20174', '2022-03-18 18:51:00.191', 1, '2022-03-19 17:54:22.318', NULL, 'f', 0, 0, 'Eurynome Cloud UI', 7200000000000, '业务中台管理系统', 0, 'refresh_token,password,authorization_code,social_credentials', 'client_secret_basic', '14a9cf797931430896ad13a6b1855611', 'a05fe1fc50ed42a4990c6c6fc4bec398', NULL, NULL, NULL, 'http://192.168.101.10:8847/eurynome-cloud-upms/open/authorized', 86400000000000, 't', 'f', 't', 0, '2023-12-31 00:00:00');


-- ----------------------------
-- Table data for oauth2_registered_client
-- ----------------------------
INSERT INTO "oauth2_registered_client" ("id", "authorization_grant_types", "client_authentication_methods", "client_id", "client_id_issued_at", "client_name", "client_secret", "client_secret_expires_at", "client_settings", "redirect_uris", "scopes", "token_settings") VALUES ('dfe3e7bc-22e9-443f-a874-947494e20174', 'refresh_token,password,authorization_code,social_credentials', 'client_secret_basic', '14a9cf797931430896ad13a6b1855611', '2022-03-18 18:51:00.373', 'dfe3e7bc-22e9-443f-a874-947494e20174', '{bcrypt}$2a$10$C9A.Mp4gqNW8.uO4gW4zjuKWPwFy6am5t3T0q2of1jo7q.KldzbLe', '2023-12-31 00:00:00', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', 'http://192.168.101.10:8847/eurynome-cloud-upms/open/authorized', 'all', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",7200.000000000],"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000]}');

-- ----------------------------
-- Table data for oauth2_scope
-- ----------------------------
INSERT INTO "oauth2_scope" ("scope_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scope_code", "scope_name") VALUES ('62197e92-2b22-4584-a32d-852f684c6d2c', '2022-03-19 11:31:03.302', 1, '2022-03-19 11:31:03.302', NULL, 'f', 0, 0, 'all', '全部权限');

-- ----------------------------
-- Table data for sys_user
-- ----------------------------
INSERT INTO "sys_user" ("user_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "avatar", "email", "nick_name", "password", "phone_number", "user_name") VALUES ('1', '2019-07-23 07:19:50', 1, '2019-07-23 07:19:52', '平台管理员', 't', NULL, 0, NULL, NULL, 'Hades', '{bcrypt}$2a$10$q4t3wbkoCVAtCMwVsVFjveLF7O6QScD2/vmqgGbdPuZRw0U1.blhq', NULL, 'system');

-- ----------------------------
-- Table data for sys_role
-- ----------------------------
INSERT INTO "sys_role"("role_id", "create_time", "ranking", "update_time", "description", "is_reserved", "status", "role_code", "role_name", "reversion") VALUES ('1', '2019-07-23 07:22:27', 1, '2019-09-11 12:04:52', '中台管理员角色', 't', 0, 'ROLE_ADMINISTRATOR', '平台管理员角色', 0);

-- ----------------------------
-- Table data for sys_user_role
-- ----------------------------
INSERT INTO "sys_user_role"("user_id", "role_id") VALUES ('1', '1');

-- ----------------------------
-- Table data for sys_default_role
-- ----------------------------
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('1', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '机构人员', 't', 0, 0, 'INSTITUTION', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('2', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '手机验证码', 't', 0, 0, 'SMS', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('3', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微信小程序', 't', 0, 0, 'WXAPP', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('4', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'QQ', 't', 0, 0, 'QQ', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('5', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微博', 't', 0, 0, 'WEIBO', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('6', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '百度', 't', 0, 0, 'BAIDU', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('7', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微信开放平台', 't', 0, 0, 'WECHAT_OPEN', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('8', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微信公众号', 't', 0, 0, 'WECHAT_MP', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('9', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '企业微信二维码', 't', 0, 0, 'WECHAT_ENTERPRISE', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('10', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '企业微信网页', 't', 0, 0, 'WECHAT_ENTERPRISE_WEB', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('11', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '钉钉', 't', 0, 0, 'DINGTALK', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('12', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '钉钉账号', 't', 0, 0, 'DINGTALK_ACCOUNT', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('13', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '阿里云', 't', 0, 0, 'ALIYUN', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('14', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '淘宝', 't', 0, 0, 'TAOBAO', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('15', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '支付宝', 't', 0, 0, 'ALIPAY', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('16', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Teambition', 't', 0, 0, 'TEAMBITION', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('17', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '华为', 't', 0, 0, 'HUAWEI', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('18', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '飞书', 't', 0, 0, 'FEISHU', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('19', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '京东', 't', 0, 0, 'JD', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('20', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '抖音', 't', 0, 0, 'DOUYIN', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('21', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '今日头条', 't', 0, 0, 'TOUTIAO', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('22', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '小米', 't', 0, 0, 'MI', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('23', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '人人', 't', 0, 0, 'RENREN', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('24', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '美团', 't', 0, 0, 'MEITUAN', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('25', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '饿了么', 't', 0, 0, 'ELEME', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('26', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '酷家乐', 't', 0, 0, 'KUJIALE', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('27', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '喜马拉雅', 't', 0, 0, 'XMLY', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('28', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '码云', 't', 0, 0, 'GITEE', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('29', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '开源中国', 't', 0, 0, 'OSCHINA', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('30', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'CSDN', 't', 0, 0, 'CSDN', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('31', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Github', 't', 0, 0, 'GITHUB', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('32', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Gitlab', 't', 0, 0, 'GITLAB', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('33', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Stackoverflow', 't', 0, 0, 'STACK_OVERFLOW', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('34', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Coding', 't', 0, 0, 'CODING', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('35', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '谷歌', 't', 0, 0, 'GOOGLE', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('36', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '微软', 't', 0, 0, 'MICROSOFT', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('37', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '脸书', 't', 0, 0, 'FACEBOOK', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('38', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '领英', 't', 0, 0, 'LINKEDIN', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('39', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '推特', 't', 0, 0, 'TWITTER', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('40', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', '亚马逊', 't', 0, 0, 'AMAZON', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('41', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Slack', 't', 0, 0, 'SLACK', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('42', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Line', 't', 0, 0, 'LINE', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('43', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Okta', 't', 0, 0, 'OKTA', '1');
INSERT INTO "sys_default_role" ("default_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scene", "role_id") VALUES ('44', '2021-10-12 09:45:33', 1, '2021-10-12 09:45:38', 'Pinterest', 't', 0, 0, 'PINTEREST', '1');


INSERT INTO sys_role_authority (role_id, authority_id)
SELECT '1' role_id,
       sa.authority_id
FROM "sys_authority" sa
WHERE NOT EXISTS(SELECT sa.authority_id FROM sys_role_authority sra WHERE sra.authority_id = sa.authority_id)