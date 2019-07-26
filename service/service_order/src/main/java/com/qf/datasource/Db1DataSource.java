package com.qf.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FileName: Db1DataSource.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 0:48
 */
@Component
@ConfigurationProperties(prefix = "spring.orderdb1.datasource")
public class Db1DataSource extends BaseDataSource {
}
