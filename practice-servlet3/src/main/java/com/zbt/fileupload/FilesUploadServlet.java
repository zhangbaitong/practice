package com.zbt.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
  
@WebServlet(  
        description = "文件上传",     
        urlPatterns = { "/uploads" }  
        )  
@MultipartConfig(  
        location = "/home/baitong/tmp",//文件存放路径，指定的目录必须存在，否则会抛异常（jetty8测试无效）
        maxFileSize = 1024 * 1024 * 10//10M文件大小
        //fileSizeThreshold = 819200//当数据量大于该值时，内容将被写入文件。（specification中的解释的大概意思，不知道是不是指Buffer size），字节单位  
        //maxRequestSize =  8*1024*1024*6 //针对该 multipart/form-data 请求的最大数量，默认值为 -1，表示没有限制。以字节为单位。  
)  
public class FilesUploadServlet extends HttpServlet {  
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(FilesUploadServlet.class);
    
    //得到注解信息
    private static final MultipartConfig config;
    
    static {
    	  config = FilesUploadServlet.class.getAnnotation(MultipartConfig.class);
    }
    
    private static final String SAVE_PATH = config.location();
    
         
    /** 
     * @see HttpServlet#HttpServlet() 
     */  
    public FilesUploadServlet() {  
        super();  
    }  
  
    /** 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        //throw new UnsupportedOperationException();
    	request.getRequestDispatcher("/filesupload.jsp").forward(request, response);
    }  
  
    /** 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        List<String> fileNames = new LinkedList<String>(); 
        //为避免获取文件名称时出现乱码
        request.setCharacterEncoding("UTF-8");
        //判断文件是否存在并创建
        UploadUtil.existOrcreate(new File(SAVE_PATH + File.separator));
        //获取所有的表单内容
        Collection<Part> parts = request.getParts();
        //单个文件处理
        //<input name="file" type="file">名字对应就可以
        //Part part = request.getPart("file");
        //基本不会出现，因为form中使用parts必然包含一个提交按钮。。。
        if (parts == null || parts.isEmpty()) {
    	   doError(request, response, "上传文件为空！");
    	   return;
        }
        //遍历所有的表单内容，将表单中的文件写入上传文件目录  
        for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext();) {  
            Part part = iterator.next();
            if(part == null)continue;
            String fileName = UploadUtil.getFileName(part);
            if(fileName == null)continue;
            fileNames.add(fileName);
            log.info("fileName : " + fileName);
            //run-jetty eclipse plugin test jetty8 is not work well.
            part.write(fileName);
            //part.write(SAVE_PATH + File.separator + fileName);
        }  
        request.setAttribute("fileNames", fileNames);
        //显示上传的文件列表  
        request.getRequestDispatcher("filesupload.jsp").forward(request, response);  
    }
    
    private void doError(HttpServletRequest request,HttpServletResponse response, String errMsg) throws ServletException, IOException {
	  request.setAttribute("errMsg", errMsg);
	  this.doGet(request, response);
    }
}