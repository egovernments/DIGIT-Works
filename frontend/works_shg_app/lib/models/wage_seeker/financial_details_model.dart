import 'package:dart_mappable/dart_mappable.dart';

part 'financial_details_model.mapper.dart';

@MappableClass()
class FinancialDetails with FinancialDetailsMappable {
  final String? accountHolderName;
  final String? accountNumber;
  final String? reAccountNumber;
  final String? accountType;
  final String? ifscCode;
  final String? referenceID;
  final String? branchName;
  final String? bankName;

  FinancialDetails(
      {this.accountHolderName,
      this.accountNumber,
      this.reAccountNumber,
      this.accountType,
      this.ifscCode,
      this.referenceID,
      this.branchName,
      this.bankName});
}
