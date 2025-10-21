package com.axonivy.utils.approvaldecision.demo.validation;

import javax.faces.validator.FacesValidator;

import com.axonivy.utils.approvaldecision.validation.ApprovalDecisionValidator;

@FacesValidator(value = "ticketProcessValidator")
public class TicketProcessValidator extends ApprovalDecisionValidator {
	
}
