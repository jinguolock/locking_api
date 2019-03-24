package com.islora.lock.wx.server.netty;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.islora.lock.wx.server.core.MyProp;
import com.islora.lock.wx.server.db.dao.LcAuthCardDao;
import com.islora.lock.wx.server.db.dao.LcLockDao;
import com.islora.lock.wx.server.db.entity.LcAuthCard;
import com.islora.lock.wx.server.db.entity.LcLock;

//{"Command":"83","NodeId":"11601811","Target":"node","ContentType":"byte", "Content":""}
public class DownCommand implements Serializable {
	private String NodeId;
	private String Command;
	private String Target;
	private String ContentType;
	private String Content;

	public String getNodeId() {
		return NodeId;
	}

	public void setNodeId(String nodeId) {
		NodeId = nodeId;
	}

	public String getCommand() {
		return Command;
	}

	public void setCommand(String command) {
		Command = command;
	}

	public String getTarget() {
		return Target;
	}

	public void setTarget(String target) {
		Target = target;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public static void sendAllLock() {
		try {
			LcLockDao lockDao = new LcLockDao();
			List<LcLock> locks = lockDao.findAll();
			LcAuthCardDao cardDao = new LcAuthCardDao();
			JSONArray ja = new JSONArray();
			for (LcLock lock : locks) {
				if ("card".equals(lock.getLtype())) {
					ja.add(MyProp.getAddLockJSON(lock.getSn(), lock.getSecurekey(), lock.getStatus(), lock.getVersion(),
							"", 1));
				} else if ("sncard".equals(lock.getLtype())) {
					List<LcAuthCard> list = cardDao.getAuthByLockId(lock.getId());
					StringBuilder sb = new StringBuilder();
					for (LcAuthCard card : list) {
						String id = card.getCardNo();
						int leng = id.length() / 2;
						for (int i = 0; i < leng; i++) {
							int ii = i * 2;
							String ss = id.substring(ii, ii + 2);
							sb.append(ss).append(",");
						}
					}
					if (sb.length() > 1) {
						sb.deleteCharAt(sb.length() - 1);
					}

					ja.add(MyProp.getAddLockJSON(lock.getSn(), lock.getSecurekey(), lock.getStatus(), lock.getVersion(),
							sb.toString(), 2));
				}

			}

			System.out.println(ja.toJSONString());
			// ctx.channel().writeAndFlush(ja.toJSONString());
			MainServer.getInstance().writeToAll(ja.toJSONString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
