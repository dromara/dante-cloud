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

package org.dromara.dante.module.metadata.converter;

import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: List<SysInterface> 转 List<SysAttribute> 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/23 22:59
 */
public class SysInterfacesToSysAttributesConverter implements Converter<List<SysInterface>, List<SysAttribute>> {
    @Override
    public List<SysAttribute> convert(List<SysInterface> sysInterfaces) {
        if (CollectionUtils.isNotEmpty(sysInterfaces)) {
            return sysInterfaces.stream().map(this::convert).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private SysAttribute convert(SysInterface sysInterface) {
        SysAttribute sysAttribute = new SysAttribute();
        sysAttribute.setAttributeId(sysInterface.getInterfaceId());
        sysAttribute.setAttributeCode(sysInterface.getInterfaceCode());
        sysAttribute.setRequestMethod(sysInterface.getRequestMethod());
        sysAttribute.setServiceId(sysInterface.getServiceId());
        sysAttribute.setClassName(sysInterface.getClassName());
        sysAttribute.setMethodName(sysInterface.getMethodName());
        sysAttribute.setUrl(sysInterface.getUrl());
        sysAttribute.setStatus(sysInterface.getStatus());
        sysAttribute.setReserved(sysInterface.getReserved());
        sysAttribute.setDescription(sysInterface.getDescription());
        sysAttribute.setReversion(sysInterface.getReversion());
        sysAttribute.setCreateTime(sysInterface.getCreateTime());
        sysAttribute.setUpdateTime(sysInterface.getUpdateTime());
        sysAttribute.setRanking(sysInterface.getRanking());
        return sysAttribute;
    }

}
