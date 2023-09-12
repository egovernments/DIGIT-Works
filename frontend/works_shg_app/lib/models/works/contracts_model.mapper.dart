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
      auditDetails: container.$getOpt(map, 'auditDetails'),
      businessService: container.$getOpt(map, 'businessService'),
      supplementNumber: container.$getOpt(map, 'supplementNumber'));

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
        'auditDetails': container.$enc(c.auditDetails, 'auditDetails'),
        'businessService': container.$enc(c.businessService, 'businessService'),
        'supplementNumber':
            container.$enc(c.supplementNumber, 'supplementNumber')
      };

  @override
  String stringify(Contracts self) =>
      'Contracts(id: ${container.asString(self.id)}, contractNumber: ${container.asString(self.contractNumber)}, businessService: ${container.asString(self.businessService)}, tenantId: ${container.asString(self.tenantId)}, wfStatus: ${container.asString(self.wfStatus)}, executingAuthority: ${container.asString(self.executingAuthority)}, contractType: ${container.asString(self.contractType)}, totalContractedAmount: ${container.asString(self.totalContractedAmount)}, securityDeposit: ${container.asString(self.securityDeposit)}, agreementDate: ${container.asString(self.agreementDate)}, issueDate: ${container.asString(self.issueDate)}, defectLiabilityPeriod: ${container.asString(self.defectLiabilityPeriod)}, orgId: ${container.asString(self.orgId)}, startDate: ${container.asString(self.startDate)}, endDate: ${container.asString(self.endDate)}, completionPeriod: ${container.asString(self.completionPeriod)}, status: ${container.asString(self.status)}, lineItems: ${container.asString(self.lineItems)}, supplementNumber: ${container.asString(self.supplementNumber)}, documents: ${container.asString(self.documents)}, auditDetails: ${container.asString(self.auditDetails)}, additionalDetails: ${container.asString(self.additionalDetails)}, processInstance: ${container.asString(self.processInstance)})';
  @override
  int hash(Contracts self) =>
      container.hash(self.id) ^
      container.hash(self.contractNumber) ^
      container.hash(self.businessService) ^
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
      container.hash(self.supplementNumber) ^
      container.hash(self.documents) ^
      container.hash(self.auditDetails) ^
      container.hash(self.additionalDetails) ^
      container.hash(self.processInstance);
  @override
  bool equals(Contracts self, Contracts other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.contractNumber, other.contractNumber) &&
      container.isEqual(self.businessService, other.businessService) &&
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
      container.isEqual(self.supplementNumber, other.supplementNumber) &&
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
      ContractAuditDetails? auditDetails,
      String? businessService,
      String? supplementNumber});
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
          Object? auditDetails = $none,
          Object? businessService = $none,
          Object? supplementNumber = $none}) =>
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
          auditDetails: or(auditDetails, $value.auditDetails),
          businessService: or(businessService, $value.businessService),
          supplementNumber: or(supplementNumber, $value.supplementNumber)));
}

class ORGAdditionalDetailsMapper extends MapperBase<ORGAdditionalDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {ORGAdditionalDetailsMapper()},
  );

  @override
  ORGAdditionalDetailsMapperElement createElement(MapperContainer container) {
    return ORGAdditionalDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'ORGAdditionalDetails';

  static final fromMap = container.fromMap<ORGAdditionalDetails>;
  static final fromJson = container.fromJson<ORGAdditionalDetails>;
}

class ORGAdditionalDetailsMapperElement
    extends MapperElementBase<ORGAdditionalDetails> {
  ORGAdditionalDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ORGAdditionalDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ORGAdditionalDetails fromMap(Map<String, dynamic> map) =>
      ORGAdditionalDetails(
          deptRegistrationNum: container.$getOpt(map, 'deptRegistrationNum'),
          registeredByDept: container.$getOpt(map, 'registeredByDept'));

  @override
  Function get encoder => encode;
  dynamic encode(ORGAdditionalDetails v) => toMap(v);
  Map<String, dynamic> toMap(ORGAdditionalDetails o) => {
        'deptRegistrationNum':
            container.$enc(o.deptRegistrationNum, 'deptRegistrationNum'),
        'registeredByDept':
            container.$enc(o.registeredByDept, 'registeredByDept')
      };

  @override
  String stringify(ORGAdditionalDetails self) =>
      'ORGAdditionalDetails(registeredByDept: ${container.asString(self.registeredByDept)}, deptRegistrationNum: ${container.asString(self.deptRegistrationNum)})';
  @override
  int hash(ORGAdditionalDetails self) =>
      container.hash(self.registeredByDept) ^
      container.hash(self.deptRegistrationNum);
  @override
  bool equals(ORGAdditionalDetails self, ORGAdditionalDetails other) =>
      container.isEqual(self.registeredByDept, other.registeredByDept) &&
      container.isEqual(self.deptRegistrationNum, other.deptRegistrationNum);
}

