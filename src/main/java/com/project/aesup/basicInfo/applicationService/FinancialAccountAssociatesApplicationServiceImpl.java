package com.project.aesup.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.project.aesup.base.dao.CodeDetailDAO;
import com.project.aesup.base.to.CodeDetailTO;
import com.project.aesup.basicInfo.dao.FinancialAccountAssociatesDAO;
import com.project.aesup.basicInfo.to.FinancialAccountAssociatesTO;

public class FinancialAccountAssociatesApplicationServiceImpl implements FinancialAccountAssociatesApplicationService {

	// 참조변수 선언
	private FinancialAccountAssociatesDAO associatesDAO;
	private CodeDetailDAO codeDetailDAO;
	
	public void setAssociatesDAO(FinancialAccountAssociatesDAO associatesDAO) {
		this.associatesDAO = associatesDAO;
	}
	public void setCodeDetailDAO(CodeDetailDAO codeDetailDAO) {
		this.codeDetailDAO = codeDetailDAO;
	}
	
	
	public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
			String workplaceCode) {

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;

		switch (searchCondition) {

		case "ALL":

			financialAccountAssociatesList = associatesDAO.selectFinancialAccountAssociatesListByCompany();
			break;

		case "WORKPLACE":

			financialAccountAssociatesList = associatesDAO
					.selectFinancialAccountAssociatesListByWorkplace(workplaceCode);
			break;

		}


		return financialAccountAssociatesList;
	}

	public String getNewFinancialAccountAssociatesCode() {


		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;
		String newFinancialAccountAssociatesCode = null;

		financialAccountAssociatesList = associatesDAO
				.selectFinancialAccountAssociatesListByCompany();

		TreeSet<Integer> financialAccountAssociatesCodeNoSet = new TreeSet<>();

		for (FinancialAccountAssociatesTO bean : financialAccountAssociatesList) {

			if (bean.getAccountAssociatesCode().startsWith("FPT-")) {

				try {

					Integer no = Integer.parseInt(bean.getAccountAssociatesCode().split("FPT-")[1]);
					financialAccountAssociatesCodeNoSet.add(no);

				} catch (NumberFormatException e) {

					// "FPT-" 다음 부분을 Integer 로 변환하지 못하는 경우 : 그냥 다음 반복문 실행

				}

			}

		}

		if (financialAccountAssociatesCodeNoSet.isEmpty()) {
			newFinancialAccountAssociatesCode = "FPT-" + String.format("%02d", 1);
		} else {
			newFinancialAccountAssociatesCode = "FPT-"
					+ String.format("%02d", financialAccountAssociatesCodeNoSet.pollLast() + 1);
		}

		return newFinancialAccountAssociatesCode;
	}

	public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
			ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList) {


		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeBean = new CodeDetailTO();

			for (FinancialAccountAssociatesTO bean : financialAccountAssociatesList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				// 새로운 금융거래처번호 생성 후 bean 에 set
				String newFinancialAccountAssociatesCode = getNewFinancialAccountAssociatesCode();
				bean.setAccountAssociatesCode(newFinancialAccountAssociatesCode);

				// 금융거래처 테이블에 insert
				associatesDAO.insertFinancialAccountAssociates(bean);
				insertList.add(bean.getAccountAssociatesCode());

				// CODE_DETAIL 테이블에 Insert
				detailCodeBean.setDivisionCodeNo("CL-02");
				detailCodeBean.setDetailCode(bean.getAccountAssociatesCode());
				detailCodeBean.setDetailCodeName(bean.getAccountAssociatesName());

				codeDetailDAO.insertDetailCode(detailCodeBean);

				break;

			case "UPDATE":

				associatesDAO.updateFinancialAccountAssociates(bean);
				updateList.add(bean.getAccountAssociatesCode());

				// CODE_DETAIL 테이블에 Update
				detailCodeBean.setDivisionCodeNo("CL-02");
				detailCodeBean.setDetailCode(bean.getAccountAssociatesCode());
				detailCodeBean.setDetailCodeName(bean.getAccountAssociatesName());

				codeDetailDAO.updateDetailCode(detailCodeBean);

				break;

			case "DELETE":

				associatesDAO.deleteFinancialAccountAssociates(bean);
				deleteList.add(bean.getAccountAssociatesCode());

				// CODE_DETAIL 테이블에 Delete
				detailCodeBean.setDivisionCodeNo("CL-02");
				detailCodeBean.setDetailCode(bean.getAccountAssociatesCode());
				detailCodeBean.setDetailCodeName(bean.getAccountAssociatesName());

				codeDetailDAO.deleteDetailCode(detailCodeBean);

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);

		return resultMap;
	}

}
