// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'banking_details_model.dart';

class BankingDetailsModelMapper extends MapperBase<BankingDetailsModel> {
  static MapperContainer container = MapperContainer(
    mappers: {BankingDetailsModelMapper()},
  )..linkAll({BankAccountsMapper.container});

  @override
  BankingDetailsModelMapperElement createElement(MapperContainer container) {
    return BankingDetailsModelMapperElement._(this, container);
  }

  @override
  String get id => 'BankingDetailsModel';

  static final fromMap = container.fromMap<BankingDetailsModel>;
  static final fromJson = container.fromJson<BankingDetailsModel>;
}

class BankingDetailsModelMapperElement
    extends MapperElementBase<BankingDetailsModel> {
  BankingDetailsModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BankingDetailsModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BankingDetailsModel fromMap(Map<String, dynamic> map) =>
      BankingDetailsModel(bankAccounts: container.$getOpt(map, 'bankAccounts'));

  @override
  Function get encoder => encode;
  dynamic encode(BankingDetailsModel v) => toMap(v);
  Map<String, dynamic> toMap(BankingDetailsModel b) =>
      {'bankAccounts': container.$enc(b.bankAccounts, 'bankAccounts')};

  @override
  String stringify(BankingDetailsModel self) =>
      'BankingDetailsModel(bankAccounts: ${container.asString(self.bankAccounts)})';
  @override
  int hash(BankingDetailsModel self) => container.hash(self.bankAccounts);
  @override
  bool equals(BankingDetailsModel self, BankingDetailsModel other) =>
      container.isEqual(self.bankAccounts, other.bankAccounts);
}

mixin BankingDetailsModelMappable {
  String toJson() =>
      BankingDetailsModelMapper.container.toJson(this as BankingDetailsModel);
  Map<String, dynamic> toMap() =>
      BankingDetailsModelMapper.container.toMap(this as BankingDetailsModel);
  BankingDetailsModelCopyWith<BankingDetailsModel, BankingDetailsModel,
          BankingDetailsModel>
      get copyWith => _BankingDetailsModelCopyWithImpl(
          this as BankingDetailsModel, $identity, $identity);
  @override
  String toString() => BankingDetailsModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BankingDetailsModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => BankingDetailsModelMapper.container.hash(this);
}

extension BankingDetailsModelValueCopy<$R, $Out extends BankingDetailsModel>
    on ObjectCopyWith<$R, BankingDetailsModel, $Out> {
  BankingDetailsModelCopyWith<$R, BankingDetailsModel, $Out>
      get asBankingDetailsModel =>
          base.as((v, t, t2) => _BankingDetailsModelCopyWithImpl(v, t, t2));
}

typedef BankingDetailsModelCopyWithBound = BankingDetailsModel;