mixin ORGAdditionalDetailsMappable {
  String toJson() =>
      ORGAdditionalDetailsMapper.container.toJson(this as ORGAdditionalDetails);
  Map<String, dynamic> toMap() =>
      ORGAdditionalDetailsMapper.container.toMap(this as ORGAdditionalDetails);
  ORGAdditionalDetailsCopyWith<ORGAdditionalDetails, ORGAdditionalDetails,
          ORGAdditionalDetails>
      get copyWith => _ORGAdditionalDetailsCopyWithImpl(
          this as ORGAdditionalDetails, $identity, $identity);
  @override
  String toString() => ORGAdditionalDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ORGAdditionalDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ORGAdditionalDetailsMapper.container.hash(this);
}

extension ORGAdditionalDetailsValueCopy<$R, $Out extends ORGAdditionalDetails>
    on ObjectCopyWith<$R, ORGAdditionalDetails, $Out> {
  ORGAdditionalDetailsCopyWith<$R, ORGAdditionalDetails, $Out>
      get asORGAdditionalDetails =>
          base.as((v, t, t2) => _ORGAdditionalDetailsCopyWithImpl(v, t, t2));
}

typedef ORGAdditionalDetailsCopyWithBound = ORGAdditionalDetails;

abstract class ORGAdditionalDetailsCopyWith<$R,
        $In extends ORGAdditionalDetails, $Out extends ORGAdditionalDetails>
    implements ObjectCopyWith<$R, $In, $Out> {
  ORGAdditionalDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ORGAdditionalDetails>(
          Then<ORGAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? deptRegistrationNum, String? registeredByDept});
}

