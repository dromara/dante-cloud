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
 * File Name: NumericArrayType.java
 * Author: gengwei.zheng
 * Date: 2019/11/14 下午5:23
 * LastModified: 2019/11/7 下午2:27
 */

package cn.herodotus.eurynome.component.data.jpa.hibernate.postgresql;


import cn.herodotus.eurynome.component.data.jpa.hibernate.AbstractArrayType;

import java.math.BigDecimal;

/** 
 * <p>Description: 扩展NumericArrayType </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/15 10:33
 */
public class NumericArrayType extends AbstractArrayType<BigDecimal> {

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
}
