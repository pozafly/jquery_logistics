package com.project.aesup.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.production.to.ProductionPerformanceInfoTO;
import com.project.aesup.logistics.production.to.WorkOrderInfoTO;
import com.project.aesup.logistics.production.to.WorkOrderSimulationTO;
import com.project.aesup.logistics.production.to.WorkOrderableMrpListTO;

public class WorkOrderDAOImpl extends IbatisSupportDAO implements WorkOrderDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public HashMap<String,Object> getWorkOrderableMrpList() {
		
		HashMap<String,Object> paramMap = new HashMap<>();
		HashMap<String,Object> resultMap = new HashMap<>();
		
		ArrayList<WorkOrderableMrpListTO> workOrderableMrpList=
				(ArrayList<WorkOrderableMrpListTO>)getSqlMapClientTemplate().queryForList("workOrder.getWorkOrderableMrpList", paramMap);

		resultMap.put("gridRowJson",workOrderableMrpList);
		resultMap.put("errorCode",paramMap.get("errorCode"));
		resultMap.put("errorMsg",paramMap.get("errorMsg"));
		
		return resultMap;
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public HashMap<String,Object> getWorkOrderSimulationList(String mrpNo) {

		HashMap<String,Object> paramMap = new HashMap<>();
		HashMap<String,Object> resultMap = new HashMap<>();
		
		paramMap.put("mrpNo", mrpNo);
		
		ArrayList<WorkOrderSimulationTO> workOrderSimulationList = 
				(ArrayList<WorkOrderSimulationTO>)getSqlMapClientTemplate().queryForList("workOrder.getWorkOrderSimulationList", paramMap);

		resultMap.put("gridRowJson", workOrderSimulationList);
		resultMap.put("errorCode",paramMap.get("errorCode"));
		resultMap.put("errorMsg",paramMap.get("errorMsg"));
		
		return resultMap;
		
	}
	
	@SuppressWarnings({"deprecation" })
	@Override
	public HashMap<String,Object> workOrder() {

		HashMap<String,Object> resultMap = new HashMap<>();
		
		getSqlMapClientTemplate().queryForList("workOrder.doWorkOrder", resultMap);
            
        return resultMap;
        	
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<WorkOrderInfoTO> selectWorkOrderInfoList() {
		
		  return (ArrayList<WorkOrderInfoTO>)
				  getSqlMapClientTemplate().queryForList("workOrder.selectWorkOrderInfoList");

	}

	@SuppressWarnings({"deprecation"})
	@Override
	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {

		HashMap<String,Object> paramMap = new HashMap<>();
		HashMap<String,Object> resultMap = new HashMap<>();
		
		paramMap.put("workOrderNo", workOrderNo);
		paramMap.put("actualCompletionAmount", actualCompletionAmount);
		
		getSqlMapClientTemplate().queryForList("workOrder.workOrderCompletion",paramMap);
		
		resultMap.put("errorCode",paramMap.get("errorCode"));
		resultMap.put("errorMsg",paramMap.get("errorMsg"));	
		
		return paramMap;
		
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<ProductionPerformanceInfoTO> selectProductionPerformanceInfoList() {
		
		 return (ArrayList<ProductionPerformanceInfoTO>)
				 getSqlMapClientTemplate().queryForList("workOrder.selectProductionPerformanceInfoList");

	}
	
}
