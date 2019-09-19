import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class Main {
	static final String _1="111111111111111111111111111111111111111111";
	public static void main(String[] args) throws IOException {
		System.out.println(_1.length());
		List<String> lines = FileUtils.readLines(new File("C:/Users/QDHL/Downloads/pass"));
		Map<String, int[]> map = new HashMap<String, int[]>();
		String userid = "";
		String dt = "";
		for (String line : lines) {
			String[] arr = line.split("\t");
			userid = arr[0];
			if(!arr[1].contains("2017-08")){
				continue;
			}
			if (!map.containsKey(userid)) {
				int[] v = new int[31];
				map.put(userid, v);
			}
//System.out.println(arr[1].substring(8,10));
			(map.get(userid))[Integer.parseInt(arr[1].substring(8, 10)) - 1] = 1;
		}

		List<String> res=new ArrayList<String>();
		for (Entry<String, int[]> entry : map.entrySet()) {
//			System.out.println(entry.getKey()+"\t"+join(entry.getValue()));
			res.add(entry.getKey()+"\t"+join(entry.getValue()));
		}
		List<String> res2=new ArrayList<String>();
		for (String line  : res) {
			String[] arr = line.split("\t");
			userid = arr[0];
			String data=arr[1];
			String max=longestSerialSubstring(data,_1);
			res2.add(userid+"\t"+data+"\t"+max.length());
		}
		FileUtils.writeLines(new File("C:/Users/QDHL/Downloads/pass2"), res2);
	}

	private static String join(int[] value) {
		StringBuffer sb=new StringBuffer();
		for (int i : value) {
			sb.append(String.valueOf(i));
		}
		return sb.toString();
	}

	public static String longestSerialSubstring(String s1, String s2) {
		String res = "";

		int m = s1.length(), n = s2.length();
		int[][] dp = new int[m][n];

		int max = 0;
		int index = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				char c1 = s1.charAt(i);
				char c2 = s2.charAt(j);

				if (c1 == c2) {
					if (i == 0 || j == 0) {
						dp[i][j] = 1;
					} else {
						dp[i][j] = dp[i - 1][j - 1] + 1;
					}
					if (dp[i][j] > max) {
						max = dp[i][j];
						index = i;
					}
				}
			}
		}
		res = s1.substring(index - max + 1, index + 1);
		return res;
	}
}