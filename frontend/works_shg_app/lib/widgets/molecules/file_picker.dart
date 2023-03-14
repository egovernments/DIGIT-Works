import 'dart:io';

import 'package:digit_components/theme/digit_theme.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path/path.dart' as path;
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/models/file_store/file_store_model.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/constants.dart';

import '../../data/repositories/core_repo/core_repository.dart';
import '../../utils/common_methods.dart';
import '../../utils/notifiers.dart';

class FilePickerDemo extends StatefulWidget {
  final Function(List<FileStoreModel>?) callBack;
  final String moduleName;
  final String label;
  final List<String>? extensions;

  const FilePickerDemo(
      {Key? key,
      required this.callBack,
      required this.moduleName,
      this.extensions,
      required this.label})
      : super(key: key);
  @override
  FilePickerDemoState createState() => FilePickerDemoState();
}

class FilePickerDemoState extends State<FilePickerDemo> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  List<dynamic> _selectedFiles = <dynamic>[];
  List<FileStoreModel> fileStoreList = <FileStoreModel>[];
  String? _directoryPath;
  String? _extension;
  bool _loadingPath = false;
  bool multiPick = false;
  FileType pickingType = FileType.custom;
  TextEditingController controller = TextEditingController();
  final ImagePicker _picker = ImagePicker();

  @override
  void initState() {
    super.initState();
    controller.addListener(() => _extension = controller.text);
  }

  void _openFileExplorer() async {
    setState(() => _loadingPath = true);
    try {
      print('open File Exp');
      _directoryPath = null;
      var paths = (await FilePicker.platform.pickFiles(
        type: pickingType,
        allowMultiple: multiPick,
        allowedExtensions: widget.extensions ??
            ((_extension?.isNotEmpty ?? false)
                ? _extension?.replaceAll(' ', '').split(',')
                : null),
      ))
          ?.files;

      if (paths != null) {
        var isNotValidSize = false;
        for (var path in paths) {
          if (!(await CommonMethods.isValidFileSize(path.size))) {
            isNotValidSize = true;
          }
          ;
        }

        if (isNotValidSize) {
          Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
              i18.common.fileSize, 'ERROR');
          return;
        }
        if (multiPick) {
          _selectedFiles.addAll(paths);
        } else {
          _selectedFiles = paths;
        }

        List<dynamic> files = paths;
        if (!kIsWeb) {
          files = paths.map((e) => File(e.path ?? '')).toList();
        }

        uploadFiles(files);
      }
    } on PlatformException catch (e) {
      print("Unsupported operation$e");
    } catch (ex) {
      print(ex);
    }
    if (!mounted) return;
    setState(() {
      _loadingPath = false;
    });
  }

  uploadFiles(List<dynamic> files) async {
    try {
      var response = await CoreRepository()
          .uploadFiles(files, widget.moduleName.toString());
      fileStoreList.addAll(response);
      if (_selectedFiles.isNotEmpty) widget.callBack(fileStoreList);
    } catch (e) {
      Notifiers.getToastMessage(context, e.toString(), 'ERROR');
    }
  }

  _getContainer(constraints, context) {
    return [
      Container(
          width: constraints.maxWidth > 760
              ? MediaQuery.of(context).size.width / 3
              : MediaQuery.of(context).size.width,
          padding: const EdgeInsets.only(top: 18, bottom: 3),
          child: Align(
              alignment: Alignment.centerLeft,
              child: Text(widget.label,
                  textAlign: TextAlign.left,
                  style: TextStyle(
                      fontWeight: FontWeight.w400,
                      fontSize: 16,
                      color: DigitTheme.instance.colorScheme.onSurface)))),
      Container(
          width: constraints.maxWidth > 760
              ? MediaQuery.of(context).size.width / 2.5
              : MediaQuery.of(context).size.width,
          // height: 50,
          decoration: BoxDecoration(border: Border.all(color: Colors.grey)),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Container(
                  margin: const EdgeInsets.only(
                      left: 4.0, right: 16.0, top: 4.0, bottom: 4.0),
                  alignment: Alignment.centerLeft,
                  child: ElevatedButton(
                    style: ButtonStyle(
                        padding: MaterialStateProperty.all(
                            const EdgeInsets.symmetric(horizontal: 15)),
                        backgroundColor: MaterialStateProperty.all<Color>(
                            const Color(0XFFD6D5D4)),
                        shape:
                            MaterialStateProperty.all<RoundedRectangleBorder>(
                          const RoundedRectangleBorder(
                            borderRadius: BorderRadius.zero,
                          ),
                        )),
                    onPressed: () => selectDocumentOrImage(),
                    child: Text(
                      "${AppLocalizations.of(context).translate(i18.common.chooseFile)}",
                      style: TextStyle(
                          color: DigitTheme.instance.colorScheme.onSurface,
                          fontSize: 16),
                    ),
                  )),
              _selectedFiles.isNotEmpty
                  ? Expanded(
                      child: SingleChildScrollView(
                        child: Wrap(
                            direction: Axis.horizontal,
                            spacing: 3,
                            children: List.generate(
                                _selectedFiles.length,
                                (index) => Wrap(
                                      direction: Axis.horizontal,
                                      crossAxisAlignment:
                                          WrapCrossAlignment.center,
                                      spacing: 2,
                                      children: [
                                        Text(
                                          _selectedFiles[index] is File
                                              ? (path.basename(
                                                  _selectedFiles[index].path))
                                              : _selectedFiles[index].name,
                                          maxLines: 1,
                                          overflow: TextOverflow.ellipsis,
                                        ),
                                        IconButton(
                                            padding: EdgeInsets.all(5),
                                            onPressed: () =>
                                                onClickOfClear(index),
                                            icon: Icon(Icons.cancel))
                                      ],
                                    )).toList()),
                      ),
                    )
                  : Text(
                      "${AppLocalizations.of(context).translate(i18.common.noFileUploaded)}",
                      style: const TextStyle(color: Colors.black, fontSize: 16),
                    )
            ],
          ))
    ];
  }

  void onClickOfClear(int index) {
    setState(() {
      _selectedFiles.removeAt(index);
      if (index < fileStoreList.length) fileStoreList.removeAt(index);
    });
    widget.callBack(fileStoreList);
  }

  void reset() {
    _selectedFiles.clear();
    fileStoreList.clear();
  }

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      return Center(
          child: Padding(
              padding: const EdgeInsets.only(left: 8.0, right: 8.0),
              child: SingleChildScrollView(
                child: Container(
                  margin: constraints.maxWidth > 760
                      ? const EdgeInsets.only(
                          top: 5.0, bottom: 5, right: 10, left: 10)
                      : const EdgeInsets.only(
                          top: 5.0, bottom: 5, right: 0, left: 0),
                  child: constraints.maxWidth > 760
                      ? Row(children: _getContainer(constraints, context))
                      : Column(children: _getContainer(constraints, context)),
                ),
              )));
    });
  }

  Future<void> selectDocumentOrImage() async {
    FocusScope.of(context).unfocus();
    var list = [
      {"label": i18.common.camera, 'icon': Icons.camera_alt},
      {"label": i18.common.fileManager, 'icon': Icons.drive_folder_upload},
    ];

    if (kIsWeb) {
      _openFileExplorer();
      return;
    }

    callBack(String value) {
      Navigator.pop(context);
      if (list.first['label'] == value) {
        imagePath(context, selectionMode: 'camera');
      } else {
        imagePath(context, selectionMode: 'filePicker');
      }
    }

    await showModalBottomSheet(
        context: context,
        shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.vertical(top: Radius.circular(25.0)),
        ),
        builder: (BuildContext context) {
          return Padding(
            padding:
                const EdgeInsets.only(bottom: 25, left: 25, right: 25, top: 10),
            child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisSize: MainAxisSize.min,
                children: [
                  Container(
                    padding: const EdgeInsets.symmetric(vertical: 8),
                    alignment: Alignment.center,
                    child: Container(
                      height: 2,
                      width: 30,
                      color: Colors.grey,
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(bottom: 16, top: 5),
                    child: Text(
                        AppLocalizations.of(context)
                            .translate(i18.common.chooseAnAction),
                        style: TextStyle(
                            fontSize: 16, fontWeight: FontWeight.bold)),
                  ),
                  Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: list
                          .map((e) => _buildIcon(e['label'] as String,
                              e['icon'] as IconData, callBack))
                          .toList()),
                ]),
          );
        });
  }

  Future<void> imagePath(BuildContext context,
      {required String selectionMode}) async {
    FocusScope.of(context).unfocus();
    try {
      if (selectionMode == 'camera') {
        final pickedFile = await _picker.pickImage(source: ImageSource.camera);
        if (pickedFile != null) {
          String newPath = path.join(path.dirname(pickedFile.path),
              '${CommonMethods.getRandomName()}${path.extension(pickedFile.path)}');
          final File file = await File(pickedFile.path).copy(newPath);
          if (file != null) {
            if (!(await CommonMethods.isValidFileSize(await file.length()))) {
              Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
                  i18.common.fileSize, 'ERROR');
              return;
            }
            ;
            if (multiPick) {
              _selectedFiles.addAll([file]);
            } else {
              _selectedFiles = [file];
            }
            uploadFiles(<File>[file]);
            return;
          } else {
            return null;
          }
        } else {
          _openFileExplorer();
        }
      } else {
        _openFileExplorer();
      }
    } on Exception catch (e) {
      Notifiers.getToastMessage(context, e.toString(), 'ERROR');
    }
  }

  Widget _buildIcon(String label, IconData icon, Function(String) callBack) {
    return Wrap(
      direction: Axis.vertical,
      crossAxisAlignment: WrapCrossAlignment.center,
      alignment: WrapAlignment.center,
      spacing: 8,
      children: [
        IconButton(
            onPressed: () => callBack(label), iconSize: 45, icon: Icon(icon)),
        Text(
          label,
          textAlign: TextAlign.center,
          style: TextStyle(fontSize: 15),
        )
      ],
    );
  }
}
