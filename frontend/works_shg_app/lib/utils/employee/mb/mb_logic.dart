// ignore_for_file: public_member_api_docs, sort_constructors_first

import 'package:collection/collection.dart';
import 'package:works_shg_app/models/muster_rolls/muster_workflow_model.dart';
import '../../../models/employee/mb/filtered_measures.dart';
import '../../../models/employee/mb/mb_detail_response.dart';
import '../../../models/employee/mb/mb_inbox_response.dart';

enum MBScreen { create, update }

class MBLogic {
// start experiment

  static List<FilteredMeasurements> formContract(
      {required MBDetailResponse mbDetailResponse}) {
        //TODO:[temporay commented for testing flutter upgrade]
    // List<FilteredMeasurementsContract> s =
    //     mbDetailResponse.contract!.lineItems!.map((e) {
    //   FilteredMeasurementsContract filteredMeasurementsContract =
    //       FilteredMeasurementsContract(
    //           contractAdditionalDetails:
    //               mbDetailResponse.contract?.additionalDetails,
    //           estimateId: e.estimateId,
    //           estimateLineItemId: e.estimateLineItemId,
    //           contractLineItemRef: e.contractLineItemRef,
    //           unitRate: e.unitRate,
    //           status: e.status,
    //           estimates: getEstimate(e.estimateLineItemId!, mbDetailResponse));

    //   return filteredMeasurementsContract;
    // }).toList();

// end
    FilteredMeasurements sata = FilteredMeasurements(
        totalAmount: 0.0,
        totalNorSorAmount: 0.0,
        totalSorAmount: 0.0,
        musterRollNumber: null,
        mbNumber: null,
        wfStatus: null,
        tenantId: mbDetailResponse.contract!.tenantId,
        endDate: mbDetailResponse.period?.endDate ?? 0,
        startDate: mbDetailResponse.period?.startDate ?? 0,
        entryDate: DateTime.now().millisecondsSinceEpoch,
        referenceId: mbDetailResponse.contract!.contractNumber,
        id: null,
        physicalRefNumber: null,
        measures: mbDetailResponse.contract!.lineItems!.mapIndexed((index, e) {
          FilteredMeasurementsMeasure filteredMeasurementsMeasure =
              FilteredMeasurementsMeasure(
                  contracts:
                      getContract(e.contractLineItemRef!, mbDetailResponse),
                  length: 0.0,
                  breath: 0.0,
                  height: 0.0,
                  numItems: 0.0,
                  cumulativeValue: 0.0,
                  currentValue: 0.0,
                  tenantId:
                      mbDetailResponse.contract!.lineItems!.first.tenantId!,
                  mbAmount: 0.0,
                  targetId: e.contractLineItemRef,
                  isActive: null,
                  id: "${e.contractLineItemRef}$index",
                  measureLineItems: [
                const MeasureLineItem(
                  width: 0.0,
                  height: 0.0,
                  length: 0.0,
                  number: 0.0,
                  quantity: 0.0,
                  measurelineitemNo: 0,
                ),
              ]);

          return filteredMeasurementsMeasure;
        }).toList(),
        documents: null);

    return [sata];
  }

// end
  static List<FilteredMeasurements> getMeasureList({
    required MBDetailResponse mbDetailResponse,
    required MBScreen type,
  }) {
    final List<Measurement> allMeasurements = mbDetailResponse.allMeasurements
            is List
        ? (mbDetailResponse.allMeasurements as List).map<Measurement>((item) {
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
                  lastModifiedBy: item['auditDetails']['lastModifiedBy'],
                  createdTime: item['auditDetails']['createdTime'],
                  lastModifiedTime: item['auditDetails']['lastModifiedTime'],
                ),
                additionalDetail: MeasurementAdditionalDetail(
                  endDate: item['additionalDetails'] == null
                      ? 0
                      : item['additionalDetails']['endDate'],
                  sorAmount: item['additionalDetails'] == null
                      ? 0.0
                      : double.parse(
                              item['additionalDetails']['sorAmount'].toString())
                          .toDouble(),
                  startDate: item['additionalDetails'] == null
                      ? 1
                      : item['additionalDetails']['startDate'],
                  totalAmount: item['additionalDetails'] == null
                      ? 0.0
                      : double.parse(item['additionalDetails']['totalAmount']
                              .toString())
                          .toDouble(),
                  nonSorAmount: item['additionalDetails'] == null
                      ? 0.0
                      : double.parse(item['additionalDetails']['nonSorAmount']
                              .toString())
                          .toDouble(),
                  musterRollNumber: item['additionalDetails'] == null
                      ? null
                      : item['additionalDetails']['musterRollNumber'],
                ),
                measures: (item['measures'] as List)
                    .map<Measure>(
                        (e) => Measure.fromJson(e as Map<String, dynamic>))
                    .toList(),
                documents: (item['documents'] as List)
                    .map<WorkflowDocument>((e) =>
                        WorkflowDocument.fromJson(e as Map<String, dynamic>))
                    .toList(),
              );
            } else {
              // Assuming there's a conversion method or constructor for Measurement
              return Measurement.fromJson(item as Map<String, dynamic>);
            }
          }).toList()
        : [];

    final data = allMeasurements.mapIndexed((index, e) {
      FilteredMeasurements datak = FilteredMeasurements(
          documents: e.documents
              ?.mapIndexed((index, e) => WorkflowDocument(
                  documentType: e.documentType,
                  documentUid: e.documentUid,
                  documentAdditionalDetails: e.documentAdditionalDetails,
                  fileStore: e.fileStore,
                  fileStoreId: e.fileStoreId,
                  id: e.id,
                  tenantId: e.tenantId,
                  isActive: true,
                  indexing: (index + 1)))
              .toList(),
          id: e.id,
          totalSorAmount: e.additionalDetail?.sorAmount ?? 0.0,
          totalNorSorAmount: e.additionalDetail?.nonSorAmount ?? 0.0,
          totalAmount: e.additionalDetail?.totalAmount ?? 0.0,
          endDate: e.additionalDetail?.endDate ??
              (mbDetailResponse.period?.endDate ?? 00),
          startDate: e.additionalDetail?.startDate ??
              (mbDetailResponse.period?.startDate ?? 00),
          entryDate: (e.entryDate != 0 && e.entryDate != null)
              ? e.entryDate
              : DateTime.now().millisecondsSinceEpoch,
          physicalRefNumber: e.physicalRefNumber,
          referenceId: e.referenceId,
          musterRollNumber: mbDetailResponse.musterRolls is List
              ? convertList(
                  mbDetailResponse.musterRolls, e.additionalDetail?.startDate)
              : null,
          mbNumber: e.measurementNumber,
          wfStatus: e.wfStatus,
          tenantId: e.tenantId,
          measures: e.measures?.map((e) {
            FilteredMeasurementsMeasure filteredMeasurementsMeasure =
                FilteredMeasurementsMeasure(
              mbAmount: e.measureAdditionalDetails?.mbAmount,
              type: e.measureAdditionalDetails?.type,
              length: e.length,
              breath: e.breadth,
              height: e.height,
              numItems: e.numItems,
              currentValue: e.currentValue,
              cumulativeValue: e.cumulativeValue,
              tenantId: e.targetId,
              targetId: e.targetId,
              isActive: e.isActive,
              id: e.id,
              contracts: getContract(
                e.targetId!,
                mbDetailResponse,
              ),
              measureLineItems: e.measureAdditionalDetails != null
                  ? (e.measureAdditionalDetails!.measureLineItems != null &&
                          e.measureAdditionalDetails!.measureLineItems!
                              .isNotEmpty)
                      ? e.measureAdditionalDetails!.measureLineItems!.map((e) {
                          return e;
                        }).toList()
                      : [
                          const MeasureLineItem(
                            width: 0.0,
                            height: 0.0,
                            length: 0.0,
                            number: 0.0,
                            quantity: 0.0,
                            measurelineitemNo: 0,
                          ),
                        ]
                  // end
                  : null,
            );

            return filteredMeasurementsMeasure;
          }).toList());

      return datak;
    }).toList();

    if (type == MBScreen.create) {
      final ff = formContract(mbDetailResponse: mbDetailResponse);

      return [...ff, ...data.whereNotNull().toList() ?? []];
    } else {
      return data.whereNotNull().toList() ?? [];
    }
  }

  static List<FilteredMeasurementsContract> getContract(
      String targetId, MBDetailResponse mb) {
    final alldata = mb.contract?.lineItems?.map((e) {
      if (e.contractLineItemRef == targetId) {
        FilteredMeasurementsContract filteredMeasurementsContract =
            FilteredMeasurementsContract(
                contractAdditionalDetails: mb.contract?.additionalDetails,
                estimateId: e.estimateId,
                estimateLineItemId: e.estimateLineItemId,
                contractLineItemRef: e.contractLineItemRef,
                unitRate: e.unitRate,
                status: e.status,
                wfStatus: mb.contract?.wfStatus,
                estimates: getEstimate(e.estimateLineItemId!, mb));

        return filteredMeasurementsContract;
      }
    }).toList();
    return alldata?.whereNotNull().toList() ?? [];
  }

  // estimate

  static List<FilteredMeasurementsEstimate> getEstimate(
      String contractLineItemRef, MBDetailResponse mb) {
    final alldata = mb.estimate?.estimateDetails?.mapIndexed((index, e) {
      if (e.id == contractLineItemRef) {
        FilteredMeasurementsEstimate filteredMeasurementsEstimate =
            FilteredMeasurementsEstimate(
          id: e.id,
          // Info::::
          //TODO :[hard code for non-sor id 45 then we are changing it to other mumber]
          // previous code
//sorId: e.sorId,
          //end of it
          sorId: e.sorId == "45" ? (index + 1).toString() : e.sorId,
          category: e.category,
          name: e.name,
          description: e.description,
          unitRate: e.unitRate,
          noOfunit: e.noOfunit != null
              ? e.isDeduction == true
                  ? -double.parse((e.noOfunit!.toDouble()).toStringAsFixed(4))
                  : double.parse((e.noOfunit!.toDouble()).toStringAsFixed(4))
              : 0,
          uom: e.uom,
          length: e.length != null ? e.length!.toInt() : 0,
          width: e.width != null ? e.width!.toInt() : 0,
          height: e.height != null ? e.height!.toInt() : 0,
          quantity: e.quantity != null
              ? e.quantity!.toDouble()
              : 0.0,
          isDeduction: e.isDeduction,
          status: mb.estimate?.status,
          wfStatus: mb.estimate?.wfStatus,
        );

        return filteredMeasurementsEstimate;
      }
    }).toList();

    return alldata?.whereNotNull().toList() ?? [];
  }

  static List<List<List<SorObject>>> getSors(List<FilteredMeasurements> s) {
    List<List<List<SorObject>>> mark = [];

    for (int a = 0; a < s.length; a++) {
      List<FilteredMeasurementsMeasure> sor = [];
      List<FilteredMeasurementsMeasure> nonSor = [];

      for (int i = 0; i < s[a].measures!.length; i++) {
        if (s[a].measures![i].contracts!.first.estimates != null &&
            s[a].measures![i].contracts!.first.estimates!.first.category !=
                null) {
          if (s[a].measures![i].contracts!.first.estimates!.first.category ==
              "SOR") {
            sor.add(s[a].measures![i]);
          } else {
            nonSor.add(s[a].measures![i]);
          }
        }
      }

      List<SorObject> listSors = [];
      List<SorObject> listNonSors = [];

      bool isObjectExists(String objectId, String type) {
        List<SorObject> list = type == "NonSOR" ? listNonSors : listSors;
        return list.any((obj) => obj.sorId == objectId);
      }

      void addObjectOrModify(
          String objectId, FilteredMeasurementsMeasure newobj, String type) {
        List<SorObject> list = type == "NonSOR" ? listNonSors : listSors;
        if (isObjectExists(objectId, type)) {
          SorObject existingObject =
              list.firstWhere((obj) => obj.sorId == objectId);
          List<FilteredMeasurementsMeasure> mutableList =
              List.from(existingObject.filteredMeasurementsMeasure);
          mutableList.add(newobj);
          int index = list.indexWhere((obj) => obj.sorId == objectId);
          
          list[index] = SorObject(
            sorId: existingObject.sorId,
            id: existingObject.id,
            filteredMeasurementsMeasure: mutableList,
          );
        } else {
          list.add(
            SorObject(
              filteredMeasurementsMeasure: [newobj],
              id: newobj.contracts!.first.estimates!.first.id,
              sorId: newobj.contracts!.first.estimates!.first.sorId,
            ),
          );
        }
      }

      for (var obj in sor) {
        String? mValue = obj.contracts!.first.estimates!.first.sorId;
        if (mValue != null) {
          addObjectOrModify(mValue, obj, "SOR");
        }
      }

      for (var obj in nonSor) {
        String? mValue = obj.contracts!.first.estimates!.first.sorId;

        if (mValue != null) {
          addObjectOrModify(mValue, obj, "NonSOR");
        }
      }

      mark.add([listSors, listNonSors]);
    }
    return mark;
  }

  // to get

  static List<Measure> getList(List<SorObject> sorObjects) {
    List<Measure> measureList = [];

    for (SorObject sorObject in sorObjects) {
      for (FilteredMeasurementsMeasure measure
          in sorObject.filteredMeasurementsMeasure) {
        measureList.add(Measure(
          description: measure.contracts?.first.estimates?.first.description,
          comments:
              null, // You can set comments to the appropriate value if available
          targetId: measure.targetId,
          breadth: measure.breath,
          length: measure.length,
          height: measure.height,
          isActive: measure.isActive,
          referenceId: measure.referenceId,
          numItems: measure.numItems,
          id: measure.id,
          cumulativeValue: measure.cumulativeValue,
          currentValue: measure.currentValue,
          measureAdditionalDetails: MeasureAdditionalDetails(
            type: measure.type,
            mbAmount: measure.mbAmount,
            measureLineItems: measure.measureLineItems,
          ),
        ));
      }
    }

    return measureList;
  }

