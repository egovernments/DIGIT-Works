import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:works_shg_app/models/attendance/attendance_registry_model.dart';
import 'package:works_shg_app/models/table/table_model.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/widgets/Back.dart';
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/delete_button.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/molecules/digit_table.dart' as shg_app;

import '../blocs/attendance/attendance_user_search.dart';
import '../blocs/attendance/create_attendee.dart';
import '../blocs/attendance/search_projects.dart';
import '../blocs/localization/app_localization.dart';
import '../models/user_search/user_search_model.dart';
import '../router/app_router.dart';
import '../utils/models.dart';
import '../utils/notifiers.dart';
import '../widgets/SideBar.dart';
import '../widgets/atoms/auto_complete_search_bar.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart';

class AttendanceRegisterTablePage extends StatefulWidget {
  final String registerId;
  final String tenantId;
  final List<Map<String, dynamic>> projectDetails;
  final AttendanceRegister? attendanceRegister;
  const AttendanceRegisterTablePage(
      @PathParam('registerId') this.registerId,
      @PathParam('tenantId') this.tenantId,
      this.projectDetails,
      this.attendanceRegister,
      {Key? key})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _AttendanceRegisterTablePage();
  }
}

class _AttendanceRegisterTablePage extends State<AttendanceRegisterTablePage> {
  var searchController = TextEditingController();
  var suggestionsBoxController = SuggestionsBoxController();
  List<Map<String, dynamic>> userList = [];
  List<Map<String, dynamic>> filteredUserList = [];
  List<Map<String, dynamic>> addToTableList = [];
  List<TableDataModel> userTableList = [];
  List<Map<String, dynamic>> attendeePayLoadList = [];

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<AttendanceProjectsSearchBloc>().add(
          SearchIndividualAttendanceProjectEvent(
              id: widget.registerId ?? '',
              tenantId: widget.tenantId.toString()),
        );
  }

  @override
  Widget build(BuildContext context) {
    // final List list = json.decode(fakeTableData);
    // List<TableDataModel> tableList =
    //     list.map((e) => TableDataModel.fromJson(e)).toList();
    return Scaffold(
        appBar: AppBar(),
        drawer: DrawerWrapper(const Drawer(
            child: SideBar(
          module: 'rainmaker-common,rainmaker-attendencemgmt',
        ))),
        body: Stack(children: [
          Container(
            color: const Color.fromRGBO(238, 238, 238, 1),
            padding: const EdgeInsets.only(left: 8, right: 8, bottom: 16),
            height: MediaQuery.of(context).size.height - 100,
            child: CustomScrollView(slivers: [
              SliverList(
                  delegate: SliverChildListDelegate([
                Back(
                  backLabel:
                      AppLocalizations.of(context).translate(i18.common.back),
                ),
                WorkDetailsCard(widget.projectDetails),
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
                        child: AutoCompleteSearchBar(
                          hintText: AppLocalizations.of(context)
                              .translate(i18.common.searchByNameAadhaar),
                          controller: searchController,
                          suggestionsBoxController: suggestionsBoxController,
                          onSuggestionSelected: onSuggestionSelected,
                          callBack: onSearchVendorList,
                          minCharsForSuggestions: 10,
                          maxLength: 10,
                          listTile: buildTile,
                          inputFormatter: [
                            FilteringTextInputFormatter.allow(
                                RegExp("[a-zA-Z0-9]"))
                          ],
                          labelText: '',
                        )),
                    const SizedBox(
                      height: 20,
                    ),
                    BlocBuilder<AttendanceProjectsSearchBloc,
                            AttendanceProjectsSearchState>(
                        builder: (context, registerState) {
                      if (registerState.individualAttendanceRegisterModel
                                  ?.attendanceRegister !=
                              null &&
                          registerState.individualAttendanceRegisterModel!
                              .attendanceRegister!.isNotEmpty) {
                        if (registerState
                                    .individualAttendanceRegisterModel
                                    ?.attendanceRegister!
                                    .first
                                    .attendeesEntries !=
                                null &&
                            registerState
                                .individualAttendanceRegisterModel!
                                .attendanceRegister!
                                .first
                                .attendeesEntries!
                                .isNotEmpty) {
                          context.read<AttendanceUserSearchBloc>().add(
                                SearchAttendanceUserUuidEvent(
                                    uuids: registerState
                                        .individualAttendanceRegisterModel!
                                        .attendanceRegister!
                                        .first
                                        .attendeesEntries
                                        ?.map((e) => e.individualId.toString())
                                        .toList()),
                              );
                        }
                        return BlocBuilder<AttendanceUserSearchBloc,
                                AttendanceUserSearchState>(
                            builder: (context, state) {
                          return state.maybeWhen(
                              loading: () => Loaders.circularLoader(context),
                              loaded: (UserSearchModel? userSearchModel) {
                                userList = userSearchModel!.user!.isNotEmpty
                                    ? userSearchModel!.user!
                                        .map((e) => {
                                              "name": e.name,
                                              "aadhaar": e.uuid,
                                              "bankNumber": e.mobileNumber,
                                              "uuid": e.uuid,
                                              "mobileNumber": e.mobileNumber,
                                              "tenantId": e.tenantId
                                            })
                                        .toList()
                                    : [];

                                attendeePayLoadList = addToTableList.isNotEmpty
                                    ? addToTableList
                                        .map((e) => {
                                              "registerId": widget
                                                  .attendanceRegister?.id
                                                  .toString(),
                                              "individualId": e["uuid"],
                                              "tenantId": e["tenantId"]
                                            })
                                        .toList()
                                    : [];
                                userTableList = addToTableList.isNotEmpty
                                    ? addToTableList
                                        .map((e) => TableDataModel.fromJson(e))
                                        .toList()
                                    : [];
                                var tableData =
                                    getAttendanceData(userTableList);
                                return addToTableList.isNotEmpty
                                    ? Column(
                                        crossAxisAlignment:
                                            CrossAxisAlignment.start,
                                        children: [
                                            LayoutBuilder(builder:
                                                (context, constraints) {
                                              var width = constraints.maxWidth <
                                                      760
                                                  ? 120.0
                                                  : (constraints.maxWidth / 5);
                                              return Padding(
                                                padding:
                                                    const EdgeInsets.all(8.0),
                                                child: shg_app.DigitTable(
                                                  headerList: headerList,
                                                  tableData: tableData,
                                                  leftColumnWidth: width,
                                                  rightColumnWidth: width * 3,
                                                  height: 58 +
                                                      (52.0 * tableData.length),
                                                ),
                                              );
                                            }),
                                          ])
                                    : const EmptyImage();
                              },
                              orElse: () => Container());
                        });
                      } else {
                        return const EmptyImage();
                      }
                    })
                  ]))
            ]),
          ),
          BlocBuilder<AttendeeCreateBloc, AttendeeCreateState>(
              builder: (context, state) {
            SchedulerBinding.instance.addPostFrameCallback((_) {
              state.maybeWhen(
                  error: () {
                    Notifiers.getToastMessage(
                        context,
                        AppLocalizations.of(context).translate(
                            i18.attendanceMgmt.attendanceCreateFailed),
                        'ERROR');
                  },
                  loaded: () {
                    Notifiers.getToastMessage(
                        context,
                        AppLocalizations.of(context).translate(
                            i18.attendanceMgmt.attendanceCreateSuccess),
                        'SUCCESS');
                  },
                  orElse: () => Container());
            });
            return Container();
          }),
          Align(
            alignment: Alignment.bottomCenter,
            child: Padding(
              padding: const EdgeInsets.only(
                left: 8.0,
                right: 8.0,
              ),
              child: SizedBox(
                height: 50,
                child: DigitElevatedButton(
                  onPressed: () {
                    context.read<AttendeeCreateBloc>().add(
                          CreateAttendeeEvent(
                              attendeeList: attendeePayLoadList),
                        );

                    context.router.push(const HomeRoute());
                    // if (!state.loading) {

                    // }
                  },
                  child: Text(
                      AppLocalizations.of(context).translate(i18.common.submit),
                      style: Theme.of(context)
                          .textTheme
                          .titleMedium!
                          .apply(color: Colors.white)),
                ),
              ),
            ),
          )
        ])
        //   } else {
        //     return Loaders.circularLoader(context);
        //   }
        // }),
        );
  }

  void onSuggestionSelected(user) {
    setState(() {
      searchController.text = '';
      bool hasDuplicate =
          addToTableList.where((e) => e["uuid"] == user["uuid"]).isNotEmpty;
      if (!hasDuplicate) {
        addToTableList.add({
          "name": user["name"],
          "aadhaar": user["aadhaar"],
          "bankNumber": user["bankNumber"],
          "uuid": user["uuid"],
          "mobileNumber": user["mobileNumber"],
          "tenantId": user["tenantId"]
        });
      }
    });
  }

  Widget buildTile(context, user) {
    var style = const TextStyle(fontSize: 16);
    return Container(
        padding: const EdgeInsets.symmetric(vertical: 6, horizontal: 5),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('${user["name"]}', style: style),
            Text('${user["aadhaar"]}', style: style.apply(fontSizeDelta: -2))
          ],
        ));
  }

  Future<List<dynamic>> onSearchVendorList(pattern) async {
    context.read<AttendanceUserSearchBloc>().add(
          SearchAttendanceUserEvent(mobileNumber: pattern),
        );
    await Future.delayed(const Duration(seconds: 2));
    setState(() {
      filteredUserList = userList.where((e) {
        if (e["mobileNumber"]!
            .contains(pattern.toString().trim().toLowerCase())) {
          return true;
        } else {
          return false;
        }
      }).toList();
    });
    return filteredUserList;
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
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.bankAccountNumber),
            apiKey: 'bankNumber'),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate('Del'),
            apiKey: '')
      ];

  TableDataRow getAttendanceRow(TableDataModel tableDataModel) {
    return TableDataRow([
      TableData(label: tableDataModel.name, apiKey: tableDataModel.name),
      TableData(label: tableDataModel.aadhaar, apiKey: tableDataModel.aadhaar),
      TableData(
          label: tableDataModel.bankNumber, apiKey: tableDataModel.bankNumber),
      TableData(widget: const DeleteButton(onTap: null))
    ]);
  }

  List<TableDataRow> getAttendanceData(List<TableDataModel> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }

  void onDelete() {}
}
