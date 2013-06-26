package com.akwolf.rbac.persistence;

import com.akwolf.rbac.model.Session;

public interface SessionMapper {
	
	public Session getSessionBySessionId(String sessionId) ;
	
	public int deleteSessionBySessionId(String sessionId) ;
	
	public int addSession(Session session) ;
}
