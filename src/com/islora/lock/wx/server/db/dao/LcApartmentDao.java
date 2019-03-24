/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcApartmentDao extends OwnerBaseDao<LcApartment>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	public void deleteByBuildId(int buildId)throws CustomException{
		String sql = "delete from "+super.getTableName()+" where buildingId="+buildId;
		super.update(sql, new Object[]{});
	}
}
