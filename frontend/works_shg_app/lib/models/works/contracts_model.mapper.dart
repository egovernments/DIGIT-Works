// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'contracts_model.dart';

class ContractsModelMapper extends MapperBase<ContractsModel> {
  static MapperContainer container = MapperContainer(
    mappers: {ContractsModelMapper()},
  )..linkAll({ContractsMapper.container});

  @override
  ContractsModelMapperElement createElement(MapperContainer container) {
    return ContractsModelMapperElement._(this, container);
  }

  @override
  String get id => 'ContractsModel';

  static final fromMap = container.fromMap<ContractsModel>;
  static final fromJson = container.fromJson<ContractsModel>;
}

class ContractsModelMapperElement extends MapperElementBase<ContractsModel> {
  ContractsModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ContractsModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ContractsModel fromMap(Map<String, dynamic> map) =>
      ContractsModel(contracts: container.$getOpt(map, 'contracts'));

  @override
  Function get encoder => encode;
  dynamic encode(ContractsModel v) => toMap(v);
  Map<String, dynamic> toMap(ContractsModel c) =>
      {'contracts': container.$enc(c.contracts, 'contracts')};

  @override
  String stringify(ContractsModel self) =>
      'ContractsModel(contracts: ${container.asString(self.contracts)})';
  @override
  int hash(ContractsModel self) => container.hash(self.contracts);
  @override
  bool equals(ContractsModel self, ContractsModel other) =>
      container.isEqual(self.contracts, other.contracts);
}

mixin ContractsModelMappable {
  String toJson() =>
      ContractsModelMapper.container.toJson(this as ContractsModel);
  Map<String, dynamic> toMap() =>
      ContractsModelMapper.container.toMap(this as ContractsModel);
  ContractsModelCopyWith<ContractsModel, ContractsModel, ContractsModel>
      get copyWith => _ContractsModelCopyWithImpl(
          this as ContractsModel, $identity, $identity);
  @override
  String toString() => ContractsModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ContractsModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => ContractsModelMapper.container.hash(this);
}

extension ContractsModelValueCopy<$R, $Out extends ContractsModel>
    on ObjectCopyWith<$R, ContractsModel, $Out> {
  ContractsModelCopyWith<$R, ContractsModel, $Out> get asContractsModel =>
      base.as((v, t, t2) => _ContractsModelCopyWithImpl(v, t, t2));
}

typedef ContractsModelCopyWithBound = ContractsModel;

