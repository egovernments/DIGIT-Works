import 'package:flutter/material.dart';
import 'package:flutter_training/utils/global_variables.dart';

import '../../blocs/localization/app_localization.dart';

class AppBarLogo extends StatelessWidget {
  const AppBarLogo({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(right: 8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          GlobalVariables.stateInfoListModel?.logoUrlWhite != null
              ? Image(
                  width: 130,
                  image: NetworkImage(
                    GlobalVariables.stateInfoListModel!.logoUrlWhite ?? '',
                  ))
              : const SizedBox.shrink(),
          Padding(
              padding: const EdgeInsets.only(right: 8.0, top: 4.0, bottom: 4.0),
              child: Column(
                children: [
                  Text(
                    AppLocalizations.of(context).translate(
                        'TENANT_TENANTS_${GlobalVariables.userRequestModel?['tenantId'].toString().replaceAll('.', '_').toUpperCase()}'),
                    style: const TextStyle(
                      fontWeight: FontWeight.w400,
                      fontSize: 12,
                    ),
                    textAlign: TextAlign.start,
                  ),
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
