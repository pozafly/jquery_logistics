package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.FinancialAccountAssociatesTO;

public interface FinancialAccountAssociatesDAO {

	public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByCompany();

	public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByWorkplace(
			String workplaceCode);

	public void insertFinancialAccountAssociates(FinancialAccountAssociatesTO TO);

	public void updateFinancialAccountAssociates(FinancialAccountAssociatesTO TO);

	public void deleteFinancialAccountAssociates(FinancialAccountAssociatesTO TO);

}
