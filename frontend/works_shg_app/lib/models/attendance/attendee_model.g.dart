// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendee_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_AttendeeModel _$$_AttendeeModelFromJson(Map<String, dynamic> json) =>
    _$_AttendeeModel(
      attendanceRegister: (json['attendanceRegister'] as List<dynamic>?)
          ?.map((e) => AttendanceRegister.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AttendeeModelToJson(_$_AttendeeModel instance) =>
    <String, dynamic>{
      'attendanceRegister': instance.attendanceRegister,
    };

_$_AttendeeTrackListModel _$$_AttendeeTrackListModelFromJson(
        Map<String, dynamic> json) =>
    _$_AttendeeTrackListModel();

Map<String, dynamic> _$$_AttendeeTrackListModelToJson(
        _$_AttendeeTrackListModel instance) =>
    <String, dynamic>{};

_$_AttendeesTrackList _$$_AttendeesTrackListFromJson(
        Map<String, dynamic> json) =>
    _$_AttendeesTrackList(
      name: json['name'] as String?,
      aadhaar: json['aadhaar'] as String?,
      individualId: json['individualId'] as String?,
      monEntryId: json['monEntryId'] as String?,
      monExitId: json['monExitId'] as String?,
      monIndex: (json['monIndex'] as num?)?.toDouble() ?? 0.0,
      tueEntryId: json['tueEntryId'] as String?,
      tueExitId: json['tueExitId'] as String?,
      tueIndex: (json['tueIndex'] as num?)?.toDouble() ?? 0.0,
      wedEntryId: json['wedEntryId'] as String?,
      wedExitId: json['wedExitId'] as String?,
      wedIndex: (json['wedIndex'] as num?)?.toDouble() ?? 0.0,
      thuEntryId: json['thuEntryId'] as String?,
      thuExitId: json['thuExitId'] as String?,
      thursIndex: (json['thursIndex'] as num?)?.toDouble() ?? 0.0,
      friEntryId: json['friEntryId'] as String?,
      friExitId: json['friExitId'] as String?,
      friIndex: (json['friIndex'] as num?)?.toDouble() ?? 0.0,
      satEntryId: json['satEntryId'] as String?,
      satExitId: json['satExitId'] as String?,
      satIndex: (json['satIndex'] as num?)?.toDouble() ?? 0.0,
      sunEntryId: json['sunEntryId'] as String?,
      sunExitId: json['sunExitId'] as String?,
      sunIndex: (json['sunIndex'] as num?)?.toDouble() ?? 0.0,
    );

Map<String, dynamic> _$$_AttendeesTrackListToJson(
        _$_AttendeesTrackList instance) =>
    <String, dynamic>{
      'name': instance.name,
      'aadhaar': instance.aadhaar,
      'individualId': instance.individualId,
      'monEntryId': instance.monEntryId,
      'monExitId': instance.monExitId,
      'monIndex': instance.monIndex,
      'tueEntryId': instance.tueEntryId,
      'tueExitId': instance.tueExitId,
      'tueIndex': instance.tueIndex,
      'wedEntryId': instance.wedEntryId,
      'wedExitId': instance.wedExitId,
      'wedIndex': instance.wedIndex,
      'thuEntryId': instance.thuEntryId,
      'thuExitId': instance.thuExitId,
      'thursIndex': instance.thursIndex,
      'friEntryId': instance.friEntryId,
      'friExitId': instance.friExitId,
      'friIndex': instance.friIndex,
      'satEntryId': instance.satEntryId,
      'satExitId': instance.satExitId,
      'satIndex': instance.satIndex,
      'sunEntryId': instance.sunEntryId,
      'sunExitId': instance.sunExitId,
      'sunIndex': instance.sunIndex,
    };
