/**
 * 功能：这个是为了实现对session的监听
 * 时间：2015年6月5日20:43:35
 * 文件：SiteSession.java
 * 作者：cutter_point
 */
package com.cutter_point.web.action.shopping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SiteSession implements HttpSessionListener
{
	//这个用来存放session
	private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

	@Override
	public void sessionCreated(HttpSessionEvent arg0) 
	{
		//根据id号来存放session
		sessions.put(arg0.getSession().getId(), arg0.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) 
	{
		//session有超时时间限制的，时间到了我们就remove掉
		sessions.remove(arg0.getSession().getId());
	}
	
	/**
	 * 根据session的id号取得相应的session
	 * @param sessionID
	 * @return
	 */
	public static HttpSession getSession(String sessionID)
	{
		return sessions.get(sessionID);
	}
	
	/**
	 * 这里是吧这个session在里面的引用删除掉
	 * @param sessionID
	 */
	public static void removeSession(String sessionID)
	{
		if(sessions.containsKey(sessionID))
		{
			//如果存在的话就删除掉
			sessions.remove(sessionID);
		}
	}
}
