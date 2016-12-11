package com.coursescenter.pojo;

public class RequestDetail
{
	private Integer Id;
	private Request Request;
	private Course Course;
	
	public Integer getId()
	{
		return this.Id;
	}
	public void setId(Integer id)
	{
		this.Id = id;
	}

	public Request getRequest()
	{
		return this.Request;
	}
	public void setRequest(Request request)
	{
		this.Request = request;
	}
	
	public Course getCourse()
	{
		return Course;
	}
	public void setCourse(Course course)
	{
		this.Course = course;
	}
}