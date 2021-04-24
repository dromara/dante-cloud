package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractResult;
import cn.herodotus.eurynome.integration.content.domain.aliyun.common.HintWordsInfo;
import cn.herodotus.eurynome.integration.content.domain.aliyun.common.HitLibInfo;
import cn.herodotus.eurynome.integration.content.domain.aliyun.common.LogoData;
import cn.herodotus.eurynome.integration.content.domain.aliyun.common.SFaceData;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里视频同步检测返回结果：results对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:58
 */
public class VideoResult extends AbstractResult {

    /**
     * 如果检测场景包含智能鉴黄（porn）和暴恐涉政（terrorism），则该字段可以返回检测结果的细分类标签。
     * 该字段默认不会返回。如果有需要，您可以提交工单联系我们进行配置，配置后才会返回。
     */
    private String sublabel;

    /**
     * 包含违规内容的视频截帧的信息。
     */
    private List<ResultFrame> frames;

    /**
     * 额外附加信息。
     * 图文违规（ad）场景中，该参数可能返回以下内容。
     * <p>
     * hitLibInfo：如果视频中的文字命中了自定义文本库，则返回命中的文本库信息。具体结构描述，请参见hitLibInfo。
     */
    private HitLibInfo extras;

    /**
     * 视频中含有广告或文字违规信息时，返回视频中广告文字命中的风险关键词信息。具体结构描述，请参见hintWordsInfo。
     * 说明 只有图文违规（ad）场景会返回该结果。
     */
    private List<HintWordsInfo> hintWordsInfo;

    /**
     * 视频中含有logo时，返回识别出来的logo信息，具体结构描述，请参见logoData。
     * 说明 只有视频logo（logo）场景会返回该结果。
     */
    private List<LogoData> logoData;

    /**
     * 视频中包含暴恐识涉政内容时，返回识别出来的暴恐涉政信息，具体结构描述，请参见sfaceData。
     * 说明 只有视频暴恐涉政（terrorism）场景会返回该结果。
     */
    private List<SFaceData> sfaceData;

    public String getSublabel() {
        return sublabel;
    }

    public void setSublabel(String sublabel) {
        this.sublabel = sublabel;
    }

    public List<ResultFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<ResultFrame> frames) {
        this.frames = frames;
    }

    public HitLibInfo getExtras() {
        return extras;
    }

    public void setExtras(HitLibInfo extras) {
        this.extras = extras;
    }

    public List<HintWordsInfo> getHintWordsInfo() {
        return hintWordsInfo;
    }

    public void setHintWordsInfo(List<HintWordsInfo> hintWordsInfo) {
        this.hintWordsInfo = hintWordsInfo;
    }

    public List<LogoData> getLogoData() {
        return logoData;
    }

    public void setLogoData(List<LogoData> logoData) {
        this.logoData = logoData;
    }

    public List<SFaceData> getSfaceData() {
        return sfaceData;
    }

    public void setSfaceData(List<SFaceData> sfaceData) {
        this.sfaceData = sfaceData;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sublabel", sublabel)
                .toString();
    }
}
