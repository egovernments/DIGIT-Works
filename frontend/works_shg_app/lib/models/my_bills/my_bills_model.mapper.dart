// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'my_bills_model.dart';

class MyBillsListModelMapper extends MapperBase<MyBillsListModel> {
  static MapperContainer container = MapperContainer(
    mappers: {MyBillsListModelMapper()},
  )..linkAll({MyBillModelMapper.container});

  @override
  MyBillsListModelMapperElement createElement(MapperContainer container) {
    return MyBillsListModelMapperElement._(this, container);
  }

  @override
  String get id => 'MyBillsListModel';

  static final fromMap = container.fromMap<MyBillsListModel>;
  static final fromJson = container.fromJson<MyBillsListModel>;
}

class MyBillsListModelMapperElement
    extends MapperElementBase<MyBillsListModel> {
  MyBillsListModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  MyBillsListModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  MyBillsListModel fromMap(Map<String, dynamic> map) =>
      MyBillsListModel(bills: container.$getOpt(map, 'bills'));

  @override
  Function get encoder => encode;
  dynamic encode(MyBillsListModel v) => toMap(v);
  Map<String, dynamic> toMap(MyBillsListModel m) =>
      {'bills': container.$enc(m.bills, 'bills')};

  @override
  String stringify(MyBillsListModel self) =>
      'MyBillsListModel(bills: ${container.asString(self.bills)})';
  @override
  int hash(MyBillsListModel self) => container.hash(self.bills);
  @override
  bool equals(MyBillsListModel self, MyBillsListModel other) =>
      container.isEqual(self.bills, other.bills);
}

mixin MyBillsListModelMappable {
  String toJson() =>
      MyBillsListModelMapper.container.toJson(this as MyBillsListModel);
  Map<String, dynamic> toMap() =>
      MyBillsListModelMapper.container.toMap(this as MyBillsListModel);
  MyBillsListModelCopyWith<MyBillsListModel, MyBillsListModel, MyBillsListModel>
      get copyWith => _MyBillsListModelCopyWithImpl(
          this as MyBillsListModel, $identity, $identity);
  @override
  String toString() => MyBillsListModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          MyBillsListModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => MyBillsListModelMapper.container.hash(this);
}

extension MyBillsListModelValueCopy<$R, $Out extends MyBillsListModel>
    on ObjectCopyWith<$R, MyBillsListModel, $Out> {
  MyBillsListModelCopyWith<$R, MyBillsListModel, $Out> get asMyBillsListModel =>
      base.as((v, t, t2) => _MyBillsListModelCopyWithImpl(v, t, t2));
}

typedef MyBillsListModelCopyWithBound = MyBillsListModel;

