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
        const Padding(
          padding: EdgeInsets.only(right: 16.0),
          child: Icon(
            Icons.notifications_active,
            size: 30,
          ),
        ),
      ],
    );
  }
}
