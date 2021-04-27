package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.CompanyTO;

public interface CompanyDAO {
	
	public ArrayList<CompanyTO> selectCompanyList();
	
	public void insertCompany(CompanyTO TO);
	
	public void updateCompany(CompanyTO TO);

	public void deleteCompany(CompanyTO TO);
	
}
