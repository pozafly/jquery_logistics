package com.project.aesup.logistics.logisticsInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.logisticsInfo.applicationService.ItemApplicationService;
import com.project.aesup.logistics.logisticsInfo.to.ItemInfoTO;
import com.project.aesup.logistics.logisticsInfo.to.ItemTO;

public class LogisticsInfoServiceFacadeImpl implements LogisticsInfoServiceFacade {

	
	private ItemApplicationService itemAS;
	
	public void setItemAS(ItemApplicationService itemAS) {
		this.itemAS=itemAS;
		
	}

	@Override
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {

			return itemAS.getItemInfoList(searchCondition, paramArray);
			
	}

	@Override
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {

			return itemAS.batchItemListProcess(itemTOList);
			
	}

	@Override
	public int getItemprice(String itemcode) {

			return itemAS.getItemprice(itemcode);
					

	}

}
