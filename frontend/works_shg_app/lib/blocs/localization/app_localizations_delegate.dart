import 'package:flutter/material.dart';

import 'app_localization.dart';

class AppLocalizationsDelegate extends LocalizationsDelegate<AppLocalizations> {
  const AppLocalizationsDelegate();
  @override
  bool isSupported(Locale locale) {
    return ['en', 'hi', 'pn'].contains(locale.languageCode);
  }

  @override
  Future<AppLocalizations> load(
    Locale locale,
  ) async {
    AppLocalizations appLocalizations = AppLocalizations(locale);
    await appLocalizations.load();

    return appLocalizations;
  }

  @override
  bool shouldReload(covariant LocalizationsDelegate<AppLocalizations> old) {
    return false;
  }
}
