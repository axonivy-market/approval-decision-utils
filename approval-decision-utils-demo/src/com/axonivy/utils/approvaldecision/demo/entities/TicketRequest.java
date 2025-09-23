package com.axonivy.utils.approvaldecision.demo.entities;

import ch.ivyteam.ivy.business.data.store.context.BusinessCaseData;
import ch.ivyteam.ivy.environment.Ivy;

@BusinessCaseData
public class TicketRequest {

	private String forwardToMail;

	private Long caseId;

	private String ticketTitle;

	private String ticketNumber;

	private String ticketType;

	private String ticketRaiser;

	private String ticketDescription;

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getTicketRaiser() {
		return ticketRaiser;
	}

	public void setTicketRaiser(String ticketRaiser) {
		this.ticketRaiser = ticketRaiser;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	public String getForwardToMail() {
		return forwardToMail;
	}

	public void setForwardToMail(String forwardToMail) {
		this.forwardToMail = forwardToMail;
	}

	public String getSessionUsername() {
		return Ivy.session().getSessionUserName();
	}
}
