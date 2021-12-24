/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-captcha
 * File Name: CaptchaCharacter.java
 * Author: gengwei.zheng
 * Date: 2021/12/24 09:21:24
 */

package cn.herodotus.eurynome.captcha.definition.enums;

import cn.herodotus.eurynome.captcha.provider.RandomProvider;

/**
 * <p>Description: 验证码字符类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/24 9:20
 */
public enum CaptchaCharacter {

    /**
     * 验证码字母显示类别
     */
    NUM_AND_CHAR(RandomProvider.NUM_MIN_INDEX, RandomProvider.CHAR_MAX_INDEX, "数字和字母混合"),
    ONLY_NUM(RandomProvider.NUM_MIN_INDEX, RandomProvider.NUM_MAX_INDEX, "纯数字"),
    ONLY_CHAR(RandomProvider.CHAR_MIN_INDEX, RandomProvider.CHAR_MAX_INDEX, "纯字母"),
    ONLY_UPPER_CHAR(RandomProvider.UPPER_MIN_INDEX, RandomProvider.UPPER_MAX_INDEX, "纯大写字母"),
    ONLY_LOWER_CHAR(RandomProvider.LOWER_MIN_INDEX, RandomProvider.LOWER_MAX_INDEX, "纯小写字母"),
    NUM_AND_UPPER_CHAR(RandomProvider.NUM_MIN_INDEX, RandomProvider.UPPER_MAX_INDEX, "数字和大写字母");

    /**
     * 字符索引开始位置
     */
    private final int start;
    /**
     * 字符索引结束位置
     */
    private final int end;
    /**
     * 类型说明
     */
    private final String description;

    CaptchaCharacter(int start, int end, String description) {
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }
}
