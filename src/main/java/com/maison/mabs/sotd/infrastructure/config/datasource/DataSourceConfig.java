package com.maison.mabs.sotd.infrastructure.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableScheduling // Todo: Add scheduled job that checks the health of replica instance
public class DataSourceConfig {

	@Bean
	@ConfigurationProperties("spring.datasource.primary")
	public DataSourceProperties primaryProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.replica")
	public DataSourceProperties replicaProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.primary.hikari")
	public HikariDataSource primaryDataSource(@Qualifier("primaryProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.replica.hikari")
	public HikariDataSource replicaDataSource(@Qualifier("replicaProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	/*
	 * Holds both pools in a map and picks one per connection request based on the current
	 * transaction's read-only flag (see ReadWriteRoutingDataSource). Exposed as a
	 * concrete type so the health checker can flip its flag.
	 */
	@Bean
	public ReadWriteRoutingDataSource routingDataSource(@Qualifier("primaryDataSource") DataSource primary,
			@Qualifier("replicaDataSource") DataSource replica) {

		ReadWriteRoutingDataSource routing = new ReadWriteRoutingDataSource();
		routing.setTargetDataSources(Map.of(ReadWriteRoutingDataSource.DataSourceType.PRIMARY, primary,
				ReadWriteRoutingDataSource.DataSourceType.REPLICA, replica));
		routing.setDefaultTargetDataSource(primary);
		return routing;
	}

	/*
	 * CRITICAL: without this proxy, the JDBC connection is acquired when the transaction
	 * STARTS — before Spring has set the read-only flag — so every query would route to
	 * primary. The proxy defers acquisition until the first actual statement, at which
	 * point the flag is visible.
	 */
	@Bean
	@Primary
	public DataSource dataSource(ReadWriteRoutingDataSource routingDataSource) {
		return new LazyConnectionDataSourceProxy(routingDataSource);
	}

}
