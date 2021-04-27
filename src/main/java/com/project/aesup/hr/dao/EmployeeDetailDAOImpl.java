package com.project.aesup.hr.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.hr.to.EmployeeDetailTO;

public class EmployeeDetailDAOImpl extends IbatisSupportDAO implements EmployeeDetailDAO {

	
	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<EmployeeDetailTO> selectEmployeeDetailList(String companyCode, String empCode) {

		HashMap<String,String> map=new HashMap<>();
		map.put("companyCode",companyCode);
		map.put("empCode",empCode);
		
		return (ArrayList<EmployeeDetailTO>)getSqlMapClientTemplate().queryForList("employeeDetail.selectEmployeeDetailList",map);
	}

	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<EmployeeDetailTO> selectUserIdList(String companyCode) { 
		
		return (ArrayList<EmployeeDetailTO>)getSqlMapClientTemplate().queryForList("employeeDetail.selectUserIdList",companyCode);
		
	}

	@SuppressWarnings("deprecation")
	public void insertEmployeeDetail(EmployeeDetailTO TO) {
          
		getSqlMapClientTemplate().insert("employeeDetail.insertEmployeeDetail", TO);
				}

}
