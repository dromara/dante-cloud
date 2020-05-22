/*
 * Copyright 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-component-data
 * File Name: AbstractRoutingDataSource.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午2:53
 * LastModified: 2020/5/22 下午2:53
 */

package cn.herodotus.eurynome.component.data.datasource.definition;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: AbstractRoutingDataSource </p>
 *
 * <p>Description: 动态获取数据源 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/22 14:53
 */
public abstract class AbstractRoutingDataSource extends AbstractDataSource implements InitializingBean, DisposableBean {

    /**
     * 获取最终数据源
     *
     * @return 数据源
     */
    protected abstract DataSource determineDataSource();

    @Override
    public Connection getConnection() throws SQLException {
        return determineDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineDataSource().getConnection(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineDataSource().isWrapperFor(iface));
    }
}
