import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:syncfusion_flutter_datepicker/datepicker.dart';
import 'package:works_shg_app/blocs/muster_rolls/create_muster.dart';
import 'package:works_shg_app/utils/common_methods.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/Back.dart';
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/date_range_picker.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/molecules/digit_table.dart' as shg_app;

import '../blocs/attendance/attendance_create_log.dart';
import '../blocs/attendance/attendance_hours_mdms.dart';
import '../blocs/attendance/search_projects/search_individual_project.dart';
import '../blocs/attendance/skills/skills_bloc.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/muster_rolls/from_to_date_search_muster_roll.dart';
import '../blocs/muster_rolls/get_muster_workflow.dart';
import '../blocs/muster_rolls/muster_roll_estimate.dart';
import '../models/attendance/attendance_registry_model.dart';
import '../models/attendance/attendee_model.dart';
import '../models/mdms/attendance_hours.dart';
import '../models/muster_rolls/estimate_muster_roll_model.dart';
import '../models/muster_rolls/muster_roll_model.dart';
import '../models/muster_rolls/muster_workflow_model.dart';
import '../models/skills/skills.dart';
import '../router/app_router.dart';
import '../utils/constants.dart';
import '../utils/date_formats.dart';
import '../utils/models.dart';
import '../utils/models/track_attendance_payload.dart';
import '../utils/notifiers.dart';
import '../widgets/ButtonLink.dart';
import '../widgets/CircularButton.dart';
import '../widgets/SideBar.dart';
import '../widgets/atoms/app_bar_logo.dart';
import '../widgets/atoms/table_dropdown.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart' as shg_loader;

class TrackAttendancePage extends StatefulWidget {
  final String id;
  final String tenantId;

  const TrackAttendancePage(
      @PathParam('id') this.id, @PathParam('tenantId') this.tenantId,
      {Key? key})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _TrackAttendancePage();
  }
}

