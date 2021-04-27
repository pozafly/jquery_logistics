package com.project.aesup.logistics.purchase.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.to.StockLogTO;
import com.project.aesup.logistics.purchase.to.StockTO;

public interface StockDAO {
	
	public ArrayList<StockTO> selectStockList();
	
	public ArrayList<StockLogTO> selectStockLogList(String startDate,String endDate);
	
	public HashMap<String,Object> warehousing(String orderNoList);
	
}
