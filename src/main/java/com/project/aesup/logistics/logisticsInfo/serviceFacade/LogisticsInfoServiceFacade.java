package com.project.aesup.logistics.logisticsInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.logisticsInfo.to.ItemInfoTO;
import com.project.aesup.logistics.logisticsInfo.to.ItemTO;

public interface LogisticsInfoServiceFacade {

	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray);
	
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList);
	
    public int getItemprice(String itemcode);
}
