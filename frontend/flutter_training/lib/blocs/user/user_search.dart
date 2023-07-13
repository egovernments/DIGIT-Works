import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/Env/env_config.dart';
import 'package:flutter_training/models/user_search/user_search_model.dart';
import 'package:flutter_training/services/urls.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/user_search_repository/user_search.dart';

part 'user_search.freezed.dart';

typedef UserSearchEmitter = Emitter<UserSearchState>;

class UserSearchBloc extends Bloc<UserSearchEvent, UserSearchState> {
  UserSearchBloc() : super(const UserSearchState()) {
    on<SearchUserEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(
      UserSearchEvent event, UserSearchEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    UserSearchModel userSearchModel = await UserSearchRepository(client.init())
        .searchUser(url: Urls.userServices.userSearchProfile, body: {
      "tenantId": envConfig.variables.tenantId,
      "uuid": [GlobalVariables.uuid]
    });
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(userSearchModel: userSearchModel, loading: false));
  }
}

@freezed
class UserSearchEvent with _$UserSearchEvent {
  const factory UserSearchEvent.search() = SearchUserEvent;
}

@freezed
class UserSearchState with _$UserSearchState {
  const UserSearchState._();

  const factory UserSearchState({
    @Default(false) bool loading,
    UserSearchModel? userSearchModel,
  }) = _UserSearchState;
}
