//filtered_Measures

import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/employee/mb/mb_inbox_response.dart';

import '../../muster_rolls/muster_workflow_model.dart';

part 'filtered_measures.freezed.dart';
part 'filtered_measures.g.dart';

@freezed
class FilteredMeasurements with _$FilteredMeasurements {
  const factory FilteredMeasurements({
    double? totalSorAmount,
    double? totalNorSorAmount,
    double? totalAmount,
    String? musterRollNumber,
    String? mbNumber,
    String? wfStatus,
    String? tenantId,
    int? endDate,
    int? startDate,
    int? entryDate,
    String? referenceId,
    String? id,
    String? physicalRefNumber,

    List<FilteredMeasurementsMeasure>? measures,
    
    List<WorkflowDocument> ?documents,
  }) = _FilteredMeasurements;

  factory FilteredMeasurements.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$FilteredMeasurementsFromJson(json);
}

// measures


@freezed
class FilteredMeasurementsMeasure with _$FilteredMeasurementsMeasure {
  const factory FilteredMeasurementsMeasure({
    double? length,
    double? breath,
    double? height,
    double? numItems,
    double? currentValue,
    double? cumulativeValue,
    String? tenantId,
    double? mbAmount,
    String? type,
    String? targetId,
    bool? isActive,
    String? id,
    String? referenceId,
     List<MeasureLineItem>? measureLineItems,

    List<FilteredMeasurementsContract>? contracts
  }) = _FilteredMeasurementsMeasure;

  factory FilteredMeasurementsMeasure.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$FilteredMeasurementsMeasureFromJson(json);
}


// contracts

@freezed
class FilteredMeasurementsContract with _$FilteredMeasurementsContract {
  const factory FilteredMeasurementsContract({
    String ? estimateId,
     String ? estimateLineItemId,
      String ?   contractLineItemRef,
       double?   unitRate,
        String?  status,
        String? wfStatus,
        ContractAdditionalDetails? contractAdditionalDetails,
         List<FilteredMeasurementsEstimate>? estimates 
  }) = _FilteredMeasurementsContract;

  factory FilteredMeasurementsContract.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$FilteredMeasurementsContractFromJson(json);
}


// estimate

@freezed
class FilteredMeasurementsEstimate with _$FilteredMeasurementsEstimate {
  const factory FilteredMeasurementsEstimate({
    String? id,
    String? sorId,
    String? category,
    String? name,
    String? description,
    double? unitRate,
    dynamic noOfunit,
    String? uom,
    dynamic length,
    dynamic width,
    dynamic height,
    dynamic quantity,
    bool? isDeduction,
    String? wfStatus,
    String? status,
  }) = _FilteredMeasurementsEstimate;

  factory FilteredMeasurementsEstimate.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$FilteredMeasurementsEstimateFromJson(json);
}
