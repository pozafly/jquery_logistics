package com.project.aesup.logistics.sales.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.project.aesup.logistics.sales.applicationService.ContractApplicationService;
import com.project.aesup.logistics.sales.applicationService.DeliveryApplicationService;
import com.project.aesup.logistics.sales.applicationService.EstimateApplicationService;
import com.project.aesup.logistics.sales.applicationService.SalesPlanApplicationService;
import com.project.aesup.logistics.sales.to.ContractDetailTO;
import com.project.aesup.logistics.sales.to.ContractInfoTO;
import com.project.aesup.logistics.sales.to.ContractTO;
import com.project.aesup.logistics.sales.to.DeliveryInfoTO;
import com.project.aesup.logistics.sales.to.EstimateDetailTO;
import com.project.aesup.logistics.sales.to.EstimateTO;
import com.project.aesup.logistics.sales.to.SalesPlanTO;

public class SalesServiceFacadeImpl implements SalesServiceFacade {

	private EstimateApplicationService estimateAS;
	private ContractApplicationService contractAS;
	private SalesPlanApplicationService salesPlanAS;
	private DeliveryApplicationService deliveryAS;
	
	public void setEstimateAS (EstimateApplicationService estimateAS) {
		this.estimateAS = estimateAS;
	}
	public void setContractAS (ContractApplicationService contractAS) {
		this.contractAS = contractAS;
	}
	public void setSalesPlanAS (SalesPlanApplicationService salesPlanAS) {
		this.salesPlanAS = salesPlanAS;
	}
	public void setDeliveryAS (DeliveryApplicationService deliveryAS) {
		this.deliveryAS = deliveryAS;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {

		return estimateAS.getEstimateList(dateSearchCondition, startDate, endDate);
	}

	@Override
	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {

		return estimateAS.getEstimateDetailList(estimateNo);
	}

	@Override
	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO) {
		HashMap<String, Object> resultMap = null;
		resultMap = estimateAS.addNewEstimate(estimateDate, newEstimateTO);
		System.out.println("              SalesSF에서resultMap값은 : " + resultMap);
		// SalesSF에서resultMap값은 : {DELETE=[], newEstimateNo=ES2018100901,
		// INSERT=[ES2018100901-01], UPDATE=[]}

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList) {

		return estimateAS.batchEstimateDetailListProcess(estimateDetailTOList);
	}

	@Override
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {

		return contractAS.getContractList(searchCondition, paramArray);
	}

	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(String searchCondition, String[] paramArray) {

		return contractAS.getDeliverableContractList(searchCondition, paramArray);
	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo) {

		return contractAS.getContractDetailList(estimateNo);
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {

		return contractAS.getEstimateListInContractAvailable(startDate, endDate);
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
			ContractTO workingContractTO) {
		
		return contractAS.addNewContract(contractDate, personCodeInCharge, workingContractTO);
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		return contractAS.batchContractDetailListProcess(contractDetailTOList);
	}

	@Override
	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
			contractAS.changeContractStatusInEstimate(estimateNo, contractStatus);

	}

	@Override
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {

		return salesPlanAS.getSalesPlanList(dateSearchCondition, startDate, endDate);
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {

		return salesPlanAS.batchSalesPlanListProcess(salesPlanTOList);
	}

	@Override
	public ArrayList<DeliveryInfoTO> getDeliveryInfoList() {

		return deliveryAS.getDeliveryInfoList();
	}

	@Override
	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList) {

		return deliveryAS.batchDeliveryListProcess(deliveryTOList);
	}

	@Override
	public HashMap<String, Object> deliver(String contractDetailNo) {

		return deliveryAS.deliver(contractDetailNo);
	}
	
}
