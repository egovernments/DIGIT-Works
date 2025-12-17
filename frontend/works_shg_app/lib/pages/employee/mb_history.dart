// import 'package:digit_components/theme/colors.dart';
// import 'package:digit_components/theme/digit_theme.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/digit_divider.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/bottom_sheet.dart';
import 'package:digit_ui_components/widgets/molecules/digit_card.dart';
import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/blocs/muster_rolls/get_muster_workflow.dart';
import 'package:works_shg_app/models/muster_rolls/muster_workflow_model.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../../blocs/employee/mb/mb_detail_view.dart';
import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../utils/employee/mb/mb_logic.dart';
import '../../widgets/mb/float_action_card.dart';
import '../../widgets/mb/work_flow_button_list.dart';
import 'mb_inbox.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

@RoutePage()
class MBHistoryBookPage extends StatefulWidget {
  final String contractNumber;
  final String mbNumber;
  final String? tenantId;
  final MBScreen type;
  const MBHistoryBookPage(
      {super.key,
      required this.contractNumber,
      required this.mbNumber,
      this.tenantId,
      required this.type});

  @override
  State<MBHistoryBookPage> createState() => _MBHistoryBookPageState();
}

class _MBHistoryBookPageState extends State<MBHistoryBookPage> {
  final ScrollController _scrollController = ScrollController();
  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocBuilder<LocalizationBloc, LocalizationState>(
      builder: (context, state) {
        return BlocBuilder<MeasurementDetailBloc, MeasurementDetailState>(
          builder: (context, state) {
            return state.maybeMap(
              orElse: () {
                return const SizedBox.shrink();
              },
              loaded: (value) {
                final k = value.data
                    .where((element) => element.wfStatus == "APPROVED")
                    .toList();
                return Scaffold(
                  backgroundColor:
                      Theme.of(context).colorTheme.generic.background,
                  // appBar: customAppBar(),
                  // drawer: const MySideBar(),
                  body: Stack(
                    children: [
                      CustomScrollView(
                        controller: _scrollController,
                        slivers: [
                          SliverPersistentHeader(
                            pinned: true,
                            delegate: MyHeaderDelegate(
                              child: Container(
                                color: Theme.of(context)
                                    .colorTheme
                                    .generic
                                    .background,
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.start,
                                      crossAxisAlignment:
                                          CrossAxisAlignment.center,
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
                                          child: BackNavigationButton(
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
                                              context.router.maybePopTop();
                                            },
                                          ),
                                        ),
                                      ],
                                    ),
                                    Padding(
                                      padding: EdgeInsets.only(
                                          left: Theme.of(context)
                                              .spacerTheme
                                              .spacer4,
                                          top: 0.0),
                                      child: DigitTextBlock(
                                        heading: t.translate(
                                            i18.measurementBook.mbHistory),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                              height: kIsWeb ? 120 : 120,
                            ),
                          ),
                          SliverList(
                            delegate: SliverChildBuilderDelegate(
                              (BuildContext context, int index) {
                                final adjustedIndex =
                                    widget.type == MBScreen.update
                                        ? index
                                        : index;
                                if (adjustedIndex <= k.length) {
                                  if ((k[adjustedIndex].musterRollNumber ==
                                          null ||
                                      k[adjustedIndex]
                                              .musterRollNumber
                                              .toString() ==
                                          "")) {
                                    return DigitCard(
                                      margin: EdgeInsets.all(Theme.of(context)
                                          .spacerTheme
                                          .spacer2),
                                      cardType: CardType.primary,
                                      children: [
                                        LabelValueList(
                                            // padding: const EdgeInsets.only(
                                            //   left: 8.0,
                                            //   bottom: 8.0,
                                            //   right: 4.0,
                                            //   top: 8.0,
                                            // ),
                                            heading:
                                                "${DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(k[adjustedIndex].startDate!))} - ${DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(k[adjustedIndex].endDate!))}",
                                            maxLines: 3,
                                            labelFlex: 5,
                                            valueFlex: 5,
                                            items: [
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbNumber),
                                                  value: k[adjustedIndex]
                                                          .mbNumber ??
                                                      ""),
                                              LabelValuePair(
                                                  label: t.translate(
                                                      i18.common.date),
                                                  value: DateFormat(
                                                          'dd/MM/yyyy')
                                                      .format(DateTime
                                                          .fromMillisecondsSinceEpoch(
                                                              k[adjustedIndex]
                                                                  .entryDate!))),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbAmount),
                                                  value: k[adjustedIndex]
                                                              .totalAmount !=
                                                          null
                                                      ? double.parse(
                                                              (k[adjustedIndex]
                                                                      .totalAmount!)
                                                                  .toString())
                                                          .toStringAsFixed(2)
                                                      : '0.00'),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbStatus),
                                                  value: k[adjustedIndex]
                                                          .wfStatus ??
                                                      ""),
                                            ]),
                                      ],

                                      // showSla: false, showStatus: true, status: k[adjustedIndex].wfStatus??'',
                                    );
                                  } else {
                                    return DigitCard(
                                      margin: EdgeInsets.all(Theme.of(context)
                                          .spacerTheme
                                          .spacer2),
                                      cardType: CardType.primary,
                                      children: [
                                        LabelValueList(
                                            // padding: const EdgeInsets.only(
                                            //   left: 8.0,
                                            //   bottom: 8.0,
                                            //   right: 4.0,
                                            //   top: 8.0,
                                            // ),
                                            heading:
                                                "${DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(k[adjustedIndex].startDate!))} - ${DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(k[adjustedIndex].endDate!))}",
                                            maxLines: 3,
                                            labelFlex: 5,
                                            valueFlex: 5,
                                            items: [
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbNumber),
                                                  value: k[adjustedIndex]
                                                          .mbNumber ??
                                                      ""),
                                              LabelValuePair(
                                                  label: t.translate(
                                                      i18.common.date),
                                                  value: DateFormat(
                                                          'dd/MM/yyyy')
                                                      .format(DateTime
                                                          .fromMillisecondsSinceEpoch(
                                                              k[adjustedIndex]
                                                                  .entryDate!))),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbAmount),
                                                  value: k[adjustedIndex]
                                                              .totalAmount !=
                                                          null
                                                      ? double.parse(
                                                              (k[adjustedIndex]
                                                                      .totalAmount!)
                                                                  .toString())
                                                          .toStringAsFixed(2)
                                                      : '0.00'),
                                              LabelValuePair(
                                                  label: t.translate(i18
                                                      .measurementBook
                                                      .mbStatus),
                                                  value: k[adjustedIndex]
                                                          .wfStatus ??
                                                      ""),
                                            ]),
                                        Button(
                                            suffixIcon:
                                                Icons.arrow_forward_outlined,
                                            label: t
                                                .translate(i18.home.musterRoll),
                                            onPressed: () {
                                              context.router
                                                  .push(MBMusterScreenRoute(
                                                mbNumber: widget.mbNumber,
                                                musterRollNumber:
                                                    k[adjustedIndex]
                                                        .musterRollNumber
                                                        .toString(),
                                                tenantId: widget.tenantId!,
                                              ));
                                            },
                                            type: ButtonType.link,
                                            size: ButtonSize.medium),
                                      ],

                                      // showSla: false, showStatus: true, status: k[adjustedIndex].wfStatus??'',
                                    );
                                  }

                                  // return DigitCard(
                                  //   margin:  EdgeInsets.all(
                                  //      Theme.of(context).spacerTheme.spacer2),
                                  //   cardType: CardType.primary,
                                  //   children: [
                                  //     LabelValueList(
                                  //         // padding: const EdgeInsets.only(
                                  //         //   left: 8.0,
                                  //         //   bottom: 8.0,
                                  //         //   right: 4.0,
                                  //         //   top: 8.0,
                                  //         // ),
                                  //         heading:
                                  //             "${DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(k[adjustedIndex].startDate!))} - ${DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(k[adjustedIndex].endDate!))}",
                                  //         maxLines: 3,
                                  //         labelFlex: 5,
                                  //         valueFlex: 5,
                                  //         items: [
                                  //           LabelValuePair(
                                  //               label: t.translate(i18
                                  //                   .measurementBook.mbNumber),
                                  //               value:
                                  //                   k[adjustedIndex].mbNumber ??
                                  //                       ""),
                                  //           LabelValuePair(
                                  //               label: t
                                  //                   .translate(i18.common.date),
                                  //               value: DateFormat('dd/MM/yyyy')
                                  //                   .format(DateTime
                                  //                       .fromMillisecondsSinceEpoch(
                                  //                           k[adjustedIndex]
                                  //                               .entryDate!))),
                                  //           LabelValuePair(
                                  //               label: t.translate(i18
                                  //                   .measurementBook.mbAmount),
                                  //               value: k[adjustedIndex]
                                  //                           .totalAmount !=
                                  //                       null
                                  //                   ? double.parse(
                                  //                           (k[adjustedIndex]
                                  //                                   .totalAmount!)
                                  //                               .toString())
                                  //                       .toStringAsFixed(2)
                                  //                   : '0.00'),
                                  //           LabelValuePair(
                                  //               label: t.translate(i18
                                  //                   .measurementBook.mbStatus),
                                  //               value:
                                  //                   k[adjustedIndex].wfStatus ??
                                  //                       ""),
                                  //         ]),
                                  //     (k[adjustedIndex].musterRollNumber ==
                                  //                 null ||
                                  //             k[adjustedIndex]
                                  //                     .musterRollNumber
                                  //                     .toString() ==
                                  //                 "")
                                  //         ? const SizedBox.shrink()
                                  //         : Button(
                                  //             suffixIcon:
                                  //                 Icons.arrow_forward_outlined,
                                  //             label: t.translate(
                                  //                 i18.home.musterRoll),
                                  //             onPressed: () {
                                  //               context.router
                                  //                   .push(MBMusterScreenRoute(
                                  //                 mbNumber: widget.mbNumber,
                                  //                 musterRollNumber:
                                  //                     k[adjustedIndex]
                                  //                         .musterRollNumber
                                  //                         .toString(),
                                  //                 tenantId: widget.tenantId!,
                                  //               ));
                                  //             },
                                  //             type: ButtonType.link,
                                  //             size: ButtonSize.medium),
                                  //   ],

                                  //   // showSla: false, showStatus: true, status: k[adjustedIndex].wfStatus??'',
                                  // );
                                } else {
                                  return null; // Return null for the skipped item
                                }
                              },
                              childCount: widget.type == MBScreen.update
                                  ? k.length
                                  : k.length,
                            ),
                          ),
                         