abstract class ContractsModelCopyWith<$R, $In extends ContractsModel,
    $Out extends ContractsModel> implements ObjectCopyWith<$R, $In, $Out> {
  ContractsModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ContractsModel>(
          Then<ContractsModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, Contracts, ContractsCopyWith<$R, Contracts, Contracts>>?
      get contracts;
  $R call({List<Contracts>? contracts});
}

class _ContractsModelCopyWithImpl<$R, $Out extends ContractsModel>
    extends CopyWithBase<$R, ContractsModel, $Out>
    implements ContractsModelCopyWith<$R, ContractsModel, $Out> {
  _ContractsModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  ContractsModelCopyWith<$R2, ContractsModel, $Out2>
      chain<$R2, $Out2 extends ContractsModel>(
              Then<ContractsModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _ContractsModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, Contracts, ContractsCopyWith<$R, Contracts, Contracts>>?
      get contracts => $value.contracts != null
          ? ListCopyWith(
              $value.contracts!,
              (v, t) => v.copyWith.chain<$R, Contracts>($identity, t),
              (v) => call(contracts: v))
          : null;
  @override
  $R call({Object? contracts = $none}) =>
      $then(ContractsModel(contracts: or(contracts, $value.contracts)));
}

class ContractsMapper extends MapperBase<Contracts> {
  static MapperContainer container = MapperContainer(
    mappers: {ContractsMapper()},
  )..linkAll({
      LineItemsMapper.container,
      DocumentsMapper.container,
      ContractProcessInstanceMapper.container,
      ContractAdditionalDetailsMapper.container,
      ContractAuditDetailsMapper.container,
    });

  @override
  ContractsMapperElement createElement(MapperContainer container) {
    return ContractsMapperElement._(this, container);
  }

  @override
  String get id => 'Contracts';

  static final fromMap = container.fromMap<Contracts>;
  static final fromJson = container.fromJson<Contracts>;
}

class ContractsMapperElement extends MapperElementBase<Contracts> {
  ContractsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  Contracts decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  Contracts fromMap(Map<String, dynamic> map) => Contracts(
      id: container.$getOpt(map, 'id'),
      contractNumber: container.$getOpt(map, 'contractNumber'),
      tenantId: container.$getOpt(map, 'tenantId'),
      status: container.$getOpt(map, 'status'),
      endDate: container.$getOpt(map, 'endDate'),
      startDate: container.$getOpt(map, 'startDate'),
      agreementDate: container.$getOpt(map, 'agreementDate'),
      completionPeriod: container.$getOpt(map, 'completionPeriod'),
      contractType: container.$getOpt(map, 'contractType'),
      defectLiabilityPeriod: container.$getOpt(map, 'defectLiabilityPeriod'),
      executingAuthority: container.$getOpt(map, 'executingAuthority'),
      issueDate: container.$getOpt(map, 'issueDate'),
      orgId: container.$getOpt(map, 'orgId'),
      securityDeposit: container.$getOpt(map, 'securityDeposit'),
      wfStatus: container.$getOpt(map, 'wfStatus'),
      totalContractedAmount: container.$getOpt(map, 'totalContractedAmount'),
      lineItems: container.$getOpt(map, 'lineItems'),
      documents: container.$getOpt(map, 'documents'),
      processInstance: container.$getOpt(map, 'processInstance'),
      additionalDetails: container.$getOpt(map, 'additionalDetails'),
      auditDetails: container.$getOpt(map, 'auditDetails'));

  @override
  Function get encoder => encode;
  dynamic encode(Contracts v) => toMap(v);
  Map<String, dynamic> toMap(Contracts c) => {
        'id': container.$enc(c.id, 'id'),
        'contractNumber': container.$enc(c.contractNumber, 'contractNumber'),
        'tenantId': container.$enc(c.tenantId, 'tenantId'),
        'status': container.$enc(c.status, 'status'),
        'endDate': container.$enc(c.endDate, 'endDate'),
        'startDate': container.$enc(c.startDate, 'startDate'),
        'agreementDate': container.$enc(c.agreementDate, 'agreementDate'),
        'completionPeriod':
            container.$enc(c.completionPeriod, 'completionPeriod'),
        'contractType': container.$enc(c.contractType, 'contractType'),
        'defectLiabilityPeriod':
            container.$enc(c.defectLiabilityPeriod, 'defectLiabilityPeriod'),
        'executingAuthority':
            container.$enc(c.executingAuthority, 'executingAuthority'),
        'issueDate': container.$enc(c.issueDate, 'issueDate'),
        'orgId': container.$enc(c.orgId, 'orgId'),
        'securityDeposit': container.$enc(c.securityDeposit, 'securityDeposit'),
        'wfStatus': container.$enc(c.wfStatus, 'wfStatus'),
        'totalContractedAmount':
            container.$enc(c.totalContractedAmount, 'totalContractedAmount'),
        'lineItems': container.$enc(c.lineItems, 'lineItems'),
        'documents': container.$enc(c.documents, 'documents'),
        'processInstance': container.$enc(c.processInstance, 'processInstance'),
        'additionalDetails':
            container.$enc(c.additionalDetails, 'additionalDetails'),
        'auditDetails': container.$enc(c.auditDetails, 'auditDetails')
      };

  @override
  String stringify(Contracts self) =>
      'Contracts(id: ${container.asString(self.id)}, contractNumber: ${container.asString(self.contractNumber)}, tenantId: ${container.asString(self.tenantId)}, wfStatus: ${container.asString(self.wfStatus)}, executingAuthority: ${container.asString(self.executingAuthority)}, contractType: ${container.asString(self.contractType)}, totalContractedAmount: ${container.asString(self.totalContractedAmount)}, securityDeposit: ${container.asString(self.securityDeposit)}, agreementDate: ${container.asString(self.agreementDate)}, issueDate: ${container.asString(self.issueDate)}, defectLiabilityPeriod: ${container.asString(self.defectLiabilityPeriod)}, orgId: ${container.asString(self.orgId)}, startDate: ${container.asString(self.startDate)}, endDate: ${container.asString(self.endDate)}, completionPeriod: ${container.asString(self.completionPeriod)}, status: ${container.asString(self.status)}, lineItems: ${container.asString(self.lineItems)}, documents: ${container.asString(self.documents)}, auditDetails: ${container.asString(self.auditDetails)}, additionalDetails: ${container.asString(self.additionalDetails)}, processInstance: ${container.asString(self.processInstance)})';
  @override
  int hash(Contracts self) =>
      container.hash(self.id) ^
      container.hash(self.contractNumber) ^
      container.hash(self.tenantId) ^
      container.hash(self.wfStatus) ^
      container.hash(self.executingAuthority) ^
      container.hash(self.contractType) ^
      container.hash(self.totalContractedAmount) ^
      container.hash(self.securityDeposit) ^
      container.hash(self.agreementDate) ^
      container.hash(self.issueDate) ^
      container.hash(self.defectLiabilityPeriod) ^
      container.hash(self.orgId) ^
      container.hash(self.startDate) ^
      container.hash(self.endDate) ^
      container.hash(self.completionPeriod) ^
      container.hash(self.status) ^
      container.hash(self.lineItems) ^
      container.hash(self.documents) ^
      container.hash(self.auditDetails) ^
      container.hash(self.additionalDetails) ^
      container.hash(self.processInstance);
  @override
  bool equals(Contracts self, Contracts other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.contractNumber, other.contractNumber) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.wfStatus, other.wfStatus) &&
      container.isEqual(self.executingAuthority, other.executingAuthority) &&
      container.isEqual(self.contractType, other.contractType) &&
      container.isEqual(
          self.totalContractedAmount, other.totalContractedAmount) &&
      container.isEqual(self.securityDeposit, other.securityDeposit) &&
      container.isEqual(self.agreementDate, other.agreementDate) &&
      container.isEqual(self.issueDate, other.issueDate) &&
      container.isEqual(
          self.defectLiabilityPeriod, other.defectLiabilityPeriod) &&
      container.isEqual(self.orgId, other.orgId) &&
      container.isEqual(self.startDate, other.startDate) &&
      container.isEqual(self.endDate, other.endDate) &&
      container.isEqual(self.completionPeriod, other.completionPeriod) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.lineItems, other.lineItems) &&
      container.isEqual(self.documents, other.documents) &&
      container.isEqual(self.auditDetails, other.auditDetails) &&
      container.isEqual(self.additionalDetails, other.additionalDetails) &&
      container.isEqual(self.processInstance, other.processInstance);
}

mixin ContractsMappable {
  String toJson() => ContractsMapper.container.toJson(this as Contracts);
  Map<String, dynamic> toMap() =>
      ContractsMapper.container.toMap(this as Contracts);
  ContractsCopyWith<Contracts, Contracts, Contracts> get copyWith =>
      _ContractsCopyWithImpl(this as Contracts, $identity, $identity);
  @override
  String toString() => ContractsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ContractsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ContractsMapper.container.hash(this);
}

extension ContractsValueCopy<$R, $Out extends Contracts>
    on ObjectCopyWith<$R, Contracts, $Out> {
  ContractsCopyWith<$R, Contracts, $Out> get asContracts =>
      base.as((v, t, t2) => _ContractsCopyWithImpl(v, t, t2));
}

typedef ContractsCopyWithBound = Contracts;

abstract class ContractsCopyWith<$R, $In extends Contracts,
    $Out extends Contracts> implements ObjectCopyWith<$R, $In, $Out> {
  ContractsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends Contracts>(
      Then<Contracts, $Out2> t, Then<$Out2, $R2> t2);
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
      ContractAuditDetails? auditDetails});
}

