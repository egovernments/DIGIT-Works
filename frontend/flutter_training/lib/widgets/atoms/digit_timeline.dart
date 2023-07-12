import 'package:digit_components/theme/colors.dart';
import 'package:digit_components/theme/digit_theme.dart';
import 'package:easy_stepper/easy_stepper.dart';
import 'package:timelines/timelines.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:flutter_training/widgets/atoms/attachments.dart';

import '../../models/file_store/file_store_model.dart';

class DigitTimeline extends StatelessWidget {
  final double stepRadius;
  final List<DigitTimelineOptions> timelineOptions;
  const DigitTimeline({
    super.key,
    required this.timelineOptions,
    this.stepRadius = 12,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: List.generate(
          timelineOptions.length,
          (index) => IntrinsicHeight(
                child: Row(
                  children: [
                    TimelineNode(
                      indicatorPosition: 0,
                      indicator: Icon(
                        Icons.circle,
                        color: index == 0
                            ? const DigitColors().burningOrange
                            : const DigitColors().cloudGray,
                      ),
                      startConnector: index == 0
                          ? const SolidLineConnector(
                              color: Colors.white,
                            )
                          : SolidLineConnector(
                              color: const DigitColors().cloudGray,
                            ),
                      endConnector: index == timelineOptions.length - 1
                          ? const SolidLineConnector(
                              color: Colors.white,
                            )
                          : SolidLineConnector(
                              color: const DigitColors().cloudGray,
                            ),
                    ),
                    Container(
                      padding: const EdgeInsets.only(left: 8.0),
                      width: MediaQuery.of(context).size.width / 1.5,
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            timelineOptions[index].title,
                            style: const TextStyle(
                                fontSize: 16, fontWeight: FontWeight.w400),
                            textAlign: TextAlign.left,
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
                                  timelineOptions[index]
                                      .mobileNumber!
                                      .isNotEmpty
                              ? Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    timelineOptions[index].mobileNumber !=
                                                null &&
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
                          timelineOptions[index].comments != null &&
                                  timelineOptions[index].comments!.isNotEmpty
                              ? Text(
                                  timelineOptions[index].comments ?? '',
                                  style: TextStyle(
                                      fontSize: 14,
                                      fontWeight: FontWeight.w400,
                                      color: const DigitColors().davyGray),
                                )
                              : const SizedBox.shrink(),
                          timelineOptions[index].documents != null &&
                                  timelineOptions[index].documents!.isNotEmpty
                              ? Attachments(
                                  AppLocalizations.of(context)
                                      .translate(i18.common.attachments),
                                  timelineOptions[index].documents,
                                  labelStyle: DigitTheme.instance.mobileTheme
                                      .textTheme.headlineSmall,
                                )
                              : const SizedBox.shrink(),
                          const SizedBox(
                            height: 16.0,
                          )
                        ],
                      ),
                    ),
                  ],
                ),
              )),
    );
  }
}

class DigitTimelineOptions {
  final String title;
  final String? subTitle;
  final String? assignee;
  final String? mobileNumber;
  final String? comments;
  final bool isCurrentState;
  final List<FileStoreModel>? documents;

  const DigitTimelineOptions(
      {this.title = '',
      this.subTitle,
      this.mobileNumber,
      this.assignee,
      this.comments,
      this.isCurrentState = false,
      this.documents});
}
