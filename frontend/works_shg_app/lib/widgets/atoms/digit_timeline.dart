import 'package:digit_components/digit_components.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class DigitTimeline extends StatelessWidget {
  final List<DigitTimelineOptions> timelineOptions;
  const DigitTimeline({super.key, required this.timelineOptions});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.max,
      children: [
        SizedBox(
          child: Column(
              children: List.generate(
                  timelineOptions.length,
                  (index) => Column(
                        mainAxisAlignment: MainAxisAlignment.end,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Icon(
                            Icons.circle_rounded,
                            size: 25,
                            color: timelineOptions[index].isCurrentState
                                ? DigitTheme.instance.colorScheme.primary
                                : const DigitColors().cloudGray,
                          ),
                          index == timelineOptions.length - 1
                              ? const SizedBox.shrink()
                              : Container(
                                  alignment: Alignment.centerLeft,
                                  margin: const EdgeInsets.only(
                                    left: 12.0,
                                  ),
                                  height: 60,
                                  width: 2,
                                  color: timelineOptions[index].isCurrentState
                                      ? const DigitColors().burningOrange
                                      : const DigitColors().cloudGray,
                                ),
                        ],
                      ))),
        ),
        Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: List.generate(
              timelineOptions.length,
              (index) => Padding(
                    padding:
                        const EdgeInsets.only(left: 16.0, bottom: 8.0, top: 0),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      mainAxisSize: MainAxisSize.max,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          timelineOptions[index].title,
                          style: const TextStyle(
                              fontSize: 16, fontWeight: FontWeight.w400),
                          textAlign: TextAlign.start,
                        ),
                        timelineOptions[index].subTitle != null &&
                                timelineOptions[index].subTitle!.isNotEmpty
                            ? SizedBox(
                                width: 200,
                                child: Text(
                                  timelineOptions[index].subTitle ?? '',
                                  style: TextStyle(
                                      fontSize: 14,
                                      fontWeight: FontWeight.w400,
                                      color: const DigitColors().davyGray),
                                ),
                              )
                            : const SizedBox.shrink(),
                        timelineOptions[index].assignee != null &&
                                timelineOptions[index].assignee!.isNotEmpty
                            ? SizedBox(
                                width: 200,
                                child: Text(
                                  timelineOptions[index].assignee ?? '',
                                  style: TextStyle(
                                      fontSize: 14,
                                      fontWeight: FontWeight.w400,
                                      color: const DigitColors().davyGray),
                                ),
                              )
                            : const SizedBox.shrink(),
                        timelineOptions[index].mobileNumber != null &&
                                timelineOptions[index].mobileNumber!.isNotEmpty
                            ? Row(
                                mainAxisAlignment: MainAxisAlignment.start,
                                children: [
                                  timelineOptions[index].mobileNumber != null &&
                                          timelineOptions[index]
                                              .mobileNumber!
                                              .isNotEmpty
                                      ? const Icon(
                                          Icons.phone,
                                          size: 16,
                                        )
                                      : const Text(''),
                                  timelineOptions[index].mobileNumber != null
                                      ? SizedBox(
                                          width: 200,
                                          child: Text(
                                            timelineOptions[index]
                                                            .mobileNumber !=
                                                        null &&
                                                    timelineOptions[index]
                                                        .mobileNumber!
                                                        .isNotEmpty
                                                ? timelineOptions[index]
                                                    .mobileNumber
                                                    .toString()
                                                : '',
                                            style: TextStyle(
                                                fontSize: 14,
                                                fontWeight: FontWeight.w400,
                                                color: const DigitColors()
                                                    .davyGray),
                                          ),
                                        )
                                      : const SizedBox.shrink(),
                                ],
                              )
                            : const SizedBox.shrink(),
                        SizedBox(
                            height: index != timelineOptions.length - 1
                                ? timelineOptions[index].mobileNumber != null &&
                                        timelineOptions[index].assignee != null
                                    ? 18
                                    : timelineOptions[index].mobileNumber ==
                                                null &&
                                            timelineOptions[index].assignee !=
                                                null
                                        ? 36
                                        : timelineOptions[index].mobileNumber !=
                                                    null &&
                                                timelineOptions[index]
                                                        .assignee ==
                                                    null
                                            ? 32
                                            : kIsWeb
                                                ? 40
                                                : 48
                                : 0)
                      ],
                    ),
                  )),
        )
      ],
    );
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
