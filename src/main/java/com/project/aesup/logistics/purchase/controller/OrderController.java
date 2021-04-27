package com.project.aesup.logistics.purchase.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.purchase.serviceFacade.PurchaseServiceFacade;
import com.project.aesup.logistics.purchase.to.OrderInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class OrderController extends MultiActionController {
	// serviceFacade 참조변수 선언
	PurchaseServiceFacade purchaseSF;
	
	public void setPurchaseSF(PurchaseServiceFacade purchaseSF) {
		this.purchaseSF = purchaseSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView getOrderList(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		HashMap<String, Object> resultMap = new HashMap<>();

		try {
			

			resultMap = purchaseSF.getOrderList(startDate, endDate);


		} catch (Exception e1) {
			
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
		
	}

	public ModelAndView openOrderDialog(HttpServletRequest request, HttpServletResponse response) {

		String mrpNoListStr = request.getParameter("mrpNoList");
		ArrayList<String> mrpNoArr = gson.fromJson(mrpNoListStr, new TypeToken<ArrayList<String>>() {
		}.getType());
		HashMap<String, Object> resultMap = new HashMap<>();

		try {
			
			resultMap = purchaseSF.getOrderDialogInfo(mrpNoArr);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
		
	}

	public ModelAndView showOrderInfo(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {

			ArrayList<OrderInfoTO> orderInfoList = purchaseSF.getOrderInfoList(startDate,endDate);
			modelMap.put("gridRowJson", orderInfoList);
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
	
	public ModelAndView searchOrderInfoListOnDelivery(HttpServletRequest request, HttpServletResponse response) {


		try {

			ArrayList<OrderInfoTO> orderInfoListOnDelivery = purchaseSF.getOrderInfoListOnDelivery();
			modelMap.put("gridRowJson", orderInfoListOnDelivery);
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

	public ModelAndView order(HttpServletRequest request, HttpServletResponse response) {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			
			resultMap = purchaseSF.order();


		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}

	public ModelAndView optionOrder(HttpServletRequest request, HttpServletResponse response) {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			
			String itemCode = request.getParameter("itemCode");
			String itemAmount = request.getParameter("itemAmount");

			resultMap = purchaseSF.optionOrder(itemCode, itemAmount);


		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", resultMap);
		return modelAndView;
	}
}
