package com.zbt.spring.mvc.upload.controller;

import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zbt.spring.mvc.upload.common.Constant;
import com.zbt.spring.mvc.upload.util.PropertiesUtil;


//http://localhost:8080/spring-mvc/index.jsp
@Controller
@RequestMapping("/upload")
public class UploadFileController {	
	
	/**
	 * 进入上传页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toUpload")
	public ModelAndView toUpload() throws Exception{
		return new ModelAndView("/upload");
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	public ModelAndView uploadFile(MultipartHttpServletRequest request) throws Exception{		
		PropertiesUtil pUtil=PropertiesUtil.createPropertiesUtil(Constant.UPLOADPATH_FILE);
		List<MultipartFile> files=request.getFiles("file");
		String uploadpath=request.getSession().getServletContext().getRealPath(pUtil.getProperty(Constant.UPLOADPATH_PATH));
		System.out.println("uploadpath :"+uploadpath);
		for (MultipartFile file : files) {
			if (file.isEmpty()) continue;
			FileOutputStream fileOS=new FileOutputStream(uploadpath+"/"+file.getOriginalFilename());
			fileOS.write(file.getBytes());
			fileOS.close();
		}
		return new ModelAndView("/success");
	}	

}
