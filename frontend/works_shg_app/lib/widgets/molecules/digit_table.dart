import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:horizontal_data_table/horizontal_data_table.dart';

import '../../utils/models.dart';
import '../ScrollParent.dart';

class DigitTable extends StatelessWidget {
  final List<TableHeader> headerList;
  final List<TableDataRow> tableData;
  final double leftColumnWidth;
  final double rightColumnWidth;
  final double? height;
  final ScrollPhysics? scrollPhysics;

  DigitTable(
      {super.key,
      required this.headerList,
      required this.tableData,
      required this.leftColumnWidth,
      required this.rightColumnWidth,
      this.height,
      this.scrollPhysics});
  final ScrollController controller = ScrollController();
  final double columnRowFixedHeight = 52.0;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: height ?? MediaQuery.of(context).size.height,
      child: HorizontalDataTable(
          leftHandSideColumnWidth: leftColumnWidth,
          rightHandSideColumnWidth: rightColumnWidth,
          isFixedHeader: true,
          headerWidgets: _getTitleWidget(),
          leftSideItemBuilder: _generateFirstColumnRow,
          rightSideItemBuilder: _generateRightHandSideColumnRow,
          itemCount: tableData.length,
          elevation: 0,
          // rowSeparatorWidget: const Divider(
          //   color: Colors.black54,
          //   height: 1.0,
          //   thickness: 0.0,
          // ),
          leftHandSideColBackgroundColor: const Color(0xFFFFFFFF),
          rightHandSideColBackgroundColor: const Color(0xFFFFFFFF),
          scrollPhysics: scrollPhysics,
          verticalScrollbarStyle: const ScrollbarStyle(
            isAlwaysShown: true,
            thickness: 4.0,
            radius: Radius.circular(5.0),
          ),
          horizontalScrollbarStyle: const ScrollbarStyle(
            isAlwaysShown: true,
            thickness: 4.0,
            radius: Radius.circular(5.0),
          ),
          enablePullToRefresh: false),
    );
  }

  List<Widget> _getTitleWidget() {
    var index = 0;
    return headerList.map((e) {
      index++;
      if (e.isSortingRequired ?? false) {
        return TextButton(
            style: TextButton.styleFrom(
              padding: EdgeInsets.zero,
            ),
            onPressed: e.callBack == null ? null : () => e.callBack!(e),
            child: _getTitleItemWidget((e.label),
                isAscending: e.isAscendingOrder,
                isBorderRequired: (index - 1) == 0));
      } else {
        return _getTitleItemWidget(e.label);
      }
    }).toList();
  }

  Widget _getTitleItemWidget(String label,
      {bool? isAscending, bool isBorderRequired = false}) {
    var textWidget = Text(label,
        style: const TextStyle(
            fontWeight: FontWeight.w700, color: Colors.black, fontSize: 12));

    return Container(
      decoration: isBorderRequired
          ? BoxDecoration(
              border: Border(
                  left: tableCellBorder,
                  bottom: tableCellBorder,
                  right: tableCellBorder))
          : null,
      width: leftColumnWidth,
      height: 56,
      padding: const EdgeInsets.only(left: 17, right: 5, top: 6, bottom: 6),
      alignment: Alignment.centerLeft,
      child: isAscending != null
          ? Wrap(
              crossAxisAlignment: WrapCrossAlignment.center,
              spacing: 5,
              children: [
                textWidget,
                Icon(isAscending
                    ? Icons.arrow_upward
                    : Icons.arrow_downward_sharp)
              ],
            )
          : textWidget,
    );
  }

  double columnRowIncreasedHeight(int index) {
    return (50 +
        tableData[index].tableRow.first.label!.substring(28).length.toDouble());
    //if greater than 28 characters
  }

  Widget _generateFirstColumnRow(BuildContext context, int index) {
    var data = tableData[index].tableRow.first;
    return ScrollParent(
        controller,
        InkWell(
          onTap: () {
            if (data.callBack != null) {
              data.callBack!(data);
            }
          },
          child: Container(
            decoration: BoxDecoration(
                border: Border(
              left: tableCellBorder,
              bottom: tableCellBorder,
              right: tableCellBorder,
            )),
            width: leftColumnWidth,
            height: tableData[index].tableRow.first.label != null &&
                    tableData[index].tableRow.first.label!.length > 28
                ? columnRowIncreasedHeight(index)
                : columnRowFixedHeight,
            padding:
                const EdgeInsets.only(left: 17, right: 5, top: 6, bottom: 6),
            alignment: Alignment.centerLeft,
            child: Text(
              tableData[index].tableRow.first.label ?? '',
              style: tableData[index].tableRow.first.style ??
                  TextStyle(color: DigitTheme.instance.colorScheme.secondary),
            ),
          ),
        ));
  }

  Widget _generateColumnRow(BuildContext context, int index, String input,
      {Widget? buttonWidget, TextStyle? style}) {
    return Container(
      width: leftColumnWidth,
      height: tableData[index].tableRow.first.label!.length > 28
          ? columnRowIncreasedHeight(index)
          : columnRowFixedHeight,
      padding: const EdgeInsets.only(left: 17, right: 5, top: 6, bottom: 6),
      alignment: Alignment.centerLeft,
      child: Row(
        children: <Widget>[
          Expanded(
            child: input.isNotEmpty
                ? Text(
                    input,
                    style: style,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  )
                : buttonWidget ?? const Text(''),
          )
        ],
      ),
    );
  }

  Widget _generateRightHandSideColumnRow(BuildContext context, int index) {
    var data = tableData[index];
    var list = <Widget>[];
    for (int i = 1; i < data.tableRow.length; i++) {
      list.add(_generateColumnRow(context, index, data.tableRow[i].label ?? '',
          style: data.tableRow[i].style,
          buttonWidget: data.tableRow[i].widget));
    }
    return Container(
        color: index % 2 == 0 ? const Color(0xffEEEEEE) : Colors.white,
        child: Row(children: list));
  }

  BorderSide get tableCellBorder =>
      const BorderSide(color: Color.fromRGBO(238, 238, 238, 1), width: 0.5);
}
