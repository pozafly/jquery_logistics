package com.project.aesup.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.sales.dao.ContractDAO;
import com.project.aesup.logistics.sales.dao.ContractDetailDAO;
import com.project.aesup.logistics.sales.dao.EstimateDAO;
import com.project.aesup.logistics.sales.dao.EstimateDetailDAO;
import com.project.aesup.logistics.sales.to.ContractDetailTO;
import com.project.aesup.logistics.sales.to.ContractInfoTO;
import com.project.aesup.logistics.sales.to.ContractTO;
import com.project.aesup.logistics.sales.to.EstimateTO;

public class ContractApplicationServiceImpl implements ContractApplicationService {

	// 참조변수 선언
	private ContractDAO contractDAO;
	private ContractDetailDAO contractDetailDAO;
	private EstimateDAO estimateDAO;
	private EstimateDetailDAO estimateDetailDAO;
	
	public void setContractDAO (ContractDAO contractDAO) {
		this.contractDAO = contractDAO;
	}
	public void setContractDetailDAO (ContractDetailDAO contractDetailDAO) {
		this.contractDetailDAO = contractDetailDAO;
	}
	public void setEstimateDAO (EstimateDAO estimateDAO) {
		this.estimateDAO = estimateDAO;
	}
	public void setEstimateDetailDAO (EstimateDetailDAO estimateDetailDAO) {
		this.estimateDetailDAO = estimateDetailDAO;
	}
	

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {


		ArrayList<ContractInfoTO> contractInfoTOList = null;

		switch (searchCondition) {

		case "searchByDate":

			String startDate = paramArray[0];
			String endDate = paramArray[1];

			contractInfoTOList = contractDAO.selectContractListByDate(startDate, endDate);

			break;

		case "searchByCustomer":

			String customerCode = paramArray[0];

			contractInfoTOList = contractDAO.selectContractListByCustomer(customerCode);

			break;

		}

		for (ContractInfoTO bean : contractInfoTOList) {

			bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.getContractNo()));

		}

		return contractInfoTOList;

	}

	public ArrayList<ContractInfoTO> getDeliverableContractList(String searchCondition, String[] paramArray) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;

		switch (searchCondition) {

		case "searchByDate":

			String startDate = paramArray[0];
			String endDate = paramArray[1];

			contractInfoTOList = contractDAO.selectDeliverableContractListByDate(startDate, endDate);

			break;

		case "searchByCustomer":

			String customerCode = paramArray[0];

			contractInfoTOList = contractDAO.selectDeliverableContractListByCustomer(customerCode);

			break;

		}

		for (ContractInfoTO bean : contractInfoTOList) {

			bean.setContractDetailTOList(contractDetailDAO.selectDeliverableContractDetailList(bean.getContractNo()));

		}

		return contractInfoTOList;

	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String contractNo) {

		return contractDetailDAO.selectContractDetailList(contractNo);
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {

		ArrayList<EstimateTO> estimateListInContractAvailable = null;

		estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(startDate, endDate);
		//estimateListInContractAvailable = EstimateListInContractAvailable

		for (EstimateTO bean : estimateListInContractAvailable) {

			bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean.getEstimateNo()));

		}
			
		return estimateListInContractAvailable;
	}

	@Override
	public String getNewContractNo(String contractDate) {
		StringBuffer newContractNo = null;

		int i = contractDAO.selectContractCount(contractDate);

		newContractNo = new StringBuffer();
		newContractNo.append("CO");
		newContractNo.append(contractDate.replace("-", ""));
		newContractNo.append(String.format("%02d", i));


		return newContractNo.toString();
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
			ContractTO workingContractBean) {

		HashMap<String, Object> resultMap = null;

		// 새로운 수주일련번호 생성
		String newContractNo = getNewContractNo(contractDate);

		workingContractBean.setContractNo(newContractNo); // 새로운 수주일련번호 세팅
		workingContractBean.setContractDate(contractDate); // 뷰에서 전달한 수주일자 세팅
		workingContractBean.setPersonCodeInCharge(personCodeInCharge); // 뷰에서 전달한 수주담당자코드 세팅

		contractDAO.insertContract(workingContractBean);
		
		// 견적 테이블에 수주여부 "Y" 로 수정
		changeContractStatusInEstimate(workingContractBean.getEstimateNo(), "Y");


			
		resultMap = contractDetailDAO.insertContractDetail(
					workingContractBean.getEstimateNo(),   //견적일련번호(ES2019101602)
					newContractNo);    //새로생성된 수주일련번호(CO2019101601)
	
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (ContractDetailTO bean : contractDetailTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				/*contractDetailDAO.insertContractDetail(bean);*/
				insertList.add(bean.getContractDetailNo());

				break;

			case "UPDATE":

				/*contractDetailDAO.updateContractDetail(bean);*/
				updateList.add(bean.getContractDetailNo());

				break;

			case "DELETE":

				contractDetailDAO.deleteContractDetail(bean);
				deleteList.add(bean.getContractDetailNo());

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);

		return resultMap;
	}

	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {

		estimateDAO.changeContractStatusOfEstimate(estimateNo, contractStatus);
	}

}