// form payload for update MB

  static MBDetailResponse getMbPayloadUpdate({
    required List<FilteredMeasurements> data,
    required List<List<SorObject>> sorList,
    required WorkFlow workFlow,
    required MBScreen type,
  }) {
    MBDetailResponse sa = MBDetailResponse(
      measurement: Measurement(
        documents: data.first.documents?.map((e) => e).toList(),
        id: data.first.id,
        tenantId: data.first.tenantId,
        measurementNumber: data.first.mbNumber,
        physicalRefNumber: data.first.physicalRefNumber,
        referenceId: data.first.referenceId,
        entryDate: type == MBScreen.update
            ? data.first.entryDate
            : DateTime.now().millisecondsSinceEpoch,
        isActive: true,
        wfStatus: data.first.wfStatus,
        workflow: workFlow,
        additionalDetail: MeasurementAdditionalDetail(
          endDate: data.first.endDate,
          sorAmount: data.first.totalSorAmount,
          nonSorAmount: data.first.totalNorSorAmount,
          startDate: data.first.startDate,
          musterRollNumber: [data.first.musterRollNumber.toString()],
          totalAmount: data.first.totalAmount,
        ),
        measures: MBLogic.getList(
          sorList.expand((element) => element).toList(),
        ),
      ),
    );

    return sa;
  }

