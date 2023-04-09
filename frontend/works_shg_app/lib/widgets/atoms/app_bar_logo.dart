import 'package:flutter/material.dart';
import 'package:works_shg_app/utils/global_variables.dart';

class AppBarLogo extends StatelessWidget {
  const AppBarLogo({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Image(
            width: 130,
            image: NetworkImage(
              GlobalVariables.stateInfoListModel!.logoUrlWhite!,
            )),
        Padding(
            padding: const EdgeInsets.all(4.0),
            child: Column(
              children: [
                Text(
                  GlobalVariables
                          .organisationListModel?.organisations?.first.name ??
                      'NA',
                  style: const TextStyle(
                    fontWeight: FontWeight.w400,
                    fontSize: 12,
                  ),
                ),
                Text(
                  GlobalVariables.organisationListModel?.organisations?.first
                          .orgNumber ??
                      'NA',
                  style: const TextStyle(
                    fontWeight: FontWeight.w400,
                    fontSize: 12,
                  ),
                )
              ],
            )
            // Icon(
            //   Icons.notifications,
            //   size: 30,
            // ),
            ),
      ],
    );
  }
}
