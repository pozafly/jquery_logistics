package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.sales.to.EstimateTO;

public class EstimateDAOImpl extends IbatisSupportDAO implements EstimateDAO {

  
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<EstimateTO> selectEstimateList(String dateSearchCondition, String startDate, String endDate) {
     
		HashMap<String, String> map = new HashMap<>();
		map.put("dateSearchCondition", dateSearchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);  
		
		return(ArrayList<EstimateTO>)getSqlMapClientTemplate().queryForList("estimate.selectEstimateList",map);
	}

	@SuppressWarnings("deprecation")
	@Override
	public EstimateTO selectEstimate(String estimateNo) {

		return(EstimateTO)getSqlMapClientTemplate().queryForList("estimate.selectEstimate",estimateNo);
	}

	
	@SuppressWarnings("deprecation")
	public int selectEstimateCount(String estimateDate) {

		return (Integer)getSqlMapClientTemplate().queryForObject("estimate.selectEstimateCount",estimateDate)+1;

	
	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertEstimate(EstimateTO bean) {

		getSqlMapClientTemplate().insert("estimate.insertEstimate", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateEstimate(EstimateTO bean) {

		getSqlMapClientTemplate().update("estimate.updateEstimate", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeContractStatusOfEstimate(String estimateNo, String contractStatus) {

		HashMap<String, String> map = new HashMap<>();
		map.put("estimateNo", estimateNo);
		map.put("contractStatus", contractStatus);

		getSqlMapClientTemplate().update("estimate.changeContractStatusOfEstimate", map);

	}

	

}