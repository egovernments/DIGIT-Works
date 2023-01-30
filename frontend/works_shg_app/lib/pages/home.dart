import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/ButtonLink.dart';

import '../blocs/attendance/search_projects.dart';
import '../blocs/localization/app_localization.dart';
import '../widgets/loaders.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, state) {
      return state.isLocalizationLoadCompleted
          ? Scaffold(
              body: ScrollableContent(
              children: [
                DigitCard(
                  onPressed: null,
                  padding: const EdgeInsets.fromLTRB(16, 30, 16, 16),
                  child: Align(
                    alignment: Alignment.topCenter,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        LabeledField(
                          label: AppLocalizations.of(context)
                              .translate(i18.home.worksMgmt),
                          child: ButtonLink(
                              AppLocalizations.of(context)
                                  .translate(i18.home.workOrder),
                              () =>
                                  context.router.push(const WorkOrderRoute())),
                        )
                      ],
                    ),
                  ),
                ),
                DigitCard(
                  onPressed: null,
                  padding: const EdgeInsets.fromLTRB(16, 30, 16, 16),
                  child: Align(
                    alignment: Alignment.topCenter,
                    child: LabeledField(
                        label: AppLocalizations.of(context)
                            .translate(i18.home.attendanceMgmt),
                        child: Column(
                          children: [
                            ButtonLink(
                                AppLocalizations.of(context)
                                    .translate(i18.home.manageWageSeekers), () {
                              context.read<AttendanceProjectsSearchBloc>().add(
                                    const SearchAttendanceProjectsEvent(),
                                  );
                              context.router
                                  .push(const ManageAttendanceRegisterRoute());
                            }),
                            ButtonLink(
                                AppLocalizations.of(context)
                                    .translate(i18.home.registerIndividual),
                                () => null),
                            ButtonLink(
                                AppLocalizations.of(context)
                                    .translate(i18.home.musterRoll),
                                () => context.router
                                    .push(const ViewMusterRollsRoute())),
                            ButtonLink(
                                AppLocalizations.of(context)
                                    .translate(i18.home.trackAttendance), () {
                              context.read<AttendanceProjectsSearchBloc>().add(
                                    const SearchAttendanceProjectsEvent(),
                                  );
                              context.router.push(const AttendanceInboxRoute());
                            })
                          ],
                        )),
                  ),
                )
              ],
            ))
          : Loaders.circularLoader(context);
    });
  }
}
