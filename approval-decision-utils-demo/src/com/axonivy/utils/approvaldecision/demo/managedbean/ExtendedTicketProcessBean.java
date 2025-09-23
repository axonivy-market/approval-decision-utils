package com.axonivy.utils.approvaldecision.demo.managedbean;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.axonivy.utils.approvaldecision.demo.contentstate.TicketProcessContentState;
import com.axonivy.utils.approvaldecision.demo.entities.ExtendedTicketRequest;
import com.axonivy.utils.approvaldecision.demo.enums.Department;
import com.axonivy.utils.approvaldecision.demo.enums.ProcessStep;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalConfirmation;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalDecision;
import com.axonivy.utils.approvaldecision.demo.utils.TicketProcessUtils;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * Bean for {@link ExtendedTicketRequest}
 */
public class ExtendedTicketProcessBean {

	private ExtendedTicketRequest request;
	private ExtendTicketRequestBean approvalDecisionBean;
	private TicketProcessContentState contentState;
	private Map<String, String> departmentMails;

	private ProcessStep processStep;

	public ExtendedTicketProcessBean(ProcessStep processStep) {
		this.processStep = processStep;
		init();
	}

	private void init() {
		contentState = new TicketProcessContentState();
		Long caseId = Ivy.wfCase().getId();
		
		if (processStep == ProcessStep.REQUEST_TICKET) {
			approvalDecisionBean = new ExtendTicketRequestBean(caseId,
					TicketProcessApprovalDecision.getRequestApprovalDecision(), null);
			contentState.initRequestTicketContentState();
			initForwardEmail();
			onChangeDecision();
		} else if (processStep == ProcessStep.REVIEW_TICKET) {
			approvalDecisionBean = new ExtendTicketRequestBean(caseId,
					TicketProcessApprovalDecision.getReviewApprovalDecision(), null);
			contentState.initReviewTicketContentState();
		} else if (processStep == ProcessStep.CONFIRM_TICKET) {
			approvalDecisionBean = new ExtendTicketRequestBean(caseId,
					TicketProcessApprovalDecision.getConfirmApprovalDecision(),
					TicketProcessApprovalConfirmation.getConfirmApprovalConfirmation());
			contentState.initConfirmTicketContentState();
		} else {
			approvalDecisionBean = new ExtendTicketRequestBean(caseId, null, null);
			contentState.initResultTicketContentState();
		}
		request = approvalDecisionBean.getRequest();
		if(request.getId() == null) {
			initTestRequestData();
		}
	}

	private void initTestRequestData() {
		request.setTicketTitle("Cinema Ticket");
		request.setTicketNumber("0123456789");
		request.setTicketType("Diamond");
		request.setTicketRaiser("Hero");
		request.setTicketDescription("hello world");
	}

	private void initForwardEmail() {
		this.departmentMails = new HashMap<>();
		for (Department department : Department.values()) {
			departmentMails.put(department.getName(), department.getEmail());
		}
	}

	public void save() {
		ExtendedTicketRequest saved = approvalDecisionBean.handleForSave();
		setRequest(saved);
		TicketProcessUtils.showInfo();
	}

	public void submit() {
		if (processStep == ProcessStep.CONFIRM_TICKET) {
			approvalDecisionBean.getApprovalHistory().setDecision(TicketProcessApprovalDecision.COMPLETE.name());
		}
		ExtendedTicketRequest saved = approvalDecisionBean.handleForSubmit();
		setRequest(saved);
	}

	public void cancel() throws MalformedURLException {
		Ivy.wfTask().reset();
		TicketProcessUtils.navigateToHomePage();
	}

	public void onChangeDecision() {
		this.contentState.setShowDropdownOfMails(false);
		if (TicketProcessApprovalDecision.FORWARD_TO.name()
				.equals(this.approvalDecisionBean.getApprovalHistory().getDecision())) {
			this.contentState.setShowDropdownOfMails(true);
		}
	}

	public ExtendedTicketRequest getRequest() {
		return request;
	}

	public void setRequest(ExtendedTicketRequest request) {
		this.request = request;
	}

	public ExtendTicketRequestBean getApprovalDecisionBean() {
		return approvalDecisionBean;
	}

	public void setApprovalDecisionBean(ExtendTicketRequestBean approvalDecisionBean) {
		this.approvalDecisionBean = approvalDecisionBean;
	}

	public TicketProcessContentState getContentState() {
		return contentState;
	}

	public void setContentState(TicketProcessContentState contentState) {
		this.contentState = contentState;
	}

	public Map<String, String> getDepartmentMails() {
		return departmentMails;
	}

	public void setDepartmentMails(Map<String, String> departmentMails) {
		this.departmentMails = departmentMails;
	}

}
