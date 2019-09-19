package com.qding.bigdata.ds.aop.dynamicsource;

import com.qding.bigdata.ds.annotation.DynamicDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @author yanpf
 * @date 2019/5/14 11:01
 * @description
 */

@Aspect
@Component
public class DataSourceSwitchAspectJ {

    ExpressionParser parser = new SpelExpressionParser();
    LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(com.qding.bigdata.ds.annotation.DynamicDataSource)")
    public void beforMethod(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        String[] params = u.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }

        DynamicDataSource annotation = method.getAnnotation(DynamicDataSource.class);

        String dataSource;
        try {
            dataSource = parser.parseExpression(annotation.value()).getValue(context, String.class);
        }catch (SpelEvaluationException e){
            dataSource = annotation.value();
        }

        logger.info("dataSource取值为: " + dataSource);
        DataSourceContextHolder.setDataSource(dataSource);
    }

    @After("@annotation(com.qding.bigdata.ds.annotation.DynamicDataSource)")
    public void afterMethod(JoinPoint joinPoint){
        DataSourceContextHolder.clearDataSource();
    }

}