class _ContractsCopyWithImpl<$R, $Out extends Contracts>
    extends CopyWithBase<$R, Contracts, $Out>
    implements ContractsCopyWith<$R, Contracts, $Out> {
  _ContractsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ContractsCopyWith<$R2, Contracts, $Out2> chain<$R2, $Out2 extends Contracts>(
          Then<Contracts, $Out2> t, Then<$Out2, $R2> t2) =>
      _ContractsCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, LineItems, LineItemsCopyWith<$R, LineItems, LineItems>>?
      get lineItems => $value.lineItems != null
          ? ListCopyWith(
              $value.lineItems!,
              (v, t) => v.copyWith.chain<$R, LineItems>($identity, t),
              (v) => call(lineItems: v))
          : null;
  @override
  ListCopyWith<$R, Documents, DocumentsCopyWith<$R, Documents, Documents>>?
      get documents => $value.documents != null
          ? ListCopyWith(
              $value.documents!,
              (v, t) => v.copyWith.chain<$R, Documents>($identity, t),
              (v) => call(documents: v))
          : null;
  @override
  ContractProcessInstanceCopyWith<$R, ContractProcessInstance,
          ContractProcessInstance>?
      get processInstance => $value.processInstance?.copyWith
          .chain($identity, (v) => call(processInstance: v));
  @override
  ContractAdditionalDetailsCopyWith<$R, ContractAdditionalDetails,
          ContractAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .chain($identity, (v) => call(additionalDetails: v));
  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
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
          Object? auditDetails = $none}) =>
      $then(Contracts(
          id: or(id, $value.id),
          contractNumber: or(contractNumber, $value.contractNumber),
          tenantId: or(tenantId, $value.tenantId),
          status: or(status, $value.status),
          endDate: or(endDate, $value.endDate),
          startDate: or(startDate, $value.startDate),
          agreementDate: or(agreementDate, $value.agreementDate),
          completionPeriod: or(completionPeriod, $value.completionPeriod),
          contractType: or(contractType, $value.contractType),
          defectLiabilityPeriod:
              or(defectLiabilityPeriod, $value.defectLiabilityPeriod),
          executingAuthority: or(executingAuthority, $value.executingAuthority),
          issueDate: or(issueDate, $value.issueDate),
          orgId: or(orgId, $value.orgId),
          securityDeposit: or(securityDeposit, $value.securityDeposit),
          wfStatus: or(wfStatus, $value.wfStatus),
          totalContractedAmount:
              or(totalContractedAmount, $value.totalContractedAmount),
          lineItems: or(lineItems, $value.lineItems),
          documents: or(documents, $value.documents),
          processInstance: or(processInstance, $value.processInstance),
          additionalDetails: or(additionalDetails, $value.additionalDetails),
          auditDetails: or(auditDetails, $value.auditDetails)));
}

class LineItemsMapper extends MapperBase<LineItems> {
  static MapperContainer container = MapperContainer(
    mappers: {LineItemsMapper()},
  )..linkAll(
      {ContractAuditDetailsMapper.container, AmountBreakupsMapper.container});

  @override
  LineItemsMapperElement createElement(MapperContainer container) {
    return LineItemsMapperElement._(this, container);
  }

  @override
  String get id => 'LineItems';

  static final fromMap = container.fromMap<LineItems>;
  static final fromJson = container.fromJson<LineItems>;
}

class LineItemsMapperElement extends MapperElementBase<LineItems> {
  LineItemsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  LineItems decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  LineItems fromMap(Map<String, dynamic> map) => LineItems(
      id: container.$getOpt(map, 'id'),
      status: container.$getOpt(map, 'status'),
      tenantId: container.$getOpt(map, 'tenantId'),
      name: container.$getOpt(map, 'name'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      amountBreakups: container.$getOpt(map, 'amountBreakups'),
      category: container.$getOpt(map, 'category'),
      estimateId: container.$getOpt(map, 'estimateId'),
      estimateLineItemId: container.$getOpt(map, 'estimateLineItemId'),
      noOfunit: container.$getOpt(map, 'noOfunit'),
      unitRate: container.$getOpt(map, 'unitRate'));

  @override
  Function get encoder => encode;
  dynamic encode(LineItems v) => toMap(v);
  Map<String, dynamic> toMap(LineItems l) => {
        'id': container.$enc(l.id, 'id'),
        'status': container.$enc(l.status, 'status'),
        'tenantId': container.$enc(l.tenantId, 'tenantId'),
        'name': container.$enc(l.name, 'name'),
        'auditDetails': container.$enc(l.auditDetails, 'auditDetails'),
        'amountBreakups': container.$enc(l.amountBreakups, 'amountBreakups'),
        'category': container.$enc(l.category, 'category'),
        'estimateId': container.$enc(l.estimateId, 'estimateId'),
        'estimateLineItemId':
            container.$enc(l.estimateLineItemId, 'estimateLineItemId'),
        'noOfunit': container.$enc(l.noOfunit, 'noOfunit'),
        'unitRate': container.$enc(l.unitRate, 'unitRate')
      };

  @override
  String stringify(LineItems self) =>
      'LineItems(id: ${container.asString(self.id)}, estimateId: ${container.asString(self.estimateId)}, estimateLineItemId: ${container.asString(self.estimateLineItemId)}, tenantId: ${container.asString(self.tenantId)}, unitRate: ${container.asString(self.unitRate)}, noOfunit: ${container.asString(self.noOfunit)}, category: ${container.asString(self.category)}, name: ${container.asString(self.name)}, status: ${container.asString(self.status)}, amountBreakups: ${container.asString(self.amountBreakups)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(LineItems self) =>
      container.hash(self.id) ^
      container.hash(self.estimateId) ^
      container.hash(self.estimateLineItemId) ^
      container.hash(self.tenantId) ^
      container.hash(self.unitRate) ^
      container.hash(self.noOfunit) ^
      container.hash(self.category) ^
      container.hash(self.name) ^
      container.hash(self.status) ^
      container.hash(self.amountBreakups) ^
      container.hash(self.auditDetails);
  @override
  bool equals(LineItems self, LineItems other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.estimateId, other.estimateId) &&
      container.isEqual(self.estimateLineItemId, other.estimateLineItemId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.unitRate, other.unitRate) &&
      container.isEqual(self.noOfunit, other.noOfunit) &&
      container.isEqual(self.category, other.category) &&
      container.isEqual(self.name, other.name) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.amountBreakups, other.amountBreakups) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin LineItemsMappable {
  String toJson() => LineItemsMapper.container.toJson(this as LineItems);
  Map<String, dynamic> toMap() =>
      LineItemsMapper.container.toMap(this as LineItems);
  LineItemsCopyWith<LineItems, LineItems, LineItems> get copyWith =>
      _LineItemsCopyWithImpl(this as LineItems, $identity, $identity);
  @override
  String toString() => LineItemsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          LineItemsMapper.container.isEqual(this, other));
  @override
  int get hashCode => LineItemsMapper.container.hash(this);
}

extension LineItemsValueCopy<$R, $Out extends LineItems>
    on ObjectCopyWith<$R, LineItems, $Out> {
  LineItemsCopyWith<$R, LineItems, $Out> get asLineItems =>
      base.as((v, t, t2) => _LineItemsCopyWithImpl(v, t, t2));
}

typedef LineItemsCopyWithBound = LineItems;

