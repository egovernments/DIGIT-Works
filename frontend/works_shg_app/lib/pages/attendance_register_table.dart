import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/digit_button.dart'
    as ui_button;
import 'package:digit_ui_components/widgets/molecules/digit_card.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:works_shg_app/data/repositories/attendence_repository/individual_repository.dart';
import 'package:works_shg_app/models/attendance/attendance_registry_model.dart';
import 'package:works_shg_app/models/table/table_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/work_details_card.dart';
import 'package:works_shg_app/widgets/atoms/delete_button.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/molecules/digit_table.dart' as shg_app;

import '../blocs/attendance/create_attendee.dart';
import '../blocs/attendance/de_enroll_attendee.dart';
import '../blocs/attendance/individual_wms_search.dart';
import '../blocs/attendance/search_projects/search_individual_project.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../data/remote_client.dart';
import '../models/attendance/individual_list_model.dart';
import '../router/app_router.dart';
import '../utils/models.dart';
import '../widgets/atoms/auto_complete_search_bar.dart';
import '../widgets/loaders.dart' as shg_loader;

@RoutePage()
class AttendanceRegisterTablePage extends StatefulWidget {
  final String registerId;
  final String tenantId;
  const AttendanceRegisterTablePage(@PathParam('registerId') this.registerId,
      @PathParam('tenantId') this.tenantId,
      {super.key});

  @override
  State<StatefulWidget> createState() {
    return _AttendanceRegisterTablePage();
  }
}

