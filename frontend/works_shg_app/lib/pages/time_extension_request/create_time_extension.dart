import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
as i18;
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/time_extension_request/create_time_extension_request.dart';
import '../../blocs/work_orders/search_individual_work.dart';
import '../../models/works/contracts_model.dart';
import '../../router/app_router.dart';
import '../../utils/common_methods.dart';
import '../../utils/common_widgets.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/drawer_wrapper.dart';
import '../../widgets/loaders.dart' as shg_loader;

class CreateTimeExtensionRequestPage extends StatefulWidget {
  final String? contractNumber;
  final bool? isEdit;

  const CreateTimeExtensionRequestPage(
      {super.key, @queryParam this.contractNumber,@queryParam this.isEdit,});

  @override
  State<StatefulWidget> createState() {
    return _CreateTimeExtensionRequestPage();
  }
}

class _CreateTimeExtensionRequestPage
    extends State<CreateTimeExtensionRequestPage> {
  bool hasLoaded = true;
  bool inProgress = true;
  String extensionDaysKey = 'extensionDays';
  String reasonForExtensionKey = 'reasonForExtension';

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    context.read<SearchIndividualWorkBloc>().add(
      IndividualWorkSearchEvent(contractNumber: widget.contractNumber, body: widget.isEdit == true
          ? {
        "businessService": "CONTRACT-REVISION"
      } : null),
    );
    await Future.delayed(const Duration(seconds: 1));
  }

  @override
  void deactivate() {
    context.read<SearchIndividualWorkBloc>().add(
      const DisposeIndividualContract(),
    );
    super.deactivate();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return WillPopScope(
      onWillPop: () async {
        if(context.router.currentUrl.contains('isEdit')){
          context.router.popUntilRouteWithPath('home');
          context.router.push(const MyServiceRequestsRoute());
        }
        else {
          context.router.popUntilRouteWithPath('home');
          context.router.push(const WorkOrderRoute());
        }
        return false;
      },
      child: Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(
            Drawer(child: SideBar(module: CommonMethods.getLocaleModules()))),
        bottomNavigationBar: const SizedBox(
          height: 50,
          child: Align(
            alignment: Alignment.bottomCenter,
            child: PoweredByDigit(),
          ),
        ),
        body: BlocBuilder<SearchIndividualWorkBloc,
            SearchIndividualWorkState>(
            builder: (context, contractState) {
              return contractState.maybeWhen(orElse: () => const SizedBox.shrink(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (ContractsModel? contracts) {
                    return ReactiveFormBuilder(form: () => buildForm(contractState),
                        builder: (context, form, child) {
                          return ScrollableContent(
                            footer: SizedBox(
                              height: 100,
                              child: Center(
                                child: DigitCard(
                                  child: DigitElevatedButton(
                                    onPressed: () {
                                      form.markAllAsTouched(
                                          updateParent:
                                          false);
                                      if (!form.valid) {
                                        return;
                                      } else {
                                        DateTime endDate = DateTime
                                            .fromMillisecondsSinceEpoch(
                                            contracts?.contracts?.first.endDate ?? 0);
                                        int extensionDate = endDate
                                            .add(Duration(days: int.parse(form
                                            .value[extensionDaysKey].toString())))
                                            .millisecondsSinceEpoch;
                                        context.read<
                                            CreateTimeExtensionRequestBloc>().add(
                                            TimeExtensionRequestEvent(
                                                contractsModel: contracts?.contracts?.first,
                                                action: widget.isEdit == true ? 'EDIT' : 'CREATE',
                                                extensionDate: extensionDate,
                                                isEdit: widget.isEdit ?? false,
                                                reason: form
                                                    .value[reasonForExtensionKey]
                                                    .toString(),
                                                extensionDays: form
                                                    .value[extensionDaysKey]
                                                    .toString()
                                            ));
                                      }
                                    },
                                    child: Center(
                                        child: Text(
                                            t.translate(
                                                i18.common.submit))),
                                  ),
                                ),
                              ),
                            ),
                            children: [
                              BlocBuilder<LocalizationBloc, LocalizationState>(
                                  builder: (context, localState) {
                                    return BlocConsumer<SearchIndividualWorkBloc,
                                        SearchIndividualWorkState>(
                                      listener: (context, state) {
                                        state.maybeWhen(
                                          orElse: () => false,
                                        );
                                      },
                                      builder: (context, searchState) {
                                        return searchState.maybeWhen(
                                            orElse: () => Container(),
                                            loading: () =>
                                                shg_loader.Loaders.circularLoader(context),
                                            initial: () => Container(),
                                            loaded: (ContractsModel? contractsModel) {
                                              final contracts = contractsModel?.contracts;
                                              if (contracts != null) {
                                                return Column(
                                                    crossAxisAlignment: CrossAxisAlignment
                                                        .start,
                                                    children: [
                                                      Back(
                                                        backLabel: AppLocalizations.of(
                                                            context)
                                                            .translate(i18.common.back),
                                                        callback: () {
                                                          if(context.router.currentUrl.contains('isEdit')){
                                                            context.router.popUntilRouteWithPath('home');
                                                            context.router.push(const MyServiceRequestsRoute());
                                                          }
                                                          else {
                                                            context.router.popUntilRouteWithPath('home');
                                                            context.router.push(const WorkOrderRoute());
                                                          }
                                                        },
                                                      ),
                                                      DigitCard(
                                                        padding: const EdgeInsets.all(8.0),
                                                        child: Column(
                                                            mainAxisAlignment: MainAxisAlignment
                                                                .start,
                                                            crossAxisAlignment: CrossAxisAlignment
                                                                .start,
                                                            children: [
                                                              contracts.isNotEmpty
                                                                  ? Column(
                                                                children: [
                                                                  CommonWidgets
                                                                      .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18.workOrder
                                                                          .workOrderNo),
                                                                      description: contracts
                                                                          .first
                                                                          .contractNumber ??
                                                                          t.translate(i18.common
                                                                              .noValue)),
                                                                  CommonWidgets
                                                                      .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .attendanceMgmt
                                                                          .projectId),
                                                                      description: contracts
                                                                          .first
                                                                          .additionalDetails
                                                                          ?.projectId ??
                                                                          t.translate(i18.common
                                                                              .noValue)),
                                                                  CommonWidgets
                                                                      .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18
                                                                          .attendanceMgmt
                                                                          .projectDesc),
                                                                      description: contracts
                                                                          .first
                                                                          .additionalDetails
                                                                          ?.projectDesc ??
                                                                          t.translate(
                                                                              i18.common
                                                                                  .noValue)),
                                                                  CommonWidgets
                                                                      .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18.workOrder
                                                                          .completionPeriod),
                                                                      description: '${contracts
                                                                          .first
                                                                          .completionPeriod} ${t
                                                                          .translate(
                                                                          i18.common
                                                                              .days)}'),
                                                                  CommonWidgets
                                                                      .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18.workOrder
                                                                          .workStartDate),
                                                                      description: contracts
                                                                          .first
                                                                          .startDate !=
                                                                          null &&
                                                                          contracts.first
                                                                              .startDate !=
                                                                              0
                                                                          ? DateFormats
                                                                          .getFilteredDate(
                                                                          DateTime
                                                                              .fromMillisecondsSinceEpoch(
                                                                              contracts
                                                                                  .first
                                                                                  .startDate ??
                                                                                  0)
                                                                              .toString())
                                                                          : t.translate(
                                                                          i18.common
                                                                              .noValue)),
                                                                  CommonWidgets
                                                                      .getItemWidget(
                                                                      context,
                                                                      title: t.translate(i18.workOrder
                                                                          .workEndDate),
                                                                      description: contracts
                                                                          .first.endDate !=
                                                                          null &&
                                                                          contracts.first
                                                                              .endDate != 0
                                                                          ? DateFormats
                                                                          .getFilteredDate(
                                                                          DateTime
                                                                              .fromMillisecondsSinceEpoch(
                                                                              contracts
                                                                                  .first
                                                                                  .endDate ??
                                                                                  0)
                                                                              .toString())
                                                                          : t.translate(
                                                                          i18.common
                                                                              .noValue)),
                                                                ],
                                                              )
                                                                  : EmptyImage(
                                                                label: t.translate(
                                                                    i18.workOrder
                                                                        .noWorkOrderAssigned),
                                                                align: Alignment.center,
                                                              ),
                                                              Column(
                                                                children: [
                                                                  DigitTextFormField(
                                                                    label: t.translate(i18.workOrder
                                                                        .extensionReqInDays),
                                                                    formControlName:
                                                                    extensionDaysKey,
                                                                    isRequired: true,
                                                                    keyboardType:
                                                                    TextInputType.number,
                                                                    inputFormatter: [
                                                                      FilteringTextInputFormatter
                                                                          .allow(
                                                                          RegExp("[0-9]"))
                                                                    ],
                                                                    validationMessages: {
                                                                      'required': (_) =>
                                                                          t.translate(
                                                                            i18.workOrder
                                                                                .extensionReqInDaysIsRequired,
                                                                          ),
                                                                      'min': (_) =>
                                                                          t.translate(
                                                                            i18.workOrder
                                                                                .extensionReqInDaysMinVal,
                                                                          ),
                                                                    },
                                                                  ),
                                                                  DigitTextFormField(
                                                                    label: t.translate(i18.workOrder
                                                                        .reasonForExtension),
                                                                    formControlName:
                                                                    reasonForExtensionKey,
                                                                    isRequired: true,
                                                                    keyboardType:
                                                                    TextInputType.text,
                                                                    inputFormatter: [
                                                                      FilteringTextInputFormatter
                                                                          .allow(RegExp(
                                                                          "[a-zA-Z0-9 .,\\/\\-_@#\\']"))
                                                                    ],
                                                                    validationMessages: {
                                                                      'required': (_) =>
                                                                          t.translate(
                                                                            i18.workOrder
                                                                                .reasonForExtensionIsRequired,
                                                                          ),
                                                                      'minLength': (_) =>
                                                                          t.translate(
                                                                            i18.workOrder
                                                                                .reasonForExtensionMinChar,
                                                                          ),
                                                                      'maxLength': (_) =>
                                                                          t.translate(
                                                                            i18.workOrder
                                                                                .reasonForExtensionMaxChar,
                                                                          ),
                                                                    },
                                                                  ),
                                                                ],
                                                              ),
                                                              const SizedBox(
                                                                height: 16.0,
                                                              ),
                                                            ]),
                                                      ),
                                                    ]);
                                              } else {
                                                return Container();
                                              }
                                            });
                                      },
                                    );
                                  }),
                              BlocListener<CreateTimeExtensionRequestBloc,
                                  CreateTimeExtensionRequestState>(
                                listener: (context, timeExtensionState) {
                                  timeExtensionState.maybeWhen(orElse: () => false,
                                      loading: () => Loaders.circularLoader(context),
                                      error: (String? error) => Notifiers.getToastMessage(context, error.toString(), 'ERROR'),
                                      loaded: (ContractsModel? contractsModel) =>
                                          context.router
                                              .popAndPush(
                                              SuccessResponseRoute(
                                                  header: t.translate(i18.workOrder
                                                      .timeExtensionRequestedSuccess),
                                                  subHeader: t.translate(
                                                      i18.workOrder.requestID),
                                                  subText:
                                                  contractsModel?.contracts?.first
                                                      .supplementNumber,
                                                  subTitle: t
                                                      .translate(
                                                      i18.workOrder
                                                          .timeExtensionRequestedSuccessSubText),
                                                  backButton:
                                                  true,
                                                  callBack: () =>
                                                      context.router.push(
                                                          const HomeRoute()),
                                                  buttonLabel: t
                                                      .translate(
                                                    i18
                                                        .common
                                                        .backToHome,
                                                  ))));
                                },
                                child: const SizedBox.shrink(),)
                            ],
                          );
                        }
                    );
                  }
              );
            }
        ),
      ),
    );
  }

  FormGroup buildForm(SearchIndividualWorkState contractState) {
    final extensionDays = contractState.mapOrNull(loaded: (value) {
      return widget.isEdit == true ? value.contractsModel?.contracts?.first.additionalDetails?.timeExt : '';
    });
    final extensionReason = contractState.mapOrNull(loaded: (value) {
      return widget.isEdit == true ? value.contractsModel?.contracts?.first.additionalDetails?.timeExtReason : '';
    });
    return fb.group(<String, Object>{
      extensionDaysKey: FormControl<String>(value: extensionDays ?? '', validators: [
        Validators.required,
        Validators.min('1'),
      ]),
      reasonForExtensionKey: FormControl<String>(value: extensionReason ?? '', validators: [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(128)
      ]),
    });
  }
}