abstract class LineItemsCopyWith<$R, $In extends LineItems,
    $Out extends LineItems> implements ObjectCopyWith<$R, $In, $Out> {
  LineItemsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends LineItems>(
      Then<LineItems, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _LineItemsCopyWithImpl<$R, $Out extends LineItems>
    extends CopyWithBase<$R, LineItems, $Out>
    implements LineItemsCopyWith<$R, LineItems, $Out> {
  _LineItemsCopyWithImpl(super.value, super.then, super.then2);
  @override
  LineItemsCopyWith<$R2, LineItems, $Out2> chain<$R2, $Out2 extends LineItems>(
          Then<LineItems, $Out2> t, Then<$Out2, $R2> t2) =>
      _LineItemsCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, AmountBreakups,
          AmountBreakupsCopyWith<$R, AmountBreakups, AmountBreakups>>?
      get amountBreakups => $value.amountBreakups != null
          ? ListCopyWith(
              $value.amountBreakups!,
              (v, t) => v.copyWith.chain<$R, AmountBreakups>($identity, t),
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
      $then(LineItems(
          id: or(id, $value.id),
          status: or(status, $value.status),
          tenantId: or(tenantId, $value.tenantId),
          name: or(name, $value.name),
          auditDetails: or(auditDetails, $value.auditDetails),
          amountBreakups: or(amountBreakups, $value.amountBreakups),
          category: or(category, $value.category),
          estimateId: or(estimateId, $value.estimateId),
          estimateLineItemId: or(estimateLineItemId, $value.estimateLineItemId),
          noOfunit: or(noOfunit, $value.noOfunit),
          unitRate: or(unitRate, $value.unitRate)));
}

class ContractProcessInstanceMapper
    extends MapperBase<ContractProcessInstance> {
  static MapperContainer container = MapperContainer(
    mappers: {ContractProcessInstanceMapper()},
  )..linkAll({ContractStateMapper.container});

  @override
  ContractProcessInstanceMapperElement createElement(
      MapperContainer container) {
    return ContractProcessInstanceMapperElement._(this, container);
  }

  @override
  String get id => 'ContractProcessInstance';

  static final fromMap = container.fromMap<ContractProcessInstance>;
  static final fromJson = container.fromJson<ContractProcessInstance>;
}

class ContractProcessInstanceMapperElement
    extends MapperElementBase<ContractProcessInstance> {
  ContractProcessInstanceMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ContractProcessInstance decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ContractProcessInstance fromMap(Map<String, dynamic> map) =>
      ContractProcessInstance(
          action: container.$getOpt(map, 'action'),
          tenantId: container.$getOpt(map, 'tenantId'),
          state: container.$getOpt(map, 'state'),
          id: container.$getOpt(map, 'id'),
          businessId: container.$getOpt(map, 'businessId'),
          businessService: container.$getOpt(map, 'businessService'),
          moduleName: container.$getOpt(map, 'moduleName'));

  @override
  Function get encoder => encode;
  dynamic encode(ContractProcessInstance v) => toMap(v);
  Map<String, dynamic> toMap(ContractProcessInstance c) => {
        'action': container.$enc(c.action, 'action'),
        'tenantId': container.$enc(c.tenantId, 'tenantId'),
        'state': container.$enc(c.state, 'state'),
        'id': container.$enc(c.id, 'id'),
        'businessId': container.$enc(c.businessId, 'businessId'),
        'businessService': container.$enc(c.businessService, 'businessService'),
        'moduleName': container.$enc(c.moduleName, 'moduleName')
      };

  @override
  String stringify(ContractProcessInstance self) =>
      'ContractProcessInstance(id: ${container.asString(self.id)}, tenantId: ${container.asString(self.tenantId)}, businessService: ${container.asString(self.businessService)}, businessId: ${container.asString(self.businessId)}, action: ${container.asString(self.action)}, moduleName: ${container.asString(self.moduleName)}, state: ${container.asString(self.state)})';
  @override
  int hash(ContractProcessInstance self) =>
      container.hash(self.id) ^
      container.hash(self.tenantId) ^
      container.hash(self.businessService) ^
      container.hash(self.businessId) ^
      container.hash(self.action) ^
      container.hash(self.moduleName) ^
      container.hash(self.state);
  @override
  bool equals(ContractProcessInstance self, ContractProcessInstance other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.businessService, other.businessService) &&
      container.isEqual(self.businessId, other.businessId) &&
      container.isEqual(self.action, other.action) &&
      container.isEqual(self.moduleName, other.moduleName) &&
      container.isEqual(self.state, other.state);
}

mixin ContractProcessInstanceMappable {
  String toJson() => ContractProcessInstanceMapper.container
      .toJson(this as ContractProcessInstance);
  Map<String, dynamic> toMap() => ContractProcessInstanceMapper.container
      .toMap(this as ContractProcessInstance);
  ContractProcessInstanceCopyWith<ContractProcessInstance,
          ContractProcessInstance, ContractProcessInstance>
      get copyWith => _ContractProcessInstanceCopyWithImpl(
          this as ContractProcessInstance, $identity, $identity);
  @override
  String toString() => ContractProcessInstanceMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ContractProcessInstanceMapper.container.isEqual(this, other));
  @override
  int get hashCode => ContractProcessInstanceMapper.container.hash(this);
}

extension ContractProcessInstanceValueCopy<$R,
        $Out extends ContractProcessInstance>
    on ObjectCopyWith<$R, ContractProcessInstance, $Out> {
  ContractProcessInstanceCopyWith<$R, ContractProcessInstance, $Out>
      get asContractProcessInstance =>
          base.as((v, t, t2) => _ContractProcessInstanceCopyWithImpl(v, t, t2));
}

typedef ContractProcessInstanceCopyWithBound = ContractProcessInstance;

abstract class ContractProcessInstanceCopyWith<
        $R,
        $In extends ContractProcessInstance,
        $Out extends ContractProcessInstance>
    implements ObjectCopyWith<$R, $In, $Out> {
  ContractProcessInstanceCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ContractProcessInstance>(
          Then<ContractProcessInstance, $Out2> t, Then<$Out2, $R2> t2);
  ContractStateCopyWith<$R, ContractState, ContractState>? get state;
  $R call(
      {String? action,
      String? tenantId,
      ContractState? state,
      String? id,
      String? businessId,
      String? businessService,
      String? moduleName});
}

class _ContractProcessInstanceCopyWithImpl<$R,
        $Out extends ContractProcessInstance>
    extends CopyWithBase<$R, ContractProcessInstance, $Out>
    implements
        ContractProcessInstanceCopyWith<$R, ContractProcessInstance, $Out> {
  _ContractProcessInstanceCopyWithImpl(super.value, super.then, super.then2);
  @override
  ContractProcessInstanceCopyWith<$R2, ContractProcessInstance, $Out2>
      chain<$R2, $Out2 extends ContractProcessInstance>(
              Then<ContractProcessInstance, $Out2> t, Then<$Out2, $R2> t2) =>
          _ContractProcessInstanceCopyWithImpl($value, t, t2);

  @override
  ContractStateCopyWith<$R, ContractState, ContractState>? get state =>
      $value.state?.copyWith.chain($identity, (v) => call(state: v));
  @override
  $R call(
          {Object? action = $none,
          Object? tenantId = $none,
          Object? state = $none,
          Object? id = $none,
          Object? businessId = $none,
          Object? businessService = $none,
          Object? moduleName = $none}) =>
      $then(ContractProcessInstance(
          action: or(action, $value.action),
          tenantId: or(tenantId, $value.tenantId),
          state: or(state, $value.state),
          id: or(id, $value.id),
          businessId: or(businessId, $value.businessId),
          businessService: or(businessService, $value.businessService),
          moduleName: or(moduleName, $value.moduleName)));
}

class ContractAdditionalDetailsMapper
    extends MapperBase<ContractAdditionalDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {ContractAdditionalDetailsMapper()},
  );

  @override
  ContractAdditionalDetailsMapperElement createElement(
      MapperContainer container) {
    return ContractAdditionalDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'ContractAdditionalDetails';

  static final fromMap = container.fromMap<ContractAdditionalDetails>;
  static final fromJson = container.fromJson<ContractAdditionalDetails>;
}

class ContractAdditionalDetailsMapperElement
    extends MapperElementBase<ContractAdditionalDetails> {
  ContractAdditionalDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ContractAdditionalDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ContractAdditionalDetails fromMap(Map<String, dynamic> map) =>
      ContractAdditionalDetails(
          officerInChargeId: container.$getOpt(map, 'officerInChargeId'),
          attendanceRegisterNumber:
              container.$getOpt(map, 'attendanceRegisterNumber'),
          projectId: container.$getOpt(map, 'projectId'),
          projectType: container.$getOpt(map, 'projectType'),
          orgName: container.$getOpt(map, 'orgName'),
          projectName: container.$getOpt(map, 'projectName'),
          ward: container.$getOpt(map, 'ward'));

  @override
  Function get encoder => encode;
  dynamic encode(ContractAdditionalDetails v) => toMap(v);
  Map<String, dynamic> toMap(ContractAdditionalDetails c) => {
        'officerInChargeId':
            container.$enc(c.officerInChargeId, 'officerInChargeId'),
        'attendanceRegisterNumber': container.$enc(
            c.attendanceRegisterNumber, 'attendanceRegisterNumber'),
        'projectId': container.$enc(c.projectId, 'projectId'),
        'projectType': container.$enc(c.projectType, 'projectType'),
        'orgName': container.$enc(c.orgName, 'orgName'),
        'projectName': container.$enc(c.projectName, 'projectName'),
        'ward': container.$enc(c.ward, 'ward')
      };

  @override
  String stringify(ContractAdditionalDetails self) =>
      'ContractAdditionalDetails(officerInChargeId: ${container.asString(self.officerInChargeId)}, attendanceRegisterNumber: ${container.asString(self.attendanceRegisterNumber)}, orgName: ${container.asString(self.orgName)}, projectId: ${container.asString(self.projectId)}, projectName: ${container.asString(self.projectName)}, projectType: ${container.asString(self.projectType)}, ward: ${container.asString(self.ward)})';
  @override
  int hash(ContractAdditionalDetails self) =>
      container.hash(self.officerInChargeId) ^
      container.hash(self.attendanceRegisterNumber) ^
      container.hash(self.orgName) ^
      container.hash(self.projectId) ^
      container.hash(self.projectName) ^
      container.hash(self.projectType) ^
      container.hash(self.ward);
  @override
  bool equals(
          ContractAdditionalDetails self, ContractAdditionalDetails other) =>
      container.isEqual(self.officerInChargeId, other.officerInChargeId) &&
      container.isEqual(
          self.attendanceRegisterNumber, other.attendanceRegisterNumber) &&
      container.isEqual(self.orgName, other.orgName) &&
      container.isEqual(self.projectId, other.projectId) &&
      container.isEqual(self.projectName, other.projectName) &&
      container.isEqual(self.projectType, other.projectType) &&
      container.isEqual(self.ward, other.ward);
}

mixin ContractAdditionalDetailsMappable {
  String toJson() => ContractAdditionalDetailsMapper.container
      .toJson(this as ContractAdditionalDetails);
  Map<String, dynamic> toMap() => ContractAdditionalDetailsMapper.container
      .toMap(this as ContractAdditionalDetails);
  ContractAdditionalDetailsCopyWith<ContractAdditionalDetails,
          ContractAdditionalDetails, ContractAdditionalDetails>
      get copyWith => _ContractAdditionalDetailsCopyWithImpl(
          this as ContractAdditionalDetails, $identity, $identity);
  @override
  String toString() => ContractAdditionalDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ContractAdditionalDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ContractAdditionalDetailsMapper.container.hash(this);
}

extension ContractAdditionalDetailsValueCopy<$R,
        $Out extends ContractAdditionalDetails>
    on ObjectCopyWith<$R, ContractAdditionalDetails, $Out> {
  ContractAdditionalDetailsCopyWith<$R, ContractAdditionalDetails, $Out>
      get asContractAdditionalDetails => base
          .as((v, t, t2) => _ContractAdditionalDetailsCopyWithImpl(v, t, t2));
}

typedef ContractAdditionalDetailsCopyWithBound = ContractAdditionalDetails;

abstract class ContractAdditionalDetailsCopyWith<
        $R,
        $In extends ContractAdditionalDetails,
        $Out extends ContractAdditionalDetails>
    implements ObjectCopyWith<$R, $In, $Out> {
  ContractAdditionalDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ContractAdditionalDetails>(
          Then<ContractAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? officerInChargeId,
      String? attendanceRegisterNumber,
      String? projectId,
      String? projectType,
      String? orgName,
      String? projectName,
      String? ward});
}

