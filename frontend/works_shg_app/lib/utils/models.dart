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

class TableDataRow {
  final List<TableData> tableRow;
  TableDataRow(this.tableRow);
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
