package com.project.aesup.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.production.dao.WorkOrderDAO;
import com.project.aesup.logistics.production.to.ProductionPerformanceInfoTO;
import com.project.aesup.logistics.production.to.WorkOrderInfoTO;

public class WorkOrderApplicationServiceImpl implements WorkOrderApplicationService {

	private WorkOrderDAO workOrderDAO;
	
	public void setWorkOrderDAO(WorkOrderDAO workOrderDAO) {
		this.workOrderDAO = workOrderDAO;
	}
	
	@Override
	public HashMap<String,Object> getWorkOrderableMrpList() {

		return workOrderDAO.getWorkOrderableMrpList();
			
	}

	@Override
	public HashMap<String,Object> getWorkOrderSimulationList(String mrpNo) {

		return workOrderDAO.getWorkOrderSimulationList(mrpNo);
		
	}

	@Override
	public HashMap<String,Object> workOrder() {

    	return workOrderDAO.workOrder();			
		
	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {

		return workOrderDAO.selectWorkOrderInfoList();
		
	}

	@Override
	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {

    	return workOrderDAO.workOrderCompletion(workOrderNo,actualCompletionAmount);
		
	}
	
	@Override
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {

		return workOrderDAO.selectProductionPerformanceInfoList();
		
	}
		
}
