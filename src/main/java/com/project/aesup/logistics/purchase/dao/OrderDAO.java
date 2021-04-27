package com.project.aesup.logistics.purchase.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.to.OrderInfoTO;

public interface OrderDAO {
	
	 public HashMap<String,Object> getOrderList(String startDate, String endDate);
	 
	 public HashMap<String,Object> getOrderDialogInfo(String mrpNoList);
	 
	 public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	 
	 public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);

	 public HashMap<String,Object> order();	 
	 
	 public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);
	 
}
