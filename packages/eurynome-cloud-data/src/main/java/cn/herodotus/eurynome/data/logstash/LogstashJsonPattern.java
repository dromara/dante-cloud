package cn.herodotus.eurynome.data.logstash;

import java.io.Serializable;

/**
 * <p> Description : LogstashTcpSocketAppender将日志解析为JSON的定义实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/9 12:48
 */
public class LogstashJsonPattern implements Serializable {

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
        return "LogstashJsonPattern{" +
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
