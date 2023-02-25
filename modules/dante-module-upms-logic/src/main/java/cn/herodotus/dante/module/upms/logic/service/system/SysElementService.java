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

import cn.herodotus.dante.module.upms.logic.entity.system.SysElement;
import cn.herodotus.dante.module.upms.logic.entity.system.SysRole;
import cn.herodotus.dante.module.upms.logic.repository.system.SysElementRepository;
import cn.herodotus.engine.assistant.core.component.router.BaseMeta;
import cn.herodotus.engine.assistant.core.component.router.ChildMeta;
import cn.herodotus.engine.assistant.core.component.router.ParentMeta;
import cn.herodotus.engine.assistant.core.component.router.RootMeta;
import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
import cn.hutool.core.lang.tree.TreeNode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: SysMenuService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/14 15:55
 */
@Service
public class SysElementService extends BaseLayeredService<SysElement, String> {

    private static final Logger log = LoggerFactory.getLogger(SysElementService.class);

    private final SysElementRepository sysElementRepository;

    @Autowired
    public SysElementService(SysElementRepository sysElementRepository) {
        this.sysElementRepository = sysElementRepository;
    }

    @Override
    public BaseRepository<SysElement, String> getRepository() {
        return sysElementRepository;
    }

    public Page<SysElement> findByCondition(int pageNumber, int pageSize, String path, String title) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysElement> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(path)) {
                predicates.add(criteriaBuilder.like(root.get("path"), like(path)));
            }

            if (ObjectUtils.isNotEmpty(title)) {
                predicates.add(criteriaBuilder.like(root.get("title"), like(title)));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Herodotus] |- SysElementService Service findByCondition.");
        return this.findByPage(specification, pageable);
    }

    public SysElement authorize(String elementId, String[] roles) {

        Set<SysRole> sysRoles = new HashSet<>();
        for (String role : roles) {
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(role);
            sysRoles.add(sysRole);
        }

        SysElement sysElement = findById(elementId);
        sysElement.setRoles(sysRoles);

        log.debug("[Herodotus] |- SysElementService Service authorize.");
        return saveOrUpdate(sysElement);
    }

    private String convertParentId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            return BaseConstants.DEFAULT_TREE_ROOT_ID;
        } else {
            return parentId;
        }
    }

    private void setBaseMeta(SysElement sysMenu, BaseMeta meta) {
        meta.setIcon(sysMenu.getIcon());
        meta.setTitle(sysMenu.getTitle());
        meta.setIgnoreAuth(sysMenu.getIgnoreAuth());
        meta.setNotKeepAlive(sysMenu.getNotKeepAlive());
    }

    private Map<String, Object> getExtra(SysElement sysMenu) {
        Map<String, Object> extra = new HashMap<>();

        if (StringUtils.isBlank(sysMenu.getParentId())) {
            RootMeta meta = new RootMeta();
            meta.setSort(sysMenu.getRanking());
            setBaseMeta(sysMenu, meta);
            extra.put("meta", meta);
            extra.put("redirect", sysMenu.getRedirect());
        } else {
            if (BooleanUtils.isTrue(sysMenu.getHaveChild())) {
                ParentMeta meta = new ParentMeta();
                meta.setHideAllChild(sysMenu.getHideAllChild());
                setBaseMeta(sysMenu, meta);
                extra.put("meta", meta);
                extra.put("componentName", sysMenu.getName());
            } else {
                ChildMeta meta = new ChildMeta();
                meta.setDetailContent(sysMenu.getDetailContent());
                setBaseMeta(sysMenu, meta);
                extra.put("meta", meta);
                extra.put("componentName", sysMenu.getName());
            }
        }
        extra.put("componentPath", sysMenu.getComponent());

        Set<SysRole> sysRoles = sysMenu.getRoles();
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            List<String> roles = sysRoles.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
            extra.put("roles", roles);
        } else {
            extra.put("roles", new ArrayList<>());
        }

        return extra;
    }

    public TreeNode<String> convertToTreeNode(SysElement sysMenu) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysMenu.getElementId());
        treeNode.setName(sysMenu.getPath());
        treeNode.setWeight(sysMenu.getRanking());
        treeNode.setParentId(convertParentId(sysMenu.getParentId()));
        treeNode.setExtra(getExtra(sysMenu));
        return treeNode;
    }
}
