import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/common_widgets.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/time_extension_request/my_service_requests_bloc.dart';
import '../../blocs/time_extension_request/service_requests_config.dart';
import '../../models/works/contracts_model.dart';
import '../../models/works/my_works_search_criteria.dart';
import '../../utils/common_methods.dart';
import '../../utils/constants.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/atoms/empty_image.dart';
import '../../widgets/atoms/tabs_button.dart';
import '../../widgets/drawer_wrapper.dart';

class MyServiceRequestsPage extends StatefulWidget {
  const MyServiceRequestsPage({Key? key}) : super(key: key);

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
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(
            Drawer(child: SideBar(module: CommonMethods.getLocaleModules()))),
        bottomNavigationBar: BlocBuilder<LocalizationBloc, LocalizationState>(
            builder: (context, localState) {
          return BlocBuilder<SearchMyServiceRequestsBloc,
              SearchMyServiceRequestsState>(builder: (context, state) {
            return state.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (ContractsModel? contractsModel) {
                  return (contractsModel?.contracts ?? []).isEmpty
                      ? const SizedBox(
                          height: 30,
                          child: Align(
                            alignment: Alignment.bottomCenter,
                            child: PoweredByDigit(),
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
                  loaded: (CBOMyServiceRequestsConfig?
                          cboMyServiceRequestsConfig) =>
                      context.read<SearchMyServiceRequestsBloc>().add(
                            MyServiceRequestsSearchEvent(
                                businessService: cboMyServiceRequestsConfig
                                        ?.searchCriteria ??
                                    'CONTRACT-REVISION'),
                          ),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'));
            }, child: BlocBuilder<ServiceRequestsConfigBloc,
                        ServiceRequestsConfigBlocState>(
                    builder: (context, searchState) {
              return searchState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  loaded:
                      (CBOMyServiceRequestsConfig? cboMyServiceRequestsConfig) =>
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
                                              Back(
                                                backLabel: AppLocalizations.of(
                                                        context)
                                                    .translate(i18.common.back),
                                              ),
                                              Column(
                                                  mainAxisAlignment:
                                                      MainAxisAlignment.start,
                                                  crossAxisAlignment:
                                                      CrossAxisAlignment.start,
                                                  children: [
                                                    Padding(
                                                      padding:
                                                          const EdgeInsets.all(
                                                              16.0),
                                                      child: Text(
                                                        '${t.translate(i18.myServiceRequests.serviceRequestsLabel)} (${contractsModel?.contracts?.length})',
                                                        style: Theme.of(context)
                                                            .textTheme
                                                            .displayMedium,
                                                        textAlign:
                                                            TextAlign.left,
                                                      ),
                                                    ),
                                                    Row(
                                                      mainAxisAlignment:
                                                          MainAxisAlignment
                                                              .center,
                                                      children: [
                                                        TabButton(
                                                          t.translate(i18.common
                                                              .inProgress),
                                                          isMainTab: true,
                                                          isSelected:
                                                              inProgress,
                                                          onPressed: () {
                                                            setState(() {
                                                              inProgress = true;
                                                            });
                                                          },
                                                        ),
                                                        TabButton(
                                                          t.translate(i18.common
                                                              .completed),
                                                          isMainTab: true,
                                                          isSelected:
                                                              !inProgress,
                                                          onPressed: () {
                                                            setState(() {
                                                              inProgress =
                                                                  false;
                                                            });
                                                          },
                                                        )
                                                      ],
                                                    ),
                                                    (contractsModel?.contracts ??
                                                                [])
                                                            .isNotEmpty && inProgress
                                                        ? Column(
                                                            children: (contractsModel
                                                                        ?.contracts ??
                                                                    [])
                                                                .map(
                                                                    (contract) {
                                                              return DigitCard(
                                                                child: Column(
                                                                  children: [
                                                                    Align(
                                                                      alignment:
                                                                          Alignment
                                                                              .centerLeft,
                                                                      child:
                                                                          Padding(
                                                                        padding: const EdgeInsets.only(
                                                                            left:
                                                                                4.0,
                                                                            bottom:
                                                                                16.0,
                                                                            top:
                                                                                8.0),
                                                                        child:
                                                                            Text(
                                                                          contract.businessService == cboMyServiceRequestsConfig?.searchCriteria
                                                                              ? t.translate(i18.workOrder.timeExtRequests)
                                                                              : t.translate(i18.workOrder.closureRequests), // Replace this with actual data
                                                                          style: DigitTheme
                                                                              .instance
                                                                              .mobileTheme
                                                                              .textTheme
                                                                              .headlineLarge
                                                                              ?.apply(
                                                                            color:
                                                                                const DigitColors().black,
                                                                          ),
                                                                          textAlign:
                                                                              TextAlign.left,
                                                                        ),
                                                                      ),
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .myServiceRequests
                                                                          .timeExtRequestId), // Replace with actual data
                                                                      description: contract
                                                                          .supplementNumber
                                                                          .toString(), // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .workOrder
                                                                          .workOrderNo), // Replace with actual data
                                                                      description: contract
                                                                          .contractNumber
                                                                          .toString(), // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .attendanceMgmt
                                                                          .projectDesc), // Replace with actual data
                                                                      description: contract
                                                                              .additionalDetails
                                                                              ?.projectDesc ??
                                                                          t.translate(i18
                                                                              .common
                                                                              .noValue), // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .workOrder
                                                                          .completionPeriod), // Replace with actual data
                                                                      description:
                                                                          '${contract.completionPeriod ?? 0} ${t.translate(i18.common.days)}', // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .workOrder
                                                                          .workStartDate), // Replace with actual data
                                                                      description: contract.startDate != null &&
                                                                              contract.startDate !=
                                                                                  0
                                                                          ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contract.startDate ?? 0)
                                                                              .toString())
                                                                          : t.translate(i18
                                                                              .common
                                                                              .noValue), // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .workOrder
                                                                          .workEndDate), // Replace with actual data
                                                                      description: contract.endDate != null &&
                                                                              contract.endDate !=
                                                                                  0
                                                                          ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contract.endDate ?? 0).subtract(Duration(days: int.parse(contract.additionalDetails?.timeExt.toString() ?? '0') ))
                                                                              .toString())
                                                                          : t.translate(i18
                                                                              .common
                                                                              .noValue), // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .workOrder
                                                                          .extensionReqInDays), // Replace with actual data
                                                                      description:
                                                                          '${contract.additionalDetails?.timeExt ?? 0} ${t.translate(i18.common.days)}', // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .myServiceRequests
                                                                          .revisedEndDate), // Replace with actual data
                                                                      description: contract.endDate != null &&
                                                                          contract.endDate !=
                                                                              0
                                                                          ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contract.endDate ?? 0)
                                                                          .toString())
                                                                          : t.translate(i18
                                                                          .common
                                                                          .noValue), // Replace with actual data
                                                                    ),
                                                                    CommonWidgets
                                                                        .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .common
                                                                          .status), // Replace with actual data
                                                                      description: t.translate('WF_CONTRACT_STATE_${contract.wfStatus.toString()}') ??
                                                                          t.translate(i18
                                                                              .common
                                                                              .noValue),
                                                                      descColor: contract.wfStatus == cboMyServiceRequestsConfig?.editTimeExtReqCode || contract.wfStatus == Constants.rejected ? DigitTheme.instance.colorScheme.error : contract.wfStatus == Constants.approvedKey ? DigitTheme.instance.colorScheme.onSurfaceVariant : null // Replace with actual data
                                                                    ),
                                                                    contract.businessService == cboMyServiceRequestsConfig?.searchCriteria  && contract.wfStatus == cboMyServiceRequestsConfig?.editTimeExtReqCode ? Padding(
                                                                      padding: const EdgeInsets.all(4.0),
                                                                      child: DigitElevatedButton(
                                                                        onPressed: () {
                                                                          context.router.push(CreateTimeExtensionRequestRoute(contractNumber: contract.contractNumber, isEdit: true));
                                                                        },
                                                                        child: Center(
                                                                          child: Text(
                                                                              contract.wfStatus == cboMyServiceRequestsConfig?.editTimeExtReqCode
                                                                                  ? t.translate(i18.myServiceRequests.editAction)
                                                                                  : t.translate(i18.common.viewDetails),
                                                                              style: DigitTheme.instance.mobileTypography.textTheme.labelSmall?.
                                                                                  apply(color: const DigitColors().white)),
                                                                        ),
                                                                      ),
                                                                    ) : const SizedBox.shrink()
                                                                  ],
                                                                ),
                                                              );
                                                            }).toList(),
                                                          )
                                                        :  EmptyImage(
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
                                                    (contractsModel?.contracts ?? []).isNotEmpty
                                                        ? const Align(
                                                            alignment: Alignment
                                                                .bottomCenter,
                                                            child:
                                                                PoweredByDigit(),
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
