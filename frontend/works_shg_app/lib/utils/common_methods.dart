import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:http_parser/http_parser.dart';
import 'package:mime/mime.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/services/local_storage.dart';

import '../data/repositories/core_repo/core_repository.dart';
import '../models/file_store/file_store_model.dart';
import 'global_variables.dart';

class CommonMethods {
  Future<void> deleteLocalStorageKey() async {
    if (kIsWeb) {
      html.window.localStorage.remove(GlobalVariables.selectedLocale());
    } else {
      await storage.delete(key: GlobalVariables.selectedLocale().toString());
    }
  }

  static String getExtension(String url) {
    return url.substring(0, url.indexOf('?')).split('/').last;
  }

  void onTapOfAttachment(
      FileStoreModel store, String tenantId, BuildContext context) async {
    var random = Random();
    List<FileStoreModel>? file = await CoreRepository().fetchFiles(
        [store.fileStoreId.toString()],
        GlobalVariables.organisationListModel!.organisations!.first.tenantId
            .toString());
    var fileName = CommonMethods.getExtension(file!.first.url.toString());
    CoreRepository().fileDownload(file.first.url.toString(),
        '${random.nextInt(200)}${random.nextInt(100)}$fileName');
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

  static getConvertedLocalizedCode(String type, {String subString = ''}) {
    switch (type) {
      case 'city':
        return GlobalVariables
            .organisationListModel!.organisations!.first.tenantId
            .toString()
            .toUpperCase()
            .replaceAll('.', '_');

      case 'ward':
      case 'locality':
        return '${GlobalVariables.organisationListModel!.organisations!.first.tenantId.toString().toUpperCase().replaceAll('.', '_')}_ADMIN_${subString.toUpperCase()}';
    }
  }

  static String getLocaleModules() {
    return 'rainmaker-common,rainmaker-common-masters,rainmaker-attendencemgmt,rainmaker-${GlobalVariables.organisationListModel!.organisations!.first.tenantId.toString()},rainmaker-${GlobalVariables.stateInfoListModel!.code.toString()}';
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
