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
 * File Name: GaussianBlur.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:47:17
 */

package cn.herodotus.eurynome.captcha.algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>Description: 高斯模糊算法 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:47
 */
public class GaussianBlur {

    /**
     * 高斯模糊
     *
     * @param image  原图
     * @param pixelX X像素点
     * @param pixelY Y像素点
     * @param matrix 高斯模糊矩阵
     * @param values 高斯模糊存周边像素值
     * @param size 模糊区域大小
     */
    public static void execute(BufferedImage image, int pixelX, int pixelY, int[][] matrix, int[] values, int size) {
        // 抠图区域高斯模糊
        readPixel(image, pixelX, pixelY, values, size);
        fillMatrix(matrix, values);
        image.setRGB(pixelX, pixelY, averageMatrix(matrix));
    }

    private static void readPixel(BufferedImage bufferedImage, int x, int y, int[] pixels, int size) {
        int xStart = x - 1;
        int yStart = y - 1;

        int current = 0;
        for (int i = xStart; i < size + xStart; i++) {
            for (int j = yStart; j < size + yStart; j++) {
                int tempX = calculatePixel(x, i, bufferedImage.getWidth());
                int tempY = calculatePixel(y, j, bufferedImage.getHeight());
                pixels[current++] = bufferedImage.getRGB(tempX, tempY);
            }
        }
    }

    private static int calculatePixel(int value, int step, int bound) {
        int pixel = step;
        if (pixel < 0) {
            pixel = -pixel;
        } else if (pixel >= bound) {
            pixel = value;
        }
        return pixel;
    }

    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int[] x : matrix) {
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    private static int averageMatrix(int[][] matrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int[] x : matrix) {
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }

        return new Color(r / 8, g / 8, b / 8).getRGB();
    }
}
