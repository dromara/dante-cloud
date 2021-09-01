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
 * File Name: MybatisPlusConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 12:24:01
 */

package cn.herodotus.eurynome.data.configuration;

import cn.herodotus.eurynome.common.constant.magic.PropertyConstants;
import cn.herodotus.eurynome.data.mybatis.HerodotusIdentifierGenerator;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Mybatis Plus 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 12:24
 */
@Configuration
public class MybatisPlusConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MybatisPlusConfiguration.class);

    @Value(PropertyConstants.ANNOTATION_SQL_INIT_PLATFORM)
    private String platform;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Plugin [Herodotus Mybatis Plus] Auto Configure.");
    }

    private DbType parseDbType() {
        if (StringUtils.isNotBlank(platform)) {
            DbType type = DbType.getDbType(platform);
            if (ObjectUtils.isNotEmpty(type)) {
                return type;
            }
        }

        return DbType.POSTGRE_SQL;
    }

    /**
     * 防止 修改与删除时对全表进行操作
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(parseDbType()));
        log.trace("[Eurynome] |- Bean [Mybatis Plus Interceptor] Auto Configure.");
        return mybatisPlusInterceptor;
    }

    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        log.trace("[Eurynome] |- Bean [Block Attack Inner Interceptor] Auto Configure.");
        return blockAttackInnerInterceptor;
    }

    @Bean
    public IdentifierGenerator identifierGenerator() {
        HerodotusIdentifierGenerator herodotusIdentifierGenerator = new HerodotusIdentifierGenerator();
        log.trace("[Eurynome] |- Bean [Herodotus Identifier Generator] Auto Configure.");
        return herodotusIdentifierGenerator;
    }
}
