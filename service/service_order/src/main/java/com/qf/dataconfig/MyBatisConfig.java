package com.qf.dataconfig;

import com.qf.datasource.Db1DataSource;
import com.qf.datasource.Db2DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: MyBatisConfig.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 1:00
 */
@Configuration
public class MyBatisConfig {

    @Autowired
    private Db1DataSource db1DataSource;

    @Autowired
    private Db2DataSource db2DataSource;

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocation;

    /**
     * 配置动态数据源
     *
     * @return
     */
    @Bean
    public DynamicDataSource getDataSource(){

        Map<Object, Object> map = new HashMap<>();
        map.put(db1DataSource.getKeyWord(), db1DataSource.getDataSource());
        map.put(db2DataSource.getKeyWord(), db2DataSource.getDataSource());

        DynamicDataSource datasource = new DynamicDataSource();
        datasource.setDefaultTargetDataSource(db1DataSource.getDataSource());
        datasource.setTargetDataSources(map);

        return datasource;
    }

    /**
     * 配置SqlSessionFactoryBean
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DynamicDataSource getDataSource){

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource);
        try {
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }

}
