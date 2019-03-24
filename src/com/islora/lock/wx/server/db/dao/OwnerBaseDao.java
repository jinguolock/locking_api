package com.islora.lock.wx.server.db.dao;

import java.util.List;

import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

public class OwnerBaseDao<T> extends BaseDao<T> {
	public List<T> findByOwnerId(int ownerId){
		String tableName;
		try {
			tableName = super.convertToTableName(super.entityClass.getSimpleName());
			String sql = "select * from "+tableName+" where ownerId="+ownerId;
			return super.queryBeanList(entityClass, null, sql, null);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteByOwnerId(int ownerId)throws CustomException{
		String sql = "delete from "+super.getTableName()+" where ownerId="+ownerId;
		super.update(sql, new Object[]{});
	}
	
}