class _ORGAdditionalDetailsCopyWithImpl<$R, $Out extends ORGAdditionalDetails>
    extends CopyWithBase<$R, ORGAdditionalDetails, $Out>
    implements ORGAdditionalDetailsCopyWith<$R, ORGAdditionalDetails, $Out> {
  _ORGAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ORGAdditionalDetailsCopyWith<$R2, ORGAdditionalDetails, $Out2>
      chain<$R2, $Out2 extends ORGAdditionalDetails>(
              Then<ORGAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _ORGAdditionalDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? deptRegistrationNum = $none,
          Object? registeredByDept = $none}) =>
      $then(ORGAdditionalDetails(
          deptRegistrationNum:
              or(deptRegistrationNum, $value.deptRegistrationNum),
          registeredByDept: or(registeredByDept, $value.registeredByDept)));
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
  )..linkAll({
      EstimateDocsMapper.container,
      DescriptionMapper.container,
      OfficerInChargeMapper.container,
    });

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
          cboOrgNumber: container.$getOpt(map, 'cboOrgNumber'),
          projectId: container.$getOpt(map, 'projectId'),
          projectType: container.$getOpt(map, 'projectType'),
          orgName: container.$getOpt(map, 'orgName'),
          projectName: container.$getOpt(map, 'projectName'),
          ward: container.$getOpt(map, 'ward'),
          locality: container.$getOpt(map, 'locality'),
          cboCode: container.$getOpt(map, 'cboCode'),
          cboName: container.$getOpt(map, 'cboName'),
          estimateDocs: container.$getOpt(map, 'estimateDocs'),
          estimateNumber: container.$getOpt(map, 'estimateNumber'),
          totalEstimatedAmount: container.$getOpt(map, 'totalEstimatedAmount'),
          completionPeriod: container.$getOpt(map, 'completionPeriod'),
          termsAndConditions: container.$getOpt(map, 'termsAndConditions'),
          projectDesc: container.$getOpt(map, 'projectDesc'),
          officerInChargeName: container.$getOpt(map, 'officerInChargeName'),
          officerInChargeDesgn: container.$getOpt(map, 'officerInChargeDesgn'),
          timeExtReason: container.$getOpt(map, 'timeExtReason'),
          timeExt: container.$getOpt(map, 'timeExt'));

  @override
  Function get encoder => encode;
  dynamic encode(ContractAdditionalDetails v) => toMap(v);
  Map<String, dynamic> toMap(ContractAdditionalDetails c) => {
        'officerInChargeId':
            container.$enc(c.officerInChargeId, 'officerInChargeId'),
        'attendanceRegisterNumber': container.$enc(
            c.attendanceRegisterNumber, 'attendanceRegisterNumber'),
        'cboOrgNumber': container.$enc(c.cboOrgNumber, 'cboOrgNumber'),
        'projectId': container.$enc(c.projectId, 'projectId'),
        'projectType': container.$enc(c.projectType, 'projectType'),
        'orgName': container.$enc(c.orgName, 'orgName'),
        'projectName': container.$enc(c.projectName, 'projectName'),
        'ward': container.$enc(c.ward, 'ward'),
        'locality': container.$enc(c.locality, 'locality'),
        'cboCode': container.$enc(c.cboCode, 'cboCode'),
        'cboName': container.$enc(c.cboName, 'cboName'),
        'estimateDocs': container.$enc(c.estimateDocs, 'estimateDocs'),
        'estimateNumber': container.$enc(c.estimateNumber, 'estimateNumber'),
        'totalEstimatedAmount':
            container.$enc(c.totalEstimatedAmount, 'totalEstimatedAmount'),
        'completionPeriod':
            container.$enc(c.completionPeriod, 'completionPeriod'),
        'termsAndConditions':
            container.$enc(c.termsAndConditions, 'termsAndConditions'),
        'projectDesc': container.$enc(c.projectDesc, 'projectDesc'),
        'officerInChargeName':
            container.$enc(c.officerInChargeName, 'officerInChargeName'),
        'officerInChargeDesgn':
            container.$enc(c.officerInChargeDesgn, 'officerInChargeDesgn'),
        'timeExtReason': container.$enc(c.timeExtReason, 'timeExtReason'),
        'timeExt': container.$enc(c.timeExt, 'timeExt')
      };

  @override
  String stringify(ContractAdditionalDetails self) =>
      'ContractAdditionalDetails(officerInChargeId: ${container.asString(self.officerInChargeId)}, attendanceRegisterNumber: ${container.asString(self.attendanceRegisterNumber)}, orgName: ${container.asString(self.orgName)}, cboOrgNumber: ${container.asString(self.cboOrgNumber)}, projectId: ${container.asString(self.projectId)}, projectName: ${container.asString(self.projectName)}, projectDesc: ${container.asString(self.projectDesc)}, projectType: ${container.asString(self.projectType)}, ward: ${container.asString(self.ward)}, cboName: ${container.asString(self.cboName)}, officerInChargeName: ${container.asString(self.officerInChargeName)}, officerInChargeDesgn: ${container.asString(self.officerInChargeDesgn)}, cboCode: ${container.asString(self.cboCode)}, estimateNumber: ${container.asString(self.estimateNumber)}, locality: ${container.asString(self.locality)}, totalEstimatedAmount: ${container.asString(self.totalEstimatedAmount)}, estimateDocs: ${container.asString(self.estimateDocs)}, termsAndConditions: ${container.asString(self.termsAndConditions)}, completionPeriod: ${container.asString(self.completionPeriod)}, timeExtReason: ${container.asString(self.timeExtReason)}, timeExt: ${container.asString(self.timeExt)})';
  @override
  int hash(ContractAdditionalDetails self) =>
      container.hash(self.officerInChargeId) ^
      container.hash(self.attendanceRegisterNumber) ^
      container.hash(self.orgName) ^
      container.hash(self.cboOrgNumber) ^
      container.hash(self.projectId) ^
      container.hash(self.projectName) ^
      container.hash(self.projectDesc) ^
      container.hash(self.projectType) ^
      container.hash(self.ward) ^
      container.hash(self.cboName) ^
      container.hash(self.officerInChargeName) ^
      container.hash(self.officerInChargeDesgn) ^
      container.hash(self.cboCode) ^
      container.hash(self.estimateNumber) ^
      container.hash(self.locality) ^
      container.hash(self.totalEstimatedAmount) ^
      container.hash(self.estimateDocs) ^
      container.hash(self.termsAndConditions) ^
      container.hash(self.completionPeriod) ^
      container.hash(self.timeExtReason) ^
      container.hash(self.timeExt);
  @override
  bool equals(
          ContractAdditionalDetails self, ContractAdditionalDetails other) =>
      container.isEqual(self.officerInChargeId, other.officerInChargeId) &&
      container.isEqual(
          self.attendanceRegisterNumber, other.attendanceRegisterNumber) &&
      container.isEqual(self.orgName, other.orgName) &&
      container.isEqual(self.cboOrgNumber, other.cboOrgNumber) &&
      container.isEqual(self.projectId, other.projectId) &&
      container.isEqual(self.projectName, other.projectName) &&
      container.isEqual(self.projectDesc, other.projectDesc) &&
      container.isEqual(self.projectType, other.projectType) &&
      container.isEqual(self.ward, other.ward) &&
      container.isEqual(self.cboName, other.cboName) &&
      container.isEqual(self.officerInChargeName, other.officerInChargeName) &&
      container.isEqual(
          self.officerInChargeDesgn, other.officerInChargeDesgn) &&
      container.isEqual(self.cboCode, other.cboCode) &&
      container.isEqual(self.estimateNumber, other.estimateNumber) &&
      container.isEqual(self.locality, other.locality) &&
      container.isEqual(
          self.totalEstimatedAmount, other.totalEstimatedAmount) &&
      container.isEqual(self.estimateDocs, other.estimateDocs) &&
      container.isEqual(self.termsAndConditions, other.termsAndConditions) &&
      container.isEqual(self.completionPeriod, other.completionPeriod) &&
      container.isEqual(self.timeExtReason, other.timeExtReason) &&
      container.isEqual(self.timeExt, other.timeExt);
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
  ListCopyWith<$R, EstimateDocs,
          EstimateDocsCopyWith<$R, EstimateDocs, EstimateDocs>>?
      get estimateDocs => $value.estimateDocs != null
          ? ListCopyWith(
              $value.estimateDocs!,
              (v, t) => v.copyWith.chain<$R, EstimateDocs>($identity, t),
              (v) => call(estimateDocs: v))
          : null;
  @override
  ListCopyWith<$R, Description?,
          DescriptionCopyWith<$R, Description, Description>?>?
      get termsAndConditions => $value.termsAndConditions != null
          ? ListCopyWith(
              $value.termsAndConditions!,
              (v, t) => v?.copyWith.chain<$R, Description>($identity, t),
              (v) => call(termsAndConditions: v))
          : null;
  @override
  OfficerInChargeCopyWith<$R, OfficerInCharge, OfficerInCharge>?
      get officerInChargeName => $value.officerInChargeName?.copyWith
          .chain($identity, (v) => call(officerInChargeName: v));
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
      $then(ContractAdditionalDetails(
          officerInChargeId: or(officerInChargeId, $value.officerInChargeId),
          attendanceRegisterNumber:
              or(attendanceRegisterNumber, $value.attendanceRegisterNumber),
          cboOrgNumber: or(cboOrgNumber, $value.cboOrgNumber),
          projectId: or(projectId, $value.projectId),
          projectType: or(projectType, $value.projectType),
          orgName: or(orgName, $value.orgName),
          projectName: or(projectName, $value.projectName),
          ward: or(ward, $value.ward),
          locality: or(locality, $value.locality),
          cboCode: or(cboCode, $value.cboCode),
          cboName: or(cboName, $value.cboName),
          estimateDocs: or(estimateDocs, $value.estimateDocs),
          estimateNumber: or(estimateNumber, $value.estimateNumber),
          totalEstimatedAmount:
              or(totalEstimatedAmount, $value.totalEstimatedAmount),
          completionPeriod: or(completionPeriod, $value.completionPeriod),
          termsAndConditions: or(termsAndConditions, $value.termsAndConditions),
          projectDesc: or(projectDesc, $value.projectDesc),
          officerInChargeName:
              or(officerInChargeName, $value.officerInChargeName),
          officerInChargeDesgn:
              or(officerInChargeDesgn, $value.officerInChargeDesgn),
          timeExtReason: or(timeExtReason, $value.timeExtReason),
          timeExt: or(timeExt, $value.timeExt)));
}

