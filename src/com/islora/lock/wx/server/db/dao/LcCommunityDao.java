/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcCommunity;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcCommunityDao extends OwnerBaseDao<LcCommunity>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
//	public void update(LcCommunity com)throws CustomException{
//		String sql="update lc_community SET 'name'='','ctype'='','lon'='','lat'='','ownerId'=1,'address'='' where id=6";
//	}
}
