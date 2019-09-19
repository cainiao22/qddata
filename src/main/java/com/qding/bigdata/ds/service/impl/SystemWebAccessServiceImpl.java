package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.SystemWebAccessDao;
import com.qding.bigdata.ds.enums.SourceEnum;
import com.qding.bigdata.ds.model.CountData;
import com.qding.bigdata.ds.model.SystemWebAccess;
import com.qding.bigdata.ds.service.SystemWebAccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lzs on 2018/7/4.
 */
@Service
public class SystemWebAccessServiceImpl implements SystemWebAccessService {

    @Resource
    SystemWebAccessDao systemWebAccessDao;

    //JedisCluster jedis = JedisClusterUtil.jedis;
    @Resource(name = "jedisCluster")
    JedisCluster jedis;

    @Override
    public List<SystemWebAccess> listBySource(CountData data) {
        String startTime = data.getStartTime().replace("-", "");
        data.setStartTime(startTime);

        String endTime = data.getEndTime().replace("-", "");
        data.setEndTime(endTime);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String todayTime = df.format(date);

        String source = data.getSource();
        List<SystemWebAccess> systemWebAccesses;

        if (StringUtils.equals(endTime, todayTime)) {
            String key = endTime + "_" + source;
            //取当天的数据
            Set<Tuple> tuples = jedis.zrangeWithScores(key, 0, -1);

            //当前时间后一天的所需数据
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
            endTime = df.format(calendar.getTime());
            data.setEndTime(endTime);

            List<SystemWebAccess> list1 = systemWebAccessDao.listBySource(data);

            for (Tuple tuple : tuples) {
                String nameAndId = tuple.getElement();
                double pv2 = tuple.getScore();
                boolean have = false;
                for (SystemWebAccess swa : list1){
                    String value = swa.getUserName() + "_" + swa.getUser();
                    //转换source 为中文
                    //swa.setSource(SourceEnum.getSource(swa.getSource()));
                    if (StringUtils.equals(value, nameAndId)) {
                        swa.setPv((int) (swa.getPv() + tuple.getScore()));
                        swa.setDayNumber(swa.getDayNumber()+1);
                        have = true;
                        break;
                    }
                }

                if(!have){
                    SystemWebAccess sa=new  SystemWebAccess();
                    String[] split = nameAndId.split("_");
                    String tuser=split[0];
                    String tusername=split[1];
                    sa.setDate(todayTime);
                    sa.setUser(tusername);
                    sa.setUserName(tuser);
                    sa.setPv((int)(pv2));
                    //转换source 为中文
                    //sa.setSource(SourceEnum.getSource(source));
                    sa.setSource(source);
                    sa.setDayNumber(1);

                    list1.add(sa);
                }

            }


            /*for (SystemWebAccess swa : list1) {
                String userName = swa.getUserName();
                String user = swa.getUser();
                int pv = swa.getPv();
                String date1=swa.getDate();
                String value = userName + "_" + user;

                for (Tuple tuple : tuples) {
                    String nameAndId = tuple.getElement();
                    double pv2 = tuple.getScore();

                    if (StringUtils.equals(value, nameAndId)) {
                        pv = (int) (pv + pv2);

                        swa.setPv(pv);

                    }else{
                        SystemWebAccess sa=new  SystemWebAccess();
                        String[] split = nameAndId.split("_");
                        String tuser=split[0];
                        String tusername=split[1];
                        sa.setDate(todayTime);
                        sa.setUser(tusername);
                        sa.setUserName(tuser);
                        sa.setPv((int)(pv2));
                        sa.setSource(source);

                        set2.add(sa);
                    }


                }
                set2.add(swa);
            }*/

            /*List<SystemWebAccess> listnewList = new ArrayList<SystemWebAccess>(set2);*/
            //转换source 为中文
          /*  for (SystemWebAccess sw: list1) {
                sw.setSource(SourceEnum.getSource(source));
            }*/
            systemWebAccesses = list1;

        } else {
            systemWebAccesses = systemWebAccessDao.listBySource(data);
        }
        //转换source 为中文
        for (SystemWebAccess sw: systemWebAccesses) {
            sw.setSource(SourceEnum.getSource(source));
        }
        return systemWebAccesses;
    }

    @Override
    public List<SystemWebAccess> listAll(CountData data) {
        String startTime = data.getStartTime().replace("-", "");
        data.setStartTime(startTime);

        String endTime = data.getEndTime().replace("-", "");
        data.setEndTime(endTime);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String todayTime = df.format(date);

        String source = data.getSource();

        List<SystemWebAccess> systemWebAccesses = new ArrayList<SystemWebAccess>();
        if (StringUtils.equals(endTime, todayTime)) {
            String key = endTime + "_" + source;
            //取当天的数据
            Set<Tuple> tuples = jedis.zrangeWithScores(key, 0, -1);

            //当前时间后一天的所需数据
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
            endTime = df.format(calendar.getTime());
            data.setEndTime(endTime);
            List<SystemWebAccess> list1 = systemWebAccessDao.listAll(data);

            for (Tuple tuple : tuples) {
                    String nameAndId = tuple.getElement();
                    double pv2 = tuple.getScore();
                    SystemWebAccess sa= new SystemWebAccess();
                    String[] split = nameAndId.split("_");
                    String tuser=split[0];
                    String tusername=split[1];
                    sa.setDate(todayTime);
                    sa.setUser(tusername);
                    sa.setUserName(tuser);
                    sa.setPv((int)(pv2));
                    sa.setSource(source);

                list1.add(sa);
            }
            systemWebAccesses=list1;
        } else {
            systemWebAccesses=systemWebAccessDao.listAll(data);
        }
        //转换source 为中文
        for (SystemWebAccess sw: systemWebAccesses) {
            sw.setSource(SourceEnum.getSource(source));
        }
        return systemWebAccesses;
    }
}
