import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class DigitOTPField extends StatefulWidget {
  final int numberOfFields;
  final double fieldWidth;
  final double fieldHeight;
  final Function(String) onChanged;

  const DigitOTPField(
      {super.key,
      this.numberOfFields = 6,
      this.fieldWidth = 35,
      this.fieldHeight = 40,
      required this.onChanged});

  @override
  State<StatefulWidget> createState() {
    return _DigitOTPField();
  }
}

class _DigitOTPField extends State<DigitOTPField> {
  List<FocusNode> focusNodes = [];
  List<TextEditingController> controllers = [];

  @override
  void initState() {
    super.initState();
    for (int i = 0; i < widget.numberOfFields; i++) {
      focusNodes.add(FocusNode());
      controllers.add(TextEditingController());
    }
  }

  @override
  void dispose() {
    for (int i = 0; i < widget.numberOfFields; i++) {
      focusNodes[i].dispose();
      controllers[i].dispose();
    }
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: List.generate(widget.numberOfFields, (index) {
        return Container(
          width: widget.fieldWidth,
          height: widget.fieldHeight,
          margin: const EdgeInsets.symmetric(horizontal: 10),
          child: TextFormField(
            autofocus: index == 0,
            keyboardType: TextInputType.number,
            textAlign: TextAlign.center,
            obscureText: false,
            focusNode: focusNodes[index],
            inputFormatters: [
              FilteringTextInputFormatter.allow(RegExp(r'^[0-9]+$')),
              CustomLengthLimitingTextInputFormatter(1),
            ],
            controller: controllers[index],
            onChanged: (value) {
              if (value.length == 1) {
                if (index != widget.numberOfFields - 1) {
                  FocusScope.of(context).requestFocus(focusNodes[index + 1]);
                } else {
                  focusNodes[index].unfocus();
                }
              } else if (value.isEmpty) {
                // Handle backspace key
                if (index != 0) {
                  FocusScope.of(context).requestFocus(focusNodes[index - 1]);
                }
              }
              widget.onChanged(getOtp());
            },
          ),
        );
      }),
    );
  }

  String getOtp() {
    String otp = '';
    for (var controller in controllers) {
      otp += controller.text;
    }
    return otp;
  }
}

class CustomLengthLimitingTextInputFormatter extends TextInputFormatter {
  final int maxLength;

  CustomLengthLimitingTextInputFormatter(this.maxLength);

  @override
  TextEditingValue formatEditUpdate(
      TextEditingValue oldValue, TextEditingValue newValue) {
    if (newValue.text.length > maxLength) {
      return oldValue;
    }
    return newValue;
  }
}
