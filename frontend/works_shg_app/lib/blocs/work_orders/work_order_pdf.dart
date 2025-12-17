import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/common_repository/common_repository.dart';

part 'work_order_pdf.freezed.dart';

typedef WorkOrderPDFEmitter = Emitter<WorkOrderPDFState>;

class WorkOrderPDFBloc extends Bloc<WorkOrderPDFEvent, WorkOrderPDFState> {
  WorkOrderPDFBloc() : super(const WorkOrderPDFState.initial()) {
    on<PDFEventWorkOrder>(_onWorkOrderPDF);
     on<PDFEventAnalysis>(_onAnalysisPDF);
    
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
    } on DioException catch (e) {
      emit(WorkOrderPDFState.error(e.response?.data['Errors'][0]['code']));
    }
  }
String convertString(String input) {
  // Convert the input string to lowercase
  String lowerCaseInput = input.toLowerCase();
  
  // Replace '/' with '_'
  String replacedSlashes = lowerCaseInput.replaceAll('/', '_');
  
  // Replace '-' with '_'
  String replacedDashes = replacedSlashes.replaceAll('-', '_');
  
  return replacedDashes;
}
  FutureOr<void> _onAnalysisPDF(
    PDFEventAnalysis event,
    WorkOrderPDFEmitter emit,
  ) async {
    Client client = Client();
    try {
        
      emit(const WorkOrderPDFState.initial());
      var selectedLocale = await GlobalVariables.selectedLocale();
      await CommonRepository(client.init()).downloadPDF(
        url: '${Urls.commonServices.pdfDownload}/analysisStatement/analysis-statement',
        queryParameters: {
          "referenceId": event.estimateId.toString(),
          "tenantId": event.tenantId.toString(),
        },
        fileName: 'analysis_statement_${convertString(event.workorder??"_")}.pdf',
        options: Options(extra: {
          "userInfo": GlobalVariables.userRequestModel,
          "accessToken": GlobalVariables.authToken,
          "msgId": "20170310130900|$selectedLocale"
        }, headers: {
          HttpHeaders.contentTypeHeader: 'application/json',
        }, responseType: ResponseType.bytes),
      );
    } on DioException catch (e) {

      if (e.response != null) {
      final responseData = e.response?.data;

      // Logging the error for debugging purposes
     
      
      if (responseData is List<int>) {
        // Decode the byte array to a string
        String responseString = utf8.decode(responseData);
        
        // Parse the JSON string to a Map
        final Map<String, dynamic> responseJson = jsonDecode(responseString);

        // Extracting and emitting specific error code if available
        if (responseJson.containsKey('Errors')) {
          final errorCode = responseJson['Errors'][0]['message'];
          emit(WorkOrderPDFState.error(errorCode));
        } else {
          emit(WorkOrderPDFState.error('Unexpected error format: $responseString'));
        }
      } else {
        emit(WorkOrderPDFState.error('Unexpected response type: ${e.response?.data}'));
      }
    } else {
     
     
      emit(WorkOrderPDFState.error(e.message));
    }
    }
     catch (e) {
      emit(const WorkOrderPDFState.error("Something went wrong"));
    }
  }
}

@freezed
class WorkOrderPDFEvent with _$WorkOrderPDFEvent {
  const factory WorkOrderPDFEvent.onWorkOrderPDF(
      {String? tenantId, String? contractId}) = PDFEventWorkOrder;
      const factory WorkOrderPDFEvent.onAnalysisPDF(
      {String? tenantId, String? estimateId,String? workorder}) = PDFEventAnalysis;
}

@freezed
class WorkOrderPDFState with _$WorkOrderPDFState {
  const WorkOrderPDFState._();
  const factory WorkOrderPDFState.initial() = _Initial;
  const factory WorkOrderPDFState.loading() = _Loading;
  const factory WorkOrderPDFState.loaded() = _Loaded;
  const factory WorkOrderPDFState.error(String? error) = _Error;
}
