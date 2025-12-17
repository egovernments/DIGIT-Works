import 'package:digit_ui_components/widgets/molecules/digit_header.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/utils/global_variables.dart';

PreferredSize customAppBar() {
    return PreferredSize(
      preferredSize: const Size.fromHeight(56), // here the desired height
      child: Builder(
        builder: (context) => CustomHeaderMolecule(
          leadingWidget: GlobalVariables.stateInfoListModel?.logoUrlWhite !=
                  null
              ? Row(
                  children: [
                    InkWell(
                      onTap: () {
                        Scaffold.of(context).openDrawer();
                      },
                      child: const Icon(
                        Icons.menu,
                        size: 24,
                        color: Colors.white,
                      ),
                    ),
                    Image(
                        width: 130,
                        image: NetworkImage(
                          GlobalVariables.stateInfoListModel!.logoUrlWhite ??
                              '',
                        )),
                  ],
                )
              : const SizedBox.shrink(),
          type: HeaderType.dark,
          leadingDigitLogo: false,
          trailingDigitLogo: false,
          actionRequired: true,
          // trailingWidget: Column(
          //   mainAxisSize: MainAxisSize.min,
          //   children: [
          //     Text(
          //       GlobalVariables.roleType == RoleType.employee
          //           ? GlobalVariables.userRequestModel!['name']
          //           : GlobalVariables.organisationListModel != null
          //               ? GlobalVariables.organisationListModel?.organisations
          //                       ?.first.name ??
          //                   ''
          //               : '',
          //       style: Theme.of(context).textTheme.labelSmall?.copyWith(
          //             fontWeight: FontWeight.w400,
          //             fontSize: 12,
          //             color: Theme.of(context).colorTheme.paper.primary,
          //           ),
          //       textAlign: TextAlign.start,
          //     ),
          //     Text(
          //       GlobalVariables.roleType == RoleType.employee
          //           ? GlobalVariables.userRequestModel!['userName'].toString()
          //           : GlobalVariables.organisationListModel?.organisations
          //                   ?.first.orgNumber ??
          //               '',
          //       style: Theme.of(context).textTheme.labelSmall?.copyWith(
          //             fontWeight: FontWeight.w400,
          //             fontSize: 12,
          //             color: Theme.of(context).colorTheme.paper.primary,
          //           ),
          //       textAlign: TextAlign.start,
          //     )
          //   ],
          // ),
          onMenuTap: () {
            Scaffold.of(context).openDrawer();
          },
        ),
      ),
    );
  }