import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/time_extension_request/my_service_requests_bloc.dart';
import '../../blocs/time_extension_request/service_requests_config.dart';
import '../../models/works/contracts_model.dart';
import '../../models/works/my_works_search_criteria.dart';
import '../../utils/constants.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/atoms/empty_image.dart';

@RoutePage()
class MyServiceRequestsPage extends StatefulWidget {
  const MyServiceRequestsPage({super.key});

  @override
  State<StatefulWidget> createState() {
    return _MyServiceRequestsPage();
  }
}

class _MyServiceRequestsPage extends State<MyServiceRequestsPage> {
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
    context.read<ServiceRequestsConfigBloc>().add(
          const GetServiceRequestsConfigEvent(),
        );
  }

  @override
  void deactivate() {
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
          return BlocBuilder<SearchMyServiceRequestsBloc,
              SearchMyServiceRequestsState>(builder: (context, state) {
            return state.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (ContractsModel? contractsModel) {
                  return (contractsModel?.contracts ?? []).isEmpty
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
          });
        }),
        body: SingleChildScrollView(
          child: BlocBuilder<LocalizationBloc, LocalizationState>(
              builder: (context, localState) {
            return BlocListener<ServiceRequestsConfigBloc,
                    ServiceRequestsConfigBlocState>(
                listener: (context, searchCriteriaState) {
              searchCriteriaState.maybeWhen(
                orElse: () => false,
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded:
                    (CBOMyServiceRequestsConfig? cboMyServiceRequestsConfig) =>
                        context.read<SearchMyServiceRequestsBloc>().add(
                              MyServiceRequestsSearchEvent(
                                  businessService: cboMyServiceRequestsConfig
                                          ?.searchCriteria ??
                                      'CONTRACT-REVISION'),
                            ),
                error: (String? error) => Notifiers.getToastMessage(
                    context, error.toString(), 'ERROR'),

                // Toast.showToast(context,
                //     message: t.translate(error.toString()),
                //     type: ToastType.error),
              );
            }, child: BlocBuilder<ServiceRequestsConfigBloc,
                        ServiceRequestsConfigBlocState>(
                    builder: (context, searchState) {
              return searchState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  loaded:
                      (CBOMyServiceRequestsConfig?
                              cboMyServiceRequestsConfig) =>
                          BlocListener<SearchMyServiceRequestsBloc,
                                  SearchMyServiceRequestsState>(
                              listener: (context, state) {
                            state.maybeWhen(
                                orElse: () => false,
                                loading: () =>
                                    shg_loader.Loaders.circularLoader(context),
                                error: (String? error) =>
                                    Notifiers.getToastMessage(
                                        context, error.toString(), 'ERROR'),
                                // Toast.showToast(context,
                                //     message: t.translate(error.toString()),
                                //     type: ToastType.error),
                                loaded: (ContractsModel? contracts) => false);
                          }, child: BlocBuilder<SearchMyServiceRequestsBloc,
                                      SearchMyServiceRequestsState>(
                                  builder: (context, searchState) {
                            return searchState.maybeWhen(
                                orElse: () => Container(),
                                loading: () =>
                                    shg_loader.Loaders.circularLoader(context),
                                loaded:
                                    (ContractsModel? contractsModel) => Column(
                                            crossAxisAlignment:
                                                CrossAxisAlignment.start,
                                            children: [
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
                                                  mainAxisAlignment:
                                                      MainAxisAlignment.start,
                                                  children: [
                                                    BackNavigationButton(
                                                      backNavigationButtonThemeData:
                                                          const BackNavigationButtonThemeData()
                                                              .copyWith(
                                                                  context:
                                                                      context,
                                                                  backButtonIcon:
                                                                      Icon(
                                                                    Icons
                                                                        .arrow_circle_left_outlined,
                                                                    size: MediaQuery.of(context).size.width <
                                                                            500
                                                                        ? Theme.of(context)
                                                                            .spacerTheme
                                                                            .spacer5
                                                                        : Theme.of(context)
                                                                            .spacerTheme
                                                                            .spacer6,
                                                                    color: Theme.of(
                                                                            context)
                                                                        .colorTheme
                                                                        .primary
                                                                        .primary2,
                                                                  )),
                                                      backButtonText:
                                                          AppLocalizations.of(
                                                                  context)
                                                              .translate(i18
                                                                  .common.back),
                                                      handleBack: () {
                                                        Navigator.pop(context);
                                                      },
                                                    ),
                                                  ],
                                                ),
                                              ),
                                              Column(
                                                  mainAxisAlignment:
                                                      MainAxisAlignment.start,
                                                  crossAxisAlignment:
                                                      CrossAxisAlignment.start,
                                                  children: [
                                                    Padding(
                                                      padding: EdgeInsets.only(
                                                          left:
                                                              Theme.of(context)
                                                                  .spacerTheme
                                                                  .spacer4,
                                                          top: 0.0,
                                                          bottom:
                                                              Theme.of(context)
                                                                  .spacerTheme
                                                                  .spacer4,
                                                          right: 0.0),
                                                      child: DigitTextBlock(
                                                        heading:
                                                            '${t.translate(i18.myServiceRequests.serviceRequestsLabel)} (${contractsModel?.contracts?.length})',
                                                      ),
                                                    ),
                                                    ToggleList(
                                                      toggleWidth:
                                                          MediaQuery.of(context)
                                                                  .size
                                                                  .width *
                                                              .48,
                                                      mainAxisAlignment:
                                                          MainAxisAlignment
                                                              .center,
                                                      toggleButtons: [
                                                        ToggleButtonModel(
                                                            name: t.translate(i18
                                                                .common
                                                                .inProgress),
                                                            code: "0"),
                                                        ToggleButtonModel(
                                                            name: t.translate(
                                                                i18.common
                                                                    .completed),
                                                            code: "1"),
                                                      ],
                                                      onChanged:
                                                          (ToggleButtonModel
                                                              toggleButtonModel) {
                                                        if (toggleButtonModel
                                                                .code ==
                                                            "0") {
                                                          setState(() {
                                                            inProgress = true;
                                                          });
                                                        } else {
                                                          setState(() {
                                                            inProgress = false;
                                                          });
                                                        }
                                                      },
                                                      selectedIndex: 0,
                                                    ),
                                                    (contractsModel?.contracts ??
                                                                    [])
                                                                .isNotEmpty &&
                                                            inProgress
                                                        ? Column(
                                                            children: (contractsModel
                                                                        ?.contracts ??
                                                                    [])
                                                                .map(
                                                                    (contract) {
                                                              return ui_card
                                                                  .DigitCard(
                                                                margin: EdgeInsets
                                                                    .all(Theme.of(
                                                                            context)
                                                                        .spacerTheme
                                                                        .spacer2),
                                                                cardType:
                                                                    CardType
                                                                        .primary,
                                                                children: [
                                                                  // Align(
                                                                  //   alignment:
                                                                  //       Alignment
                                                                  //           .centerLeft,
                                                                  //   child:
                                                                  //       Padding(
                                                                  //     padding: const EdgeInsets.only(
                                                                  //         left:
                                                                  //             4.0,
                                                                  //         bottom:
                                                                  //             16.0,
                                                                  //         top:
                                                                  //             8.0),
                                                                  //     child:
                                                                  //         TextChunk(
                                                                  //     subHeading:  contract.businessService == cboMyServiceRequestsConfig?.searchCriteria
                                                                  //           ? t.translate(i18.workOrder.timeExtRequests)
                                                                  //           : t.translate(i18.workOrder.closureRequests), // Replace this with actual data

                                                                  //         )

                                                                  //   ),
                                                                  // ),

                                                                  LabelValueList(
                                                                      heading: contract.businessService == cboMyServiceRequestsConfig?.searchCriteria
                                                                          ? t.translate(i18
                                                                              .workOrder
                                                                              .timeExtRequests)
                                                                          : t.translate(i18
                                                                              .workOrder
                                                                              .closureRequests),
                                                                      maxLines:
                                                                          3,
                                                                      labelFlex:
                                                                          5,
                                                                      valueFlex:
                                                                          5,
                                                                      items: [
                                                                        LabelValuePair(
                                                                            label:
                                                                                t.translate(i18.myServiceRequests.timeExtRequestId),
                                                                            value: contract.supplementNumber.toString()),
                                                                        LabelValuePair(
                                                                            label:
                                                                                t.translate(i18.workOrder.workOrderNo),
                                                                            value: contract.contractNumber.toString()),
                                                                        LabelValuePair(
                                                                            label:
                                                                                t.translate(i18.attendanceMgmt.projectDesc),
                                                                            value: contract.additionalDetails?.projectDesc ?? t.translate(i18.common.noValue)),
                                                                        LabelValuePair(
                                                                            label:
                                                                                t.translate(i18.workOrder.completionPeriod),
                                                                            value: '${contract.completionPeriod ?? 0} ${t.translate(i18.common.days)}'),
                                                                        LabelValuePair(
                                                                            label: t.translate(i18
                                                                                .workOrder.workStartDate),
                                                                            value: contract.startDate != null && contract.startDate != 0
                                                                                ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contract.startDate ?? 0).toString())
                                                                                : t.translate(i18.common.noValue)),
                                                                        LabelValuePair(
                                                                            label: t.translate(i18
                                                                                .workOrder.workEndDate),
                                                                            value: contract.endDate != null && contract.endDate != 0
                                                                                ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contract.endDate ?? 0).subtract(Duration(days: int.parse(contract.additionalDetails?.timeExt.toString() ?? '0'))).toString())
                                                                                : t.translate(i18.common.noValue)),
                                                                        LabelValuePair(
                                                                            label:
                                                                                t.translate(i18.workOrder.extensionReqInDays),
                                                                            value: '${contract.additionalDetails?.timeExt ?? 0} ${t.translate(i18.common.days)}'),
                                                                        LabelValuePair(
                                                                            label: t.translate(i18
                                                                                .myServiceRequests.revisedEndDate),
                                                                            value: contract.endDate != null && contract.endDate != 0
                                                                                ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contract.endDate ?? 0).toString())
                                                                                : t.translate(i18.common.noValue)),
                                                                        LabelValuePair(
                                                                            label:
                                                                                t.translate(i18.common.status),
                                                                            value: t.translate('WF_CONTRACT_TE_STATE_${contract.wfStatus.toString()}') ?? t.translate(i18.common.noValue)),
                                                                      ]),

                                                                  contract.businessService ==
                                                                              cboMyServiceRequestsConfig
                                                                                  ?.searchCriteria &&
                                                                          contract.wfStatus ==
                                                                              cboMyServiceRequestsConfig
                                                                                  ?.editTimeExtReqCode
                                                                      ? Padding(
                                                                          padding: const EdgeInsets
                                                                              .all(
                                                                              4.0),
                                                                          child:
                                                                              Button(
                                                                            type:
                                                                                ButtonType.primary,
                                                                            size:
                                                                                ButtonSize.large,
                                                                            mainAxisSize:
                                                                                MainAxisSize.max,
                                                                            onPressed:
                                                                                () {
                                                                              context.router.push(CreateTimeExtensionRequestRoute(contractNumber: contract.contractNumber, isEdit: true));
                                                                            },
                                                                            label: contract.wfStatus == cboMyServiceRequestsConfig?.editTimeExtReqCode
                                                                                ? t.translate(i18.myServiceRequests.editAction)
                                                                                : t.translate(i18.common.viewDetails),
                                                                          ),
                                                                        )
                                                                      : const SizedBox
                                                                          .shrink()
                                                                ],
                                                              );
                                                            }).toList(),
                                                          )
                                                        : EmptyImage(
                                                            label: AppLocalizations
                                                                    .of(context)
                                                                .translate(i18
                                                                    .myServiceRequests
                                                                    .noServiceRequests),
                                                            align: Alignment
                                                                .center,
                                                          ),
                                                    const SizedBox(
                                                      height: 16.0,
                                                    ),
                                                    (contractsModel?.contracts ??
                                                                [])
                                                            .isNotEmpty
                                                        ? const Align(
                                                            alignment: Alignment
                                                                .bottomCenter,
                                                            child:
                                                                PoweredByDigit(
                                                              version: Constants
                                                                  .appVersion,
                                                            ),
                                                          )
                                                        : const SizedBox
                                                            .shrink()
                                                  ]),
                                            ]));
                          })));
            }));
          }),
        ));
  }
}
