import 'package:collection/collection.dart';
// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/digit_button.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/svg.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/blocs/employee/mb/mb_check.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/blocs/organisation/org_search_bloc.dart';
import 'package:works_shg_app/blocs/wage_seeker_registration/wage_seeker_location_bloc.dart';
import 'package:works_shg_app/models/app_config/app_config_model.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/employee/mb/mb_logic.dart';
import 'package:works_shg_app/utils/employee/support_services.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../../../blocs/employee/work_order/workorder_book.dart';
import '../../../blocs/localization/app_localization.dart';
import '../../../utils/constants.dart';
import '../../../widgets/work_order/work_order_card.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

@RoutePage()
class WorkOderInboxPage extends StatefulWidget {
  const WorkOderInboxPage({super.key});

  @override
  State<WorkOderInboxPage> createState() => _WorkOderInboxPageState();
}

class _WorkOderInboxPageState extends State<WorkOderInboxPage> {
  final ScrollController _scrollController = ScrollController();
  List<String> items = []; // List to hold items
  int pageCount = 0; // Initial page count
  bool isLoading = false; // Loading indicator

  @override
  void initState() {
    context.read<WorkOrderInboxBloc>().add(
          WorkOrderInboxBlocCreateEvent(
            businessService: "MB",
            limit: 10,
            moduleName: 'contract-service',
            offset: pageCount,
            tenantId: GlobalVariables.tenantId!,
          ),
        );
    context.read<WageSeekerLocationBloc>().add(
          LocationEventWageSeeker(tenantId: GlobalVariables.tenantId!),
        );
    context.read<ORGSearchBloc>().add(
          SearchMbORGEvent(tenantId: GlobalVariables.tenantId!),
        );

    super.initState();
    _scrollController.addListener(_scrollListener);
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

    final state = context.read<WorkOrderInboxBloc>().state;

    state.maybeMap(
      orElse: () => {},
      loaded: (value) {
        if (value.search && value.searchData['contractNumber'] == "") {
          context.read<WorkOrderInboxBloc>().add(
                WorkOrderInboxSearchRepeatBlocEvent(
                  businessService: "Contract",
                  limit: 10,
                  moduleName: 'contract-module',
                  offset: s,
                  tenantId: GlobalVariables.tenantId!,
                ),
              );
          //return;
        } else if (value.search && value.searchData['contractNumber'] != "") {
//  context.read<WorkOrderInboxBloc>().add(
//                 WorkOrderInboxSearchRepeatBlocEvent(
//                   businessService: "Contract",
//                   limit: 10,
//                   moduleName: 'contract-module',
//                   offset: s,
//                   tenantId: GlobalVariables.tenantId!,
//                 ),
//               );
          return;
        } else {
          context.read<WorkOrderInboxBloc>().add(
                WorkOrderInboxBlocCreateEvent(
                  businessService: "MB",
                  limit: 10,
                  moduleName: 'contract-service',
                  offset: s,
                  tenantId: GlobalVariables.tenantId!,
                ),
              );
        }
      },
    );

    // context.read<WorkOrderInboxBloc>().add(
    //       WorkOrderInboxBlocCreateEvent(
    //         businessService: "MB",
    //         limit: 10,
    //         moduleName: 'measurement-module',
    //         offset: s,
    //         tenantId: GlobalVariables.tenantId!,
    //       ),
    //     );

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
          backgroundColor: Theme.of(context).colorTheme.generic.background,
          floatingActionButton:
              BlocBuilder<WorkOrderInboxBloc, WorkOrderInboxState>(
            builder: (context, state) {
              return state.maybeMap(
                orElse: () {
                  return const SizedBox.shrink();
                },
                loaded: (value) {
                  if (value.contracts!.length > 19) {
                    return TextButton.icon(
                      style: TextButton.styleFrom(
                        backgroundColor: Colors.white,
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
          floatingActionButtonLocation:
              FloatingActionButtonLocation.centerDocked,
          // appBar: customAppBar(),
          // drawer: const MySideBar(),
          body: BlocBuilder<WorkOrderInboxBloc, WorkOrderInboxState>(
            builder: (context, state) {
              return state.maybeMap(
                orElse: () => const SizedBox.shrink(),
                loaded: (value) {
                  return CustomScrollView(
                    controller: _scrollController,
                    slivers: [
                      SliverPersistentHeader(
                        pinned: true,
                        floating: true,
                        delegate: MyHeaderDelegate(
                          child: Container(
                            color:Theme.of(context).colorTheme.generic.background ,
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
                                          backButtonText: AppLocalizations.of(
                                                      context)
                                                  .translate(i18.common.back) ??
                                              'Back',
                                          handleBack: () {
                                            context.router.maybePop();
                                          },
                                        ),
                                      ]),
                                ),
                                Padding(
                                  padding:  EdgeInsets.only(left: Theme.of(context).spacerTheme.spacer4,top: 0.0,bottom: 0.0),
                                  child: DigitTextBlock(
                                    heading:
                                        "${t.translate(i18.measurementBook.workOrderInbox)} (${value.contracts?.length ?? 0})",
                                  ),
                                ),
                                Padding(
                                  padding:  EdgeInsets.only(
                                      left: Theme.of(context).spacerTheme.spacer2,
                                      right: Theme.of(context).spacerTheme.spacer2,
                                      top: 0.0,
                                      bottom: 0),
                                  child: Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                    children: [
                                      ui_component.Button(
                                          prefixIcon: Icons.filter_alt,
                                          label: t.translate(i18.common.filter),
                                          onPressed: () {
                                            context.router
                                                .push(const WOFilterRoute());
                                          },
                                          type:
                                              ui_component.ButtonType.tertiary,
                                          size: ui_component.ButtonSize.large),
                            
                                      // reset
                                      //TODO: not needed
                                      // value.search
                                      //     ? IconButton(
                                      //         onPressed: () {
                                      //           pageCount = 0;
                                      //           context
                                      //               .read<WorkOrderInboxBloc>()
                                      //               .add(
                                      //                 WorkOrderInboxBlocCreateEvent(
                                      //                   businessService: "MB",
                                      //                   limit: 10,
                                      //                   moduleName:
                                      //                       'contract-service',
                                      //                   offset: pageCount,
                                      //                   tenantId:
                                      //                       GlobalVariables
                                      //                           .tenantId!,
                                      //                 ),
                                      //               );
                                      //         },
                                      //         icon: Icon(
                                      //           Icons.restore_outlined,
                                      //           color: const DigitColors()
                                      //               .burningOrange,
                                      //         ),
                                      //       )
                                      //     : const SizedBox.shrink(),
                                      //
                                      ui_component.Button(
                                          prefixIcon: Icons.swap_vert,
                                          label: t.translate(
                                              i18.measurementBook.sort),
                                          onPressed: () {
                                            Conversion.openSortingModal(
                                              context,
                                              listData:
                                                  Conversion.sortWorkOrder,
                                              sortType: SortType.woSort,
                                            );
                                          },
                                          type:
                                              ui_component.ButtonType.tertiary,
                                          size: ui_component.ButtonSize.large),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                          ),
                          height: localizationState.maybeMap(
                            orElse: () => kIsWeb?180:160,
                            loaded: (value) {
                              Languages? ll = value.languages?.firstWhereOrNull(
                                  (element) => element.isSelected == true);
                              if (ll != null &&
                                  ll.value == LanguageEnum.en_IN.name) {
                                return kIsWeb?180:160;
                              } else {
                                return kIsWeb?200:180;
                              }
                            },
                            loading:(value) =>  kIsWeb?180:160,
                            error: (value) => kIsWeb?180:160,
                          ),
                        ),
                      ),
                      value.contracts!.isEmpty
                          ? SliverList(
                              delegate: SliverChildBuilderDelegate(
                                (BuildContext context, int index) {
                                  return Center(
                                    child: EmptyImage(
                                      align: Alignment.center,
                                      label: t.translate(
                                          i18.common.workOrderNotFound),
                                    ),
                                  );
                                },
                                childCount: 1,
                              ),
                            )
                          : SliverList(
                              delegate: SliverChildBuilderDelegate(
                                (BuildContext context, int index) {
                                  if (index ==
                                          (value.isLoading
                                              ? value.contracts!.length
                                              : value.contracts!.length - 1) &&
                                      value.isLoading) {
                                    return Container(
                                      padding: const EdgeInsets.all(16.0),
                                      alignment: Alignment.center,
                                      child: const CircularProgressIndicator(),
                                    );
                                  }

                                  return WorkOrderCard(
                                    widget1: Button(
                                      suffixIcon: Icons.arrow_forward_outlined,
                                      size: ButtonSize.large,
                                      type: ButtonType.link,
                                      label:
                                          t.translate(i18.common.viewDetails),
                                      onPressed: () {
                                        context.router
                                            .push(ViewWorkDetailsRoute(
                                          contractNumber: value
                                                  .contracts![index]
                                                  .contractNumber ??
                                              "",
                                          wfStatus: "ACCEPTED",
                                        ));
                                      },
                                    ),
                                    widget2: BlocListener<MeasurementCheckBloc,
                                        MeasurementCheckState>(
                                      listener: (context, measureMentState) {
                                        measureMentState.maybeMap(
                                          orElse: () => const SizedBox.shrink(),
                                          loaded: (value) {
                                            if (value.estimateStatus == true &&
                                                value.existingMB == true) {
                                              context.router.push(MBDetailRoute(
                                                contractNumber:
                                                    value.workOrderNumber!,
                                                mbNumber: "",
                                                tenantId:
                                                    GlobalVariables.tenantId,
                                                type: MBScreen.create,
                                              ));
                                            } else {
                                              if (value.estimateStatus ==
                                                  false) {
                                                Notifiers.getToastMessage(
                                                    context,
                                                    t.translate(i18.workOrder
                                                        .estimateRevisionError),
                                                    'ERROR');
                                                // Toast.showToast(context,
                                                //     message: t.translate(i18
                                                //         .workOrder
                                                //         .estimateRevisionError),
                                                //     type: ToastType.error);
                                              } else {
                                                Notifiers.getToastMessage(
                                                    context,
                                                    t.translate(i18.workOrder
                                                        .existingMBCreateError),
                                                    'ERROR');
                                                // Toast.showToast(context,
                                                //     message: t.translate(i18
                                                //         .workOrder
                                                //         .existingMBCreateError),
                                                //     type: ToastType.error);
                                              }
                                            }
                                          },
                                          error: (value) {
                                            Notifiers.getToastMessage(
                                                context, value.error!, 'ERROR');
                                            // Toast.showToast(
                                            //   context,
                                            //   message:
                                            //       t.translate(value.error!),
                                            //   type: ToastType.error,
                                            // );
                                          },
                                        );
                                      },
                                      child: Button(
                                        mainAxisSize: MainAxisSize.max,
                                        size: ButtonSize.large,
                                        type: ButtonType.primary,
                                        label: t.translate(
                                            i18.measurementBook.createMb),
                                        onPressed: () {
                                          final contract = value
                                                  .contracts?[index]
                                                  .contractNumber ??
                                              "";

                                          context
                                              .read<MeasurementCheckBloc>()
                                              .add(MeasurementCheckEvent(
                                                tenantId: value
                                                    .contracts![index]
                                                    .tenantId!,
                                                contractNumber: contract,
                                                measurementNumber: "",
                                                screenType: MBScreen.create,
                                              ));
                                        },
                                      ),
                                    ),
                                    items: {
                                      t.translate(i18.measurementBook
                                          .workOrderNumberInbox): value
                                              .contracts?[index]
                                              .contractNumber ??
                                          "",
                                      t.translate(i18.measurementBook
                                          .projectDescription): value
                                              .contracts?[index]
                                              .additionalDetails
                                              ?.projectDesc ??
                                          "",
                                      t.translate(i18.measurementBook.cboName):
                                          value.contracts?[index]
                                                  .additionalDetails?.cboName ??
                                              "",
                                      t.translate(i18.measurementBook.cboRole):
                                          value
                                                  .contracts?[index]
                                                  .additionalDetails
                                                  ?.cboOrgNumber ??
                                              "",
                                      t.translate(i18.measurementBook
                                          .officerInChargeName): value
                                              .contracts?[index]
                                              .additionalDetails
                                              ?.officerInChargeName
                                              ?.name ??
                                          "NA",
                                      t.translate(i18.common.startDate): value
                                                  .contracts?[index]
                                                  .startDate !=
                                              null
                                          ? DateFormat('dd/MM/yyyy').format(
                                              DateTime
                                                  .fromMillisecondsSinceEpoch(
                                                      value.contracts?[index]
                                                              .startDate! ??
                                                          0))
                                          : "NA",
                                      t.translate(i18.common.endDate): value
                                                  .contracts?[index].endDate !=
                                              null
                                          ? DateFormat('dd/MM/yyyy').format(
                                              DateTime
                                                  .fromMillisecondsSinceEpoch(
                                                      value.contracts?[index]
                                                              .endDate! ??
                                                          0))
                                          : "NA",
                                      t.translate(
                                              i18.measurementBook.workValue):
                                          "${NumberFormat('##,##,##,##,###').format(value.contracts?[index].additionalDetails!.totalEstimatedAmount!.roundToDouble() ?? 0.00)}.00",
                                      t
                                          .translate(
                                              i18.common.status): t.translate(
                                          "WF_WORK_ORDER_STATE_${value.contracts?[index].wfStatus}")
                                    },
                                  );
                                },
                                childCount: value.isLoading
                                    ? value.contracts!.length + 1
                                    : value.contracts!.length,
                              ),
                            ),
                    ],
                  );
                },
                loading: (value) =>
                    Center(child: shg_loader.Loaders.circularLoader(context)),
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
