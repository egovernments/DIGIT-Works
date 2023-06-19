import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/organisation/org_search_bloc.dart';
import 'package:works_shg_app/data/init_client.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../blocs/localization/localization.dart';
import '../blocs/muster_rolls/search_muster_roll.dart';
import '../data/remote_client.dart';
import '../data/repositories/remote/localization.dart';

class AuthenticatedPageWrapper extends StatefulWidget {
  const AuthenticatedPageWrapper({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _AuthenticatedPageWrapper();
  }
}

class _AuthenticatedPageWrapper extends State<AuthenticatedPageWrapper> {
  String? selectedLocale;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    selectedLocale = await GlobalVariables.selectedLocale();
  }

  @override
  Widget build(BuildContext context) {
    Client client = Client();
    InitClient initClient = InitClient();
    return Scaffold(
      body: MultiBlocProvider(
        providers: [
          BlocProvider(
            create: (_) =>
                MusterRollSearchBloc()..add(const SearchMusterRollEvent()),
          )
        ],
        child: BlocProvider(
          create: (context) => LocalizationBloc(
            const LocalizationState.initial(),
            LocalizationRepository(initClient.init()),
          )..add(LocalizationEvent.onLoadLocalization(
              module: 'rainmaker-attendencemgmt',
              tenantId: GlobalVariables
                  .globalConfigObject!.globalConfigs!.stateTenantId
                  .toString(),
              locale: selectedLocale.toString(),
            )),
          child: BlocProvider(
            create: (_) => ORGSearchBloc()
              ..add(SearchORGEvent(
                  GlobalVariables.userRequestModel!['mobileNumber'])),
            child: const AutoRouter(),
          ),
        ),
      ),
    );
  }
}
