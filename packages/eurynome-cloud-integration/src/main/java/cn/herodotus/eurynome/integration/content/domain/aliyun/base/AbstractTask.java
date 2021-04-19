package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>Description: 阿里云请求参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:46
 */
public abstract class AbstractTask implements Serializable {

    /**
     * 检测对象对应的数据ID。
     * 由大小写英文字母、数字、下划线（_）、短划线（-）、英文句号（.）组成，不超过128个字符，可以用于唯一标识您的业务数据。
     */
    @ApiModelProperty(name = "检测对象对应的数据ID", notes = "由大小写英文字母、数字、下划线（_）、短划线（-）、英文句号（.）组成，不超过128个字符，可以用于唯一标识您的业务数据")
    private String dataId;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dataId", dataId)
                .toString();
    }
}
