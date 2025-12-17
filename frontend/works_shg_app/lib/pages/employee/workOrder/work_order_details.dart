import 'package:auto_route/auto_route.dart';
import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/employee/mb/mb_logic.dart';
//import 'package:works_shg_app/widgets/Back.dart';

import '../../../blocs/localization/app_localization.dart';
import '../../../blocs/localization/localization.dart';
import '../../../utils/common_methods.dart';
import '../../../utils/common_widgets.dart';
import '../../../widgets/SideBar.dart';
import '../../../widgets/atoms/app_bar_logo.dart';
import '../../../widgets/drawer_wrapper.dart';
import '../../../widgets/work_order/work_order_card.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

class WorkOrderDetailPage extends StatefulWidget {
  const WorkOrderDetailPage({super.key});

  @override
  State<WorkOrderDetailPage> createState() => _WorkOrderDetailPageState();
}

class _WorkOrderDetailPageState extends State<WorkOrderDetailPage> {
  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.only(top: 8.0),
        child: Container(
          height: 60,
          decoration: BoxDecoration(color: const DigitColors().white),
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: DigitElevatedButton(
              child:  Text(t.translate(i18.measurementBook.createMb)),
              onPressed: () {
                context.router.push(MBDetailRoute(
                  contractNumber: '',
                  mbNumber: '',
                  type: MBScreen.create,
                ));
              },
            ),
          ),
        ),
      ),
      appBar: AppBar(
        titleSpacing: 0,
        title: const AppBarLogo(),
      ),
      drawer: DrawerWrapper(
          Drawer(child: SideBar(module: CommonMethods.getLocaleModules()))),
      body: BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, state) {
          return ScrollableContent(
            header: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                // Back(
                //   backLabel:
                //       AppLocalizations.of(context).translate(i18.common.back),
                //   callback: () {
                //     // context.router.popUntilRouteWithPath('home') ;
                //     // context.router.push(const WorkOrderRoute());
                //     context.router.pop();
                //   },
                // ),
                CommonWidgets.downloadButton(
                    AppLocalizations.of(context).translate(i18.common.download),
                    () {
                  // context.read<WorkOrderPDFBloc>().add(
                  //     PDFEventWorkOrder(
                  //         contractId: widget.contractNumber,
                  //         tenantId: contracts.first.tenantId));
                })
              ],
            ),
            children: [
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      "Work Order Details",
                      style: DigitTheme
                          .instance.mobileTheme.textTheme.headlineLarge,
                    ),
                  ],
                ),
              ),
              const WorkOrderCard(
                items: {
                  "Work Order Number": "we#12",
                  "Project Name": "sa",
                  "ProjectID": "123@#",
                  "Location": "MG Road",
                  "Project Type": "Wall Paintng",
                  "Project Description": "Wall Painting in Ward 1"
                },
              ),
              const WorkOrderCard(
                headLabel: "Contact Details",
                items: {
                  "Name of CBO": "we#12",
                  "Role of CBO": "sa",
                  "Name of officer in-charge": "123@#",
                  "Desgination of officer in-charge": "MG Road",
                  "Completion Period": "Wall Paintng",
                  "Work Value": "Wall Painting in Ward 1"
                },
              ),
              const WorkOrderCard(
                headLabel: "Work Timelines",
                items: {
                  "Name of CBO": "we#12",
                  "Role of CBO": "sa",
                  "Name of officer in-charge": "123@#",
                  "Desgination of officer in-charge": "MG Road",
                  "Completion Period": "Wall Paintng",
                  "Work Value": "Wall Painting in Ward 1"
                },
              )
            ],
          );
        },
      ),
    );
  }
}
