// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'mb_detail_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MBDetailResponseImpl _$$MBDetailResponseImplFromJson(
        Map<String, dynamic> json) =>
    _$MBDetailResponseImpl(
      contract: json['contract'] == null
          ? null
          : Contract.fromJson(json['contract'] as Map<String, dynamic>),
      estimate: json['estimate'] == null
          ? null
          : Estimate.fromJson(json['estimate'] as Map<String, dynamic>),
      period: json['period'] == null
          ? null
          : Period.fromJson(json['period'] as Map<String, dynamic>),
      allMeasurements: json['allMeasurements'],
      measurement: json['measurement'] == null
          ? null
          : Measurement.fromJson(json['measurement'] as Map<String, dynamic>),
      musterRolls: json['musterRolls'],
    );

Map<String, dynamic> _$$MBDetailResponseImplToJson(
        _$MBDetailResponseImpl instance) =>
    <String, dynamic>{
      'contract': instance.contract,
      'estimate': instance.estimate,
      'period': instance.period,
      'allMeasurements': instance.allMeasurements,
      'measurement': instance.measurement,
      'musterRolls': instance.musterRolls,
    };

_$WorkFlowImpl _$$WorkFlowImplFromJson(Map<String, dynamic> json) =>
    _$WorkFlowImpl(
      action: json['action'] as String?,
      comment: json['comment'] as String?,
      assignees: (json['assignees'] as List<dynamic>?)
          ?.map((e) => e as String)
          .toList(),
      documents: (json['documents'] as List<dynamic>?)
          ?.map((e) =>
              WorkFlowSupportDocument.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$WorkFlowImplToJson(_$WorkFlowImpl instance) =>
    <String, dynamic>{
      'action': instance.action,
      'comment': instance.comment,
      'assignees': instance.assignees,
      'documents': instance.documents,
    };

_$WorkFlowSupportDocumentImpl _$$WorkFlowSupportDocumentImplFromJson(
        Map<String, dynamic> json) =>
    _$WorkFlowSupportDocumentImpl(
      documentType: json['documentType'] as String?,
      documentUid: json['documentUid'] as String?,
      fileName: json['fileName'] as String?,
      fileStoreId: json['fileStoreId'] as String?,
      tenantId: json['tenantId'] as String?,
    );

Map<String, dynamic> _$$WorkFlowSupportDocumentImplToJson(
        _$WorkFlowSupportDocumentImpl instance) =>
    <String, dynamic>{
      'documentType': instance.documentType,
      'documentUid': instance.documentUid,
      'fileName': instance.fileName,
      'fileStoreId': instance.fileStoreId,
      'tenantId': instance.tenantId,
    };

_$MusterRollImpl _$$MusterRollImplFromJson(Map<String, dynamic> json) =>
    _$MusterRollImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
      musterRollNumber: json['musterRollNumber'],
      registerId: json['registerId'] as String?,
      status: json['status'] as String?,
      musterRollStatus: json['musterRollStatus'] as String?,
      startDate: (json['startDate'] as num?)?.toInt(),
      endDate: (json['endDate'] as num?)?.toInt(),
      referenceId: json['referenceId'] as String?,
      serviceCode: json['serviceCode'] as String?,
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
      additional: json['additionalDetails'] == null
          ? null
          : MusterRollAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$MusterRollImplToJson(_$MusterRollImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'musterRollNumber': instance.musterRollNumber,
      'registerId': instance.registerId,
      'status': instance.status,
      'musterRollStatus': instance.musterRollStatus,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'referenceId': instance.referenceId,
      'serviceCode': instance.serviceCode,
      'auditDetails': instance.auditDetails,
      'additionalDetails': instance.additional,
    };

_$MusterRollAdditionalDetailsImpl _$$MusterRollAdditionalDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$MusterRollAdditionalDetailsImpl(
      ward: json['ward'] as String?,
      orgId: json['orgId'] as String?,
      amount: (json['amount'] as num?)?.toDouble(),
      orgName: json['orgName'] as String?,
      locality: json['locality'] as String?,
      projectId: json['projectId'] as String?,
      contractId: json['contractId'] as String?,
      projectDesc: json['projectDesc'] as String?,
      projectName: json['projectName'] as String?,
      projectType: json['projectType'] as String?,
      executingAuthority: json['executingAuthority'] as String?,
      attendanceRegisterNo: json['attendanceRegisterNo'] as String?,
      attendanceRegisterName: json['attendanceRegisterName'] as String?,
    );

Map<String, dynamic> _$$MusterRollAdditionalDetailsImplToJson(
        _$MusterRollAdditionalDetailsImpl instance) =>
    <String, dynamic>{
      'ward': instance.ward,
      'orgId': instance.orgId,
      'amount': instance.amount,
      'orgName': instance.orgName,
      'locality': instance.locality,
      'projectId': instance.projectId,
      'contractId': instance.contractId,
      'projectDesc': instance.projectDesc,
      'projectName': instance.projectName,
      'projectType': instance.projectType,
      'executingAuthority': instance.executingAuthority,
      'attendanceRegisterNo': instance.attendanceRegisterNo,
      'attendanceRegisterName': instance.attendanceRegisterName,
    };

_$MeasurementImpl _$$MeasurementImplFromJson(Map<String, dynamic> json) =>
    _$MeasurementImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
      measurementNumber: json['measurementNumber'] as String?,
      physicalRefNumber: json['physicalRefNumber'] as String?,
      referenceId: json['referenceId'] as String?,
      entryDate: (json['entryDate'] as num?)?.toInt(),
      isActive: json['isActive'] as bool?,
      wfStatus: json['wfStatus'] as String?,
      workflow: json['workflow'] == null
          ? null
          : WorkFlow.fromJson(json['workflow'] as Map<String, dynamic>),
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
      additionalDetail: json['additionalDetails'] == null
          ? null
          : MeasurementAdditionalDetail.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
      measures: (json['measures'] as List<dynamic>?)
          ?.map((e) => Measure.fromJson(e as Map<String, dynamic>))
          .toList(),
      documents: (json['documents'] as List<dynamic>?)
          ?.map((e) => WorkflowDocument.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MeasurementImplToJson(_$MeasurementImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'measurementNumber': instance.measurementNumber,
      'physicalRefNumber': instance.physicalRefNumber,
      'referenceId': instance.referenceId,
      'entryDate': instance.entryDate,
      'isActive': instance.isActive,
      'wfStatus': instance.wfStatus,
      'workflow': instance.workflow,
      'auditDetails': instance.auditDetails,
      'additionalDetails': instance.additionalDetail,
      'measures': instance.measures,
      'documents': instance.documents,
    };

_$MeasurementAdditionalDetailImpl _$$MeasurementAdditionalDetailImplFromJson(
        Map<String, dynamic> json) =>
    _$MeasurementAdditionalDetailImpl(
      endDate: (json['endDate'] as num?)?.toInt(),
      sorAmount: (json['sorAmount'] as num?)?.toDouble(),
      startDate: (json['startDate'] as num?)?.toInt(),
      totalAmount: (json['totalAmount'] as num?)?.toDouble(),
      nonSorAmount: (json['nonSorAmount'] as num?)?.toDouble(),
      musterRollNumber: json['musterRollNumber'],
      source: json['source'],
    );

Map<String, dynamic> _$$MeasurementAdditionalDetailImplToJson(
        _$MeasurementAdditionalDetailImpl instance) =>
    <String, dynamic>{
      'endDate': instance.endDate,
      'sorAmount': instance.sorAmount,
      'startDate': instance.startDate,
      'totalAmount': instance.totalAmount,
      'nonSorAmount': instance.nonSorAmount,
      'musterRollNumber': instance.musterRollNumber,
      'source': instance.source,
    };

_$PeriodImpl _$$PeriodImplFromJson(Map<String, dynamic> json) => _$PeriodImpl(
      startDate: (json['startDate'] as num?)?.toInt(),
      endDate: (json['endDate'] as num?)?.toInt(),
      message: json['message'] as String?,
      type: json['type'] as String?,
    );

Map<String, dynamic> _$$PeriodImplToJson(_$PeriodImpl instance) =>
    <String, dynamic>{
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'message': instance.message,
      'type': instance.type,
    };

_$EstimateImpl _$$EstimateImplFromJson(Map<String, dynamic> json) =>
    _$EstimateImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
      estimateNumber: json['estimateNumber'] as String?,
      revisionNumber: json['revisionNumber'] as String?,
      businessService: json['businessService'] as String?,
      oldUuid: json['oldUuid'] as String?,
      projectId: json['projectId'] as String?,
      versionNumber: (json['versionNumber'] as num?)?.toInt(),
      proposalDate: (json['proposalDate'] as num?)?.toInt(),
      status: json['status'] as String?,
      wfStatus: json['wfStatus'] as String?,
      name: json['name'] as String?,
      referenceNumber: json['referenceNumber'] as String?,
      description: json['description'] as String?,
      executingDepartment: json['executingDepartment'] as String?,
      address: json['address'] == null
          ? null
          : EstimateAddress.fromJson(json['address'] as Map<String, dynamic>),
      estimateDetails: (json['estimateDetails'] as List<dynamic>?)
          ?.map((e) => EstimateDetail.fromJson(e as Map<String, dynamic>))
          .toList(),
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$EstimateImplToJson(_$EstimateImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'estimateNumber': instance.estimateNumber,
      'revisionNumber': instance.revisionNumber,
      'businessService': instance.businessService,
      'oldUuid': instance.oldUuid,
      'projectId': instance.projectId,
      'versionNumber': instance.versionNumber,
      'proposalDate': instance.proposalDate,
      'status': instance.status,
      'wfStatus': instance.wfStatus,
      'name': instance.name,
      'referenceNumber': instance.referenceNumber,
      'description': instance.description,
      'executingDepartment': instance.executingDepartment,
      'address': instance.address,
      'estimateDetails': instance.estimateDetails,
      'auditDetails': instance.auditDetails,
    };

_$EstimateDetailImpl _$$EstimateDetailImplFromJson(Map<String, dynamic> json) =>
    _$EstimateDetailImpl(
      id: json['id'] as String?,
      previousLineItemId: json['previousLineItemId'] as String?,
      sorId: json['sorId'] as String?,
      category: json['category'] as String?,
      name: json['name'] as String?,
      description: json['description'] as String?,
      unitRate: (json['unitRate'] as num?)?.toDouble(),
      noOfunit: json['noOfunit'] as num?,
      uom: json['uom'] as String?,
      uomValue: (json['uomValue'] as num?)?.toDouble(),
      length: json['length'] as num?,
      width: json['width'] as num?,
      height: json['height'] as num?,
      quantity: json['quantity'] as num?,
      isDeduction: json['isDeduction'] as bool?,
      isActive: json['isActive'] as bool?,
      amountDetails: (json['amountDetail'] as List<dynamic>?)
          ?.map((e) => AmoutDetail.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$EstimateDetailImplToJson(
        _$EstimateDetailImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'previousLineItemId': instance.previousLineItemId,
      'sorId': instance.sorId,
      'category': instance.category,
      'name': instance.name,
      'description': instance.description,
      'unitRate': instance.unitRate,
      'noOfunit': instance.noOfunit,
      'uom': instance.uom,
      'uomValue': instance.uomValue,
      'length': instance.length,
      'width': instance.width,
      'height': instance.height,
      'quantity': instance.quantity,
      'isDeduction': instance.isDeduction,
      'isActive': instance.isActive,
      'amountDetail': instance.amountDetails,
    };

_$AmoutDetailImpl _$$AmoutDetailImplFromJson(Map<String, dynamic> json) =>
    _$AmoutDetailImpl(
      type: json['type'] as String?,
      amount: (json['amount'] as num?)?.toDouble(),
      id: json['id'] as String?,
      isActive: json['isActive'] as bool?,
    );

Map<String, dynamic> _$$AmoutDetailImplToJson(_$AmoutDetailImpl instance) =>
    <String, dynamic>{
      'type': instance.type,
      'amount': instance.amount,
      'id': instance.id,
      'isActive': instance.isActive,
    };

_$EstimateAddressImpl _$$EstimateAddressImplFromJson(
        Map<String, dynamic> json) =>
    _$EstimateAddressImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
      doorNo: json['doorNo'] as String?,
      latitude: (json['latitude'] as num?)?.toDouble(),
      longitude: (json['longitude'] as num?)?.toDouble(),
      locationAccuracy: (json['locationAccuracy'] as num?)?.toDouble(),
      type: json['type'] as String?,
      addressNumber: json['addressNumber'] as String?,
      addressLine1: json['addressLine1'] as String?,
      addressLine2: json['addressLine2'] as String?,
      landmark: json['landmark'] as String?,
      city: json['city'] as String?,
    );

Map<String, dynamic> _$$EstimateAddressImplToJson(
        _$EstimateAddressImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'doorNo': instance.doorNo,
      'latitude': instance.latitude,
      'longitude': instance.longitude,
      'locationAccuracy': instance.locationAccuracy,
      'type': instance.type,
      'addressNumber': instance.addressNumber,
      'addressLine1': instance.addressLine1,
      'addressLine2': instance.addressLine2,
      'landmark': instance.landmark,
      'city': instance.city,
    };

_$SorObjectImpl _$$SorObjectImplFromJson(Map<String, dynamic> json) =>
    _$SorObjectImpl(
      sorId: json['sorId'] as String?,
      id: json['id'] as String?,
      filteredMeasurementsMeasure:
          (json['filteredMeasurementsMeasure'] as List<dynamic>?)
                  ?.map((e) => FilteredMeasurementsMeasure.fromJson(
                      e as Map<String, dynamic>))
                  .toList() ??
              const [],
    );

Map<String, dynamic> _$$SorObjectImplToJson(_$SorObjectImpl instance) =>
    <String, dynamic>{
      'sorId': instance.sorId,
      'id': instance.id,
      'filteredMeasurementsMeasure': instance.filteredMeasurementsMeasure,
    };