// to map

  static Map<String, dynamic> measurementToMap(Measurement measurement, MBScreen mbScreen) {
    Map<String, dynamic> data = {
      "documents": measurement.documents != null
          ? measurement.documents!.map((e) {
              return {
                "isActive": e.isActive,
                "fileStore": e.fileStore,
                "id": e.id,
                "documentUid": e.documentUid,
                "documentType": e.documentType,
                "additionalDetails": {
                  "fileName": e.documentAdditionalDetails != null
                      ? e.documentAdditionalDetails!.fileName ?? ''
                      : '',
                  "fileType": e.documentAdditionalDetails != null
                      ? e.documentAdditionalDetails!.fileType ?? ''
                      : '',
                  "tenantId": e.documentAdditionalDetails != null
                      ? e.documentAdditionalDetails!.tenantId ?? ''
                      : ''
                }
              };
            }).toList()
          : [],
      'id': measurement.id,
      'tenantId': measurement.tenantId,
      'measurementNumber': measurement.measurementNumber,
      'entryDate': measurement.entryDate,
      'isActive': measurement.isActive,
      'wfStatus': measurement.wfStatus,
      'referenceId': measurement.referenceId,
      'physicalRefNumber': measurement.physicalRefNumber,
      'workflow': {
        'action': measurement.workflow?.action,
        'comments': measurement.workflow?.comment,
        'assignes': measurement.workflow?.assignees,
        'documents': measurement.workflow?.documents?.map((e) {
          return {
            "documentType": e.documentType,
            "documentUid": e.documentUid,
            "fileName": e.fileName,
            "fileStoreId": e.fileStoreId,
            "tenantId": e.tenantId
          };
        }).toList()
      },
      'additionalDetails': {
        'source':mbScreen==MBScreen.update?measurement.additionalDetail?.source:"Mobile",
        'endDate': measurement.additionalDetail?.endDate,
        'sorAmount': measurement.additionalDetail?.sorAmount,
        'startDate': measurement.additionalDetail?.startDate,
        'totalAmount': measurement.additionalDetail?.totalAmount,
        'nonSorAmount': measurement.additionalDetail?.nonSorAmount,
        'musterRollNumber': [measurement.additionalDetail?.musterRollNumber],
      },
      'measures': measurement.measures!.map((measure) {
        return {
          'description': measure.description,
          'comments': measure.comments,
          'targetId': measure.targetId,
          
          'breadth':
              (measure.numItems == 0.0 || measure.numItems! < 0.0) ? 0.0 : 1.0,
          'length':
              (measure.numItems == 0.0 || measure.numItems! < 0.0) ? 0.0 : 1.0,
          'height':
              (measure.numItems == 0.0 || measure.numItems! < 0.0) ? 0.0 : 1.0,
          'isActive': measure.isActive,
          'referenceId': measure.referenceId,
          'numItems': (measure.numItems! < 0.0)
              ? (measure.numItems! * -1)
              : measure.numItems,
          'id': measure.id,
          'cumulativeValue': measure.cumulativeValue,
          'currentValue': measure.currentValue,
          'additionalDetails': {
            'type': measure.measureAdditionalDetails?.type,
            'mbAmount': (measure.measureAdditionalDetails!.mbAmount! < 0)
                ? (measure.measureAdditionalDetails!.mbAmount!) * -1
                : measure.measureAdditionalDetails!.mbAmount,
            'measureLineItems': measureListFilter(measure),
          },
        };
      }).toList(),
    };

    return data;
  }

  // copywith

  static List<SorObject> updateMeasurementLine(
      List<SorObject> sorObjects,
      String sorId,
      String filteredMeasurementsMeasureId,
      int measurementLineIndex,
      MeasureLineItem updatedMeasurementLine) {
    return sorObjects.map((sorObject) {
      if (sorObject.sorId == sorId) {
        final List<FilteredMeasurementsMeasure>
            updatedFilteredMeasurementsMeasureList = sorObject
                .filteredMeasurementsMeasure
                .map((filteredMeasurementsMeasure) {
          if (filteredMeasurementsMeasure.id == filteredMeasurementsMeasureId) {
            final List<MeasureLineItem> updatedMeasurementLineList =
                filteredMeasurementsMeasure.measureLineItems!
                    .map((measurementLine) {
              if (measurementLine.measurelineitemNo == measurementLineIndex) {
               

                return updatedMeasurementLine;
              }
              return measurementLine;
            }).toList();

            return filteredMeasurementsMeasure.copyWith(
              measureLineItems: updatedMeasurementLineList,
            );
          }
          return filteredMeasurementsMeasure;
        }).toList();

        return sorObject.copyWith(
          filteredMeasurementsMeasure: updatedFilteredMeasurementsMeasureList,
        );
      }
      return sorObject;
    }).toList();
  }

  static List<SorObject> addMeasurementLine(
    List<SorObject> sorObjects,
    String sorId,
    String filteredMeasurementsMeasureId,
    int measurementLineIndex,
    List<MeasureLineItem> updatedMeasurementLine,
  ) {
    return sorObjects.map((sorObject) {
      if (sorObject.sorId == sorId) {
        final List<FilteredMeasurementsMeasure>
            updatedFilteredMeasurementsMeasureList = sorObject
                .filteredMeasurementsMeasure
                .map((filteredMeasurementsMeasure) {
          if (filteredMeasurementsMeasure.id == filteredMeasurementsMeasureId) {
            final List<MeasureLineItem> updatedMeasurementLineItems =
                List.from(filteredMeasurementsMeasure.measureLineItems ?? [])
                  ..addAll(updatedMeasurementLine);

            return filteredMeasurementsMeasure.copyWith(
              measureLineItems: updatedMeasurementLineItems,
            );
          }
          return filteredMeasurementsMeasure;
        }).toList();

        return sorObject.copyWith(
          filteredMeasurementsMeasure: updatedFilteredMeasurementsMeasureList,
        );
      }
      return sorObject;
    }).toList();
  }

