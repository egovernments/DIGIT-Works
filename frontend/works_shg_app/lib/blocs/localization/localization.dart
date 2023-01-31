import 'dart:async';
import 'dart:convert';
import 'dart:ui';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/services/urls.dart';

import '../../data/repositories/remote/localization.dart';
import '../../models/localization/localization_model.dart';
import '../../services/local_storage.dart';
import '../../utils/global_variables.dart';
import 'app_localization.dart';

part 'localization.freezed.dart';

typedef LocalizationEmitter = Emitter<LocalizationState>;
List<LocalizationMessageModel>? localizationMessages;

class LocalizationBloc extends Bloc<LocalizationEvent, LocalizationState> {
  final LocalizationRepository localizationRepository;
  LocalizationBloc(super.initialState, this.localizationRepository) {
    on<OnLoadLocalizationEvent>(_onLoadLocalization);
  }

  FutureOr<void> _onLoadLocalization(
    OnLoadLocalizationEvent event,
    LocalizationEmitter emit,
  ) async {
    if (GlobalVariables.isLocaleSelect(event.locale, event.module)) {
      emit(state.copyWith(isLocalizationLoadCompleted: false));
      dynamic localLabelResponse;
      if (kIsWeb) {
        localLabelResponse = html.window.localStorage[event.locale ?? ''];
      } else {
        localLabelResponse = storage.read(key: event.locale ?? '');
      }

      if (localLabelResponse != null) {
        localizationMessages = jsonDecode(localLabelResponse)
            .map<LocalizationMessageModel>(
                (e) => LocalizationMessageModel.fromJson(e))
            .toList();
      }

      emit(state.copyWith(localization: localizationMessages));
      await AppLocalizations(
        Locale(event.locale.split('_').first, event.locale.split('_').last),
      ).load();
      emit(state.copyWith(isLocalizationLoadCompleted: true));
      await AppLocalizations(
        Locale(event.locale.split('_').first, event.locale.split('_').last),
      ).load();
    } else {
      emit(state.copyWith(isLocalizationLoadCompleted: false));
      LocalizationModel result = await localizationRepository.search(
        url: Urls.initServices.localizationSearch,
        queryParameters: {
          "module": event.module,
          "locale": event.locale,
          "tenantId": event.tenantId,
        },
      );

      if (kIsWeb) {
        var existing = html.window.localStorage[event.locale ?? ''];
        if (existing != null) {
          var existingObject = json.decode(existing);
          existingObject
              .addAll(result.messages.map((e) => e.toJson()).toList());
          html.window.localStorage[event.locale ?? ''] =
              jsonEncode(existingObject);
        } else {
          html.window.localStorage[event.locale ?? ''] =
              jsonEncode(result.messages.map((e) => e.toJson()).toList());
        }
      } else {
        var existing = await storage.read(key: event.locale);
        if (existing != null) {
          var existingObject = json.decode(existing);
          existingObject
              .addAll(result.messages.map((e) => e.toJson()).toList());
          await storage.write(
              key: event.locale ?? '', value: jsonEncode(existingObject));
        } else {
          await storage.write(
              key: event.locale ?? '',
              value:
                  jsonEncode(result.messages.map((e) => e.toJson()).toList()));
        }
      }

      dynamic localLabelResponse;
      if (kIsWeb) {
        localLabelResponse = html.window.localStorage[event.locale ?? ''];
      } else {
        localLabelResponse = storage.read(key: event.locale ?? '');
      }

      if (localLabelResponse != null) {
        localizationMessages = jsonDecode(localLabelResponse)
            .map<LocalizationMessageModel>(
                (e) => LocalizationMessageModel.fromJson(e))
            .toList();
      }

      emit(state.copyWith(localization: localizationMessages));

      await AppLocalizations(Locale(
              event.locale.split('_').first, event.locale.split('_').last))
          .load();
      emit(state.copyWith(isLocalizationLoadCompleted: true));
      await AppLocalizations(
        Locale(event.locale.split('_').first, event.locale.split('_').last),
      ).load();
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
    List<LocalizationMessageModel>? localization,
    @Default(false) bool isLocalizationLoadCompleted,
  }) = _LocalizationState;
}