class _ContractAdditionalDetailsCopyWithImpl<$R,
        $Out extends ContractAdditionalDetails>
    extends CopyWithBase<$R, ContractAdditionalDetails, $Out>
    implements
        ContractAdditionalDetailsCopyWith<$R, ContractAdditionalDetails, $Out> {
  _ContractAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ContractAdditionalDetailsCopyWith<$R2, ContractAdditionalDetails, $Out2>
      chain<$R2, $Out2 extends ContractAdditionalDetails>(
              Then<ContractAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _ContractAdditionalDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? officerInChargeId = $none,
          Object? attendanceRegisterNumber = $none,
          Object? projectId = $none,
          Object? projectType = $none,
          Object? orgName = $none,
          Object? projectName = $none,
          Object? ward = $none}) =>
      $then(ContractAdditionalDetails(
          officerInChargeId: or(officerInChargeId, $value.officerInChargeId),
          attendanceRegisterNumber:
              or(attendanceRegisterNumber, $value.attendanceRegisterNumber),
          projectId: or(projectId, $value.projectId),
          projectType: or(projectType, $value.projectType),
          orgName: or(orgName, $value.orgName),
          projectName: or(projectName, $value.projectName),
          ward: or(ward, $value.ward)));
}

class ContractAuditDetailsMapper extends MapperBase<ContractAuditDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {ContractAuditDetailsMapper()},
  );

  @override
  ContractAuditDetailsMapperElement createElement(MapperContainer container) {
    return ContractAuditDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'ContractAuditDetails';

  static final fromMap = container.fromMap<ContractAuditDetails>;
  static final fromJson = container.fromJson<ContractAuditDetails>;
}

