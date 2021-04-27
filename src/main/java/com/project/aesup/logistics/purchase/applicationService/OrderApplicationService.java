package com.project.aesup.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.to.OrderInfoTO;

public interface OrderApplicationService {
	
	public HashMap<String,Object> getOrderList(String startDate, String endDate);

	public HashMap<String,Object> getOrderDialogInfo(ArrayList<String> mrpNoArr);

	public HashMap<String,Object> order();
	
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);
	
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);
	
}
