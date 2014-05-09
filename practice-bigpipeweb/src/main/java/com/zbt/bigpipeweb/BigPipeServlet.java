package com.zbt.bigpipeweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//http://localhost:8080/bigpipeweb/bigpipetest/
/**
 * 异步bigpipe这种实现方式存在很大问题，属于伪实现
 * 问题：
 * servlet线程不中断，导致异步线程的返回取决于servlet的结束时间，如果servlet结束.
 * 但是异步线程没有完成，则没有返回结果，相当于丢失了。
 * @author zhangbaitong
 *
 */
public class BigPipeServlet extends HttpServlet {
	 
    private static ExecutorService executor = Executors.newFixedThreadPool(500, new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Service Thread "+ t.getId());
            t.setDaemon(true);
            return t;
        }
    });
 
    protected void service(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();
 
        String doctype = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
 
        String head = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"de\" lang=\"de\"> \n" +
                "<head> \n" +
                "<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" /> \n" +
                "<meta http-equiv=\"Content-language\" content=\"de\" />\n";
 
        writer.write(doctype);
        writer.write(head);
        writer.write("<script type=\"text/javascript\">function arrived(id,text) { var b=document.getElementById(id); b.innerHTML = text; }</script>");
        writer.write("</HEAD><BODY><div>Progressive Loading");
 
        content(writer, "content1", "content2", "content3", "content4", "content5", "content6");
        writer.write("</div>\n");
 
        final Random random = new Random();
 
        List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
 
        for (int i = 0; i < 6; i++) {
            final int id = i + 1;
            tasks.add(new Callable<Boolean>() {          
                public Boolean call() {
                    try {
                        // One service call is nasty and takes 50sec
                        if (id == 3) {
                            Thread.sleep(50 * 1000);
                        } else {
                        	int timeSleep = random.nextInt(2 * 1000);
                        	System.out.println("Thread id :" + id + " sleep time is : " + timeSleep);
                            Thread.sleep(timeSleep);
                        }
                        pagelet(writer, "content" + id, "Wohooo" + id);
                    } catch (InterruptedException e) {
                        return false;
                    }
                    return true;
                }
            });
        }
 
        try {
            executor.invokeAll(tasks, 1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
            // ignored
        }
        writer.write("</BODY></HTML>");
    }
 
    private void content(PrintWriter writer, String... contentIds) {
        for (String id : contentIds) {
            writer.write("<div id=\"" + id + "\">-</div>\n");
        }
    }
 
    private void pagelet(PrintWriter writer, String id, String content) {
        if (writer.checkError()) return;
        writer.write("<script>" +
                "arrived(\"" + id + "\", \"" + content + "\");" +
                "</script>\n");
        writer.flush();
    }
}

/**
 * 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de" id="facebook" class=" no_js"> 
<head> 
<meta http-equiv="Content-type" content="text/html; charset=utf-8" /> 
<meta http-equiv="Content-language" content="de" /> 
<script type="text/javascript">function arrivedHtml(id,text) { var b=document.getElementById(id); b.innerHTML = text; }</script></HEAD><BODY>
<div>Progressive Loading
  <div id="content1">-</div> 
  <div id="content2">-</div> 
</div> 
<script>arrivedHtml("content2", "Wohoo");</script> 
<script>arrivedHtml("content1", "Wohoo");</script> 
</BODY></HTML>
*
*/
