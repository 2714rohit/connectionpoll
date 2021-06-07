package com.connectionpoll.connectionpoll_app;

/**
 * Java JDBC Connection pool using Apache commons DBCP2 example program
 * 
 * @author pankaj
 */
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
 
public class DBCP2Demo {
 
    /**private static BasicDataSource dataSource = null;
 
    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/empdb?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("rohit");
 
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);
 
    }*/
	
	private static DataSource dataSource = null;
	 
    static {
 
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "rohit");
 
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("jdbc:mysql://localhost:3306/empdb",
                properties);
 
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
 
        GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(25);
        config.setMaxIdle(10);
        config.setMinIdle(5);
 
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory, config);
        poolableConnectionFactory.setPool(connectionPool);
 
        dataSource = new PoolingDataSource<>(connectionPool);
 
    }
 
public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Connection connection1 = null;
        Connection connection2 = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection1 = dataSource.getConnection();
            connection2 = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from tblemployee");
            while (resultSet.next()) {
                System.out.println("empId:" + resultSet.getInt("empId"));
                System.out.println("empName:" + resultSet.getString("empName"));
                System.out.println("dob:" + resultSet.getDate("dob"));
                System.out.println("designation:" + resultSet.getString("designation"));
            }
        } finally {
 
            resultSet.close();
            statement.close();
            connection.close();
            connection1.close();
            connection2.close();
        }
    }
 
}