import 'dart:async';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/common_repository/common_repository.dart';

part 'muster_roll_pdf.freezed.dart';

typedef MusterRollPDFEmitter = Emitter<MusterRollPDFState>;

class MusterRollPDFBloc extends Bloc<MusterRollPDFEvent, MusterRollPDFState> {
  MusterRollPDFBloc() : super(const MusterRollPDFState.initial()) {
    on<PDFEventMusterRoll>(_onMusterRollPDF);
  }

  FutureOr<void> _onMusterRollPDF(
    PDFEventMusterRoll event,
    MusterRollPDFEmitter emit,
  ) async {
    Client client = Client();
    var selectedLocale = await GlobalVariables.selectedLocale();
    try {
      await CommonRepository(client.init()).downloadPDF(
        url: '${Urls.commonServices.pdfDownload}/musterRoll/muster-roll',
        queryParameters: {
          "musterRollNumber": event.musterRollNumber.toString(),
          "tenantId": event.tenantId.toString(),
        },
        fileName: 'MusterRoll.pdf',
        options: Options(extra: {
          "userInfo": GlobalVariables.userRequestModel,
          "accessToken": GlobalVariables.authToken,
          "msgId": "20170310130900|$selectedLocale"
        }, headers: {
          HttpHeaders.contentTypeHeader: 'application/json',
        }, responseType: ResponseType.bytes),
      );
    } on DioException catch (e) {
      emit(MusterRollPDFState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MusterRollPDFEvent with _$MusterRollPDFEvent {
  const factory MusterRollPDFEvent.onMusterRollPDF(
      {String? tenantId, String? musterRollNumber}) = PDFEventMusterRoll;
}

@freezed
class MusterRollPDFState with _$MusterRollPDFState {
  const MusterRollPDFState._();
  const factory MusterRollPDFState.initial() = _Initial;
  const factory MusterRollPDFState.loading() = _Loading;
  const factory MusterRollPDFState.loaded() = _Loaded;
  const factory MusterRollPDFState.error(String? error) = _Error;
}
