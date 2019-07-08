package com.bentest.spiders.aliyunoss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;

@Service
public class AliyunOSSClientFactory {

	@Autowired
	OSSConfig ossConfig;
	
	@Bean
	public OSSClient OSSClientFactory() {
		String endpoint = ossConfig.getEndpoint();
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = ossConfig.getAccessKeyId();
		String accessKeySecret = ossConfig.getAccessKeySecret();
		
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return ossClient;
	}
	
}
