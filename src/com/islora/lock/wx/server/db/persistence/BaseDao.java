package com.islora.lock.wx.server.db.persistence;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;


import com.islora.lock.wx.server.db.DBManager;
import com.islora.lock.wx.server.db.utils.CustomException;
import com.islora.lock.wx.server.db.utils.Reflections;

public class BaseDao<T> implements BaseDaoInf{
	
	
	
	/**
	 * 实体类类型(由构造方法自动赋值)
	 */
	protected Class<T> entityClass;
	
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public BaseDao() {
		entityClass = Reflections.getClassGenricType(getClass());
	}
	
	public Connection getConnection(){
		try {
			return getDataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DBManager getDBManager(){
		return DBManager.getInstance();
	}
	
	public BasicDataSource getDataSource(){
		return getDBManager().getDataSource();
	}
	
	public QueryRunner getQueryRunner(){
		return new QueryRunner(getDataSource());
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

	// 查询（返回 Array）
    public Object[] queryArray(String sql, Object... params)throws CustomException{
        Object[] result = null;
        try {
            result = getQueryRunner().query(sql, new ArrayHandler(), params);
            return result;
        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
        
    }
 
    // 查询（返回 ArrayList）
    public  List<Object[]> queryArrayList(String sql, Object... params) throws CustomException{
        List<Object[]> result = null;
        try {
            result = getQueryRunner().query(sql, new ArrayListHandler(), params);
            return result;
        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
 
    // 查询（返回 Map）
    public  Map<String, Object> queryMap(String sql, Object... params)throws CustomException {
        Map<String, Object> result = null;
        try {
            result = getQueryRunner().query(sql, new MapHandler(), params);
            return result;
        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
 
    // 查询（返回 MapList）
    public  List<Map<String, Object>> queryMapList(String sql, Object... params)throws CustomException {
        List<Map<String, Object>> result = null;
        try {
            result = getQueryRunner().query(sql, new MapListHandler(), params);
            return result;
        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
 
    // 查询（返回 Bean）
    public T queryBean(Class<T> cls, Map<String, String> map, String sql, Object... params) throws CustomException{
        T result = null;
        try {
            if (map!=null) {
                result = getQueryRunner().query(sql, new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
            } else {
                result = getQueryRunner().query(sql, new BeanHandler<T>(cls), params);
            }
            printSQL(sql);
            return result;
        } catch (SQLException e) {
             
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
 
    // 查询（返回 BeanList）
    public List<T> queryBeanList(Class<T> cls, Map<String, String> map, String sql, Object... params) throws CustomException{
        List<T> result = null;
        try {
            if (map!=null) {
                result = getQueryRunner().query(sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
            } else {
                result = getQueryRunner().query(sql, new BeanListHandler<T>(cls), params);
            }
            printSQL(sql);
            return result;
        } catch (SQLException e) {
             
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
 
    // 查询指定列名的值（单条数据）
    public Object queryColumn(String column, String sql, Object... params) throws CustomException{
        Object result = null;
        try {
            result = getQueryRunner().query(sql, new ScalarHandler<Object>(column), params);
            printSQL(sql);
            return result;
        } catch (SQLException e) {
             
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
    
    public Object query(String sql){
    	Object result = null;
    	try {
			result =  getQueryRunner().query(sql, new ScalarHandler<Object>());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return result;
    }
 
    // 查询指定列名的值（多条数据）
    public List<T> queryColumnList(String column, String sql, Object... params)throws CustomException {
        List<T> result = null;
        try {
            result = getQueryRunner().query(sql, new ColumnListHandler<T>(column), params);
            printSQL(sql);
            return result;
        } catch (SQLException e) {
             
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
 
    // 查询指定列名对应的记录映射
    public Map<T, Map<String, Object>> queryKeyMap(String column, String sql, Object... params)throws CustomException {
        Map<T, Map<String, Object>> result = null;
        try {
            result = getQueryRunner().query(sql, new KeyedHandler<T>(column), params);
            printSQL(sql);
            return result;
        } catch (SQLException e) {
             
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(getConnection());
        }
    }
 
 // 更新（包括 UPDATE、INSERT、DELETE，返回受影响的行数）
    public Map<String, Object> insert(String sql, Object... params)throws CustomException {
        Connection conn= getConnection();
        Map<String, Object> result = null;
        try {
        	ResultSetHandler<Map<String, Object>> rsh = new MapHandler();
        	result = getQueryRunner().insert(conn, sql, rsh,params);
            return result;
        } catch (SQLException e) {
             
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(conn);
        }
    }
    
    
    // 更新（包括 UPDATE、INSERT、DELETE，返回受影响的行数）
    public int update(String sql, Object... params)throws CustomException {
        int result = 0;
        Connection conn= getConnection();
        try {
            result = getQueryRunner().update(conn, sql, params);
            printSQL(sql);
            return result;
        } catch (SQLException e) {
             
            throw new CustomException(e.getMessage(), e);
        }finally{
        	DbUtils.closeQuietly(conn);
        }
    }
 
    /*
    * 执行批量sql语句 
    * @param sql sql语句 
    * @param params 二维参数数组 
    * @return 受影响的行数的数组 
    */ 
   public int[] batchUpdate(String sql, Object[][] params) { 
       int[] affectedRows = new int[0]; 
       try { 
           affectedRows = getQueryRunner().batch(sql, params); 
       } catch (SQLException e) { 
    	  e.printStackTrace();
       } 
       return affectedRows; 
   }
    
    private  void printSQL(String sql) {
    	//logger.info("SQL: " + sql);
    }

    /**
     * @category 返回表中所有的主键
     * @param c
     *            Person.class
     * @return list
     */
    private List<String> getPrimaryKeys()throws CustomException {
      String tableName = convertToTableName(entityClass.getSimpleName());// person 表的名字
      ResultSet rs = null;
      List<String> list = new ArrayList<String>();
      Connection conn= getConnection();
      try {
        rs = conn.getMetaData().getPrimaryKeys(null, null,tableName);
        while (rs.next()) {
          list.add(rs.getString(4));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }finally{
    	  DbUtils.closeQuietly(conn, null, rs);
      }
      return list;
    }
    
	@Override
	public Map<String, Object> save(Object entity)throws CustomException {
		Map<String, Object> resutl = null;
		if (entity == null || entityClass.getSimpleName().equals(entity.getClass().getName())){
			return resutl;
		}
        Field[] fields = entity.getClass().getDeclaredFields();  
        int fieldSize = fields.length;  
        String tableName = convertToTableName(entityClass.getSimpleName());// person
        StringBuffer sql0 = new StringBuffer("insert into " + tableName);
        StringBuffer sql1 = new StringBuffer(" values(");
        String sql = "";
        try {
        	List attrs = new ArrayList(fieldSize);
        	List params = new ArrayList(fieldSize);		//初始化参数数组长度
            for (int j = 0; j < fieldSize; j++) {
            	fields[j].setAccessible(true);
//            	if(!"id".equals(fields[j].getName())){
            		attrs.add(fields[j].getName());
            		sql1.append("?,");
            		params.add(fields[j].get(entity));
//            	}
            }
            String attr = StringUtils.join(attrs, ",");
            sql0.append("(");
            sql0.append(attr);
            sql0.append(")");
            sql1.deleteCharAt(sql1.length() - 1);  
            sql1.append(")");
            sql = sql0.toString()+sql1.toString();
            System.out.println(sql);
            resutl = insert(sql.toString(), params.toArray());
        } catch (Exception e) {
        	 
            throw new CustomException(e.getMessage(), e);
        }
        return resutl;
	}
    
    
	@Override
	public int saveOrUpdate(Object entity)throws CustomException {
		int p = 0;
		if (entity == null || entityClass.getSimpleName().equals(entity.getClass().getName())){
			return p;
		}
        Field[] fields = entity.getClass().getDeclaredFields();  
        int fieldSize = fields.length;  
        String tableName = convertToTableName(entityClass.getSimpleName());// person
        StringBuffer sql0 = new StringBuffer("replace into " + tableName);
        StringBuffer sql1 = new StringBuffer(" values(");
        String sql = "";
        try {
        	List attrs = new ArrayList(fieldSize);
        	List params = new ArrayList(fieldSize);		//初始化参数数组长度
        	int id=-1;
            for (int j = 0; j < fieldSize; j++) {
            	fields[j].setAccessible(true);
            	//if(!"id".equals(fields[j].getName())){
            		attrs.add(fields[j].getName());
            		sql1.append("?,");  
            		params.add(fields[j].get(entity));
            	//}else{
            	//	id=(Integer)fields[j].get(entity);
            	//}
            }
            
            String attr = StringUtils.join(attrs, ",");
            sql0.append("(");
            sql0.append(attr);
            sql0.append(")");
            sql1.deleteCharAt(sql1.length() - 1);  
            sql1.append(")");
            sql = sql0.toString()+sql1.toString();
            System.out.println(sql);
            p = update(sql.toString(), params.toArray());
        } catch (Exception e) {
        	 
            throw new CustomException(e.getMessage(), e);
        }
        return p;
	}
	@Override
	public int update(Object entity)throws CustomException {
		int p = 0;
		if (entity == null || entityClass.getSimpleName().equals(entity.getClass().getName())){
			return p;
		}
        Field[] fields = entity.getClass().getDeclaredFields();  
        int fieldSize = fields.length;  
        String tableName = convertToTableName(entityClass.getSimpleName());// person
      //UPDATE "main"."tms_projector" SET "ipAddr"='10.201.15.211' WHERE ("id"=2)
        StringBuffer sql0 = new StringBuffer("update " + tableName);
        StringBuffer sql1 = new StringBuffer(" SET ");
        String sql = "";
        try {
        	int id=-1;
        	List attrs = new ArrayList(fieldSize);
        	List params = new ArrayList(fieldSize);		//初始化参数数组长度
            for (int j = 0; j < fieldSize; j++) {
            	fields[j].setAccessible(true);
            	if(!"id".equals(fields[j].getName())){
            		attrs.add(fields[j].getName());
            		sql1.append(fields[j].getName()+"=?,");  
            		params.add(fields[j].get(entity));
            	}else{
            		id=(Integer)fields[j].get(entity);
            	}
            }
          //  String attr = StringUtils.join(attrs, ",");
          //  sql0.append("(");
         //   sql0.append(attr);
          //  sql0.append(")");
            sql1.deleteCharAt(sql1.length() - 1);  
          //  sql1.append(")");
            sql = sql0.toString()+sql1.toString()+" where id="+id;
            System.out.println(sql);
            p = update(sql.toString(), params.toArray());
        } catch (Exception e) {
        	 
            throw new CustomException(e.getMessage(), e);
        }
        return p;
	}
	@Override
	public int delete(Object... primaryKey)throws CustomException {
		String tableName = convertToTableName(entityClass.getSimpleName());// person 表的名字
	    List<String> primaryKeysList = getPrimaryKeys();
	    StringBuilder sb = new StringBuilder("delete from " + tableName
	        + " where ");
	    for (int i = 0; i < primaryKeysList.size(); i++) {
	      sb.append(primaryKeysList.get(i) + "=? and ");
	    }
	    sb.delete(sb.length() - 4, sb.length());
	    int p = 0;
	    try {
	      p = update(sb.toString(), primaryKey);
	    } catch (Exception e) {
	    	 
            throw new CustomException(e.getMessage(), e);
	    }
	    return p;
	}
	public int deleteById(int id)throws CustomException {
		String tableName = convertToTableName(entityClass.getSimpleName());// person 表的名字
	    
	    StringBuilder sb = new StringBuilder("delete from " + tableName
	        + " where id="+id);

	    int p = 0;
	    try {
	      p = update(sb.toString(), new Object[]{});
	    } catch (Exception e) {
	    	 
            throw new CustomException(e.getMessage(), e);
	    }
	    return p;
	}
	
	public List<T> findAll(){
		String tableName;
		try {
			tableName = convertToTableName(entityClass.getSimpleName());
			String sql = "select * from "+tableName;
			return this.queryBeanList(entityClass, null, sql, null);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public T findById(int id){
		String tableName;
		try {
			tableName = convertToTableName(entityClass.getSimpleName());
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ").append(tableName).append(" where id=").append(id);
			return this.queryBean(entityClass, null, sb.toString(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<T> findByIds(Object[] ids){
		List<T> list = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ").append(convertToTableName(entityClass.getSimpleName())).append(" where id in (");
			for (int i = 0; i < ids.length; i++) {
				sql.append(ids[i]).append(',');
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
			list = this.queryBeanList(entityClass, null, sql.toString(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public T findByQueryName(String queryName,String queryValue){
		String tableName;
		try {
			tableName = convertToTableName(entityClass.getSimpleName());
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ").append(tableName).append(" where").append(' ').append(queryName).append("='").append(queryValue).append("'");
			return this.queryBean(entityClass, null, sb.toString(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String convertToTableName(String entityName)throws CustomException{
		StringBuffer sb = new StringBuffer();  
		if(StringUtils.isNoneBlank(entityName)){
			for(int i=0;i<entityName.length();i++){  
	            char c = entityName.charAt(i);  
	            if(Character.isUpperCase(c)){
	            	if(i!=0){
	            		sb.append("_"+Character.toLowerCase(c));
	            	}else{
	            		sb.append(Character.toLowerCase(c));
	            	}
	            }else{  
	                sb.append(c);   
	            }  
	        }
			return sb.toString();
		}
		return null;
	}
	
	public String getTableName()throws CustomException{
		String entityName = entityClass.getSimpleName();
		StringBuffer sb = new StringBuffer();  
		if(StringUtils.isNoneBlank(entityName)){
			for(int i=0;i<entityName.length();i++){  
	            char c = entityName.charAt(i);  
	            if(Character.isUpperCase(c)){
	            	if(i!=0){
	            		sb.append("_"+Character.toLowerCase(c));
	            	}else{
	            		sb.append(Character.toLowerCase(c));
	            	}
	            }else{  
	                sb.append(c);   
	            }  
	        }
			return sb.toString();
		}
		return null;
	}
}
