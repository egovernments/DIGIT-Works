import 'dart:convert';

import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_training/blocs/localization/selected_localization_model.dart';
import 'package:flutter_training/models/app_config/app_config_model.dart';
import 'package:flutter_training/models/init_mdms/global_config_model.dart';
import 'package:package_info_plus/package_info_plus.dart';
import 'package:universal_html/html.dart' as html;

import '../models/init_mdms/init_mdms_model.dart';
import '../models/user_details/user_details_model.dart';
import '../services/local_storage.dart';

class GlobalVariables {
  static dynamic getUserInfo() async {
    if (kIsWeb) {
      return jsonDecode(html.window.sessionStorage['userRequest'].toString());
    } else {
      var userReq = await storage.read(key: 'userRequest');
      return jsonDecode(userReq.toString());
    }
  }

  static Future<String?> getAuthToken() async {
    dynamic accessToken;
    if (kIsWeb) {
      accessToken = html.window.sessionStorage['accessToken'];
      return jsonDecode(accessToken.toString());
    } else {
      accessToken = await storage.read(key: 'accessToken');
      return jsonDecode(accessToken.toString());
    }
  }

  static dynamic getLanguages() async {
    if (kIsWeb) {
      return jsonDecode(html.window.sessionStorage['languages'].toString())
          .map<DigitRowCardModel>((e) => DigitRowCardModel.fromJson(e))
          .toList();
    } else {
      var localLanguage = await storage.read(key: 'languages');
      return jsonDecode(localLanguage ?? '')
          .map<DigitRowCardModel>((e) => DigitRowCardModel.fromJson(e))
          .toList();
    }
  }

  static Future<bool> isLocaleSelect(String locale, String module) async {
    List<LocalizationLabel>? messages;
    List<String> modules = module.contains(',')
        ? module.split(',').map((m) => m.trim()).toList()
        : [module];
    if (kIsWeb) {
      messages = html.window.sessionStorage.keys.contains(locale)
          ? jsonDecode(html.window.sessionStorage[locale].toString())
              .map<LocalizationLabel>((e) => LocalizationLabel.fromJson(e))
              .toList()
          : [];
      return modules.every((module) {
        return messages!.any((message) {
          return message.module == module;
        });
      });
    } else {
      if (await storage.containsKey(key: locale)) {
        var localMessages = await storage.read(key: locale);
        messages = jsonDecode(localMessages.toString())
            .map<LocalizationLabel>((e) => LocalizationLabel.fromJson(e))
            .toList();
      } else {
        messages = [];
      }
      return modules.every((module) {
        return messages!.any((message) {
          return message.module == module;
        });
      });
    }
  }

  static Future<String> selectedLocale() async {
    List<Languages>? languagesList;
    if (kIsWeb) {
      languagesList =
          jsonDecode(html.window.sessionStorage['languages'].toString())
              .map<Languages>((e) => Languages.fromJson(e))
              .toList();
      return languagesList!.where((elem) => elem.isSelected).first.value;
    } else {
      var langStorage = await storage.read(key: 'languages');
      languagesList = jsonDecode(langStorage.toString())
          .map<Languages>((e) => Languages.fromJson(e))
          .toList();

      return languagesList!.where((elem) => elem.isSelected).first.value;
    }
  }

  static GlobalConfigModel? globalConfigObject;
  static StateInfoListModel? stateInfoListModel;
  static String? uuid;
  static String? authToken;
  static Map<String, dynamic>? userRequestModel;
  static UserRequestModel? userInfo;
  static Map<String, String> downloadUrl = {};
}

PackageInfo? packageInfo;
