// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'banking_details_model.dart';

class BankingDetailsModelMapper extends MapperBase<BankingDetailsModel> {
  static MapperContainer container = MapperContainer(
    mappers: {BankingDetailsModelMapper()},
  )..linkAll({BankAccountDetailsMapper.container});

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
  ListCopyWith<
      $R,
      BankAccountDetails,
      BankAccountDetailsCopyWith<$R, BankAccountDetails,
          BankAccountDetails>>? get bankAccounts;
  $R call({List<BankAccountDetails>? bankAccounts});
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
  ListCopyWith<
      $R,
      BankAccountDetails,
      BankAccountDetailsCopyWith<$R, BankAccountDetails,
          BankAccountDetails>>? get bankAccounts => $value.bankAccounts != null
      ? ListCopyWith(
          $value.bankAccounts!,
          (v, t) => v.copyWith.chain<$R, BankAccountDetails>($identity, t),
          (v) => call(bankAccounts: v))
      : null;
  @override
  $R call({Object? bankAccounts = $none}) => $then(
      BankingDetailsModel(bankAccounts: or(bankAccounts, $value.bankAccounts)));
}

class BankAccountDetailsMapper extends MapperBase<BankAccountDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {BankAccountDetailsMapper()},
  );

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
      referenceId: container.$getOpt(map, 'referenceId'),
      serviceCode: container.$getOpt(map, 'serviceCode'),
      indID: container.$getOpt(map, 'indID'));

  @override
  Function get encoder => encode;
  dynamic encode(BankAccountDetails v) => toMap(v);
  Map<String, dynamic> toMap(BankAccountDetails b) => {
        'id': container.$enc(b.id, 'id'),
        'tenantId': container.$enc(b.tenantId, 'tenantId'),
        'referenceId': container.$enc(b.referenceId, 'referenceId'),
        'serviceCode': container.$enc(b.serviceCode, 'serviceCode'),
        'indID': container.$enc(b.indID, 'indID')
      };

  @override
  String stringify(BankAccountDetails self) =>
      'BankAccountDetails(serviceCode: ${container.asString(self.serviceCode)}, tenantId: ${container.asString(self.tenantId)}, referenceId: ${container.asString(self.referenceId)}, id: ${container.asString(self.id)}, indID: ${container.asString(self.indID)})';
  @override
  int hash(BankAccountDetails self) =>
      container.hash(self.serviceCode) ^
      container.hash(self.tenantId) ^
      container.hash(self.referenceId) ^
      container.hash(self.id) ^
      container.hash(self.indID);
  @override
  bool equals(BankAccountDetails self, BankAccountDetails other) =>
      container.isEqual(self.serviceCode, other.serviceCode) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.referenceId, other.referenceId) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.indID, other.indID);
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
  $R call(
      {String? id,
      String? tenantId,
      String? referenceId,
      String? serviceCode,
      String? indID});
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
  $R call(
          {Object? id = $none,
          Object? tenantId = $none,
          Object? referenceId = $none,
          Object? serviceCode = $none,
          Object? indID = $none}) =>
      $then(BankAccountDetails(
          id: or(id, $value.id),
          tenantId: or(tenantId, $value.tenantId),
          referenceId: or(referenceId, $value.referenceId),
          serviceCode: or(serviceCode, $value.serviceCode),
          indID: or(indID, $value.indID)));
}
