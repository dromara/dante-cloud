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
 * File Name: TextArrayType.java
 * Author: gengwei.zheng
 * Date: 2019/11/14 下午5:23
 * LastModified: 2019/11/7 下午2:27
 */

package cn.herodotus.eurynome.data.jpa.hibernate.postgresql;

import cn.herodotus.eurynome.data.jpa.hibernate.AbstractArrayType;
import org.hibernate.HibernateException;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

/**
 * <p>Description: 自定义文本数组类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/15 10:34
 */
public class TextArrayType extends AbstractArrayType<String> implements Serializable {

    @Override
    public Class returnedClass() {
        return String[].class;
    }

    @Override
    protected Object safeReturnedObject() {
        return new String[]{};
    }

    @Override
    protected String dbRealTypeName() {
        return "TEXT";
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null) {
            return 0;
        }

        return x.hashCode();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

}
