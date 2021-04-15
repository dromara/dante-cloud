package cn.herodotus.eurynome.integration.content.domain.baidu;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: OCR识别位置信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 14:16
 */
public class Word implements Serializable {

    private Location location;
    private String words;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("location", location)
                .add("words", words)
                .toString();
    }
}
