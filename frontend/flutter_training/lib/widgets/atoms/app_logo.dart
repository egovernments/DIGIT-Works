import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/utils/global_variables.dart';

import '../../blocs/app_initilization/app_initilization.dart';

class AppLogo extends StatelessWidget {
  const AppLogo({super.key});

  @override
  Widget build(BuildContext context) {
    return Align(
        alignment: Alignment.centerLeft,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
                padding: const EdgeInsets.only(top: 4.0, bottom: 8),
                child: Center(
                  child: GlobalVariables.stateInfoListModel?.logoUrl != null
                      ? Image(
                          width: 150,
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
        ));
  }
}
