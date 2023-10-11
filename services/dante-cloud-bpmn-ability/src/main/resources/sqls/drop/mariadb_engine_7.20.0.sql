--
-- Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
-- under one or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information regarding copyright
-- ownership. Camunda licenses this file to you under the Apache License,
-- Version 2.0; you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

alter table ACT_RE_DECISION_DEF
    drop FOREIGN KEY ACT_FK_DEC_REQ;

drop index ACT_IDX_DEC_DEF_TENANT_ID on ACT_RE_DECISION_DEF;
drop index ACT_IDX_DEC_DEF_REQ_ID on ACT_RE_DECISION_DEF;
drop index ACT_IDX_DEC_REQ_DEF_TENANT_ID on ACT_RE_DECISION_REQ_DEF;

drop table if exists ACT_RE_DECISION_DEF;
drop table if exists ACT_RE_DECISION_REQ_DEF;
--
-- Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
-- under one or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information regarding copyright
-- ownership. Camunda licenses this file to you under the Apache License,
-- Version 2.0; you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

drop index ACT_IDX_CASE_EXEC_BUSKEY on ACT_RU_CASE_EXECUTION;
drop index ACT_IDX_CASE_DEF_TENANT_ID on ACT_RE_CASE_DEF;
drop index ACT_IDX_CASE_EXEC_TENANT_ID on ACT_RU_CASE_EXECUTION;

alter table ACT_RU_CASE_EXECUTION
    drop FOREIGN KEY ACT_FK_CASE_EXE_CASE_INST;

alter table ACT_RU_CASE_EXECUTION
    drop FOREIGN KEY ACT_FK_CASE_EXE_PARENT;

alter table ACT_RU_CASE_EXECUTION
    drop FOREIGN KEY ACT_FK_CASE_EXE_CASE_DEF;

alter table ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_CASE_EXE;

alter table ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_CASE_INST;

alter table ACT_RU_TASK
    drop foreign key ACT_FK_TASK_CASE_EXE;

alter table ACT_RU_TASK
    drop foreign key ACT_FK_TASK_CASE_DEF;

alter table ACT_RU_CASE_SENTRY_PART
    drop foreign key ACT_FK_CASE_SENTRY_CASE_INST;

alter table ACT_RU_CASE_SENTRY_PART
    drop foreign key ACT_FK_CASE_SENTRY_CASE_EXEC;

-- https://app.camunda.com/jira/browse/CAM-9165
drop index ACT_IDX_CASE_EXE_CASE_INST on ACT_RU_CASE_EXECUTION;

drop table if exists ACT_RE_CASE_DEF;
drop table if exists ACT_RU_CASE_EXECUTION;
drop table if exists ACT_RU_CASE_SENTRY_PART;
--
-- Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
-- under one or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information regarding copyright
-- ownership. Camunda licenses this file to you under the Apache License,
-- Version 2.0; you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

drop index ACT_IDX_BYTEARRAY_ROOT_PI on ACT_GE_BYTEARRAY;
drop index ACT_IDX_BYTEARRAY_RM_TIME on ACT_GE_BYTEARRAY;
drop index ACT_IDX_EXEC_ROOT_PI on ACT_RU_EXECUTION;
drop index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION;
drop index ACT_IDX_TASK_CREATE on ACT_RU_TASK;
drop index ACT_IDX_TASK_LAST_UPDATED on ACT_RU_TASK;
drop index ACT_IDX_TASK_ASSIGNEE on ACT_RU_TASK;
drop index ACT_IDX_TASK_OWNER on ACT_RU_TASK;
drop index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK;
drop index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK;
drop index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE;
drop index ACT_IDX_VARIABLE_TASK_NAME_TYPE on ACT_RU_VARIABLE;
drop index ACT_IDX_INC_CONFIGURATION on ACT_RU_INCIDENT;
drop index ACT_IDX_JOB_PROCINST on ACT_RU_JOB;
drop index ACT_IDX_AUTH_GROUP_ID on ACT_RU_AUTHORIZATION;

-- new metric milliseconds column
DROP INDEX ACT_IDX_METER_LOG_MS ON ACT_RU_METER_LOG;
DROP INDEX ACT_IDX_METER_LOG_NAME_MS ON ACT_RU_METER_LOG;
DROP INDEX ACT_IDX_METER_LOG_REPORT ON ACT_RU_METER_LOG;

