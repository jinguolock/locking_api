/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcUserDao extends BaseDao<LcUser>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	public LcUser getUser(String username)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where username='"+username+"'";
		LcUser tcc=super.queryBean(LcUser.class, null, sql, new Object[]{});
	    return tcc;
	}
	public LcUser getUserByEmail(String mail)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where email='"+mail+"'";
		LcUser tcc=super.queryBean(LcUser.class, null, sql, new Object[]{});
	    return tcc;
	}
	public LcUser getUserByPhone(String phone)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where phone='"+phone+"'";
		LcUser tcc=super.queryBean(LcUser.class, null, sql, new Object[]{});
	    return tcc;
	}
	public LcUser getUserByIdsn(String idsn)throws CustomException{
		String sql = "select * from "+super.getTableName()+" where idsn='"+idsn+"'";
		LcUser tcc=super.queryBean(LcUser.class, null, sql, new Object[]{});
	    return tcc;
	}
	public String register(LcUser user)throws CustomException{
		/*String sql = "select * from "+super.getTableName()+" where idsn='"+user.getIdsn()+"' or username='"+
				user.getUsername()+"' or phone='"+user.getPhone()+"' or email='"+user.getEmail()+"'";*/
		//LcUser tcc=super.queryBean(LcUser.class, null, sql, new Object[]{});
		LcUser tcc=getUser(user.getUsername());
		if(tcc!=null) {
			return "error_username";
		}
		tcc=getUserByEmail(user.getEmail());
		if(tcc!=null) {
			return "error_email";
		}
		tcc=getUserByPhone(user.getPhone());
		if(tcc!=null) {
			return "error_phone";
		}
		tcc=getUserByIdsn(user.getIdsn());
		if(tcc!=null) {
			return "error_id";
		}
		super.save(user);
		return "success";
	}
	public void changeUser(LcUser user)throws CustomException{
		super.update(user);
	}
}
