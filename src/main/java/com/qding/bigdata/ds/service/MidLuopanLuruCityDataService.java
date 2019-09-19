package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.MidLuopanLuruCityData;
import com.qding.bigdata.ds.model.MidLuopanRengongData;

import java.util.List;
import java.util.Map;

public interface MidLuopanLuruCityDataService extends  BaseService<MidLuopanLuruCityData>{

    /**
     * 查询录入城市列表不带分页
     * @param param
     * @return
     */
    public List<MidLuopanLuruCityData> listNopage(MidLuopanLuruCityData param);


    public int updateByParam(MidLuopanLuruCityData param);

    public int batchUpdate(List<MidLuopanLuruCityData>  param);

}
