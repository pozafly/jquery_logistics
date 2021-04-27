package com.project.aesup.hr.dao;


import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.hr.to.EmpInfoTO;

public class EmpSearchingDAOImpl extends IbatisSupportDAO implements EmpSearchingDAO {

	
	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<EmpInfoTO> selectAllEmpList(String searchCondition, String[] paramArray) {

	
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);

		for (int i = 0; i < paramArray.length; i++) {

			switch (i + "") {

			case "0":
				map.put("companyCode", paramArray[0]);
				break;

			case "1":
				map.put("workplaceCode", paramArray[1]);
				break;

			case "2":
				map.put("deptCode", paramArray[2]);
				break;

			}
		}
			
			return(ArrayList<EmpInfoTO>)getSqlMapClientTemplate().queryForList("empInfo.selectAllEmpList",map);

		}


	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<EmpInfoTO> getTotalEmpInfo(String companyCode, String workplaceCode, String userId) {
	
		HashMap<String, String> map = new HashMap<>();
		map.put("companyCode", companyCode);
		map.put("workplaceCode", workplaceCode);
		map.put("userId", userId);
		
		System.out.println("~~~~~~~~~~~~~");
        System.out.println((ArrayList<EmpInfoTO>)getSqlMapClientTemplate().queryForList("empInfo.getTotalEmpInfo",map));
		return (ArrayList<EmpInfoTO>)getSqlMapClientTemplate().queryForList("empInfo.getTotalEmpInfo",map);
	}
	
	
}