class OfficerInChargeMapper extends MapperBase<OfficerInCharge> {
  static MapperContainer container = MapperContainer(
    mappers: {OfficerInChargeMapper()},
  );

  @override
  OfficerInChargeMapperElement createElement(MapperContainer container) {
    return OfficerInChargeMapperElement._(this, container);
  }

  @override
  String get id => 'OfficerInCharge';

  static final fromMap = container.fromMap<OfficerInCharge>;
  static final fromJson = container.fromJson<OfficerInCharge>;
}

class OfficerInChargeMapperElement extends MapperElementBase<OfficerInCharge> {
  OfficerInChargeMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  OfficerInCharge decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  OfficerInCharge fromMap(Map<String, dynamic> map) => OfficerInCharge(
      code: container.$getOpt(map, 'code'),
      name: container.$getOpt(map, 'name'));

  @override
  Function get encoder => encode;
  dynamic encode(OfficerInCharge v) => toMap(v);
  Map<String, dynamic> toMap(OfficerInCharge o) => {
        'code': container.$enc(o.code, 'code'),
        'name': container.$enc(o.name, 'name')
      };

  @override
  String stringify(OfficerInCharge self) =>
      'OfficerInCharge(code: ${container.asString(self.code)}, name: ${container.asString(self.name)})';
  @override
  int hash(OfficerInCharge self) =>
      container.hash(self.code) ^ container.hash(self.name);
  @override
  bool equals(OfficerInCharge self, OfficerInCharge other) =>
      container.isEqual(self.code, other.code) &&
      container.isEqual(self.name, other.name);
}

