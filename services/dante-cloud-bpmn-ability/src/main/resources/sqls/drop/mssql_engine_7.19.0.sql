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
    drop constraint ACT_FK_DEC_REQ;

drop index ACT_RE_DECISION_DEF.ACT_IDX_DEC_DEF_TENANT_ID;
drop index ACT_RE_DECISION_DEF.ACT_IDX_DEC_DEF_REQ_ID;
drop index ACT_RE_DECISION_REQ_DEF.ACT_IDX_DEC_REQ_DEF_TENANT_ID;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_DECISION_DEF') drop table ACT_RE_DECISION_DEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_DECISION_REQ_DEF') drop table ACT_RE_DECISION_REQ_DEF;
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

drop index ACT_RU_CASE_EXECUTION.ACT_IDX_CASE_EXEC_BUSKEY;
drop index ACT_RE_CASE_DEF.ACT_IDX_CASE_DEF_TENANT_ID;
drop index ACT_RU_CASE_EXECUTION.ACT_IDX_CASE_EXEC_TENANT_ID;

-- indexes for concurrency problems - https://app.camunda.com/jira/browse/CAM-1646 --
drop index ACT_RU_CASE_EXECUTION.ACT_IDX_CASE_EXEC_CASE;
drop index ACT_RU_CASE_EXECUTION.ACT_IDX_CASE_EXEC_PARENT;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_CASE_EXEC;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_CASE_INST;
drop index ACT_RU_TASK.ACT_IDX_TASK_CASE_EXEC;
drop index ACT_RU_TASK.ACT_IDX_TASK_CASE_DEF_ID;
drop index ACT_RU_CASE_SENTRY_PART.ACT_IDX_CASE_SENTRY_CASE_INST;
drop index ACT_RU_CASE_SENTRY_PART.ACT_IDX_CASE_SENTRY_CASE_EXEC;

alter table ACT_RU_CASE_EXECUTION
    drop constraint ACT_FK_CASE_EXE_CASE_INST;

alter table ACT_RU_CASE_EXECUTION
    drop constraint ACT_FK_CASE_EXE_PARENT;

alter table ACT_RU_CASE_EXECUTION
    drop constraint ACT_FK_CASE_EXE_CASE_DEF;

alter table ACT_RU_VARIABLE
    drop constraint ACT_FK_VAR_CASE_EXE;

alter table ACT_RU_VARIABLE
    drop constraint ACT_FK_VAR_CASE_INST;

alter table ACT_RU_TASK
    drop constraint ACT_FK_TASK_CASE_EXE;

alter table ACT_RU_TASK
    drop constraint ACT_FK_TASK_CASE_DEF;

alter table ACT_RU_CASE_SENTRY_PART
    drop constraint ACT_FK_CASE_SENTRY_CASE_INST;

alter table ACT_RU_CASE_SENTRY_PART
    drop constraint ACT_FK_CASE_SENTRY_CASE_EXEC;

-- https://app.camunda.com/jira/browse/CAM-9165
drop index ACT_RU_CASE_EXECUTION.ACT_IDX_CASE_EXE_CASE_INST;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_CASE_DEF') drop table ACT_RE_CASE_DEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_CASE_EXECUTION') drop table ACT_RU_CASE_EXECUTION;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_CASE_SENTRY_PART') drop table ACT_RU_CASE_SENTRY_PART;
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

