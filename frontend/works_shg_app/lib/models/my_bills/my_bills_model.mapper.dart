// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'my_bills_model.dart';

class MyBillsModelMapper extends MapperBase<MyBillsModel> {
  static MapperContainer container = MapperContainer(
    mappers: {MyBillsModelMapper()},
  )..linkAll({BillModelMapper.container});

  @override
  MyBillsModelMapperElement createElement(MapperContainer container) {
    return MyBillsModelMapperElement._(this, container);
  }

  @override
  String get id => 'MyBillsModel';

  static final fromMap = container.fromMap<MyBillsModel>;
  static final fromJson = container.fromJson<MyBillsModel>;
}

class MyBillsModelMapperElement extends MapperElementBase<MyBillsModel> {
  MyBillsModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  MyBillsModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  MyBillsModel fromMap(Map<String, dynamic> map) =>
      MyBillsModel(bills: container.$getOpt(map, 'bills'));

  @override
  Function get encoder => encode;
  dynamic encode(MyBillsModel v) => toMap(v);
  Map<String, dynamic> toMap(MyBillsModel m) =>
      {'bills': container.$enc(m.bills, 'bills')};

  @override
  String stringify(MyBillsModel self) =>
      'MyBillsModel(bills: ${container.asString(self.bills)})';
  @override
  int hash(MyBillsModel self) => container.hash(self.bills);
  @override
  bool equals(MyBillsModel self, MyBillsModel other) =>
      container.isEqual(self.bills, other.bills);
}

mixin MyBillsModelMappable {
  String toJson() => MyBillsModelMapper.container.toJson(this as MyBillsModel);
  Map<String, dynamic> toMap() =>
      MyBillsModelMapper.container.toMap(this as MyBillsModel);
  MyBillsModelCopyWith<MyBillsModel, MyBillsModel, MyBillsModel> get copyWith =>
      _MyBillsModelCopyWithImpl(this as MyBillsModel, $identity, $identity);
  @override
  String toString() => MyBillsModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          MyBillsModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => MyBillsModelMapper.container.hash(this);
}

extension MyBillsModelValueCopy<$R, $Out extends MyBillsModel>
    on ObjectCopyWith<$R, MyBillsModel, $Out> {
  MyBillsModelCopyWith<$R, MyBillsModel, $Out> get asMyBillsModel =>
      base.as((v, t, t2) => _MyBillsModelCopyWithImpl(v, t, t2));
}

typedef MyBillsModelCopyWithBound = MyBillsModel;

