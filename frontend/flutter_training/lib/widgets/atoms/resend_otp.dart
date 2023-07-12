import 'dart:async';

import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;

class ResendOTP extends StatefulWidget {
  final void Function()? onPressed;
  const ResendOTP({required this.onPressed, super.key});

  @override
  ResendOTPState createState() => ResendOTPState();
}

class ResendOTPState extends State<ResendOTP> {
  int _timer = 30;
  bool _canResendOTP = false;
  Timer? _timerInstance;

  @override
  void initState() {
    super.initState();
    _startTimer();
  }

  void _startTimer() {
    _timerInstance = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (_timer == 0) {
        setState(() {
          _canResendOTP = true;
        });
        _timerInstance?.cancel();
      } else {
        setState(() {
          _timer--;
        });
      }
    });
  }

  void changeState() {
    setState(() {
      _canResendOTP = false;
      _timer = 30;
    });
    _startTimer();
  }

  @override
  void dispose() {
    _timerInstance?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var localizationText =
        '${AppLocalizations.of(context).translate(i18.login.resendOTPInSec)}';
    localizationText =
        localizationText.replaceFirst('{timer}', _timer.toString());
    return GestureDetector(
      onTap: _canResendOTP
          ? () {
              changeState();
              widget.onPressed!();
            }
          : null,
      child: Text(
        _canResendOTP
            ? AppLocalizations.of(context).translate(i18.login.resendOTP)
            : localizationText,
        textAlign: TextAlign.left,
        style: TextStyle(
          color: _canResendOTP
              ? const DigitColors().burningOrange
              : DigitTheme.instance.colorScheme.shadow,
        ),
      ),
    );
  }
}