drop index ACT_GE_BYTEARRAY.ACT_IDX_BYTEARRAY_RM_TIME;
drop index ACT_GE_BYTEARRAY.ACT_IDX_BYTEARRAY_ROOT_PI;
drop index ACT_RU_EXECUTION.ACT_IDX_EXEC_ROOT_PI;
drop index ACT_RU_EXECUTION.ACT_IDX_EXEC_BUSKEY;
drop index ACT_RU_TASK.ACT_IDX_TASK_CREATE;
drop index ACT_RU_TASK.ACT_IDX_TASK_LAST_UPDATED;
drop index ACT_RU_TASK.ACT_IDX_TASK_ASSIGNEE;
drop index ACT_RU_TASK.ACT_IDX_TASK_OWNER;
drop index ACT_RU_IDENTITYLINK.ACT_IDX_IDENT_LNK_USER;
drop index ACT_RU_IDENTITYLINK.ACT_IDX_IDENT_LNK_GROUP;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_TASK_ID;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_TASK_NAME_TYPE;
drop index ACT_RU_EVENT_SUBSCR.ACT_IDX_EVENT_SUBSCR_CONFIG_;
drop index ACT_RU_INCIDENT.ACT_IDX_INC_CONFIGURATION;
drop index ACT_RU_JOB.ACT_IDX_JOB_PROCINST;
drop index ACT_RU_AUTHORIZATION.ACT_UNIQ_AUTH_GROUP;
drop index ACT_RU_AUTHORIZATION.ACT_UNIQ_AUTH_USER;
drop index ACT_RU_VARIABLE.ACT_UNIQ_VARIABLE;
drop index ACT_RU_AUTHORIZATION.ACT_IDX_AUTH_GROUP_ID;

-- new metric milliseconds column
DROP INDEX ACT_RU_METER_LOG.ACT_IDX_METER_LOG_MS;
DROP INDEX ACT_RU_METER_LOG.ACT_IDX_METER_LOG_NAME_MS;
DROP INDEX ACT_RU_METER_LOG.ACT_IDX_METER_LOG_REPORT;

-- old metric timestamp column
DROP INDEX ACT_RU_METER_LOG.ACT_IDX_METER_LOG_TIME;
DROP INDEX ACT_RU_METER_LOG.ACT_IDX_METER_LOG;

-- task metric timestamp column
drop index ACT_RU_TASK_METER_LOG.ACT_IDX_TASK_METER_LOG_TIME;


-- indexes for concurrency problems - https://app.camunda.com/jira/browse/CAM-1646 --
drop index ACT_RU_EXECUTION.ACT_IDX_EXECUTION_PROC;
drop index ACT_RU_EXECUTION.ACT_IDX_EXECUTION_PARENT;
drop index ACT_RU_EXECUTION.ACT_IDX_EXECUTION_SUPER;
drop index ACT_RU_EXECUTION.ACT_IDX_EXECUTION_PROCINST;
drop index ACT_RU_EVENT_SUBSCR.ACT_IDX_EVENT_SUBSCR_EXEC;
drop index ACT_GE_BYTEARRAY.ACT_IDX_BA_DEPLOYMENT;
drop index ACT_RU_IDENTITYLINK.ACT_IDX_IDENT_LNK_TASK;
drop index ACT_RU_INCIDENT.ACT_IDX_INCIDENT_EXEC;
drop index ACT_RU_INCIDENT.ACT_IDX_INCIDENT_PROCINST;
drop index ACT_RU_INCIDENT.ACT_IDX_INCIDENT_PROC_DEF_ID;
drop index ACT_RU_INCIDENT.ACT_IDX_INCIDENT_CAUSE;
drop index ACT_RU_INCIDENT.ACT_IDX_INCIDENT_ROOT_CAUSE;
drop index ACT_RU_INCIDENT.ACT_IDX_INCIDENT_JOB_DEF;
drop index ACT_RU_JOB.ACT_IDX_JOB_EXCEPTION_STACK;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_BA;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_EXEC;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_PROCINST;
drop index ACT_RU_TASK.ACT_IDX_TASK_EXEC;
drop index ACT_RU_TASK.ACT_IDX_TASK_PROCINST;
drop index ACT_RU_TASK.ACT_IDX_TASK_PROC_DEF_ID;
drop index ACT_RU_AUTHORIZATION.ACT_IDX_AUTH_RESOURCE_ID;
drop index ACT_RU_EXT_TASK.ACT_IDX_EXT_TASK_TOPIC;
drop index ACT_RU_EXT_TASK.ACT_IDX_EXT_TASK_EXEC;


drop index ACT_GE_BYTEARRAY.ACT_IDX_BYTEARRAY_NAME;
drop index ACT_RE_DEPLOYMENT.ACT_IDX_DEPLOYMENT_NAME;
drop index ACT_RU_JOBDEF.ACT_IDX_JOBDEF_PROC_DEF_ID;
drop index ACT_RU_JOB.ACT_IDX_JOB_HANDLER_TYPE;
drop index ACT_RU_EVENT_SUBSCR.ACT_IDX_EVENT_SUBSCR_EVT_NAME;
drop index ACT_RE_PROCDEF.ACT_IDX_PROCDEF_DEPLOYMENT_ID;