abstract class BankingDetailsModelCopyWith<$R, $In extends BankingDetailsModel,
    $Out extends BankingDetailsModel> implements ObjectCopyWith<$R, $In, $Out> {
  BankingDetailsModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BankingDetailsModel>(
          Then<BankingDetailsModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, BankAccounts,
      BankAccountsCopyWith<$R, BankAccounts, BankAccounts>>? get bankAccounts;
  $R call({List<BankAccounts>? bankAccounts});
}

class _BankingDetailsModelCopyWithImpl<$R, $Out extends BankingDetailsModel>
    extends CopyWithBase<$R, BankingDetailsModel, $Out>
    implements BankingDetailsModelCopyWith<$R, BankingDetailsModel, $Out> {
  _BankingDetailsModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  BankingDetailsModelCopyWith<$R2, BankingDetailsModel, $Out2>
      chain<$R2, $Out2 extends BankingDetailsModel>(
              Then<BankingDetailsModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _BankingDetailsModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, BankAccounts,
          BankAccountsCopyWith<$R, BankAccounts, BankAccounts>>?
      get bankAccounts => $value.bankAccounts != null
          ? ListCopyWith(
              $value.bankAccounts!,
              (v, t) => v.copyWith.chain<$R, BankAccounts>($identity, t),
              (v) => call(bankAccounts: v))
          : null;
  @override
  $R call({Object? bankAccounts = $none}) => $then(
      BankingDetailsModel(bankAccounts: or(bankAccounts, $value.bankAccounts)));
}

class BankAccountsMapper extends MapperBase<BankAccounts> {
  static MapperContainer container = MapperContainer(
    mappers: {BankAccountsMapper()},
  )..linkAll({
      BankAccountDetailsMapper.container,
      ContractAuditDetailsMapper.container
    });

  @override
  BankAccountsMapperElement createElement(MapperContainer container) {
    return BankAccountsMapperElement._(this, container);
  }

  @override
  String get id => 'BankAccounts';

  static final fromMap = container.fromMap<BankAccounts>;
  static final fromJson = container.fromJson<BankAccounts>;
}

class BankAccountsMapperElement extends MapperElementBase<BankAccounts> {
  BankAccountsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BankAccounts decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BankAccounts fromMap(Map<String, dynamic> map) => BankAccounts(
      id: container.$getOpt(map, 'id'),
      tenantId: container.$getOpt(map, 'tenantId'),
      referenceId: container.$getOpt(map, 'referenceId'),
      serviceCode: container.$getOpt(map, 'serviceCode'),
      bankAccountDetails: container.$getOpt(map, 'bankAccountDetails'),
      indID: container.$getOpt(map, 'indID'),
      auditDetails: container.$getOpt(map, 'auditDetails'));

  @override
  Function get encoder => encode;
  dynamic encode(BankAccounts v) => toMap(v);
  Map<String, dynamic> toMap(BankAccounts b) => {
        'id': container.$enc(b.id, 'id'),
        'tenantId': container.$enc(b.tenantId, 'tenantId'),
        'referenceId': container.$enc(b.referenceId, 'referenceId'),
        'serviceCode': container.$enc(b.serviceCode, 'serviceCode'),
        'bankAccountDetails':
            container.$enc(b.bankAccountDetails, 'bankAccountDetails'),
        'indID': container.$enc(b.indID, 'indID'),
        'auditDetails': container.$enc(b.auditDetails, 'auditDetails')
      };

  @override
  String stringify(BankAccounts self) =>
      'BankAccounts(serviceCode: ${container.asString(self.serviceCode)}, tenantId: ${container.asString(self.tenantId)}, referenceId: ${container.asString(self.referenceId)}, id: ${container.asString(self.id)}, indID: ${container.asString(self.indID)}, auditDetails: ${container.asString(self.auditDetails)}, bankAccountDetails: ${container.asString(self.bankAccountDetails)})';
  @override
  int hash(BankAccounts self) =>
      container.hash(self.serviceCode) ^
      container.hash(self.tenantId) ^
      container.hash(self.referenceId) ^
      container.hash(self.id) ^
      container.hash(self.indID) ^
      container.hash(self.auditDetails) ^
      container.hash(self.bankAccountDetails);
  @override
  bool equals(BankAccounts self, BankAccounts other) =>
      container.isEqual(self.serviceCode, other.serviceCode) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.referenceId, other.referenceId) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.indID, other.indID) &&
      container.isEqual(self.auditDetails, other.auditDetails) &&
      container.isEqual(self.bankAccountDetails, other.bankAccountDetails);
}

mixin BankAccountsMappable {
  String toJson() => BankAccountsMapper.container.toJson(this as BankAccounts);
  Map<String, dynamic> toMap() =>
      BankAccountsMapper.container.toMap(this as BankAccounts);
  BankAccountsCopyWith<BankAccounts, BankAccounts, BankAccounts> get copyWith =>
      _BankAccountsCopyWithImpl(this as BankAccounts, $identity, $identity);
  @override
  String toString() => BankAccountsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BankAccountsMapper.container.isEqual(this, other));
  @override
  int get hashCode => BankAccountsMapper.container.hash(this);
}

extension BankAccountsValueCopy<$R, $Out extends BankAccounts>
    on ObjectCopyWith<$R, BankAccounts, $Out> {
  BankAccountsCopyWith<$R, BankAccounts, $Out> get asBankAccounts =>
      base.as((v, t, t2) => _BankAccountsCopyWithImpl(v, t, t2));
}

typedef BankAccountsCopyWithBound = BankAccounts;