-- old metric timestamp column
DROP INDEX ACT_IDX_METER_LOG_TIME ON ACT_RU_METER_LOG;
DROP INDEX ACT_IDX_METER_LOG ON ACT_RU_METER_LOG;

-- task metric timestamp column
drop index ACT_IDX_TASK_METER_LOG_TIME on ACT_RU_TASK_METER_LOG;


drop index ACT_IDX_EXT_TASK_TOPIC on ACT_RU_EXT_TASK;

alter table ACT_GE_BYTEARRAY
    drop FOREIGN KEY ACT_FK_BYTEARR_DEPL;

alter table ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_PROCINST;

alter table ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_PARENT;

alter table ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_SUPER;

alter table ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_PROCDEF;

alter table ACT_RU_IDENTITYLINK
    drop FOREIGN KEY ACT_FK_TSKASS_TASK;

alter table ACT_RU_IDENTITYLINK
    drop FOREIGN KEY ACT_FK_ATHRZ_PROCEDEF;

alter table ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_EXE;

alter table ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_PROCINST;

alter table ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_PROCDEF;

alter table ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_EXE;

alter table ACT_RU_VARIABLE
	drop FOREIGN KEY ACT_FK_VAR_PROCINST;

alter table ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_BYTEARRAY;

alter table ACT_RU_JOB
    drop FOREIGN KEY ACT_FK_JOB_EXCEPTION;

alter table ACT_RU_EVENT_SUBSCR
    drop FOREIGN KEY ACT_FK_EVENT_EXEC;

alter table ACT_RU_INCIDENT
    drop FOREIGN KEY ACT_FK_INC_EXE;

alter table ACT_RU_INCIDENT
    drop FOREIGN KEY ACT_FK_INC_PROCINST;

alter table ACT_RU_INCIDENT
    drop FOREIGN KEY ACT_FK_INC_PROCDEF;

alter table ACT_RU_INCIDENT
    drop FOREIGN KEY ACT_FK_INC_CAUSE;

alter table ACT_RU_INCIDENT
    drop FOREIGN KEY ACT_FK_INC_RCAUSE;

alter table ACT_RU_INCIDENT
    drop FOREIGN KEY ACT_FK_INC_JOB_DEF;

alter table ACT_RU_EXT_TASK
    drop FOREIGN KEY ACT_FK_EXT_TASK_EXE;

alter table ACT_RU_BATCH
    drop FOREIGN KEY ACT_FK_BATCH_SEED_JOB_DEF;

alter table ACT_RU_BATCH
    drop FOREIGN KEY ACT_FK_BATCH_MONITOR_JOB_DEF;

alter table ACT_RU_BATCH
    drop FOREIGN KEY ACT_FK_BATCH_JOB_DEF;

alter table ACT_RU_EXT_TASK
    drop FOREIGN KEY ACT_FK_EXT_TASK_ERROR_DETAILS;

alter table ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_BATCH;

drop index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK;
drop index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR;

-- indexes for deadlock problems - https://app.camunda.com/jira/browse/CAM-2567
drop index ACT_IDX_INC_CAUSEINCID on ACT_RU_INCIDENT;
drop index ACT_IDX_INC_EXID on ACT_RU_INCIDENT;
drop index ACT_IDX_INC_PROCDEFID on ACT_RU_INCIDENT;
drop index ACT_IDX_INC_PROCINSTID on ACT_RU_INCIDENT;
drop index ACT_IDX_INC_ROOTCAUSEINCID on ACT_RU_INCIDENT;
drop index ACT_IDX_INC_JOB_DEF on ACT_RU_INCIDENT;
drop index ACT_IDX_AUTH_RESOURCE_ID on ACT_RU_AUTHORIZATION;
drop index ACT_IDX_EXT_TASK_EXEC on ACT_RU_EXT_TASK;

drop index ACT_IDX_BYTEARRAY_NAME on ACT_GE_BYTEARRAY;
drop index ACT_IDX_DEPLOYMENT_NAME on ACT_RE_DEPLOYMENT;
drop index ACT_IDX_JOBDEF_PROC_DEF_ID ON ACT_RU_JOBDEF;
drop index ACT_IDX_JOB_HANDLER_TYPE ON ACT_RU_JOB;
drop index ACT_IDX_EVENT_SUBSCR_EVT_NAME ON ACT_RU_EVENT_SUBSCR;
drop index ACT_IDX_PROCDEF_DEPLOYMENT_ID ON ACT_RE_PROCDEF;

