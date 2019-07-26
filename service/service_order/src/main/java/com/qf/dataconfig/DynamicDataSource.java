package com.qf.dataconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * FileName: DynamicDataSource.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 0:54
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return threadLocal.get();
    }

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 传一个数据源的标识
     * @param keyWord
     */
    public static void set(String keyWord){
        threadLocal.set(keyWord);
    }

}
