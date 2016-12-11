package com.coursescenter.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.coursescenter.config.EntityManager;
import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.pojo.Instructor;

public class InstructorDAO
{
	private EntityManager entityManager = null;
	SessionFactory sessionFactory = null;
	
	public InstructorDAO(SGDB database)
	{
		entityManager = new EntityManager(database);
		sessionFactory = entityManager.getSessionFactory();
	}
	
    public void save(Instructor instructor)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.saveOrUpdate(instructor);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void save(List<Instructor> instructors)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (Instructor instructor : instructors)
        {
            try
            {
                session.saveOrUpdate(instructor);
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
    
    public void delete(Instructor instructor)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.delete(instructor);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void delete(List<Instructor> instructors)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (Instructor instructor : instructors)
        {
            try
            {
                session.delete(instructor);

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
    
    public List<Instructor> getList()
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
			@SuppressWarnings("unchecked")
			List<Instructor> instructors = session.createCriteria(Instructor.class)
        		.addOrder(Order.asc("Identification"))
				.list();
            
            return instructors;
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