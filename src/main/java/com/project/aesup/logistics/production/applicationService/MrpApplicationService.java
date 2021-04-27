package com.project.aesup.logistics.production.applicationService;

import java.util.ArrayList; 
import java.util.HashMap;

import com.project.aesup.logistics.production.to.MrpGatheringTO;
import com.project.aesup.logistics.production.to.MrpTO;

public interface MrpApplicationService {

	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition);
	
	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate);
	
	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo);
	
	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate, String endDate);
	
	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr);

	public HashMap<String, Object> registerMrp(String mrpRegisterDate, 
			ArrayList<MrpTO> newMrpList);
	
	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList);
	
	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr);
	
	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate, 
			ArrayList<MrpGatheringTO> newMrpGatheringList, HashMap<String, String> mrpNoAndItemCodeMap );
	
}
