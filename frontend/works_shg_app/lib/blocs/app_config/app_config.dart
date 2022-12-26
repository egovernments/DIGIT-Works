import 'dart:async';
import 'dart:convert';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

import '../../data/fake_app_config.dart';
import '../../models/app_config/app_config_model.dart';

part 'app_config.freezed.dart';

typedef ApplicationConfigEmitter = Emitter<ApplicationConfigState>;

class ApplicationConfigBloc
    extends Bloc<ApplicationConfigEvent, ApplicationConfigState> {
  ApplicationConfigBloc(super.initialState) {
    on<UpdateActionEvent>(_onfetchConfig);
  }

  FutureOr<void> _onfetchConfig(
    UpdateActionEvent event,
    ApplicationConfigEmitter emit,
  ) async {
    emit(state.copyWith(isloading: true));
    emit(state.copyWith(
      appConfigDetail: AppConfigModel.fromJson(json.decode(mokeAppConigData)),
      isloading: false,
    ));
  }
}

@freezed
class ApplicationConfigEvent with _$ApplicationConfigEvent {
  const factory ApplicationConfigEvent.onfetchConfig() = UpdateActionEvent;
}

@freezed
class ApplicationConfigState with _$ApplicationConfigState {
  const ApplicationConfigState._();
  const factory ApplicationConfigState({
    @Default(false) bool isloading,
    AppConfigModel? appConfigDetail,
  }) = _ApplicationConfigState;
}
