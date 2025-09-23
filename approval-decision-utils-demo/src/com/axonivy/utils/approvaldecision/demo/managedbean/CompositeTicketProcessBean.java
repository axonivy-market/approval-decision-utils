package com.axonivy.utils.approvaldecision.demo.managedbean;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.axonivy.utils.approvaldecision.demo.contentstate.TicketProcessContentState;
import com.axonivy.utils.approvaldecision.demo.dao.CompositeTicketRequestDAO;
import com.axonivy.utils.approvaldecision.demo.entities.CompositeTicketRequest;
import com.axonivy.utils.approvaldecision.demo.enums.Department;
import com.axonivy.utils.approvaldecision.demo.enums.ProcessStep;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalConfirmation;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalDecision;
import com.axonivy.utils.approvaldecision.demo.utils.TicketProcessUtils;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * Bean for CompositeTicketRequest
 */
public class CompositeTicketProcessBean {

	private CompositeTicketRequest request;
	private CompositeTicketRequestBean approvalDecisionBean;
	private TicketProcessContentState contentState;
	private Map<String, String> departmentMails;

	private ProcessStep processStep;

	public CompositeTicketProcessBean(ProcessStep processStep) {
		this.processStep = processStep;
		init();
	}

	private void init() {
		Long caseId = Ivy.wfCase().getId();
		request = CompositeTicketRequestDAO.getInstance().findByCaseId(caseId);

		if (request == null) {
			request = new CompositeTicketRequest();
			request.setCaseId(caseId);
			initTestRequestData();
		}

		contentState = new TicketProcessContentState();

		if (processStep == ProcessStep.REQUEST_TICKET) {
			approvalDecisionBean = new CompositeTicketRequestBean(request,
					TicketProcessApprovalDecision.getRequestApprovalDecision(), null);
			contentState.initRequestTicketContentState();
			initForwardEmail();
			onChangeDecision();
		} else if (processStep == ProcessStep.REVIEW_TICKET) {
			approvalDecisionBean = new CompositeTicketRequestBean(request,
					TicketProcessApprovalDecision.getReviewApprovalDecision(), null);
			contentState.initReviewTicketContentState();
		} else if (processStep == ProcessStep.CONFIRM_TICKET) {
			approvalDecisionBean = new CompositeTicketRequestBean(request,
					TicketProcessApprovalDecision.getConfirmApprovalDecision(),
					TicketProcessApprovalConfirmation.getConfirmApprovalConfirmation());
			contentState.initConfirmTicketContentState();
		} else {
			approvalDecisionBean = new CompositeTicketRequestBean(request, null, null);
			contentState.initResultTicketContentState();
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

	private void handleSaving() {
		//TODO duplicated save
//		CompositeTicketRequest saved = CompositeTicketRequestDAO.getInstance().save(this.request);
//		setRequest(saved);
	}

	public void save() {
		approvalDecisionBean.handleApprovalHistoryBeforeSave();
		handleSaving();
		TicketProcessUtils.showInfo();
	}

	public void submit() {
		if (processStep == ProcessStep.CONFIRM_TICKET) {
			approvalDecisionBean.getApprovalHistory().setDecision(TicketProcessApprovalDecision.COMPLETE.name());
		}
		approvalDecisionBean.handleApprovalHistoryBeforeSubmit();
		handleSaving();
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

	public CompositeTicketRequest getRequest() {
		return request;
	}

	public void setRequest(CompositeTicketRequest request) {
		this.request = request;
	}

	public CompositeTicketRequestBean getApprovalDecisionBean() {
		return approvalDecisionBean;
	}

	public void setApprovalDecisionBean(CompositeTicketRequestBean approvalDecisionBean) {
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
