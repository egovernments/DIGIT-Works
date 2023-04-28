import 'package:flutter/material.dart';
import 'package:works_shg_app/utils/global_variables.dart';

class AppBarLogo extends StatelessWidget {
  const AppBarLogo({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(right: 8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Image(
              width: 130,
              image: NetworkImage(
                GlobalVariables.stateInfoListModel!.logoUrlWhite!,
              )),
          Padding(
              padding: const EdgeInsets.only(right: 8.0, top: 4.0, bottom: 4.0),
              child: Column(
                children: [
                  Text(
                    GlobalVariables.organisationListModel != null
                        ? GlobalVariables.organisationListModel?.organisations
                                ?.first.name ??
                            ''
                        : '',
                    style: const TextStyle(
                      fontWeight: FontWeight.w400,
                      fontSize: 12,
                    ),
                    textAlign: TextAlign.start,
                  ),
                  Text(
                    GlobalVariables.organisationListModel?.organisations?.first
                            .orgNumber ??
                        '',
                    style: const TextStyle(
                      fontWeight: FontWeight.w400,
                      fontSize: 12,
                    ),
                    textAlign: TextAlign.start,
                  )
                ],
              )
              // Icon(
              //   Icons.notifications,
              //   size: 30,
              // ),
              ),
        ],
      ),
    );
  }
}
