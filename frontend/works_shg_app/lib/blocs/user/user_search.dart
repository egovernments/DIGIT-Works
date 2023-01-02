import 'dart:async';
import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/data/repositories/user_search_repository/user_search.dart';
import 'package:works_shg_app/models/request_info/request_info_model.dart';
import 'package:works_shg_app/models/user_search/user_search_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../services/local_storage.dart';

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

    dynamic localUserDetails;
    String? accessToken;
    String? uuid;
    String? tenantId;
    if (kIsWeb) {
      localUserDetails = html.window.localStorage['userRequest' ?? ''];
      accessToken = html.window.localStorage['accessToken' ?? ''];
      uuid = html.window.localStorage['uuid' ?? ''];
      tenantId = html.window.localStorage['tenantId' ?? ''];
    } else {
      localUserDetails = await storage.read(key: 'userRequest' ?? '');
      accessToken = await storage.read(key: 'accessToken' ?? '');
      uuid = await storage.read(key: 'uuid' ?? '');
      tenantId = await storage.read(key: 'tenantId' ?? '');
    }
    UserSearchModel userSearchModel = await UserSearchRepository(client.init())
        .searchUser(url: Urls.userServices.userSearchProfile, body: {
      "RequestInfo": {
        ...RequestInfoModel(
          apiId: 'Rainmaker',
          ver: ".01",
          ts: "",
          action: "_search",
          did: "1",
          key: "",
          msgId: "20170310130900|en_IN",
          authToken: jsonDecode(accessToken.toString()),
        ).toJson(),
        "userInfo": jsonDecode(localUserDetails)
      },
      "tenantId": jsonDecode(tenantId.toString()),
      "uuid": [jsonDecode(uuid.toString())]
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