drop index ACT_RU_EXT_TASK.ACT_IDX_EXT_TASK_TENANT_ID;
drop index ACT_RU_EXT_TASK.ACT_IDX_EXT_TASK_PRIORITY;
drop index ACT_RU_EXT_TASK.ACT_IDX_EXT_TASK_ERR_DETAILS;
drop index ACT_RU_INCIDENT.ACT_IDX_INC_TENANT_ID;
drop index ACT_RU_JOBDEF.ACT_IDX_JOBDEF_TENANT_ID;
drop index ACT_RU_JOB.ACT_IDX_JOB_TENANT_ID;
drop index ACT_RU_EVENT_SUBSCR.ACT_IDX_EVENT_SUBSCR_TENANT_ID;
drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_TENANT_ID;
drop index ACT_RU_TASK.ACT_IDX_TASK_TENANT_ID;
drop index ACT_RU_EXECUTION.ACT_IDX_EXEC_TENANT_ID;
drop index ACT_RE_PROCDEF.ACT_IDX_PROCDEF_TENANT_ID;
drop index ACT_RE_DEPLOYMENT.ACT_IDX_DEPLOYMENT_TENANT_ID;

drop index ACT_RU_JOB.ACT_IDX_JOB_JOB_DEF_ID;
drop index ACT_RU_BATCH.ACT_IDX_BATCH_SEED_JOB_DEF;
drop index ACT_RU_BATCH.ACT_IDX_BATCH_MONITOR_JOB_DEF;
drop index ACT_RU_BATCH.ACT_IDX_BATCH_JOB_DEF;

drop index ACT_RU_JOB.ACT_IDX_JOB_EXECUTION_ID;

alter table ACT_GE_BYTEARRAY
    drop constraint ACT_FK_BYTEARR_DEPL;

alter table ACT_RU_EXECUTION
    drop constraint ACT_FK_EXE_PROCINST;

alter table ACT_RU_EXECUTION
    drop constraint ACT_FK_EXE_PROCDEF;

alter table ACT_RU_EXECUTION
    drop constraint ACT_FK_EXE_PARENT;

alter table ACT_RU_EXECUTION
    drop constraint ACT_FK_EXE_SUPER;

alter table ACT_RU_IDENTITYLINK
    drop constraint ACT_FK_TSKASS_TASK;

alter table ACT_RU_IDENTITYLINK
    drop constraint ACT_FK_ATHRZ_PROCEDEF;

alter table ACT_RU_TASK
	drop constraint ACT_FK_TASK_EXE;

alter table ACT_RU_TASK
	drop constraint ACT_FK_TASK_PROCINST;

alter table ACT_RU_TASK
	drop constraint ACT_FK_TASK_PROCDEF;

alter table ACT_RU_VARIABLE
    drop constraint ACT_FK_VAR_EXE;

alter table ACT_RU_VARIABLE
	drop constraint ACT_FK_VAR_PROCINST;

alter table ACT_RU_VARIABLE
    drop constraint ACT_FK_VAR_BYTEARRAY;

alter table ACT_RU_JOB
    drop constraint ACT_FK_JOB_EXCEPTION;

alter table ACT_RU_EVENT_SUBSCR
    drop constraint ACT_FK_EVENT_EXEC;

alter table ACT_RU_INCIDENT
    drop constraint ACT_FK_INC_EXE;

alter table ACT_RU_INCIDENT
    drop constraint ACT_FK_INC_PROCINST;

alter table ACT_RU_INCIDENT
    drop constraint ACT_FK_INC_PROCDEF;

alter table ACT_RU_INCIDENT
    drop constraint ACT_FK_INC_CAUSE;

alter table ACT_RU_INCIDENT
    drop constraint ACT_FK_INC_RCAUSE;

alter table ACT_RU_INCIDENT
    drop constraint ACT_FK_INC_JOB_DEF;

