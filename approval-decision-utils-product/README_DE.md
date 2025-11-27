# Genehmigen-Entscheidung Utils

Axon Ivy's Approval Decision Utils bietet einen standardisierten Ansatz für die
Implementierung Ihrer Genehmigungen in jedem Geschäftsprozess. Diese Komponente:

- Bietet Ihnen ein Standardmuster für Entscheidungen und Bestätigungen.
- Implementiert eine Kommentarfunktion für eine bessere Dokumentation.
- Bietet einen klaren Überblick über den laufenden Genehmigungsprozess, um
  regulatorische, Compliance- und Transparenzanforderungen zu erfüllen.

## Demo
Die Demo zeigt, wie Sie Approval-Decision-Utilities in Ihr Projekt integrieren
können. Es gibt 3 Demos: 1 für eine einfache Einrichtung, 2 für verschiedene
Anpassungen mit demselben Prozessschritt:

### 1. Entscheidungsoption anzeigen

![](./images/1-request.png)

### 2. Entscheidungsoption validieren

![](./images/3-request-validate.png)

### 3. Genehmigungshistorie verfolgen

![](./images/4-request-history.png)

### 4. Bestätigungsfeld auswählen

![](./images/5-request-confirmation.png)

## Setup

In der Demo finden Sie Beispiele für drei Konfigurationen: eine einfache
Konfiguration, eine erweiterte Konfiguration von BaseRequest mit
Genehmigungs-Historie (CompositeTicketRequest) und eine separate Konfiguration:
Entity TicketRequest ist eigenständig mit BaseRequest.

Um Approval Decision Utilities in Ihr Projekt zu integrieren und zu verwenden,
müssen Sie eine Bean für die UI-Komponente `ApprovalDecision bereitstellen.`

#### Stellen Sie Bean für die UI-Komponente „ `ApprovalDecision” bereit.`

Verwenden Sie die integrierte „ `“ „DefaultApprovalDecisionBean“ „` “ wie in der
einfachen Demo. Oder erstellen Sie eine Bean-Klasse, die „ `“
„AbstractApprovalDecisionBean“ „` “ mit den Konstruktorparametern „histories“,
„decisions“ und „confirmations“ erweitert.

Es gibt eine vordefinierte Aufzählungs `ApprovalDecisionOption` (Werte:
APPROVAL, REJECT) kann als Entscheidung der Bean verwendet werden.

Beispiel:

    public class SimpleApprovalBean extends AbstractApprovalDecisionBean<ApprovalHistory, Long> {

    	private static final long serialVersionUID = 1L;

    	public SimpleApprovalBean() {
    		super(null, List.of(ApprovalDecisionOption.values()), null);
    	}

    	@Override
    	protected Class<ApprovalHistory> getApprovalHistoryType() {
    		return ApprovalHistory.class;
    	}
    }

Wenn Sie Ihre eigene Enumeration verwenden, überschreiben Sie bitte die
Bean-Methode „ `getDecisionLabel(String decisionName)` ”, wobei „decisionName”
der Wertestring Ihrer benutzerdefinierten Enumeration ist.

In der Demo verwendet die Bean `TicketApprovalDecisionBean`
Entscheidungsoptionen aus der Enumeration `TicketProcessApprovalDecision`.

### Die UI-Komponente
     <ic:com.axonivy.utils.approvaldecision.ApprovalDecision
    	id="approvalDecision"
    	managedBean="#{managedBean.approvalDecisionBean}"
    	validatorId="#{managedBean.approvalDecisionBean.validatorId}"
    	fieldsetLegend="Request Decision"
    	fieldsetToggleable="#{true}"
    	fieldsetStyleClass="p-mt-3"
    	headline="Step 1: Pelease select a decision option"
    	headlinePanelStyleClass=""
    	headlineStyleClass="p-text-bold"
    	helpText="My help text"
    	helpTextPanelStyleClass=""
    	helpTextStyleClass=""
    	decisionRendered="#{managedBean.contentState.decisionRendered}"
    	decisionDisable="#{managedBean.contentState.decisionDisable}"
    	decisionRequired="#{managedBean.contentState.decisionRequired}"
    	listenerOnDecisionAction="#{managedBean.onChangeDecision()}"
    	componentToUpdateOnDecision="approvalDecision:dropDownListOfMails"
    	commentRendered="#{managedBean.contentState.commentRendered}"
    	commentRequired="#{managedBean.contentState.commentRequired}"
    	approvalHistoryRendered="#{managedBean.contentState.approvalHistoryRendered}">

