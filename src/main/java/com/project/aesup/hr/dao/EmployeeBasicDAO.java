package com.project.aesup.hr.dao;

import java.util.ArrayList;

import com.project.aesup.hr.to.EmployeeBasicTO;

public interface EmployeeBasicDAO {

	public ArrayList<EmployeeBasicTO> selectEmployeeBasicList(String companyCode);
	
	public EmployeeBasicTO selectEmployeeBasicTO(String companyCode, String empCode);
	
	public void insertEmployeeBasic(EmployeeBasicTO TO);
	
	public void changeUserAccountStatus(String companyCode, String empCode, String userStatus);
	
}
