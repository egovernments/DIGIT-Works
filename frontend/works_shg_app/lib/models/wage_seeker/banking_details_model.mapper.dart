// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'banking_details_model.dart';

class BankingDetailsModelMapper extends ClassMapperBase<BankingDetailsModel> {
  BankingDetailsModelMapper._();

  static BankingDetailsModelMapper? _instance;
  static BankingDetailsModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BankingDetailsModelMapper._());
      BankAccountsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'BankingDetailsModel';

  static List<BankAccounts>? _$bankAccounts(BankingDetailsModel v) =>
      v.bankAccounts;
  static const Field<BankingDetailsModel, List<BankAccounts>> _f$bankAccounts =
      Field('bankAccounts', _$bankAccounts, opt: true);

  @override
  final MappableFields<BankingDetailsModel> fields = const {
    #bankAccounts: _f$bankAccounts,
  };

  static BankingDetailsModel _instantiate(DecodingData data) {
    return BankingDetailsModel(bankAccounts: data.dec(_f$bankAccounts));
  }

  @override
  final Function instantiate = _instantiate;

  static BankingDetailsModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BankingDetailsModel>(map);
  }

  static BankingDetailsModel fromJson(String json) {
    return ensureInitialized().decodeJson<BankingDetailsModel>(json);
  }
}

mixin BankingDetailsModelMappable {
  String toJson() {
    return BankingDetailsModelMapper.ensureInitialized()
        .encodeJson<BankingDetailsModel>(this as BankingDetailsModel);
  }

  Map<String, dynamic> toMap() {
    return BankingDetailsModelMapper.ensureInitialized()
        .encodeMap<BankingDetailsModel>(this as BankingDetailsModel);
  }

  BankingDetailsModelCopyWith<BankingDetailsModel, BankingDetailsModel,
          BankingDetailsModel>
      get copyWith => _BankingDetailsModelCopyWithImpl(
          this as BankingDetailsModel, $identity, $identity);
  @override
  String toString() {
    return BankingDetailsModelMapper.ensureInitialized()
        .stringifyValue(this as BankingDetailsModel);
  }

  @override
  bool operator ==(Object other) {
    return BankingDetailsModelMapper.ensureInitialized()
        .equalsValue(this as BankingDetailsModel, other);
  }

  @override
  int get hashCode {
    return BankingDetailsModelMapper.ensureInitialized()
        .hashValue(this as BankingDetailsModel);
  }
}

extension BankingDetailsModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BankingDetailsModel, $Out> {
  BankingDetailsModelCopyWith<$R, BankingDetailsModel, $Out>
      get $asBankingDetailsModel =>
          $base.as((v, t, t2) => _BankingDetailsModelCopyWithImpl(v, t, t2));
}

