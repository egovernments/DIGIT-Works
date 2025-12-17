// import 'package:digit_components/digit_components.dart';
// import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_bloc/flutter_bloc.dart';
// import 'package:works_shg_app/blocs/auth/auth.dart';
// import 'package:works_shg_app/icons/shg_icons.dart';
// import 'package:works_shg_app/router/app_router.dart';
// import 'package:works_shg_app/utils/constants.dart';
// import 'package:works_shg_app/utils/global_variables.dart';
// import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
//     as i18;

// import '../blocs/app_initilization/app_initilization.dart';
// import '../blocs/localization/app_localization.dart';
// import '../blocs/localization/localization.dart';
// import '../blocs/organisation/org_search_bloc.dart';
// import '../models/organisation/organisation_model.dart';

// class SideBar extends StatefulWidget {
//   final String module;
//   const SideBar(
//       {super.key,
//       this.module =
//           'rainmaker-common,rainmaker-attendencemgmt,rainmaker-common-masters'});
//   @override
//   State<StatefulWidget> createState() {
//     return _SideBar();
//   }
// }

// class _SideBar extends State<SideBar> {
//   List<DigitRowCardModel>? digitRowCardItems;

//   @override
//   void initState() {
//     WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
//     super.initState();
//   }

//   afterViewBuild() async {
//     digitRowCardItems = await GlobalVariables.getLanguages();
//   }

//   @override
//   dispose() {
//     super.dispose();
//   }

//   @override
//   Widget build(BuildContext buildContext) {
//     final theme = DigitTheme.instance;

//     return BlocBuilder<LocalizationBloc, LocalizationState>(
//         builder: (context, localeState) {
//       return localeState.maybeMap(
//         orElse: () => const SizedBox.shrink(),
//         loaded: (value) {
//           return ScrollableContent(
//             footer: const PoweredByDigit(),
//             children: [
//               BlocBuilder<ORGSearchBloc, ORGSearchState>(
//                   builder: (context, orgState) {
//                 return orgState.maybeWhen(
//                     orElse: () => Container(),
//                     loading: () => SizedBox(
//                         height: MediaQuery.of(buildContext).size.height / 3,
//                         child: Loaders.circularLoader(context)),
//                     loaded: (OrganisationListModel? organisationListModel) {
//                       return organisationListModel?.organisations != null
//                           ? Container(
//                               width: MediaQuery.of(buildContext).size.width,
//                               height:
//                                   MediaQuery.of(buildContext).size.height / 3,
//                               color: const DigitColors().quillGray,
//                               child: Column(
//                                 mainAxisAlignment: MainAxisAlignment.center,
//                                 children: [
//                                   Text(
//                                     organisationListModel!
//                                         .organisations!.first.name
//                                         .toString(),
//                                     style: theme
//                                         .mobileTheme.textTheme.headlineMedium
//                                         ?.apply(
//                                             color: const DigitColors().black),
//                                   ),
//                                   Text(
//                                     organisationListModel
//                                         .organisations!
//                                         .first
//                                         .contactDetails!
//                                         .first
//                                         .contactMobileNumber
//                                         .toString(),
//                                     style: theme
//                                         .mobileTheme.textTheme.bodyMedium
//                                         ?.apply(
//                                             color:
//                                                 const DigitColors().davyGray),
//                                   ),
//                                 ],
//                               ),
//                             )
//                           : Container();
//                     });
//               }),
//               Row(
//                 children: [
//                   context.router.currentPath == '/home'
//                       ? Container(
//                           alignment: Alignment.centerLeft,
//                           height: 60,
//                           width: 9,
//                           color: const DigitColors().burningOrange,
//                         )
//                       : const SizedBox.shrink(),
//                   Expanded(
//                     child: DigitIconTile(
//                       title: AppLocalizations.of(context)
//                           .translate(i18.common.home),
//                       selected: context.router.currentPath == '/home',
//                       icon: Icons.home,
//                       onPressed: () =>
//                           context.router.replace(const HomeRoute()),
//                     ),
//                   ),
//                 ],
//               ),
//               DigitIconTile(
//                 title:
//                     AppLocalizations.of(context).translate(i18.common.language),
//                 icon: SHGIcons.language,
//                 content: Padding(
//                   padding: const EdgeInsets.all(16),
//                   child: BlocBuilder<AppInitializationBloc,
//                       AppInitializationState>(
//                     builder: (context, state) {
//                       return state.digitRowCardItems != null &&
//                               state.isInitializationCompleted
//                           ? DigitRowCard(
//                               onChanged: (data) async {
//                                 context.read<LocalizationBloc>().add(
//                                       LocalizationEvent
//                                           .onSpecificLoadLocalization(
//                                         locale: data.value,
//                                         module: widget.module,
//                                         tenantId: state
//                                             .initMdmsModel!
//                                             .commonMastersModel!
//                                             .stateInfoListModel!
//                                             .first
//                                             .code
//                                             .toString(),
//                                       ),
//                                     );
//                               },
//                               rowItems: value.languages
//                                   ?.map((e) =>
//                                       DigitRowCardModel.fromJson(e.toJson()))
//                                   .toList() as List<DigitRowCardModel>,
//                               width: 80)
//                           : const Text('');
//                     },
//                   ),
//                 ),
//                 onPressed: () {},
//               ),
//               Row(
//                 children: [
//                   context.router.currentPath.contains('orgProfile')
//                       ? Container(
//                           alignment: Alignment.centerLeft,
//                           height: 50,
//                           width: 9,
//                           color: const DigitColors().burningOrange,
//                         )
//                       : const SizedBox.shrink(),
//                   Expanded(
//                     child: DigitIconTile(
//                         title: AppLocalizations.of(context)
//                             .translate(i18.common.orgProfile),
//                         selected:
//                             context.router.currentPath.contains('orgProfile'),
//                         icon: Icons.perm_contact_cal_sharp,
//                         onPressed: () {
//                           context.router.push(const ORGProfileRoute());
//                         }),
//                   ),
//                 ],
//               ),
//               DigitIconTile(
//                   title:
//                       AppLocalizations.of(context).translate(i18.common.logOut),
//                   icon: Icons.logout,
//                   onPressed: () {
//                     context.read<AppInitializationBloc>().add(
//                          AppInitializationSetupEvent(
//                             selectedLang: LanguageEnum.en_IN.name));
//                     context.read<AuthBloc>().add(const AuthLogoutEvent());
//                   }),
//             ],
//           );
//         },
//         loading: (value) {
//           return Loaders.circularLoader(context);
//         },
//       );
//     });
//   }
// }

