package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import po.CsvOrder;

public class Test2 {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws ParsePropertyException 
	 */
	public static void main(String[] args) throws ParsePropertyException, InvalidFormatException, IOException {
		 List<CsvOrder> orders = new ArrayList<CsvOrder>();
	        CsvOrder order = new CsvOrder();
	        order.setDate("2008年8月28日");
	        order.setIncome(new BigDecimal(2000));
	        order.setTradeNo("200808280118");
	        order.setTrader("德比软件");
	        orders.add(order);
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("orders", orders);
	        XLSTransformer transformer = new XLSTransformer();
	        transformer.transformXLS(new Test2().getClass().getClassLoader().getResource("").getPath()+"template/template2.xls", map, 
	        		new Test2().getClass().getClassLoader().getResource("").getPath()+"result/result2.xls");

	}

}
