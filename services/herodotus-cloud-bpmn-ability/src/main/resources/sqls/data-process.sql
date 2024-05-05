/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */


-- ----------------------------
-- 系统单位导入camunda tenant
-- ----------------------------
INSERT INTO act_id_tenant ( id_, name_, rev_ ) SELECT
so.organization_id AS id_,
so.organization_name AS name_,
so.reversion AS rev_ 
FROM
	"sys_organization" so;
-- ----------------------------
-- 系统部门导入camunda group
-- ----------------------------
INSERT INTO act_id_group ( id_, name_, rev_ ) SELECT
sd.department_id AS id_,
sd.department_name AS name_,
sd.reversion AS rev_ 
FROM
	"sys_department" sd;
-- ----------------------------
-- 系统用户导入camunda
-- ----------------------------
INSERT INTO act_id_user ( id_, first_, email_, rev_ ) SELECT
se.employee_id AS id_,
se.employee_name AS first_,
se.email AS email_,
se.reversion AS rev_ 
FROM
	"sys_employee" se;
-- ----------------------------
-- 系统用户部门关系导入camunda membership
-- ----------------------------
INSERT INTO act_id_membership ( group_id_, user_id_ ) SELECT
sed.department_id AS group_id_,
sed.employee_id AS user_id_ 
FROM
	"sys_employee_department" sed;
-- ----------------------------
-- 删除两个唯一键
-- ----------------------------
ALTER TABLE act_id_tenant_member DROP CONSTRAINT act_uniq_tenant_memb_user;
ALTER TABLE act_id_tenant_member DROP CONSTRAINT act_uniq_tenant_memb_group;
-- ----------------------------
-- 系统单位部门用户关系导入camunda tenant_member
-- ----------------------------
INSERT INTO act_id_tenant_member ( id_, tenant_id_, user_id_, group_id_ ) SELECT
sop.ownership_id AS id_,
sop.organization_id AS tenant_id_,
sop.employee_id AS user_id_,
sop.department_id AS group_id_ 
FROM
	"sys_ownership" sop;