package com.wossha.social.configure;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class OracleConfig implements WebMvcConfigurer {
	
	@Value("${spring.datasource.url}")
	private String datasourceUrl;
	
	@Value("${spring.datasource.username}")
	private String datasourceUsername;
	
	@Value("${spring.datasource.password}")
	private String datasourcePassword;
	
	@Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(datasourceUsername);
        dataSource.setPassword(datasourcePassword);
        dataSource.setURL(datasourceUrl);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }
	
	
}
