package com.project.aesup.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.basicInfo.applicationService.CustomerApplicationService;
import com.project.aesup.basicInfo.applicationService.FinancialAccountAssociatesApplicationService;
import com.project.aesup.basicInfo.to.CustomerTO;
import com.project.aesup.basicInfo.to.FinancialAccountAssociatesTO;

public class CooperatorServiceFacadeImpl implements CooperatorServiceFacade {

	private CustomerApplicationService customerAS;
	private FinancialAccountAssociatesApplicationService associatsAS;
	
	public void setCustomerAS(CustomerApplicationService customerAS) {
		this.customerAS = customerAS;
	}
	public void setAssociatsAS(FinancialAccountAssociatesApplicationService associatsAS) {
		this.associatsAS = associatsAS;
	}

	@Override
	public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode) {

		return customerAS.getCustomerList(searchCondition, companyCode, workplaceCode);
	}

	@Override
	public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList) {

		return customerAS.batchCustomerListProcess(customerList);

	}

	@Override
	public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
			String workplaceCode) {

		return associatsAS.getFinancialAccountAssociatesList(searchCondition,	workplaceCode);

	}

	@Override
	public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
			ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList) {

		return associatsAS.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);
	}

}
