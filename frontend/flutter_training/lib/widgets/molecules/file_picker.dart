import 'dart:io';

import 'package:digit_components/theme/colors.dart';
import 'package:digit_components/theme/digit_theme.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path/path.dart' as path;
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/models/file_store/file_store_model.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:flutter_training/utils/models/file_picker_data.dart';
import 'package:flutter_training/widgets/ButtonLink.dart';

import '../../data/repositories/core_repo/core_repository.dart';
import '../../utils/common_methods.dart';
import '../../utils/notifiers.dart';

class SHGFilePicker extends StatefulWidget {
  final Function(List<FileStoreModel>?) callBack;
  final String moduleName;
  final String label;
  final List<String>? extensions;

  const SHGFilePicker(
      {Key? key,
      required this.callBack,
      required this.moduleName,
      this.extensions,
      required this.label})
      : super(key: key);
  @override
  SHGFilePickerState createState() => SHGFilePickerState();
}

class SHGFilePickerState extends State<SHGFilePicker> {
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

  void _openFileExplorer(BuildContext context) async {
    setState(() => _loadingPath = true);
    try {
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
        var isNotValidFile = false;

        for (var path in paths) {
          if (!(await CommonMethods.isValidFileSize(path.size))) {
            isNotValidSize = true;
          }
          if (!CommonMethods.validateImageFileExtension(path.extension ?? '',
              widget.extensions ?? ['png', 'jpeg', 'jpg'])) {
            isNotValidFile = true;
          }
        }

        if (isNotValidSize) {
          Notifiers.getToastMessage(context, i18.common.fileSize, 'ERROR');
          return;
        }
        if (isNotValidFile) {
          Notifiers.getToastMessage(
              context, i18.common.invalidImageFile, 'ERROR');
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
          setState(() {
            FilePickerData.imageFile = File(paths.single.path!);
          });
        }

        setState(() {
          FilePickerData.bytes = paths.single.bytes;
        });

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
        height: MediaQuery.of(context).size.width / 2.5,
        decoration: BoxDecoration(border: Border.all(color: Colors.grey)),
        child: Align(
          alignment: Alignment.center,
          child: kIsWeb && FilePickerData.bytes != null
              ? Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                      Image.memory(
                        FilePickerData.bytes!,
                        fit: BoxFit.fitHeight,
                        width: constraints.maxWidth > 760
                            ? MediaQuery.of(context).size.width / 3
                            : MediaQuery.of(context).size.width / 1.5,
                        height: MediaQuery.of(context).size.width / 2.8,
                      ),
                      IconButton(
                          padding: const EdgeInsets.all(2),
                          onPressed: () => onClickOfClear(0),
                          icon: const Icon(Icons.cancel))
                    ])
              : !kIsWeb && FilePickerData.imageFile != null
                  ? Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                          Image.file(
                            FilePickerData.imageFile!,
                            fit: BoxFit.fitHeight,
                            width: constraints.maxWidth > 760
                                ? MediaQuery.of(context).size.width / 3
                                : MediaQuery.of(context).size.width / 1.5,
                            height: MediaQuery.of(context).size.width / 2.8,
                          ),
                          IconButton(
                              padding: const EdgeInsets.all(2),
                              onPressed: () => onClickOfClear(0),
                              icon: const Icon(Icons.cancel))
                        ])
                  : GestureDetector(
                      onTap: () => selectDocumentOrImage(context),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(
                            Icons.camera_enhance,
                            color: DigitTheme.instance.colorScheme.primary,
                            size: 50,
                          ),
                          ButtonLink(
                            AppLocalizations.of(context)
                                .translate(i18.common.clickToAddPhoto),
                            null,
                            align: Alignment.center,
                            fontSize: 12,
                          )
                        ],
                      ),
                    ),
        ),
      ),
      Container(
        padding: const EdgeInsets.only(top: 2.0),
        alignment: Alignment.centerLeft,
        child: Text(
            AppLocalizations.of(context).translate(i18.common.validPhotoGraph),
            style: TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.normal,
                color: const DigitColors().cloudGray)),
      )
    ];
  }

  void onClickOfClear(int index) {
    setState(() {
      if (_selectedFiles.isNotEmpty) {
        _selectedFiles.removeAt(index);
      }
      if (!index.isNegative) {
        if (index < fileStoreList.length) {
          fileStoreList.removeAt(index);
        }
      }
      FilePickerData.imageFile = null;
      FilePickerData.bytes = null;
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
              padding: const EdgeInsets.only(left: 4.0, right: 4.0),
              child: SingleChildScrollView(
                child: Container(
                  margin: const EdgeInsets.only(
                      top: 5.0, bottom: 5, right: 0, left: 0),
                  child: Column(children: _getContainer(constraints, context)),
                ),
              )));
    });
  }

  Future<void> selectDocumentOrImage(BuildContext context) async {
    FocusScope.of(context).unfocus();
    var list = [
      {"label": i18.common.camera, 'icon': Icons.camera_enhance},
      {"label": i18.common.fileManager, 'icon': Icons.drive_folder_upload},
    ];

    if (kIsWeb) {
      _openFileExplorer(context);
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
                        style: const TextStyle(
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
              Notifiers.getToastMessage(context, i18.common.fileSize, 'ERROR');
              return;
            }
            if (multiPick) {
              _selectedFiles.addAll([file]);
            } else {
              _selectedFiles = [file];
            }
            setState(() {
              FilePickerData.imageFile = _selectedFiles.first;
            });
            uploadFiles(<File>[file]);
            return;
          } else {
            return null;
          }
        } else {
          _openFileExplorer(context);
        }
      } else {
        _openFileExplorer(context);
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
            onPressed: () => callBack(label),
            iconSize: 45,
            icon: Icon(
              icon,
              color: DigitTheme.instance.colorScheme.primary,
            )),
        Text(
          AppLocalizations.of(context).translate(label),
          textAlign: TextAlign.center,
          style: const TextStyle(fontSize: 16),
        )
      ],
    );
  }
}
