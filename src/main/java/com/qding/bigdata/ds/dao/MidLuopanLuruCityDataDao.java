package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.MidLuopanLuruCityData;
import com.qding.bigdata.ds.model.MidLuopanRengongData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MidLuopanLuruCityDataDao extends BaseDao<MidLuopanLuruCityData>{

    /**
     * 查询所有人工录入数据 不带分页
     * @param midLuopanRengongData
     * @return
     */
    List<MidLuopanLuruCityData> listNopage(MidLuopanLuruCityData midLuopanRengongData);

    /**
     * 依据参数更新数据,没有id,只会更新value的值和更新时间
     * @param midLuopanLuruCityData
     * @return
     */
    int updateByParam(MidLuopanLuruCityData midLuopanLuruCityData);

    /**
     * 依据参数批量更新数据,没有id,只会更新value的值和更新时间
     * @param
     * @return
     */
    int batchUpdate(@Param("list") List<MidLuopanLuruCityData>  list);

}