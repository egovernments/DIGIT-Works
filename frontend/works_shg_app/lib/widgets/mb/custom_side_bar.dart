import 'package:digit_ui_components/widgets/helper_widget/digit_profile.dart';
import 'package:digit_ui_components/widgets/molecules/hamburger.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/app_initilization/app_initilization.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/common_methods.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/loaders.dart';

class MySideBar extends StatefulWidget {
  const MySideBar({super.key});

  @override
  State<MySideBar> createState() => _MySideBarState();
}

class _MySideBarState extends State<MySideBar> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LocalizationBloc, LocalizationState>(
      builder: (context, state) {
        return state.maybeMap(
          loading: (value) {
            return Loaders.circularLoader(context);
          },
          orElse: () => const SizedBox.shrink(),
          loaded: (value) {
            return BlocBuilder<AppInitializationBloc, AppInitializationState>(
              builder: (context, stateInit) {
                return BlocBuilder<AuthBloc, AuthState>(
                  builder: (context, stateAuth) {
                    return stateAuth.maybeMap(
                      orElse: () => const SizedBox.shrink(),
                      loaded: (authValue) {
                        return GlobalVariables.roleType == RoleType.cbo
                            ? SideBar(
                                profile: ProfileWidget(
                                  title: authValue.userDetailsModel
                                          ?.userRequestModel?.name
                                          .toString() ??
                                      '',
                                  description: authValue.userDetailsModel
                                          ?.userRequestModel?.mobileNumber
                                          .toString() ??
                                      '',
                                  //leading: const SizedBox.shrink(),
                                ),
                                sidebarItems: [
                                  SidebarItem(
                                    title: AppLocalizations.of(context)
                                        .translate(i18.common.home),
                                    icon: Icons.home,
                                    onPressed: () {
                                       Navigator.of(context).pop();
                                      // context.router.replace(const HomeRoute());
//  Navigator.of(
//                                       context,
//                                       rootNavigator: true,
//                                     ).popUntil(
//                                       (route) => route is! PopupRoute,
//                                     );
                                      context.router.replaceAll([const HomeRoute()]);
                                      //Navigator.of(context).pop();
                                      // Navigate to Home
                                    },
                                  ),
                                  SidebarItem(
                                    isSearchEnabled: false,
                                    children: stateInit.digitRowCardItems !=
                                                null &&
                                            stateInit.isInitializationCompleted
                                        ? List.generate(
                                            value.languages != null
                                                ? value.languages!.length
                                                : 0,
                                            (index) => SidebarItem(
                                                isSearchEnabled: false,
                                                initiallySelected: value
                                                    .languages![index]
                                                    .isSelected,
                                                title: value
                                                    .languages![index].label,
                                                // icon: Icons.language,
                                                onPressed: () {
                                                  context
                                                      .read<LocalizationBloc>()
                                                      .add(
                                                        LocalizationEvent
                                                            .onSpecificLoadLocalization(
                                                          locale: value
                                                              .languages![index]
                                                              .value,
                                                          module: CommonMethods
                                                              .getLocaleModules(),
                                                          tenantId: stateInit
                                                              .initMdmsModel!
                                                              .commonMastersModel!
                                                              .stateInfoListModel!
                                                              .first
                                                              .code
                                                              .toString(),
                                                        ),
                                                      );
                                                }),
                                          ).toList()
                                        : [],
                                    title: 'Language',
                                    //   icon: Icons.language,
                                    onPressed: () {
                                      // Implement language change
                                    },
                                  ),
                                  SidebarItem(
                                    title: AppLocalizations.of(context)
                                        .translate(i18.common.orgProfile),
                                    icon: Icons.person,
                                    onPressed: () {
                                      Navigator.of(context).pop();

                                      context.router
                                          .push(const ORGProfileRoute());
                                      // Navigate to Profile
                                    },
                                  ),
                                ],
                                logOutButtonLabel: AppLocalizations.of(context)
                                    .translate(i18.common.logOut),
                                onLogOut: () {
                                  context
                                      .read<AuthBloc>()
                                      .add(const AuthLogoutEvent());
                                },
                              )
                            : SideBar(
                                profile: ProfileWidget(
                                  title: authValue.userDetailsModel
                                          ?.userRequestModel?.name
                                          .toString() ??
                                      '',
                                  description: authValue.userDetailsModel
                                          ?.userRequestModel?.mobileNumber
                                          .toString() ??
                                      '',
                                 // leading: const SizedBox.shrink(),
                                ),
                                sidebarItems: [
                                  SidebarItem(
                                    title: AppLocalizations.of(context)
                                        .translate(i18.common.home),
                                    icon: Icons.home,
                                    onPressed: () {
                                        Navigator.of(context).pop();
                                      // context.router.replace(const HomeRoute());
                                      
                                       context.router.replaceAll([const HomeRoute()]);
                                      // Navigator.of(context).pop();
                                      // Navigate to Home
                                    },
                                  ),
                                  SidebarItem(
                                    isSearchEnabled: false,
                                    children: stateInit.digitRowCardItems !=
                                                null &&
                                            stateInit.isInitializationCompleted
                                        ? List.generate(
                                            value.languages != null
                                                ? value.languages!.length
                                                : 0,
                                            (index) => SidebarItem(
                                                isSearchEnabled: false,
                                                initiallySelected: value
                                                    .languages![index]
                                                    .isSelected,
                                                title: value
                                                    .languages![index].label,
                                                // icon: Icons.language,
                                                onPressed: () {
                                                  context
                                                      .read<LocalizationBloc>()
                                                      .add(
                                                        LocalizationEvent
                                                            .onSpecificLoadLocalization(
                                                          locale: value
                                                              .languages![index]
                                                              .value,
                                                          module: CommonMethods
                                                              .getLocaleModules(),
                                                          tenantId: stateInit
                                                              .initMdmsModel!
                                                              .commonMastersModel!
                                                              .stateInfoListModel!
                                                              .first
                                                              .code
                                                              .toString(),
                                                        ),
                                                      );
                                                }),
                                          ).toList()
                                        : [],
                                    title: 'Language',
                                    //   icon: Icons.language,
                                    onPressed: () {
                                      // Implement language change
                                    },
                                  ),
                                ],
                                logOutButtonLabel: AppLocalizations.of(context)
                                    .translate(i18.common.logOut),
                                onLogOut: () {
                                  context
                                      .read<AuthBloc>()
                                      .add(const AuthLogoutEvent());
                                },
                              );
                      },
                    );
                  },
                );
              },
            );
          },
        );
      },
    );
  }
}
