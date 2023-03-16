import 'package:dart_mappable/dart_mappable.dart';

part 'contracts_model.mapper.dart';

@MappableClass()
class ContractsModel with ContractsModelMappable {
  List<Contracts>? contracts;
  ContractsModel({this.contracts});
}

@MappableClass()
class Contracts with ContractsMappable {
  String? id;
  String? contractNumber;
  String? tenantId;
  String? wfStatus;
  String? executingAuthority;
  String? contractType;
  double? totalContractedAmount;
  double? securityDeposit;
  int? agreementDate;
  int? issueDate;
  int? defectLiabilityPeriod;
  String? orgId;
  int? startDate;
  int? endDate;
  int? completionPeriod;
  String? status;
  List<LineItems>? lineItems;
  List<Documents>? documents;
  ContractAuditDetails? auditDetails;
  ContractAdditionalDetails? additionalDetails;
  ContractProcessInstance? processInstance;

  Contracts(
      {this.id,
      this.contractNumber,
      this.tenantId,
      this.status,
      this.endDate,
      this.startDate,
      this.agreementDate,
      this.completionPeriod,
      this.contractType,
      this.defectLiabilityPeriod,
      this.executingAuthority,
      this.issueDate,
      this.orgId,
      this.securityDeposit,
      this.wfStatus,
      this.totalContractedAmount,
      this.lineItems,
      this.documents,
      this.processInstance,
      this.additionalDetails,
      this.auditDetails});
}

@MappableClass()
class LineItems with LineItemsMappable {
  String? id;
  String? estimateId;
  String? estimateLineItemId;
  String? tenantId;
  double? unitRate;
  double? noOfunit;
  String? category;
  String? name;
  String? status;
  List<AmountBreakups>? amountBreakups;
  ContractAuditDetails? auditDetails;
  LineItems(
      {this.id,
      this.status,
      this.tenantId,
      this.name,
      this.auditDetails,
      this.amountBreakups,
      this.category,
      this.estimateId,
      this.estimateLineItemId,
      this.noOfunit,
      this.unitRate});
}

@MappableClass()
class ContractProcessInstance with ContractProcessInstanceMappable {
  String? id;
  String? tenantId;
  String? businessService;
  String? businessId;
  String? action;
  String? moduleName;
  ContractState? state;
}

@MappableClass()
class ContractAdditionalDetails with ContractAdditionalDetailsMappable {
  String? officerInChargeId;
  String? orgName;
  String? projectId;
  String? projectName;
  String? projectType;
  String? ward;

  ContractAdditionalDetails(
      {this.officerInChargeId,
      this.projectId,
      this.projectType,
      this.orgName,
      this.projectName,
      this.ward});
}

@MappableClass()
class ContractAuditDetails with ContractAuditDetailsMappable {
  String? createdBy;
  String? lastModifiedBy;
  int? createdTime;
  int? lastModifiedTime;

  ContractAuditDetails(
      {this.createdTime,
      this.lastModifiedTime,
      this.createdBy,
      this.lastModifiedBy});
}

@MappableClass()
class Documents with DocumentsMappable {
  String? contractId;
  String? id;
  String? documentType;
  String? fileStore;
  String? documentUid;
  String? status;

  Documents(
      {this.contractId,
      this.id,
      this.documentType,
      this.status,
      this.documentUid,
      this.fileStore});
}

@MappableClass()
class AmountBreakups with AmountBreakupsMappable {
  String? id;
  String? estimateAmountBreakupId;
  String? amount;
  String? status;
  double? additionalDetails;

  AmountBreakups(
      {this.id,
      this.status,
      this.additionalDetails,
      this.amount,
      this.estimateAmountBreakupId});
}

@MappableClass()
class ContractState with ContractStateMappable {
  ContractAuditDetails? auditDetails;
  String? uuid;
  String? tenantId;
  String? businessServiceId;
  String? applicationStatus;
  String? state;

  ContractState(
      {this.auditDetails,
      this.uuid,
      this.tenantId,
      this.state,
      this.applicationStatus,
      this.businessServiceId});
}
