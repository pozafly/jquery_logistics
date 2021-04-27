package com.project.aesup.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.production.serviceFacade.ProductionServiceFacade;
import com.project.aesup.logistics.production.to.MrpGatheringTO;
import com.project.aesup.logistics.production.to.MrpTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MrpController extends MultiActionController {

	ProductionServiceFacade productionSF;
	public void setProductionSF(ProductionServiceFacade productionSF) {
		this.productionSF = productionSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
	
	
	public ModelAndView getMrpList(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringStatusCondition = request.getParameter("mrpGatheringStatusCondition");
		String dateSearchCondition = request.getParameter("dateSearchCondition");
		String mrpStartDate = request.getParameter("mrpStartDate");
		String mrpEndDate = request.getParameter("mrpEndDate");
		String mrpGatheringNo = request.getParameter("mrpGatheringNo");

		
		try {
			ArrayList<MrpTO> mrpList = null;
			
		
			if(mrpGatheringStatusCondition != null ) {
				//여기 null이라는 스트링값이 담겨저왔으니 null은 아님. 객체가있는상태.
				
				mrpList = productionSF.searchMrpList(mrpGatheringStatusCondition);
				
			} else if (dateSearchCondition != null) {
				
				mrpList = productionSF.searchMrpList(dateSearchCondition, mrpStartDate, mrpEndDate);
				
			} else if (mrpGatheringNo != null) {
				
				mrpList = productionSF.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
				
			}
			
			modelMap.put("gridRowJson", mrpList);
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
	
	
	public ModelAndView openMrp(HttpServletRequest request, HttpServletResponse response) {

		
		String mpsNoListStr = request.getParameter("mpsNoList");
		System.out.println("mpsNoListStr 값확인 : "+mpsNoListStr);
		
		ArrayList<String> mpsNoArr = gson.fromJson(mpsNoListStr, new TypeToken<ArrayList<String>>() { }.getType());		
		//지금 넘어온 mpsNoListStr 즉, 주생산계획번호를 <ArrayList<String>> 형식으로 바꾼다.
		System.out.println("mpsNoArr 값확인 : "+mpsNoArr);
		HashMap<String, Object> openMrpResultMap = new HashMap<>();

		try {
			openMrpResultMap = productionSF.openMrp(mpsNoArr);

		} catch (Exception e1) {
			
			e1.printStackTrace();
			openMrpResultMap.put("errorCode", -1);
			openMrpResultMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", openMrpResultMap);
		return modelAndView;
	}

	
	public ModelAndView registerMrp(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		String mrpRegisterDate = request.getParameter("mrpRegisterDate");

		ArrayList<MrpTO> newMrpList 
			= gson.fromJson(batchList,
					new TypeToken<ArrayList<MrpTO>>() { }.getType());	
		
		System.out.println("결과 MPS에 등록  batchList -> newMrpList : "+batchList+"->"+newMrpList);
		System.out.println("mrpRegisterDate"+mrpRegisterDate);

		try {

			HashMap<String, Object> resultMap = productionSF.registerMrp(mrpRegisterDate, newMrpList);	 
			System.out.println("resultMap : "+resultMap);
			
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
	
	
	
	public ModelAndView getMrpGatheringList(HttpServletRequest request, HttpServletResponse response) {

		
		String mrpNoList = request.getParameter("mrpNoList");
		
		ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
				new TypeToken<ArrayList<String>>() { }.getType());		

		try {

			ArrayList<MrpGatheringTO> mrpGatheringList = productionSF.getMrpGathering(mrpNoArr);
			
			modelMap.put("gridRowJson", mrpGatheringList);
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
	
	

	public ModelAndView registerMrpGathering(HttpServletRequest request, HttpServletResponse response) {

		
		String mrpGatheringRegisterDate = request.getParameter("mrpGatheringRegisterDate");
		String batchList = request.getParameter("batchList");
		String mrpNoAndItemCodeList = request.getParameter("mrpNoAndItemCodeList");
		System.out.println("mrpRegisterAndGather.jsp -> 넘어온 파라미터값 확인 ↓ ");
		System.out.println("mrpGatheringRegisterDate : "+mrpGatheringRegisterDate);
		System.out.println("batchList : "+batchList);
		System.out.println("mrpNoAndItemCodeList : "+mrpNoAndItemCodeList);
		System.out.println(" 파라미터값  데이터 확인 ↓");
		
		
		ArrayList<MrpGatheringTO> newMrpGatheringList
			= gson.fromJson(batchList,
					new TypeToken<ArrayList<MrpGatheringTO>>() { }.getType());	
		
		HashMap<String, String> mrpNoAndItemCodeMap 
			=  gson.fromJson(mrpNoAndItemCodeList,
					new TypeToken<HashMap<String, String>>() { }.getType());	
		System.out.println("batchList : "+batchList);	
		System.out.println("mrpNoAndItemCodeList : "+mrpNoAndItemCodeList);

		try {

			HashMap<String, Object> resultMap 
				= productionSF.registerMrpGathering(mrpGatheringRegisterDate, newMrpGatheringList, mrpNoAndItemCodeMap);	 
			
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
	

	public ModelAndView searchMrpGathering(HttpServletRequest request, HttpServletResponse response) {
		String searchDateCondition = request.getParameter("searchDateCondition");
		String startDate = request.getParameter("mrpGatheringStartDate");
		String endDate = request.getParameter("mrpGatheringEndDate");

		try {
			ArrayList<MrpGatheringTO> mrpGatheringList = 
					productionSF.searchMrpGatheringList(searchDateCondition, startDate, endDate);
			
			modelMap.put("gridRowJson", mrpGatheringList);
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
