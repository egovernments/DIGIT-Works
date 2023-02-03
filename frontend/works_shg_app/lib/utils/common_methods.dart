import 'package:flutter/foundation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/services/local_storage.dart';

import 'global_variables.dart';

class CommonMethods {
  void deleteLocalStorageKey() {
    if (kIsWeb) {
      html.window.localStorage.remove(GlobalVariables.selectedLocale());
    } else {
      storage.delete(key: GlobalVariables.selectedLocale());
    }
  }

  static DateTime firstDayOfWeek(DateTime date) {
    int currentDay = date.weekday;

    DateTime firstDayOfWeek = date.subtract(Duration(days: currentDay - 1));
    // For Sunday as firstDay date.subtract(Duration(days: currentDay)
    // For Monday as firstDay date.subtract(Duration(days: currentDay - 1)
    // For Tuesday as firstDay date.subtract(Duration(days: currentDay - 2)
    // ......so on
    return firstDayOfWeek;
  }

  static DateTime endDayOfWeek(DateTime date) {
    int currentDay = date.weekday;
    DateTime endDayOfWeek =
        date.add(Duration(days: DateTime.daysPerWeek - currentDay - 1));
    // For Saturday as endDay date.add(Duration(days: DateTime.daysPerWeek - currentDay));
    // For Friday as endDay date.add(Duration(days: DateTime.daysPerWeek - currentDay - 1));
    // For Sunday as endDay date.add(Duration(days: DateTime.daysPerWeek - currentDay + 1));
    return endDayOfWeek;
  }
}
