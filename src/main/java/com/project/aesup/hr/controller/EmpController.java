package com.project.aesup.hr.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.project.aesup.hr.serviceFacade.HrServiceFacade;
import com.project.aesup.hr.to.EmpInfoTO;
import com.project.aesup.hr.to.EmployeeBasicTO;
import com.project.aesup.hr.to.EmployeeDetailTO;
import com.project.aesup.hr.to.EmployeeSecretTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class EmpController extends MultiActionController {

	// serviceFacade 참조변수 선언
	HrServiceFacade hrSF;
	
	public void setHrSF(HrServiceFacade hrSF) {
		this.hrSF = hrSF;
	}

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchAllEmpList(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String companyCode = request.getParameter("companyCode");
		String workplaceCode = request.getParameter("workplaceCode");
		String deptCode = request.getParameter("deptCode");

		ArrayList<EmpInfoTO> empList = null;
		String[] paramArray = null;

		try {

			switch (searchCondition) {

			case "ALL":

				paramArray = new String[] { companyCode };
				break;

			case "WORKPLACE":

				paramArray = new String[] { companyCode, workplaceCode };
				break;

			case "DEPT":

				paramArray = new String[] { companyCode, deptCode };
				break;

			case "RETIREMENT":

				paramArray = new String[] { companyCode };
				break;

			}

			empList = hrSF.getAllEmpList(searchCondition, paramArray);

			modelMap.put("gridRowJson", empList);
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

	public ModelAndView searchEmpInfo(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String empCode = request.getParameter("empCode");

		EmpInfoTO empInfoTO = null;

		try {
			empInfoTO = hrSF.getEmpInfo(companyCode, empCode);

			modelMap.put("empInfo", empInfoTO);
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

	public ModelAndView checkUserIdDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newUserId = request.getParameter("newUseId");

		try {
			Boolean flag = hrSF.checkUserIdDuplication(companyCode, newUserId);

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

	public ModelAndView checkEmpCodeDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = request.getParameter("newEmpCode");

		try {

			Boolean flag = hrSF.checkEmpCodeDuplication(companyCode, newEmpCode);

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

	public ModelAndView getNewEmpCode(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = null;

		try {
			newEmpCode = hrSF.getNewEmpCode(companyCode);

			modelMap.put("newEmpCode", newEmpCode);
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

			ArrayList<EmployeeBasicTO> empBasicList = null;
			ArrayList<EmployeeDetailTO> empDetailList = null;
			ArrayList<EmployeeSecretTO> empSecretList = null;

			HashMap<String, Object> resultMap = null;

			if (tableName.equals("BASIC")) {

				empBasicList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeBasicTO>>() {
				}.getType());

				resultMap = hrSF.batchEmpBasicListProcess(empBasicList);

			} else if (tableName.equals("DETAIL")) {

				empDetailList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeDetailTO>>() {
				}.getType());
				System.out.println(gson.toJson(empDetailList));
				
				resultMap = hrSF.batchEmpDetailListProcess(empDetailList);

			} else if (tableName.equals("SECRET")) {

				empSecretList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeSecretTO>>() {
				}.getType());

				System.out.println(gson.toJson(empSecretList));

				
				resultMap = hrSF.batchEmpSecretListProcess(empSecretList);

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

}
