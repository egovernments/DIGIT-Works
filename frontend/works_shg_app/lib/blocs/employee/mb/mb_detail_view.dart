//mb_detail_view

import 'dart:async';
import 'dart:core';

import 'package:collection/collection.dart';
import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/employee/mb/mb_inbox_response.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../../data/remote_client.dart';
import '../../../data/repositories/employee_repository/mb.dart';
import '../../../models/employee/mb/filtered_measures.dart';
import '../../../models/employee/mb/mb_detail_response.dart';
import '../../../models/muster_rolls/muster_workflow_model.dart';
import '../../../services/urls.dart';
import '../../../utils/employee/mb/mb_logic.dart';

part 'mb_detail_view.freezed.dart';

typedef MeasurementDetailBlocEventEmitter = Emitter<MeasurementDetailState>;

class MeasurementDetailBloc
    extends Bloc<MeasurementDetailBlocEvent, MeasurementDetailState> {
  MeasurementDetailBloc() : super(const MeasurementDetailState.initial()) {
    on<MeasurementDetailBookBlocEvent>(getMBDetail);
    on<AddToMeasurementLineEvent>(addMeasurementLine);
    on<UpdateToMeasurementLineEvent>(updateMeasurementLine);
    on<UpdateViewModeEvent>(updateViewMode);
    on<CancelUpdateEvent>(cancelUpdate);
    on<SubmitLineEvent>(updatePriceSOR);
    on<MeasurementUploadDocumentBlocEvent>(modifyDocument);
    on<UpdateMsgCodeEvent>(updateMSGMode);

    on<DeleteMeasurementLineEvent>(deleteMeasurementLine);
  }
  FutureOr<void> getMBDetail(
    MeasurementDetailBookBlocEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const MeasurementDetailState.initial());
      emit(const MeasurementDetailState.loading());
      final MBDetailResponse res = await MBRepository(client.init())
          .fetchMbDetail(url: Urls.measurementService.measurementDetail, body: {
        "contractNumber": event.contractNumber,
        "tenantId": GlobalVariables.tenantId,
        "measurementNumber": event.measurementNumber,
        "key": event.screenType == MBScreen.create ? "" : "View",
      });

      List<FilteredMeasurements> data;
      if (event.screenType == MBScreen.create && res.allMeasurements is! List) {
        data = MBLogic.formContract(mbDetailResponse: res);
      } else {
        data = MBLogic.getMeasureList(
          mbDetailResponse: res,
          type: event.screenType,
        );
      }

      List<List<List<SorObject>>> sorList = MBLogic.getSors(data);

      emit(
        MeasurementDetailState.loaded(
          0,
          null,
          event.screenType == MBScreen.create
              ? false
              : data.first.wfStatus == "DRAFTED"
                  ? false
                  : true,
          res.allMeasurements is List
              ? res.allMeasurements
                  .map<Measurement>((dynamic item) {
                    if (item is Map) {
                      return Measurement(
                        id: item['id'],
                        tenantId: item['tenantId'],
                        measurementNumber: item['measurementNumber'],
                        physicalRefNumber: item['physicalRefNumber'],
                        referenceId: item['referenceId'],
                        entryDate: item['entryDate'],
                        isActive: item['isActive'],
                        wfStatus: item['wfStatus'],
                        auditDetails: AuditDetails(
                          createdBy: item['auditDetails']['createdBy'],
                          lastModifiedBy: item['auditDetails']
                              ['lastModifiedBy'],
                          createdTime: item['auditDetails']['createdTime'],
                          lastModifiedTime: item['auditDetails']
                              ['lastModifiedTime'],
                        ),
                        additionalDetail: MeasurementAdditionalDetail(
                          endDate: item['additionalDetails'] == null
                              ? 0
                              : item['additionalDetails']['endDate'],
                          sorAmount: item['additionalDetails'] == null
                              ? 0.0
                              : double.parse(item['additionalDetails']
                                          ['sorAmount']
                                      .toString())
                                  .toDouble(),
                          startDate: item['additionalDetails'] == null
                              ? 1
                              : item['additionalDetails']['startDate'],
                          totalAmount: item['additionalDetails'] == null
                              ? 0.0
                              : double.parse(item['additionalDetails']
                                          ['totalAmount']
                                      .toString())
                                  .toDouble(),
                          nonSorAmount: item['additionalDetails'] == null
                              ? 0.0
                              : double.parse(item['additionalDetails']
                                          ['nonSorAmount']
                                      .toString())
                                  .toDouble(),
                          musterRollNumber: item['additionalDetails'] == null
                              ? null
                              : item['additionalDetails']['musterRollNumber'],
                        ),
                        measures: (item['measures'] as List)
                            .map<Measure>((e) =>
                                Measure.fromJson(e as Map<String, dynamic>))
                            .toList(),
                        documents: (item['documents'] as List)
                            .map<WorkflowDocument>((e) =>
                                WorkflowDocument.fromJson(
                                    e as Map<String, dynamic>))
                            .toList(),
                      );
                    } else {
                      // Assuming there's a conversion method or constructor for Measurement
                      return Measurement.fromJson(item as Map<String, dynamic>);
                    }
                  })
                  .toList()
                  .first
              : res.allMeasurements!,
          data,
          //sorList.first.first,
          getSorted(sorList.first.first, res),
          //sorList.first.last,
          getSorted(sorList.first.last, res),
          // sorList.length >= 2 ? sorList[1].first : null,
          // sorList.length >= 2 ? sorList[1].last : null,
           sorList.length >= 2 ? getSorted(sorList[1].first, res) : null,
          sorList.length >= 2 ? getSorted(sorList[1].last, res) : null,
        ),
      );
    } on DioException catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      emit(MeasurementDetailState.error(e.toString()));
    }
    on Exception catch(ex){
       emit(MeasurementDetailState.error(ex.toString()));
    }
  }

  List<SorObject> getSorted(List<SorObject> sorData, MBDetailResponse mb) {
    Set<SorObject> sData = <SorObject>{};
    for (EstimateDetail element in mb.estimate!.estimateDetails!) {
      for (SorObject object in sorData) {
        if (element.sorId == object.sorId) {
          sData.add(object);
          break;
        }
      }
    }
    return sData.toList();
  }

  FutureOr<void> addMeasurementLine(
    AddToMeasurementLineEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) {
          List<MeasureLineItem> mk = [];

          if (event.single) {
            MeasureLineItem mm =  MeasureLineItem(
                measurementSummary: event.measurementSummary,
              width: 0,
              height: 0,
              length: 0,
              number: 0,
              quantity: 0,
              measurelineitemNo: 1,
            );
            mk = [mm];
          } else {
            MeasureLineItem mm = MeasureLineItem(
              measurementSummary: event.measurementSummary,
              width: 0,
              height: 0,
              length: 0,
              number: 0,
              quantity: 0,
              measurelineitemNo: event.measurementLineIndex,
              // measurelineitemNo: randomNumber,
            );
            mk = [mm];
          }

          if (event.type != "NON-SOR") {
            List<SorObject> data = MBLogic.addMeasurementLine(
              value.sor!,
              event.sorId,
              event.filteredMeasurementMeasureId!,
              event.measurementLineIndex!,
              mk,
            );

            emit(value.copyWith(
              qtyErrorMsg: 1,
              sor: data,
            ));
          } else {
            List<SorObject> data = MBLogic.addMeasurementLine(
              value.nonSor!,
              event.sorId,
              event.filteredMeasurementMeasureId!,
              event.measurementLineIndex!,
              mk,
            );

            emit(value.copyWith(
              qtyErrorMsg: 1,
              nonSor: data,
            ));
          }
        },
      );
    } catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      // emit(MeasurementDetailState.error(e.toString()));
    }
  }

