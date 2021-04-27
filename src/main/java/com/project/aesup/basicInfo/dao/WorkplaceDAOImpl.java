package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.WorkplaceTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class WorkplaceDAOImpl extends IbatisSupportDAO implements WorkplaceDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<WorkplaceTO> selectWorkplaceList(String companyCode) {

		return (ArrayList<WorkplaceTO>) getSqlMapClientTemplate().queryForList("workplace.selectWorkplaceList", companyCode);
 
	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertWorkplace(WorkplaceTO bean) {

		getSqlMapClientTemplate().insert("workplace.insertWorkplace", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateWorkplace(WorkplaceTO bean) {

		getSqlMapClientTemplate().update("workplace.updateWorkplace", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteWorkplace(WorkplaceTO bean) {

		getSqlMapClientTemplate().delete("workplace.deleteWorkplace", bean);

	}

}