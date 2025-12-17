import 'package:collection/collection.dart';
// import 'package:digit_components/theme/colors.dart';
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_component;
import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/svg.dart';
import 'package:works_shg_app/blocs/employee/mb/project_type.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/models/app_config/app_config_model.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/employee/mb/mb_logic.dart';
import 'package:works_shg_app/utils/employee/support_services.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../../blocs/employee/mb/measurement_book.dart';
import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_location_bloc.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

@RoutePage()
class MeasurementBookInboxPage extends StatefulWidget {
  const MeasurementBookInboxPage({super.key});

  @override
  State<MeasurementBookInboxPage> createState() =>
      _MeasurementBookInboxPageState();
}

class _MeasurementBookInboxPageState extends State<MeasurementBookInboxPage> {
  final ScrollController _scrollController = ScrollController();
  List<String> items = []; // List to hold items
  int pageCount = 0; // Initial page count
  bool isLoading = false; // Loading indicator

  @override
  void initState() {
    context
        .read<ProjectTypeBloc>()
        .add(ProjectTypeEvent(tenantId: GlobalVariables.tenantId!));

    context.read<MeasurementInboxBloc>().add(
          MeasurementBookInboxBlocEvent(
            businessService: "MB",
            limit: 10,
            moduleName: 'measurement-module',
            offset: pageCount,
            tenantId: GlobalVariables.tenantId!,
          ),
        );

    context.read<WageSeekerLocationBloc>().add(
          LocationEventWageSeeker(tenantId: GlobalVariables.tenantId!),
        );
    _scrollController.addListener(_scrollListener);

    super.initState();
  }

  @override
  void dispose() {
    _scrollController.removeListener(_scrollListener);
    _scrollController.dispose();
    super.dispose();
  }

  void _scrollListener() {
    if (_scrollController.position.pixels ==
        _scrollController.position.maxScrollExtent) {
      _addRandomData();
    }
  }

