// import 'package:digit_components/theme/digit_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:flutter/material.dart';

class CustomInfoCard extends StatelessWidget {
  final String title;
  final String description;
  final Color? backgroundColor;
  final IconData? icon;
  final TextStyle? titleStyle;
  final Color? iconColor;
  final EdgeInsets? padding;
  final EdgeInsets? margin;
  final TextStyle? descStyle;
  final Widget? child;

  const CustomInfoCard(
      {super.key,
      required this.title,
      required this.description,
      this.titleStyle,
      this.backgroundColor,
      this.iconColor,
      this.icon,
      this.padding,
      this.margin,
      this.descStyle,
      this.child});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Container(
      margin: margin ?? EdgeInsets.all( Theme.of(context).spacerTheme.spacer2),
      padding: padding ?? const EdgeInsets.all(8),
      decoration: ShapeDecoration(
        shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(4)),
        ),
        color: backgroundColor ?? theme.colorScheme.tertiaryContainer,
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Padding(
                padding: padding ?? const EdgeInsets.all(8),
                child: Icon(
                  icon ?? Icons.info,
                  color: iconColor ?? theme.colorScheme.surfaceTint,
                ),
              ),
              Expanded(
                child: Text(
                  title,
                  style: titleStyle ?? theme.textTheme.headlineMedium,
                ),
              )
            ],
          ),
          Padding(
            padding:  EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
            child: Text(
              description,
              style: theme.textTheme.bodyLarge,
              textAlign: TextAlign.start,
            ),
          ),
          Padding(
            padding:  EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
            child: child ?? const SizedBox.shrink(),
          )
        ],
      ),
    );
  }
}
