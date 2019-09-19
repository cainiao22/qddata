import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestMetrics {
public static void main(String[] args) throws IOException {
	Set<String> set=new HashSet<String>();
		List<String> lines = FileUtils.readLines(new File("C:/Users/QDHL/Downloads/superset_09_11"));
		for (String line : lines) {
			try {
				
	
			String param=line.split("\t")[1];
			JSONObject jo = JSONObject.parseObject(param);
			JSONArray jsonArray = jo.getJSONArray("metrics");
			if(jsonArray.isEmpty()||jsonArray.size()<1){
				continue;
			}
			for (Object obj : jsonArray) {
//				System.out.println(ascii2native((String)obj));
				set.add((String)obj);
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		for (String s : set) {
			System.out.println(s);
		}
}

public static String ascii2native(String ascii) {  
    int n = ascii.length() / 6;  
    StringBuilder sb = new StringBuilder(n);  
    for (int i = 0, j = 2; i < n; i++, j += 6) {  
        String code = ascii.substring(j, j + 4);  
        char ch = (char) Integer.parseInt(code, 16);  
        sb.append(ch);  
    }  
    return sb.toString();  
}  
}
