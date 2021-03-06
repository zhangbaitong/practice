package com.zbt.scene.countword.readmultithread;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.zbt.scene.countword.singlethread.CalcUtil;

public class ReadMultiThread extends Thread{
	
	private ContainerMulti c;
	
	public ReadMultiThread(ContainerMulti c){
		this.c = c;
	}
	
	public void run() {
		File file = new File(CalcUtil.FILENAME);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				c.set(tempString);
				//System.out.println(tempString);
			}
			c.finished();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		new ReadMultiThread(new ContainerMulti()).start();
	}

}
