package com.project.aesup.hr.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.hr.to.EmployeeBasicTO;

public class EmployeeBasicDAOImpl extends IbatisSupportDAO implements EmployeeBasicDAO {
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<EmployeeBasicTO> selectEmployeeBasicList(String companyCode) {
     
		return (ArrayList<EmployeeBasicTO>) getSqlMapClientTemplate().queryForList("employeeBasic.selectEmployeeBasicList",companyCode);
	}
   
	@SuppressWarnings("deprecation")
	public EmployeeBasicTO selectEmployeeBasicTO(String companyCode, String empCode) {
         
		HashMap<String, String> map = new HashMap<>();
		
		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		
		return (EmployeeBasicTO)getSqlMapClientTemplate().queryForList("employeeBasic.selectEmployeeBasicTO",map);
	}

	@SuppressWarnings("deprecation")
	public void insertEmployeeBasic(EmployeeBasicTO TO) {
     
		getSqlMapClientTemplate().queryForList("employeeBasic.insertEmployeeBasic",TO);
	
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void changeUserAccountStatus(String companyCode, String empCode, String userStatus) {

        HashMap<String, String> map = new HashMap<>();
		
		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		map.put("userStatus", userStatus);
		
		getSqlMapClientTemplate().queryForList("employeeBasic.changeUserAccountStatus",map);
		
	}


}
