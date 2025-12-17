import 'package:collection/collection.dart';
// import 'package:digit_components/widgets/widgets.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:digit_ui_components/widgets/molecules/digit_timeline_molecule.dart';
import 'package:digit_ui_components/widgets/powered_by_digit.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/blocs/muster_rolls/get_muster_workflow.dart';
import 'package:works_shg_app/blocs/muster_rolls/muster_roll_pdf.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
import 'package:works_shg_app/models/muster_rolls/muster_workflow_model.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/common_methods.dart';
import 'package:works_shg_app/utils/date_formats.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/models/track_attendance_payload.dart';
import 'package:works_shg_app/widgets/circular_button.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';
import 'package:works_shg_app/widgets/work_details_card.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/atoms/digit_timeline.dart';
import 'package:works_shg_app/widgets/molecules/digit_table.dart' as shg_app;
import '../../models/attendance/attendee_model.dart';
import '../../models/file_store/file_store_model.dart';
import '../../utils/common_widgets.dart';
import '../../utils/constants.dart';
import '../../utils/models.dart';
import '../../widgets/atoms/table_dropdown.dart';
import '../../widgets/loaders.dart' as shg_loader;

@RoutePage()
class MBMusterScreenPage extends StatefulWidget {
  final String tenantId;
  final String musterRollNumber;
  final String mbNumber;
  const MBMusterScreenPage(
      {super.key,
      required this.tenantId,
      required this.musterRollNumber,
      required this.mbNumber});

  @override
  State<MBMusterScreenPage> createState() => _MBMusterScreenPageState();
}

class _MBMusterScreenPageState extends State<MBMusterScreenPage> {
  List<Map<String, dynamic>> projectDetails = [];
  List<TrackAttendanceTableData> newList = [];
  List<TableDataRow> tableData = [];
  List<String> dates = [];
  List<DigitTimelineOptions> timeLineAttributes = [];

  @override
  void initState() {
    context.read<MusterRollSearchBloc>().add(MbSearchMusterRollEvent(
        musterRollNumner: widget.musterRollNumber, tenantId: widget.tenantId));
    context.read<MusterGetWorkflowBloc>().add(
          FetchMBWorkFlowEvent(
              tenantId: widget.tenantId, mbNumber: widget.musterRollNumber),
        );

    super.initState();
  }

