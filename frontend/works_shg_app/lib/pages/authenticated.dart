import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/user/user_search.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/widgets/loaders.dart';

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

    return Scaffold(
      body: MultiBlocProvider(
        providers: [
          BlocProvider(
            create: (_) => UserSearchBloc()..add(const SearchUserEvent()),
          ),
          BlocProvider(
            create: (_) =>
                MusterRollSearchBloc()..add(const SearchMusterRollEvent()),
          )
        ],
        child: BlocProvider(
            create: (context) => LocalizationBloc(
                  const LocalizationState(),
                  LocalizationRepository(client.init()),
                )..add(LocalizationEvent.onLoadLocalization(
                    module: 'rainmaker-attendencemgmt',
                    tenantId: GlobalVariables
                        .globalConfigObject!.globalConfigs!.stateTenantId
                        .toString(),
                    locale: selectedLocale.toString(),
                  )),
            child: BlocBuilder<UserSearchBloc, UserSearchState>(
                builder: (context, userState) {
              if (!userState.loading && userState.userSearchModel != null) {
                return const AutoRouter();
              } else {
                return Loaders.circularLoader(context);
              }
            })),
      ),
    );
  }
}