// Function to calculate total quantity and skip object at specified index
  static double calculateTotalQuantityAndSkip(
    List<SorObject> sorObjects,
    String sorId,
    String filteredMeasurementsMeasureId,
    int measurementLineIndex,
  ) {
    double totalQuantity = 0.0; // Initialize total quantity

    for (var sorObject in sorObjects) {
      if (sorObject.sorId == sorId) {
        for (var filteredMeasurementsMeasure
            in sorObject.filteredMeasurementsMeasure) {
          if (filteredMeasurementsMeasure.id == filteredMeasurementsMeasureId) {
            for (var measurementLine
                in filteredMeasurementsMeasure.measureLineItems!) {
              if (measurementLine.measurelineitemNo != measurementLineIndex) {
                // Add quantity to totalQuantity
                totalQuantity +=
                    double.parse(measurementLine.quantity.toString());
              }
            }
          }
        }
      }
    }

    // Return totalQuantity
    return totalQuantity.toDouble();
  }

// Function to calculate total quantity and  object at specified index
  static TotalEstimate calculateTotalQuantity(
    List<SorObject> sorObjects,
    String sorId,
    String filteredMeasurementsMeasureId,
    int measurementLineIndex,
  ) {
    List<SorObject> updatedSorObjects = [];
    double totalAmount = 0.0;
    for (SorObject sorObject in sorObjects) {
      List<FilteredMeasurementsMeasure> updatedMeasures = [];
      for (FilteredMeasurementsMeasure measure
          in sorObject.filteredMeasurementsMeasure) {
        double sum = 0.0;
        for (int j = 0; j < measure.measureLineItems!.length; j++) {
          sum += measure.contracts?.first.estimates?.first.isDeduction == true
              ? -double.parse(measure.measureLineItems![j].quantity.toString())
              : double.parse(measure.measureLineItems![j].quantity.toString());
        }
        // Create a new instance of FilteredMeasurementsMeasure with updated numItems
        FilteredMeasurementsMeasure updatedMeasure =
            FilteredMeasurementsMeasure(
          length: measure.length,
          breath: measure.breath,
          height: measure.height,
          numItems: sum,
          cumulativeValue: measure.cumulativeValue,
          currentValue: measure.currentValue,
          tenantId: measure.tenantId,
          mbAmount: double.parse(
              (sum * measure.contracts!.first.unitRate!).toString()),
          type: measure.type,
          targetId: measure.targetId,
          isActive: measure.isActive,
          id: measure.id,
          referenceId: measure.referenceId,
          measureLineItems: measure.measureLineItems,
          contracts: measure.contracts,
        );
        totalAmount = totalAmount +
            double.parse(
                measure.contracts?.first.estimates?.first.isDeduction == true
                    ? (sum * measure.contracts!.first.unitRate!).toString()
                    : (sum * measure.contracts!.first.unitRate!).toString());
        updatedMeasures.add(updatedMeasure);
      }
      // Create a new SorObject instance with the updated list
      SorObject updatedSorObject = SorObject(
        // Copy other properties from sorObject if necessary
        id: sorObject.id,
        sorId: sorObject.sorId,
        filteredMeasurementsMeasure: updatedMeasures,
        // Add other properties from sorObject if necessary
      );
      updatedSorObjects.add(updatedSorObject);
    }
    // return updatedSorObjects;

    return TotalEstimate(totalAmount, updatedSorObjects);
  }

