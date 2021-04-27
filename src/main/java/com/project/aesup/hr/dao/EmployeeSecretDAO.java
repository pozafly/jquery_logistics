package com.project.aesup.hr.dao;

import java.util.ArrayList;

import com.project.aesup.hr.to.EmployeeSecretTO;

public interface EmployeeSecretDAO {

	public ArrayList<EmployeeSecretTO> selectEmployeeSecretList(String companyCode, String empCode);

	public EmployeeSecretTO selectUserPassWord(String companyCode, String empCode);

	public void insertEmployeeSecret(EmployeeSecretTO TO);
	
	public int selectUserPassWordCount(String companyCode, String empCode);

}
