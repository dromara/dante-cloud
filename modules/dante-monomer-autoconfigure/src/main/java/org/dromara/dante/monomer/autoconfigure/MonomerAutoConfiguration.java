/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.monomer.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.dromara.dante.monomer.autoconfigure.processor.MonomerBusBridge;
import org.dromara.dante.monomer.autoconfigure.processor.MonomerCorsFilter;
import org.dromara.dante.rest.servlet.upms.annotation.EnableHerodotusRestServletUpms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: 单体版核心 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/3 17:38
 */
@AutoConfiguration
@EnableHerodotusRestServletUpms
public class MonomerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MonomerAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Auto [Monomer] Configure.");
    }

    @Bean
    public BusBridge busBridge() {
        return new MonomerBusBridge();
    }

    @Bean
    public MonomerCorsFilter monomerCorsFilter() {
        return new MonomerCorsFilter();
    }
}
