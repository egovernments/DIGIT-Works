import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:universal_html/html.dart' as html;
import 'package:flutter_training/blocs/localization/localization.dart';

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

  Future<List<LocalizationMessageModel>?> getLocalizationLabels() async {
    dynamic localLabelResponse;
    if (kIsWeb) {
      localLabelResponse = html.window.sessionStorage[
          '${locale?.languageCode}_${locale?.countryCode}' ?? ''];
    } else {
      localLabelResponse = await storage.read(
          key: '${locale?.languageCode}_${locale?.countryCode}');
    }
    await Future.delayed(const Duration(seconds: 1));
    if (localLabelResponse != null && localLabelResponse.trim().isNotEmpty) {
      return localizedStrings = jsonDecode(localLabelResponse)
          .map<LocalizationMessageModel>(
              (e) => LocalizationMessageModel.fromJson(e))
          .toList();
    } else {
      localizedStrings = BlocProvider.of<LocalizationBloc>(
                  scaffoldMessengerKey.currentContext!)
              .state
              .maybeWhen(
                  orElse: () => [],
                  loaded: (List<LocalizationMessageModel>? localization) {
                    return localization;
                  }) ??
          [];

      return localizedStrings;
    }
  }

  Future<bool> load() async {
    if (scaffoldMessengerKey.currentContext != null) {
      await getLocalizationLabels();
      return true;
    } else {
      return false;
    }
  }

  translate(
    String localizedValues,
  ) {
    var index =
        localizedStrings.indexWhere((medium) => medium.code == localizedValues);
    return index != -1 ? localizedStrings[index].message : localizedValues;
  }
}
