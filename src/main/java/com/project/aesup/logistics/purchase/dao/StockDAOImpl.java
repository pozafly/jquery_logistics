package com.project.aesup.logistics.purchase.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.purchase.to.StockLogTO;
import com.project.aesup.logistics.purchase.to.StockTO;

public class StockDAOImpl extends IbatisSupportDAO implements StockDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<StockTO> selectStockList() {
		
		return (ArrayList<StockTO>)
				getSqlMapClientTemplate().queryForList("stock.selectStockList");
			
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<StockLogTO> selectStockLogList(String startDate, String endDate) {
		
		
		HashMap<String,Object> paramMap = new HashMap<>();
		paramMap.put("startDate",startDate);
		paramMap.put("endDate",endDate);
		
		return (ArrayList<StockLogTO>)
				getSqlMapClientTemplate().queryForList("stock.selectStockLogList",paramMap);
			
	}

	@SuppressWarnings({ "deprecation" })
	@Override
		public HashMap<String,Object> warehousing(String orderNoList) {
		
		HashMap<String,Object> paramMap = new HashMap<>();
		HashMap<String,Object> resultMap = new HashMap<>();
		paramMap.put("orderNoList",orderNoList);
		
		getSqlMapClientTemplate().queryForList("stock.warehousing",paramMap);

		resultMap.put("errorCode",paramMap.get("errorCode"));
		resultMap.put("errorMsg",paramMap.get("errorMsg"));
		
		return resultMap;
		
				
	}
	
}
