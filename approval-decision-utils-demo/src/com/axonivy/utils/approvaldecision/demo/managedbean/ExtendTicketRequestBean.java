package com.axonivy.utils.approvaldecision.demo.managedbean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.approvaldecision.demo.entities.ExtendedTicketRequest;
import com.axonivy.utils.approvaldecision.demo.entities.CustomApprovalHistory;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalConfirmation;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalDecision;
import com.axonivy.utils.approvaldecision.managedbean.AbstractApprovalDecisionBean;

import ch.ivyteam.ivy.environment.Ivy;

public class ExtendTicketRequestBean
		extends AbstractApprovalDecisionBean<CustomApprovalHistory, ExtendedTicketRequest> {

	private static final long serialVersionUID = 1L;

	private static final String VALIDATOR_ID = "ticketProcessValidator";
	private String validatorId;

	public ExtendTicketRequestBean(Long caseId, List<Enum<?>> decisions, List<Enum<?>> confirmations) {
		super(caseId, decisions, confirmations);

		this.validatorId = VALIDATOR_ID;
	}

	@Override
	protected ExtendedTicketRequest createRequest() {
		return new ExtendedTicketRequest();
	}

	@Override
	protected CustomApprovalHistory createApprovalHistory() {
		return new CustomApprovalHistory();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Class<ExtendedTicketRequest> getRequestType() {
		return (Class) ExtendedTicketRequest.class;
	}

	@Override
	public String getDecisionLabel(String decisionName) {
		if (StringUtils.isBlank(decisionName)) {
			return "";
		}
		return TicketProcessApprovalDecision.valueOf(decisionName).getCmsName();
	}

	@Override
	public String getConfirmationLabel(String confirmationName) {
		if (StringUtils.isBlank(confirmationName)) {
			return "";
		}
		return TicketProcessApprovalConfirmation.valueOf(confirmationName).getCmsName();
	}

	@Override
	public ExtendedTicketRequest handleForSave() {
		setCustomApprovalHistory();
		return super.handleForSave();
	}

	@Override
	public ExtendedTicketRequest handleForSubmit() {
		setCustomApprovalHistory();
		return super.handleForSubmit();
	}

	/*
	 * set custom field for ApprovalHistory before saving
	 */
	private void setCustomApprovalHistory() {
		this.getApprovalHistory().setApproverEmail(Ivy.session().getSessionUser().getEMailAddress());
	}

	public String getValidatorId() {
		return validatorId;
	}

	public void setValidatorId(String validatorId) {
		this.validatorId = validatorId;
	}
}
