import 'package:flutter/material.dart';

class BackgroundContainer extends StatelessWidget {
  final Widget widget;
  final String bannerURL;
  const BackgroundContainer(this.widget, this.bannerURL, {super.key});
  @override
  Widget build(BuildContext context) {
    return Container(
        decoration: BoxDecoration(
          color: const Color(0xff0B4B66),
          image: DecorationImage(
            colorFilter: ColorFilter.mode(
                Colors.black.withOpacity(0.2), BlendMode.dstATop),
            image: NetworkImage(bannerURL),
            fit: BoxFit.cover,
          ),
        ),
        child: widget);
  }
}
