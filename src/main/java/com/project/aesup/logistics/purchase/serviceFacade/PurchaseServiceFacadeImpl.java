package com.project.aesup.logistics.purchase.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.applicationService.BomApplicationService;
import com.project.aesup.logistics.purchase.applicationService.OrderApplicationService;
import com.project.aesup.logistics.purchase.applicationService.StockApplicationService;
import com.project.aesup.logistics.purchase.to.BomDeployTO;
import com.project.aesup.logistics.purchase.to.BomInfoTO;
import com.project.aesup.logistics.purchase.to.BomTO;
import com.project.aesup.logistics.purchase.to.OrderInfoTO;
import com.project.aesup.logistics.purchase.to.StockLogTO;
import com.project.aesup.logistics.purchase.to.StockTO;

public class PurchaseServiceFacadeImpl implements PurchaseServiceFacade {


	private BomApplicationService bomAS;
	private OrderApplicationService orderAS;
	private StockApplicationService stockAS;
	
	public void setBomAS (BomApplicationService bomAS) {
		this.bomAS = bomAS;
	}
	public void setOrderAS (OrderApplicationService orderAS) {
		this.orderAS = orderAS;
	}
	public void setStockAS (StockApplicationService stockAS) {
		this.stockAS = stockAS;
	}

	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode,
			String itemClassificationCondition) {

		return bomAS.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);
	}

	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		return bomAS.getBomInfoList(parentItemCode);
	}

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {

		return orderAS.getOrderList(startDate, endDate);
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

		return bomAS.getAllItemWithBomRegisterAvailable();
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		return bomAS.batchBomListProcess(batchBomList);

	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(ArrayList<String> mrpNoArr) {

		return orderAS.getOrderDialogInfo(mrpNoArr);

	}

	@Override
	public HashMap<String,Object> order() {

    	return orderAS.order();
		
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {

    	return orderAS.optionOrder(itemCode, itemAmount);
		
	}

	@Override
	public ArrayList<StockTO> getStockList() {

		return stockAS.getStockList();
	}

	@Override
	public ArrayList<StockLogTO> getStockLogList(String startDate, String endDate) {

		return stockAS.getStockLogList(startDate, endDate);
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {

		return orderAS.getOrderInfoListOnDelivery();

	}

	@Override
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {

    	return stockAS.warehousing(orderNoArr);
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {

		return orderAS.getOrderInfoList(startDate,endDate);

	}

}
