package com.project.aesup.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.production.serviceFacade.ProductionServiceFacade;
import com.project.aesup.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.project.aesup.logistics.production.to.MpsTO;
import com.project.aesup.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MpsController extends MultiActionController {

	ProductionServiceFacade productionSF;
	public void setProductionSF(ProductionServiceFacade productionSF) {
		this.productionSF = productionSF;
	}

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchMpsInfo(HttpServletRequest request, HttpServletResponse response) {


		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String includeMrpApply = request.getParameter("includeMrpApply"); 
								// 포함 = includeMrpApply, 미포함 = excludeMrpApply;
		System.out.println("MPS 컨트롤러 값확인  - stDate : "+startDate+", endDate : "+endDate+", includeMrpApply:" +includeMrpApply);

		try {

			ArrayList<MpsTO> mpsTOList = productionSF.getMpsList(startDate, endDate, includeMrpApply);

			modelMap.put("gridRowJson", mpsTOList);
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

	public ModelAndView searchContractDetailListInMpsAvailable(HttpServletRequest request,
			HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		System.out.println(searchCondition);
		
		String startDate = request.getParameter("startDate");
		System.out.println(startDate);
		
		String endDate = request.getParameter("endDate");
		System.out.println(endDate);

		try {

			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = productionSF
					.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
													   //contractDate, 2019-07-01, 2019-07-31
			modelMap.put("gridRowJson", contractDetailInMpsAvailableList);
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

	public ModelAndView searchSalesPlanListInMpsAvailable(HttpServletRequest request, HttpServletResponse response) {


		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {

			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = productionSF
					.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

			modelMap.put("gridRowJson", salesPlanInMpsAvailableList);
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

	public ModelAndView convertContractDetailToMps(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
				System.out.println("batchList = "+batchList);
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<ContractDetailInMpsAvailableTO>>() {
				}.getType());


				System.out.println("contractDetailInMpsAvailableList = "+contractDetailInMpsAvailableList);

		try {

			HashMap<String, Object> resultMap = productionSF
					.convertContractDetailToMps(contractDetailInMpsAvailableList);

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

	public ModelAndView convertSalesPlanToMps(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<SalesPlanInMpsAvailableTO>>() {
				}.getType());

		try {

			HashMap<String, Object> resultMap = productionSF.convertSalesPlanToMps(salesPlanInMpsAvailableList);

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

}