// delete mbline
  FutureOr<void> deleteMeasurementLine(
    DeleteMeasurementLineEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) {
          if (event.type != "NonSor") {
            List<SorObject> data = MBLogic.deleteMeasurementLine(
              value.sor!,
              event.sorId,
              event.filteredMeasurementMeasureId,
              event.measurementLineIndex,
            );

            emit(value.copyWith(
              qtyErrorMsg: 1,
              sor: data,
            ));
          } else {
            List<SorObject> data = MBLogic.deleteMeasurementLine(
              value.nonSor!,
              event.sorId,
              event.filteredMeasurementMeasureId,
              event.measurementLineIndex,
            );

            emit(value.copyWith(
              qtyErrorMsg: 1,
              nonSor: data,
            ));
          }
        },
      );
    } catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      // emit(MeasurementDetailState.error(e.toString()));
    }
  }

// end of delete mbline

  // update mbline

  FutureOr<void> updateMeasurementLine(
    UpdateToMeasurementLineEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) {
          MeasureLineItem ml = MeasureLineItem(
            measurementSummary: event.measurementSummary,
            width: event.width,
            height: event.height,
            length: event.length,
            number: event.number,
            quantity: calulateQuantity(
              height: event.height.toString(),
              width: event.width.toString(),
              length: event.length.toString(),
              number: event.number.toString(),
            ),
            measurelineitemNo: event.measurementLineIndex,
          );

          if (event.type != "NON-SOR") {
            List<SorObject> data = MBLogic.updateMeasurementLine(
              value.sor!,
              event.sorId,
              event.filteredMeasurementMeasureId!,
              event.measurementLineIndex!,
              ml,
            );

            emit(value.copyWith(
              sor: data,
              warningMsg: null,
              qtyErrorMsg: 1,
            ));
          } else {
            List<SorObject> data = MBLogic.updateMeasurementLine(
              value.nonSor!,
              event.sorId,
              event.filteredMeasurementMeasureId!,
              event.measurementLineIndex!,
              ml,
            );

            emit(value.copyWith(
              nonSor: data,
              warningMsg: null,
              qtyErrorMsg: 1,
            ));
          }
        },
      );
    } catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      emit(MeasurementDetailState.error(e.toString()));
    }
  }

