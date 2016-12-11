package com.coursescenter.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.coursescenter.config.EntityManager;
import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.pojo.Request;

public class RequestDAO
{	
	private EntityManager entityManager = null;
	SessionFactory sessionFactory = null;
	
	public RequestDAO(SGDB database)
	{
		entityManager = new EntityManager(database);
		sessionFactory = entityManager.getSessionFactory();
	}
	
    public void save(Request request)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
		
        try
        {
            session.saveOrUpdate(request);
        }
        catch (HibernateException ex)
        {
			transaction.rollback();
            throw ex;
        }

        transaction.commit();
        session.close();
    }
    public void save(List<Request> requests)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
    	
		int count = 1;
        for (Request request : requests)
        {	
            try
            {
                session.saveOrUpdate(request);
				count++;
                
                if (count % 20 == 0)
                {
                    session.flush();
                    session.clear();
				}					
            }
            catch (HibernateException ex)
            {
				transaction.rollback();
                throw ex;
            }
        }
        
        transaction.commit();
        session.close();
    }
    
    public void delete(Request request)
    {
		Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.delete(request);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void delete(List<Request> requests)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (Request request : requests)
        {
            try
            {
                session.delete(request);

                if (count % 20 == 0)
                {
                    session.flush();
                    session.clear();
                }
            }
            catch (HibernateException ex)
            {
                transaction.rollback();
                throw ex;
            }
        }
        
        transaction.commit();
        session.close();
    }
    
    public Integer getLastId()
    {
    	Session session = sessionFactory.openSession();
		
        try
        {
        	return (Integer) session.createCriteria(Request.class)
				.setProjection(Projections.property("Id"))
				.setMaxResults(1)
				.addOrder(Order.desc("Id"))
				.uniqueResult();
        }
        catch (HibernateException ex)
        {
            throw ex;
        }
        finally
        {
        	session.close();
        }
    }
    
    // LazyLoad
    public List<Request> getList()
    {
    	Session session = sessionFactory.openSession();
		
        try
        {
			@SuppressWarnings("unchecked")
			List<Request> requests = session.createCriteria(Request.class)
        		.list();
			
			for (Request request : requests)
			{
				Hibernate.initialize(request.getRequestDetails());
			}
            
            return requests;
        }
        catch (HibernateException ex)
        {
            throw ex;
        }
        finally
        {
        	session.close();
        }
    }
    public List<Request> getRequestsWithoutCourses()
    {
    	Session session = sessionFactory.openSession();
		
        try
        {
			@SuppressWarnings("unchecked")
			List<Request> requestsWithoutCourses = session.createCriteria(Request.class)
        		.add(Restrictions.isEmpty("RequestDetails"))
				.list();
			
            return requestsWithoutCourses;
        }
        catch (HibernateException ex)
        {
            throw ex;
        }
        finally
        {
        	session.close();
        }
    }
}