alter table ACT_RU_EXT_TASK
    drop constraint ACT_FK_EXT_TASK_EXE;

alter table ACT_RU_BATCH
    drop constraint ACT_FK_BATCH_SEED_JOB_DEF;

alter table ACT_RU_BATCH
    drop constraint ACT_FK_BATCH_MONITOR_JOB_DEF;

alter table ACT_RU_BATCH
    drop constraint ACT_FK_BATCH_JOB_DEF;

alter table ACT_RU_EXT_TASK
    drop CONSTRAINT ACT_FK_EXT_TASK_ERROR_DETAILS;

alter table ACT_RU_VARIABLE
    drop CONSTRAINT ACT_FK_VAR_BATCH;

drop index ACT_RU_IDENTITYLINK.ACT_IDX_ATHRZ_PROCEDEF;

drop index ACT_RE_PROCDEF.ACT_IDX_PROCDEF_VER_TAG;

drop index ACT_RU_AUTHORIZATION.ACT_IDX_AUTH_ROOT_PI;
drop index ACT_RU_AUTHORIZATION.ACT_IDX_AUTH_RM_TIME;

drop index ACT_RU_VARIABLE.ACT_IDX_BATCH_ID;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_GE_PROPERTY') drop table ACT_GE_PROPERTY;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_GE_BYTEARRAY') drop table ACT_GE_BYTEARRAY;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_PROCDEF') drop table ACT_RE_PROCDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_CAMFORMDEF') drop table ACT_RE_CAMFORMDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_DEPLOYMENT') drop table ACT_RE_DEPLOYMENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_IDENTITYLINK') drop table ACT_RU_IDENTITYLINK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_TASK') drop table ACT_RU_TASK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_VARIABLE') drop table ACT_RU_VARIABLE;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EXECUTION') drop table ACT_RU_EXECUTION;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EVENT_SUBSCR') drop table ACT_RU_EVENT_SUBSCR;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_JOB') drop table ACT_RU_JOB;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_JOBDEF') drop table ACT_RU_JOBDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_INCIDENT') drop table ACT_RU_INCIDENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_AUTHORIZATION') drop table ACT_RU_AUTHORIZATION;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_FILTER') drop table ACT_RU_FILTER;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_METER_LOG') drop table ACT_RU_METER_LOG;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_TASK_METER_LOG') drop table ACT_RU_TASK_METER_LOG;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EXT_TASK') drop table ACT_RU_EXT_TASK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_BATCH') drop table ACT_RU_BATCH;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_GE_SCHEMA_LOG') drop table ACT_GE_SCHEMA_LOG;
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

drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_ID;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_KEY;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_PI;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_CI;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_ACT;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_ACT_INST;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_TIME;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_TENANT_ID;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_ROOT_ID;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_REQ_ID;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_REQ_KEY;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_ROOT_PI;
drop index ACT_HI_DECINST.ACT_IDX_HI_DEC_INST_RM_TIME;

drop index ACT_HI_DEC_IN.ACT_IDX_HI_DEC_IN_INST;
drop index ACT_HI_DEC_IN.ACT_IDX_HI_DEC_IN_CLAUSE;
drop index ACT_HI_DEC_IN.ACT_IDX_HI_DEC_IN_ROOT_PI;
drop index ACT_HI_DEC_IN.ACT_IDX_HI_DEC_IN_RM_TIME;

drop index ACT_HI_DEC_OUT.ACT_IDX_HI_DEC_OUT_INST;
drop index ACT_HI_DEC_OUT.ACT_IDX_HI_DEC_OUT_RULE;
drop index ACT_HI_DEC_OUT.ACT_IDX_HI_DEC_OUT_ROOT_PI;
drop index ACT_HI_DEC_OUT.ACT_IDX_HI_DEC_OUT_RM_TIME;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_DECINST') drop table ACT_HI_DECINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_DEC_IN') drop table ACT_HI_DEC_IN;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_DEC_OUT') drop table ACT_HI_DEC_OUT;
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

