/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
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
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dantecloud.feign.autoconfigure.extension;

import cn.herodotus.dante.core.constant.HerodotusHeaders;
import cn.herodotus.dantecloud.feign.autoconfigure.annotation.Inner;
import feign.MethodMetadata;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.convert.ConversionService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * <p>Description: 自定义 Inner 处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/31 11:28
 */
public class FeignInnerContract extends SpringMvcContract {

    private static final Logger log = LoggerFactory.getLogger(FeignInnerContract.class);

    public FeignInnerContract() {
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors) {
        super(annotatedParameterProcessors);
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService) {
        super(annotatedParameterProcessors, conversionService);
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService, FeignClientProperties feignClientProperties) {
        super(annotatedParameterProcessors, conversionService, feignClientProperties);
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {

        if (methodAnnotation instanceof Inner) {
            Inner inner = findMergedAnnotation(method, Inner.class);
            if (ObjectUtils.isNotEmpty(inner)) {
                log.debug("[Herodotus] |- Found inner annotation on Feign interface, add header!");
                data.template().header(HerodotusHeaders.X_HERODOTUS_FROM_IN, "true");
            }
        }

        super.processAnnotationOnMethod(data, methodAnnotation, method);
    }
}
