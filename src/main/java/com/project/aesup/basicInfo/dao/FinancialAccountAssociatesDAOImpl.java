package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.FinancialAccountAssociatesTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class FinancialAccountAssociatesDAOImpl extends IbatisSupportDAO implements FinancialAccountAssociatesDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByCompany() {
		
		return (ArrayList<FinancialAccountAssociatesTO>) getSqlMapClientTemplate().queryForList("financialAccountAssociates.selectFinancialAccountAssociatesListByCompany");

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByWorkplace(
			String workplaceCode) {

		return (ArrayList<FinancialAccountAssociatesTO>) getSqlMapClientTemplate().queryForList("financialAccountAssociates.selectFinancialAccountAssociatesListByWorkplace", workplaceCode);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {

		getSqlMapClientTemplate().insert("financialAccountAssociates.insertFinancialAccountAssociates", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {
		
		getSqlMapClientTemplate().update("financialAccountAssociates.updateFinancialAccountAssociates", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {
		
		getSqlMapClientTemplate().delete("financialAccountAssociates.deleteFinancialAccountAssociates", bean);

	}

}
