/*
 * Copyright 2019-2019 the original author or authors.
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-data
 * File Name: AbstractArrayType.java
 * Author: gengwei.zheng
 * Date: 2019/11/14 下午5:23
 * LastModified: 2019/11/7 下午2:27
 */

package cn.herodotus.eurynome.data.jpa.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import java.sql.*;

/** 
 * <p>Description: TODO </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/15 10:34
 */
@SuppressWarnings("unchecked")
public abstract class AbstractArrayType<T extends Serializable> extends AbstractUserType {

    protected static final int[] SQL_TYPES = { Types.ARRAY };

    @Override
    public final int[] sqlTypes() {
        return SQL_TYPES;
    }

    protected abstract Object safeReturnedObject();

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        Array array = resultSet.getArray(strings[0]);
        T[] typeArray = (T[]) array.getArray();
        if (typeArray == null) {
            return safeReturnedObject();
        }

        return typeArray;
    }

    protected abstract String dbRealTypeName();

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        Connection connection = preparedStatement.getConnection();
        T[] typeArray = (T[]) value;
        Array array = connection.createArrayOf(dbRealTypeName(), typeArray);
        if (null != array) {
            preparedStatement.setArray(index, array);
        } else {
            preparedStatement.setNull(index, SQL_TYPES[0]);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value == null ? null : ((T[]) value).clone();
    }

}