drop index ACT_HI_CASEINST.ACT_IDX_HI_CAS_I_CLOSE;
drop index ACT_HI_CASEINST.ACT_IDX_HI_CAS_I_BUSKEY;
drop index ACT_HI_CASEINST.ACT_IDX_HI_CAS_I_TENANT_ID;
drop index ACT_HI_CASEACTINST.ACT_IDX_HI_CAS_A_I_CREATE;
drop index ACT_HI_CASEACTINST.ACT_IDX_HI_CAS_A_I_END;
drop index ACT_HI_CASEACTINST.ACT_IDX_HI_CAS_A_I_COMP;
drop index ACT_HI_CASEACTINST.ACT_IDX_HI_CAS_A_I_TENANT_ID;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_CASEINST') drop table ACT_HI_CASEINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_CASEACTINST') drop table ACT_HI_CASEACTINST;
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

drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_INST_END;
drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_I_BUSKEY;
drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_INST_TENANT_ID;
drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_INST_PROC_DEF_KEY;
drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_INST_PROC_TIME;
drop index ACT_HI_PROCINST.ACT_IDX_HI_PI_PDEFID_END_TIME;
drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_INST_ROOT_PI;
drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_INST_RM_TIME;

drop index ACT_HI_ACTINST.ACT_IDX_HI_ACTINST_ROOT_PI;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_START_END;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_END;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_PROCINST;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_COMP;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_STATS;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_TENANT_ID;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_PROC_DEF_KEY;
drop index ACT_HI_ACTINST.ACT_IDX_HI_AI_PDEFID_END_TIME;
drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_RM_TIME;

drop index ACT_HI_TASKINST.ACT_IDX_HI_TASKINST_ROOT_PI;
drop index ACT_HI_TASKINST.ACT_IDX_HI_TASK_INST_TENANT_ID;
drop index ACT_HI_TASKINST.ACT_IDX_HI_TASK_INST_PROC_DEF_KEY;
drop index ACT_HI_TASKINST.ACT_IDX_HI_TASKINST_PROCINST;
drop index ACT_HI_TASKINST.ACT_IDX_HI_TASKINSTID_PROCINST;
drop index ACT_HI_TASKINST.ACT_IDX_HI_TASK_INST_RM_TIME;
drop index ACT_HI_TASKINST.ACT_IDX_HI_TASK_INST_START;
drop index ACT_HI_TASKINST.ACT_IDX_HI_TASK_INST_END;

drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_ROOT_PI;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_PROC_INST;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_ACT_INST;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_CASE_INST;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_CASE_EXEC;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_TIME;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_NAME;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_TASK_ID;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_TENANT_ID;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_PROC_DEF_KEY;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_BYTEAR;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_RM_TIME;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_TASK_BYTEAR;
drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_VAR_INST_ID;

drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_ROOT_PI;
drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_USER;
drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_GROUP;
drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_TENANT_ID;
drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_PROC_DEF_KEY;
drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LINK_TASK;
drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LINK_RM_TIME;
drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_TIMESTAMP;

drop index ACT_HI_VARINST.ACT_IDX_HI_VARINST_ROOT_PI;
drop index ACT_HI_VARINST.ACT_IDX_HI_PROCVAR_PROC_INST;
drop index ACT_HI_VARINST.ACT_IDX_HI_PROCVAR_NAME_TYPE;
drop index ACT_HI_VARINST.ACT_IDX_HI_CASEVAR_CASE_INST;
drop index ACT_HI_VARINST.ACT_IDX_HI_VAR_INST_TENANT_ID;
drop index ACT_HI_VARINST.ACT_IDX_HI_VAR_INST_PROC_DEF_KEY;
drop index ACT_HI_VARINST.ACT_IDX_HI_VARINST_BYTEAR;
drop index ACT_HI_VARINST.ACT_IDX_HI_VARINST_RM_TIME;
drop index ACT_HI_VARINST.ACT_IDX_HI_VAR_PI_NAME_TYPE;
drop index ACT_HI_VARINST.ACT_IDX_HI_VARINST_NAME;
drop index ACT_HI_VARINST.ACT_IDX_HI_VARINST_ACT_INST_ID;

