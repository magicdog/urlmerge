package com.hipu.urlfilter.mergesort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;


public class GenerateFile {
	private static final Logger logger = Logger.getLogger(GenerateFile.class);

	private final String CHAR_SET = "abcdefghijklmnopqistuvwxyz123456.";
	private final int CHAR_LENGTH = CHAR_SET.length();
	private final String PREFIX = "http://";
	private final int URL_LENGTH = 107;
	private final String CHANGE_ROW = System.getProperty("line.separator");
	private int urlCount;
	private File destDir = null;

	protected GenerateFile(String destFile, int urlCount){
		this.urlCount = urlCount;
		this.destDir = new File(destFile);
		System.out.println("dest file path: " + this.destDir.getAbsolutePath());
	}

	protected void generete() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(destDir));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = System.currentTimeMillis();
		Random random = new Random(System.currentTimeMillis());
		long count = 0;
		int urlLength;
		StringBuffer url = new StringBuffer(PREFIX);
		for (; count < this.urlCount; count++) {
			urlLength = random.nextInt(URL_LENGTH) + 1;
			for (int j = 0; j < urlLength; j++) {
				url.append(CHAR_SET.charAt(random.nextInt(CHAR_LENGTH)));
			}
			try {
				writer.write(url.toString()+CHANGE_ROW);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url.delete(PREFIX.length(), url.length());
			if (count > 0 && ((count % 10000) == 0)) {
				this.logger.info("Added "
						+ count
						+ " in "
						+ (System.currentTimeMillis() - start));
				start = System.currentTimeMillis();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.logger.info("Added " + count + " in "
				+ (System.currentTimeMillis() - start));
	}
}
