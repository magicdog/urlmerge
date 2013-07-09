package com.hipu.urlfilter.mergesort;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class MergeSortTest {
	private final Logger LOG = Logger.getLogger(MergeSortTest.class.getName());
	
	public static void main(String args[]) {
		
		URL url = MergeSortTest.class.getResource("/log4j.properties");
        if ( url == null ) {
            url =  MergeSortTest.class.getResource("/conf/log4j.properties");
        }
        PropertyConfigurator.configure(url);
        
        url = MergeSortTest.class.getResource("/");
        File file = new File(url.getPath()+"url");
        if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        GenerateFile gf = new GenerateFile(file.getAbsolutePath(), 100000000);
        gf.generete();
        
//		List<File> files = new ArrayList<File>();
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile368981694649145835213"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile260290516693694542919"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile677305789536390727816"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile181574398809277572411"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile10498219570390589846"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile1448321509811310227"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile80072452133636965854"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile680694488821695386112"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile122510453775151768118"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile47401787067894427101"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile39428679434400259399"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile136858575985834956114"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile56204448977458538778"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile41390685909063465862"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile75552042095893507943"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile859641490493167100117"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile665030033498923503715"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile41839977896516018635"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile88392046077612049410"));
//		files.add(new File("C:\\Users\\shelton\\AppData\\Local\\Temp\\splitFile31309454460433381020"));
		MergeSort ms = new  MergeSort(url.getPath()+"url", 20);
//		ms.setSplitFiles(files);
//		ms.mergeFile();
		ms.sort();
//		for (String str : files) {
//			ms.singleFileSort(new File(str));
//			System.out.println("finished "+str);
//		}
		//ms.showSplitFile();
		//ms.destroy();
	}
}