abstract class MyBillsModelCopyWith<$R, $In extends MyBillsModel,
    $Out extends MyBillsModel> implements ObjectCopyWith<$R, $In, $Out> {
  MyBillsModelCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends MyBillsModel>(
      Then<MyBillsModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, BillModel, BillModelCopyWith<$R, BillModel, BillModel>>?
      get bills;
  $R call({List<BillModel>? bills});
}

class _MyBillsModelCopyWithImpl<$R, $Out extends MyBillsModel>
    extends CopyWithBase<$R, MyBillsModel, $Out>
    implements MyBillsModelCopyWith<$R, MyBillsModel, $Out> {
  _MyBillsModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  MyBillsModelCopyWith<$R2, MyBillsModel, $Out2>
      chain<$R2, $Out2 extends MyBillsModel>(
              Then<MyBillsModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _MyBillsModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, BillModel, BillModelCopyWith<$R, BillModel, BillModel>>?
      get bills => $value.bills != null
          ? ListCopyWith(
              $value.bills!,
              (v, t) => v.copyWith.chain<$R, BillModel>($identity, t),
              (v) => call(bills: v))
          : null;
  @override
  $R call({Object? bills = $none}) =>
      $then(MyBillsModel(bills: or(bills, $value.bills)));
}

class BillModelMapper extends MapperBase<BillModel> {
  static MapperContainer container = MapperContainer(
    mappers: {BillModelMapper()},
  )..linkAll({
      ContractAuditDetailsMapper.container,
      BillDetailsMapper.container,
      PayerMapper.container,
    });

  @override
  BillModelMapperElement createElement(MapperContainer container) {
    return BillModelMapperElement._(this, container);
  }

  @override
  String get id => 'BillModel';

  static final fromMap = container.fromMap<BillModel>;
  static final fromJson = container.fromJson<BillModel>;
}

class BillModelMapperElement extends MapperElementBase<BillModel> {
  BillModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BillModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BillModel fromMap(Map<String, dynamic> map) => BillModel(
      id: container.$getOpt(map, 'id'),
      tenantId: container.$getOpt(map, 'tenantId'),
      referenceId: container.$getOpt(map, 'referenceId'),
      status: container.$getOpt(map, 'status'),
      dueDate: container.$getOpt(map, 'dueDate'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      paymentStatus: container.$getOpt(map, 'paymentStatus'),
      billDate: container.$getOpt(map, 'billDate'),
      billDetails: container.$getOpt(map, 'billDetails'),
      businessservice: container.$getOpt(map, 'businessservice'),
      fromPeriod: container.$getOpt(map, 'fromPeriod'),
      netPaidAmount: container.$getOpt(map, 'netPaidAmount'),
      netPayableAmount: container.$getOpt(map, 'netPayableAmount'),
      payer: container.$getOpt(map, 'payer'),
      toPeriod: container.$getOpt(map, 'toPeriod'));

  @override
  Function get encoder => encode;
  dynamic encode(BillModel v) => toMap(v);
  Map<String, dynamic> toMap(BillModel b) => {
        'id': container.$enc(b.id, 'id'),
        'tenantId': container.$enc(b.tenantId, 'tenantId'),
        'referenceId': container.$enc(b.referenceId, 'referenceId'),
        'status': container.$enc(b.status, 'status'),
        'dueDate': container.$enc(b.dueDate, 'dueDate'),
        'auditDetails': container.$enc(b.auditDetails, 'auditDetails'),
        'paymentStatus': container.$enc(b.paymentStatus, 'paymentStatus'),
        'billDate': container.$enc(b.billDate, 'billDate'),
        'billDetails': container.$enc(b.billDetails, 'billDetails'),
        'businessservice': container.$enc(b.businessservice, 'businessservice'),
        'fromPeriod': container.$enc(b.fromPeriod, 'fromPeriod'),
        'netPaidAmount': container.$enc(b.netPaidAmount, 'netPaidAmount'),
        'netPayableAmount':
            container.$enc(b.netPayableAmount, 'netPayableAmount'),
        'payer': container.$enc(b.payer, 'payer'),
        'toPeriod': container.$enc(b.toPeriod, 'toPeriod')
      };

  @override
  String stringify(BillModel self) =>
      'BillModel(tenantId: ${container.asString(self.tenantId)}, id: ${container.asString(self.id)}, billDate: ${container.asString(self.billDate)}, dueDate: ${container.asString(self.dueDate)}, netPayableAmount: ${container.asString(self.netPayableAmount)}, netPaidAmount: ${container.asString(self.netPaidAmount)}, businessservice: ${container.asString(self.businessservice)}, referenceId: ${container.asString(self.referenceId)}, fromPeriod: ${container.asString(self.fromPeriod)}, toPeriod: ${container.asString(self.toPeriod)}, paymentStatus: ${container.asString(self.paymentStatus)}, status: ${container.asString(self.status)}, payer: ${container.asString(self.payer)}, billDetails: ${container.asString(self.billDetails)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(BillModel self) =>
      container.hash(self.tenantId) ^
      container.hash(self.id) ^
      container.hash(self.billDate) ^
      container.hash(self.dueDate) ^
      container.hash(self.netPayableAmount) ^
      container.hash(self.netPaidAmount) ^
      container.hash(self.businessservice) ^
      container.hash(self.referenceId) ^
      container.hash(self.fromPeriod) ^
      container.hash(self.toPeriod) ^
      container.hash(self.paymentStatus) ^
      container.hash(self.status) ^
      container.hash(self.payer) ^
      container.hash(self.billDetails) ^
      container.hash(self.auditDetails);
  @override
  bool equals(BillModel self, BillModel other) =>
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.billDate, other.billDate) &&
      container.isEqual(self.dueDate, other.dueDate) &&
      container.isEqual(self.netPayableAmount, other.netPayableAmount) &&
      container.isEqual(self.netPaidAmount, other.netPaidAmount) &&
      container.isEqual(self.businessservice, other.businessservice) &&
      container.isEqual(self.referenceId, other.referenceId) &&
      container.isEqual(self.fromPeriod, other.fromPeriod) &&
      container.isEqual(self.toPeriod, other.toPeriod) &&
      container.isEqual(self.paymentStatus, other.paymentStatus) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.payer, other.payer) &&
      container.isEqual(self.billDetails, other.billDetails) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin BillModelMappable {
  String toJson() => BillModelMapper.container.toJson(this as BillModel);
  Map<String, dynamic> toMap() =>
      BillModelMapper.container.toMap(this as BillModel);
  BillModelCopyWith<BillModel, BillModel, BillModel> get copyWith =>
      _BillModelCopyWithImpl(this as BillModel, $identity, $identity);
  @override
  String toString() => BillModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BillModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => BillModelMapper.container.hash(this);
}

extension BillModelValueCopy<$R, $Out extends BillModel>
    on ObjectCopyWith<$R, BillModel, $Out> {
  BillModelCopyWith<$R, BillModel, $Out> get asBillModel =>
      base.as((v, t, t2) => _BillModelCopyWithImpl(v, t, t2));
}

typedef BillModelCopyWithBound = BillModel;

abstract class BillModelCopyWith<$R, $In extends BillModel,
    $Out extends BillModel> implements ObjectCopyWith<$R, $In, $Out> {
  BillModelCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends BillModel>(
      Then<BillModel, $Out2> t, Then<$Out2, $R2> t2);
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  ListCopyWith<$R, BillDetails,
      BillDetailsCopyWith<$R, BillDetails, BillDetails>>? get billDetails;
  PayerCopyWith<$R, Payer, Payer>? get payer;
  $R call(
      {String? id,
      String? tenantId,
      String? referenceId,
      String? status,
      int? dueDate,
      ContractAuditDetails? auditDetails,
      String? paymentStatus,
      int? billDate,
      List<BillDetails>? billDetails,
      String? businessservice,
      int? fromPeriod,
      int? netPaidAmount,
      int? netPayableAmount,
      Payer? payer,
      int? toPeriod});
}

class _BillModelCopyWithImpl<$R, $Out extends BillModel>
    extends CopyWithBase<$R, BillModel, $Out>
    implements BillModelCopyWith<$R, BillModel, $Out> {
  _BillModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  BillModelCopyWith<$R2, BillModel, $Out2> chain<$R2, $Out2 extends BillModel>(
          Then<BillModel, $Out2> t, Then<$Out2, $R2> t2) =>
      _BillModelCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, BillDetails,
          BillDetailsCopyWith<$R, BillDetails, BillDetails>>?
      get billDetails => $value.billDetails != null
          ? ListCopyWith(
              $value.billDetails!,
              (v, t) => v.copyWith.chain<$R, BillDetails>($identity, t),
              (v) => call(billDetails: v))
          : null;
  @override
  PayerCopyWith<$R, Payer, Payer>? get payer =>
      $value.payer?.copyWith.chain($identity, (v) => call(payer: v));
  @override
  $R call(
          {Object? id = $none,
          Object? tenantId = $none,
          Object? referenceId = $none,
          Object? status = $none,
          Object? dueDate = $none,
          Object? auditDetails = $none,
          Object? paymentStatus = $none,
          Object? billDate = $none,
          Object? billDetails = $none,
          Object? businessservice = $none,
          Object? fromPeriod = $none,
          Object? netPaidAmount = $none,
          Object? netPayableAmount = $none,
          Object? payer = $none,
          Object? toPeriod = $none}) =>
      $then(BillModel(
          id: or(id, $value.id),
          tenantId: or(tenantId, $value.tenantId),
          referenceId: or(referenceId, $value.referenceId),
          status: or(status, $value.status),
          dueDate: or(dueDate, $value.dueDate),
          auditDetails: or(auditDetails, $value.auditDetails),
          paymentStatus: or(paymentStatus, $value.paymentStatus),
          billDate: or(billDate, $value.billDate),
          billDetails: or(billDetails, $value.billDetails),
          businessservice: or(businessservice, $value.businessservice),
          fromPeriod: or(fromPeriod, $value.fromPeriod),
          netPaidAmount: or(netPaidAmount, $value.netPaidAmount),
          netPayableAmount: or(netPayableAmount, $value.netPayableAmount),
          payer: or(payer, $value.payer),
          toPeriod: or(toPeriod, $value.toPeriod)));
}

class PayerMapper extends MapperBase<Payer> {
  static MapperContainer container = MapperContainer(
    mappers: {PayerMapper()},
  )..linkAll({ContractAuditDetailsMapper.container});

  @override
  PayerMapperElement createElement(MapperContainer container) {
    return PayerMapperElement._(this, container);
  }

  @override
  String get id => 'Payer';

  static final fromMap = container.fromMap<Payer>;
  static final fromJson = container.fromJson<Payer>;
}

class PayerMapperElement extends MapperElementBase<Payer> {
  PayerMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  Payer decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  Payer fromMap(Map<String, dynamic> map) => Payer(
      id: container.$getOpt(map, 'id'),
      status: container.$getOpt(map, 'status'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      type: container.$getOpt(map, 'type'),
      identifier: container.$getOpt(map, 'identifier'),
      parentId: container.$getOpt(map, 'parentId'));

  @override
  Function get encoder => encode;
  dynamic encode(Payer v) => toMap(v);
  Map<String, dynamic> toMap(Payer p) => {
        'id': container.$enc(p.id, 'id'),
        'status': container.$enc(p.status, 'status'),
        'auditDetails': container.$enc(p.auditDetails, 'auditDetails'),
        'type': container.$enc(p.type, 'type'),
        'identifier': container.$enc(p.identifier, 'identifier'),
        'parentId': container.$enc(p.parentId, 'parentId')
      };

  @override
  String stringify(Payer self) =>
      'Payer(parentId: ${container.asString(self.parentId)}, identifier: ${container.asString(self.identifier)}, type: ${container.asString(self.type)}, id: ${container.asString(self.id)}, status: ${container.asString(self.status)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(Payer self) =>
      container.hash(self.parentId) ^
      container.hash(self.identifier) ^
      container.hash(self.type) ^
      container.hash(self.id) ^
      container.hash(self.status) ^
      container.hash(self.auditDetails);
  @override
  bool equals(Payer self, Payer other) =>
      container.isEqual(self.parentId, other.parentId) &&
      container.isEqual(self.identifier, other.identifier) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin PayerMappable {
  String toJson() => PayerMapper.container.toJson(this as Payer);
  Map<String, dynamic> toMap() => PayerMapper.container.toMap(this as Payer);
  PayerCopyWith<Payer, Payer, Payer> get copyWith =>
      _PayerCopyWithImpl(this as Payer, $identity, $identity);
  @override
  String toString() => PayerMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          PayerMapper.container.isEqual(this, other));
  @override
  int get hashCode => PayerMapper.container.hash(this);
}

extension PayerValueCopy<$R, $Out extends Payer>
    on ObjectCopyWith<$R, Payer, $Out> {
  PayerCopyWith<$R, Payer, $Out> get asPayer =>
      base.as((v, t, t2) => _PayerCopyWithImpl(v, t, t2));
}

typedef PayerCopyWithBound = Payer;

abstract class PayerCopyWith<$R, $In extends Payer, $Out extends Payer>
    implements ObjectCopyWith<$R, $In, $Out> {
  PayerCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends Payer>(
      Then<Payer, $Out2> t, Then<$Out2, $R2> t2);
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      String? status,
      ContractAuditDetails? auditDetails,
      String? type,
      String? identifier,
      String? parentId});
}

class _PayerCopyWithImpl<$R, $Out extends Payer>
    extends CopyWithBase<$R, Payer, $Out>
    implements PayerCopyWith<$R, Payer, $Out> {
  _PayerCopyWithImpl(super.value, super.then, super.then2);
  @override
  PayerCopyWith<$R2, Payer, $Out2> chain<$R2, $Out2 extends Payer>(
          Then<Payer, $Out2> t, Then<$Out2, $R2> t2) =>
      _PayerCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? status = $none,
          Object? auditDetails = $none,
          Object? type = $none,
          Object? identifier = $none,
          Object? parentId = $none}) =>
      $then(Payer(
          id: or(id, $value.id),
          status: or(status, $value.status),
          auditDetails: or(auditDetails, $value.auditDetails),
          type: or(type, $value.type),
          identifier: or(identifier, $value.identifier),
          parentId: or(parentId, $value.parentId)));
}

