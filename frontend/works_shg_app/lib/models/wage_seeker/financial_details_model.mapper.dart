// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'financial_details_model.dart';

class FinancialDetailsMapper extends ClassMapperBase<FinancialDetails> {
  FinancialDetailsMapper._();

  static FinancialDetailsMapper? _instance;
  static FinancialDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = FinancialDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'FinancialDetails';

  static String? _$accountHolderName(FinancialDetails v) => v.accountHolderName;
  static const Field<FinancialDetails, String> _f$accountHolderName =
      Field('accountHolderName', _$accountHolderName, opt: true);
  static String? _$accountNumber(FinancialDetails v) => v.accountNumber;
  static const Field<FinancialDetails, String> _f$accountNumber =
      Field('accountNumber', _$accountNumber, opt: true);
  static String? _$reAccountNumber(FinancialDetails v) => v.reAccountNumber;
  static const Field<FinancialDetails, String> _f$reAccountNumber =
      Field('reAccountNumber', _$reAccountNumber, opt: true);
  static String? _$accountType(FinancialDetails v) => v.accountType;
  static const Field<FinancialDetails, String> _f$accountType =
      Field('accountType', _$accountType, opt: true);
  static String? _$ifscCode(FinancialDetails v) => v.ifscCode;
  static const Field<FinancialDetails, String> _f$ifscCode =
      Field('ifscCode', _$ifscCode, opt: true);
  static String? _$referenceID(FinancialDetails v) => v.referenceID;
  static const Field<FinancialDetails, String> _f$referenceID =
      Field('referenceID', _$referenceID, opt: true);
  static String? _$branchName(FinancialDetails v) => v.branchName;
  static const Field<FinancialDetails, String> _f$branchName =
      Field('branchName', _$branchName, opt: true);
  static String? _$bankName(FinancialDetails v) => v.bankName;
  static const Field<FinancialDetails, String> _f$bankName =
      Field('bankName', _$bankName, opt: true);

  @override
  final MappableFields<FinancialDetails> fields = const {
    #accountHolderName: _f$accountHolderName,
    #accountNumber: _f$accountNumber,
    #reAccountNumber: _f$reAccountNumber,
    #accountType: _f$accountType,
    #ifscCode: _f$ifscCode,
    #referenceID: _f$referenceID,
    #branchName: _f$branchName,
    #bankName: _f$bankName,
  };

  static FinancialDetails _instantiate(DecodingData data) {
    return FinancialDetails(
        accountHolderName: data.dec(_f$accountHolderName),
        accountNumber: data.dec(_f$accountNumber),
        reAccountNumber: data.dec(_f$reAccountNumber),
        accountType: data.dec(_f$accountType),
        ifscCode: data.dec(_f$ifscCode),
        referenceID: data.dec(_f$referenceID),
        branchName: data.dec(_f$branchName),
        bankName: data.dec(_f$bankName));
  }

  @override
  final Function instantiate = _instantiate;

  static FinancialDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<FinancialDetails>(map);
  }

  static FinancialDetails fromJson(String json) {
    return ensureInitialized().decodeJson<FinancialDetails>(json);
  }
}

mixin FinancialDetailsMappable {
  String toJson() {
    return FinancialDetailsMapper.ensureInitialized()
        .encodeJson<FinancialDetails>(this as FinancialDetails);
  }

  Map<String, dynamic> toMap() {
    return FinancialDetailsMapper.ensureInitialized()
        .encodeMap<FinancialDetails>(this as FinancialDetails);
  }

  FinancialDetailsCopyWith<FinancialDetails, FinancialDetails, FinancialDetails>
      get copyWith => _FinancialDetailsCopyWithImpl(
          this as FinancialDetails, $identity, $identity);
  @override
  String toString() {
    return FinancialDetailsMapper.ensureInitialized()
        .stringifyValue(this as FinancialDetails);
  }

  @override
  bool operator ==(Object other) {
    return FinancialDetailsMapper.ensureInitialized()
        .equalsValue(this as FinancialDetails, other);
  }

  @override
  int get hashCode {
    return FinancialDetailsMapper.ensureInitialized()
        .hashValue(this as FinancialDetails);
  }
}

extension FinancialDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, FinancialDetails, $Out> {
  FinancialDetailsCopyWith<$R, FinancialDetails, $Out>
      get $asFinancialDetails =>
          $base.as((v, t, t2) => _FinancialDetailsCopyWithImpl(v, t, t2));
}

abstract class FinancialDetailsCopyWith<$R, $In extends FinancialDetails, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {String? accountHolderName,
      String? accountNumber,
      String? reAccountNumber,
      String? accountType,
      String? ifscCode,
      String? referenceID,
      String? branchName,
      String? bankName});
  FinancialDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _FinancialDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, FinancialDetails, $Out>
    implements FinancialDetailsCopyWith<$R, FinancialDetails, $Out> {
  _FinancialDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<FinancialDetails> $mapper =
      FinancialDetailsMapper.ensureInitialized();
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
      $apply(FieldCopyWithData({
        if (accountHolderName != $none) #accountHolderName: accountHolderName,
        if (accountNumber != $none) #accountNumber: accountNumber,
        if (reAccountNumber != $none) #reAccountNumber: reAccountNumber,
        if (accountType != $none) #accountType: accountType,
        if (ifscCode != $none) #ifscCode: ifscCode,
        if (referenceID != $none) #referenceID: referenceID,
        if (branchName != $none) #branchName: branchName,
        if (bankName != $none) #bankName: bankName
      }));
  @override
  FinancialDetails $make(CopyWithData data) => FinancialDetails(
      accountHolderName:
          data.get(#accountHolderName, or: $value.accountHolderName),
      accountNumber: data.get(#accountNumber, or: $value.accountNumber),
      reAccountNumber: data.get(#reAccountNumber, or: $value.reAccountNumber),
      accountType: data.get(#accountType, or: $value.accountType),
      ifscCode: data.get(#ifscCode, or: $value.ifscCode),
      referenceID: data.get(#referenceID, or: $value.referenceID),
      branchName: data.get(#branchName, or: $value.branchName),
      bankName: data.get(#bankName, or: $value.bankName));

  @override
  FinancialDetailsCopyWith<$R2, FinancialDetails, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _FinancialDetailsCopyWithImpl($value, $cast, t);
}