class _TrackAttendancePage extends State<TrackAttendancePage> {
  // AttendanceRegister? _attendanceRegister;
  DateRangePickerController rangePickerController = DateRangePickerController();
  DateRangePickerSelectionMode selectionMode =
      DateRangePickerSelectionMode.single;
  String? registerId;
  int? registerStartDate;
  int? registerEndDate;
  DateRange? selectedDateRange;
  var dateController = TextEditingController();
  var searchController = TextEditingController();
  List<Map<String, dynamic>> cardDetails = [];
  List<TrackAttendanceTableData> newList = [];
  List<Map<String, dynamic>> updateAttendeePayload = [];
  List<Map<String, dynamic>> createAttendeePayload = [];
  List<Map<String, dynamic>> skillsPayLoad = [];
  List<TableDataRow> tableData = [];
  bool hasLoaded = true;
  bool createMusterLoaded = true;
  bool hide = true;
  List<EntryExitModel>? entryExitList;
  List<IndividualSkills> existingSkills = [];
  List<Skill> skillList = [];
  List<String> skillDropDown = [];
  DaysInRange? daysInRange;
  List<String> dates = [];
  bool isInWorkFlow = false;
  bool skillsDisable = true;
  bool allowEdit = true;
  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<AttendanceIndividualProjectSearchBloc>().add(
          SearchIndividualAttendanceProjectEvent(
              id: widget.id, tenantId: widget.tenantId),
        );
    context.read<AttendanceHoursBloc>().add(
          const AttendanceHoursEvent(),
        );
    context.read<SkillsBloc>().add(
          const SkillsEvent(),
        );
  }

  @override
  void deactivate() {
    context.read<MusterRollEstimateBloc>().add(
          const DisposeEstimateMusterRollEvent(),
        );
    context.read<MusterGetWorkflowBloc>().add(
          const DisposeMusterRollWorkflowEvent(),
        ); // Change the state of the widget when it is no longer visible
    super.deactivate();
  }

  @override
  void dispose() {
    // Clear the data when the widget is disposed
    newList.clear();
    tableData.clear();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width < 760
        ? 150.0
        : (MediaQuery.of(context).size.width / 7.5);
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(Drawer(
            child: SideBar(
          module: CommonMethods.getLocaleModules(),
        ))),
        body: BlocBuilder<SkillsBloc, SkillsBlocState>(
            builder: (context, skillsState) {
          return skillsState.maybeWhen(
              orElse: () => Container(),
              loading: () => shg_loader.Loaders.circularLoader(context),
              error: (String? error) => Notifiers.getToastMessage(
                  context,
                  AppLocalizations.of(context).translate(error.toString()),
                  'ERROR'),
              loaded: (SkillsList? skillsList) {
                skillList = skillsList!.wageSeekerSkills
                        ?.where((obj) => obj.active == true)
                        .map((e) => Skill(code: e.code))
                        .toList() ??
                    [];
                for (Skill skill in skillList) {
                  skillDropDown.add(skill.code);
                }
                return BlocBuilder<AttendanceHoursBloc, AttendanceHoursState>(
                    builder: (context, mdmsState) {
                  return mdmsState.maybeWhen(
                      orElse: () => Container(),
                      loaded: (AttendanceHoursList? attendanceHoursList) {
                        entryExitList = attendanceHoursList!.attendanceHours
                            ?.where((obj) => obj.active == true)
                            .map((e) => EntryExitModel(
                                hours: int.parse(e.value), code: e.code))
                            .toList();
                        return BlocBuilder<
                                AttendanceIndividualProjectSearchBloc,
                                AttendanceIndividualProjectSearchState>(
                            builder: (context, state) {
                          return state.maybeWhen(
                              loading: () =>
                                  shg_loader.Loaders.circularLoader(context),
                              error: (String? error) =>
                                  Notifiers.getToastMessage(
                                      context, error.toString(), 'ERROR'),
                              loaded: (AttendanceRegistersModel?
                                  individualAttendanceRegisterModel) {
                                registerId = individualAttendanceRegisterModel!
                                    .attendanceRegister!.first.id;
                                registerStartDate =
                                    individualAttendanceRegisterModel
                                        .attendanceRegister!.first.startDate;
                                registerEndDate =
                                    individualAttendanceRegisterModel
                                        .attendanceRegister!.first.endDate;
                                cardDetails = individualAttendanceRegisterModel
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
                                                  'NA',
                                          i18.attendanceMgmt.individualsCount: e
                                                      .attendeesEntries !=
                                                  null
                                              ? e.attendeesEntries
                                                  ?.where((att) =>
                                                      att.denrollmentDate ==
                                                          null ||
                                                      !(att.denrollmentDate! <=
                                                          e.endDate!.toInt()))
                                                  .toList()
                                                  .length
                                              : 0,
                                          i18.common.startDate:
                                              DateFormats.timeStampToDate(
                                                  e.startDate,
                                                  format: "dd/MM/yyyy"),
                                          i18.common.endDate:
                                              DateFormats.timeStampToDate(
                                                  e.endDate,
                                                  format: "dd/MM/yyyy"),
                                        })
                                    .toList();
                                return Stack(children: [
                                  Container(
                                    color:
                                        const Color.fromRGBO(238, 238, 238, 1),
                                    padding: const EdgeInsets.only(
                                        left: 8, right: 8, bottom: 16),
                                    height: MediaQuery.of(context).size.height,
                                    child: CustomScrollView(slivers: [
                                      SliverList(
                                          delegate: SliverChildListDelegate([
                                        Back(
                                          backLabel:
                                              AppLocalizations.of(context)
                                                  .translate(i18.common.back),
                                        ),
                                        WorkDetailsCard(cardDetails),
                                      ])),
                                      SliverToBoxAdapter(
                                          child: Column(
                                              crossAxisAlignment:
                                                  CrossAxisAlignment.center,
                                              children: [
                                            const SizedBox(
                                              height: 20,
                                            ),
                                            // DigitSearchBar(borderRadious: 0,),
                                            // DateRangePicker(label: 'Mark attendance for the week'),
                                            DateRangePicker(
                                              label: AppLocalizations.of(
                                                      context)
                                                  .translate(i18.attendanceMgmt
                                                      .markAttendanceForTheWeek),
                                              controller: dateController,
                                              onChangeOfDate: _onDateChange,
                                              rangePickerController:
                                                  rangePickerController,
                                              onViewChange: _onViewChangedDate,
                                              selectionMode:
                                                  DateRangePickerSelectionMode
                                                      .range,
                                              onSubmit: () => onSubmit(
                                                  individualAttendanceRegisterModel
                                                      .attendanceRegister!
                                                      .first
                                                      .id
                                                      .toString()),
                                              onCancel: () {
                                                dateController.text = '';
                                                selectedDateRange = null;
                                              },
                                              applyLabel: AppLocalizations.of(
                                                      context)
                                                  .translate(i18.common.apply),
                                              cancelLabel: AppLocalizations.of(
                                                      context)
                                                  .translate(i18.common.cancel),
                                            ),
                                            Container(
                                                margin:
                                                    const EdgeInsets.all(8.0),
                                                child: TextFormField(
                                                  controller: searchController,
                                                  autofocus: false,
                                                  decoration: InputDecoration(
                                                    hintText:
                                                        AppLocalizations.of(
                                                                context)
                                                            .translate(i18
                                                                .common
                                                                .searchByName),
                                                    border:
                                                        const OutlineInputBorder(
                                                      borderRadius:
                                                          BorderRadius.zero,
                                                    ),
                                                    filled: true,
                                                    fillColor: Colors.white,
                                                    prefixIconConstraints:
                                                        const BoxConstraints(
                                                            minWidth: 0,
                                                            minHeight: 0),
                                                    prefixStyle: TextStyle(
                                                        fontSize: 16,
                                                        fontWeight:
                                                            FontWeight.w400,
                                                        color: Theme.of(context)
                                                            .primaryColorDark),
                                                    prefixIcon: const Padding(
                                                        padding:
                                                            EdgeInsets.all(8.0),
                                                        child: Icon(Icons
                                                            .search_sharp)),
                                                  ),
                                                  onChanged: (val) =>
                                                      onTextSearch(),
                                                )),
                                            const SizedBox(
                                              height: 20,
                                            ),
                                            individualAttendanceRegisterModel
                                                        .attendanceRegister!
                                                        .first
                                                        .attendeesEntries !=
                                                    null
                                                ? BlocListener<
                                                        MusterRollFromToDateSearchBloc,
                                                        MusterRollFromToDateSearchState>(
                                                    listener: (context,
                                                        musterSearch) {
                                                    musterSearch.maybeWhen(
                                                        orElse: () =>
                                                            Container(),
                                                        loading: () =>
                                                            shg_loader.Loaders
                                                                .circularLoader(
                                                                    context),
                                                        loaded: (MusterRollsModel?
                                                            musterRollsSearch) {
                                                          if (musterRollsSearch!
                                                                      .musterRoll !=
                                                                  null &&
                                                              musterRollsSearch
                                                                  .musterRoll!
                                                                  .isNotEmpty) {
                                                            existingSkills =
                                                                musterRollsSearch
                                                                    .musterRoll!
                                                                    .first
                                                                    .individualEntries!
                                                                    .map((e) =>
                                                                        IndividualSkills(
                                                                          individualId:
                                                                              e.individualId,
                                                                          skillCode:
                                                                              e.musterIndividualAdditionalDetails?.skillCode ?? '',
                                                                          name: e.musterIndividualAdditionalDetails?.userName ??
                                                                              e.individualId ??
                                                                              '',
                                                                          aadhaar: e.musterIndividualAdditionalDetails?.aadharNumber ??
                                                                              e.individualId ??
                                                                              '',
                                                                          individualGaurdianName:
                                                                              e.musterIndividualAdditionalDetails?.fatherName ?? '',
                                                                          id: e
                                                                              .id,
                                                                        ))
                                                                    .toList();
                                                            context
                                                                .read<
                                                                    MusterGetWorkflowBloc>()
                                                                .add(
                                                                  GetMusterWorkflowEvent(
                                                                      tenantId:
                                                                          widget
                                                                              .tenantId,
                                                                      musterRollNumber: musterRollsSearch
                                                                          .musterRoll!
                                                                          .first
                                                                          .musterRollNumber
                                                                          .toString()),
                                                                );
                                                          } else {
                                                            existingSkills
                                                                .clear();
                                                          }
                                                        });
                                                  }, child: BlocBuilder<
                                                        MusterRollEstimateBloc,
                                                        MusterRollEstimateState>(
                                                    builder:
                                                        (context, musterState) {
                                                      return musterState
                                                          .maybeWhen(
                                                        orElse: () =>
                                                            Container(),
                                                        loading: () =>
                                                            shg_loader.Loaders
                                                                .circularLoader(
                                                                    context),
                                                        loaded:
                                                            (EstimateMusterRollsModel?
                                                                musterRollsModel) {
                                                          List<AttendeesTrackList> attendeeList = individualAttendanceRegisterModel
                                                              .attendanceRegister!
                                                              .first
                                                              .attendeesEntries!
                                                              .where((e) =>
                                                                  e.denrollmentDate == null ||
                                                                  !(e.denrollmentDate! <=
                                                                      individualAttendanceRegisterModel
                                                                          .attendanceRegister!
                                                                          .first
                                                                          .endDate!
                                                                          .toInt()))
                                                              .toList()
                                                              .map((e) => AttendeesTrackList(
                                                                  name: e.additionalDetails?.individualName ??
                                                                      '',
                                                                  aadhaar:
                                                                      e.additionalDetails?.identifierId ??
                                                                          '',
                                                                  individualId:
                                                                      e.individualId,
                                                                  individualGaurdianName: e.additionalDetails?.individualGaurdianName ?? ''))
                                                              .toList();
                                                          if (attendeeList !=
                                                                  null &&
                                                              attendeeList
                                                                  .isNotEmpty) {
                                                            if (musterRollsModel
                                                                        ?.musterRoll !=
                                                                    null &&
                                                                musterRollsModel!
                                                                    .musterRoll!
                                                                    .isNotEmpty &&
                                                                musterRollsModel
                                                                    .musterRoll!
                                                                    .first
                                                                    .individualEntries!
                                                                    .isNotEmpty) {
                                                              List<EstimateIndividualEntries>?
                                                                  estimateMusterRoll =
                                                                  musterRollsModel
                                                                      .musterRoll!
                                                                      .first
                                                                      .individualEntries;
                                                              attendeeList = individualAttendanceRegisterModel
                                                                  .attendanceRegister!
                                                                  .first
                                                                  .attendeesEntries!
                                                                  .map((e) => AttendeesTrackList(
                                                                      name: existingSkills.isNotEmpty
                                                                          ? existingSkills.firstWhere((s) => s.individualId == e.individualId, orElse: () => IndividualSkills()).name
                                                                          : estimateMusterRoll!.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty
                                                                              ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.musterIndividualAdditionalDetails?.userName
                                                                              : e.additionalDetails?.individualName ?? '',
                                                                      aadhaar: existingSkills.isNotEmpty
                                                                          ? existingSkills.firstWhere((s) => s.individualId == e.individualId, orElse: () => IndividualSkills()).aadhaar
                                                                          : estimateMusterRoll!.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty
                                                                              ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.musterIndividualAdditionalDetails?.aadharNumber
                                                                              : e.additionalDetails?.identifierId ?? '',
                                                                      individualGaurdianName: existingSkills.isNotEmpty
                                                                          ? existingSkills.firstWhere((s) => s.individualId == e.individualId, orElse: () => IndividualSkills()).individualGaurdianName
                                                                          : estimateMusterRoll!.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty
                                                                              ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.musterIndividualAdditionalDetails?.fatherName
                                                                              : e.additionalDetails?.individualGaurdianName ?? '',
                                                                      individualId: e.individualId,
                                                                      skillCodeList: estimateMusterRoll!.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.musterIndividualAdditionalDetails?.skillCode ?? [] : [],
                                                                      id: existingSkills.isNotEmpty
                                                                          ? existingSkills.firstWhere((s) => s.individualId == e.individualId, orElse: () => IndividualSkills()).id
                                                                          : estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty
                                                                              ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.id
                                                                              : '',
                                                                      skill: existingSkills.isNotEmpty ? existingSkills.firstWhere((s) => s.individualId == e.individualId, orElse: () => IndividualSkills()).skillCode : '',
                                                                      monEntryId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                                                                      monExitId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                                                                      monIndex: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendance ?? -1 : -1,
                                                                      tueEntryId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                                                                      tueExitId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                                                                      tueIndex: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendance ?? -1 : -1,
                                                                      wedEntryId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                                                                      wedExitId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                                                                      wedIndex: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendance ?? -1 : -1,
                                                                      thuEntryId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                                                                      thuExitId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                                                                      thursIndex: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendance ?? -1 : -1,
                                                                      friEntryId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                                                                      friExitId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                                                                      friIndex: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendance ?? -1 : -1,
                                                                      satEntryId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                                                                      satExitId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                                                                      satIndex: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendance ?? -1 : -1,
                                                                      sunEntryId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendanceEntriesAdditionalDetails?.entryAttendanceLogId : null,
                                                                      sunExitId: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendanceEntriesAdditionalDetails?.exitAttendanceLogId : null,
                                                                      sunIndex: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendance ?? -1 : -1,
                                                                      auditDetails: estimateMusterRoll.where((mu) => mu.individualId == e.individualId).toList().isNotEmpty ? estimateMusterRoll.where((m) => m.individualId == e.individualId).first.attendanceEntries!.first.auditDetails : null))
                                                                  .toList();
                                                              if (newList
                                                                  .isEmpty) {
                                                                for (var i = 0;
                                                                    i <
                                                                        attendeeList
                                                                            .length;
                                                                    i++) {
                                                                  var item1 =
                                                                      attendeeList[
                                                                          i];
                                                                  TrackAttendanceTableData
                                                                      data =
                                                                      TrackAttendanceTableData();
                                                                  data.name =
                                                                      item1.name ??
                                                                          '';
                                                                  data.aadhaar =
                                                                      item1.aadhaar ??
                                                                          '';
                                                                  data.individualGaurdianName =
                                                                      item1.individualGaurdianName ??
                                                                          '';
                                                                  data.individualId =
                                                                      item1.individualId ??
                                                                          '';
                                                                  data.id =
                                                                      item1.id ??
                                                                          '';
                                                                  data.skill =
                                                                      item1.skill ??
                                                                          '';
                                                                  data.skillCodeList =
                                                                      item1.skillCodeList ??
                                                                          [];
                                                                  data.monIndex =
                                                                      item1
                                                                          .monIndex;
                                                                  data.monEntryId =
                                                                      item1
                                                                          .monEntryId;
                                                                  data.monExitId =
                                                                      item1
                                                                          .monExitId;
                                                                  data.tueIndex =
                                                                      item1
                                                                          .tueIndex;
                                                                  data.tueEntryId =
                                                                      item1
                                                                          .tueEntryId;
                                                                  data.tueExitId =
                                                                      item1
                                                                          .tueExitId;
                                                                  data.wedIndex =
                                                                      item1
                                                                          .wedIndex;
                                                                  data.wedEntryId =
                                                                      item1
                                                                          .wedEntryId;
                                                                  data.wedExitId =
                                                                      item1
                                                                          .wedExitId;
                                                                  data.thuIndex =
                                                                      item1
                                                                          .thursIndex;
                                                                  data.thuEntryId =
                                                                      item1
                                                                          .thuEntryId;
                                                                  data.thuExitId =
                                                                      item1
                                                                          .thuExitId;
                                                                  data.friIndex =
                                                                      item1
                                                                          .friIndex;
                                                                  data.friEntryId =
                                                                      item1
                                                                          .friEntryId;
                                                                  data.friExitId =
                                                                      item1
                                                                          .friExitId;
                                                                  data.satIndex =
                                                                      item1
                                                                          .satIndex;
                                                                  data.satEntryId =
                                                                      item1
                                                                          .satEntryId;
                                                                  data.satExitId =
                                                                      item1
                                                                          .satExitId;
                                                                  data.sunIndex =
                                                                      item1
                                                                          .sunIndex;
                                                                  data.sunEntryId =
                                                                      item1
                                                                          .sunEntryId;
                                                                  data.sunExitId =
                                                                      item1
                                                                          .sunExitId;
                                                                  data.auditDetails =
                                                                      item1
                                                                          .auditDetails;
                                                                  newList.add(
                                                                      data);
                                                                }
                                                              }
                                                            } else {
                                                              if (newList
                                                                  .isEmpty) {
                                                                for (var i = 0;
                                                                    i <
                                                                        attendeeList
                                                                            .length;
                                                                    i++) {
                                                                  var item1 =
                                                                      attendeeList[
                                                                          i];
                                                                  TrackAttendanceTableData
                                                                      data =
                                                                      TrackAttendanceTableData();
                                                                  data.name =
                                                                      item1.name ??
                                                                          '';
                                                                  data.aadhaar =
                                                                      item1.aadhaar ??
                                                                          '';
                                                                  data.individualGaurdianName =
                                                                      item1.individualGaurdianName ??
                                                                          '';
                                                                  data.individualId =
                                                                      item1.individualId ??
                                                                          '';
                                                                  data.id =
                                                                      item1.id;
                                                                  data.skill =
                                                                      item1.skill ??
                                                                          '';
                                                                  data.skillCodeList =
                                                                      item1.skillCodeList ??
                                                                          [];
                                                                  data.monIndex =
                                                                      item1
                                                                          .monIndex;
                                                                  data.tueIndex =
                                                                      item1
                                                                          .tueIndex;
                                                                  data.wedIndex =
                                                                      item1
                                                                          .wedIndex;
                                                                  data.thuIndex =
                                                                      item1
                                                                          .thursIndex;
                                                                  data.friIndex =
                                                                      item1
                                                                          .friIndex;
                                                                  data.satIndex =
                                                                      item1
                                                                          .satIndex;
                                                                  data.sunIndex =
                                                                      item1
                                                                          .sunIndex;
                                                                  data.auditDetails =
                                                                      item1
                                                                          .auditDetails;
                                                                  newList.add(
                                                                      data);
                                                                }
                                                              }
                                                            }
                                                            tableData =
                                                                getAttendanceData(
                                                                    newList);
                                                            return Column(
                                                                crossAxisAlignment:
                                                                    CrossAxisAlignment
                                                                        .start,
                                                                children: [
                                                                  Padding(
                                                                    padding:
                                                                        const EdgeInsets.all(
                                                                            8.0),
                                                                    child: shg_app
                                                                        .DigitTable(
                                                                      headerList:
                                                                          headerList,
                                                                      tableData:
                                                                          tableData,
                                                                      leftColumnWidth:
                                                                          width,
                                                                      rightColumnWidth: skillsDisable
                                                                          ? width *
                                                                              9
                                                                          : width *
                                                                              10,
                                                                      height: 58 +
                                                                          (52.0 *
                                                                              (tableData.length + 1)),
                                                                      scrollPhysics:
                                                                          const NeverScrollableScrollPhysics(),
                                                                    ),
                                                                  ),
                                                                  Align(
                                                                    alignment:
                                                                        Alignment
                                                                            .bottomCenter,
                                                                    child:
                                                                        Padding(
                                                                      padding:
                                                                          const EdgeInsets
                                                                              .only(
                                                                        left:
                                                                            8.0,
                                                                        right:
                                                                            8.0,
                                                                      ),
                                                                      child: SizedBox(
                                                                          height: isInWorkFlow ? 0 : 100,
                                                                          child: BlocBuilder<MusterRollFromToDateSearchBloc, MusterRollFromToDateSearchState>(builder: (context, musterSearchState) {
                                                                            return musterSearchState.maybeWhen(
                                                                                orElse: () => Container(),
                                                                                loading: () => shg_loader.Loaders.circularLoader(context),
                                                                                loaded: (MusterRollsModel? musterRollsSearch) {
                                                                                  return BlocListener<MusterGetWorkflowBloc, MusterGetWorkflowState>(
                                                                                    listener: (context, workflowState) {
                                                                                      workflowState.maybeWhen(
                                                                                          error: () {
                                                                                            Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.unableToCheckWorkflowStatus), 'ERROR');
                                                                                          },
                                                                                          loading: () => shg_loader.Loaders.circularLoader(context),
                                                                                          loaded: (MusterWorkFlowModel? musterWorkFlowModel, bool inWorkFlow) {
                                                                                            if (!inWorkFlow) {
                                                                                              if (isInWorkFlow != false) {
                                                                                                setState(() {
                                                                                                  isInWorkFlow = false;
                                                                                                });
                                                                                              }
                                                                                            } else {
                                                                                              if (musterRollsSearch!.musterRoll!.isNotEmpty && selectedDateRange != null) {
                                                                                                if (skillsDisable != false) {
                                                                                                  setState(() {
                                                                                                    skillsDisable = false;
                                                                                                  });
                                                                                                }
                                                                                                if (isInWorkFlow != true) {
                                                                                                  setState(() {
                                                                                                    isInWorkFlow = true;
                                                                                                  });
                                                                                                }
                                                                                              }
                                                                                            }
                                                                                            return Container();
                                                                                          },
                                                                                          orElse: () {
                                                                                            return Container();
                                                                                          });
                                                                                    },
                                                                                    child: BlocBuilder<MusterGetWorkflowBloc, MusterGetWorkflowState>(builder: (context, workflowState) {
                                                                                      return isInWorkFlow
                                                                                          ? Container()
                                                                                          : Column(
                                                                                              children: [
                                                                                                BlocListener<AttendanceLogCreateBloc, AttendanceLogCreateState>(
                                                                                                  listener: (context, logState) {
                                                                                                    SchedulerBinding.instance.addPostFrameCallback((_) {
                                                                                                      logState.maybeWhen(
                                                                                                          error: () {
                                                                                                            if (!hasLoaded && selectedDateRange != null) {
                                                                                                              Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceLoggedFailed), 'ERROR');
                                                                                                              onSubmit(widget.id);
                                                                                                              hasLoaded = true;
                                                                                                            }
                                                                                                          },
                                                                                                          loading: () => shg_loader.Loaders.circularLoader(context),
                                                                                                          loaded: () {
                                                                                                            if (!hasLoaded && selectedDateRange != null) {
                                                                                                              Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceLoggedSuccess), 'SUCCESS');
                                                                                                              onSubmit(widget.id);
                                                                                                              hasLoaded = true;
                                                                                                            }
                                                                                                          },
                                                                                                          orElse: () => Container());
                                                                                                    });
                                                                                                  },
                                                                                                  child: OutlinedButton(
                                                                                                      style: OutlinedButton.styleFrom(backgroundColor: Colors.white, side: BorderSide(width: 2, color: DigitTheme.instance.colorScheme.secondary)),
                                                                                                      onPressed: musterRollsSearch != null && musterRollsSearch.musterRoll!.isNotEmpty && isInWorkFlow
                                                                                                          ? null
                                                                                                          : () {
                                                                                                              if (selectedDateRange == null) {
                                                                                                                Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.selectDateRangeFirst), 'ERROR');
                                                                                                              } else {
                                                                                                                hasLoaded = false;
                                                                                                                if (updateAttendeePayload.isNotEmpty && createAttendeePayload.isNotEmpty) {
                                                                                                                  context.read<AttendanceLogCreateBloc>().add(UpdateAttendanceLogEvent(attendanceList: updateAttendeePayload));
                                                                                                                  context.read<AttendanceLogCreateBloc>().add(CreateAttendanceLogEvent(attendanceList: createAttendeePayload));
                                                                                                                } else if (updateAttendeePayload.isNotEmpty) {
                                                                                                                  context.read<AttendanceLogCreateBloc>().add(UpdateAttendanceLogEvent(attendanceList: updateAttendeePayload));
                                                                                                                } else if (createAttendeePayload.isNotEmpty) {
                                                                                                                  context.read<AttendanceLogCreateBloc>().add(CreateAttendanceLogEvent(attendanceList: createAttendeePayload));
                                                                                                                }
                                                                                                              }
                                                                                                            },
                                                                                                      child: Center(
                                                                                                          child: Text(
                                                                                                        AppLocalizations.of(context).translate(i18.common.saveAsDraft),
                                                                                                        style: createAttendeePayload.isEmpty && updateAttendeePayload.isEmpty ? Theme.of(context).textTheme.titleSmall?.apply(color: const Color.fromRGBO(149, 148, 148, 1)) : Theme.of(context).textTheme.titleSmall,
                                                                                                      ))),
                                                                                                ),
                                                                                                const SizedBox(
                                                                                                  height: 10,
                                                                                                ),
                                                                                                BlocListener<MusterCreateBloc, MusterCreateState>(
                                                                                                  listener: (context, musterCreateState) {
                                                                                                    musterCreateState.maybeWhen(
                                                                                                        error: () {
                                                                                                          if (!createMusterLoaded && selectedDateRange != null) {
                                                                                                            Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.musterCreateFailed), 'ERROR');
                                                                                                            // onSubmit(registerId.toString());
                                                                                                            createMusterLoaded = true;
                                                                                                            context.router.popAndPush(TrackAttendanceRoute(
                                                                                                              tenantId: widget.tenantId.toString(),
                                                                                                              id: widget.id.toString(),
                                                                                                            ));
                                                                                                          }
                                                                                                        },
                                                                                                        loaded: (MusterRollsModel? createdMuster) {
                                                                                                          if (!createMusterLoaded && selectedDateRange != null) {
                                                                                                            Notifiers.getToastMessage(context, ' ${createdMuster?.musterRoll?.first.musterRollNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.musterSentForApproval)}', 'SUCCESS');
                                                                                                            createMusterLoaded = true;
                                                                                                            // onSubmit(registerId.toString());
                                                                                                            context.router.popAndPush(TrackAttendanceRoute(
                                                                                                              tenantId: widget!.tenantId.toString(),
                                                                                                              id: widget.id.toString(),
                                                                                                            ));
                                                                                                          }
                                                                                                        },
                                                                                                        orElse: () => Container());
                                                                                                  },
                                                                                                  child: DigitElevatedButton(
                                                                                                    onPressed: selectedDateRange!.endDate > DateTime.now().millisecondsSinceEpoch
                                                                                                        ? null
                                                                                                        : musterRollsModel?.musterRoll != null && musterRollsModel!.musterRoll!.first.individualEntries != null && musterRollsModel!.musterRoll!.first.individualEntries!.isNotEmpty
                                                                                                            ? musterRollsSearch?.musterRoll != null && musterRollsSearch!.musterRoll!.isNotEmpty
                                                                                                                ? isInWorkFlow == false
                                                                                                                    ? () {
                                                                                                                        if (selectedDateRange == null) {
                                                                                                                          Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.selectDateRangeFirst), 'ERROR');
                                                                                                                        } else if (updateAttendeePayload.isNotEmpty) {
                                                                                                                          Notifiers.getToastMessage(context, 'Attendance changed, Please click on Save as Draft first', 'INFO');
                                                                                                                        } else if (skillsDisable || newList.where((n) => n.skillCodeList?.isNotEmpty ?? false).any((e) => e.skill == null && e.skill.toString().isEmpty)) {
                                                                                                                          setState(() {
                                                                                                                            skillsDisable = false;
                                                                                                                          });
                                                                                                                          Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.reviewSkills), 'INFO');
                                                                                                                        } else {
                                                                                                                          createMusterLoaded = false;
                                                                                                                          context.read<MusterCreateBloc>().add(UpdateMusterEvent(tenantId: widget.tenantId, id: musterRollsSearch.musterRoll!.first.id.toString(), orgName: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.orgName ?? 'NA', contractId: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.contractId ?? 'NA', registerNo: individualAttendanceRegisterModel.attendanceRegister?.first.registerNumber ?? 'NA', registerName: individualAttendanceRegisterModel.attendanceRegister?.first.name ?? 'NA', skillsList: skillsPayLoad));
                                                                                                                        }
                                                                                                                      }
                                                                                                                    : null
                                                                                                                : () {
                                                                                                                    if (selectedDateRange == null) {
                                                                                                                      Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.selectDateRangeFirst), 'ERROR');
                                                                                                                    } else if (createAttendeePayload.isNotEmpty) {
                                                                                                                      Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceChangedValidation), 'INFO');
                                                                                                                    } else if (skillsDisable || newList.where((n) => n.skillCodeList?.isNotEmpty ?? false).any((e) => e.skill == null || e.skill.toString().isEmpty)) {
                                                                                                                      setState(() {
                                                                                                                        skillsDisable = false;
                                                                                                                      });
                                                                                                                      Notifiers.getToastMessage(context, AppLocalizations.of(context).translate(i18.attendanceMgmt.reviewSkills), 'INFO');
                                                                                                                    } else {
                                                                                                                      createMusterLoaded = false;
                                                                                                                      context.read<MusterCreateBloc>().add(CreateMusterEvent(tenantId: widget.tenantId, registerId: widget.id, startDate: selectedDateRange!.startDate, serviceCode: individualAttendanceRegisterModel.attendanceRegister?.first.serviceCode, referenceId: individualAttendanceRegisterModel.attendanceRegister?.first.referenceId, orgName: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.orgName ?? 'NA', contractId: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.contractId ?? 'NA', executingAuthority: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.executingAuthority, registerNo: individualAttendanceRegisterModel.attendanceRegister?.first.registerNumber ?? 'NA', registerName: individualAttendanceRegisterModel.attendanceRegister?.first.name ?? 'NA', projectName: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.projectName ?? '', projectType: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.projectType ?? '', projectDesc: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.projectDesc ?? '', projectId: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.projectId ?? '', locality: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.locality ?? '', ward: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.ward ?? '', amount: individualAttendanceRegisterModel.attendanceRegister?.first.attendanceRegisterAdditionalDetails?.amount ?? 14500, skillsList: skillsPayLoad));
                                                                                                                    }
                                                                                                                  }
                                                                                                            : null,
                                                                                                    child: Center(
                                                                                                      child: Text(AppLocalizations.of(context).translate(i18.common.sendForApproval), style: Theme.of(context).textTheme.titleSmall!.apply(color: Colors.white)),
                                                                                                    ),
                                                                                                  ),
                                                                                                ),
                                                                                              ],
                                                                                            );
                                                                                    }),
                                                                                  );
                                                                                });
                                                                          })),
                                                                    ),
                                                                  )
                                                                ]);
                                                          } else {
                                                            return Column(
                                                              children: [
                                                                const EmptyImage(
                                                                  align: Alignment
                                                                      .center,
                                                                ),
                                                                ButtonLink(
                                                                  AppLocalizations.of(
                                                                          context)
                                                                      .translate(i18
                                                                          .attendanceMgmt
                                                                          .addNewWageSeeker),
                                                                  () {
                                                                    context
                                                                        .router
                                                                        .push(
                                                                            const RegisterIndividualRoute());
                                                                  },
                                                                  align: Alignment
                                                                      .center,
                                                                ),
                                                              ],
                                                            );
                                                          }
                                                        },
                                                      );
                                                    },
                                                  ))
                                                : Column(
                                                    children: [
                                                      const EmptyImage(
                                                        align: Alignment.center,
                                                      ),
                                                      ButtonLink(
                                                        AppLocalizations.of(
                                                                context)
                                                            .translate(i18
                                                                .attendanceMgmt
                                                                .addNewWageSeeker),
                                                        () {},
                                                        align: Alignment.center,
                                                      ),
                                                    ],
                                                  ),
                                          ]))
                                    ]),
                                  ),
                                ]);
                              },
                              orElse: () => Container());
                        });
                      },
                      loading: () =>
                          shg_loader.Loaders.circularLoader(context));
                });
              });
        }));
  }

  void _onDateChange(DateRangePickerSelectionChangedArgs args) {
    int firstDayOfWeek = DateTime.monday;
    int endDayOfWeek = DateTime.sunday;
    endDayOfWeek = endDayOfWeek <= 0 ? 7 + endDayOfWeek : endDayOfWeek;
    PickerDateRange ranges = args.value;
    DateTime date1 = ranges.startDate!;
    DateTime date2 = ranges.endDate ?? ranges.startDate!;
    if (date1.isAfter(date2)) {
      var date = date1;
      date1 = date2;
      date2 = date;
    }

    int day1 = date1.weekday % 7;
    int day2 = date2.weekday % 7;
    if (day2 == 0) {
      day2 = 7;
    }
    DateTime dat1 = date1.add(Duration(days: (firstDayOfWeek - day1)));
    DateTime dat2 = date2.add(Duration(days: (endDayOfWeek - day2)));
    if (dat1.compareTo(ranges.startDate!) != 0 ||
        dat2.compareTo(ranges.endDate!) != 0) {
      rangePickerController.selectedRange = PickerDateRange(dat1, dat2);
      dateController.text = '${DateFormat('dd/MM/yyyy').format(dat1)} -'
          ' ${DateFormat('dd/MM/yyyy').format(dat2)}';
      selectedDateRange = DateRange(
          dateController.text,
          DateFormats.dateToTimeStamp(DateFormat('dd/MM/yyyy').format(dat1)),
          DateFormats.dateToTimeStamp(DateFormat('dd/MM/yyyy').format(dat2)));
    }
  }

  void onTextSearch() {
    if (searchController.text.isNotEmpty) {
      setState(() {
        newList.retainWhere((e) => e.name!
            .toLowerCase()
            .contains(searchController.text.toLowerCase()));
      });
    } else {
      onSubmit(widget.id);
    }
  }

  void onSubmit(String id) {
    if (selectedDateRange != null) {
      newList.clear();
      updateAttendeePayload.clear();
      createAttendeePayload.clear();
      context.read<MusterRollFromToDateSearchBloc>().add(
            SearchMusterRollFromToDateEvent(
                registerId: registerId ?? '',
                tenantId: widget.tenantId.toString(),
                fromDate: selectedDateRange!.startDate,
                toDate: selectedDateRange!.endDate),
          );
      context.read<MusterRollEstimateBloc>().add(
            EstimateMusterRollEvent(
              tenantId: widget.tenantId,
              registerId: id.toString(),
              startDate: selectedDateRange!.startDate,
              endDate: selectedDateRange!.endDate,
            ),
          );
      dates = DateFormats.getFormattedDatesOfAWeek(
          selectedDateRange!.startDate, selectedDateRange!.endDate);
      daysInRange = DateFormats.checkDaysInRange(selectedDateRange!.startDate,
          selectedDateRange!.endDate, registerStartDate!, registerEndDate!);
      isInWorkFlow = false;
      skillsDisable = true;
    } else {
      Notifiers.getToastMessage(
          context,
          AppLocalizations.of(context)
              .translate(i18.attendanceMgmt.selectDateRangeFirst),
          'ERROR');
    }
  }

  void _onViewChangedDate(DateRangePickerViewChangedArgs args) {
    final DateTime visibleStartDate = args.visibleDateRange.startDate!;
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
            hide: skillsDisable),
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
          hide: skillsDisable && !isInWorkFlow,
          apiKey: tableDataModel.skill,
          widget: DropDownDialog(
            isDisabled: isInWorkFlow,
            options: tableDataModel.skillCodeList ?? [],
            label: i18.common.selectSkill,
            selectedOption: tableDataModel.skill.toString(),
            onChanged: (val) {
              tableDataModel.skill = val;
              if (skillsPayLoad
                  .where(
                      (e) => e["individualId"] == tableDataModel.individualId)
                  .isNotEmpty) {
                skillsPayLoad.removeWhere((elem) =>
                    elem["individualId"] == tableDataModel.individualId);
                if (tableDataModel.id != null &&
                    tableDataModel.id!.trim().isNotEmpty) {
                  skillsPayLoad.add({
                    "id": tableDataModel.id,
                    "additionalDetails": {"code": val}
                  });
                } else {
                  skillsPayLoad.add({
                    "individualId": tableDataModel.individualId,
                    "additionalDetails": {"code": val}
                  });
                }
              } else {
                if (tableDataModel.id != null &&
                    tableDataModel.id!.trim().isNotEmpty) {
                  skillsPayLoad.add({
                    "id": tableDataModel.id,
                    "additionalDetails": {"code": val}
                  });
                } else {
                  skillsPayLoad.add({
                    "individualId": tableDataModel.individualId,
                    "additionalDetails": {"code": val}
                  });
                }
              }
            },
          )),
      TableData(
        apiKey: tableDataModel.monIndex.toString(),
        widget: CircularButton(
          icon: Icons.circle_rounded,
          size: 15,
          color: const Color.fromRGBO(0, 100, 0, 1),
          index: tableDataModel.monIndex ?? -1,
          isNotGreyed: false,
          onTap: daysInRange == null || !daysInRange!.monday
              ? null
              : entryExitList!.length > 2
                  ? () => onTapButton(
                      tableDataModel.individualId ?? '',
                      'mon',
                      tableDataModel.monEntryId,
                      tableDataModel.monExitId,
                      tableDataModel.auditDetails)
                  : () => onTapOnlyAbsentPresent(
                      tableDataModel.individualId ?? '',
                      'mon',
                      tableDataModel.monEntryId,
                      tableDataModel.monExitId,
                      tableDataModel.auditDetails),
        ),
      ),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 15,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.tueIndex ?? -1,
        isNotGreyed: false,
        onTap: daysInRange == null || !daysInRange!.tuesday
            ? null
            : entryExitList!.length > 2
                ? () => onTapButton(
                    tableDataModel.individualId ?? '',
                    'tue',
                    tableDataModel.tueEntryId,
                    tableDataModel.tueExitId,
                    tableDataModel.auditDetails)
                : () => onTapOnlyAbsentPresent(
                    tableDataModel.individualId ?? '',
                    'tue',
                    tableDataModel.tueEntryId,
                    tableDataModel.tueExitId,
                    tableDataModel.auditDetails),
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 15,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.wedIndex ?? -1,
        isNotGreyed: false,
        onTap: daysInRange == null || !daysInRange!.wednesday
            ? null
            : entryExitList!.length > 2
                ? () => onTapButton(
                    tableDataModel.individualId ?? '',
                    'wed',
                    tableDataModel.wedEntryId,
                    tableDataModel.wedExitId,
                    tableDataModel.auditDetails)
                : () => onTapOnlyAbsentPresent(
                    tableDataModel.individualId ?? '',
                    'wed',
                    tableDataModel.wedEntryId,
                    tableDataModel.wedExitId,
                    tableDataModel.auditDetails),
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 15,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.thuIndex ?? -1,
        isNotGreyed: false,
        onTap: daysInRange == null || !daysInRange!.thursday
            ? null
            : entryExitList!.length > 2
                ? () => onTapButton(
                    tableDataModel.individualId ?? '',
                    'thu',
                    tableDataModel.thuEntryId,
                    tableDataModel.thuExitId,
                    tableDataModel.auditDetails)
                : () => onTapOnlyAbsentPresent(
                    tableDataModel.individualId ?? '',
                    'thu',
                    tableDataModel.thuEntryId,
                    tableDataModel.thuExitId,
                    tableDataModel.auditDetails),
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 15,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.friIndex ?? -1,
        isNotGreyed: false,
        onTap: daysInRange == null || !daysInRange!.friday
            ? null
            : entryExitList!.length > 2
                ? () => onTapButton(
                    tableDataModel.individualId ?? '',
                    'fri',
                    tableDataModel.friEntryId,
                    tableDataModel.friExitId,
                    tableDataModel.auditDetails)
                : () => onTapOnlyAbsentPresent(
                    tableDataModel.individualId ?? '',
                    'fri',
                    tableDataModel.friEntryId,
                    tableDataModel.friExitId,
                    tableDataModel.auditDetails),
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 15,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.satIndex ?? -1,
        isNotGreyed: false,
        onTap: daysInRange == null || !daysInRange!.saturday
            ? null
            : entryExitList!.length > 2
                ? () => onTapButton(
                    tableDataModel.individualId ?? '',
                    'sat',
                    tableDataModel.satEntryId,
                    tableDataModel.satExitId,
                    tableDataModel.auditDetails)
                : () => onTapOnlyAbsentPresent(
                    tableDataModel.individualId ?? '',
                    'sat',
                    tableDataModel.satEntryId,
                    tableDataModel.satExitId,
                    tableDataModel.auditDetails),
      )),
      TableData(
          widget: CircularButton(
        icon: Icons.circle_rounded,
        size: 15,
        color: const Color.fromRGBO(0, 100, 0, 1),
        index: tableDataModel.sunIndex ?? -1,
        isNotGreyed: false,
        onTap: daysInRange == null || !daysInRange!.sunday
            ? null
            : entryExitList!.length > 2
                ? () => onTapButton(
                    tableDataModel.individualId ?? '',
                    'sun',
                    tableDataModel.sunEntryId,
                    tableDataModel.sunExitId,
                    tableDataModel.auditDetails)
                : () => onTapOnlyAbsentPresent(
                    tableDataModel.individualId ?? '',
                    'sun',
                    tableDataModel.sunEntryId,
                    tableDataModel.sunExitId,
                    tableDataModel.auditDetails),
      )),
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

  double convertedValue(double tableVal) {
    if (tableVal < 0) {
      return 0;
    } else {
      return tableVal;
    }
  }

  List<TableDataRow> getAttendanceData(List<TrackAttendanceTableData> list) {
    return list.map((e) => getAttendanceRow(e)).toList();
  }

  void onTapButton(individualId, day, entryID, exitId, auditDetails) {
    int morning =
        entryExitList?.firstWhere((e) => e.code == 'MORNING').hours ?? 0;
    int afternoon =
        entryExitList?.firstWhere((e) => e.code == 'AFTERNOON').hours ?? 0;
    int evening =
        entryExitList?.firstWhere((e) => e.code == 'EVENING').hours ?? 0;
    int index = newList.indexWhere((item) => item.individualId == individualId);

    if (index != -1) {
      setState(() {
        if (newList[index].getProperty(day) == 0.0 ||
            newList[index].getProperty(day) == -1) {
          newList[index].setProperty(day, 1.0);
          if (entryID != null && exitId != null) {
            updateAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            updateAttendeePayload.addAll(updateAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    evening),
                entryID,
                exitId,
                widget.tenantId,
                auditDetails,
                true,
                true));
          } else {
            createAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            createAttendeePayload.addAll(createAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    evening),
                widget.tenantId));
          }
        } else if (newList[index].getProperty(day) == 0.5) {
          newList[index].setProperty(day, 0.0);
          if (entryID != null && exitId != null) {
            updateAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            updateAttendeePayload.addAll(updateAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                entryID,
                exitId,
                widget.tenantId,
                auditDetails,
                false,
                false));
          } else {
            createAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
          }
        } else {
          newList[index].setProperty(day, 0.5);
          if (entryID != null && exitId != null) {
            updateAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            updateAttendeePayload.addAll(updateAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    afternoon),
                entryID,
                exitId,
                widget.tenantId,
                auditDetails,
                true,
                true));
          } else {
            createAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            createAttendeePayload.addAll(createAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    afternoon),
                widget.tenantId));
          }
        }
      });
    }
  }

  void onTapOnlyAbsentPresent(
      individualId, day, entryID, exitId, auditDetails) {
    int morning =
        entryExitList?.firstWhere((e) => e.code == 'MORNING').hours ?? 0;
    int evening =
        entryExitList?.firstWhere((e) => e.code == 'EVENING').hours ?? 0;
    int index = newList.indexWhere((item) => item.individualId == individualId);

    if (index != -1) {
      setState(() {
        if (newList[index].getProperty(day) == 0.0 ||
            newList[index].getProperty(day) == -1) {
          newList[index].setProperty(day, 1.0);
          if (entryID != null && exitId != null) {
            updateAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            updateAttendeePayload.addAll(updateAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    evening),
                entryID,
                exitId,
                widget.tenantId,
                auditDetails,
                true,
                true));
          } else {
            createAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            createAttendeePayload.addAll(createAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    evening),
                widget.tenantId));
          }
        } else {
          newList[index].setProperty(day, 0.0);
          if (entryID != null && exitId != null) {
            updateAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
            updateAttendeePayload.addAll(updateAttendanceLogPayload(
                newList[index],
                registerId ?? '',
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                DateFormats.getTimestampFromWeekDay(
                    DateFormats.getDateFromTimestamp(
                        selectedDateRange!.startDate),
                    day,
                    morning),
                entryID,
                exitId,
                widget.tenantId,
                auditDetails,
                false,
                false));
          } else {
            createAttendeePayload.removeWhere((e) =>
                e['individualId'] == individualId &&
                DateFormats.getDay(e['time']).toLowerCase() == day);
          }
        }
      });
    }
  }
}
