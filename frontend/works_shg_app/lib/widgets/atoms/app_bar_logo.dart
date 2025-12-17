// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
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
                    GlobalVariables.roleType == RoleType.employee
                        ? GlobalVariables.userRequestModel!['name']
                        : GlobalVariables.organisationListModel != null
                            ? GlobalVariables.organisationListModel
                                    ?.organisations?.first.name ??
                                ''
                            : '',
                    style: Theme.of(context).textTheme.labelSmall?.copyWith(
                          fontWeight: FontWeight.w400,
                          fontSize: 12,
                          color: Theme.of(context).colorTheme.paper.primary,
                        ),
                    textAlign: TextAlign.start,
                  ),
                  Text(
                    GlobalVariables.roleType == RoleType.employee
                        ? GlobalVariables.userRequestModel!['userName']
                            .toString()
                        : GlobalVariables.organisationListModel?.organisations
                                ?.first.orgNumber ??
                            '',
                    style: Theme.of(context).textTheme.labelSmall?.copyWith(
                          fontWeight: FontWeight.w400,
                          fontSize: 12,
                          color: Theme.of(context).colorTheme.paper.primary,
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
