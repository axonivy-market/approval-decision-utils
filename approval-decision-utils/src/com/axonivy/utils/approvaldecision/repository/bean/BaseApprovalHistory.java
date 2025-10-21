package com.axonivy.utils.approvaldecision.repository.bean;

import java.time.Instant;
import java.time.LocalDateTime;

public class BaseApprovalHistory {

	private LocalDateTime approvalDate;

	private String comment;

	private String decision;

	// transient
	private Boolean isEditing;

	private String selectedConfirmations;

	private String modifiedByUserName;

	private Instant modifiedDate;

	// transient
	private String displayUserName;

	// transient
	private String displayApprovalDate;

	// transient
	private String sortableApprovalDate;

	public LocalDateTime getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDateTime approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Boolean getIsEditing() {
		return isEditing;
	}

	public void setIsEditing(Boolean isEditing) {
		this.isEditing = isEditing;
	}

	public String getModifiedByUserName() {
		return modifiedByUserName;
	}

	public void setModifiedByUserName(String modifiedByUserName) {
		this.modifiedByUserName = modifiedByUserName;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getSelectedConfirmations() {
		return selectedConfirmations;
	}

	public void setSelectedConfirmations(String selectedConfirmations) {
		this.selectedConfirmations = selectedConfirmations;
	}

	public String getDisplayUserName() {
		return displayUserName;
	}

	public void setDisplayUserName(String displayUserName) {
		this.displayUserName = displayUserName;
	}

	public String getDisplayApprovalDate() {
		return displayApprovalDate;
	}

	public void setDisplayApprovalDate(String displayApprovalDate) {
		this.displayApprovalDate = displayApprovalDate;
	}

	public String getSortableApprovalDate() {
		return sortableApprovalDate;
	}

	public void setSortableApprovalDate(String sortableApprovalDate) {
		this.sortableApprovalDate = sortableApprovalDate;
	}

}
