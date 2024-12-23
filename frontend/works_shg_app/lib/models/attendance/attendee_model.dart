// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

import '../muster_rolls/muster_roll_model.dart';
import 'attendance_registry_model.dart';

part 'attendee_model.freezed.dart';
part 'attendee_model.g.dart';

@freezed
class AttendeeModel with _$AttendeeModel {
  const factory AttendeeModel({
    @JsonKey(name: 'attendanceRegister')
        List<AttendanceRegister>? attendanceRegister,
  }) = _AttendeeModel;

  factory AttendeeModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeeModelFromJson(json);
}

@freezed
class AttendeeTrackListModel with _$AttendeeTrackListModel {
  const factory AttendeeTrackListModel({
    @JsonKey(ignore: true) @Default([]) List<AttendeesTrackList>? attendeeList,
  }) = _AttendeeTrackListModel;

  factory AttendeeTrackListModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeeTrackListModelFromJson(json);
}

@freezed
class AttendeesTrackList with _$AttendeesTrackList {
  const factory AttendeesTrackList({
    String? name,
    String? aadhaar,
    String? gender,
    String? individualGaurdianName,
    String? individualId,
    String? id,
    String? skill,
    String? monEntryId,
    String? monExitId,
    @Default(-1) double? monIndex,
    String? tueEntryId,
    String? tueExitId,
    @Default(-1) double? tueIndex,
    String? wedEntryId,
    String? wedExitId,
    @Default(-1) double? wedIndex,
    String? thuEntryId,
    String? thuExitId,
    @Default(-1) double? thursIndex,
    String? friEntryId,
    String? friExitId,
    List<String>? skillCodeList,
    @Default(-1) double? friIndex,
    String? satEntryId,
    String? satExitId,
    @Default(-1) double? satIndex,
    String? sunEntryId,
    String? sunExitId,
    @Default(-1) double? sunIndex,
    @JsonKey(name: 'auditDetails') AuditDetails? auditDetails,
  }) = _AttendeesTrackList;

  factory AttendeesTrackList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeesTrackListFromJson(json);
}

@freezed
class AttendeeAuditDetails with _$AttendeeAuditDetails {
  const factory AttendeeAuditDetails(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime}) = _AttendeeAuditDetails;

  factory AttendeeAuditDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeeAuditDetailsFromJson(json);
}
