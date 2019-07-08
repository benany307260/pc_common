package com.bentest.spiders.aliyunoss;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;



@Service
public class OSSService {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OSSClient ossClient;
	
	@Autowired
	OSSConfig ossConfig;
	
	/**
	 * 上传字符串
	 * @param content
	 * @return
	 */
	public boolean uploadString(String name, String content) {
		try {
			ossClient.putObject(ossConfig.getBucketName(), name, new ByteArrayInputStream(content.getBytes()));
			return true;
		} catch (Exception e) {
			log.error("上传字符串到阿里云OSS，异常。name="+name+",content="+content, e);
			return false;
		}
	}
	
	/**
	 * 下载字符串
	 * @param content
	 * @return
	 */
	public String downloadString(String name) {
		BufferedReader reader = null;
		InputStreamReader isReader = null;
		try {
			// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
			OSSObject ossObject = ossClient.getObject(ossConfig.getBucketName(), name);

			// 读取文件内容
			isReader = new InputStreamReader(ossObject.getObjectContent());
			reader = new BufferedReader(isReader);
			StringBuffer sb = new StringBuffer();
			while (true) {
			    String line = reader.readLine();
			    if (line == null) break;

			    sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("下载字符串从阿里云OSS，异常。name="+name, e);
			return null;
		} finally {
			try {
				if(isReader != null) {
					isReader.close();
				}
			} catch (IOException e1) {
				log.error("下载字符串从阿里云OSS，关闭InputStreamReader，异常。", e1);
			}
			try {
				if(reader != null) {
					// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
					reader.close();
				}
			} catch (IOException e2) {
				log.error("下载字符串从阿里云OSS，关闭BufferedReader，异常。", e2);
			}
		}
	}
	

}
