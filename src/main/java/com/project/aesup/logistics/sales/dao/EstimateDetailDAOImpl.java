package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.sales.to.EstimateDetailTO;

public class EstimateDetailDAOImpl extends IbatisSupportDAO implements EstimateDetailDAO {


	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<EstimateDetailTO> selectEstimateDetailList(String estimateNo) {

		return (ArrayList<EstimateDetailTO>) getSqlMapClientTemplate().queryForList("estimateDetail.selectEstimateDetailList",
				estimateNo);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public int selectEstimateDetailCount(String estimateNo) {
		
		List<EstimateDetailTO> estimateDetailNoList = getSqlMapClientTemplate()
				.queryForList("estimateDetail.selectEstimateDetailCount", estimateNo);

		TreeSet<Integer> intSet = new TreeSet<>();

		for (EstimateDetailTO bean : estimateDetailNoList) {

			String estimateDetailNo = bean.getEstimateDetailNo();
			int no = Integer.parseInt(estimateDetailNo.split("-")[1]);

			intSet.add(no);
		}

		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1;
		}
			
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void insertEstimateDetail(EstimateDetailTO bean) {

		getSqlMapClientTemplate().insert("estimateDetail.insertEstimateDetail", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateEstimateDetail(EstimateDetailTO bean) {

		getSqlMapClientTemplate().update("estimateDetail.updateEstimateDetail", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteEstimateDetail(EstimateDetailTO bean) {

		getSqlMapClientTemplate().delete("estimateDetail.deleteEstimateDetail", bean);

	}
}
