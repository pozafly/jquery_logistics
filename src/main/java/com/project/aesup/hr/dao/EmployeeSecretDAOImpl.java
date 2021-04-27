package com.project.aesup.hr.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.hr.to.EmployeeSecretTO;

public class EmployeeSecretDAOImpl extends IbatisSupportDAO implements EmployeeSecretDAO {

	
	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<EmployeeSecretTO> selectEmployeeSecretList(String companyCode, String empCode) {
		          
		        HashMap<String,String> map= new HashMap<>();
		        map.put("companyCode", companyCode);
		        map.put("empCode", empCode);
               return(ArrayList<EmployeeSecretTO>)getSqlMapClientTemplate().queryForList("employeeSecret.selectEmployeeSecretList",map);
			}

	
	@SuppressWarnings("deprecation")
	public EmployeeSecretTO selectUserPassWord(String companyCode, String empCode) {

        HashMap<String,String> map= new HashMap<>();
        map.put("companyCode", companyCode);
        map.put("empCode", empCode);

        return(EmployeeSecretTO)getSqlMapClientTemplate().queryForObject("employeeSecret.selectUserPassWord",map);
		
   	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void insertEmployeeSecret(EmployeeSecretTO TO) {
	
		getSqlMapClientTemplate().queryForList("employeeSecret.insertEmployeeSecret",TO);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int selectUserPassWordCount(String companyCode, String empCode) {
		HashMap<String,String> map= new HashMap<>();
        map.put("companyCode", companyCode);
        map.put("empCode", empCode);
		
		return (Integer)getSqlMapClientTemplate().queryForObject("employeeSecret.selectUserPassWordCount",map);
      
	}
}
