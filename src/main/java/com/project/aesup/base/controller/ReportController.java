package com.project.aesup.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.project.aesup.base.serviceFacade.BaseServiceFacade;
import com.project.aesup.base.to.ContractReportTO;
import com.project.aesup.base.to.EstimateReportTO;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportController extends MultiActionController {

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	private BaseServiceFacade baseSF;

	public void setBaseSF(BaseServiceFacade baseSF) {
		this.baseSF = baseSF;
	}


	public ModelAndView estimateReport(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> parameters = new HashMap<>();
		
		String estimateNo = request.getParameter("orderDraftNo");
		
		try {
			
			ArrayList<EstimateReportTO> estimateList = baseSF.getEstimateReport(estimateNo);
			parameters.put("orderDraftNo", estimateNo);

			
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(estimateList);
			modelMap.put("source", source);
			modelMap.put("format", "pdf");

		
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelAndView = new ModelAndView("estimatePdfView", modelMap);
		return modelAndView;
	}

	public ModelAndView contractReport(HttpServletRequest request, HttpServletResponse response) {

		String contractNo = request.getParameter("orderDraftNo");

		try {

			ArrayList<ContractReportTO> contractList = baseSF.getContractReport(contractNo);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(contractList);
			modelMap.put("source", source);
			modelMap.put("format", "pdf");

		} catch (Exception e) {

			e.printStackTrace();
		}

		modelAndView = new ModelAndView("contractPdfView", modelMap);
		return modelAndView;
	}
}