import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:works_shg_app/models/table/table_model.dart';
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/molecules/digit_table.dart';

import '../data/fake_table_data.dart';
import '../utils/models.dart';
import '../widgets/Back.dart';
import '../widgets/CircularButton.dart';

class SHGInboxPage extends StatelessWidget {
  final List<Map<String, dynamic>> musterDetails;
  const SHGInboxPage(this.musterDetails, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final List list = json.decode(fakeTableData);
    List<TableDataModel> tableList =
        list.map((e) => TableDataModel.fromJson(e)).toList();
    return Scaffold(
      body: CustomScrollView(slivers: [
        SliverList(
            delegate: SliverChildListDelegate([
          const Back(),
          WorkDetailsCard(musterDetails),
        ])),
        SliverToBoxAdapter(
            child:
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          const SizedBox(
            height: 20,
          ),
          Container(
              margin: const EdgeInsets.all(8.0),
              child: TextFormField(
                  autofocus: true,
                  decoration: InputDecoration(
                    hintText: "Search by Name/Aadhaar-Number",
                    border: const OutlineInputBorder(
                      borderRadius: BorderRadius.zero,
                    ),
                    filled: true,
                    fillColor: Colors.white,
                    prefixIconConstraints:
                        const BoxConstraints(minWidth: 0, minHeight: 0),
                    prefixStyle: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w400,
                        color: Theme.of(context).primaryColorDark),
                    prefixIcon: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Icon(Icons.search_sharp)),
                  ))),
          const SizedBox(
            height: 20,
          ),
          Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            LayoutBuilder(builder: (context, constraints) {
              var width = constraints.maxWidth < 760
                  ? 145.0
                  : (constraints.maxWidth / 4);
              var tableData = getAttendanceData(tableList);
              return DigitTable(
                headerList: headerList,
                tableData: tableData,
                leftColumnWidth: width,
                rightColumnWidth: width * 7,
                height: 58 + (52.0 * tableData.length),
              );
            })
          ])
        ]))
      ]),
    );
  }

  List<TableHeader> get headerList => [
        TableHeader(
          'Name',
          apiKey: 'name',
        ),
        TableHeader(
          'Aadhaar Number',
          apiKey: 'aadhaarNumber',
        ),
        TableHeader(
          'Mon',
        ),
        TableHeader(
          'Tue',
        ),
        TableHeader(
          'Wed',
        ),
        TableHeader(
          'Thurs',
        ),
        TableHeader(
          'Fri',
        ),
        TableHeader(
          'Sat',
        ),
      ];

  TableDataRow getAttendanceRow(TableDataModel tableDataModel) {
    return TableDataRow([
      TableData(label: tableDataModel.name, apiKey: tableDataModel.name),
      TableData(label: tableDataModel.aadhaar, apiKey: tableDataModel.aadhaar),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.monIndex,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.tueIndex,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.wedIndex,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.thursIndex,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.friIndex,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.satIndex,
        isNotGreyed: false,
        // onPressed: null,
      ))
    ]);
  }

  List<TableDataRow> getAttendanceData(List<TableDataModel> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }
}
