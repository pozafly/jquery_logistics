package com.project.aesup.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.to.StockLogTO;
import com.project.aesup.logistics.purchase.to.StockTO;

public interface StockApplicationService {
	
	public ArrayList<StockTO> getStockList();
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr);
	
}
