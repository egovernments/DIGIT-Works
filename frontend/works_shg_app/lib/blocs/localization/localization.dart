import 'dart:async';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/remote/localization.dart';
import '../../models/localization/localization_model.dart';

part 'localization.freezed.dart';

typedef LocalizationEmitter = Emitter<LocalizationState>;

class LocalizationBloc extends Bloc<LocalizationEvent, LocalizationState> {
  LocalizationBloc(super.initialState) {
    on<OnLoadLocalizationEvent>(_onLoadLocalization);
  }

  FutureOr<void> _onLoadLocalization(
    OnLoadLocalizationEvent event,
    LocalizationEmitter emit,
  ) async {
    Client client = Client();

    LocalizationModel result =
        await LocalizationRepository(client.init()).search(
      url: 'localization/messages/v1/_search',
      queryParameters: {
        "module": "rainmaker-common,rainmaker-pb,rainmaker-works",
        "locale": event.locale,
        "tenantId": "pb",
      },
    );

    emit(state.copyWith(localization: result));
    await Future.delayed(const Duration(seconds: 1));
  }
}

@freezed
class LocalizationEvent with _$LocalizationEvent {
  const factory LocalizationEvent.onLoadLocalization({
    required String module,
    required String tenantId,
    required String locale,
  }) = OnLoadLocalizationEvent;
}

@freezed
class LocalizationState with _$LocalizationState {
  const factory LocalizationState({
    LocalizationModel? localization,
    @Default(false) bool isLocalizationLoadCompleted,
  }) = _LocalizationState;
}
