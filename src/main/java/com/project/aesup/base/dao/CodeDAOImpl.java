package com.project.aesup.base.dao;

import java.util.ArrayList;

import com.project.aesup.base.to.CodeTO;
import com.project.aesup.common.dao.IbatisSupportDAO;

public class CodeDAOImpl extends IbatisSupportDAO implements CodeDAO {


	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<CodeTO> selectCodeList() {

		return (ArrayList<CodeTO>)getSqlMapClientTemplate().queryForList("code.selectCodeList");

	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertCode(CodeTO bean) {

		getSqlMapClientTemplate().insert("code.insertCode", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateCode(CodeTO bean) {

		getSqlMapClientTemplate().update("code.updateCode", bean);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteCode(CodeTO bean) {

		getSqlMapClientTemplate().delete("code.deleteCode", bean);
	}

}
