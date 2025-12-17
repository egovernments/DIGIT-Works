// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'my_bills_model.dart';

class MyBillsListModelMapper extends ClassMapperBase<MyBillsListModel> {
  MyBillsListModelMapper._();

  static MyBillsListModelMapper? _instance;
  static MyBillsListModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = MyBillsListModelMapper._());
      MyBillModelMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'MyBillsListModel';

  static List<MyBillModel>? _$bills(MyBillsListModel v) => v.bills;
  static const Field<MyBillsListModel, List<MyBillModel>> _f$bills =
      Field('bills', _$bills, opt: true);

  @override
  final MappableFields<MyBillsListModel> fields = const {
    #bills: _f$bills,
  };

  static MyBillsListModel _instantiate(DecodingData data) {
    return MyBillsListModel(bills: data.dec(_f$bills));
  }

  @override
  final Function instantiate = _instantiate;

  static MyBillsListModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<MyBillsListModel>(map);
  }

  static MyBillsListModel fromJson(String json) {
    return ensureInitialized().decodeJson<MyBillsListModel>(json);
  }
}

mixin MyBillsListModelMappable {
  String toJson() {
    return MyBillsListModelMapper.ensureInitialized()
        .encodeJson<MyBillsListModel>(this as MyBillsListModel);
  }

  Map<String, dynamic> toMap() {
    return MyBillsListModelMapper.ensureInitialized()
        .encodeMap<MyBillsListModel>(this as MyBillsListModel);
  }

  MyBillsListModelCopyWith<MyBillsListModel, MyBillsListModel, MyBillsListModel>
      get copyWith => _MyBillsListModelCopyWithImpl(
          this as MyBillsListModel, $identity, $identity);
  @override
  String toString() {
    return MyBillsListModelMapper.ensureInitialized()
        .stringifyValue(this as MyBillsListModel);
  }

  @override
  bool operator ==(Object other) {
    return MyBillsListModelMapper.ensureInitialized()
        .equalsValue(this as MyBillsListModel, other);
  }

  @override
  int get hashCode {
    return MyBillsListModelMapper.ensureInitialized()
        .hashValue(this as MyBillsListModel);
  }
}

extension MyBillsListModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, MyBillsListModel, $Out> {
  MyBillsListModelCopyWith<$R, MyBillsListModel, $Out>
      get $asMyBillsListModel =>
          $base.as((v, t, t2) => _MyBillsListModelCopyWithImpl(v, t, t2));
}

