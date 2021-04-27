package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.sales.to.DeliveryInfoTO;


public class DeliveryDAOImpl extends IbatisSupportDAO implements DeliveryDAO {


	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<DeliveryInfoTO> selectDeliveryInfoList() {
		
		return (ArrayList<DeliveryInfoTO>)
				getSqlMapClientTemplate().queryForList("delivery.selectDeliveryInfoList");
		
	}


	@SuppressWarnings({ "deprecation" })
	@Override
	public HashMap<String,Object> deliver(String contractDetailNo) {

		HashMap<String, Object> paramMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();
		
		paramMap.put("contractDetailNo", contractDetailNo);
		
		getSqlMapClientTemplate().queryForObject("delivery.deliver", paramMap);

		resultMap.put("errorCode", paramMap.get("errorCode"));
		resultMap.put("errorMsg", paramMap.get("errorMsg"));
	
		return resultMap;
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void insertDeliveryResult(DeliveryInfoTO bean) {

		getSqlMapClientTemplate().insert("delivery.insertDeliveryResult", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateDeliveryResult(DeliveryInfoTO bean) {

		getSqlMapClientTemplate().insert("delivery.updateDeliveryResult", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteDeliveryResult(DeliveryInfoTO bean) {

		getSqlMapClientTemplate().delete("delivery.deleteDeliveryResult", bean);

	}
	

}
