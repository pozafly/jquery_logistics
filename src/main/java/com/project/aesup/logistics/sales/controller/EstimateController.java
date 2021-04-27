package com.project.aesup.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.sales.serviceFacade.SalesServiceFacade;
import com.project.aesup.logistics.sales.to.EstimateDetailTO;
import com.project.aesup.logistics.sales.to.EstimateTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class EstimateController extends MultiActionController {


	// serviceFacade 참조변수 선언
	SalesServiceFacade salesSF;
	
	public void setSalesSF(SalesServiceFacade salesSF) {
		this.salesSF = salesSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchEstimateInfo(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateSearchCondition = request.getParameter("dateSearchCondition");

		try {

			ArrayList<EstimateTO> estimateTOList = salesSF.getEstimateList(dateSearchCondition, startDate, endDate);

			modelMap.put("gridRowJson", estimateTOList);
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

	public ModelAndView searchEstimateDetailInfo(HttpServletRequest request, HttpServletResponse response) {

		String estimateNo = request.getParameter("estimateNo");

		try {

			ArrayList<EstimateDetailTO> estimateDetailTOList = salesSF.getEstimateDetailList(estimateNo);

			modelMap.put("gridRowJson", estimateDetailTOList);
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

	public ModelAndView addNewEstimate(HttpServletRequest request, HttpServletResponse response) {
		//estimateRegister.html에서, 견적 '일괄저장' 눌렀을 때.

		String estimateDate = request.getParameter("estimateDate");
		System.out.println("            estimateDate : " + estimateDate); //견적일자
		String newEstimateInfo = request.getParameter("newEstimateInfo"); //견적과 견적상세의 데이터값의 json배열 전부.
		System.out.println("            newEstimateInfo : " + newEstimateInfo);
		EstimateTO newEstimateTO = gson.fromJson(newEstimateInfo, EstimateTO.class);
		// newEstimateInfo 는 뷰단에서 보낸 json 문자열 => 문자열을 자바 객체로 변환

		try {

			HashMap<String, Object> resultList = salesSF.addNewEstimate(estimateDate, newEstimateTO);
			//견적일자와 견적,견적상세의 json객체를 EstimateTO로 변환한 newEstimateTO를 map에 담음
			modelMap.put("result", resultList);
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

		ArrayList<EstimateDetailTO> estimateDetailTOList = gson.fromJson(batchList,
				new TypeToken<ArrayList<EstimateDetailTO>>() {
				}.getType());

		try {

			HashMap<String, Object> resultList = salesSF.batchEstimateDetailListProcess(estimateDetailTOList);

			modelMap.put("result", resultList);
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
