import 'package:flutter/services.dart';

class DecimalTextInputFormatter extends TextInputFormatter {
  final int decimalRange;

  DecimalTextInputFormatter({required this.decimalRange})
      : assert(decimalRange >= 0, 'Decimal range must be non-negative');

  @override
  TextEditingValue formatEditUpdate(
      TextEditingValue oldValue, TextEditingValue newValue) {
    if (newValue.text == '') {
      return newValue;
    }

    final int selectionIndex = newValue.selection.end;

    String newText = newValue.text;

    // Validate decimal places
    if (newText.contains('.') &&
        newText.substring(newText.indexOf('.') + 1).length > decimalRange) {
      return oldValue;
    }

    // Prevent leading dot
    if (newText.startsWith('.')) {
      newText = '0$newText';
    }

    return TextEditingValue(
      text: newText,
      selection: TextSelection.collapsed(offset: selectionIndex),
    );
  }
}
