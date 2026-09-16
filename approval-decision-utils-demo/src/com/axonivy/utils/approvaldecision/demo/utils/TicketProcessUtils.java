package com.axonivy.utils.approvaldecision.demo.utils;

import org.primefaces.PrimeFaces;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class TicketProcessUtils {

	public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage("growl-message", new FacesMessage(severity, summary, detail));
	}

	public static void reset() {
		PrimeFaces.current().resetInputs("content-form");
	}

	public static void showInfo() {
		addMessage(FacesMessage.SEVERITY_INFO, "Save successfully", "");
	}

}
