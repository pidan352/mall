package com.lyl.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/10
 */

@RestController
@CrossOrigin("*")
public class UploadFileDemo {

	/**
	 * 从application.yml中读取，但是为了安全不能上传oss的key，所以不上传application.yml
	 */
	@Value("${oss.bucketName}")
	private String bucketName;

	@Value("${oss.endpoint}")
	private String endpoint;

	@Value("${oss.accessKey}")
	private String accessKey;

	@Value("${oss.secretKey}")
	private String secretKey;

	@Value("${oss.bucketEndpoint}")
	private String bucketEndpoint;

	@PostMapping("/uploadFile")
	public String uploadFile(MultipartFile file) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String filename = file.getOriginalFilename();

		// 填写Object完整路径，完整路径中不能包含Bucket名称
		String objectName = format.format(new Date()) + "/" + filename;

		//访问资源的URL地址
		String filePath = bucketEndpoint + "/" + objectName;

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey);

		try {
			// 创建PutObjectRequest对象。
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,
																	 file.getInputStream());
			// 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
			// ObjectMetadata metadata = new ObjectMetadata();
			// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
			// metadata.setObjectAcl(CannedAccessControlList.Private);
			// putObjectRequest.setMetadata(metadata);

			// 上传文件。
			ossClient.putObject(putObjectRequest);
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
									   + "but was rejected with an error response for some reason.");
			System.out.println("Error Message:" + oe.getErrorMessage());
			System.out.println("Error Code:" + oe.getErrorCode());
			System.out.println("Request ID:" + oe.getRequestId());
			System.out.println("Host ID:" + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
									   + "a serious internal problem while trying to communicate with OSS, "
									   + "such as not being able to access the network.");
			System.out.println("Error Message:" + ce.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return filePath;
	}
}
