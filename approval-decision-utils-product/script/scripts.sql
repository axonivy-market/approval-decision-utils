create table TicketRequest (
	id varchar(32) not null,
	version int,
	createdByUserName varchar(255),
	createdDate datetime2,
	flaggedDeletedByUserName varchar(255),
	flaggedDeletedDate datetime2,
	modifiedByUserName varchar(255),
	modifiedDate datetime2,
	caseId int,
	ticketTitle varchar(250),
	ticketNumber varchar(250),
	ticketType varchar(250),
	ticketRaiser varchar(250),
	ticketDescription varchar(1000),
	forwardToMail varchar(50),
	primary key (id)
)

create table ApprovalHistory (
	id varchar(32) not null,
	version int,
	createdByUserName varchar(255),
	createdDate datetime2,
	flaggedDeletedByUserName varchar(255),
	flaggedDeletedDate datetime2,
	modifiedByUserName varchar(255),
	modifiedDate datetime2,
	approvalDate datetime2,
	comment varchar(1000),
	decision varchar(100),
	isEditing bit,
	selectedConfirmations varchar(1000),
	primary key (id)
)
 
create table RequestApprovalHistory (
	requestId varchar(32) not null,
	approvalHistoryId varchar(32) not null,
	primary key (requestId, approvalHistoryId)
)

alter table RequestApprovalHistory 
   add constraint fk_requestApprovalHistory_request
   foreign key (requestId) 
   references TicketRequest(id)
   
alter table RequestApprovalHistory 
   add constraint fk_requestApprovalHistory_approvalHistory 
   foreign key (approvalHistoryId) 
   references ApprovalHistory(id);

