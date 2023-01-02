import 'dart:async';
import 'dart:convert';
import 'dart:ui';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/remote/localization.dart';
import '../../models/localization/localization_model.dart';
import '../../services/local_storage.dart';
import 'app_localization.dart';

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

    if (html.window.localStorage[event.locale] != null) {
      await AppLocalizations(
        Locale(event.locale.split('_').first, event.locale.split('_').last),
      ).load();
    } else {
      LocalizationModel result =
          await LocalizationRepository(client.init()).search(
        url: Urls.initServices.localizationSearch,
        queryParameters: {
          "module": event.module,
          "locale": event.locale,
          "tenantId": "pb",
        },
      );

      if (kIsWeb) {
        html.window.localStorage[event.locale ?? ''] =
            jsonEncode(result.messages.map((e) => e.toJson()).toList());
      } else {
        await storage.write(
            key: event.locale ?? '',
            value: jsonEncode(result.messages.map((e) => e.toJson()).toList()));
      }
      final List<LocalizationMessageModel> newLocalizationList =
          <LocalizationMessageModel>[];
    }
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
