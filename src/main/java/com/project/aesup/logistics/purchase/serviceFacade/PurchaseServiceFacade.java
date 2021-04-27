package com.project.aesup.logistics.purchase.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.to.BomDeployTO;
import com.project.aesup.logistics.purchase.to.BomInfoTO;
import com.project.aesup.logistics.purchase.to.BomTO;
import com.project.aesup.logistics.purchase.to.OrderInfoTO;
import com.project.aesup.logistics.purchase.to.StockLogTO;
import com.project.aesup.logistics.purchase.to.StockTO;

public interface PurchaseServiceFacade {

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition);
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable();
	
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList);
	
	public HashMap<String,Object> getOrderList(String startDate, String endDate);
	
	public HashMap<String,Object> getOrderDialogInfo(ArrayList<String> mrpNoArr);
	
	public HashMap<String,Object> order();
	
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);
	
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr);
	
	public ArrayList<StockTO> getStockList();
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);
}
