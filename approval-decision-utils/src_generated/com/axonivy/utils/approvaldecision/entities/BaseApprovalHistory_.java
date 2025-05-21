package com.axonivy.utils.approvaldecision.entities;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseApprovalHistory.class)
public abstract class BaseApprovalHistory_ extends com.axonivy.utils.persistence.beans.AuditableEntity_ {

	public static volatile SingularAttribute<BaseApprovalHistory, LocalDateTime> approvalDate;
	public static volatile SingularAttribute<BaseApprovalHistory, String> decision;
	public static volatile SingularAttribute<BaseApprovalHistory, String> displayUserName;
	public static volatile SingularAttribute<BaseApprovalHistory, Boolean> isEditing;
	public static volatile SingularAttribute<BaseApprovalHistory, String> displayApprovalDate;
	public static volatile SingularAttribute<BaseApprovalHistory, String> comment;
	public static volatile SingularAttribute<BaseApprovalHistory, String> selectedConfirmations;
	public static volatile SingularAttribute<BaseApprovalHistory, String> sortableApprovalDate;

	public static final String APPROVAL_DATE = "approvalDate";
	public static final String DECISION = "decision";
	public static final String DISPLAY_USER_NAME = "displayUserName";
	public static final String IS_EDITING = "isEditing";
	public static final String DISPLAY_APPROVAL_DATE = "displayApprovalDate";
	public static final String COMMENT = "comment";
	public static final String SELECTED_CONFIRMATIONS = "selectedConfirmations";
	public static final String SORTABLE_APPROVAL_DATE = "sortableApprovalDate";

}

