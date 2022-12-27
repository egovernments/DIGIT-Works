import 'package:flutter/material.dart';

import '../../models/localization/localization_model.dart';
import 'app_localizations_delegate.dart';

class AppLocalizations {
  final Locale? locale;

  AppLocalizations(this.locale);
  static AppLocalizations of(BuildContext context) {
    return Localizations.of<AppLocalizations>(context, AppLocalizations)!;
  }

  List<LocalizationMessageModel> _localizedStrings =
      <LocalizationMessageModel>[];
  static const LocalizationsDelegate<AppLocalizations> delegate =
      AppLocalizationsDelegate();

  Future<bool> load() async {
    // Read the Data from Local DB
    _localizedStrings = [];

    return true;
  }

  String translate(
    String localizedValues,
  ) {
    var index = _localizedStrings
        .indexWhere((medium) => medium.code == localizedValues);

    return index != -1 ? _localizedStrings[index].message : localizedValues;
  }
}
