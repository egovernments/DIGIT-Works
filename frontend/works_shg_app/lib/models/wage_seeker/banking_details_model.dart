import 'package:dart_mappable/dart_mappable.dart';

part 'banking_details_model.mapper.dart';

@MappableClass()
class BankingDetailsModel with BankingDetailsModelMappable {
  List<BankAccountDetails>? bankAccounts;
  BankingDetailsModel({this.bankAccounts});
}

@MappableClass()
class BankAccountDetails with BankAccountDetailsMappable {
  String? serviceCode;
  String? tenantId;
  String? referenceId;
  String? id;
  String? indID;

  BankAccountDetails(
      {this.id, this.tenantId, this.referenceId, this.serviceCode, this.indID});
}
