/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.module.upms.logic.service.assistant;

import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.eurynome.module.upms.logic.enums.Gender;
import cn.herodotus.eurynome.module.upms.logic.enums.Identity;
import cn.herodotus.eurynome.module.upms.logic.enums.OrganizationCategory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 常量服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/25 15:37
 */
@Service
public class ConstantsService {

    private static final List<Map<String, Object>> STATUS_ENUM = DataItemStatus.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> GENDER_ENUM = Gender.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> IDENTITY_ENUM = Identity.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> ORGANIZATION_CATEGORY_ENUM = OrganizationCategory.getPreprocessedJsonStructure();

    public Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", STATUS_ENUM);
        map.put("gender", GENDER_ENUM);
        map.put("identity", IDENTITY_ENUM);
        map.put("organizationCategory", ORGANIZATION_CATEGORY_ENUM);
        return map;
    }
}
