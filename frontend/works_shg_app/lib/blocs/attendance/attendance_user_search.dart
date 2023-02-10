import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/user_search/user_search_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/user_search_repository/user_search.dart';

part 'attendance_user_search.freezed.dart';

typedef AttendanceUserSearchEmitter = Emitter<AttendanceUserSearchState>;

class AttendanceUserSearchBloc
    extends Bloc<AttendanceUserSearchEvent, AttendanceUserSearchState> {
  AttendanceUserSearchBloc(super.initialState) {
    on<SearchAttendanceUserEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(
      AttendanceUserSearchEvent event, AttendanceUserSearchEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    UserSearchModel userSearchModel = await UserSearchRepository(client.init())
        .searchUser(url: Urls.userServices.userSearchProfile, body: {
      "tenantId": GlobalVariables.getTenantId(),
      "mobileNumber": GlobalVariables.getMobileNumber()
    });
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(userSearchModel: userSearchModel, loading: false));
  }
}

@freezed
class AttendanceUserSearchEvent with _$AttendanceUserSearchEvent {
  const factory AttendanceUserSearchEvent.search() = SearchAttendanceUserEvent;
}

@freezed
class AttendanceUserSearchState with _$AttendanceUserSearchState {
  const AttendanceUserSearchState._();

  const factory AttendanceUserSearchState({
    @Default(false) bool loading,
    UserSearchModel? userSearchModel,
  }) = _UserSearchState;
}
