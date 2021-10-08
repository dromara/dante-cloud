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
 * Module Name: eurynome-cloud-common
 * File Name: BeanUtils.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * <p> Description : 自定义的Bean工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/11 13:36
 */
@Slf4j
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    /**
     * 方法说明：map转化为对象
     * 
     * @param map
     * @param t
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> t) {
        try {
            T instance = t.newInstance();
            populate(instance, map);
            return instance;
        } catch (Exception e) {
            log.error("[Herodotus] |- BeanUtils mapToBean error！", e);
            return null;
        }
    }
}
