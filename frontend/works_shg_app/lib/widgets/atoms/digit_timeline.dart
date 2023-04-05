import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class DigitTimeline extends StatelessWidget {
  final List<DigitTimelineOptions> timelineOptions;
  const DigitTimeline({super.key, required this.timelineOptions});

  @override
  Widget build(BuildContext context) {
    return Column(
        children: List.generate(
      timelineOptions.length,
      (index) => Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Icon(
                Icons.circle_rounded,
                size: 40,
                color: timelineOptions[index].isCurrentState
                    ? DigitTheme.instance.colorScheme.primary
                    : const DigitColors().davyGray,
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    timelineOptions[index].title,
                    style: const TextStyle(
                        fontSize: 16, fontWeight: FontWeight.w700),
                    textAlign: TextAlign.start,
                  ),
                  SizedBox(
                    width: MediaQuery.of(context).size.width / 2.5,
                    child: Text(
                      timelineOptions[index].subTitle ?? '',
                      style: TextStyle(
                          fontSize: 14,
                          fontWeight: FontWeight.w400,
                          color: const DigitColors().davyGray),
                    ),
                  ),
                ],
              )
            ],
          ),
          Container(
            alignment: Alignment.centerLeft,
            margin: const EdgeInsets.only(left: 20.0, right: 8.0),
            height: 60,
            width: 1,
            color: timelineOptions[index].isCurrentState
                ? const DigitColors().burningOrange
                : const DigitColors().davyGray,
          ),
        ],
      ),
    ));
  }
}

class DigitTimelineOptions {
  final String title;
  final String? subTitle;
  final bool isCurrentState;

  const DigitTimelineOptions(
      {this.title = '', this.subTitle, this.isCurrentState = false});
}
