import 'dart:async';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/common_repository/common_repository.dart';

part 'work_order_pdf.freezed.dart';

typedef WorkOrderPDFEmitter = Emitter<WorkOrderPDFState>;

class WorkOrderPDFBloc extends Bloc<WorkOrderPDFEvent, WorkOrderPDFState> {
  WorkOrderPDFBloc() : super(const WorkOrderPDFState.initial()) {
    on<PDFEventWorkOrder>(_onWorkOrderPDF);
  }

  FutureOr<void> _onWorkOrderPDF(
    PDFEventWorkOrder event,
    WorkOrderPDFEmitter emit,
  ) async {
    Client client = Client();
    try {
      var selectedLocale = await GlobalVariables.selectedLocale();
      await CommonRepository(client.init()).downloadPDF(
        url: '${Urls.commonServices.pdfDownload}/workOrder/work-order',
        queryParameters: {
          "contractId": event.contractId.toString(),
          "tenantId": event.tenantId.toString(),
        },
        fileName: 'WorkOrder.pdf',
        options: Options(extra: {
          "userInfo": GlobalVariables.userRequestModel,
          "accessToken": GlobalVariables.authToken,
          "msgId": "20170310130900|$selectedLocale"
        }, headers: {
          HttpHeaders.contentTypeHeader: 'application/json',
        }, responseType: ResponseType.bytes),
      );
    } on DioError catch (e) {
      emit(WorkOrderPDFState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class WorkOrderPDFEvent with _$WorkOrderPDFEvent {
  const factory WorkOrderPDFEvent.onWorkOrderPDF(
      {String? tenantId, String? contractId}) = PDFEventWorkOrder;
}

@freezed
class WorkOrderPDFState with _$WorkOrderPDFState {
  const WorkOrderPDFState._();
  const factory WorkOrderPDFState.initial() = _Initial;
  const factory WorkOrderPDFState.loading() = _Loading;
  const factory WorkOrderPDFState.loaded() = _Loaded;
  const factory WorkOrderPDFState.error(String? error) = _Error;
}
