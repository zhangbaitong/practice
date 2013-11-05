package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import po.Fruit;
import util.ExcelUtil;
/** 
 * 测试类. 
 */  
public class Test {  
  
    public static void main(String[] args) throws InvalidFormatException {  
        List<Fruit> list = new ArrayList<Fruit>();  
        list.add(new Fruit("苹果2",2.01f));  
        list.add(new Fruit("桔子2",2.05f));
//        List<Map> list2 = new ArrayList<Map>(); 
//        Map map = new HashMap();
//        map.put("name", "苹");
//        map.put("price", 2.01f);
//        list2.add(map);
//        Map map2 = new HashMap();
//        map2.put("name", "guo");
//        map2.put("price", 2.05f);
//        list2.add(map2);
        String templateFileName = "template/template.xls";  
        String resultFileName = "result/fruit.xls";  
        new ExcelUtil().createExcel(templateFileName,list,resultFileName);  
  
    }  
  
}  