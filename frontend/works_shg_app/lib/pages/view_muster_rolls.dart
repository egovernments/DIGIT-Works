// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';
import 'package:digit_ui_components/widgets/powered_by_digit.dart';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';
import 'package:works_shg_app/widgets/work_details_card.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../blocs/muster_rolls/muster_inbox_status_bloc.dart';
import '../models/muster_rolls/muster_roll_model.dart';
import '../utils/constants.dart';
import '../utils/date_formats.dart';
import '../widgets/loaders.dart' as shg_loader;

@RoutePage()
class ViewMusterRollsPage extends StatefulWidget {
  const ViewMusterRollsPage({super.key});

  @override
  State<StatefulWidget> createState() {
    return _ViewMusterRollsPage();
  }
}

class _ViewMusterRollsPage extends State<ViewMusterRollsPage> {
  List<Map<String, dynamic>> musterList = [];
  List<MusterRoll> musters = [];

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<MusterRollSearchBloc>().add(
          const SearchMusterRollEvent(),
        );
    context.read<MusterInboxStatusBloc>().add(
          const MusterInboxStatusEvent(),
        );
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localState) {
      return Scaffold(
          backgroundColor: Theme.of(context).colorTheme.generic.background,
          // appBar: customAppBar(),
          // drawer: const MySideBar(),
          bottomNavigationBar:
              BlocBuilder<MusterRollSearchBloc, MusterRollSearchState>(
                  builder: (context, state) {
            return state.maybeWhen(
                orElse: () => const SizedBox.shrink(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (MusterRollsModel? musterRollsModel) {
                  return musterList.isEmpty || musterList.length == 1
                      ? Padding(
                        padding:  EdgeInsets.all(Theme.of(context).spacerTheme.spacer4),
                        child: const SizedBox(
                            height: 45,
                            child: Align(
                              alignment: Alignment.bottomCenter,
                              child: PoweredByDigit(
                                version: Constants.appVersion,
                              ),
                            ),
                          ),
                      )
                      : const SizedBox.shrink();
                });
          }),
          body: SingleChildScrollView(child:
              BlocBuilder<MusterInboxStatusBloc, MusterInboxStatusState>(
                  builder: (context, searchState) {
            return searchState.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (String? sentBackToCBOCode) =>
                    BlocListener<MusterRollSearchBloc, MusterRollSearchState>(
                      listener: (context, state) {
                        state.maybeWhen(
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
                            loaded: (MusterRollsModel? musterRoll) {
                              if (musterRoll?.musterRoll != null) {
                                musters = List<MusterRoll>.from(
                                    musterRoll!.musterRoll!);
                                musters.sort((a, b) => b
                                    .musterAuditDetails!.lastModifiedTime!
                                    .compareTo(a
                                        .musterAuditDetails!.lastModifiedTime!
                                        .toInt()));
                                musterList = musters
                                    .map((e) => {
                                          i18.attendanceMgmt.musterRollId:
                                              e.musterRollNumber,
                                          i18.workOrder.workOrderNo: e
                                                  .musterAdditionalDetails
                                                  ?.contractId ??
                                              i18.common.noValue,
                                          i18.attendanceMgmt.projectId: e
                                                  .musterAdditionalDetails
                                                  ?.projectId ??
                                              t.translate(i18.common.noValue),
                                          i18.attendanceMgmt.projectName: e
                                                  .musterAdditionalDetails
                                                  ?.projectName ??
                                              i18.common.noValue,
                                          i18.attendanceMgmt.projectDesc: e
                                                  .musterAdditionalDetails
                                                  ?.projectDesc ??
                                              t.translate(i18.common.noValue),
                                          i18.attendanceMgmt.musterRollPeriod:
                                              '${DateFormats.timeStampToDate(e.startDate, format: "dd/MM/yyyy")} - ${DateFormats.timeStampToDate(e.endDate, format: "dd/MM/yyyy")}',
                                          i18.common.status:
                                              'CBO_MUSTER_${e.musterRollStatus}',
                                          Constants.activeInboxStatus:
                                              e.musterRollStatus ==
                                                      sentBackToCBOCode
                                                  ? 'false'
                                                  : 'true'
                                        })
                                    .toList();
                              }
                            },
                            orElse: () => const SizedBox.shrink());
                      },
                      child: BlocBuilder<MusterRollSearchBloc,
                          MusterRollSearchState>(builder: (context, state) {
                        return state.maybeWhen(
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
                            loaded: (MusterRollsModel? musterRollsModel) {
                              return Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    // Back(
                                    //   backLabel: AppLocalizations.of(context)
                                    //       .translate(i18.common.back),
                                    // ),

                                    Padding(
                                      padding: EdgeInsets.symmetric(
                                        horizontal: Theme.of(context)
                                            .spacerTheme
                                            .spacer4,
                                        vertical: Theme.of(context)
                                            .spacerTheme
                                            .spacer4,
                                      ),
                                      child: Row(
                                        children: [
                                          BackNavigationButton(
                                            backNavigationButtonThemeData:
                                                const BackNavigationButtonThemeData()
                                                    .copyWith(
                                                        context: context,
                                                        backButtonIcon: Icon(
                                                          Icons
                                                              .arrow_circle_left_outlined,
                                                          size: MediaQuery.of(
                                                                          context)
                                                                      .size
                                                                      .width <
                                                                  500
                                                              ? Theme.of(
                                                                      context)
                                                                  .spacerTheme
                                                                  .spacer5
                                                              : Theme.of(
                                                                      context)
                                                                  .spacerTheme
                                                                  .spacer6,
                                                          color:
                                                              Theme.of(context)
                                                                  .colorTheme
                                                                  .primary
                                                                  .primary2,
                                                        )),
                                            handleBack: () {
                                              Navigator.pop(context);
                                            },
                                            backButtonText:
                                                AppLocalizations.of(context)
                                                    .translate(i18.common.back),
                                          ),
                                        ],
                                      ),
                                    ),
                                    Padding(
                                      padding: EdgeInsets.only(
                                        left: Theme.of(context)
                                            .spacerTheme
                                            .spacer4,
                                        bottom: Theme.of(context)
                                            .spacerTheme
                                            .spacer4,
                                        top: 0.0,
                                      ),
                                      child: DigitTextBlock(
                                        heading:
                                            '${t.translate(i18.attendanceMgmt.musterRolls)}(${musterList.length})',
                                      ),
                                    ),
                                    musterList.isEmpty
                                        ? EmptyImage(
                                            align: Alignment.center,
                                            label: t.translate(
                                              i18.attendanceMgmt
                                                  .noMusterRollsFound,
                                            ))
                                        : WorkDetailsCard(
                                            musterList,
                                            isSHGInbox: true,
                                            musterBackToCBOCode:
                                                sentBackToCBOCode,
                                            musterRollsModel: musters,
                                            elevatedButtonLabel: t.translate(
                                                i18.common.viewDetails),
                                          ),
                                    SizedBox(
                                      height:
                                          Theme.of(context).spacerTheme.spacer4,
                                    ),
                                    musterList.isNotEmpty &&
                                            musterList.length > 1
                                        ? const Align(
                                            alignment: Alignment.bottomCenter,
                                            child: PoweredByDigit(
                                              version: Constants.appVersion,
                                            ),
                                          )
                                        : const SizedBox.shrink()
                                  ]);
                            },
                            orElse: () => Container());
                      }),
                    ));
          })));
    });
  }
}
