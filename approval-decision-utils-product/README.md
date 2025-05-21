# Approval Decision Utils

Axon Ivy's Approval Decision Utils provides a standardized approach for implementing your approvals in any business process. This component:

- Gives you a standard pattern to make decisions and confirmations.
- Implements a comment function for better documentation.
- Provides a clear view of the ongoing approval history to meet regulatory, compliance, and transparency requirements.

## Demo
The Demo shows how to integrate Approval-Decision-Utils in your project. It contains a simplest HSQLDB as datasource, that can be run without additional configuration. 

### 1. Display decision option

![](./images/1-request.png)

### 2. Validate decision option

![](./images/3-request-validate.png)

### 3. Track approval history

![](./images/4-request-history.png)

### 4. Select confirmation checkbox

![](./images/5-request-confirmation.png)

## Setup

To integrate and use  Approval Decision Utils in your project, you must
- create database tables:  your business data table, table `ApprovalHistory`, and a table for relationship between `ApprovalHistory` & your business data table.
- provide entities & DAO classes
- provide the project a bean for the UI Component `ApprovalDecision`

#### Create database tables

Create 3 tables: business data table, table `ApprovalHistory`, and a join table linking `ApprovalHistory` with the business data table.

In the Demo, business data table is `TicketRequest`, the full script [here](/script/scripts.sql).

#### Provide entities & DAO classes

Create 2 entites: 
- business data entity which extends class `BaseRequest`.
- entity `ApprovalHistory` which extends class `BaseApprovalHistory`.

Approval Decision Utils already includes [Axon Ivy Persistence Utils](https://github.com/axonivy-market/persistence-utils) library to interact with the database.

#### Provide bean for the UI Component `ApprovalDecision`

Create a managed bean extends `AbstractApprovalDecisionBean`.

The enum `ApprovalDecisionOption` to obtain decision options. If you prefer to use your own enum as options for decision, override the managed bean methods `getDecisionLabel(String decisionName)`, `List<Enum<?>> getDecisions()`.

### The UI component
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

#### Attributes

- `managedBean`: It is required and must extend `com.axonivy.utils.approvaldecision.managedbean.AbstractApprovalDecisionBean` class.
- `isReadOnly`: Configures the component to be read-only. The default is `false`.
- `fieldsetToggleable`: Makes the fieldset toggleable. Default is `false`.
- `fieldsetLegend`: Legend text of the fieldset. Default is `Approval decision`.
- `fieldsetStyleClass`: Style class of the fieldset.
- `headline`: Headline text inside the component.
- `headlinePanelStyleClass`: Style class for the panel of the headline.
- `helpText`: Help text inside the component.
- `helpTextPanelStyleClass`: Style class for the panel of the help text.
- `helpTextStyleClass`: Style class for the help text.
- `validatorId`: ID of the validator, default value is `approvalDecisionValidator` (`com.axonivy.utils.approvaldecision.validation.ApprovalDecisionValidator`).
- `decisionLabel`: Label for the decision options.
- `decisionRequired`: Flag to perform a mandatory check for decision. Default is `true`.
- `decisionRendered`: Flag to render decision options. Default is `true`.
- `decisionRequiredMessage`: Error message displayed when performing a mandatory check for the decision options. The default value is the CMS `/Labels/RequiredFieldMessage`.
- `decisionPanelStyleClass`: Style class for the panel of decision options.
- `listenerOnDecisionAction`: Listener event to be triggered when a decision is selected.
- `componentToUpdateOnDecision`: Components to be updated when a decision is selected. The default value is `@this`.
- `decisionCommentLabel`: Label for the comment. Default value is the CMS `/Labels/Comment`.
- `commentRequired`: Flag to perform mandatory check for comment. Default is `true`.
- `commentRendered`: Flag to render comment. Default is `true`.
- `commentRequiredMessage`: Error message displayed when performing mandatory check for comment. Default value is the CMS `/Labels/RequiredFieldMessage`.
- `commentPanelStyleClass`: Style class for the comment panel.
- `confirmationRequired`: Flag to perform mandatory check for the confirmation options. Default is `false`.
- `confirmationRequiredMessage`: Error message displayed when performing mandatory check for the confirmation options. Default value is the CMS `/Labels/RequiredFieldMessage`.
- `confirmationPanelStyleClass`: Style class for the panel of the confirmation options.
- `confirmationLabel`: Label for the confirmation options.
- `approvalHistoryRendered`: Flag to render the approval history table. Default is `true`.
- `approvalHistoryPanelStyleClass`: Style class for the panel of the approval history table.

#### Facets

---

- `customHeadline`: Custom headline. Use this when you need a more elaborate headline than simple text.

Example:

    <ic:com.axonivy.utils.approvaldecision.ApprovalDecision id="approvalDecision"
    managedBean="#{managedBean.approvalDecisionBean}">
    	<f:facet name="customHeadline">
    	  <p>Please check this <a href="www.google.com">Email</a> before proceed</p>
    	</f:facet>
    </ic:com.axonivy.utils.approvaldecision.ApprovalDecision>

- `customHelpText`: Custom help text. Use this when you need a more elaborate help text than simple text.

Example:

    <ic:com.axonivy.utils.approvaldecision.ApprovalDecision id="approvalDecision"
    managedBean="#{managedBean.approvalDecisionBean}">
    	<f:facet name="customHelpText">
    	  <p>Please check this <a href="www.google.com">Email</a> before proceed</p>
    	</f:facet>
    </ic:com.axonivy.utils.approvaldecision.ApprovalDecision>

- `customContent`: Custom content for special requirements.

Example: The following code adds the label `Email address of relevant department` and the dropdown list to the content.

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

#### Customize Approval history table (Optional)

The Approval history table is initially sorted by approval date in descending order. To customize the sort order, start by disabling the default sort through overriding the method `isApprovalHistoryTableSortDescending()`.

    @Override public boolean isApprovalHistoryTableSortDescending() { return false; }

Next, implement the custom sort by overriding the method `getApprovalHistoryTableSortField()`. The following fields are supported to sort:

- displayApprovalDate: Approval date.
- displayUserName: Name of the creator.
- comment: Comment.

Example:

    @Override public String getApprovalHistoryTableSortField() { return "displayUserName"; }
