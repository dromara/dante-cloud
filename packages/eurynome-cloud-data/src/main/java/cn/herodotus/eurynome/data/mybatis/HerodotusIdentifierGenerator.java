/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
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
 * File Name: HerodotusIdentifierGenerator.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 12:26:01
 */

package cn.herodotus.eurynome.data.mybatis;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 自定义Mybatis Plus ID 生成器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 12:26
 */
@Component
public class HerodotusIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        // 采用雪花算法获取id,时间回拨会存在重复,这里用随机数来减少重复的概率
        final Snowflake snowflake = IdUtil.getSnowflake(1, (int) (Math.random() * 20 + 1));
        return snowflake.nextId();
    }
}
