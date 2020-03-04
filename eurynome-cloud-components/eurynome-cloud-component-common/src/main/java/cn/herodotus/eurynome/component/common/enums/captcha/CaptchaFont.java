package cn.herodotus.eurynome.component.common.enums.captcha;

import com.wf.captcha.base.Captcha;

/**
 * <p> Description : easy-captcha font </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/25 14:29
 */
public enum CaptchaFont {
    /**
     * enum
     */

    FONT_1(Captcha.FONT_1, "actionj"),
    FONT_2(Captcha.FONT_2, "epilog"),
    FONT_3(Captcha.FONT_3, "fresnel"),
    FONT_4(Captcha.FONT_4, "headache"),
    FONT_5(Captcha.FONT_5, "lexo"),
    FONT_6(Captcha.FONT_6, "prefix"),
    FONT_7(Captcha.FONT_7, "progbot"),
    FONT_8(Captcha.FONT_8, "ransom"),
    FONT_9(Captcha.FONT_9, "robot"),
    FONT_10(Captcha.FONT_10, "scandal");

    private int index;
    private String fontName;

    CaptchaFont(int index, String fontName) {
        this.index = index;
        this.fontName = fontName;
    }

    public int getIndex() {
        return index;
    }

    public String getFontName() {
        return fontName;
    }
}
