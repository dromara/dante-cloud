package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractResult;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.HintWordsInfo;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.SFaceData;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: ImageResultInfo </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:47
 */
public class ImageResultInfo extends AbstractResult {

    /**
     * 图片中含有广告或文字违规信息时，返回图片中广告文字命中的风险关键词信息。
     * 说明 只有图文违规（ad）场景会返回该结果。
     */
    private List<HintWordsInfo> hintWordsInfo;
    /**
     * 图片中包含暴恐识涉政内容时，返回识别出来的暴恐涉政信息。具体结构描述，请参见sfaceData。
     */
    private List<SFaceData> sfaceData;
    /**
     * 识别到的图片中的完整文字信息。
     * 说明 默认不返回。如果需要该结果，请提交工单联系我们进行配置。
     */
    private List<String> ocrData;

    public List<HintWordsInfo> getHintWordsInfo() {
        return hintWordsInfo;
    }

    public void setHintWordsInfo(List<HintWordsInfo> hintWordsInfo) {
        this.hintWordsInfo = hintWordsInfo;
    }

    public List<SFaceData> getSfaceData() {
        return sfaceData;
    }

    public void setSfaceData(List<SFaceData> sfaceData) {
        this.sfaceData = sfaceData;
    }

    public List<String> getOcrData() {
        return ocrData;
    }

    public void setOcrData(List<String> ocrData) {
        this.ocrData = ocrData;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}
