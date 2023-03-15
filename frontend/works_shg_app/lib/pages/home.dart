import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/widgets/ButtonLink.dart';

import '../blocs/attendance/search_projects/search_projects.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/muster_rolls/search_muster_roll.dart';
import '../widgets/SideBar.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, state) {
      return state.isLocalizationLoadCompleted
          ? Scaffold(
              appBar: AppBar(),
              drawer: DrawerWrapper(const Drawer(
                  child: SideBar(
                module: 'rainmaker-common,rainmaker-attendencemgmt',
              ))),
              body: ScrollableContent(
                footer: const PoweredByDigit(),
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
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              LabeledField(
                                label: AppLocalizations.of(context)
                                    .translate(i18.home.mukta),
                                child: Column(
                                  children: [
                                    ButtonLink(
                                        AppLocalizations.of(context)
                                            .translate(i18.home.myWorks),
                                        () => context.router
                                            .push(const WorkOrderRoute())),
                                  ],
                                ),
                              ),
                              SvgPicture.asset(Constants.muktaIcon)
                            ],
                          ),
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
                                  .translate(i18.home.trackAttendance), () {
                            context.read<AttendanceProjectsSearchBloc>().add(
                                  const SearchAttendanceProjectsEvent(),
                                );
                            context.router
                                .push(const TrackAttendanceInboxRoute());
                          }),
                          ButtonLink(
                              AppLocalizations.of(context)
                                  .translate(i18.home.musterRolls), () {
                            context.read<MusterRollSearchBloc>().add(
                                  const SearchMusterRollEvent(),
                                );
                            context.router.push(const ViewMusterRollsRoute());
                          }),
                          ButtonLink(
                              AppLocalizations.of(context)
                                  .translate(i18.home.registerWageSeeker),
                              () => context.router
                                  .push(const RegisterIndividualRoute())),
                        ],
                      ),
                    ),
                  )
                ],
              ))
          : Loaders.circularLoader(context);
    });
  }
}
