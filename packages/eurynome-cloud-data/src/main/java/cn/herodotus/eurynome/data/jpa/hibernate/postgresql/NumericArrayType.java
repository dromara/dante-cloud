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
 * File Name: NumericArrayType.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.data.jpa.hibernate.postgresql;


import cn.herodotus.eurynome.data.jpa.hibernate.AbstractArrayType;
import org.hibernate.HibernateException;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
 * <p>Description: 自定义数字数组类型 </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/15 10:33
 */
public class NumericArrayType extends AbstractArrayType<BigDecimal> implements Serializable {

    @Override
    public Class returnedClass() {
        return BigDecimal[].class;
    }

    @Override
    protected Object safeReturnedObject() {
        return new BigDecimal[]{};
    }

    @Override
    protected String dbRealTypeName() {
        return "NUMERIC";
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
