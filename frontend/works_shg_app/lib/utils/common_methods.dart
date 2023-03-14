import 'dart:math';

import 'package:flutter/foundation.dart';
import 'package:http_parser/http_parser.dart';
import 'package:mime/mime.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/services/local_storage.dart';

import 'global_variables.dart';

class CommonMethods {
  Future<void> deleteLocalStorageKey() async {
    if (kIsWeb) {
      html.window.localStorage.remove(GlobalVariables.selectedLocale());
    } else {
      await storage.delete(key: GlobalVariables.selectedLocale().toString());
    }
  }

  MediaType getMediaType(String? path) {
    if (path == null) return MediaType('', '');
    String? mimeStr = lookupMimeType(path);
    var fileType = mimeStr?.split('/');
    if (fileType != null && fileType.isNotEmpty) {
      return MediaType(fileType.first, fileType.last);
    } else {
      return MediaType('', '');
    }
  }

  static Future<bool> isValidFileSize(int fileLength) async {
    var flag = true;
    if (fileLength > 5000000) {
      flag = false;
    }
    return flag;
  }

  static String getRandomName() {
    return '${GlobalVariables.userRequestModel!['id']}${Random().nextInt(3)}';
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