class BillDetailsMapper extends MapperBase<BillDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {BillDetailsMapper()},
  )..linkAll({
      ContractAuditDetailsMapper.container,
      BillLineItemsMapper.container,
      PayableLineItemsMapper.container,
      PayeeMapper.container,
    });

  @override
  BillDetailsMapperElement createElement(MapperContainer container) {
    return BillDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'BillDetails';

  static final fromMap = container.fromMap<BillDetails>;
  static final fromJson = container.fromJson<BillDetails>;
}

class BillDetailsMapperElement extends MapperElementBase<BillDetails> {
  BillDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BillDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BillDetails fromMap(Map<String, dynamic> map) => BillDetails(
      id: container.$getOpt(map, 'id'),
      referenceId: container.$getOpt(map, 'referenceId'),
      billId: container.$getOpt(map, 'billId'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      toPeriod: container.$getOpt(map, 'toPeriod'),
      fromPeriod: container.$getOpt(map, 'fromPeriod'),
      paymentStatus: container.$getOpt(map, 'paymentStatus'),
      lineItems: container.$getOpt(map, 'lineItems'),
      payableLineItems: container.$getOpt(map, 'payableLineItems'),
      payee: container.$getOpt(map, 'payee'));

  @override
  Function get encoder => encode;
  dynamic encode(BillDetails v) => toMap(v);
  Map<String, dynamic> toMap(BillDetails b) => {
        'id': container.$enc(b.id, 'id'),
        'referenceId': container.$enc(b.referenceId, 'referenceId'),
        'billId': container.$enc(b.billId, 'billId'),
        'auditDetails': container.$enc(b.auditDetails, 'auditDetails'),
        'toPeriod': container.$enc(b.toPeriod, 'toPeriod'),
        'fromPeriod': container.$enc(b.fromPeriod, 'fromPeriod'),
        'paymentStatus': container.$enc(b.paymentStatus, 'paymentStatus'),
        'lineItems': container.$enc(b.lineItems, 'lineItems'),
        'payableLineItems':
            container.$enc(b.payableLineItems, 'payableLineItems'),
        'payee': container.$enc(b.payee, 'payee')
      };

  @override
  String stringify(BillDetails self) =>
      'BillDetails(billId: ${container.asString(self.billId)}, referenceId: ${container.asString(self.referenceId)}, id: ${container.asString(self.id)}, paymentStatus: ${container.asString(self.paymentStatus)}, fromPeriod: ${container.asString(self.fromPeriod)}, toPeriod: ${container.asString(self.toPeriod)}, payee: ${container.asString(self.payee)}, lineItems: ${container.asString(self.lineItems)}, payableLineItems: ${container.asString(self.payableLineItems)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(BillDetails self) =>
      container.hash(self.billId) ^
      container.hash(self.referenceId) ^
      container.hash(self.id) ^
      container.hash(self.paymentStatus) ^
      container.hash(self.fromPeriod) ^
      container.hash(self.toPeriod) ^
      container.hash(self.payee) ^
      container.hash(self.lineItems) ^
      container.hash(self.payableLineItems) ^
      container.hash(self.auditDetails);
  @override
  bool equals(BillDetails self, BillDetails other) =>
      container.isEqual(self.billId, other.billId) &&
      container.isEqual(self.referenceId, other.referenceId) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.paymentStatus, other.paymentStatus) &&
      container.isEqual(self.fromPeriod, other.fromPeriod) &&
      container.isEqual(self.toPeriod, other.toPeriod) &&
      container.isEqual(self.payee, other.payee) &&
      container.isEqual(self.lineItems, other.lineItems) &&
      container.isEqual(self.payableLineItems, other.payableLineItems) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin BillDetailsMappable {
  String toJson() => BillDetailsMapper.container.toJson(this as BillDetails);
  Map<String, dynamic> toMap() =>
      BillDetailsMapper.container.toMap(this as BillDetails);
  BillDetailsCopyWith<BillDetails, BillDetails, BillDetails> get copyWith =>
      _BillDetailsCopyWithImpl(this as BillDetails, $identity, $identity);
  @override
  String toString() => BillDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BillDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => BillDetailsMapper.container.hash(this);
}

extension BillDetailsValueCopy<$R, $Out extends BillDetails>
    on ObjectCopyWith<$R, BillDetails, $Out> {
  BillDetailsCopyWith<$R, BillDetails, $Out> get asBillDetails =>
      base.as((v, t, t2) => _BillDetailsCopyWithImpl(v, t, t2));
}

typedef BillDetailsCopyWithBound = BillDetails;

abstract class BillDetailsCopyWith<$R, $In extends BillDetails,
    $Out extends BillDetails> implements ObjectCopyWith<$R, $In, $Out> {
  BillDetailsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends BillDetails>(
      Then<BillDetails, $Out2> t, Then<$Out2, $R2> t2);
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  ListCopyWith<$R, BillLineItems,
      BillLineItemsCopyWith<$R, BillLineItems, BillLineItems>>? get lineItems;
  ListCopyWith<$R, PayableLineItems,
          PayableLineItemsCopyWith<$R, PayableLineItems, PayableLineItems>>?
      get payableLineItems;
  PayeeCopyWith<$R, Payee, Payee>? get payee;
  $R call(
      {String? id,
      String? referenceId,
      String? billId,
      ContractAuditDetails? auditDetails,
      int? toPeriod,
      int? fromPeriod,
      String? paymentStatus,
      List<BillLineItems>? lineItems,
      List<PayableLineItems>? payableLineItems,
      Payee? payee});
}

class _BillDetailsCopyWithImpl<$R, $Out extends BillDetails>
    extends CopyWithBase<$R, BillDetails, $Out>
    implements BillDetailsCopyWith<$R, BillDetails, $Out> {
  _BillDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  BillDetailsCopyWith<$R2, BillDetails, $Out2>
      chain<$R2, $Out2 extends BillDetails>(
              Then<BillDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _BillDetailsCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, BillLineItems,
          BillLineItemsCopyWith<$R, BillLineItems, BillLineItems>>?
      get lineItems => $value.lineItems != null
          ? ListCopyWith(
              $value.lineItems!,
              (v, t) => v.copyWith.chain<$R, BillLineItems>($identity, t),
              (v) => call(lineItems: v))
          : null;
  @override
  ListCopyWith<$R, PayableLineItems,
          PayableLineItemsCopyWith<$R, PayableLineItems, PayableLineItems>>?
      get payableLineItems => $value.payableLineItems != null
          ? ListCopyWith(
              $value.payableLineItems!,
              (v, t) => v.copyWith.chain<$R, PayableLineItems>($identity, t),
              (v) => call(payableLineItems: v))
          : null;
  @override
  PayeeCopyWith<$R, Payee, Payee>? get payee =>
      $value.payee?.copyWith.chain($identity, (v) => call(payee: v));
  @override
  $R call(
          {Object? id = $none,
          Object? referenceId = $none,
          Object? billId = $none,
          Object? auditDetails = $none,
          Object? toPeriod = $none,
          Object? fromPeriod = $none,
          Object? paymentStatus = $none,
          Object? lineItems = $none,
          Object? payableLineItems = $none,
          Object? payee = $none}) =>
      $then(BillDetails(
          id: or(id, $value.id),
          referenceId: or(referenceId, $value.referenceId),
          billId: or(billId, $value.billId),
          auditDetails: or(auditDetails, $value.auditDetails),
          toPeriod: or(toPeriod, $value.toPeriod),
          fromPeriod: or(fromPeriod, $value.fromPeriod),
          paymentStatus: or(paymentStatus, $value.paymentStatus),
          lineItems: or(lineItems, $value.lineItems),
          payableLineItems: or(payableLineItems, $value.payableLineItems),
          payee: or(payee, $value.payee)));
}

class PayeeMapper extends MapperBase<Payee> {
  static MapperContainer container = MapperContainer(
    mappers: {PayeeMapper()},
  )..linkAll({ContractAuditDetailsMapper.container});

  @override
  PayeeMapperElement createElement(MapperContainer container) {
    return PayeeMapperElement._(this, container);
  }

  @override
  String get id => 'Payee';

  static final fromMap = container.fromMap<Payee>;
  static final fromJson = container.fromJson<Payee>;
}

class PayeeMapperElement extends MapperElementBase<Payee> {
  PayeeMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  Payee decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  Payee fromMap(Map<String, dynamic> map) => Payee(
      id: container.$getOpt(map, 'id'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      parentId: container.$getOpt(map, 'parentId'),
      identifier: container.$getOpt(map, 'identifier'),
      type: container.$getOpt(map, 'type'),
      status: container.$getOpt(map, 'status'));

  @override
  Function get encoder => encode;
  dynamic encode(Payee v) => toMap(v);
  Map<String, dynamic> toMap(Payee p) => {
        'id': container.$enc(p.id, 'id'),
        'auditDetails': container.$enc(p.auditDetails, 'auditDetails'),
        'parentId': container.$enc(p.parentId, 'parentId'),
        'identifier': container.$enc(p.identifier, 'identifier'),
        'type': container.$enc(p.type, 'type'),
        'status': container.$enc(p.status, 'status')
      };

  @override
  String stringify(Payee self) =>
      'Payee(id: ${container.asString(self.id)}, parentId: ${container.asString(self.parentId)}, type: ${container.asString(self.type)}, identifier: ${container.asString(self.identifier)}, status: ${container.asString(self.status)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(Payee self) =>
      container.hash(self.id) ^
      container.hash(self.parentId) ^
      container.hash(self.type) ^
      container.hash(self.identifier) ^
      container.hash(self.status) ^
      container.hash(self.auditDetails);
  @override
  bool equals(Payee self, Payee other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.parentId, other.parentId) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.identifier, other.identifier) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin PayeeMappable {
  String toJson() => PayeeMapper.container.toJson(this as Payee);
  Map<String, dynamic> toMap() => PayeeMapper.container.toMap(this as Payee);
  PayeeCopyWith<Payee, Payee, Payee> get copyWith =>
      _PayeeCopyWithImpl(this as Payee, $identity, $identity);
  @override
  String toString() => PayeeMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          PayeeMapper.container.isEqual(this, other));
  @override
  int get hashCode => PayeeMapper.container.hash(this);
}

extension PayeeValueCopy<$R, $Out extends Payee>
    on ObjectCopyWith<$R, Payee, $Out> {
  PayeeCopyWith<$R, Payee, $Out> get asPayee =>
      base.as((v, t, t2) => _PayeeCopyWithImpl(v, t, t2));
}

typedef PayeeCopyWithBound = Payee;

abstract class PayeeCopyWith<$R, $In extends Payee, $Out extends Payee>
    implements ObjectCopyWith<$R, $In, $Out> {
  PayeeCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends Payee>(
      Then<Payee, $Out2> t, Then<$Out2, $R2> t2);
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      ContractAuditDetails? auditDetails,
      String? parentId,
      String? identifier,
      String? type,
      String? status});
}

