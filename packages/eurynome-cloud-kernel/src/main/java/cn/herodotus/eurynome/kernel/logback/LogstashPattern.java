/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-kernel
 * File Name: LogstashPattern.java
 * Author: gengwei.zheng
 * Date: 2020/6/9 下午1:04
 * LastModified: 2020/5/29 下午8:25
 */

package cn.herodotus.eurynome.kernel.logback;

import java.io.Serializable;

/**
 * <p> Description : LogstashTcpSocketAppender将日志解析为JSON的定义实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/9 12:48
 */
public class LogstashPattern implements Serializable {

    private String level = "%level";
    private String service = "";
    private String trace = "%X{X-B3-TraceId:-}";
    private String span = "%X{X-B3-SpanId:-}";
    private String parent = "%X{X-B3-ParentSpanId:-}";
    private String exportable = "%X{X-Span-Export:-}";
    private String pid = "${PID:-}";
    private String thread = "%thread";
    private String clazz = "%logger";
    private String log = "%message";

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getService() {
        return service + ":-";
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getExportable() {
        return exportable;
    }

    public void setExportable(String exportable) {
        this.exportable = exportable;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "LogstashPattern{" +
                "level='" + level + '\'' +
                ", service='" + service + '\'' +
                ", trace='" + trace + '\'' +
                ", span='" + span + '\'' +
                ", parent='" + parent + '\'' +
                ", exportable='" + exportable + '\'' +
                ", pid='" + pid + '\'' +
                ", thread='" + thread + '\'' +
                ", clazz='" + clazz + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
