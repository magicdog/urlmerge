package com.hipu.urlfilter.mergesort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Random;

public class RandomRead {
	public static void main(String args[]) {
		URL filePath = RandomRead.class.getResource("/urlnew");
		File file = new File(filePath.getFile());
		int testTime = 10000;
		try {
			RandomAccessFile rf = new RandomAccessFile(file, "r");
			int length = (int) rf.length();
			Random random = new Random(System.currentTimeMillis());
			long startTime = System.currentTimeMillis();
			int position ;
			for (int i=0; i<testTime; i++) {
				position = random.nextInt(length);
				rf.seek(position);
				System.out.println("" +position+ " "+ rf.readLine());
			}
			long diff = System.currentTimeMillis() -startTime;
			System.out.println("total time " + diff);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
