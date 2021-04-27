package com.project.aesup.logistics.production.dao;

import java.util.ArrayList;

import com.project.aesup.logistics.production.to.MrpGatheringTO;

public interface MrpGatheringDAO {

	public ArrayList<MrpGatheringTO> getMrpGathering(String mrpNoList);
	
	public ArrayList<MrpGatheringTO> selectMrpGatheringList(String searchDateCondition, String startDate, String endDate);
	
	public int selectMrpGatheringCount(String mrpGatheringRegisterDate);
	
	public void insertMrpGathering(MrpGatheringTO TO);
	
	public void updateMrpGathering(MrpGatheringTO TO);
	
	public void deleteMrpGathering(MrpGatheringTO TO);
	
}
