package com.coursescenter.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.coursescenter.config.EntityManager;
import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.helper.Aleatory;
import com.coursescenter.pojo.Course;

public class CourseDAO
{
	private EntityManager entityManager = null;
	SessionFactory sessionFactory = null;
	Aleatory aleatory = new Aleatory();
	
	public CourseDAO(SGDB database)
	{
		entityManager = new EntityManager(database);
		sessionFactory = entityManager.getSessionFactory();
	}
	
	public void save(Course course)
    {
		Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.saveOrUpdate(course);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void save(List<Course> courses)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (Course course : courses)
        {
            try
            {
                session.saveOrUpdate(course);
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
    
    public void delete(Course course)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.delete(course);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void delete(List<Course> courses)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (Course course : courses)
        {
            try
            {
                session.delete(course);

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
    
    public Course getRandom()
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
        	int minimumId = 0;
            int maximumId = 0;
        	
            minimumId = (Integer) session.createCriteria(Course.class)
        		.setProjection(Projections.property("Id"))
        		.setMaxResults(1)
        		.addOrder(Order.asc("Id"))
        		.uniqueResult();
            
            maximumId = (Integer) session.createCriteria(Course.class)
        		.setProjection(Projections.property("Id"))
        		.setMaxResults(1)
        		.addOrder(Order.desc("Id"))
        		.uniqueResult();
            
            return getById(aleatory.getInteger(minimumId, maximumId));
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
    public Course getById(Integer id)
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
        	Course course = (Course) session.get(Course.class, id);
            
            return course;
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
        
    public List<Course> getList()
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
        	@SuppressWarnings("unchecked")
            List<Course> courses = session.createCriteria(Course.class)
            	.addOrder(Order.asc("Id"))
                .list();
            
            return courses;
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