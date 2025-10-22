package com.axonivy.utils.approvaldecision.demo.managedbean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.approvaldecision.demo.entities.TicketRequest;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalConfirmation;
import com.axonivy.utils.approvaldecision.demo.enums.TicketProcessApprovalDecision;
import com.axonivy.utils.approvaldecision.managedbean.AbstractApprovalDecisionBean;
import com.axonivy.utils.approvaldecision.repository.bean.BaseApprovalHistory;
import com.axonivy.utils.approvaldecision.repository.bean.BaseRequest;

public class TicketApprovalDecisionBean
		extends AbstractApprovalDecisionBean<BaseApprovalHistory, BaseRequest<BaseApprovalHistory>> {

	private static final long serialVersionUID = 1L;

	private static final String VALIDATOR_ID = "ticketProcessValidator";
	private String validatorId;

	public TicketApprovalDecisionBean(TicketRequest request, List<Enum<?>> decisions, List<Enum<?>> confirmations) {
		super(request.getCaseId(), decisions, confirmations);

		this.validatorId = VALIDATOR_ID;
	}

	@Override
	protected BaseRequest<BaseApprovalHistory> createRequest() {
		return new BaseRequest<>();
	}

	@Override
	protected BaseApprovalHistory createApprovalHistory() {
		return new BaseApprovalHistory();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Class<BaseRequest<?>> getRequestType() {
		return (Class) BaseRequest.class;
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
}
