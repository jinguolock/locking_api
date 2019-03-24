package com.islora.lock.wx.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.utils.DBProp;


public class DBManager {
	private static BasicDataSource dataSource;
	private static DBManager instance;
	private static Logger logger = LogManager.getLogger(DBManager.class.getName());
	/*private static class SingletonHolder{
        *//**
         * 静态初始化器，由JVM来保证线程安全
         *//*
        private static DBManager instance = new DBManager();
    }*/
	
	private DBManager() {
		logger.info("init db info.......");
		String file = DBProp.getPropStr("file");
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DBProp.getPropStr("driverClassName"));  
		dataSource.setUsername(DBProp.getPropStr("user"));  
        dataSource.setPassword(DBProp.getPropStr("password"));  
        dataSource.setUrl(DBProp.getPropStr("url")+file);  
        dataSource.setInitialSize(Integer.parseInt(DBProp.getPropStr("initialSize"))); // 初始的连接数；  
        dataSource.setMaxTotal(Integer.parseInt(DBProp.getPropStr("maxTotal")));  
        dataSource.setMaxIdle(Integer.parseInt(DBProp.getPropStr("maxIdle")));  
        dataSource.setMaxWaitMillis(Integer.parseInt(DBProp.getPropStr("maxWaitMillis")));  
        dataSource.setMinIdle(Integer.parseInt(DBProp.getPropStr("minIdle")));
        dataSource.setValidationQuery(DBProp.getPropStr("validationQuery"));
        logger.info("end db info.......");
	}

	public static DBManager getInstance() {
		if(instance==null)
			instance=new DBManager();
		return instance;
	}



	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public BasicDataSource getDataSource(){
		return dataSource;
	}
	
	
	
	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	public void closeConnection(Connection conn, PreparedStatement pstmt, Statement stmt, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (conn != null)
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public QueryRunner getQueryRunner(){
		return new QueryRunner(getDataSource());
	}
	 public  List<Map<String, Object>> queryMapList(String sql) {
	        List<Map<String, Object>> result = null;
	        try {
	            result = getQueryRunner().query(sql, new MapListHandler());
	        } catch (SQLException e) {
	            throw new RuntimeException(e.getMessage(), e);
	        }
	        printSQL(sql);
	        return result;
	    }
	// 查询（返回 Array）
    public Object[] queryArray(QueryRunner runner, String sql, Object... params) {
        Object[] result = null;
        try {
            result = runner.query(sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        printSQL(sql);
        return result;
    }
 
    // 查询（返回 ArrayList）
    public  List<Object[]> queryArrayList(QueryRunner runner, String sql, Object... params) {
        List<Object[]> result = null;
        try {
            result = runner.query(sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        printSQL(sql);
        return result;
    }
 
    // 查询（返回 Map）
    public  Map<String, Object> queryMap(QueryRunner runner, String sql, Object... params) {
        Map<String, Object> result = null;
        try {
            result = runner.query(sql, new MapHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        printSQL(sql);
        return result;
    }
 
    // 查询（返回 MapList）
    public  List<Map<String, Object>> queryMapList(QueryRunner runner, String sql, Object... params) {
        List<Map<String, Object>> result = null;
        try {
            result = runner.query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        printSQL(sql);
        return result;
    }
 
    // 查询（返回 Bean）
    public  <T> T queryBean(QueryRunner runner, Class<T> cls, Map<String, String> map, String sql, Object... params) {
        T result = null;
        try {
            if (map!=null) {
                result = runner.query(sql, new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
            } else {
                result = runner.query(sql, new BeanHandler<T>(cls), params);
            }
            printSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }
 
    // 查询（返回 BeanList）
    public  <T> List<T> queryBeanList(QueryRunner runner, Class<T> cls, Map<String, String> map, String sql, Object... params) {
        List<T> result = null;
        try {
            if (map!=null) {
                result = runner.query(sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
            } else {
                result = runner.query(sql, new BeanListHandler<T>(cls), params);
            }
            printSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }
 
    // 查询指定列名的值（单条数据）
    public  Object queryColumn(QueryRunner runner, String column, String sql, Object... params) {
        Object result = null;
        try {
            result = runner.query(sql, new ScalarHandler<Object>(column), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        printSQL(sql);
        return result;
    }
 
    // 查询指定列名的值（多条数据）
    public  <T> List<T> queryColumnList(QueryRunner runner, String column, String sql, Object... params) {
        List<T> result = null;
        try {
            result = runner.query(sql, new ColumnListHandler<T>(column), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        printSQL(sql);
        return result;
    }
 
    // 查询指定列名对应的记录映射
    public  <T> Map<T, Map<String, Object>> queryKeyMap(QueryRunner runner, String column, String sql, Object... params) {
        Map<T, Map<String, Object>> result = null;
        try {
            result = runner.query(sql, new KeyedHandler<T>(column), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        printSQL(sql);
        return result;
    }
 
    // 更新（包括 UPDATE、INSERT、DELETE，返回受影响的行数）
    public  int update(QueryRunner runner, Connection conn, String sql, Object... params) {
        int result = 0;
        try {
            if (conn != null) {
                result = runner.update(conn, sql, params);
            } else {
                result = runner.update(sql, params);
            }
            printSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }
 
    private  void printSQL(String sql) {
        System.out.println(sql);
    }
}
