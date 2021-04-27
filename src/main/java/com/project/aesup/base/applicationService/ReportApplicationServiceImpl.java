package com.project.aesup.base.applicationService;

import java.util.ArrayList;

import com.project.aesup.base.dao.ReportDAO;
import com.project.aesup.base.to.ContractReportTO;
import com.project.aesup.base.to.EstimateReportTO;

public class ReportApplicationServiceImpl implements ReportApplicationService {

	private ReportDAO reportDAO;

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	@Override
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo) {

		return reportDAO.selectEstimateReport(estimateNo);

	}

	@Override
	public ArrayList<ContractReportTO> getContractReport(String contractNo) {

		return reportDAO.selectContractReport(contractNo);
	}

}
