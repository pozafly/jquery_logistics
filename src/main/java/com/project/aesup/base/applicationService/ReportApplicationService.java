package com.project.aesup.base.applicationService;

import java.util.ArrayList;

import com.project.aesup.base.to.ContractReportTO;
import com.project.aesup.base.to.EstimateReportTO;

public interface ReportApplicationService {

	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);
	
}
