/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
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
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.upms.logic.service.system;

import cn.herodotus.dante.module.upms.logic.entity.system.SysAuthority;
import cn.herodotus.dante.module.upms.logic.entity.system.SysSecurityAttribute;
import cn.herodotus.dante.module.upms.logic.repository.system.SysAuthorityRepository;
import cn.herodotus.engine.assistant.core.enums.AuthorityType;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: SysAuthorityService </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 16:11
 */
@Service
public class SysAuthorityService extends BaseLayeredService<SysAuthority, String> {

    private static final Logger log = LoggerFactory.getLogger(SysAuthorityService.class);

    private final SysAuthorityRepository sysAuthorityRepository;

    @Autowired
    public SysAuthorityService(SysAuthorityRepository sysAuthorityRepository) {
        this.sysAuthorityRepository = sysAuthorityRepository;
    }

    @Override
    public BaseRepository<SysAuthority, String> getRepository() {
        return this.sysAuthorityRepository;
    }

    public List<SysAuthority> batchSaveOrUpdate(List<SysAuthority> sysAuthorities) {
        List<SysAuthority> authorities = sysAuthorityRepository.saveAllAndFlush(sysAuthorities);
        log.debug("[Herodotus] |- SysAuthority Service batchSaveOrUpdate.");
        return authorities;
    }

    public List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType) {
        List<SysAuthority> sysAuthorities = sysAuthorityRepository.findAllByAuthorityType(authorityType);
        log.debug("[Herodotus] |- SysAuthority Service findAllByAuthorityType.");
        return sysAuthorities;
    }

    public List<SysAuthority> storeRequestMappings(Collection<RequestMapping> requestMappings) {
        List<SysAuthority> sysAuthorities = toSysAuthorities(requestMappings);
        return batchSaveOrUpdate(sysAuthorities);
    }

    private List<SysAuthority> toSysAuthorities(Collection<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            return requestMappings.stream().map(this::toSysAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private SysAuthority toSysAuthority(RequestMapping requestMapping) {
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setAuthorityId(requestMapping.getMetadataId());
        sysAuthority.setAuthorityName(requestMapping.getMetadataName());
        sysAuthority.setAuthorityCode(requestMapping.getMetadataCode());
        sysAuthority.setRequestMethod(requestMapping.getRequestMethod());
        sysAuthority.setServiceId(requestMapping.getServiceId());
        sysAuthority.setUrl(requestMapping.getUrl());
        sysAuthority.setParentId(requestMapping.getParentId());
        sysAuthority.setClassName(requestMapping.getClassName());
        sysAuthority.setMethodName(requestMapping.getMethodName());
        return sysAuthority;
    }

    /**
     * 查找SysSecurityAttribute中不存在的SysAuthority
     * @return SysAuthority列表
     */
    public List<SysAuthority> findAllocatable() {

        // exist sql 结构示例： SELECT * FROM article WHERE EXISTS (SELECT * FROM user WHERE article.uid = user.uid)
        Specification<SysAuthority> specification = (root, criteriaQuery, criteriaBuilder) -> {

            // 构造Not Exist子查询
            Subquery<SysSecurityAttribute> subQuery = criteriaQuery.subquery(SysSecurityAttribute.class);
            Root<SysSecurityAttribute> subRoot = subQuery.from(SysSecurityAttribute.class);

            // 构造Not Exist 子查询的where条件
            Predicate subPredicate = criteriaBuilder.equal(subRoot.get("attributeId"), root.get("authorityId"));
            subQuery.where(subPredicate);

            // 构造完整的子查询语句
            //这句话不加会报错，因为他不知道你子查询要查出什么字段。就是上面示例中的子查询中的“select *”的作用
            subQuery.select(subRoot.get("attributeId"));

            // 构造完整SQL
            // 正确的结构参考：SELECT * FROM sys_authority sa WHERE NOT EXISTS ( SELECT * FROM sys_metadata sm WHERE sm.metadata_id = sa.authority_id )
            criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Herodotus] |- SysAuthority Service findAllocatable.");
        return this.findAll(specification);
    }
}
