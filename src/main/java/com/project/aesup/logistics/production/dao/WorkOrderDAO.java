package com.project.aesup.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.production.to.ProductionPerformanceInfoTO;
import com.project.aesup.logistics.production.to.WorkOrderInfoTO;

public interface WorkOrderDAO {

	public HashMap<String,Object> getWorkOrderableMrpList();
	
	public HashMap<String,Object> getWorkOrderSimulationList(String mrpNo);	
	
	public HashMap<String,Object> workOrder();
	
	public ArrayList<WorkOrderInfoTO> selectWorkOrderInfoList();
	
	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount);
	
	public ArrayList<ProductionPerformanceInfoTO> selectProductionPerformanceInfoList();
	
}
