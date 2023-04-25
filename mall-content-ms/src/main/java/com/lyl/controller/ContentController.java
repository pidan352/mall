package com.lyl.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.pojo.*;
import com.lyl.service.CategoryService;
import com.lyl.service.ContentServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/11
 */

@RestController
public class ContentController {

	@Resource
	private ContentServie contentServie;

	@Resource
	private CategoryService categoryService;

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

	/**
	 * 分页查询广告内容
	 *
	 * @param pageReq
	 * @return
	 */
	@GetMapping("/queryContentByPage")
	public RespBean queryBrandByPage(PageReq pageReq) {
		try {
			if (pageReq.getPage() == 0 || pageReq.getSize() == 0) {
				pageReq.setPage(1);
				pageReq.setSize(5);
			}
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			//分页拦截后返回的是Page
			Page<TbContent> page = (Page<TbContent>) contentServie.queryContent();

			PageResp<TbContent> pageResp = new PageResp<>();
			pageResp.setTotal(page.getTotal());
			pageResp.setList(page.getResult());

			return RespBean.ok("查询成功", pageResp);
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("数据查询出错");
		}
	}

	/**
	 * 根据广告类别查询广告数据
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/findByCategoryId/{id}")
	public RespBean findByCategoryId(@PathVariable Integer id) {
		try {
			List<TbContent> contentList = contentServie.findByCategoryId(id);
			return RespBean.ok("查询成功", contentList);
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("数据查询出错");
		}
	}


	@PostMapping("/deleteContent")
	public RespBean deleteContent(@RequestBody ArrayList<Long> idList) {
		try {
			contentServie.deleteByIdList(idList);
			return RespBean.ok("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("删除失败");
		}
	}

	@GetMapping("/queryCategory")
	public RespBean queryCategory() {
		try {
			List<TbContentCategory> contentCategoryList = categoryService.queryCategory();
			return RespBean.ok("查询成功", contentCategoryList);
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("查询失败");
		}
	}

	@PostMapping("/uploadPic")
	public String uploadPic(MultipartFile file) {
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

	@PostMapping("/addContent")
	public RespBean addContent(@RequestBody TbContent content) {
		System.out.println(content);
		try {
			contentServie.insert(content);
			return RespBean.ok("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("添加失败");
		}
	}
}
