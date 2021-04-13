package cn.herodotus.eurynome.common.utils;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>Description: Base64 图片转换工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 13:55
 */
@Slf4j
public class Base64ImageUtils {


	public static byte[] onlineImageToBase64(String imageUrl) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			URL url = new URL(imageUrl);
			byte[] buffer = new byte[1024];

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			InputStream inputStream = connection.getInputStream();
			//将内容读取内存中
			int length = -1;
			while ((length = inputStream.read(buffer)) != -1) {
				data.write(buffer, 0, length);
			}
			inputStream.close();
		} catch (MalformedURLException e) {
			log.error("[Braineex] |- Online Image url is error!");
		} catch (IOException e) {
			log.error("[Braineex] |- Online image convert to base64 error!");
		}

		return data.toByteArray();
	}

	public static byte[] localImageToBase64(String imagePath) {
		InputStream in;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imagePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (FileNotFoundException e) {
			log.error("[Braineex] |- Local image is not found : {}", imagePath);
		} catch (IOException e) {
			log.error("[Braineex] |- Local image convert to base64 error!");
		}

		return data;
	}
}
