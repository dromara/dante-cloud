/*
 * Copyright 2019-2020 the original author or authors.
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
 * Module Name: luban-cloud-bpmn-logic
 * File Name: FlowableUserHelper.java
 * Author: gengwei.zheng
 * Date: 2020/1/26 下午3:52
 * LastModified: 2020/1/20 下午4:25
 */

package cn.herodotus.eurynome.bpmn.logic.helper;

import cn.herodotus.eurynome.component.security.core.HerodotusRole;
import cn.herodotus.eurynome.component.security.core.userdetails.HerodotusUserDetails;
import org.apache.commons.collections4.CollectionUtils;
import org.flowable.ui.common.model.RemoteGroup;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.DefaultPrivileges;
import org.flowable.ui.common.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 15:21
 */
public class FlowableUserHelper {

    public static void setAuthenticatedUser(HerodotusUserDetails herodotusUserDetails) {
        SecurityUtils.assumeUser(convertArtisanUserDetailsToFlowableUser(herodotusUserDetails));
    }

    public static RemoteUser convertArtisanUserDetailsToFlowableUser(HerodotusUserDetails herodotusUserDetails) {
        RemoteUser remoteUser = new RemoteUser();
        remoteUser.setId(herodotusUserDetails.getUserId());
        remoteUser.setFirstName(herodotusUserDetails.getUsername());
        remoteUser.setLastName(herodotusUserDetails.getUsername());
        remoteUser.setDisplayName(herodotusUserDetails.getNickName());
        remoteUser.setFullName(herodotusUserDetails.getUsername());
        remoteUser.setGroups(convertArtisanRolesToFlowableGroups(herodotusUserDetails.getRoles()));
        remoteUser.setPrivileges(getDefaultPrivileges());
        return remoteUser;
    }

    public static List<RemoteGroup> convertArtisanRolesToFlowableGroups(List<HerodotusRole> herodotusRoles) {
        if (CollectionUtils.isNotEmpty(herodotusRoles)) {
            return herodotusRoles.stream().map(FlowableUserHelper::convertArtisanRoleToFlowableGroup).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static RemoteGroup convertArtisanRoleToFlowableGroup(HerodotusRole herodotusRole) {
        RemoteGroup remoteGroup = new RemoteGroup();
        remoteGroup.setId(herodotusRole.getRoleId());
        remoteGroup.setName(herodotusRole.getRoleName());
        remoteGroup.setType(herodotusRole.getRoleCode());
        return remoteGroup;
    }

    private static List<String> getDefaultPrivileges() {
        List<String> privileges = new ArrayList<>();
        privileges.add(DefaultPrivileges.ACCESS_MODELER);
        privileges.add(DefaultPrivileges.ACCESS_IDM);
        privileges.add(DefaultPrivileges.ACCESS_ADMIN);
        privileges.add(DefaultPrivileges.ACCESS_TASK);
        privileges.add(DefaultPrivileges.ACCESS_REST_API);

        return privileges;
    }

    public static UserRepresentation createFlowableDefaultUser() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId("admin");
        userRepresentation.setEmail("admin@flowable.org");
        userRepresentation.setFullName("Administrator");
        userRepresentation.setLastName("Administrator");
        userRepresentation.setFirstName("Administrator");
        userRepresentation.setPrivileges(FlowableUserHelper.getDefaultPrivileges());
        return userRepresentation;
    }
}
