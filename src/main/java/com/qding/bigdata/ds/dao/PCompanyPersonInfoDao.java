package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.PCompanyPersonInfo;
import com.qding.bigdata.ds.model.UserDetailParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-17 下午6:31
 * @Description TODO
 **/

public interface PCompanyPersonInfoDao {

    Double queryAllPerson();

    Double queryAllRegPerson();

    Double queryRegPersonByCondition(@Param("qrYcompanyId") String qrYcompanyId, @Param("companyId") String companyId);

    List<Map<String, Object>> queryAllPersonByCompany();

    List<Map<String, Object>> queryAllRegPersonByCompany();

    List<Map<String, Object>> queryPersonByCompany(@Param("qrYcompanyId") String qrYcompanyId);

    List<Map<String, Object>> queryRegPersonByCompany(@Param("qrYcompanyId") String qrYcompanyId);


    Long queryAllPersonByQryCompany(@Param("qrYcompanyId") String qrYcompanyId);

    Long queryAllRegPersonByQryCompany(@Param("qrYcompanyId") String qrYcompanyId);

    List<Map<String, Object>> queryPersonDetails(@Param("param") UserDetailParam param);
}
