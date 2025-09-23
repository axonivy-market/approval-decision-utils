package com.axonivy.utils.approvaldecision.demo.dao;

import com.axonivy.utils.approvaldecision.demo.entities.TicketRequest;
import com.axonivy.utils.approvaldecision.repository.service.BaseRepository;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.businesscase.IBusinessCase;

public class TicketRequestDAO extends BaseRepository<TicketRequest> {

	private static final TicketRequestDAO INSTANCE = new TicketRequestDAO();

	public static TicketRequestDAO getInstance() {
		return INSTANCE;
	}

	@Override
	protected Class<TicketRequest> getType() {
		return TicketRequest.class;
	}
	
	public TicketRequest findByCaseId(Long caseId) {
		IBusinessCase businessCase = Ivy.wf().findCase(caseId).getBusinessCase();
		TicketRequest request = Ivy.repo().find(businessCase, getType());
		return request;
	}

}
