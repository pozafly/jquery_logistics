package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.project.aesup.logistics.sales.to.ContractDetailTO;

public class ContractDetailDAOImpl extends IbatisSupportDAO implements ContractDetailDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo) {

		return (ArrayList<ContractDetailTO>) getSqlMapClientTemplate().queryForList("contractDetail.selectContractDetailList",
				contractNo);

	}

	@Override
	public int selectContractDetailCount(String contractNo) {

		@SuppressWarnings({ "unchecked", "deprecation" })
		List<ContractDetailTO> contractDetailNoList = getSqlMapClientTemplate()
				.queryForList("contractDetail.selectContractDetailCount", contractNo);

		TreeSet<Integer> intSet = new TreeSet<>();

		for (ContractDetailTO bean : contractDetailNoList) {

			String contractDetailNo = bean.getContractDetailNo();
			int no = Integer.parseInt(contractDetailNo.split("-")[1]);

			intSet.add(no);
		}

		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

		HashMap<String, String> map = new HashMap<>();

		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<ContractDetailInMpsAvailableTO>) getSqlMapClientTemplate()
				.queryForList("contractDetail.selectContractDetailListInMpsAvailable", map);

	}

	
	 @SuppressWarnings("deprecation")
	 
	 @Override public void insertContractDetail(ContractDetailTO bean) {
	 
	 getSqlMapClientTemplate().insert("contractDetail.insertContractDetail",
	 bean);
	 
	 }
	 

	@SuppressWarnings("deprecation")
	@Override
	public void updateContractDetail(ContractDetailTO bean) {

		getSqlMapClientTemplate().update("contractDetail.updateContractDetail", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeMpsStatusOfContractDetail(String contractDetailNo, String mpsStatus) {

		HashMap<String, String> map = new HashMap<>();

		map.put("contractDetailNo", contractDetailNo);
		map.put("mpsStatus", mpsStatus);

		getSqlMapClientTemplate().update("contractDetail.changeMpsStatusOfContractDetail", map);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteContractDetail(ContractDetailTO bean) {

		getSqlMapClientTemplate().delete("contractDetail.deleteContractDetail", bean);

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public ArrayList<ContractDetailTO> selectDeliverableContractDetailList(String contractNo) {
		
		return (ArrayList<ContractDetailTO>) getSqlMapClientTemplate().queryForList("contractDetail.selectContractDetailList",
				contractNo);
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public HashMap<String, Object> insertContractDetail(String estimateNo, String contractNo) {

			HashMap<String, String> paramMap = new HashMap<>();
			HashMap<String, Object> resultMap = new HashMap<>();
			paramMap.put("estimateNo", estimateNo);
			paramMap.put("contractNo", contractNo);
			
			getSqlMapClientTemplate().queryForList
				("contractDetail.procedureInsertContractDetail",paramMap);
			
			resultMap.put("errorCode",paramMap.get("errorCode"));
			resultMap.put("errorMsg",paramMap.get("errorMsg"));
			System.out.println(resultMap);
			System.out.println(estimateNo);
			System.out.println(contractNo);
			return resultMap;
			
	}

}
