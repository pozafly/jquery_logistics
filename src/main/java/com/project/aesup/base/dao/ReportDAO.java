package com.project.aesup.base.dao;

import java.util.ArrayList;

import com.project.aesup.base.to.ContractReportTO;
import com.project.aesup.base.to.EstimateReportTO;

public interface ReportDAO {

	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> selectContractReport(String contractNo);
	
}
