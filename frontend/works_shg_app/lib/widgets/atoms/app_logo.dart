import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../blocs/app_initilization/app_initilization.dart';

class AppLogo extends StatelessWidget {
  const AppLogo({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LocalizationBloc, LocalizationState>(
      builder: (context, state) {
       
          return Align(
              alignment: Alignment.centerLeft,
              child: Padding(
                padding:  EdgeInsets.only(bottom:Theme.of(context).spacerTheme.spacer2),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Padding(
                        padding: const EdgeInsets.only(top: 0.0, bottom:0),
                        child: Center(
                          child: GlobalVariables.stateInfoListModel?.logoUrl !=
                                  null
                              ? Image(
                                  width: 130,
                                  image: NetworkImage(
                                    GlobalVariables.stateInfoListModel!.logoUrl
                                        .toString(),
                                  ))
                              : const SizedBox.shrink(),
                        )),
                    const Padding(
                        padding: EdgeInsets.only(left: 16),
                        child: Text(
                          " | ",
                          style: TextStyle(
                              fontSize: 19,
                              fontWeight: FontWeight.w400,
                              color: Color.fromRGBO(0, 0, 0, 1)),
                        )),
                    Align(
                        alignment: Alignment.centerLeft,
                        child: Padding(
                          padding: const EdgeInsets.only(
                            left: 8.0,
                            right: 8.0,
                          ),
                          child: BlocBuilder<AppInitializationBloc,
                              AppInitializationState>(builder: (context, state) {
                            return Text(
                              AppLocalizations.of(context).translate(
                                  'TENANT_TENANTS_${GlobalVariables.stateInfoListModel!.code.toString().toUpperCase()}'),
                              style: const TextStyle(
                                  fontSize: 19,
                                  fontWeight: FontWeight.w400,
                                  color: Color.fromRGBO(0, 0, 0, 1)),
                              textAlign: TextAlign.left,
                            );
                          }),
                        ))
                  ],
                ),
              ));
       
      },
    );
  }
}
