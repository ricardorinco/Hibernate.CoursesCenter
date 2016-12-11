package com.coursescenter.pojo;

import java.util.Date;

public class Report
{
	// CourseType
	private String CourseTypeIdentification;
	
	public String getCourseTypeIdentification()
	{
		return this.CourseTypeIdentification;
	}
	public void setCourseTypeIdentification(String courseTypeIdentification)
	{
		this.CourseTypeIdentification = courseTypeIdentification;
	}
	
	
	// Course
    private Long CourseBestSeller;
    private String CourseIdentification;

    public Long getCourseBestSeller()
    {
		return this.CourseBestSeller;
	}
	public void setCourseBestSeller(Long courseBestSeller)
	{
		this.CourseBestSeller = courseBestSeller;
	}
    
    public String getCourseIdentification()
    {
		return this.CourseIdentification;
	}
	public void setCourseIdentification(String courseIdentification)
	{
		this.CourseIdentification = courseIdentification;
	}

	
	// Student
	private Integer StudentRegistration;
	private String StudentIdentification;

    public Integer getStudentRegistration()
	{
		return this.StudentRegistration;
	}
	public void setStudentRegistration(Integer studentRegistration)
	{
		this.StudentRegistration = studentRegistration;
	}
	    
    public String getStudentIdentification()
    {
		return this.StudentIdentification;
	}
	public void setStudentIdentification(String studentIdentification)
	{
		this.StudentIdentification = studentIdentification;
	}

	
    // Instructor
    private String InstructorIdentification;

    public String getInstructorIdentification()
    {
		return this.InstructorIdentification;
	}
	public void setInstructorIdentification(String instructorIdentification) {
		this.InstructorIdentification = instructorIdentification;
	}
    

	// Request
    private Long RequestId;
    private Date RequestDateTime;
    
    public Long getRequestId()
    {
		return this.RequestId;
	}
	public void setRequestId(Long requestId)
	{
		this.RequestId = requestId;
	}

	public Date getRequestDateTime()
	{
		return this.RequestDateTime;
	}
	public void setRequestDateTime(Date requestDateTime)
	{
		this.RequestDateTime = requestDateTime;
	}
	
	// Total
    private Long TotalCourseMinistered;
    private Long TotalCourseRequest;
    private Double TotalCoursePrice;
	
    public Long getTotalCourseMinistered()
    {
		return this.TotalCourseMinistered;
	}
	public void setTotalCourseMinistered(Long totalCourseMinistered) {
		this.TotalCourseMinistered = totalCourseMinistered;
	}
	
	public Long getTotalCourseRequest()
	{
		return this.TotalCourseRequest;
	}
	public void setTotalCourseRequest(Long totalCourseRequest) {
		this.TotalCourseRequest = totalCourseRequest;
	}
	
	public Double getTotalCoursePrice()
	{
		return this.TotalCoursePrice;
	}
	public void setTotalCoursePrice(Double totalCoursePrice) {
		this.TotalCoursePrice = totalCoursePrice;
	}
}