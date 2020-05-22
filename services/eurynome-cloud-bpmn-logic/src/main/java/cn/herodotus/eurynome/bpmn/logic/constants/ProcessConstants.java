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
 * File Name: FlowableConstants.java
 * Author: gengwei.zheng
 * Date: 2020/1/26 下午3:37
 * LastModified: 2020/1/26 下午3:37
 */

package cn.herodotus.eurynome.bpmn.logic.constants;

/**
 * <p> Description : Flowable REST Api 访问上下文路径 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/26 15:37
 */
public class ProcessConstants {

    public static final String FLOWABLE_APP_REST_API_MAPPEED_TO = "/app-api";
    public static final String FLOWABLE_BPMN_REST_API_MAPPEED_TO = "/process-api";
    public static final String FLOWABLE_IDM_REST_API_MAPPED_TO="/idm-api";
    public static final String FLOWABLE_FORM_REST_API_MAPPED_TO="/form-api";
    public static final String FLOWABLE_DMN_REST_API_MAPPED_TO="/dmn-api";
    public static final String FLOWABLE_CONTENT_REST_API_MAPPED_TO="/content-api";
    public static final String FLOWABLE_CMMN_REST_API_MAPPED_TO="/cmmn-api";

    /**
     * 提交人的变量名称
     */
    public static final String FLOW_SUBMITTER_VARIABLE = "initiator";

    /**
     * 提交人的节点ID
     */
    public static final String FLOW_SUBMITTER_ACTIVITY_ID = "initiator";

    /**
     * 提交人的节点名称
     */
    public static final String FLOW_SUBMITTER_ACTIVITY_NAME = "填写申请";

    /**
     * 自动跳过参数
     */
    public static final String FLOWABLE_SKIP_EXPRESSION_ENABLED = "_FLOWABLE_SKIP_EXPRESSION_ENABLED";

    /**
     * 动态Url表单，参数name
     */
    public static final String FLOWABLE_DYNAMIC_FORM_URL = "_FLOWABLE_DYNAMIC_FORM_URL";

}
