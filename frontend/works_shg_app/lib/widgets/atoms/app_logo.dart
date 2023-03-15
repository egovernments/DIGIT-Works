import 'package:flutter/material.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/global_variables.dart';

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
                padding: const EdgeInsets.only(top: 15, bottom: 15),
                child: Center(
                  child: Image(
                      width: 150,
                      image: NetworkImage(
                        GlobalVariables.stateInfoListModel!.logoUrl.toString(),
                      )),
                )),
            const Padding(
                padding: EdgeInsets.only(left: 15),
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
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    AppLocalizations.of(context).translate(
                        GlobalVariables.stateInfoListModel!.code.toString()),
                    style: const TextStyle(
                        fontSize: 19,
                        fontWeight: FontWeight.w400,
                        color: Color.fromRGBO(0, 0, 0, 1)),
                    textAlign: TextAlign.left,
                  ),
                ))
          ],
        ));
  }
}
