package com.coursescenter.pojo;

public class Course
{
	private Integer Id;
	private String Identification;
	private Double Price;
	
	private CourseType CourseType;
	private Instructor Instructor;
		
	public Integer getId()
    {
        return this.Id;
    }
    public void setId(Integer id)
    {
        this.Id = id;
    }
	
    public String getIdentification()
    {
        return this.Identification;
    }
    public void setIdentification(String identification)
    {
    	this.Identification = identification;
    }
    
    public Double getPrice()
    {
    	return this.Price;
    }
    public void setPrice(Double price)
    {
    	this.Price = price;
    }
    
    public CourseType getCourseType()
    {
    	return this.CourseType;
    }
    public void setCourseType(CourseType courseType)
    {
    	this.CourseType = courseType;
    }
    
    public Instructor getInstructor()
    {
    	return this.Instructor;
    }
    public void setInstructor(Instructor instructor)
    {
    	this.Instructor = instructor;
    }
}