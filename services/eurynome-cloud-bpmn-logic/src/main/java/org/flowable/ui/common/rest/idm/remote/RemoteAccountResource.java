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
 * Project Name: hades-multi-module
 * Module Name: hades-bpmn
 * File Name: RemoteAccountResource.java
 * Author: gengwei.zheng
 * Date: 2019/11/2 下午2:20
 * LastModified: 2019/10/30 下午9:52
 */

package org.flowable.ui.common.rest.idm.remote;

import cn.herodotus.eurynome.bpmn.logic.helper.FlowableUserHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.flowable.idm.api.User;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.model.RemoteGroup;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.NotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : gengwei.zheng
 * @description : TODO
 * @date : 2019/10/28 18:10
 */
@RestController
@RequestMapping("/app")
public class RemoteAccountResource {

    /**
     * GET /rest/account -> get the current user.
     */
    @RequestMapping(value = "/modeler/account", method = RequestMethod.GET, produces = "application/json")
    public UserRepresentation getAccount() {
        UserRepresentation userRepresentation = null;
        User currentUser = SecurityUtils.getCurrentUserObject();
        if (ObjectUtils.isNotEmpty(currentUser) && currentUser instanceof RemoteUser) {
            RemoteUser remoteUser = (RemoteUser) SecurityUtils.getCurrentUserObject();

            userRepresentation = new UserRepresentation(remoteUser);

            if (remoteUser.getGroups() != null && remoteUser.getGroups().size() > 0) {
                List<GroupRepresentation> groups = new ArrayList<>();
                for (RemoteGroup remoteGroup : remoteUser.getGroups()) {
                    groups.add(new GroupRepresentation(remoteGroup));
                }
                userRepresentation.setGroups(groups);
            }

            if (remoteUser.getPrivileges() != null && remoteUser.getPrivileges().size() > 0) {
                userRepresentation.setPrivileges(remoteUser.getPrivileges());
            }
        } else {
            userRepresentation = FlowableUserHelper.createFlowableDefaultUser();
        }

        if (userRepresentation != null) {
            return userRepresentation;
        } else {
            throw new NotFoundException();
        }
    }


}
