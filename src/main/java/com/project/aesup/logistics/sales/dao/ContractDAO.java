package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;

import com.project.aesup.logistics.sales.to.ContractInfoTO;
import com.project.aesup.logistics.sales.to.ContractTO;
import com.project.aesup.logistics.sales.to.EstimateTO;

public interface ContractDAO {

	public ArrayList<EstimateTO> selectEstimateListInContractAvailable(String startDate, String endDate);

	public ArrayList<ContractInfoTO> selectContractListByDate(String startDate, String endDate);

	public ArrayList<ContractInfoTO> selectContractListByCustomer(String customerCode);

	public ArrayList<ContractInfoTO> selectDeliverableContractListByDate(String startDate, String endDate);

	public ArrayList<ContractInfoTO> selectDeliverableContractListByCustomer(String customerCode);

	
	public int selectContractCount(String contractDate);

	public void insertContract(ContractTO TO);

	public void updateContract(ContractTO TO);

}
