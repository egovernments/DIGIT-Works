import 'package:flutter/material.dart';

class CircularButton extends StatelessWidget {
  final IconData icon;
  final double size;
  final Color color;
  final double index;
  final bool isNotGreyed;
  final void Function()? onTap;
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
      this.onTap});

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: GestureDetector(
          onTap: onTap,
          child: Container(
              height: 30,
              width: 30,
              margin: const EdgeInsets.all(2.0),
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                border: Border.all(
                  width: 2,
                  color: color,
                  style: BorderStyle.solid,
                ),
              ),
              child: ShaderMask(
                blendMode: BlendMode.srcATop,
                shaderCallback: (Rect rect) {
                  return LinearGradient(
                    stops: const [0, 0.5, 0.5],
                    colors: [
                      index == 0.0 ? Colors.white : color,
                      index == 0.0 ? Colors.white : color,
                      index == 0.0 ? Colors.white : color.withOpacity(0)
                    ],
                  ).createShader(rect);
                },
                child: SizedBox(
                  width: size,
                  height: size,
                  child: Icon(icon,
                      size: size,
                      color: index == 0.0
                          ? Colors.transparent
                          : index == 0.5
                              ? isNotGreyed
                                  ? Colors.white
                                  : const Color.fromRGBO(238, 238, 238, 1)
                              : color),
                ),
              ))),
    );
  }
}
