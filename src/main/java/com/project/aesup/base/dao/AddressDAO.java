package com.project.aesup.base.dao;

import java.util.ArrayList;

import com.project.aesup.base.to.AddressTO;

public interface AddressDAO {

	public String selectSidoCode(String sidoName);
	
	public ArrayList<AddressTO> selectRoadNameAddressList(String sidoCode, String searchValue, String buildingMainNumber);
	
	public ArrayList<AddressTO> selectJibunAddressList(String sidoCode, String searchValue, String jibunMainAddress);

	
}
