package com.project.aesup.authorityManager.applicationService;

import com.project.aesup.authorityManager.exception.IdNotFoundException;
import com.project.aesup.authorityManager.exception.PwMissMatchException;
import com.project.aesup.authorityManager.exception.PwNotFoundException;
import com.project.aesup.hr.to.EmpInfoTO;

public interface LogInApplicationService {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

}
