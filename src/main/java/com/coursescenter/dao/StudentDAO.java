package com.coursescenter.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.stat.Statistics;

import com.coursescenter.config.EntityManager;
import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.helper.Aleatory;
import com.coursescenter.pojo.Student;

public class StudentDAO
{
    private EntityManager entityManager = null;
    SessionFactory sessionFactory = null;
    Statistics statistics = null;
    Aleatory aleatory = new Aleatory();
    
    public StudentDAO(SGDB database)
    {
    	entityManager = new EntityManager(database);
        sessionFactory = entityManager.getSessionFactory();
    }
    
    public void save(Student student)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.saveOrUpdate(student);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void save(List<Student> students)
    {    	
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (Student student : students)
        {
            try
            {
                session.saveOrUpdate(student);
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
    
    public void delete(Student student)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            session.delete(student);
        }
        catch (HibernateException ex)
        {
            transaction.rollback();
            throw ex;
        }
        
        transaction.commit();
        session.close();
    }
    public void delete(List<Student> students)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        int count = 1;
        for (Student student : students)
        {
            try
            {
                session.delete(student);

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
    
    public Student getRandom()
    {
        Session session = sessionFactory.openSession();
        
        try
        {
            int minimumId = 0;
            int maximumId = 0;
            
            minimumId = (Integer) session.createCriteria(Student.class)
                .setProjection(Projections.property("Id"))
                .setMaxResults(1)
                .addOrder(Order.asc("Id"))
                .uniqueResult();
            
            maximumId = (Integer) session.createCriteria(Student.class)
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
    public Student getById(Integer id)
    {
        Session session = sessionFactory.openSession();
        
        try
        {
            Student student = (Student) session.createCriteria(Student.class)
                .add(Restrictions.eq("Id", id))
                .uniqueResult();
            
            return student;
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
    public Student getByIdentification(String identification)
    {
        Session session = sessionFactory.openSession();
        
        try
        {
            Student student = (Student) session.createCriteria(Student.class)
                .add(Restrictions.eq("Identification", identification))
                .uniqueResult();
            
            return student;
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
    
    public List<Student> getList()
    {
        Session session = sessionFactory.openSession();
        
        try
        {
            @SuppressWarnings("unchecked")
            List<Student> students = session.createCriteria(Student.class)
                .list();
            
            return students;
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