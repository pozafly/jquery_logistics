package com.project.aesup.authorityManager.serviceFacade;

import javax.servlet.ServletContext;

import com.project.aesup.authorityManager.applicationService.LogInApplicationService;
import com.project.aesup.authorityManager.applicationService.UserMenuApplicationService;
import com.project.aesup.authorityManager.exception.IdNotFoundException;
import com.project.aesup.authorityManager.exception.PwMissMatchException;
import com.project.aesup.authorityManager.exception.PwNotFoundException;
import com.project.aesup.hr.to.EmpInfoTO;

public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	private LogInApplicationService logInAS;
	private UserMenuApplicationService userMenuAS;
	
	public void setLogInAS(LogInApplicationService logInAS) {
		this.logInAS = logInAS;
	}
	public void setUserMenuAS(UserMenuApplicationService userMenuAS) {
		this.userMenuAS = userMenuAS;
	}

	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {

		return logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);
	}

	@Override
	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application) {

		System.out.println("		@ DB 연동 : getUserMenuCode");
		return userMenuAS.getUserMenuCode(workplaceCode, deptCode, positionCode, application);
	}

}
