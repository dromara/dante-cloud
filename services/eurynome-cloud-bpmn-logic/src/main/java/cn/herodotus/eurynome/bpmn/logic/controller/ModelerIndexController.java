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
 * Module Name: luban-cloud-bpmn-modeler
 * File Name: IndexController.java
 * Author: gengwei.zheng
 * Date: 2020/1/18 上午8:29
 * LastModified: 2020/1/18 上午8:29
 */

package cn.herodotus.eurynome.bpmn.logic.controller;

import cn.herodotus.eurynome.bpmn.logic.helper.FlowableUserHelper;
import cn.herodotus.eurynome.security.core.userdetails.HerodotusUserDetails;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p> Description : 考虑为了和其它系统整合，增加一层连接。否则一访问：“/”就会跳转到编辑器。 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/18 8:29
 */
@Slf4j
@Controller
public class ModelerIndexController {

    /**
     * Angular SPA 单页面应用和MVC控制器的整合，会有一个疑问，SPA中点击链接的跳转，是经过SPA的路由，还是直接被SpringMVC拦截了?
     * <p>
     * SPA中点击链接的跳转先经过路由还是拦截器，主要看链接的形式，
     * Angular SPA会把/#/形式的链接先进行路由中转处理，除了/#/形式的链接会按正常流程进入拦截器处理
     *
     * @return
     */
    @RequestMapping(value = "/flowable-modeler", method = RequestMethod.GET)
    public String index() {

        // 在此处设置Flowable User是为了避免 Flowable的 SecurityUtils 在获取用户信息时抛出Null。
        // 逻辑上在登录处设置比较合适。但是发现，重新刷新flowable-modeler连接之后，之前设置的用户信息会丢失。因此改为在此处进行设置。
        HerodotusUserDetails herodotusUserDetails = SecurityUtils.getPrincipal();
        if (ObjectUtils.isNotEmpty(herodotusUserDetails)) {
            FlowableUserHelper.setAuthenticatedUser(herodotusUserDetails);
        } else {
            log.warn("[Herodotus] |- SecurityUtils.getPrincipal() is null, So can not set Flowable User Info!");
        }
        return "/flowable-modeler/index.html";
    }
}
