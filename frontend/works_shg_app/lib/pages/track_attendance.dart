import 'dart:math';

import 'package:digit_components/digit_components.dart';
import 'package:digit_components/widgets/digit_elevated_button.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/models/table/table_model.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/Back.dart';
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/molecules/digit_table.dart' as shg_app;

import '../blocs/attendance/attendance_user_search.dart';
import '../blocs/localization/app_localization.dart';
import '../models/attendance/attendance_registry_model.dart';
import '../router/app_router.dart';
import '../utils/constants.dart';
import '../utils/models.dart';
import '../widgets/CircularButton.dart';

class TrackAttendancePage extends StatelessWidget {
  final List<Map<String, dynamic>> projectDetails;
  final AttendanceRegister? attendanceRegister;
  const TrackAttendancePage(this.projectDetails, this.attendanceRegister,
      {Key? key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    Random random = Random();
    // final List list = json.decode(fakeTableData);
    // List<TableDataModel> tableList =
    //     list.map((e) => TableDataModel.fromJson(e)).toList();
    return Scaffold(
      body: BlocBuilder<AttendanceUserSearchBloc, AttendanceUserSearchState>(
          builder: (context, state) {
        if (!state.loading && state.userSearchModel != null) {
          List<Map<String, dynamic>> userList = state.userSearchModel!.user!
              .map((e) => {
                    "name": e.name,
                    "aadhaar": e.uuid,
                    "monIndex": -1,
                    "tueIndex": 0,
                    "wedIndex": 1,
                    "thursIndex": -1,
                    "friIndex": 0,
                    "satIndex": 1
                  })
              .toList();
          List<Map<String, dynamic>> attendeePayLoadList =
              state.userSearchModel!.user!
                  .map((e) => {
                        "registerId": attendanceRegister?.id.toString(),
                        "individualId": e.uuid,
                        "tenantId": e.tenantId
                      })
                  .toList();
          List<TableDataModel>? userTableList =
              userList.map((e) => TableDataModel.fromJson(e)).toList();
          var tableData = getAttendanceData(userTableList);
          return Stack(children: [
            Container(
              color: const Color.fromRGBO(238, 238, 238, 1),
              padding: const EdgeInsets.only(left: 8, right: 8, bottom: 16),
              height: MediaQuery.of(context).size.height - 150,
              child: CustomScrollView(slivers: [
                SliverList(
                    delegate: SliverChildListDelegate([
                  const Back(),
                  WorkDetailsCard(
                    projectDetails,
                  ),
                ])),
                SliverToBoxAdapter(
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
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
                                prefixIconConstraints: const BoxConstraints(
                                    minWidth: 0, minHeight: 0),
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
                      Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            LayoutBuilder(builder: (context, constraints) {
                              var width = constraints.maxWidth < 760
                                  ? 145.0
                                  : (constraints.maxWidth / 4);
                              return Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: shg_app.DigitTable(
                                  headerList: headerList,
                                  tableData: tableData,
                                  leftColumnWidth: width,
                                  rightColumnWidth: width * 7,
                                  height: 58 + (52.0 * tableData.length),
                                ),
                              );
                            }),
                            // SizedBox(
                            //   height: 30,
                            // )
                          ])
                    ]))
              ]),
            ),
            Align(
              alignment: Alignment.bottomCenter,
              child: Padding(
                padding: const EdgeInsets.only(
                  left: 8.0,
                  right: 8.0,
                ),
                child: SizedBox(
                  height: 100,
                  child: Column(
                    children: [
                      OutlinedButton(
                          style: OutlinedButton.styleFrom(
                              backgroundColor: Colors.white,
                              side: BorderSide(
                                  width: 2,
                                  color: DigitTheme
                                      .instance.colorScheme.secondary)),
                          onPressed: () {},
                          child: Center(
                              child: Text(
                            AppLocalizations.of(context)
                                .translate(i18.common.saveAsDraft),
                            style: Theme.of(context).textTheme.subtitle2,
                          ))),
                      SizedBox(
                        height: 10,
                      ),
                      DigitElevatedButton(
                        onPressed: () {
                          // context.read<AttendeeCreateBloc>().add(
                          //   CreateAttendeeEvent(
                          //       attendeeList: attendeePayLoadList),
                          // );

                          context.router.push(const HomeRoute());
                          // if (!state.loading) {

                          // }
                        },
                        child: Text(
                            AppLocalizations.of(context)
                                .translate(i18.common.sendForApproval),
                            style: Theme.of(context)
                                .textTheme
                                .subtitle1!
                                .apply(color: Colors.white)),
                      ),
                    ],
                  ),
                ),
              ),
            )
          ]);
        } else {
          return const CircularProgressIndicator();
        }
      }),
    );
  }

  List<TableHeader> get headerList => [
        TableHeader(
          AppLocalizations.of(scaffoldMessengerKey.currentContext!)
              .translate(i18.common.nameLabel),
          apiKey: 'name',
        ),
        TableHeader(
          AppLocalizations.of(scaffoldMessengerKey.currentContext!)
              .translate(i18.common.aadhaarNumber),
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
        index: tableDataModel.monIndex ?? 0,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.tueIndex ?? 0,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.wedIndex ?? 0,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.thursIndex ?? 0,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.friIndex ?? 0,
        isNotGreyed: false,
        // onPressed: null,
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 10,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.satIndex ?? 0,
        isNotGreyed: false,
        // onPressed: null,
      ))
    ]);
  }

  List<TableDataRow> getAttendanceData(List<TableDataModel> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }
}
