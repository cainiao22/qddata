import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.Blob;
import com.qding.qdh.bigdata.ufo.service.IUfoIotDeviceDailyActivityService;
import com.qding.qdh.framework.model.ServiceResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by QDHL on 2018/7/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class TestJedis {
    @Autowired
    JedisCluster jedisCluster;

    @Test
    public void cluster(){
        /*jedisCluster.set("foo", "bar");*/
        /*String value = jedisCluster.get("foo");*/
       /* System.out.println(value);*/
        Set<Tuple> tuples = jedisCluster.zrangeWithScores("20180705_jiashicang", 0, -1);
        int size = tuples.size();
        System.out.println(size+"-------------------");
        for (Tuple tuple: tuples) {
            String nameAccount = tuple.getElement();
            double scoreNumber = tuple.getScore();
            System.out.println(nameAccount+"--------"+scoreNumber);
        }
        /*Long zcount = jedisCluster.zcount("20180705_jiashicang",1.0,5.0);
        System.out.println(zcount);*/
    }

    @Autowired
    IUfoIotDeviceDailyActivityService service;

    @Test
    public void testDubbox() throws Exception {
        ServiceResult<String> result = service.getActivityData(new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-22"), "month");
        System.out.println(JSON.toJSONString(result));
    }

}
