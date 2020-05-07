-- ----------------------------
-- Table data for dev_supplier
-- ----------------------------
INSERT INTO "dev_supplier"("supplier_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "parent_id", "supplier_code", "supplier_name", "supplier_type") VALUES ('067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e', '2020-05-05 17:13:00.067', 0, '2020-05-05 17:13:00.067', '', 't', 0, 1, NULL, 'HERODOTUS', '业务中台架构及开发团队', 'CORE');

-- ----------------------------
-- Table data for oauth_microservices
-- ----------------------------
INSERT INTO "oauth_microservices"("service_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "app_code", "app_name", "app_secret", "app_type", "supplier_id") VALUES ('f8e3f156-2cf4-449c-926d-d1377fe82c86', '2020-05-05 17:14:49.183', 0, '2020-05-05 17:14:49.183', '', 't', 0, 1, 'eurynome-cloud-platform-uaa', '业务中台认证中心服务', '067e9d1c-12ed-4400-92ce-97281ddd71ff', 'SERVICE', '067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e');
INSERT INTO "oauth_microservices"("service_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "app_code", "app_name", "app_secret", "app_type", "supplier_id") VALUES ('004b87d8-0a18-4e62-a35c-f2f123713349', '2020-05-06 11:24:06.377', 0, '2020-05-06 11:24:06.377', '', 't', 0, 1, 'eurynome-cloud-upms-rest', '业务中台用户中心服务', '2bda7d3a-dba1-45a4-b08e-cbd731a0418e', 'SERVICE', '067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e');