// update view mode
  FutureOr<void> updateViewMode(
    UpdateViewModeEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) {
          emit(
            value.copyWith(
              viewStatus: event.updateView,
            ),
          );
        },
      );
    } catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      // emit(MeasurementDetailState.error(e.toString()));
    }
  }

// cancel sor&non-sor mode
  FutureOr<void> cancelUpdate(
    CancelUpdateEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) async {
          if (event.type == "sor") {
            List<SorObject> s = await resetFilteredMeasure(
                value.sor!, event.sorId, [value.data.first]);

            TotalEstimate sorData = MBLogic.calculateTotalQuantity(
                s, "sorId", "filteredMeasurementsMeasureId", 0);

            List<FilteredMeasurements> newData = value.data.mapIndexed(
              (index, e) {
                if (index == 0) {
                  return FilteredMeasurements(
                    tenantId: e.tenantId,
                    id: e.id,
                    wfStatus: e.wfStatus,
                    mbNumber: e.mbNumber,
                    totalAmount: (e.totalNorSorAmount! + sorData.totalAmount),
                    totalNorSorAmount: e.totalNorSorAmount,
                    totalSorAmount: sorData.totalAmount,
                    musterRollNumber: e.musterRollNumber,
                    endDate: e.endDate,
                    startDate: e.startDate,
                    entryDate: e.entryDate,
                    referenceId: e.referenceId,
                    physicalRefNumber: e.physicalRefNumber,
                    measures: e.measures,
                    documents: e.documents?.map((e) => e).toList(),
                  );
                } else {
                  return e;
                }
              },
            ).toList();

            emit(
              value.copyWith(
                data: newData,
                warningMsg: null,
                sor: sorData.sorObjectList,
                qtyErrorMsg: -1,
              ),
            );
          } else {
            List<SorObject> ns = await resetFilteredMeasure(
                value.nonSor!, event.sorId, [value.data.first]);

            TotalEstimate nonSorData = MBLogic.calculateTotalQuantity(
                ns, "sorId", "filteredMeasurementsMeasureId", 0);

            List<FilteredMeasurements> newData = value.data.mapIndexed(
              (index, e) {
                if (index == 0) {
                  return FilteredMeasurements(
                    tenantId: e.tenantId,
                    id: e.id,
                    wfStatus: e.wfStatus,
                    mbNumber: e.mbNumber,
                    totalAmount: (nonSorData.totalAmount + e.totalSorAmount!),
                    totalNorSorAmount: nonSorData.totalAmount,
                    totalSorAmount: e.totalSorAmount,
                    musterRollNumber: e.musterRollNumber,
                    endDate: e.endDate,
                    startDate: e.startDate,
                    entryDate: e.entryDate,
                    referenceId: e.referenceId,
                    physicalRefNumber: e.physicalRefNumber,
                    measures: e.measures,
                    documents: e.documents?.map((e) => e).toList(),
                  );
                } else {
                  return e;
                }
              },
            ).toList();

            emit(
              value.copyWith(
                data: newData,
                warningMsg: null,
                nonSor: nonSorData.sorObjectList,
                qtyErrorMsg: -1,
              ),
            );
          }
        },
      );
    } catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      emit(MeasurementDetailState.error(e.toString()));
    }
  }

  Future<List<SorObject>> resetFilteredMeasure(List<SorObject> sorObjects,
      String sorId, List<FilteredMeasurements> previousData) async {
    // Find the index of the SorObject with the specified sorId
    int sorIndex =
        sorObjects.indexWhere((sorObject) => sorObject.sorId == sorId);
    if (sorIndex != -1) {
      // Find the previous SorObject from previousData based on sorId
      SorObject? previousSor = findSorObjectById(previousData, sorId);

      if (previousSor != null) {
        // Create a copy of sorObjects
        List<SorObject> copyOfSorObjects = List.of(sorObjects);

        // Replace the existing SorObject with the previous one
        copyOfSorObjects[sorIndex] = previousSor;

        // Return the modified list instead of modifying sorObjects directly
        return copyOfSorObjects;
      }
    }

    // Return the original list if no modification is made
    return sorObjects;
  }

  SorObject? findSorObjectById(
      List<FilteredMeasurements> filteredMeasurementsList, String sorId) {
    for (FilteredMeasurements filteredMeasurements
        in filteredMeasurementsList) {
      List<FilteredMeasurementsMeasure> mutableList = [];
      for (FilteredMeasurementsMeasure measure
          in filteredMeasurements.measures ?? []) {
        if (measure.contracts!.first.estimates!.first.sorId == sorId) {
          mutableList.add(measure);
          // Do not return here
        }
      }
      if (mutableList.isNotEmpty) {
        // If any matching measures are found, create and return SorObject
        return SorObject(
            id: mutableList.first.contracts!.first.estimates!.first.id,
            sorId: mutableList.first.contracts!.first.estimates!.first.sorId,
            filteredMeasurementsMeasure: mutableList
            // Fill in the properties of SorObject based on the found measures
            );
      }
    }
    return null; // If no SorObject with the given sorId is found in any filtered measurements
  }

