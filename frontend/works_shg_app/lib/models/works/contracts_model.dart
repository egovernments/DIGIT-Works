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
  ORGAdditionalDetails? orgAdditionalDetails;

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
      this.auditDetails,
      this.orgAdditionalDetails});
}

@MappableClass()
class ORGAdditionalDetails with ORGAdditionalDetailsMappable {
  String? registeredByDept;
  String? deptRegistrationNum;

  ORGAdditionalDetails({this.deptRegistrationNum, this.registeredByDept});
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

  ContractProcessInstance(
      {this.action,
      this.tenantId,
      this.state,
      this.id,
      this.businessId,
      this.businessService,
      this.moduleName});
}

@MappableClass()
class ContractAdditionalDetails with ContractAdditionalDetailsMappable {
  String? officerInChargeId;
  String? attendanceRegisterNumber;
  String? orgName;
  String? cboOrgNumber;
  String? projectId;
  String? projectName;
  String? projectDesc;
  String? projectType;
  String? ward;
  String? cboName;
  OfficerInCharge? officerInChargeName;
  String? officerInChargeDesgn;
  String? cboCode;
  String? estimateNumber;
  String? locality;
  double? totalEstimatedAmount;
  List<EstimateDocs>? estimateDocs;
  List<Description?>? termsAndConditions;
  int? completionPeriod;
  ContractAdditionalDetails({
    this.officerInChargeId,
    this.attendanceRegisterNumber,
    this.cboOrgNumber,
    this.projectId,
    this.projectType,
    this.orgName,
    this.projectName,
    this.ward,
    this.locality,
    this.cboCode,
    this.cboName,
    this.estimateDocs,
    this.estimateNumber,
    this.totalEstimatedAmount,
    this.completionPeriod,
    this.termsAndConditions,
    this.projectDesc,
    this.officerInChargeName,
    this.officerInChargeDesgn,
  });
}

@MappableClass()
class OfficerInCharge with OfficerInChargeMappable {
  String? code;
  String? name;
  OfficerInCharge({this.code, this.name});
}

@MappableClass()
class Description with DescriptionMappable {
  String? description;
  Description({this.description});
}

@MappableClass()
class EstimateDocs with EstimateDocsMappable {
  String? documentUid;
  String? fileName;
  String? fileStoreId;
  String? fileType;
  String? tenantId;

  EstimateDocs(
      {this.tenantId,
      this.documentUid,
      this.fileStoreId,
      this.fileName,
      this.fileType});
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
  DocumentAdditionalDetails? additionalDetails;
  String? contractId;
  String? id;
  String? documentType;
  String? fileStore;
  String? documentUid;
  String? status;

  Documents(
      {this.additionalDetails,
      this.contractId,
      this.id,
      this.documentType,
      this.status,
      this.documentUid,
      this.fileStore});
}

@MappableClass()
class DocumentAdditionalDetails with DocumentAdditionalDetailsMappable {
  String? fileName;
  String? otherCategoryName;
  DocumentAdditionalDetails(this.fileName, this.otherCategoryName);
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
