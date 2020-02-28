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
 * File Name: AbstractUserType.java
 * Author: gengwei.zheng
 * Date: 2019/11/14 下午5:23
 * LastModified: 2019/11/7 下午2:27
 */

package cn.herodotus.eurynome.component.data.jpa.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/15 10:32
 */
public abstract class AbstractUserType implements UserType {

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) this.deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }
}