class _PayeeCopyWithImpl<$R, $Out extends Payee>
    extends CopyWithBase<$R, Payee, $Out>
    implements PayeeCopyWith<$R, Payee, $Out> {
  _PayeeCopyWithImpl(super.value, super.then, super.then2);
  @override
  PayeeCopyWith<$R2, Payee, $Out2> chain<$R2, $Out2 extends Payee>(
          Then<Payee, $Out2> t, Then<$Out2, $R2> t2) =>
      _PayeeCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? auditDetails = $none,
          Object? parentId = $none,
          Object? identifier = $none,
          Object? type = $none,
          Object? status = $none}) =>
      $then(Payee(
          id: or(id, $value.id),
          auditDetails: or(auditDetails, $value.auditDetails),
          parentId: or(parentId, $value.parentId),
          identifier: or(identifier, $value.identifier),
          type: or(type, $value.type),
          status: or(status, $value.status)));
}

class BillLineItemsMapper extends MapperBase<BillLineItems> {
  static MapperContainer container = MapperContainer(
    mappers: {BillLineItemsMapper()},
  )..linkAll({ContractAuditDetailsMapper.container});

  @override
  BillLineItemsMapperElement createElement(MapperContainer container) {
    return BillLineItemsMapperElement._(this, container);
  }