class ContractAuditDetailsMapperElement
    extends MapperElementBase<ContractAuditDetails> {
  ContractAuditDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ContractAuditDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ContractAuditDetails fromMap(Map<String, dynamic> map) =>
      ContractAuditDetails(
          createdTime: container.$getOpt(map, 'createdTime'),
          lastModifiedTime: container.$getOpt(map, 'lastModifiedTime'),
          createdBy: container.$getOpt(map, 'createdBy'),
          lastModifiedBy: container.$getOpt(map, 'lastModifiedBy'));

  @override
  Function get encoder => encode;
  dynamic encode(ContractAuditDetails v) => toMap(v);
  Map<String, dynamic> toMap(ContractAuditDetails c) => {
        'createdTime': container.$enc(c.createdTime, 'createdTime'),
        'lastModifiedTime':
            container.$enc(c.lastModifiedTime, 'lastModifiedTime'),
        'createdBy': container.$enc(c.createdBy, 'createdBy'),
        'lastModifiedBy': container.$enc(c.lastModifiedBy, 'lastModifiedBy')
      };

  @override
  String stringify(ContractAuditDetails self) =>
      'ContractAuditDetails(createdBy: ${container.asString(self.createdBy)}, lastModifiedBy: ${container.asString(self.lastModifiedBy)}, createdTime: ${container.asString(self.createdTime)}, lastModifiedTime: ${container.asString(self.lastModifiedTime)})';
  @override
  int hash(ContractAuditDetails self) =>
      container.hash(self.createdBy) ^
      container.hash(self.lastModifiedBy) ^
      container.hash(self.createdTime) ^
      container.hash(self.lastModifiedTime);
  @override
  bool equals(ContractAuditDetails self, ContractAuditDetails other) =>
      container.isEqual(self.createdBy, other.createdBy) &&
      container.isEqual(self.lastModifiedBy, other.lastModifiedBy) &&
      container.isEqual(self.createdTime, other.createdTime) &&
      container.isEqual(self.lastModifiedTime, other.lastModifiedTime);
}

mixin ContractAuditDetailsMappable {
  String toJson() =>
      ContractAuditDetailsMapper.container.toJson(this as ContractAuditDetails);
  Map<String, dynamic> toMap() =>
      ContractAuditDetailsMapper.container.toMap(this as ContractAuditDetails);
  ContractAuditDetailsCopyWith<ContractAuditDetails, ContractAuditDetails,
          ContractAuditDetails>
      get copyWith => _ContractAuditDetailsCopyWithImpl(
          this as ContractAuditDetails, $identity, $identity);
  @override
  String toString() => ContractAuditDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ContractAuditDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ContractAuditDetailsMapper.container.hash(this);
}

extension ContractAuditDetailsValueCopy<$R, $Out extends ContractAuditDetails>
    on ObjectCopyWith<$R, ContractAuditDetails, $Out> {
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, $Out>
      get asContractAuditDetails =>
          base.as((v, t, t2) => _ContractAuditDetailsCopyWithImpl(v, t, t2));
}

typedef ContractAuditDetailsCopyWithBound = ContractAuditDetails;

abstract class ContractAuditDetailsCopyWith<$R,
        $In extends ContractAuditDetails, $Out extends ContractAuditDetails>
    implements ObjectCopyWith<$R, $In, $Out> {
  ContractAuditDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ContractAuditDetails>(
          Then<ContractAuditDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {int? createdTime,
      int? lastModifiedTime,
      String? createdBy,
      String? lastModifiedBy});
}

class _ContractAuditDetailsCopyWithImpl<$R, $Out extends ContractAuditDetails>
    extends CopyWithBase<$R, ContractAuditDetails, $Out>
    implements ContractAuditDetailsCopyWith<$R, ContractAuditDetails, $Out> {
  _ContractAuditDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ContractAuditDetailsCopyWith<$R2, ContractAuditDetails, $Out2>
      chain<$R2, $Out2 extends ContractAuditDetails>(
              Then<ContractAuditDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _ContractAuditDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? createdTime = $none,
          Object? lastModifiedTime = $none,
          Object? createdBy = $none,
          Object? lastModifiedBy = $none}) =>
      $then(ContractAuditDetails(
          createdTime: or(createdTime, $value.createdTime),
          lastModifiedTime: or(lastModifiedTime, $value.lastModifiedTime),
          createdBy: or(createdBy, $value.createdBy),
          lastModifiedBy: or(lastModifiedBy, $value.lastModifiedBy)));
}

class DocumentsMapper extends MapperBase<Documents> {
  static MapperContainer container = MapperContainer(
    mappers: {DocumentsMapper()},
  );

  @override
  DocumentsMapperElement createElement(MapperContainer container) {
    return DocumentsMapperElement._(this, container);
  }

  @override
  String get id => 'Documents';

  static final fromMap = container.fromMap<Documents>;
  static final fromJson = container.fromJson<Documents>;
}

class DocumentsMapperElement extends MapperElementBase<Documents> {
  DocumentsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  Documents decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  Documents fromMap(Map<String, dynamic> map) => Documents(
      contractId: container.$getOpt(map, 'contractId'),
      id: container.$getOpt(map, 'id'),
      documentType: container.$getOpt(map, 'documentType'),
      status: container.$getOpt(map, 'status'),
      documentUid: container.$getOpt(map, 'documentUid'),
      fileStore: container.$getOpt(map, 'fileStore'));

  @override
  Function get encoder => encode;
  dynamic encode(Documents v) => toMap(v);
  Map<String, dynamic> toMap(Documents d) => {
        'contractId': container.$enc(d.contractId, 'contractId'),
        'id': container.$enc(d.id, 'id'),
        'documentType': container.$enc(d.documentType, 'documentType'),
        'status': container.$enc(d.status, 'status'),
        'documentUid': container.$enc(d.documentUid, 'documentUid'),
        'fileStore': container.$enc(d.fileStore, 'fileStore')
      };

