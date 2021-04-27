package com.project.aesup.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.production.serviceFacade.ProductionServiceFacade;
import com.project.aesup.logistics.production.to.ProductionPerformanceInfoTO;
import com.project.aesup.logistics.production.to.WorkOrderInfoTO;

public class WorkOrderController extends MultiActionController {
	// serviceFacade 참조변수 선언
	ProductionServiceFacade productionSF;
	public void setProductionSF (ProductionServiceFacade productionSF) {
		this.productionSF = productionSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();


	public ModelAndView getWorkOrderableMrpList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			
			resultMap = productionSF.getWorkOrderableMrpList();

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}

	public ModelAndView showWorkOrderDialog(HttpServletRequest request, HttpServletResponse response) {

		String mrpNo = request.getParameter("mrpNo");
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			
			resultMap = productionSF.getWorkOrderSimulationList(mrpNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}

	public ModelAndView workOrder(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			
			resultMap = productionSF.workOrder();

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}

	public ModelAndView showWorkOrderInfoList(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;

		try {
			workOrderInfoList = productionSF.getWorkOrderInfoList();

			modelMap.put("gridRowJson", workOrderInfoList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
	
	public ModelAndView workOrderCompletion(HttpServletRequest request, HttpServletResponse response) {

		String workOrderNo=request.getParameter("workOrderNo");
		String actualCompletionAmount=request.getParameter("actualCompletionAmount");
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			
			resultMap = productionSF.workOrderCompletion(workOrderNo,actualCompletionAmount);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}
	
	public ModelAndView getProductionPerformanceInfoList(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;

		try {

			productionPerformanceInfoList = productionSF.getProductionPerformanceInfoList();

			modelMap.put("gridRowJson", productionPerformanceInfoList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
	
}
