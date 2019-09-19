package com.qding.bigdata.ds.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;

public class ClusterUtil {

	public static LinkedHashMap<Object,Long>  cluster(LinkedHashMap<Object,Long> map){
		LinkedHashMap<Object,Long> result=new LinkedHashMap<Object,Long>();
		double sum=0;
		long cnt=0;
		int range=0;
		for (Entry<Object, Long> e : map.entrySet()) {
			if(!(NumberUtils.isNumber((String)e.getKey()) )){
				return map;
			}
			sum+=Double.parseDouble((String)e.getKey())*e.getValue();
			cnt+=e.getValue();
		}
		range=(int) (sum/cnt*50);
		 for (Entry<Object, Long> e : map.entrySet()) {
			 double k=Double.parseDouble((String)e.getKey());
			 	String key=range*(int)(k/range)+"-"+range*(int)(k/range+1);
			 	if(result.containsKey(key)){
			 		result.put(key, result.get(key)+e.getValue());
			 	}else{
			 		result.put(key, e.getValue());
			 	}
		 }
			 
			
		return result;
	}
}
