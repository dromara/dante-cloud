package cn.herodotus.eurynome.integration.content.domain.aliyun.voice;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractResult;

import java.util.List;

/**
 * <p>Description: 阿里音频同步检测返回结果对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:56
 */
public class VoiceResult extends AbstractResult {

    private List<Detail> details;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}
