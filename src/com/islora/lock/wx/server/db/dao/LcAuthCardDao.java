/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcAuth;
import com.islora.lock.wx.server.db.entity.LcAuthApp;
import com.islora.lock.wx.server.db.entity.LcAuthCard;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcAuthCardDao extends OwnerBaseDao<LcAuthCard>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	public static DateFormat df2=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	public LcAuthCard getAuthByCardId(String cardNo)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where cardNo='"+cardNo+"'";
		LcAuthCard tcc=super.queryBean(LcAuthCard.class, null, sql, new Object[]{});
	    return tcc;
	}
	public List<LcAuthCard> getAuthByLockId(int lockId)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where lockId="+lockId;
		List<LcAuthCard> tcc=super.queryBeanList(LcAuthCard.class, null, sql, new Object[]{});
	    return tcc;
	}
	public void clearExpireAuth(int ownerId)throws CustomException{
		List<LcAuthCard> list=this.findByOwnerId(ownerId);
		Date now=new Date();
	    if(list==null) return;
	    for(LcAuthCard card:list) {
	    	try {
	    		Date start=df2.parse(card.getStartTime());
	    		Date end=df2.parse(card.getEndTime());
	    		if(now.before(start)||now.after(end)) {
	    			this.deleteById(card.getId());
	    		}
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
	    	}
	    }
	}
	
}
