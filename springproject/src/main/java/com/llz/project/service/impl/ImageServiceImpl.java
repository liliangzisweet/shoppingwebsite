package com.llz.project.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.llz.project.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	@Value("${url}")
	private String imageFolderPath;

	// 将网络地址上的图片转换为byte[]
	public byte[] imageUrlToByte(String imageUrl) throws Exception {
		URL url = new URL(imageUrl);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		return outStream.toByteArray();
	}

	// 在图片目录下创建图片
	public void setImageFile(int id, byte[] imageArray, boolean isEdited) {
		String imagePath = imageFolderPath + id + ".jpg";
		File imageFile = new File(imagePath);
		System.out.println("imagePath:"+imageFile.getAbsolutePath());
		// 如果文件不存在，并且是新发布的文件
		if ((!imageFile.exists()) && (isEdited == false)) {
			// 创建输出流
			FileOutputStream outStream = null;
			try {
				imageFile.createNewFile();
				outStream = new FileOutputStream(imageFile);
				// 写入数据
				outStream.write(imageArray);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭输出流
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 如果文件存在并且是编辑发布的文件
		if ((imageFile.exists()) && (isEdited == true)) {
			// 创建输出流
			FileOutputStream outStream = null;
			try {
				imageFile.delete();
				imageFile.createNewFile();
				outStream = new FileOutputStream(imageFile);
				// 写入数据
				outStream.write(imageArray);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭输出流
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public boolean deleteImageFileById(int id) {
		String imagePath = imageFolderPath + id + ".jpg";
		File imageFile = new File(imagePath);
		if (imageFile.exists()) {
			imageFile.delete();
			return true;
		}else{
			return false;
		}

	}
}
