package com.project.aesup.authorityManager.dao;

import java.util.HashMap;
import java.util.List;

import com.project.aesup.authorityManager.to.UserMenuTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class UserMenuDAOImpl extends IbatisSupportDAO implements UserMenuDAO {

	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode,
			String positionCode) {

		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("workplaceCode", workplaceCode);
		map.put("deptCode", deptCode);
		map.put("positionCode", positionCode);
		
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<UserMenuTO> list = getSqlMapClientTemplate().queryForList("usermenu.selectUserMenuCodeList", map);
		
		HashMap<String, UserMenuTO> userMenuTOMap = new HashMap<>();

		for(UserMenuTO bean : list) {
			userMenuTOMap.put(bean.getNo() + "", bean);
		}

		return userMenuTOMap;

	}

}