mixin OfficerInChargeMappable {
  String toJson() =>
      OfficerInChargeMapper.container.toJson(this as OfficerInCharge);
  Map<String, dynamic> toMap() =>
      OfficerInChargeMapper.container.toMap(this as OfficerInCharge);
  OfficerInChargeCopyWith<OfficerInCharge, OfficerInCharge, OfficerInCharge>
      get copyWith => _OfficerInChargeCopyWithImpl(
          this as OfficerInCharge, $identity, $identity);
  @override
  String toString() => OfficerInChargeMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          OfficerInChargeMapper.container.isEqual(this, other));
  @override
  int get hashCode => OfficerInChargeMapper.container.hash(this);
}

extension OfficerInChargeValueCopy<$R, $Out extends OfficerInCharge>
    on ObjectCopyWith<$R, OfficerInCharge, $Out> {
  OfficerInChargeCopyWith<$R, OfficerInCharge, $Out> get asOfficerInCharge =>
      base.as((v, t, t2) => _OfficerInChargeCopyWithImpl(v, t, t2));
}

typedef OfficerInChargeCopyWithBound = OfficerInCharge;

abstract class OfficerInChargeCopyWith<$R, $In extends OfficerInCharge,
    $Out extends OfficerInCharge> implements ObjectCopyWith<$R, $In, $Out> {
  OfficerInChargeCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends OfficerInCharge>(
          Then<OfficerInCharge, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? code, String? name});
}

class _OfficerInChargeCopyWithImpl<$R, $Out extends OfficerInCharge>
    extends CopyWithBase<$R, OfficerInCharge, $Out>
    implements OfficerInChargeCopyWith<$R, OfficerInCharge, $Out> {
  _OfficerInChargeCopyWithImpl(super.value, super.then, super.then2);
  @override
  OfficerInChargeCopyWith<$R2, OfficerInCharge, $Out2>
      chain<$R2, $Out2 extends OfficerInCharge>(
              Then<OfficerInCharge, $Out2> t, Then<$Out2, $R2> t2) =>
          _OfficerInChargeCopyWithImpl($value, t, t2);

  @override
  $R call({Object? code = $none, Object? name = $none}) =>
      $then(OfficerInCharge(
          code: or(code, $value.code), name: or(name, $value.name)));
}

class DescriptionMapper extends MapperBase<Description> {
  static MapperContainer container = MapperContainer(
    mappers: {DescriptionMapper()},
  );

  @override
  DescriptionMapperElement createElement(MapperContainer container) {
    return DescriptionMapperElement._(this, container);
  }

  @override
  String get id => 'Description';

  static final fromMap = container.fromMap<Description>;
  static final fromJson = container.fromJson<Description>;
}

class DescriptionMapperElement extends MapperElementBase<Description> {
  DescriptionMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  Description decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  Description fromMap(Map<String, dynamic> map) =>
      Description(description: container.$getOpt(map, 'description'));

  @override
  Function get encoder => encode;
  dynamic encode(Description v) => toMap(v);
  Map<String, dynamic> toMap(Description d) =>
      {'description': container.$enc(d.description, 'description')};

  @override
  String stringify(Description self) =>
      'Description(description: ${container.asString(self.description)})';
  @override
  int hash(Description self) => container.hash(self.description);
  @override
  bool equals(Description self, Description other) =>
      container.isEqual(self.description, other.description);
}

