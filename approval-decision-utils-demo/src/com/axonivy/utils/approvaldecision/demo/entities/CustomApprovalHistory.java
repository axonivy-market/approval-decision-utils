package com.axonivy.utils.approvaldecision.demo.entities;

import com.axonivy.utils.approvaldecision.repository.bean.BaseApprovalHistory;

public class CustomApprovalHistory extends BaseApprovalHistory {
	private Integer priority;

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