// calculate qty
  dynamic calulateQuantity({
    required dynamic height,
    required dynamic width,
    required dynamic length,
    required dynamic number,
  }) {
    //return  field==0?1:field;

    height = double.parse(
            (height == "" || height == ".") ? "0.0" : height.toString())
        .toStringAsFixed(4);
    width =
        double.parse((width == "" || width == ".") ? "0.0" : width.toString())
            .toStringAsFixed(4);
    length = double.parse(
            (length == "" || length == ".") ? "0.0" : length.toString())
        .toStringAsFixed(4);
    number = double.parse(
            (number == "" || number == ".") ? "0.0" : number.toString())
        .toStringAsFixed(4);

    if ((height == '0.0000' &&
            width == '0.0000' &&
            length == '0.0000' &&
            number == '0.0000') ||
        (height == '0.0000' &&
            width == '0.0000' &&
            length == '0.0000' &&
            number == '0.0000')) {
      return 0;
    } else {
      if (height == '0.0000') {
        height = 1;
      }
      if (width == '0.0000') {
        width = 1;
      }
      if (length == '0.0000') {
        length = 1;
      }
      if (number == '0.0000') {
        number = 1;
      }
      double h = double.parse(height.toString());

      double n = double.parse(number.toString());
      double w = double.parse(width.toString());
      double l = double.parse(length.toString());

      return double.parse((h * n * w * l).toString()).toStringAsFixed(4);
    }
  }

