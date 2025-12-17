import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../utils/global_variables.dart';
import '../../data/remote_client.dart';
import '../../data/repositories/work_order_repository/my_works_repository.dart';
import '../../models/works/my_works_search_criteria.dart';

part 'my_works_search_criteria.freezed.dart';

typedef MyWorksSearchCriteriaBlocEmitter
    = Emitter<MyWorksSearchCriteriaBlocState>;

class MyWorksSearchCriteriaBloc extends Bloc<MyWorksSearchCriteriaBlocEvent,
    MyWorksSearchCriteriaBlocState> {
  MyWorksSearchCriteriaBloc()
      : super(const MyWorksSearchCriteriaBlocState.initial()) {
    on<GetMyWorksSearchCriteriaConfigEvent>(_getMyWorksSearchCriteriaConfig);
  }

  FutureOr<void> _getMyWorksSearchCriteriaConfig(
    GetMyWorksSearchCriteriaConfigEvent event,
    MyWorksSearchCriteriaBlocEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const MyWorksSearchCriteriaBlocState.loading());
      MyWorksSearchCriteriaModel configModel =
          await MyWorksRepository(client.init()).getSearchCriteria(
              apiEndPoint: Urls.initServices.mdms,
              tenantId: GlobalVariables
                  .globalConfigObject!.globalConfigs!.stateTenantId
                  .toString(),
              moduleDetails: [
            {
              "moduleName": "commonUiConfig",
              "masterDetails": [
                {"name": "CBOMyWorks"}
              ],
            }
          ]);

      emit(MyWorksSearchCriteriaBlocState.loaded(
          configModel
              .commonUiConfig?.cboMyWorksSearchCriteria?.first.searchCriteria,
          configModel
              .commonUiConfig?.cboMyWorksSearchCriteria?.first.acceptCode));
    } on DioException catch (e) {
      emit(MyWorksSearchCriteriaBlocState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MyWorksSearchCriteriaBlocEvent with _$MyWorksSearchCriteriaBlocEvent {
  const factory MyWorksSearchCriteriaBlocEvent.getSearchCriteria() =
      GetMyWorksSearchCriteriaConfigEvent;
}

@freezed
class MyWorksSearchCriteriaBlocState with _$MyWorksSearchCriteriaBlocState {
  const MyWorksSearchCriteriaBlocState._();
  const factory MyWorksSearchCriteriaBlocState.initial() = _Initial;
  const factory MyWorksSearchCriteriaBlocState.loading() = _Loading;
  const factory MyWorksSearchCriteriaBlocState.loaded(
      final List<String>? searchCriteria, final String? acceptCode) = _Loaded;
  const factory MyWorksSearchCriteriaBlocState.error(String? error) = _Error;
}
