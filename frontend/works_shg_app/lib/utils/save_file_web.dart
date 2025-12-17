import 'dart:async';
import 'dart:convert';

// ignore: avoid_web_libraries_in_flutter
import 'package:universal_html/html.dart';

///To save the file in the device
Future<void> saveAndLaunchFile(List<int> bytes, String fileName) async {
  AnchorElement(
      href:
          'data:application/octet-stream;charset=utf-16le;base64,${base64.encode(bytes)}')
    ..setAttribute('download', fileName)
    ..click();
}
