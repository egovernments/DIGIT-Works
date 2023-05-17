import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/date_formats.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/my_bills/search_my_bills.dart';
import '../../models/my_bills/my_bills_model.dart';
import '../../utils/common_methods.dart';
import '../../utils/constants.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/drawer_wrapper.dart';

class MyBillsPage extends StatefulWidget {
  const MyBillsPage({Key? key}) : super(key: key);

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
    context.read<SearchMyBillsBloc>().add(
          const MyBillsSearchEvent(),
        );
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
        bottomNavigationBar: BlocBuilder<SearchMyBillsBloc, SearchMyBillsState>(
            builder: (context, state) {
          return state.maybeWhen(
              orElse: () => const SizedBox.shrink(),
              loading: () => shg_loader.Loaders.circularLoader(context),
              loaded: (MyBillsListModel? myBillsModel) {
                return billList.length < 2
                    ? const SizedBox(
                        height: 30,
                        child: Align(
                          alignment: Alignment.bottomCenter,
                          child: PoweredByDigit(),
                        ),
                      )
                    : const SizedBox.shrink();
              });
        }),
        body: SingleChildScrollView(
          child: BlocListener<SearchMyBillsBloc, SearchMyBillsState>(
            listener: (context, state) {
              state.maybeWhen(
                  orElse: () => false,
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  loaded: (MyBillsListModel? myBillsModel) {
                    bills = List<MyBillModel>.from(myBillsModel!.bills!);
                    bills.sort((a, b) => b.bill!.auditDetails!.lastModifiedTime!
                        .compareTo(
                            a.bill!.auditDetails!.lastModifiedTime!.toInt()));
                    billList = bills.map((e) {
                      if (e.bill?.businessService ==
                          Constants.myBillsWageType) {
                        return {
                          i18.myBills.billType: t.translate(
                              'EXP_BILL_TYPE_${e.bill?.businessService ?? 'NA'}'),
                          i18.myBills.billNumber: e.bill?.billNumber,
                          i18.myBills.billDate: e.bill?.billDate != null
                              ? DateFormats.getDateFromTimestamp(
                                  e.bill?.billDate ?? 0)
                              : 'NA',
                          i18.workOrder.workOrderNo: e.contractNumber ?? 'NA',
                          i18.attendanceMgmt.projectDesc:
                              e.bill?.additionalDetails?.projectDesc ?? 'NA',
                          i18.attendanceMgmt.musterRollId:
                              e.musterRollNumber ?? 'NA',
                          i18.attendanceMgmt.musterRollPeriod: e
                                      .bill?.fromPeriod !=
                                  null
                              ? '${DateFormats.getDateFromTimestamp(e.bill?.fromPeriod ?? 0)} - ${DateFormats.getDateFromTimestamp(e.bill?.toPeriod ?? 0)}'
                              : 'NA',
                          i18.myBills.netPayable: (e.bill?.totalAmount ?? 0),
                          i18.common.status: t.translate(
                              'BILL_STATUS_${e.bill?.wfStatus ?? 'NA'}'),
                          Constants.activeInboxStatus: 'true'
                        };
                      } else if (e.bill?.businessService ==
                          Constants.myBillsPurchaseType) {
                        return {
                          i18.myBills.billType: t.translate(
                              'EXP_BILL_TYPE_${e.bill?.businessService ?? 'NA'}'),
                          i18.myBills.billNumber: e.bill?.billNumber,
                          i18.myBills.billDate: e.bill?.billDate != null
                              ? DateFormats.getDateFromTimestamp(
                                  e.bill?.billDate ?? 0)
                              : 'NA',
                          i18.workOrder.workOrderNo: e.contractNumber ?? 'NA',
                          i18.attendanceMgmt.projectDesc:
                              e.bill?.additionalDetails?.projectDesc ?? 'NA',
                          i18.myBills.invoiceId:
                              e.bill?.additionalDetails?.invoiceNumber,
                          i18.myBills.invoiceDate: e
                                      .bill?.additionalDetails?.invoiceDate !=
                                  null
                              ? DateFormats.getDateFromTimestamp(
                                  e.bill?.additionalDetails?.invoiceDate ?? 0)
                              : 'NA',
                          i18.myBills.payeeName: e.bill?.payer?.identifier,
                          i18.myBills.netPayable: (e.bill?.totalAmount ?? 0),
                          i18.common.status: t.translate(
                              'BILL_STATUS_${e.bill?.wfStatus ?? 'NA'}'),
                          Constants.activeInboxStatus: 'true'
                        };
                      } else {
                        return {
                          i18.myBills.billType: t.translate(
                              'EXP_BILL_TYPE_${e.bill?.businessService ?? 'NA'}'),
                          i18.myBills.billNumber: e.bill?.billNumber,
                          i18.myBills.billDate: e.bill?.billDate != null
                              ? DateFormats.getDateFromTimestamp(
                                  e.bill?.billDate ?? 0)
                              : 'NA',
                          i18.workOrder.workOrderNo: e.contractNumber ?? 'NA',
                          i18.attendanceMgmt.projectDesc:
                              e.bill?.additionalDetails?.projectDesc ?? 'NA',
                          i18.myBills.payeeName: e.bill?.payer?.identifier,
                          i18.myBills.netPayable: (e.bill?.totalAmount ?? 0),
                          i18.common.status: t.translate(
                              'BILL_STATUS_${e.bill?.wfStatus ?? 'NA'}'),
                          Constants.activeInboxStatus: 'true'
                        };
                      }
                    }).toList();
                  });
            },
            child: BlocBuilder<SearchMyBillsBloc, SearchMyBillsState>(
                builder: (context, searchState) {
              return searchState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (MyBillsListModel? myBillsModel) => Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Back(
                              backLabel: AppLocalizations.of(context)
                                  .translate(i18.common.back),
                            ),
                            Column(
                                mainAxisAlignment: MainAxisAlignment.start,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Padding(
                                    padding: const EdgeInsets.all(16.0),
                                    child: Text(
                                      '${AppLocalizations.of(context).translate(i18.home.myBills)} (${billList.length})',
                                      style: Theme.of(context)
                                          .textTheme
                                          .displayMedium,
                                      textAlign: TextAlign.left,
                                    ),
                                  ),
                                  billList.isNotEmpty
                                      ? WorkDetailsCard(
                                          billList,
                                        )
                                      : EmptyImage(
                                          label: AppLocalizations.of(context)
                                              .translate(i18.myBills.noBills),
                                          align: Alignment.center,
                                        ),
                                  const SizedBox(
                                    height: 16.0,
                                  ),
                                  billList.length > 1
                                      ? const Align(
                                          alignment: Alignment.bottomCenter,
                                          child: PoweredByDigit(),
                                        )
                                      : const SizedBox.shrink()
                                ]),
                          ]));
            }),
          ),
        ));
  }
}
