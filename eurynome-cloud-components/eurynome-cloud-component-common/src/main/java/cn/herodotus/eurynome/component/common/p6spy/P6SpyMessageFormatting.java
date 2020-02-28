/*
 * Copyright (c) 2019. All Rights Reserved
 * ProjectName: hades-multi-module
 * FileName: P6SpyMessageFormat
 * Author: gengwei.zheng
 * Date: 19-1-20 下午6:21
 * LastModified: 19-1-20 下午6:21
 */

package cn.herodotus.eurynome.component.common.p6spy;

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
