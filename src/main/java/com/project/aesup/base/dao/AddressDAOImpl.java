package com.project.aesup.base.dao;


import java.util.HashMap;

import com.project.aesup.base.to.AddressTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

import java.util.ArrayList;


public class AddressDAOImpl extends IbatisSupportDAO implements AddressDAO {

	@SuppressWarnings("deprecation")
	@Override
	public String selectSidoCode(String sidoName) {

		return (String)getSqlMapClientTemplate().queryForObject("address.selectSidoCode", sidoName);
			
	}
    
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<AddressTO> selectRoadNameAddressList(String sidoCode, String searchValue, String buildingMainNumber) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("sidoCode", sidoCode);
		map.put("searchValue", searchValue);
		map.put("buildingMainNumber", buildingMainNumber);
		
		return (ArrayList<AddressTO>)getSqlMapClientTemplate().queryForList("address.selectRoadNameAddressList",map);


			}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<AddressTO> selectJibunAddressList(String sidoCode, String searchValue, String jibunMainAddress) {


		HashMap<String, Object> map = new HashMap<>();
		map.put("sidoCode", sidoCode);
		map.put("searchValue", searchValue);
		map.put("jibunMainAddress", jibunMainAddress);
		
		return (ArrayList<AddressTO>)getSqlMapClientTemplate().queryForList("address.selectJibunAddressList",map);


	}

}