abstract class BankAccountsCopyWith<$R, $In extends BankAccounts,
    $Out extends BankAccounts> implements ObjectCopyWith<$R, $In, $Out> {
  BankAccountsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends BankAccounts>(
      Then<BankAccounts, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _BankAccountsCopyWithImpl<$R, $Out extends BankAccounts>
    extends CopyWithBase<$R, BankAccounts, $Out>
    implements BankAccountsCopyWith<$R, BankAccounts, $Out> {
  _BankAccountsCopyWithImpl(super.value, super.then, super.then2);
  @override
  BankAccountsCopyWith<$R2, BankAccounts, $Out2>
      chain<$R2, $Out2 extends BankAccounts>(
              Then<BankAccounts, $Out2> t, Then<$Out2, $R2> t2) =>
          _BankAccountsCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<
      $R,
      BankAccountDetails,
      BankAccountDetailsCopyWith<$R, BankAccountDetails,
          BankAccountDetails>>? get bankAccountDetails =>
      $value.bankAccountDetails != null
          ? ListCopyWith(
              $value.bankAccountDetails!,
              (v, t) => v.copyWith.chain<$R, BankAccountDetails>($identity, t),
              (v) => call(bankAccountDetails: v))
          : null;
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? tenantId = $none,
          Object? referenceId = $none,
          Object? serviceCode = $none,
          Object? bankAccountDetails = $none,
          Object? indID = $none,
          Object? auditDetails = $none}) =>
      $then(BankAccounts(
          id: or(id, $value.id),
          tenantId: or(tenantId, $value.tenantId),
          referenceId: or(referenceId, $value.referenceId),
          serviceCode: or(serviceCode, $value.serviceCode),
          bankAccountDetails: or(bankAccountDetails, $value.bankAccountDetails),
          indID: or(indID, $value.indID),
          auditDetails: or(auditDetails, $value.auditDetails)));
}

class BankAccountDetailsMapper extends MapperBase<BankAccountDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {BankAccountDetailsMapper()},
  )..linkAll({BankBranchIdentifierMapper.container});

  @override
  BankAccountDetailsMapperElement createElement(MapperContainer container) {
    return BankAccountDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'BankAccountDetails';

  static final fromMap = container.fromMap<BankAccountDetails>;
  static final fromJson = container.fromJson<BankAccountDetails>;
}

class BankAccountDetailsMapperElement
    extends MapperElementBase<BankAccountDetails> {
  BankAccountDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BankAccountDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BankAccountDetails fromMap(Map<String, dynamic> map) => BankAccountDetails(
      id: container.$getOpt(map, 'id'),
      tenantId: container.$getOpt(map, 'tenantId'),
      accountHolderName: container.$getOpt(map, 'accountHolderName'),
      accountNumber: container.$getOpt(map, 'accountNumber'),
      accountType: container.$getOpt(map, 'accountType'),
      bankBranchIdentifier: container.$getOpt(map, 'bankBranchIdentifier'),
      isPrimary: container.$getOpt(map, 'isPrimary'),
      isActive: container.$getOpt(map, 'isActive'));

  @override
  Function get encoder => encode;
  dynamic encode(BankAccountDetails v) => toMap(v);
  Map<String, dynamic> toMap(BankAccountDetails b) => {
        'id': container.$enc(b.id, 'id'),
        'tenantId': container.$enc(b.tenantId, 'tenantId'),
        'accountHolderName':
            container.$enc(b.accountHolderName, 'accountHolderName'),
        'accountNumber': container.$enc(b.accountNumber, 'accountNumber'),
        'accountType': container.$enc(b.accountType, 'accountType'),
        'bankBranchIdentifier':
            container.$enc(b.bankBranchIdentifier, 'bankBranchIdentifier'),
        'isPrimary': container.$enc(b.isPrimary, 'isPrimary'),
        'isActive': container.$enc(b.isActive, 'isActive')
      };

  @override
  String stringify(BankAccountDetails self) =>
      'BankAccountDetails(accountHolderName: ${container.asString(self.accountHolderName)}, tenantId: ${container.asString(self.tenantId)}, accountNumber: ${container.asString(self.accountNumber)}, id: ${container.asString(self.id)}, accountType: ${container.asString(self.accountType)}, isActive: ${container.asString(self.isActive)}, isPrimary: ${container.asString(self.isPrimary)}, bankBranchIdentifier: ${container.asString(self.bankBranchIdentifier)})';
  @override
  int hash(BankAccountDetails self) =>
      container.hash(self.accountHolderName) ^
      container.hash(self.tenantId) ^
      container.hash(self.accountNumber) ^
      container.hash(self.id) ^
      container.hash(self.accountType) ^
      container.hash(self.isActive) ^
      container.hash(self.isPrimary) ^
      container.hash(self.bankBranchIdentifier);
  @override
  bool equals(BankAccountDetails self, BankAccountDetails other) =>
      container.isEqual(self.accountHolderName, other.accountHolderName) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.accountNumber, other.accountNumber) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.accountType, other.accountType) &&
      container.isEqual(self.isActive, other.isActive) &&
      container.isEqual(self.isPrimary, other.isPrimary) &&
      container.isEqual(self.bankBranchIdentifier, other.bankBranchIdentifier);
}

