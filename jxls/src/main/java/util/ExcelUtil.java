package util;

import java.io.IOException;  
import java.net.URL;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
  
import net.sf.jxls.exception.ParsePropertyException;  
import net.sf.jxls.transformer.XLSTransformer;  
/** 
 * Excel生成类. 
 */  
public class ExcelUtil {  
    /** 
     * 根据模板生成Excel文件. 
     * @param templateFileName 模板文件. 
     * @param list 模板中存放的数据. 
     * @param resultFileName 生成的文件. 
     * @throws InvalidFormatException 
     */  
    public void createExcel(String templateFileName, List<?> list, String resultFileName) throws InvalidFormatException{  
        //创建XLSTransformer对象  
        XLSTransformer transformer = new XLSTransformer();  
        //获取java项目编译后根路径  
        URL url = this.getClass().getClassLoader().getResource("");  
        //得到模板文件路径  
        String srcFilePath = url.getPath() + templateFileName;  
        Map<String,Object> beanParams = new HashMap<String,Object>();  
        beanParams.put("list", list);  
        String destFilePath = url.getPath() + resultFileName;  
        try {  
            //生成Excel文件  
            transformer.transformXLS(srcFilePath, beanParams, destFilePath);  
        } catch (ParsePropertyException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  