  @override
  String get id => 'BillLineItems';

  static final fromMap = container.fromMap<BillLineItems>;
  static final fromJson = container.fromJson<BillLineItems>;
}

class BillLineItemsMapperElement extends MapperElementBase<BillLineItems> {
  BillLineItemsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BillLineItems decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BillLineItems fromMap(Map<String, dynamic> map) => BillLineItems(
      id: container.$getOpt(map, 'id'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      tenantId: container.$getOpt(map, 'tenantId'),
      amount: container.$getOpt(map, 'amount'),
      type: container.$getOpt(map, 'type'),
      status: container.$getOpt(map, 'status'),
      billDetailId: container.$getOpt(map, 'billDetailId'),
      headCode: container.$getOpt(map, 'headCode'),
      isLineItemPayable: container.$getOpt(map, 'isLineItemPayable'),
      paidAmount: container.$getOpt(map, 'paidAmount'));

  @override
  Function get encoder => encode;
  dynamic encode(BillLineItems v) => toMap(v);
  Map<String, dynamic> toMap(BillLineItems b) => {
        'id': container.$enc(b.id, 'id'),
        'auditDetails': container.$enc(b.auditDetails, 'auditDetails'),
        'tenantId': container.$enc(b.tenantId, 'tenantId'),
        'amount': container.$enc(b.amount, 'amount'),
        'type': container.$enc(b.type, 'type'),
        'status': container.$enc(b.status, 'status'),
        'billDetailId': container.$enc(b.billDetailId, 'billDetailId'),
        'headCode': container.$enc(b.headCode, 'headCode'),
        'isLineItemPayable':
            container.$enc(b.isLineItemPayable, 'isLineItemPayable'),
        'paidAmount': container.$enc(b.paidAmount, 'paidAmount')
      };

  @override
  String stringify(BillLineItems self) =>
      'BillLineItems(id: ${container.asString(self.id)}, billDetailId: ${container.asString(self.billDetailId)}, tenantId: ${container.asString(self.tenantId)}, headCode: ${container.asString(self.headCode)}, amount: ${container.asString(self.amount)}, type: ${container.asString(self.type)}, paidAmount: ${container.asString(self.paidAmount)}, status: ${container.asString(self.status)}, isLineItemPayable: ${container.asString(self.isLineItemPayable)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(BillLineItems self) =>
      container.hash(self.id) ^
      container.hash(self.billDetailId) ^
      container.hash(self.tenantId) ^
      container.hash(self.headCode) ^
      container.hash(self.amount) ^
      container.hash(self.type) ^
      container.hash(self.paidAmount) ^
      container.hash(self.status) ^
      container.hash(self.isLineItemPayable) ^
      container.hash(self.auditDetails);
  @override
  bool equals(BillLineItems self, BillLineItems other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.billDetailId, other.billDetailId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.headCode, other.headCode) &&
      container.isEqual(self.amount, other.amount) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.paidAmount, other.paidAmount) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.isLineItemPayable, other.isLineItemPayable) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin BillLineItemsMappable {
  String toJson() =>
      BillLineItemsMapper.container.toJson(this as BillLineItems);
  Map<String, dynamic> toMap() =>
      BillLineItemsMapper.container.toMap(this as BillLineItems);
  BillLineItemsCopyWith<BillLineItems, BillLineItems, BillLineItems>
      get copyWith => _BillLineItemsCopyWithImpl(
          this as BillLineItems, $identity, $identity);
  @override
  String toString() => BillLineItemsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BillLineItemsMapper.container.isEqual(this, other));
  @override
  int get hashCode => BillLineItemsMapper.container.hash(this);
}

