import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:universal_html/html.dart' as html;

import '../../models/localization/localization_model.dart';
import '../../services/local_storage.dart';
import '../../utils/constants.dart';
import 'app_localizations_delegate.dart';

class AppLocalizations {
  final Locale? locale;

  AppLocalizations(this.locale);
  static AppLocalizations of(BuildContext context) {
    return Localizations.of<AppLocalizations>(context, AppLocalizations)!;
  }

  static List<LocalizationMessageModel> localizedStrings =
      <LocalizationMessageModel>[];
  static const LocalizationsDelegate<AppLocalizations> delegate =
      AppLocalizationsDelegate();

  Future<List<LocalizationMessageModel>> getLocalizationLabels() async {
    print(locale);
    dynamic localLabelResponse;
    if (kIsWeb) {
      localLabelResponse = html.window.localStorage[locale!.languageCode ?? ''];
    } else {
      localLabelResponse = await storage.read(key: locale!.languageCode ?? '');
    }

    if (localLabelResponse != null && localLabelResponse.trim().isNotEmpty) {
      return localizedStrings = jsonDecode(localLabelResponse)
          .map<LocalizationMessageModel>(
              (e) => LocalizationMessageModel.fromJson(e))
          .toList();
    }
    return localizedStrings;
  }

  Future<bool> load() async {
    await getLocalizationLabels();

    if (scaffoldMessengerKey.currentContext == null) {
      return false;
    }
    return true;
  }

  translate(
    String localizedValues,
  ) {
    var index =
        localizedStrings.indexWhere((medium) => medium.code == localizedValues);
    return index != -1 ? localizedStrings[index].message : localizedValues;
  }
}
