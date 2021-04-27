package com.project.aesup.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.sales.serviceFacade.SalesServiceFacade;
import com.project.aesup.logistics.sales.to.ContractInfoTO;
import com.project.aesup.logistics.sales.to.DeliveryInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DeliveryController extends MultiActionController {

	SalesServiceFacade salesSF;
	public void setSalesSF(SalesServiceFacade salesSF) {
		this.salesSF = salesSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환

	public ModelAndView searchDeliveryInfoList(HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList<DeliveryInfoTO> deliveryInfoList = salesSF.getDeliveryInfoList();

			modelMap.put("gridRowJson", deliveryInfoList);
			modelMap.put("errorCode", 0);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	// batchListProcess

	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		try {

			List<DeliveryInfoTO> deliveryTOList = gson.fromJson(batchList, new TypeToken<ArrayList<DeliveryInfoTO>>() {
			}.getType());

			System.out.println(deliveryTOList);

			HashMap<String, Object> resultMap = salesSF.batchDeliveryListProcess(deliveryTOList);

			modelMap.put("result", resultMap);
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

	public ModelAndView searchDeliverableContractList(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String customerCode = request.getParameter("customerCode");

		try {

			ArrayList<ContractInfoTO> deliverableContractList = null;

			if (searchCondition.equals("searchByDate")) {

				String[] paramArray = { startDate, endDate };
				deliverableContractList = salesSF.getDeliverableContractList("searchByDate", paramArray);

			} else if (searchCondition.equals("searchByCustomer")) {

				String[] paramArray = { customerCode };
				deliverableContractList = salesSF.getDeliverableContractList("searchByCustomer", paramArray);

			}

			modelMap.put("gridRowJson", deliverableContractList);
			modelMap.put("errorCode", 0);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView deliver(HttpServletRequest request, HttpServletResponse response) {

		String contractDetailNo = request.getParameter("contractDetailNo");
		
		HashMap<String,Object> resultMap = new HashMap<>();
		
		try {

			
			resultMap = salesSF.deliver(contractDetailNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}

}
