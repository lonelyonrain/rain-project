package com.time.scenery.rain.spring.utils;


import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class DataSourceAspect implements MethodBeforeAdvice,AfterReturningAdvice{  
    public void afterReturning(Object returnValue, Method method,  
            Object[] args, Object target) throws Throwable {  
        // TODO Auto-generated method stub  
        DataSourceContextHolder.clearDataSourceType();  
    }  
  
    public void before(Method method, Object[] args, Object target)  
            throws Throwable {  
      
//        if (method.isAnnotationPresent(DataSource.class)){  
//            DataSource datasource = method.getAnnotation(DataSource.class);  
//            DataSourceContextHolder.setDataSourceType(datasource.name());  
//        }else {  
//            DataSourceContextHolder.setDataSourceType(DataSourceContextHolder.getDataSourceType());  
//        }       
    }  
}  
