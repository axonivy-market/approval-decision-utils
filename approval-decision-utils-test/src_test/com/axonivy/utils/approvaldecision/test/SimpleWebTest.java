package com.axonivy.utils.approvaldecision.test;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.engine.WebAppFixture;
import com.axonivy.ivy.webtest.primeui.PrimeUi;
import com.axonivy.ivy.webtest.primeui.widget.SelectOneRadio;
import com.axonivy.ivy.webtest.primeui.widget.Table;

/**
 * Test the simpleRequest
 */
@IvyWebTest
public class SimpleWebTest {

	private static final String DECISION_OPTION_APPROVE = "Approve";
	private static final String REQUEST_COMMENT = "Please review my ticket request";

	@BeforeEach
	void startProcess(WebAppFixture fixture) throws UnsupportedEncodingException {
		String username = URLEncoder.encode("user1", "UTF-8");
		String password = URLEncoder.encode("123456", "UTF-8");
		fixture.login(username, password);
		
		open(EngineUrl.createProcessUrl("/approval-decision-utils-demo/18BA886784A13BAE/simpleRequest.ivp"));
	}

	@Test
	public void checkFieldsAndButtonsExists() {
		$(By.id("form:cancel")).shouldBe(visible);
		$(By.id("form:complete-button")).shouldBe(visible);
	
		$(By.id("form:approval-decision:decision-panel")).shouldBe(visible);
		$(By.id("form:approval-decision:comment-panel")).shouldBe(visible);
	}

	@Test
	public void testButtonSubmitWorking() {
		// set select decision
		SelectOneRadio decision = PrimeUi.selectOneRadio(By.id("form:approval-decision:decision-options"));
		decision.selectItemByLabel(DECISION_OPTION_APPROVE);

		$(By.id("form:approval-decision:decision-comment")).setValue(REQUEST_COMMENT);
		$(By.id("form:complete-button")).click();
		
		//Wait for Result page
		$(By.id("form:approval-decision:headline-panel")).shouldHave(matchText("^Result.*"), Duration.ofSeconds(10));

		// assert
		$(By.id("form:approval-decision:decision-comment")).shouldNotBe(exist);
	}

	@Test
	public void testShowApprovalHistoryContent() {
		// set select decision
		SelectOneRadio decision = PrimeUi.selectOneRadio(By.id("form:approval-decision:decision-options"));
		decision.selectItemByLabel(DECISION_OPTION_APPROVE);

		$(By.id("form:approval-decision:decision-comment")).setValue(REQUEST_COMMENT);
		$(By.id("form:complete-button")).click();
		
		//Wait for Result page
		$(By.id("form:approval-decision:headline-panel")).shouldHave(matchText("^Result.*"), Duration.ofSeconds(10));

		Table table = PrimeUi.table(By.id("form:approval-decision:approval-history-table"));
		table.contains(DECISION_OPTION_APPROVE);
		table.contains(REQUEST_COMMENT);
	}
}
