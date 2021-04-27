package com.project.aesup.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.basicInfo.to.FinancialAccountAssociatesTO;

public interface FinancialAccountAssociatesApplicationService {

	public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
			String workplaceCode);

	public String getNewFinancialAccountAssociatesCode();

	public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
			ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList);
}
