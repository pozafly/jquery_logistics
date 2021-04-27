package com.project.aesup.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.basicInfo.to.CustomerTO;
import com.project.aesup.basicInfo.to.FinancialAccountAssociatesTO;

public interface CooperatorServiceFacade {

	public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode);

	public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList);

	public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
			String workplaceCode);

	public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
			ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList);

}
