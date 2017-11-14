package com.time.scenery.rain.spring.utils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  
public class DynamicDataSource extends AbstractRoutingDataSource {  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return DataSourceContextHolder.getDataSourceType();  
    }  
}  