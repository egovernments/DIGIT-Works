import 'package:freezed_annotation/freezed_annotation.dart';

part 'muster_submission.freezed.dart';
part 'muster_submission.g.dart';

@freezed
class MusterSubmissionList with _$MusterSubmissionList {
  const factory MusterSubmissionList({
    @JsonKey(name: 'CBOMusterSubmission')
        List<MusterSubmission>? musterSubmission,
  }) = _MusterSubmissionList;

  factory MusterSubmissionList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterSubmissionListFromJson(json);
}

@freezed
class MusterSubmission with _$MusterSubmission {
  const factory MusterSubmission({
    required String code,
    required String value,
    required bool active,
  }) = _MusterSubmission;

  factory MusterSubmission.fromJson(Map<String, dynamic> json) =>
      _$MusterSubmissionFromJson(json);
}
