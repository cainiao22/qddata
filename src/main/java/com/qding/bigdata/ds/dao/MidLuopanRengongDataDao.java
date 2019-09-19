package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.MidLuopanRengongData;

import java.util.List;
import java.util.Map;

public interface MidLuopanRengongDataDao extends BaseDao<MidLuopanRengongData>{
    int insert(MidLuopanRengongData record);

    int insertSelective(MidLuopanRengongData record);

    /**
     * 查询所有人工录入数据 不带分页
     * @param midLuopanRengongData
     * @return
     */
    List<MidLuopanRengongData> listNoPage(MidLuopanRengongData midLuopanRengongData);

    /**
     * 依据参数更新数据,没有id,只会更新value的值和更新时间
     * @param midLuopanRengongData
     * @return
     */
    int updateByParam(MidLuopanRengongData midLuopanRengongData);

    /**
     * 模糊搜索查询社区下拉列表
     * @param param
     * @return
     */
    List<Map<String,Object>> selectProjectByQ(Map<String,Object> param);

    /**
     * 依据uuid查询一条社区
     * @param uuid
     * @return
     */
    Map<String,Object> getProjectById(String uuid);

    /**
     * 查询一条社区的uuid
     * @return
     */
    Map<String,Object>  selectOneProjectUUid(Map<String,Object> param);


    /**
     * 单独校验是否存在 依据社区id 城市id 类型  时间 和物业公司名称
     * @param midLuopanRengongData
     * @return
     */
    Integer checkCount(MidLuopanRengongData midLuopanRengongData);

    /**
     * 物业云设施状态更新数据 参数加上社区id
     * @param midLuopanRengongData
     * @return
     */
    int updateToWuyeyunByParam(MidLuopanRengongData midLuopanRengongData);

}