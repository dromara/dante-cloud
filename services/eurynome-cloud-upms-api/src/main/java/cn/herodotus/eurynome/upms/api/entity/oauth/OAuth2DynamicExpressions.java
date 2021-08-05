/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-upms-api
 * File Name: OAuth2DynamicExpressions.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 16:59:05
 */

package cn.herodotus.eurynome.upms.api.entity.oauth;

import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: OAuth2 动态表达式 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 16:59
 */
@ApiModel(description = "OAuth2 动态表达式")
@Entity
@Table(name = "oauth_dynamic_expressions", indexes = {@Index(name = "oauth_dynamic_expressions_id_idx", columnList = "expression_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_DYNAMIC_EXPRESSIONS)
public class OAuth2DynamicExpressions extends BaseSysEntity {

    @ApiModelProperty(value = "表达式ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "expression_id", length = 64)
    private String expressionId;

    @ApiModelProperty(value = "表达式内容")
    @Column(name = "expression_content", length = 128)
    private String expressionContent;

    public String getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }

    public String getExpressionContent() {
        return expressionContent;
    }

    public void setExpressionContent(String expressionContent) {
        this.expressionContent = expressionContent;
    }

    @Override
    public String getLinkedProperty() {
        return this.getExpressionContent();
    }

    @Override
    public String getId() {
        return this.getExpressionId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("expressionId", expressionId)
                .add("expressionContent", expressionContent)
                .toString();
    }
}
