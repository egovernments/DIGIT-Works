import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/ButtonLink.dart';
import 'package:works_shg_app/widgets/atoms/app_bar_logo.dart';

import '../blocs/localization/app_localization.dart';
import '../blocs/muster_rolls/search_muster_roll.dart';
import '../blocs/organisation/org_search_bloc.dart';
import '../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../models/organisation/organisation_model.dart';
import '../utils/constants.dart';
import '../utils/global_variables.dart';
import '../utils/models/file_picker_data.dart';
import '../widgets/SideBar.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart' as shg_loader;

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _HomePage();
  }
}

class _HomePage extends State<HomePage> {
  @override
  void initState() {
    context.read<ORGSearchBloc>().add(
          SearchORGEvent(GlobalVariables.userRequestModel!['mobileNumber']),
        );
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: BlocBuilder<ORGSearchBloc, ORGSearchState>(
            builder: (context, state) {
              return state.maybeWhen(
                  orElse: () => Container(),
                  loaded: (OrganisationListModel? organisationListModel) {
                    return const AppBarLogo();
                  });
            },
          ),
        ),
        drawer: DrawerWrapper(const Drawer(
            child: SideBar(
          module: 'rainmaker-common,rainmaker-attendencemgmt',
        ))),
        body: BlocBuilder<ORGSearchBloc, ORGSearchState>(
            builder: (context, state) {
          return state.maybeWhen(
              orElse: () => Container(),
              loading: () => shg_loader.Loaders.circularLoader(context),
              loaded: (OrganisationListModel? organisationListModel) {
                return ScrollableContent(
                  footer: const Padding(
                    padding: EdgeInsets.all(16.0),
                    child: PoweredByDigit(),
                  ),
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
                                    .translate(i18.home.trackAttendance), () {
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
                                () {
                              context.read<WageSeekerBloc>().add(
                                    const WageSeekerClearEvent(),
                                  );
                              FilePickerData.imageFile = null;
                              FilePickerData.bytes = null;
                              context.router
                                  .push(const RegisterIndividualRoute());
                            }),
                          ],
                        ),
                      ),
                    )
                  ],
                );
              });
        }));
  }
}
