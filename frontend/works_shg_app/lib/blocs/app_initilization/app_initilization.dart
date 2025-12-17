import 'dart:async';
import 'dart:convert';

import 'package:collection/collection.dart';
// import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:digit_ui_components/widgets/molecules/language_selection_card.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/models/init_mdms/init_mdms_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/repositories/remote/get_global_config_repo.dart';
import '../../data/repositories/remote/mdms.dart';
import '../../models/init_mdms/global_config_model.dart';
import '../../services/local_storage.dart';
import '../localization/localization.dart';

part 'app_initilization.freezed.dart';

typedef AppInitializationEmitter = Emitter<AppInitializationState>;
InitMdmsModel initMdmsModelData = const InitMdmsModel();
StateInfoListModel stateInfoListModel = const StateInfoListModel();
List<DigitRowCardModel>? digitRowCardItems;

class AppInitializationBloc
    extends Bloc<AppInitializationEvent, AppInitializationState> {
  final LocalizationBloc localizationBloc;
  final MdmsRepository mdmsRepository;
  AppInitializationBloc(
      super.initialState, this.mdmsRepository, this.localizationBloc) {
    on<AppInitializationSetupEvent>(_onAppInitializeSetup);
  }

  // Event handler for initializing the application
  FutureOr<void> _onAppInitializeSetup(
    AppInitializationSetupEvent event,
    AppInitializationEmitter emit,
  ) async {
    // Check if global configuration and state info are not yet fetched

    if (GlobalVariables.globalConfigObject == null ||
        GlobalVariables.stateInfoListModel == null) {
      GlobalConfigModel globalConfigModel =
          await GetGlobalConfig().getGlobalConfig();

      // Initialize MDMS registry
      InitMdmsModel result = await mdmsRepository.initMdmsRegistry(
          apiEndPoint: Urls.initServices.mdms,
          tenantId: globalConfigModel.globalConfigs!.stateTenantId.toString(),
          moduleDetails: [
            {
              "moduleName": "common-masters",
              "masterDetails": [
                {
                  "name": "StateInfo",
                },
              ],
            },
            {
              "moduleName": "tenant",
              "masterDetails": [
                {
                  "name": "tenants",
                },
                {
                  "name": "citymodule",
                }
              ],
            },

            {
              "moduleName": "commonUiConfig",
              "masterDetails": [
                {
                  "name": "PrivacyPolicy",
                },
                
              ],
            },
          ]);
      GlobalVariables.globalConfigObject = globalConfigModel;
      GlobalVariables.stateInfoListModel =
          result.commonMastersModel!.stateInfoListModel!.first;
      StateInfoListModel ss =
          result.commonMastersModel!.stateInfoListModel!.first.copyWith(
              languages: [
        ...result.commonMastersModel!.stateInfoListModel![0].languages!
            .mapIndexed((i, element) {
          if (element.value ==
              result.commonMastersModel!.stateInfoListModel!.first.languages!
                  .first.value) {
            return element.copyWith(isSelected: true);
          } else {
            return element;
          }
        })
      ].toList());

      // Store fetched data locally
      // Session storage for web, local storage for other platforms
      if (kIsWeb) {
        html.window.sessionStorage['initData'] = jsonEncode(result.toJson());
        html.window.sessionStorage['StateInfo'] = jsonEncode(ss);
        html.window.sessionStorage['languages'] = jsonEncode(ss.languages);
        html.window.sessionStorage['tenantId'] = jsonEncode(ss.code);
      } else {
        await storage.write(
            key: 'initData', value: jsonEncode(result.toJson()));
        await storage.write(key: 'StateInfo', value: jsonEncode(ss.toJson()));
        await storage.write(key: 'languages', value: jsonEncode(ss.languages));
        await storage.write(key: 'tenantId', value: jsonEncode(ss.code));
      }

      dynamic localInitData;
      dynamic localStateData;
      dynamic localLanguageData;
      if (kIsWeb) {
        localInitData = html.window.sessionStorage['initData'];
        localStateData = html.window.sessionStorage['StateInfo'];
        localLanguageData = html.window.sessionStorage['languages'];
      } else {
        localInitData = await storage.read(key: 'initData');
        localStateData = await storage.read(key: 'StateInfo');
        localLanguageData = await storage.read(key: 'languages');
      }

      // Update local variables with fetched data
      // Initialize digitRowCardItems with languages

      if (localInitData != null &&
          localInitData.trim().isNotEmpty &&
          localStateData != null &&
          localLanguageData != null) {
        initMdmsModelData = InitMdmsModel.fromJson(jsonDecode(localInitData));
        stateInfoListModel =
            StateInfoListModel.fromJson(jsonDecode(localStateData));
        digitRowCardItems = jsonDecode(localLanguageData)
            .map<DigitRowCardModel>((e) => DigitRowCardModel(label: e['label'].toString(), value: e['value'].toString(),isSelected: e['isSelected']))
            .toList();
      } else {}

      // Dispatch LocalizationEvent for loading localization data
      localizationBloc.add(
        LocalizationEvent.onLoadLocalization(
          module:
              'rainmaker-common,rainmaker-common-masters,rainmaker-${stateInfoListModel.code}',
          tenantId: initMdmsModelData
              .commonMastersModel!.stateInfoListModel!.first.code
              .toString(),
          locale: digitRowCardItems!.firstWhere((e) => e.isSelected).value,
          languages: ss.languages,
          localizationModules: ss.localizationModules,
        ),
      );
      // Emit updated state
      emit(
        state.copyWith(
          isInitializationCompleted: true,
          initMdmsModel: initMdmsModelData,
          stateInfoListModel: stateInfoListModel,
          digitRowCardItems: digitRowCardItems,
        ),
      );
    } else {
      // State info is already fetched, update language selection
      StateInfoListModel ss = GlobalVariables.stateInfoListModel!.copyWith(
          languages: [
        ...GlobalVariables.stateInfoListModel!.languages!
            .mapIndexed((i, element) {
          if (element.value == event.selectedLang) {
            return element.copyWith(isSelected: true);
          } else {
            return element;
          }
        })
      ].toList());

      // Store updated language selection
      if (kIsWeb) {
        html.window.sessionStorage['languages'] = jsonEncode(ss.languages);
      } else {
        await storage.write(key: 'languages', value: jsonEncode(ss.languages));
      }

      // Fetch locally stored data
      dynamic localInitData;
      dynamic localStateData;
      dynamic localLanguageData;
      if (kIsWeb) {
        localInitData = html.window.sessionStorage['initData'];
        localStateData = html.window.sessionStorage['StateInfo'];
        localLanguageData = html.window.sessionStorage['languages'];
      } else {
        localInitData = await storage.read(key: 'initData');
        localStateData = await storage.read(key: 'StateInfo');
        localLanguageData = await storage.read(key: 'languages');
      }

      if (localInitData != null &&
          localInitData.trim().isNotEmpty &&
          localStateData != null &&
          localLanguageData != null) {
        // Update local variables with fetched data
        initMdmsModelData = InitMdmsModel.fromJson(jsonDecode(localInitData));
        stateInfoListModel =
            StateInfoListModel.fromJson(jsonDecode(localStateData));
        digitRowCardItems = jsonDecode(localLanguageData)
            .map<DigitRowCardModel>((e) => DigitRowCardModel(label: e['label'], value: e['value'],isSelected: e['isSelected']))
            .toList();
      } else {}

      // Dispatch LocalizationEvent for loading localization data
      localizationBloc.add(
        LocalizationEvent.onLoadLocalization(
          module:
              'rainmaker-common,rainmaker-common-masters,rainmaker-${stateInfoListModel.code}',
          tenantId: initMdmsModelData
              .commonMastersModel!.stateInfoListModel!.first.code
              .toString(),
          locale: digitRowCardItems!.firstWhere((e) => e.isSelected).value,
          languages: ss.languages,
          localizationModules: ss.localizationModules,
        ),
      );
      // Emit updated state
      emit(
        state.copyWith(
          isInitializationCompleted: true,
          initMdmsModel: initMdmsModelData,
          stateInfoListModel: stateInfoListModel,
          digitRowCardItems: digitRowCardItems,
        ),
      );
    }
  }
}

@freezed
class AppInitializationEvent with _$AppInitializationEvent {
  const factory AppInitializationEvent.onapplicationInitializeSetup(
      {required String selectedLang}) = AppInitializationSetupEvent;
}

@freezed
class AppInitializationState with _$AppInitializationState {
  const AppInitializationState._();

  const factory AppInitializationState(
      {@Default(false) bool isInitializationCompleted,
      InitMdmsModel? initMdmsModel,
      StateInfoListModel? stateInfoListModel,
      List<DigitRowCardModel>? digitRowCardItems}) = _AppInitializationState;
}
