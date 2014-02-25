package org.priactice.spider;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
//    	String path = "";
//    	URL pageURL = new URL(path);
//    	InputStream stream = pageURL.openStream(); 
    	
        //创建一个客户端，类似于打开一个浏览器  
        CloseableHttpClient httpclient = HttpClients.createDefault(); 
         
        //创建一个get方法，类似于在浏览器地址栏中输入一个地址  
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        
         
        //回车，获得响应状态码  
        StatusLine status = response1.getStatusLine();
        System.out.println(status.getStatusCode());
        
        
         
        //查看命中情况，可以获得的东西还有很多，比如head、cookies等
        System.out.println(EntityUtils.toString(response1.getEntity()));
        
        EntityUtils.consume(response1.getEntity());
        //System.out.println("response=" + getMethod.getResponseBodyAsString());  
         
        //释放  
        response1.close();
        
    }
}