mixin DescriptionMappable {
  String toJson() => DescriptionMapper.container.toJson(this as Description);
  Map<String, dynamic> toMap() =>
      DescriptionMapper.container.toMap(this as Description);
  DescriptionCopyWith<Description, Description, Description> get copyWith =>
      _DescriptionCopyWithImpl(this as Description, $identity, $identity);
  @override
  String toString() => DescriptionMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          DescriptionMapper.container.isEqual(this, other));
  @override
  int get hashCode => DescriptionMapper.container.hash(this);
}

extension DescriptionValueCopy<$R, $Out extends Description>
    on ObjectCopyWith<$R, Description, $Out> {
  DescriptionCopyWith<$R, Description, $Out> get asDescription =>
      base.as((v, t, t2) => _DescriptionCopyWithImpl(v, t, t2));
}

typedef DescriptionCopyWithBound = Description;

abstract class DescriptionCopyWith<$R, $In extends Description,
    $Out extends Description> implements ObjectCopyWith<$R, $In, $Out> {
  DescriptionCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends Description>(
      Then<Description, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? description});
}

class _DescriptionCopyWithImpl<$R, $Out extends Description>
    extends CopyWithBase<$R, Description, $Out>
    implements DescriptionCopyWith<$R, Description, $Out> {
  _DescriptionCopyWithImpl(super.value, super.then, super.then2);
  @override
  DescriptionCopyWith<$R2, Description, $Out2>
      chain<$R2, $Out2 extends Description>(
              Then<Description, $Out2> t, Then<$Out2, $R2> t2) =>
          _DescriptionCopyWithImpl($value, t, t2);

  @override
  $R call({Object? description = $none}) =>
      $then(Description(description: or(description, $value.description)));
}

class EstimateDocsMapper extends MapperBase<EstimateDocs> {
  static MapperContainer container = MapperContainer(
    mappers: {EstimateDocsMapper()},
  );

  @override
  EstimateDocsMapperElement createElement(MapperContainer container) {
    return EstimateDocsMapperElement._(this, container);
  }

  @override
  String get id => 'EstimateDocs';

  static final fromMap = container.fromMap<EstimateDocs>;
  static final fromJson = container.fromJson<EstimateDocs>;
}

class EstimateDocsMapperElement extends MapperElementBase<EstimateDocs> {
  EstimateDocsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  EstimateDocs decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  EstimateDocs fromMap(Map<String, dynamic> map) => EstimateDocs(
      tenantId: container.$getOpt(map, 'tenantId'),
      documentUid: container.$getOpt(map, 'documentUid'),
      fileStoreId: container.$getOpt(map, 'fileStoreId'),
      fileName: container.$getOpt(map, 'fileName'),
      fileType: container.$getOpt(map, 'fileType'));

  @override
  Function get encoder => encode;
  dynamic encode(EstimateDocs v) => toMap(v);
  Map<String, dynamic> toMap(EstimateDocs e) => {
        'tenantId': container.$enc(e.tenantId, 'tenantId'),
        'documentUid': container.$enc(e.documentUid, 'documentUid'),
        'fileStoreId': container.$enc(e.fileStoreId, 'fileStoreId'),
        'fileName': container.$enc(e.fileName, 'fileName'),
        'fileType': container.$enc(e.fileType, 'fileType')
      };

  @override
  String stringify(EstimateDocs self) =>
      'EstimateDocs(documentUid: ${container.asString(self.documentUid)}, fileName: ${container.asString(self.fileName)}, fileStoreId: ${container.asString(self.fileStoreId)}, fileType: ${container.asString(self.fileType)}, tenantId: ${container.asString(self.tenantId)})';
  @override
  int hash(EstimateDocs self) =>
      container.hash(self.documentUid) ^
      container.hash(self.fileName) ^
      container.hash(self.fileStoreId) ^
      container.hash(self.fileType) ^
      container.hash(self.tenantId);
  @override
  bool equals(EstimateDocs self, EstimateDocs other) =>
      container.isEqual(self.documentUid, other.documentUid) &&
      container.isEqual(self.fileName, other.fileName) &&
      container.isEqual(self.fileStoreId, other.fileStoreId) &&
      container.isEqual(self.fileType, other.fileType) &&
      container.isEqual(self.tenantId, other.tenantId);
}

mixin EstimateDocsMappable {
  String toJson() => EstimateDocsMapper.container.toJson(this as EstimateDocs);
  Map<String, dynamic> toMap() =>
      EstimateDocsMapper.container.toMap(this as EstimateDocs);
  EstimateDocsCopyWith<EstimateDocs, EstimateDocs, EstimateDocs> get copyWith =>
      _EstimateDocsCopyWithImpl(this as EstimateDocs, $identity, $identity);
  @override
  String toString() => EstimateDocsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          EstimateDocsMapper.container.isEqual(this, other));
  @override
  int get hashCode => EstimateDocsMapper.container.hash(this);
}

