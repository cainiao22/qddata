package com.qding.bigdata.ds.aop;

import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.component.ResultFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/7/17 14:37
 * @description
 */

@Aspect
@Component
public class FilterResultAspectJ {


    @Autowired
    private List<ResultFilter> resultFilterList;


    @Around("@annotation(com.qding.bigdata.ds.annotation.FilterResultAnno) || @within(com.qding.bigdata.ds.annotation.FilterResultAnno)")
    public Object doAfter(ProceedingJoinPoint jp) throws Throwable {
        //拦截的实体类
        Object target = jp.getTarget();
        //拦截的方法名称
        String methodName = jp.getSignature().getName();
        //拦截的方法参数
        Object[] args = jp.getArgs();
        //拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature)jp.getSignature()).getMethod().getParameterTypes();

        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Object result = jp.proceed();

        // 如果返回值为空 就不要让它往下走了
        if(result == null){
            return result;
        }

        FilterResultAnno filterResultAnno = method.getAnnotation(FilterResultAnno.class);
        Class<? extends ResultFilter>[] resultFilters = filterResultAnno.value();

        for (Class<? extends ResultFilter> filter : resultFilters) {
            for (ResultFilter resultFilter : resultFilterList) {
                if(resultFilter.getClass() == filter){
                    //TODO 优化
                    if(jp.getArgs() != null && jp.getArgs().length == 1) {
                        result = resultFilter.doAfter(result, jp.getArgs()[0]);
                    }else {
                        result = resultFilter.doAfter(result, null);
                    }
                }
            }
        }

        return result;
    }

}
