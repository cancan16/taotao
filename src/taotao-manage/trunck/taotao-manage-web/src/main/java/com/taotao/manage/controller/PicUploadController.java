package com.taotao.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.bean.PicUploadResult;
import com.taotao.manage.service.PropertiesService;

/**
 * 图片上传
 */
@Controller
@RequestMapping("/pic")
public class PicUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * @Value作用：获取配置文件的值。
     * 注入值：在Spring容器初始化（所有的bean）之后，在当前的所在容器中获取值，然后注入。
     * 第一个容器：spring初始化容器
     * 	<!--Spring的ApplicationContext 载入 -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	第二个容器：springmvc容器
	Spring容器  --  父容器
        SpringMVC容器  -- 子容器
                          父子容器的关系：
        1、	子容器能够访问父容器的资源（bean）
        a)	示例：Controller可以注入Service
        2、	父容器不能访问子容器的资源（bean）
     */
    @Autowired
    private PropertiesService propertiesService;

    /**
     * 1.校验 包含图片的后缀名、大小限制、内容是否为图片(即含有图片的宽和高为图片)三个方面
     */
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg", ".jpeg", ".gif", ".png" };

    /*
     * @ResponseBody注解默认就是application/json类型
     * 但是KindEditor需要返回文本类型的json数据，也就是text/plain文本类型，再转成json字符串 produces:
     * 指定响应的类型
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response)
	    throws Exception {

	// 校验图片格式
	boolean isLegal = false;
	for (String type : IMAGE_TYPE) {
	    if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
		isLegal = true;
		break;
	    }
	}

	// 封装Result对象，并且将文件的byte数组放置到result对象中
	PicUploadResult fileUploadResult = new PicUploadResult();

	// 状态
	fileUploadResult.setError(isLegal ? 0 : 1);

	// 文件新路径
	String filePath = getFilePath(uploadFile.getOriginalFilename());

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Pic file upload .[{}] to [{}] .", uploadFile.getOriginalFilename(), filePath);
	}

	// 生成图片的绝对引用地址
	String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, propertiesService.REPOSITORY_PATH), "\\", "/");
	fileUploadResult.setUrl(propertiesService.IMAGE_BASE_URL + picUrl);

	File newFile = new File(filePath);

	// 使用spring提供的工具类对象写入文件，写文件到磁盘
	uploadFile.transferTo(newFile);
	// 校验图片是否合法
	isLegal = false;
	try {
	    BufferedImage image = ImageIO.read(newFile);
	    if (image != null) {
		fileUploadResult.setWidth(image.getWidth() + "");
		fileUploadResult.setHeight(image.getHeight() + "");
		isLegal = true;
	    }
	} catch (IOException e) {
	}

	// 状态
	fileUploadResult.setError(isLegal ? 0 : 1);

	if (!isLegal) {
	    // 不合法，将磁盘上的文件删除
	    newFile.delete();
	}

	// 将java对象转化成（序列化）json字符串
	return mapper.writeValueAsString(fileUploadResult);
    }

    // E:\0114\taotao-upload\images\2016\01\17\2016011704532743905941.jpg
    private String getFilePath(String sourceFileName) {
	String baseFolder = propertiesService.REPOSITORY_PATH + File.separator + "images";
	Date nowDate = new Date();
	// yyyy/MM/dd
	String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy") + File.separator
		+ new DateTime(nowDate).toString("MM") + File.separator + new DateTime(nowDate).toString("dd");
	File file = new File(fileFolder);
	if (!file.isDirectory()) {
	    // 如果目录不存在，则创建目录
	    file.mkdirs();
	}
	// 生成新的文件名
	String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS") + RandomUtils.nextInt(100, 9999) + "."
		+ StringUtils.substringAfterLast(sourceFileName, ".");
	return fileFolder + File.separator + fileName;
    }

}
