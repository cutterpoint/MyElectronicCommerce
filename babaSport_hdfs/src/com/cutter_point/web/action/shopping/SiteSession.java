/**
 * ���ܣ������Ϊ��ʵ�ֶ�session�ļ���
 * ʱ�䣺2015��6��5��20:43:35
 * �ļ���SiteSession.java
 * ���ߣ�cutter_point
 */
package com.cutter_point.web.action.shopping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SiteSession implements HttpSessionListener
{
	//����������session
	private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

	@Override
	public void sessionCreated(HttpSessionEvent arg0) 
	{
		//����id�������session
		sessions.put(arg0.getSession().getId(), arg0.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) 
	{
		//session�г�ʱʱ�����Ƶģ�ʱ�䵽�����Ǿ�remove��
		sessions.remove(arg0.getSession().getId());
	}
	
	/**
	 * ����session��id��ȡ����Ӧ��session
	 * @param sessionID
	 * @return
	 */
	public static HttpSession getSession(String sessionID)
	{
		return sessions.get(sessionID);
	}
	
	/**
	 * �����ǰ����session�����������ɾ����
	 * @param sessionID
	 */
	public static void removeSession(String sessionID)
	{
		if(sessions.containsKey(sessionID))
		{
			//������ڵĻ���ɾ����
			sessions.remove(sessionID);
		}
	}
}