// delete measurementLineItem from the list

  static List<SorObject> deleteMeasurementLine(
    List<SorObject> sorObjects,
    String sorId,
    String filteredMeasurementsMeasureId,
    int measurementLineIndex,
  ) {
    return sorObjects.map((sorObject) {
      if (sorObject.sorId == sorId) {
        final List<FilteredMeasurementsMeasure>
            updatedFilteredMeasurementsMeasureList = sorObject
                .filteredMeasurementsMeasure
                .map((filteredMeasurementsMeasure) {
          if (filteredMeasurementsMeasure.id == filteredMeasurementsMeasureId) {
           
            final List<MeasureLineItem> updatedMeasurementLineItems =
                (filteredMeasurementsMeasure.measureLineItems ?? [])
                    .where((item) =>
                        item.measurelineitemNo !=
                        measurementLineIndex) // Assuming MeasureLineItem has a name property
                    .toList();

            

            return filteredMeasurementsMeasure.copyWith(
              measureLineItems: updatedMeasurementLineItems,
              
            );
          }
          return filteredMeasurementsMeasure;
        }).toList();

        return sorObject.copyWith(
          filteredMeasurementsMeasure: updatedFilteredMeasurementsMeasureList,
        );
      }
      return sorObject;
    }).toList();
  }

  static String? convertList(List<dynamic> dynamicList, int? id) {
    List<Map<String, dynamic>> convertedList = [];

    for (var item in dynamicList) {
      if (item is Map<String, dynamic>) {
        convertedList.add(item);
      } else {
        // Handle cases where items in the dynamic list are not maps
        // You might want to skip these or convert them if possible
      }
    }
    if (id == null) {
      return null;
    } else {
      Map<String, dynamic>? data = {};
      data = convertedList.firstWhereOrNull(
          (element) => int.parse(element['startDate'].toString()) == id);

      return data == null ? null : data['musterRollNumber'];
    }
  }

  static List<dynamic> measureListFilter(Measure measure) {

    final List<Map<String, dynamic>>? data =
        measure.measureAdditionalDetails?.measureLineItems
            ?.map((item) {
              if (item.number != 0 &&
                  item.width != 0 &&
                  item.height != 0 &&
                  item.length != 0 &&
                  item.quantity != 0) {
                return {
                  'width': item.width,
                  'height': item.height,
                  'length': item.length,
                  'number': item.number,
                  'quantity': item.quantity,
                  'measurelineitemNo': item.measurelineitemNo,
                  'measureSummary':item.measurementSummary
                };
              }
              return null;
            })
            .where((element) => element != null)
            .toList()
            .cast<Map<String, dynamic>>();

    if (data == null || data.isEmpty) {
      return [];
    } else {
      return data;
    }
  }
}

class TotalEstimate {
  final double totalAmount;
  final List<SorObject> sorObjectList;
  const TotalEstimate(this.totalAmount, this.sorObjectList);
}
