package com.project.aesup.authorityManager.dao;

import java.util.HashMap;

import com.project.aesup.authorityManager.to.UserMenuTO;

public interface UserMenuDAO {

	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode, String positionCode);

	
}