                          SliverList(
                            delegate: SliverChildBuilderDelegate(
                              (BuildContext context, int index) {
                                return const Center(
                                    child: SizedBox(
                                  height: 250,
                                ));
                              },
                              childCount: 1,
                            ),
                          )
                        ],
                      ),

                      widget.type == MBScreen.create
                          ? const SizedBox.shrink()
                          : BlocBuilder<MusterGetWorkflowBloc,
                              MusterGetWorkflowState>(
                              builder: (context, state) {
                                return state.maybeMap(
                                  orElse: () => const SizedBox.shrink(),
                                  loaded: (mbWorkFlow) {
                                    final g = mbWorkFlow
                                        .musterWorkFlowModel?.processInstances;
                                    return DigitBottomSheet(
                                      primaryActionLabel: t.translate(
                                          i18.measurementBook.mbAction),
                                      onPrimaryAction: (g != null &&
                                              (g.first.nextActions != null &&
                                                  g.first.nextActions!.isEmpty))
                                          ? null
                                          : (ctx) {
                                              // Navigator.of(context).pop();
                                              showDialog(
                                                context: context,
                                                builder: (context) =>
                                                    CommonButtonCard(
                                                  g: mbWorkFlow
                                                      .musterWorkFlowModel
                                                      ?.processInstances,
                                                  contractNumber:
                                                      widget.contractNumber,
                                                  mbNumber: widget.mbNumber,
                                                  type: widget.type,
                                                ),
                                              );
                                            },
                                      fixedHeight: 200,
                                      initialHeightPercentage: 30,
                                      content: Column(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        mainAxisSize: MainAxisSize.min,
                                        children: [
                                          Container(
                                            decoration: const BoxDecoration(
                                              border: Border(
                                                bottom: BorderSide(),
                                              ),
                                            ),
                                            child: Padding(
                                              padding: const EdgeInsets.only(
                                                  bottom: 8.0),
                                              child: ListTile(
                                                title: Text(
                                                  // "Total SOR Amount",
                                                  t.translate(i18
                                                      .measurementBook
                                                      .totalSorAmount),
                                                  style: Theme.of(context)
                                                      .digitTextTheme(context)
                                                      .headingM
                                                      .copyWith(
                                                        color: Theme.of(context)
                                                            .colorScheme
                                                            .secondary,
                                                      ),
                                                ),
                                                subtitle: Text(
                                                  t.translate(i18
                                                      .measurementBook
                                                      .forCurrentEntry),
                                                  style: Theme.of(context)
                                                      .digitTextTheme(context)
                                                      .bodyS
                                                      .copyWith(
                                                        color: Theme.of(context)
                                                            .colorScheme
                                                            .secondary,
                                                      ),
                                                ),
                                                trailing: Text(
                                                  value.data.first
                                                      .totalSorAmount!
                                                      .toDouble()
                                                      .toStringAsFixed(2),
                                                  // totalSorAmount.toDouble().toStringAsFixed(2),
                                                  style: Theme.of(context)
                                                      .digitTextTheme(context)
                                                      .headingL
                                                      .copyWith(
                                                        color: Colors.black,
                                                      ),
                                                ),
                                              ),
                                            ),
                                          ),
                                          const SizedBox(
                                            height: 20,
                                          ),
                                          Container(
                                            decoration: const BoxDecoration(
                                              border: Border(
                                                bottom: BorderSide(),
                                              ),
                                            ),
                                            child: Padding(
                                              padding: const EdgeInsets.only(
                                                  bottom: 8.0),
                                              child: ListTile(
                                                title: Text(
                                                  // "Total Non SOR Amount",
                                                  t.translate(i18
                                                      .measurementBook
                                                      .totalNonSorAmount),
                                                  style: Theme.of(context)
                                                      .digitTextTheme(context)
                                                      .headingM
                                                      .copyWith(
                                                          color:
                                                              Theme.of(context)
                                                                  .colorScheme
                                                                  .secondary),
                                                ),
                                                subtitle: Text(
                                                  t.translate(i18
                                                      .measurementBook
                                                      .forCurrentEntry),
                                                  style: Theme.of(context)
                                                      .digitTextTheme(context)
                                                      .bodyS
                                                      .copyWith(
                                                          color:
                                                              Theme.of(context)
                                                                  .colorScheme
                                                                  .secondary),
                                                ),
                                                trailing: Text(
                                                  // "23.98",
                                                  value.data.first
                                                      .totalNorSorAmount!
                                                      .toDouble()
                                                      .toStringAsFixed(2),
                                                  // totalNonSorAmount.toDouble().toStringAsFixed(2),
                                                  style: Theme.of(context)
                                                      .digitTextTheme(context)
                                                      .headingL
                                                      .copyWith(
                                                          color: Colors.black),
                                                ),
                                              ),
                                            ),
                                          ),
                                          const SizedBox(
                                            height: 15,
                                          ),
                                          Container(
                                            //  height: 80,
                                            width: MediaQuery.sizeOf(context)
                                                .width,
                                            decoration: BoxDecoration(
                                              //color: Colors.red,
                                              borderRadius:
                                                  const BorderRadius.all(
                                                Radius.circular(10),
                                              ),
                                              border: Border.all(
                                                color: Colors.grey,
                                                width: 2,
                                              ),
                                            ),
                                            child: Padding(
                                              padding:
                                                  const EdgeInsets.symmetric(
                                                      horizontal: 8.0),
                                              child: Row(
                                                mainAxisAlignment:
                                                    MainAxisAlignment
                                                        .spaceBetween,
                                                children: [
                                                  Expanded(
                                                    flex: 4,
                                                    child: ListTile(
                                                      title: Text(
                                                        // "Total MB Amount",
                                                        t.translate(i18
                                                            .measurementBook
                                                            .totalMbAmount),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingM
                                                            .copyWith(
                                                                color: Theme.of(
                                                                        context)
                                                                    .colorScheme
                                                                    .secondary),
                                                      ),
                                                      subtitle: Text(
                                                        t.translate(i18
                                                            .measurementBook
                                                            .forCurrentEntry),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .bodyS
                                                            .copyWith(
                                                                color: Theme.of(
                                                                        context)
                                                                    .colorScheme
                                                                    .secondary),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                      flex: 4,
                                                      child: Row(
                                                        mainAxisAlignment:
                                                            MainAxisAlignment
                                                                .end,
                                                        children: [
                                                          Text(
                                                            // '3456',
                                                            value.data.first
                                                                .totalAmount!
                                                                .roundToDouble()
                                                                .toStringAsFixed(
                                                                    2),
                                                            // mbAmount.roundToDouble().toStringAsFixed(2),
                                                            style: Theme.of(
                                                                    context)
                                                                .digitTextTheme(
                                                                    context)
                                                                .headingL
                                                                .copyWith(
                                                                    color: Colors
                                                                        .black),
                                                          ),
                                                        ],
                                                      )),
                                                ],
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                    );
                                  },
                                );
                              },
                            ),

                      //
                    ],
                  ),
                );
              },
            );
          },
        );
      },
    );
  }

  void _openBottomSheet(
    AppLocalizations t,
    BuildContext context,
    double totalSorAmount,
    double totalNonSorAmount,
    double mbAmount,
    List<ProcessInstances>? processInstances,
    String contractNumber,
    String mbNumber,
    bool showBtn,
  ) {
    showModalBottomSheet(
      shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.vertical(top: Radius.circular(20))),
      context: context,
      builder: (BuildContext context) {
        return Padding(
          padding: const EdgeInsets.only(
              left: 16.0, right: 16.0, top: 16.0, bottom: 0.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            mainAxisSize: MainAxisSize.min,
            children: [
              const Center(
                child: SizedBox(
                  width: 100,
                  child: DigitDivider(
                    dividerType: DividerType.large,
                  ),
                ),
              ),
              Container(
                decoration: const BoxDecoration(
                  border: Border(
                    bottom: BorderSide(),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.only(bottom: 8.0),
                  child: ListTile(
                    title: Text(
                      // "Total SOR Amount",
                      t.translate(i18.measurementBook.totalSorAmount),
                      style: Theme.of(context).digitTextTheme(context).headingM,
                    ),
                    subtitle: Text(
                      t.translate(i18.measurementBook.forCurrentEntry),
                      style: Theme.of(context).digitTextTheme(context).bodyS,
                    ),
                    trailing: Text(
                      totalSorAmount.toDouble().toStringAsFixed(2),
                      style: Theme.of(context).digitTextTheme(context).headingM,
                    ),
                  ),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              Container(
                decoration: const BoxDecoration(
                  border: Border(
                    bottom: BorderSide(),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.only(bottom: 8.0),
                  child: ListTile(
                    title: Text(
                      // "Total Non SOR Amount",
                      t.translate(i18.measurementBook.totalNonSorAmount),
                      style: Theme.of(context).digitTextTheme(context).headingM,
                    ),
                    subtitle: Text(
                      t.translate(i18.measurementBook.forCurrentEntry),
                      style: Theme.of(context).digitTextTheme(context).bodyS,
                    ),
                    trailing: Text(
                      totalNonSorAmount.toDouble().toStringAsFixed(2),
                      style: Theme.of(context).digitTextTheme(context).headingM,
                    ),
                  ),
                ),
              ),
              const SizedBox(
                height: 15,
              ),
              Container(
                //  height: 80,
                width: MediaQuery.sizeOf(context).width,
                decoration: BoxDecoration(
                  //color: Colors.red,
                  borderRadius: const BorderRadius.all(
                    Radius.circular(10),
                  ),
                  border: Border.all(
                    color: Colors.grey,
                    width: 2,
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Expanded(
                        flex: 4,
                        child: ListTile(
                          title: Text(
                            // "Total MB Amount",
                            t.translate(i18.measurementBook.totalMbAmount),
                            style: Theme.of(context)
                                .digitTextTheme(context)
                                .headingM,
                          ),
                          subtitle: Text(
                            t.translate(i18.measurementBook.forCurrentEntry),
                            style:
                                Theme.of(context).digitTextTheme(context).bodyS,
                          ),
                        ),
                      ),
                      Expanded(
                          flex: 4,
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.end,
                            children: [
                              Text(
                                mbAmount.roundToDouble().toStringAsFixed(2),
                                style: Theme.of(context)
                                    .digitTextTheme(context)
                                    .headingM,
                              ),
                            ],
                          )),
                    ],
                  ),
                ),
              ),
              const SizedBox(
                height: 15,
              ),
              showBtn
                  ? Padding(
                      padding: const EdgeInsets.only(bottom: 8.0),
                      child: ui_component.Button(
                        mainAxisSize: MainAxisSize.max,
                        label: t.translate(i18.measurementBook.mbAction),
                        onPressed: () {
                          Navigator.of(context).pop();
                          showDialog(
                            context: context,
                            builder: (context) => CommonButtonCard(
                              g: processInstances,
                              contractNumber: contractNumber,
                              mbNumber: mbNumber,
                              type: widget.type,
                            ),
                          );
                        },
                        type: ButtonType.primary,
                        size: ButtonSize.large,
                      ),
                    )
                  : const SizedBox.shrink(),
            ],
          ),
        );
      },
    );
  }
}