mixin BankAccountDetailsMappable {
  String toJson() =>
      BankAccountDetailsMapper.container.toJson(this as BankAccountDetails);
  Map<String, dynamic> toMap() =>
      BankAccountDetailsMapper.container.toMap(this as BankAccountDetails);
  BankAccountDetailsCopyWith<BankAccountDetails, BankAccountDetails,
          BankAccountDetails>
      get copyWith => _BankAccountDetailsCopyWithImpl(
          this as BankAccountDetails, $identity, $identity);
  @override
  String toString() => BankAccountDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BankAccountDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => BankAccountDetailsMapper.container.hash(this);
}

extension BankAccountDetailsValueCopy<$R, $Out extends BankAccountDetails>
    on ObjectCopyWith<$R, BankAccountDetails, $Out> {
  BankAccountDetailsCopyWith<$R, BankAccountDetails, $Out>
      get asBankAccountDetails =>
          base.as((v, t, t2) => _BankAccountDetailsCopyWithImpl(v, t, t2));
}

typedef BankAccountDetailsCopyWithBound = BankAccountDetails;

abstract class BankAccountDetailsCopyWith<$R, $In extends BankAccountDetails,
    $Out extends BankAccountDetails> implements ObjectCopyWith<$R, $In, $Out> {
  BankAccountDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BankAccountDetails>(
          Then<BankAccountDetails, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _BankAccountDetailsCopyWithImpl<$R, $Out extends BankAccountDetails>
    extends CopyWithBase<$R, BankAccountDetails, $Out>
    implements BankAccountDetailsCopyWith<$R, BankAccountDetails, $Out> {
  _BankAccountDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  BankAccountDetailsCopyWith<$R2, BankAccountDetails, $Out2>
      chain<$R2, $Out2 extends BankAccountDetails>(
              Then<BankAccountDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _BankAccountDetailsCopyWithImpl($value, t, t2);

  @override
  BankBranchIdentifierCopyWith<$R, BankBranchIdentifier, BankBranchIdentifier>?
      get bankBranchIdentifier => $value.bankBranchIdentifier?.copyWith
          .chain($identity, (v) => call(bankBranchIdentifier: v));
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
      $then(BankAccountDetails(
          id: or(id, $value.id),
          tenantId: or(tenantId, $value.tenantId),
          accountHolderName: or(accountHolderName, $value.accountHolderName),
          accountNumber: or(accountNumber, $value.accountNumber),
          accountType: or(accountType, $value.accountType),
          bankBranchIdentifier:
              or(bankBranchIdentifier, $value.bankBranchIdentifier),
          isPrimary: or(isPrimary, $value.isPrimary),
          isActive: or(isActive, $value.isActive)));
}

class BankBranchIdentifierMapper extends MapperBase<BankBranchIdentifier> {
  static MapperContainer container = MapperContainer(
    mappers: {BankBranchIdentifierMapper()},
  )..linkAll({BranchAdditionalDetailsMapper.container});

  @override
  BankBranchIdentifierMapperElement createElement(MapperContainer container) {
    return BankBranchIdentifierMapperElement._(this, container);
  }

  @override
  String get id => 'BankBranchIdentifier';

  static final fromMap = container.fromMap<BankBranchIdentifier>;
  static final fromJson = container.fromJson<BankBranchIdentifier>;
}

class BankBranchIdentifierMapperElement
    extends MapperElementBase<BankBranchIdentifier> {
  BankBranchIdentifierMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BankBranchIdentifier decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BankBranchIdentifier fromMap(Map<String, dynamic> map) =>
      BankBranchIdentifier(
          type: container.$getOpt(map, 'type'),
          id: container.$getOpt(map, 'id'),
          code: container.$getOpt(map, 'code'),
          additionalDetails: container.$getOpt(map, 'additionalDetails'));

  @override
  Function get encoder => encode;
  dynamic encode(BankBranchIdentifier v) => toMap(v);
  Map<String, dynamic> toMap(BankBranchIdentifier b) => {
        'type': container.$enc(b.type, 'type'),
        'id': container.$enc(b.id, 'id'),
        'code': container.$enc(b.code, 'code'),
        'additionalDetails':
            container.$enc(b.additionalDetails, 'additionalDetails')
      };

  @override
  String stringify(BankBranchIdentifier self) =>
      'BankBranchIdentifier(type: ${container.asString(self.type)}, code: ${container.asString(self.code)}, id: ${container.asString(self.id)}, additionalDetails: ${container.asString(self.additionalDetails)})';
  @override
  int hash(BankBranchIdentifier self) =>
      container.hash(self.type) ^
      container.hash(self.code) ^
      container.hash(self.id) ^
      container.hash(self.additionalDetails);
  @override
  bool equals(BankBranchIdentifier self, BankBranchIdentifier other) =>
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.code, other.code) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.additionalDetails, other.additionalDetails);
}

mixin BankBranchIdentifierMappable {
  String toJson() =>
      BankBranchIdentifierMapper.container.toJson(this as BankBranchIdentifier);
  Map<String, dynamic> toMap() =>
      BankBranchIdentifierMapper.container.toMap(this as BankBranchIdentifier);
  BankBranchIdentifierCopyWith<BankBranchIdentifier, BankBranchIdentifier,
          BankBranchIdentifier>
      get copyWith => _BankBranchIdentifierCopyWithImpl(
          this as BankBranchIdentifier, $identity, $identity);
  @override
  String toString() => BankBranchIdentifierMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BankBranchIdentifierMapper.container.isEqual(this, other));
  @override
  int get hashCode => BankBranchIdentifierMapper.container.hash(this);
}