// update price
  FutureOr<void> updatePriceSOR(
    SubmitLineEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) {
          TotalEstimate sorData = MBLogic.calculateTotalQuantity(
              value.sor!, "sorId", "filteredMeasurementsMeasureId", 0);

          TotalEstimate nonSorData = MBLogic.calculateTotalQuantity(
              value.nonSor!, "sorId", "filteredMeasurementsMeasureId", 0);

          final ss = event.type == "NonSor"
              ? nonSorData.sorObjectList
                  .firstWhere((element) => element.sorId == event.sorId)
                  .filteredMeasurementsMeasure
                  .fold(0.0, (sum, element) {
                  double m =
                      double.parse(element.numItems.toString()).toDouble();
                  return sum + m;
                })
              : sorData.sorObjectList
                  .firstWhere((element) => element.sorId == event.sorId)
                  .filteredMeasurementsMeasure
                  .fold(0.0, (sum, element) {
                  double m =
                      double.parse(element.numItems.toString()).toDouble();
                  return sum + m;
                });

          if (ss <=
                  ((double.parse(event.noOfUnit.toString()) -
                      double.parse(event.cummulativePrevQty.toString()))) &&
              ss >= 0) {
            List<FilteredMeasurements> newData = value.data.mapIndexed(
              (index, e) {
                if (index == 0) {
                  return FilteredMeasurements(
                    tenantId: e.tenantId,
                    id: e.id,
                    wfStatus: e.wfStatus,
                    mbNumber: e.mbNumber,
                    totalAmount: (sorData.totalAmount + nonSorData.totalAmount),
                    totalNorSorAmount: nonSorData.totalAmount,
                    totalSorAmount: sorData.totalAmount,
                    musterRollNumber: e.musterRollNumber,
                    endDate: e.endDate,
                    startDate: e.startDate,
                    entryDate: e.entryDate,
                    referenceId: e.referenceId,
                    physicalRefNumber: e.physicalRefNumber,
                    measures: e.measures,
                    documents: e.documents?.map((e) => e).toList(),
                  );
                } else {
                  return e;
                }
              },
            ).toList();

            emit(
              value.copyWith(
                data: newData,
                warningMsg: null,
                sor: sorData.sorObjectList,
                nonSor: nonSorData.sorObjectList,
                qtyErrorMsg: value.qtyErrorMsg == 0 ? -2 : 0,
              ),
            );
          } else {
            emit(value.copyWith(
              qtyErrorMsg: 2,
              warningMsg: "mbQtyErrMsg",
            ));
          }
        },
      );
    } on DioException catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      emit(MeasurementDetailState.error(e.toString()));
    }
  }

// modify document

  FutureOr<void> modifyDocument(
    MeasurementUploadDocumentBlocEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) {
          List<WorkflowDocument> updatedDocuments = [];

          updatedDocuments = List.from(event.workflowDocument);

          List<FilteredMeasurements> newData = value.data.mapIndexed(
            (index, e) {
              if (index == 0) {
                return FilteredMeasurements(
                  tenantId: e.tenantId,
                  id: e.id,
                  wfStatus: e.wfStatus,
                  mbNumber: e.mbNumber,
                  totalAmount: e.totalAmount,
                  totalNorSorAmount: e.totalNorSorAmount,
                  totalSorAmount: e.totalSorAmount,
                  musterRollNumber: e.musterRollNumber,
                  endDate: e.endDate,
                  startDate: e.startDate,
                  entryDate: e.entryDate,
                  referenceId: e.referenceId,
                  physicalRefNumber: e.physicalRefNumber,
                  measures: e.measures,
                  documents: updatedDocuments.map((e) => e).toList(),
                );
              } else {
                return e;
              }
            },
          ).toList();

          emit(
            value.copyWith(
              data: newData,
              warningMsg: null,
              qtyErrorMsg: 0,
            ),
          );
        },
      );
    } on DioException catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      emit(MeasurementDetailState.error(e.toString()));
    }
  }

