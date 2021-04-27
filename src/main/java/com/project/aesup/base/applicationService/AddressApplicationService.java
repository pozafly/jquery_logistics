package com.project.aesup.base.applicationService;

import java.util.ArrayList;

import com.project.aesup.base.to.AddressTO;

public interface AddressApplicationService {
		
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
}
