import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/blocs/organisation/org_search_bloc.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../blocs/localization/localization.dart';

@RoutePage()
class AuthenticatedWrapperPage extends StatefulWidget {
  const AuthenticatedWrapperPage({
    super.key,
  });

  @override
  State<StatefulWidget> createState() {
    return _AuthenticatedPageWrapper();
  }
}

class _AuthenticatedPageWrapper extends State<AuthenticatedWrapperPage> {
  String? selectedLocale;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    // selectedLocale = await GlobalVariables.selectedLocale();
    final data = BlocProvider.of<LocalizationBloc>(context).state;
    final selectedLocale = data.maybeMap(
      orElse: () => null,
      loaded: (value) {
        return value.languages!.firstWhere((element) => element.isSelected);
      },
    );
    // ignore: use_build_context_synchronously
    context
        .read<LocalizationBloc>()
        .add(LocalizationEvent.onSpecificLoadLocalization(
          module: GlobalVariables.roleType == RoleType.employee
              ? 'rainmaker-contracts,rainmaker-attendencemgmt,rainmaker-measurement,rainmaker-workflow'
              : 'rainmaker-contracts,rainmaker-attendencemgmt,rainmaker-workflow',
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          locale: selectedLocale!.value.toString(),
        ));

    // ignore: use_build_context_synchronously
    context
        .read<ORGSearchBloc>()
        .add(
          SearchORGEvent(GlobalVariables.userRequestModel!['mobileNumber'])
          );
  }

  @override
  Widget build(BuildContext context) {

    return SafeArea(
      child: Scaffold(
        appBar: customAppBar(),
        drawer: const MySideBar(),
        body: const AutoRouter(),
      ),
    );
   // return const AutoRouter();
  }
}