// update view mode
  FutureOr<void> updateMSGMode(
    UpdateMsgCodeEvent event,
    MeasurementDetailBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () => null,
        loaded: (value) {
          emit(
            value.copyWith(
              qtyErrorMsg: event.updateCode,
            ),
          );
        },
      );
    } catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      // emit(MeasurementDetailState.error(e.toString()));
    }
  }
}

@freezed
class MeasurementDetailBlocEvent with _$MeasurementDetailBlocEvent {
  const factory MeasurementDetailBlocEvent.uploadDocument({
    required String tenantId,
    required List<WorkflowDocument> workflowDocument,
  }) = MeasurementUploadDocumentBlocEvent;

  const factory MeasurementDetailBlocEvent.create({
    required String tenantId,
    required String contractNumber,
    required String measurementNumber,
    required MBScreen screenType,
  }) = MeasurementDetailBookBlocEvent;

  const factory MeasurementDetailBlocEvent.clear() =
      MeasurementDetailBlocClearEvent;
  const factory MeasurementDetailBlocEvent.addToMeasurementLineList({
    required String sorId,
    required String type,
    int? index,
    int? measurementLineIndex,
    String? filteredMeasurementMeasureId,
    dynamic height,
    dynamic width,
    dynamic length,
    dynamic number,
    dynamic quantity,
     dynamic measurementSummary,
    required bool single,
  }) = AddToMeasurementLineEvent;

  const factory MeasurementDetailBlocEvent.updateToMeasurementLineList({
    required dynamic noOfUnit,
    required dynamic cummulativePrevQty,
    required String sorId,
    required String type,
    int? index,
    int? measurementLineIndex,
    String? filteredMeasurementMeasureId,
    dynamic height,
    dynamic width,
    dynamic length,
    dynamic number,
    dynamic quantity,
    dynamic measurementSummary,
  }) = UpdateToMeasurementLineEvent;
  // update view mode

  const factory MeasurementDetailBlocEvent.updateViewMode({
    required bool updateView,
  }) = UpdateViewModeEvent;

  // reset the sorlist

  const factory MeasurementDetailBlocEvent.cancelUpdate({
    required bool cancelUpdate,
    required String sorId,
    required dynamic filteredMeasurementsMeasureId,
    required String type,
  }) = CancelUpdateEvent;

  // submit

  const factory MeasurementDetailBlocEvent.submitLine({
    required dynamic noOfUnit,
    required dynamic cummulativePrevQty,
    required String sorId,
    required String type,
    int? index,
    int? measurementLineIndex,
    String? filteredMeasurementMeasureId,
  }) = SubmitLineEvent;

  // update msg code

  const factory MeasurementDetailBlocEvent.updateMsgCode({
    required int updateCode,
  }) = UpdateMsgCodeEvent;

  // delete measurementLineItem

  const factory MeasurementDetailBlocEvent.deleteMeasurementLine({
    required String sorId,
    required String type,
    required int index,
    required int measurementLineIndex,
    required String filteredMeasurementMeasureId,
  }) = DeleteMeasurementLineEvent;
}

@freezed
class MeasurementDetailState with _$MeasurementDetailState {
  const MeasurementDetailState._();

  const factory MeasurementDetailState.initial() = _Initial;
  const factory MeasurementDetailState.loading() = _Loading;
  const factory MeasurementDetailState.loaded(
    int qtyErrorMsg,
    String? warningMsg,
    bool viewStatus,
    dynamic rawData,
    List<FilteredMeasurements> data,
    List<SorObject>? sor,
    List<SorObject>? nonSor,
    List<SorObject>? preSor,
    List<SorObject>? preNonSor,
  ) = _Loaded;
  const factory MeasurementDetailState.error(String? error) = _Error;
}
