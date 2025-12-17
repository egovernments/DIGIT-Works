import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/mdms/location_mdms.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/common_repository/common_repository.dart';

part 'wage_seeker_locality_bloc.freezed.dart';

typedef WageSeekerLocalityEmitter = Emitter<WageSeekerLocalityState>;

class WageSeekerLocalityBloc
    extends Bloc<WageSeekerLocalityEvent, WageSeekerLocalityState> {
  WageSeekerLocalityBloc() : super(const WageSeekerLocalityState.initial()) {
    on<LocalityEventWageSeeker>(_onWageSeekerLocality);
  }

  FutureOr<void> _onWageSeekerLocality(
    LocalityEventWageSeeker event,
    WageSeekerLocalityEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const WageSeekerLocalityState.loading());

      Location result = await CommonRepository(client.init()).getCities(
          url: Urls.commonServices.fetchCities,
          queryParameters: {
            "hierarchyType": "ADMIN",
            "boundaryType": "Locality",
            "tenantId": event.tenantId.toString()
          },
          options: Options(extra: {
            "accessToken": GlobalVariables.authToken,
            "userInfo": GlobalVariables.userRequestModel,
          }));

      if (result != null) {
        emit(WageSeekerLocalityState.loaded(result));
      }
    } on DioException catch (e) {
      emit(
          WageSeekerLocalityState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class WageSeekerLocalityEvent with _$WageSeekerLocalityEvent {
  const factory WageSeekerLocalityEvent.onWageSeekerLocality(
      {String? tenantId}) = LocalityEventWageSeeker;
}

@freezed
class WageSeekerLocalityState with _$WageSeekerLocalityState {
  const WageSeekerLocalityState._();
  const factory WageSeekerLocalityState.initial() = _Initial;
  const factory WageSeekerLocalityState.loading() = _Loading;
  const factory WageSeekerLocalityState.loaded(Location? locality) = _Loaded;
  const factory WageSeekerLocalityState.error(String? error) = _Error;
}
