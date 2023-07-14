import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/Env/env_config.dart';

import '../blocs/localization/localization.dart';
import '../data/init_client.dart';
import '../data/remote_client.dart';
import '../data/repositories/remote/localization.dart';
import '../utils/global_variables.dart';

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
      body: BlocProvider(
        create: (context) => LocalizationBloc(
          const LocalizationState.initial(),
          LocalizationRepository(initClient.init()),
        )..add(LocalizationEvent.onLoadLocalization(
            module: 'rainmaker-bnd',
            tenantId: envConfig.variables.tenantId,
            locale: selectedLocale.toString(),
          )),
        child: const AutoRouter(),
      ),
    );
  }
}
