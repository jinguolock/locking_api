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
import com.islora.lock.wx.server.db.entity.LcBuilding;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcBuildingDao extends OwnerBaseDao<LcBuilding>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	public void deleteByCommunityId(int communityId)throws CustomException{
		String sql = "delete from "+super.getTableName()+" where communityId="+communityId;
		super.update(sql, new Object[]{});
	}
	public List<LcBuilding> findByCommunityId(int communityId)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where communityId="+communityId;
		return super.queryBeanList(LcBuilding.class, null, sql, new Object[]{});
	}
}
