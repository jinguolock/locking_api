/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcLockDao extends OwnerBaseDao<LcLock>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	public void updateLockInfo(int lockId,int apartmentId,String type,int keyExpireInterval)throws CustomException{
		String sql = "UPDATE "+super.getTableName()+" SET keyExpireInterval="+keyExpireInterval+", ltype='"+type+"', apartmentId="+apartmentId+" where id="+lockId;
		System.out.println(sql);
		super.update(sql, new Object[] {});
	}
	
	public void updateLockOp(int lockId)throws CustomException{
		String sql = "UPDATE "+super.getTableName()+" set version = version + 1 where id ="+lockId;
		System.out.println(sql);
		super.update(sql, new Object[] {});
	}
	public LcLock getLockByApartment(int ApartmentId)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where apartmentId="+ApartmentId;
		LcLock tcc=super.queryBean(LcLock.class, null, sql, new Object[]{});
	    return tcc;
	}
	public LcLock getLockBySn(String sn)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where sn='"+sn+"'";
		LcLock tcc=super.queryBean(LcLock.class, null, sql, new Object[]{});
	    return tcc;
	}
}
