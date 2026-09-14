package com.axonivy.utils.approvaldecision.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.enterprise.context.ApplicationScoped;

@FacesValidator(value = "approvalDecisionValidator", managed = true)
@ApplicationScoped
public class ApprovalDecisionValidator implements Validator<Object> {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		boolean isBooleanCheck = ValidationUtils.isBooleanCheck(component);

		if (ValidationUtils.isValidationRequired(context)) {
			if (isBooleanCheck) {
				ValidationUtils.validateBooleanComponent(component, value);
			} else {
				ValidationUtils.validate(component, value);
			}
		}
	}

}
