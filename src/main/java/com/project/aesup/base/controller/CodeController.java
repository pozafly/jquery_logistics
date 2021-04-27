package com.project.aesup.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.aesup.base.serviceFacade.BaseServiceFacade;
import com.project.aesup.base.to.CodeDetailTO;
import com.project.aesup.base.to.CodeTO;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CodeController extends MultiActionController {


	private BaseServiceFacade baseSF;
	
	public void setBaseSF(BaseServiceFacade baseSF) {
		this.baseSF = baseSF;
	}

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView findCodeList(HttpServletRequest request, HttpServletResponse response) {


		try {

			ArrayList<CodeTO> codeList = baseSF.getCodeList();

			modelMap.put("codeList", codeList);
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

	public ModelAndView findDetailCodeList(HttpServletRequest request, HttpServletResponse response) {

		String divisionCode = request.getParameter("divisionCode"); //멀티액션서 넘어온거
		System.out.println(	"@divisionCode : "+divisionCode);


		try {


			ArrayList<CodeDetailTO> detailCodeList = baseSF.getDetailCodeList(divisionCode); //베이스퍼사드에 getDetailCodeList호출
			;

			modelMap.put("detailCodeList", detailCodeList);
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

	public ModelAndView checkCodeDuplication(HttpServletRequest request, HttpServletResponse response) {

		String divisionCode = request.getParameter("divisionCode");
		String newDetailCode = request.getParameter("newCode");


		try {

			Boolean flag = baseSF.checkCodeDuplication(divisionCode, newDetailCode);

			modelMap.put("result", flag);
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
		String tableName = request.getParameter("tableName");

		try {

			ArrayList<CodeTO> codeList = null;
			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			if (tableName.equals("CODE")) {

				codeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeTO>>() {
				}.getType());

				resultMap = baseSF.batchCodeListProcess(codeList);

			} else if (tableName.equals("CODE_DETAIL")) {

				detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
				}.getType());

				resultMap = baseSF.batchDetailCodeListProcess(detailCodeList);

			}

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

	
	public ModelAndView changeCodeUseCheckProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		try {

			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
			}.getType());

			resultMap = baseSF.changeCodeUseCheckProcess(detailCodeList);

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
