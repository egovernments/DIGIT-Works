import 'dart:convert';
import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:http/http.dart' as http;
import 'package:works_shg_app/models/file_store/file_store_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../../Env/app_config.dart';
import '../../../utils/common_methods.dart';
import '../../../utils/models.dart';

class CoreRepository {
  Future<List<FileStoreModel>> uploadFiles(
      List<dynamic>? paths, String moduleName) async {
    Map? respStr;

    var postUri = Uri.parse("$apiBaseUrl${Urls.commonServices.fileUpload}");
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
          GlobalVariables.stateInfoListModel!.code.toString();
      request.fields['module'] = moduleName;
      await request.send().then((response) async {
        if (response.statusCode == 201) {
          respStr = json.decode(await response.stream.bytesToString());
        }
      });
      if (respStr != null && respStr?['files'] != null) {
        return respStr?['files']
            .map<FileStoreModel>((e) => FileStoreModelMapper.fromJson(e))
            .toList();
      }
    }
    return <FileStoreModel>[];
  }
}
