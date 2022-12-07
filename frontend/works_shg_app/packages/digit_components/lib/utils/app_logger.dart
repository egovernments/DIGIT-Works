import 'package:flutter/foundation.dart';
import 'package:logging/logging.dart';

class AppLogger {
  static AppLogger get instance => _instance;
  static const _instance = AppLogger._();

  const AppLogger._();

  void debug(dynamic input, {String? title}) =>
      _printMessage(input, title: title, level: Level.CONFIG);

  void info(dynamic input, {String? title}) =>
      _printMessage(input, title: title, level: Level.INFO);

  void error({required String title, required StackTrace stackTrace}) =>
      _printError(title: title, stackTrace: stackTrace);

  void _printError({
    required String title,
    required StackTrace stackTrace,
  }) =>
      debugPrintStack(
        label: title,
        stackTrace: stackTrace,
      );

  void _printMessage(
    dynamic input, {
    String? title,
    required Level level,
  }) =>
      debugPrint(
        [
          '[${level.name.padRight(4, ' ').substring(0, 4)}] ',
          '${(title ?? runtimeType.toString())}\n',
          '${input.toString()}\n',
        ].join(''),
        wrapWidth: 120,
      );
}
