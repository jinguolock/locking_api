package com.islora.lock.wx.server.db.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;

public class GenDao {
	/** sqlite 数据库目录 */
    private String dbPath = "";
    private Connection connection = null;
    private Statement statement;
    /**保存到...*/
    private File savaFile;
    /**包名*/
    private String packageName = "";
     
     
    /**
     * @说明 Writer写入文件 保存为 UTF-8格式
     * @author xuzhen
     * @param content
     *            写入的数据
     * @return void
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IOException
     */
    public  boolean fileWriters_LOG() throws ClassNotFoundException, SQLException{
        /** 获取该表中的所有字段*/
    	String filepath = "D:"+File.separator+"实体类_目录"+File.separator+"安监";
    	File file = new File(filepath);
    	if(!file.exists()){
    		file.mkdirs();
    	}
    	if(file.isDirectory()){
    		String[] filelist = file.list();
    		for (int i = 0; i < filelist.length; i++) {
    			File readfile = new File(filepath + File.separator + filelist[i]);
    			String trueFileName = readfile.getName().substring(0,readfile.getName().indexOf("."));
    			System.out.println(trueFileName);
    			String fileName = trueFileName+"Dao.java";
    			String fileNameTmp = trueFileName+"Dao";
    	        FileOutputStream f;
    	        OutputStreamWriter osw = null;
    	        try {
    	            f = new FileOutputStream(filepath+File.separator+fileName, true);
    	            osw = new OutputStreamWriter(f, "UTF-8");
    	            StringBuffer javaCode = new StringBuffer();
    	            javaCode.append("package com.zyhq.tms.gate.service.core.db.dao;"+"\n");
    	            javaCode.append("import com.zyhq.tms.gate.service.core.db.entity."+trueFileName+";\n");
    	            javaCode.append("import com.zyhq.tms.gate.service.core.db.persistence.BaseDao;\n");
    	            javaCode.append("/** 继承BaseDao基类 */ \n");
    	            javaCode.append("public class "+fileNameTmp+" extends BaseDao<"+trueFileName+">{ \n");
    	            javaCode.append("/** 可以自定义其它数据库操作 */ \n");
    	            javaCode.append("\n\n\n\n");
    	            javaCode.append("} \n");
    	            osw.write(javaCode.toString());
    	        } catch (FileNotFoundException e) {
    	            // TODO Auto-generated catch block
    	            e.printStackTrace();
    	        } catch (UnsupportedEncodingException e) {
    	            // TODO Auto-generated catch block
    	            e.printStackTrace();
    	        } catch (IOException e) {
    	            // TODO Auto-generated catch block
    	            e.printStackTrace();
    	        } finally {
    	            try {
    	                osw.flush();
    	            } catch (IOException e) {
    	                // TODO Auto-generated catch block
    	                e.printStackTrace();
    	            }
    	        }
    		}
    	}
        return true;
    }
    
    public static void main(String[] args) {
    	GenDao dao = new GenDao();
    	try {
			dao.fileWriters_LOG();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
