import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:reactive_forms/reactive_forms.dart';

class DigitTextFormField extends StatelessWidget {
  final bool? readOnly;
  final String formControlName;
  final String? hint;
  final Widget? suffix;
  final bool? isRequired;
  final int? minLines;
  final int? maxLength;
  final TextInputType? keyboardType;
  final FocusNode? focusNode;
  final VoidCallback? onTap;
  final bool? obscureText;
  final String label;
  final TextCapitalization? textCapitalization;

  const DigitTextFormField({
    super.key,
    this.readOnly,
    required this.formControlName,
    this.hint,
    this.suffix,
    this.minLines,
    this.maxLength,
    this.onTap,
    this.isRequired,
    this.keyboardType,
    this.focusNode,
    this.textCapitalization,
    this.obscureText,
    required this.label,
  });

  @override
  Widget build(BuildContext context) {
    return LabeledField(
        label: label,
        child: ReactiveTextField(
          readOnly: readOnly ?? false,
          formControlName: formControlName,
          maxLength: maxLength,
          autofocus: false,
          textCapitalization: textCapitalization ?? TextCapitalization.none,
          minLines: minLines,
          obscureText: obscureText ?? false,
          focusNode: focusNode,
          keyboardType: keyboardType ?? TextInputType.text,
          decoration: InputDecoration(
            labelText: hint,
            suffixIconConstraints: const BoxConstraints(
              maxHeight: 48,
              maxWidth: 48,
            ),
            suffixIcon: suffix == null
                ? null
                : InkWell(
                    onTap: onTap,
                    child: suffix,
                  ),
          ),
        ));
  }
}
