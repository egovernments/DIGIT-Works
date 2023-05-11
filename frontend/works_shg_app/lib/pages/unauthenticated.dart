import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../data/remote_client.dart';
import '../data/repositories/remote/mdms.dart';
import '../models/localization/localization_model.dart';
import '../widgets/loaders.dart';

class UnauthenticatedPageWrapper extends StatelessWidget {
  const UnauthenticatedPageWrapper({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Client client = Client();
    return Scaffold(
        body: MultiBlocProvider(
      providers: [
        BlocProvider(
          create: (context) => AppInitializationBloc(
            const AppInitializationState(),
            MdmsRepository(client.init()),
          )..add(const AppInitializationSetupEvent(selectedLang: 'en_IN')),
        ),
      ],
      child: BlocBuilder<AppInitializationBloc, AppInitializationState>(
          builder: (context, appInitState) {
        return (appInitState.isInitializationCompleted &&
                appInitState.digitRowCardItems != null &&
                appInitState.digitRowCardItems!.isNotEmpty)
            ? BlocBuilder<LocalizationBloc, LocalizationState>(
                builder: (context, localeState) {
                return localeState.maybeWhen(
                    orElse: () => Container(),
                    loading: () => Loaders.circularLoader(context),
                    loaded: (List<LocalizationMessageModel>? localization) {
                      return const AutoRouter();
                    });
              })
            : Loaders.circularLoader(context);
      }),
    ));
  }
}
