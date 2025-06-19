package com.axonivy.utils.approvaldecision.demo.managedbean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.approvaldecision.demo.entities.ApprovalHistory;
import com.axonivy.utils.approvaldecision.demo.entities.TicketRequest;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalConfirmation;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalDecision;
import com.axonivy.utils.approvaldecision.managedbean.AbstractApprovalDecisionBean;

public class TicketApprovalDecisionBean extends AbstractApprovalDecisionBean<ApprovalHistory, Long> {

	private static final long serialVersionUID = 1L;

	private static final String VALIDATOR_ID = "ticketProcessValidator";
	private String validatorId;

	public TicketApprovalDecisionBean(TicketRequest request, List<Enum<?>> decisions, List<Enum<?>> confirmations) {
		super(request.getApprovalHistories(), decisions, confirmations);
		
		this.validatorId = VALIDATOR_ID;
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

	public String getValidatorId() {
		return validatorId;
	}

	public void setValidatorId(String validatorId) {
		this.validatorId = validatorId;
	}

	@Override
	protected Class<ApprovalHistory> getApprovalHistoryType() {
		return ApprovalHistory.class;
	}

}
