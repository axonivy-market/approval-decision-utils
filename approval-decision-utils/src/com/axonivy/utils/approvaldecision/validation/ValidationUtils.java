package com.axonivy.utils.approvaldecision.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectmanycheckbox.SelectManyCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import ch.ivyteam.ivy.environment.Ivy;

public class ValidationUtils {

	private static final String COMPLETE = "content-form:complete-button";
	private static final String BOOLEAN_CHECK_ATTR = "isBooleanCheck";

	public static boolean isValidationRequired(FacesContext context) {
		return isButtonClicked(context, COMPLETE);
	}
	
	public static boolean isValidationRequired(FacesContext context, String button) {
		return isButtonClicked(context, button);
	}

	public static boolean isButtonClicked(FacesContext context, String buttonId) {
		String sourceId = context.getExternalContext().getRequestParameterValuesMap().get("javax.faces.source")[0];
		boolean isButtonClicked = false;
		if (sourceId.contentEquals(buttonId)) {
			isButtonClicked = true;
		}
		return isButtonClicked;
	}

	public static boolean isBooleanCheck(UIComponent component) {
		return Boolean.parseBoolean(Optional.ofNullable(component.getAttributes().get(BOOLEAN_CHECK_ATTR))
				.map(Object::toString).orElse(""));
	}

	public static void validateBooleanComponent(UIComponent component, Object value) {
		String requiredMessage = ((UIInput) component).getRequiredMessage();
		if (value == null || StringUtils.isBlank(value.toString())) {
			addGrowlErrorMessage(requiredMessage);
		}
	}

	public static void validate(UIComponent component, Object value) {
		// Get the required message from the UIInput component
		String requiredMessage = ((UIInput) component).getRequiredMessage();

		// Check if the value is null or an empty string or a blank string
		// Also, check if the value is a select one menu, select many checkbox,
		// select boolean checkbox, or an empty collection
		if (value == null || StringUtils.isBlank(value.toString()) || validateSelectOneMenu(component, value)
				|| validateSelectManyCheckbox(component, value) || validateSelectBooleanCheckbox(component, value)
				|| (value instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) value))) {
			// If any of the conditions are true, add a growl error message
			addGrowlErrorMessage(requiredMessage);
		}
	}

	@SuppressWarnings("rawtypes")
	private static boolean validateSelectManyCheckbox(UIComponent component, Object value) {
		return component instanceof SelectManyCheckbox && value instanceof ArrayList && ((ArrayList) value).size() == 0;
	}

	private static boolean validateSelectOneMenu(UIComponent component, Object value) {
		return component instanceof SelectOneMenu && Boolean.FALSE.equals(value);
	}

	private static boolean validateSelectBooleanCheckbox(UIComponent component, Object value) {
		return component instanceof SelectBooleanCheckbox && Boolean.FALSE.equals(value);
	}

	public static void addGrowlErrorMessage(String requiredMessage) {
		Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages("growl-message");
		if (!messages.hasNext()) {
			addGrowlErrorMessageWithoutException(requiredMessage);
		}
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, null));
	}

	public static void addGrowlErrorMessageWithoutException(String requiredMessage) {
		FacesContext.getCurrentInstance().addMessage("growl-message",
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "", Ivy.cms().co("/Labels/GrowlForRequireField")));
	}

}
