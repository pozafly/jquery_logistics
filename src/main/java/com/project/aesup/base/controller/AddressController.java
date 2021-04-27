package com.project.aesup.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.aesup.base.serviceFacade.BaseServiceFacade;
import com.project.aesup.base.to.AddressTO;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class AddressController extends MultiActionController {

	// serviceFacade 참조변수 선언
	private BaseServiceFacade baseSF;
	public void setBaseSF(BaseServiceFacade baseSF) {
		this.baseSF= baseSF;
	}
	
	ModelAndView modelAndView = null;
	ModelMap modelmap = new ModelMap();


	public ModelAndView searchAddressList(HttpServletRequest request, HttpServletResponse response) {

		String sidoName = request.getParameter("sidoName");
		String searchAddressType = request.getParameter("searchAddressType");
		String searchValue = request.getParameter("searchValue");
		String mainNumber = request.getParameter("mainNumber");


		try {

			ArrayList<AddressTO> addressList = baseSF.getAddressList(sidoName, searchAddressType, searchValue,
					mainNumber);

			modelmap.put("addressList", addressList);
			modelmap.put("errorCode", 1);
			modelmap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelmap.put("errorCode", -1);
			modelmap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", modelmap);
		return modelAndView;
	}

}
