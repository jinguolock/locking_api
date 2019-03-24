package com.islora.lock.wx.server.db.persistence;

import java.sql.Connection;

import com.islora.lock.wx.server.db.utils.CustomException;

public interface BaseDaoInf<T> {

	public abstract int saveOrUpdate(Object entity)throws CustomException;
	public int update(Object entity)throws CustomException;
	/**
	 * 根据主键删除数据,支持联合主键
	 * @param primaryKey
	 * @return
	 */
	public abstract int delete(Object... primaryKey)throws CustomException;
	
	public T save(Object entity)throws CustomException;
}
