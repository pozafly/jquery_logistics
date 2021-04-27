package com.project.aesup.logistics.production.to;

public class WorkOrderInfoTO {

	private String workOrderNo;
	private String mrpNo;
	private String mpsNo;
	private String mrpGatheringNo;
	private String itemClassification;
	private String itemCode;
	private String itemName;
	private String unitOfMrp;
	private String requiredAmount;
	
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	public String getMrpNo() {
		return mrpNo;
	}
	public void setMrpNo(String mrpNo) {
		this.mrpNo = mrpNo;
	}
	public String getMpsNo() {
		return mpsNo;
	}
	public void setMpsNo(String mpsNo) {
		this.mpsNo = mpsNo;
	}
	public String getMrpGatheringNo() {
		return mrpGatheringNo;
	}
	public void setMrpGatheringNo(String mrpGatheringNo) {
		this.mrpGatheringNo = mrpGatheringNo;
	}
	public String getItemClassification() {
		return itemClassification;
	}
	public void setItemClassification(String itemClassification) {
		this.itemClassification = itemClassification;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUnitOfMrp() {
		return unitOfMrp;
	}
	public void setUnitOfMrp(String unitOfMrp) {
		this.unitOfMrp = unitOfMrp;
	}
	public String getRequiredAmount() {
		return requiredAmount;
	}
	public void setRequiredAmount(String requiredAmount) {
		this.requiredAmount = requiredAmount;
	}
	
}