![](./images/1-request.png)

#### Attribute

AttributBeschreibungStandardwertmanagedBeanErforderlich. Muss
AbstractApprovalDecisionBean erweitern.

| Name                                  | Beschreibung                                                                                                  | Standard                           |
| ------------------------------------- | ------------------------------------------------------------------------------------------------------------- | ---------------------------------- |
| `managedBean`                         | Eine Bean erweitert die Klasse „com.axonivy.utils.approvaldecision.managedbean.AbstractApprovalDecisionBean”. |                                    |
| `isReadOnly`                          | Konfiguriert die Komponente als schreibgeschützt.                                                             | `false`                            |
| `fieldsetToggleable`                  | Macht das Feld umschaltbar.                                                                                   | `false`                            |
| `fieldsetLegend`                      | Legendentext des Feldsatzes.                                                                                  | `Genehmigungsentscheidung`         |
| `fieldsetStyleClass`                  | Stilklasse des Feldsatzes.                                                                                    |                                    |
| `Überschrift`                         | Überschriftstext innerhalb der Komponente.                                                                    |                                    |
| `headlinePanelStyleClass`             | Stilklasse für das Feld der Überschrift.                                                                      |                                    |
| `helpText`                            | Hilfetext innerhalb der Komponente.                                                                           |                                    |
| `helpTextPanelStyleClass`             | Stilklasse für das Feld des Hilfetextes.                                                                      |                                    |
| `helpTextStyleClass`                  | Stilklasse für den Hilfetext.                                                                                 |                                    |
| `validatorId`                         | ID des Validators.                                                                                            | `approvalDecisionValidator`        |
| `Entscheidungsbezeichnung`            | Bezeichnung für die Entscheidungsoptionen.                                                                    |                                    |
| `Entscheidung erforderlich`           | Obligatorische Überprüfung für die Entscheidung.                                                              | `true`                             |
| `Entscheidung getroffen`              | Flag zum Rendern von Entscheidungsoptionen.                                                                   | `true`                             |
| `Entscheidung erforderlich`           | Fehlermeldung für obligatorische Entscheidungsprüfung.                                                        | `CMS /Labels/RequiredFieldMessage` |
| `decisionPanelStyleClass`             | Stilklasse für das Entscheidungsfeld.                                                                         |                                    |
| `listenerOnDecisionAction`            | Der Listener wird ausgelöst, wenn eine Entscheidung ausgewählt wird.                                          |                                    |
| `componentToUpdateOnDecision`         | Komponenten, die aktualisiert werden müssen, wenn eine Entscheidung getroffen wird.                           | `@this`                            |
| `Entscheidungs-Kommentar-Bezeichnung` | Bezeichnung für den Kommentar.                                                                                | `CMS /Labels/Kommentar`            |
| `Kommentar erforderlich`              | Obligatorische Überprüfung für Kommentare.                                                                    | `true`                             |
| `commentRendered`                     | Flag zum Rendern von Kommentaren.                                                                             | `true`                             |
| `commentRequiredMessage`              | Fehlermeldung für obligatorische Kommentarprüfung.                                                            | `CMS /Labels/RequiredFieldMessage` |
| `commentPanelStyleClass`              | Stilklasse für das Kommentarfeld.                                                                             |                                    |
| `Bestätigung erforderlich`            | Obligatorische Überprüfung der Bestätigungsoptionen.                                                          | `false`                            |
| `confirmationRequiredMessage`         | Fehlermeldung für obligatorische Bestätigungsprüfung.                                                         | `CMS /Labels/RequiredFieldMessage` |
| `confirmationPanelStyleClass`         | Stilklasse für das Bestätigungsfeld.                                                                          |                                    |
| `confirmationLabel`                   | Bezeichnung für die Bestätigungsoptionen.                                                                     |                                    |
| `approvalHistoryRendered`             | Flag zum Rendern der Tabelle mit dem Genehmigungsverlauf.                                                     | `true`                             |

