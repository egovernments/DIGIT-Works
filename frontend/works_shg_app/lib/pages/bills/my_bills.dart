import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/my_bills/search_my_bills.dart';
import '../../models/my_bills/my_bills_model.dart';
import '../../utils/common_methods.dart';
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
  List<Map<String, dynamic>> workOrderList = [];

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
        body: SingleChildScrollView(
          child: BlocListener<SearchMyBillsBloc, SearchMyBillsState>(
            listener: (context, state) {
              state.maybeWhen(
                  orElse: () => false,
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  loaded: (MyBillsModel? myBillsModel) {
                    workOrderList = myBillsModel!.bills!
                        .map((e) => {
                              i18.workOrder.workOrderNo:
                                  e.billDetails?.first.paymentStatus ?? 'NA',
                            })
                        .toList();
                  });
            },
            child: BlocBuilder<SearchMyBillsBloc, SearchMyBillsState>(
                builder: (context, searchState) {
              return searchState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (MyBillsModel? myBillsModel) => Column(
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
                                      '${AppLocalizations.of(context).translate(i18.home.myWorks)} (${workOrderList.length})',
                                      style: Theme.of(context)
                                          .textTheme
                                          .displayMedium,
                                      textAlign: TextAlign.left,
                                    ),
                                  ),
                                  workOrderList.isNotEmpty
                                      ? WorkDetailsCard(
                                          workOrderList,
                                        )
                                      : EmptyImage(
                                          label: AppLocalizations.of(context)
                                              .translate(i18.workOrder
                                                  .noWorkOrderAssigned),
                                          align: Alignment.center,
                                        ),
                                  const SizedBox(
                                    height: 16.0,
                                  ),
                                  const Align(
                                    alignment: Alignment.bottomCenter,
                                    child: PoweredByDigit(),
                                  )
                                ]),
                          ]));
            }),
          ),
        ));
  }
}
