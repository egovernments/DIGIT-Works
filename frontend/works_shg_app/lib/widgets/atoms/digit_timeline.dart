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
                    : const DigitColors().cloudGray,
              ),
              Padding(
                padding: const EdgeInsets.all(4.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      timelineOptions[index].title,
                      style: const TextStyle(
                          fontSize: 16, fontWeight: FontWeight.w400),
                      textAlign: TextAlign.start,
                    ),
                    SizedBox(
                      width: MediaQuery.of(context).size.width / 2,
                      child: Text(
                        timelineOptions[index].subTitle ?? '',
                        style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.w400,
                            color: const DigitColors().davyGray),
                      ),
                    ),
                    SizedBox(
                      width: MediaQuery.of(context).size.width / 2.5,
                      child: Text(
                        timelineOptions[index].assignee ?? '',
                        style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.w400,
                            color: const DigitColors().davyGray),
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        timelineOptions[index].mobileNumber != null &&
                                timelineOptions[index].mobileNumber!.isNotEmpty
                            ? const Icon(Icons.phone)
                            : const Text(''),
                        SizedBox(
                          width: MediaQuery.of(context).size.width / 2.5,
                          child: Text(
                            timelineOptions[index].mobileNumber != null &&
                                    timelineOptions[index]
                                        .mobileNumber!
                                        .isNotEmpty
                                ? timelineOptions[index].mobileNumber.toString()
                                : '',
                            style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.w400,
                                color: const DigitColors().davyGray),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              )
            ],
          ),
          index == timelineOptions.length - 1
              ? Container()
              : Container(
                  alignment: Alignment.centerLeft,
                  margin: const EdgeInsets.only(
                    left: 20.0,
                  ),
                  height: 70,
                  width: 1,
                  color: timelineOptions[index].isCurrentState
                      ? const DigitColors().burningOrange
                      : const DigitColors().cloudGray,
                ),
        ],
      ),
    ));
  }
}

class DigitTimelineOptions {
  final String title;
  final String? subTitle;
  final String? assignee;
  final String? mobileNumber;
  final bool isCurrentState;

  const DigitTimelineOptions(
      {this.title = '',
      this.subTitle,
      this.mobileNumber,
      this.assignee,
      this.isCurrentState = false});
}
