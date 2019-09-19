package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.model.MidLuopanRengongData;

import java.util.List;
import java.util.Map;

public interface MidLuopanRengongDataService extends  BaseService<MidLuopanRengongData>{

    /**
     * 查询人工录入列表不带分页
     * @param param
     * @return
     */
    public List<MidLuopanRengongData> listNopage(MidLuopanRengongData param);


    /**
     * 将操作人也获取到,存入数据库中
     * @param midluopanrengongdatas
     * @param username
     * @return
     */
    public Map<String,Integer> addOrUpdate(List<MidLuopanRengongData> midluopanrengongdatas,String username);

    /**
     * 社区下拉列表查询
     * @param param
     * @return
     */
    public  List<Map<String,Object>> selectProjectByQ(Map<String,Object> param);

    /**
     * 物业云实施状态录入
     * @param midluopanrengongdata
     * @param username
     * @return
     */
    public Map<String,Object> addOrUpdatewuyeyun(MidLuopanRengongData midluopanrengongdata,String username);

    /**
     * 查询一条罗盘人工数据 里边带一个长度为3的集合
     * @param midluopanrengongdata
     * @return
     */
    MidLuopanRengongData selectOne(MidLuopanRengongData midluopanrengongdata);

    /**
     * 查询任务绑定户数集合 依据月份查询 不管是否存在,均返回八条数据
     * @param param
     * @return
     */
    public List<MidLuopanRengongData> listRenwuMlrds(MidLuopanRengongData param);

    public Map<String,Object> selectOneProjectUUid(String projectId);

}
