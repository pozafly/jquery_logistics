package com.project.aesup.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.project.aesup.common.dao.IbatisSupportDAO;
import com.project.aesup.logistics.production.to.MrpGatheringTO;

public class MrpGatheringDAOImpl extends IbatisSupportDAO implements MrpGatheringDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<MrpGatheringTO> getMrpGathering(String mrpNoList) {

		return (ArrayList<MrpGatheringTO>) getSqlMapClientTemplate().queryForList("mrpGathering.getMrpGathering",
				mrpNoList);

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<MrpGatheringTO> selectMrpGatheringList(String searchDateCondition, String startDate,
			String endDate) {

		HashMap<String, String> map = new HashMap<>();
		
		map.put("searchDateCondition", searchDateCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<MrpGatheringTO>) getSqlMapClientTemplate().queryForList("mrpGathering.selectMrpGatheringList",
				map);

	}

	@Override
	public int selectMrpGatheringCount(String mrpGatheringRegisterDate) {

		@SuppressWarnings({ "unchecked", "deprecation" })
		List<MrpGatheringTO> mrpGatheringTOList = getSqlMapClientTemplate()
				.queryForList("mrpGathering.selectMrpGatheringCount", mrpGatheringRegisterDate);

		TreeSet<Integer> intSet = new TreeSet<>();

		for (MrpGatheringTO bean : mrpGatheringTOList) {

			String mrpGatheringNo = bean.getMrpGatheringNo();

			// mrpGathering 일련번호에서 마지막 2자리만 가져오기
			int no = Integer.parseInt(mrpGatheringNo.substring(mrpGatheringNo.length() - 2, mrpGatheringNo.length()));

			intSet.add(no);
		}

		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1; // 가장 높은 번호 + 1
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertMrpGathering(MrpGatheringTO bean) {

		getSqlMapClientTemplate().insert("mrpGathering.insertMrpGathering", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateMrpGathering(MrpGatheringTO bean) {

		getSqlMapClientTemplate().update("mrpGathering.updateMrpGathering", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteMrpGathering(MrpGatheringTO bean) {

		getSqlMapClientTemplate().delete("mrpGathering.deleteMrpGathering", bean);

	}

}
