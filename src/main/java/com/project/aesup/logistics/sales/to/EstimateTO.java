package com.project.aesup.logistics.sales.to;

import java.util.ArrayList;

import com.project.aesup.base.to.BaseTO;

public class EstimateTO extends BaseTO {
	private String effectiveDate;
	private String estimateNo;
	private String estimateRequester;
	private String description;
	private String contractStatus;
	private String customerCode;
	private String personCodeInCharge;
	private String personNameCharge;
	private String estimateDate;
	private String customerName;
	private String empCodeName;
	private ArrayList<EstimateDetailTO> estimateDetailTOList;

	 public String getEffectiveDate() { 
		 return effectiveDate;
	 }
	 public void setEffectiveDate(String effectiveDate) { 
		 this.effectiveDate = effectiveDate;
	 }
	 public String getEstimateNo() {
		 return estimateNo;
	 }
	 public void setEstimateNo(String estimateNo) {
		 this.estimateNo = estimateNo;
	 }
	 public String getEstimateRequester() {
		 return estimateRequester;
	 }
	 public void setEstimateRequester(String estimateRequester) {
		 this.estimateRequester = estimateRequester;
	 }
	 public String getDescription() { 
		 return description;
	 }
	 public void setDescription(String description) { 
		 this.description = description;
	 }
	 public String getContractStatus() { 
		 return contractStatus;
	 }
	 public void setContractStatus(String contractStatus) { 
		 this.contractStatus = contractStatus;
	 }
	 public String getCustomerCode() { 
		 return customerCode;
	 }
	 public void setCustomerCode(String customerCode) { 
		 this.customerCode = customerCode;
	 }
	 public String getPersonCodeInCharge() { 
		 return personCodeInCharge;
	 }
	 public String getPersonNameCharge() { 
		 return personNameCharge;
	 }
	 public void setPersonNameCharge(String personNameCharge) { 
		 this.personNameCharge = personNameCharge;
	 }
	 public void setPersonCodeInCharge(String personCodeInCharge) { 
		 this.personCodeInCharge = personCodeInCharge;
	 }
	 public String getEstimateDate() { 
		 return estimateDate;
	 }
	 public void setEstimateDate(String estimateDate) { 
		 this.estimateDate = estimateDate;
	 }
	 public String getCustomerName() {
		 return customerName;
	 }
	 public void setCustomerName(String customerName) {
		 this.customerName = customerName;
	 }
	 public String getEmpCodeName() {
		 return empCodeName;
	 }
	 public void setEmpCodeName(String empCodeName) {
		 this.empCodeName = empCodeName;
	 }
	 public ArrayList<EstimateDetailTO> getEstimateDetailTOList() {
		return estimateDetailTOList;
	}
	public void setEstimateDetailTOList(ArrayList<EstimateDetailTO> estimateDetailTOList) {
		this.estimateDetailTOList = estimateDetailTOList;
	}
}