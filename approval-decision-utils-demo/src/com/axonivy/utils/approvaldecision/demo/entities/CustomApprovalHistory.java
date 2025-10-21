package com.axonivy.utils.approvaldecision.demo.entities;

import com.axonivy.utils.approvaldecision.repository.bean.BaseApprovalHistory;

public class CustomApprovalHistory extends BaseApprovalHistory {
	private String approverEmail;

	public String getApproverEmail() {
		return approverEmail;
	}

	public void setApproverEmail(String approverEmail) {
		this.approverEmail = approverEmail;
	}
}