  @override
  String stringify(Documents self) =>
      'Documents(contractId: ${container.asString(self.contractId)}, id: ${container.asString(self.id)}, documentType: ${container.asString(self.documentType)}, fileStore: ${container.asString(self.fileStore)}, documentUid: ${container.asString(self.documentUid)}, status: ${container.asString(self.status)})';
  @override
  int hash(Documents self) =>
      container.hash(self.contractId) ^
      container.hash(self.id) ^
      container.hash(self.documentType) ^
      container.hash(self.fileStore) ^
      container.hash(self.documentUid) ^
      container.hash(self.status);
  @override
  bool equals(Documents self, Documents other) =>
      container.isEqual(self.contractId, other.contractId) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.documentType, other.documentType) &&
      container.isEqual(self.fileStore, other.fileStore) &&
      container.isEqual(self.documentUid, other.documentUid) &&
      container.isEqual(self.status, other.status);
}

mixin DocumentsMappable {
  String toJson() => DocumentsMapper.container.toJson(this as Documents);
  Map<String, dynamic> toMap() =>
      DocumentsMapper.container.toMap(this as Documents);
  DocumentsCopyWith<Documents, Documents, Documents> get copyWith =>
      _DocumentsCopyWithImpl(this as Documents, $identity, $identity);
  @override
  String toString() => DocumentsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          DocumentsMapper.container.isEqual(this, other));
  @override
  int get hashCode => DocumentsMapper.container.hash(this);
}

extension DocumentsValueCopy<$R, $Out extends Documents>
    on ObjectCopyWith<$R, Documents, $Out> {
  DocumentsCopyWith<$R, Documents, $Out> get asDocuments =>
      base.as((v, t, t2) => _DocumentsCopyWithImpl(v, t, t2));
}

typedef DocumentsCopyWithBound = Documents;

abstract class DocumentsCopyWith<$R, $In extends Documents,
    $Out extends Documents> implements ObjectCopyWith<$R, $In, $Out> {
  DocumentsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends Documents>(
      Then<Documents, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? contractId,
      String? id,
      String? documentType,
      String? status,
      String? documentUid,
      String? fileStore});
}

class _DocumentsCopyWithImpl<$R, $Out extends Documents>
    extends CopyWithBase<$R, Documents, $Out>
    implements DocumentsCopyWith<$R, Documents, $Out> {
  _DocumentsCopyWithImpl(super.value, super.then, super.then2);
  @override
  DocumentsCopyWith<$R2, Documents, $Out2> chain<$R2, $Out2 extends Documents>(
          Then<Documents, $Out2> t, Then<$Out2, $R2> t2) =>
      _DocumentsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? contractId = $none,
          Object? id = $none,
          Object? documentType = $none,
          Object? status = $none,
          Object? documentUid = $none,
          Object? fileStore = $none}) =>
      $then(Documents(
          contractId: or(contractId, $value.contractId),
          id: or(id, $value.id),
          documentType: or(documentType, $value.documentType),
          status: or(status, $value.status),
          documentUid: or(documentUid, $value.documentUid),
          fileStore: or(fileStore, $value.fileStore)));
}

class AmountBreakupsMapper extends MapperBase<AmountBreakups> {
  static MapperContainer container = MapperContainer(
    mappers: {AmountBreakupsMapper()},
  );

  @override
  AmountBreakupsMapperElement createElement(MapperContainer container) {
    return AmountBreakupsMapperElement._(this, container);
  }

  @override
  String get id => 'AmountBreakups';

  static final fromMap = container.fromMap<AmountBreakups>;
  static final fromJson = container.fromJson<AmountBreakups>;
}

class AmountBreakupsMapperElement extends MapperElementBase<AmountBreakups> {
  AmountBreakupsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  AmountBreakups decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  AmountBreakups fromMap(Map<String, dynamic> map) => AmountBreakups(
      id: container.$getOpt(map, 'id'),
      status: container.$getOpt(map, 'status'),
      additionalDetails: container.$getOpt(map, 'additionalDetails'),
      amount: container.$getOpt(map, 'amount'),
      estimateAmountBreakupId:
          container.$getOpt(map, 'estimateAmountBreakupId'));

  @override
  Function get encoder => encode;
  dynamic encode(AmountBreakups v) => toMap(v);
  Map<String, dynamic> toMap(AmountBreakups a) => {
        'id': container.$enc(a.id, 'id'),
        'status': container.$enc(a.status, 'status'),
        'additionalDetails':
            container.$enc(a.additionalDetails, 'additionalDetails'),
        'amount': container.$enc(a.amount, 'amount'),
        'estimateAmountBreakupId':
            container.$enc(a.estimateAmountBreakupId, 'estimateAmountBreakupId')
      };

  @override
  String stringify(AmountBreakups self) =>
      'AmountBreakups(id: ${container.asString(self.id)}, estimateAmountBreakupId: ${container.asString(self.estimateAmountBreakupId)}, amount: ${container.asString(self.amount)}, status: ${container.asString(self.status)}, additionalDetails: ${container.asString(self.additionalDetails)})';
  @override
  int hash(AmountBreakups self) =>
      container.hash(self.id) ^
      container.hash(self.estimateAmountBreakupId) ^
      container.hash(self.amount) ^
      container.hash(self.status) ^
      container.hash(self.additionalDetails);
  @override
  bool equals(AmountBreakups self, AmountBreakups other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(
          self.estimateAmountBreakupId, other.estimateAmountBreakupId) &&
      container.isEqual(self.amount, other.amount) &&
      container.isEqual(self.status, other.status) &&
      container.isEqual(self.additionalDetails, other.additionalDetails);
}

mixin AmountBreakupsMappable {
  String toJson() =>
      AmountBreakupsMapper.container.toJson(this as AmountBreakups);
  Map<String, dynamic> toMap() =>
      AmountBreakupsMapper.container.toMap(this as AmountBreakups);
  AmountBreakupsCopyWith<AmountBreakups, AmountBreakups, AmountBreakups>
      get copyWith => _AmountBreakupsCopyWithImpl(
          this as AmountBreakups, $identity, $identity);
  @override
  String toString() => AmountBreakupsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          AmountBreakupsMapper.container.isEqual(this, other));
  @override
  int get hashCode => AmountBreakupsMapper.container.hash(this);
}

extension AmountBreakupsValueCopy<$R, $Out extends AmountBreakups>
    on ObjectCopyWith<$R, AmountBreakups, $Out> {
  AmountBreakupsCopyWith<$R, AmountBreakups, $Out> get asAmountBreakups =>
      base.as((v, t, t2) => _AmountBreakupsCopyWithImpl(v, t, t2));
}

typedef AmountBreakupsCopyWithBound = AmountBreakups;

