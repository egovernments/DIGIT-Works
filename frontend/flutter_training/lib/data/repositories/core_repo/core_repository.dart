import 'dart:convert';
import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_training/Env/app_config.dart';
import 'package:flutter_training/models/file_store/file_store_model.dart';
import 'package:flutter_training/services/urls.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:universal_html/html.dart' as html;

import '../../../Env/env_config.dart';
import '../../../utils/common_methods.dart';
import '../../../utils/models.dart';
import '../../../utils/save_file_mobile.dart';

class CoreRepository {
  Future<List<FileStoreModel>> uploadFiles(
      List<dynamic>? paths, String moduleName) async {
    Map? respStr;

    var postUri = Uri.parse(
        "${kIsWeb && !kDebugMode ? apiBaseUrl : envConfig.variables.baseUrl}${Urls.commonServices.fileUpload}");
    var request = http.MultipartRequest("POST", postUri);
    if (paths != null && paths.isNotEmpty) {
      if (paths is List<PlatformFile>) {
        for (var i = 0; i < paths.length; i++) {
          var path = paths[i];
          var fileName = '${path.name}.${path.extension?.toLowerCase()}';
          http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
              'file', path.bytes!,
              contentType: CommonMethods().getMediaType(fileName),
              filename: fileName);
          request.files.add(multipartFile);
        }
      } else if (paths is List<File>) {
        for (var file in paths) {
          request.files.add(await http.MultipartFile.fromPath('file', file.path,
              contentType: CommonMethods().getMediaType(file.path),
              filename: file.path.split('/').last));
        }
      } else if (paths is List<CustomFile>) {
        for (var i = 0; i < paths.length; i++) {
          var path = paths[i];
          var fileName = '${path.name}.${path.extension.toLowerCase()}';
          http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
              'file', path.bytes,
              contentType: CommonMethods().getMediaType(fileName),
              filename: fileName);
          request.files.add(multipartFile);
        }
      }
      request.fields['tenantId'] =
          GlobalVariables.userInfo!.tenantId.toString();
      request.fields['module'] = moduleName;
      await request.send().then((response) async {
        if (response.statusCode == 201) {
          respStr = json.decode(await response.stream.bytesToString());
        }
      });
      if (respStr != null) {
        return respStr?['files']
            .map<FileStoreModel>((e) => FileStoreModelMapper.fromMap(e))
            .toList();
      }
    }
    return <FileStoreModel>[];
  }

  Future<bool?> fileDownload(String url, [String? fileName]) async {
    if (url.contains(',')) {
      url = url.split(',').first;
    }

    fileName = fileName ?? CommonMethods.getExtension(url);
    try {
      String? downloadPath;
      if (kIsWeb) {
        html.AnchorElement anchorElement = html.AnchorElement(href: url);
        anchorElement.download = url;
        anchorElement.target = '_blank';
        anchorElement.click();
        return true;
      } else if (Platform.isIOS) {
        downloadPath = (await getApplicationDocumentsDirectory()).path;
      } else {
        downloadPath = (await getExternalStorageDirectory())?.path;
      }
      var status = await Permission.storage.status;
      if (!status.isGranted) {
        await Permission.storage.request();
      }
      final response = await http.get(Uri.parse(url));
      final bytes = response.bodyBytes;
      await saveAndLaunchFile(bytes, fileName ?? 'Common.pdf');
    } catch (e, s) {
      print(e);
      // Notifiers.getToastMessage(
      //     scaffoldMessengerKey.currentContext!, e.toString(), 'ERROR');
    }
    return false;
  }

  Future<List<FileStoreModel>?> fetchFiles(
      List<String> storeId, String tenantId) async {
    List<FileStoreModel>? fileStoreIds;
    FileStoreListModel? fileStoreListModel;

    var res = await http.get(Uri.parse(
        '${kIsWeb && !kDebugMode ? apiBaseUrl : envConfig.variables.baseUrl}${Urls.commonServices.fileFetch}?tenantId=$tenantId&fileStoreIds=${storeId.join(',')}'));

    if (res != null) {
      fileStoreListModel = FileStoreListModelMapper.fromMap(
          jsonDecode(res.body) as Map<String, dynamic>);
    }
    return fileStoreListModel?.fileStoreIds;
  }
}
