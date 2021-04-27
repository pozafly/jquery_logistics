package com.project.aesup.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.purchase.dao.BomDAO;
import com.project.aesup.logistics.purchase.to.BomDeployTO;
import com.project.aesup.logistics.purchase.to.BomInfoTO;
import com.project.aesup.logistics.purchase.to.BomTO;

public class BomApplicationServiceImpl implements BomApplicationService {

	// DAO 참조변수 선언
	private BomDAO bomDAO;
	
	public void setBomDAO(BomDAO bomDAO) {
		this.bomDAO= bomDAO;
	}

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition) {

		return bomDAO.selectBomDeployList(deployCondition, itemCode, itemClassificationCondition);
	}
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		return bomDAO.selectBomInfoList(parentItemCode);
	}

	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

		return bomDAO.selectAllItemWithBomRegisterAvailable();

	}

	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		HashMap<String, Object> resultMap = new HashMap<>();

		int insertCount = 0;
		int updateCount = 0;
		int deleteCount = 0;

		for (BomTO TO : batchBomList) {

			String status = TO.getStatus();

			switch (status) {

			case "INSERT":

				bomDAO.insertBom(TO);

				insertCount++;

				break;

			case "UPDATE":

				bomDAO.updateBom(TO);

				updateCount++;

				break;

			case "DELETE":

				bomDAO.deleteBom(TO);

				deleteCount++;

				break;

			}

		}

		resultMap.put("INSERT", insertCount);
		resultMap.put("UPDATE", updateCount);
		resultMap.put("DELETE", deleteCount);

		return resultMap;
	}

}
