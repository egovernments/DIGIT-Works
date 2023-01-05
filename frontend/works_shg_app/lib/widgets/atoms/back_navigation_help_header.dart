import 'package:flutter/material.dart';

import '../../router/app_router.dart';

class BackNavigationHelpHeaderWidget extends StatelessWidget {
  final bool showHelp;
  final bool showBackNavigation;

  const BackNavigationHelpHeaderWidget({
    super.key,
    this.showHelp = true,
    this.showBackNavigation = true,
  });

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Row(
      children: [
        if (context.router.canPop() && showBackNavigation) ...[
          const SizedBox(width: 8),
          TextButton(
            style: TextButton.styleFrom(
              foregroundColor: theme.colorScheme.onBackground,
              padding: EdgeInsets.zero,
            ),
            onPressed: () => context.router.pop(),
            child: Row(
              children: const [
                Icon(Icons.arrow_left_sharp),
                Text('Back'),
              ],
            ),
          ),
        ],
        const Spacer(),
        if (showHelp) ...[
          TextButton(
            style: TextButton.styleFrom(padding: EdgeInsets.zero),
            onPressed: null,
            child: Row(
              children: const [
                Text('Help'),
                Icon(Icons.help_outline_outlined),
              ],
            ),
          ),
          const SizedBox(width: 8),
        ],
      ],
    );
  }
}
