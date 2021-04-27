package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.CompanyTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class CompanyDAOImpl extends IbatisSupportDAO implements CompanyDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<CompanyTO> selectCompanyList() {

		return (ArrayList<CompanyTO>) getSqlMapClientTemplate().queryForList("company.selectCompanyList");
		
	}

	@SuppressWarnings("deprecation")
	public void insertCompany(CompanyTO bean) {

		getSqlMapClientTemplate().insert("company.inserCompany", bean);
	}

	@SuppressWarnings("deprecation")
	public void updateCompany(CompanyTO bean) {

		getSqlMapClientTemplate().insert("company.updateCompany", bean);
		
	}

	@SuppressWarnings("deprecation")
	public void deleteCompany(CompanyTO bean) {

		getSqlMapClientTemplate().delete("company.deleteCompany", bean);
		
	}
}
