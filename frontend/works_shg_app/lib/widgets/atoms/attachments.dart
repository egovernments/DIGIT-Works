// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../models/file_store/file_store_model.dart';
import '../../utils/common_methods.dart';

class Attachments extends StatelessWidget {
  final String label;
  final TextStyle? labelStyle;
  final List<FileStoreModel>? fileStoreList;
  const Attachments(this.label, this.fileStoreList,
      {this.labelStyle, super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin:
          const EdgeInsets.only(top: 4.0, bottom: 4.0, right: 4.0, left: 4.0),
      alignment: Alignment.centerLeft,
      child: Wrap(
        direction: Axis.horizontal,
        children: [
          label.isNotEmpty
              ? Align(
                  alignment: Alignment.centerLeft,
                  child: Text(
                    label,
                    style: labelStyle ??
                        Theme.of(context).digitTextTheme(context).headingL.copyWith(color: Colors.black),
                            
                    textAlign: TextAlign.left,
                  ),
                )
              : Container(),
          Wrap(
              children: fileStoreList != null
                  ? fileStoreList!
                      .map<Widget>((e) => InkWell(
                            onTap: () => CommonMethods().onTapOfAttachment(
                                e,
                                e.tenantId==null?
                                GlobalVariables.roleType==RoleType.employee?
                                GlobalVariables.tenantId!:
                                    GlobalVariables.stateInfoListModel!.code
                                        .toString():e.tenantId!,
                               // "od.testing",
                                context,
                               roleType: GlobalVariables.roleType==RoleType.employee?RoleType.employee:RoleType.cbo
                                ),
                            child: Container(
                                width: 50,
                                margin: const EdgeInsets.symmetric(
                                    vertical: 10, horizontal: 5),
                                child:
                                    Wrap(runSpacing: 5, spacing: 8, children: [
                                  Image.asset('assets/png/attachment.png'),
                                  Text(
                                    AppLocalizations.of(context)
                                        .translate(e.name.toString()),
                                    maxLines: 2,
                                    overflow: TextOverflow.ellipsis,
                                  )
                                ])),
                          ))
                      .toList()
                  : [])
        ],
      ),
    );
  }
}
