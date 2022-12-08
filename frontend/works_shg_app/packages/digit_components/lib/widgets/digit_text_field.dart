import 'package:digit_components/widgets/labeled_field.dart';
import 'package:flutter/material.dart';

class DigitTextField extends StatelessWidget {
  final String label;
  final TextEditingController? controller;

  const DigitTextField({
    super.key,
    required this.label,
    this.controller,
  });

  @override
  Widget build(BuildContext context) {
    return LabeledField(
      label: label,
      child: TextField(controller: controller),
    );
  }
}
