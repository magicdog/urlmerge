package com.hipu.urlfilter.mergesort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

public class MergeSort {
	
	private final Logger LOG = Logger.getLogger(MergeSort.class.getName());
	
	private final String CHANGE_ROW = System.getProperty("line.separator");
	
	private File originalFile;
	
	private int splitCount = 5;
	
	private List<File> splitFiles = null;
	
	public MergeSort(String filePath, int splitCount) {
		this.splitFiles = new ArrayList<File>();
		
		this.splitCount = splitCount < 0 ? 5 : splitCount;
		
		this.originalFile = new File(filePath);
		if (!checkFile(originalFile)) {
			LOG.error("can not use this file " + filePath);
			return ;
		}
	}
	
	public void sort() {
		splitFile();
		LOG.info("split file finished.");
		sortFile();
		mergeFile();
	}
	
	public void splitFile() {
		List<BufferedWriter> writers = new ArrayList<BufferedWriter>();
		File tempFile = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = null;
			for (int i=0; i < splitCount; i++) {
				tempFile = File.createTempFile("splitFile", String.valueOf(i));
				writer = new BufferedWriter(new FileWriter(tempFile));
				writers.add(writer);
				this.splitFiles.add(tempFile);
			}
		} catch (FileNotFoundException e) {
			LOG.error("can not open file " + originalFile.getPath());
		} catch (IOException e) {
			LOG.error("can not create temp file " + tempFile.getPath());
		}
		
		String url = "";
		int index = 0;
		try {
			while ( (url = reader.readLine()) != null) {
				writers.get(index).write(url+CHANGE_ROW);
				index = (index+1) % splitCount;
			}
			reader.close();
			for (BufferedWriter writer : writers) {
				writer.close();
			}
		} catch (IOException e) {
			LOG.error("encountered error when reading file "+originalFile.getPath());
		}
	}
	
	public void sortFile() {
		for (File file : splitFiles) {
			singleFileSort(file);
			LOG.info("sort file "+file.getName()+"finished");
		}
	}
	
	public void mergeFile() {
		String currentUrls[] = new String[splitCount];
		BufferedReader readers[] = new BufferedReader[splitCount];
		BufferedReader reader = null;
		BufferedWriter writer = null;
		File tempFile = null;
		String url = "";
		int index = 0;
		int arraySize = 0;

		try {
			tempFile = File.createTempFile("orderedfile", "1");
			for (File file : splitFiles) {
				reader = new BufferedReader(new FileReader(file));
				readers[index] = reader;
				if ( (url = reader.readLine()) != null) {
					currentUrls[index] = url;
					arraySize ++;
					LOG.debug("add url " +url + " from " + file.getName());
				}
				index ++;	
			}
			writer = new BufferedWriter(new FileWriter(tempFile));
		} catch (FileNotFoundException e) {
			LOG.error("can not open file " + e.toString());
		} catch (IOException e) {
			LOG.error("can not create temp file " + tempFile.getPath());
		}

		try {
			while (arraySize != 0) {
				index =getMinEleIndex(currentUrls);
				writer.write( currentUrls[index] + CHANGE_ROW);
				currentUrls[index] = null;
				arraySize --;
				if ( (url = readers[index].readLine()) != null) {
					currentUrls[index] = url;
					arraySize ++;
					LOG.debug("add url " +url + " from " + this.splitFiles.get(index).getName());
				}
			}
			for (BufferedReader read : readers) {
				read.close();
			}
			writer.close();
			
			tempFile.renameTo(new File(this.originalFile.getAbsolutePath()+"new"));
		} catch (IOException e) {
			LOG.error("can not write file "+tempFile.getAbsolutePath());
		}
	}
	
	public void setSplitFiles(List<File> lists) {
		this.splitFiles = lists;
	}
	
	public int getMinEleIndex(String urls[]) {
		int index = 0;
		String min = "";
		boolean first = true;
		for (int i=0; i<urls.length; i++) {
			if (urls[i] == null)
				continue;
			if (first) {
				min = urls[i];
				index = i;
				first = false;
			}
			if (urls[i].compareTo(min) < 0) {
				min = urls[i];
				index = i;
			}
		}
		return index;
	}
	
	public void singleFileSort(File file) {
		List<String> urls = new ArrayList<String>();
		if (!checkFile(file)) {
			LOG.error("can not read url file to sort "+file.getAbsolutePath());
			return ;
		}
		BufferedReader reader = null;
		String url;
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((url = reader.readLine()) != null) {
				urls.add(url);
			}
			reader.close();
			Collections.sort(urls);
		} catch (FileNotFoundException e) {
			LOG.error("can not find file "+file.getAbsolutePath());
		} catch (IOException e) {
			LOG.error("read file failed " + file.getAbsolutePath());
		}
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (String str : urls) {
				writer.write(str+CHANGE_ROW);
			}
			writer.close();
		} catch (IOException e) {
			LOG.error("can not write file "+file.getAbsolutePath());
		}
		
	}
	
	public boolean checkFile(File file) {
		if (file == null) 
			return false;
		return (file.exists() && file.canRead() && file.canWrite());
	}
	
	public void showSplitFile() {
		for (File file : splitFiles) {
			LOG.info("split file name " + file.getAbsolutePath());
		}
	}
	
	public void destroy() {
		for (File file : splitFiles) {
			//file.delete();
		}
	}
	
}
