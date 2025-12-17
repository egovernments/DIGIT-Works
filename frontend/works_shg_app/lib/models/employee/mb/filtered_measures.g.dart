// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'filtered_measures.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$FilteredMeasurementsImpl _$$FilteredMeasurementsImplFromJson(
        Map<String, dynamic> json) =>
    _$FilteredMeasurementsImpl(
      totalSorAmount: (json['totalSorAmount'] as num?)?.toDouble(),
      totalNorSorAmount: (json['totalNorSorAmount'] as num?)?.toDouble(),
      totalAmount: (json['totalAmount'] as num?)?.toDouble(),
      musterRollNumber: json['musterRollNumber'] as String?,
      mbNumber: json['mbNumber'] as String?,
      wfStatus: json['wfStatus'] as String?,
      tenantId: json['tenantId'] as String?,
      endDate: (json['endDate'] as num?)?.toInt(),
      startDate: (json['startDate'] as num?)?.toInt(),
      entryDate: (json['entryDate'] as num?)?.toInt(),
      referenceId: json['referenceId'] as String?,
      id: json['id'] as String?,
      physicalRefNumber: json['physicalRefNumber'] as String?,
      measures: (json['measures'] as List<dynamic>?)
          ?.map((e) =>
              FilteredMeasurementsMeasure.fromJson(e as Map<String, dynamic>))
          .toList(),
      documents: (json['documents'] as List<dynamic>?)
          ?.map((e) => WorkflowDocument.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$FilteredMeasurementsImplToJson(
        _$FilteredMeasurementsImpl instance) =>
    <String, dynamic>{
      'totalSorAmount': instance.totalSorAmount,
      'totalNorSorAmount': instance.totalNorSorAmount,
      'totalAmount': instance.totalAmount,
      'musterRollNumber': instance.musterRollNumber,
      'mbNumber': instance.mbNumber,
      'wfStatus': instance.wfStatus,
      'tenantId': instance.tenantId,
      'endDate': instance.endDate,
      'startDate': instance.startDate,
      'entryDate': instance.entryDate,
      'referenceId': instance.referenceId,
      'id': instance.id,
      'physicalRefNumber': instance.physicalRefNumber,
      'measures': instance.measures,
      'documents': instance.documents,
    };

_$FilteredMeasurementsMeasureImpl _$$FilteredMeasurementsMeasureImplFromJson(
        Map<String, dynamic> json) =>
    _$FilteredMeasurementsMeasureImpl(
      length: (json['length'] as num?)?.toDouble(),
      breath: (json['breath'] as num?)?.toDouble(),
      height: (json['height'] as num?)?.toDouble(),
      numItems: (json['numItems'] as num?)?.toDouble(),
      currentValue: (json['currentValue'] as num?)?.toDouble(),
      cumulativeValue: (json['cumulativeValue'] as num?)?.toDouble(),
      tenantId: json['tenantId'] as String?,
      mbAmount: (json['mbAmount'] as num?)?.toDouble(),
      type: json['type'] as String?,
      targetId: json['targetId'] as String?,
      isActive: json['isActive'] as bool?,
      id: json['id'] as String?,
      referenceId: json['referenceId'] as String?,
      measureLineItems: (json['measureLineItems'] as List<dynamic>?)
          ?.map((e) => MeasureLineItem.fromJson(e as Map<String, dynamic>))
          .toList(),
      contracts: (json['contracts'] as List<dynamic>?)
          ?.map((e) =>
              FilteredMeasurementsContract.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$FilteredMeasurementsMeasureImplToJson(
        _$FilteredMeasurementsMeasureImpl instance) =>
    <String, dynamic>{
      'length': instance.length,
      'breath': instance.breath,
      'height': instance.height,
      'numItems': instance.numItems,
      'currentValue': instance.currentValue,
      'cumulativeValue': instance.cumulativeValue,
      'tenantId': instance.tenantId,
      'mbAmount': instance.mbAmount,
      'type': instance.type,
      'targetId': instance.targetId,
      'isActive': instance.isActive,
      'id': instance.id,
      'referenceId': instance.referenceId,
      'measureLineItems': instance.measureLineItems,
      'contracts': instance.contracts,
    };

_$FilteredMeasurementsContractImpl _$$FilteredMeasurementsContractImplFromJson(
        Map<String, dynamic> json) =>
    _$FilteredMeasurementsContractImpl(
      estimateId: json['estimateId'] as String?,
      estimateLineItemId: json['estimateLineItemId'] as String?,
      contractLineItemRef: json['contractLineItemRef'] as String?,
      unitRate: (json['unitRate'] as num?)?.toDouble(),
      status: json['status'] as String?,
      wfStatus: json['wfStatus'] as String?,
      contractAdditionalDetails: json['contractAdditionalDetails'] == null
          ? null
          : ContractAdditionalDetails.fromJson(
              json['contractAdditionalDetails'] as Map<String, dynamic>),
      estimates: (json['estimates'] as List<dynamic>?)
          ?.map((e) =>
              FilteredMeasurementsEstimate.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$FilteredMeasurementsContractImplToJson(
        _$FilteredMeasurementsContractImpl instance) =>
    <String, dynamic>{
      'estimateId': instance.estimateId,
      'estimateLineItemId': instance.estimateLineItemId,
      'contractLineItemRef': instance.contractLineItemRef,
      'unitRate': instance.unitRate,
      'status': instance.status,
      'wfStatus': instance.wfStatus,
      'contractAdditionalDetails': instance.contractAdditionalDetails,
      'estimates': instance.estimates,
    };

_$FilteredMeasurementsEstimateImpl _$$FilteredMeasurementsEstimateImplFromJson(
        Map<String, dynamic> json) =>
    _$FilteredMeasurementsEstimateImpl(
      id: json['id'] as String?,
      sorId: json['sorId'] as String?,
      category: json['category'] as String?,
      name: json['name'] as String?,
      description: json['description'] as String?,
      unitRate: (json['unitRate'] as num?)?.toDouble(),
      noOfunit: json['noOfunit'],
      uom: json['uom'] as String?,
      length: json['length'],
      width: json['width'],
      height: json['height'],
      quantity: json['quantity'],
      isDeduction: json['isDeduction'] as bool?,
      wfStatus: json['wfStatus'] as String?,
      status: json['status'] as String?,
    );

Map<String, dynamic> _$$FilteredMeasurementsEstimateImplToJson(
        _$FilteredMeasurementsEstimateImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'sorId': instance.sorId,
      'category': instance.category,
      'name': instance.name,
      'description': instance.description,
      'unitRate': instance.unitRate,
      'noOfunit': instance.noOfunit,
      'uom': instance.uom,
      'length': instance.length,
      'width': instance.width,
      'height': instance.height,
      'quantity': instance.quantity,
      'isDeduction': instance.isDeduction,
      'wfStatus': instance.wfStatus,
      'status': instance.status,
    };
