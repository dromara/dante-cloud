package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里网页审核结果对应的Context </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:44
 */
public class Context {
    /**
     * 检测文本命中的风险内容的上下文信息。如果命中了您自定义的风险文本库，则会返回命中的文本内容（关键词或相似文本）。
     */
    private String context;
    /**
     * 命中自定义词库时，返回当前字段。取值为创建词库时设置的词库名称。
     */
    private String libName;
    /**
     * 命中您自定义文本库时，返回当前字段。取值为创建风险文本库后系统返回的文本库code。
     */
    private String libCode;
    /**
     * 命中行为规则时，返回当前字段，取值：
     * user_id：用户ID
     * ip：用户IP
     * content：文本内容重复
     * similar_content：文本内容相似
     * imei：设备唯一标识
     * imsi：设备唯一标识
     */
    private String ruleType;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("context", context)
                .add("libName", libName)
                .add("libCode", libCode)
                .add("ruleType", ruleType)
                .toString();
    }
}
