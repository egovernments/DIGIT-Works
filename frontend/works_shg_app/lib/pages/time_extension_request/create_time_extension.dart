// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_scrollable;
import 'package:digit_ui_components/digit_components.dart' as ui_new;
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/time_extension_request/create_time_extension_request.dart';
import '../../blocs/work_orders/search_individual_work.dart';
import '../../models/works/contracts_model.dart';
import '../../router/app_router.dart';
import '../../utils/date_formats.dart';
import '../../widgets/loaders.dart' as shg_loader;

@RoutePage()
class CreateTimeExtensionRequestPage extends StatefulWidget {
  final String? contractNumber;
  final bool? isEdit;

  const CreateTimeExtensionRequestPage({
    super.key,
    @queryParam this.contractNumber,
    @queryParam this.isEdit,
  });

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
          IndividualWorkSearchEvent(
              contractNumber: widget.contractNumber,
              body: widget.isEdit == true
                  ? {"businessService": "CONTRACT-REVISION"}
                  : null),
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
    return PopScope(
      canPop: true,
      onPopInvoked: (value) async {
        if (context.router.currentUrl.contains('isEdit')) {
          context.router.popUntilRouteWithPath('home');
          context.router.push(const MyServiceRequestsRoute());
        } else {
          context.router.popUntilRouteWithPath('home');
          context.router.push(const WorkOrderRoute());
        }
      },
      child: Scaffold(
        backgroundColor: Theme.of(context).colorTheme.generic.background,
        // appBar: customAppBar(),
        // drawer: const MySideBar(),
        // bottomNavigationBar: const SizedBox(
        //   height: 50,
        // child: Align(
        //   alignment: Alignment.bottomCenter,
        //   child:ui_scrollable. PoweredByDigit(
        //        version: Constants.appVersion,
        //       ),
        // ),
        // ),
        body: BlocBuilder<SearchIndividualWorkBloc, SearchIndividualWorkState>(
            builder: (context, contractState) {
          return contractState.maybeWhen(
              orElse: () => const SizedBox.shrink(),
              loading: () => shg_loader.Loaders.circularLoader(context),
              loaded: (ContractsModel? contracts) {
                return ReactiveFormBuilder(
                    form: () => buildForm(contractState),
                    builder: (context, form, child) {
                      return ui_scrollable.ScrollableContent(
                        backgroundColor:
                            Theme.of(context).colorTheme.generic.background,
                        footer: ui_card.DigitCard(
                            margin: EdgeInsets.all(
                                Theme.of(context).spacerTheme.spacer2),
                            cardType: CardType.primary,
                            children: [
                              Button(
                                mainAxisSize: MainAxisSize.max,
                                type: ButtonType.primary,
                                size: ButtonSize.large,
                                onPressed: () {
                                  form.markAllAsTouched(updateParent: false);
                                  if (!form.valid) {
                                    return;
                                  } else if (int.parse(form
                                          .value[extensionDaysKey]
                                          .toString()) >
                                      365) {
                                    Notifiers.getToastMessage(
                                      context,
                                      t.translate(
                                        i18.workOrder.extensionReqInDaysMaxVal,
                                      ),
                                      'ERROR',
                                    );
                                    // Toast.showToast(context,
                                    //     message: t.translate(
                                    //       i18.workOrder
                                    //           .extensionReqInDaysMaxVal,
                                    //     ),
                                    //     type: ToastType.error);

                                    return;
                                  } else {
                                    DateTime endDate =
                                        DateTime.fromMillisecondsSinceEpoch(
                                            contracts?.contracts?.first
                                                    .endDate ??
                                                0);
                                    int extensionDate = endDate
                                        .add(Duration(
                                            days: int.parse(form
                                                .value[extensionDaysKey]
                                                .toString())))
                                        .millisecondsSinceEpoch;
                                    // sorting contracts list based on last modified time
                                    // to send the latest conract data for time extention
                                    contracts?.contracts!.sort((a, b) => b
                                        .auditDetails!.lastModifiedTime!
                                        .compareTo(
                                            a.auditDetails!.lastModifiedTime!));

                                    context
                                        .read<CreateTimeExtensionRequestBloc>()
                                        .add(TimeExtensionRequestEvent(
                                            contractsModel:
                                                contracts?.contracts?.first,
                                            action: widget.isEdit == true
                                                ? 'EDIT'
                                                : 'CREATE',
                                            extensionDate: extensionDate,
                                            isEdit: widget.isEdit ?? false,
                                            reason: form
                                                .value[reasonForExtensionKey]
                                                .toString(),
                                            extensionDays: form
                                                .value[extensionDaysKey]
                                                .toString()));
                                  }
                                },
                                label: t.translate(i18.common.submit),
                              ),
                              const Align(
                                alignment: Alignment.bottomCenter,
                                child: ui_scrollable.PoweredByDigit(
                                  version: Constants.appVersion,
                                ),
                              )
                            ]),
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
                                        shg_loader.Loaders.circularLoader(
                                            context),
                                    initial: () => Container(),
                                    loaded: (ContractsModel? contractsModel) {
                                      final contracts =
                                          contractsModel?.contracts;
                                      if (contracts != null) {
                                        return Column(
                                            crossAxisAlignment:
                                                CrossAxisAlignment.start,
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
                                                        if (context
                                                            .router.currentUrl
                                                            .contains(
                                                                'isEdit')) {
                                                          context.router
                                                              .popUntilRouteWithPath(
                                                                  'home');
                                                          context.router.push(
                                                              const MyServiceRequestsRoute());
                                                        } else {
                                                          context.router
                                                              .popUntilRouteWithPath(
                                                                  'home');
                                                          context.router.push(
                                                              const WorkOrderRoute());
                                                        }
                                                      },
                                                    ),
                                                  ],
                                                ),
                                              ),

                                              ui_card.DigitCard(
                                                margin: EdgeInsets.all(
                                                    Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2),
                                                cardType: CardType.primary,
                                                children: [
                                                  contracts.isNotEmpty
                                                      ? LabelValueList(
                                                          maxLines: 3,
                                                          labelFlex: 5,
                                                          valueFlex: 5,
                                                          items: [
                                                              LabelValuePair(
                                                                  label: t.translate(i18
                                                                      .workOrder
                                                                      .workOrderNo),
                                                                  value: contracts
                                                                          .first
                                                                          .contractNumber ??
                                                                      t.translate(i18
                                                                          .common
                                                                          .noValue)),
                                                              LabelValuePair(
                                                                  label: t.translate(i18
                                                                      .attendanceMgmt
                                                                      .projectId),
                                                                  value: contracts
                                                                          .first
                                                                          .additionalDetails
                                                                          ?.projectId ??
                                                                      t.translate(i18
                                                                          .common
                                                                          .noValue)),
                                                              LabelValuePair(
                                                                  label: t.translate(i18
                                                                      .attendanceMgmt
                                                                      .projectDesc),
                                                                  value: contracts
                                                                          .first
                                                                          .additionalDetails
                                                                          ?.projectDesc ??
                                                                      t.translate(i18
                                                                          .common
                                                                          .noValue)),
                                                              LabelValuePair(
                                                                  label: t.translate(i18
                                                                      .workOrder
                                                                      .completionPeriod),
                                                                  value:
                                                                      '${contracts.first.completionPeriod} ${t.translate(i18.common.days)}'),
                                                              LabelValuePair(
                                                                  label: t.translate(i18
                                                                      .workOrder
                                                                      .workStartDate),
                                                                  value: contracts.first.startDate !=
                                                                              null &&
                                                                          contracts.first.startDate !=
                                                                              0
                                                                      ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contracts.first.startDate ??
                                                                              0)
                                                                          .toString())
                                                                      : t.translate(i18
                                                                          .common
                                                                          .noValue)),
                                                              LabelValuePair(
                                                                  label: t.translate(i18
                                                                      .workOrder
                                                                      .workEndDate),
                                                                  value: contracts.first.endDate !=
                                                                              null &&
                                                                          contracts.first.endDate !=
                                                                              0
                                                                      ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(contracts.first.endDate ??
                                                                              0)
                                                                          .toString())
                                                                      : t.translate(i18
                                                                          .common
                                                                          .noValue)),
                                                            ])
                                                      : EmptyImage(
                                                          label: t.translate(i18
                                                              .workOrder
                                                              .noWorkOrderAssigned),
                                                          align:
                                                              Alignment.center,
                                                        ),
// old
                                                  // DigitTextFormField(
                                                  //   label: t.translate(i18
                                                  //       .workOrder
                                                  //       .extensionReqInDays),
                                                  //   formControlName:
                                                  //       extensionDaysKey,
                                                  //   isRequired: true,
                                                  //   keyboardType:
                                                  //       TextInputType.number,
                                                  //   inputFormatters: [
                                                  //     FilteringTextInputFormatter
                                                  //         .allow(
                                                  //             RegExp("[0-9]"))
                                                  //   ],
                                                  //   validationMessages: {
                                                  //     'required': (_) =>
                                                  //         t.translate(
                                                  //           i18.workOrder
                                                  //               .extensionReqInDaysIsRequired,
                                                  //         ),
                                                  //     'min': (_) => t.translate(
                                                  //           i18.workOrder
                                                  //               .extensionReqInDaysMinVal,
                                                  //         ),
                                                  //     // 'max':(_)=>t.translate(
                                                  //     //   i18.workOrder
                                                  //     //       .extensionReqInDaysMaxVal,
                                                  //     // ),
                                                  //   },
                                                  // ),
// end
                                                  ui_new.LabeledField(
                                                    isRequired: true,
                                                    label: t.translate(i18
                                                        .workOrder
                                                        .extensionReqInDays),
                                                    child: ReactiveWrapperField(
                                                      validationMessages: {
                                                        'required': (error) =>
                                                            t.translate(
                                                              i18
                                                                  .workOrder
                                                                  //.extensionReqInDaysIsRequired
                                                                  .reasonForExtensionIsRequired,
                                                            ),
                                                        'min': (error) =>
                                                            t.translate(
                                                              i18.workOrder
                                                                  .extensionReqInDaysMinVal,
                                                            ),
                                                      },
                                                      formControlName:
                                                          extensionDaysKey,
                                                      showErrors: (control) =>
                                                          control.invalid &&
                                                          control.touched,
                                                      builder: (field) {
                                                        return DigitTextFormInput(
                                                          isRequired: true,
                                                          onChange: (value) {
                                                            field.control
                                                                .markAsTouched();

                                                            form
                                                                .control(
                                                                    extensionDaysKey)
                                                                .value = value;
                                                          },
                                                          errorMessage:
                                                              field.errorText,
                                                          controller:
                                                              TextEditingController()
                                                                ..text = form
                                                                    .control(
                                                                        extensionDaysKey)
                                                                    .value,
                                                          keyboardType:
                                                              TextInputType
                                                                  .number,
                                                          inputFormatters: [
                                                            FilteringTextInputFormatter
                                                                .allow(RegExp(
                                                                    "[0-9]"))
                                                          ],
                                                        );
                                                      },
                                                    ),
                                                  ),

//old
                                                  // DigitTextFormField(
                                                  // label: t.translate(i18
                                                  //     .workOrder
                                                  //     .reasonForExtension),
                                                  //   formControlName:
                                                  //       reasonForExtensionKey,
                                                  //   isRequired: true,
                                                  //   keyboardType:
                                                  //       TextInputType.text,
                                                  //   inputFormatters: [
                                                  // FilteringTextInputFormatter
                                                  //     .allow(RegExp(
                                                  //         "[a-zA-Z0-9 .,\\/\\-_@#\\']"))
                                                  //   ],
                                                  // validationMessages: {
                                                  //   'required': (_) =>
                                                  //       t.translate(
                                                  //         i18.workOrder

                                                  //             //.reasonForExtensionIsRequired
                                                  //             .extensionReqInDaysIsRequired
                                                  //             ,
                                                  //       ),
                                                  //   'minLength': (_) =>
                                                  //       t.translate(
                                                  //         i18.workOrder
                                                  //             .reasonForExtensionMinChar,
                                                  //       ),
                                                  //   'maxLength': (_) =>
                                                  //       t.translate(
                                                  //         i18.workOrder
                                                  //             .reasonForExtensionMaxChar,
                                                  //       ),
                                                  // },
                                                  // ),
                                                  //end

                                                  ui_new.LabeledField(
                                                    isRequired: true,
                                                    label: t.translate(i18
                                                        .workOrder
                                                        .reasonForExtension),
                                                    child: ReactiveWrapperField(
                                                      validationMessages: {
                                                        'required': (_) =>
                                                            t.translate(
                                                              i18
                                                                  .workOrder

                                                                  //.reasonForExtensionIsRequired
                                                                  .extensionReqInDaysIsRequired,
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
                                                      formControlName:
                                                          reasonForExtensionKey,
                                                      showErrors: (control) =>
                                                          control.invalid &&
                                                          control.touched,
                                                      builder: (field) {
                                                        return DigitTextFormInput(
                                                          isRequired: true,
                                                          onChange: (value) {
                                                            field.control
                                                                .markAsTouched();

                                                            form
                                                                .control(
                                                                    reasonForExtensionKey)
                                                                .value = value;
                                                          },
                                                          errorMessage:
                                                              field.errorText,
                                                          controller:
                                                              TextEditingController()
                                                                ..text = form
                                                                    .control(
                                                                        reasonForExtensionKey)
                                                                    .value,
                                                          keyboardType:
                                                              TextInputType
                                                                  .text,
                                                          inputFormatters: [
                                                            FilteringTextInputFormatter
                                                                .allow(RegExp(
                                                                    "[a-zA-Z0-9 .,\\/\\-_@#\\']"))
                                                          ],
                                                        );
                                                      },
                                                    ),
                                                  ),

                                                  // const SizedBox(
                                                  //   height: 16.0,
                                                  // ),
                                                ],
                                              ),
                                            ]);
                                      } else {
                                        return const SizedBox.shrink();
                                      }
                                    });
                              },
                            );
                          }),
                          BlocListener<CreateTimeExtensionRequestBloc,
                              CreateTimeExtensionRequestState>(
                            listener: (context, timeExtensionState) {
                              timeExtensionState.maybeWhen(
                                  orElse: () => false,
                                  loading: () {
                                    Navigator.of(
                                      context,
                                      rootNavigator: true,
                                    ).popUntil(
                                      (route) => route is! PopupRoute,
                                    );
                                    shg_loader.Loaders.showLoadingDialog(
                                    label:t.translate(i18.common.loading),
                                        context);
                                  },
                                  error: (String? error) {
                                    Navigator.of(
                                      context,
                                      rootNavigator: true,
                                    ).popUntil(
                                      (route) => route is! PopupRoute,
                                    );
                                    Notifiers.getToastMessage(
                                        context, error.toString(), 'ERROR');
                                    // Toast.showToast(context,
                                    //     message:
                                    //         t.translate(error.toString()),
                                    //     type: ToastType.error),
                                  },
                                  loaded: (ContractsModel? contractsModel) {
                                    Navigator.of(
                                      context,
                                      rootNavigator: true,
                                    ).popUntil(
                                      (route) => route is! PopupRoute,
                                    );
                                    if (widget.isEdit == true) {
                                      Notifiers.getToastMessage(
                                          context,
                                          t.translate(i18.workOrder
                                              .timeExtensionRequestedUpdatedSuccessfully),
                                          "SUCCESS");
                                      // Toast.showToast(context,
                                      //     message: t.translate(i18.workOrder
                                      //         .timeExtensionRequestedUpdatedSuccessfully),
                                      //     type: ToastType.success);
                                      context.router.maybePopTop();
                                    } else {
                                      context.router.popAndPush(
                                          SuccessResponseRoute(
                                              header: t.translate(i18.workOrder
                                                  .timeExtensionRequestedSuccess),
                                              subHeader: t.translate(
                                                  i18.workOrder.requestID),
                                              subText: contractsModel?.contracts
                                                  ?.first.supplementNumber,
                                              subTitle: t.translate(i18
                                                  .workOrder
                                                  .timeExtensionRequestedSuccessSubText),
                                              backButton: true,
                                              callBack: () => context.router
                                                  .push(const HomeRoute()),
                                              buttonLabel: t.translate(
                                                i18.common.backToHome,
                                              )));
                                    }
                                  });
                            },
                            child: const SizedBox.shrink(),
                          )
                        ],
                      );
                    });
              });
        }),
      ),
    );
  }

  FormGroup buildForm(SearchIndividualWorkState contractState) {
    final extensionDays = contractState.mapOrNull(loaded: (value) {
      return widget.isEdit == true
          ? value.contractsModel?.contracts?.first.additionalDetails?.timeExt
          : '';
    });
    final extensionReason = contractState.mapOrNull(loaded: (value) {
      return widget.isEdit == true
          ? value
              .contractsModel?.contracts?.first.additionalDetails?.timeExtReason
          : '';
    });
    return fb.group(<String, Object>{
      extensionDaysKey:
          FormControl<String>(value: extensionDays ?? '', validators: [
        Validators.required,
        Validators.min('1'),
        // Validators.max("365")
        //    Validators.required,
        // Validators.min<int>(1),
        // Validators.max<int>(365)
      ]),
      reasonForExtensionKey: FormControl<String>(
          value: extensionReason ?? '',
          validators: [
            Validators.required,
            Validators.minLength(2),
            Validators.maxLength(128)
          ]),
    });
  }
}
