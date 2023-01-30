// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

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
    String? individualId,
    String? monEntryId,
    String? monExitId,
    @Default(0.0) double? monIndex,
    String? tueEntryId,
    String? tueExitId,
    @Default(0.0) double? tueIndex,
    String? wedEntryId,
    String? wedExitId,
    @Default(0.0) double? wedIndex,
    String? thuEntryId,
    String? thuExitId,
    @Default(0.0) double? thursIndex,
    String? friEntryId,
    String? friExitId,
    @Default(0.0) double? friIndex,
    String? satEntryId,
    String? satExitId,
    @Default(0.0) double? satIndex,
    String? sunEntryId,
    String? sunExitId,
    @Default(0.0) double? sunIndex,
  }) = _AttendeesTrackList;

  factory AttendeesTrackList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeesTrackListFromJson(json);
}
