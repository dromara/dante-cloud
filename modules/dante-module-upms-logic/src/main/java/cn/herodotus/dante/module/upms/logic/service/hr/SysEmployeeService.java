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

package cn.herodotus.dante.module.upms.logic.service.hr;

import cn.herodotus.dante.module.upms.logic.dto.AllocatableDeploy;
import cn.herodotus.dante.module.upms.logic.dto.AllocatableRemove;
import cn.herodotus.dante.module.upms.logic.entity.hr.SysDepartment;
import cn.herodotus.dante.module.upms.logic.entity.hr.SysEmployee;
import cn.herodotus.dante.module.upms.logic.entity.hr.SysOwnership;
import cn.herodotus.dante.module.upms.logic.entity.system.SysUser;
import cn.herodotus.dante.module.upms.logic.enums.Gender;
import cn.herodotus.dante.module.upms.logic.enums.Identity;
import cn.herodotus.dante.module.upms.logic.repository.hr.SysEmployeeRepository;
import cn.herodotus.dante.module.upms.logic.service.system.SysUserService;
import cn.herodotus.engine.assistant.core.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
import org.apache.commons.collections4.CollectionUtils;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 人员 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:54
 */
@Service
public class SysEmployeeService extends BaseLayeredService<SysEmployee, String> {

    private static final Logger log = LoggerFactory.getLogger(SysEmployeeService.class);

    private final SysEmployeeRepository sysEmployeeRepository;
    private final SysOwnershipService sysOwnershipService;
    private final SysUserService sysUserService;

    @Autowired
    public SysEmployeeService(SysEmployeeRepository sysEmployeeRepository, SysOwnershipService sysOwnershipService, SysUserService sysUserService) {
        this.sysEmployeeRepository = sysEmployeeRepository;
        this.sysOwnershipService = sysOwnershipService;
        this.sysUserService = sysUserService;
    }

    @Override
    public BaseRepository<SysEmployee, String> getRepository() {
        return sysEmployeeRepository;
    }