drop index ACT_IDX_EXT_TASK_TENANT_ID on ACT_RU_EXT_TASK;
drop index ACT_IDX_EXT_TASK_PRIORITY on ACT_RU_EXT_TASK;
drop index ACT_IDX_EXT_TASK_ERR_DETAILS on ACT_RU_EXT_TASK;
drop index ACT_IDX_INC_TENANT_ID on ACT_RU_INCIDENT;
drop index ACT_IDX_JOBDEF_TENANT_ID ON ACT_RU_JOBDEF;
drop index ACT_IDX_JOB_TENANT_ID ON ACT_RU_JOB;
drop index ACT_IDX_EVENT_SUBSCR_TENANT_ID on ACT_RU_EVENT_SUBSCR;
drop index ACT_IDX_VARIABLE_TENANT_ID ON ACT_RU_VARIABLE;
drop index ACT_IDX_TASK_TENANT_ID ON ACT_RU_TASK;
drop index ACT_IDX_EXEC_TENANT_ID ON ACT_RU_EXECUTION;
drop index ACT_IDX_PROCDEF_TENANT_ID ON ACT_RE_PROCDEF;
drop index ACT_IDX_DEPLOYMENT_TENANT_ID ON ACT_RE_DEPLOYMENT;

drop index ACT_IDX_JOB_JOB_DEF_ID on ACT_RU_JOB;
drop index ACT_IDX_BATCH_SEED_JOB_DEF on ACT_RU_BATCH;
drop index ACT_IDX_BATCH_MONITOR_JOB_DEF on ACT_RU_BATCH;
drop index ACT_IDX_BATCH_JOB_DEF on ACT_RU_BATCH;

drop index ACT_IDX_PROCDEF_VER_TAG on ACT_RE_PROCDEF;

drop index ACT_IDX_JOB_EXECUTION_ID on ACT_RU_JOB;
drop index ACT_IDX_JOB_HANDLER on ACT_RU_JOB;

drop index ACT_IDX_AUTH_ROOT_PI on ACT_RU_AUTHORIZATION;
drop index ACT_IDX_AUTH_RM_TIME on ACT_RU_AUTHORIZATION;

drop index ACT_IDX_BATCH_ID on ACT_RU_VARIABLE;

drop table if exists ACT_GE_PROPERTY;
drop table if exists ACT_RU_VARIABLE;
drop table if exists ACT_GE_BYTEARRAY;
drop table if exists ACT_RE_DEPLOYMENT;
drop table if exists ACT_RU_IDENTITYLINK;
drop table if exists ACT_RU_TASK;
drop table if exists ACT_RE_PROCDEF;
drop table if exists ACT_RE_CAMFORMDEF;
drop table if exists ACT_RU_EXECUTION;
drop table if exists ACT_RU_JOB;
drop table if exists ACT_RU_JOBDEF;
drop table if exists ACT_RU_EVENT_SUBSCR;
drop table if exists ACT_RU_INCIDENT;
drop table if exists ACT_RU_AUTHORIZATION;
drop table if exists ACT_RU_FILTER;
drop table if exists ACT_RU_METER_LOG;
drop table if exists ACT_RU_TASK_METER_LOG;
drop table if exists ACT_RU_EXT_TASK;
drop table if exists ACT_RU_BATCH;
drop table if exists ACT_GE_SCHEMA_LOG;
--
-- Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
-- under one or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information regarding copyright
-- ownership. Camunda licenses this file to you under the Apache License,
-- Version 2.0; you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

drop index ACT_IDX_HI_DEC_INST_ID on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_KEY on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_PI on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_CI on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_ACT on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_ACT_INST on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_TIME on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_TENANT_ID on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_ROOT_ID on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_REQ_ID on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_REQ_KEY on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_ROOT_PI on ACT_HI_DECINST;
drop index ACT_IDX_HI_DEC_INST_RM_TIME on ACT_HI_DECINST;

drop index ACT_IDX_HI_DEC_IN_INST on ACT_HI_DEC_IN;
drop index ACT_IDX_HI_DEC_IN_CLAUSE on ACT_HI_DEC_IN;
drop index ACT_IDX_HI_DEC_IN_ROOT_PI on ACT_HI_DEC_IN;
drop index ACT_IDX_HI_DEC_IN_RM_TIME on ACT_HI_DEC_IN;

