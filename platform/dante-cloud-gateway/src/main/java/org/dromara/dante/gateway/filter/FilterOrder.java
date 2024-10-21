/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.dante.gateway.filter;

/**
 * <p> Description : 过滤器排序值统一管理 </p>
 * <p>
 * 值越小优先级越高
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 18:31
 */
public class FilterOrder {

    private static final int INITIAL_ORDER = -10;
    private static final int ORDER_STEP = 10;

    public static final int GLOBAL_CACHE_BODY_FILTER_ORDER = INITIAL_ORDER;
    public static final int GLOBAL_SQL_INJECTION_FILTER_ORDER = GLOBAL_CACHE_BODY_FILTER_ORDER + ORDER_STEP;
    public static final int GLOBAL_CERTIFICATION_FILTER_ORDER = GLOBAL_SQL_INJECTION_FILTER_ORDER + ORDER_STEP;
    public static final int GLOBAL_RESPONSE_FILTER_ORDER = GLOBAL_CERTIFICATION_FILTER_ORDER + ORDER_STEP;

}
