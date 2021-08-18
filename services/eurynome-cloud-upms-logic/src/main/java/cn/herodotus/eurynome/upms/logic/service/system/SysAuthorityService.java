/*
 * Copyright 2019-2019 the original author or authors.
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-upms-logic
 * File Name: SysAuthorityService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午11:00
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.common.constant.enums.AuthorityType;
import cn.herodotus.eurynome.crud.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysSecurityAttribute;
import cn.herodotus.eurynome.upms.logic.repository.system.SysAuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

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
        log.debug("[Eurynome] |- SysAuthority Service batchSaveOrUpdate.");
        return sysAuthorityRepository.saveAllAndFlush(sysAuthorities);
    }

    public List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType) {
        List<SysAuthority> sysAuthorities = sysAuthorityRepository.findAllByAuthorityType(authorityType);
        log.debug("[Eurynome] |- SysAuthority Service findAllByAuthorityType.");
        return sysAuthorities;
    }

    /**
     * 查找SysSecurityAttribute中不存在的SysAuthority
     *
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

        log.debug("[Eurynome] |- SysAuthority Service findAllocatable.");
        return this.findAll(specification);
    }
}