extension BillLineItemsValueCopy<$R, $Out extends BillLineItems>
    on ObjectCopyWith<$R, BillLineItems, $Out> {
  BillLineItemsCopyWith<$R, BillLineItems, $Out> get asBillLineItems =>
      base.as((v, t, t2) => _BillLineItemsCopyWithImpl(v, t, t2));
}

typedef BillLineItemsCopyWithBound = BillLineItems;

abstract class BillLineItemsCopyWith<$R, $In extends BillLineItems,
    $Out extends BillLineItems> implements ObjectCopyWith<$R, $In, $Out> {
  BillLineItemsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BillLineItems>(
          Then<BillLineItems, $Out2> t, Then<$Out2, $R2> t2);
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      ContractAuditDetails? auditDetails,
      String? tenantId,
      int? amount,
      String? type,
      String? status,
      String? billDetailId,
      String? headCode,
      bool? isLineItemPayable,
      int? paidAmount});
}

class _BillLineItemsCopyWithImpl<$R, $Out extends BillLineItems>
    extends CopyWithBase<$R, BillLineItems, $Out>
    implements BillLineItemsCopyWith<$R, BillLineItems, $Out> {
  _BillLineItemsCopyWithImpl(super.value, super.then, super.then2);
  @override
  BillLineItemsCopyWith<$R2, BillLineItems, $Out2>
      chain<$R2, $Out2 extends BillLineItems>(
              Then<BillLineItems, $Out2> t, Then<$Out2, $R2> t2) =>
          _BillLineItemsCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? auditDetails = $none,
          Object? tenantId = $none,
          Object? amount = $none,
          Object? type = $none,
          Object? status = $none,
          Object? billDetailId = $none,
          Object? headCode = $none,
          Object? isLineItemPayable = $none,
          Object? paidAmount = $none}) =>
      $then(BillLineItems(
          id: or(id, $value.id),
          auditDetails: or(auditDetails, $value.auditDetails),
          tenantId: or(tenantId, $value.tenantId),
          amount: or(amount, $value.amount),
          type: or(type, $value.type),
          status: or(status, $value.status),
          billDetailId: or(billDetailId, $value.billDetailId),
          headCode: or(headCode, $value.headCode),
          isLineItemPayable: or(isLineItemPayable, $value.isLineItemPayable),
          paidAmount: or(paidAmount, $value.paidAmount)));
}

