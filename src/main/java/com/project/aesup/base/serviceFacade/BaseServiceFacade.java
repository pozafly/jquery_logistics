package com.project.aesup.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.base.to.AddressTO;
import com.project.aesup.base.to.CodeDetailTO;
import com.project.aesup.base.to.CodeTO;
import com.project.aesup.base.to.ContractReportTO;
import com.project.aesup.base.to.EstimateReportTO;

public interface BaseServiceFacade {

	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode);

	public ArrayList<CodeTO> getCodeList();

	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode);

	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList);

	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList);

	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList);

	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);

}
