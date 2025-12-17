// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'contracts_model.dart';

class ContractsModelMapper extends ClassMapperBase<ContractsModel> {
  ContractsModelMapper._();

  static ContractsModelMapper? _instance;
  static ContractsModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = ContractsModelMapper._());
      ContractsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'ContractsModel';

  static List<Contracts>? _$contracts(ContractsModel v) => v.contracts;
  static const Field<ContractsModel, List<Contracts>> _f$contracts =
      Field('contracts', _$contracts, opt: true);

  @override
  final MappableFields<ContractsModel> fields = const {
    #contracts: _f$contracts,
  };

  static ContractsModel _instantiate(DecodingData data) {
    return ContractsModel(contracts: data.dec(_f$contracts));
  }

  @override
  final Function instantiate = _instantiate;

  static ContractsModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<ContractsModel>(map);
  }

  static ContractsModel fromJson(String json) {
    return ensureInitialized().decodeJson<ContractsModel>(json);
  }
}

mixin ContractsModelMappable {
  String toJson() {
    return ContractsModelMapper.ensureInitialized()
        .encodeJson<ContractsModel>(this as ContractsModel);
  }

  Map<String, dynamic> toMap() {
    return ContractsModelMapper.ensureInitialized()
        .encodeMap<ContractsModel>(this as ContractsModel);
  }

  ContractsModelCopyWith<ContractsModel, ContractsModel, ContractsModel>
      get copyWith => _ContractsModelCopyWithImpl(
          this as ContractsModel, $identity, $identity);
  @override
  String toString() {
    return ContractsModelMapper.ensureInitialized()
        .stringifyValue(this as ContractsModel);
  }

  @override
  bool operator ==(Object other) {
    return ContractsModelMapper.ensureInitialized()
        .equalsValue(this as ContractsModel, other);
  }

  @override
  int get hashCode {
    return ContractsModelMapper.ensureInitialized()
        .hashValue(this as ContractsModel);
  }
}

extension ContractsModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, ContractsModel, $Out> {
  ContractsModelCopyWith<$R, ContractsModel, $Out> get $asContractsModel =>
      $base.as((v, t, t2) => _ContractsModelCopyWithImpl(v, t, t2));
}

