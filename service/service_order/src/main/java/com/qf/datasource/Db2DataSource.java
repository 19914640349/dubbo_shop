package com.qf.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FileName: Db2DataSource.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 0:51
 */
@Component
@ConfigurationProperties(prefix = "spring.orderdb2.datasource")
public class Db2DataSource extends BaseDataSource {
}
