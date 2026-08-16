package com.citiustech.model;

public class Employee {

	private int id;
	private String name;
	private String designation;
	private String emailId;
	
	public Employee() {
		// TODO Auto-generated constructor stub
		designation = "Fresher";
	}

	public Employee(int id, String name, String designation, String emailId) {
		this.id = id;
		this.name = name;
		this.designation = designation;
		this.emailId = emailId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", designation=" + designation + ", emailId=" + emailId + "]";
	}
	
}







