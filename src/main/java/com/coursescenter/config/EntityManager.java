package com.coursescenter.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EntityManager
{
	public enum SGDB { SQLServer, MySQL }
	
	private static SessionFactory sessionFactory;
	
	@SuppressWarnings("deprecation")
	public EntityManager(SGDB database)
	{
		if (database == SGDB.SQLServer)
			sessionFactory = new Configuration().configure("Hibernate_SQL.cfg.xml").buildSessionFactory();
		else if (database == SGDB.MySQL)
			sessionFactory = new Configuration().configure("Hibernate_MySQL.cfg.xml").buildSessionFactory();
			
	}
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}