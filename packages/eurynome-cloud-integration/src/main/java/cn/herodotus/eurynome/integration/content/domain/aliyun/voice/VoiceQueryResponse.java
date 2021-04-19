package cn.herodotus.eurynome.integration.content.domain.aliyun.voice;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里语音异步检测结果查询返回数据实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/19 16:30
 */
public class VoiceQueryResponse extends VoiceSyncResponse{

    @JsonProperty("new_url")
    @JSONField(name = "new_url")
    private String newUrl;

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("newUrl", newUrl)
                .toString();
    }
}
