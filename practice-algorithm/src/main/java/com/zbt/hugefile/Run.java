package com.zbt.hugefile;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.zbt.scene.countword.singlethread.TimeUtil;


/**
 * http://stoffe.deephacks.org/2013/01/13/huge-files.html
 * @author zbt
 *
 */
public class Run {
	
	public static byte comma = " ".getBytes()[0];
	
//	public static String fileName = "D:/Documents and Settings/zhangtao_jg/Desktop/test-small.txt";
	public static String fileName = "D:/Documents and Settings/zhangtao_jg/Desktop/test1g.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date old = new Date();
		TextRowDecoder decoder = new TextRowDecoder(4, comma);
		File file = new File(fileName);
		FileReader<byte[][]> reader = FileReader.create(decoder, file);
		for (List<byte[][]> chunk : reader) {
			//System.out.println(chunk.toString());
		  // do something with each chunk
		}
		
		TimeUtil.calc(old);
		
		
//		TrueFxDecoder decoder = new TrueFxDecoder();
//		FileReader<TrueFxData> reader = FileReader.create(decoder, file.listFiles());
//		long periodLength = TimeUnit.DAYS.toMillis(1);
//		PeriodEntries<TrueFxData> periods = PeriodEntries.create(reader, periodLength);
//		 
//		for (List<TrueFxData> entries : periods) {
//		   // data for each day
//		   for (TrueFxData entry : entries) {
//		     // process each entry
//		   }
//		}

	}

}
