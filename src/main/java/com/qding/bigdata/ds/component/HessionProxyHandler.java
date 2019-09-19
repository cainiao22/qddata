package com.qding.bigdata.ds.component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.service.HessionProxyService;
import com.qding.manager.service.IPuserRPCService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanpf on 2017/8/17.
 */
@Component
public class HessionProxyHandler implements InitializingBean {

    private HessionProxyService hessionProxyService;

    @Override
    public void afterPropertiesSet() throws Exception {
        HessionProxyService real = new HessionProxyServiceImpl();
        hessionProxyService = (HessionProxyService) Proxy.newProxyInstance(HessionProxyService.class.getClassLoader(),
                new Class[]{HessionProxyService.class},
                new HessionProxy(real));
    }
    /** 你也是个单例 **/
    public HessionProxyService getHessionProxyService(){
        return hessionProxyService;
    }

}

class HessionProxy<T> implements InvocationHandler {

    private T real;

    public HessionProxy(T real) {
        this.real = real;
    }
    /** 可以保证所有的RPC代理都是单例模式的 **/
    private Map<String, SoftReference> cache = new HashMap<String, SoftReference>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String key = method.getName() + "_" + method.getParameterTypes().length;
        SoftReference result = cache.get(key);
        if(result != null && result.get() != null){
            return result.get();
        }
        Object obj = method.invoke(real, args);
        cache.put(key, new SoftReference(obj));
        return obj;
    }
}

class HessionProxyServiceImpl implements HessionProxyService {

    private HessianProxyFactory pf = new HessianProxyFactory();

    @Override
    public IPuserRPCService getIPuserRPCService() {
        String url = Constant.JBOSS_DOMAIN + "/manager-remote/remote/manager";
        IPuserRPCService da = getRPCServiceProxy(url);
        if (da != null) return da;

        return null;
    }

    private <T> T getRPCServiceProxy(String url) {
        try {
            T da = (T) pf.create(url);
            return da;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
