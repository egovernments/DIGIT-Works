import 'package:hive/hive.dart';

part 'localization.g.dart';

@HiveType(typeId: 0)
class KeyLocaleModel extends HiveObject {
  @HiveField(0)
  String? locale;

  @HiveField(1)
  List<Localization>? localizationsList;
}

@HiveType(typeId: 1)
class Localization {
  @HiveField(0)
  String? code;

  @HiveField(1)
  String? message;

  @HiveField(2)
  String? module;

  @HiveField(3)
  String? locale;
}