abstract class BankingDetailsModelCopyWith<$R, $In extends BankingDetailsModel,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, BankAccounts,
      BankAccountsCopyWith<$R, BankAccounts, BankAccounts>>? get bankAccounts;
  $R call({List<BankAccounts>? bankAccounts});
  BankingDetailsModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _BankingDetailsModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BankingDetailsModel, $Out>
    implements BankingDetailsModelCopyWith<$R, BankingDetailsModel, $Out> {
  _BankingDetailsModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BankingDetailsModel> $mapper =
      BankingDetailsModelMapper.ensureInitialized();
  @override
  ListCopyWith<$R, BankAccounts,
          BankAccountsCopyWith<$R, BankAccounts, BankAccounts>>?
      get bankAccounts => $value.bankAccounts != null
          ? ListCopyWith($value.bankAccounts!, (v, t) => v.copyWith.$chain(t),
              (v) => call(bankAccounts: v))
          : null;
  @override
  $R call({Object? bankAccounts = $none}) => $apply(FieldCopyWithData(
      {if (bankAccounts != $none) #bankAccounts: bankAccounts}));
  @override
  BankingDetailsModel $make(CopyWithData data) => BankingDetailsModel(
      bankAccounts: data.get(#bankAccounts, or: $value.bankAccounts));

  @override
  BankingDetailsModelCopyWith<$R2, BankingDetailsModel, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _BankingDetailsModelCopyWithImpl($value, $cast, t);
}

class BankAccountsMapper extends ClassMapperBase<BankAccounts> {
  BankAccountsMapper._();

  static BankAccountsMapper? _instance;
  static BankAccountsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BankAccountsMapper._());
      BankAccountDetailsMapper.ensureInitialized();
      ContractAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'BankAccounts';

  static String? _$id(BankAccounts v) => v.id;
  static const Field<BankAccounts, String> _f$id = Field('id', _$id, opt: true);
  static String? _$tenantId(BankAccounts v) => v.tenantId;
  static const Field<BankAccounts, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static String? _$referenceId(BankAccounts v) => v.referenceId;
  static const Field<BankAccounts, String> _f$referenceId =
      Field('referenceId', _$referenceId, opt: true);
  static String? _$serviceCode(BankAccounts v) => v.serviceCode;
  static const Field<BankAccounts, String> _f$serviceCode =
      Field('serviceCode', _$serviceCode, opt: true);
  static List<BankAccountDetails>? _$bankAccountDetails(BankAccounts v) =>
      v.bankAccountDetails;
  static const Field<BankAccounts, List<BankAccountDetails>>
      _f$bankAccountDetails =
      Field('bankAccountDetails', _$bankAccountDetails, opt: true);
  static String? _$indID(BankAccounts v) => v.indID;
  static const Field<BankAccounts, String> _f$indID =
      Field('indID', _$indID, opt: true);
  static ContractAuditDetails? _$auditDetails(BankAccounts v) => v.auditDetails;
  static const Field<BankAccounts, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);

  @override
  final MappableFields<BankAccounts> fields = const {
    #id: _f$id,
    #tenantId: _f$tenantId,
    #referenceId: _f$referenceId,
    #serviceCode: _f$serviceCode,
    #bankAccountDetails: _f$bankAccountDetails,
    #indID: _f$indID,
    #auditDetails: _f$auditDetails,
  };

  static BankAccounts _instantiate(DecodingData data) {
    return BankAccounts(
        id: data.dec(_f$id),
        tenantId: data.dec(_f$tenantId),
        referenceId: data.dec(_f$referenceId),
        serviceCode: data.dec(_f$serviceCode),
        bankAccountDetails: data.dec(_f$bankAccountDetails),
        indID: data.dec(_f$indID),
        auditDetails: data.dec(_f$auditDetails));
  }

  @override
  final Function instantiate = _instantiate;

  static BankAccounts fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BankAccounts>(map);
  }

  static BankAccounts fromJson(String json) {
    return ensureInitialized().decodeJson<BankAccounts>(json);
  }
}

mixin BankAccountsMappable {
  String toJson() {
    return BankAccountsMapper.ensureInitialized()
        .encodeJson<BankAccounts>(this as BankAccounts);
  }

  Map<String, dynamic> toMap() {
    return BankAccountsMapper.ensureInitialized()
        .encodeMap<BankAccounts>(this as BankAccounts);
  }

  BankAccountsCopyWith<BankAccounts, BankAccounts, BankAccounts> get copyWith =>
      _BankAccountsCopyWithImpl(this as BankAccounts, $identity, $identity);
  @override
  String toString() {
    return BankAccountsMapper.ensureInitialized()
        .stringifyValue(this as BankAccounts);
  }

  @override
  bool operator ==(Object other) {
    return BankAccountsMapper.ensureInitialized()
        .equalsValue(this as BankAccounts, other);
  }

  @override
  int get hashCode {
    return BankAccountsMapper.ensureInitialized()
        .hashValue(this as BankAccounts);
  }
}

extension BankAccountsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BankAccounts, $Out> {
  BankAccountsCopyWith<$R, BankAccounts, $Out> get $asBankAccounts =>
      $base.as((v, t, t2) => _BankAccountsCopyWithImpl(v, t, t2));
}

abstract class BankAccountsCopyWith<$R, $In extends BankAccounts, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<
      $R,
      BankAccountDetails,
      BankAccountDetailsCopyWith<$R, BankAccountDetails,
          BankAccountDetails>>? get bankAccountDetails;
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      String? tenantId,
      String? referenceId,
      String? serviceCode,
      List<BankAccountDetails>? bankAccountDetails,
      String? indID,
      ContractAuditDetails? auditDetails});
  BankAccountsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _BankAccountsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BankAccounts, $Out>
    implements BankAccountsCopyWith<$R, BankAccounts, $Out> {
  _BankAccountsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BankAccounts> $mapper =
      BankAccountsMapper.ensureInitialized();
  @override
  ListCopyWith<
      $R,
      BankAccountDetails,
      BankAccountDetailsCopyWith<$R, BankAccountDetails,
          BankAccountDetails>>? get bankAccountDetails =>
      $value.bankAccountDetails != null
          ? ListCopyWith(
              $value.bankAccountDetails!,
              (v, t) => v.copyWith.$chain(t),
              (v) => call(bankAccountDetails: v))
          : null;
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? tenantId = $none,
          Object? referenceId = $none,
          Object? serviceCode = $none,
          Object? bankAccountDetails = $none,
          Object? indID = $none,
          Object? auditDetails = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (tenantId != $none) #tenantId: tenantId,
        if (referenceId != $none) #referenceId: referenceId,
        if (serviceCode != $none) #serviceCode: serviceCode,
        if (bankAccountDetails != $none)
          #bankAccountDetails: bankAccountDetails,
        if (indID != $none) #indID: indID,
        if (auditDetails != $none) #auditDetails: auditDetails
      }));
  @override
  BankAccounts $make(CopyWithData data) => BankAccounts(
      id: data.get(#id, or: $value.id),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      referenceId: data.get(#referenceId, or: $value.referenceId),
      serviceCode: data.get(#serviceCode, or: $value.serviceCode),
      bankAccountDetails:
          data.get(#bankAccountDetails, or: $value.bankAccountDetails),
      indID: data.get(#indID, or: $value.indID),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails));

  @override
  BankAccountsCopyWith<$R2, BankAccounts, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _BankAccountsCopyWithImpl($value, $cast, t);
}

class BankAccountDetailsMapper extends ClassMapperBase<BankAccountDetails> {
  BankAccountDetailsMapper._();

  static BankAccountDetailsMapper? _instance;
  static BankAccountDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BankAccountDetailsMapper._());
      BankBranchIdentifierMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'BankAccountDetails';

  static String? _$id(BankAccountDetails v) => v.id;
  static const Field<BankAccountDetails, String> _f$id =
      Field('id', _$id, opt: true);
  static String? _$tenantId(BankAccountDetails v) => v.tenantId;
  static const Field<BankAccountDetails, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static String? _$accountHolderName(BankAccountDetails v) =>
      v.accountHolderName;
  static const Field<BankAccountDetails, String> _f$accountHolderName =
      Field('accountHolderName', _$accountHolderName, opt: true);
  static String? _$accountNumber(BankAccountDetails v) => v.accountNumber;
  static const Field<BankAccountDetails, String> _f$accountNumber =
      Field('accountNumber', _$accountNumber, opt: true);
  static String? _$accountType(BankAccountDetails v) => v.accountType;
  static const Field<BankAccountDetails, String> _f$accountType =
      Field('accountType', _$accountType, opt: true);
  static BankBranchIdentifier? _$bankBranchIdentifier(BankAccountDetails v) =>
      v.bankBranchIdentifier;
  static const Field<BankAccountDetails, BankBranchIdentifier>
      _f$bankBranchIdentifier =
      Field('bankBranchIdentifier', _$bankBranchIdentifier, opt: true);
  static bool? _$isPrimary(BankAccountDetails v) => v.isPrimary;
  static const Field<BankAccountDetails, bool> _f$isPrimary =
      Field('isPrimary', _$isPrimary, opt: true);
  static bool? _$isActive(BankAccountDetails v) => v.isActive;
  static const Field<BankAccountDetails, bool> _f$isActive =
      Field('isActive', _$isActive, opt: true);

  @override
  final MappableFields<BankAccountDetails> fields = const {
    #id: _f$id,
    #tenantId: _f$tenantId,
    #accountHolderName: _f$accountHolderName,
    #accountNumber: _f$accountNumber,
    #accountType: _f$accountType,
    #bankBranchIdentifier: _f$bankBranchIdentifier,
    #isPrimary: _f$isPrimary,
    #isActive: _f$isActive,
  };

  static BankAccountDetails _instantiate(DecodingData data) {
    return BankAccountDetails(
        id: data.dec(_f$id),
        tenantId: data.dec(_f$tenantId),
        accountHolderName: data.dec(_f$accountHolderName),
        accountNumber: data.dec(_f$accountNumber),
        accountType: data.dec(_f$accountType),
        bankBranchIdentifier: data.dec(_f$bankBranchIdentifier),
        isPrimary: data.dec(_f$isPrimary),
        isActive: data.dec(_f$isActive));
  }

  @override
  final Function instantiate = _instantiate;

  static BankAccountDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BankAccountDetails>(map);
  }

  static BankAccountDetails fromJson(String json) {
    return ensureInitialized().decodeJson<BankAccountDetails>(json);
  }
}

mixin BankAccountDetailsMappable {
  String toJson() {
    return BankAccountDetailsMapper.ensureInitialized()
        .encodeJson<BankAccountDetails>(this as BankAccountDetails);
  }

  Map<String, dynamic> toMap() {
    return BankAccountDetailsMapper.ensureInitialized()
        .encodeMap<BankAccountDetails>(this as BankAccountDetails);
  }

  BankAccountDetailsCopyWith<BankAccountDetails, BankAccountDetails,
          BankAccountDetails>
      get copyWith => _BankAccountDetailsCopyWithImpl(
          this as BankAccountDetails, $identity, $identity);
  @override
  String toString() {
    return BankAccountDetailsMapper.ensureInitialized()
        .stringifyValue(this as BankAccountDetails);
  }

  @override
  bool operator ==(Object other) {
    return BankAccountDetailsMapper.ensureInitialized()
        .equalsValue(this as BankAccountDetails, other);
  }

  @override
  int get hashCode {
    return BankAccountDetailsMapper.ensureInitialized()
        .hashValue(this as BankAccountDetails);
  }
}

extension BankAccountDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BankAccountDetails, $Out> {
  BankAccountDetailsCopyWith<$R, BankAccountDetails, $Out>
      get $asBankAccountDetails =>
          $base.as((v, t, t2) => _BankAccountDetailsCopyWithImpl(v, t, t2));
}

abstract class BankAccountDetailsCopyWith<$R, $In extends BankAccountDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  BankBranchIdentifierCopyWith<$R, BankBranchIdentifier, BankBranchIdentifier>?
      get bankBranchIdentifier;
  $R call(
      {String? id,
      String? tenantId,
      String? accountHolderName,
      String? accountNumber,
      String? accountType,
      BankBranchIdentifier? bankBranchIdentifier,
      bool? isPrimary,
      bool? isActive});
  BankAccountDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _BankAccountDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BankAccountDetails, $Out>
    implements BankAccountDetailsCopyWith<$R, BankAccountDetails, $Out> {
  _BankAccountDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BankAccountDetails> $mapper =
      BankAccountDetailsMapper.ensureInitialized();
  @override
  BankBranchIdentifierCopyWith<$R, BankBranchIdentifier, BankBranchIdentifier>?
      get bankBranchIdentifier => $value.bankBranchIdentifier?.copyWith
          .$chain((v) => call(bankBranchIdentifier: v));
  @override
  $R call(
          {Object? id = $none,
          Object? tenantId = $none,
          Object? accountHolderName = $none,
          Object? accountNumber = $none,
          Object? accountType = $none,
          Object? bankBranchIdentifier = $none,
          Object? isPrimary = $none,
          Object? isActive = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (tenantId != $none) #tenantId: tenantId,
        if (accountHolderName != $none) #accountHolderName: accountHolderName,
        if (accountNumber != $none) #accountNumber: accountNumber,
        if (accountType != $none) #accountType: accountType,
        if (bankBranchIdentifier != $none)
          #bankBranchIdentifier: bankBranchIdentifier,
        if (isPrimary != $none) #isPrimary: isPrimary,
        if (isActive != $none) #isActive: isActive
      }));
  @override
  BankAccountDetails $make(CopyWithData data) => BankAccountDetails(
      id: data.get(#id, or: $value.id),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      accountHolderName:
          data.get(#accountHolderName, or: $value.accountHolderName),
      accountNumber: data.get(#accountNumber, or: $value.accountNumber),
      accountType: data.get(#accountType, or: $value.accountType),
      bankBranchIdentifier:
          data.get(#bankBranchIdentifier, or: $value.bankBranchIdentifier),
      isPrimary: data.get(#isPrimary, or: $value.isPrimary),
      isActive: data.get(#isActive, or: $value.isActive));

  @override
  BankAccountDetailsCopyWith<$R2, BankAccountDetails, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _BankAccountDetailsCopyWithImpl($value, $cast, t);
}

class BankBranchIdentifierMapper extends ClassMapperBase<BankBranchIdentifier> {
  BankBranchIdentifierMapper._();

  static BankBranchIdentifierMapper? _instance;
  static BankBranchIdentifierMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BankBranchIdentifierMapper._());
      BranchAdditionalDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'BankBranchIdentifier';

  static String? _$type(BankBranchIdentifier v) => v.type;
  static const Field<BankBranchIdentifier, String> _f$type =
      Field('type', _$type, opt: true);
  static String? _$id(BankBranchIdentifier v) => v.id;
  static const Field<BankBranchIdentifier, String> _f$id =
      Field('id', _$id, opt: true);
  static String? _$code(BankBranchIdentifier v) => v.code;
  static const Field<BankBranchIdentifier, String> _f$code =
      Field('code', _$code, opt: true);
  static BranchAdditionalDetails? _$additionalDetails(BankBranchIdentifier v) =>
      v.additionalDetails;
  static const Field<BankBranchIdentifier, BranchAdditionalDetails>
      _f$additionalDetails =
      Field('additionalDetails', _$additionalDetails, opt: true);

  @override
  final MappableFields<BankBranchIdentifier> fields = const {
    #type: _f$type,
    #id: _f$id,
    #code: _f$code,
    #additionalDetails: _f$additionalDetails,
  };

  static BankBranchIdentifier _instantiate(DecodingData data) {
    return BankBranchIdentifier(
        type: data.dec(_f$type),
        id: data.dec(_f$id),
        code: data.dec(_f$code),
        additionalDetails: data.dec(_f$additionalDetails));
  }

  @override
  final Function instantiate = _instantiate;

  static BankBranchIdentifier fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BankBranchIdentifier>(map);
  }

  static BankBranchIdentifier fromJson(String json) {
    return ensureInitialized().decodeJson<BankBranchIdentifier>(json);
  }
}

mixin BankBranchIdentifierMappable {
  String toJson() {
    return BankBranchIdentifierMapper.ensureInitialized()
        .encodeJson<BankBranchIdentifier>(this as BankBranchIdentifier);
  }

  Map<String, dynamic> toMap() {
    return BankBranchIdentifierMapper.ensureInitialized()
        .encodeMap<BankBranchIdentifier>(this as BankBranchIdentifier);
  }

  BankBranchIdentifierCopyWith<BankBranchIdentifier, BankBranchIdentifier,
          BankBranchIdentifier>
      get copyWith => _BankBranchIdentifierCopyWithImpl(
          this as BankBranchIdentifier, $identity, $identity);
  @override
  String toString() {
    return BankBranchIdentifierMapper.ensureInitialized()
        .stringifyValue(this as BankBranchIdentifier);
  }

  @override
  bool operator ==(Object other) {
    return BankBranchIdentifierMapper.ensureInitialized()
        .equalsValue(this as BankBranchIdentifier, other);
  }

  @override
  int get hashCode {
    return BankBranchIdentifierMapper.ensureInitialized()
        .hashValue(this as BankBranchIdentifier);
  }
}

extension BankBranchIdentifierValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BankBranchIdentifier, $Out> {
  BankBranchIdentifierCopyWith<$R, BankBranchIdentifier, $Out>
      get $asBankBranchIdentifier =>
          $base.as((v, t, t2) => _BankBranchIdentifierCopyWithImpl(v, t, t2));
}

abstract class BankBranchIdentifierCopyWith<
    $R,
    $In extends BankBranchIdentifier,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails,
      BranchAdditionalDetails>? get additionalDetails;
  $R call(
      {String? type,
      String? id,
      String? code,
      BranchAdditionalDetails? additionalDetails});
  BankBranchIdentifierCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _BankBranchIdentifierCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BankBranchIdentifier, $Out>
    implements BankBranchIdentifierCopyWith<$R, BankBranchIdentifier, $Out> {
  _BankBranchIdentifierCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BankBranchIdentifier> $mapper =
      BankBranchIdentifierMapper.ensureInitialized();
  @override
  BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails,
          BranchAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .$chain((v) => call(additionalDetails: v));
  @override
  $R call(
          {Object? type = $none,
          Object? id = $none,
          Object? code = $none,
          Object? additionalDetails = $none}) =>
      $apply(FieldCopyWithData({
        if (type != $none) #type: type,
        if (id != $none) #id: id,
        if (code != $none) #code: code,
        if (additionalDetails != $none) #additionalDetails: additionalDetails
      }));
  @override
  BankBranchIdentifier $make(CopyWithData data) => BankBranchIdentifier(
      type: data.get(#type, or: $value.type),
      id: data.get(#id, or: $value.id),
      code: data.get(#code, or: $value.code),
      additionalDetails:
          data.get(#additionalDetails, or: $value.additionalDetails));

  @override
  BankBranchIdentifierCopyWith<$R2, BankBranchIdentifier, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _BankBranchIdentifierCopyWithImpl($value, $cast, t);
}

class BranchAdditionalDetailsMapper
    extends ClassMapperBase<BranchAdditionalDetails> {
  BranchAdditionalDetailsMapper._();

  static BranchAdditionalDetailsMapper? _instance;
  static BranchAdditionalDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals
          .use(_instance = BranchAdditionalDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'BranchAdditionalDetails';

  static String? _$ifsccode(BranchAdditionalDetails v) => v.ifsccode;
  static const Field<BranchAdditionalDetails, String> _f$ifsccode =
      Field('ifsccode', _$ifsccode, opt: true);

  @override
  final MappableFields<BranchAdditionalDetails> fields = const {
    #ifsccode: _f$ifsccode,
  };

  static BranchAdditionalDetails _instantiate(DecodingData data) {
    return BranchAdditionalDetails(ifsccode: data.dec(_f$ifsccode));
  }

  @override
  final Function instantiate = _instantiate;

  static BranchAdditionalDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BranchAdditionalDetails>(map);
  }

  static BranchAdditionalDetails fromJson(String json) {
    return ensureInitialized().decodeJson<BranchAdditionalDetails>(json);
  }
}

mixin BranchAdditionalDetailsMappable {
  String toJson() {
    return BranchAdditionalDetailsMapper.ensureInitialized()
        .encodeJson<BranchAdditionalDetails>(this as BranchAdditionalDetails);
  }

  Map<String, dynamic> toMap() {
    return BranchAdditionalDetailsMapper.ensureInitialized()
        .encodeMap<BranchAdditionalDetails>(this as BranchAdditionalDetails);
  }

  BranchAdditionalDetailsCopyWith<BranchAdditionalDetails,
          BranchAdditionalDetails, BranchAdditionalDetails>
      get copyWith => _BranchAdditionalDetailsCopyWithImpl(
          this as BranchAdditionalDetails, $identity, $identity);
  @override
  String toString() {
    return BranchAdditionalDetailsMapper.ensureInitialized()
        .stringifyValue(this as BranchAdditionalDetails);
  }

  @override
  bool operator ==(Object other) {
    return BranchAdditionalDetailsMapper.ensureInitialized()
        .equalsValue(this as BranchAdditionalDetails, other);
  }

  @override
  int get hashCode {
    return BranchAdditionalDetailsMapper.ensureInitialized()
        .hashValue(this as BranchAdditionalDetails);
  }
}

extension BranchAdditionalDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BranchAdditionalDetails, $Out> {
  BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails, $Out>
      get $asBranchAdditionalDetails => $base
          .as((v, t, t2) => _BranchAdditionalDetailsCopyWithImpl(v, t, t2));
}

abstract class BranchAdditionalDetailsCopyWith<
    $R,
    $In extends BranchAdditionalDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? ifsccode});
  BranchAdditionalDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _BranchAdditionalDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BranchAdditionalDetails, $Out>
    implements
        BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails, $Out> {
  _BranchAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BranchAdditionalDetails> $mapper =
      BranchAdditionalDetailsMapper.ensureInitialized();
  @override
  $R call({Object? ifsccode = $none}) =>
      $apply(FieldCopyWithData({if (ifsccode != $none) #ifsccode: ifsccode}));
  @override
  BranchAdditionalDetails $make(CopyWithData data) => BranchAdditionalDetails(
      ifsccode: data.get(#ifsccode, or: $value.ifsccode));

  @override
  BranchAdditionalDetailsCopyWith<$R2, BranchAdditionalDetails, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _BranchAdditionalDetailsCopyWithImpl($value, $cast, t);
}
