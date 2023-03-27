import 'package:flutter/material.dart';

class InfoCard extends StatelessWidget {
  final String title;
  final String description;
  final Color backGroundColor;
  final IconData? icon;
  final Color? iconColor;
  final EdgeInsets? padding;
  final TextStyle? titleStyle;
  final TextStyle? descStyle;
  const InfoCard(
      {super.key,
      required this.title,
      required this.description,
      required this.backGroundColor,
      this.iconColor,
      this.icon,
      this.padding,
      this.titleStyle,
      this.descStyle});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(color: Color.fromRGBO(199, 224, 241, 1)),
      width: MediaQuery.of(context).size.width,
      margin: const EdgeInsets.all(8.0),
      padding: const EdgeInsets.all(8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Wrap(
            spacing: 5,
            crossAxisAlignment: WrapCrossAlignment.center,
            children: [
              const Icon(Icons.info, color: Color.fromRGBO(52, 152, 219, 1)),
              Text(title,
                  style: titleStyle ??
                      const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w700,
                          color: Color.fromRGBO(52, 152, 219, 1)))
            ],
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8),
            child: Text(
              description,
              style: descStyle ??
                  const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w400,
                      height: 1.5,
                      color: Color.fromRGBO(52, 152, 219, 1)),
            ),
          )
        ],
      ),
    );
  }
}
