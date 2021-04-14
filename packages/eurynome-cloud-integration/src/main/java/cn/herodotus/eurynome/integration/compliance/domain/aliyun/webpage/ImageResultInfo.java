package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.HintWordsInfo;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.SFaceData;

import java.util.List;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:47
 */
public class ImageResultInfo {

    private String scene;
    private String label;
    private String suggestion;
    private Float rate;
    private List<HintWordsInfo> hintWordsInfo;
    private List<SFaceData> sfaceData;
    private List<String> ocrData;
}
