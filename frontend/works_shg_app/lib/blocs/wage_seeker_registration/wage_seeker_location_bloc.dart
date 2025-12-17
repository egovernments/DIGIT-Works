import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/common_repository/common_repository.dart';
import '../../models/mdms/location_mdms.dart';

part 'wage_seeker_location_bloc.freezed.dart';

typedef WageSeekerLocationEmitter = Emitter<WageSeekerLocationState>;

class WageSeekerLocationBloc
    extends Bloc<WageSeekerLocationEvent, WageSeekerLocationState> {
  WageSeekerLocationBloc() : super(const WageSeekerLocationState.initial()) {
    on<LocationEventWageSeeker>(_onWageSeekerLocation);
  }

  FutureOr<void> _onWageSeekerLocation(
    LocationEventWageSeeker event,
    WageSeekerLocationEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const WageSeekerLocationState.loading());

      Location result = await CommonRepository(client.init()).getCities(
          url: Urls.commonServices.fetchCities,
          queryParameters: {
            "hierarchyType": "ADMIN",
            "boundaryType": "Ward",
            "tenantId": event.tenantId.toString()
          },
          options: Options(extra: {
            "accessToken": GlobalVariables.authToken,
            "userInfo": GlobalVariables.userRequestModel,
          }));

      if (result != null) {
        emit(WageSeekerLocationState.loaded(result));
      }
    } on DioException catch (e) {
      emit(
          WageSeekerLocationState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class WageSeekerLocationEvent with _$WageSeekerLocationEvent {
  const factory WageSeekerLocationEvent.onWageSeekerLocation(
      {String? tenantId}) = LocationEventWageSeeker;
}

@freezed
class WageSeekerLocationState with _$WageSeekerLocationState {
  const WageSeekerLocationState._();
  const factory WageSeekerLocationState.initial() = _Initial;
  const factory WageSeekerLocationState.loading() = _Loading;
  const factory WageSeekerLocationState.loaded(Location? location) = _Loaded;
  const factory WageSeekerLocationState.error(String? error) = _Error;
}
