package com.axonivy.utils.decisioncomponent.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectmanycheckbox.SelectManyCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import ch.ivyteam.ivy.environment.Ivy;

public class ValidationUtils {
	
	private static final String COMPLETE = "content-form:complete-button";
	public static final String BOOLEAN_CHECK_ATTR = "isBooleanCheck";
	public static final String SKIP_VALIDATE_ATTR = "skipValidate";
	public static final String FORCED_VALIDATE_ATTR = "isForcedValidate";
	
	public static boolean isValidationRequired(FacesContext context) {
		return isButtonClicked(context, COMPLETE) || isForceValidate(context);
	}
	
	public static boolean isButtonClicked(FacesContext context, String buttonId) {
		String sourceId = context.getExternalContext().getRequestParameterValuesMap().get("javax.faces.source")[0];
		boolean isButtonClicked = false;
		if (sourceId.contentEquals(buttonId)) {
			isButtonClicked = true;
		}
		return isButtonClicked;
	}
	
	public static boolean isForceValidate(FacesContext context) {
		return Boolean.parseBoolean(
				Optional.ofNullable(context.getExternalContext().getRequestParameterMap().get(FORCED_VALIDATE_ATTR))
						.orElse(""));
	}
	
	public static boolean isBooleanCheck(UIComponent component) {
		return Boolean.parseBoolean(Optional.ofNullable(component.getAttributes().get(BOOLEAN_CHECK_ATTR))
				.map(Object::toString).orElse(""));
	}
	
	public static boolean isSkipValidate(UIComponent component) {
		return Boolean.parseBoolean(Optional.ofNullable(component.getAttributes().get(SKIP_VALIDATE_ATTR))
				.map(Object::toString).orElse(""));
	}
	
	public static void validateBooleanComponent(UIComponent component, Object value) {
		String requiredMessage = ((UIInput) component).getRequiredMessage();
		if (value == null || StringUtils.isBlank(value.toString())) {
			addGrowlErrorMessage(requiredMessage);
		}
	}
	
	public static void validate(UIComponent component, Object value) {
		String requiredMessage = ((UIInput) component).getRequiredMessage();
		if (value == null || StringUtils.isBlank(value.toString()) || validateSelectOneMenu(component, value)
				|| validateSelectManyCheckbox(component, value) || validateSelectBooleanCheckbox(component, value)
				|| value instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) value)) {
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
