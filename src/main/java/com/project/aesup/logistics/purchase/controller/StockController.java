package com.project.aesup.logistics.purchase.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.purchase.serviceFacade.PurchaseServiceFacade;
import com.project.aesup.logistics.purchase.to.StockLogTO;
import com.project.aesup.logistics.purchase.to.StockTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class StockController extends MultiActionController {

	// serviceFacade 참조변수 선언
	PurchaseServiceFacade purchaseSF;
	
	public void setPurchaseSF(PurchaseServiceFacade purchaseSF) {
		this.purchaseSF = purchaseSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchStockList(HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList<StockTO> stockList = purchaseSF.getStockList();

			modelMap.put("gridRowJson", stockList);
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

	public ModelAndView searchStockLogList(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		try {

			ArrayList<StockLogTO> stockLogList = purchaseSF.getStockLogList(startDate,endDate);

			modelMap.put("gridRowJson", stockLogList);
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
	
	public ModelAndView warehousing(HttpServletRequest request, HttpServletResponse response) {

		String orderNoListStr = request.getParameter("orderNoList");

		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());	
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {

			resultMap = purchaseSF.warehousing(orderNoArr);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}
	
	
}
