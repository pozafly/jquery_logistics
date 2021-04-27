package com.project.aesup.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.sales.dao.SalesPlanDAO;
import com.project.aesup.logistics.sales.to.SalesPlanTO;

public class SalesPlanApplicationServiceImpl implements SalesPlanApplicationService {

	private SalesPlanDAO salesPlanDAO;
	
	public void setSalesPlanDAO(SalesPlanDAO salesPlanDAO) {
		this.salesPlanDAO = salesPlanDAO;
	}

	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {

		return salesPlanDAO.selectSalesPlanList(dateSearchCondition, startDate, endDate);
	}

	public String getNewSalesPlanNo(String salesPlanDate) {


		StringBuffer newEstimateNo = null;

		int newNo = salesPlanDAO.selectSalesPlanCount(salesPlanDate);

		newEstimateNo = new StringBuffer();

		newEstimateNo.append("SA");
		newEstimateNo.append(salesPlanDate.replace("-", ""));
		newEstimateNo.append(String.format("%02d", newNo)); // 2자리 숫자

		return newEstimateNo.toString();
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {


		HashMap<String, Object> resultMap = new HashMap<>();

		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (SalesPlanTO bean : salesPlanTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				// 새로운 판매계획일련번호 생성
				String newSalesPlanNo = getNewSalesPlanNo(bean.getSalesPlanDate());

				// Bean 에 새로운 판매계획일련번호 세팅
				bean.setSalesPlanNo(newSalesPlanNo);

				salesPlanDAO.insertSalesPlan(bean);

				insertList.add(newSalesPlanNo);

				break;

			case "UPDATE":

				salesPlanDAO.updateSalesPlan(bean);

				updateList.add(bean.getSalesPlanNo());

				break;

			case "DELETE":

				salesPlanDAO.deleteSalesPlan(bean);

				deleteList.add(bean.getSalesPlanNo());

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		
		return resultMap;

	}

}