    /**
     * 根据动态条件分页查询人员信息
     * <p>
     * Predicate类：一个简单或复杂的谓词类型，用来拼接条件。
     * Criteria 查询是以元模型的概念为基础的，元模型是为具体持久化单元的受管实体定义的，这些实体可以是实体类，嵌入类或者映射的父类。
     * Root接口：代表Criteria查询的根对象，能够提供查询用到的数据库字段。
     * CriteriaQuery接口：代表一个specific的顶层查询对象，用来执行最后的操作，它包含着查询的各个部分，比如：select 、from、where、group by、order by等注意：CriteriaQuery对象只对实体类型或嵌入式类型的Criteria查询起作用。
     * CriteriaBuilder接口：表示具体的比较条件。
     *
     * @param pageNumber        当前页码
     * @param pageSize          每页显示数据条目
     * @param employeeName      人员姓名
     * @param mobilePhoneNumber 手机号码
     * @param officePhoneNumber 办公电话
     * @param email             电子邮件
     * @param pkiEmail          PKI电子邮件
     * @param gender            性别 {@link Gender}
     * @param identity          身份 {@link Identity}
     * @return 人员你的分页数据
     */
    public Page<SysEmployee> findByCondition(int pageNumber, int pageSize, String employeeName, String mobilePhoneNumber, String officePhoneNumber, String email, String pkiEmail, Gender gender, Identity identity) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysEmployee> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(employeeName)) {
                predicates.add(criteriaBuilder.like(root.get("employeeName"), like(employeeName)));
            }

            if (StringUtils.isNotBlank(mobilePhoneNumber)) {
                predicates.add(criteriaBuilder.like(root.get("mobilePhoneNumber"), like(mobilePhoneNumber)));
            }

            if (StringUtils.isNotBlank(officePhoneNumber)) {
                predicates.add(criteriaBuilder.like(root.get("officePhoneNumber"), like(officePhoneNumber)));
            }

            if (StringUtils.isNotBlank(email)) {
                predicates.add(criteriaBuilder.like(root.get("email"), like(email)));
            }

            if (StringUtils.isNotBlank(pkiEmail)) {
                predicates.add(criteriaBuilder.like(root.get("pkiEmail"), like(pkiEmail)));
            }

            if (ObjectUtils.isNotEmpty(gender)) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }

            if (ObjectUtils.isNotEmpty(identity)) {
                predicates.add(criteriaBuilder.equal(root.get("identity"), identity));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Herodotus] |- SysEmployee Service findByCondition.");
        return this.findByPage(specification, pageable);
    }

    /**
     * 根据单位ID和部门ID，查找当前部门下未被设置人员归属的人员。
     * <p>
     * 业务描述：
     * 目前采用人事归属的方式设置单位、部门、人员的关系，而不采用传统的：用人员关联单位ID、部门ID的方式进行机构人员关系的创建。
     * 人事归属的方式解决了什么问题，主要的考虑是：
     * （1）使用人事归属的方式，可以支持一般的组织机构、党组机构、团组机构等多种机构类型，而人员始终就是一套人员。
     * （2）传统MIS中用人员关联单位ID、部门ID的方式创建关系，只能支持一种组织机构。如果同一套系统或者从灵活度的角度要支持多种机构，那么只能通过每种机构关系创建一个人员信息，而每个人员信息又对应一个用户。同一个人员有多套信息和多个用户信息，导致维护起来非常混乱。
     * （3）这种结构与Camunda或者Flowable、Activity等工作流结构对应，便于信息的同步与管理。
     * organization --> tenant
     * department   --> group
     * employee     --> user
     * <p>
     * 当然有更好的设计方式，可以告诉我进行修改
     * <p>
     * 实现描述：
     * 因为涉及到Page问题，所以采用Specification的方式，如果不涉及分页，按照如下的优先级和方式可能更好。
     * （1） 在repository中，定义JPQL
     * （2） 在repository中，定义原生SQL
     * （3） 在数据库中编写SQL View，然后进行ORM映射
     *
     * @param pageNumber     当前页码
     * @param pageSize       每页显示数据条目
     * @param organizationId 单位ID：如果所有部门的ID都是唯一的，那么单位ID可以为空
     * @param departmentId   部门ID
     * @return 当前部门下可以进行人事归属设置的人员分页信息，排除已设置过的人员信息
     */
    public Page<SysEmployee> findAllocatable(int pageNumber, int pageSize, String organizationId, String departmentId, String employeeName, String mobilePhoneNumber, String email, Gender gender, Identity identity) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // exist sql 结构示例： SELECT * FROM article WHERE EXISTS (SELECT * FROM user WHERE article.uid = user.uid)
        Specification<SysEmployee> specification = (root, criteriaQuery, criteriaBuilder) -> {

            // 构造Not Exist子查询
            Subquery<SysOwnership> subQuery = criteriaQuery.subquery(SysOwnership.class);
            Root<SysOwnership> subRoot = subQuery.from(SysOwnership.class);

            // 构造Not Exist 子查询的where条件
            List<Predicate> subPredicates = new ArrayList<>();
            subPredicates.add(criteriaBuilder.equal(subRoot.get("employeeId"), root.get("employeeId")));
            if (StringUtils.isNotBlank(organizationId)) {
                subPredicates.add(criteriaBuilder.equal(subRoot.get("organizationId"), organizationId));
            }
            if (StringUtils.isNotBlank(departmentId)) {
                subPredicates.add(criteriaBuilder.equal(subRoot.get("departmentId"), departmentId));
            }

            Predicate[] subPredicateArray = new Predicate[subPredicates.size()];
            subQuery.where(criteriaBuilder.and(subPredicates.toArray(subPredicateArray)));

            // 构造完整的子查询语句
            //这句话不加会报错，因为他不知道你子查询要查出什么字段。就是上面示例中的子查询中的“select *”的作用
            subQuery.select(subRoot.get("ownershipId"));

            List<Predicate> rootPredicates = new ArrayList<>();
            rootPredicates.add(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
            if (StringUtils.isNotBlank(employeeName)) {
                rootPredicates.add(criteriaBuilder.like(root.get("employeeName"), like(employeeName)));
            }

            if (StringUtils.isNotBlank(mobilePhoneNumber)) {
                rootPredicates.add(criteriaBuilder.like(root.get("mobilePhoneNumber"), like(mobilePhoneNumber)));
            }

            if (StringUtils.isNotBlank(email)) {
                rootPredicates.add(criteriaBuilder.like(root.get("email"), like(email)));
            }

            if (ObjectUtils.isNotEmpty(gender)) {
                rootPredicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }

            if (ObjectUtils.isNotEmpty(identity)) {
                rootPredicates.add(criteriaBuilder.equal(root.get("identity"), identity));
            }

            Predicate[] rootPredicateArray = new Predicate[rootPredicates.size()];
            // 构造完整SQL
            // 正确的结构参考：select * from sys_employee e where not exists (select ownership_id from sys_ownership o where o.employee_id = e.employee_id and o.organization = 1? and o.department_id = 2?) and XXXXXX.... limit ?
            criteriaQuery.where(criteriaBuilder.and(rootPredicates.toArray(rootPredicateArray)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Herodotus] |- SysEmployee Service findAllocatable.");
        return this.findByPage(specification, pageable);
    }

    public Page<SysEmployee> findByDepartmentId(int pageNumber, int pageSize, String departmentId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysEmployee> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Join<SysEmployee, SysDepartment> join = root.join("departments", JoinType.LEFT);
            return criteriaBuilder.equal(join.get("departmentId"), departmentId);
        };

        log.debug("[Herodotus] |- SysEmployee Service findAllocatable.");
        return this.findByPage(specification, pageable);
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public SysEmployee authorize(String employeeId) {
        SysEmployee sysEmployee = this.findById(employeeId);
        SysUser sysUser = sysUserService.register(sysEmployee);
        if (ObjectUtils.isNotEmpty(sysUser) && ObjectUtils.isNotEmpty(sysEmployee)) {
            sysUser.setEmployee(sysEmployee);
            SysUser newUser = sysUserService.saveOrUpdate(sysUser);
            if (ObjectUtils.isNotEmpty(newUser)) {
                log.debug("[Herodotus] |- SysEmployee Service authorize.");
                return newUser.getEmployee();
            }
        }

        return null;
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Override
    public void deleteById(String employeeId) {
        sysOwnershipService.deleteByEmployeeId(employeeId);
        super.deleteById(employeeId);
        log.debug("[Herodotus] |- SysEmployee Service deleteById.");
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public boolean deployAllocatable(AllocatableDeploy allocatableDeploy) {
        List<SysEmployee> sysEmployees = allocatableDeploy.getAllocatable();
        List<SysOwnership> sysOwnerships = allocatableDeploy.getOwnerships();
        if (CollectionUtils.isNotEmpty(sysEmployees) && CollectionUtils.isNotEmpty(sysOwnerships)) {
            List<SysEmployee> result = sysEmployeeRepository.saveAllAndFlush(sysEmployees);
            if (CollectionUtils.isNotEmpty(result)) {
                sysOwnershipService.saveAll(sysOwnerships);
                log.debug("[Herodotus] |- SysEmployee Service deleteById.");
                return true;
            }
        }

        return false;
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public boolean removeAllocatable(AllocatableRemove allocatableRemove) {
        SysEmployee sysEmployee = super.findById(allocatableRemove.getEmployeeId());
        if (ObjectUtils.isNotEmpty(sysEmployee)) {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentId(allocatableRemove.getDepartmentId());
            sysEmployee.getDepartments().remove(sysDepartment);
            SysEmployee result = super.save(sysEmployee);
            if (ObjectUtils.isNotEmpty(result)) {
                sysOwnershipService.delete(allocatableRemove.getOrganizationId(), allocatableRemove.getDepartmentId(), allocatableRemove.getEmployeeId());
                return true;
            }
        }

        return false;
    }

    public SysEmployee findByEmployeeName(String employeeName) {
        SysEmployee sysEmployee = sysEmployeeRepository.findByEmployeeName(employeeName);
        log.debug("[Herodotus] |- SysEmployee Service findByEmployeName.");
        return sysEmployee;
    }

}
