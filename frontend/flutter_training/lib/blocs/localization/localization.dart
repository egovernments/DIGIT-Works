import 'dart:async';
import 'dart:convert';
import 'dart:ui';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:flutter_training/services/urls.dart';

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
    if (await GlobalVariables.isLocaleSelect(event.locale, event.module)) {
      dynamic localLabelResponse;
      if (kIsWeb) {
        localLabelResponse = html.window.sessionStorage[event.locale ?? ''];
      } else {
        localLabelResponse = await storage.read(key: event.locale ?? '');
      }

      if (localLabelResponse != null) {
        localizationMessages = jsonDecode(localLabelResponse)
            .map<LocalizationMessageModel>(
                (e) => LocalizationMessageModel.fromJson(e))
            .toList();
      }
      await AppLocalizations(
        Locale(event.locale.split('_').first, event.locale.split('_').last),
      ).load();
      emit(LocalizationState.loaded(localizationMessages));
      await AppLocalizations(
        Locale(event.locale.split('_').first, event.locale.split('_').last),
      ).load();
    } else {
      emit(const LocalizationState.loading());
      LocalizationModel result = await localizationRepository.search(
        url: Urls.initServices.localizationSearch,
        queryParameters: {
          "module": event.module,
          "locale": event.locale,
          "tenantId": event.tenantId,
        },
      );

      if (kIsWeb) {
        var existing = html.window.sessionStorage[event.locale ?? ''];
        if (existing != null) {
          var existingObject = json.decode(existing);
          existingObject
              .addAll(result.messages.map((e) => e.toJson()).toList());
          html.window.sessionStorage[event.locale ?? ''] =
              jsonEncode(existingObject);
        } else {
          html.window.sessionStorage[event.locale ?? ''] =
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
        localLabelResponse = html.window.sessionStorage[event.locale ?? ''];
      } else {
        localLabelResponse = await storage.read(key: event.locale ?? '');
      }

      if (localLabelResponse != null) {
        localizationMessages = jsonDecode(localLabelResponse)
            .map<LocalizationMessageModel>(
                (e) => LocalizationMessageModel.fromJson(e))
            .toList();
      }
      await AppLocalizations(Locale(
              event.locale.split('_').first, event.locale.split('_').last))
          .load();
      emit(LocalizationState.loaded(localizationMessages));
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
  const LocalizationState._();
  const factory LocalizationState.initial() = _Initial;
  const factory LocalizationState.loading() = _Loading;
  const factory LocalizationState.loaded(
      List<LocalizationMessageModel>? localization) = _Loaded;
  const factory LocalizationState.error(String? error) = _Error;
}
