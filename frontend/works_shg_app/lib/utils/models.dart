import 'dart:typed_data';

import 'package:flutter/cupertino.dart';

class TableHeader {
  final String label;
  final ValueChanged<TableHeader>? callBack;
  bool? isSortingRequired = false;
  bool? isAscendingOrder;
  String? apiKey;
  TableHeader(this.label,
      {this.callBack,
      this.isSortingRequired,
      this.isAscendingOrder,
      this.apiKey});
}

class CustomFile {
  final Uint8List bytes;
  final String name;
  final String extension;

  CustomFile(this.bytes, this.name, this.extension);
}

class TableDataRow {
  final List<TableData> tableRow;
  TableDataRow(this.tableRow);
}

class KeyValue {
  String label;
  dynamic key;
  KeyValue(this.label, this.key);
}

class FilesAttached {
  String name;
  String fileStoreId;
  FilesAttached(this.name, this.fileStoreId);
}

class DaysInRange {
  bool monday;
  bool tuesday;
  bool wednesday;
  bool thursday;
  bool friday;
  bool saturday;
  bool sunday;

  DaysInRange({
    this.monday = false,
    this.tuesday = false,
    this.wednesday = false,
    this.thursday = false,
    this.friday = false,
    this.saturday = false,
    this.sunday = false,
  });
}

class TableData {
  final String? label;
  final Widget? widget;
  final TextStyle? style;
  final String? apiKey;
  ValueChanged<TableData>? callBack;
  TableData({this.label, this.widget, this.style, this.callBack, this.apiKey});
}

class DateRange {
  final String range;
  final int startDate;
  final int endDate;
  DateRange(this.range, this.startDate, this.endDate);
}

class MenuItemModel {
  final String name;
  final String code;

  MenuItemModel({this.name = 'SKill 1', this.code = ' SKILL1'});
}

class Skill {
  final String code;
  Skill({required this.code});
}

class SkillCategory {
  final String code;

  SkillCategory({required this.code});
}

class SelectSkill {
  final String code;
  final String label;

  SelectSkill(this.code, this.label);
}
