package com.project.aesup.logistics.logisticsInfo.dao;

import java.util.ArrayList;

import com.project.aesup.logistics.logisticsInfo.to.ItemInfoTO;
import com.project.aesup.logistics.logisticsInfo.to.ItemTO;

public interface ItemDAO {

	public ArrayList<ItemInfoTO> selectAllItemList();
	
	public ArrayList<ItemInfoTO> selectItemList(String searchCondition, String paramArray[]);
	
	public void insertItem(ItemTO TO);
	
	public void updateItem(ItemTO TO);
	
	public void deleteItem(ItemTO TO);
	
	public int selectItemprice(String itemcode);
	
}
