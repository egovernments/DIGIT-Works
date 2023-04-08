import 'package:dart_mappable/dart_mappable.dart';

part 'banking_details_model.mapper.dart';

@MappableClass()
class BankingDetailsModel with BankingDetailsModelMappable {
  List<BankAccounts>? bankAccounts;
  BankingDetailsModel({this.bankAccounts});
}

@MappableClass()
class BankAccounts with BankAccountsMappable {
  String? serviceCode;
  String? tenantId;
  String? referenceId;
  String? id;
  String? indID;
  List<BankAccountDetails>? bankAccountDetails;

  BankAccounts(
      {this.id,
      this.tenantId,
      this.referenceId,
      this.serviceCode,
      this.bankAccountDetails,
      this.indID});
}

@MappableClass()
class BankAccountDetails with BankAccountDetailsMappable {
  String? accountHolderName;
  String? tenantId;
  String? accountNumber;
  String? id;
  String? accountType;
  bool? isPrimary;
  BankBranchIdentifier? bankBranchIdentifier;
  BankAccountDetails(
      {this.id,
      this.tenantId,
      this.accountHolderName,
      this.accountNumber,
      this.accountType,
      this.bankBranchIdentifier,
      this.isPrimary});
}

@MappableClass()
class BankBranchIdentifier with BankBranchIdentifierMappable {
  String? type;
  String? code;
  String? id;
  BranchAdditionalDetails? additionalDetails;
  BankBranchIdentifier({this.type, this.id, this.code, this.additionalDetails});
}

@MappableClass()
class BranchAdditionalDetails with BranchAdditionalDetailsMappable {
  String? ifsccode;
  BranchAdditionalDetails({this.ifsccode});
}
