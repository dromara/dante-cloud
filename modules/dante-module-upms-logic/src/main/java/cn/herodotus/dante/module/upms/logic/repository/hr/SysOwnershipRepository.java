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

package cn.herodotus.dante.module.upms.logic.repository.hr;

import cn.herodotus.dante.module.upms.logic.entity.hr.SysOwnership;
import cn.herodotus.engine.assistant.core.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Description: 人事归属Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:28
 */
public interface SysOwnershipRepository extends BaseRepository<SysOwnership, String> {

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param organizationId 单位ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.organizationId = :organizationId")
    void deleteByOrganizationId(String organizationId);

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param departmentId 部门ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.departmentId = :departmentId")
    void deleteByDepartmentId(String departmentId);

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param employeeId 人员ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.employeeId = :employeeId")
    void deleteByEmployeeId(String employeeId);

    /**
     * 删除人事归属
     * @param organizationId 单位ID
     * @param departmentId 部门ID
     * @param employeeId 人员ID
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query(value = "delete from SysOwnership o where o.organizationId = :organizationId and o.departmentId = :departmentId and o.employeeId = :employeeId")
    void deleteByOrganizationIdAndDepartmentIdAndEmployeeId(String organizationId, String departmentId, String employeeId);

}