extension EstimateDocsValueCopy<$R, $Out extends EstimateDocs>
    on ObjectCopyWith<$R, EstimateDocs, $Out> {
  EstimateDocsCopyWith<$R, EstimateDocs, $Out> get asEstimateDocs =>
      base.as((v, t, t2) => _EstimateDocsCopyWithImpl(v, t, t2));
}

typedef EstimateDocsCopyWithBound = EstimateDocs;

abstract class EstimateDocsCopyWith<$R, $In extends EstimateDocs,
    $Out extends EstimateDocs> implements ObjectCopyWith<$R, $In, $Out> {
  EstimateDocsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends EstimateDocs>(
      Then<EstimateDocs, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? tenantId,
      String? documentUid,
      String? fileStoreId,
      String? fileName,
      String? fileType});
}

class _EstimateDocsCopyWithImpl<$R, $Out extends EstimateDocs>
    extends CopyWithBase<$R, EstimateDocs, $Out>
    implements EstimateDocsCopyWith<$R, EstimateDocs, $Out> {
  _EstimateDocsCopyWithImpl(super.value, super.then, super.then2);
  @override
  EstimateDocsCopyWith<$R2, EstimateDocs, $Out2>
      chain<$R2, $Out2 extends EstimateDocs>(
              Then<EstimateDocs, $Out2> t, Then<$Out2, $R2> t2) =>
          _EstimateDocsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? tenantId = $none,
          Object? documentUid = $none,
          Object? fileStoreId = $none,
          Object? fileName = $none,
          Object? fileType = $none}) =>
      $then(EstimateDocs(
          tenantId: or(tenantId, $value.tenantId),
          documentUid: or(documentUid, $value.documentUid),
          fileStoreId: or(fileStoreId, $value.fileStoreId),
          fileName: or(fileName, $value.fileName),
          fileType: or(fileType, $value.fileType)));
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
  )..linkAll({DocumentAdditionalDetailsMapper.container});

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
      additionalDetails: container.$getOpt(map, 'additionalDetails'),
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
        'additionalDetails':
            container.$enc(d.additionalDetails, 'additionalDetails'),
        'contractId': container.$enc(d.contractId, 'contractId'),
        'id': container.$enc(d.id, 'id'),
        'documentType': container.$enc(d.documentType, 'documentType'),
        'status': container.$enc(d.status, 'status'),
        'documentUid': container.$enc(d.documentUid, 'documentUid'),
        'fileStore': container.$enc(d.fileStore, 'fileStore')
      };

  @override
  String stringify(Documents self) =>
      'Documents(additionalDetails: ${container.asString(self.additionalDetails)}, contractId: ${container.asString(self.contractId)}, id: ${container.asString(self.id)}, documentType: ${container.asString(self.documentType)}, fileStore: ${container.asString(self.fileStore)}, documentUid: ${container.asString(self.documentUid)}, status: ${container.asString(self.status)})';
  @override
  int hash(Documents self) =>
      container.hash(self.additionalDetails) ^
      container.hash(self.contractId) ^
      container.hash(self.id) ^
      container.hash(self.documentType) ^
      container.hash(self.fileStore) ^
      container.hash(self.documentUid) ^
      container.hash(self.status);
  @override
  bool equals(Documents self, Documents other) =>
      container.isEqual(self.additionalDetails, other.additionalDetails) &&
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
  DocumentAdditionalDetailsCopyWith<$R, DocumentAdditionalDetails,
          DocumentAdditionalDetails>?
      get additionalDetails => $value.additionalDetails?.copyWith
          .chain($identity, (v) => call(additionalDetails: v));
  @override
  $R call(
          {Object? additionalDetails = $none,
          Object? contractId = $none,
          Object? id = $none,
          Object? documentType = $none,
          Object? status = $none,
          Object? documentUid = $none,
          Object? fileStore = $none}) =>
      $then(Documents(
          additionalDetails: or(additionalDetails, $value.additionalDetails),
          contractId: or(contractId, $value.contractId),
          id: or(id, $value.id),
          documentType: or(documentType, $value.documentType),
          status: or(status, $value.status),
          documentUid: or(documentUid, $value.documentUid),
          fileStore: or(fileStore, $value.fileStore)));
}

