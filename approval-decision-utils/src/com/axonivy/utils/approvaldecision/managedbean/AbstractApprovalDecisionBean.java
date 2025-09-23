package com.axonivy.utils.approvaldecision.managedbean;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortMeta;

import com.axonivy.utils.approvaldecision.enums.ApprovalDecisionOption;
import com.axonivy.utils.approvaldecision.repository.bean.BaseApprovalHistory;
import com.axonivy.utils.approvaldecision.repository.bean.BaseRequest;
import com.axonivy.utils.approvaldecision.repository.service.BaseRequestRepository;
import com.axonivy.utils.approvaldecision.utils.DateUtils;
import com.axonivy.utils.approvaldecision.utils.SortFieldUtils;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.security.IUser;

public abstract class AbstractApprovalDecisionBean<T extends BaseApprovalHistory, R extends BaseRequest<T>> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected R baseRequest;
	protected T approvalHistory;
	private List<Enum<?>> decisions = new ArrayList<>();
	private List<Enum<?>> confirmations = new ArrayList<>();
	private List<String> selectedConfirmations = new ArrayList<>();
	private List<T> approvalHistories = new ArrayList<>();
	private SortMeta defaultSortField;
	
	@SuppressWarnings("unused")
	private AbstractApprovalDecisionBean() { }
	
	public AbstractApprovalDecisionBean(Long caseId, List<Enum<?>> decisions, List<Enum<?>> confirmations) {
		setDecisions(decisions);
		setConfirmations(confirmations);
		initApprovalHistories(caseId);
		initSelectedConfirmations();
	}
	
	protected abstract R createRequest();
    protected abstract T createApprovalHistory();
    protected abstract Class<? extends BaseRequest<?>> getRequestType();
    
	/**
	 * Get decision name from an enum. We use enum ApprovalDecisionOption by
	 * default. If you use other enum, override this method
	 *
	 * @param decisionName
	 * @return decision label
	 */
	public String getDecisionLabel(String decisionName) {
		if (StringUtils.isBlank(decisionName)) {
			return "";
		}
		return ApprovalDecisionOption.valueOf(decisionName).getCmsName();
	}

	/**
	 * Get confirmation text from an enum. If you don't implement confirmation part,
	 * return null.
	 *
	 * @param confirmationName
	 * @return confirmation text
	 */
	public String getConfirmationLabel(String confirmationName) {
		return null;
	}

	/**
	 * Handle confirmation before save. By default, we save name list of selected
	 * confirmation. Please modify it if needed.
	 *
	 */
	public void handleConfirmation() {
		approvalHistory.setSelectedConfirmations(
				String.join(",", selectedConfirmations).trim());
	}

	private void initApprovalHistories(Long caseId) {
		baseRequest = findBaseRequest(caseId);
		if (baseRequest == null) {
			baseRequest = createRequest();
			baseRequest.setCaseId(caseId);
			baseRequest.setApprovalHistories(new ArrayList<>());
		}
			
		List<T> histories = baseRequest.getApprovalHistories();
		if (CollectionUtils.isEmpty(histories)) {
			histories = new ArrayList<>();
		}

		approvalHistories = histories.stream().filter(p -> !p.getIsEditing())
				.sorted(Comparator.comparing(BaseApprovalHistory::getApprovalDate).reversed())
				.collect(Collectors.toList());
	    approvalHistory = histories.stream().filter(p -> p.getIsEditing()).findFirst().orElse(createApprovalHistory());

		approvalHistories.forEach(history -> {
			history.setDisplayUserName(history.getModifiedByUserName());
			history.setDisplayApprovalDate(DateUtils.getFormattedDateTime(history.getApprovalDate()));
			history.setSortableApprovalDate(DateUtils.getSortableFormattedDateTime(history.getApprovalDate()));
		});

		initDefaultSortField();
	}

	/** get histories by caseId
	 */
	private R findBaseRequest(Long caseId) {
		var baseRequest = createRepository().findByCaseId(caseId);
		return baseRequest;
	}

	public boolean isApprovalHistoryTableSortDescending() {
		return true;
	}

	public String getApprovalHistoryTableSortField() {
		return "sortableApprovalDate";
	}

	public void initDefaultSortField() {
		setDefaultSortField(
				SortFieldUtils.buildSortMeta(getApprovalHistoryTableSortField(),
						isApprovalHistoryTableSortDescending()));
	}

	private void initSelectedConfirmations() {
		if (StringUtils.isNotBlank(approvalHistory.getSelectedConfirmations())
				&& CollectionUtils.isNotEmpty(confirmations)) {
			for (String selectedConfirmationName : approvalHistory.getSelectedConfirmations().split(",")) {
				if (StringUtils.isNotBlank(selectedConfirmationName)) {
					selectedConfirmations.add(selectedConfirmationName);
				}
			}
		}
	}

	/**
	 * Handle decision and confirmation stuffs before save.
	 *
	 */
	public R handleForSave() {
		approvalHistory.setIsEditing(true);
		
		handleApprovalHistoryForSave();
		
		baseRequest = createRepository().save(baseRequest);
		return baseRequest;
	}

	/**
	 * Handle decision and confirmation stuffs before submit.
	 *
	 */
	public R handleForSubmit() {
		approvalHistory.setIsEditing(false);
		handleApprovalHistoryForSave();
		
		baseRequest = createRepository().save(baseRequest);
		return baseRequest;
	}

	private void handleApprovalHistoryForSave() {
		if (CollectionUtils.isNotEmpty(confirmations)) {
			handleConfirmation();
		}

		baseRequest.setApprovalHistories(new ArrayList<>());
		baseRequest.getApprovalHistories().addAll(getApprovalHistories());
		
		if (StringUtils.isNotBlank(approvalHistory.getDecision())
				|| StringUtils.isNotBlank(approvalHistory.getComment())
				|| StringUtils.isNotBlank(approvalHistory.getSelectedConfirmations())) {
			approvalHistory.setApprovalDate(LocalDateTime.now());						// set ApprovalDate
			approvalHistory.setModifiedDate(Instant.now());								// set ModifiedDate
			approvalHistory.setModifiedByUserName(Ivy.session().getSessionUserName());	// set UserName

			baseRequest.getApprovalHistories().add(approvalHistory);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	protected BaseRequestRepository<R> createRepository() {
		Class<? extends BaseRequest> requestType = getRequestType();
		return BaseRequestRepository.of((Class<R>) requestType);
    }
	
	/**
	 * Get display name of an Ivy user by username
	 *
	 * @param username
	 * @return display name of Ivy user
	 */
	public String getDisplayName(String username) {
		if (StringUtils.isBlank(username)) {
			return "";
		}
		return Optional.ofNullable(ISecurityContext.current().users().findById(username)).map(IUser::getDisplayName)
				.orElse(username);
	}

	public R getRequest() {
		return baseRequest;
	}
	
	public T getApprovalHistory() {
		return approvalHistory;
	}

	public void setApprovalHistory(T approvalHistory) {
		this.approvalHistory = approvalHistory;
	}

	public List<Enum<?>> getDecisions() {
		return decisions;
	}

	public void setDecisions(List<Enum<?>> decisions) {
		this.decisions = decisions;
	}

	public List<Enum<?>> getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(List<Enum<?>> confirmations) {
		this.confirmations = confirmations;
	}

	public List<String> getSelectedConfirmations() {
		return selectedConfirmations;
	}

	public void setSelectedConfirmations(List<String> selectedConfirmations) {
		this.selectedConfirmations = selectedConfirmations;
	}

	public List<T> getApprovalHistories() {
		return approvalHistories;
	}

	public SortMeta getDefaultSortField() {
		return defaultSortField;
	}

	public void setDefaultSortField(SortMeta defaultSortField) {
		this.defaultSortField = defaultSortField;
	}
}
