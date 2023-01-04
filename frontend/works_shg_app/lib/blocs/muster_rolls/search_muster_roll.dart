import 'dart:async';
import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/data/repositories/muster_roll_repository/muster_roll.dart';
import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';
import 'package:works_shg_app/models/request_info/request_info_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../services/local_storage.dart';

part 'search_muster_roll.freezed.dart';

typedef MusterRollSearchEmitter = Emitter<MusterRollSearchState>;

class MusterRollSearchBloc
    extends Bloc<MusterRollSearchEvent, MusterRollSearchState> {
  MusterRollSearchBloc() : super(const MusterRollSearchState()) {
    on<SearchMusterRollEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(
      MusterRollSearchEvent event, MusterRollSearchEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    dynamic localUserDetails;
    String? accessToken;
    String? tenantId;

    if (kIsWeb) {
      localUserDetails = html.window.localStorage['userRequest' ?? ''];
      accessToken = html.window.localStorage['accessToken' ?? ''];
      tenantId = html.window.localStorage['tenantId' ?? ''];
    } else {
      localUserDetails = await storage.read(key: 'userRequest' ?? '');
      accessToken = await storage.read(key: 'accessToken' ?? '');
      tenantId = await storage.read(key: 'tenantId' ?? '');
    }

    MusterRollsModel musterRollsModel =
        await MusterRollRepository(client.init()).searchMusterRolls(
            url: Urls.musterRollServices.searchMusterRolls,
            queryParameters: {
          "tenantId": jsonDecode(tenantId.toString())
        },
            body: {
          "RequestInfo": {
            ...RequestInfoModel(
              apiId: 'asset-services',
              ver: ".01",
              ts: "",
              action: "_search",
              did: "1",
              key: "",
              msgId: "search with from and to values",
              authToken: jsonDecode(accessToken.toString()),
            ).toJson(),
            "userInfo": jsonDecode(localUserDetails)
          }
        });
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(musterRollsModel: musterRollsModel, loading: false));
  }
}

@freezed
class MusterRollSearchEvent with _$MusterRollSearchEvent {
  const factory MusterRollSearchEvent.search() = SearchMusterRollEvent;
}

@freezed
class MusterRollSearchState with _$MusterRollSearchState {
  const MusterRollSearchState._();

  const factory MusterRollSearchState({
    @Default(false) bool loading,
    MusterRollsModel? musterRollsModel,
  }) = _MusterRollSearchState;
}
