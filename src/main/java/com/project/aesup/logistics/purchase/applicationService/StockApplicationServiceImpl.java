package com.project.aesup.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.dao.StockDAO;
import com.project.aesup.logistics.purchase.to.StockLogTO;
import com.project.aesup.logistics.purchase.to.StockTO;

public class StockApplicationServiceImpl implements StockApplicationService{

		// DAO 참조변수 선언
		private StockDAO stockDAO;
		public void setStockDAO(StockDAO stockDAO) {
			this.stockDAO = stockDAO;
		}

		@Override
		public ArrayList<StockTO> getStockList() {

			return stockDAO.selectStockList();
		}
	
		@Override
		public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate) {

			return stockDAO.selectStockLogList(startDate,endDate);
		}

		@Override
		public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {
			
            HashMap<String,Object> resultMap = null;
			String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
			
			resultMap = stockDAO.warehousing(orderNoList);

        	return resultMap;
			
		}
		
}
