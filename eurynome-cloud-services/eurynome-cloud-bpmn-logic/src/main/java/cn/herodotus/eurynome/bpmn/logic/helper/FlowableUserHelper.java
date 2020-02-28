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

import cn.herodotus.eurynome.component.security.domain.ArtisanRole;
import cn.herodotus.eurynome.component.security.domain.ArtisanUserDetails;
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

    public static void setAuthenticatedUser(ArtisanUserDetails artisanUserDetails) {
        SecurityUtils.assumeUser(convertArtisanUserDetailsToFlowableUser(artisanUserDetails));
    }

    public static RemoteUser convertArtisanUserDetailsToFlowableUser(ArtisanUserDetails artisanUserDetails) {
        RemoteUser remoteUser = new RemoteUser();
        remoteUser.setId(artisanUserDetails.getUserId());
        remoteUser.setFirstName(artisanUserDetails.getUsername());
        remoteUser.setLastName(artisanUserDetails.getUsername());
        remoteUser.setDisplayName(artisanUserDetails.getNickName());
        remoteUser.setFullName(artisanUserDetails.getUsername());
        remoteUser.setGroups(convertArtisanRolesToFlowableGroups(artisanUserDetails.getRoles()));
        remoteUser.setPrivileges(getDefaultPrivileges());
        return remoteUser;
    }

    public static List<RemoteGroup> convertArtisanRolesToFlowableGroups(List<ArtisanRole> artisanRoles) {
        if (CollectionUtils.isNotEmpty(artisanRoles)) {
            return artisanRoles.stream().map(FlowableUserHelper::convertArtisanRoleToFlowableGroup).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static RemoteGroup convertArtisanRoleToFlowableGroup(ArtisanRole artisanRole) {
        RemoteGroup remoteGroup = new RemoteGroup();
        remoteGroup.setId(artisanRole.getRoleId());
        remoteGroup.setName(artisanRole.getRoleName());
        remoteGroup.setType(artisanRole.getRoleCode());
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
