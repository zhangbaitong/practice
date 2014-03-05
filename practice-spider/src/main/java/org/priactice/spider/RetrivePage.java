package org.priactice.spider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RetrivePage {

	private static CloseableHttpClient httpclient = HttpClients.createDefault(); 

	public static boolean downloadPage(String path)
			throws Exception{  
		
		InputStream input = null;
		OutputStream output = null;
		
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		
		int statusCode = response1.getStatusLine().getStatusCode(); 
			        // 针对状态码进行处理 (简单起见，只处理返回值为200的状态码)  
			        if (statusCode == HttpStatus.SC_OK) {  
			            input = response1.getEntity().getContent();  
			            //得到文件名  
//			            String filename = path.substring(path.lastIndexOf('/')+1);
			            String filename = "test.html";
			            //获得文件输出流  
			            output = new FileOutputStream(filename);  
			 
			            //输出到文件  
			            int tempByte = -1;  
			            while((tempByte=input.read())>0){  
			                output.write(tempByte);  
			            }  
			            //关闭输入输出流  
			            if(input!=null){  
			                input.close();  
			            }  
			            if(output!=null){  
			                output.close();  
			            }  
			            return true;  
			        }  
			        return false;  
			    }  
			 
			    /**  
			     * 测试代码  
			     */  
			    public static void main(String[] args) {  
			        // 抓取lietu首页，输出  
			        try {  
			            RetrivePage.downloadPage("http://www.baidu.com");  
			        } catch (Exception e) {  
			            e.printStackTrace();  
			        }
			    }  

}
