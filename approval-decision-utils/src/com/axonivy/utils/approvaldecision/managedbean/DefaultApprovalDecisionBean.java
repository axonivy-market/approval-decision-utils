package com.axonivy.utils.approvaldecision.managedbean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.approvaldecision.enums.ApprovalDecisionOption;
import com.axonivy.utils.approvaldecision.repository.bean.BaseApprovalHistory;
import com.axonivy.utils.approvaldecision.repository.bean.BaseRequest;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * <p>
 * DefaultApprovalDecisionBean a built-in managed bean for ApprovalDecision UI
 * component.
 * </p>
 * <p>
 * This bean extends {@link AbstractApprovalDecisionBean}, attach to current Ivy
 * case, Approval options: Approve & Reject, empty confirmation.
 * </p>
 */
public class DefaultApprovalDecisionBean
		extends AbstractApprovalDecisionBean<BaseApprovalHistory, BaseRequest<BaseApprovalHistory>> {

	private static final long serialVersionUID = 1L;

	private static final String VALIDATOR_ID = "approvalDecisionValidator";
	private String validatorId;

	public DefaultApprovalDecisionBean() {
		super(Ivy.wfCase().getId(), List.of(ApprovalDecisionOption.APPROVE, ApprovalDecisionOption.REJECT), List.of());
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
		return ApprovalDecisionOption.valueOf(decisionName).getCmsName();
	}

	@Override
	public String getConfirmationLabel(String confirmationName) {
		if (StringUtils.isBlank(confirmationName)) {
			return "";
		}
		return confirmationName;
	}

	public String getValidatorId() {
		return validatorId;
	}

	public void onChangeDecision() {

	}
}
