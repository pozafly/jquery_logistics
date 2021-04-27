package com.project.aesup.basicInfo.dao;

import java.util.ArrayList;

import com.project.aesup.basicInfo.to.WorkplaceTO;

public interface WorkplaceDAO {
	
	public ArrayList<WorkplaceTO> selectWorkplaceList(String companyCode);

	public void insertWorkplace(WorkplaceTO TO);
	
	public void updateWorkplace(WorkplaceTO TO);
	
	public void deleteWorkplace(WorkplaceTO TO);
}
