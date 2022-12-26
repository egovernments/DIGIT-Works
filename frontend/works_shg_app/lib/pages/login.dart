import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../blocs/auth/auth.dart';
import '../blocs/localization/app_localization.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Scaffold(
      appBar: AppBar(),
      body: ScrollableContent(
        children: [
          DigitCard(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.min,
              children: [
                Text(
                  AppLocalizations.of(context).translate('CORE_COMMON_LOGIN'),
                  style: theme.textTheme.displayMedium,
                ),
                const DigitTextField(label: 'User ID'),
                const DigitTextField(label: 'Password'),
                const SizedBox(height: 16),
                BlocBuilder<AuthBloc, AuthState>(
                  builder: (context, state) => DigitElevatedButton(
                    onPressed: state.loading
                        ? null
                        : () {
                            context.read<AuthBloc>().add(
                                  const AuthLoginEvent(
                                    userId: '',
                                    password: '',
                                  ),
                                );
                          },
                    child: const Center(
                      child: Text('Login'),
                    ),
                  ),
                ),
                TextButton(
                  onPressed: () => DigitDialog.show(
                    context,
                    title: 'Forgot Password?',
                    content:
                        'Please contact the administrator if you have forgotten your password',
                    primaryActionLabel: 'OK',
                    primaryAction: () => Navigator.pop(context),
                  ),
                  child: const Center(child: Text('Forgot Password?')),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
