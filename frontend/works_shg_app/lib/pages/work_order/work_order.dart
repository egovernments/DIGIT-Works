// import 'package:digit_components/digit_components.dart' as ui_old;
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';
import 'package:works_shg_app/widgets/work_details_card.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/time_extension_request/valid_time_extension.dart';
import '../../blocs/work_orders/accept_work_order.dart';
import '../../blocs/work_orders/decline_work_order.dart';
import '../../blocs/work_orders/my_works_search_criteria.dart';
import '../../blocs/work_orders/search_my_works.dart';
import '../../models/works/contracts_model.dart';
import '../../utils/constants.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';

@RoutePage()
class WorkOrderPage extends StatefulWidget {
  const WorkOrderPage({super.key});

  @override
  State<StatefulWidget> createState() {
    return _WorkOrderPage();
  }
}

class _WorkOrderPage extends State<WorkOrderPage> {
  bool hasLoaded = true;
  bool inProgress = true;
  List<Map<String, dynamic>> workOrderList = [];
  List<Map<String, dynamic>> inProgressWorkOrderList = [];
  List<Map<String, dynamic>> completedWorkOrderList = [];

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<MyWorksSearchCriteriaBloc>().add(
          const GetMyWorksSearchCriteriaConfigEvent(),
        );
  }

  @override
  void deactivate() {
    context.read<AcceptWorkOrderBloc>().add(
          const WorkOrderDisposeEvent(),
        );
    super.deactivate();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
      backgroundColor: Theme.of(context).colorTheme.generic.background,
      // appBar: customAppBar(),
      // drawer: const MySideBar(),
      bottomNavigationBar: BlocBuilder<LocalizationBloc, LocalizationState>(
          builder: (context, localState) {
        return BlocBuilder<SearchMyWorksBloc, SearchMyWorksState>(
            builder: (context, state) {
          return state.maybeWhen(
              orElse: () => Container(),
              loading: () => shg_loader.Loaders.circularLoader(context),
              loaded: (ContractsModel? contractsModel) {
                return workOrderList.isEmpty || workOrderList.length == 1
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
        });
      }),
      body: SingleChildScrollView(
        child: BlocBuilder<LocalizationBloc, LocalizationState>(
          builder: (context, localState) {
            return BlocListener<MyWorksSearchCriteriaBloc,
                MyWorksSearchCriteriaBlocState>(
              listener: (context, searchCriteriaState) {
                searchCriteriaState.maybeWhen(
                  orElse: () => false,
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (List<String>? searchCriteria, String? acceptCode) =>
                      context.read<SearchMyWorksBloc>().add(
                            MyWorksSearchEvent(searchCriteria),
                          ),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  // Toast.showToast(context,
                  //     message: t.translate(error.toString()),
                  //     type: ToastType.error),
                );
              },
              child: BlocBuilder<MyWorksSearchCriteriaBloc,
                  MyWorksSearchCriteriaBlocState>(
                builder: (context, searchState) {
                  return searchState.maybeWhen(
                    orElse: () => Container(),
                    loading: () => shg_loader.Loaders.circularLoader(context),
                    error: (String? error) => Notifiers.getToastMessage(
                        context, error.toString(), 'ERROR'),
                    loaded:
                        (List<String>? searchCriteria, String? acceptCode) =>
                            BlocListener<SearchMyWorksBloc, SearchMyWorksState>(
                      listener: (context, state) {
                        state.maybeWhen(
                            orElse: () => false,
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
                            error: (String? error) => Notifiers.getToastMessage(
                                context, error.toString(), 'ERROR'),
                            // Toast.showToast(context,
                            //     message: t.translate(error.toString()),
                            //     type: ToastType.error),
                            loaded: (ContractsModel? contracts) {
                              workOrderList = contracts!.contracts!
                                  .map((e) => {
                                        'cardDetails': {
                                          i18.workOrder.workOrderNo:
                                              e.contractNumber ??
                                                  i18.common.noValue,
                                          i18.attendanceMgmt.projectName: e
                                                  .additionalDetails
                                                  ?.projectName ??
                                              i18.common.noValue,
                                          i18.attendanceMgmt.projectDesc: e
                                                  .additionalDetails
                                                  ?.projectDesc ??
                                              i18.common.noValue,
                                          i18.workOrder.roleOfCBO:
                                              'COMMON_MASTERS_${e.executingAuthority ?? 'NA'}',
                                          i18.attendanceMgmt.engineerInCharge: e
                                                  .additionalDetails
                                                  ?.officerInChargeName
                                                  ?.name ??
                                              t.translate(i18.common.noValue),
                                          i18.workOrder.contractIssueDate:
                                              e.issueDate != null
                                                  ? DateFormats.timeStampToDate(
                                                      e.issueDate,
                                                      format: "dd/MM/yyyy")
                                                  : i18.common.noValue,
                                          i18.workOrder.dueDate: e.issueDate !=
                                                  null
                                              ? DateFormats.getFilteredDate(DateTime
                                                      .fromMillisecondsSinceEpoch(
                                                          e.issueDate ?? 0)
                                                  .add(const Duration(days: 7))
                                                  .toLocal()
                                                  .toString())
                                              : t.translate(i18.common.noValue),
                                          i18.workOrder.workOrderAmount:
                                              '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                          i18.common.status:
                                              'WF_WORK_ORDER_STATE_${e.wfStatus.toString()}',
                                          //TODO:temo workorder
                                          Constants.activeInboxStatus:
                                              e.wfStatus != acceptCode
                                                  ? 'false'
                                                  : 'true'
                                        },
                                        'payload': e.toMap()
                                      })
                                  .toList();
                            });
                      },
                      child: BlocBuilder<SearchMyWorksBloc, SearchMyWorksState>(
                        builder: (context, searchState) {
                          return searchState.maybeWhen(
                            orElse: () => Container(),
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
                            loaded: (ContractsModel? contractsModel) => Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              mainAxisAlignment: MainAxisAlignment.start,
                              children: [
                                Padding(
                                  padding: EdgeInsets.symmetric(
                                    horizontal:
                                        Theme.of(context).spacerTheme.spacer4,
                                    vertical:
                                        Theme.of(context).spacerTheme.spacer4,
                                  ),
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
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
                                    ],
                                  ),
                                ),
                                Column(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Padding(
                                        padding: EdgeInsets.only(
                                            top: 0.0,
                                            bottom: 0.0,
                                            left: Theme.of(context)
                                                .spacerTheme
                                                .spacer4),
                                        child: DigitTextBlock(
                                          heading:
                                              '${AppLocalizations.of(context).translate(i18.home.myWorks)} (${workOrderList.length})',
                                        ),
                                      ),

                                      // TODO: new toogle
                                      Padding(
                                        padding: EdgeInsets.only(
                                            top: Theme.of(context)
                                                .spacerTheme
                                                .spacer4,
                                            bottom: Theme.of(context)
                                                .spacerTheme
                                                .spacer4),
                                        child: ToggleList(
                                          toggleWidth: MediaQuery.of(context)
                                                  .size
                                                  .width *
                                              .48,
                                          crossAxisAlignment:
                                              CrossAxisAlignment.center,
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          toggleButtons: [
                                            ToggleButtonModel(
                                                name: t.translate(
                                                    i18.common.inProgress),
                                                code: "0"),
                                            ToggleButtonModel(
                                                name: t.translate(
                                                    i18.common.completed),
                                                code: "1"),
                                          ],
                                          onChanged: (ToggleButtonModel
                                              toggleButtonModel) {
                                            if (toggleButtonModel.code == "0") {
                                              setState(() {
                                                inProgress = true;
                                                workOrderList = contractsModel!
                                                    .contracts!
                                                    .map((e) => {
                                                          'cardDetails': {
                                                            i18.workOrder
                                                                .workOrderNo: e
                                                                    .contractNumber ??
                                                                t.translate(i18
                                                                    .common
                                                                    .noValue),
                                                            i18.attendanceMgmt
                                                                .projectName: e
                                                                    .additionalDetails
                                                                    ?.projectName ??
                                                                t.translate(i18
                                                                    .common
                                                                    .noValue),
                                                            i18.attendanceMgmt
                                                                .projectDesc: e
                                                                    .additionalDetails
                                                                    ?.projectDesc ??
                                                                t.translate(i18
                                                                    .common
                                                                    .noValue),
                                                            i18.workOrder
                                                                    .roleOfCBO:
                                                                t.translate(
                                                                    'COMMON_MASTERS_${e.executingAuthority ?? 'NA'}'),
                                                            i18.attendanceMgmt
                                                                .engineerInCharge: e
                                                                    .additionalDetails
                                                                    ?.officerInChargeName
                                                                    ?.name ??
                                                                t.translate(i18
                                                                    .common
                                                                    .noValue),
                                                            i18.workOrder
                                                                .contractIssueDate: e.issueDate !=
                                                                    null
                                                                ? DateFormats.timeStampToDate(
                                                                    e.issueDate,
                                                                    format:
                                                                        "dd/MM/yyyy")
                                                                : t.translate(i18
                                                                    .common
                                                                    .noValue),
                                                            i18.workOrder
                                                                .dueDate: e
                                                                        .issueDate !=
                                                                    null
                                                                ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(
                                                                        e.issueDate ??
                                                                            0)
                                                                    .add(const Duration(
                                                                        days:
                                                                            7))
                                                                    .toLocal()
                                                                    .toString())
                                                                : t.translate(i18
                                                                    .common
                                                                    .noValue),
                                                            i18.workOrder
                                                                    .workOrderAmount:
                                                                '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                                            i18.common.status:
                                                                t.translate(
                                                                    'WF_WORK_ORDER_STATE_${e.wfStatus.toString()}'),
                                                            Constants
                                                                    .activeInboxStatus:
                                                                e.wfStatus ==
                                                                        acceptCode
                                                                    ? 'true'
                                                                    : 'false'
                                                          },
                                                          'payload': e.toMap()
                                                        })
                                                    .toList();
                                              });
                                            } else {
                                              setState(() {
                                                inProgress = false;
                                                workOrderList = [];
                                              });
                                            }
                                          },
                                          selectedIndex: 0,
                                        ),
                                      ),

                                      workOrderList.isNotEmpty
                                          ? WorkDetailsCard(
                                              workOrderList,
                                              isWorkOrderInbox: true,
                                              acceptWorkOrderCode: acceptCode,
                                              elevatedButtonLabel:
                                                  AppLocalizations.of(context)
                                                      .translate(
                                                          i18.common.accept),
                                              outlinedButtonLabel:
                                                  AppLocalizations.of(context)
                                                      .translate(
                                                          i18.common.decline),
                                            )
                                          : inProgress
                                              ? EmptyImage(
                                                  label: AppLocalizations.of(
                                                          context)
                                                      .translate(i18.workOrder
                                                          .noWorkOrderAssigned),
                                                  align: Alignment.center,
                                                )
                                              : EmptyImage(
                                                  label: AppLocalizations.of(
                                                          context)
                                                      .translate(i18.workOrder
                                                          .noCompletedWorkOrderFound),
                                                  align: Alignment.center,
                                                ),
                                      SizedBox(
                                        height: Theme.of(context)
                                            .spacerTheme
                                            .spacer4,
                                      ),
                                      workOrderList.isNotEmpty &&
                                              workOrderList.length > 1
                                          ?
                                          // TODO:
                                          const Align(
                                              alignment: Alignment.bottomCenter,
                                              child: PoweredByDigit(
                                                version: Constants.appVersion,
                                              ),
                                            )
                                          : const SizedBox.shrink()
                                    ]),
                                BlocListener<DeclineWorkOrderBloc,
                                    DeclineWorkOrderState>(
                                  listener: (context, state) {
                                    state.maybeWhen(
                                        initial: () => Container(),
                                        loading: () => hasLoaded = false,
                                        error: (String? error) {
                                          Notifiers.getToastMessage(context,
                                              error.toString(), 'ERROR');
                                          // Toast.showToast(context,
                                          //     message:
                                          //         t.translate(error.toString()),
                                          //     type: ToastType.error);
                                        },
                                        loaded:
                                            (ContractsModel? declinedContract) {
                                          Notifiers.getToastMessage(
                                              context,
                                              '${declinedContract?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}',
                                              'SUCCESS');
                                          // Toast.showToast(context,
                                          //     message: t.translate(
                                          //         '${declinedContract?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}'),
                                          //     type: ToastType.success);
                                          context.router.popAndPush(
                                              const WorkOrderRoute());
                                        },
                                        orElse: () => const SizedBox.shrink());
                                  },
                                  child: const SizedBox.shrink(),
                                ),
                                BlocListener<AcceptWorkOrderBloc,
                                    AcceptWorkOrderState>(
                                  listener: (context, state) {
                                    state.maybeWhen(
                                        initial: () => Container(),
                                        loading: () =>
                                            shg_loader.Loaders.circularLoader(
                                                context),
                                        error: (String? error) {
                                          Notifiers.getToastMessage(context,
                                              error.toString(), 'ERROR');
                                          // Toast.showToast(context,
                                          //     message:
                                          //         t.translate(error.toString()),
                                          //     type: ToastType.error);
                                        },
                                        loaded:
                                            (ContractsModel? acceptedContract) {
                                          Notifiers.getToastMessage(
                                              context,
                                              '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${acceptedContract?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}',
                                              'SUCCESS');
                                          // Toast.showToast(context,
                                          //     message: t.translate(
                                          //         '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${acceptedContract?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}'),
                                          //     type: ToastType.success);
                                          context.router.popAndPush(
                                              const WorkOrderRoute());
                                        },
                                        orElse: () => false);
                                  },
                                  child: Container(),
                                ),
                                BlocListener<ValidTimeExtCreationsSearchBloc,
                                    ValidTimeExtCreationsSearchState>(
                                  listener: (context, validContractState) {
                                    validContractState.maybeWhen(
                                      orElse: () => false,
                                      loaded: (Contracts? contracts) => context
                                          .router
                                          .push(CreateTimeExtensionRequestRoute(
                                        contractNumber:
                                            contracts?.contractNumber,
                                      )),
                                      error: (String? error) =>
                                          Notifiers.getToastMessage(
                                              context,
                                              error.toString() ?? 'ERR!',
                                              'ERROR'),
                                      // Toast.showToast(context,
                                      //     message:
                                      //         t.translate(error ?? 'ERR!'),
                                      //     type: ToastType.error),
                                    );
                                  },
                                  child: const SizedBox.shrink(),
                                ),
                              ],
                            ),
                          );
                        },
                      ),
                    ),
                  );
                },
              ),
            );
          },
        ),
      ),
    );
  }
}
