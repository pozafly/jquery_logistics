package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.project.aesup.logistics.sales.to.SalesPlanTO;

public class SalesPlanDAOImpl extends IbatisSupportDAO implements SalesPlanDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<SalesPlanTO> selectSalesPlanList(String dateSearchCondition, String startDate, String endDate) {

		HashMap<String, String> map = new HashMap<>();

		map.put("dateSearchCondition", dateSearchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<SalesPlanTO>) getSqlMapClientTemplate().queryForList("salesPlan.selectSalesPlanList", map);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int selectSalesPlanCount(String salesPlanDate) {

		Integer i = (Integer) getSqlMapClientTemplate().queryForObject("salesPlan.selectSalesPlanCount", salesPlanDate);

		return i + 1;

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<SalesPlanInMpsAvailableTO> selectSalesPlanListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

		HashMap<String, String> map = new HashMap<>();

		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<SalesPlanInMpsAvailableTO>) getSqlMapClientTemplate()
				.queryForList("salesPlan.selectSalesPlanListInMpsAvailable", map);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertSalesPlan(SalesPlanTO bean) {

		getSqlMapClientTemplate().insert("salesPlan.insertSalesPlan", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateSalesPlan(SalesPlanTO bean) {

		getSqlMapClientTemplate().update("salesPlan.updateSalesPlan", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeMpsStatusOfSalesPlan(String salesPlanNo, String mpsStatus) {

		HashMap<String, String> map = new HashMap<>();
		map.put("salesPlanNo", salesPlanNo);
		map.put("mpsStatus", mpsStatus);

		getSqlMapClientTemplate().update("salesPlan.changeMpsStatusOfSalesPlan", map);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteSalesPlan(SalesPlanTO bean) {

		getSqlMapClientTemplate().delete("salesPlan.deleteSalesPlan", bean);

	}

}
