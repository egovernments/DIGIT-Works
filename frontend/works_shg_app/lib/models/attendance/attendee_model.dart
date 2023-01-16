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
