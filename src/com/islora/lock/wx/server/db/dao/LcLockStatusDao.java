/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcLockStatus;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcLockStatusDao extends OwnerBaseDao<LcLockStatus>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public LcLockStatus getStatusByLockId(int lockId)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where lockId="+lockId;
		LcLockStatus tcc=super.queryBean(LcLockStatus.class, null, sql, new Object[]{});
	    return tcc;
	}
	
}
