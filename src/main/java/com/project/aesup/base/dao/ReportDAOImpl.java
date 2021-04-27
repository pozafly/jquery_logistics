package com.project.aesup.base.dao;

import java.util.ArrayList;

import com.project.aesup.base.to.ContractReportTO;
import com.project.aesup.base.to.EstimateReportTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class ReportDAOImpl extends IbatisSupportDAO implements ReportDAO {


	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo) {

		return (ArrayList<EstimateReportTO>) getSqlMapClientTemplate().queryForList("report.selectEstimateReport", estimateNo);

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public ArrayList<ContractReportTO> selectContractReport(String contractNo) {

		return (ArrayList<ContractReportTO>) getSqlMapClientTemplate().queryForList("report.selectContractReport", contractNo);
		
	}
}
