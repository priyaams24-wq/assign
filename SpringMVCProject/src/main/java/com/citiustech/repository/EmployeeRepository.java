package com.citiustech.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.citiustech.model.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	private DataSource dataSource;
	
	public void saveEmployee(Employee employee) {
		saveUsingDataSource(employee);
	}
	
	public void saveUsingDataSource(Employee employee) {
		try(Connection con = dataSource.getConnection()){
			try(PreparedStatement pstmt = con.prepareStatement("INSERT INTO employee(id, name, designation, emailId) VALUES(?, ?, ?, ?)")){
				pstmt.setInt(1, employee.getId());
				pstmt.setString(2, employee.getName());
				pstmt.setString(3, employee.getDesignation());
				pstmt.setString(4, employee.getEmailId());
				pstmt.executeUpdate();
			}
		}catch(Exception e) {
			 throw new RuntimeException(e);
		}
	}
	
	public List<Employee> getAllEmployees(){
		List<Employee> employees = new ArrayList<Employee>();
		try(Connection con = dataSource.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, designation, emailId FROM employee");
			while(rs.next()) {
				employees.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			
			rs.close();
			stmt.close();
			
			return employees;
		}catch(Exception e) {
			 throw new RuntimeException(e);
		}
	}
}