class DocumentAdditionalDetailsMapper
    extends MapperBase<DocumentAdditionalDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {DocumentAdditionalDetailsMapper()},
  );

  @override
  DocumentAdditionalDetailsMapperElement createElement(
      MapperContainer container) {
    return DocumentAdditionalDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'DocumentAdditionalDetails';

  static final fromMap = container.fromMap<DocumentAdditionalDetails>;
  static final fromJson = container.fromJson<DocumentAdditionalDetails>;
}

class DocumentAdditionalDetailsMapperElement
    extends MapperElementBase<DocumentAdditionalDetails> {
  DocumentAdditionalDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  DocumentAdditionalDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  DocumentAdditionalDetails fromMap(Map<String, dynamic> map) =>
      DocumentAdditionalDetails(container.$getOpt(map, 'fileName'),
          container.$getOpt(map, 'otherCategoryName'));

  @override
  Function get encoder => encode;
  dynamic encode(DocumentAdditionalDetails v) => toMap(v);
  Map<String, dynamic> toMap(DocumentAdditionalDetails d) => {
        'fileName': container.$enc(d.fileName, 'fileName'),
        'otherCategoryName':
            container.$enc(d.otherCategoryName, 'otherCategoryName')
      };

  @override
  String stringify(DocumentAdditionalDetails self) =>
      'DocumentAdditionalDetails(fileName: ${container.asString(self.fileName)}, otherCategoryName: ${container.asString(self.otherCategoryName)})';
  @override
  int hash(DocumentAdditionalDetails self) =>
      container.hash(self.fileName) ^ container.hash(self.otherCategoryName);
  @override
  bool equals(
          DocumentAdditionalDetails self, DocumentAdditionalDetails other) =>
      container.isEqual(self.fileName, other.fileName) &&
      container.isEqual(self.otherCategoryName, other.otherCategoryName);
}

mixin DocumentAdditionalDetailsMappable {
  String toJson() => DocumentAdditionalDetailsMapper.container
      .toJson(this as DocumentAdditionalDetails);
  Map<String, dynamic> toMap() => DocumentAdditionalDetailsMapper.container
      .toMap(this as DocumentAdditionalDetails);
  DocumentAdditionalDetailsCopyWith<DocumentAdditionalDetails,
          DocumentAdditionalDetails, DocumentAdditionalDetails>
      get copyWith => _DocumentAdditionalDetailsCopyWithImpl(
          this as DocumentAdditionalDetails, $identity, $identity);
  @override
  String toString() => DocumentAdditionalDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          DocumentAdditionalDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => DocumentAdditionalDetailsMapper.container.hash(this);
}

extension DocumentAdditionalDetailsValueCopy<$R,
        $Out extends DocumentAdditionalDetails>
    on ObjectCopyWith<$R, DocumentAdditionalDetails, $Out> {
  DocumentAdditionalDetailsCopyWith<$R, DocumentAdditionalDetails, $Out>
      get asDocumentAdditionalDetails => base
          .as((v, t, t2) => _DocumentAdditionalDetailsCopyWithImpl(v, t, t2));
}

typedef DocumentAdditionalDetailsCopyWithBound = DocumentAdditionalDetails;

abstract class DocumentAdditionalDetailsCopyWith<
        $R,
        $In extends DocumentAdditionalDetails,
        $Out extends DocumentAdditionalDetails>
    implements ObjectCopyWith<$R, $In, $Out> {
  DocumentAdditionalDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends DocumentAdditionalDetails>(
          Then<DocumentAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? fileName, String? otherCategoryName});
}

class _DocumentAdditionalDetailsCopyWithImpl<$R,
        $Out extends DocumentAdditionalDetails>
    extends CopyWithBase<$R, DocumentAdditionalDetails, $Out>
    implements
        DocumentAdditionalDetailsCopyWith<$R, DocumentAdditionalDetails, $Out> {
  _DocumentAdditionalDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  DocumentAdditionalDetailsCopyWith<$R2, DocumentAdditionalDetails, $Out2>
      chain<$R2, $Out2 extends DocumentAdditionalDetails>(
              Then<DocumentAdditionalDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _DocumentAdditionalDetailsCopyWithImpl($value, t, t2);

  @override
  $R call({Object? fileName = $none, Object? otherCategoryName = $none}) =>
      $then(DocumentAdditionalDetails(or(fileName, $value.fileName),
          or(otherCategoryName, $value.otherCategoryName)));
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
