import 'package:dart_mappable/dart_mappable.dart';

import '../works/contracts_model.dart';

part 'my_bills_model.mapper.dart';

@MappableClass()
class MyBillsListModel with MyBillsListModelMappable {
  List<MyBillModel>? bills;
  MyBillsListModel({this.bills});
}

@MappableClass()
class MyBillModel with MyBillModelMappable {
  String? contractNumber;
  String? musterRollNumber;
  BillModel? bill;
  MyBillModel({
    this.bill,
    this.musterRollNumber,
    this.contractNumber,
  });
}

@MappableClass()
class BillModel with BillModelMappable {
  String tenantId;
  String? id;
  int? billDate;
  int? dueDate;
  num? netPayableAmount;
  num? netPaidAmount;
  num? totalAmount;
  num? totalPaidAmount;
  String? businessService;
  String? billNumber;
  String? referenceId;
  int? fromPeriod;
  int? toPeriod;
  String? paymentStatus;
  String? status;
  String? wfStatus;
  Payer? payer;
  BillAdditionalDetails? additionalDetails;
  List<BillDetails>? billDetails;
  ContractAuditDetails? auditDetails;

  BillModel({
    this.id,
    required this.tenantId,
    this.referenceId,
    this.status,
    this.dueDate,
    this.auditDetails,
    this.paymentStatus,
    this.billDate,
    this.billDetails,
    this.businessService,
    this.fromPeriod,
    this.netPaidAmount,
    this.netPayableAmount,
    this.payer,
    this.toPeriod,
    this.billNumber,
    this.wfStatus,
    this.additionalDetails,
    this.totalAmount,
    this.totalPaidAmount,
  });
}

@MappableClass()
class Payer with PayerMappable {
  String? parentId;
  String? identifier;
  String? type;
  String? id;
  String? status;
  ContractAuditDetails? auditDetails;
  Payer(
      {this.id,
      this.status,
      this.auditDetails,
      this.type,
      this.identifier,
      this.parentId});
}

@MappableClass()
class BillDetails with BillDetailsMappable {
  String? billId;
  String? referenceId;
  String? id;
  String? paymentStatus;
  int? fromPeriod;
  int? toPeriod;
  Payee? payee;
  List<BillLineItems>? lineItems;
  List<PayableLineItems>? payableLineItems;
  ContractAuditDetails? auditDetails;
  BillDetails(
      {this.id,
      this.referenceId,
      this.billId,
      this.auditDetails,
      this.toPeriod,
      this.fromPeriod,
      this.paymentStatus,
      this.lineItems,
      this.payableLineItems,
      this.payee});
}

@MappableClass()
class BillAdditionalDetails with BillAdditionalDetailsMappable {
  String? invoiceNumber;
  int? invoiceDate;
  String? locality;
  String? orgName;
  String? projectDesc;
  String? projectName;
  String? projectId;
  String? totalBillAmount;
  String? ward;
  BillAdditionalDetails({
    this.invoiceNumber,
    this.locality,
    this.orgName,
    this.projectDesc,
    this.projectName,
    this.projectId,
    this.ward,
    this.totalBillAmount,
    this.invoiceDate,
  });
}

@MappableClass()
class Payee with PayeeMappable {
  String? id;
  String? parentId;
  String? type;
  String? identifier;
  String? status;
  ContractAuditDetails? auditDetails;
  Payee(
      {this.id,
      this.auditDetails,
      this.parentId,
      this.identifier,
      this.type,
      this.status});
}

@MappableClass()
class BillLineItems with BillLineItemsMappable {
  String? id;
  String? billDetailId;
  String tenantId;
  String? headCode;
  num? amount;
  String? type;
  num? paidAmount;
  String? status;
  bool? isLineItemPayable;
  ContractAuditDetails? auditDetails;
  BillLineItems(
      {this.id,
      this.auditDetails,
      required this.tenantId,
      this.amount,
      this.type,
      this.status,
      this.billDetailId,
      this.headCode,
      this.isLineItemPayable,
      this.paidAmount});
}

@MappableClass()
class PayableLineItems with PayableLineItemsMappable {
  String? id;
  String? billDetailId;
  String tenantId;
  String? headCode;
  num? amount;
  String? type;
  num? paidAmount;
  String? status;
  bool? isLineItemPayable;
  ContractAuditDetails? auditDetails;
  PayableLineItems(
      {this.id,
      this.auditDetails,
      required this.tenantId,
      this.amount,
      this.type,
      this.status,
      this.billDetailId,
      this.headCode,
      this.isLineItemPayable,
      this.paidAmount});
}
