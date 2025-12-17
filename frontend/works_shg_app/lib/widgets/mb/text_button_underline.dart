import 'package:digit_components/theme/colors.dart';
import 'package:digit_components/theme/digit_theme.dart';
import 'package:flutter/material.dart';


class CommonTextButtonUnderline extends StatelessWidget {

  final String label;
  final VoidCallback onPressed;
  const CommonTextButtonUnderline({
    super.key, required this.label, required this.onPressed,
    
  });

 

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 200,
      child: TextButton(
        style: TextButton.styleFrom(
            padding: EdgeInsets.zero,
            minimumSize: const Size(50, 30),
            tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            alignment: Alignment.centerLeft),
        onPressed: onPressed,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Text(
              label,
              style:
                  DigitTheme.instance.mobileTheme.textTheme.bodyLarge!.copyWith(
                color: const DigitColors().burningOrange,
                decoration: TextDecoration.underline,
                decorationColor: const DigitColors().burningOrange,
                decorationThickness: 2,
              ),
            ),
            const Icon(Icons.arrow_forward_outlined)
          ],
        ),
      ),
    );
  }
}