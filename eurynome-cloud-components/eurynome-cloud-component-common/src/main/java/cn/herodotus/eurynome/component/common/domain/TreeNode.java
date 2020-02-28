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
 * Module Name: luban-cloud-component-common
 * File Name: TreeNode.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:13
 * LastModified: 2019/11/8 下午4:13
 */

package cn.herodotus.eurynome.component.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 属性结构定义
 *
 * @author gengwei.zheng
 */
public class TreeNode extends AbstractDomain {

    /**
     * 本节点id
     */
    private String id;
    /**
     * 本节点名称
     */
    private String name;

    /**
     * 本节点的父节点
     */
    private String parentId;
    private List<TreeNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("parentId", parentId)
                .append("children", children)
                .toString();
    }
}
