package com.project.aesup.authorityManager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ShowErrorPageController extends MultiActionController {
	
	ModelAndView modelAndView = null;
	ModelMap modelmap = new ModelMap();
	
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

		String viewName = "redirect:/hello.html";


		if (request.getRequestURI().contains("accessDenied")) {
			modelmap.put("errorCode", -1);
			modelmap.put("errorTitle", "Access Denied");
			modelmap.put("errorMsg", "액세스 거부되었습니다");
			viewName = "errorPage";
		}

		modelAndView = new ModelAndView(viewName, modelmap);
		return modelAndView;
	}

}
