import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../data/remote_client.dart';
import '../data/repositories/remote/mdms.dart';

class UnauthenticatedPageWrapper extends StatelessWidget {
  const UnauthenticatedPageWrapper({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Client client = Client();
    return Scaffold(
        appBar: AppBar(),
        body: MultiBlocProvider(
          providers: [
            BlocProvider(
              create: (context) => AppInitializationBloc(
                const AppInitializationState(),
                MdmsRepository(client.init()),
              )..add(const AppInitializationSetupEvent(selectedLangIndex: 0)),
            ),
          ],
          child: BlocBuilder<AppInitializationBloc, AppInitializationState>(
              builder: (context, appInitState) {
            return (appInitState.isInitializationCompleted &&
                    appInitState.digitRowCardItems != null &&
                    appInitState.digitRowCardItems!.isNotEmpty)
                ? BlocBuilder<LocalizationBloc, LocalizationState>(
                    builder: (context, localeState) {
                    return localeState.isLocalizationLoadCompleted &&
                            localeState.localization != null
                        ? const AutoRouter()
                        : const CircularProgressIndicator();
                  })
                : const CircularProgressIndicator();
          }),
        ));
  }
}
