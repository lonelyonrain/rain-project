package com.time.scenery.rain.space.mybatis.dao;

import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisSessionFactory {
	private final static String configurationFile = "mybatis-config.xml"; // 配置文件
	private static SqlSessionFactory factory_oracle = null;
	private static SqlSessionFactory factory_sqlserver = null;
	private static SqlSessionFactory factory_mysql = null;

	public static void rebuildSeesionFactory(String factoryStr) throws Exception {
		System.out.println("Trying to find [" + configurationFile + "]");
		InputStream is = MybatisSessionFactory.class.getResourceAsStream("/" + configurationFile);
		if (null == is) {
			System.err.println("MybatisSessionFactory失败!!!!");
			return;
		}
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		switch (factoryStr) {
		case "development_oracle":
			if (factory_oracle == null) {
				factory_oracle = builder.build(is, factoryStr);
			}
			break;
		case "development_sqlserver":
			if (factory_sqlserver == null) {
				factory_sqlserver = builder.build(is, factoryStr);
			}
			break;
		case "development_mysql":
			if (factory_mysql == null) {
				factory_mysql = builder.build(is, factoryStr);
			}
			break;
		default:
			break;
		}
		builder = null;
	}

	/**
	 * 打开数据库连接
	 * 
	 * @param1 session1
	 */
	public static SqlSession openSession_oracle() {
		try {
			if (null == factory_oracle) {
				rebuildSeesionFactory("development_oracle");
			}
			if (null == factory_oracle) {
				System.err.println("oracle工厂模式建立失败，请检查是否有配置数据源！！！");
				return null;
			}
			if (factory_oracle.getConfiguration().getEnvironment()==null) {
				System.err.println("mysql工厂模式建立失败，请检查是否有配置数据源！！！");
				return null;
			}
			return factory_oracle.openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SqlSession openSession_sqlserver() {
		try {
			if (null == factory_sqlserver) {
				rebuildSeesionFactory("development_sqlserver");
			}
			if (null == factory_sqlserver) {
				System.err.println("sqlserver工厂模式建立失败，请检查是否有配置数据源！！！");
				return null;
			}
			if (factory_sqlserver.getConfiguration().getEnvironment()==null) {
				System.err.println("mysql工厂模式建立失败，请检查是否有配置数据源！！！");
				return null;
			}
			return factory_sqlserver.openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SqlSession openSession_mysql() {
		try {
			if (null == factory_mysql) {
				rebuildSeesionFactory("development_mysql");
			}
			if (null == factory_mysql) {
				System.err.println("mysql工厂模式建立失败，请检查是否有配置数据源！！！");
				return null;
			}
			if (factory_mysql.getConfiguration().getEnvironment()==null) {
				System.err.println("mysql工厂模式建立失败，请检查是否有配置数据源！！！");
				return null;
			}
			return factory_mysql.openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void closeSession(SqlSession session) {
		session.close();
	}
	//
}
