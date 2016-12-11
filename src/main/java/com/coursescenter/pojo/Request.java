package com.coursescenter.pojo;

import java.util.Date;
import java.util.List;

public class Request
{	
	private Integer Id;
	private Date RequestDateTime;
	private Student Student;
	private List<RequestDetail> RequestDetails;
	
	public Integer getId()
    {
        return this.Id;
    }
    public void setId(Integer id)
    {
        this.Id = id;
    }

    public Date getRequestDateTime()
    {
    	return this.RequestDateTime;
    }
    public void setRequestDateTime(Date requestDateTime)
    {
    	this.RequestDateTime = requestDateTime;
    }

    public Student getStudent()
    {
    	return this.Student;
    }
    public void setStudent(Student student)
    {
    	this.Student = student;
    }

    public List<RequestDetail> getRequestDetails()
    {
    	return this.RequestDetails;
    }
    public void setRequestDetails(List<RequestDetail> requestDetails)
    {
    	this.RequestDetails = requestDetails;
    }
}