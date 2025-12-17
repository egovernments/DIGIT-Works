import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/my_bills/my_bills_inbox_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/date_formats.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';
import 'package:works_shg_app/widgets/work_details_card.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/my_bills/search_my_bills.dart';
import '../../models/my_bills/my_bills_model.dart';
import '../../utils/constants.dart';

@RoutePage()
class MyBillsPage extends StatefulWidget {
  const MyBillsPage({super.key});

  @override
  State<StatefulWidget> createState() {
    return _MyBillsPage();
  }
}

class _MyBillsPage extends State<MyBillsPage> {
  bool hasLoaded = true;
  bool inProgress = true;
  List<Map<String, dynamic>> billList = [];
  List<MyBillModel> bills = [];

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<MyBillInboxBloc>().add(
          const BillsInboxEvent(),
        );
    context.read<SearchMyBillsBloc>().add(
          const MyBillsSearchEvent(),
        );
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
        backgroundColor: Theme.of(context).colorTheme.generic.background,
        // appBar: customAppBar(),
        // drawer: const MySideBar(),
        bottomNavigationBar: BlocBuilder<SearchMyBillsBloc, SearchMyBillsState>(
            builder: (context, state) {
          return state.maybeWhen(
              orElse: () => const SizedBox.shrink(),
              loading: () => shg_loader.Loaders.circularLoader(context),
              loaded: (MyBillsListModel? myBillsModel) {
                return billList.length < 2
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
        }),
        body: SingleChildScrollView(
          child: BlocBuilder<LocalizationBloc, LocalizationState>(
              builder: (context, localState) {
            return BlocBuilder<MyBillInboxBloc, MyBillsInboxState>(
                builder: (context, billInboxState) {
              return billInboxState.maybeWhen(
                  orElse: () => const SizedBox.shrink(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (String? rejectCode, String? approvedCode) =>
                      BlocListener<SearchMyBillsBloc, SearchMyBillsState>(
                        listener: (context, state) {
                          state.maybeWhen(
                              orElse: () => false,
                              loading: () =>
                                  shg_loader.Loaders.circularLoader(context),
                              error: (String? error) {
                                Notifiers.getToastMessage(context,
                                    t.translate(error.toString()), 'ERROR');
                                // Toast.showToast(context,
                                //     message: t.translate(error.toString()),
                                //     type: ToastType.error);
                              },
                              loaded: (MyBillsListModel? myBillsModel) {
                                bills = List<MyBillModel>.from(myBillsModel!
                                    .bills!
                                    .where((e) => e.bill != null));
                                bills.sort((a, b) => b
                                    .bill!.auditDetails!.lastModifiedTime!
                                    .compareTo(a
                                        .bill!.auditDetails!.lastModifiedTime!
                                        .toInt()));
                                billList = bills.map((e) {
                                  num deduction = 0;

                                  for (var billDetail
                                      in e.bill?.billDetails ?? []) {
                                    List<PayableLineItems>? payableLineItems =
                                        billDetail.payableLineItems;
                                    if (payableLineItems != null &&
                                        payableLineItems.isNotEmpty) {
                                      for (var payableLineItem
                                          in payableLineItems) {
                                        if (payableLineItem.headCode == 'LC') {
                                          num amount =
                                              payableLineItem.amount ?? 0;
                                          deduction += amount;
                                        }
                                      }
                                    }
                                  }
                                  if (e.bill?.businessService ==
                                      Constants.myBillsWageType) {
                                    num deduction = 0;
                                    for (var billDetail
                                        in e.bill?.billDetails ?? []) {
                                      List<PayableLineItems>? payableLineItems =
                                          billDetail.payableLineItems;
                                      if (payableLineItems != null &&
                                          payableLineItems.isNotEmpty) {
                                        for (var lineItem in payableLineItems) {
                                          if (lineItem.type == 'DEDUCTION' &&
                                              lineItem.status == 'ACTIVE') {
                                            num amount = lineItem.amount ?? 0;
                                            deduction += amount;
                                          }
                                        }
                                      }
                                    }
                                    return {
                                      i18.myBills.billType:
                                          'EXP_BILL_TYPE_${e.bill?.businessService ?? 'NA'}',
                                      i18.myBills.billNumber:
                                          e.bill?.billNumber,
                                      i18.myBills.billDate: e.bill?.billDate !=
                                              null
                                          ? DateFormats.getDateFromTimestamp(
                                              e.bill?.billDate ?? 0)
                                          : i18.common.noValue,
                                      i18.workOrder.workOrderNo:
                                          e.contractNumber ??
                                              i18.common.noValue,
                                      i18.attendanceMgmt.projectDesc: e
                                              .bill
                                              ?.additionalDetails
                                              ?.projectDesc ??
                                          i18.common.noValue,
                                      i18.attendanceMgmt.musterRollId:
                                          e.musterRollNumber ??
                                              i18.common.noValue,
                                      i18.attendanceMgmt.musterRollPeriod: e
                                                  .bill?.fromPeriod !=
                                              null
                                          ? '${DateFormats.getDateFromTimestamp(e.bill?.fromPeriod ?? 0)} - ${DateFormats.getDateFromTimestamp(e.bill?.toPeriod ?? 0)}'
                                          : i18.common.noValue,
                                      i18.myBills.netPayable:
                                          '₹ ${((e.bill?.totalAmount ?? 0) - deduction).ceil()}',
                                      i18.common.status:
                                          'BILL_STATE_${e.bill?.wfStatus ?? 'NA'}',
                                      Constants.activeInboxStatus:
                                          e.bill?.wfStatus == approvedCode
                                              ? 'true'
                                              : e.bill?.wfStatus == rejectCode
                                                  ? 'false'
                                                  : 'none'
                                    };
                                  } else if (e.bill?.businessService ==
                                      Constants.myBillsPurchaseType) {
                                    num deduction = 0;

                                    for (var billDetail
                                        in e.bill?.billDetails ?? []) {
                                      List<BillLineItems>? lineItems =
                                          billDetail.lineItems;
                                      if (lineItems != null &&
                                          lineItems.isNotEmpty) {
                                        for (var lineItem in lineItems) {
                                          if (lineItem.type == 'DEDUCTION' &&
                                              lineItem.status == 'ACTIVE') {
                                            num amount = lineItem.amount ?? 0;
                                            deduction += amount;
                                          }
                                        }
                                      }
                                    }
                                    return {
                                      i18.myBills.billType:
                                          'EXP_BILL_TYPE_${e.bill?.businessService ?? 'NA'}',
                                      i18.myBills.billNumber:
                                          e.bill?.billNumber,
                                      i18.myBills.billDate: e.bill?.billDate !=
                                              null
                                          ? DateFormats.getDateFromTimestamp(
                                              e.bill?.billDate ?? 0)
                                          : i18.common.noValue,
                                      i18.workOrder.workOrderNo:
                                          e.contractNumber ??
                                              i18.common.noValue,
                                      i18.attendanceMgmt.projectDesc: e
                                              .bill
                                              ?.additionalDetails
                                              ?.projectDesc ??
                                          i18.common.noValue,
                                      i18.myBills.invoiceId: e.bill
                                          ?.additionalDetails?.invoiceNumber,
                                      i18.myBills.invoiceDate: e
                                                  .bill
                                                  ?.additionalDetails
                                                  ?.invoiceDate !=
                                              null
                                          ? DateFormats.getDateFromTimestamp(e
                                                  .bill
                                                  ?.additionalDetails
                                                  ?.invoiceDate ??
                                              0)
                                          : i18.common.noValue,
                                      // i18.myBills.payeeName:
                                      //     e.bill?.payer?.identifier,
                                      i18.myBills.netPayable:
                                          '₹ ${((e.bill?.totalAmount ?? 0) - deduction).ceil()}',
                                      i18.common.status:
                                          'BILL_STATE_${e.bill?.wfStatus ?? 'NA'}',
                                      Constants.activeInboxStatus:
                                          e.bill?.wfStatus == approvedCode
                                              ? 'true'
                                              : e.bill?.wfStatus == rejectCode
                                                  ? 'false'
                                                  : 'none'
                                    };
                                  } else {
                                    num deduction = 0;
                                    for (var billDetail
                                        in e.bill?.billDetails ?? []) {
                                      List<PayableLineItems>? payableLineItems =
                                          billDetail.payableLineItems;
                                      if (payableLineItems != null &&
                                          payableLineItems.isNotEmpty) {
                                        for (var lineItem in payableLineItems) {
                                          if (lineItem.type == 'DEDUCTION' &&
                                              lineItem.status == 'ACTIVE') {
                                            num amount = lineItem.amount ?? 0;
                                            deduction += amount;
                                          }
                                        }
                                      }
                                    }
                                    return {
                                      i18.myBills.billType:
                                          'EXP_BILL_TYPE_${e.bill?.businessService ?? 'NA'}',
                                      i18.myBills.billNumber:
                                          e.bill?.billNumber,
                                      i18.myBills.billDate: e.bill?.billDate !=
                                              null
                                          ? DateFormats.getDateFromTimestamp(
                                              e.bill?.billDate ?? 0)
                                          : t.translate(i18.common.noValue),
                                      i18.workOrder.workOrderNo:
                                          e.contractNumber ??
                                              i18.common.noValue,
                                      i18.attendanceMgmt.projectDesc: e
                                              .bill
                                              ?.additionalDetails
                                              ?.projectDesc ??
                                          i18.common.noValue,
                                      // i18.myBills.payeeName:
                                      //     e.bill?.payer?.identifier,
                                      i18.myBills.netPayable:
                                          '₹ ${((e.bill?.totalAmount ?? 0) - deduction).ceil()}',
                                      i18.common.status:
                                          'BILL_STATE_${e.bill?.wfStatus ?? 'NA'}',
                                      Constants.activeInboxStatus:
                                          e.bill?.wfStatus == approvedCode
                                              ? 'true'
                                              : e.bill?.wfStatus == rejectCode
                                                  ? 'false'
                                                  : 'none'
                                    };
                                  }
                                }).toList();
                              });
                        },
                        child:
                            BlocBuilder<SearchMyBillsBloc, SearchMyBillsState>(
                                builder: (context, searchState) {
                          return searchState.maybeWhen(
                              orElse: () => Container(),
                              loading: () =>
                                  shg_loader.Loaders.circularLoader(context),
                              loaded: (MyBillsListModel? myBillsModel) =>
                                  Column(
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
                                            children: [
                                              BackNavigationButton(
                                                backNavigationButtonThemeData:
                                                    const BackNavigationButtonThemeData()
                                                        .copyWith(
                                                            context: context,
                                                            backButtonIcon:
                                                                Icon(
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
                                                              color: Theme.of(
                                                                      context)
                                                                  .colorTheme
                                                                  .primary
                                                                  .primary2,
                                                            )),
                                                backButtonText:
                                                    AppLocalizations.of(context)
                                                        .translate(
                                                            i18.common.back),
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
                                                padding:
                                                     EdgeInsets.only(left:Theme.of(context).spacerTheme.spacer4,bottom: Theme.of(context).spacerTheme.spacer4,top: 0.0,right: 0.0),
                                                child: DigitTextBlock(
                                                  heading:
                                                      '${AppLocalizations.of(context).translate(i18.home.myBills)} (${billList.length})',
                                                ),
                                              ),
                                              billList.isNotEmpty
                                                  ? WorkDetailsCard(
                                                      billList,
                                                    )
                                                  : EmptyImage(
                                                      label:
                                                          AppLocalizations.of(
                                                                  context)
                                                              .translate(i18
                                                                  .myBills
                                                                  .noBills),
                                                      align: Alignment.center,
                                                    ),
                                              const SizedBox(
                                                height: 16.0,
                                              ),
                                              billList.length >= 2
                                                  ? const Align(
                                                      alignment: Alignment
                                                          .bottomCenter,
                                                      child: PoweredByDigit(
                                                        version: Constants
                                                            .appVersion,
                                                      ),
                                                    )
                                                  : const SizedBox.shrink()
                                            ]),
                                      ]));
                        }),
                      ));
            });
          }),
        ));
  }
}
