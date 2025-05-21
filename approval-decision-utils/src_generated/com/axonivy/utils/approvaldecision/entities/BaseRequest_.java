package com.axonivy.utils.approvaldecision.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseRequest.class)
public abstract class BaseRequest_ extends com.axonivy.utils.persistence.beans.AuditableEntity_ {

	public static volatile ListAttribute<BaseRequest, BaseApprovalHistory> approvalHistories;

	public static final String APPROVAL_HISTORIES = "approvalHistories";

}

