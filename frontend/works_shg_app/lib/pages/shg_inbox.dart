// import 'package:digit_components/digit_components.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter/scheduler.dart';
// import 'package:flutter_bloc/flutter_bloc.dart';
// import 'package:syncfusion_flutter_datepicker/datepicker.dart';
// import 'package:works_shg_app/blocs/muster_rolls/create_muster.dart';
// import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
// import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
// import 'package:works_shg_app/widgets/Back.dart';
// import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
// import 'package:works_shg_app/widgets/atoms/empty_image.dart';
// import 'package:works_shg_app/widgets/molecules/digit_table.dart' as shg_app;
//
// import '../blocs/attendance/attendance_create_log.dart';
// import '../blocs/attendance/attendance_hours_mdms.dart';
// import '../blocs/localization/app_localization.dart';
// import '../blocs/muster_rolls/get_muster_workflow.dart';
// import '../blocs/muster_rolls/muster_roll_estimate.dart';
// import '../models/attendance/attendee_model.dart';
// import '../models/muster_rolls/muster_workflow_model.dart';
// import '../router/app_router.dart';
// import '../utils/constants.dart';
// import '../utils/date_formats.dart';
// import '../utils/models.dart';
// import '../utils/models/track_attendance_payload.dart';
// import '../utils/notifiers.dart';
// import '../widgets/ButtonLink.dart';
// import '../widgets/CircularButton.dart';
// import '../widgets/SideBar.dart';
// import '../widgets/drawer_wrapper.dart';
// import '../widgets/loaders.dart';
//
// class SHGInboxPage extends StatefulWidget {
//   final String tenantId;
//   final String musterRollNo;
//   final List<Map<String, dynamic>> projectDetails;
//
//   const SHGInboxPage(@PathParam('tenantId') this.tenantId,
//       @PathParam('musterRollNo') this.musterRollNo, this.projectDetails,
//       {Key? key})
//       : super(key: key);
//
//   @override
//   State<StatefulWidget> createState() {
//     return _SHGInboxPage();
//   }
// }
//
// class _SHGInboxPage extends State<SHGInboxPage> {
//   // AttendanceRegister? _attendanceRegister;
//   DateRangePickerController rangePickerController = DateRangePickerController();
//   DateRangePickerSelectionMode selectionMode =
//       DateRangePickerSelectionMode.single;
//   String? registerId;
//   String? musterId;
//   DateRange? selectedDateRange;
//   var dateController = TextEditingController();
//   var searchController = TextEditingController();
//   List<TrackAttendanceTableData> newList = [];
//   List<Map<String, dynamic>> updateAttendeePayload = [];
//   List<Map<String, dynamic>> createAttendeePayload = [];
//   List<TableDataRow> tableData = [];
//   bool hasLoaded = true;
//   bool updateLoaded = true;
//   List<EntryExitModel>? entryExitList;
//   DaysInRange? daysInRange;
//   bool inWorkFlow = true;
//   bool workFlowLoaded = true;
//   @override
//   void initState() {
//     WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
//     super.initState();
//   }
//
//   afterViewBuild() {
//     context.read<MusterGetWorkflowBloc>().add(
//           GetMusterWorkflowEvent(
//               tenantId: widget.tenantId, musterRollNumber: widget.musterRollNo),
//         );
//     context.read<AttendanceHoursBloc>().add(
//           const AttendanceHoursEvent(),
//         );
//   }
//
//   @override
//   void dispose() {
//     // Clear the data when the widget is disposed
//     newList.clear();
//     tableData.clear();
//     super.dispose();
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     var width = MediaQuery.of(context).size.width < 760
//         ? 130.0
//         : (MediaQuery.of(context).size.width / 6);
//     return Scaffold(
//         appBar: AppBar(),
//         drawer: DrawerWrapper(const Drawer(
//             child: SideBar(
//           module: 'rainmaker-common,rainmaker-attendencemgmt',
//         ))),
//         body: BlocBuilder<AttendanceHoursBloc, AttendanceHoursState>(
//             builder: (context, mdmsState) {
//           if (mdmsState.isAttendanceMDMSLoaded &&
//               mdmsState.attendanceHoursList != null) {
//             entryExitList = mdmsState.attendanceHoursList!.attendanceHours
//                 ?.where((obj) => obj.active == true)
//                 .map((e) =>
//                     EntryExitModel(hours: int.parse(e.value), code: e.code))
//                 .toList();
//             return BlocBuilder<MusterRollSearchBloc, MusterRollSearchState>(
//                 builder: (context, state) {
//               if (!state.loading && state.individualMusterRollModel != null) {
//                 musterId =
//                     state.individualMusterRollModel!.musterRoll!.first.id;
//                 registerId = state
//                     .individualMusterRollModel!.musterRoll!.first.registerId;
//                 selectedDateRange = DateRange(
//                     '',
//                     state.individualMusterRollModel!.musterRoll!.first
//                             .startDate ??
//                         0,
//                     state.individualMusterRollModel!.musterRoll!.first
//                             .endDate ??
//                         0);
//                 daysInRange = DateFormats.checkDaysInRange(
//                     selectedDateRange!.startDate,
//                     selectedDateRange!.endDate,
//                     state.individualMusterRollModel!.musterRoll!.first
//                         .startDate!,
//                     state
//                         .individualMusterRollModel!.musterRoll!.first.endDate!);
//                 return Stack(children: [
//                   Container(
//                     color: const Color.fromRGBO(238, 238, 238, 1),
//                     padding:
//                         const EdgeInsets.only(left: 8, right: 8, bottom: 16),
//                     height: state.individualMusterRollModel!.musterRoll!.first
//                                 .individualEntries !=
//                             null
//                         ? MediaQuery.of(context).size.height - 150
//                         : MediaQuery.of(context).size.height,
//                     child: CustomScrollView(slivers: [
//                       SliverList(
//                           delegate: SliverChildListDelegate([
//                         Back(
//                           backLabel: AppLocalizations.of(context)
//                               .translate(i18.common.back),
//                         ),
//                         WorkDetailsCard(
//                           widget.projectDetails,
//                         ),
//                       ])),
//                       SliverToBoxAdapter(
//                           child: Column(
//                               crossAxisAlignment: CrossAxisAlignment.center,
//                               children: [
//                             const SizedBox(
//                               height: 20,
//                             ),
//                             Container(
//                                 margin: const EdgeInsets.all(8.0),
//                                 child: TextFormField(
//                                   controller: searchController,
//                                   autofocus: true,
//                                   decoration: InputDecoration(
//                                     hintText: AppLocalizations.of(context)
//                                         .translate(
//                                             i18.common.searchByNameAadhaar),
//                                     border: const OutlineInputBorder(
//                                       borderRadius: BorderRadius.zero,
//                                     ),
//                                     filled: true,
//                                     fillColor: Colors.white,
//                                     prefixIconConstraints: const BoxConstraints(
//                                         minWidth: 0, minHeight: 0),
//                                     prefixStyle: TextStyle(
//                                         fontSize: 16,
//                                         fontWeight: FontWeight.w400,
//                                         color:
//                                             Theme.of(context).primaryColorDark),
//                                     prefixIcon: const Padding(
//                                         padding: EdgeInsets.all(8.0),
//                                         child: Icon(Icons.search_sharp)),
//                                   ),
//                                   onChanged: (val) => onTextSearch(),
//                                 )),
//                             const SizedBox(
//                               height: 20,
//                             ),
//                             state.individualMusterRollModel!.musterRoll!.first
//                                         .individualEntries !=
//                                     null
//                                 ? BlocBuilder<MusterRollEstimateBloc,
//                                         MusterRollEstimateState>(
//                                     builder: (context, musterState) {
//                                     if (!musterState.loading &&
//                                         musterState.viewMusterRollsModel !=
//                                             null &&
//                                         musterState
//                                                 .viewMusterRollsModel!
//                                                 .musterRoll!
//                                                 .first
//                                                 .individualEntries !=
//                                             null &&
//                                         musterState
//                                             .viewMusterRollsModel!
//                                             .musterRoll!
//                                             .first
//                                             .individualEntries!
//                                             .isNotEmpty) {
//                                       List<AttendeesTrackList> attendeeList =
//                                           [];
//
//                                       if (musterState
//                                           .viewMusterRollsModel!
//                                           .musterRoll!
//                                           .first
//                                           .individualEntries!
//                                           .isNotEmpty) {
//                                         attendeeList = musterState
//                                             .viewMusterRollsModel!
//                                             .musterRoll!
//                                             .first
//                                             .individualEntries!
//                                             .map((e) => AttendeesTrackList(
//                                                 name: e
//                                                     .musterIndividualAdditionalDetails
//                                                     ?.userName,
//                                                 aadhaar: e
//                                                     .musterIndividualAdditionalDetails
//                                                     ?.aadharNumber,
//                                                 individualId: e.individualId,
//                                                 monEntryId: e.attendanceEntries!
//                                                     .lastWhere((att) =>
//                                                         DateFormats.getDay(att.time!) ==
//                                                         'Mon')
//                                                     .attendanceEntriesAdditionalDetails
//                                                     ?.entryAttendanceLogId,
//                                                 monExitId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendanceEntriesAdditionalDetails?.exitAttendanceLogId,
//                                                 monIndex: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Mon').attendance ?? 0.0,
//                                                 tueEntryId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendanceEntriesAdditionalDetails?.entryAttendanceLogId,
//                                                 tueExitId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendanceEntriesAdditionalDetails?.exitAttendanceLogId,
//                                                 tueIndex: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Tue').attendance ?? 0.0,
//                                                 wedEntryId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendanceEntriesAdditionalDetails?.entryAttendanceLogId,
//                                                 wedExitId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendanceEntriesAdditionalDetails?.exitAttendanceLogId,
//                                                 wedIndex: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Wed').attendance ?? 0.0,
//                                                 thuEntryId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendanceEntriesAdditionalDetails?.entryAttendanceLogId,
//                                                 thuExitId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendanceEntriesAdditionalDetails?.exitAttendanceLogId,
//                                                 thursIndex: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Thu').attendance ?? 0.0,
//                                                 friEntryId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendanceEntriesAdditionalDetails?.entryAttendanceLogId,
//                                                 friExitId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendanceEntriesAdditionalDetails?.exitAttendanceLogId,
//                                                 friIndex: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Fri').attendance ?? 0.0,
//                                                 satEntryId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendanceEntriesAdditionalDetails?.entryAttendanceLogId,
//                                                 satExitId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendanceEntriesAdditionalDetails?.exitAttendanceLogId,
//                                                 satIndex: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sat').attendance ?? 0.0,
//                                                 sunEntryId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendanceEntriesAdditionalDetails?.entryAttendanceLogId,
//                                                 sunExitId: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendanceEntriesAdditionalDetails?.exitAttendanceLogId,
//                                                 sunIndex: e.attendanceEntries!.lastWhere((att) => DateFormats.getDay(att.time!) == 'Sun').attendance ?? 0.0,
//                                                 auditDetails: e.attendanceEntries?.first.auditDetails))
//                                             .toList();
//
//                                         if (newList.isEmpty) {
//                                           for (var i = 0;
//                                               i < attendeeList.length;
//                                               i++) {
//                                             var item1 = attendeeList[i];
//                                             TrackAttendanceTableData data =
//                                                 TrackAttendanceTableData();
//                                             data.name = item1.name;
//                                             data.aadhaar = item1.aadhaar;
//                                             data.individualId =
//                                                 item1.individualId ?? '';
//                                             data.monIndex = item1.monIndex;
//                                             data.monEntryId = item1.monEntryId;
//                                             data.monExitId = item1.monExitId;
//                                             data.tueIndex = item1.tueIndex;
//                                             data.tueEntryId = item1.tueEntryId;
//                                             data.tueExitId = item1.tueExitId;
//                                             data.wedIndex = item1.wedIndex;
//                                             data.wedEntryId = item1.wedEntryId;
//                                             data.wedExitId = item1.wedExitId;
//                                             data.thuIndex = item1.thursIndex;
//                                             data.thuEntryId = item1.thuEntryId;
//                                             data.thuExitId = item1.thuExitId;
//                                             data.friIndex = item1.friIndex;
//                                             data.friEntryId = item1.friEntryId;
//                                             data.friExitId = item1.friExitId;
//                                             data.satIndex = item1.satIndex;
//                                             data.satEntryId = item1.satEntryId;
//                                             data.satExitId = item1.satExitId;
//                                             data.sunIndex = item1.sunIndex;
//                                             data.sunEntryId = item1.sunEntryId;
//                                             data.sunExitId = item1.sunExitId;
//                                             data.auditDetails =
//                                                 item1.auditDetails;
//                                             newList.add(data);
//                                           }
//                                         }
//                                       } else {
//                                         if (newList.isEmpty) {
//                                           for (var i = 0;
//                                               i < attendeeList.length;
//                                               i++) {
//                                             var item1 = attendeeList[i];
//                                             TrackAttendanceTableData data =
//                                                 TrackAttendanceTableData();
//                                             data.name = item1.name;
//                                             data.aadhaar = item1.aadhaar;
//                                             data.individualId =
//                                                 item1.individualId ?? '';
//                                             data.monIndex = item1.monIndex;
//                                             data.tueIndex = item1.tueIndex;
//                                             data.wedIndex = item1.wedIndex;
//                                             data.thuIndex = item1.thursIndex;
//                                             data.friIndex = item1.friIndex;
//                                             data.satIndex = item1.satIndex;
//                                             data.sunIndex = item1.sunIndex;
//                                             data.auditDetails =
//                                                 item1.auditDetails;
//                                             newList.add(data);
//                                           }
//                                         }
//                                       }
//
//                                       tableData = getAttendanceData(newList);
//
//                                       return Column(
//                                           crossAxisAlignment:
//                                               CrossAxisAlignment.start,
//                                           children: [
//                                             Padding(
//                                               padding:
//                                                   const EdgeInsets.all(8.0),
//                                               child: shg_app.DigitTable(
//                                                 headerList: headerList,
//                                                 tableData: tableData,
//                                                 leftColumnWidth: width,
//                                                 rightColumnWidth: width * 8,
//                                                 height: 58 +
//                                                     (52.0 * tableData.length),
//                                               ),
//                                             ),
//                                           ]);
//                                     } else {
//                                       if (musterState.loading) {
//                                         Loaders.circularLoader(context);
//                                       }
//                                       return Container();
//                                     }
//                                   })
//                                 : Column(
//                                     children: [
//                                       const EmptyImage(
//                                         align: Alignment.center,
//                                       ),
//                                       ButtonLink(
//                                         AppLocalizations.of(context).translate(
//                                             i18.attendanceMgmt
//                                                 .addNewWageSeeker),
//                                         () {},
//                                         align: Alignment.center,
//                                       ),
//                                     ],
//                                   ),
//                           ]))
//                     ]),
//                   ),
//                   state.individualMusterRollModel?.musterRoll?.first
//                                   .individualEntries !=
//                               null &&
//                           state.individualMusterRollModel!.musterRoll!.first
//                               .individualEntries!.isNotEmpty
//                       ? BlocBuilder<MusterRollEstimateBloc,
//                               MusterRollEstimateState>(
//                           builder: (context, musterState) {
//                           return Align(
//                             alignment: Alignment.bottomCenter,
//                             child: Padding(
//                               padding: const EdgeInsets.only(
//                                 left: 8.0,
//                                 right: 8.0,
//                               ),
//                               child: SizedBox(
//                                 height: 100,
//                                 child: BlocBuilder<MusterGetWorkflowBloc,
//                                         MusterGetWorkflowState>(
//                                     builder: (context, workflowState) {
//                                   SchedulerBinding.instance
//                                       .addPostFrameCallback((_) {
//                                     workflowState.maybeWhen(
//                                         initial: () {
//                                           workFlowLoaded = false;
//                                         },
//                                         loading: () {
//                                           workFlowLoaded = false;
//                                         },
//                                         error: () => Notifiers.getToastMessage(
//                                             context,
//                                             AppLocalizations.of(context)
//                                                 .translate(i18.attendanceMgmt
//                                                     .unableToCheckWorkflowStatus),
//                                             'ERROR'),
//                                         loaded: (MusterWorkFlowModel?
//                                             musterWorkFlowModel) {
//                                           if (!workFlowLoaded) {
//                                             if (musterWorkFlowModel!
//                                                     .processInstances!
//                                                     .isNotEmpty &&
//                                                 (musterWorkFlowModel
//                                                         .processInstances
//                                                         ?.first
//                                                         .workflowState
//                                                         ?.applicationStatus ==
//                                                     'REJECTED')) {
//                                               inWorkFlow = false;
//                                             } else {
//                                               if (state
//                                                   .individualMusterRollModel!
//                                                   .musterRoll!
//                                                   .isNotEmpty) {
//                                                 Notifiers.getToastMessage(
//                                                     context,
//                                                     AppLocalizations.of(context)
//                                                         .translate(i18
//                                                             .attendanceMgmt
//                                                             .applicationInWorkFlow),
//                                                     'ERROR');
//
//                                                 inWorkFlow = true;
//                                               }
//                                               inWorkFlow = true;
//                                             }
//                                             workFlowLoaded = true;
//                                           }
//                                         },
//                                         orElse: () => Container());
//                                   });
//                                   return Column(
//                                     children: [
//                                       BlocBuilder<AttendanceLogCreateBloc,
//                                               AttendanceLogCreateState>(
//                                           builder: (context, logState) {
//                                         SchedulerBinding.instance
//                                             .addPostFrameCallback((_) {
//                                           logState.maybeWhen(
//                                               error: () {
//                                                 if (!hasLoaded) {
//                                                   Notifiers.getToastMessage(
//                                                       context,
//                                                       AppLocalizations.of(
//                                                               context)
//                                                           .translate(i18
//                                                               .attendanceMgmt
//                                                               .attendanceLoggedFailed),
//                                                       'ERROR');
//                                                   onSubmit(
//                                                       registerId.toString());
//                                                   hasLoaded = true;
//                                                 }
//                                               },
//                                               loaded: () {
//                                                 if (!hasLoaded) {
//                                                   Notifiers.getToastMessage(
//                                                       context,
//                                                       AppLocalizations.of(
//                                                               context)
//                                                           .translate(i18
//                                                               .attendanceMgmt
//                                                               .attendanceLoggedSuccess),
//                                                       'SUCCESS');
//                                                   onSubmit(
//                                                       registerId.toString());
//                                                   hasLoaded = true;
//                                                 }
//                                               },
//                                               orElse: () => Container());
//                                         });
//                                         return OutlinedButton(
//                                             style: OutlinedButton.styleFrom(
//                                                 backgroundColor: Colors.white,
//                                                 side: BorderSide(
//                                                     width: 2,
//                                                     color: DigitTheme
//                                                         .instance
//                                                         .colorScheme
//                                                         .secondary)),
//                                             onPressed: () {
//                                               if (selectedDateRange == null) {
//                                                 Notifiers.getToastMessage(
//                                                     context,
//                                                     AppLocalizations.of(context)
//                                                         .translate(i18
//                                                             .attendanceMgmt
//                                                             .selectDateRangeFirst),
//                                                     'ERROR');
//                                               } else {
//                                                 hasLoaded = false;
//                                                 if (updateAttendeePayload
//                                                         .isNotEmpty &&
//                                                     createAttendeePayload
//                                                         .isNotEmpty) {
//                                                   context
//                                                       .read<
//                                                           AttendanceLogCreateBloc>()
//                                                       .add(UpdateAttendanceLogEvent(
//                                                           attendanceList:
//                                                               updateAttendeePayload));
//                                                   context
//                                                       .read<
//                                                           AttendanceLogCreateBloc>()
//                                                       .add(CreateAttendanceLogEvent(
//                                                           attendanceList:
//                                                               createAttendeePayload));
//                                                 } else if (updateAttendeePayload
//                                                     .isNotEmpty) {
//                                                   context
//                                                       .read<
//                                                           AttendanceLogCreateBloc>()
//                                                       .add(UpdateAttendanceLogEvent(
//                                                           attendanceList:
//                                                               updateAttendeePayload));
//                                                 } else if (createAttendeePayload
//                                                     .isNotEmpty) {
//                                                   context
//                                                       .read<
//                                                           AttendanceLogCreateBloc>()
//                                                       .add(CreateAttendanceLogEvent(
//                                                           attendanceList:
//                                                               createAttendeePayload));
//                                                 }
//                                               }
//                                             },
//                                             child: Center(
//                                                 child: Text(
//                                               AppLocalizations.of(context)
//                                                   .translate(
//                                                       i18.common.saveAsDraft),
//                                               style: inWorkFlow
//                                                   ? Theme.of(context)
//                                                       .textTheme
//                                                       .titleSmall
//                                                       ?.apply(
//                                                           color: const Color
//                                                                   .fromRGBO(
//                                                               149, 148, 148, 1))
//                                                   : Theme.of(context)
//                                                       .textTheme
//                                                       .titleSmall,
//                                             )));
//                                       }),
//                                       const SizedBox(
//                                         height: 10,
//                                       ),
//                                       BlocBuilder<MusterCreateBloc,
//                                               MusterCreateState>(
//                                           builder:
//                                               (context, musterUpdateState) {
//                                         SchedulerBinding.instance
//                                             .addPostFrameCallback((_) {
//                                           musterUpdateState.maybeWhen(
//                                               error: () {
//                                                 if (!updateLoaded) {
//                                                   Notifiers.getToastMessage(
//                                                       context,
//                                                       AppLocalizations.of(
//                                                               context)
//                                                           .translate(i18
//                                                               .attendanceMgmt
//                                                               .musterUpdateFailed),
//                                                       'ERROR');
//                                                   onSubmit(
//                                                       registerId.toString());
//                                                   updateLoaded = true;
//                                                 }
//                                               },
//                                               loaded: () {
//                                                 if (!updateLoaded) {
//                                                   Notifiers.getToastMessage(
//                                                       context,
//                                                       AppLocalizations.of(
//                                                               context)
//                                                           .translate(i18
//                                                               .attendanceMgmt
//                                                               .musterSentForApproval),
//                                                       'SUCCESS');
//                                                   onSubmit(
//                                                       registerId.toString());
//                                                   updateLoaded = true;
//                                                 }
//                                               },
//                                               orElse: () => Container());
//                                         });
//                                         return Container();
//                                       }),
//                                       DigitElevatedButton(
//                                         onPressed: !inWorkFlow
//                                             ? () {
//                                                 if (selectedDateRange == null) {
//                                                   Notifiers.getToastMessage(
//                                                       context,
//                                                       AppLocalizations.of(
//                                                               context)
//                                                           .translate(i18
//                                                               .attendanceMgmt
//                                                               .selectDateRangeFirst),
//                                                       'ERROR');
//                                                 } else {
//                                                   updateLoaded = false;
//                                                   context
//                                                       .read<MusterCreateBloc>()
//                                                       .add(UpdateMusterEvent(
//                                                         tenantId:
//                                                             widget.tenantId,
//                                                         id: musterId.toString(),
//                                                         contractId: state
//                                                                 .individualMusterRollModel!
//                                                                 .musterRoll!
//                                                                 .first
//                                                                 .musterAdditionalDetails!
//                                                                 .contractId ??
//                                                             'NA',
//                                                         registerNo: state
//                                                                 .individualMusterRollModel!
//                                                                 .musterRoll!
//                                                                 .first
//                                                                 .musterAdditionalDetails!
//                                                                 .attendanceRegisterNo ??
//                                                             'NA',
//                                                         registerName: state
//                                                                 .individualMusterRollModel!
//                                                                 .musterRoll!
//                                                                 .first
//                                                                 .musterAdditionalDetails!
//                                                                 .attendanceRegisterName ??
//                                                             'NA',
//                                                         orgName: state
//                                                                 .individualMusterRollModel!
//                                                                 .musterRoll!
//                                                                 .first
//                                                                 .musterAdditionalDetails!
//                                                                 .contractId ??
//                                                             'NA',
//                                                       ));
//                                                   Future.delayed(const Duration(
//                                                       seconds: 2));
//                                                   onSubmit(state
//                                                       .individualMusterRollModel!
//                                                       .musterRoll!
//                                                       .first
//                                                       .registerId
//                                                       .toString());
//                                                 }
//                                               }
//                                             : null,
//                                         child: Text(
//                                             AppLocalizations.of(context)
//                                                 .translate(i18.attendanceMgmt
//                                                     .resubmitMusterRoll),
//                                             style: Theme.of(context)
//                                                 .textTheme
//                                                 .titleSmall!
//                                                 .apply(color: Colors.white)),
//                                       )
//                                     ],
//                                   );
//                                 }),
//                               ),
//                             ),
//                           );
//                         })
//                       : Container()
//                 ]);
//               } else {
//                 return Loaders.circularLoader(context);
//               }
//             });
//           } else {
//             return Loaders.circularLoader(context);
//           }
//         }));
//   }
//
//   void onTextSearch() {
//     if (searchController.text.isNotEmpty) {
//       setState(() {
//         newList.retainWhere((e) =>
//             e.individualId!.toLowerCase().contains(searchController.text));
//       });
//     } else {
//       onSubmit(registerId.toString());
//     }
//   }
//
//   void onSubmit(String registerId) {
//     if (selectedDateRange != null) {
//       newList.clear();
//       updateAttendeePayload.clear();
//       createAttendeePayload.clear();
//       context.read<MusterRollEstimateBloc>().add(
//             ViewEstimateMusterRollEvent(
//               tenantId: widget.tenantId,
//               registerId: registerId.toString(),
//               startDate: selectedDateRange!.startDate,
//               endDate: selectedDateRange!.endDate,
//             ),
//           );
//       context.read<MusterGetWorkflowBloc>().add(
//             GetMusterWorkflowEvent(
//                 tenantId: widget.tenantId,
//                 musterRollNumber: widget.musterRollNo),
//           );
//       workFlowLoaded = false;
//     } else {
//       Notifiers.getToastMessage(
//           context,
//           AppLocalizations.of(context)
//               .translate(i18.attendanceMgmt.selectDateRangeFirst),
//           'ERROR');
//     }
//   }
//
//   List<TableHeader> get headerList => [
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.nameLabel),
//           apiKey: 'name',
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.aadhaarNumber),
//           apiKey: 'aadhaarNumber',
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.mon),
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.tue),
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.wed),
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.thu),
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.fri),
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.sat),
//         ),
//         TableHeader(
//           AppLocalizations.of(scaffoldMessengerKey.currentContext!)
//               .translate(i18.common.sun),
//         ),
//       ];
//
//   TableDataRow getAttendanceRow(TrackAttendanceTableData tableDataModel) {
//     return TableDataRow([
//       TableData(label: tableDataModel.name, apiKey: tableDataModel.name),
//       TableData(label: tableDataModel.aadhaar, apiKey: tableDataModel.aadhaar),
//       TableData(
//         apiKey: tableDataModel.monIndex.toString(),
//         widget: CircularButton(
//           icon: Icons.circle_rounded,
//           size: 15,
//           color: const Color.fromRGBO(0, 100, 0, 1),
//           index: tableDataModel.monIndex ?? 0.0,
//           isNotGreyed: false,
//           onTap: daysInRange == null || !daysInRange!.tuesday
//               ? null
//               : entryExitList!.length > 2
//                   ? () => onTapButton(
//                       tableDataModel.individualId ?? '',
//                       'mon',
//                       tableDataModel.monEntryId,
//                       tableDataModel.monExitId,
//                       tableDataModel.auditDetails)
//                   : () => onTapOnlyAbsentPresent(
//                       tableDataModel.individualId ?? '',
//                       'mon',
//                       tableDataModel.monEntryId,
//                       tableDataModel.monExitId,
//                       tableDataModel.auditDetails),
//         ),
//       ),
//       TableData(
//           widget: CircularButton(
//         icon: Icons.circle_rounded,
//         size: 15,
//         color: const Color.fromRGBO(0, 100, 0, 1),
//         index: tableDataModel.tueIndex ?? 0,
//         isNotGreyed: false,
//         onTap: daysInRange == null || !daysInRange!.tuesday
//             ? null
//             : entryExitList!.length > 2
//                 ? () => onTapButton(
//                     tableDataModel.individualId ?? '',
//                     'tue',
//                     tableDataModel.tueEntryId,
//                     tableDataModel.tueExitId,
//                     tableDataModel.auditDetails)
//                 : () => onTapOnlyAbsentPresent(
//                     tableDataModel.individualId ?? '',
//                     'tue',
//                     tableDataModel.tueEntryId,
//                     tableDataModel.tueExitId,
//                     tableDataModel.auditDetails),
//       )),
//       TableData(
//           widget: CircularButton(
//         icon: Icons.circle_rounded,
//         size: 15,
//         color: const Color.fromRGBO(0, 100, 0, 1),
//         index: tableDataModel.wedIndex ?? 0,
//         isNotGreyed: false,
//         onTap: daysInRange == null || !daysInRange!.wednesday
//             ? null
//             : entryExitList!.length > 2
//                 ? () => onTapButton(
//                     tableDataModel.individualId ?? '',
//                     'wed',
//                     tableDataModel.wedEntryId,
//                     tableDataModel.wedExitId,
//                     tableDataModel.auditDetails)
//                 : () => onTapOnlyAbsentPresent(
//                     tableDataModel.individualId ?? '',
//                     'wed',
//                     tableDataModel.wedEntryId,
//                     tableDataModel.wedExitId,
//                     tableDataModel.auditDetails),
//       )),
//       TableData(
//           widget: CircularButton(
//         icon: Icons.circle_rounded,
//         size: 15,
//         color: const Color.fromRGBO(0, 100, 0, 1),
//         index: tableDataModel.thuIndex ?? 0,
//         isNotGreyed: false,
//         onTap: daysInRange == null || !daysInRange!.thursday
//             ? null
//             : entryExitList!.length > 2
//                 ? () => onTapButton(
//                     tableDataModel.individualId ?? '',
//                     'thu',
//                     tableDataModel.thuEntryId,
//                     tableDataModel.thuExitId,
//                     tableDataModel.auditDetails)
//                 : () => onTapOnlyAbsentPresent(
//                     tableDataModel.individualId ?? '',
//                     'thu',
//                     tableDataModel.thuEntryId,
//                     tableDataModel.thuExitId,
//                     tableDataModel.auditDetails),
//       )),
//       TableData(
//           widget: CircularButton(
//         icon: Icons.circle_rounded,
//         size: 15,
//         color: const Color.fromRGBO(0, 100, 0, 1),
//         index: tableDataModel.friIndex ?? 0,
//         isNotGreyed: false,
//         onTap: daysInRange == null || !daysInRange!.friday
//             ? null
//             : entryExitList!.length > 2
//                 ? () => onTapButton(
//                     tableDataModel.individualId ?? '',
//                     'fri',
//                     tableDataModel.friEntryId,
//                     tableDataModel.friExitId,
//                     tableDataModel.auditDetails)
//                 : () => onTapOnlyAbsentPresent(
//                     tableDataModel.individualId ?? '',
//                     'fri',
//                     tableDataModel.friEntryId,
//                     tableDataModel.friExitId,
//                     tableDataModel.auditDetails),
//       )),
//       TableData(
//           widget: CircularButton(
//         icon: Icons.circle_rounded,
//         size: 15,
//         color: const Color.fromRGBO(0, 100, 0, 1),
//         index: tableDataModel.satIndex ?? 0,
//         isNotGreyed: false,
//         onTap: daysInRange == null || !daysInRange!.saturday
//             ? null
//             : entryExitList!.length > 2
//                 ? () => onTapButton(
//                     tableDataModel.individualId ?? '',
//                     'sat',
//                     tableDataModel.satEntryId,
//                     tableDataModel.satExitId,
//                     tableDataModel.auditDetails)
//                 : () => onTapOnlyAbsentPresent(
//                     tableDataModel.individualId ?? '',
//                     'sat',
//                     tableDataModel.satEntryId,
//                     tableDataModel.satExitId,
//                     tableDataModel.auditDetails),
//       )),
//       TableData(
//           widget: CircularButton(
//         icon: Icons.circle_rounded,
//         size: 15,
//         color: const Color.fromRGBO(0, 100, 0, 1),
//         index: tableDataModel.sunIndex ?? 0,
//         isNotGreyed: false,
//         onTap: daysInRange == null || !daysInRange!.sunday
//             ? null
//             : entryExitList!.length > 2
//                 ? () => onTapButton(
//                     tableDataModel.individualId ?? '',
//                     'sun',
//                     tableDataModel.sunEntryId,
//                     tableDataModel.sunExitId,
//                     tableDataModel.auditDetails)
//                 : () => onTapOnlyAbsentPresent(
//                     tableDataModel.individualId ?? '',
//                     'sun',
//                     tableDataModel.sunEntryId,
//                     tableDataModel.sunExitId,
//                     tableDataModel.auditDetails),
//       ))
//     ]);
//   }
//
//   List<TableDataRow> getAttendanceData(List<TrackAttendanceTableData> list) {
//     return list.map((e) => getAttendanceRow(e)).toList();
//   }
//
//   void onTapButton(individualId, day, entryID, exitId, auditDetails) {
//     int morning =
//         entryExitList?.firstWhere((e) => e.code == 'MORNING').hours ?? 0;
//     int afternoon =
//         entryExitList?.firstWhere((e) => e.code == 'AFTERNOON').hours ?? 0;
//     int evening =
//         entryExitList?.firstWhere((e) => e.code == 'EVENING').hours ?? 0;
//     int index = newList.indexWhere((item) => item.individualId == individualId);
//
//     if (index != -1) {
//       setState(() {
//         if (newList[index].getProperty(day) == 0.0 ||
//             newList[index].getProperty(day) == -1) {
//           newList[index].setProperty(day, 0.5);
//           if (entryID != null && exitId != null) {
//             updateAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             updateAttendeePayload.addAll(updateAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     afternoon),
//                 entryID,
//                 exitId,
//                 widget.tenantId,
//                 auditDetails,
//                 true,
//                 true));
//           } else {
//             createAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             createAttendeePayload.addAll(createAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     afternoon),
//                 widget.tenantId));
//           }
//         } else if (newList[index].getProperty(day) == 0.5) {
//           newList[index].setProperty(day, 1.0);
//           if (entryID != null && exitId != null) {
//             updateAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             updateAttendeePayload.addAll(updateAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     evening),
//                 entryID,
//                 exitId,
//                 widget.tenantId,
//                 auditDetails,
//                 true,
//                 true));
//           } else {
//             createAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             createAttendeePayload.addAll(createAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     evening),
//                 widget.tenantId));
//           }
//         } else {
//           newList[index].setProperty(day, 0.0);
//           if (entryID != null && exitId != null) {
//             updateAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             updateAttendeePayload.addAll(updateAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 entryID,
//                 exitId,
//                 widget.tenantId,
//                 auditDetails,
//                 false,
//                 false));
//           } else {
//             createAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//           }
//         }
//       });
//     }
//   }
//
//   void onTapOnlyAbsentPresent(
//       individualId, day, entryID, exitId, auditDetails) {
//     int morning =
//         entryExitList?.firstWhere((e) => e.code == 'MORNING').hours ?? 0;
//     int afternoon =
//         entryExitList?.firstWhere((e) => e.code == 'AFTERNOON').hours ?? 0;
//     int evening =
//         entryExitList?.firstWhere((e) => e.code == 'EVENING').hours ?? 0;
//     int index = newList.indexWhere((item) => item.individualId == individualId);
//
//     if (index != -1) {
//       setState(() {
//         if (newList[index].getProperty(day) == 0.0) {
//           newList[index].setProperty(day, 1.0);
//           if (entryID != null && exitId != null) {
//             updateAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             updateAttendeePayload.addAll(updateAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     evening),
//                 entryID,
//                 exitId,
//                 widget.tenantId,
//                 auditDetails,
//                 true,
//                 true));
//           } else {
//             createAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             createAttendeePayload.addAll(createAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     evening),
//                 widget.tenantId));
//           }
//         } else {
//           newList[index].setProperty(day, 0.0);
//           if (entryID != null && exitId != null) {
//             updateAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//             updateAttendeePayload.addAll(updateAttendanceLogPayload(
//                 newList[index],
//                 registerId ?? '',
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 DateFormats.getTimestampFromWeekDay(
//                     DateFormats.getDateFromTimestamp(
//                         selectedDateRange!.startDate),
//                     day,
//                     morning),
//                 entryID,
//                 exitId,
//                 widget.tenantId,
//                 auditDetails,
//                 false,
//                 false));
//           } else {
//             createAttendeePayload.removeWhere((e) =>
//                 e['individualId'] == individualId &&
//                 DateFormats.getDay(e['time']).toLowerCase() == day);
//           }
//         }
//       });
//     }
//   }
// }