class _AttendanceRegisterTablePage extends State<AttendanceRegisterTablePage> {
  var searchController = TextEditingController();
  int minCharForSuggestions = 3;
  var suggestionsBoxController = SuggestionsBoxController();
  List<Map<String, dynamic>> userList = [];
  List<Map<String, dynamic>> filteredUserList = [];
  List<Map<String, dynamic>> addToTableList = [];
  List<Map<String, dynamic>> attendeeTableList = [];
  List<TableDataModel> userTableList = [];
  List<Map<String, dynamic>> registerDetails = [];
  String registerId = '';
  List<Map<String, dynamic>> createAttendeePayLoadList = [];
  List<Map<String, dynamic>> deleteAttendeePayLoadList = [];
  bool searchUser = false;
  List<Map<String, dynamic>> existingAttendeeList = [];
  List<TableDataRow> tableData = [];
  int registerStartDate = DateTime.now().millisecondsSinceEpoch;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    tableData.clear();
    existingAttendeeList.clear();
    userList.clear();
    filteredUserList.clear();
    addToTableList.clear();
    attendeeTableList.clear();
    userTableList.clear();
    createAttendeePayLoadList.clear();
    deleteAttendeePayLoadList.clear();
    searchUser = false;
    context.read<AttendanceIndividualProjectSearchBloc>().add(
          SearchIndividualAttendanceRegisterEvent(
              registerNumber: widget.registerId,
              tenantId: widget.tenantId.toString()),
        );
    await Future.delayed(const Duration(seconds: 1));
  }

  @override
  void deactivate() {
    context.read<AttendanceIndividualProjectSearchBloc>().add(
          const DisposeIndividualAttendanceRegisterEvent(),
        );
    context.read<IndividualWMSSearchBloc>().add(
          const DisposeSearchWMSIndividualEvent(),
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
  void dispose() {
    // TODO: implement dispose
    searchController.clear();
    searchController.dispose();
    suggestionsBoxController.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return PopScope(
      canPop: true,
      onPopInvoked: (value) async {
        if (context.router.stack[1].routeData.path.contains('work-order')) {
          context.router.popUntilRouteWithPath('home');
          context.router.push(const WorkOrderRoute());
        } else {
          context.router.popUntilRouteWithPath('home');
          context.router.push(const TrackAttendanceInboxRoute());
        }
      },
      child: BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localState) {
          return Scaffold(
            backgroundColor: Theme.of(context).colorTheme.generic.background,
            bottomNavigationBar: DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                children: [
                  InkWell(
                    onTap: createAttendeePayLoadList.isEmpty &&
                            deleteAttendeePayLoadList.isEmpty
                        ? null
                        : () {
                            if (createAttendeePayLoadList.isNotEmpty &&
                                deleteAttendeePayLoadList.isNotEmpty) {
                              context.read<AttendeeCreateBloc>().add(
                                    CreateAttendeeEvent(
                                        attendeeList:
                                            createAttendeePayLoadList),
                                  );
                              // context.read<AttendeeDeEnrollBloc>().add(
                              //       DeEnrollAttendeeEvent(
                              //           attendeeList:
                              //               deleteAttendeePayLoadList),
                              //     );
                            } else if (createAttendeePayLoadList.isNotEmpty &&
                                deleteAttendeePayLoadList.isEmpty) {
                              context.read<AttendeeCreateBloc>().add(
                                    CreateAttendeeEvent(
                                        attendeeList:
                                            createAttendeePayLoadList),
                                  );
                            } else if (deleteAttendeePayLoadList.isNotEmpty &&
                                createAttendeePayLoadList.isEmpty) {
                              // context.read<AttendeeDeEnrollBloc>().add(
                              //       DeEnrollAttendeeEvent(
                              //           attendeeList:
                              //               deleteAttendeePayLoadList),
                              //     );
                            } else {}
                          },
                    child: IgnorePointer(
                      child: ui_button.Button(
                        isDisabled:
                            (createAttendeePayLoadList.isEmpty ? true : false),
                        //old
                        // isDisabled: (createAttendeePayLoadList.isEmpty &&
                        //     deleteAttendeePayLoadList.isEmpty),
                        type: ButtonType.primary,
                        size: ButtonSize.large,
                        mainAxisSize: MainAxisSize.max,
                        onPressed: () {},
                        label: AppLocalizations.of(context)
                            .translate(i18.common.submit),
                      ),
                    ),
                  ),
                ]),
            // appBar: customAppBar(),
            // drawer: const MySideBar(),
            body: Stack(
              children: [
                Container(
                  color: Theme.of(context).colorTheme.generic.background,
                  padding: const EdgeInsets.only(left: 8, right: 8, bottom: 16),
                  height: MediaQuery.of(context).size.height - 50,
                  child: CustomScrollView(
                    slivers: [
                      SliverList(
                        delegate: SliverChildListDelegate(
                          [
                            Padding(
                              padding: EdgeInsets.symmetric(
                                horizontal:
                                    Theme.of(context).spacerTheme.spacer4,
                                vertical: Theme.of(context).spacerTheme.spacer4,
                              ),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.start,
                                children: [
                                  BackNavigationButton(
                                    backNavigationButtonThemeData:
                                        const BackNavigationButtonThemeData()
                                            .copyWith(
                                                context: context,
                                                backButtonIcon: Icon(
                                                  Icons
                                                      .arrow_circle_left_outlined,
                                                  size: MediaQuery.of(context)
                                                              .size
                                                              .width <
                                                          500
                                                      ? Theme.of(context)
                                                          .spacerTheme
                                                          .spacer5
                                                      : Theme.of(context)
                                                          .spacerTheme
                                                          .spacer6,
                                                  color: Theme.of(context)
                                                      .colorTheme
                                                      .primary
                                                      .primary2,
                                                )),
                                    backButtonText: AppLocalizations.of(context)
                                        .translate(i18.common.back),
                                    handleBack: () {
                                      if (context.router.stack[1].routeData.path
                                          .contains('work-orders')) {
                                        context.router
                                            .popUntilRouteWithPath('home');
                                        context.router
                                            .push(const WorkOrderRoute());
                                      } else {
                                        context.router
                                            .popUntilRouteWithPath('home');
                                        context.router.push(
                                            const TrackAttendanceInboxRoute());
                                      }
                                    },
                                  ),
                                ],
                              ),
                            ),
                            BlocListener<AttendanceIndividualProjectSearchBloc,
                                AttendanceIndividualProjectSearchState>(
                              listener: (context, registerState) {
                                registerState.maybeWhen(
                                    loading: () =>
                                        shg_loader.Loaders.circularLoader(
                                            context),
                                    initial: () {
                                      existingAttendeeList.clear();
                                    },
                                    loaded: (AttendanceRegistersModel?
                                        individualAttendanceRegisterModel) {
                                      registerId =
                                          individualAttendanceRegisterModel!
                                              .attendanceRegister!.first.id
                                              .toString();
                                      registerDetails =
                                          individualAttendanceRegisterModel
                                              .attendanceRegister!
                                              .map((e) => {
                                                    i18.workOrder.workOrderNo:
                                                        e.attendanceRegisterAdditionalDetails
                                                                ?.contractId ??
                                                            t.translate(i18
                                                                .common
                                                                .noValue),
                                                    i18.attendanceMgmt
                                                            .registerId:
                                                        e.registerNumber,
                                                    i18.attendanceMgmt
                                                            .projectId:
                                                        e.attendanceRegisterAdditionalDetails
                                                                ?.projectId ??
                                                            t.translate(i18
                                                                .common
                                                                .noValue),
                                                    i18.attendanceMgmt
                                                            .projectName:
                                                        e.attendanceRegisterAdditionalDetails
                                                                ?.projectName ??
                                                            t.translate(i18
                                                                .common
                                                                .noValue),
                                                    i18.attendanceMgmt
                                                            .projectDesc:
                                                        e.attendanceRegisterAdditionalDetails
                                                                ?.projectDesc ??
                                                            t.translate(i18
                                                                .common.noValue)
                                                  })
                                              .toList();
                                    },
                                    orElse: () => false);
                              },
                              child: BlocBuilder<
                                  AttendanceIndividualProjectSearchBloc,
                                  AttendanceIndividualProjectSearchState>(
                                builder: (context, registerState) {
                                  return registerState.maybeWhen(
                                      orElse: () => Container(),
                                      loading: () =>
                                          shg_loader.Loaders.circularLoader(
                                              context),
                                      loaded: (AttendanceRegistersModel?
                                          individualAttendanceRegisterModel) {
                                        return WorkDetailsCard(registerDetails);
                                        //return SizedBox.shrink();
                                      });
                                },
                              ),
                            ),
                          ],
                        ),
                      ),
                      SliverToBoxAdapter(
                          child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                            Container(
                                alignment: Alignment.centerLeft,
                                margin: const EdgeInsets.all(4.0),
                                child: StatefulBuilder(
                                    builder: (context, setAutoState) {
                                  return AutoCompleteSearchBar(
                                    hintText: AppLocalizations.of(context)
                                        .translate(i18.common.searchByName),
                                    controller: searchController,
                                    suggestionsBoxController:
                                        suggestionsBoxController,
                                    onSuggestionSelected: onSuggestionSelected,
                                    callBack: onSearchVendorList,
                                    minCharsForSuggestions: 2,
                                    listTile: buildTile,
                                    labelText: '',
                                  );
                                })),
                            const SizedBox(
                              height: 10,
                            ),
                            BlocListener<AttendanceIndividualProjectSearchBloc,
                                AttendanceIndividualProjectSearchState>(
                              listener: (context, registerState) {
                                registerState.maybeWhen(
                                    loading: () =>
                                        shg_loader.Loaders.circularLoader(
                                            context),
                                    initial: () {
                                      existingAttendeeList.clear();
                                    },
                                    loaded: (AttendanceRegistersModel?
                                        individualAttendanceRegisterModel) {
                                      if (individualAttendanceRegisterModel!
                                                  .attendanceRegister !=
                                              null &&
                                          individualAttendanceRegisterModel
                                              .attendanceRegister!.isNotEmpty) {
                                        registerStartDate =
                                            individualAttendanceRegisterModel
                                                    .attendanceRegister!
                                                    .first
                                                    .startDate ??
                                                DateTime.now()
                                                    .millisecondsSinceEpoch;
                                        if (individualAttendanceRegisterModel
                                                    .attendanceRegister!
                                                    .first
                                                    .attendeesEntries !=
                                                null &&
                                            individualAttendanceRegisterModel
                                                .attendanceRegister!
                                                .first
                                                .attendeesEntries!
                                                // TODO:temp
                                                .where((e) =>
                                                    e.denrollmentDate == null ||
                                                    !(e.denrollmentDate! <=
                                                        DateTime.now()
                                                            .millisecondsSinceEpoch))
                                                .toList()
                                                .isNotEmpty) {
                                          existingAttendeeList =
                                              individualAttendanceRegisterModel
                                                  .attendanceRegister!
                                                  .first
                                                  .attendeesEntries!
                                                  //TODO:temp
                                                  .where((att) =>
                                                      att.denrollmentDate ==
                                                          null ||
                                                      !(att.denrollmentDate! <=
                                                          DateTime.now()
                                                              .millisecondsSinceEpoch))
                                                  .map((e) => {
                                                        "uuid": e.individualId
                                                            .toString()
                                                      })
                                                  .toList();
                                          context
                                              .read<IndividualWMSSearchBloc>()
                                              .add(
                                                SearchWMSIndividualIdEvent(
                                                    ids: individualAttendanceRegisterModel
                                                        .attendanceRegister!
                                                        .first
                                                        .attendeesEntries!
                                                        //TODO:temp
                                                        ?.where((att) =>
                                                            att.denrollmentDate ==
                                                                null ||
                                                            !(att.denrollmentDate! <=
                                                                DateTime.now()
                                                                    .millisecondsSinceEpoch))
                                                        .map((e) => e
                                                            .individualId
                                                            .toString())
                                                        .toList(),
                                                    tenant: widget.tenantId),
                                              );
                                        }
                                      }
                                    },
                                    error: (String? error) => Toast.showToast(
                                        context,
                                        message: error.toString(),
                                        type: ToastType.error),
                                    // Notifiers.getToastMessage(
                                    //     context, error.toString(), 'ERROR'),

                                    orElse: () => const SizedBox.shrink());
                              },
                              child: BlocBuilder<IndividualWMSSearchBloc,
                                      IndividualWMSSearchState>(
                                  builder: (context, userState) {
                                return userState.maybeWhen(
                                    loading: () =>
                                        shg_loader.Loaders.circularLoader(
                                            context),
                                    initial: () {
                                      existingAttendeeList.clear();
                                      return Column(
                                        children: [
                                          const EmptyImage(
                                              align: Alignment.center),
                                          Button(
                                            size: ButtonSize.large,
                                            type: ButtonType.tertiary,
                                            label: AppLocalizations.of(context)
                                                .translate(i18.attendanceMgmt
                                                    .addNewWageSeeker),
                                            onPressed: () {
                                              context.router.push(
                                                  const RegisterIndividualRoute());
                                            },
                                          ),
                                        ],
                                      );
                                    },
                                    loaded: (WMSIndividualListModel?
                                        individualListModel) {
                                      userList = (individualListModel!.items ??
                                                  [])
                                              .isNotEmpty
                                          ? individualListModel.items!
                                              .map((e) => {
                                                    "name": e.businessObject
                                                        ?.name?.givenName,
                                                    "aadhaar": e
                                                            .businessObject
                                                            ?.identifiers
                                                            ?.first
                                                            .identifierId ??
                                                        e.businessObject
                                                            ?.individualId,
                                                    "gender": e
                                                        .businessObject?.gender,
                                                    "individualCode": e
                                                        .businessObject
                                                        ?.individualId,
                                                    "skill": AppLocalizations
                                                            .of(context)
                                                        .translate(
                                                            'COMMON_MASTERS_SKILLS_${e.businessObject?.skills?.first.level?.toUpperCase()}'),
                                                    "individualId":
                                                        e.businessObject?.id,
                                                    "uuid":
                                                        e.businessObject?.id,
                                                    "individualGaurdianName": e
                                                            .businessObject
                                                            ?.fatherName ??
                                                        e.businessObject
                                                            ?.husbandName,
                                                    "mobileNumber": e
                                                        .businessObject
                                                        ?.mobileNumber,
                                                    "tenantId": e.businessObject
                                                        ?.tenantId
                                                  })
                                              .toList()
                                          : [];
                                      if (userList.isNotEmpty) {
                                        for (var user in userList) {
                                          var userToAdd = {
                                            "name": user["name"],
                                            "aadhaar": user["aadhaar"],
                                            "gender": user["gender"],
                                            "individualCode":
                                                user["individualCode"],
                                            "uuid": user["uuid"],
                                            "skill": user["skill"],
                                            "individualGaurdianName":
                                                user["individualGaurdianName"],
                                            "individualId":
                                                user["individualId"],
                                            "mobileNumber":
                                                user["mobileNumber"],
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
                                        userTableList = attendeeTableList
                                                .isNotEmpty
                                            ? attendeeTableList
                                                .map((e) =>
                                                    TableDataModel.fromJson(e))
                                                .toList()
                                            : [];
                                        tableData =
                                            getAttendanceData(userTableList);
                                      }
                                      return userTableList.isNotEmpty ||
                                              deleteAttendeePayLoadList
                                                  .isNotEmpty
                                          ? Column(
                                              crossAxisAlignment:
                                                  CrossAxisAlignment.start,
                                              children: [
                                                  LayoutBuilder(builder:
                                                      (context, constraints) {
                                                    var width =
                                                        constraints.maxWidth <
                                                                760
                                                            ? 150.0
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
                                                            width * 4,
                                                        height: 58 +
                                                            (52.0 *
                                                                (tableData
                                                                        .length +
                                                                    0.2)),
                                                        scrollPhysics:
                                                            const NeverScrollableScrollPhysics(),
                                                      ),
                                                    );
                                                  }),
                                                  BlocListener<
                                                      AttendeeCreateBloc,
                                                      AttendeeCreateState>(
                                                    listener:
                                                        (context, createState) {
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
//new
                                                            // Toast.showToast(
                                                            //     context,
                                                            //     message: AppLocalizations.of(
                                                            //             context)
                                                            //         .translate(i18
                                                            //             .attendanceMgmt
                                                            //             .attendeeCreateSuccess),
                                                            //     type: ToastType
                                                            //         .success);

                                                            context.router.popAndPush(AttendanceRegisterTableRoute(
                                                                registerId: widget
                                                                    .registerId
                                                                    .toString(),
                                                                tenantId: widget
                                                                    .tenantId
                                                                    .toString()));
                                                          },
                                                          error:
                                                              (String? error) {
                                                            Notifiers.getToastMessage(
                                                                context,
                                                                AppLocalizations.of(
                                                                        context)
                                                                    .translate(error
                                                                        .toString()),
                                                                'ERROR');

                                                            // new

                                                            // Toast.showToast(
                                                            //     context,
                                                            //     message: AppLocalizations.of(
                                                            //             context)
                                                            //         .translate(error
                                                            //             .toString()),
                                                            //     type: ToastType
                                                            //         .error);

                                                            context.router.popAndPush(AttendanceRegisterTableRoute(
                                                                registerId: widget
                                                                    .registerId
                                                                    .toString(),
                                                                tenantId: widget
                                                                    .tenantId
                                                                    .toString()));
                                                          },
                                                          orElse: () {});
                                                    },
                                                    child: BlocListener<
                                                        AttendeeDeEnrollBloc,
                                                        AttendeeDeEnrollState>(
                                                      listener: (context,
                                                          deEnrollState) {
                                                        deEnrollState.maybeWhen(
                                                            loading: () {
                                                              Navigator.of(
                                                                context,
                                                                rootNavigator:
                                                                    true,
                                                              ).popUntil(
                                                                (route) => route
                                                                    is! PopupRoute,
                                                              );
                                                              return shg_loader
                                                                      .Loaders
                                                                  .showLoadingDialog(
                                                                      context,
                                                                      label:
                                                                          "Deleting...");
                                                            },
                                                            loaded: (uuid) {
                                                              Navigator.of(
                                                                context,
                                                                rootNavigator:
                                                                    true,
                                                              ).popUntil(
                                                                (route) => route
                                                                    is! PopupRoute,
                                                              );
                                                              Notifiers.getToastMessage(
                                                                  context,
                                                                  AppLocalizations.of(
                                                                          context)
                                                                      .translate(i18
                                                                          .attendanceMgmt
                                                                          .attendeeDeEnrollSuccess),
                                                                  'SUCCESS');

                                                              //new

                                                              // Toast.showToast(
                                                              //     context,
                                                              //     message: AppLocalizations.of(
                                                              //             context)
                                                              //         .translate(i18
                                                              //             .attendanceMgmt
                                                              //             .attendeeDeEnrollSuccess),
                                                              //     type: ToastType
                                                              //         .success);
// TODO: commented for wage seeker deengagement
// to avoid pop and push same screen
                                                              // Future.delayed(
                                                              //     const Duration(
                                                              //         seconds:
                                                              //             1));
                                                              // context.router.popAndPush(AttendanceRegisterTableRoute(
                                                              //     registerId: widget
                                                              //         .registerId
                                                              //         .toString(),
                                                              //     tenantId: widget
                                                              //         .tenantId
                                                              //         .toString()));

                                                              //end

                                                              // new impl
                                                              setState(() {
                                                                searchUser =
                                                                    true;
                                                                existingAttendeeList
                                                                    .removeWhere((e) =>
                                                                        e['uuid'] ==
                                                                        uuid);
                                                                createAttendeePayLoadList
                                                                    .removeWhere((e) =>
                                                                        e['individualId'] ==
                                                                        uuid);
                                                                attendeeTableList
                                                                    .removeWhere((e) =>
                                                                        e['uuid'] ==
                                                                        uuid);
                                                                userList.removeWhere(
                                                                    (e) =>
                                                                        e['uuid'] ==
                                                                        uuid);
                                                                addToTableList
                                                                    .removeWhere((e) =>
                                                                        e['uuid'] ==
                                                                        uuid);
                                                                userTableList
                                                                    .removeWhere((e) =>
                                                                        e.uuid ==
                                                                        uuid);
                                                              });
                                                            },
                                                            error:
                                                                (String? error,
                                                                    uuid) {
                                                              Navigator.of(
                                                                context,
                                                                rootNavigator:
                                                                    true,
                                                              ).popUntil(
                                                                (route) => route
                                                                    is! PopupRoute,
                                                              );
                                                              Notifiers.getToastMessage(
                                                                  context,
                                                                  AppLocalizations.of(
                                                                          context)
                                                                      .translate(
                                                                          error
                                                                              .toString()),
                                                                  'ERROR');
                                                              //new
                                                              // Toast.showToast(
                                                              //     context,
                                                              //     message: AppLocalizations.of(
                                                              //             context)
                                                              //         .translate(
                                                              //             error
                                                              //                 .toString()),
                                                              //     type: ToastType
                                                              //         .error);

                                                              //TODO:

                                                              // context.router.popAndPush(AttendanceRegisterTableRoute(
                                                              //     registerId: widget
                                                              //         .registerId
                                                              //         .toString(),
                                                              //     tenantId: widget
                                                              //         .tenantId
                                                              //         .toString()));

                                                              // new impl
                                                            },
                                                            orElse: () =>
                                                                Container());
                                                      },
                                                      child: Container(),
                                                    ),
                                                  ),
                                                ])
                                          : Column(
                                              children: [
                                                const EmptyImage(
                                                    align: Alignment.center),
                                                Button(
                                                  type: ButtonType.tertiary,
                                                  size: ButtonSize.large,
                                                  label: AppLocalizations.of(
                                                          context)
                                                      .translate(i18
                                                          .attendanceMgmt
                                                          .addNewWageSeeker),
                                                  onPressed: () {
                                                    context.router.push(
                                                        const RegisterIndividualRoute());
                                                  },
                                                ),
                                              ],
                                            );
                                    },
                                    error: (String? error) => Container(),
                                    orElse: () => Container());
                              }),
                            ),
                            const SizedBox(height: 30),
                            const Align(
                              alignment: Alignment.bottomCenter,
                              child: PoweredByDigit(
                                version: Constants.appVersion,
                              ),
                            ),
                          ]))
                    ],
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }

  void onSuggestionSelected(user) async {
    IndividualListModel individualListModelData =
        await IndividualRepository(Client().init()).searchIndividual(
            url: Urls.attendanceRegisterServices.individualSearch,
            queryParameters: {
          "offset": '0',
          "limit": '100',
          "tenantId": widget.tenantId.toString(),
        },
            body: {
          "Individual": {
            "individualId": [user["individualCode"]]
          }
        });


    setState(() {
      searchController.text = '';
      bool hasDuplicate = addToTableList
              .where((e) => e["uuid"] == user["uuid"])
              .isNotEmpty ||
          attendeeTableList.where((e) => e['uuid'] == user['uuid']).isNotEmpty;
      if (!hasDuplicate) {
        addToTableList.add({
          "name": user["name"],
          "aadhaar": user["aadhaar"],
          "gender": user["gender"],
          "individualCode": user["individualCode"],
          "individualGaurdianName":individualListModelData.Individual?.first.fatherName??individualListModelData.Individual?.first.husbandName,
          "skill": user["skill"],
          "individualId": user["individualId"],
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
                      // develop
                      "registerId": registerId.toString(),
                      "individualId": e["uuid"],
                      "enrollmentDate": registerStartDate,
                      "tenantId": e["tenantId"],
                      "additionalDetails": {
                        "individualName": e["name"],
                        "individualID": e["individualCode"],
                        "individualGaurdianName": e["individualGaurdianName"],
                        "identifierId": e["aadhaar"],
                        "gender": e["gender"]
                      }
                    })
                .toList()
            : [];
        deleteAttendeePayLoadList
            .removeWhere((e) => e["individualId"] == user["uuid"]);
      } else {
        Notifiers.getToastMessage(
            context, i18.common.individualAlreadyAdded, 'ERROR');
        // new
        // Toast.showToast(context,
        //     message: AppLocalizations.of(context)
        //         .translate(i18.common.individualAlreadyAdded.toString()),
        //     type: ToastType.error);
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
            Text('${user["individualGaurdianName"]}', style: style),
            Text('${user["individualCode"]}',
                style: style.apply(fontSizeDelta: -2))
          ],
        ));
  }

  Future<List<dynamic>> onSearchVendorList(pattern) async {
    searchUser = true;
    context.read<IndividualWMSSearchBloc>().add(
          SearchWMSIndividualNameEvent(name: pattern, tenant: widget.tenantId),
        );
    await Future.delayed(const Duration(milliseconds: 500));

    setState(() {
      filteredUserList = userList;
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
                .translate(i18.common.wageSeekerID),
            apiKey: 'individualCode'),
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
              .translate('${tableDataModel.skill}'),
          apiKey: tableDataModel.skill),
      TableData(
          label: tableDataModel.individualCode,
          apiKey: tableDataModel.individualId),
      TableData(
          widget: DeleteButton(
              onTap: () => onDelete(tableDataModel.uuid.toString())))
    ]);
  }

  List<TableDataRow> getAttendanceData(List<TableDataModel> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }

  void onDelete(String uuid) {
    deleteAttendeePayLoadList.clear();
    switch (createAttendeePayLoadList.isNotEmpty &&
        createAttendeePayLoadList
            .where((e) => e["individualId"] == uuid)
            .isNotEmpty) {
      case true:
        if (!(createAttendeePayLoadList
                .where((e) => e["individualId"] == uuid)
                .isNotEmpty &&
            createAttendeePayLoadList
                    .where((e) => e["individualId"] == uuid)
                    .first['enrollmentDate'] !=
                null)) {
          //  deleteAttendeePayLoadList.clear();
          deleteAttendeePayLoadList.add({
            "registerId": registerId.toString(),
            "individualId": uuid.toString(),
            "enrollmentDate": null,
            "denrollmentDate": DateTime.now()
                .subtract(const Duration(minutes: 1, seconds: 30))
                .millisecondsSinceEpoch,
            "tenantId": widget.tenantId.toString()
          });
          context.read<AttendeeDeEnrollBloc>().add(
                DeEnrollAttendeeEvent(
                    attendeeList: deleteAttendeePayLoadList, uuid: uuid ?? ''),
              );
          setState(() {
            searchUser = true;
            createAttendeePayLoadList
                .removeWhere((e) => e['individualId'] == uuid);
            attendeeTableList.removeWhere((e) => e['uuid'] == uuid);
            userList.removeWhere((e) => e['uuid'] == uuid);
            addToTableList.removeWhere((e) => e['uuid'] == uuid);
            userTableList.removeWhere((e) => e.uuid == uuid);
          });
        } else {
          setState(() {
            searchUser = true;
            createAttendeePayLoadList
                .removeWhere((e) => e['individualId'] == uuid);
            attendeeTableList.removeWhere((e) => e['uuid'] == uuid);
            userList.removeWhere((e) => e['uuid'] == uuid);
            addToTableList.removeWhere((e) => e['uuid'] == uuid);
            userTableList.removeWhere((e) => e.uuid == uuid);
          });
          //   deleteAttendeePayLoadList.clear();
          deleteAttendeePayLoadList.add({
            "registerId": registerId.toString(),
            "individualId": uuid.toString(),
            "enrollmentDate": null,
            "denrollmentDate": DateTime.now()
                .subtract(const Duration(minutes: 1, seconds: 30))
                .millisecondsSinceEpoch,
            "tenantId": widget.tenantId.toString()
          });
        }
        break;
      case false:
        if (existingAttendeeList.where((e) => e["uuid"] == uuid).isNotEmpty) {
          deleteAttendeePayLoadList.add({
            "registerId": registerId.toString(),
            "individualId": uuid.toString(),
            "enrollmentDate": null,
            "denrollmentDate": DateTime.now()
                .subtract(const Duration(minutes: 1, seconds: 30))
                .millisecondsSinceEpoch,
            "tenantId": widget.tenantId.toString()
          });
        }
        context.read<AttendeeDeEnrollBloc>().add(
              DeEnrollAttendeeEvent(
                  attendeeList: deleteAttendeePayLoadList, uuid: uuid ?? ''),
            );
        // setState(() {
        //   searchUser = true;
        //   createAttendeePayLoadList
        //       .removeWhere((e) => e['individualId'] == uuid);
        //   attendeeTableList.removeWhere((e) => e['uuid'] == uuid);
        //   userList.removeWhere((e) => e['uuid'] == uuid);
        //   addToTableList.removeWhere((e) => e['uuid'] == uuid);
        //   userTableList.removeWhere((e) => e.uuid == uuid);
        // });
        break;
      default:
        break;
    }
  }
}
