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
 * Module Name: eurynome-cloud-data
 * File Name: HerodotusPostgreSQLDialect.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.data.jpa.hibernate.postgresql;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

/**
 * <p>Description: 自定义PostgreSQLDialect，增加JSONB等类型支持 </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/15 10:31
 */
public class HerodotusPostgreSQLDialect extends PostgreSQL9Dialect {

    public HerodotusPostgreSQLDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
        this.registerColumnType(Types.ARRAY, "text[]");
    }

    /**
     * 重载getAddForeignKeyConstraintString方法，阻止ddl-auto添加外键关联
     * @param constraintName
     * @param foreignKey
     * @param referencedTable
     * @param primaryKey
     * @param referencesPrimaryKey
     * @return String
     */
    @Override
    public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
        //  设置foreignkey对应的列值可以为空
        return " alter " + foreignKey[0] + " set default null ";
    }
}
