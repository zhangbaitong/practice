package com.zbt.fileupload;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadUtil {
	
	private static final Log log = LogFactory.getLog(UploadUtil.class);
	
	public static String fileNameExtractorRegex = "filename=\".+\"";
	
    /** 
     * 从Part的Header信息中提取上传文件的文件名 
     * @param part 
     * @return  上传文件的文件名，如果如果没有则返回null 
     */  
    public static String getFileName(Part part){  
        String cotentDesc = part.getHeader("content-disposition");
        log.info("content-disposition : " + cotentDesc);
        String fileName = null;  
        Pattern pattern = Pattern.compile(fileNameExtractorRegex);  
        Matcher matcher = pattern.matcher(cotentDesc);  
        if(matcher.find()){  
            fileName = matcher.group();  
            fileName = fileName.substring(10, fileName.length()-1);  
        }
        return fileName;  
    }
    
    public static void existOrcreate(File file){
        log.info("file path is exists? " + file.exists());
        if (file.exists()) {
        	log.info("file path : " + file.getPath());
        }else{
        	file.mkdirs();
        }
    }
    
    public static String getFileName2(Part part){
        String cotentDesc = part.getHeader("content-disposition");
        log.info("content-disposition : " + cotentDesc);
        return cotentDesc.substring(cotentDesc.lastIndexOf("=") + 2, cotentDesc.length() - 1);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
