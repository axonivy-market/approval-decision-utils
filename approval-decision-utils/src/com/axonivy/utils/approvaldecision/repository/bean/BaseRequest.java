package com.axonivy.utils.approvaldecision.repository.bean;

import java.util.ArrayList;
import java.util.List;

public class BaseRequest<T extends BaseApprovalHistory>{

	private String id;
	
	private Long caseId;
	
	private List<T> approvalHistories = new ArrayList<>();

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public List<T> getApprovalHistories() {
		return approvalHistories;
	}

	public void setApprovalHistories(List<T> approvalHistories) {
		this.approvalHistories = approvalHistories;
	}

}