class PayableLineItemsMapper extends MapperBase<PayableLineItems> {
  static MapperContainer container = MapperContainer(
    mappers: {PayableLineItemsMapper()},
  )..linkAll({ContractAuditDetailsMapper.container});

  @override
  PayableLineItemsMapperElement createElement(MapperContainer container) {
    return PayableLineItemsMapperElement._(this, container);
  }

  @override
  String get id => 'PayableLineItems';

  static final fromMap = container.fromMap<PayableLineItems>;
  static final fromJson = container.fromJson<PayableLineItems>;
}

class PayableLineItemsMapperElement
    extends MapperElementBase<PayableLineItems> {
  PayableLineItemsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  PayableLineItems decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  PayableLineItems fromMap(Map<String, dynamic> map) => PayableLineItems(
      id: container.$getOpt(map, 'id'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      tenantId: container.$getOpt(map, 'tenantId'),
      amount: container.$getOpt(map, 'amount'),
      type: container.$getOpt(map, 'type'),
      status: container.$getOpt(map, 'status'),
      billDetailId: container.$getOpt(map, 'billDetailId'),
      headCode: container.$getOpt(map, 'headCode'),
      isLineItemPayable: container.$getOpt(map, 'isLineItemPayable'),
      paidAmount: container.$getOpt(map, 'paidAmount'));

  @override
  Function get encoder => encode;
  dynamic encode(PayableLineItems v) => toMap(v);
  Map<String, dynamic> toMap(PayableLineItems p) => {
        'id': container.$enc(p.id, 'id'),
        'auditDetails': container.$enc(p.auditDetails, 'auditDetails'),
        'tenantId': container.$enc(p.tenantId, 'tenantId'),
        'amount': container.$enc(p.amount, 'amount'),
        'type': container.$enc(p.type, 'type'),
        'status': container.$enc(p.status, 'status'),
        'billDetailId': container.$enc(p.billDetailId, 'billDetailId'),
        'headCode': container.$enc(p.headCode, 'headCode'),
        'isLineItemPayable':
            container.$enc(p.isLineItemPayable, 'isLineItemPayable'),
        'paidAmount': container.$enc(p.paidAmount, 'paidAmount')
      };

  @override
  String stringify(PayableLineItems self) =>
      'PayableLineItems(id: ${container.asString(self.id)}, billDetailId: ${container.asString(self.billDetailId)}, tenantId: ${container.asString(self.tenantId)}, headCode: ${container.asString(self.headCode)}, amount: ${container.asString(self.amount)}, type: ${container.asString(self.type)}, paidAmount: ${container.asString(self.paidAmount)}, status: ${container.asString(self.status)}, isLineItemPayable: ${container.asString(self.isLineItemPayable)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(PayableLineItems self) =>
      container.hash(self.id) ^
      container.hash(self.billDetailId) ^
      container.hash(self.tenantId) ^
      container.hash(self.headCode) ^
      container.hash(self.amount) ^
      container.hash(self.type) ^
      container.hash(self.paidAmount) ^
      container.hash(self.status) ^
      container.hash(self.isLineItemPayable) ^
      container.hash(self.auditDetails);
  @override
  bool equals(PayableLineItems self, PayableLineItems other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.billDetailId, other.billDetailId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.headCode, other.headCode) &&
      container.isEqual(self.amount, other.amount) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.paidAmount, other.paidAmount) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.isLineItemPayable, other.isLineItemPayable) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin PayableLineItemsMappable {
  String toJson() =>
      PayableLineItemsMapper.container.toJson(this as PayableLineItems);
  Map<String, dynamic> toMap() =>
      PayableLineItemsMapper.container.toMap(this as PayableLineItems);
  PayableLineItemsCopyWith<PayableLineItems, PayableLineItems, PayableLineItems>
      get copyWith => _PayableLineItemsCopyWithImpl(
          this as PayableLineItems, $identity, $identity);
  @override
  String toString() => PayableLineItemsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          PayableLineItemsMapper.container.isEqual(this, other));
  @override
  int get hashCode => PayableLineItemsMapper.container.hash(this);
}

