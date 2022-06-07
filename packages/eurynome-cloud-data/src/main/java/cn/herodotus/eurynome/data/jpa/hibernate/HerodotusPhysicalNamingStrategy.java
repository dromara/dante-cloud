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
 * File Name: HerodotusPhysicalNamingStrategy.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.data.jpa.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * <p>Description: 使用hbm2ddl自动创建表时，默认将@Colume中的信息转换为小写，小写的字段名称与其它系统的字段标准不同（驼峰式，单词首字母大写） 复写原始类，生成符合标准的字段名称。</p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/15 10:34
 */
public class HerodotusPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {

        // Hibernate 默认使用 Identifier.getCanonicalName()的值最为最终的值，text是原始值。如果quoted为true则使用text，否则就进行小写转换。所以此处quoted设置为true。参见具体方法。
        return new Identifier(name.getText(), true);
    }
}
