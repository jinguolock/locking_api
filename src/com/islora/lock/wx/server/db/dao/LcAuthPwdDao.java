/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcAuthPwd;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcAuthPwdDao extends OwnerBaseDao<LcAuthPwd>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	

	public List<LcAuthPwd> getAuthByLock(int lockId)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where lockId="+lockId;
		List<LcAuthPwd> tcc=super.queryBeanList(LcAuthPwd.class, null, sql, new Object[]{});
	    return tcc;
	}
}
