package cn.herodotus.eurynome.integration.compliance.domain.aliyun.text;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description:  阿里文本审核返回值：context对应实体  </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 16:29
 */
public class Context implements Serializable {

    /**
     * 检测文本命中的风险关键词，如果命中了关键词会返回该内容，如果命中了算法模型，则不会返回该字段。
     */
    private String context;
    /**
     * 如果命中关键词，会返回该词在原始文本中的位置。
     */
    private List<Position> positions;
    /**
     * 命中自定义词库时，才会返回当前字段。取值为创建词库时设置的词库名称。
     */
    private String libName;
    /**
     * 命中您自定义文本库时，才会返回当前字段。取值为创建风险文本库后系统返回的文本库code。
     */
    private String libCode;
    /**
     * 命中行为规则时，才会返回当前字段。取值：
     * user_id
     * ip
     * umid
     * content
     * similar_content
     * imei
     * imsi
     */
    private String ruleType;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
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

