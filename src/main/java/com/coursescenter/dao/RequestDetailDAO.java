package com.coursescenter.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.coursescenter.config.EntityManager;
import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.pojo.RequestDetail;

public class RequestDetailDAO
{
	private EntityManager entityManager = null;
	SessionFactory sessionFactory = null;
	
	public RequestDetailDAO(SGDB database)
	{
		entityManager = new EntityManager(database);
		sessionFactory = entityManager.getSessionFactory();
	}
	
    public void save(RequestDetail requestDetail)
    {
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
        try
        {
            session.saveOrUpdate(requestDetail);
        }
        catch (HibernateException ex)
        {
        	transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
		session.close();
    }
    public void save(List<RequestDetail> requestDetails)
    {  
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
		int count = 1;
        for (RequestDetail requestDetail : requestDetails)
        {
            try
            {
                session.saveOrUpdate(requestDetail);
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


            transaction.commit();
        	session.close();
        }
    }
    
    public void delete(RequestDetail requestDetail)
    {
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
        try
        {
            session.delete(requestDetail);
        }
        catch (HibernateException ex)
        {
        	transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
		session.close();
    }
    public void delete(List<RequestDetail> requestDetails)
    {
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
		int count = 1;
        for (RequestDetail requestDetail : requestDetails)
        {
            try
            {
                session.delete(requestDetail);
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
            
            transaction.commit();
        	session.close();
        }
    }
    
    public List<RequestDetail> getList()
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
			@SuppressWarnings("unchecked")
			List<RequestDetail> requestDetails = session.createCriteria(RequestDetail.class)
				.createAlias("Request", "Request")
                .createAlias("Request.Student", "Student")
                .createAlias("Course", "Course")
                .createAlias("Course.CourseType", "CourseType")
                .addOrder(Order.asc("Student.Identification"))
                .addOrder(Order.desc("Request.RequestDateTime"))
                .addOrder(Order.asc("CourseType.Identification"))
                .addOrder(Order.asc("Course.Identification"))
        		.list();
            
            return requestDetails;
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