#### Facetten

---

- `customHeadline`: Benutzerdefinierte Überschrift. Verwenden Sie diese Option,
  wenn Sie eine ausführlichere Überschrift als einfachen Text benötigen.

Beispiel:

    <ic:com.axonivy.utils.approvaldecision.ApprovalDecision id="approvalDecision"
    managedBean="#{managedBean.approvalDecisionBean}">
    	<f:facet name="customHeadline">
    	  <p>Please check this <a href="www.google.com">Email</a> before proceed</p>
    	</f:facet>
    </ic:com.axonivy.utils.approvaldecision.ApprovalDecision>

- `customHelpText`: Benutzerdefinierter Hilfetext. Verwenden Sie diesen, wenn
  Sie einen ausführlicheren Hilfetext als einfachen Text benötigen.

Beispiel:

    <ic:com.axonivy.utils.approvaldecision.ApprovalDecision id="approvalDecision"
    managedBean="#{managedBean.approvalDecisionBean}">
    	<f:facet name="customHelpText">
    	  <p>Please check this <a href="www.google.com">Email</a> before proceed</p>
    	</f:facet>
    </ic:com.axonivy.utils.approvaldecision.ApprovalDecision>

- `customContent`: Benutzerdefinierter Inhalt für spezielle Anforderungen.

Beispiel: Der folgende Code fügt die Beschriftung „ `“ (E-Mail-Adresse der
zuständigen Abteilung` ) und die Dropdown-Liste zum Inhalt hinzu.

    <ic:com.axonivy.utils.approvaldecision.ApprovalDecision id="approvalDecision"
    	managedBean="#{managedBean.approvalDecisionBean}">
    	<f:facet name="customContent">
    	  <h:panelGroup id="dropDownListOfMails">
    		  <h:panelGroup id="mail-panel"
    			layout="block"
    			styleClass="p-formgrid p-grid p-align-baseline ui-fluid"
    			rendered="#{managedBean.contentState.showDropdownOfMails}">
    			<div class="p-field p-text-left p-text-md-right p-col-12 p-md-2">
    			  <p:outputLabel for="dropdownlist-mail"
    				value="Email address of relevant department">
    				<span class="ui-outputlabel-rfi">*</span>
    			  </p:outputLabel>
    			</div>
    			<div class="p-field p-col-12 p-md-4">
    			  <p:selectOneMenu id="dropdownlist-mail"
    				value="#{managedBean.request.forwardToMail}"
    				requiredMessage="#{ivy.cms.co('/Labels/RequiredFieldMessage')}">
    				<f:selectItem itemLabel="SelectOne" itemValue="" />
    				<f:selectItems
    				  value="#{managedBean.departmentMails.entrySet()}"
    				  var="department" itemLabel="#{department.key}"
    				  itemValue="#{department.value}" />
    				<f:validator validatorId="aprovalDecisionValidator" />
    			  </p:selectOneMenu>
    			  <p:message for="dropdownlist-mail" />
    			</div>
    		  </h:panelGroup>
    		</h:panelGroup>
    	</f:facet>
    </ic:com.axonivy.utils.approvaldecision.ApprovalDecision>

![](./images/2-request-custom-content.png)

#### Anpassen der Tabelle „Genehmigungshistorie“ (optional)

Die Tabelle „Genehmigungshistorie“ ist zunächst nach Genehmigungsdatum in
absteigender Reihenfolge sortiert. Um die Sortierung anzupassen, deaktivieren
Sie zunächst die Standardsortierung, indem Sie die Methode „
`isApprovalHistoryTableSortDescending()“ überschreiben „` “.

    @Override public boolean isApprovalHistoryTableSortDescending() { return false; }

Implementieren Sie als Nächstes die benutzerdefinierte Sortierung, indem Sie die
Methode `getApprovalHistoryTableSortField()` überschreiben. Die folgenden Felder
werden für die Sortierung unterstützt:

- displayApprovalDate: Genehmigungsdatum.
- displayUserName: Name des Erstellers.
- Kommentar: Kommentar.

Beispiel:

    @Override public String getApprovalHistoryTableSortField() { return "displayUserName"; }