extension BankBranchIdentifierValueCopy<$R, $Out extends BankBranchIdentifier>
    on ObjectCopyWith<$R, BankBranchIdentifier, $Out> {
  BankBranchIdentifierCopyWith<$R, BankBranchIdentifier, $Out>
      get asBankBranchIdentifier =>
          base.as((v, t, t2) => _BankBranchIdentifierCopyWithImpl(v, t, t2));
}

typedef BankBranchIdentifierCopyWithBound = BankBranchIdentifier;

abstract class BankBranchIdentifierCopyWith<$R,
        $In extends BankBranchIdentifier, $Out extends BankBranchIdentifier>
    implements ObjectCopyWith<$R, $In, $Out> {
  BankBranchIdentifierCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BankBranchIdentifier>(
          Then<BankBranchIdentifier, $Out2> t, Then<$Out2, $R2> t2);
  BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails,
      BranchAdditionalDetails>? get additionalDetails;
  $R call(
      {String? type,
      String? id,
      String? code,
      BranchAdditionalDetails? additionalDetails});
}

class _BankBranchIdentifierCopyWithImpl<$R, $Out extends BankBranchIdentifier>
    extends CopyWithBase<$R, BankBranchIdentifier, $Out>
    implements BankBranchIdentifierCopyWith<$R, BankBranchIdentifier, $Out> {
  _BankBranchIdentifierCopyWithImpl(super.value, super.then, super.then2);
  @override
  BankBranchIdentifierCopyWith<$R2, BankBranchIdentifier, $Out2>
      chain<$R2, $Out2 extends BankBranchIdentifier>(
              Then<BankBranchIdentifier, $Out2> t, Then<$Out2, $R2> t2) =>
          _BankBranchIdentifierCopyWithImpl($value, t, t2);

  @override
  BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails,
          BranchAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .chain($identity, (v) => call(additionalDetails: v));
  @override
  $R call(
          {Object? type = $none,
          Object? id = $none,
          Object? code = $none,
          Object? additionalDetails = $none}) =>
      $then(BankBranchIdentifier(
          type: or(type, $value.type),
          id: or(id, $value.id),
          code: or(code, $value.code),
          additionalDetails: or(additionalDetails, $value.additionalDetails)));
}