  @override
  void dispose() {
    newList.clear();
    tableData.clear();
    timeLineAttributes.clear();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width < 760
        ? 150.0
        : (MediaQuery.of(context).size.width / 7.5);
    var t = AppLocalizations.of(context);

    return BlocBuilder<LocalizationBloc, LocalizationState>(
      builder: (context, localState) {
        return PopScope(
          canPop: true,
          onPopInvoked: (didPop) {
            if (GlobalVariables.roleType == RoleType.employee) {
              context.read<MusterGetWorkflowBloc>().add(
                    FetchMBWorkFlowEvent(
                        tenantId: widget.tenantId, mbNumber: widget.mbNumber),
                  );
            }
          },
          child: Scaffold(
             backgroundColor: Theme.of(context).colorTheme.generic.background,
            // appBar: customAppBar(),
            // drawer: const MySideBar(),
            body: BlocBuilder<MusterRollSearchBloc, MusterRollSearchState>(
              builder: (context, state) {
                return state.maybeMap(
                  orElse: () => const SizedBox.shrink(),
                  loaded: (value) {
                    projectDetails = value.musterRollsModel!.musterRoll!
                        .map((e) => {
                              i18.attendanceMgmt.musterRollId:
                                  e.musterRollNumber,
                              i18.workOrder.workOrderNo:
                                  e.musterAdditionalDetails?.contractId ??
                                      i18.common.noValue,
                              i18.attendanceMgmt.projectId:
                                  e.musterAdditionalDetails?.projectId ??
                                      i18.common.noValue,
                              i18.attendanceMgmt.projectName:
                                  e.musterAdditionalDetails?.projectName ??
                                      i18.common.noValue,
                              i18.attendanceMgmt.projectDesc:
                                  e.musterAdditionalDetails?.projectDesc ??
                                      'NA',
                              i18.attendanceMgmt.musterRollPeriod:
                                  '${DateFormats.timeStampToDate(e.startDate, format: "dd/MM/yyyy")} - ${DateFormats.timeStampToDate(e.endDate, format: "dd/MM/yyyy")}',
                              i18.common.status:
                                  'CBO_MUSTER_${e.musterRollStatus}',
                            })
                        .toList();
                    // table

                    List<AttendeesTrackList> attendeeList = [];

                    if (value.musterRollsModel!.musterRoll!.first
                        .individualEntries!.isNotEmpty) {
                      attendeeList = value.musterRollsModel!.musterRoll!.first
                          .individualEntries!
                          .where((est) => est.attendanceEntries != null)
                          .map((e) => AttendeesTrackList(
                              name: e.musterIndividualAdditionalDetails?.userName ??
                                  '',
                              aadhaar: e.musterIndividualAdditionalDetails
                                      ?.aadharNumber ??
                                  '',
                              gender:
                                  e.musterIndividualAdditionalDetails?.gender ??
                                      '',
                              individualId: e.individualId,
                              skillCodeList:
                                  [e.musterIndividualAdditionalDetails?.skillCode ?? ''] ??
                                      [],
                              individualGaurdianName: e
                                      .musterIndividualAdditionalDetails
                                      ?.fatherName ??
                                  e.musterIndividualAdditionalDetails?.fatherName ??
                                  '',
                              id: e.id ?? (value.musterRollsModel!.musterRoll!.first.individualEntries!.any((i) => i.individualId == e.individualId) ? value.musterRollsModel!.musterRoll!.first.individualEntries?.firstWhere((s) => s.individualId == e.individualId).id ?? '' : ''),
                              skill: value.musterRollsModel!.musterRoll!.first.individualEntries!.any((i) => i.individualId == e.individualId) ? value.musterRollsModel!?.musterRoll!.first.individualEntries?.firstWhere((s) => s.individualId == e.individualId).musterIndividualAdditionalDetails?.skillCode ?? '' : '',
                              monEntryId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                              monExitId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                              monIndex: e.attendanceEntries != null ? e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendance ?? -1 : -1,
                              tueEntryId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                              tueExitId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                              tueIndex: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendance ?? -1 : -1,
                              wedEntryId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                              wedExitId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                              wedIndex: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendance ?? -1 : -1,
                              thuEntryId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                              thuExitId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                              thursIndex: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendance ?? -1 : -1,
                              friEntryId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                              friExitId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                              friIndex: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendance ?? -1 : -1,
                              satEntryId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                              satExitId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                              satIndex: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendance ?? -1 : -1,
                              sunEntryId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                              sunExitId: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                              sunIndex: e.attendanceEntries != null ? e.attendanceEntries?.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendance ?? -1 : -1,
                              auditDetails: e.attendanceEntries != null ? e.attendanceEntries?.first.auditDetails : null))
                          .toList();
                    }

                    newList.clear();
                    for (var i = 0; i < attendeeList.length; i++) {
                      var item1 = attendeeList[i];
                      TrackAttendanceTableData data =
                          TrackAttendanceTableData();
                      data.name = item1.name;
                      data.individualGaurdianName =
                          item1.individualGaurdianName ?? '';
                      data.aadhaar = item1.aadhaar;
                      data.gender = item1.gender;
                      data.individualId = item1.individualId ?? '';
                      data.id = item1.id ?? '';
                      data.skill = item1.skill;
                      data.skillCodeList = item1.skillCodeList ?? [];
                      data.monIndex = item1.monIndex;
                      data.monEntryId = item1.monEntryId;
                      data.monExitId = item1.monExitId;
                      data.tueIndex = item1.tueIndex;
                      data.tueEntryId = item1.tueEntryId;
                      data.tueExitId = item1.tueExitId;
                      data.wedIndex = item1.wedIndex;
                      data.wedEntryId = item1.wedEntryId;
                      data.wedExitId = item1.wedExitId;
                      data.thuIndex = item1.thursIndex;
                      data.thuEntryId = item1.thuEntryId;
                      data.thuExitId = item1.thuExitId;
                      data.friIndex = item1.friIndex;
                      data.friEntryId = item1.friEntryId;
                      data.friExitId = item1.friExitId;
                      data.satIndex = item1.satIndex;
                      data.satEntryId = item1.satEntryId;
                      data.satExitId = item1.satExitId;
                      data.sunIndex = item1.sunIndex;
                      data.sunEntryId = item1.sunEntryId;
                      data.sunExitId = item1.sunExitId;
                      data.auditDetails = item1.auditDetails;
                      newList.add(data);
                    }
                    dates = DateFormats.getFormattedDatesOfAWeek(
                        value.musterRollsModel!.musterRoll!.first.startDate!,
                        value.musterRollsModel!.musterRoll!.first.endDate!);

                    tableData = getAttendanceData(newList);
                    //
                    return CustomScrollView(
                      slivers: [
                        SliverList(
                          delegate: SliverChildListDelegate(
                            [
                              Padding(
                                padding:  EdgeInsets.symmetric(
                                      horizontal: Theme.of(context).spacerTheme.spacer4,
                                      vertical: Theme.of(context).spacerTheme.spacer4,
                                    ),
                                child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    BackNavigationButton(
                                      backNavigationButtonThemeData: const BackNavigationButtonThemeData().copyWith(
                  context: context,
                  backButtonIcon: Icon(
                    Icons.arrow_circle_left_outlined,
                    size: MediaQuery.of(context).size.width < 500
                        ? Theme.of(context).spacerTheme.spacer5
                        : Theme.of(context).spacerTheme.spacer6,
                    color: Theme.of(context).colorTheme.primary.primary2,
                  )),
                                      backButtonText:
                                          AppLocalizations.of(context)
                                                  .translate(i18.common.back) ??
                                              'Back',
                                      handleBack: () {
                                        Navigator.of(context).pop();
                                      },
                                    ),
                                    
                                    CommonWidgets.downloadButton(
                                        AppLocalizations.of(context)
                                            .translate(i18.common.download),
                                        () {
                                      context.read<MusterRollPDFBloc>().add(
                                          PDFEventMusterRoll(
                                              musterRollNumber:
                                                  widget.musterRollNumber,
                                              tenantId: widget.tenantId));
                                    })
                                  ],
                                ),
                              ),
                              WorkDetailsCard(
                                projectDetails,
                                showButtonLink: true,
                                musterBackToCBOCode: "PENDINGFORVERIFICATION",
                                linkLabel: "",
                                onLinkPressed: () {},
                              ),
                            ],
                          ),
                        ),

                        SliverToBoxAdapter(
                            child: Column(
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                              //workflow
                              BlocBuilder<MusterGetWorkflowBloc,
                                  MusterGetWorkflowState>(
                                builder: (context, state) {
                                  return state.maybeMap(
                                    orElse: SizedBox.shrink,
                                    loaded: (value) {
                                      List<ProcessInstances> modifiedData =
                                          value.musterWorkFlowModel!
                                              .processInstances!
                                              .where((element) =>
                                                  element.action !=
                                                  Constants.saveAsDraft)
                                              .toList();

                                      if (modifiedData.isNotEmpty &&
                                          (modifiedData.first.nextActions !=
                                                  null &&
                                              modifiedData.first.nextActions!
                                                  .isNotEmpty)) {
                                        modifiedData = [
                                          ...[modifiedData.first],
                                          ...modifiedData
                                        ];
                                      }
                                      timeLineAttributes.clear();
                                      timeLineAttributes = modifiedData
                                          .mapIndexed((i, e) =>
                                              DigitTimelineOptions(
                                                title: t.translate((i == 0 &&
                                                        e.action == "APPROVE")
                                                    ? e.workflowState?.state ==
                                                            "EDIT_RE_SUBMIT"
                                                        ? 'WF_MB_STATUS_${e.workflowState?.state}'
                                                        : 'WF_MB_STATUS_${e.workflowState?.state}'
                                                    : i == 0
                                                        ? e.workflowState
                                                                    ?.state ==
                                                                "EDIT_RE_SUBMIT"
                                                            ? 'WF_MB_STATUS_${e.workflowState?.state}'
                                                            : 'WF_MB_STATUS_${e.workflowState?.state}'
                                                        : 'WF_MB_STATUS_${e.action}'),
                                                subTitle: (i == 0 &&
                                                        e.action == "APPROVE")
                                                    ? DateFormats.getTimeLineDate(e
                                                            .auditDetails
                                                            ?.lastModifiedTime ??
                                                        0)
                                                    : i != 0
                                                        ? DateFormats
                                                            .getTimeLineDate(e
                                                                    .auditDetails
                                                                    ?.lastModifiedTime ??
                                                                0)
                                                        : null,
                                                isCurrentState:
                                                    e.action == "APPROVE"
                                                        ? false
                                                        : i == 0,
                                                comments: (i == 0 &&
                                                        e.action == "APPROVE")
                                                    ? e.comment
                                                    : i != 0
                                                        ? e.comment
                                                        : null,
                                                documents: (i == 0 &&
                                                        e.action == "APPROVE")
                                                    ? e.documents != null &&
                                                            e.documents!
                                                                .isNotEmpty
                                                        ? e.documents
                                                            ?.map((d) =>
                                                                FileStoreModel(
                                                                    name: '',
                                                                    fileStoreId: d
                                                                        .documentUid))
                                                            .toList()
                                                        : null
                                                    : i != 0
                                                        ? e.documents != null &&
                                                                e.documents!
                                                                    .isNotEmpty
                                                            ? e.documents
                                                                ?.map((d) =>
                                                                    FileStoreModel(
                                                                        name:
                                                                            '',
                                                                        fileStoreId:
                                                                            d.documentUid))
                                                                .toList()
                                                            : null
                                                        : null,
                                                assignee: (i == 0 &&
                                                        e.action == "APPROVE")
                                                    ? e.assigner?.name
                                                    : i != 0
                                                        ? e.assigner?.name
                                                        : null,
                                                mobileNumber: (i == 0 &&
                                                        e.action == "APPROVE")
                                                    ? e.assigner != null
                                                        ? '+91-${e.assigner?.mobileNumber}'
                                                        : null
                                                    : i != 0
                                                        ? e.assigner != null
                                                            ? '+91-${e.assigner?.mobileNumber}'
                                                            : null
                                                        : null,
                                              ))
                                          .toList();
                                      timeLineAttributes =
                                          timeLineAttributes.reversed.toList();

                                      return timeLineAttributes.isNotEmpty
                                          ? ui_card.DigitCard(
                                              margin:
                                                  const EdgeInsets.symmetric(
                                                horizontal: 8,
                                                vertical: 8,
                                              ),
                                              cardType:
                                                  ui_component.CardType.primary,
                                              children: [
                                                LabelValueList(
                                                  heading: t.translate(i18
                                                      .common.workflowTimeline),
                                                  items: const [],
                                                ),
                                                Builder(
                                                  builder: (context) {
                                                    return TimelineMolecule(
                                                      steps: List.generate(
                                                        timeLineAttributes
                                                            .length,
                                                        (i) => TimelineStep(
                                                          additionalWidgets: timeLineAttributes[
                                                                              i]
                                                                          .documents !=
                                                                      null &&
                                                                  timeLineAttributes[
                                                                          i]
                                                                      .documents!
                                                                      .isNotEmpty
                                                              ? List.generate(
                                                                  timeLineAttributes[
                                                                          i]
                                                                      .documents!
                                                                      .length,
                                                                  (index) =>
                                                                      InkWell(
                                                                        onTap: () => CommonMethods().onTapOfAttachment(
                                                                            timeLineAttributes[i].documents![index],
                                                                            timeLineAttributes[i].documents![index].tenantId == null
                                                                                ? GlobalVariables.roleType == RoleType.employee
                                                                                    ? GlobalVariables.tenantId!
                                                                                    : GlobalVariables.stateInfoListModel!.code.toString()
                                                                                : timeLineAttributes[i].documents![index].tenantId!,
                                                                            // "od.testing",
                                                                            context,
                                                                            roleType: GlobalVariables.roleType == RoleType.employee ? RoleType.employee : RoleType.cbo),
                                                                        child: Container(
                                                                            width: 50,
                                                                            margin: const EdgeInsets.symmetric(vertical: 10, horizontal: 5),
                                                                            child: Wrap(runSpacing: 5, spacing: 8, children: [
                                                                              Image.asset('assets/png/attachment.png'),
                                                                              Text(
                                                                                AppLocalizations.of(context).translate(timeLineAttributes[i].documents![index].name.toString()),
                                                                                maxLines: 2,
                                                                                overflow: TextOverflow.ellipsis,
                                                                              )
                                                                            ])),
                                                                      )).toList()
                                                              : [],
                                                          description: [
                                                            timeLineAttributes[
                                                                        i]
                                                                    .subTitle ??
                                                                '',
                                                            timeLineAttributes[
                                                                        i]
                                                                    .assignee ??
                                                                '',
                                                            timeLineAttributes[
                                                                        i]
                                                                    .mobileNumber ??
                                                                '',
                                                                 timeLineAttributes[
                                                                        i]
                                                                    .comments ??
                                                                '',
                                                          ],
                                                          label:
                                                              timeLineAttributes[
                                                                      i]
                                                                  .title,
                                                          state: timeLineAttributes[
                                                                      i]
                                                                  .isCurrentState
                                                              ? ui_component
                                                                  .TimelineStepState
                                                                  .present
                                                              : ui_component
                                                                  .TimelineStepState
                                                                  .completed,
                                                        ),
                                                      ).toList(),
                                                    );
                                                  },
                                                ),
                                              ],
                                            )
                                          : const SizedBox.shrink();
                                      //
                                    },
                                  );
                                },
                              ),
                              Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Padding(
                                      padding: const EdgeInsets.all(8.0),
                                      child: shg_app.DigitTable(
                                        headerList: headerList,
                                        tableData: tableData,
                                        leftColumnWidth: width,
                                        rightColumnWidth: width * 10,
                                        height: 58 +
                                            (52.0 * (tableData.length + 0.2)),
                                        scrollPhysics:
                                            const NeverScrollableScrollPhysics(),
                                      ),
                                    ),
                                  ]),
                              const Align(
                                alignment: Alignment.bottomCenter,
                                child: PoweredByDigit(
                                  version: Constants.appVersion,
                                ),
                              )
                            ]))

                        // end
                      ],
                    );
                  },
                  loading: (value) {
                    return Center(
                      child: shg_loader.Loaders.circularLoader(context),
                    );
                  },
                  error: (value) {
                    return const SizedBox.shrink();
                  },
                );
              },
            ),
          ),
        );
      },
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
              .translate(i18.common.fatherName),
          apiKey: 'individualGaurdianName',
        ),
        TableHeader(
            '${AppLocalizations.of(scaffoldMessengerKey.currentContext!).translate(i18.attendanceMgmt.skill)}*',
            hide: false),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.mon),
            subLabel: dates.isNotEmpty ? dates[0] : ''),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.tue),
            subLabel: dates.isNotEmpty ? dates[1] : ''),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.wed),
            subLabel: dates.isNotEmpty ? dates[2] : ''),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.thu),
            subLabel: dates.isNotEmpty ? dates[3] : ''),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.fri),
            subLabel: dates.isNotEmpty ? dates[4] : ''),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.sat),
            subLabel: dates.isNotEmpty ? dates[5] : ''),
        TableHeader(
            AppLocalizations.of(scaffoldMessengerKey.currentContext!)
                .translate(i18.common.sun),
            subLabel: dates.isNotEmpty ? dates[6] : ''),
        TableHeader(
          AppLocalizations.of(scaffoldMessengerKey.currentContext!)
              .translate(i18.common.total),
        )
      ];

  TableDataRow getAttendanceRow(TrackAttendanceTableData tableDataModel) {
    return TableDataRow([
      TableData(label: tableDataModel.name, apiKey: tableDataModel.name),
      TableData(
          label: tableDataModel.individualGaurdianName,
          apiKey: tableDataModel.individualGaurdianName),
      TableData(
          apiKey: tableDataModel.skill,
          hide: false,
          widget: DropDownDialog(
            isDisabled: true,
            options: tableDataModel.skillCodeList ?? [],
            label: i18.common.selectSkill,
            selectedOption: tableDataModel.skill.toString(),
            onChanged: (val) {},
          )),
      TableData(
        apiKey: tableDataModel.monIndex.toString(),
        widget: CircularButton(
            icon: Icons.circle_rounded,
            size: 15,
            viewOnly: true,
            color: const Color.fromRGBO(0, 100, 0, 1),
            index: tableDataModel.monIndex ?? 0.0,
            isNotGreyed: false,
            onTap: () {}),
      ),
      TableData(
          widget: CircularButton(
              icon: Icons.circle_rounded,
              size: 15,
              viewOnly: true,
              color: const Color.fromRGBO(0, 100, 0, 1),
              index: tableDataModel.tueIndex ?? 0,
              isNotGreyed: false,
              onTap: () {})),
      TableData(
          widget: CircularButton(
              icon: Icons.circle_rounded,
              viewOnly: true,
              size: 15,
              color: const Color.fromRGBO(0, 100, 0, 1),
              index: tableDataModel.wedIndex ?? 0,
              isNotGreyed: false,
              onTap: () {})),
      TableData(
          widget: CircularButton(
              icon: Icons.circle_rounded,
              size: 15,
              viewOnly: true,
              color: const Color.fromRGBO(0, 100, 0, 1),
              index: tableDataModel.thuIndex ?? 0,
              isNotGreyed: false,
              onTap: () {})),
      TableData(
          widget: CircularButton(
              icon: Icons.circle_rounded,
              size: 15,
              viewOnly: true,
              color: const Color.fromRGBO(0, 100, 0, 1),
              index: tableDataModel.friIndex ?? 0,
              isNotGreyed: false,
              onTap: () {})),
      TableData(
          widget: CircularButton(
              icon: Icons.circle_rounded,
              size: 15,
              viewOnly: true,
              color: const Color.fromRGBO(0, 100, 0, 1),
              index: tableDataModel.satIndex ?? 0,
              isNotGreyed: false,
              onTap: () {})),
      TableData(
          widget: CircularButton(
              icon: Icons.circle_rounded,
              size: 15,
              viewOnly: true,
              color: const Color.fromRGBO(0, 100, 0, 1),
              index: tableDataModel.sunIndex ?? 0,
              isNotGreyed: false,
              onTap: () {})),
      TableData(
        label: (convertedValue(tableDataModel.monIndex!.toDouble()) +
                convertedValue(tableDataModel.tueIndex!.toDouble()) +
                convertedValue(tableDataModel.wedIndex!.toDouble()) +
                convertedValue(tableDataModel.thuIndex!.toDouble()) +
                convertedValue(tableDataModel.friIndex!.toDouble()) +
                convertedValue(tableDataModel.satIndex!.toDouble()) +
                convertedValue(tableDataModel.sunIndex!.toDouble()))
            .toString(),
      )
    ]);
  }

  List<TableDataRow> getAttendanceData(List<TrackAttendanceTableData> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }

  double convertedValue(double tableVal) {
    if (tableVal < 0) {
      return 0;
    } else {
      return tableVal;
    }
  }
}