abstract class MyBillsListModelCopyWith<$R, $In extends MyBillsListModel,
    $Out extends MyBillsListModel> implements ObjectCopyWith<$R, $In, $Out> {
  MyBillsListModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends MyBillsListModel>(
          Then<MyBillsListModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, MyBillModel,
      MyBillModelCopyWith<$R, MyBillModel, MyBillModel>>? get bills;
  $R call({List<MyBillModel>? bills});
}

class _MyBillsListModelCopyWithImpl<$R, $Out extends MyBillsListModel>
    extends CopyWithBase<$R, MyBillsListModel, $Out>
    implements MyBillsListModelCopyWith<$R, MyBillsListModel, $Out> {
  _MyBillsListModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  MyBillsListModelCopyWith<$R2, MyBillsListModel, $Out2>
      chain<$R2, $Out2 extends MyBillsListModel>(
              Then<MyBillsListModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _MyBillsListModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, MyBillModel,
          MyBillModelCopyWith<$R, MyBillModel, MyBillModel>>?
      get bills => $value.bills != null
          ? ListCopyWith(
              $value.bills!,
              (v, t) => v.copyWith.chain<$R, MyBillModel>($identity, t),
              (v) => call(bills: v))
          : null;
  @override
  $R call({Object? bills = $none}) =>
      $then(MyBillsListModel(bills: or(bills, $value.bills)));
}

class MyBillModelMapper extends MapperBase<MyBillModel> {
  static MapperContainer container = MapperContainer(
    mappers: {MyBillModelMapper()},
  )..linkAll({BillModelMapper.container});

  @override
  MyBillModelMapperElement createElement(MapperContainer container) {
    return MyBillModelMapperElement._(this, container);
  }

  @override
  String get id => 'MyBillModel';

  static final fromMap = container.fromMap<MyBillModel>;
  static final fromJson = container.fromJson<MyBillModel>;
}

class MyBillModelMapperElement extends MapperElementBase<MyBillModel> {
  MyBillModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  MyBillModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  MyBillModel fromMap(Map<String, dynamic> map) => MyBillModel(
      bill: container.$getOpt(map, 'bill'),
      musterRollNumber: container.$getOpt(map, 'musterRollNumber'),
      contractNumber: container.$getOpt(map, 'contractNumber'));

  @override
  Function get encoder => encode;
  dynamic encode(MyBillModel v) => toMap(v);
  Map<String, dynamic> toMap(MyBillModel m) => {
        'bill': container.$enc(m.bill, 'bill'),
        'musterRollNumber':
            container.$enc(m.musterRollNumber, 'musterRollNumber'),
        'contractNumber': container.$enc(m.contractNumber, 'contractNumber')
      };

  @override
  String stringify(MyBillModel self) =>
      'MyBillModel(contractNumber: ${container.asString(self.contractNumber)}, musterRollNumber: ${container.asString(self.musterRollNumber)}, bill: ${container.asString(self.bill)})';
  @override
  int hash(MyBillModel self) =>
      container.hash(self.contractNumber) ^
      container.hash(self.musterRollNumber) ^
      container.hash(self.bill);
  @override
  bool equals(MyBillModel self, MyBillModel other) =>
      container.isEqual(self.contractNumber, other.contractNumber) &&
      container.isEqual(self.musterRollNumber, other.musterRollNumber) &&
      container.isEqual(self.bill, other.bill);
}

mixin MyBillModelMappable {
  String toJson() => MyBillModelMapper.container.toJson(this as MyBillModel);
  Map<String, dynamic> toMap() =>
      MyBillModelMapper.container.toMap(this as MyBillModel);
  MyBillModelCopyWith<MyBillModel, MyBillModel, MyBillModel> get copyWith =>
      _MyBillModelCopyWithImpl(this as MyBillModel, $identity, $identity);
  @override
  String toString() => MyBillModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          MyBillModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => MyBillModelMapper.container.hash(this);
}

extension MyBillModelValueCopy<$R, $Out extends MyBillModel>
    on ObjectCopyWith<$R, MyBillModel, $Out> {
  MyBillModelCopyWith<$R, MyBillModel, $Out> get asMyBillModel =>
      base.as((v, t, t2) => _MyBillModelCopyWithImpl(v, t, t2));
}

typedef MyBillModelCopyWithBound = MyBillModel;

abstract class MyBillModelCopyWith<$R, $In extends MyBillModel,
    $Out extends MyBillModel> implements ObjectCopyWith<$R, $In, $Out> {
  MyBillModelCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends MyBillModel>(
      Then<MyBillModel, $Out2> t, Then<$Out2, $R2> t2);
  BillModelCopyWith<$R, BillModel, BillModel>? get bill;
  $R call({BillModel? bill, String? musterRollNumber, String? contractNumber});
}

class _MyBillModelCopyWithImpl<$R, $Out extends MyBillModel>
    extends CopyWithBase<$R, MyBillModel, $Out>
    implements MyBillModelCopyWith<$R, MyBillModel, $Out> {
  _MyBillModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  MyBillModelCopyWith<$R2, MyBillModel, $Out2>
      chain<$R2, $Out2 extends MyBillModel>(
              Then<MyBillModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _MyBillModelCopyWithImpl($value, t, t2);

  @override
  BillModelCopyWith<$R, BillModel, BillModel>? get bill =>
      $value.bill?.copyWith.chain($identity, (v) => call(bill: v));
  @override
  $R call(
          {Object? bill = $none,
          Object? musterRollNumber = $none,
          Object? contractNumber = $none}) =>
      $then(MyBillModel(
          bill: or(bill, $value.bill),
          musterRollNumber: or(musterRollNumber, $value.musterRollNumber),
          contractNumber: or(contractNumber, $value.contractNumber)));
}

class BillModelMapper extends MapperBase<BillModel> {
  static MapperContainer container = MapperContainer(
    mappers: {BillModelMapper()},
  )..linkAll({
      ContractAuditDetailsMapper.container,
      BillDetailsMapper.container,
      PayerMapper.container,
      BillAdditionalDetailsMapper.container,
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
      tenantId: container.$get(map, 'tenantId'),
      referenceId: container.$getOpt(map, 'referenceId'),
      status: container.$getOpt(map, 'status'),
      dueDate: container.$getOpt(map, 'dueDate'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      paymentStatus: container.$getOpt(map, 'paymentStatus'),
      billDate: container.$getOpt(map, 'billDate'),
      billDetails: container.$getOpt(map, 'billDetails'),
      businessService: container.$getOpt(map, 'businessService'),
      fromPeriod: container.$getOpt(map, 'fromPeriod'),
      netPaidAmount: container.$getOpt(map, 'netPaidAmount'),
      netPayableAmount: container.$getOpt(map, 'netPayableAmount'),
      payer: container.$getOpt(map, 'payer'),
      toPeriod: container.$getOpt(map, 'toPeriod'),
      billNumber: container.$getOpt(map, 'billNumber'),
      wfStatus: container.$getOpt(map, 'wfStatus'),
      additionalDetails: container.$getOpt(map, 'additionalDetails'),
      totalAmount: container.$getOpt(map, 'totalAmount'),
      totalPaidAmount: container.$getOpt(map, 'totalPaidAmount'));

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
        'businessService': container.$enc(b.businessService, 'businessService'),
        'fromPeriod': container.$enc(b.fromPeriod, 'fromPeriod'),
        'netPaidAmount': container.$enc(b.netPaidAmount, 'netPaidAmount'),
        'netPayableAmount':
            container.$enc(b.netPayableAmount, 'netPayableAmount'),
        'payer': container.$enc(b.payer, 'payer'),
        'toPeriod': container.$enc(b.toPeriod, 'toPeriod'),
        'billNumber': container.$enc(b.billNumber, 'billNumber'),
        'wfStatus': container.$enc(b.wfStatus, 'wfStatus'),
        'additionalDetails':
            container.$enc(b.additionalDetails, 'additionalDetails'),
        'totalAmount': container.$enc(b.totalAmount, 'totalAmount'),
        'totalPaidAmount': container.$enc(b.totalPaidAmount, 'totalPaidAmount')
      };

  @override
  String stringify(BillModel self) =>
      'BillModel(tenantId: ${container.asString(self.tenantId)}, id: ${container.asString(self.id)}, billDate: ${container.asString(self.billDate)}, dueDate: ${container.asString(self.dueDate)}, netPayableAmount: ${container.asString(self.netPayableAmount)}, netPaidAmount: ${container.asString(self.netPaidAmount)}, totalAmount: ${container.asString(self.totalAmount)}, totalPaidAmount: ${container.asString(self.totalPaidAmount)}, businessService: ${container.asString(self.businessService)}, billNumber: ${container.asString(self.billNumber)}, referenceId: ${container.asString(self.referenceId)}, fromPeriod: ${container.asString(self.fromPeriod)}, toPeriod: ${container.asString(self.toPeriod)}, paymentStatus: ${container.asString(self.paymentStatus)}, status: ${container.asString(self.status)}, wfStatus: ${container.asString(self.wfStatus)}, payer: ${container.asString(self.payer)}, additionalDetails: ${container.asString(self.additionalDetails)}, billDetails: ${container.asString(self.billDetails)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(BillModel self) =>
      container.hash(self.tenantId) ^
      container.hash(self.id) ^
      container.hash(self.billDate) ^
      container.hash(self.dueDate) ^
      container.hash(self.netPayableAmount) ^
      container.hash(self.netPaidAmount) ^
      container.hash(self.totalAmount) ^
      container.hash(self.totalPaidAmount) ^
      container.hash(self.businessService) ^
      container.hash(self.billNumber) ^
      container.hash(self.referenceId) ^
      container.hash(self.fromPeriod) ^
      container.hash(self.toPeriod) ^
      container.hash(self.paymentStatus) ^
      container.hash(self.status) ^
      container.hash(self.wfStatus) ^
      container.hash(self.payer) ^
      container.hash(self.additionalDetails) ^
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
      container.isEqual(self.totalAmount, other.totalAmount) &&
      container.isEqual(self.totalPaidAmount, other.totalPaidAmount) &&
      container.isEqual(self.businessService, other.businessService) &&
      container.isEqual(self.billNumber, other.billNumber) &&
      container.isEqual(self.referenceId, other.referenceId) &&
      container.isEqual(self.fromPeriod, other.fromPeriod) &&
      container.isEqual(self.toPeriod, other.toPeriod) &&
      container.isEqual(self.paymentStatus, other.paymentStatus) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.wfStatus, other.wfStatus) &&
      container.isEqual(self.payer, other.payer) &&
      container.isEqual(self.additionalDetails, other.additionalDetails) &&
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
  BillAdditionalDetailsCopyWith<$R, BillAdditionalDetails,
      BillAdditionalDetails>? get additionalDetails;
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
      String? businessService,
      int? fromPeriod,
      num? netPaidAmount,
      num? netPayableAmount,
      Payer? payer,
      int? toPeriod,
      String? billNumber,
      String? wfStatus,
      BillAdditionalDetails? additionalDetails,
      num? totalAmount,
      num? totalPaidAmount});
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
  BillAdditionalDetailsCopyWith<$R, BillAdditionalDetails,
          BillAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .chain($identity, (v) => call(additionalDetails: v));
  @override
  $R call(
          {Object? id = $none,
          String? tenantId,
          Object? referenceId = $none,
          Object? status = $none,
          Object? dueDate = $none,
          Object? auditDetails = $none,
          Object? paymentStatus = $none,
          Object? billDate = $none,
          Object? billDetails = $none,
          Object? businessService = $none,
          Object? fromPeriod = $none,
          Object? netPaidAmount = $none,
          Object? netPayableAmount = $none,
          Object? payer = $none,
          Object? toPeriod = $none,
          Object? billNumber = $none,
          Object? wfStatus = $none,
          Object? additionalDetails = $none,
          Object? totalAmount = $none,
          Object? totalPaidAmount = $none}) =>
      $then(BillModel(
          id: or(id, $value.id),
          tenantId: tenantId ?? $value.tenantId,
          referenceId: or(referenceId, $value.referenceId),
          status: or(status, $value.status),
          dueDate: or(dueDate, $value.dueDate),
          auditDetails: or(auditDetails, $value.auditDetails),
          paymentStatus: or(paymentStatus, $value.paymentStatus),
          billDate: or(billDate, $value.billDate),
          billDetails: or(billDetails, $value.billDetails),
          businessService: or(businessService, $value.businessService),
          fromPeriod: or(fromPeriod, $value.fromPeriod),
          netPaidAmount: or(netPaidAmount, $value.netPaidAmount),
          netPayableAmount: or(netPayableAmount, $value.netPayableAmount),
          payer: or(payer, $value.payer),
          toPeriod: or(toPeriod, $value.toPeriod),
          billNumber: or(billNumber, $value.billNumber),
          wfStatus: or(wfStatus, $value.wfStatus),
          additionalDetails: or(additionalDetails, $value.additionalDetails),
          totalAmount: or(totalAmount, $value.totalAmount),
          totalPaidAmount: or(totalPaidAmount, $value.totalPaidAmount)));
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

class BillAdditionalDetailsMapper extends MapperBase<BillAdditionalDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {BillAdditionalDetailsMapper()},
  );

  @override
  BillAdditionalDetailsMapperElement createElement(MapperContainer container) {
    return BillAdditionalDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'BillAdditionalDetails';

  static final fromMap = container.fromMap<BillAdditionalDetails>;
  static final fromJson = container.fromJson<BillAdditionalDetails>;
}

class BillAdditionalDetailsMapperElement
    extends MapperElementBase<BillAdditionalDetails> {
  BillAdditionalDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  BillAdditionalDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  BillAdditionalDetails fromMap(Map<String, dynamic> map) =>
      BillAdditionalDetails(
          invoiceNumber: container.$getOpt(map, 'invoiceNumber'),
          locality: container.$getOpt(map, 'locality'),
          orgName: container.$getOpt(map, 'orgName'),
          projectDesc: container.$getOpt(map, 'projectDesc'),
          projectName: container.$getOpt(map, 'projectName'),
          projectId: container.$getOpt(map, 'projectId'),
          ward: container.$getOpt(map, 'ward'),
          totalBillAmount: container.$getOpt(map, 'totalBillAmount'),
          invoiceDate: container.$getOpt(map, 'invoiceDate'));

  @override
  Function get encoder => encode;
  dynamic encode(BillAdditionalDetails v) => toMap(v);
  Map<String, dynamic> toMap(BillAdditionalDetails b) => {
        'invoiceNumber': container.$enc(b.invoiceNumber, 'invoiceNumber'),
        'locality': container.$enc(b.locality, 'locality'),
        'orgName': container.$enc(b.orgName, 'orgName'),
        'projectDesc': container.$enc(b.projectDesc, 'projectDesc'),
        'projectName': container.$enc(b.projectName, 'projectName'),
        'projectId': container.$enc(b.projectId, 'projectId'),
        'ward': container.$enc(b.ward, 'ward'),
        'totalBillAmount': container.$enc(b.totalBillAmount, 'totalBillAmount'),
        'invoiceDate': container.$enc(b.invoiceDate, 'invoiceDate')
      };

  @override
  String stringify(BillAdditionalDetails self) =>
      'BillAdditionalDetails(invoiceNumber: ${container.asString(self.invoiceNumber)}, invoiceDate: ${container.asString(self.invoiceDate)}, locality: ${container.asString(self.locality)}, orgName: ${container.asString(self.orgName)}, projectDesc: ${container.asString(self.projectDesc)}, projectName: ${container.asString(self.projectName)}, projectId: ${container.asString(self.projectId)}, totalBillAmount: ${container.asString(self.totalBillAmount)}, ward: ${container.asString(self.ward)})';
  @override
  int hash(BillAdditionalDetails self) =>
      container.hash(self.invoiceNumber) ^
      container.hash(self.invoiceDate) ^
      container.hash(self.locality) ^
      container.hash(self.orgName) ^
      container.hash(self.projectDesc) ^
      container.hash(self.projectName) ^
      container.hash(self.projectId) ^
      container.hash(self.totalBillAmount) ^
      container.hash(self.ward);
  @override
  bool equals(BillAdditionalDetails self, BillAdditionalDetails other) =>
      container.isEqual(self.invoiceNumber, other.invoiceNumber) &&
      container.isEqual(self.invoiceDate, other.invoiceDate) &&
      container.isEqual(self.locality, other.locality) &&
      container.isEqual(self.orgName, other.orgName) &&
      container.isEqual(self.projectDesc, other.projectDesc) &&
      container.isEqual(self.projectName, other.projectName) &&
      container.isEqual(self.projectId, other.projectId) &&
      container.isEqual(self.totalBillAmount, other.totalBillAmount) &&
      container.isEqual(self.ward, other.ward);
}

mixin BillAdditionalDetailsMappable {
  String toJson() => BillAdditionalDetailsMapper.container
      .toJson(this as BillAdditionalDetails);
  Map<String, dynamic> toMap() => BillAdditionalDetailsMapper.container
      .toMap(this as BillAdditionalDetails);
  BillAdditionalDetailsCopyWith<BillAdditionalDetails, BillAdditionalDetails,
          BillAdditionalDetails>
      get copyWith => _BillAdditionalDetailsCopyWithImpl(
          this as BillAdditionalDetails, $identity, $identity);
  @override
  String toString() => BillAdditionalDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          BillAdditionalDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => BillAdditionalDetailsMapper.container.hash(this);
}

extension BillAdditionalDetailsValueCopy<$R, $Out extends BillAdditionalDetails>
    on ObjectCopyWith<$R, BillAdditionalDetails, $Out> {
  BillAdditionalDetailsCopyWith<$R, BillAdditionalDetails, $Out>
      get asBillAdditionalDetails =>
          base.as((v, t, t2) => _BillAdditionalDetailsCopyWithImpl(v, t, t2));
}

typedef BillAdditionalDetailsCopyWithBound = BillAdditionalDetails;

abstract class BillAdditionalDetailsCopyWith<$R,
        $In extends BillAdditionalDetails, $Out extends BillAdditionalDetails>
    implements ObjectCopyWith<$R, $In, $Out> {
  BillAdditionalDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends BillAdditionalDetails>(
          Then<BillAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? invoiceNumber,
      String? locality,
      String? orgName,
      String? projectDesc,
      String? projectName,
      String? projectId,
      String? ward,
      String? totalBillAmount,
      int? invoiceDate});
}

