package com.project.aesup.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.aesup.logistics.sales.to.DeliveryInfoTO;

public interface DeliveryDAO {

	public ArrayList<DeliveryInfoTO> selectDeliveryInfoList();
	
	public HashMap<String,Object> deliver(String contractDetailNo);
	
	public void insertDeliveryResult(DeliveryInfoTO TO);

	public void updateDeliveryResult(DeliveryInfoTO TO);

	public void deleteDeliveryResult(DeliveryInfoTO TO);
}
