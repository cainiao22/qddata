package com.qding.bigdata.ds;

import java.lang.reflect.Method;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * Created by yanpf on 2017/8/3.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class RedisTest {

    //@Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() throws  Exception {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().equals("printxx")){
                method.invoke("aaa", new Object[]{new String[]{"aaa"}});
            }
        }
    }

    private static void printxx(String a, String ... xx){
        System.out.println(a);
    }


    @Test
    public void testConvertRow(){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(int i=0; i<10; i++) {
            Map<String, String> item = new HashMap<String, String>();
            item.put("column1", "v" + i);
            item.put("column2", "p" + i);
            item.put("column3", "q" + i);
            item.put("column4", "r" + i);
            list.add(item);
        }
        System.out.println(JSON.toJSONString(list));
        Map<String, List<Object>> result = new HashMap<String, List<Object>>();
        if(!CollectionUtils.isEmpty(list)){
            Set<String> head = list.get(0).keySet();
            for(int i=0; i<list.size(); i++){
               for(String key : head){
                   if(result.get(key) == null){
                       result.put(key, new LinkedList<Object>());
                   }
                   result.get(key).add(list.get(i).get(key));
               }
            }
        }

        System.out.println(JSON.toJSONString(result));

    }
}
