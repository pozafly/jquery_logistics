package com.project.aesup.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.dao.OrderDAO;
import com.project.aesup.logistics.purchase.to.OrderInfoTO;

public class OrderApplicationServiceImpl implements OrderApplicationService {

	// DAO 참조변수 선언
	private OrderDAO orderDAO;
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {

		return orderDAO.getOrderList(startDate, endDate);
	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(ArrayList<String> mrpNoArr) {


        HashMap<String,Object> resultMap = null;

		String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
		System.out.println("mrpNoList = "+mrpNoList);
		resultMap = orderDAO.getOrderDialogInfo(mrpNoList);
		return resultMap;
	}

	@Override
	public HashMap<String,Object> order() {

    	return orderDAO.order();
		
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {

    	return orderDAO.optionOrder(itemCode, itemAmount);
		
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {

		return orderDAO.getOrderInfoListOnDelivery();
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {

		return orderDAO.getOrderInfoList(startDate,endDate);
		
	}

}
