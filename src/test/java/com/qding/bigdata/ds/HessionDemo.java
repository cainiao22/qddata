package com.qding.bigdata.ds;

import java.net.MalformedURLException;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.qding.framework.common.AppUser;
import com.qding.manager.service.IPuserRPCService;
import com.qding.manager.strut.request.AuthorizationInfoRequest;
import com.qding.manager.strut.response.AuthorizationInfoResponse;
import com.qding.manager.strut.response.ManagerBaseResponse;


public class HessionDemo {
	 public static void main(String[] args) throws MalformedURLException, ClassNotFoundException  {

         String url = "http://boss.qdingnet.com/manager-remote/remote/manager";

         HessianProxyFactory pf = new HessianProxyFactory();
		 IPuserRPCService iPuserRPCService = (IPuserRPCService) pf.create(url);
		ManagerBaseResponse<AppUser> response = iPuserRPCService
				.getAppUserBySessionId("58ac7183-0e8e-4366-a959-a52ee46677a8");
		//
		//{"resultObject":{"allProjectVisable":true,"dataroleType":5,"id":"3268","mobile":"18910269755","newId":"8aa55de0591acf3001591ad216410000","orgTypeFlag":"bs","realname":"张杰","username":"jackychang"},"returnInfo":{"code":200,"message":"OK"}}

         System.out.println(JSON.toJSONString(response));

  }
}