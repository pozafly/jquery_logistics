package com.project.aesup.base.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.base.to.CodeDetailTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class CodeDetailDAOImpl extends IbatisSupportDAO implements CodeDetailDAO {


	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<CodeDetailTO> selectDetailCodeList(String divisionCode) {

		return (ArrayList<CodeDetailTO>) getSqlMapClientTemplate().queryForList("codeDetail.selectDetailCodeList", divisionCode);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertDetailCode(CodeDetailTO bean) {

		getSqlMapClientTemplate().insert("codeDetail.insertDetailCode", bean);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateDetailCode(CodeDetailTO bean) {
		
		getSqlMapClientTemplate().update("codeDetail.updateDetailCode", bean);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteDetailCode(CodeDetailTO bean) {

		getSqlMapClientTemplate().delete("codeDetail.deleteDetailCode", bean);

	}

	@Override
	public void changeCodeUseCheck(String divisionCodeNo, String detailCode, String codeUseCheck) {

		HashMap<String, String> map = new HashMap<>();

		map.put("divisionCodeNo", divisionCodeNo);
		map.put("detailCode", detailCode);
		map.put("codeUseCheck", codeUseCheck);


	}

}
