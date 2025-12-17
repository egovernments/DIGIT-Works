// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';
import 'package:digit_ui_components/widgets/powered_by_digit.dart';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/work_details_card.dart';

import '../../blocs/attendance/search_projects/search_projects.dart';
import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../models/attendance/attendance_registry_model.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/atoms/empty_image.dart';
import '../../widgets/loaders.dart' as shg_loader;

@RoutePage()
class TrackAttendanceInboxPage extends StatefulWidget {
  const TrackAttendanceInboxPage({super.key});

  @override
  State<StatefulWidget> createState() {
    return _TrackAttendanceInboxPage();
  }
}

class _TrackAttendanceInboxPage extends State<TrackAttendanceInboxPage> {
  List<Map<String, dynamic>> projectList = [];
  List<AttendanceRegister> attendanceRegisters = [];

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<AttendanceProjectsSearchBloc>().add(
          const SearchAttendanceProjectsEvent(),
        );
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localState) {
      return Scaffold(
          // appBar: customAppBar(),
          backgroundColor: Theme.of(context).colorTheme.generic.background,
          // drawer: const MySideBar(),
          bottomNavigationBar: BlocBuilder<AttendanceProjectsSearchBloc,
              AttendanceProjectsSearchState>(builder: (context, state) {
            return state.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (AttendanceRegistersModel? attendanceModel) {
                  return projectList.isEmpty || projectList.length == 1
                      ? const SizedBox(
                          height: 50,
                          child: Align(
                            alignment: Alignment.bottomCenter,
                            child: PoweredByDigit(
                              version: Constants.appVersion,
                            ),
                          ),
                        )
                      : const SizedBox.shrink();
                });
          }),
          body: SingleChildScrollView(
              child: BlocListener<AttendanceProjectsSearchBloc,
                  AttendanceProjectsSearchState>(
            listener: (context, state) {
              state.maybeWhen(
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (AttendanceRegistersModel? attendanceRegistersModel) {
                    attendanceRegisters = List<AttendanceRegister>.from(
                        attendanceRegistersModel!.attendanceRegister!);

                    attendanceRegisters.sort((a, b) =>
                        b.registerAuditDetails!.lastModifiedTime!.compareTo(
                            a.registerAuditDetails!.lastModifiedTime!.toInt()));
                    projectList = attendanceRegisters
                        .map((e) => {
                              i18.workOrder.workOrderNo: e
                                      .attendanceRegisterAdditionalDetails
                                      ?.contractId ??
                                  i18.common.noValue,
                              i18.attendanceMgmt.registerId: e.registerNumber,
                              i18.attendanceMgmt.projectId: e
                                      .attendanceRegisterAdditionalDetails
                                      ?.projectId ??
                                  t.translate(i18.common.noValue),
                              i18.attendanceMgmt.projectName: e
                                      .attendanceRegisterAdditionalDetails
                                      ?.projectName ??
                                  i18.common.noValue,
                              i18.attendanceMgmt.projectDesc: e
                                      .attendanceRegisterAdditionalDetails
                                      ?.projectDesc ??
                                  t.translate(i18.common.noValue),
                              i18.attendanceMgmt.individualsCount:
                                  e.attendeesEntries != null
                                      ? e.attendeesEntries
                                          ?.where((att) =>
                                              att.denrollmentDate == null ||
                                              !(att.denrollmentDate! <=
                                                  DateTime.now()
                                                      .millisecondsSinceEpoch))
                                          .toList()
                                          .length
                                      : 0,
                              i18.common.startDate: DateFormats.timeStampToDate(
                                  e.startDate,
                                  format: "dd/MM/yyyy"),
                              i18.common.endDate: DateFormats.timeStampToDate(
                                  e.endDate,
                                  format: "dd/MM/yyyy"),
                            })
                        .toList();
                  },
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  orElse: () => Container());
            },
            child: BlocBuilder<AttendanceProjectsSearchBloc,
                AttendanceProjectsSearchState>(
              builder: (context, state) {
                return state.maybeWhen(
                    orElse: () => Container(),
                    loading: () => shg_loader.Loaders.circularLoader(context),
                    loaded: (AttendanceRegistersModel? attendanceModel) {
                      return Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Padding(
                              padding: EdgeInsets.symmetric(
                                horizontal:
                                    Theme.of(context).spacerTheme.spacer4,
                                vertical: Theme.of(context).spacerTheme.spacer4,
                              ),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.start,
                                crossAxisAlignment: CrossAxisAlignment.center,
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
                                      Navigator.pop(context);
                                    },
                                  ),
                                ],
                              ),
                            ),
                           

                            Padding(
                              padding: EdgeInsets.only(
                                 left: Theme.of(context).spacerTheme.spacer4,bottom: Theme.of(context).spacerTheme.spacer4),
                              child: DigitTextBlock(
                                heading:
                                    '${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceRegisters)}(${projectList.length})',
                                
                              ),
                            ),
                            projectList.isEmpty
                                ? EmptyImage(
                                    align: Alignment.center,
                                    label:
                                        AppLocalizations.of(context).translate(
                                      i18.attendanceMgmt.noRegistersFound,
                                    ))
                                : WorkDetailsCard(
                                    projectList,
                                    isTrackAttendance: true,
                                    elevatedButtonLabel:
                                        AppLocalizations.of(context).translate(
                                            i18.attendanceMgmt
                                                .updateAttendance),
                                    attendanceRegistersModel:
                                        attendanceRegisters,
                                  ),
                            SizedBox(
                              height: Theme.of(context).spacerTheme.spacer4,
                            ),
                            projectList.isNotEmpty && projectList.length > 1
                                ? const Align(
                                    alignment: Alignment.bottomCenter,
                                    child: PoweredByDigit(
                                      version: Constants.appVersion,
                                    ),
                                  )
                                : const SizedBox.shrink()
                          ]);
                    });
              },
            ),
          )));
    });
  }
}
