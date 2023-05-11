import 'dart:async';
import 'dart:convert';
import 'dart:ui';

import 'package:collection/collection.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/models/init_mdms/init_mdms_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/repositories/remote/getGlobalConfig_repo.dart';
import '../../data/repositories/remote/mdms.dart';
import '../../models/init_mdms/global_config_model.dart';
import '../../services/local_storage.dart';
import '../localization/app_localization.dart';

part 'app_initilization.freezed.dart';

typedef AppInitializationEmitter = Emitter<AppInitializationState>;
InitMdmsModel initMdmsModelData = const InitMdmsModel();
StateInfoListModel stateInfoListModel = const StateInfoListModel();
List<DigitRowCardModel>? digitRowCardItems;

class AppInitializationBloc
    extends Bloc<AppInitializationEvent, AppInitializationState> {
  final MdmsRepository mdmsRepository;
  AppInitializationBloc(super.initialState, this.mdmsRepository) {
    on<AppInitializationSetupEvent>(_onAppInitializeSetup);
  }

  FutureOr<void> _onAppInitializeSetup(
    AppInitializationSetupEvent event,
    AppInitializationEmitter emit,
  ) async {
    if (GlobalVariables.globalConfigObject == null ||
        GlobalVariables.stateInfoListModel == null) {
      GlobalConfigModel globalConfigModel =
          await GetGlobalConfig().getGlobalConfig();

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
          ]);
      GlobalVariables.globalConfigObject = globalConfigModel;
      GlobalVariables.stateInfoListModel =
          result.commonMastersModel!.stateInfoListModel!.first;
      StateInfoListModel ss =
          result.commonMastersModel!.stateInfoListModel!.first.copyWith(
              languages: [
        ...result.commonMastersModel!.stateInfoListModel![0].languages!
            .mapIndexed((i, element) {
          if (element.value == event.selectedLang) {
            return element.copyWith(isSelected: true);
          } else {
            return element;
          }
        })
      ].toList());
      if (kIsWeb) {
        html.window.localStorage['initData'] = jsonEncode(result.toJson());
        html.window.localStorage['StateInfo'] = jsonEncode(ss);
        html.window.localStorage['languages'] = jsonEncode(ss.languages);
        html.window.localStorage['tenantId'] = jsonEncode(ss.code);
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
        localInitData = html.window.localStorage['initData'];
        localStateData = html.window.localStorage['StateInfo'];
        localLanguageData = html.window.localStorage['languages'];
      } else {
        localInitData = await storage.read(key: 'initData');
        localStateData = await storage.read(key: 'StateInfo');
        localLanguageData = await storage.read(key: 'languages');
      }

      if (localInitData != null &&
          localInitData.trim().isNotEmpty &&
          localStateData != null &&
          localLanguageData != null) {
        initMdmsModelData = InitMdmsModel.fromJson(jsonDecode(localInitData));
        stateInfoListModel =
            StateInfoListModel.fromJson(jsonDecode(localStateData));
        digitRowCardItems = jsonDecode(localLanguageData)
            .map<DigitRowCardModel>((e) => DigitRowCardModel.fromJson(e))
            .toList();
      }
    } else {
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
      if (kIsWeb) {
        html.window.localStorage['languages'] = jsonEncode(ss.languages);
      } else {
        await storage.write(key: 'languages', value: jsonEncode(ss.languages));
      }
      dynamic localInitData;
      dynamic localStateData;
      dynamic localLanguageData;
      if (kIsWeb) {
        localInitData = html.window.localStorage['initData'];
        localStateData = html.window.localStorage['StateInfo'];
        localLanguageData = html.window.localStorage['languages'];
      } else {
        localInitData = await storage.read(key: 'initData');
        localStateData = await storage.read(key: 'StateInfo');
        localLanguageData = await storage.read(key: 'languages');
      }

      if (localInitData != null &&
          localInitData.trim().isNotEmpty &&
          localStateData != null &&
          localLanguageData != null) {
        initMdmsModelData = InitMdmsModel.fromJson(jsonDecode(localInitData));
        stateInfoListModel =
            StateInfoListModel.fromJson(jsonDecode(localStateData));
        digitRowCardItems = jsonDecode(localLanguageData)
            .map<DigitRowCardModel>((e) => DigitRowCardModel.fromJson(e))
            .toList();
      }
    }
    await AppLocalizations(
      Locale(
          digitRowCardItems!
              .firstWhere((e) => e.isSelected)
              .value
              .split('_')
              .first,
          digitRowCardItems!
              .firstWhere((e) => e.isSelected)
              .value
              .split('_')
              .last),
    ).load();

    emit(state.copyWith(
        isInitializationCompleted: true,
        initMdmsModel: initMdmsModelData,
        stateInfoListModel: stateInfoListModel,
        digitRowCardItems: digitRowCardItems));
    await AppLocalizations(
      Locale(
          digitRowCardItems!
              .firstWhere((e) => e.isSelected)
              .value
              .split('_')
              .first,
          digitRowCardItems!
              .firstWhere((e) => e.isSelected)
              .value
              .split('_')
              .last),
    ).load();
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
