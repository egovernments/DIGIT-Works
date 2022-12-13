import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class DigitDialog extends StatelessWidget {
  final Widget title;
  final Widget? content;
  final EdgeInsets titlePadding;
  final EdgeInsets contentPadding;
  final String primaryActionLabel;
  final VoidCallback? primaryAction;
  final String? secondaryActionLabel;
  final VoidCallback? secondaryAction;

  const DigitDialog({
    super.key,
    this.content,
    required this.title,
    required this.primaryActionLabel,
    this.primaryAction,
    this.titlePadding = const EdgeInsets.fromLTRB(8, 16, 8, 8),
    this.contentPadding = const EdgeInsets.fromLTRB(8, 8, 8, 8),
    this.secondaryActionLabel,
    this.secondaryAction,
  });

  static Future<T?> show<T>(
    BuildContext context, {
    required String title,
    required String content,
    required String primaryActionLabel,
    String? secondaryActionLabel,
    VoidCallback? primaryAction,
    VoidCallback? secondaryAction,
  }) =>
      showDialog<T>(
        context: context,
        barrierColor: DigitTheme.instance.colors.black.withOpacity(0.7),
        builder: (context) => DigitDialog(
          title: Text(title, textAlign: TextAlign.left),
          content: Text(content, textAlign: TextAlign.left),
          primaryActionLabel: primaryActionLabel,
          primaryAction: primaryAction,
          secondaryActionLabel: secondaryActionLabel,
          secondaryAction: secondaryAction,
        ),
      );

  @override
  Widget build(BuildContext context) => AlertDialog(
        title: title,
        content: content,
        actionsAlignment: MainAxisAlignment.spaceBetween,
        actions: <Widget>[
          DigitElevatedButton(
            onPressed: primaryAction,
            child: Center(child: Text(primaryActionLabel)),
          ),
          if (secondaryActionLabel != null)
            TextButton(
              onPressed: secondaryAction,
              child: Center(child: Text(secondaryActionLabel!)),
            ),
        ],
        titlePadding: titlePadding,
        contentPadding: contentPadding,
      );
}