  void _addRandomData() {
    int s = pageCount + 10;
    final state = context.read<MeasurementInboxBloc>().state;
    state.maybeMap(
      orElse: () => {},
      loaded: (value) {
        if (value.search && value.data.length == 1) {
          context.read<MeasurementInboxBloc>().add(
                MeasurementBookInboxSearchRepeatBlocEvent(
                  businessService: "MB",
                  limit: 10,
                  moduleName: 'measurement-module',
                  offset: value.data['inbox']!['offset'] + 10,
                  tenantId: GlobalVariables.tenantId!,
                ),
              );
        } else {
          context.read<MeasurementInboxBloc>().add(
                MeasurementBookInboxBlocEvent(
                  businessService: "MB",
                  limit: 10,
                  moduleName: 'measurement-module',
                  offset: s,
                  tenantId: GlobalVariables.tenantId!,
                ),
              );
        }
      },
    );

    setState(() {
      pageCount = s;
    });
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocBuilder<LocalizationBloc, LocalizationState>(
      builder: (context, localizationState) {
        return Scaffold(
          floatingActionButtonLocation:
              FloatingActionButtonLocation.centerDocked,
          backgroundColor: Theme.of(context).colorTheme.generic.background,
          floatingActionButton:
              BlocBuilder<MeasurementInboxBloc, MeasurementInboxState>(
            builder: (context, state) {
              return state.maybeMap(
                orElse: () {
                  return const SizedBox.shrink();
                },
                loaded: (value) {
                  if (value.mbInboxResponse.items!.length > 19) {
                    return TextButton.icon(
                      style: TextButton.styleFrom(
                        backgroundColor: Theme.of(context).colorTheme.paper.secondary,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20),
                          side: BorderSide(
                              color: Theme.of(context)
                                  .colorTheme
                                  .primary
                                  .primary1),
                        ),
                      ),
                      label: Text(
                        t.translate(i18.measurementBook.backToTop),
                        style: Theme.of(context)
                            .digitTextTheme(context)
                            .bodyL
                            .copyWith(
                              color:
                                  Theme.of(context).colorTheme.primary.primary1,
                            ),
                      ),
                      onPressed: () {
                        _scrollController.animateTo(
                          0.0,
                          duration: const Duration(milliseconds: 800),
                          curve: Curves.easeInOut,
                        );
                      },
                      icon: SvgPicture.asset(Constants.doubleArrow),
                    );
                  } else {
                    return const SizedBox.shrink();
                  }
                },
              );
            },
          ),
          // appBar: customAppBar(),
          // drawer: const MySideBar(),
          body: BlocBuilder<MeasurementInboxBloc, MeasurementInboxState>(
            builder: (context, state) {
              return state.maybeMap(
                orElse: () {
                  return const SizedBox.shrink();
                },
                loaded: (mbInboxResponse) {
                  return CustomScrollView(
                    controller: _scrollController,
                    slivers: [
                      SliverPersistentHeader(
                        floating: true,
                        pinned: true,
                        delegate: MyHeaderDelegate(
                          child: LayoutBuilder(
                            builder: (context, constraints) => Container(
                              color: Theme.of(context).colorTheme.generic.background,
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.start,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Padding(
                                    padding:  EdgeInsets.symmetric(
                                      horizontal: Theme.of(context).spacerTheme.spacer4,
                                      vertical: Theme.of(context).spacerTheme.spacer4,
                                    ),
                                    child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.start,
                                        children: [
                                          BackNavigationButton(
                                            backNavigationButtonThemeData:
                                                const BackNavigationButtonThemeData()
                                                    .copyWith(
                                                        textColor:
                                                            Theme.of(context)
                                                                .colorTheme
                                                                .primary
                                                                .primary2,
                                                        contentPadding:
                                                            EdgeInsets.zero,
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
                                            backButtonText:
                                                AppLocalizations.of(context)
                                                        .translate(
                                                            i18.common.back) ??
                                                    'Back',
                                            handleBack: () {
                                              context.router.maybePop();
                                            },
                                          ),
                                        ]),
                                  ),
                                  Padding(
                                    padding: const EdgeInsets.only(left: 16.0,top: 0.0,bottom: 0.0),
                                    child: DigitTextBlock(
                                      heading:
                                          "${t.translate(i18.measurementBook.mbInbox)} (${mbInboxResponse.mbInboxResponse.totalCount ?? 0})",
                                    ),
                                  ),
                                  Padding(
                                    padding: const EdgeInsets.only(
                                        left: 4.0,
                                        right: 8.0,
                                        top: 8.0,
                                        bottom: 0),
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.spaceBetween,
                                      children: [
                                        Button(
                                          label: t.translate(i18.common.filter),
                                          onPressed: () {
                                            setState(() {
                                              pageCount = 0;
                                            });
                                            context.router
                                                .push(const MBFilterRoute());
                                          },
                                          type: ButtonType.tertiary,
                                          size: ButtonSize.large,
                                          prefixIcon: Icons.filter_list_alt,
                                        ),
                                        Button(
                                          prefixIcon: Icons.swap_vert,
                                          label: t.translate(
                                              i18.measurementBook.sort),
                                          onPressed: () {
                                            Conversion.openSortingModal(context,
                                                listData: Conversion.sortMB,
                                                sortType: SortType.mbSort);
                                          },
                                          type: ButtonType.tertiary,
                                          size: ButtonSize.large,
                                        ),
                                      ],
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          height: localizationState.maybeMap(
                            orElse: () => kIsWeb?180:160,
                            loaded: (value) {
                              Languages? ll = value.languages?.firstWhereOrNull(
                                  (element) => element.isSelected == true);
                              if (ll != null &&
                                  ll.value == LanguageEnum.en_IN.name) {
                                return kIsWeb?180: 160;
                              } else {
                                return kIsWeb?210:200;
                              }
                            },
                            loading: (value) => kIsWeb?180: 160,
                            error: (value) => kIsWeb?160:140,
                          ),
                        ),
                      ),
                      mbInboxResponse.mbInboxResponse.items!.isEmpty
                          ? SliverList(
                              delegate: SliverChildBuilderDelegate(
                                (BuildContext context, int index) {
                                  // Display items

                                  return Center(
                                    child: EmptyImage(
                                      align: Alignment.center,
                                      label: t.translate(
                                          i18.measurementBook.mbNotFound),
                                    ),
                                  );
                                },
                                childCount: 1,
                              ),
                            )
                          : SliverList(
                              delegate: SliverChildBuilderDelegate(
                                (BuildContext context, int index) {
                                  // Display items
                                  if (index ==
                                          (mbInboxResponse.isLoading
                                              ? mbInboxResponse
                                                  .mbInboxResponse.items!.length
                                              : mbInboxResponse.mbInboxResponse
                                                      .items!.length -
                                                  1) &&
                                      mbInboxResponse.isLoading) {
                                    // Display loading indicator
                                    return Container(
                                      padding: const EdgeInsets.all(16.0),
                                      alignment: Alignment.center,
                                      child: CircularProgressIndicator.adaptive(
                                        valueColor:
                                            AlwaysStoppedAnimation<Color>(
                                          Theme.of(context).colorScheme.primary,
                                        ),
                                      ),
                                    );
                                  }

                                  return ui_component.DigitCard(
                                      cardType: CardType.primary,
                                      margin: EdgeInsets.all(Theme.of(context)
                                          .spacerTheme
                                          .spacer2),
                                      children: [
                                        LabelValueList(
                                            maxLines: 3,
                                            labelFlex: 5,
                                            valueFlex: 5,
                                            items: [
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbNumber),
                                                  value: mbInboxResponse
                                                          .mbInboxResponse
                                                          .items?[index]
                                                          .businessObject
                                                          ?.measurementNumber ??
                                                      ""),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .projectDescription),
                                                  value: mbInboxResponse
                                                          .mbInboxResponse
                                                          .items?[index]
                                                          .businessObject
                                                          ?.contract
                                                          ?.additionalDetails
                                                          ?.projectDesc ??
                                                      ""),
                                              LabelValuePair(
                                                  label: t.translate(
                                                      i18.common.assignee),
                                                  value: mbInboxResponse
                                                          .mbInboxResponse
                                                          .items?[index]
                                                          .processInstance
                                                          ?.assignes
                                                          ?.first
                                                          .name ??
                                                      "NA"),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .workflowState),
                                                  value: mbInboxResponse
                                                              .mbInboxResponse
                                                              .items?[index]
                                                              .processInstance
                                                              ?.state
                                                              ?.state !=
                                                          null
                                                      ? t.translate(
                                                          "MB_WFMB_STATE_${mbInboxResponse.mbInboxResponse.items![index].processInstance!.state!.state!}")
                                                      : ""),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbAmount),
                                                  value: mbInboxResponse
                                                          .mbInboxResponse
                                                          .items?[index]
                                                          .businessObject
                                                          ?.measurementAdditionalDetail
                                                          ?.totalAmount
                                                          ?.roundToDouble()
                                                          .toStringAsFixed(2) ??
                                                      "0.00"),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbSlaDaysRemaining),
                                                  value: (mbInboxResponse
                                                              .mbInboxResponse
                                                              .items?[index]
                                                              .businessObject
                                                              ?.serviceSla ??
                                                          0)
                                                      .toString())
                                            ]),
                                        Button(
                                            mainAxisSize: MainAxisSize.max,
                                            label: t.translate(
                                                i18.measurementBook.openMbBook),
                                            onPressed: () {
                                              final contract = mbInboxResponse
                                                      .mbInboxResponse
                                                      .items?[index]
                                                      .businessObject
                                                      ?.contract
                                                      ?.contractNumber ??
                                                  "";
                                              final mbNumber = mbInboxResponse
                                                      .mbInboxResponse
                                                      .items?[index]
                                                      .businessObject
                                                      ?.measurementNumber ??
                                                  "";
                                              context.router.push(MBDetailRoute(
                                                contractNumber: contract,
                                                mbNumber: mbNumber,
                                                tenantId:
                                                    GlobalVariables.tenantId,
                                                type: MBScreen.update,
                                              ));
                                            },
                                            type: ButtonType.secondary,
                                            size: ButtonSize.large)
                                      ]);
                                },
                                childCount: mbInboxResponse.isLoading
                                    ? mbInboxResponse
                                            .mbInboxResponse.items!.length +
                                        1
                                    : mbInboxResponse
                                        .mbInboxResponse.items!.length,
                              ),
                            ),
                    ],
                  );
                },
                loading: (value) {
                  return Center(
                    child: shg_loader.Loaders.circularLoader(context),
                  );
                },
                error: (value) {
                  return Center(
                    child: EmptyImage(
                      align: Alignment.center,
                      label: t.translate(i18.common.wentWrong),
                    ),
                  );
                },
              );
            },
          ),
        );
      },
    );
  }
}

class MyHeaderDelegate extends SliverPersistentHeaderDelegate {
  final double height;
  final Widget child;

  MyHeaderDelegate({required this.child, required this.height});

  @override
  Widget build(
      BuildContext context, double shrinkOffset, bool overlapsContent) {
    return child;
  }

  @override
  double get maxExtent => height;

  @override
  double get minExtent => height;

  @override
  bool shouldRebuild(SliverPersistentHeaderDelegate oldDelegate) {
    return true;
  }
}
