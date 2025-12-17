import 'dart:async';
import 'dart:convert';
import 'dart:ui';
import '../../services/local_storage.dart';
import 'package:collection/collection.dart';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:works_shg_app/models/localization/module_status.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:universal_html/html.dart' as html;
import '../../data/repositories/remote/localization.dart';
import '../../data/schema/localization.dart';
import '../../models/app_config/app_config_model.dart';
import '../../models/localization/localization_model.dart';
import 'app_localization.dart';

part 'localization.freezed.dart';

typedef LocalizationEmitter = Emitter<LocalizationState>;
List<LocalizationMessageModel>? localizationMessages;

class LocalizationBloc extends Bloc<LocalizationEvent, LocalizationState> {
  final LocalizationRepository localizationRepository;
  LocalizationBloc(
    super.initialState,
    this.localizationRepository,
  ) {
    on<OnLoadLocalizationEvent>(_onLoadLocalization);
    on<OnSpecificLoadLocalizationEvent>(_onSpecificLoadLocalization);
  }

  FutureOr<void> _onLoadLocalization(
    OnLoadLocalizationEvent event,
    LocalizationEmitter emit,
  ) async {
    try {
      emit(const LocalizationState.loading());
      // Create ModuleStatus list based on localization modules
      List<ModuleStatus> module = event.localizationModules!.map((e) {
        Map<String, bool> languageMap = {};
        event.languages!.asMap().forEach((index, lang) {
          languageMap[lang.value] = false;
        });
        return ModuleStatus(
          label: e.label,
          value: e.value,
          status: languageMap,
        );
      }).toList();
      // Extract selected modules
      final List<String> selectedModules = event.module.split(',');

// filter the selected module for fetching the localization data

      for (final selectedModule in selectedModules) {
        final index =
            module.indexWhere((element) => element.value == selectedModule);
        if (index != -1) {
          final Map<String, bool> updatedStatus = Map.from(
              module[index].status); // Make a copy of the existing status map
          updatedStatus[event.locale] =
              true; // Update the value for the desired key
          module[index] = module[index].copyWith(status: updatedStatus);
        }
      }

      // Access Hive box for  localization
      final box = Hive.box<KeyLocaleModel>('keyValueModel');

      LocalizationModel result = await localizationRepository.search(
        url: Urls.initServices.localizationSearch,
        queryParameters: {
          "module": event.module.toString(),
          "locale": event.locale,
          "tenantId": event.tenantId,
        },
      );

      final List<Localization> newLocalizationList = result.messages
          .map((e) => Localization()
            ..message = e.message
            ..code = e.code
            ..locale = e.locale
            ..module = e.module)
          .toList();
      // Add fetched data to Hive box
      KeyLocaleModel keyValueModel = KeyLocaleModel()
        ..locale = event.locale
        ..localizationsList = newLocalizationList;
      await box.add(keyValueModel);

      final List codes = event.locale.split('_');
      await _loadLocale(codes, event.locale);
      // Emit loaded state
      emit(LocalizationState.loaded(event.languages, module));
    } on DioException catch (e) {
      LocalizationState.error(e.response?.data['Errors'][0]['code']);
    }
  }

// Handler for loading specific localization data
  FutureOr<void> _onSpecificLoadLocalization(
    OnSpecificLoadLocalizationEvent event,
    LocalizationEmitter emit,
  ) async {
    try {
      // Check the current state and proceed only if it's in the 'loaded' state
      await state.maybeMap(
        orElse: () => null,
        loaded: (value) async {
          emit(const LocalizationState.loading());
          // update the selected locale
          if (kIsWeb) {
            List<Languages> languagesList =
                jsonDecode(html.window.sessionStorage['languages'].toString())
                    .map<Languages>((e) => Languages.fromJson(e))
                    .toList();

            html.window.sessionStorage['languages'] =
                jsonEncode(languagesList.mapIndexed((i, element) {
              if (element.value == event.locale) {
                return element.copyWith(isSelected: true);
              } else {
                return element.copyWith(isSelected: false);
              }
            }).toList());
          } else {
            var langStorage = await storage.read(key: 'languages');
            List<Languages> languagesList = jsonDecode(langStorage.toString())
                .map<Languages>((e) => Languages.fromJson(e))
                .toList();

            await storage.write(
                key: 'languages',
                value: jsonEncode(languagesList.mapIndexed((i, element) {
                  if (element.value == event.locale) {
                    return element.copyWith(isSelected: true);
                  } else {
                    return element.copyWith(isSelected: false);
                  }
                }).toList()));
          }

          // Generate a list of configured languages with updated selected status
          final List<Languages> configLanguage =
              List.from(value.languages!).map((e) {
            if (e.value == event.locale) {
              final data =
                  Languages(label: e.label, value: e.value, isSelected: true);
              return data;
            } else {
              final data =
                  Languages(label: e.label, value: e.value, isSelected: false);
              return data;
            }
          }).toList();
// Extract selected modules from the event
          final List<String> selectedModules = event.module.split(',');
          // Clone the existing module status list
          List<ModuleStatus> ss = List.from(value.moduleStatus!);
          // Create a copy of selected modules list
          List<String> loopingData = List.from(selectedModules);
          // Update module status based on locale

          for (final selectedModule in loopingData) {
            final index =
                ss.indexWhere((element) => element.value == selectedModule);
            if (index != -1) {
              if (ss[index].status[event.locale] == true) {
                selectedModules.remove(selectedModule);
              } else {
                final Map<String, bool> updatedStatus = Map.from(
                    ss[index].status); // Make a copy of the existing status map
                updatedStatus[event.locale] = true;

                final data = ModuleStatus(
                  label: value.moduleStatus![index].label,
                  value: value.moduleStatus![index].value,
                  status: updatedStatus,
                );
                ss.removeAt(index);
                ss.insert(index, data);
              }
            }
          }

          // Access Hive box for localizations
          final box = Hive.box<KeyLocaleModel>('keyValueModel');

          // Fetch localization data from remote API for selected modules
          if (selectedModules.isNotEmpty) {
            LocalizationModel result = await localizationRepository.search(
              url: Urls.initServices.localizationSearch,
              queryParameters: {
                "module": selectedModules.join(",").toString(),
                "locale": event.locale,
                "tenantId": event.tenantId,
              },
            );
            // Update Hive box with fetched localization data

            final List<Localization> newLocalizationList = result.messages
                .map((e) => Localization()
                  ..message = e.message
                  ..code = e.code
                  ..locale = e.locale
                  ..module = e.module)
                .toList();
            final List<KeyLocaleModel> localizationList = box.values.toList();
            final ll = localizationList
                .firstWhereOrNull((element) => element.locale == event.locale);

            if (ll == null) {
              KeyLocaleModel keyValueModel = KeyLocaleModel()
                ..locale = event.locale
                ..localizationsList = newLocalizationList;
              await box.add(keyValueModel);
            } else {
              // Add Odia localizations to box

              for (int i = 0; i < localizationList.length; i++) {
                if (localizationList[i].locale == event.locale) {
                  // Update the desired fields of the object

                  localizationList[i]
                      .localizationsList!
                      .addAll(newLocalizationList);

                  // Put the updated list back into the Hive box
                  box.putAll({for (var obj in localizationList) obj.key: obj});

                  break; // Exit the loop since we found and updated the object
                }
              }
            }

            final List codes = event.locale.split('_');
            await _loadLocale(codes, event.locale);

            emit(value.copyWith(moduleStatus: ss, languages: configLanguage));
          } else {
            // If no modules are selected, emit updated state without fetching new data
            final List codes = event.locale.split('_');
            await _loadLocale(codes, event.locale);

            emit(value.copyWith(moduleStatus: ss, languages: configLanguage));
          }
        },
      );
    } on DioException catch (e) {
      // Handle Dio errors and emit error state
      LocalizationState.error(e.response?.data['Errors'][0]['code']);
    }
  }

  FutureOr<void> _loadLocale(List codes, String locale) async {
    await AppLocalizations(Locale(codes.first, codes.last))
        .load(locale: "${codes.first}_${codes.last}");
  }
}

@freezed
class LocalizationEvent with _$LocalizationEvent {
  const factory LocalizationEvent.onLoadLocalization({
    required String module,
    required String tenantId,
    required String locale,
    final List<Languages>? languages,
    final List<LocalizationModules>? localizationModules,
  }) = OnLoadLocalizationEvent;

  const factory LocalizationEvent.onSpecificLoadLocalization({
    required String module,
    required String tenantId,
    required String locale,
  }) = OnSpecificLoadLocalizationEvent;
}

@freezed
class LocalizationState with _$LocalizationState {
  const LocalizationState._();
  const factory LocalizationState.initial() = _Initial;
  const factory LocalizationState.loading() = _Loading;
  factory LocalizationState.loaded(
    List<Languages>? languages,
    List<ModuleStatus>? moduleStatus,
  ) = _Loaded;
  const factory LocalizationState.error(String? error) = _Error;
}