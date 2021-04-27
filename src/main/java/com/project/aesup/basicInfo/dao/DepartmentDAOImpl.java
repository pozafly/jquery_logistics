package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.DepartmentTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class DepartmentDAOImpl extends IbatisSupportDAO implements DepartmentDAO {
	

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<DepartmentTO> selectDepartmentListByCompany(String companyCode) {
		
		return (ArrayList<DepartmentTO>) getSqlMapClientTemplate().queryForList("department.selectDepartmentListByCompany", companyCode);
		
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<DepartmentTO> selectDepartmentListByWorkplace(String workplaceCode) {

		return (ArrayList<DepartmentTO>) getSqlMapClientTemplate().queryForList("department.selectDepartmentListByWorkplace", workplaceCode);
		
	}
	
	@SuppressWarnings("deprecation")
	public void insertDepartment(DepartmentTO bean) {

		getSqlMapClientTemplate().insert("department.insertDepartment", bean);
		
	}
	
	
	@SuppressWarnings("deprecation")
	public void updateDepartment(DepartmentTO bean) {

		getSqlMapClientTemplate().update("department.updateDepartment", bean);

	}
	

	@SuppressWarnings("deprecation")
	public void deleteDepartment(DepartmentTO bean) {

		getSqlMapClientTemplate().delete("department.deleteDepartment", bean);

	}
	
}