//

import 'package:digit_components/digit_components.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/icons/shg_icons.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../blocs/organisation/org_search_bloc.dart';
import '../models/organisation/organisation_model.dart';

class SideBar extends StatefulWidget {
  final String module;
  const SideBar(
      {super.key,
      this.module =
          'rainmaker-common,rainmaker-attendencemgmt,rainmaker-common-masters'});
  @override
  State<StatefulWidget> createState() {
    return _SideBar();
  }
}

class _SideBar extends State<SideBar> {
  List<DigitRowCardModel>? digitRowCardItems;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    digitRowCardItems = await GlobalVariables.getLanguages();
  }

  @override
  dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext buildContext) {
    final theme = DigitTheme.instance;

    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localeState) {
      return localeState.maybeMap(
        orElse: () => const SizedBox.shrink(),
        loaded: (value) {
          return ScrollableContent(
            footer: const PoweredByDigit(version: Constants.appVersion,),
            children: [
              BlocBuilder<AuthBloc, AuthState>(
                builder: (context, state) {
                  return state.maybeMap(
                    orElse: () {
                      return const SizedBox.shrink();
                    },
                    loaded: (value) {
                      if (value.roleType == RoleType.cbo) {
                        return BlocBuilder<ORGSearchBloc, ORGSearchState>(
                            builder: (context, orgState) {
                          return orgState.maybeWhen(
                              orElse: () => Container(),
                              loading: () => SizedBox(
                                  height:
                                      MediaQuery.of(buildContext).size.height /
                                          3,
                                  child: Loaders.circularLoader(context)),
                              loaded: (OrganisationListModel?
                                  organisationListModel) {
                                return organisationListModel?.organisations !=
                                        null
                                    ? Container(
                                        width: MediaQuery.of(buildContext)
                                            .size
                                            .width,
                                        height: MediaQuery.of(buildContext)
                                                .size
                                                .height /
                                            3,
                                        color: const DigitColors().quillGray,
                                        child: Column(
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          children: [
                                            Text(
                                              organisationListModel!
                                                  .organisations!.first.name
                                                  .toString(),
                                              style: theme.mobileTheme.textTheme
                                                  .headlineMedium
                                                  ?.apply(
                                                      color: const DigitColors()
                                                          .black),
                                            ),
                                            Text(
                                              organisationListModel
                                                  .organisations!
                                                  .first
                                                  .contactDetails!
                                                  .first
                                                  .contactMobileNumber
                                                  .toString(),
                                              style: theme.mobileTheme.textTheme
                                                  .bodyMedium
                                                  ?.apply(
                                                      color: const DigitColors()
                                                          .davyGray),
                                            ),
                                          ],
                                        ),
                                      )
                                    : Container();
                              });
                        });
                      } else {
                        return Container(
                          width: MediaQuery.of(buildContext).size.width,
                          height: MediaQuery.of(buildContext).size.height / 3,
                          color: const DigitColors().quillGray,
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Text(
                                value.userDetailsModel?.userRequestModel?.name
                                        .toString() ??
                                    '',
                                style: theme
                                    .mobileTheme.textTheme.headlineMedium
                                    ?.apply(color: const DigitColors().black),
                              ),
                              Text(
                                value.userDetailsModel?.userRequestModel
                                        ?.mobileNumber
                                        .toString() ??
                                    '',
                                style: theme.mobileTheme.textTheme.bodyMedium
                                    ?.apply(
                                        color: const DigitColors().davyGray),
                              ),
                            ],
                          ),
                        );
                      }
                    },
                  );
                },
              ),
              Row(
                children: [
                  context.router.currentPath == '/home'
                      ? Container(
                          alignment: Alignment.centerLeft,
                          height: 60,
                          width: 9,
                          color: const DigitColors().burningOrange,
                        )
                      : const SizedBox.shrink(),
                  Expanded(
                    child: DigitIconTile(
                      title: AppLocalizations.of(context)
                          .translate(i18.common.home),
                      selected: context.router.currentPath == '/home',
                      icon: Icons.home,
                      onPressed: () =>
                          context.router.replace(const HomeRoute()),
                    ),
                  ),
                ],
              ),
              DigitIconTile(
                title:
                    AppLocalizations.of(context).translate(i18.common.language),
                icon: SHGIcons.language,
                content: Padding(
                  padding: const EdgeInsets.all(16),
                  child: BlocBuilder<AppInitializationBloc,
                      AppInitializationState>(
                    builder: (context, state) {
                      return state.digitRowCardItems != null &&
                              state.isInitializationCompleted
                          ? DigitRowCard(
                              onChanged: (data) async {
                                context.read<LocalizationBloc>().add(
                                      LocalizationEvent
                                          .onSpecificLoadLocalization(
                                        locale: data.value,
                                        module: widget.module,
                                        tenantId: state
                                            .initMdmsModel!
                                            .commonMastersModel!
                                            .stateInfoListModel!
                                            .first
                                            .code
                                            .toString(),
                                      ),
                                    );
                              },
                              rowItems: value.languages
                                  ?.map((e) =>
                                      DigitRowCardModel.fromJson(e.toJson()))
                                  .toList() as List<DigitRowCardModel>,
                              width: 80)
                          : const Text('');
                    },
                  ),
                ),
                onPressed: () {},
              ),
              Row(
                children: [
                  context.router.currentPath.contains('orgProfile')
                      ? Container(
                          alignment: Alignment.centerLeft,
                          height: 50,
                          width: 9,
                          color: const DigitColors().burningOrange,
                        )
                      : const SizedBox.shrink(),
                  GlobalVariables.roleType == RoleType.cbo
                      ? Expanded(
                          child: DigitIconTile(
                              title: AppLocalizations.of(context)
                                  .translate(i18.common.orgProfile),
                              selected: context.router.currentPath
                                  .contains('orgProfile'),
                              icon: Icons.perm_contact_cal_sharp,
                              onPressed: () {
                                context.router.push(const ORGProfileRoute());
                              }),
                        )
                      : const SizedBox.shrink(),
                ],
              ),
              DigitIconTile(
                  title:
                      AppLocalizations.of(context).translate(i18.common.logOut),
                  icon: Icons.logout,
                  onPressed: () {
                    context.read<AuthBloc>().add(const AuthLogoutEvent());
                  }),
            ],
          );
        },
        loading: (value) {
          return Loaders.circularLoader(context);
        },
      );
    });
  }
}
