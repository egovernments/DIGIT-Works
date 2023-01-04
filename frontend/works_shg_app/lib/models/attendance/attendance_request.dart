import 'package:freezed_annotation/freezed_annotation.dart';

part 'attendance_request.g.dart';

@JsonSerializable()
class AttendanceRequest {
  @JsonKey(name: "tenantId")
  String? tenantId;

  @JsonKey(name: "registerNumber")
  String? registerNumber;

  @JsonKey(name: "name")
  String? name;

  @JsonKey(name: "startDate")
  String? startDate;

  @JsonKey(name: "endDate")
  String? endDate;

  @JsonKey(name: "staff")
  List? staff;

  @JsonKey(name: "attendees")
  List? attendees;

  AttendanceRequest();

  factory AttendanceRequest.fromJson(Map<String, dynamic> json) =>
      _$AttendanceRequestFromJson(json);

  Map<String, dynamic> toJson() => _$AttendanceRequestToJson(this);
}
