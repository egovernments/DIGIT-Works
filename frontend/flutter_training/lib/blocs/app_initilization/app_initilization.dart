import 'dart:async';
import 'dart:convert';
import 'dart:ui';

import 'package:collection/collection.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/models/init_mdms/init_mdms_model.dart';
import 'package:flutter_training/services/urls.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;

import '../../Env/env_config.dart';
import '../../data/repositories/remote/mdms.dart';
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
    if (GlobalVariables.stateInfoListModel == null) {
      InitMdmsModel result = await mdmsRepository.initMdmsRegistry(
          apiEndPoint: Urls.initServices.mdms,
          tenantId: envConfig.variables.tenantId,
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
      } else {}

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
        html.window.sessionStorage['languages'] = jsonEncode(ss.languages);
      } else {
        await storage.write(key: 'languages', value: jsonEncode(ss.languages));
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
      } else {}

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
