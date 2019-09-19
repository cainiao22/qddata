package com.qding.bigdata.ds.user;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.qding.manager.service.IPuserRPCService;
import com.qding.manager.strut.request.AuthorizationInfoRequest;
import com.qding.manager.strut.response.AuthorizationInfoResponse;
 

public class HessionDemo {
	 public static void main(String[] args) throws MalformedURLException, ClassNotFoundException  {

         String url = "http://boss.qdingnet.com/manager-remote/remote/manager";

         HessianProxyFactory pf = new HessianProxyFactory();
		IPuserRPCService da = (IPuserRPCService) pf.create(url);
         AuthorizationInfoRequest req = new AuthorizationInfoRequest();
         req.setSessionId("8cc03880-c5fb-42e0-bff2-2e93340131bf");
         AuthorizationInfoResponse authorizationInfo = da.getAuthorizationInfo(req);
         System.out.println(authorizationInfo);


  }
}
