

import '../../models/file_store/file_store_model.dart';



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
