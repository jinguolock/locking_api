/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcReader;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcReaderDao extends OwnerBaseDao<LcReader>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	public void updateReaderInfo(int readerId,String pwd,String type)throws CustomException{
		String sql = "UPDATE "+super.getTableName()+" SET rtype='"+type+"', password='"+pwd+"' where id="+readerId;
		System.out.println(sql);
		super.update(sql, new Object[] {});
	}
	public LcReader getBySn(String sn)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where readerNo='"+sn+"'";
		LcReader tcc=super.queryBean(LcReader.class, null, sql, new Object[]{});
	    return tcc;
	}
}