abstract class AmountBreakupsCopyWith<$R, $In extends AmountBreakups,
    $Out extends AmountBreakups> implements ObjectCopyWith<$R, $In, $Out> {
  AmountBreakupsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends AmountBreakups>(
          Then<AmountBreakups, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? id,
      String? status,
      double? additionalDetails,
      String? amount,
      String? estimateAmountBreakupId});
}

class _AmountBreakupsCopyWithImpl<$R, $Out extends AmountBreakups>
    extends CopyWithBase<$R, AmountBreakups, $Out>
    implements AmountBreakupsCopyWith<$R, AmountBreakups, $Out> {
  _AmountBreakupsCopyWithImpl(super.value, super.then, super.then2);
  @override
  AmountBreakupsCopyWith<$R2, AmountBreakups, $Out2>
      chain<$R2, $Out2 extends AmountBreakups>(
              Then<AmountBreakups, $Out2> t, Then<$Out2, $R2> t2) =>
          _AmountBreakupsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? id = $none,
          Object? status = $none,
          Object? additionalDetails = $none,
          Object? amount = $none,
          Object? estimateAmountBreakupId = $none}) =>
      $then(AmountBreakups(
          id: or(id, $value.id),
          status: or(status, $value.status),
          additionalDetails: or(additionalDetails, $value.additionalDetails),
          amount: or(amount, $value.amount),
          estimateAmountBreakupId:
              or(estimateAmountBreakupId, $value.estimateAmountBreakupId)));
}

class ContractStateMapper extends MapperBase<ContractState> {
  static MapperContainer container = MapperContainer(
    mappers: {ContractStateMapper()},
  )..linkAll({ContractAuditDetailsMapper.container});

  @override
  ContractStateMapperElement createElement(MapperContainer container) {
    return ContractStateMapperElement._(this, container);
  }

  @override
  String get id => 'ContractState';

  static final fromMap = container.fromMap<ContractState>;
  static final fromJson = container.fromJson<ContractState>;
}

class ContractStateMapperElement extends MapperElementBase<ContractState> {
  ContractStateMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ContractState decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ContractState fromMap(Map<String, dynamic> map) => ContractState(
      auditDetails: container.$getOpt(map, 'auditDetails'),
      uuid: container.$getOpt(map, 'uuid'),
      tenantId: container.$getOpt(map, 'tenantId'),
      state: container.$getOpt(map, 'state'),
      applicationStatus: container.$getOpt(map, 'applicationStatus'),
      businessServiceId: container.$getOpt(map, 'businessServiceId'));

  @override
  Function get encoder => encode;
  dynamic encode(ContractState v) => toMap(v);
  Map<String, dynamic> toMap(ContractState c) => {
        'auditDetails': container.$enc(c.auditDetails, 'auditDetails'),
        'uuid': container.$enc(c.uuid, 'uuid'),
        'tenantId': container.$enc(c.tenantId, 'tenantId'),
        'state': container.$enc(c.state, 'state'),
        'applicationStatus':
            container.$enc(c.applicationStatus, 'applicationStatus'),
        'businessServiceId':
            container.$enc(c.businessServiceId, 'businessServiceId')
      };

  @override
  String stringify(ContractState self) =>
      'ContractState(auditDetails: ${container.asString(self.auditDetails)}, uuid: ${container.asString(self.uuid)}, tenantId: ${container.asString(self.tenantId)}, businessServiceId: ${container.asString(self.businessServiceId)}, applicationStatus: ${container.asString(self.applicationStatus)}, state: ${container.asString(self.state)})';
  @override
  int hash(ContractState self) =>
      container.hash(self.auditDetails) ^
      container.hash(self.uuid) ^
      container.hash(self.tenantId) ^
      container.hash(self.businessServiceId) ^
      container.hash(self.applicationStatus) ^
      container.hash(self.state);
  @override
  bool equals(ContractState self, ContractState other) =>
      container.isEqual(self.auditDetails, other.auditDetails) &&
      container.isEqual(self.uuid, other.uuid) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.businessServiceId, other.businessServiceId) &&
      container.isEqual(self.applicationStatus, other.applicationStatus) &&
      container.isEqual(self.state, other.state);
}

mixin ContractStateMappable {
  String toJson() =>
      ContractStateMapper.container.toJson(this as ContractState);
  Map<String, dynamic> toMap() =>
      ContractStateMapper.container.toMap(this as ContractState);
  ContractStateCopyWith<ContractState, ContractState, ContractState>
      get copyWith => _ContractStateCopyWithImpl(
          this as ContractState, $identity, $identity);
  @override
  String toString() => ContractStateMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ContractStateMapper.container.isEqual(this, other));
  @override
  int get hashCode => ContractStateMapper.container.hash(this);
}

extension ContractStateValueCopy<$R, $Out extends ContractState>
    on ObjectCopyWith<$R, ContractState, $Out> {
  ContractStateCopyWith<$R, ContractState, $Out> get asContractState =>
      base.as((v, t, t2) => _ContractStateCopyWithImpl(v, t, t2));
}

typedef ContractStateCopyWithBound = ContractState;

abstract class ContractStateCopyWith<$R, $In extends ContractState,
    $Out extends ContractState> implements ObjectCopyWith<$R, $In, $Out> {
  ContractStateCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ContractState>(
          Then<ContractState, $Out2> t, Then<$Out2, $R2> t2);
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails;
  $R call(
      {ContractAuditDetails? auditDetails,
      String? uuid,
      String? tenantId,
      String? state,
      String? applicationStatus,
      String? businessServiceId});
}

class _ContractStateCopyWithImpl<$R, $Out extends ContractState>
    extends CopyWithBase<$R, ContractState, $Out>
    implements ContractStateCopyWith<$R, ContractState, $Out> {
  _ContractStateCopyWithImpl(super.value, super.then, super.then2);
  @override
  ContractStateCopyWith<$R2, ContractState, $Out2>
      chain<$R2, $Out2 extends ContractState>(
              Then<ContractState, $Out2> t, Then<$Out2, $R2> t2) =>
          _ContractStateCopyWithImpl($value, t, t2);

  @override
  ContractAuditDetailsCopyWith<$R, ContractAuditDetails, ContractAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  $R call(
          {Object? auditDetails = $none,
          Object? uuid = $none,
          Object? tenantId = $none,
          Object? state = $none,
          Object? applicationStatus = $none,
          Object? businessServiceId = $none}) =>
      $then(ContractState(
          auditDetails: or(auditDetails, $value.auditDetails),
          uuid: or(uuid, $value.uuid),
          tenantId: or(tenantId, $value.tenantId),
          state: or(state, $value.state),
          applicationStatus: or(applicationStatus, $value.applicationStatus),
          businessServiceId: or(businessServiceId, $value.businessServiceId)));
}
