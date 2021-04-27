package com.project.aesup.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.base.applicationService.AddressApplicationService;
import com.project.aesup.base.applicationService.CodeApplicationService;
import com.project.aesup.base.applicationService.ReportApplicationService;
import com.project.aesup.base.to.AddressTO;
import com.project.aesup.base.to.CodeDetailTO;
import com.project.aesup.base.to.CodeTO;
import com.project.aesup.base.to.ContractReportTO;
import com.project.aesup.base.to.EstimateReportTO;


public class BaseServiceFacadeImpl implements BaseServiceFacade {

	private CodeApplicationService codeAS;
	private AddressApplicationService addressAS;
	private ReportApplicationService reportAS;
	
	public void setCodeAS(CodeApplicationService codeAS) {
		this.codeAS = codeAS;
	}
	public void setAddressAS(AddressApplicationService addressAS) {
		this.addressAS = addressAS;
	}
	public void setReportAS(ReportApplicationService reportAS) {
		this.reportAS = reportAS;
	}

	@Override
	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode) {

		return codeAS.getDetailCodeList(divisionCode);
	}

	@Override
	public ArrayList<CodeTO> getCodeList() {

		return codeAS.getCodeList();
	}

	@Override
	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode) {

		return codeAS.checkCodeDuplication(divisionCode, newDetailCode);
	}

	@Override
	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList) {

		return codeAS.batchCodeListProcess(codeList);
	}

	@Override
	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList) {

		return codeAS.batchDetailCodeListProcess(detailCodeList);
	}

	@Override
	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList) {

		return codeAS.changeCodeUseCheckProcess(detailCodeList);
	}

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber) {

		return addressAS.getAddressList(sidoName, searchAddressType, searchValue, mainNumber);
	}
	@Override
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo) {

		return reportAS.getEstimateReport(estimateNo);
	}

	@Override
	public ArrayList<ContractReportTO> getContractReport(String contractNo) {

		return reportAS.getContractReport(contractNo);
	}

}
