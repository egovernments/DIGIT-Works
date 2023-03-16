import 'package:digit_components/widgets/digit_elevated_button.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:works_shg_app/models/attendance/attendance_registry_model.dart';
import 'package:works_shg_app/models/table/table_model.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/constants.dart';
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
import '../widgets/Back.dart';
import '../widgets/SideBar.dart';
import '../widgets/WorkDetailsCard.dart';
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
  Map<String, dynamic> registerDetails = {};
  List<Map<String, dynamic>> existingAttendeeList = [];
  List<AttendeesEntries> existingAttendees = [];
  var tableData;
  List<IndividualModel> filteredIndividual = [];
  IndividualListModel searchIndividualList = IndividualListModel();

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
              child: BlocListener<AttendanceIndividualProjectSearchBloc,
                  AttendanceIndividualProjectSearchState>(
                listener: (context, registerState) {
                  registerState.maybeWhen(
                      orElse: () => false,
                      error: (String? error) => Notifiers.getToastMessage(
                          context, error.toString(), 'ERROR'),
                      loading: () => Loaders.circularLoader(context),
                      loaded: (AttendanceRegistersModel?
                          individualAttendanceRegisterModel) {
                        registerDetails = {
                          i18.workOrder.workOrderNo:
                              individualAttendanceRegisterModel
                                      ?.attendanceRegister
                                      ?.first
                                      .attendanceRegisterAdditionalDetails
                                      ?.contractId ??
                                  'NA',
                          i18.attendanceMgmt.registerId:
                              individualAttendanceRegisterModel
                                  ?.attendanceRegister?.first.registerNumber,
                          i18.attendanceMgmt.projectId:
                              individualAttendanceRegisterModel
                                      ?.attendanceRegister
                                      ?.first
                                      .attendanceRegisterAdditionalDetails
                                      ?.projectId ??
                                  'NA',
                          i18.attendanceMgmt.projectDesc:
                              individualAttendanceRegisterModel
                                      ?.attendanceRegister
                                      ?.first
                                      .attendanceRegisterAdditionalDetails
                                      ?.projectName ??
                                  'NA'
                        };
                        if (individualAttendanceRegisterModel
                                    ?.attendanceRegister
                                    ?.first
                                    .attendeesEntries !=
                                null &&
                            individualAttendanceRegisterModel!
                                .attendanceRegister!.first.attendeesEntries!
                                .where((e) =>
                                    e.denrollmentDate == null ||
                                    !(e.denrollmentDate! <=
                                        DateTime.now().millisecondsSinceEpoch))
                                .toList()
                                .isNotEmpty) {
                          existingAttendees = individualAttendanceRegisterModel
                              .attendanceRegister!.first.attendeesEntries!
                              .where((e) =>
                                  e.denrollmentDate == null ||
                                  !(e.denrollmentDate! <=
                                      DateTime.now().millisecondsSinceEpoch))
                              .toList();
                          attendeeTableList = existingAttendees.isNotEmpty
                              ? existingAttendees
                                  .map((e) => {
                                        "name":
                                            e.additionalDetails?.individualName,
                                        "aadhaar": e.additionalDetails
                                                ?.identifierId ??
                                            e.additionalDetails?.individualID,
                                        "individualId":
                                            e.additionalDetails?.individualID,
                                        "bankNumber":
                                            e.additionalDetails?.bankNumber ??
                                                'NA'
                                      })
                                  .toList()
                              : [];
                          userTableList = attendeeTableList.isNotEmpty
                              ? attendeeTableList
                                  .map((e) => TableDataModel.fromJson(e))
                                  .toList()
                              : [];
                          tableData = getAttendanceData(userTableList);
                        }
                      });
                },
                child: BlocBuilder<AttendanceIndividualProjectSearchBloc,
                        AttendanceIndividualProjectSearchState>(
                    builder: (context, registerState) {
                  return registerState.maybeWhen(
                      orElse: () => Container(),
                      loading: () => Loaders.circularLoader(context),
                      loaded: (AttendanceRegistersModel?
                          individualAttendanceRegisterModel) {
                        return CustomScrollView(slivers: [
                          SliverList(
                              delegate: SliverChildListDelegate([
                            Back(
                              backLabel: AppLocalizations.of(context)
                                  .translate(i18.common.back),
                            ),
                            WorkDetailsCard([registerDetails]),
                          ])),
                          SliverToBoxAdapter(
                              child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                const SizedBox(
                                  height: 20,
                                ),
                                BlocListener<IndividualSearchBloc,
                                        IndividualSearchState>(
                                    listener: (context, individualState) {
                                  individualState.maybeWhen(
                                      orElse: () => false,
                                      initial: () {
                                        searchIndividualList.Individual = [];
                                      },
                                      error: (String? error) =>
                                          Notifiers.getToastMessage(context,
                                              error.toString(), 'ERROR'),
                                      loading: () =>
                                          Loaders.circularLoader(context),
                                      loaded: (IndividualListModel?
                                          individualListModel) {
                                        if (individualListModel!
                                            .Individual!.isNotEmpty) {
                                          userList =
                                              individualListModel.Individual!
                                                  .map((e) => {
                                                        "name":
                                                            e.name?.givenName ??
                                                                'NA',
                                                        "aadhaar": e
                                                                .identifiers
                                                                ?.first
                                                                .identifierId ??
                                                            'NA',
                                                        "mobileNumber":
                                                            e.mobileNumber,
                                                        "tenantId": e.tenantId,
                                                        "individualId":
                                                            e.individualId
                                                      })
                                                  .toList();
                                          // searchIndividualList.copyWith(
                                          //     individuals:
                                          //         individualListModel
                                          //             .individuals);
                                        }
                                      });
                                }, child: BlocBuilder<IndividualSearchBloc,
                                            IndividualSearchState>(
                                        builder: (context, individualState) {
                                  return Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        Container(
                                            alignment: Alignment.centerLeft,
                                            margin: const EdgeInsets.all(8.0),
                                            child: AutoCompleteSearchBar(
                                              hintText:
                                                  AppLocalizations.of(context)
                                                      .translate(i18.common
                                                          .searchByNameAadhaar),
                                              controller: searchController,
                                              suggestionsBoxController:
                                                  suggestionsBoxController,
                                              onSuggestionSelected:
                                                  onSuggestionSelected,
                                              callBack: onSearchVendorList,
                                              minCharsForSuggestions: 10,
                                              maxLength: 10,
                                              listTile: buildTile,
                                              inputFormatter: [
                                                FilteringTextInputFormatter
                                                    .allow(
                                                        RegExp("[a-zA-Z0-9]"))
                                              ],
                                              labelText: '',
                                            )),
                                        const SizedBox(
                                          height: 20,
                                        ),
                                      ]);
                                })),
                                BlocBuilder<
                                    AttendanceIndividualProjectSearchBloc,
                                    AttendanceIndividualProjectSearchState>(
                                  builder: (context, registerState) {
                                    return registerState.maybeWhen(
                                        loaded: (AttendanceRegistersModel?
                                            individualAttendanceRegisterModel) {
                                          return userTableList.isNotEmpty
                                              ? LayoutBuilder(builder:
                                                  (context, constraints) {
                                                  var width =
                                                      constraints.maxWidth < 760
                                                          ? 120.0
                                                          : (constraints
                                                                  .maxWidth /
                                                              5);
                                                  return Padding(
                                                    padding:
                                                        const EdgeInsets.all(
                                                            8.0),
                                                    child: shg_app.DigitTable(
                                                      headerList: headerList,
                                                      tableData: tableData,
                                                      leftColumnWidth: width,
                                                      rightColumnWidth:
                                                          width * 3,
                                                      height: 58 +
                                                          (52.0 *
                                                              (tableData
                                                                      .length +
                                                                  1)),
                                                    ),
                                                  );
                                                })
                                              : const EmptyImage(
                                                  align: Alignment.center,
                                                  label:
                                                      'There are no attendees for the register',
                                                );
                                        },
                                        orElse: () => Container());
                                  },
                                ),
                                BlocListener<AttendeeCreateBloc,
                                    AttendeeCreateState>(
                                  listener: (context, createState) {
                                    createState.maybeWhen(
                                        loaded: () {
                                          Notifiers.getToastMessage(
                                              context,
                                              AppLocalizations.of(context)
                                                  .translate(i18.attendanceMgmt
                                                      .attendeeCreateSuccess),
                                              'SUCCESS');
                                        },
                                        error: (String? error) {
                                          Notifiers.getToastMessage(
                                              context,
                                              AppLocalizations.of(context)
                                                  .translate(error.toString()),
                                              'ERROR');
                                        },
                                        orElse: () {});
                                  },
                                  child: BlocListener<AttendeeDeEnrollBloc,
                                      AttendeeDeEnrollState>(
                                    listener: (context, deEnrollState) {
                                      deEnrollState.maybeWhen(
                                          loaded: () {
                                            Notifiers.getToastMessage(
                                                context,
                                                AppLocalizations.of(context)
                                                    .translate(i18
                                                        .attendanceMgmt
                                                        .attendeeDeEnrollSuccess),
                                                'SUCCESS');
                                          },
                                          error: (String? error) {
                                            Notifiers.getToastMessage(
                                                context,
                                                AppLocalizations.of(context)
                                                    .translate(
                                                        error.toString()),
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
                                            createAttendeePayLoadList.isEmpty &&
                                                    deleteAttendeePayLoadList
                                                        .isEmpty
                                                ? null
                                                : () {
                                                    print('create');
                                                    print(
                                                        createAttendeePayLoadList);
                                                    print('delete');
                                                    print(
                                                        deleteAttendeePayLoadList);
                                                    if (createAttendeePayLoadList
                                                            .isNotEmpty &&
                                                        deleteAttendeePayLoadList
                                                            .isNotEmpty) {
                                                      // context
                                                      //     .read<
                                                      //         AttendeeCreateBloc>()
                                                      //     .add(
                                                      //       CreateAttendeeEvent(
                                                      //           attendeeList:
                                                      //               createAttendeePayLoadList),
                                                      //     );
                                                      // context
                                                      //     .read<
                                                      //         AttendeeDeEnrollBloc>()
                                                      //     .add(
                                                      //       DeEnrollAttendeeEvent(
                                                      //           attendeeList:
                                                      //               deleteAttendeePayLoadList),
                                                      //     );
                                                    } else if (createAttendeePayLoadList
                                                            .isNotEmpty &&
                                                        deleteAttendeePayLoadList
                                                            .isEmpty) {
                                                      // context
                                                      //     .read<
                                                      //         AttendeeCreateBloc>()
                                                      //     .add(
                                                      //       CreateAttendeeEvent(
                                                      //           attendeeList:
                                                      //               createAttendeePayLoadList),
                                                      //     );
                                                    } else if (deleteAttendeePayLoadList
                                                            .isNotEmpty &&
                                                        createAttendeePayLoadList
                                                            .isEmpty) {
                                                      // context
                                                      //     .read<
                                                      //         AttendeeDeEnrollBloc>()
                                                      //     .add(
                                                      //       DeEnrollAttendeeEvent(
                                                      //           attendeeList:
                                                      //               deleteAttendeePayLoadList),
                                                      //     );
                                                    } else {}
                                                    // context.router
                                                    //     .push(const HomeRoute());
                                                  },
                                        child: Text(
                                            AppLocalizations.of(context)
                                                .translate(i18.common.submit),
                                            style: Theme.of(context)
                                                .textTheme
                                                .titleMedium!
                                                .apply(color: Colors.white)),
                                      ),
                                    ),
                                  ),
                                )
                              ]))
                        ]);
                      });
                }),
              ))
        ]));
  }

  void onSuggestionSelected(user) {
    print('selected');
    print(user);
    setState(() {
      searchController.text = '';
      bool hasDuplicate = addToTableList
          .where((e) => e["individualId"] == user["individualId"])
          .isNotEmpty;
      if (!hasDuplicate) {
        addToTableList.add({
          "name": user["name"],
          "aadhaar": user["aadhaar"],
          "bankNumber": user["bankNumber"] ?? 'NA',
          "individualId": user["individualId"],
          "mobileNumber": user["mobileNumber"],
          "tenantId": user["tenantId"]
        });
        for (final obj in addToTableList) {
          print('1');
          bool existingObj = attendeeTableList
              .where((obj1) => obj1['individualId'] == obj['individualId'])
              .isNotEmpty;
          print('2');
          if (!existingObj) {
            print(obj);
            print('existingObj');
            attendeeTableList.add(obj);
            print('added');
            deleteAttendeePayLoadList
                .removeWhere((e) => e["individualId"] == user["individualId"]);
          }
        }
        createAttendeePayLoadList = addToTableList.isNotEmpty &&
                existingAttendeeList
                    .where((e) => e["individualId"] == user["individualId"])
                    .isEmpty
            ? addToTableList
                .map((e) => {
                      "registerId": widget.attendanceRegister?.id.toString(),
                      "individualId": e["individualId"],
                      "tenantId": e["tenantId"],
                      "additionalDetails": {"name": e["name"]}
                    })
                .toList()
            : [];
        deleteAttendeePayLoadList
            .removeWhere((e) => e["individualId"] == user["individualId"]);
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
            Text('${user['name']}', style: style),
            Text('${user['individualId']}',
                style: style.apply(fontSizeDelta: -2))
          ],
        ));
  }

  Future<List<dynamic>> onSearchVendorList(pattern) async {
    searchUser = true;
    context.read<IndividualSearchBloc>().add(
          SearchIndividualEvent(mobileNumber: pattern, tenant: 'pg.citya'),
        );
    await Future.delayed(const Duration(seconds: 2));
    print(userList);
    setState(() {
      // filteredIndividual = searchIndividualList.individuals!.where((e) {
      //   if (e.mobileNumber!.contains(pattern.toString().trim().toLowerCase())) {
      //     return true;
      //   } else {
      //     return false;
      //   }
      // }).toList();
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
                .translate(i18.common.action),
            apiKey: '')
      ];

  TableDataRow getAttendanceRow(TableDataModel tableDataModel) {
    return TableDataRow([
      TableData(label: tableDataModel.name, apiKey: tableDataModel.name),
      TableData(label: tableDataModel.aadhaar, apiKey: tableDataModel.aadhaar),
      TableData(
          label: tableDataModel.bankNumber, apiKey: tableDataModel.bankNumber),
      TableData(
          widget: DeleteButton(
              onTap: () => onDelete(tableDataModel.individualId.toString())))
    ]);
  }

  List<TableDataRow> getAttendanceData(List<TableDataModel> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }

  void onDelete(String individualId) {
    switch (createAttendeePayLoadList.length > 1 &&
        existingAttendeeList
            .where((e) => e["individualId"] == individualId)
            .isNotEmpty) {
      case true:
        setState(() {
          searchUser = true;
          createAttendeePayLoadList
              .removeWhere((e) => e['individualId'] == individualId);
          attendeeTableList
              .removeWhere((e) => e['individualId'] == individualId);
          userList.removeWhere((e) => e['individualId'] == individualId);
          addToTableList.removeWhere((e) => e['individualId'] == individualId);
          userTableList.removeWhere((e) => e.individualId == individualId);
          deleteAttendeePayLoadList.add({
            "registerId": widget.registerId.toString(),
            "individualId": individualId.toString(),
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
              .removeWhere((e) => e['individualId'] == individualId);
          attendeeTableList
              .removeWhere((e) => e['individualId'] == individualId);
          userList.removeWhere((e) => e['individualId'] == individualId);
          addToTableList.removeWhere((e) => e['individualId'] == individualId);
          userTableList.removeWhere((e) => e.individualId == individualId);
          if (existingAttendeeList
              .where((e) => e["individualId"] == individualId)
              .isNotEmpty) {
            deleteAttendeePayLoadList.add({
              "registerId": widget.registerId.toString(),
              "individualId": individualId.toString(),
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