extension PayableLineItemsValueCopy<$R, $Out extends PayableLineItems>
    on ObjectCopyWith<$R, PayableLineItems, $Out> {
  PayableLineItemsCopyWith<$R, PayableLineItems, $Out> get asPayableLineItems =>
      base.as((v, t, t2) => _PayableLineItemsCopyWithImpl(v, t, t2));
}

typedef PayableLineItemsCopyWithBound = PayableLineItems;

abstract class PayableLineItemsCopyWith<$R, $In extends PayableLineItems,
    $Out extends PayableLineItems> implements ObjectCopyWith<$R, $In, $Out> {
  PayableLineItemsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends PayableLineItems>(
          Then<PayableLineItems, $Out2> t, Then<$Out2, $R2> t2);
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      ContractAuditDetails? auditDetails,
      String? tenantId,
      int? amount,
      String? type,
      String? status,
      String? billDetailId,
      String? headCode,
      bool? isLineItemPayable,
      int? paidAmount});
}

class _PayableLineItemsCopyWithImpl<$R, $Out extends PayableLineItems>
    extends CopyWithBase<$R, PayableLineItems, $Out>
    implements PayableLineItemsCopyWith<$R, PayableLineItems, $Out> {
  _PayableLineItemsCopyWithImpl(super.value, super.then, super.then2);
  @override
  PayableLineItemsCopyWith<$R2, PayableLineItems, $Out2>
      chain<$R2, $Out2 extends PayableLineItems>(
              Then<PayableLineItems, $Out2> t, Then<$Out2, $R2> t2) =>
          _PayableLineItemsCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? auditDetails = $none,
          Object? tenantId = $none,
          Object? amount = $none,
          Object? type = $none,
          Object? status = $none,
          Object? billDetailId = $none,
          Object? headCode = $none,
          Object? isLineItemPayable = $none,
          Object? paidAmount = $none}) =>
      $then(PayableLineItems(
          id: or(id, $value.id),
          auditDetails: or(auditDetails, $value.auditDetails),
          tenantId: or(tenantId, $value.tenantId),
          amount: or(amount, $value.amount),
          type: or(type, $value.type),
          status: or(status, $value.status),
          billDetailId: or(billDetailId, $value.billDetailId),
          headCode: or(headCode, $value.headCode),
          isLineItemPayable: or(isLineItemPayable, $value.isLineItemPayable),
          paidAmount: or(paidAmount, $value.paidAmount)));
}
