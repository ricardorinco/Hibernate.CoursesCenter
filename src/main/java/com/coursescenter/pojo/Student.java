package com.coursescenter.pojo;

import java.util.Date;
import java.util.List;

public class Student
{	
	private Integer Id;
	private String Identification;
	private Date BirthDate;
	private String Email;
	private String Registration;
	private List<Request> Requests;
	
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
    
    public Date getBirthDate()
    {
        return this.BirthDate;
    }
    public void setBirthDate(Date birthDate)
    {
    	this.BirthDate = birthDate;
    }

    public String getEmail()
    {
        return this.Email;
    }
    public void setEmail(String email)
    {
    	this.Email = email;
    }

    public String getRegistration()
    {
    	return this.Registration;
    }
    public void setRegistration(String registration)
    {
    	this.Registration = registration;
    }

    public List<Request> getRequests()
    {
    	return this.Requests;
    }
    public void setRequests(List<Request> requests)
    {
    	this.Requests = requests;
    }
}