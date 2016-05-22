package com.walmart;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class DBUtils {
	private static final DBUtils INSTANCE = new DBUtils();
	private static DataSource dataSource = null;	
	private static PropertiesConfiguration config = null;
	
	private DBUtils() { }

	public static void start() throws Exception {		
		INSTANCE.loadProperties();
		dataSource = INSTANCE.setupDataSource();
	}

	public static void stop() {		
		config.clear();
		dataSource = null;
	}

	public static Connection getConnection() throws Exception {
		final Connection conn = dataSource.getConnection(); 
		Validate.isTrue(null != conn && !conn.isClosed(), "connection is closed.");

		return conn;
	}

	public static void safeClose(final ResultSet resultSet) throws Exception {
		if (null != resultSet) { resultSet.close(); }
	}

	public static void safeClose(final PreparedStatement pstmt) throws Exception {
		if (null != pstmt) { pstmt.close(); }
	}
	
	public static void safeClose(final Connection conn) throws Exception {
		if (null != conn) { conn.close(); }
	}
	
	private DataSource setupDataSource() throws Exception {
		final String database = config.getString("database");
		Validate.notBlank(database, "database cannot be blank.");		
		final String host = config.getString("host");
		Validate.notBlank(host, "host cannot be blank.");
		final String port = config.getString("port");
		Validate.notBlank(port, "port cannot be blank.");
		final String scheme = config.getString("scheme");
		Validate.notBlank(scheme, "scheme cannot be blank.");

		final String parameters = config.getString("parameters");
		final StringBuilder connectURI = new StringBuilder();
		connectURI.append("jdbc")
		  .append(":").append(database)
		  .append(":").append("//").append(host).append(":").append(port).append("/").append(scheme)
		  .append(!StringUtils.isBlank(parameters) ? "?" + parameters : "");

		// TODO (amado): Big No-no i understand!  This is just a demo.
		final String password = config.getString("password");
		Validate.notBlank(password, "password cannot be blank.");
		final Properties properties=ConfigurationConverter.getProperties(config);
		properties.setProperty("password", password);

		final ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI.toString(), properties);
        final PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        final ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);

        return new PoolingDataSource<PoolableConnection>(connectionPool);
	}

	private void loadProperties() throws Exception {
		final String dataSource = System.getProperty("dataSource");
		Validate.notBlank(dataSource, "dataSource environment variable undefined.");

		config = new  PropertiesConfiguration(new File(dataSource)) ;
		Validate.notNull(config, "dataSource properties cannot be empty.");

		config.setReloadingStrategy (new FileChangedReloadingStrategy()) ;
	}

}