class BranchAdditionalDetailsMapper
    extends MapperBase<BranchAdditionalDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {BranchAdditionalDetailsMapper()},
  );

  @override
  BranchAdditionalDetailsMapperElement createElement(
      MapperContainer container) {
    return BranchAdditionalDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'BranchAdditionalDetails';

  static final fromMap = container.fromMap<BranchAdditionalDetails>;
  static final fromJson = container.fromJson<BranchAdditionalDetails>;
}

class BranchAdditionalDetailsMapperElement
    extends MapperElementBase<BranchAdditionalDetails> {
  BranchAdditionalDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BranchAdditionalDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BranchAdditionalDetails fromMap(Map<String, dynamic> map) =>
      BranchAdditionalDetails(ifsccode: container.$getOpt(map, 'ifsccode'));

  @override
  Function get encoder => encode;
  dynamic encode(BranchAdditionalDetails v) => toMap(v);
  Map<String, dynamic> toMap(BranchAdditionalDetails b) =>
      {'ifsccode': container.$enc(b.ifsccode, 'ifsccode')};

  @override
  String stringify(BranchAdditionalDetails self) =>
      'BranchAdditionalDetails(ifsccode: ${container.asString(self.ifsccode)})';
  @override
  int hash(BranchAdditionalDetails self) => container.hash(self.ifsccode);
  @override
  bool equals(BranchAdditionalDetails self, BranchAdditionalDetails other) =>
      container.isEqual(self.ifsccode, other.ifsccode);
}

mixin BranchAdditionalDetailsMappable {
  String toJson() => BranchAdditionalDetailsMapper.container
      .toJson(this as BranchAdditionalDetails);
  Map<String, dynamic> toMap() => BranchAdditionalDetailsMapper.container
      .toMap(this as BranchAdditionalDetails);
  BranchAdditionalDetailsCopyWith<BranchAdditionalDetails,
          BranchAdditionalDetails, BranchAdditionalDetails>
      get copyWith => _BranchAdditionalDetailsCopyWithImpl(
          this as BranchAdditionalDetails, $identity, $identity);
  @override
  String toString() => BranchAdditionalDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BranchAdditionalDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => BranchAdditionalDetailsMapper.container.hash(this);
}

extension BranchAdditionalDetailsValueCopy<$R,
        $Out extends BranchAdditionalDetails>
    on ObjectCopyWith<$R, BranchAdditionalDetails, $Out> {
  BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails, $Out>
      get asBranchAdditionalDetails =>
          base.as((v, t, t2) => _BranchAdditionalDetailsCopyWithImpl(v, t, t2));
}

typedef BranchAdditionalDetailsCopyWithBound = BranchAdditionalDetails;

abstract class BranchAdditionalDetailsCopyWith<
        $R,
        $In extends BranchAdditionalDetails,
        $Out extends BranchAdditionalDetails>
    implements ObjectCopyWith<$R, $In, $Out> {
  BranchAdditionalDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BranchAdditionalDetails>(
          Then<BranchAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? ifsccode});
}

class _BranchAdditionalDetailsCopyWithImpl<$R,
        $Out extends BranchAdditionalDetails>
    extends CopyWithBase<$R, BranchAdditionalDetails, $Out>
    implements
        BranchAdditionalDetailsCopyWith<$R, BranchAdditionalDetails, $Out> {
  _BranchAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  BranchAdditionalDetailsCopyWith<$R2, BranchAdditionalDetails, $Out2>
      chain<$R2, $Out2 extends BranchAdditionalDetails>(
              Then<BranchAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _BranchAdditionalDetailsCopyWithImpl($value, t, t2);

  @override
  $R call({Object? ifsccode = $none}) =>
      $then(BranchAdditionalDetails(ifsccode: or(ifsccode, $value.ifsccode)));
}
