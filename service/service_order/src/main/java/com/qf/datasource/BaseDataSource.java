package com.qf.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

import javax.sql.DataSource;

/**
 * FileName: BaseDataSource.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 0:44
 */
@Data
public class BaseDataSource {

    protected String url;
    protected String username;
    protected String password;
    protected String driverClassName;
    protected String keyWord;

    public DataSource getDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);
        return hikariDataSource;
    }
}
