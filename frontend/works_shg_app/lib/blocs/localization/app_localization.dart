import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/utils/global_variables.dart';

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

  List<LocalizationMessageModel>? getLocalizationLabels() {
    dynamic localLabelResponse;
    if (kIsWeb) {
      localLabelResponse =
          html.window.localStorage[GlobalVariables.selectedLocale() ?? ''];
    } else {
      localLabelResponse =
          storage.read(key: GlobalVariables.selectedLocale() ?? '');
    }

    if (localLabelResponse != null && localLabelResponse.trim().isNotEmpty) {
      return localizedStrings = jsonDecode(localLabelResponse)
          .map<LocalizationMessageModel>(
              (e) => LocalizationMessageModel.fromJson(e))
          .toList();
    } else {
      localizedStrings = BlocProvider.of<LocalizationBloc>(
                  scaffoldMessengerKey.currentContext!)
              .state
              .localization ??
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
