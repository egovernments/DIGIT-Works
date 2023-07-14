import 'dart:io';
import 'dart:math';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_training/services/local_storage.dart';
import 'package:http_parser/http_parser.dart';
import 'package:mime/mime.dart';
import 'package:package_info_plus/package_info_plus.dart';
import 'package:universal_html/html.dart' as html;
import 'package:url_launcher/url_launcher.dart';

import '../data/repositories/core_repo/core_repository.dart';
import '../models/file_store/file_store_model.dart';
import 'global_variables.dart';

class CommonMethods {
  Future<void> deleteLocalStorageKey() async {
    if (kIsWeb) {
      html.window.sessionStorage.remove(GlobalVariables.selectedLocale());
    } else {
      await storage.delete(key: GlobalVariables.selectedLocale().toString());
    }
  }

  static String getExtension(String url) {
    return url.substring(0, url.indexOf('?')).split('/').last;
  }

  static Future<void> fetchPackageInfo() async {
    try {
      if (kIsWeb) {
        html.window.sessionStorage.clear();
      } else {
        await storage.deleteAll();
      }
      packageInfo = await PackageInfo.fromPlatform();
    } catch (e, s) {
      print(e);
    }
  }

  void checkVersion(BuildContext context, String? packageName, String? iOSId,
      String? latestAppVersion) async {
    try {
      if (latestAppVersion != null && !kIsWeb) {
        if (int.parse(packageInfo!.version.split('.').join("").toString()) <
            int.parse(latestAppVersion.split('.').join("").toString())) {
          late Uri uri;

          if (Platform.isAndroid) {
            uri = Uri.https(
                "play.google.com", "/store/apps/details", {"id": packageName});
          } else {
            uri = Uri.https("apps.apple.com", "/in/app/mgramseva/id$iOSId");
          }

          showDialog(
              context: context,
              barrierDismissible: false,
              builder: (BuildContext context) {
                return WillPopScope(
                    child: AlertDialog(
                      title: const Text('UPDATE AVAILABLE'),
                      content: Text(
                          'Please update the app from ${packageInfo?.version} to $latestAppVersion'),
                      actions: [
                        TextButton(
                            onPressed: () => launchPlayStore(uri, context),
                            child: const Text('Update'))
                      ],
                    ),
                    onWillPop: () async {
                      if (Platform.isAndroid) {
                        SystemNavigator.pop();
                      } else if (Platform.isIOS) {
                        exit(0);
                      }
                      return true;
                    });
              });
        }
      }
    } catch (e) {}
  }

  void launchPlayStore(Uri appLink, BuildContext context) async {
    try {
      if (await canLaunchUrl(appLink)) {
        await launchUrl(appLink);
      } else {
        throw 'Could not launch appStoreLink';
      }
    } catch (e) {
      Navigator.pop(context);
    }
  }

  bool containsOnlyLetters(String str) {
    final regex = RegExp(r'^[a-zA-Z]+$');
    return regex.hasMatch(str);
  }

  bool containsOnlyNumbers(String str) {
    final regex = RegExp(r'^[0-9]+$');
    return regex.hasMatch(str);
  }

  void onTapOfAttachment(
      FileStoreModel store, String tenantId, BuildContext context) async {
    var random = Random();
    List<FileStoreModel>? file = await CoreRepository().fetchFiles(
        [store.fileStoreId.toString()],
        GlobalVariables.userInfo!.tenantId.toString());
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

  static bool validateImageFileExtension(
      String fileExtension, List<String> supportedExtensions) {
    // Define the list of supported image file extensions
    // List<String> supportedExtensions = ['png', 'jpg', 'jpeg'];
    // Check if the file extension is in the list of supported extensions

    return supportedExtensions.contains(fileExtension);
  }

  static String getRandomName() {
    return '${GlobalVariables.userRequestModel!['id']}${Random().nextInt(3)}';
  }

  static getConvertedLocalizedCode(String type, {String subString = ''}) {
    switch (type) {
      case 'city':
        return GlobalVariables.userInfo!.tenantId
            .toString()
            .toUpperCase()
            .replaceAll('.', '_');

      case 'ward':
      case 'locality':
        return '${GlobalVariables.userInfo!.tenantId.toString().toUpperCase().replaceAll('.', '_')}_ADMIN_${subString.toUpperCase()}';
    }
  }

  static String getLocaleModules() {
    return 'rainmaker-common,rainmaker-bnd';
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
