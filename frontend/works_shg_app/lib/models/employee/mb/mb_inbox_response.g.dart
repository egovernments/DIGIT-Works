// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'mb_inbox_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MBInboxResponseImpl _$$MBInboxResponseImplFromJson(
        Map<String, dynamic> json) =>
    _$MBInboxResponseImpl(
      totalCount: (json['totalCount'] as num?)?.toInt(),
      nearingSlaCount: (json['nearingSlaCount'] as num?)?.toInt(),
      statusMap: (json['statusMap'] as List<dynamic>?)
          ?.map((e) => StatusMap.fromJson(e as Map<String, dynamic>))
          .toList(),
      items: (json['items'] as List<dynamic>?)
          ?.map((e) => ItemData.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MBInboxResponseImplToJson(
        _$MBInboxResponseImpl instance) =>
    <String, dynamic>{
      'totalCount': instance.totalCount,
      'nearingSlaCount': instance.nearingSlaCount,
      'statusMap': instance.statusMap,
      'items': instance.items,
    };

_$StatusMapImpl _$$StatusMapImplFromJson(Map<String, dynamic> json) =>
    _$StatusMapImpl(
      statusid: json['statusid'] as String?,
      count: (json['count'] as num?)?.toInt(),
      state: json['state'] as String?,
      applicationstatus: json['applicationstatus'] as String?,
      businessservice: json['businessservice'] as String?,
    );

Map<String, dynamic> _$$StatusMapImplToJson(_$StatusMapImpl instance) =>
    <String, dynamic>{
      'statusid': instance.statusid,
      'count': instance.count,
      'state': instance.state,
      'applicationstatus': instance.applicationstatus,
      'businessservice': instance.businessservice,
    };

_$ItemDataImpl _$$ItemDataImplFromJson(Map<String, dynamic> json) =>
    _$ItemDataImpl(
      processInstance: json['ProcessInstance'] == null
          ? null
          : ProcessInstance.fromJson(
              json['ProcessInstance'] as Map<String, dynamic>),
      businessObject: json['businessObject'] == null
          ? null
          : BusinessObject.fromJson(
              json['businessObject'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$ItemDataImplToJson(_$ItemDataImpl instance) =>
    <String, dynamic>{
      'ProcessInstance': instance.processInstance,
      'businessObject': instance.businessObject,
    };

_$ProcessInstanceImpl _$$ProcessInstanceImplFromJson(
        Map<String, dynamic> json) =>
    _$ProcessInstanceImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
      businessService: json['businessService'] as String?,
      businessId: json['businessId'] as String?,
      action: json['action'] as String?,
      moduleName: json['moduleName'] as String?,
      businesssServiceSla: (json['businesssServiceSla'] as num?)?.toInt(),
      rating: (json['rating'] as num?)?.toInt(),
      state: json['state'] == null
          ? null
          : State.fromJson(json['state'] as Map<String, dynamic>),
      assigner: json['assigner'] == null
          ? null
          : Assigner.fromJson(json['assigner'] as Map<String, dynamic>),
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
      assignes: (json['assignes'] as List<dynamic>?)
          ?.map((e) => Assigne.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$ProcessInstanceImplToJson(
        _$ProcessInstanceImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'businessService': instance.businessService,
      'businessId': instance.businessId,
      'action': instance.action,
      'moduleName': instance.moduleName,
      'businesssServiceSla': instance.businesssServiceSla,
      'rating': instance.rating,
      'state': instance.state,
      'assigner': instance.assigner,
      'auditDetails': instance.auditDetails,
      'assignes': instance.assignes,
    };

_$AuditDetailsImpl _$$AuditDetailsImplFromJson(Map<String, dynamic> json) =>
    _$AuditDetailsImpl(
      createdBy: json['createdBy'] as String?,
      lastModifiedBy: json['lastModifiedBy'] as String?,
      createdTime: (json['createdTime'] as num?)?.toInt(),
      lastModifiedTime: (json['lastModifiedTime'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$AuditDetailsImplToJson(_$AuditDetailsImpl instance) =>
    <String, dynamic>{
      'createdBy': instance.createdBy,
      'lastModifiedBy': instance.lastModifiedBy,
      'createdTime': instance.createdTime,
      'lastModifiedTime': instance.lastModifiedTime,
    };

_$StateImpl _$$StateImplFromJson(Map<String, dynamic> json) => _$StateImpl(
      uuid: json['uuid'] as String?,
      tenantId: json['tenantId'] as String?,
      businessServiceId: json['businessServiceId'] as String?,
      sla: (json['sla'] as num?)?.toInt(),
      state: json['state'] as String?,
      applicationStatus: json['applicationStatus'] as String?,
      docUploadRequired: json['docUploadRequired'] as bool?,
      isStartState: json['isStartState'] as bool?,
      isTerminateState: json['isTerminateState'] as bool?,
      isStateUpdatable: json['isStateUpdatable'] as bool?,
      actions: (json['actions'] as List<dynamic>?)
          ?.map((e) => Action.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$StateImplToJson(_$StateImpl instance) =>
    <String, dynamic>{
      'uuid': instance.uuid,
      'tenantId': instance.tenantId,
      'businessServiceId': instance.businessServiceId,
      'sla': instance.sla,
      'state': instance.state,
      'applicationStatus': instance.applicationStatus,
      'docUploadRequired': instance.docUploadRequired,
      'isStartState': instance.isStartState,
      'isTerminateState': instance.isTerminateState,
      'isStateUpdatable': instance.isStateUpdatable,
      'actions': instance.actions,
    };

_$ActionImpl _$$ActionImplFromJson(Map<String, dynamic> json) => _$ActionImpl(
      uuid: json['uuid'] as String?,
      tenantId: json['tenantId'] as String?,
      currentState: json['currentState'] as String?,
      action: json['action'] as String?,
      nextState: json['nextState'] as String?,
      active: json['active'] as bool?,
      roles:
          (json['roles'] as List<dynamic>?)?.map((e) => e as String).toList(),
    );

Map<String, dynamic> _$$ActionImplToJson(_$ActionImpl instance) =>
    <String, dynamic>{
      'uuid': instance.uuid,
      'tenantId': instance.tenantId,
      'currentState': instance.currentState,
      'action': instance.action,
      'nextState': instance.nextState,
      'active': instance.active,
      'roles': instance.roles,
    };

_$AssignerImpl _$$AssignerImplFromJson(Map<String, dynamic> json) =>
    _$AssignerImpl(
      id: (json['id'] as num?)?.toInt(),
      userName: json['userName'] as String?,
      name: json['name'] as String?,
      type: json['type'] as String?,
      mobileNumber: json['mobileNumber'] as String?,
      emailId: json['emailId'] as String?,
      tenantId: json['tenantId'] as String?,
      uuid: json['uuid'] as String?,
      roles: (json['roles'] as List<dynamic>?)
          ?.map((e) => Role.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$AssignerImplToJson(_$AssignerImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userName': instance.userName,
      'name': instance.name,
      'type': instance.type,
      'mobileNumber': instance.mobileNumber,
      'emailId': instance.emailId,
      'tenantId': instance.tenantId,
      'uuid': instance.uuid,
      'roles': instance.roles,
    };

_$AssigneImpl _$$AssigneImplFromJson(Map<String, dynamic> json) =>
    _$AssigneImpl(
      id: (json['id'] as num?)?.toInt(),
      userName: json['userName'] as String?,
      name: json['name'] as String?,
      type: json['type'] as String?,
      mobileNumber: json['mobileNumber'] as String?,
      emailId: json['emailId'] as String?,
      tenantId: json['tenantId'] as String?,
      uuid: json['uuid'] as String?,
      roles: (json['roles'] as List<dynamic>?)
          ?.map((e) => Role.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$AssigneImplToJson(_$AssigneImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userName': instance.userName,
      'name': instance.name,
      'type': instance.type,
      'mobileNumber': instance.mobileNumber,
      'emailId': instance.emailId,
      'tenantId': instance.tenantId,
      'uuid': instance.uuid,
      'roles': instance.roles,
    };

_$RoleImpl _$$RoleImplFromJson(Map<String, dynamic> json) => _$RoleImpl(
      name: json['name'] as String?,
      id: json['id'] as String?,
      code: json['code'] as String?,
      tenantId: json['tenantId'] as String?,
    );

Map<String, dynamic> _$$RoleImplToJson(_$RoleImpl instance) =>
    <String, dynamic>{
      'name': instance.name,
      'id': instance.id,
      'code': instance.code,
      'tenantId': instance.tenantId,
    };

_$BusinessObjectImpl _$$BusinessObjectImplFromJson(Map<String, dynamic> json) =>
    _$BusinessObjectImpl(
      measurementNumber: json['measurementNumber'] as String?,
      id: json['id'] as String?,
      referenceId: json['referenceId'] as String?,
      measures: (json['measures'] as List<dynamic>?)
          ?.map((e) => Measure.fromJson(e as Map<String, dynamic>))
          .toList(),
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
      contract: json['contract'] == null
          ? null
          : Contract.fromJson(json['contract'] as Map<String, dynamic>),
      serviceSla: (json['serviceSla'] as num?)?.toInt(),
      measurementAdditionalDetail: json['additionalDetails'] == null
          ? null
          : MeasurementAdditionalDetail.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$BusinessObjectImplToJson(
        _$BusinessObjectImpl instance) =>
    <String, dynamic>{
      'measurementNumber': instance.measurementNumber,
      'id': instance.id,
      'referenceId': instance.referenceId,
      'measures': instance.measures,
      'auditDetails': instance.auditDetails,
      'contract': instance.contract,
      'serviceSla': instance.serviceSla,
      'additionalDetails': instance.measurementAdditionalDetail,
    };

_$MeasureImpl _$$MeasureImplFromJson(Map<String, dynamic> json) =>
    _$MeasureImpl(
      description: json['description'] as String?,
      comments: json['comments'] as String?,
      targetId: json['targetId'] as String?,
      breadth: (json['breadth'] as num?)?.toDouble(),
      length: (json['length'] as num?)?.toDouble(),
      isActive: json['isActive'] as bool?,
      referenceId: json['referenceId'] as String?,
      numItems: (json['numItems'] as num?)?.toDouble(),
      id: json['id'] as String?,
      currentValue: (json['currentValue'] as num?)?.toDouble(),
      cumulativeValue: (json['cumulativeValue'] as num?)?.toDouble(),
      height: (json['height'] as num?)?.toDouble(),
      measureAdditionalDetails: json['additionalDetails'] == null
          ? null
          : MeasureAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$MeasureImplToJson(_$MeasureImpl instance) =>
    <String, dynamic>{
      'description': instance.description,
      'comments': instance.comments,
      'targetId': instance.targetId,
      'breadth': instance.breadth,
      'length': instance.length,
      'isActive': instance.isActive,
      'referenceId': instance.referenceId,
      'numItems': instance.numItems,
      'id': instance.id,
      'currentValue': instance.currentValue,
      'cumulativeValue': instance.cumulativeValue,
      'height': instance.height,
      'additionalDetails': instance.measureAdditionalDetails,
    };

_$MeasureAdditionalDetailsImpl _$$MeasureAdditionalDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$MeasureAdditionalDetailsImpl(
      type: json['type'] as String?,
      mbAmount: (json['mbAmount'] as num?)?.toDouble(),
      measureLineItems: (json['measureLineItems'] as List<dynamic>?)
          ?.map((e) => MeasureLineItem.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MeasureAdditionalDetailsImplToJson(
        _$MeasureAdditionalDetailsImpl instance) =>
    <String, dynamic>{
      'type': instance.type,
      'mbAmount': instance.mbAmount,
      'measureLineItems': instance.measureLineItems,
    };

_$MeasureLineItemImpl _$$MeasureLineItemImplFromJson(
        Map<String, dynamic> json) =>
    _$MeasureLineItemImpl(
      width: json['width'],
      height: json['height'],
      length: json['length'],
      number: json['number'],
      quantity: json['quantity'],
      measurelineitemNo: json['measurelineitemNo'],
      measurementSummary: json['measureSummary'],
    );

Map<String, dynamic> _$$MeasureLineItemImplToJson(
        _$MeasureLineItemImpl instance) =>
    <String, dynamic>{
      'width': instance.width,
      'height': instance.height,
      'length': instance.length,
      'number': instance.number,
      'quantity': instance.quantity,
      'measurelineitemNo': instance.measurelineitemNo,
      'measureSummary': instance.measurementSummary,
    };

_$ContractImpl _$$ContractImplFromJson(Map<String, dynamic> json) =>
    _$ContractImpl(
      contractNumber: json['contractNumber'] as String?,
      id: json['id'] as String?,
      versionNumber: (json['versionNumber'] as num?)?.toInt(),
      oldUuid: json['oldUuid'] as String?,
      businessService: json['businessService'] as String?,
      tenantId: json['tenantId'] as String?,
      wfStatus: json['wfStatus'] as String?,
      executingAuthority: json['executingAuthority'] as String?,
      contractType: json['contractType'] as String?,
      totalContractedAmount:
          (json['totalContractedAmount'] as num?)?.toDouble(),
      securityDeposit: (json['securityDeposit'] as num?)?.toDouble(),
      agreementDate: (json['agreementDate'] as num?)?.toInt(),
      issueDate: (json['issueDate'] as num?)?.toInt(),
      defectLiabilityPeriod: (json['defectLiabilityPeriod'] as num?)?.toInt(),
      orgId: json['orgId'] as String?,
      startDate: (json['startDate'] as num?)?.toInt(),
      endDate: (json['endDate'] as num?)?.toInt(),
      completionPeriod: (json['completionPeriod'] as num?)?.toInt(),
      status: json['status'] as String?,
      lineItems: (json['lineItems'] as List<dynamic>?)
          ?.map((e) => LineItem.fromJson(e as Map<String, dynamic>))
          .toList(),
      additionalDetails: json['additionalDetails'] == null
          ? null
          : ContractAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$ContractImplToJson(_$ContractImpl instance) =>
    <String, dynamic>{
      'contractNumber': instance.contractNumber,
      'id': instance.id,
      'versionNumber': instance.versionNumber,
      'oldUuid': instance.oldUuid,
      'businessService': instance.businessService,
      'tenantId': instance.tenantId,
      'wfStatus': instance.wfStatus,
      'executingAuthority': instance.executingAuthority,
      'contractType': instance.contractType,
      'totalContractedAmount': instance.totalContractedAmount,
      'securityDeposit': instance.securityDeposit,
      'agreementDate': instance.agreementDate,
      'issueDate': instance.issueDate,
      'defectLiabilityPeriod': instance.defectLiabilityPeriod,
      'orgId': instance.orgId,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'completionPeriod': instance.completionPeriod,
      'status': instance.status,
      'lineItems': instance.lineItems,
      'additionalDetails': instance.additionalDetails,
    };

_$ContractAdditionalDetailsImpl _$$ContractAdditionalDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$ContractAdditionalDetailsImpl(
      orgName: json['orgName'] as String?,
      totalEstimatedAmount: json['totalEstimatedAmount'],
      attendanceRegisterNumber: json['attendanceRegisterNumber'] as String?,
      officerInChargeId: json['officerInChargeId'] as String?,
      cboOrgNumber: json['cboOrgNumber'] as String?,
      estimateNumber: json['estimateNumber'] as String?,
      locality: json['locality'] as String?,
      projectType: json['projectType'] as String?,
      timeExtReason: json['timeExtReason'] as String?,
      ward: json['ward'] as String?,
      officerInChargeDesgn: json['officerInChargeDesgn'] as String?,
      projectDesc: json['projectDesc'] as String?,
      projectName: json['projectName'] as String?,
      cboCode: json['cboCode'] as String?,
      projectId: json['projectId'] as String?,
      cboName: json['cboName'] as String?,
      timeExt: json['timeExt'],
      completionPeriod: (json['completionPeriod'] as num?)?.toInt(),
      estmateDocs: (json['estimateDocs'] as List<dynamic>?)
          ?.map((e) => EstmateDoc.fromJson(e as Map<String, dynamic>))
          .toList(),
      termsAndConditions: (json['termsAndConditions'] as List<dynamic>?)
          ?.map((e) => TermsAndConditions.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$ContractAdditionalDetailsImplToJson(
        _$ContractAdditionalDetailsImpl instance) =>
    <String, dynamic>{
      'orgName': instance.orgName,
      'totalEstimatedAmount': instance.totalEstimatedAmount,
      'attendanceRegisterNumber': instance.attendanceRegisterNumber,
      'officerInChargeId': instance.officerInChargeId,
      'cboOrgNumber': instance.cboOrgNumber,
      'estimateNumber': instance.estimateNumber,
      'locality': instance.locality,
      'projectType': instance.projectType,
      'timeExtReason': instance.timeExtReason,
      'ward': instance.ward,
      'officerInChargeDesgn': instance.officerInChargeDesgn,
      'projectDesc': instance.projectDesc,
      'projectName': instance.projectName,
      'cboCode': instance.cboCode,
      'projectId': instance.projectId,
      'cboName': instance.cboName,
      'timeExt': instance.timeExt,
      'completionPeriod': instance.completionPeriod,
      'estimateDocs': instance.estmateDocs,
      'termsAndConditions': instance.termsAndConditions,
    };

_$EstmateDocImpl _$$EstmateDocImplFromJson(Map<String, dynamic> json) =>
    _$EstmateDocImpl(
      fileName: json['fileName'] as String?,
      fileType: json['fileType'] as String?,
      tenantId: json['tenantId'] as String?,
      documentUid: json['documentUid'] as String?,
      fileStoreId: json['fileStoreId'] as String?,
    );

Map<String, dynamic> _$$EstmateDocImplToJson(_$EstmateDocImpl instance) =>
    <String, dynamic>{
      'fileName': instance.fileName,
      'fileType': instance.fileType,
      'tenantId': instance.tenantId,
      'documentUid': instance.documentUid,
      'fileStoreId': instance.fileStoreId,
    };

_$TermsAndConditionsImpl _$$TermsAndConditionsImplFromJson(
        Map<String, dynamic> json) =>
    _$TermsAndConditionsImpl(
      description: json['description'] as String?,
    );

Map<String, dynamic> _$$TermsAndConditionsImplToJson(
        _$TermsAndConditionsImpl instance) =>
    <String, dynamic>{
      'description': instance.description,
    };

_$LineItemImpl _$$LineItemImplFromJson(Map<String, dynamic> json) =>
    _$LineItemImpl(
      id: json['id'] as String?,
      estimateId: json['estimateId'] as String?,
      estimateLineItemId: json['estimateLineItemId'] as String?,
      contractLineItemRef: json['contractLineItemRef'] as String?,
      tenantId: json['tenantId'] as String?,
      unitRate: (json['unitRate'] as num?)?.toDouble(),
      category: json['category'] as String?,
      noOfunit: json['noOfunit'] as num?,
      name: json['name'] as String?,
      status: json['status'] as String?,
      amountBreakups: (json['amountBreakups'] as List<dynamic>?)
          ?.map((e) => AmountBreakup.fromJson(e as Map<String, dynamic>))
          .toList(),
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$LineItemImplToJson(_$LineItemImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'estimateId': instance.estimateId,
      'estimateLineItemId': instance.estimateLineItemId,
      'contractLineItemRef': instance.contractLineItemRef,
      'tenantId': instance.tenantId,
      'unitRate': instance.unitRate,
      'category': instance.category,
      'noOfunit': instance.noOfunit,
      'name': instance.name,
      'status': instance.status,
      'amountBreakups': instance.amountBreakups,
      'auditDetails': instance.auditDetails,
    };

_$AmountBreakupImpl _$$AmountBreakupImplFromJson(Map<String, dynamic> json) =>
    _$AmountBreakupImpl(
      id: json['id'] as String?,
      estimateAmountBreakupId: json['estimateAmountBreakupId'] as String?,
      amount: (json['amount'] as num?)?.toDouble(),
      status: json['status'] as String?,
    );

Map<String, dynamic> _$$AmountBreakupImplToJson(_$AmountBreakupImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'estimateAmountBreakupId': instance.estimateAmountBreakupId,
      'amount': instance.amount,
      'status': instance.status,
    };
