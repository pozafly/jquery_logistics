package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.project.aesup.logistics.sales.to.ContractDetailTO;

public interface ContractDetailDAO {

	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo);

	public ArrayList<ContractDetailTO> selectDeliverableContractDetailList(String contractNo);
	
	public int selectContractDetailCount(String contractNo);

	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate);

	public void insertContractDetail(ContractDetailTO TO);

	public HashMap<String,Object> insertContractDetail(String estimateNo, String contractNo);
	
	public void updateContractDetail(ContractDetailTO TO);

	public void changeMpsStatusOfContractDetail(String contractDetailNo, String mpsStatus);

	public void deleteContractDetail(ContractDetailTO TO);

}
