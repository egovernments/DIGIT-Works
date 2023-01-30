import 'package:digit_components/widgets/labeled_field.dart';
import 'package:flutter/material.dart';

class DigitTextField extends StatelessWidget {
  final String label;
  final TextEditingController? controller;
  final Widget? suffixIcon;
  final bool readOnly;
  const DigitTextField(
      {super.key,
      required this.label,
      this.controller,
      this.suffixIcon,
      this.readOnly = false});

  @override
  Widget build(BuildContext context) {
    return LabeledField(
      label: label,
      child: TextField(
        controller: controller,
        readOnly: readOnly,
        decoration: InputDecoration(
          suffixIconConstraints: const BoxConstraints(
            maxHeight: 48,
            maxWidth: 48,
          ),
          suffixIcon: suffixIcon == null ? null : suffixIcon,
        ),
      ),
    );
  }
}
