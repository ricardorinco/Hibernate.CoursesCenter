package com.coursescenter.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.coursescenter.config.EntityManager;
import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.pojo.CourseType;

public class CourseTypeDAO
{
	private EntityManager entityManager = null;
	SessionFactory sessionFactory = null;
	
	public CourseTypeDAO(SGDB database)
	{
		entityManager = new EntityManager(database);
		sessionFactory = entityManager.getSessionFactory();
	}
	
    public void save(CourseType courseType)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.saveOrUpdate(courseType);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void save(List<CourseType> coursesTypes)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (CourseType coursesType : coursesTypes)
        {
            try
            {
                session.saveOrUpdate(coursesType);
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
    
    public void delete(CourseType courseType)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.delete(courseType);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void delete(List<CourseType> coursesTypes)
    {
    	Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (CourseType coursesType : coursesTypes)
        {
            try
            {
                session.delete(coursesType);

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
    
    public CourseType getById(Integer id)
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
            CourseType courseType = (CourseType) session.get(CourseType.class, id);
            
            return courseType;
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
    public CourseType getByIdentification(String identification)
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
            CourseType courseType = (CourseType) session.createCriteria(CourseType.class)
                .add(Restrictions.eq("Identification", identification))
                .uniqueResult();
            
            return courseType;
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
    
    public List<CourseType> getList()
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
        	@SuppressWarnings("unchecked")
            List<CourseType> coursesTypes = session.createCriteria(CourseType.class)
                .list();
            
            return coursesTypes;
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