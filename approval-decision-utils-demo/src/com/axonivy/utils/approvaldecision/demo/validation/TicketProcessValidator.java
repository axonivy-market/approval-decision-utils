package com.axonivy.utils.approvaldecision.demo.validation;

import jakarta.faces.validator.FacesValidator;

import com.axonivy.utils.approvaldecision.validation.ApprovalDecisionValidator;
import jakarta.enterprise.context.ApplicationScoped;

@FacesValidator(value = "ticketProcessValidator", managed = true)
@ApplicationScoped
public class TicketProcessValidator extends ApprovalDecisionValidator {
}
