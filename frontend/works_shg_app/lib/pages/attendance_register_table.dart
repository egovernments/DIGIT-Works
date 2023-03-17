import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
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

import '../blocs/attendance/create_attendee.dart';
import '../blocs/attendance/de_enroll_attendee.dart';
import '../blocs/attendance/individual_search.dart';
import '../blocs/attendance/search_projects/search_individual_project.dart';
import '../blocs/localization/app_localization.dart';
import '../models/attendance/individual_list_model.dart';
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
  List<Map<String, dynamic>> attendeeTableList = [];
  List<TableDataModel> userTableList = [];
  List<Map<String, dynamic>> createAttendeePayLoadList = [];
  List<Map<String, dynamic>> deleteAttendeePayLoadList = [];
  bool searchUser = false;
  List<Map<String, dynamic>> existingAttendeeList = [];
  var tableData;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    context.read<AttendanceIndividualProjectSearchBloc>().add(
          SearchIndividualAttendanceProjectEvent(
              id: widget.registerId ?? '',
              tenantId: widget.tenantId.toString()),
        );
    await Future.delayed(const Duration(seconds: 2));
  }

  @override
  void deactivate() {
    existingAttendeeList.clear();
    userList.clear();
    filteredUserList.clear();
    addToTableList.clear();
    attendeeTableList.clear();
    userTableList.clear();
    createAttendeePayLoadList.clear();
    deleteAttendeePayLoadList.clear();
    context.read<IndividualSearchBloc>().add(
          const DisposeSearchIndividualEvent(),
        );
    context.read<AttendeeCreateBloc>().add(
          const CreateAttendeeDisposeEvent(),
        );
    context.read<AttendeeDeEnrollBloc>().add(
          const DeEnrollAttendeeDisposeEvent(),
        );
    super.deactivate();
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
                        alignment: Alignment.centerLeft,
                        margin: const EdgeInsets.all(8.0),
                        child: AutoCompleteSearchBar(
                          hintText: AppLocalizations.of(context)
                              .translate(i18.common.searchByMobileNumber),
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
                    BlocListener<AttendanceIndividualProjectSearchBloc,
                        AttendanceIndividualProjectSearchState>(
                      listener: (context, registerState) {
                        registerState.maybeWhen(
                            loading: () => Loaders.circularLoader(context),
                            initial: () {
                              existingAttendeeList.clear();
                            },
                            loaded: (AttendanceRegistersModel?
                                individualAttendanceRegisterModel) {
                              if (individualAttendanceRegisterModel!
                                  .attendanceRegister!.isNotEmpty) {
                                individualAttendanceRegisterModel!
                                    .attendanceRegister!
                                    .map((e) => {
                                          i18.workOrder.workOrderNo:
                                              e.attendanceRegisterAdditionalDetails
                                                      ?.contractId ??
                                                  'NA',
                                          i18.attendanceMgmt.registerId:
                                              e.registerNumber,
                                          i18.attendanceMgmt.projectId:
                                              e.attendanceRegisterAdditionalDetails
                                                      ?.projectId ??
                                                  'NA',
                                          i18.attendanceMgmt.projectDesc:
                                              e.attendanceRegisterAdditionalDetails
                                                      ?.projectName ??
                                                  'NA'
                                        })
                                    .toList();
                                if (individualAttendanceRegisterModel
                                            .attendanceRegister!
                                            .first
                                            .attendeesEntries !=
                                        null &&
                                    individualAttendanceRegisterModel
                                        .attendanceRegister!
                                        .first
                                        .attendeesEntries!
                                        .where((e) =>
                                            e.denrollmentDate == null ||
                                            !(e.denrollmentDate! <=
                                                DateTime.now()
                                                    .millisecondsSinceEpoch))
                                        .toList()
                                        .isNotEmpty) {
                                  existingAttendeeList =
                                      individualAttendanceRegisterModel!
                                          .attendanceRegister!
                                          .first
                                          .attendeesEntries!
                                          .where((att) =>
                                              att.denrollmentDate == null ||
                                              !(att.denrollmentDate! <=
                                                  DateTime.now()
                                                      .millisecondsSinceEpoch))
                                          .map((e) => {
                                                "uuid":
                                                    e.individualId.toString()
                                              })
                                          .toList();
                                  context.read<IndividualSearchBloc>().add(
                                        SearchIndividualIdEvent(
                                            ids: individualAttendanceRegisterModel!
                                                .attendanceRegister!
                                                .first
                                                .attendeesEntries
                                                ?.where((att) =>
                                                    att.denrollmentDate ==
                                                        null ||
                                                    !(att.denrollmentDate! <=
                                                        DateTime.now()
                                                            .millisecondsSinceEpoch))
                                                .map((e) =>
                                                    e.individualId.toString())
                                                .toList(),
                                            tenant: widget.tenantId),
                                      );
                                }
                              }
                            },
                            error: (String? error) => Notifiers.getToastMessage(
                                context, error.toString(), 'ERROR'),
                            orElse: () => Container());
                      },
                      child: BlocBuilder<IndividualSearchBloc,
                          IndividualSearchState>(builder: (context, userState) {
                        return userState.maybeWhen(
                            loading: () => Loaders.circularLoader(context),
                            initial: () {
                              existingAttendeeList.clear();
                              return const EmptyImage(align: Alignment.center);
                            },
                            loaded: (IndividualListModel? individualListModel) {
                              userList =
                                  individualListModel!.Individual!.isNotEmpty
                                      ? individualListModel!.Individual!
                                          .map((e) => {
                                                "name": e.name?.givenName,
                                                "aadhaar": e.identifiers?.first
                                                        .identifierId ??
                                                    e.individualId,
                                                "individualCode":
                                                    e.individualId,
                                                "skill": AppLocalizations.of(
                                                        context)
                                                    .translate(
                                                        '${e.skills!.first.level?.toUpperCase()}_${e.skills!.first.type?.toUpperCase()}'),
                                                "individualId": e.id,
                                                "uuid": e.id,
                                                "individualGaurdianName":
                                                    e.fatherName,
                                                "mobileNumber": e.mobileNumber,
                                                "tenantId": e.tenantId
                                              })
                                          .toList()
                                      : [];
                              if (userList.isNotEmpty) {
                                for (var user in userList) {
                                  var userToAdd = {
                                    "name": user["name"],
                                    "aadhaar": user["aadhaar"],
                                    "individualCode": user["individualCode"],
                                    "uuid": user["uuid"],
                                    "skill": user["skill"],
                                    "individualGaurdianName":
                                        user["individualGaurdianName"],
                                    "individualId": user["individualId"],
                                    "mobileNumber": user["mobileNumber"],
                                    "tenantId": user["tenantId"]
                                  };
                                  bool hasDuplicate = attendeeTableList
                                      .where((e) =>
                                          e["individualId"] ==
                                          user["individualId"])
                                      .isNotEmpty;

                                  if (!hasDuplicate && !searchUser) {
                                    attendeeTableList.add(userToAdd);
                                  }
                                }
                                userTableList = attendeeTableList.isNotEmpty
                                    ? attendeeTableList
                                        .map((e) => TableDataModel.fromJson(e))
                                        .toList()
                                    : [];
                                tableData = getAttendanceData(userTableList);
                              }
                              return userTableList.isNotEmpty ||
                                      deleteAttendeePayLoadList.isNotEmpty
                                  ? Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                          LayoutBuilder(
                                              builder: (context, constraints) {
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
                                                    (52.0 *
                                                        (tableData.length + 1)),
                                              ),
                                            );
                                          }),
                                          BlocListener<AttendeeCreateBloc,
                                              AttendeeCreateState>(
                                            listener: (context, createState) {
                                              createState.maybeWhen(
                                                  loaded: () {
                                                    Notifiers.getToastMessage(
                                                        context,
                                                        AppLocalizations.of(
                                                                context)
                                                            .translate(i18
                                                                .attendanceMgmt
                                                                .attendeeCreateSuccess),
                                                        'SUCCESS');
                                                  },
                                                  error: (String? error) {
                                                    Notifiers.getToastMessage(
                                                        context,
                                                        AppLocalizations.of(
                                                                context)
                                                            .translate(error
                                                                .toString()),
                                                        'ERROR');
                                                  },
                                                  orElse: () {});
                                            },
                                            child: BlocListener<
                                                AttendeeDeEnrollBloc,
                                                AttendeeDeEnrollState>(
                                              listener:
                                                  (context, deEnrollState) {
                                                deEnrollState.maybeWhen(
                                                    loaded: () {
                                                      Notifiers.getToastMessage(
                                                          context,
                                                          AppLocalizations.of(
                                                                  context)
                                                              .translate(i18
                                                                  .attendanceMgmt
                                                                  .attendeeDeEnrollSuccess),
                                                          'SUCCESS');
                                                    },
                                                    error: (String? error) {
                                                      Notifiers.getToastMessage(
                                                          context,
                                                          AppLocalizations.of(
                                                                  context)
                                                              .translate(error
                                                                  .toString()),
                                                          'ERROR');
                                                    },
                                                    orElse: () => Container());
                                              },
                                              child: Container(),
                                            ),
                                          ),
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
                                                  onPressed:
                                                      createAttendeePayLoadList
                                                                  .isEmpty &&
                                                              deleteAttendeePayLoadList
                                                                  .isEmpty
                                                          ? null
                                                          : () {
                                                              if (createAttendeePayLoadList
                                                                      .isNotEmpty &&
                                                                  deleteAttendeePayLoadList
                                                                      .isNotEmpty) {
                                                                context
                                                                    .read<
                                                                        AttendeeCreateBloc>()
                                                                    .add(
                                                                      CreateAttendeeEvent(
                                                                          attendeeList:
                                                                              createAttendeePayLoadList),
                                                                    );
                                                                context
                                                                    .read<
                                                                        AttendeeDeEnrollBloc>()
                                                                    .add(
                                                                      DeEnrollAttendeeEvent(
                                                                          attendeeList:
                                                                              deleteAttendeePayLoadList),
                                                                    );
                                                              } else if (createAttendeePayLoadList
                                                                      .isNotEmpty &&
                                                                  deleteAttendeePayLoadList
                                                                      .isEmpty) {
                                                                context
                                                                    .read<
                                                                        AttendeeCreateBloc>()
                                                                    .add(
                                                                      CreateAttendeeEvent(
                                                                          attendeeList:
                                                                              createAttendeePayLoadList),
                                                                    );
                                                              } else if (deleteAttendeePayLoadList
                                                                      .isNotEmpty &&
                                                                  createAttendeePayLoadList
                                                                      .isEmpty) {
                                                                context
                                                                    .read<
                                                                        AttendeeDeEnrollBloc>()
                                                                    .add(
                                                                      DeEnrollAttendeeEvent(
                                                                          attendeeList:
                                                                              deleteAttendeePayLoadList),
                                                                    );
                                                              } else {}
                                                              context.router.push(
                                                                  const HomeRoute());
                                                            },
                                                  child: Text(
                                                      AppLocalizations.of(
                                                              context)
                                                          .translate(i18
                                                              .common.submit),
                                                      style: Theme.of(context)
                                                          .textTheme
                                                          .titleMedium!
                                                          .apply(
                                                              color: Colors
                                                                  .white)),
                                                ),
                                              ),
                                            ),
                                          )
                                        ])
                                  : const EmptyImage(align: Alignment.center);
                            },
                            error: (String? error) => Notifiers.getToastMessage(
                                context,
                                AppLocalizations.of(context)
                                    .translate(error.toString()),
                                'ERROR'),
                            orElse: () => Container());
                      }),
                    )
                  ]))
            ]),
          ),
        ]));
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
          "individualCode": user["individualCode"],
          "individualGaurdianName": user["individualGaurdianName"],
          "skill": user["skill"],
          "uuid": user["uuid"],
          "mobileNumber": user["mobileNumber"],
          "tenantId": user["tenantId"]
        });
        for (final obj in addToTableList) {
          bool existingObj = attendeeTableList
              .where((obj1) => obj1['uuid'] == obj['uuid'])
              .isNotEmpty;
          if (!existingObj) {
            attendeeTableList.add(obj);
            deleteAttendeePayLoadList
                .removeWhere((e) => e["individualId"] == user["uuid"]);
          }
        }
        createAttendeePayLoadList = addToTableList.isNotEmpty &&
                existingAttendeeList
                    .where((e) => e["uuid"] == user["uuid"])
                    .isEmpty
            ? addToTableList
                .map((e) => {
                      "registerId": widget.attendanceRegister?.id.toString(),
                      "individualId": e["uuid"],
                      "tenantId": e["tenantId"],
                      "additionalDetails": {
                        "individualName": e["name"],
                        "individualID": e["individualCode"],
                        "individualGaurdianName": e["individualGaurdianName"],
                        "identifierId": e["aadhaar"]
                      }
                    })
                .toList()
            : [];
        deleteAttendeePayLoadList
            .removeWhere((e) => e["individualId"] == user["uuid"]);
      }
      searchUser = true;
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
            Text('${user["individualCode"]}',
                style: style.apply(fontSizeDelta: -2))
          ],
        ));
  }

  Future<List<dynamic>> onSearchVendorList(pattern) async {
    searchUser = true;
    context.read<IndividualSearchBloc>().add(
          SearchIndividualEvent(mobileNumber: pattern, tenant: widget.tenantId),
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
              .translate(i18.common.fatherName),
          apiKey: 'individualGaurdianName',
        ),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.attendanceMgmt.skill),
            apiKey: 'skill'),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.action),
            apiKey: '')
      ];

  TableDataRow getAttendanceRow(TableDataModel tableDataModel) {
    return TableDataRow([
      TableData(label: tableDataModel.name, apiKey: tableDataModel.name),
      TableData(
          label: tableDataModel.individualGaurdianName,
          apiKey: tableDataModel.individualGaurdianName),
      TableData(
          label: AppLocalizations.of(scaffoldMessengerKey.currentContext!)
              .translate(tableDataModel.skill.toString()),
          apiKey: tableDataModel.skill),
      TableData(
          widget: DeleteButton(
              onTap: () => onDelete(tableDataModel.uuid.toString())))
    ]);
  }

  List<TableDataRow> getAttendanceData(List<TableDataModel> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }

  void onDelete(String uuid) {
    switch (createAttendeePayLoadList.length > 1 &&
        existingAttendeeList.where((e) => e["uuid"] == uuid).isNotEmpty) {
      case true:
        setState(() {
          searchUser = true;
          createAttendeePayLoadList
              .removeWhere((e) => e['individualId'] == uuid);
          attendeeTableList.removeWhere((e) => e['uuid'] == uuid);
          userList.removeWhere((e) => e['uuid'] == uuid);
          addToTableList.removeWhere((e) => e['uuid'] == uuid);
          userTableList.removeWhere((e) => e.uuid == uuid);
          deleteAttendeePayLoadList.add({
            "registerId": widget.registerId.toString(),
            "individualId": uuid.toString(),
            "enrollmentDate": null,
            "denrollmentDate": DateTime.now()
                .subtract(const Duration(minutes: 1, seconds: 30))
                .millisecondsSinceEpoch,
            "tenantId": widget.tenantId.toString()
          });
        });
        break;
      case false:
        setState(() {
          searchUser = true;
          createAttendeePayLoadList
              .removeWhere((e) => e['individualId'] == uuid);
          attendeeTableList.removeWhere((e) => e['uuid'] == uuid);
          userList.removeWhere((e) => e['uuid'] == uuid);
          addToTableList.removeWhere((e) => e['uuid'] == uuid);
          userTableList.removeWhere((e) => e.uuid == uuid);
          if (existingAttendeeList.where((e) => e["uuid"] == uuid).isNotEmpty) {
            deleteAttendeePayLoadList.add({
              "registerId": widget.registerId.toString(),
              "individualId": uuid.toString(),
              "enrollmentDate": null,
              "denrollmentDate": DateTime.now()
                  .subtract(const Duration(minutes: 1, seconds: 30))
                  .millisecondsSinceEpoch,
              "tenantId": widget.tenantId.toString()
            });
          }
        });
        break;
      default:
        break;
    }
  }
}
