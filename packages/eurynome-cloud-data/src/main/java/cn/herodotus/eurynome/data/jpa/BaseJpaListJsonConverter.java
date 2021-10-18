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
 * File Name: BaseJpaListJsonConverter.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.data.jpa;

import cn.herodotus.eurynome.assistant.definition.entity.AbstractEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Description : Jpa JSON字符串字段与List自动转换 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/31 11:11
 */
public abstract class BaseJpaListJsonConverter<T extends AbstractEntity> implements AttributeConverter<List<T>, String> {

    @Override
    public String convertToDatabaseColumn(List<T> ts) {
        return JSON.toJSONString(ts);
    }

    @Override
    public List<T> convertToEntityAttribute(String s) {
        List<T> result = JSON.parseObject(s, new TypeReference<List<T>>() {});
        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }
}
