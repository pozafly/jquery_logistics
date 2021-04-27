package com.project.aesup.logistics.purchase.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.logistics.purchase.serviceFacade.PurchaseServiceFacade;
import com.project.aesup.logistics.purchase.to.BomDeployTO;
import com.project.aesup.logistics.purchase.to.BomInfoTO;
import com.project.aesup.logistics.purchase.to.BomTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class BomController extends MultiActionController {


	// serviceFacade 참조변수 선언
	PurchaseServiceFacade purchaseSF;
	
	public void setPurchaseSF(PurchaseServiceFacade purchaseSF) {
		this.purchaseSF = purchaseSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchBomDeploy(HttpServletRequest request, HttpServletResponse response) {


		String deployCondition = request.getParameter("deployCondition");
		System.out.println("deployCondition : " +deployCondition);
		// forward 정전개 || reverse 역전개
		
		String itemCode = request.getParameter("itemCode");
		System.out.println("itemCode : " +itemCode);
		// CodeController를 사용하여 검색한 후 선택하여 텍스트박스에 들어있던 값을 파라미터로 받아옴
		// ex ] DK-01
		
		String itemClassificationCondition = request.getParameter("itemClassificationCondition");
		System.out.println("itemClassificationCondition : " + itemClassificationCondition);


		try {

			ArrayList<BomDeployTO> bomDeployList = purchaseSF.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);

			modelMap.put("gridRowJson", bomDeployList);
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

	public ModelAndView searchBomInfo(HttpServletRequest request, HttpServletResponse response) {


		String parentItemCode = request.getParameter("parentItemCode");

		try {

			ArrayList<BomInfoTO> bomInfoList = purchaseSF.getBomInfoList(parentItemCode);

			modelMap.put("gridRowJson", bomInfoList);
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

	public ModelAndView searchAllItemWithBomRegisterAvailable(HttpServletRequest request,
			HttpServletResponse response) {


		try {

			ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = purchaseSF.getAllItemWithBomRegisterAvailable();

			modelMap.put("gridRowJson", allItemWithBomRegisterAvailable);
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

	public ModelAndView batchBomListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		// System.out.println(batchList);
		ArrayList<BomTO> batchBomList = gson.fromJson(batchList, new TypeToken<ArrayList<BomTO>>() {
		}.getType());


		try {

			HashMap<String, Object> resultList = purchaseSF.batchBomListProcess(batchBomList);

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