abstract class MyBillsListModelCopyWith<$R, $In extends MyBillsListModel, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, MyBillModel,
      MyBillModelCopyWith<$R, MyBillModel, MyBillModel>>? get bills;
  $R call({List<MyBillModel>? bills});
  MyBillsListModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _MyBillsListModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, MyBillsListModel, $Out>
    implements MyBillsListModelCopyWith<$R, MyBillsListModel, $Out> {
  _MyBillsListModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<MyBillsListModel> $mapper =
      MyBillsListModelMapper.ensureInitialized();
  @override
  ListCopyWith<$R, MyBillModel,
          MyBillModelCopyWith<$R, MyBillModel, MyBillModel>>?
      get bills => $value.bills != null
          ? ListCopyWith($value.bills!, (v, t) => v.copyWith.$chain(t),
              (v) => call(bills: v))
          : null;
  @override
  $R call({Object? bills = $none}) =>
      $apply(FieldCopyWithData({if (bills != $none) #bills: bills}));
  @override
  MyBillsListModel $make(CopyWithData data) =>
      MyBillsListModel(bills: data.get(#bills, or: $value.bills));

  @override
  MyBillsListModelCopyWith<$R2, MyBillsListModel, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _MyBillsListModelCopyWithImpl($value, $cast, t);
}

class MyBillModelMapper extends ClassMapperBase<MyBillModel> {
  MyBillModelMapper._();

  static MyBillModelMapper? _instance;
  static MyBillModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = MyBillModelMapper._());
      BillModelMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'MyBillModel';

  static BillModel? _$bill(MyBillModel v) => v.bill;
  static const Field<MyBillModel, BillModel> _f$bill =
      Field('bill', _$bill, opt: true);
  static String? _$musterRollNumber(MyBillModel v) => v.musterRollNumber;
  static const Field<MyBillModel, String> _f$musterRollNumber =
      Field('musterRollNumber', _$musterRollNumber, opt: true);
  static String? _$contractNumber(MyBillModel v) => v.contractNumber;
  static const Field<MyBillModel, String> _f$contractNumber =
      Field('contractNumber', _$contractNumber, opt: true);

  @override
  final MappableFields<MyBillModel> fields = const {
    #bill: _f$bill,
    #musterRollNumber: _f$musterRollNumber,
    #contractNumber: _f$contractNumber,
  };

  static MyBillModel _instantiate(DecodingData data) {
    return MyBillModel(
        bill: data.dec(_f$bill),
        musterRollNumber: data.dec(_f$musterRollNumber),
        contractNumber: data.dec(_f$contractNumber));
  }

  @override
  final Function instantiate = _instantiate;

  static MyBillModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<MyBillModel>(map);
  }

  static MyBillModel fromJson(String json) {
    return ensureInitialized().decodeJson<MyBillModel>(json);
  }
}

mixin MyBillModelMappable {
  String toJson() {
    return MyBillModelMapper.ensureInitialized()
        .encodeJson<MyBillModel>(this as MyBillModel);
  }

  Map<String, dynamic> toMap() {
    return MyBillModelMapper.ensureInitialized()
        .encodeMap<MyBillModel>(this as MyBillModel);
  }

  MyBillModelCopyWith<MyBillModel, MyBillModel, MyBillModel> get copyWith =>
      _MyBillModelCopyWithImpl(this as MyBillModel, $identity, $identity);
  @override
  String toString() {
    return MyBillModelMapper.ensureInitialized()
        .stringifyValue(this as MyBillModel);
  }

  @override
  bool operator ==(Object other) {
    return MyBillModelMapper.ensureInitialized()
        .equalsValue(this as MyBillModel, other);
  }

  @override
  int get hashCode {
    return MyBillModelMapper.ensureInitialized().hashValue(this as MyBillModel);
  }
}

extension MyBillModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, MyBillModel, $Out> {
  MyBillModelCopyWith<$R, MyBillModel, $Out> get $asMyBillModel =>
      $base.as((v, t, t2) => _MyBillModelCopyWithImpl(v, t, t2));
}

abstract class MyBillModelCopyWith<$R, $In extends MyBillModel, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  BillModelCopyWith<$R, BillModel, BillModel>? get bill;
  $R call({BillModel? bill, String? musterRollNumber, String? contractNumber});
  MyBillModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _MyBillModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, MyBillModel, $Out>
    implements MyBillModelCopyWith<$R, MyBillModel, $Out> {
  _MyBillModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<MyBillModel> $mapper =
      MyBillModelMapper.ensureInitialized();
  @override
  BillModelCopyWith<$R, BillModel, BillModel>? get bill =>
      $value.bill?.copyWith.$chain((v) => call(bill: v));
  @override
  $R call(
          {Object? bill = $none,
          Object? musterRollNumber = $none,
          Object? contractNumber = $none}) =>
      $apply(FieldCopyWithData({
        if (bill != $none) #bill: bill,
        if (musterRollNumber != $none) #musterRollNumber: musterRollNumber,
        if (contractNumber != $none) #contractNumber: contractNumber
      }));
  @override
  MyBillModel $make(CopyWithData data) => MyBillModel(
      bill: data.get(#bill, or: $value.bill),
      musterRollNumber:
          data.get(#musterRollNumber, or: $value.musterRollNumber),
      contractNumber: data.get(#contractNumber, or: $value.contractNumber));

  @override
  MyBillModelCopyWith<$R2, MyBillModel, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _MyBillModelCopyWithImpl($value, $cast, t);
}

class BillModelMapper extends ClassMapperBase<BillModel> {
  BillModelMapper._();

  static BillModelMapper? _instance;
  static BillModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BillModelMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
      BillDetailsMapper.ensureInitialized();
      PayerMapper.ensureInitialized();
      BillAdditionalDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'BillModel';

  static String? _$id(BillModel v) => v.id;
  static const Field<BillModel, String> _f$id = Field('id', _$id, opt: true);
  static String _$tenantId(BillModel v) => v.tenantId;
  static const Field<BillModel, String> _f$tenantId =
      Field('tenantId', _$tenantId);
  static String? _$referenceId(BillModel v) => v.referenceId;
  static const Field<BillModel, String> _f$referenceId =
      Field('referenceId', _$referenceId, opt: true);
  static String? _$status(BillModel v) => v.status;
  static const Field<BillModel, String> _f$status =
      Field('status', _$status, opt: true);
  static int? _$dueDate(BillModel v) => v.dueDate;
  static const Field<BillModel, int> _f$dueDate =
      Field('dueDate', _$dueDate, opt: true);
  static ContractAuditDetails? _$auditDetails(BillModel v) => v.auditDetails;
  static const Field<BillModel, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String? _$paymentStatus(BillModel v) => v.paymentStatus;
  static const Field<BillModel, String> _f$paymentStatus =
      Field('paymentStatus', _$paymentStatus, opt: true);
  static int? _$billDate(BillModel v) => v.billDate;
  static const Field<BillModel, int> _f$billDate =
      Field('billDate', _$billDate, opt: true);
  static List<BillDetails>? _$billDetails(BillModel v) => v.billDetails;
  static const Field<BillModel, List<BillDetails>> _f$billDetails =
      Field('billDetails', _$billDetails, opt: true);
  static String? _$businessService(BillModel v) => v.businessService;
  static const Field<BillModel, String> _f$businessService =
      Field('businessService', _$businessService, opt: true);
  static int? _$fromPeriod(BillModel v) => v.fromPeriod;
  static const Field<BillModel, int> _f$fromPeriod =
      Field('fromPeriod', _$fromPeriod, opt: true);
  static num? _$netPaidAmount(BillModel v) => v.netPaidAmount;
  static const Field<BillModel, num> _f$netPaidAmount =
      Field('netPaidAmount', _$netPaidAmount, opt: true);
  static num? _$netPayableAmount(BillModel v) => v.netPayableAmount;
  static const Field<BillModel, num> _f$netPayableAmount =
      Field('netPayableAmount', _$netPayableAmount, opt: true);
  static Payer? _$payer(BillModel v) => v.payer;
  static const Field<BillModel, Payer> _f$payer =
      Field('payer', _$payer, opt: true);
  static int? _$toPeriod(BillModel v) => v.toPeriod;
  static const Field<BillModel, int> _f$toPeriod =
      Field('toPeriod', _$toPeriod, opt: true);
  static String? _$billNumber(BillModel v) => v.billNumber;
  static const Field<BillModel, String> _f$billNumber =
      Field('billNumber', _$billNumber, opt: true);
  static String? _$wfStatus(BillModel v) => v.wfStatus;
  static const Field<BillModel, String> _f$wfStatus =
      Field('wfStatus', _$wfStatus, opt: true);
  static BillAdditionalDetails? _$additionalDetails(BillModel v) =>
      v.additionalDetails;
  static const Field<BillModel, BillAdditionalDetails> _f$additionalDetails =
      Field('additionalDetails', _$additionalDetails, opt: true);
  static num? _$totalAmount(BillModel v) => v.totalAmount;
  static const Field<BillModel, num> _f$totalAmount =
      Field('totalAmount', _$totalAmount, opt: true);
  static num? _$totalPaidAmount(BillModel v) => v.totalPaidAmount;
  static const Field<BillModel, num> _f$totalPaidAmount =
      Field('totalPaidAmount', _$totalPaidAmount, opt: true);

  @override
  final MappableFields<BillModel> fields = const {
    #id: _f$id,
    #tenantId: _f$tenantId,
    #referenceId: _f$referenceId,
    #status: _f$status,
    #dueDate: _f$dueDate,
    #auditDetails: _f$auditDetails,
    #paymentStatus: _f$paymentStatus,
    #billDate: _f$billDate,
    #billDetails: _f$billDetails,
    #businessService: _f$businessService,
    #fromPeriod: _f$fromPeriod,
    #netPaidAmount: _f$netPaidAmount,
    #netPayableAmount: _f$netPayableAmount,
    #payer: _f$payer,
    #toPeriod: _f$toPeriod,
    #billNumber: _f$billNumber,
    #wfStatus: _f$wfStatus,
    #additionalDetails: _f$additionalDetails,
    #totalAmount: _f$totalAmount,
    #totalPaidAmount: _f$totalPaidAmount,
  };

  static BillModel _instantiate(DecodingData data) {
    return BillModel(
        id: data.dec(_f$id),
        tenantId: data.dec(_f$tenantId),
        referenceId: data.dec(_f$referenceId),
        status: data.dec(_f$status),
        dueDate: data.dec(_f$dueDate),
        auditDetails: data.dec(_f$auditDetails),
        paymentStatus: data.dec(_f$paymentStatus),
        billDate: data.dec(_f$billDate),
        billDetails: data.dec(_f$billDetails),
        businessService: data.dec(_f$businessService),
        fromPeriod: data.dec(_f$fromPeriod),
        netPaidAmount: data.dec(_f$netPaidAmount),
        netPayableAmount: data.dec(_f$netPayableAmount),
        payer: data.dec(_f$payer),
        toPeriod: data.dec(_f$toPeriod),
        billNumber: data.dec(_f$billNumber),
        wfStatus: data.dec(_f$wfStatus),
        additionalDetails: data.dec(_f$additionalDetails),
        totalAmount: data.dec(_f$totalAmount),
        totalPaidAmount: data.dec(_f$totalPaidAmount));
  }

  @override
  final Function instantiate = _instantiate;

  static BillModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BillModel>(map);
  }

  static BillModel fromJson(String json) {
    return ensureInitialized().decodeJson<BillModel>(json);
  }
}

mixin BillModelMappable {
  String toJson() {
    return BillModelMapper.ensureInitialized()
        .encodeJson<BillModel>(this as BillModel);
  }

  Map<String, dynamic> toMap() {
    return BillModelMapper.ensureInitialized()
        .encodeMap<BillModel>(this as BillModel);
  }

  BillModelCopyWith<BillModel, BillModel, BillModel> get copyWith =>
      _BillModelCopyWithImpl(this as BillModel, $identity, $identity);
  @override
  String toString() {
    return BillModelMapper.ensureInitialized()
        .stringifyValue(this as BillModel);
  }

  @override
  bool operator ==(Object other) {
    return BillModelMapper.ensureInitialized()
        .equalsValue(this as BillModel, other);
  }

  @override
  int get hashCode {
    return BillModelMapper.ensureInitialized().hashValue(this as BillModel);
  }
}

extension BillModelValueCopy<$R, $Out> on ObjectCopyWith<$R, BillModel, $Out> {
  BillModelCopyWith<$R, BillModel, $Out> get $asBillModel =>
      $base.as((v, t, t2) => _BillModelCopyWithImpl(v, t, t2));
}

abstract class BillModelCopyWith<$R, $In extends BillModel, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
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
  BillModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _BillModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BillModel, $Out>
    implements BillModelCopyWith<$R, BillModel, $Out> {
  _BillModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BillModel> $mapper =
      BillModelMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, BillDetails,
          BillDetailsCopyWith<$R, BillDetails, BillDetails>>?
      get billDetails => $value.billDetails != null
          ? ListCopyWith($value.billDetails!, (v, t) => v.copyWith.$chain(t),
              (v) => call(billDetails: v))
          : null;
  @override
  PayerCopyWith<$R, Payer, Payer>? get payer =>
      $value.payer?.copyWith.$chain((v) => call(payer: v));
  @override
  BillAdditionalDetailsCopyWith<$R, BillAdditionalDetails,
          BillAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .$chain((v) => call(additionalDetails: v));
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
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (tenantId != null) #tenantId: tenantId,
        if (referenceId != $none) #referenceId: referenceId,
        if (status != $none) #status: status,
        if (dueDate != $none) #dueDate: dueDate,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (paymentStatus != $none) #paymentStatus: paymentStatus,
        if (billDate != $none) #billDate: billDate,
        if (billDetails != $none) #billDetails: billDetails,
        if (businessService != $none) #businessService: businessService,
        if (fromPeriod != $none) #fromPeriod: fromPeriod,
        if (netPaidAmount != $none) #netPaidAmount: netPaidAmount,
        if (netPayableAmount != $none) #netPayableAmount: netPayableAmount,
        if (payer != $none) #payer: payer,
        if (toPeriod != $none) #toPeriod: toPeriod,
        if (billNumber != $none) #billNumber: billNumber,
        if (wfStatus != $none) #wfStatus: wfStatus,
        if (additionalDetails != $none) #additionalDetails: additionalDetails,
        if (totalAmount != $none) #totalAmount: totalAmount,
        if (totalPaidAmount != $none) #totalPaidAmount: totalPaidAmount
      }));
  @override
  BillModel $make(CopyWithData data) => BillModel(
      id: data.get(#id, or: $value.id),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      referenceId: data.get(#referenceId, or: $value.referenceId),
      status: data.get(#status, or: $value.status),
      dueDate: data.get(#dueDate, or: $value.dueDate),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      paymentStatus: data.get(#paymentStatus, or: $value.paymentStatus),
      billDate: data.get(#billDate, or: $value.billDate),
      billDetails: data.get(#billDetails, or: $value.billDetails),
      businessService: data.get(#businessService, or: $value.businessService),
      fromPeriod: data.get(#fromPeriod, or: $value.fromPeriod),
      netPaidAmount: data.get(#netPaidAmount, or: $value.netPaidAmount),
      netPayableAmount:
          data.get(#netPayableAmount, or: $value.netPayableAmount),
      payer: data.get(#payer, or: $value.payer),
      toPeriod: data.get(#toPeriod, or: $value.toPeriod),
      billNumber: data.get(#billNumber, or: $value.billNumber),
      wfStatus: data.get(#wfStatus, or: $value.wfStatus),
      additionalDetails:
          data.get(#additionalDetails, or: $value.additionalDetails),
      totalAmount: data.get(#totalAmount, or: $value.totalAmount),
      totalPaidAmount: data.get(#totalPaidAmount, or: $value.totalPaidAmount));

  @override
  BillModelCopyWith<$R2, BillModel, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _BillModelCopyWithImpl($value, $cast, t);
}

class BillDetailsMapper extends ClassMapperBase<BillDetails> {
  BillDetailsMapper._();

  static BillDetailsMapper? _instance;
  static BillDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BillDetailsMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
      BillLineItemsMapper.ensureInitialized();
      PayableLineItemsMapper.ensureInitialized();
      PayeeMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'BillDetails';

  static String? _$id(BillDetails v) => v.id;
  static const Field<BillDetails, String> _f$id = Field('id', _$id, opt: true);
  static String? _$referenceId(BillDetails v) => v.referenceId;
  static const Field<BillDetails, String> _f$referenceId =
      Field('referenceId', _$referenceId, opt: true);
  static String? _$billId(BillDetails v) => v.billId;
  static const Field<BillDetails, String> _f$billId =
      Field('billId', _$billId, opt: true);
  static ContractAuditDetails? _$auditDetails(BillDetails v) => v.auditDetails;
  static const Field<BillDetails, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static int? _$toPeriod(BillDetails v) => v.toPeriod;
  static const Field<BillDetails, int> _f$toPeriod =
      Field('toPeriod', _$toPeriod, opt: true);
  static int? _$fromPeriod(BillDetails v) => v.fromPeriod;
  static const Field<BillDetails, int> _f$fromPeriod =
      Field('fromPeriod', _$fromPeriod, opt: true);
  static String? _$paymentStatus(BillDetails v) => v.paymentStatus;
  static const Field<BillDetails, String> _f$paymentStatus =
      Field('paymentStatus', _$paymentStatus, opt: true);
  static List<BillLineItems>? _$lineItems(BillDetails v) => v.lineItems;
  static const Field<BillDetails, List<BillLineItems>> _f$lineItems =
      Field('lineItems', _$lineItems, opt: true);
  static List<PayableLineItems>? _$payableLineItems(BillDetails v) =>
      v.payableLineItems;
  static const Field<BillDetails, List<PayableLineItems>> _f$payableLineItems =
      Field('payableLineItems', _$payableLineItems, opt: true);
  static Payee? _$payee(BillDetails v) => v.payee;
  static const Field<BillDetails, Payee> _f$payee =
      Field('payee', _$payee, opt: true);

  @override
  final MappableFields<BillDetails> fields = const {
    #id: _f$id,
    #referenceId: _f$referenceId,
    #billId: _f$billId,
    #auditDetails: _f$auditDetails,
    #toPeriod: _f$toPeriod,
    #fromPeriod: _f$fromPeriod,
    #paymentStatus: _f$paymentStatus,
    #lineItems: _f$lineItems,
    #payableLineItems: _f$payableLineItems,
    #payee: _f$payee,
  };

  static BillDetails _instantiate(DecodingData data) {
    return BillDetails(
        id: data.dec(_f$id),
        referenceId: data.dec(_f$referenceId),
        billId: data.dec(_f$billId),
        auditDetails: data.dec(_f$auditDetails),
        toPeriod: data.dec(_f$toPeriod),
        fromPeriod: data.dec(_f$fromPeriod),
        paymentStatus: data.dec(_f$paymentStatus),
        lineItems: data.dec(_f$lineItems),
        payableLineItems: data.dec(_f$payableLineItems),
        payee: data.dec(_f$payee));
  }

  @override
  final Function instantiate = _instantiate;

  static BillDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BillDetails>(map);
  }

  static BillDetails fromJson(String json) {
    return ensureInitialized().decodeJson<BillDetails>(json);
  }
}

mixin BillDetailsMappable {
  String toJson() {
    return BillDetailsMapper.ensureInitialized()
        .encodeJson<BillDetails>(this as BillDetails);
  }

  Map<String, dynamic> toMap() {
    return BillDetailsMapper.ensureInitialized()
        .encodeMap<BillDetails>(this as BillDetails);
  }

  BillDetailsCopyWith<BillDetails, BillDetails, BillDetails> get copyWith =>
      _BillDetailsCopyWithImpl(this as BillDetails, $identity, $identity);
  @override
  String toString() {
    return BillDetailsMapper.ensureInitialized()
        .stringifyValue(this as BillDetails);
  }

  @override
  bool operator ==(Object other) {
    return BillDetailsMapper.ensureInitialized()
        .equalsValue(this as BillDetails, other);
  }

  @override
  int get hashCode {
    return BillDetailsMapper.ensureInitialized().hashValue(this as BillDetails);
  }
}

extension BillDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BillDetails, $Out> {
  BillDetailsCopyWith<$R, BillDetails, $Out> get $asBillDetails =>
      $base.as((v, t, t2) => _BillDetailsCopyWithImpl(v, t, t2));
}

abstract class BillDetailsCopyWith<$R, $In extends BillDetails, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
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
  BillDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _BillDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BillDetails, $Out>
    implements BillDetailsCopyWith<$R, BillDetails, $Out> {
  _BillDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BillDetails> $mapper =
      BillDetailsMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, BillLineItems,
          BillLineItemsCopyWith<$R, BillLineItems, BillLineItems>>?
      get lineItems => $value.lineItems != null
          ? ListCopyWith($value.lineItems!, (v, t) => v.copyWith.$chain(t),
              (v) => call(lineItems: v))
          : null;
  @override
  ListCopyWith<$R, PayableLineItems,
          PayableLineItemsCopyWith<$R, PayableLineItems, PayableLineItems>>?
      get payableLineItems => $value.payableLineItems != null
          ? ListCopyWith($value.payableLineItems!,
              (v, t) => v.copyWith.$chain(t), (v) => call(payableLineItems: v))
          : null;
  @override
  PayeeCopyWith<$R, Payee, Payee>? get payee =>
      $value.payee?.copyWith.$chain((v) => call(payee: v));
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
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (referenceId != $none) #referenceId: referenceId,
        if (billId != $none) #billId: billId,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (toPeriod != $none) #toPeriod: toPeriod,
        if (fromPeriod != $none) #fromPeriod: fromPeriod,
        if (paymentStatus != $none) #paymentStatus: paymentStatus,
        if (lineItems != $none) #lineItems: lineItems,
        if (payableLineItems != $none) #payableLineItems: payableLineItems,
        if (payee != $none) #payee: payee
      }));
  @override
  BillDetails $make(CopyWithData data) => BillDetails(
      id: data.get(#id, or: $value.id),
      referenceId: data.get(#referenceId, or: $value.referenceId),
      billId: data.get(#billId, or: $value.billId),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      toPeriod: data.get(#toPeriod, or: $value.toPeriod),
      fromPeriod: data.get(#fromPeriod, or: $value.fromPeriod),
      paymentStatus: data.get(#paymentStatus, or: $value.paymentStatus),
      lineItems: data.get(#lineItems, or: $value.lineItems),
      payableLineItems:
          data.get(#payableLineItems, or: $value.payableLineItems),
      payee: data.get(#payee, or: $value.payee));

  @override
  BillDetailsCopyWith<$R2, BillDetails, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _BillDetailsCopyWithImpl($value, $cast, t);
}

class BillLineItemsMapper extends ClassMapperBase<BillLineItems> {
  BillLineItemsMapper._();

  static BillLineItemsMapper? _instance;
  static BillLineItemsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BillLineItemsMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'BillLineItems';

  static String? _$id(BillLineItems v) => v.id;
  static const Field<BillLineItems, String> _f$id =
      Field('id', _$id, opt: true);
  static ContractAuditDetails? _$auditDetails(BillLineItems v) =>
      v.auditDetails;
  static const Field<BillLineItems, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String _$tenantId(BillLineItems v) => v.tenantId;
  static const Field<BillLineItems, String> _f$tenantId =
      Field('tenantId', _$tenantId);
  static num? _$amount(BillLineItems v) => v.amount;
  static const Field<BillLineItems, num> _f$amount =
      Field('amount', _$amount, opt: true);
  static String? _$type(BillLineItems v) => v.type;
  static const Field<BillLineItems, String> _f$type =
      Field('type', _$type, opt: true);
  static String? _$status(BillLineItems v) => v.status;
  static const Field<BillLineItems, String> _f$status =
      Field('status', _$status, opt: true);
  static String? _$billDetailId(BillLineItems v) => v.billDetailId;
  static const Field<BillLineItems, String> _f$billDetailId =
      Field('billDetailId', _$billDetailId, opt: true);
  static String? _$headCode(BillLineItems v) => v.headCode;
  static const Field<BillLineItems, String> _f$headCode =
      Field('headCode', _$headCode, opt: true);
  static bool? _$isLineItemPayable(BillLineItems v) => v.isLineItemPayable;
  static const Field<BillLineItems, bool> _f$isLineItemPayable =
      Field('isLineItemPayable', _$isLineItemPayable, opt: true);
  static num? _$paidAmount(BillLineItems v) => v.paidAmount;
  static const Field<BillLineItems, num> _f$paidAmount =
      Field('paidAmount', _$paidAmount, opt: true);

  @override
  final MappableFields<BillLineItems> fields = const {
    #id: _f$id,
    #auditDetails: _f$auditDetails,
    #tenantId: _f$tenantId,
    #amount: _f$amount,
    #type: _f$type,
    #status: _f$status,
    #billDetailId: _f$billDetailId,
    #headCode: _f$headCode,
    #isLineItemPayable: _f$isLineItemPayable,
    #paidAmount: _f$paidAmount,
  };

  static BillLineItems _instantiate(DecodingData data) {
    return BillLineItems(
        id: data.dec(_f$id),
        auditDetails: data.dec(_f$auditDetails),
        tenantId: data.dec(_f$tenantId),
        amount: data.dec(_f$amount),
        type: data.dec(_f$type),
        status: data.dec(_f$status),
        billDetailId: data.dec(_f$billDetailId),
        headCode: data.dec(_f$headCode),
        isLineItemPayable: data.dec(_f$isLineItemPayable),
        paidAmount: data.dec(_f$paidAmount));
  }

  @override
  final Function instantiate = _instantiate;

  static BillLineItems fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BillLineItems>(map);
  }

  static BillLineItems fromJson(String json) {
    return ensureInitialized().decodeJson<BillLineItems>(json);
  }
}

mixin BillLineItemsMappable {
  String toJson() {
    return BillLineItemsMapper.ensureInitialized()
        .encodeJson<BillLineItems>(this as BillLineItems);
  }

  Map<String, dynamic> toMap() {
    return BillLineItemsMapper.ensureInitialized()
        .encodeMap<BillLineItems>(this as BillLineItems);
  }

  BillLineItemsCopyWith<BillLineItems, BillLineItems, BillLineItems>
      get copyWith => _BillLineItemsCopyWithImpl(
          this as BillLineItems, $identity, $identity);
  @override
  String toString() {
    return BillLineItemsMapper.ensureInitialized()
        .stringifyValue(this as BillLineItems);
  }

  @override
  bool operator ==(Object other) {
    return BillLineItemsMapper.ensureInitialized()
        .equalsValue(this as BillLineItems, other);
  }

  @override
  int get hashCode {
    return BillLineItemsMapper.ensureInitialized()
        .hashValue(this as BillLineItems);
  }
}

extension BillLineItemsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BillLineItems, $Out> {
  BillLineItemsCopyWith<$R, BillLineItems, $Out> get $asBillLineItems =>
      $base.as((v, t, t2) => _BillLineItemsCopyWithImpl(v, t, t2));
}

abstract class BillLineItemsCopyWith<$R, $In extends BillLineItems, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
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
  BillLineItemsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _BillLineItemsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BillLineItems, $Out>
    implements BillLineItemsCopyWith<$R, BillLineItems, $Out> {
  _BillLineItemsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BillLineItems> $mapper =
      BillLineItemsMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
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
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (tenantId != null) #tenantId: tenantId,
        if (amount != $none) #amount: amount,
        if (type != $none) #type: type,
        if (status != $none) #status: status,
        if (billDetailId != $none) #billDetailId: billDetailId,
        if (headCode != $none) #headCode: headCode,
        if (isLineItemPayable != $none) #isLineItemPayable: isLineItemPayable,
        if (paidAmount != $none) #paidAmount: paidAmount
      }));
  @override
  BillLineItems $make(CopyWithData data) => BillLineItems(
      id: data.get(#id, or: $value.id),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      amount: data.get(#amount, or: $value.amount),
      type: data.get(#type, or: $value.type),
      status: data.get(#status, or: $value.status),
      billDetailId: data.get(#billDetailId, or: $value.billDetailId),
      headCode: data.get(#headCode, or: $value.headCode),
      isLineItemPayable:
          data.get(#isLineItemPayable, or: $value.isLineItemPayable),
      paidAmount: data.get(#paidAmount, or: $value.paidAmount));

  @override
  BillLineItemsCopyWith<$R2, BillLineItems, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _BillLineItemsCopyWithImpl($value, $cast, t);
}

class PayableLineItemsMapper extends ClassMapperBase<PayableLineItems> {
  PayableLineItemsMapper._();

  static PayableLineItemsMapper? _instance;
  static PayableLineItemsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = PayableLineItemsMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'PayableLineItems';

  static String? _$id(PayableLineItems v) => v.id;
  static const Field<PayableLineItems, String> _f$id =
      Field('id', _$id, opt: true);
  static ContractAuditDetails? _$auditDetails(PayableLineItems v) =>
      v.auditDetails;
  static const Field<PayableLineItems, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String _$tenantId(PayableLineItems v) => v.tenantId;
  static const Field<PayableLineItems, String> _f$tenantId =
      Field('tenantId', _$tenantId);
  static num? _$amount(PayableLineItems v) => v.amount;
  static const Field<PayableLineItems, num> _f$amount =
      Field('amount', _$amount, opt: true);
  static String? _$type(PayableLineItems v) => v.type;
  static const Field<PayableLineItems, String> _f$type =
      Field('type', _$type, opt: true);
  static String? _$status(PayableLineItems v) => v.status;
  static const Field<PayableLineItems, String> _f$status =
      Field('status', _$status, opt: true);
  static String? _$billDetailId(PayableLineItems v) => v.billDetailId;
  static const Field<PayableLineItems, String> _f$billDetailId =
      Field('billDetailId', _$billDetailId, opt: true);
  static String? _$headCode(PayableLineItems v) => v.headCode;
  static const Field<PayableLineItems, String> _f$headCode =
      Field('headCode', _$headCode, opt: true);
  static bool? _$isLineItemPayable(PayableLineItems v) => v.isLineItemPayable;
  static const Field<PayableLineItems, bool> _f$isLineItemPayable =
      Field('isLineItemPayable', _$isLineItemPayable, opt: true);
  static num? _$paidAmount(PayableLineItems v) => v.paidAmount;
  static const Field<PayableLineItems, num> _f$paidAmount =
      Field('paidAmount', _$paidAmount, opt: true);

  @override
  final MappableFields<PayableLineItems> fields = const {
    #id: _f$id,
    #auditDetails: _f$auditDetails,
    #tenantId: _f$tenantId,
    #amount: _f$amount,
    #type: _f$type,
    #status: _f$status,
    #billDetailId: _f$billDetailId,
    #headCode: _f$headCode,
    #isLineItemPayable: _f$isLineItemPayable,
    #paidAmount: _f$paidAmount,
  };

  static PayableLineItems _instantiate(DecodingData data) {
    return PayableLineItems(
        id: data.dec(_f$id),
        auditDetails: data.dec(_f$auditDetails),
        tenantId: data.dec(_f$tenantId),
        amount: data.dec(_f$amount),
        type: data.dec(_f$type),
        status: data.dec(_f$status),
        billDetailId: data.dec(_f$billDetailId),
        headCode: data.dec(_f$headCode),
        isLineItemPayable: data.dec(_f$isLineItemPayable),
        paidAmount: data.dec(_f$paidAmount));
  }

  @override
  final Function instantiate = _instantiate;

  static PayableLineItems fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<PayableLineItems>(map);
  }

  static PayableLineItems fromJson(String json) {
    return ensureInitialized().decodeJson<PayableLineItems>(json);
  }
}

mixin PayableLineItemsMappable {
  String toJson() {
    return PayableLineItemsMapper.ensureInitialized()
        .encodeJson<PayableLineItems>(this as PayableLineItems);
  }

  Map<String, dynamic> toMap() {
    return PayableLineItemsMapper.ensureInitialized()
        .encodeMap<PayableLineItems>(this as PayableLineItems);
  }

  PayableLineItemsCopyWith<PayableLineItems, PayableLineItems, PayableLineItems>
      get copyWith => _PayableLineItemsCopyWithImpl(
          this as PayableLineItems, $identity, $identity);
  @override
  String toString() {
    return PayableLineItemsMapper.ensureInitialized()
        .stringifyValue(this as PayableLineItems);
  }

  @override
  bool operator ==(Object other) {
    return PayableLineItemsMapper.ensureInitialized()
        .equalsValue(this as PayableLineItems, other);
  }

  @override
  int get hashCode {
    return PayableLineItemsMapper.ensureInitialized()
        .hashValue(this as PayableLineItems);
  }
}

extension PayableLineItemsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, PayableLineItems, $Out> {
  PayableLineItemsCopyWith<$R, PayableLineItems, $Out>
      get $asPayableLineItems =>
          $base.as((v, t, t2) => _PayableLineItemsCopyWithImpl(v, t, t2));
}

abstract class PayableLineItemsCopyWith<$R, $In extends PayableLineItems, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
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
  PayableLineItemsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _PayableLineItemsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, PayableLineItems, $Out>
    implements PayableLineItemsCopyWith<$R, PayableLineItems, $Out> {
  _PayableLineItemsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<PayableLineItems> $mapper =
      PayableLineItemsMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
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
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (tenantId != null) #tenantId: tenantId,
        if (amount != $none) #amount: amount,
        if (type != $none) #type: type,
        if (status != $none) #status: status,
        if (billDetailId != $none) #billDetailId: billDetailId,
        if (headCode != $none) #headCode: headCode,
        if (isLineItemPayable != $none) #isLineItemPayable: isLineItemPayable,
        if (paidAmount != $none) #paidAmount: paidAmount
      }));
  @override
  PayableLineItems $make(CopyWithData data) => PayableLineItems(
      id: data.get(#id, or: $value.id),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      amount: data.get(#amount, or: $value.amount),
      type: data.get(#type, or: $value.type),
      status: data.get(#status, or: $value.status),
      billDetailId: data.get(#billDetailId, or: $value.billDetailId),
      headCode: data.get(#headCode, or: $value.headCode),
      isLineItemPayable:
          data.get(#isLineItemPayable, or: $value.isLineItemPayable),
      paidAmount: data.get(#paidAmount, or: $value.paidAmount));

  @override
  PayableLineItemsCopyWith<$R2, PayableLineItems, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _PayableLineItemsCopyWithImpl($value, $cast, t);
}

class PayeeMapper extends ClassMapperBase<Payee> {
  PayeeMapper._();

  static PayeeMapper? _instance;
  static PayeeMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = PayeeMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'Payee';

  static String? _$id(Payee v) => v.id;
  static const Field<Payee, String> _f$id = Field('id', _$id, opt: true);
  static ContractAuditDetails? _$auditDetails(Payee v) => v.auditDetails;
  static const Field<Payee, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String? _$parentId(Payee v) => v.parentId;
  static const Field<Payee, String> _f$parentId =
      Field('parentId', _$parentId, opt: true);
  static String? _$identifier(Payee v) => v.identifier;
  static const Field<Payee, String> _f$identifier =
      Field('identifier', _$identifier, opt: true);
  static String? _$type(Payee v) => v.type;
  static const Field<Payee, String> _f$type = Field('type', _$type, opt: true);
  static String? _$status(Payee v) => v.status;
  static const Field<Payee, String> _f$status =
      Field('status', _$status, opt: true);

  @override
  final MappableFields<Payee> fields = const {
    #id: _f$id,
    #auditDetails: _f$auditDetails,
    #parentId: _f$parentId,
    #identifier: _f$identifier,
    #type: _f$type,
    #status: _f$status,
  };

  static Payee _instantiate(DecodingData data) {
    return Payee(
        id: data.dec(_f$id),
        auditDetails: data.dec(_f$auditDetails),
        parentId: data.dec(_f$parentId),
        identifier: data.dec(_f$identifier),
        type: data.dec(_f$type),
        status: data.dec(_f$status));
  }

  @override
  final Function instantiate = _instantiate;

  static Payee fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<Payee>(map);
  }

  static Payee fromJson(String json) {
    return ensureInitialized().decodeJson<Payee>(json);
  }
}

mixin PayeeMappable {
  String toJson() {
    return PayeeMapper.ensureInitialized().encodeJson<Payee>(this as Payee);
  }

  Map<String, dynamic> toMap() {
    return PayeeMapper.ensureInitialized().encodeMap<Payee>(this as Payee);
  }

  PayeeCopyWith<Payee, Payee, Payee> get copyWith =>
      _PayeeCopyWithImpl(this as Payee, $identity, $identity);
  @override
  String toString() {
    return PayeeMapper.ensureInitialized().stringifyValue(this as Payee);
  }

  @override
  bool operator ==(Object other) {
    return PayeeMapper.ensureInitialized().equalsValue(this as Payee, other);
  }

  @override
  int get hashCode {
    return PayeeMapper.ensureInitialized().hashValue(this as Payee);
  }
}

extension PayeeValueCopy<$R, $Out> on ObjectCopyWith<$R, Payee, $Out> {
  PayeeCopyWith<$R, Payee, $Out> get $asPayee =>
      $base.as((v, t, t2) => _PayeeCopyWithImpl(v, t, t2));
}

abstract class PayeeCopyWith<$R, $In extends Payee, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      ContractAuditDetails? auditDetails,
      String? parentId,
      String? identifier,
      String? type,
      String? status});
  PayeeCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _PayeeCopyWithImpl<$R, $Out> extends ClassCopyWithBase<$R, Payee, $Out>
    implements PayeeCopyWith<$R, Payee, $Out> {
  _PayeeCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<Payee> $mapper = PayeeMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? auditDetails = $none,
          Object? parentId = $none,
          Object? identifier = $none,
          Object? type = $none,
          Object? status = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (parentId != $none) #parentId: parentId,
        if (identifier != $none) #identifier: identifier,
        if (type != $none) #type: type,
        if (status != $none) #status: status
      }));
  @override
  Payee $make(CopyWithData data) => Payee(
      id: data.get(#id, or: $value.id),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      parentId: data.get(#parentId, or: $value.parentId),
      identifier: data.get(#identifier, or: $value.identifier),
      type: data.get(#type, or: $value.type),
      status: data.get(#status, or: $value.status));

  @override
  PayeeCopyWith<$R2, Payee, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
      _PayeeCopyWithImpl($value, $cast, t);
}

class PayerMapper extends ClassMapperBase<Payer> {
  PayerMapper._();

  static PayerMapper? _instance;
  static PayerMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = PayerMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'Payer';

  static String? _$id(Payer v) => v.id;
  static const Field<Payer, String> _f$id = Field('id', _$id, opt: true);
  static String? _$status(Payer v) => v.status;
  static const Field<Payer, String> _f$status =
      Field('status', _$status, opt: true);
  static ContractAuditDetails? _$auditDetails(Payer v) => v.auditDetails;
  static const Field<Payer, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String? _$type(Payer v) => v.type;
  static const Field<Payer, String> _f$type = Field('type', _$type, opt: true);
  static String? _$identifier(Payer v) => v.identifier;
  static const Field<Payer, String> _f$identifier =
      Field('identifier', _$identifier, opt: true);
  static String? _$parentId(Payer v) => v.parentId;
  static const Field<Payer, String> _f$parentId =
      Field('parentId', _$parentId, opt: true);

  @override
  final MappableFields<Payer> fields = const {
    #id: _f$id,
    #status: _f$status,
    #auditDetails: _f$auditDetails,
    #type: _f$type,
    #identifier: _f$identifier,
    #parentId: _f$parentId,
  };

  static Payer _instantiate(DecodingData data) {
    return Payer(
        id: data.dec(_f$id),
        status: data.dec(_f$status),
        auditDetails: data.dec(_f$auditDetails),
        type: data.dec(_f$type),
        identifier: data.dec(_f$identifier),
        parentId: data.dec(_f$parentId));
  }

  @override
  final Function instantiate = _instantiate;

  static Payer fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<Payer>(map);
  }

  static Payer fromJson(String json) {
    return ensureInitialized().decodeJson<Payer>(json);
  }
}

mixin PayerMappable {
  String toJson() {
    return PayerMapper.ensureInitialized().encodeJson<Payer>(this as Payer);
  }

  Map<String, dynamic> toMap() {
    return PayerMapper.ensureInitialized().encodeMap<Payer>(this as Payer);
  }

  PayerCopyWith<Payer, Payer, Payer> get copyWith =>
      _PayerCopyWithImpl(this as Payer, $identity, $identity);
  @override
  String toString() {
    return PayerMapper.ensureInitialized().stringifyValue(this as Payer);
  }

  @override
  bool operator ==(Object other) {
    return PayerMapper.ensureInitialized().equalsValue(this as Payer, other);
  }

  @override
  int get hashCode {
    return PayerMapper.ensureInitialized().hashValue(this as Payer);
  }
}

extension PayerValueCopy<$R, $Out> on ObjectCopyWith<$R, Payer, $Out> {
  PayerCopyWith<$R, Payer, $Out> get $asPayer =>
      $base.as((v, t, t2) => _PayerCopyWithImpl(v, t, t2));
}

abstract class PayerCopyWith<$R, $In extends Payer, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      String? status,
      ContractAuditDetails? auditDetails,
      String? type,
      String? identifier,
      String? parentId});
  PayerCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _PayerCopyWithImpl<$R, $Out> extends ClassCopyWithBase<$R, Payer, $Out>
    implements PayerCopyWith<$R, Payer, $Out> {
  _PayerCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<Payer> $mapper = PayerMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? status = $none,
          Object? auditDetails = $none,
          Object? type = $none,
          Object? identifier = $none,
          Object? parentId = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (status != $none) #status: status,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (type != $none) #type: type,
        if (identifier != $none) #identifier: identifier,
        if (parentId != $none) #parentId: parentId
      }));
  @override
  Payer $make(CopyWithData data) => Payer(
      id: data.get(#id, or: $value.id),
      status: data.get(#status, or: $value.status),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      type: data.get(#type, or: $value.type),
      identifier: data.get(#identifier, or: $value.identifier),
      parentId: data.get(#parentId, or: $value.parentId));

  @override
  PayerCopyWith<$R2, Payer, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
      _PayerCopyWithImpl($value, $cast, t);
}

class BillAdditionalDetailsMapper
    extends ClassMapperBase<BillAdditionalDetails> {
  BillAdditionalDetailsMapper._();

  static BillAdditionalDetailsMapper? _instance;
  static BillAdditionalDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = BillAdditionalDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'BillAdditionalDetails';

  static String? _$invoiceNumber(BillAdditionalDetails v) => v.invoiceNumber;
  static const Field<BillAdditionalDetails, String> _f$invoiceNumber =
      Field('invoiceNumber', _$invoiceNumber, opt: true);
  static String? _$locality(BillAdditionalDetails v) => v.locality;
  static const Field<BillAdditionalDetails, String> _f$locality =
      Field('locality', _$locality, opt: true);
  static String? _$orgName(BillAdditionalDetails v) => v.orgName;
  static const Field<BillAdditionalDetails, String> _f$orgName =
      Field('orgName', _$orgName, opt: true);
  static String? _$projectDesc(BillAdditionalDetails v) => v.projectDesc;
  static const Field<BillAdditionalDetails, String> _f$projectDesc =
      Field('projectDesc', _$projectDesc, opt: true);
  static String? _$projectName(BillAdditionalDetails v) => v.projectName;
  static const Field<BillAdditionalDetails, String> _f$projectName =
      Field('projectName', _$projectName, opt: true);
  static String? _$projectId(BillAdditionalDetails v) => v.projectId;
  static const Field<BillAdditionalDetails, String> _f$projectId =
      Field('projectId', _$projectId, opt: true);
  static String? _$ward(BillAdditionalDetails v) => v.ward;
  static const Field<BillAdditionalDetails, String> _f$ward =
      Field('ward', _$ward, opt: true);
  static String? _$totalBillAmount(BillAdditionalDetails v) =>
      v.totalBillAmount;
  static const Field<BillAdditionalDetails, String> _f$totalBillAmount =
      Field('totalBillAmount', _$totalBillAmount, opt: true);
  static int? _$invoiceDate(BillAdditionalDetails v) => v.invoiceDate;
  static const Field<BillAdditionalDetails, int> _f$invoiceDate =
      Field('invoiceDate', _$invoiceDate, opt: true);

  @override
  final MappableFields<BillAdditionalDetails> fields = const {
    #invoiceNumber: _f$invoiceNumber,
    #locality: _f$locality,
    #orgName: _f$orgName,
    #projectDesc: _f$projectDesc,
    #projectName: _f$projectName,
    #projectId: _f$projectId,
    #ward: _f$ward,
    #totalBillAmount: _f$totalBillAmount,
    #invoiceDate: _f$invoiceDate,
  };

  static BillAdditionalDetails _instantiate(DecodingData data) {
    return BillAdditionalDetails(
        invoiceNumber: data.dec(_f$invoiceNumber),
        locality: data.dec(_f$locality),
        orgName: data.dec(_f$orgName),
        projectDesc: data.dec(_f$projectDesc),
        projectName: data.dec(_f$projectName),
        projectId: data.dec(_f$projectId),
        ward: data.dec(_f$ward),
        totalBillAmount: data.dec(_f$totalBillAmount),
        invoiceDate: data.dec(_f$invoiceDate));
  }

  @override
  final Function instantiate = _instantiate;

  static BillAdditionalDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<BillAdditionalDetails>(map);
  }

  static BillAdditionalDetails fromJson(String json) {
    return ensureInitialized().decodeJson<BillAdditionalDetails>(json);
  }
}

mixin BillAdditionalDetailsMappable {
  String toJson() {
    return BillAdditionalDetailsMapper.ensureInitialized()
        .encodeJson<BillAdditionalDetails>(this as BillAdditionalDetails);
  }

  Map<String, dynamic> toMap() {
    return BillAdditionalDetailsMapper.ensureInitialized()
        .encodeMap<BillAdditionalDetails>(this as BillAdditionalDetails);
  }

  BillAdditionalDetailsCopyWith<BillAdditionalDetails, BillAdditionalDetails,
          BillAdditionalDetails>
      get copyWith => _BillAdditionalDetailsCopyWithImpl(
          this as BillAdditionalDetails, $identity, $identity);
  @override
  String toString() {
    return BillAdditionalDetailsMapper.ensureInitialized()
        .stringifyValue(this as BillAdditionalDetails);
  }

  @override
  bool operator ==(Object other) {
    return BillAdditionalDetailsMapper.ensureInitialized()
        .equalsValue(this as BillAdditionalDetails, other);
  }

  @override
  int get hashCode {
    return BillAdditionalDetailsMapper.ensureInitialized()
        .hashValue(this as BillAdditionalDetails);
  }
}

extension BillAdditionalDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, BillAdditionalDetails, $Out> {
  BillAdditionalDetailsCopyWith<$R, BillAdditionalDetails, $Out>
      get $asBillAdditionalDetails =>
          $base.as((v, t, t2) => _BillAdditionalDetailsCopyWithImpl(v, t, t2));
}

abstract class BillAdditionalDetailsCopyWith<
    $R,
    $In extends BillAdditionalDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
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
  BillAdditionalDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _BillAdditionalDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, BillAdditionalDetails, $Out>
    implements BillAdditionalDetailsCopyWith<$R, BillAdditionalDetails, $Out> {
  _BillAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<BillAdditionalDetails> $mapper =
      BillAdditionalDetailsMapper.ensureInitialized();
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
      $apply(FieldCopyWithData({
        if (invoiceNumber != $none) #invoiceNumber: invoiceNumber,
        if (locality != $none) #locality: locality,
        if (orgName != $none) #orgName: orgName,
        if (projectDesc != $none) #projectDesc: projectDesc,
        if (projectName != $none) #projectName: projectName,
        if (projectId != $none) #projectId: projectId,
        if (ward != $none) #ward: ward,
        if (totalBillAmount != $none) #totalBillAmount: totalBillAmount,
        if (invoiceDate != $none) #invoiceDate: invoiceDate
      }));
  @override
  BillAdditionalDetails $make(CopyWithData data) => BillAdditionalDetails(
      invoiceNumber: data.get(#invoiceNumber, or: $value.invoiceNumber),
      locality: data.get(#locality, or: $value.locality),
      orgName: data.get(#orgName, or: $value.orgName),
      projectDesc: data.get(#projectDesc, or: $value.projectDesc),
      projectName: data.get(#projectName, or: $value.projectName),
      projectId: data.get(#projectId, or: $value.projectId),
      ward: data.get(#ward, or: $value.ward),
      totalBillAmount: data.get(#totalBillAmount, or: $value.totalBillAmount),
      invoiceDate: data.get(#invoiceDate, or: $value.invoiceDate));

  @override
  BillAdditionalDetailsCopyWith<$R2, BillAdditionalDetails, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _BillAdditionalDetailsCopyWithImpl($value, $cast, t);
}
