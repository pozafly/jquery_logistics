package com.project.aesup.logistics.purchase.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.purchase.to.BomDeployTO;
import com.project.aesup.logistics.purchase.to.BomInfoTO;
import com.project.aesup.logistics.purchase.to.BomTO;

public class BomDAOImpl extends IbatisSupportDAO implements BomDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<BomDeployTO> selectBomDeployList(String deployCondition, String itemCode, String itemClassification) {

		HashMap<String, String> map = new HashMap<>();
		map.put("deployCondition", deployCondition);
		map.put("itemCode", itemCode);
		map.put("itemClassification", itemClassification);

		return (ArrayList<BomDeployTO>) getSqlMapClientTemplate().queryForList("bom.selectBomDeployList", map);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<BomInfoTO> selectBomInfoList(String parentItemCode) {

		return (ArrayList<BomInfoTO>) getSqlMapClientTemplate().queryForList("bom.selectBomInfoList", parentItemCode);
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<BomInfoTO> selectAllItemWithBomRegisterAvailable() {

		return (ArrayList<BomInfoTO>) getSqlMapClientTemplate().queryForList("bom.selectAllItemWithBomRegisterAvailable");

	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertBom(BomTO bean) {

		getSqlMapClientTemplate().insert("bom.insertBom", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateBom(BomTO bean) {

		getSqlMapClientTemplate().update("bom.updateBom", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteBom(BomTO bean) {

		getSqlMapClientTemplate().delete("bom.deleteBom", bean);

	}

}
