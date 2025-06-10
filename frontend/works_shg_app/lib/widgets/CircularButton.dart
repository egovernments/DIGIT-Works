import 'package:flutter/material.dart';

class CircularButton extends StatelessWidget {
  final IconData icon;
  final double size;
  final Color color;
  final double index;
  final bool isNotGreyed;
  final void Function()? onTap;
  final bool viewOnly;
  //set index -1 to not select the Circular button
  //set index 1 to select half of the Circular button
  //set index 2 to completely select the Circular button
  // set isNotGreyed to true if your background is white else false

  const CircularButton(
      {super.key,
      required this.icon,
      required this.size,
      required this.color,
      required this.index,
      required this.isNotGreyed,
      this.onTap,
      this.viewOnly = false});

  @override
  Widget build(BuildContext context) {
    return Align(
        alignment: Alignment.centerLeft,
        child: GestureDetector(
            onTap: viewOnly ? null : onTap,
            child: Container(
                height: 30,
                width: 30,
                margin: const EdgeInsets.all(2.0),
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  border: Border.all(
                    width: 2,
                    color: onTap != null
                        ? index.isNegative
                            ? Colors.black
                            : index == 0.0
                                ? const Color.fromRGBO(212, 53, 28, 1)
                                : index == 0.5
                                    ? const Color.fromRGBO(244, 169, 56, 1)
                                    : const Color.fromRGBO(0, 112, 60, 1)
                        : const Color.fromRGBO(149, 148, 148, 1),
                    style: BorderStyle.solid,
                  ),
                ),
                child: Align(
                  alignment: Alignment.center,
                  child: Text(
                    index.isNegative || onTap == null
                        ? ''
                        : index == 0.0
                            ? 'A'
                            : index == 0.5
                                ? 'H'
                                : 'F',
                    style: TextStyle(
                        fontSize: 16,
                        color: index.isNegative || onTap == null
                            ? null
                            : index == 0.0
                                ? const Color.fromRGBO(212, 53, 28, 1)
                                : index == 0.5
                                    ? const Color.fromRGBO(244, 169, 56, 1)
                                    : const Color.fromRGBO(0, 112, 60, 1),
                        fontWeight: FontWeight.bold),
                    textAlign: TextAlign.center,
                  ),
                ))));
  }
}
