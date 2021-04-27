package com.project.aesup.authorityManager.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.project.aesup.authorityManager.exception.*;
import com.project.aesup.authorityManager.serviceFacade.AuthorityManagerServiceFacade;


import com.project.aesup.hr.to.EmpInfoTO;

public class MemberLogInController extends MultiActionController {

	private AuthorityManagerServiceFacade authorityManagerSF;
	
	public void setAuthorityManagerSF(AuthorityManagerServiceFacade authorityManagerSF) {
		this.authorityManagerSF = authorityManagerSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	public ModelAndView LogInCheck(HttpServletRequest request, HttpServletResponse response) {

		String viewName = "loginform";
		HttpSession session = request.getSession();

		String companyCode = request.getParameter("companyCode");
		String workplaceCode = request.getParameter("workplaceCode");
		String inputId = request.getParameter("userId");
		String inputPassWord = request.getParameter("userPassWord");

		try {
			if (companyCode.equals("") || workplaceCode.equals("") || inputId.equals("") || inputPassWord.equals("")) {
				throw new DataNotInputException("데이터가 입력되지 않았습니다.");
			}

			EmpInfoTO TO = authorityManagerSF.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);

			if (TO != null) {

				ServletContext application = request.getServletContext();
				session.setAttribute("userId", TO.getUserId());
				session.setAttribute("empCode", TO.getEmpCode());
				session.setAttribute("empName", TO.getEmpName());
				session.setAttribute("deptCode", TO.getDeptCode());
				session.setAttribute("deptName", TO.getDeptName());
				session.setAttribute("positionCode", TO.getPositionCode());
				session.setAttribute("positionName", TO.getPositionName());
				session.setAttribute("companyCode", TO.getCompanyCode());
				session.setAttribute("workplaceCode", workplaceCode);
				session.setAttribute("workplaceName", TO.getWorkplaceName());
							
				System.out.println("		@EmpInfo : deptCode : "+TO.getDeptCode());
				System.out.println("		@EmpInfo : PositionCode : "+TO.getPositionCode());
				String menuCode = authorityManagerSF.getUserMenuCode(workplaceCode, TO.getDeptCode(),
						TO.getPositionCode(), application);
				session.setAttribute("menuCode", menuCode);

				viewName = "redirect:/hello.html";
				System.out.println("로그인성공");
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());
		}

		modelAndView = new ModelAndView(viewName, modelMap);

		return modelAndView;
	}

	
	
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().invalidate();
		String viewName = "loginform";
		modelAndView = new ModelAndView(viewName, null);

		return modelAndView;
	}

}