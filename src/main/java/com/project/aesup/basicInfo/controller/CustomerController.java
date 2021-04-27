package com.project.aesup.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.aesup.basicInfo.serviceFacade.CooperatorServiceFacade;
import com.project.aesup.basicInfo.to.CustomerTO;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CustomerController extends MultiActionController {

	// serviceFacade 참조변수 선언
	CooperatorServiceFacade cooperatorSF;
	public void setCooperatorSF(CooperatorServiceFacade cooperatorSF) {
		this.cooperatorSF = cooperatorSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	public ModelAndView searchCustomerList(HttpServletRequest request, HttpServletResponse response) {


		String searchCondition = request.getParameter("searchCondition");

		String companyCode = request.getParameter("companyCode");

		String workplaceCode = request.getParameter("workplaceCode");

		ArrayList<CustomerTO> customerList = null;
		
		try {
			customerList = cooperatorSF.getCustomerList(searchCondition, companyCode, workplaceCode);

			modelMap.put("gridRowJson", customerList);
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

		ArrayList<CustomerTO> customerList = gson.fromJson(batchList, new TypeToken<ArrayList<CustomerTO>>() {
		}.getType());


		try {
			HashMap<String, Object> resultMap = cooperatorSF.batchCustomerListProcess(customerList);

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