drop index ACT_IDX_HI_DEC_OUT_INST on ACT_HI_DEC_OUT;
drop index ACT_IDX_HI_DEC_OUT_RULE on ACT_HI_DEC_OUT;
drop index ACT_IDX_HI_DEC_OUT_ROOT_PI on ACT_HI_DEC_OUT;
drop index ACT_IDX_HI_DEC_OUT_RM_TIME on ACT_HI_DEC_OUT;

drop table if exists ACT_HI_DECINST;

drop table if exists ACT_HI_DEC_IN;

drop table if exists ACT_HI_DEC_OUT;
--
-- Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
-- under one or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information regarding copyright
-- ownership. Camunda licenses this file to you under the Apache License,
-- Version 2.0; you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

drop index ACT_IDX_HI_CAS_I_CLOSE on ACT_HI_CASEINST;
drop index ACT_IDX_HI_CAS_I_BUSKEY on ACT_HI_CASEINST;
drop index ACT_IDX_HI_CAS_I_TENANT_ID on ACT_HI_CASEINST;
drop index ACT_IDX_HI_CAS_A_I_CREATE on ACT_HI_CASEACTINST;
drop index ACT_IDX_HI_CAS_A_I_END on ACT_HI_CASEACTINST;
drop index ACT_IDX_HI_CAS_A_I_COMP on ACT_HI_CASEACTINST;
drop index ACT_IDX_HI_CAS_A_I_CASEINST on ACT_HI_CASEACTINST;
drop index ACT_IDX_HI_CAS_A_I_TENANT_ID on ACT_HI_CASEACTINST;

drop table if exists ACT_HI_CASEINST;
drop table if exists ACT_HI_CASEACTINST;
--
-- Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
-- under one or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information regarding copyright
-- ownership. Camunda licenses this file to you under the Apache License,
-- Version 2.0; you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

drop index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PRO_INST_TENANT_ID on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PRO_INST_PROC_DEF_KEY on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PRO_INST_PROC_TIME on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PI_PDEFID_END_TIME on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PRO_INST_ROOT_PI on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PRO_INST_RM_TIME on ACT_HI_PROCINST;

drop index ACT_IDX_HI_ACTINST_ROOT_PI on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_START_END on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_COMP on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_STATS on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_TENANT_ID on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_PROC_DEF_KEY on ACT_HI_ACTINST;
drop index ACT_IDX_HI_AI_PDEFID_END_TIME on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_RM_TIME on ACT_HI_ACTINST;

drop index ACT_IDX_HI_TASKINST_ROOT_PI on ACT_HI_TASKINST;
drop index ACT_IDX_HI_TASK_INST_TENANT_ID on ACT_HI_TASKINST;
drop index ACT_IDX_HI_TASK_INST_PROC_DEF_KEY on ACT_HI_TASKINST;
drop index ACT_IDX_HI_TASKINST_PROCINST on ACT_HI_TASKINST;
drop index ACT_IDX_HI_TASKINSTID_PROCINST on ACT_HI_TASKINST;
drop index ACT_IDX_HI_TASK_INST_RM_TIME on ACT_HI_TASKINST;
drop index ACT_IDX_HI_TASK_INST_START on ACT_HI_TASKINST;
drop index ACT_IDX_HI_TASK_INST_END on ACT_HI_TASKINST;

drop index ACT_IDX_HI_IDENT_LNK_ROOT_PI on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LNK_GROUP on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LNK_TENANT_ID on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LNK_PROC_DEF_KEY on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LINK_TASK on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LINK_RM_TIME on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LNK_TIMESTAMP on ACT_HI_IDENTITYLINK;

drop index ACT_IDX_HI_DETAIL_ROOT_PI on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_CASE_INST on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_CASE_EXEC on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_TENANT_ID on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_PROC_DEF_KEY on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_BYTEAR on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_RM_TIME on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_TASK_BYTEAR on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_VAR_INST_ID on ACT_HI_DETAIL;

drop index ACT_IDX_HI_VARINST_ROOT_PI on ACT_HI_VARINST;
drop index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST;
drop index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST;
drop index ACT_IDX_HI_CASEVAR_CASE_INST on ACT_HI_VARINST;
drop index ACT_IDX_HI_VAR_INST_TENANT_ID on ACT_HI_VARINST;
drop index ACT_IDX_HI_VAR_INST_PROC_DEF_KEY on ACT_HI_VARINST;
drop index ACT_IDX_HI_VARINST_BYTEAR on ACT_HI_VARINST;
drop index ACT_IDX_HI_VARINST_RM_TIME on ACT_HI_VARINST;
drop index ACT_IDX_HI_VAR_PI_NAME_TYPE on ACT_HI_VARINST;
drop index ACT_IDX_HI_VARINST_NAME on ACT_HI_VARINST;
drop index ACT_IDX_HI_VARINST_ACT_INST_ID on ACT_HI_VARINST;

