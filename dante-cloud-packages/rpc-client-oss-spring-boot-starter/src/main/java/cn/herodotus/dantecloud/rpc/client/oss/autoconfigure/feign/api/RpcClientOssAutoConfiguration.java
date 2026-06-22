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

package cn.herodotus.dantecloud.rpc.client.oss.autoconfigure.feign.api;

import cn.herodotus.dante.autoconfigure.file.FileProperties;
import cn.herodotus.dante.core.support.file.FileTemplate;
import cn.herodotus.dante.core.support.file.JsonSchemaFileManager;
import cn.herodotus.dante.core.support.file.OssPresignedUrlGenerator;
import cn.herodotus.dante.core.support.file.OssTransformer;
import cn.herodotus.dante.spring.condition.ConditionalOnArchitecture;
import cn.herodotus.dante.spring.enums.Architecture;
import cn.herodotus.dantecloud.feign.autoconfigure.annotation.Inner;
import cn.herodotus.dantecloud.rpc.client.oss.autoconfigure.feign.api.feign.FeignOssPresignedUrlGenerator;
import cn.herodotus.dantecloud.rpc.client.oss.autoconfigure.feign.api.feign.api.RemoteOssPresignedUrlService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: OSS 远程访问 RPC 客户端自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/22 22:05
 */
@AutoConfiguration
@ConditionalOnArchitecture(Architecture.DISTRIBUTED)
public class RpcClientOssAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RpcClientOssAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Rpc Client OSS] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(Inner.class)
    @EnableFeignClients(basePackages = {"cn.herodotus.dantecloud.rpc.client.oss.autoconfigure.feign.api.feign.api"})
    static class OssPresignedUrlFeignConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public OssPresignedUrlGenerator feignOssPresignedUrlGenerator(RemoteOssPresignedUrlService remoteOssPresignedUrlService) {
            FeignOssPresignedUrlGenerator generator = new FeignOssPresignedUrlGenerator(remoteOssPresignedUrlService);
            log.debug("[Herodotus] |- Strategy [Feign Oss Presigned Url Generator] Configure.");
            return generator;
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class FileProcessorConfiguration {

        @Bean
        public OssTransformer ossTransformer(OssPresignedUrlGenerator ossPresignedUrlGenerator) {
            RemoteFileTransformer transformer = new RemoteFileTransformer(ossPresignedUrlGenerator);
            log.debug("[Herodotus] |- Strategy [Remote File Transformer] Configure.");
            return transformer;
        }

        /**
         * JsonSchema文件管理器 {@link JsonSchemaFileManager} Bean 定义。
         * <p>
         * 先于 core-autoconfigure 中 DefaultJsonSchemaFileManager 注入，实现 {@link JsonSchemaFileManager} 策略化配置和替换，以支持配置了对象存储环境的代码逻辑。
         *
         * @param ossTransformer 文件传输器 {@link RemoteFileTransformer}
         * @return 对象存储 JsonSchema 文件管理 {@link OssJsonSchemaFileManager}
         */
        @Bean
        public JsonSchemaFileManager jsonSchemaFileManager(FileProperties fileProperties, FileTemplate fileTemplate, OssTransformer ossTransformer) {
            OssJsonSchemaFileManager manager = new OssJsonSchemaFileManager(fileProperties, fileTemplate, ossTransformer);
            log.debug("[Herodotus] |- Strategy [Oss JsonSchema File Manager] Configure.");
            return manager;
        }
    }
}
