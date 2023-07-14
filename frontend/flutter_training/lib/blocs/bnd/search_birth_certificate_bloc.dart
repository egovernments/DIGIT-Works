import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/data/repositories/bnd-repository/bnd_repository.dart';
import 'package:flutter_training/services/urls.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:freezed_annotation/freezed_annotation.dart';

import '../../data/remote_client.dart';
import '../../models/create-birth-registration/birth_certificates_model.dart';

part 'search_birth_certificate_bloc.freezed.dart';

typedef BirthSearchCertEmitter = Emitter<BirthSearchCertState>;

class BirthSearchCertBloc
    extends Bloc<BirthSearchCertEvent, BirthSearchCertState> {
  BirthSearchCertBloc() : super(const BirthSearchCertState.initial()) {
    on<SearchBirthCertEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(
      SearchBirthCertEvent event, BirthSearchCertEmitter emit) async {
    Client client = Client();
    try {
      emit(const BirthSearchCertState.loading());
      BirthCertificatesList birthCertificatesList =
          await BNDRepository(client.init()).searchBirthCertificates(
              url: Urls.bndServices.searchBirth,
              body: {},
              queryParameters: event.queryParams);
      if (birthCertificatesList.birthCerts != null &&
          birthCertificatesList.birthCerts.isNotEmpty) {
        emit(BirthSearchCertState.loaded(birthCertificatesList));
      } else if (birthCertificatesList.birthCerts != null &&
          birthCertificatesList.birthCerts.isEmpty) {
        emit(BirthSearchCertState.error(i18.common.noCertificatesExists));
      } else {
        emit(BirthSearchCertState.error(i18.common.someErrorOccurred));
      }
    } on DioError catch (e) {
      emit(BirthSearchCertState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class BirthSearchCertEvent with _$BirthSearchCertEvent {
  const factory BirthSearchCertEvent.search({
    required Map<String, dynamic>? queryParams,
  }) = SearchBirthCertEvent;
}

@freezed
class BirthSearchCertState with _$BirthSearchCertState {
  const BirthSearchCertState._();
  const factory BirthSearchCertState.initial() = _Initial;
  const factory BirthSearchCertState.loading() = _Loading;
  const factory BirthSearchCertState.loaded(
      BirthCertificatesList? birthCertificatesList) = _Loaded;
  const factory BirthSearchCertState.error(String? error) = _Error;
}
