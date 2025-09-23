package com.axonivy.utils.approvaldecision.demo.dao;

import com.axonivy.utils.approvaldecision.demo.entities.CompositeTicketRequest;
import com.axonivy.utils.approvaldecision.repository.service.BaseRepository;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.businesscase.IBusinessCase;

public class CompositeTicketRequestDAO extends BaseRepository<CompositeTicketRequest> {

	private static final CompositeTicketRequestDAO INSTANCE = new CompositeTicketRequestDAO();

	public static CompositeTicketRequestDAO getInstance() {
		return INSTANCE;
	}

	@Override
	protected Class<CompositeTicketRequest> getType() {
		return CompositeTicketRequest.class;
	}
	
	public CompositeTicketRequest findByCaseId(Long caseId) {
		IBusinessCase businessCase = Ivy.wf().findCase(caseId).getBusinessCase();
		CompositeTicketRequest request = Ivy.repo().find(businessCase, getType());
		return request;
	}

}
