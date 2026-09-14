package com.axonivy.utils.approvaldecision.demo.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.request.IHttpResponse;

public class TicketProcessUtils {

	public static void navigateToHomePage() throws MalformedURLException {
		IHttpResponse httpResponse = (IHttpResponse) Ivy.response();
		try {
			httpResponse.sendRedirect(URLDecoder.decode(Ivy.html().applicationHomeLink().get(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
