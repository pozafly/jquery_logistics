package com.project.aesup.logistics.purchase.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.purchase.to.OrderDialogTempTO;
import com.project.aesup.logistics.purchase.to.OrderInfoTO;
import com.project.aesup.logistics.purchase.to.OrderTempTO;

public class OrderDAOImpl extends IbatisSupportDAO implements OrderDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {

		HashMap<String, Object> paramMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();

		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		
		ArrayList<OrderTempTO> orderList = (ArrayList<OrderTempTO>) 
				getSqlMapClientTemplate().queryForList("order.getOrderList", paramMap);

		resultMap.put("errorCode",paramMap.get("errorCode"));
		resultMap.put("errorMsg",paramMap.get("errorMsg"));
		resultMap.put("gridRowJson",orderList);
		
		return resultMap;
				
		}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpNoList) {
		
		HashMap<String, Object> paramMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();

		paramMap.put("mrpNoList", mrpNoList);
		
		ArrayList<OrderDialogTempTO> orderDialogInfoList = (ArrayList<OrderDialogTempTO>) 
				getSqlMapClientTemplate().queryForList("order.getOrderDialogInfo", paramMap);

		resultMap.put("errorCode",paramMap.get("errorCode"));
		resultMap.put("errorMsg",paramMap.get("errorMsg"));
		resultMap.put("gridRowJson",orderDialogInfoList);
		
		return resultMap;
		
	}

	@SuppressWarnings({"deprecation" })
	@Override
	public HashMap<String,Object> order() {

		HashMap<String, Object> resultMap = new HashMap<>();
		
		getSqlMapClientTemplate().queryForList("order.doOrder", resultMap);
		
		return resultMap;	
		
	}

	@SuppressWarnings({"deprecation" })
	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
		
		HashMap<String, Object> paramMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();

		paramMap.put("itemCode", itemCode);
		paramMap.put("itemAmount", itemAmount);
		
		getSqlMapClientTemplate().queryForList("order.optionOrder", paramMap);

		resultMap.put("errorCode",paramMap.get("errorCode"));
		resultMap.put("errorMsg",paramMap.get("errorMsg"));
		
		return resultMap;
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
	
		
		return (ArrayList<OrderInfoTO>)
				getSqlMapClientTemplate().queryForList("order.getOrderInfoListOnDelivery");
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		
		HashMap<String, Object> paramMap = new HashMap<>();

		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		
		return (ArrayList<OrderInfoTO>)
				getSqlMapClientTemplate().queryForList("order.getOrderInfoListOnDelivery",paramMap);
		
	}

}
