package com.project.aesup.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.aesup.basicInfo.serviceFacade.CooperatorServiceFacade;
import com.project.aesup.basicInfo.to.FinancialAccountAssociatesTO;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FinancialAccountAssociatesController extends MultiActionController {

	// serviceFacade 참조변수 선언
	CooperatorServiceFacade cooperatorSF;
	public void setCooperatorSF(CooperatorServiceFacade cooperatorSF) {
		this.cooperatorSF = cooperatorSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	public ModelAndView searchFinancialAccountAssociatesList(HttpServletRequest request, HttpServletResponse response) {


		String searchCondition = request.getParameter("searchCondition");
		String workplaceCode = request.getParameter("workplaceCode");

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;

		try {
			financialAccountAssociatesList = cooperatorSF.getFinancialAccountAssociatesList(searchCondition,
					workplaceCode);

			modelMap.put("gridRowJson", financialAccountAssociatesList);
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

	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {


		String batchList = request.getParameter("batchList");

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = gson.fromJson(batchList,
				new TypeToken<ArrayList<FinancialAccountAssociatesTO>>() {
				}.getType());

		try {
			HashMap<String, Object> resultMap = cooperatorSF
					.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);

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
