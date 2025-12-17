// import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../blocs/app_initilization/app_initilization.dart';

class Back extends StatelessWidget {
  final Widget? widget;
  final VoidCallback? callback;
  final String? backLabel;

  const Back({super.key, this.widget, this.callback, this.backLabel});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AppInitializationBloc, AppInitializationState>(
        builder: (context, state) {
      return Padding(
          padding: const EdgeInsets.only(top: 16.0,left: 0.0),
          child: Row(
              mainAxisAlignment: widget == null
                  ? MainAxisAlignment.start
                  : MainAxisAlignment.spaceBetween,
              children: [
                TextButton(
                    onPressed: callback ?? () => Navigator.pop(context),
                    child: Wrap(
                      alignment: WrapAlignment.start,
                      crossAxisAlignment: WrapCrossAlignment.center,
                      children: [
                        const Icon(
                          Icons.arrow_left,
                          color: Colors.black,
                        ),
                        Text(
                            AppLocalizations.of(context)
                                    .translate(i18.common.back) ??
                                'Back',
                            style: const TextStyle(
                              color: Colors.black,
                            ))
                      ],
                    )),
                if (widget != null) widget!
              ]));
    });
  }
}