class _BillAdditionalDetailsCopyWithImpl<$R, $Out extends BillAdditionalDetails>
    extends CopyWithBase<$R, BillAdditionalDetails, $Out>
    implements BillAdditionalDetailsCopyWith<$R, BillAdditionalDetails, $Out> {
  _BillAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  BillAdditionalDetailsCopyWith<$R2, BillAdditionalDetails, $Out2>
      chain<$R2, $Out2 extends BillAdditionalDetails>(
              Then<BillAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _BillAdditionalDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? invoiceNumber = $none,
          Object? locality = $none,
          Object? orgName = $none,
          Object? projectDesc = $none,
          Object? projectName = $none,
          Object? projectId = $none,
          Object? ward = $none,
          Object? totalBillAmount = $none,
          Object? invoiceDate = $none}) =>
      $then(BillAdditionalDetails(
          invoiceNumber: or(invoiceNumber, $value.invoiceNumber),
          locality: or(locality, $value.locality),
          orgName: or(orgName, $value.orgName),
          projectDesc: or(projectDesc, $value.projectDesc),
          projectName: or(projectName, $value.projectName),
          projectId: or(projectId, $value.projectId),
          ward: or(ward, $value.ward),
          totalBillAmount: or(totalBillAmount, $value.totalBillAmount),
          invoiceDate: or(invoiceDate, $value.invoiceDate)));
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
      tenantId: container.$get(map, 'tenantId'),
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
      num? amount,
      String? type,
      String? status,
      String? billDetailId,
      String? headCode,
      bool? isLineItemPayable,
      num? paidAmount});
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
          String? tenantId,
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
          tenantId: tenantId ?? $value.tenantId,
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
      tenantId: container.$get(map, 'tenantId'),
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
      num? amount,
      String? type,
      String? status,
      String? billDetailId,
      String? headCode,
      bool? isLineItemPayable,
      num? paidAmount});
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
          String? tenantId,
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
          tenantId: tenantId ?? $value.tenantId,
          amount: or(amount, $value.amount),
          type: or(type, $value.type),
          status: or(status, $value.status),
          billDetailId: or(billDetailId, $value.billDetailId),
          headCode: or(headCode, $value.headCode),
          isLineItemPayable: or(isLineItemPayable, $value.isLineItemPayable),
          paidAmount: or(paidAmount, $value.paidAmount)));
}
