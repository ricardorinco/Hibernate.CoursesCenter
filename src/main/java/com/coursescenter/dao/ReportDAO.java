package com.coursescenter.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

import com.coursescenter.config.EntityManager;
import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.pojo.Course;
import com.coursescenter.pojo.Report;
import com.coursescenter.pojo.RequestDetail;
import com.coursescenter.pojo.Student;

public class ReportDAO
{
	private EntityManager entityManager = null;
	SessionFactory sessionFactory = null;
	
	public ReportDAO(SGDB database)
	{
		entityManager = new EntityManager(database);
		sessionFactory = entityManager.getSessionFactory();
	}
	
	public Report getCourseBestSeller()
    {
		Session session = sessionFactory.openSession();
		
        try
        {
			Report courseBestSeller = (Report) session.createCriteria(RequestDetail.class)
				.createAlias("Course", "Course")
				.setProjection(
					Projections.projectionList()
					.add(Projections.groupProperty("Course.Identification"), "CourseIdentification")
					.add(Projections.count("Course"), "CourseBestSeller")
				)
                .addOrder(Order.desc("CourseBestSeller"))
        		.setResultTransformer(Transformers.aliasToBean(Report.class))
                .setMaxResults(1)
                .uniqueResult();
            
            return courseBestSeller;
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
	
	public List<Report> getListStudentsRegisterInCourses()
    {
		Session session = sessionFactory.openSession();
		
    	StringBuilder hql = new StringBuilder();
        hql.append("select s.Registration as StudentRegistration,");
        hql.append("	   s.Identification as StudentIdentification,");
        hql.append("	   c.Identification as CourseIdentification,");
        hql.append("       ct.Identification as CourseTypeIdentification,");
        hql.append("       r.RequestDateTime");
        hql.append("  from RequestDetail rq");
        hql.append(" inner join Request r on r.Id = rq.RequestId");
        hql.append(" inner join Course c on c.Id = rq.CourseId");
        hql.append(" inner join CourseType ct on ct.Id = c.CourseTypeId");
        hql.append(" inner join Student s on s.Id = r.StudentId");
        hql.append(" order by StudentRegistration, StudentIdentification, CourseIdentification, CourseTypeIdentification");
        
        try
        {
			@SuppressWarnings("unchecked")
			List<Report> studentsRegisterInCourses = session.createSQLQuery(hql.toString())            
            	.setResultTransformer(Transformers.aliasToBean(Report.class))
            	.list();
			
			return studentsRegisterInCourses;
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
	public List<Report> getListStudentsWithoutRequests()
    {
		Session session = sessionFactory.openSession();
		
        try
        {
			@SuppressWarnings("unchecked")
			List<Report> studentsWithoutRequests = session.createCriteria(Student.class)
				.createAlias("Requests", "Request", JoinType.LEFT_OUTER_JOIN)
        		.setProjection(
					Projections.projectionList()
					.add(Projections.groupProperty("Identification"), "StudentIdentification")
				)
        		.add(Restrictions.isEmpty("Requests"))
        		.setResultTransformer(Transformers.aliasToBean(Report.class))
				.list();
			
            return studentsWithoutRequests;
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
	public List<Report> getListCountGroupByInstructor()
    {      
		Session session = sessionFactory.openSession();
		
        try
        {
        	@SuppressWarnings("unchecked")
			List<Report> countGroupByCourse = session.createCriteria(Course.class)
    			.createAlias("Instructor", "Instructor")
    			.setProjection(
					Projections.projectionList()
					.add(Projections.groupProperty("Instructor.Identification"), "InstructorIdentification")
					.add(Projections.count("Id"),  "TotalCourseMinistered")
				)
    			.addOrder(Order.desc("TotalCourseMinistered"))
    			.setResultTransformer(Transformers.aliasToBean(Report.class))
    			.list();
        	
        	return countGroupByCourse; 
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
	public List<Report> getListSumGroupByCourseType()
    {
		Session session = sessionFactory.openSession();
		
        try
        {
			@SuppressWarnings("unchecked")
			List<Report> sumGroupByCourseType = session.createCriteria(RequestDetail.class)
        		.createAlias("Course", "Course")
        		.createAlias("Course.CourseType", "CourseType")
        		.setProjection(
					Projections.projectionList()
					.add(Projections.groupProperty("CourseType.Identification"), "CourseTypeIdentification")
					.add(Projections.count("Course"), "TotalCourseRequest")
					.add(Projections.sum("Course.Price"), "TotalCoursePrice")
				)
        		.addOrder(Order.asc("CourseTypeIdentification"))
        		.setResultTransformer(Transformers.aliasToBean(Report.class))
				.list();
            
            return sumGroupByCourseType;
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
    public List<Report> getListSumGroupByCourse()
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
			@SuppressWarnings("unchecked")
			List<Report> sumGroupByCourse = session.createCriteria(RequestDetail.class)
        		.createAlias("Course", "Course")
        		.setProjection(
					Projections.projectionList()
					.add(Projections.groupProperty("Course.Identification"), "CourseIdentification")
					.add(Projections.count("Course"), "TotalCourseRequest")
					.add(Projections.sum("Course.Price"), "TotalCoursePrice")
				)
        		.addOrder(Order.asc("CourseIdentification"))
        		.setResultTransformer(Transformers.aliasToBean(Report.class))
				.list();
            
            return sumGroupByCourse;
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
    public List<Report> getListSumGroupByStudent()
    {
    	Session session = sessionFactory.openSession();
    	
        try
        {
			@SuppressWarnings("unchecked")
			List<Report> sumGroupByStudent = session.createCriteria(RequestDetail.class)
				.createAlias("Request", "Request")
                .createAlias("Request.Student", "Student")
                .createAlias("Course", "Course")
        		.setProjection(
					Projections.projectionList()
					.add(Projections.groupProperty("Student.Identification"), "StudentIdentification")
					.add(Projections.count("Course"), "TotalCourseRequest")
					.add(Projections.sum("Course.Price"), "TotalCoursePrice")
				)
        		.addOrder(Order.asc("StudentIdentification"))
        		.setResultTransformer(Transformers.aliasToBean(Report.class))
				.list();
            
            return sumGroupByStudent;
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