drop index ACT_HI_INCIDENT.ACT_IDX_HI_INCIDENT_TENANT_ID;
drop index ACT_HI_INCIDENT.ACT_IDX_HI_INCIDENT_PROC_DEF_KEY;
drop index ACT_HI_INCIDENT.ACT_IDX_HI_INCIDENT_ROOT_PI;
drop index ACT_HI_INCIDENT.ACT_IDX_HI_INCIDENT_PROCINST;
drop index ACT_HI_INCIDENT.ACT_IDX_HI_INCIDENT_RM_TIME;
drop index ACT_HI_INCIDENT.ACT_IDX_HI_INCIDENT_CREATE_TIME;
drop index ACT_HI_INCIDENT.ACT_IDX_HI_INCIDENT_END_TIME;

drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_ROOT_PI;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_PROCINST;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_PROCDEF;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_TENANT_ID;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_JOB_DEF_ID;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_PROC_DEF_KEY;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_EX_STACK;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_RM_TIME;
drop index ACT_HI_JOB_LOG.ACT_IDX_HI_JOB_LOG_JOB_CONF;

drop index ACT_HI_EXT_TASK_LOG.ACT_HI_EXT_TASK_LOG_ROOT_PI;
drop index ACT_HI_EXT_TASK_LOG.ACT_HI_EXT_TASK_LOG_PROCINST;
drop index ACT_HI_EXT_TASK_LOG.ACT_HI_EXT_TASK_LOG_PROCDEF;
drop index ACT_HI_EXT_TASK_LOG.ACT_HI_EXT_TASK_LOG_PROC_DEF_KEY;
drop index ACT_HI_EXT_TASK_LOG.ACT_HI_EXT_TASK_LOG_TENANT_ID;
drop index ACT_HI_EXT_TASK_LOG.ACT_IDX_HI_EXTTASKLOG_ERRORDET;
drop index ACT_HI_EXT_TASK_LOG.ACT_HI_EXT_TASK_LOG_RM_TIME;

drop index ACT_HI_BATCH.ACT_HI_BAT_RM_TIME;

drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_ROOT_PI;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_PROCINST;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_PROCDEF;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_TASK;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_RM_TIME;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_TIMESTAMP;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_USER_ID;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_OP_TYPE;
drop index ACT_HI_OP_LOG.ACT_IDX_HI_OP_LOG_ENTITY_TYPE;

drop index ACT_HI_ATTACHMENT.ACT_IDX_HI_ATTACHMENT_CONTENT;
drop index ACT_HI_ATTACHMENT.ACT_IDX_HI_ATTACHMENT_ROOT_PI;
drop index ACT_HI_ATTACHMENT.ACT_IDX_HI_ATTACHMENT_PROCINST;
drop index ACT_HI_ATTACHMENT.ACT_IDX_HI_ATTACHMENT_TASK;
drop index ACT_HI_ATTACHMENT.ACT_IDX_HI_ATTACHMENT_RM_TIME;

drop index ACT_HI_COMMENT.ACT_IDX_HI_COMMENT_TASK;
drop index ACT_HI_COMMENT.ACT_IDX_HI_COMMENT_ROOT_PI;
drop index ACT_HI_COMMENT.ACT_IDX_HI_COMMENT_PROCINST;
drop index ACT_HI_COMMENT.ACT_IDX_HI_COMMENT_RM_TIME;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_PROCINST') drop table ACT_HI_PROCINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_ACTINST') drop table ACT_HI_ACTINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_VARINST') drop table ACT_HI_VARINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_TASKINST') drop table ACT_HI_TASKINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_DETAIL') drop table ACT_HI_DETAIL;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_COMMENT') drop table ACT_HI_COMMENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_ATTACHMENT') drop table ACT_HI_ATTACHMENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_OP_LOG') drop table ACT_HI_OP_LOG;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_INCIDENT') drop table ACT_HI_INCIDENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_JOB_LOG') drop table ACT_HI_JOB_LOG;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_BATCH') drop table ACT_HI_BATCH;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_IDENTITYLINK') drop table ACT_HI_IDENTITYLINK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_EXT_TASK_LOG') drop table ACT_HI_EXT_TASK_LOG;
