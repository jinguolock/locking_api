/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.islora.lock.wx.server.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcAuth;
import com.islora.lock.wx.server.db.entity.LcLogBle;
import com.islora.lock.wx.server.db.entity.LcLogLora;
import com.islora.lock.wx.server.db.persistence.BaseDao;
import com.islora.lock.wx.server.db.utils.CustomException;

/**
 *
 * @author lingbo.wang
 */
public class LcLogLoraDao extends OwnerBaseDao<LcLogLora>{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
}