drop index ACT_IDX_HI_INCIDENT_TENANT_ID on ACT_HI_INCIDENT;
drop index ACT_IDX_HI_INCIDENT_PROC_DEF_KEY on ACT_HI_INCIDENT;
drop index ACT_IDX_HI_INCIDENT_ROOT_PI on ACT_HI_INCIDENT;
drop index ACT_IDX_HI_INCIDENT_PROCINST on ACT_HI_INCIDENT;
drop index ACT_IDX_HI_INCIDENT_RM_TIME on ACT_HI_INCIDENT;
drop index ACT_IDX_HI_INCIDENT_CREATE_TIME on ACT_HI_INCIDENT;
drop index ACT_IDX_HI_INCIDENT_END_TIME on ACT_HI_INCIDENT;

drop index ACT_IDX_HI_JOB_LOG_ROOT_PI on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_PROCINST on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_PROCDEF on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_TENANT_ID on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_JOB_DEF_ID on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_PROC_DEF_KEY on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_EX_STACK on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_RM_TIME on ACT_HI_JOB_LOG;
drop index ACT_IDX_HI_JOB_LOG_JOB_CONF on ACT_HI_JOB_LOG;

drop index ACT_HI_EXT_TASK_LOG_ROOT_PI on ACT_HI_EXT_TASK_LOG;
drop index ACT_HI_EXT_TASK_LOG_PROCINST on ACT_HI_EXT_TASK_LOG;
drop index ACT_HI_EXT_TASK_LOG_PROCDEF on ACT_HI_EXT_TASK_LOG;
drop index ACT_HI_EXT_TASK_LOG_PROC_DEF_KEY on ACT_HI_EXT_TASK_LOG;
drop index ACT_HI_EXT_TASK_LOG_TENANT_ID on ACT_HI_EXT_TASK_LOG;
drop index ACT_IDX_HI_EXTTASKLOG_ERRORDET on ACT_HI_EXT_TASK_LOG;
drop index ACT_HI_EXT_TASK_LOG_RM_TIME on ACT_HI_EXT_TASK_LOG;

drop index ACT_HI_BAT_RM_TIME on ACT_HI_BATCH;

drop index ACT_IDX_HI_OP_LOG_ROOT_PI on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_PROCINST on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_PROCDEF on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_TASK on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_RM_TIME on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_TIMESTAMP on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_USER_ID on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_OP_TYPE on ACT_HI_OP_LOG;
drop index ACT_IDX_HI_OP_LOG_ENTITY_TYPE on ACT_HI_OP_LOG;

drop index ACT_IDX_HI_ATTACHMENT_CONTENT on ACT_HI_ATTACHMENT;
drop index ACT_IDX_HI_ATTACHMENT_ROOT_PI on ACT_HI_ATTACHMENT;
drop index ACT_IDX_HI_ATTACHMENT_PROCINST on ACT_HI_ATTACHMENT;
drop index ACT_IDX_HI_ATTACHMENT_TASK on ACT_HI_ATTACHMENT;
drop index ACT_IDX_HI_ATTACHMENT_RM_TIME on ACT_HI_ATTACHMENT;

drop index ACT_IDX_HI_COMMENT_TASK on ACT_HI_COMMENT;
drop index ACT_IDX_HI_COMMENT_ROOT_PI on ACT_HI_COMMENT;
drop index ACT_IDX_HI_COMMENT_PROCINST on ACT_HI_COMMENT;
drop index ACT_IDX_HI_COMMENT_RM_TIME on ACT_HI_COMMENT;

drop table if exists ACT_HI_PROCINST;
drop table if exists ACT_HI_ACTINST;
drop table if exists ACT_HI_VARINST;
drop table if exists ACT_HI_TASKINST;
drop table if exists ACT_HI_DETAIL;
drop table if exists ACT_HI_COMMENT;
drop table if exists ACT_HI_ATTACHMENT;
drop table if exists ACT_HI_OP_LOG;
drop table if exists ACT_HI_INCIDENT;
drop table if exists ACT_HI_JOB_LOG;
drop table if exists ACT_HI_BATCH;
drop table if exists ACT_HI_IDENTITYLINK;
drop table if exists ACT_HI_EXT_TASK_LOG;
