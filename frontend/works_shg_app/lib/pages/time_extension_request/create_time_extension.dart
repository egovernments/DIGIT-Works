import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/work_orders/work_order_pdf.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
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
  const CreateTimeExtensionRequestPage(
      {super.key, @queryParam this.contractNumber = 'contractNumber'});
  @override
  State<StatefulWidget> createState() {
    return _CreateTimeExtensionRequestPage();
  }
}

class _CreateTimeExtensionRequestPage
    extends State<CreateTimeExtensionRequestPage> {
  bool hasLoaded = true;
  bool inProgress = true;
  List<Map<String, dynamic>> workOrderList = [];
  String extensionDaysKey = 'extensionDays';
  String reasonForExtensionKey = 'reasonForExtension';

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    workOrderList = [];
    context.read<SearchIndividualWorkBloc>().add(
          IndividualWorkSearchEvent(contractNumber: widget.contractNumber),
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
        context.router.popUntilRouteWithPath('home');
        context.router.push(const WorkOrderRoute());
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
        body: ScrollableContent(
          children: [
            BlocBuilder<LocalizationBloc, LocalizationState>(
                builder: (context, localState) {
              return BlocConsumer<SearchIndividualWorkBloc,
                  SearchIndividualWorkState>(
                listener: (context, state) {
                  state.maybeWhen(
                      orElse: () => false,
                      initial: () => false,
                      loading: () => shg_loader.Loaders.circularLoader(context),
                      error: (String? error) => Notifiers.getToastMessage(
                          context, error.toString(), 'ERROR'),
                      loaded: (ContractsModel? contracts) {
                        if (contracts?.contracts != null) {
                          workOrderList = contracts!.contracts!
                              .map((e) => {
                                    'cardDetails': {
                                      i18.workOrder.workOrderNo:
                                          e.contractNumber ?? 'NA',
                                      i18.attendanceMgmt.projectId:
                                          e.additionalDetails?.projectId ??
                                              'NA',
                                      i18.attendanceMgmt.projectDesc:
                                          e.additionalDetails?.projectDesc ??
                                              'NA',
                                      i18.workOrder.completionPeriod:
                                          '${e.completionPeriod} ${t.translate(i18.common.days)}',
                                      i18.workOrder.workStartDate: e.startDate != null &&
                                              e.startDate != 0
                                          ? DateFormats.getFilteredDate(DateTime
                                                  .fromMillisecondsSinceEpoch(
                                                      e.startDate ?? 0)
                                              .toString())
                                          : 'NA',
                                      i18.workOrder.workEndDate: e.endDate !=
                                                  null &&
                                              e.endDate != 0
                                          ? DateFormats.getFilteredDate(DateTime
                                                  .fromMillisecondsSinceEpoch(
                                                      e.endDate ?? 0)
                                              .toString())
                                          : 'NA',
                                    },
                                    'payload': e.toMap()
                                  })
                              .toList();
                        }
                      });
                },
                builder: (context, searchState) {
                  return searchState.maybeWhen(
                      orElse: () => Container(),
                      loading: () => shg_loader.Loaders.circularLoader(context),
                      initial: () => Container(),
                      loaded: (ContractsModel? contractsModel) {
                        final contracts = contractsModel?.contracts;
                        if (contracts != null) {
                          return Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Back(
                                      backLabel: AppLocalizations.of(context)
                                          .translate(i18.common.back),
                                      callback: () {
                                        context.router
                                            .popUntilRouteWithPath('home');
                                        context.router
                                            .push(const WorkOrderRoute());
                                      },
                                    ),
                                    CommonWidgets.downloadButton(
                                        AppLocalizations.of(context)
                                            .translate(i18.common.download),
                                        () {
                                      context.read<WorkOrderPDFBloc>().add(
                                          PDFEventWorkOrder(
                                              contractId: widget.contractNumber,
                                              tenantId:
                                                  contracts.first.tenantId));
                                    })
                                  ],
                                ),
                                DigitCard(
                                  padding: const EdgeInsets.all(8.0),
                                  child: Column(
                                      mainAxisAlignment: MainAxisAlignment.start,
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        contracts.isNotEmpty
                                            ? Column(
                                                children: [
                                                  CommonWidgets.getItemWidget(context,
                                                      title:  t.translate(i18.workOrder.workOrderNo),
                                                  description: contracts.first.contractNumber ?? t.translate(i18.common.noValue)),
                                                  CommonWidgets.getItemWidget(context,
                                                      title:  t.translate(i18.attendanceMgmt.projectId),
                                                      description: contracts.first.additionalDetails?.projectId ?? t.translate(i18.common.noValue)),
                                                  CommonWidgets.getItemWidget(context,
                                                      title:  t.translate(i18.attendanceMgmt.projectDesc),
                                                      description: contracts.first.additionalDetails?.projectDesc ?? t.translate(i18.common.noValue)),
                                                  CommonWidgets.getItemWidget(context,
                                                      title:  t.translate(i18.workOrder.completionPeriod),
                                                      description: '${contracts.first.completionPeriod} ${t.translate(i18.common.days)}' ?? t.translate(i18.common.noValue)),
                                                  CommonWidgets.getItemWidget(context,
                                                      title:  t.translate(i18.workOrder.workStartDate),
                                                      description: contracts.first.startDate != null &&
                                                          contracts.first.startDate != 0
                                                          ? DateFormats.getFilteredDate(DateTime
                                                          .fromMillisecondsSinceEpoch(
                                                          contracts.first.startDate ?? 0)
                                                          .toString()) : t.translate(i18.common.noValue)),
                                                  CommonWidgets.getItemWidget(context,
                                                      title:  t.translate(i18.workOrder.workEndDate),
                                                      description: contracts.first.endDate != null &&
                                                          contracts.first.endDate != 0
                                                          ? DateFormats.getFilteredDate(DateTime
                                                          .fromMillisecondsSinceEpoch(
                                                          contracts.first.endDate ?? 0)
                                                          .toString()) : t.translate(i18.common.noValue)),
                                                ],
                                              )
                                            : EmptyImage(
                                                label: t.translate(i18.workOrder
                                                    .noWorkOrderAssigned),
                                                align: Alignment.center,
                                              ),
                                        ReactiveFormBuilder(
                                            form: buildForm,
                                            builder: (context, form, child) {
                                              return Column(
                                                children: [
                                                  DigitTextFormField(
                                                    label: i18.workOrder
                                                        .extensionReqInDays,
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
                                                      'min': (_) => t.translate(
                                                            i18.workOrder
                                                                .extensionReqInDaysMinVal,
                                                          ),
                                                    },
                                                  ),
                                                  DigitTextFormField(
                                                    label: i18.workOrder
                                                        .reasonForExtension,
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
                                                  Center(
                                                    child: DigitElevatedButton(
                                                      onPressed: () {
                                                        form.markAllAsTouched(
                                                            updateParent:
                                                                false);
                                                        if (!form.valid)
                                                          return;
                                                        else {
                                                          context.router.popAndPush(
                                                              SuccessResponseRoute(
                                                                  header: t.translate(i18
                                                                      .workOrder
                                                                      .timeExtensionRequestedSuccess),
                                                                  subHeader: t.translate(i18
                                                                      .workOrder
                                                                      .requestID),
                                                                  subText:
                                                                      'RW/2023-24/000102',
                                                                  subTitle: t.translate(i18
                                                                      .workOrder
                                                                      .timeExtensionRequestedSuccessSubText),
                                                                  backButton:
                                                                      true,
                                                                  callBack: () =>
                                                                      context
                                                                          .router
                                                                          .push(
                                                                              const HomeRoute()),
                                                                  buttonLabel: t
                                                                      .translate(
                                                                    i18.common
                                                                        .backToHome,
                                                                  )));
                                                        }
                                                      },
                                                      child: Center(
                                                          child: Text(
                                                              t.translate(i18
                                                                  .common
                                                                  .submit))),
                                                    ),
                                                  )
                                                ],
                                              );
                                            }),
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
          ],
        ),
      ),
    );
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        extensionDaysKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.min('1'),
        ]),
        reasonForExtensionKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
      });
}
