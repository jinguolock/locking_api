/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcAuth;
import com.islora.lock.wx.server.db.entity.LcAuthApp;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcAuthAppDao extends OwnerBaseDao<LcAuthApp>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public LcAuthApp getAuthByClientAndLock(int clientId,int lockId)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where lockId="+lockId+" and clientId="+clientId;
		LcAuthApp tcc=super.queryBean(LcAuthApp.class, null, sql, new Object[]{});
	    return tcc;
	}
	public List<LcAuthApp> getAuthByClient(int clientId)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where clientId="+clientId;
		List<LcAuthApp> tcc=super.queryBeanList(LcAuthApp.class, null, sql, new Object[]{});
	    return tcc;
	}
}
