import 'package:auto_route/auto_route.dart';
import 'package:digit_components/digit_components.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/router/app_router.dart';

import '../../blocs/app_config/app_config.dart';
import '../../models/app_config/app_config_model.dart';

class SideBar extends StatelessWidget {
  final String? userName;
  final String? mobileNumber;
  const SideBar(
      {super.key, required this.userName, required this.mobileNumber});
  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return ScrollableContent(
      footer: const PoweredByDigit(),
      children: [
        SizedBox(
          height: MediaQuery.of(context).size.height / 3,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                userName.toString(),
                style: theme.textTheme.displayMedium,
              ),
              Text(
                mobileNumber.toString(),
                style: theme.textTheme.labelSmall,
              ),
            ],
          ),
        ),
        DigitIconTile(
          title: 'Home',
          icon: Icons.home,
          onPressed: () => context.router.replace(const HomeRoute()),
        ),
        DigitIconTile(
          title: 'Language',
          icon: Icons.language,
          content: Padding(
            padding: const EdgeInsets.all(16),
            child: BlocBuilder<ApplicationConfigBloc, ApplicationConfigState>(
              builder: (context, state) {
                List<Languages>? languageList =
                    state.appConfigDetail?.configuration?.appConfig.languages;

                return state.appConfigDetail?.configuration?.appConfig
                            .languages !=
                        null
                    ? DigitRowCard(
                        onPressed: (data) {},
                        list: languageList
                            ?.map((e) => DigitRowCardModel.fromJson(e.toJson()))
                            .toList() as List<DigitRowCardModel>,
                        width: 75,
                      )
                    : const Text('');
              },
            ),
          ),
          onPressed: () {},
        ),
        DigitIconTile(
            title: 'Logout',
            icon: Icons.logout,
            onPressed: () {
              context.read<AuthBloc>().add(AuthLogoutEvent());
            }),
      ],
    );
  }
}
