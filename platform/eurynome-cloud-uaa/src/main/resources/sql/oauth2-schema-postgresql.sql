/*
 Navicat Premium Data Transfer

 Source Server         : PostgreSQL-LUBAN
 Source Server Type    : PostgreSQL
 Source Server Version : 100010
 Source Host           : localhost:5432
 Source Catalog        : luban_cloud_platform
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100010
 File Encoding         : 65001

 Date: 05/11/2019 11:44:20
*/


-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth_access_token";
CREATE TABLE "public"."oauth_access_token" (
  "token_id" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "token" bytea,
  "authentication_id" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(256) COLLATE "pg_catalog"."default",
  "client_id" varchar(256) COLLATE "pg_catalog"."default",
  "authentication" bytea,
  "refresh_token" varchar(256) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth_approvals";
CREATE TABLE "public"."oauth_approvals" (
  "userId" varchar(256) COLLATE "pg_catalog"."default",
  "clientId" varchar(256) COLLATE "pg_catalog"."default",
  "scope" varchar(256) COLLATE "pg_catalog"."default",
  "status" varchar(10) COLLATE "pg_catalog"."default",
  "expiresAt" timestamp(6),
  "lastModifiedAt" timestamp(6)
)
;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth_client_details";
CREATE TABLE "public"."oauth_client_details" (
  "client_id" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "resource_ids" varchar(256) COLLATE "pg_catalog"."default",
  "client_secret" varchar(256) COLLATE "pg_catalog"."default",
  "scope" varchar(256) COLLATE "pg_catalog"."default",
  "authorized_grant_types" varchar(256) COLLATE "pg_catalog"."default",
  "web_server_redirect_uri" varchar(256) COLLATE "pg_catalog"."default",
  "authorities" varchar(256) COLLATE "pg_catalog"."default",
  "access_token_validity" int4,
  "refresh_token_validity" int4,
  "additional_information" varchar(4096) COLLATE "pg_catalog"."default",
  "autoapprove" varchar(256) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."oauth_client_details"."client_id" IS '客户端ID';
COMMENT ON COLUMN "public"."oauth_client_details"."resource_ids" IS '资源ID集合,多个资源时用逗号(,)分隔';
COMMENT ON COLUMN "public"."oauth_client_details"."client_secret" IS '客户端密匙';
COMMENT ON COLUMN "public"."oauth_client_details"."scope" IS '客户端申请的权限范围';
COMMENT ON COLUMN "public"."oauth_client_details"."authorized_grant_types" IS '客户端支持的grant_type';
COMMENT ON COLUMN "public"."oauth_client_details"."web_server_redirect_uri" IS '重定向URI';
COMMENT ON COLUMN "public"."oauth_client_details"."authorities" IS '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔';
COMMENT ON COLUMN "public"."oauth_client_details"."access_token_validity" IS '访问令牌有效时间值(单位:秒)';
COMMENT ON COLUMN "public"."oauth_client_details"."refresh_token_validity" IS '更新令牌有效时间值(单位:秒)';
COMMENT ON COLUMN "public"."oauth_client_details"."additional_information" IS '预留字段';
COMMENT ON COLUMN "public"."oauth_client_details"."autoapprove" IS '用户是否自动Approval操作';

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth_client_token";
CREATE TABLE "public"."oauth_client_token" (
  "token_id" varchar(256) COLLATE "pg_catalog"."default",
  "token" bytea,
  "authentication_id" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(256) COLLATE "pg_catalog"."default",
  "client_id" varchar(256) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."oauth_client_token"."token_id" IS 'MD5加密的access_token值';
COMMENT ON COLUMN "public"."oauth_client_token"."token" IS 'OAuth2AccessToken.java对象序列化后的二进制数据';
COMMENT ON COLUMN "public"."oauth_client_token"."authentication_id" IS 'MD5加密过的username,client_id,scope';
COMMENT ON COLUMN "public"."oauth_client_token"."user_name" IS '登录的用户名';
COMMENT ON COLUMN "public"."oauth_client_token"."client_id" IS '客户端ID';

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth_code";
CREATE TABLE "public"."oauth_code" (
  "code" varchar(256) COLLATE "pg_catalog"."default",
  "authentication" bytea
)
;
COMMENT ON COLUMN "public"."oauth_code"."code" IS '授权码(未加密)';
COMMENT ON COLUMN "public"."oauth_code"."authentication" IS 'AuthorizationRequestHolder.java对象序列化后的二进制数据';

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth_refresh_token";
CREATE TABLE "public"."oauth_refresh_token" (
  "token_id" varchar(256) COLLATE "pg_catalog"."default",
  "token" bytea,
  "authentication" bytea
)
;

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
 * Module Name: eurynome-cloud-uaa
 * File Name: oauth2-schema-postgresql.sql
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

-- ----------------------------
-- Indexes structure for table oauth_access_token
-- ----------------------------
CREATE INDEX "oauth_access_token_id_idx" ON "public"."oauth_access_token" USING btree (
  "authentication_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table oauth_access_token
-- ----------------------------
ALTER TABLE "public"."oauth_access_token" ADD CONSTRAINT "oauth_access_token_pkey" PRIMARY KEY ("authentication_id");

-- ----------------------------
-- Indexes structure for table oauth_client_details
-- ----------------------------
CREATE INDEX "oauth_client_details_id_idx" ON "public"."oauth_client_details" USING btree (
  "client_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table oauth_client_details
-- ----------------------------
ALTER TABLE "public"."oauth_client_details" ADD CONSTRAINT "oauth_client_details_pkey" PRIMARY KEY ("client_id");

-- ----------------------------
-- Indexes structure for table oauth_client_token
-- ----------------------------
CREATE INDEX "oauth_client_token_id_idx" ON "public"."oauth_client_token" USING btree (
  "authentication_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table oauth_client_token
-- ----------------------------
ALTER TABLE "public"."oauth_client_token" ADD CONSTRAINT "oauth_client_token_pkey" PRIMARY KEY ("authentication_id");
