import 'dart:io';
import 'dart:math';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:http_parser/http_parser.dart';
import 'package:mime/mime.dart';
import 'package:package_info_plus/package_info_plus.dart';
import 'package:root_checker_plus/root_checker_plus.dart';
import 'package:universal_html/html.dart' as html;
import 'package:url_launcher/url_launcher.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/data/schema/localization.dart';
import 'package:works_shg_app/models/attendance/individual_list_model.dart';
import 'package:works_shg_app/models/error/wager_seeker_attendance_error_model.dart';
import 'package:works_shg_app/models/muster_rolls/estimate_muster_roll_model.dart';
import 'package:works_shg_app/services/local_storage.dart';

import '../data/repositories/core_repo/core_repository.dart';
import '../models/file_store/file_store_model.dart';
import 'global_variables.dart';

class CommonMethods {
  Future<void> deleteLocalStorageKey() async {
    if (kIsWeb) {
      html.window.sessionStorage.remove(await GlobalVariables.selectedLocale());
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
    } catch (e) {
      print(e);
    }
  }

  void checkVersion(BuildContext context, String? packageName, String? iOSId,
      String? latestAppVersion) async {
    try {
      var rootedCheck = (await RootCheckerPlus.isRootChecker()) ?? false;
      var devMode = (await RootCheckerPlus.isDeveloperMode()) ?? false;
      if (Platform.isAndroid && ((rootedCheck) || (devMode))) {
        showDialog(
            context: context,
            barrierDismissible: false,
            builder: (BuildContext context) {
              return PopScope(
                onPopInvoked: (val) async {
                  if (Platform.isAndroid) {
                    SystemNavigator.pop();
                  } else if (Platform.isIOS) {
                    exit(0);
                  }
                  //return true;
                },
                canPop: true,
                child: AlertDialog(
                  title: const Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Icon(
                        Icons.warning_rounded,
                        color: Colors.red,
                      ),
                      Text(
                        'UNSUPPORTED DEVICE!',
                        style: TextStyle(
                            fontSize: 16, fontWeight: FontWeight.w700),
                      ),
                    ],
                  ),
                  content: Text(
                    '${(rootedCheck) ? 'Application can not be run on a rooted device' : 'Please disable developer mode of your device to run the application'} ',
                  ),
                ),
              );
            });
      } else if (latestAppVersion != null && !kIsWeb) {
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
                return PopScope(
                  onPopInvoked: (val) async {
                    if (Platform.isAndroid) {
                      SystemNavigator.pop();
                    } else if (Platform.isIOS) {
                      exit(0);
                    }
                    //return true;
                  },
                  canPop: true,
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
                );
              });
        }
      }
    } catch (e) {
      print(e);
    }
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
      FileStoreModel store, String tenantId, BuildContext context,
      {RoleType roleType = RoleType.cbo}) async {
    var random = Random();
    List<FileStoreModel>? file = await CoreRepository().fetchFiles(
      [store.fileStoreId.toString()],
      roleType == RoleType.cbo
          ? GlobalVariables.organisationListModel!.organisations!.first.tenantId
              .toString()
          : tenantId,
    );
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
        return GlobalVariables.tenantId ??
            GlobalVariables.organisationListModel!.organisations!.first.tenantId
                .toString()
                .toUpperCase()
                .replaceAll('.', '_');

      case 'ward':
      case 'locality':
        return '${GlobalVariables.tenantId != null ? GlobalVariables.tenantId.toString().toUpperCase().replaceAll('.', '_') : GlobalVariables.organisationListModel!.organisations!.first.tenantId.toString().toUpperCase().replaceAll('.', '_')}_ADMIN_${subString.toUpperCase()}';
    }
  }

  static String getLocaleModules() {
    return GlobalVariables.roleType == RoleType.cbo
        ? 'rainmaker-common,rainmaker-common-masters,rainmaker-contracts,rainmaker-expenditure,rainmaker-workflow,rainmaker-attendencemgmt,rainmaker-${GlobalVariables.organisationListModel!.organisations!.first.tenantId.toString()},rainmaker-${GlobalVariables.stateInfoListModel!.code.toString()}'
        : 'rainmaker-contracts,rainmaker-measurement,rainmaker-common,rainmaker-common-masters,rainmaker-expenditure,rainmaker-workflow,rainmaker-attendencemgmt,rainmaker-${GlobalVariables.stateInfoListModel!.code.toString()}';
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
   static initilizeHiveBox() async {
    await Hive.initFlutter();

    Hive.registerAdapter(KeyLocaleModelAdapter());
    Hive.registerAdapter(LocalizationAdapter());
    await Hive.openBox<KeyLocaleModel>("keyValueModel");
    await Hive.openBox<Localization>("localization");
    await Hive.box<KeyLocaleModel>('keyValueModel').clear();
    await Hive.box<Localization>('localization').clear();
  }

  // error message processing for same   day attendance mark of particular wage seeker in different projects

  static List<DuplicateWageSeeker>? getListofErrorWageSeeker(
      {required String message}) {
    try {
      // Split the text by "||"
      List<String> splitText = message.split("||");

      // List to hold the attendance objects
      List<DuplicateWageSeeker> attendanceList = [];

      for (String part in splitText) {
        // Extract individualId
        String individualId = part.split('[')[1].split(' ')[0];

        // Extract name (from givenName)
        String name = part.split('givenName=')[1].split(',')[0];

        // Extract date
        String date = part.split('on this day : ')[1].split(' ')[0];

        // Create an Attendance object and add it to the list
        DuplicateWageSeeker attendance = DuplicateWageSeeker(
            individualId: individualId, name: name, date: date);
        attendanceList.add(attendance);

       
      }
      // for development purpose to check the list 
      //  return [...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,
      //   ...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList,...attendanceList];
      return attendanceList;
    } catch (ex) {
      return null;
    }
  }


  


}


