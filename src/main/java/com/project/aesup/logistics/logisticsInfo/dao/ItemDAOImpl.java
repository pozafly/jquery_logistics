package com.project.aesup.logistics.logisticsInfo.dao;


import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.logisticsInfo.to.ItemInfoTO;
import com.project.aesup.logistics.logisticsInfo.to.ItemTO;

public class ItemDAOImpl extends IbatisSupportDAO implements ItemDAO {


	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<ItemInfoTO> selectAllItemList() {

		return (ArrayList<ItemInfoTO>)getSqlMapClientTemplate().queryForList("item.selectAllItemList");
		
	}

	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<ItemInfoTO> selectItemList(String searchCondition, String paramArray[]) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);

		if (paramArray != null) {

			for (int i = 0; i < paramArray.length; i++) {

				switch (i + "") {

				case "0":

					if (searchCondition.equals("ITEM_CLASSIFICATION")) {

						map.put("itemClassification", paramArray[0]);

					} else if (searchCondition.equals("ITEM_GROUP_CODE")) {

						map.put("itemGroupCode", paramArray[0]);

					} else if (searchCondition.equals("STANDARD_UNIT_PRICE")) {

						map.put("minPrice", paramArray[0]);

					}

					break;

				case "1":

					map.put("maxPrice", paramArray[1]);

					break;

				}

			}

		}
		return (ArrayList<ItemInfoTO>)getSqlMapClientTemplate().queryForList("item.selectItemList",map);

	
	}

	
	@SuppressWarnings( "deprecation" )
	public void insertItem(ItemTO TO) {
  
		getSqlMapClientTemplate().queryForList("item.insertItem",TO);
		
	}

	@SuppressWarnings( "deprecation" )
	public void updateItem(ItemTO TO) {
		
		getSqlMapClientTemplate().queryForList("item.updateItem",TO);

		
	}

	@SuppressWarnings( "deprecation" )
	public void deleteItem(ItemTO TO) {

		getSqlMapClientTemplate().queryForList("item.deleteItem",TO);

	}

	
	@SuppressWarnings( "deprecation" )
	@Override
	public int selectItemprice(String itemcode) {
		
		return (Integer)getSqlMapClientTemplate().queryForObject("item.selectItemprice",itemcode);
		
    }
}