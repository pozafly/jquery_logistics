package com.project.aesup.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.basicInfo.to.DepartmentTO;

public interface DepartmentApplicationService {
	
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode);
	
	public String getNewDepartmentCode(String companyCode);
	
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList);

}
