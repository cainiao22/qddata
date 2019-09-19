package com.qding.bigdata.ds.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;


public class TextUtil {

	public static void main(String[] args) throws IOException {
		File target=new File("D:/BaiduNetdiskDownload/汉语词库/target");
		Set<String> dict=new HashSet<String>();
		LineIterator lines = FileUtils.lineIterator(target);
		int size=0;
		while(lines.hasNext()){
			String s=lines.next();
			dict.add(s);
			size++;
		}
		System.out.println(size);
		System.out.println(dict.size());
	}
}
