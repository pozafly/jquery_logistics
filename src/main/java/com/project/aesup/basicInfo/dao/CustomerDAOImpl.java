package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.CustomerTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class CustomerDAOImpl extends IbatisSupportDAO implements CustomerDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<CustomerTO> selectCustomerListByCompany() {

		return (ArrayList<CustomerTO>) getSqlMapClientTemplate().queryForList("customer.selectCustomerListByCompany");
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<CustomerTO> selectCustomerListByWorkplace(String workplaceCode) {

		return (ArrayList<CustomerTO>) getSqlMapClientTemplate().queryForList("customer.selectCustomerListByWorkplace", workplaceCode);
		
	}

	@SuppressWarnings("deprecation")
	public void insertCustomer(CustomerTO bean) {
		
		getSqlMapClientTemplate().insert("customer.insertCustomer", bean);
		
	}

	@SuppressWarnings("deprecation")
	public void updateCustomer(CustomerTO bean) {

		getSqlMapClientTemplate().update("customer.updateCustomer", bean);
		
	}

	@SuppressWarnings("deprecation")
	public void deleteCustomer(CustomerTO bean) {

		getSqlMapClientTemplate().delete("customer.deleteCustomer", bean);

	}

}