abstract class ContractsModelCopyWith<$R, $In extends ContractsModel, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, Contracts, ContractsCopyWith<$R, Contracts, Contracts>>?
      get contracts;
  $R call({List<Contracts>? contracts});
  ContractsModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _ContractsModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, ContractsModel, $Out>
    implements ContractsModelCopyWith<$R, ContractsModel, $Out> {
  _ContractsModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<ContractsModel> $mapper =
      ContractsModelMapper.ensureInitialized();
  @override
  ListCopyWith<$R, Contracts, ContractsCopyWith<$R, Contracts, Contracts>>?
      get contracts => $value.contracts != null
          ? ListCopyWith($value.contracts!, (v, t) => v.copyWith.$chain(t),
              (v) => call(contracts: v))
          : null;
  @override
  $R call({Object? contracts = $none}) => $apply(
      FieldCopyWithData({if (contracts != $none) #contracts: contracts}));
  @override
  ContractsModel $make(CopyWithData data) =>
      ContractsModel(contracts: data.get(#contracts, or: $value.contracts));

  @override
  ContractsModelCopyWith<$R2, ContractsModel, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _ContractsModelCopyWithImpl($value, $cast, t);
}

class ContractsMapper extends ClassMapperBase<Contracts> {
  ContractsMapper._();

  static ContractsMapper? _instance;
  static ContractsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = ContractsMapper._());
      LineItemsMapper.ensureInitialized();
      DocumentsMapper.ensureInitialized();
      ContractProcessInstanceMapper.ensureInitialized();
      ContractAdditionalDetailsMapper.ensureInitialized();
      ContractAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'Contracts';

  static String? _$id(Contracts v) => v.id;
  static const Field<Contracts, String> _f$id = Field('id', _$id, opt: true);
  static String? _$contractNumber(Contracts v) => v.contractNumber;
  static const Field<Contracts, String> _f$contractNumber =
      Field('contractNumber', _$contractNumber, opt: true);
  static String? _$tenantId(Contracts v) => v.tenantId;
  static const Field<Contracts, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static String? _$status(Contracts v) => v.status;
  static const Field<Contracts, String> _f$status =
      Field('status', _$status, opt: true);
  static int? _$endDate(Contracts v) => v.endDate;
  static const Field<Contracts, int> _f$endDate =
      Field('endDate', _$endDate, opt: true);
  static int? _$startDate(Contracts v) => v.startDate;
  static const Field<Contracts, int> _f$startDate =
      Field('startDate', _$startDate, opt: true);
  static int? _$agreementDate(Contracts v) => v.agreementDate;
  static const Field<Contracts, int> _f$agreementDate =
      Field('agreementDate', _$agreementDate, opt: true);
  static int? _$completionPeriod(Contracts v) => v.completionPeriod;
  static const Field<Contracts, int> _f$completionPeriod =
      Field('completionPeriod', _$completionPeriod, opt: true);
  static String? _$contractType(Contracts v) => v.contractType;
  static const Field<Contracts, String> _f$contractType =
      Field('contractType', _$contractType, opt: true);
  static int? _$defectLiabilityPeriod(Contracts v) => v.defectLiabilityPeriod;
  static const Field<Contracts, int> _f$defectLiabilityPeriod =
      Field('defectLiabilityPeriod', _$defectLiabilityPeriod, opt: true);
  static String? _$executingAuthority(Contracts v) => v.executingAuthority;
  static const Field<Contracts, String> _f$executingAuthority =
      Field('executingAuthority', _$executingAuthority, opt: true);
  static int? _$issueDate(Contracts v) => v.issueDate;
  static const Field<Contracts, int> _f$issueDate =
      Field('issueDate', _$issueDate, opt: true);
  static String? _$orgId(Contracts v) => v.orgId;
  static const Field<Contracts, String> _f$orgId =
      Field('orgId', _$orgId, opt: true);
  static double? _$securityDeposit(Contracts v) => v.securityDeposit;
  static const Field<Contracts, double> _f$securityDeposit =
      Field('securityDeposit', _$securityDeposit, opt: true);
  static String? _$wfStatus(Contracts v) => v.wfStatus;
  static const Field<Contracts, String> _f$wfStatus =
      Field('wfStatus', _$wfStatus, opt: true);
  static double? _$totalContractedAmount(Contracts v) =>
      v.totalContractedAmount;
  static const Field<Contracts, double> _f$totalContractedAmount =
      Field('totalContractedAmount', _$totalContractedAmount, opt: true);
  static List<LineItems>? _$lineItems(Contracts v) => v.lineItems;
  static const Field<Contracts, List<LineItems>> _f$lineItems =
      Field('lineItems', _$lineItems, opt: true);
  static List<Documents>? _$documents(Contracts v) => v.documents;
  static const Field<Contracts, List<Documents>> _f$documents =
      Field('documents', _$documents, opt: true);
  static ContractProcessInstance? _$processInstance(Contracts v) =>
      v.processInstance;
  static const Field<Contracts, ContractProcessInstance> _f$processInstance =
      Field('processInstance', _$processInstance, opt: true);
  static ContractAdditionalDetails? _$additionalDetails(Contracts v) =>
      v.additionalDetails;
  static const Field<Contracts, ContractAdditionalDetails>
      _f$additionalDetails =
      Field('additionalDetails', _$additionalDetails, opt: true);
  static ContractAuditDetails? _$auditDetails(Contracts v) => v.auditDetails;
  static const Field<Contracts, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String? _$businessService(Contracts v) => v.businessService;
  static const Field<Contracts, String> _f$businessService =
      Field('businessService', _$businessService, opt: true);
  static String? _$supplementNumber(Contracts v) => v.supplementNumber;
  static const Field<Contracts, String> _f$supplementNumber =
      Field('supplementNumber', _$supplementNumber, opt: true);

  @override
  final MappableFields<Contracts> fields = const {
    #id: _f$id,
    #contractNumber: _f$contractNumber,
    #tenantId: _f$tenantId,
    #status: _f$status,
    #endDate: _f$endDate,
    #startDate: _f$startDate,
    #agreementDate: _f$agreementDate,
    #completionPeriod: _f$completionPeriod,
    #contractType: _f$contractType,
    #defectLiabilityPeriod: _f$defectLiabilityPeriod,
    #executingAuthority: _f$executingAuthority,
    #issueDate: _f$issueDate,
    #orgId: _f$orgId,
    #securityDeposit: _f$securityDeposit,
    #wfStatus: _f$wfStatus,
    #totalContractedAmount: _f$totalContractedAmount,
    #lineItems: _f$lineItems,
    #documents: _f$documents,
    #processInstance: _f$processInstance,
    #additionalDetails: _f$additionalDetails,
    #auditDetails: _f$auditDetails,
    #businessService: _f$businessService,
    #supplementNumber: _f$supplementNumber,
  };

  static Contracts _instantiate(DecodingData data) {
    return Contracts(
        id: data.dec(_f$id),
        contractNumber: data.dec(_f$contractNumber),
        tenantId: data.dec(_f$tenantId),
        status: data.dec(_f$status),
        endDate: data.dec(_f$endDate),
        startDate: data.dec(_f$startDate),
        agreementDate: data.dec(_f$agreementDate),
        completionPeriod: data.dec(_f$completionPeriod),
        contractType: data.dec(_f$contractType),
        defectLiabilityPeriod: data.dec(_f$defectLiabilityPeriod),
        executingAuthority: data.dec(_f$executingAuthority),
        issueDate: data.dec(_f$issueDate),
        orgId: data.dec(_f$orgId),
        securityDeposit: data.dec(_f$securityDeposit),
        wfStatus: data.dec(_f$wfStatus),
        totalContractedAmount: data.dec(_f$totalContractedAmount),
        lineItems: data.dec(_f$lineItems),
        documents: data.dec(_f$documents),
        processInstance: data.dec(_f$processInstance),
        additionalDetails: data.dec(_f$additionalDetails),
        auditDetails: data.dec(_f$auditDetails),
        businessService: data.dec(_f$businessService),
        supplementNumber: data.dec(_f$supplementNumber));
  }

  @override
  final Function instantiate = _instantiate;

  static Contracts fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<Contracts>(map);
  }

  static Contracts fromJson(String json) {
    return ensureInitialized().decodeJson<Contracts>(json);
  }
}

mixin ContractsMappable {
  String toJson() {
    return ContractsMapper.ensureInitialized()
        .encodeJson<Contracts>(this as Contracts);
  }

  Map<String, dynamic> toMap() {
    return ContractsMapper.ensureInitialized()
        .encodeMap<Contracts>(this as Contracts);
  }

  ContractsCopyWith<Contracts, Contracts, Contracts> get copyWith =>
      _ContractsCopyWithImpl(this as Contracts, $identity, $identity);
  @override
  String toString() {
    return ContractsMapper.ensureInitialized()
        .stringifyValue(this as Contracts);
  }

  @override
  bool operator ==(Object other) {
    return ContractsMapper.ensureInitialized()
        .equalsValue(this as Contracts, other);
  }

  @override
  int get hashCode {
    return ContractsMapper.ensureInitialized().hashValue(this as Contracts);
  }
}

extension ContractsValueCopy<$R, $Out> on ObjectCopyWith<$R, Contracts, $Out> {
  ContractsCopyWith<$R, Contracts, $Out> get $asContracts =>
      $base.as((v, t, t2) => _ContractsCopyWithImpl(v, t, t2));
}

abstract class ContractsCopyWith<$R, $In extends Contracts, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, LineItems, LineItemsCopyWith<$R, LineItems, LineItems>>?
      get lineItems;
  ListCopyWith<$R, Documents, DocumentsCopyWith<$R, Documents, Documents>>?
      get documents;
  ContractProcessInstanceCopyWith<$R, ContractProcessInstance,
      ContractProcessInstance>? get processInstance;
  ContractAdditionalDetailsCopyWith<$R, ContractAdditionalDetails,
      ContractAdditionalDetails>? get additionalDetails;
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {String? id,
      String? contractNumber,
      String? tenantId,
      String? status,
      int? endDate,
      int? startDate,
      int? agreementDate,
      int? completionPeriod,
      String? contractType,
      int? defectLiabilityPeriod,
      String? executingAuthority,
      int? issueDate,
      String? orgId,
      double? securityDeposit,
      String? wfStatus,
      double? totalContractedAmount,
      List<LineItems>? lineItems,
      List<Documents>? documents,
      ContractProcessInstance? processInstance,
      ContractAdditionalDetails? additionalDetails,
      ContractAuditDetails? auditDetails,
      String? businessService,
      String? supplementNumber});
  ContractsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _ContractsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, Contracts, $Out>
    implements ContractsCopyWith<$R, Contracts, $Out> {
  _ContractsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<Contracts> $mapper =
      ContractsMapper.ensureInitialized();
  @override
  ListCopyWith<$R, LineItems, LineItemsCopyWith<$R, LineItems, LineItems>>?
      get lineItems => $value.lineItems != null
          ? ListCopyWith($value.lineItems!, (v, t) => v.copyWith.$chain(t),
              (v) => call(lineItems: v))
          : null;
  @override
  ListCopyWith<$R, Documents, DocumentsCopyWith<$R, Documents, Documents>>?
      get documents => $value.documents != null
          ? ListCopyWith($value.documents!, (v, t) => v.copyWith.$chain(t),
              (v) => call(documents: v))
          : null;
  @override
  ContractProcessInstanceCopyWith<$R, ContractProcessInstance,
          ContractProcessInstance>?
      get processInstance => $value.processInstance?.copyWith
          .$chain((v) => call(processInstance: v));
  @override
  ContractAdditionalDetailsCopyWith<$R, ContractAdditionalDetails,
          ContractAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .$chain((v) => call(additionalDetails: v));
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  $R call(
          {Object? id = $none,
          Object? contractNumber = $none,
          Object? tenantId = $none,
          Object? status = $none,
          Object? endDate = $none,
          Object? startDate = $none,
          Object? agreementDate = $none,
          Object? completionPeriod = $none,
          Object? contractType = $none,
          Object? defectLiabilityPeriod = $none,
          Object? executingAuthority = $none,
          Object? issueDate = $none,
          Object? orgId = $none,
          Object? securityDeposit = $none,
          Object? wfStatus = $none,
          Object? totalContractedAmount = $none,
          Object? lineItems = $none,
          Object? documents = $none,
          Object? processInstance = $none,
          Object? additionalDetails = $none,
          Object? auditDetails = $none,
          Object? businessService = $none,
          Object? supplementNumber = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (contractNumber != $none) #contractNumber: contractNumber,
        if (tenantId != $none) #tenantId: tenantId,
        if (status != $none) #status: status,
        if (endDate != $none) #endDate: endDate,
        if (startDate != $none) #startDate: startDate,
        if (agreementDate != $none) #agreementDate: agreementDate,
        if (completionPeriod != $none) #completionPeriod: completionPeriod,
        if (contractType != $none) #contractType: contractType,
        if (defectLiabilityPeriod != $none)
          #defectLiabilityPeriod: defectLiabilityPeriod,
        if (executingAuthority != $none)
          #executingAuthority: executingAuthority,
        if (issueDate != $none) #issueDate: issueDate,
        if (orgId != $none) #orgId: orgId,
        if (securityDeposit != $none) #securityDeposit: securityDeposit,
        if (wfStatus != $none) #wfStatus: wfStatus,
        if (totalContractedAmount != $none)
          #totalContractedAmount: totalContractedAmount,
        if (lineItems != $none) #lineItems: lineItems,
        if (documents != $none) #documents: documents,
        if (processInstance != $none) #processInstance: processInstance,
        if (additionalDetails != $none) #additionalDetails: additionalDetails,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (businessService != $none) #businessService: businessService,
        if (supplementNumber != $none) #supplementNumber: supplementNumber
      }));
  @override
  Contracts $make(CopyWithData data) => Contracts(
      id: data.get(#id, or: $value.id),
      contractNumber: data.get(#contractNumber, or: $value.contractNumber),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      status: data.get(#status, or: $value.status),
      endDate: data.get(#endDate, or: $value.endDate),
      startDate: data.get(#startDate, or: $value.startDate),
      agreementDate: data.get(#agreementDate, or: $value.agreementDate),
      completionPeriod:
          data.get(#completionPeriod, or: $value.completionPeriod),
      contractType: data.get(#contractType, or: $value.contractType),
      defectLiabilityPeriod:
          data.get(#defectLiabilityPeriod, or: $value.defectLiabilityPeriod),
      executingAuthority:
          data.get(#executingAuthority, or: $value.executingAuthority),
      issueDate: data.get(#issueDate, or: $value.issueDate),
      orgId: data.get(#orgId, or: $value.orgId),
      securityDeposit: data.get(#securityDeposit, or: $value.securityDeposit),
      wfStatus: data.get(#wfStatus, or: $value.wfStatus),
      totalContractedAmount:
          data.get(#totalContractedAmount, or: $value.totalContractedAmount),
      lineItems: data.get(#lineItems, or: $value.lineItems),
      documents: data.get(#documents, or: $value.documents),
      processInstance: data.get(#processInstance, or: $value.processInstance),
      additionalDetails:
          data.get(#additionalDetails, or: $value.additionalDetails),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      businessService: data.get(#businessService, or: $value.businessService),
      supplementNumber:
          data.get(#supplementNumber, or: $value.supplementNumber));

  @override
  ContractsCopyWith<$R2, Contracts, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _ContractsCopyWithImpl($value, $cast, t);
}

class LineItemsMapper extends ClassMapperBase<LineItems> {
  LineItemsMapper._();

  static LineItemsMapper? _instance;
  static LineItemsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = LineItemsMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
      AmountBreakupsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'LineItems';

  static String? _$id(LineItems v) => v.id;
  static const Field<LineItems, String> _f$id = Field('id', _$id, opt: true);
  static String? _$status(LineItems v) => v.status;
  static const Field<LineItems, String> _f$status =
      Field('status', _$status, opt: true);
  static String? _$tenantId(LineItems v) => v.tenantId;
  static const Field<LineItems, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static String? _$name(LineItems v) => v.name;
  static const Field<LineItems, String> _f$name =
      Field('name', _$name, opt: true);
  static ContractAuditDetails? _$auditDetails(LineItems v) => v.auditDetails;
  static const Field<LineItems, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static List<AmountBreakups>? _$amountBreakups(LineItems v) =>
      v.amountBreakups;
  static const Field<LineItems, List<AmountBreakups>> _f$amountBreakups =
      Field('amountBreakups', _$amountBreakups, opt: true);
  static String? _$category(LineItems v) => v.category;
  static const Field<LineItems, String> _f$category =
      Field('category', _$category, opt: true);
  static String? _$estimateId(LineItems v) => v.estimateId;
  static const Field<LineItems, String> _f$estimateId =
      Field('estimateId', _$estimateId, opt: true);
  static String? _$estimateLineItemId(LineItems v) => v.estimateLineItemId;
  static const Field<LineItems, String> _f$estimateLineItemId =
      Field('estimateLineItemId', _$estimateLineItemId, opt: true);
  static double? _$noOfunit(LineItems v) => v.noOfunit;
  static const Field<LineItems, double> _f$noOfunit =
      Field('noOfunit', _$noOfunit, opt: true);
  static double? _$unitRate(LineItems v) => v.unitRate;
  static const Field<LineItems, double> _f$unitRate =
      Field('unitRate', _$unitRate, opt: true);

  @override
  final MappableFields<LineItems> fields = const {
    #id: _f$id,
    #status: _f$status,
    #tenantId: _f$tenantId,
    #name: _f$name,
    #auditDetails: _f$auditDetails,
    #amountBreakups: _f$amountBreakups,
    #category: _f$category,
    #estimateId: _f$estimateId,
    #estimateLineItemId: _f$estimateLineItemId,
    #noOfunit: _f$noOfunit,
    #unitRate: _f$unitRate,
  };

  static LineItems _instantiate(DecodingData data) {
    return LineItems(
        id: data.dec(_f$id),
        status: data.dec(_f$status),
        tenantId: data.dec(_f$tenantId),
        name: data.dec(_f$name),
        auditDetails: data.dec(_f$auditDetails),
        amountBreakups: data.dec(_f$amountBreakups),
        category: data.dec(_f$category),
        estimateId: data.dec(_f$estimateId),
        estimateLineItemId: data.dec(_f$estimateLineItemId),
        noOfunit: data.dec(_f$noOfunit),
        unitRate: data.dec(_f$unitRate));
  }

  @override
  final Function instantiate = _instantiate;

  static LineItems fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<LineItems>(map);
  }

  static LineItems fromJson(String json) {
    return ensureInitialized().decodeJson<LineItems>(json);
  }
}

mixin LineItemsMappable {
  String toJson() {
    return LineItemsMapper.ensureInitialized()
        .encodeJson<LineItems>(this as LineItems);
  }

  Map<String, dynamic> toMap() {
    return LineItemsMapper.ensureInitialized()
        .encodeMap<LineItems>(this as LineItems);
  }

  LineItemsCopyWith<LineItems, LineItems, LineItems> get copyWith =>
      _LineItemsCopyWithImpl(this as LineItems, $identity, $identity);
  @override
  String toString() {
    return LineItemsMapper.ensureInitialized()
        .stringifyValue(this as LineItems);
  }

  @override
  bool operator ==(Object other) {
    return LineItemsMapper.ensureInitialized()
        .equalsValue(this as LineItems, other);
  }

  @override
  int get hashCode {
    return LineItemsMapper.ensureInitialized().hashValue(this as LineItems);
  }
}

extension LineItemsValueCopy<$R, $Out> on ObjectCopyWith<$R, LineItems, $Out> {
  LineItemsCopyWith<$R, LineItems, $Out> get $asLineItems =>
      $base.as((v, t, t2) => _LineItemsCopyWithImpl(v, t, t2));
}

abstract class LineItemsCopyWith<$R, $In extends LineItems, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  ListCopyWith<$R, AmountBreakups,
          AmountBreakupsCopyWith<$R, AmountBreakups, AmountBreakups>>?
      get amountBreakups;
  $R call(
      {String? id,
      String? status,
      String? tenantId,
      String? name,
      ContractAuditDetails? auditDetails,
      List<AmountBreakups>? amountBreakups,
      String? category,
      String? estimateId,
      String? estimateLineItemId,
      double? noOfunit,
      double? unitRate});
  LineItemsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _LineItemsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, LineItems, $Out>
    implements LineItemsCopyWith<$R, LineItems, $Out> {
  _LineItemsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<LineItems> $mapper =
      LineItemsMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, AmountBreakups,
          AmountBreakupsCopyWith<$R, AmountBreakups, AmountBreakups>>?
      get amountBreakups => $value.amountBreakups != null
          ? ListCopyWith($value.amountBreakups!, (v, t) => v.copyWith.$chain(t),
              (v) => call(amountBreakups: v))
          : null;
  @override
  $R call(
          {Object? id = $none,
          Object? status = $none,
          Object? tenantId = $none,
          Object? name = $none,
          Object? auditDetails = $none,
          Object? amountBreakups = $none,
          Object? category = $none,
          Object? estimateId = $none,
          Object? estimateLineItemId = $none,
          Object? noOfunit = $none,
          Object? unitRate = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (status != $none) #status: status,
        if (tenantId != $none) #tenantId: tenantId,
        if (name != $none) #name: name,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (amountBreakups != $none) #amountBreakups: amountBreakups,
        if (category != $none) #category: category,
        if (estimateId != $none) #estimateId: estimateId,
        if (estimateLineItemId != $none)
          #estimateLineItemId: estimateLineItemId,
        if (noOfunit != $none) #noOfunit: noOfunit,
        if (unitRate != $none) #unitRate: unitRate
      }));
  @override
  LineItems $make(CopyWithData data) => LineItems(
      id: data.get(#id, or: $value.id),
      status: data.get(#status, or: $value.status),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      name: data.get(#name, or: $value.name),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      amountBreakups: data.get(#amountBreakups, or: $value.amountBreakups),
      category: data.get(#category, or: $value.category),
      estimateId: data.get(#estimateId, or: $value.estimateId),
      estimateLineItemId:
          data.get(#estimateLineItemId, or: $value.estimateLineItemId),
      noOfunit: data.get(#noOfunit, or: $value.noOfunit),
      unitRate: data.get(#unitRate, or: $value.unitRate));

  @override
  LineItemsCopyWith<$R2, LineItems, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _LineItemsCopyWithImpl($value, $cast, t);
}

class ContractAuditDetailsMapper extends ClassMapperBase<ContractAuditDetails> {
  ContractAuditDetailsMapper._();

  static ContractAuditDetailsMapper? _instance;
  static ContractAuditDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = ContractAuditDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'ContractAuditDetails';

  static int? _$createdTime(ContractAuditDetails v) => v.createdTime;
  static const Field<ContractAuditDetails, int> _f$createdTime =
      Field('createdTime', _$createdTime, opt: true);
  static int? _$lastModifiedTime(ContractAuditDetails v) => v.lastModifiedTime;
  static const Field<ContractAuditDetails, int> _f$lastModifiedTime =
      Field('lastModifiedTime', _$lastModifiedTime, opt: true);
  static String? _$createdBy(ContractAuditDetails v) => v.createdBy;
  static const Field<ContractAuditDetails, String> _f$createdBy =
      Field('createdBy', _$createdBy, opt: true);
  static String? _$lastModifiedBy(ContractAuditDetails v) => v.lastModifiedBy;
  static const Field<ContractAuditDetails, String> _f$lastModifiedBy =
      Field('lastModifiedBy', _$lastModifiedBy, opt: true);

  @override
  final MappableFields<ContractAuditDetails> fields = const {
    #createdTime: _f$createdTime,
    #lastModifiedTime: _f$lastModifiedTime,
    #createdBy: _f$createdBy,
    #lastModifiedBy: _f$lastModifiedBy,
  };

  static ContractAuditDetails _instantiate(DecodingData data) {
    return ContractAuditDetails(
        createdTime: data.dec(_f$createdTime),
        lastModifiedTime: data.dec(_f$lastModifiedTime),
        createdBy: data.dec(_f$createdBy),
        lastModifiedBy: data.dec(_f$lastModifiedBy));
  }

  @override
  final Function instantiate = _instantiate;

  static ContractAuditDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<ContractAuditDetails>(map);
  }

  static ContractAuditDetails fromJson(String json) {
    return ensureInitialized().decodeJson<ContractAuditDetails>(json);
  }
}

mixin ContractAuditDetailsMappable {
  String toJson() {
    return ContractAuditDetailsMapper.ensureInitialized()
        .encodeJson<ContractAuditDetails>(this as ContractAuditDetails);
  }

  Map<String, dynamic> toMap() {
    return ContractAuditDetailsMapper.ensureInitialized()
        .encodeMap<ContractAuditDetails>(this as ContractAuditDetails);
  }

  ContractAuditDetailsCopyWith<ContractAuditDetails, ContractAuditDetails,
          ContractAuditDetails>
      get copyWith => _ContractAuditDetailsCopyWithImpl(
          this as ContractAuditDetails, $identity, $identity);
  @override
  String toString() {
    return ContractAuditDetailsMapper.ensureInitialized()
        .stringifyValue(this as ContractAuditDetails);
  }

  @override
  bool operator ==(Object other) {
    return ContractAuditDetailsMapper.ensureInitialized()
        .equalsValue(this as ContractAuditDetails, other);
  }

  @override
  int get hashCode {
    return ContractAuditDetailsMapper.ensureInitialized()
        .hashValue(this as ContractAuditDetails);
  }
}

extension ContractAuditDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, ContractAuditDetails, $Out> {
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, $Out>
      get $asContractAuditDetails =>
          $base.as((v, t, t2) => _ContractAuditDetailsCopyWithImpl(v, t, t2));
}

abstract class ContractAuditDetailsCopyWith<
    $R,
    $In extends ContractAuditDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {int? createdTime,
      int? lastModifiedTime,
      String? createdBy,
      String? lastModifiedBy});
  ContractAuditDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _ContractAuditDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, ContractAuditDetails, $Out>
    implements ContractAuditDetailsCopyWith<$R, ContractAuditDetails, $Out> {
  _ContractAuditDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<ContractAuditDetails> $mapper =
      ContractAuditDetailsMapper.ensureInitialized();
  @override
  $R call(
          {Object? createdTime = $none,
          Object? lastModifiedTime = $none,
          Object? createdBy = $none,
          Object? lastModifiedBy = $none}) =>
      $apply(FieldCopyWithData({
        if (createdTime != $none) #createdTime: createdTime,
        if (lastModifiedTime != $none) #lastModifiedTime: lastModifiedTime,
        if (createdBy != $none) #createdBy: createdBy,
        if (lastModifiedBy != $none) #lastModifiedBy: lastModifiedBy
      }));
  @override
  ContractAuditDetails $make(CopyWithData data) => ContractAuditDetails(
      createdTime: data.get(#createdTime, or: $value.createdTime),
      lastModifiedTime:
          data.get(#lastModifiedTime, or: $value.lastModifiedTime),
      createdBy: data.get(#createdBy, or: $value.createdBy),
      lastModifiedBy: data.get(#lastModifiedBy, or: $value.lastModifiedBy));

  @override
  ContractAuditDetailsCopyWith<$R2, ContractAuditDetails, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _ContractAuditDetailsCopyWithImpl($value, $cast, t);
}

class AmountBreakupsMapper extends ClassMapperBase<AmountBreakups> {
  AmountBreakupsMapper._();

  static AmountBreakupsMapper? _instance;
  static AmountBreakupsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = AmountBreakupsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'AmountBreakups';

  static String? _$id(AmountBreakups v) => v.id;
  static const Field<AmountBreakups, String> _f$id =
      Field('id', _$id, opt: true);
  static String? _$status(AmountBreakups v) => v.status;
  static const Field<AmountBreakups, String> _f$status =
      Field('status', _$status, opt: true);
  static double? _$additionalDetails(AmountBreakups v) => v.additionalDetails;
  static const Field<AmountBreakups, double> _f$additionalDetails =
      Field('additionalDetails', _$additionalDetails, opt: true);
  static String? _$amount(AmountBreakups v) => v.amount;
  static const Field<AmountBreakups, String> _f$amount =
      Field('amount', _$amount, opt: true);
  static String? _$estimateAmountBreakupId(AmountBreakups v) =>
      v.estimateAmountBreakupId;
  static const Field<AmountBreakups, String> _f$estimateAmountBreakupId =
      Field('estimateAmountBreakupId', _$estimateAmountBreakupId, opt: true);

  @override
  final MappableFields<AmountBreakups> fields = const {
    #id: _f$id,
    #status: _f$status,
    #additionalDetails: _f$additionalDetails,
    #amount: _f$amount,
    #estimateAmountBreakupId: _f$estimateAmountBreakupId,
  };

  static AmountBreakups _instantiate(DecodingData data) {
    return AmountBreakups(
        id: data.dec(_f$id),
        status: data.dec(_f$status),
        additionalDetails: data.dec(_f$additionalDetails),
        amount: data.dec(_f$amount),
        estimateAmountBreakupId: data.dec(_f$estimateAmountBreakupId));
  }

  @override
  final Function instantiate = _instantiate;

  static AmountBreakups fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<AmountBreakups>(map);
  }

  static AmountBreakups fromJson(String json) {
    return ensureInitialized().decodeJson<AmountBreakups>(json);
  }
}

mixin AmountBreakupsMappable {
  String toJson() {
    return AmountBreakupsMapper.ensureInitialized()
        .encodeJson<AmountBreakups>(this as AmountBreakups);
  }

  Map<String, dynamic> toMap() {
    return AmountBreakupsMapper.ensureInitialized()
        .encodeMap<AmountBreakups>(this as AmountBreakups);
  }

  AmountBreakupsCopyWith<AmountBreakups, AmountBreakups, AmountBreakups>
      get copyWith => _AmountBreakupsCopyWithImpl(
          this as AmountBreakups, $identity, $identity);
  @override
  String toString() {
    return AmountBreakupsMapper.ensureInitialized()
        .stringifyValue(this as AmountBreakups);
  }

  @override
  bool operator ==(Object other) {
    return AmountBreakupsMapper.ensureInitialized()
        .equalsValue(this as AmountBreakups, other);
  }

  @override
  int get hashCode {
    return AmountBreakupsMapper.ensureInitialized()
        .hashValue(this as AmountBreakups);
  }
}

extension AmountBreakupsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, AmountBreakups, $Out> {
  AmountBreakupsCopyWith<$R, AmountBreakups, $Out> get $asAmountBreakups =>
      $base.as((v, t, t2) => _AmountBreakupsCopyWithImpl(v, t, t2));
}

abstract class AmountBreakupsCopyWith<$R, $In extends AmountBreakups, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {String? id,
      String? status,
      double? additionalDetails,
      String? amount,
      String? estimateAmountBreakupId});
  AmountBreakupsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _AmountBreakupsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, AmountBreakups, $Out>
    implements AmountBreakupsCopyWith<$R, AmountBreakups, $Out> {
  _AmountBreakupsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<AmountBreakups> $mapper =
      AmountBreakupsMapper.ensureInitialized();
  @override
  $R call(
          {Object? id = $none,
          Object? status = $none,
          Object? additionalDetails = $none,
          Object? amount = $none,
          Object? estimateAmountBreakupId = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (status != $none) #status: status,
        if (additionalDetails != $none) #additionalDetails: additionalDetails,
        if (amount != $none) #amount: amount,
        if (estimateAmountBreakupId != $none)
          #estimateAmountBreakupId: estimateAmountBreakupId
      }));
  @override
  AmountBreakups $make(CopyWithData data) => AmountBreakups(
      id: data.get(#id, or: $value.id),
      status: data.get(#status, or: $value.status),
      additionalDetails:
          data.get(#additionalDetails, or: $value.additionalDetails),
      amount: data.get(#amount, or: $value.amount),
      estimateAmountBreakupId: data.get(#estimateAmountBreakupId,
          or: $value.estimateAmountBreakupId));

  @override
  AmountBreakupsCopyWith<$R2, AmountBreakups, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _AmountBreakupsCopyWithImpl($value, $cast, t);
}

class DocumentsMapper extends ClassMapperBase<Documents> {
  DocumentsMapper._();

  static DocumentsMapper? _instance;
  static DocumentsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = DocumentsMapper._());
      DocumentAdditionalDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'Documents';

  static DocumentAdditionalDetails? _$additionalDetails(Documents v) =>
      v.additionalDetails;
  static const Field<Documents, DocumentAdditionalDetails>
      _f$additionalDetails =
      Field('additionalDetails', _$additionalDetails, opt: true);
  static String? _$contractId(Documents v) => v.contractId;
  static const Field<Documents, String> _f$contractId =
      Field('contractId', _$contractId, opt: true);
  static String? _$id(Documents v) => v.id;
  static const Field<Documents, String> _f$id = Field('id', _$id, opt: true);
  static String? _$documentType(Documents v) => v.documentType;
  static const Field<Documents, String> _f$documentType =
      Field('documentType', _$documentType, opt: true);
  static String? _$status(Documents v) => v.status;
  static const Field<Documents, String> _f$status =
      Field('status', _$status, opt: true);
  static String? _$documentUid(Documents v) => v.documentUid;
  static const Field<Documents, String> _f$documentUid =
      Field('documentUid', _$documentUid, opt: true);
  static String? _$fileStore(Documents v) => v.fileStore;
  static const Field<Documents, String> _f$fileStore =
      Field('fileStore', _$fileStore, opt: true);

  @override
  final MappableFields<Documents> fields = const {
    #additionalDetails: _f$additionalDetails,
    #contractId: _f$contractId,
    #id: _f$id,
    #documentType: _f$documentType,
    #status: _f$status,
    #documentUid: _f$documentUid,
    #fileStore: _f$fileStore,
  };

  static Documents _instantiate(DecodingData data) {
    return Documents(
        additionalDetails: data.dec(_f$additionalDetails),
        contractId: data.dec(_f$contractId),
        id: data.dec(_f$id),
        documentType: data.dec(_f$documentType),
        status: data.dec(_f$status),
        documentUid: data.dec(_f$documentUid),
        fileStore: data.dec(_f$fileStore));
  }

  @override
  final Function instantiate = _instantiate;

  static Documents fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<Documents>(map);
  }

  static Documents fromJson(String json) {
    return ensureInitialized().decodeJson<Documents>(json);
  }
}

mixin DocumentsMappable {
  String toJson() {
    return DocumentsMapper.ensureInitialized()
        .encodeJson<Documents>(this as Documents);
  }

  Map<String, dynamic> toMap() {
    return DocumentsMapper.ensureInitialized()
        .encodeMap<Documents>(this as Documents);
  }

  DocumentsCopyWith<Documents, Documents, Documents> get copyWith =>
      _DocumentsCopyWithImpl(this as Documents, $identity, $identity);
  @override
  String toString() {
    return DocumentsMapper.ensureInitialized()
        .stringifyValue(this as Documents);
  }

  @override
  bool operator ==(Object other) {
    return DocumentsMapper.ensureInitialized()
        .equalsValue(this as Documents, other);
  }

  @override
  int get hashCode {
    return DocumentsMapper.ensureInitialized().hashValue(this as Documents);
  }
}

extension DocumentsValueCopy<$R, $Out> on ObjectCopyWith<$R, Documents, $Out> {
  DocumentsCopyWith<$R, Documents, $Out> get $asDocuments =>
      $base.as((v, t, t2) => _DocumentsCopyWithImpl(v, t, t2));
}

abstract class DocumentsCopyWith<$R, $In extends Documents, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  DocumentAdditionalDetailsCopyWith<$R, DocumentAdditionalDetails,
      DocumentAdditionalDetails>? get additionalDetails;
  $R call(
      {DocumentAdditionalDetails? additionalDetails,
      String? contractId,
      String? id,
      String? documentType,
      String? status,
      String? documentUid,
      String? fileStore});
  DocumentsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _DocumentsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, Documents, $Out>
    implements DocumentsCopyWith<$R, Documents, $Out> {
  _DocumentsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<Documents> $mapper =
      DocumentsMapper.ensureInitialized();
  @override
  DocumentAdditionalDetailsCopyWith<$R, DocumentAdditionalDetails,
          DocumentAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .$chain((v) => call(additionalDetails: v));
  @override
  $R call(
          {Object? additionalDetails = $none,
          Object? contractId = $none,
          Object? id = $none,
          Object? documentType = $none,
          Object? status = $none,
          Object? documentUid = $none,
          Object? fileStore = $none}) =>
      $apply(FieldCopyWithData({
        if (additionalDetails != $none) #additionalDetails: additionalDetails,
        if (contractId != $none) #contractId: contractId,
        if (id != $none) #id: id,
        if (documentType != $none) #documentType: documentType,
        if (status != $none) #status: status,
        if (documentUid != $none) #documentUid: documentUid,
        if (fileStore != $none) #fileStore: fileStore
      }));
  @override
  Documents $make(CopyWithData data) => Documents(
      additionalDetails:
          data.get(#additionalDetails, or: $value.additionalDetails),
      contractId: data.get(#contractId, or: $value.contractId),
      id: data.get(#id, or: $value.id),
      documentType: data.get(#documentType, or: $value.documentType),
      status: data.get(#status, or: $value.status),
      documentUid: data.get(#documentUid, or: $value.documentUid),
      fileStore: data.get(#fileStore, or: $value.fileStore));

  @override
  DocumentsCopyWith<$R2, Documents, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _DocumentsCopyWithImpl($value, $cast, t);
}

class DocumentAdditionalDetailsMapper
    extends ClassMapperBase<DocumentAdditionalDetails> {
  DocumentAdditionalDetailsMapper._();

  static DocumentAdditionalDetailsMapper? _instance;
  static DocumentAdditionalDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals
          .use(_instance = DocumentAdditionalDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'DocumentAdditionalDetails';

  static String? _$fileName(DocumentAdditionalDetails v) => v.fileName;
  static const Field<DocumentAdditionalDetails, String> _f$fileName =
      Field('fileName', _$fileName);
  static String? _$otherCategoryName(DocumentAdditionalDetails v) =>
      v.otherCategoryName;
  static const Field<DocumentAdditionalDetails, String> _f$otherCategoryName =
      Field('otherCategoryName', _$otherCategoryName);

  @override
  final MappableFields<DocumentAdditionalDetails> fields = const {
    #fileName: _f$fileName,
    #otherCategoryName: _f$otherCategoryName,
  };

  static DocumentAdditionalDetails _instantiate(DecodingData data) {
    return DocumentAdditionalDetails(
        data.dec(_f$fileName), data.dec(_f$otherCategoryName));
  }

  @override
  final Function instantiate = _instantiate;

  static DocumentAdditionalDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<DocumentAdditionalDetails>(map);
  }

  static DocumentAdditionalDetails fromJson(String json) {
    return ensureInitialized().decodeJson<DocumentAdditionalDetails>(json);
  }
}

mixin DocumentAdditionalDetailsMappable {
  String toJson() {
    return DocumentAdditionalDetailsMapper.ensureInitialized()
        .encodeJson<DocumentAdditionalDetails>(
            this as DocumentAdditionalDetails);
  }

  Map<String, dynamic> toMap() {
    return DocumentAdditionalDetailsMapper.ensureInitialized()
        .encodeMap<DocumentAdditionalDetails>(
            this as DocumentAdditionalDetails);
  }

  DocumentAdditionalDetailsCopyWith<DocumentAdditionalDetails,
          DocumentAdditionalDetails, DocumentAdditionalDetails>
      get copyWith => _DocumentAdditionalDetailsCopyWithImpl(
          this as DocumentAdditionalDetails, $identity, $identity);
  @override
  String toString() {
    return DocumentAdditionalDetailsMapper.ensureInitialized()
        .stringifyValue(this as DocumentAdditionalDetails);
  }

  @override
  bool operator ==(Object other) {
    return DocumentAdditionalDetailsMapper.ensureInitialized()
        .equalsValue(this as DocumentAdditionalDetails, other);
  }

  @override
  int get hashCode {
    return DocumentAdditionalDetailsMapper.ensureInitialized()
        .hashValue(this as DocumentAdditionalDetails);
  }
}

extension DocumentAdditionalDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, DocumentAdditionalDetails, $Out> {
  DocumentAdditionalDetailsCopyWith<$R, DocumentAdditionalDetails, $Out>
      get $asDocumentAdditionalDetails => $base
          .as((v, t, t2) => _DocumentAdditionalDetailsCopyWithImpl(v, t, t2));
}

abstract class DocumentAdditionalDetailsCopyWith<
    $R,
    $In extends DocumentAdditionalDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? fileName, String? otherCategoryName});
  DocumentAdditionalDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _DocumentAdditionalDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, DocumentAdditionalDetails, $Out>
    implements
        DocumentAdditionalDetailsCopyWith<$R, DocumentAdditionalDetails, $Out> {
  _DocumentAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<DocumentAdditionalDetails> $mapper =
      DocumentAdditionalDetailsMapper.ensureInitialized();
  @override
  $R call({Object? fileName = $none, Object? otherCategoryName = $none}) =>
      $apply(FieldCopyWithData({
        if (fileName != $none) #fileName: fileName,
        if (otherCategoryName != $none) #otherCategoryName: otherCategoryName
      }));
  @override
  DocumentAdditionalDetails $make(CopyWithData data) =>
      DocumentAdditionalDetails(data.get(#fileName, or: $value.fileName),
          data.get(#otherCategoryName, or: $value.otherCategoryName));

  @override
  DocumentAdditionalDetailsCopyWith<$R2, DocumentAdditionalDetails, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _DocumentAdditionalDetailsCopyWithImpl($value, $cast, t);
}

class ContractProcessInstanceMapper
    extends ClassMapperBase<ContractProcessInstance> {
  ContractProcessInstanceMapper._();

  static ContractProcessInstanceMapper? _instance;
  static ContractProcessInstanceMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals
          .use(_instance = ContractProcessInstanceMapper._());
      ContractStateMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'ContractProcessInstance';

  static String? _$action(ContractProcessInstance v) => v.action;
  static const Field<ContractProcessInstance, String> _f$action =
      Field('action', _$action, opt: true);
  static String? _$tenantId(ContractProcessInstance v) => v.tenantId;
  static const Field<ContractProcessInstance, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static ContractState? _$state(ContractProcessInstance v) => v.state;
  static const Field<ContractProcessInstance, ContractState> _f$state =
      Field('state', _$state, opt: true);
  static String? _$id(ContractProcessInstance v) => v.id;
  static const Field<ContractProcessInstance, String> _f$id =
      Field('id', _$id, opt: true);
  static String? _$businessId(ContractProcessInstance v) => v.businessId;
  static const Field<ContractProcessInstance, String> _f$businessId =
      Field('businessId', _$businessId, opt: true);
  static String? _$businessService(ContractProcessInstance v) =>
      v.businessService;
  static const Field<ContractProcessInstance, String> _f$businessService =
      Field('businessService', _$businessService, opt: true);
  static String? _$moduleName(ContractProcessInstance v) => v.moduleName;
  static const Field<ContractProcessInstance, String> _f$moduleName =
      Field('moduleName', _$moduleName, opt: true);

  @override
  final MappableFields<ContractProcessInstance> fields = const {
    #action: _f$action,
    #tenantId: _f$tenantId,
    #state: _f$state,
    #id: _f$id,
    #businessId: _f$businessId,
    #businessService: _f$businessService,
    #moduleName: _f$moduleName,
  };

  static ContractProcessInstance _instantiate(DecodingData data) {
    return ContractProcessInstance(
        action: data.dec(_f$action),
        tenantId: data.dec(_f$tenantId),
        state: data.dec(_f$state),
        id: data.dec(_f$id),
        businessId: data.dec(_f$businessId),
        businessService: data.dec(_f$businessService),
        moduleName: data.dec(_f$moduleName));
  }

  @override
  final Function instantiate = _instantiate;

  static ContractProcessInstance fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<ContractProcessInstance>(map);
  }

  static ContractProcessInstance fromJson(String json) {
    return ensureInitialized().decodeJson<ContractProcessInstance>(json);
  }
}

mixin ContractProcessInstanceMappable {
  String toJson() {
    return ContractProcessInstanceMapper.ensureInitialized()
        .encodeJson<ContractProcessInstance>(this as ContractProcessInstance);
  }

  Map<String, dynamic> toMap() {
    return ContractProcessInstanceMapper.ensureInitialized()
        .encodeMap<ContractProcessInstance>(this as ContractProcessInstance);
  }

  ContractProcessInstanceCopyWith<ContractProcessInstance,
          ContractProcessInstance, ContractProcessInstance>
      get copyWith => _ContractProcessInstanceCopyWithImpl(
          this as ContractProcessInstance, $identity, $identity);
  @override
  String toString() {
    return ContractProcessInstanceMapper.ensureInitialized()
        .stringifyValue(this as ContractProcessInstance);
  }

  @override
  bool operator ==(Object other) {
    return ContractProcessInstanceMapper.ensureInitialized()
        .equalsValue(this as ContractProcessInstance, other);
  }

  @override
  int get hashCode {
    return ContractProcessInstanceMapper.ensureInitialized()
        .hashValue(this as ContractProcessInstance);
  }
}

extension ContractProcessInstanceValueCopy<$R, $Out>
    on ObjectCopyWith<$R, ContractProcessInstance, $Out> {
  ContractProcessInstanceCopyWith<$R, ContractProcessInstance, $Out>
      get $asContractProcessInstance => $base
          .as((v, t, t2) => _ContractProcessInstanceCopyWithImpl(v, t, t2));
}

abstract class ContractProcessInstanceCopyWith<
    $R,
    $In extends ContractProcessInstance,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  ContractStateCopyWith<$R, ContractState, ContractState>? get state;
  $R call(
      {String? action,
      String? tenantId,
      ContractState? state,
      String? id,
      String? businessId,
      String? businessService,
      String? moduleName});
  ContractProcessInstanceCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _ContractProcessInstanceCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, ContractProcessInstance, $Out>
    implements
        ContractProcessInstanceCopyWith<$R, ContractProcessInstance, $Out> {
  _ContractProcessInstanceCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<ContractProcessInstance> $mapper =
      ContractProcessInstanceMapper.ensureInitialized();
  @override
  ContractStateCopyWith<$R, ContractState, ContractState>? get state =>
      $value.state?.copyWith.$chain((v) => call(state: v));
  @override
  $R call(
          {Object? action = $none,
          Object? tenantId = $none,
          Object? state = $none,
          Object? id = $none,
          Object? businessId = $none,
          Object? businessService = $none,
          Object? moduleName = $none}) =>
      $apply(FieldCopyWithData({
        if (action != $none) #action: action,
        if (tenantId != $none) #tenantId: tenantId,
        if (state != $none) #state: state,
        if (id != $none) #id: id,
        if (businessId != $none) #businessId: businessId,
        if (businessService != $none) #businessService: businessService,
        if (moduleName != $none) #moduleName: moduleName
      }));
  @override
  ContractProcessInstance $make(CopyWithData data) => ContractProcessInstance(
      action: data.get(#action, or: $value.action),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      state: data.get(#state, or: $value.state),
      id: data.get(#id, or: $value.id),
      businessId: data.get(#businessId, or: $value.businessId),
      businessService: data.get(#businessService, or: $value.businessService),
      moduleName: data.get(#moduleName, or: $value.moduleName));

  @override
  ContractProcessInstanceCopyWith<$R2, ContractProcessInstance, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _ContractProcessInstanceCopyWithImpl($value, $cast, t);
}

class ContractStateMapper extends ClassMapperBase<ContractState> {
  ContractStateMapper._();

  static ContractStateMapper? _instance;
  static ContractStateMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = ContractStateMapper._());
      ContractAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'ContractState';

  static ContractAuditDetails? _$auditDetails(ContractState v) =>
      v.auditDetails;
  static const Field<ContractState, ContractAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String? _$uuid(ContractState v) => v.uuid;
  static const Field<ContractState, String> _f$uuid =
      Field('uuid', _$uuid, opt: true);
  static String? _$tenantId(ContractState v) => v.tenantId;
  static const Field<ContractState, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static String? _$state(ContractState v) => v.state;
  static const Field<ContractState, String> _f$state =
      Field('state', _$state, opt: true);
  static String? _$applicationStatus(ContractState v) => v.applicationStatus;
  static const Field<ContractState, String> _f$applicationStatus =
      Field('applicationStatus', _$applicationStatus, opt: true);
  static String? _$businessServiceId(ContractState v) => v.businessServiceId;
  static const Field<ContractState, String> _f$businessServiceId =
      Field('businessServiceId', _$businessServiceId, opt: true);

  @override
  final MappableFields<ContractState> fields = const {
    #auditDetails: _f$auditDetails,
    #uuid: _f$uuid,
    #tenantId: _f$tenantId,
    #state: _f$state,
    #applicationStatus: _f$applicationStatus,
    #businessServiceId: _f$businessServiceId,
  };

  static ContractState _instantiate(DecodingData data) {
    return ContractState(
        auditDetails: data.dec(_f$auditDetails),
        uuid: data.dec(_f$uuid),
        tenantId: data.dec(_f$tenantId),
        state: data.dec(_f$state),
        applicationStatus: data.dec(_f$applicationStatus),
        businessServiceId: data.dec(_f$businessServiceId));
  }

  @override
  final Function instantiate = _instantiate;

  static ContractState fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<ContractState>(map);
  }

  static ContractState fromJson(String json) {
    return ensureInitialized().decodeJson<ContractState>(json);
  }
}

mixin ContractStateMappable {
  String toJson() {
    return ContractStateMapper.ensureInitialized()
        .encodeJson<ContractState>(this as ContractState);
  }

  Map<String, dynamic> toMap() {
    return ContractStateMapper.ensureInitialized()
        .encodeMap<ContractState>(this as ContractState);
  }

  ContractStateCopyWith<ContractState, ContractState, ContractState>
      get copyWith => _ContractStateCopyWithImpl(
          this as ContractState, $identity, $identity);
  @override
  String toString() {
    return ContractStateMapper.ensureInitialized()
        .stringifyValue(this as ContractState);
  }

  @override
  bool operator ==(Object other) {
    return ContractStateMapper.ensureInitialized()
        .equalsValue(this as ContractState, other);
  }

  @override
  int get hashCode {
    return ContractStateMapper.ensureInitialized()
        .hashValue(this as ContractState);
  }
}

extension ContractStateValueCopy<$R, $Out>
    on ObjectCopyWith<$R, ContractState, $Out> {
  ContractStateCopyWith<$R, ContractState, $Out> get $asContractState =>
      $base.as((v, t, t2) => _ContractStateCopyWithImpl(v, t, t2));
}

abstract class ContractStateCopyWith<$R, $In extends ContractState, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {ContractAuditDetails? auditDetails,
      String? uuid,
      String? tenantId,
      String? state,
      String? applicationStatus,
      String? businessServiceId});
  ContractStateCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _ContractStateCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, ContractState, $Out>
    implements ContractStateCopyWith<$R, ContractState, $Out> {
  _ContractStateCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<ContractState> $mapper =
      ContractStateMapper.ensureInitialized();
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  $R call(
          {Object? auditDetails = $none,
          Object? uuid = $none,
          Object? tenantId = $none,
          Object? state = $none,
          Object? applicationStatus = $none,
          Object? businessServiceId = $none}) =>
      $apply(FieldCopyWithData({
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (uuid != $none) #uuid: uuid,
        if (tenantId != $none) #tenantId: tenantId,
        if (state != $none) #state: state,
        if (applicationStatus != $none) #applicationStatus: applicationStatus,
        if (businessServiceId != $none) #businessServiceId: businessServiceId
      }));
  @override
  ContractState $make(CopyWithData data) => ContractState(
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      uuid: data.get(#uuid, or: $value.uuid),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      state: data.get(#state, or: $value.state),
      applicationStatus:
          data.get(#applicationStatus, or: $value.applicationStatus),
      businessServiceId:
          data.get(#businessServiceId, or: $value.businessServiceId));

  @override
  ContractStateCopyWith<$R2, ContractState, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _ContractStateCopyWithImpl($value, $cast, t);
}

class ContractAdditionalDetailsMapper
    extends ClassMapperBase<ContractAdditionalDetails> {
  ContractAdditionalDetailsMapper._();

  static ContractAdditionalDetailsMapper? _instance;
  static ContractAdditionalDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals
          .use(_instance = ContractAdditionalDetailsMapper._());
      EstimateDocsMapper.ensureInitialized();
      DescriptionMapper.ensureInitialized();
      OfficerInChargeMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'ContractAdditionalDetails';

  static String? _$officerInChargeId(ContractAdditionalDetails v) =>
      v.officerInChargeId;
  static const Field<ContractAdditionalDetails, String> _f$officerInChargeId =
      Field('officerInChargeId', _$officerInChargeId, opt: true);
  static String? _$attendanceRegisterNumber(ContractAdditionalDetails v) =>
      v.attendanceRegisterNumber;
  static const Field<ContractAdditionalDetails, String>
      _f$attendanceRegisterNumber =
      Field('attendanceRegisterNumber', _$attendanceRegisterNumber, opt: true);
  static String? _$cboOrgNumber(ContractAdditionalDetails v) => v.cboOrgNumber;
  static const Field<ContractAdditionalDetails, String> _f$cboOrgNumber =
      Field('cboOrgNumber', _$cboOrgNumber, opt: true);
  static String? _$projectId(ContractAdditionalDetails v) => v.projectId;
  static const Field<ContractAdditionalDetails, String> _f$projectId =
      Field('projectId', _$projectId, opt: true);
  static String? _$projectType(ContractAdditionalDetails v) => v.projectType;
  static const Field<ContractAdditionalDetails, String> _f$projectType =
      Field('projectType', _$projectType, opt: true);
  static String? _$orgName(ContractAdditionalDetails v) => v.orgName;
  static const Field<ContractAdditionalDetails, String> _f$orgName =
      Field('orgName', _$orgName, opt: true);
  static String? _$projectName(ContractAdditionalDetails v) => v.projectName;
  static const Field<ContractAdditionalDetails, String> _f$projectName =
      Field('projectName', _$projectName, opt: true);
  static String? _$ward(ContractAdditionalDetails v) => v.ward;
  static const Field<ContractAdditionalDetails, String> _f$ward =
      Field('ward', _$ward, opt: true);
  static String? _$locality(ContractAdditionalDetails v) => v.locality;
  static const Field<ContractAdditionalDetails, String> _f$locality =
      Field('locality', _$locality, opt: true);
  static String? _$cboCode(ContractAdditionalDetails v) => v.cboCode;
  static const Field<ContractAdditionalDetails, String> _f$cboCode =
      Field('cboCode', _$cboCode, opt: true);
  static String? _$cboName(ContractAdditionalDetails v) => v.cboName;
  static const Field<ContractAdditionalDetails, String> _f$cboName =
      Field('cboName', _$cboName, opt: true);
  static List<EstimateDocs>? _$estimateDocs(ContractAdditionalDetails v) =>
      v.estimateDocs;
  static const Field<ContractAdditionalDetails, List<EstimateDocs>>
      _f$estimateDocs = Field('estimateDocs', _$estimateDocs, opt: true);
  static String? _$estimateNumber(ContractAdditionalDetails v) =>
      v.estimateNumber;
  static const Field<ContractAdditionalDetails, String> _f$estimateNumber =
      Field('estimateNumber', _$estimateNumber, opt: true);
  static double? _$totalEstimatedAmount(ContractAdditionalDetails v) =>
      v.totalEstimatedAmount;
  static const Field<ContractAdditionalDetails, double>
      _f$totalEstimatedAmount =
      Field('totalEstimatedAmount', _$totalEstimatedAmount, opt: true);
  static int? _$completionPeriod(ContractAdditionalDetails v) =>
      v.completionPeriod;
  static const Field<ContractAdditionalDetails, int> _f$completionPeriod =
      Field('completionPeriod', _$completionPeriod, opt: true);
  static List<Description?>? _$termsAndConditions(
          ContractAdditionalDetails v) =>
      v.termsAndConditions;
  static const Field<ContractAdditionalDetails, List<Description?>>
      _f$termsAndConditions =
      Field('termsAndConditions', _$termsAndConditions, opt: true);
  static String? _$projectDesc(ContractAdditionalDetails v) => v.projectDesc;
  static const Field<ContractAdditionalDetails, String> _f$projectDesc =
      Field('projectDesc', _$projectDesc, opt: true);
  static OfficerInCharge? _$officerInChargeName(ContractAdditionalDetails v) =>
      v.officerInChargeName;
  static const Field<ContractAdditionalDetails, OfficerInCharge>
      _f$officerInChargeName =
      Field('officerInChargeName', _$officerInChargeName, opt: true);
  static String? _$officerInChargeDesgn(ContractAdditionalDetails v) =>
      v.officerInChargeDesgn;
  static const Field<ContractAdditionalDetails, String>
      _f$officerInChargeDesgn =
      Field('officerInChargeDesgn', _$officerInChargeDesgn, opt: true);
  static String? _$timeExtReason(ContractAdditionalDetails v) =>
      v.timeExtReason;
  static const Field<ContractAdditionalDetails, String> _f$timeExtReason =
      Field('timeExtReason', _$timeExtReason, opt: true);
  static String? _$timeExt(ContractAdditionalDetails v) => v.timeExt;
  static const Field<ContractAdditionalDetails, String> _f$timeExt =
      Field('timeExt', _$timeExt, opt: true);

  @override
  final MappableFields<ContractAdditionalDetails> fields = const {
    #officerInChargeId: _f$officerInChargeId,
    #attendanceRegisterNumber: _f$attendanceRegisterNumber,
    #cboOrgNumber: _f$cboOrgNumber,
    #projectId: _f$projectId,
    #projectType: _f$projectType,
    #orgName: _f$orgName,
    #projectName: _f$projectName,
    #ward: _f$ward,
    #locality: _f$locality,
    #cboCode: _f$cboCode,
    #cboName: _f$cboName,
    #estimateDocs: _f$estimateDocs,
    #estimateNumber: _f$estimateNumber,
    #totalEstimatedAmount: _f$totalEstimatedAmount,
    #completionPeriod: _f$completionPeriod,
    #termsAndConditions: _f$termsAndConditions,
    #projectDesc: _f$projectDesc,
    #officerInChargeName: _f$officerInChargeName,
    #officerInChargeDesgn: _f$officerInChargeDesgn,
    #timeExtReason: _f$timeExtReason,
    #timeExt: _f$timeExt,
  };

  static ContractAdditionalDetails _instantiate(DecodingData data) {
    return ContractAdditionalDetails(
        officerInChargeId: data.dec(_f$officerInChargeId),
        attendanceRegisterNumber: data.dec(_f$attendanceRegisterNumber),
        cboOrgNumber: data.dec(_f$cboOrgNumber),
        projectId: data.dec(_f$projectId),
        projectType: data.dec(_f$projectType),
        orgName: data.dec(_f$orgName),
        projectName: data.dec(_f$projectName),
        ward: data.dec(_f$ward),
        locality: data.dec(_f$locality),
        cboCode: data.dec(_f$cboCode),
        cboName: data.dec(_f$cboName),
        estimateDocs: data.dec(_f$estimateDocs),
        estimateNumber: data.dec(_f$estimateNumber),
        totalEstimatedAmount: data.dec(_f$totalEstimatedAmount),
        completionPeriod: data.dec(_f$completionPeriod),
        termsAndConditions: data.dec(_f$termsAndConditions),
        projectDesc: data.dec(_f$projectDesc),
        officerInChargeName: data.dec(_f$officerInChargeName),
        officerInChargeDesgn: data.dec(_f$officerInChargeDesgn),
        timeExtReason: data.dec(_f$timeExtReason),
        timeExt: data.dec(_f$timeExt));
  }

  @override
  final Function instantiate = _instantiate;

  static ContractAdditionalDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<ContractAdditionalDetails>(map);
  }

  static ContractAdditionalDetails fromJson(String json) {
    return ensureInitialized().decodeJson<ContractAdditionalDetails>(json);
  }
}

mixin ContractAdditionalDetailsMappable {
  String toJson() {
    return ContractAdditionalDetailsMapper.ensureInitialized()
        .encodeJson<ContractAdditionalDetails>(
            this as ContractAdditionalDetails);
  }

  Map<String, dynamic> toMap() {
    return ContractAdditionalDetailsMapper.ensureInitialized()
        .encodeMap<ContractAdditionalDetails>(
            this as ContractAdditionalDetails);
  }

  ContractAdditionalDetailsCopyWith<ContractAdditionalDetails,
          ContractAdditionalDetails, ContractAdditionalDetails>
      get copyWith => _ContractAdditionalDetailsCopyWithImpl(
          this as ContractAdditionalDetails, $identity, $identity);
  @override
  String toString() {
    return ContractAdditionalDetailsMapper.ensureInitialized()
        .stringifyValue(this as ContractAdditionalDetails);
  }

  @override
  bool operator ==(Object other) {
    return ContractAdditionalDetailsMapper.ensureInitialized()
        .equalsValue(this as ContractAdditionalDetails, other);
  }

  @override
  int get hashCode {
    return ContractAdditionalDetailsMapper.ensureInitialized()
        .hashValue(this as ContractAdditionalDetails);
  }
}

extension ContractAdditionalDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, ContractAdditionalDetails, $Out> {
  ContractAdditionalDetailsCopyWith<$R, ContractAdditionalDetails, $Out>
      get $asContractAdditionalDetails => $base
          .as((v, t, t2) => _ContractAdditionalDetailsCopyWithImpl(v, t, t2));
}

abstract class ContractAdditionalDetailsCopyWith<
    $R,
    $In extends ContractAdditionalDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, EstimateDocs,
      EstimateDocsCopyWith<$R, EstimateDocs, EstimateDocs>>? get estimateDocs;
  ListCopyWith<$R, Description?,
          DescriptionCopyWith<$R, Description, Description>?>?
      get termsAndConditions;
  OfficerInChargeCopyWith<$R, OfficerInCharge, OfficerInCharge>?
      get officerInChargeName;
  $R call(
      {String? officerInChargeId,
      String? attendanceRegisterNumber,
      String? cboOrgNumber,
      String? projectId,
      String? projectType,
      String? orgName,
      String? projectName,
      String? ward,
      String? locality,
      String? cboCode,
      String? cboName,
      List<EstimateDocs>? estimateDocs,
      String? estimateNumber,
      double? totalEstimatedAmount,
      int? completionPeriod,
      List<Description?>? termsAndConditions,
      String? projectDesc,
      OfficerInCharge? officerInChargeName,
      String? officerInChargeDesgn,
      String? timeExtReason,
      String? timeExt});
  ContractAdditionalDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _ContractAdditionalDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, ContractAdditionalDetails, $Out>
    implements
        ContractAdditionalDetailsCopyWith<$R, ContractAdditionalDetails, $Out> {
  _ContractAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<ContractAdditionalDetails> $mapper =
      ContractAdditionalDetailsMapper.ensureInitialized();
  @override
  ListCopyWith<$R, EstimateDocs,
          EstimateDocsCopyWith<$R, EstimateDocs, EstimateDocs>>?
      get estimateDocs => $value.estimateDocs != null
          ? ListCopyWith($value.estimateDocs!, (v, t) => v.copyWith.$chain(t),
              (v) => call(estimateDocs: v))
          : null;
  @override
  ListCopyWith<$R, Description?,
          DescriptionCopyWith<$R, Description, Description>?>?
      get termsAndConditions => $value.termsAndConditions != null
          ? ListCopyWith(
              $value.termsAndConditions!,
              (v, t) => v?.copyWith.$chain(t),
              (v) => call(termsAndConditions: v))
          : null;
  @override
  OfficerInChargeCopyWith<$R, OfficerInCharge, OfficerInCharge>?
      get officerInChargeName => $value.officerInChargeName?.copyWith
          .$chain((v) => call(officerInChargeName: v));
  @override
  $R call(
          {Object? officerInChargeId = $none,
          Object? attendanceRegisterNumber = $none,
          Object? cboOrgNumber = $none,
          Object? projectId = $none,
          Object? projectType = $none,
          Object? orgName = $none,
          Object? projectName = $none,
          Object? ward = $none,
          Object? locality = $none,
          Object? cboCode = $none,
          Object? cboName = $none,
          Object? estimateDocs = $none,
          Object? estimateNumber = $none,
          Object? totalEstimatedAmount = $none,
          Object? completionPeriod = $none,
          Object? termsAndConditions = $none,
          Object? projectDesc = $none,
          Object? officerInChargeName = $none,
          Object? officerInChargeDesgn = $none,
          Object? timeExtReason = $none,
          Object? timeExt = $none}) =>
      $apply(FieldCopyWithData({
        if (officerInChargeId != $none) #officerInChargeId: officerInChargeId,
        if (attendanceRegisterNumber != $none)
          #attendanceRegisterNumber: attendanceRegisterNumber,
        if (cboOrgNumber != $none) #cboOrgNumber: cboOrgNumber,
        if (projectId != $none) #projectId: projectId,
        if (projectType != $none) #projectType: projectType,
        if (orgName != $none) #orgName: orgName,
        if (projectName != $none) #projectName: projectName,
        if (ward != $none) #ward: ward,
        if (locality != $none) #locality: locality,
        if (cboCode != $none) #cboCode: cboCode,
        if (cboName != $none) #cboName: cboName,
        if (estimateDocs != $none) #estimateDocs: estimateDocs,
        if (estimateNumber != $none) #estimateNumber: estimateNumber,
        if (totalEstimatedAmount != $none)
          #totalEstimatedAmount: totalEstimatedAmount,
        if (completionPeriod != $none) #completionPeriod: completionPeriod,
        if (termsAndConditions != $none)
          #termsAndConditions: termsAndConditions,
        if (projectDesc != $none) #projectDesc: projectDesc,
        if (officerInChargeName != $none)
          #officerInChargeName: officerInChargeName,
        if (officerInChargeDesgn != $none)
          #officerInChargeDesgn: officerInChargeDesgn,
        if (timeExtReason != $none) #timeExtReason: timeExtReason,
        if (timeExt != $none) #timeExt: timeExt
      }));
  @override
  ContractAdditionalDetails $make(CopyWithData data) =>
      ContractAdditionalDetails(
          officerInChargeId:
              data.get(#officerInChargeId, or: $value.officerInChargeId),
          attendanceRegisterNumber: data.get(#attendanceRegisterNumber,
              or: $value.attendanceRegisterNumber),
          cboOrgNumber: data.get(#cboOrgNumber, or: $value.cboOrgNumber),
          projectId: data.get(#projectId, or: $value.projectId),
          projectType: data.get(#projectType, or: $value.projectType),
          orgName: data.get(#orgName, or: $value.orgName),
          projectName: data.get(#projectName, or: $value.projectName),
          ward: data.get(#ward, or: $value.ward),
          locality: data.get(#locality, or: $value.locality),
          cboCode: data.get(#cboCode, or: $value.cboCode),
          cboName: data.get(#cboName, or: $value.cboName),
          estimateDocs: data.get(#estimateDocs, or: $value.estimateDocs),
          estimateNumber: data.get(#estimateNumber, or: $value.estimateNumber),
          totalEstimatedAmount:
              data.get(#totalEstimatedAmount, or: $value.totalEstimatedAmount),
          completionPeriod:
              data.get(#completionPeriod, or: $value.completionPeriod),
          termsAndConditions:
              data.get(#termsAndConditions, or: $value.termsAndConditions),
          projectDesc: data.get(#projectDesc, or: $value.projectDesc),
          officerInChargeName:
              data.get(#officerInChargeName, or: $value.officerInChargeName),
          officerInChargeDesgn:
              data.get(#officerInChargeDesgn, or: $value.officerInChargeDesgn),
          timeExtReason: data.get(#timeExtReason, or: $value.timeExtReason),
          timeExt: data.get(#timeExt, or: $value.timeExt));

  @override
  ContractAdditionalDetailsCopyWith<$R2, ContractAdditionalDetails, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _ContractAdditionalDetailsCopyWithImpl($value, $cast, t);
}

class EstimateDocsMapper extends ClassMapperBase<EstimateDocs> {
  EstimateDocsMapper._();

  static EstimateDocsMapper? _instance;
  static EstimateDocsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = EstimateDocsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'EstimateDocs';

  static String? _$tenantId(EstimateDocs v) => v.tenantId;
  static const Field<EstimateDocs, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static String? _$documentUid(EstimateDocs v) => v.documentUid;
  static const Field<EstimateDocs, String> _f$documentUid =
      Field('documentUid', _$documentUid, opt: true);
  static String? _$fileStoreId(EstimateDocs v) => v.fileStoreId;
  static const Field<EstimateDocs, String> _f$fileStoreId =
      Field('fileStoreId', _$fileStoreId, opt: true);
  static String? _$fileName(EstimateDocs v) => v.fileName;
  static const Field<EstimateDocs, String> _f$fileName =
      Field('fileName', _$fileName, opt: true);
  static String? _$fileType(EstimateDocs v) => v.fileType;
  static const Field<EstimateDocs, String> _f$fileType =
      Field('fileType', _$fileType, opt: true);

  @override
  final MappableFields<EstimateDocs> fields = const {
    #tenantId: _f$tenantId,
    #documentUid: _f$documentUid,
    #fileStoreId: _f$fileStoreId,
    #fileName: _f$fileName,
    #fileType: _f$fileType,
  };

  static EstimateDocs _instantiate(DecodingData data) {
    return EstimateDocs(
        tenantId: data.dec(_f$tenantId),
        documentUid: data.dec(_f$documentUid),
        fileStoreId: data.dec(_f$fileStoreId),
        fileName: data.dec(_f$fileName),
        fileType: data.dec(_f$fileType));
  }

  @override
  final Function instantiate = _instantiate;

  static EstimateDocs fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<EstimateDocs>(map);
  }

  static EstimateDocs fromJson(String json) {
    return ensureInitialized().decodeJson<EstimateDocs>(json);
  }
}

mixin EstimateDocsMappable {
  String toJson() {
    return EstimateDocsMapper.ensureInitialized()
        .encodeJson<EstimateDocs>(this as EstimateDocs);
  }

  Map<String, dynamic> toMap() {
    return EstimateDocsMapper.ensureInitialized()
        .encodeMap<EstimateDocs>(this as EstimateDocs);
  }

  EstimateDocsCopyWith<EstimateDocs, EstimateDocs, EstimateDocs> get copyWith =>
      _EstimateDocsCopyWithImpl(this as EstimateDocs, $identity, $identity);
  @override
  String toString() {
    return EstimateDocsMapper.ensureInitialized()
        .stringifyValue(this as EstimateDocs);
  }

  @override
  bool operator ==(Object other) {
    return EstimateDocsMapper.ensureInitialized()
        .equalsValue(this as EstimateDocs, other);
  }

  @override
  int get hashCode {
    return EstimateDocsMapper.ensureInitialized()
        .hashValue(this as EstimateDocs);
  }
}

extension EstimateDocsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, EstimateDocs, $Out> {
  EstimateDocsCopyWith<$R, EstimateDocs, $Out> get $asEstimateDocs =>
      $base.as((v, t, t2) => _EstimateDocsCopyWithImpl(v, t, t2));
}

abstract class EstimateDocsCopyWith<$R, $In extends EstimateDocs, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {String? tenantId,
      String? documentUid,
      String? fileStoreId,
      String? fileName,
      String? fileType});
  EstimateDocsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _EstimateDocsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, EstimateDocs, $Out>
    implements EstimateDocsCopyWith<$R, EstimateDocs, $Out> {
  _EstimateDocsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<EstimateDocs> $mapper =
      EstimateDocsMapper.ensureInitialized();
  @override
  $R call(
          {Object? tenantId = $none,
          Object? documentUid = $none,
          Object? fileStoreId = $none,
          Object? fileName = $none,
          Object? fileType = $none}) =>
      $apply(FieldCopyWithData({
        if (tenantId != $none) #tenantId: tenantId,
        if (documentUid != $none) #documentUid: documentUid,
        if (fileStoreId != $none) #fileStoreId: fileStoreId,
        if (fileName != $none) #fileName: fileName,
        if (fileType != $none) #fileType: fileType
      }));
  @override
  EstimateDocs $make(CopyWithData data) => EstimateDocs(
      tenantId: data.get(#tenantId, or: $value.tenantId),
      documentUid: data.get(#documentUid, or: $value.documentUid),
      fileStoreId: data.get(#fileStoreId, or: $value.fileStoreId),
      fileName: data.get(#fileName, or: $value.fileName),
      fileType: data.get(#fileType, or: $value.fileType));

  @override
  EstimateDocsCopyWith<$R2, EstimateDocs, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _EstimateDocsCopyWithImpl($value, $cast, t);
}

class DescriptionMapper extends ClassMapperBase<Description> {
  DescriptionMapper._();

  static DescriptionMapper? _instance;
  static DescriptionMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = DescriptionMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'Description';

  static String? _$description(Description v) => v.description;
  static const Field<Description, String> _f$description =
      Field('description', _$description, opt: true);

  @override
  final MappableFields<Description> fields = const {
    #description: _f$description,
  };

  static Description _instantiate(DecodingData data) {
    return Description(description: data.dec(_f$description));
  }

  @override
  final Function instantiate = _instantiate;

  static Description fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<Description>(map);
  }

  static Description fromJson(String json) {
    return ensureInitialized().decodeJson<Description>(json);
  }
}

mixin DescriptionMappable {
  String toJson() {
    return DescriptionMapper.ensureInitialized()
        .encodeJson<Description>(this as Description);
  }

  Map<String, dynamic> toMap() {
    return DescriptionMapper.ensureInitialized()
        .encodeMap<Description>(this as Description);
  }

  DescriptionCopyWith<Description, Description, Description> get copyWith =>
      _DescriptionCopyWithImpl(this as Description, $identity, $identity);
  @override
  String toString() {
    return DescriptionMapper.ensureInitialized()
        .stringifyValue(this as Description);
  }

  @override
  bool operator ==(Object other) {
    return DescriptionMapper.ensureInitialized()
        .equalsValue(this as Description, other);
  }

  @override
  int get hashCode {
    return DescriptionMapper.ensureInitialized().hashValue(this as Description);
  }
}

extension DescriptionValueCopy<$R, $Out>
    on ObjectCopyWith<$R, Description, $Out> {
  DescriptionCopyWith<$R, Description, $Out> get $asDescription =>
      $base.as((v, t, t2) => _DescriptionCopyWithImpl(v, t, t2));
}

abstract class DescriptionCopyWith<$R, $In extends Description, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? description});
  DescriptionCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _DescriptionCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, Description, $Out>
    implements DescriptionCopyWith<$R, Description, $Out> {
  _DescriptionCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<Description> $mapper =
      DescriptionMapper.ensureInitialized();
  @override
  $R call({Object? description = $none}) => $apply(
      FieldCopyWithData({if (description != $none) #description: description}));
  @override
  Description $make(CopyWithData data) =>
      Description(description: data.get(#description, or: $value.description));

  @override
  DescriptionCopyWith<$R2, Description, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _DescriptionCopyWithImpl($value, $cast, t);
}

class OfficerInChargeMapper extends ClassMapperBase<OfficerInCharge> {
  OfficerInChargeMapper._();

  static OfficerInChargeMapper? _instance;
  static OfficerInChargeMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = OfficerInChargeMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'OfficerInCharge';

  static String? _$code(OfficerInCharge v) => v.code;
  static const Field<OfficerInCharge, String> _f$code =
      Field('code', _$code, opt: true);
  static String? _$name(OfficerInCharge v) => v.name;
  static const Field<OfficerInCharge, String> _f$name =
      Field('name', _$name, opt: true);

  @override
  final MappableFields<OfficerInCharge> fields = const {
    #code: _f$code,
    #name: _f$name,
  };

  static OfficerInCharge _instantiate(DecodingData data) {
    return OfficerInCharge(code: data.dec(_f$code), name: data.dec(_f$name));
  }

  @override
  final Function instantiate = _instantiate;

  static OfficerInCharge fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<OfficerInCharge>(map);
  }

  static OfficerInCharge fromJson(String json) {
    return ensureInitialized().decodeJson<OfficerInCharge>(json);
  }
}

mixin OfficerInChargeMappable {
  String toJson() {
    return OfficerInChargeMapper.ensureInitialized()
        .encodeJson<OfficerInCharge>(this as OfficerInCharge);
  }

  Map<String, dynamic> toMap() {
    return OfficerInChargeMapper.ensureInitialized()
        .encodeMap<OfficerInCharge>(this as OfficerInCharge);
  }

  OfficerInChargeCopyWith<OfficerInCharge, OfficerInCharge, OfficerInCharge>
      get copyWith => _OfficerInChargeCopyWithImpl(
          this as OfficerInCharge, $identity, $identity);
  @override
  String toString() {
    return OfficerInChargeMapper.ensureInitialized()
        .stringifyValue(this as OfficerInCharge);
  }

  @override
  bool operator ==(Object other) {
    return OfficerInChargeMapper.ensureInitialized()
        .equalsValue(this as OfficerInCharge, other);
  }

  @override
  int get hashCode {
    return OfficerInChargeMapper.ensureInitialized()
        .hashValue(this as OfficerInCharge);
  }
}

extension OfficerInChargeValueCopy<$R, $Out>
    on ObjectCopyWith<$R, OfficerInCharge, $Out> {
  OfficerInChargeCopyWith<$R, OfficerInCharge, $Out> get $asOfficerInCharge =>
      $base.as((v, t, t2) => _OfficerInChargeCopyWithImpl(v, t, t2));
}

abstract class OfficerInChargeCopyWith<$R, $In extends OfficerInCharge, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? code, String? name});
  OfficerInChargeCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _OfficerInChargeCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, OfficerInCharge, $Out>
    implements OfficerInChargeCopyWith<$R, OfficerInCharge, $Out> {
  _OfficerInChargeCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<OfficerInCharge> $mapper =
      OfficerInChargeMapper.ensureInitialized();
  @override
  $R call({Object? code = $none, Object? name = $none}) =>
      $apply(FieldCopyWithData(
          {if (code != $none) #code: code, if (name != $none) #name: name}));
  @override
  OfficerInCharge $make(CopyWithData data) => OfficerInCharge(
      code: data.get(#code, or: $value.code),
      name: data.get(#name, or: $value.name));

  @override
  OfficerInChargeCopyWith<$R2, OfficerInCharge, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _OfficerInChargeCopyWithImpl($value, $cast, t);
}

class ORGAdditionalDetailsMapper extends ClassMapperBase<ORGAdditionalDetails> {
  ORGAdditionalDetailsMapper._();

  static ORGAdditionalDetailsMapper? _instance;
  static ORGAdditionalDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = ORGAdditionalDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'ORGAdditionalDetails';

  static String? _$deptRegistrationNum(ORGAdditionalDetails v) =>
      v.deptRegistrationNum;
  static const Field<ORGAdditionalDetails, String> _f$deptRegistrationNum =
      Field('deptRegistrationNum', _$deptRegistrationNum, opt: true);
  static String? _$registeredByDept(ORGAdditionalDetails v) =>
      v.registeredByDept;
  static const Field<ORGAdditionalDetails, String> _f$registeredByDept =
      Field('registeredByDept', _$registeredByDept, opt: true);

  @override
  final MappableFields<ORGAdditionalDetails> fields = const {
    #deptRegistrationNum: _f$deptRegistrationNum,
    #registeredByDept: _f$registeredByDept,
  };

  static ORGAdditionalDetails _instantiate(DecodingData data) {
    return ORGAdditionalDetails(
        deptRegistrationNum: data.dec(_f$deptRegistrationNum),
        registeredByDept: data.dec(_f$registeredByDept));
  }

  @override
  final Function instantiate = _instantiate;

  static ORGAdditionalDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<ORGAdditionalDetails>(map);
  }

  static ORGAdditionalDetails fromJson(String json) {
    return ensureInitialized().decodeJson<ORGAdditionalDetails>(json);
  }
}

mixin ORGAdditionalDetailsMappable {
  String toJson() {
    return ORGAdditionalDetailsMapper.ensureInitialized()
        .encodeJson<ORGAdditionalDetails>(this as ORGAdditionalDetails);
  }

  Map<String, dynamic> toMap() {
    return ORGAdditionalDetailsMapper.ensureInitialized()
        .encodeMap<ORGAdditionalDetails>(this as ORGAdditionalDetails);
  }

  ORGAdditionalDetailsCopyWith<ORGAdditionalDetails, ORGAdditionalDetails,
          ORGAdditionalDetails>
      get copyWith => _ORGAdditionalDetailsCopyWithImpl(
          this as ORGAdditionalDetails, $identity, $identity);
  @override
  String toString() {
    return ORGAdditionalDetailsMapper.ensureInitialized()
        .stringifyValue(this as ORGAdditionalDetails);
  }

  @override
  bool operator ==(Object other) {
    return ORGAdditionalDetailsMapper.ensureInitialized()
        .equalsValue(this as ORGAdditionalDetails, other);
  }

  @override
  int get hashCode {
    return ORGAdditionalDetailsMapper.ensureInitialized()
        .hashValue(this as ORGAdditionalDetails);
  }
}

extension ORGAdditionalDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, ORGAdditionalDetails, $Out> {
  ORGAdditionalDetailsCopyWith<$R, ORGAdditionalDetails, $Out>
      get $asORGAdditionalDetails =>
          $base.as((v, t, t2) => _ORGAdditionalDetailsCopyWithImpl(v, t, t2));
}

abstract class ORGAdditionalDetailsCopyWith<
    $R,
    $In extends ORGAdditionalDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? deptRegistrationNum, String? registeredByDept});
  ORGAdditionalDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _ORGAdditionalDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, ORGAdditionalDetails, $Out>
    implements ORGAdditionalDetailsCopyWith<$R, ORGAdditionalDetails, $Out> {
  _ORGAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<ORGAdditionalDetails> $mapper =
      ORGAdditionalDetailsMapper.ensureInitialized();
  @override
  $R call(
          {Object? deptRegistrationNum = $none,
          Object? registeredByDept = $none}) =>
      $apply(FieldCopyWithData({
        if (deptRegistrationNum != $none)
          #deptRegistrationNum: deptRegistrationNum,
        if (registeredByDept != $none) #registeredByDept: registeredByDept
      }));
  @override
  ORGAdditionalDetails $make(CopyWithData data) => ORGAdditionalDetails(
      deptRegistrationNum:
          data.get(#deptRegistrationNum, or: $value.deptRegistrationNum),
      registeredByDept:
          data.get(#registeredByDept, or: $value.registeredByDept));

  @override
  ORGAdditionalDetailsCopyWith<$R2, ORGAdditionalDetails, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _ORGAdditionalDetailsCopyWithImpl($value, $cast, t);
}
