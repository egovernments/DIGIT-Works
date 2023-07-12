// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'financial_details_model.dart';

class FinancialDetailsMapper extends MapperBase<FinancialDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {FinancialDetailsMapper()},
  );

  @override
  FinancialDetailsMapperElement createElement(MapperContainer container) {
    return FinancialDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'FinancialDetails';

  static final fromMap = container.fromMap<FinancialDetails>;
  static final fromJson = container.fromJson<FinancialDetails>;
}

class FinancialDetailsMapperElement
    extends MapperElementBase<FinancialDetails> {
  FinancialDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  FinancialDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  FinancialDetails fromMap(Map<String, dynamic> map) => FinancialDetails(
      accountHolderName: container.$getOpt(map, 'accountHolderName'),
      accountNumber: container.$getOpt(map, 'accountNumber'),
      reAccountNumber: container.$getOpt(map, 'reAccountNumber'),
      accountType: container.$getOpt(map, 'accountType'),
      ifscCode: container.$getOpt(map, 'ifscCode'),
      referenceID: container.$getOpt(map, 'referenceID'),
      branchName: container.$getOpt(map, 'branchName'),
      bankName: container.$getOpt(map, 'bankName'));

  @override
  Function get encoder => encode;
  dynamic encode(FinancialDetails v) => toMap(v);
  Map<String, dynamic> toMap(FinancialDetails f) => {
        'accountHolderName':
            container.$enc(f.accountHolderName, 'accountHolderName'),
        'accountNumber': container.$enc(f.accountNumber, 'accountNumber'),
        'reAccountNumber': container.$enc(f.reAccountNumber, 'reAccountNumber'),
        'accountType': container.$enc(f.accountType, 'accountType'),
        'ifscCode': container.$enc(f.ifscCode, 'ifscCode'),
        'referenceID': container.$enc(f.referenceID, 'referenceID'),
        'branchName': container.$enc(f.branchName, 'branchName'),
        'bankName': container.$enc(f.bankName, 'bankName')
      };

  @override
  String stringify(FinancialDetails self) =>
      'FinancialDetails(accountHolderName: ${container.asString(self.accountHolderName)}, accountNumber: ${container.asString(self.accountNumber)}, reAccountNumber: ${container.asString(self.reAccountNumber)}, accountType: ${container.asString(self.accountType)}, ifscCode: ${container.asString(self.ifscCode)}, referenceID: ${container.asString(self.referenceID)}, branchName: ${container.asString(self.branchName)}, bankName: ${container.asString(self.bankName)})';
  @override
  int hash(FinancialDetails self) =>
      container.hash(self.accountHolderName) ^
      container.hash(self.accountNumber) ^
      container.hash(self.reAccountNumber) ^
      container.hash(self.accountType) ^
      container.hash(self.ifscCode) ^
      container.hash(self.referenceID) ^
      container.hash(self.branchName) ^
      container.hash(self.bankName);
  @override
  bool equals(FinancialDetails self, FinancialDetails other) =>
      container.isEqual(self.accountHolderName, other.accountHolderName) &&
      container.isEqual(self.accountNumber, other.accountNumber) &&
      container.isEqual(self.reAccountNumber, other.reAccountNumber) &&
      container.isEqual(self.accountType, other.accountType) &&
      container.isEqual(self.ifscCode, other.ifscCode) &&
      container.isEqual(self.referenceID, other.referenceID) &&
      container.isEqual(self.branchName, other.branchName) &&
      container.isEqual(self.bankName, other.bankName);
}

mixin FinancialDetailsMappable {
  String toJson() =>
      FinancialDetailsMapper.container.toJson(this as FinancialDetails);
  Map<String, dynamic> toMap() =>
      FinancialDetailsMapper.container.toMap(this as FinancialDetails);
  FinancialDetailsCopyWith<FinancialDetails, FinancialDetails, FinancialDetails>
      get copyWith => _FinancialDetailsCopyWithImpl(
          this as FinancialDetails, $identity, $identity);
  @override
  String toString() => FinancialDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          FinancialDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => FinancialDetailsMapper.container.hash(this);
}

extension FinancialDetailsValueCopy<$R, $Out extends FinancialDetails>
    on ObjectCopyWith<$R, FinancialDetails, $Out> {
  FinancialDetailsCopyWith<$R, FinancialDetails, $Out> get asFinancialDetails =>
      base.as((v, t, t2) => _FinancialDetailsCopyWithImpl(v, t, t2));
}

typedef FinancialDetailsCopyWithBound = FinancialDetails;

abstract class FinancialDetailsCopyWith<$R, $In extends FinancialDetails,
    $Out extends FinancialDetails> implements ObjectCopyWith<$R, $In, $Out> {
  FinancialDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends FinancialDetails>(
          Then<FinancialDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? accountHolderName,
      String? accountNumber,
      String? reAccountNumber,
      String? accountType,
      String? ifscCode,
      String? referenceID,
      String? branchName,
      String? bankName});
}

class _FinancialDetailsCopyWithImpl<$R, $Out extends FinancialDetails>
    extends CopyWithBase<$R, FinancialDetails, $Out>
    implements FinancialDetailsCopyWith<$R, FinancialDetails, $Out> {
  _FinancialDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  FinancialDetailsCopyWith<$R2, FinancialDetails, $Out2>
      chain<$R2, $Out2 extends FinancialDetails>(
              Then<FinancialDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _FinancialDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? accountHolderName = $none,
          Object? accountNumber = $none,
          Object? reAccountNumber = $none,
          Object? accountType = $none,
          Object? ifscCode = $none,
          Object? referenceID = $none,
          Object? branchName = $none,
          Object? bankName = $none}) =>
      $then(FinancialDetails(
          accountHolderName: or(accountHolderName, $value.accountHolderName),
          accountNumber: or(accountNumber, $value.accountNumber),
          reAccountNumber: or(reAccountNumber, $value.reAccountNumber),
          accountType: or(accountType, $value.accountType),
          ifscCode: or(ifscCode, $value.ifscCode),
          referenceID: or(referenceID, $value.referenceID),
          branchName: or(branchName, $value.branchName),
          bankName: or(bankName, $value.bankName)));
}
