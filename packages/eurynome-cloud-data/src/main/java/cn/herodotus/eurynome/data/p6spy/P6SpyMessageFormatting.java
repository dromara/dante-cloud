/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-data
 * File Name: P6SpyMessageFormatting.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.data.p6spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: P6Spy自定义格式化</p>
 *
 * @author gengwei.zheng
 * @date 2019/1/20
 */
public class P6SpyMessageFormatting implements MessageFormattingStrategy {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {

        StringBuilder builder = new StringBuilder(this.format.format(new Date()));
        builder.append(" | took ");
        builder.append(elapsed);
        builder.append("ms | ");
        builder.append(category);
        builder.append(" | connection ");
        builder.append(connectionId);
        builder.append(" | url ");
        builder.append(url);
        builder.append("\n------------------------| ");
        builder.append(sql);
        builder.append(";");

        return StringUtils.isNotEmpty(sql.trim()) ? String.valueOf(builder) : "";
    }
}
