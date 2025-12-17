import 'package:flutter/material.dart';

class CommonInfoCard extends StatelessWidget {
  final double? cardWidth;
  final  Widget widget;

  final Color color;
   const CommonInfoCard({
    super.key,
    this.cardWidth,
    required this.color,
    required this.widget
  });

  @override
  Widget build(BuildContext context) {
    return Container(

      width: cardWidth ?? MediaQuery.of(context).size.width,
     height: 50,
       decoration: BoxDecoration(
        color: color,
    border: Border.all(width: 0, color: color),
    borderRadius: const BorderRadius.all(Radius.circular(5)),
  ), 
      child: widget,
    );
  }
}
