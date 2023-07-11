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
      gender: json['gender'] as String?,
      individualGaurdianName: json['individualGaurdianName'] as String?,
      individualId: json['individualId'] as String?,
      id: json['id'] as String?,
      skill: json['skill'] as String?,
      monEntryId: json['monEntryId'] as String?,
      monExitId: json['monExitId'] as String?,
      monIndex: (json['monIndex'] as num?)?.toDouble() ?? -1,
      tueEntryId: json['tueEntryId'] as String?,
      tueExitId: json['tueExitId'] as String?,
      tueIndex: (json['tueIndex'] as num?)?.toDouble() ?? -1,
      wedEntryId: json['wedEntryId'] as String?,
      wedExitId: json['wedExitId'] as String?,
      wedIndex: (json['wedIndex'] as num?)?.toDouble() ?? -1,
      thuEntryId: json['thuEntryId'] as String?,
      thuExitId: json['thuExitId'] as String?,
      thursIndex: (json['thursIndex'] as num?)?.toDouble() ?? -1,
      friEntryId: json['friEntryId'] as String?,
      friExitId: json['friExitId'] as String?,
      skillCodeList: (json['skillCodeList'] as List<dynamic>?)
          ?.map((e) => e as String)
          .toList(),
      friIndex: (json['friIndex'] as num?)?.toDouble() ?? -1,
      satEntryId: json['satEntryId'] as String?,
      satExitId: json['satExitId'] as String?,
      satIndex: (json['satIndex'] as num?)?.toDouble() ?? -1,
      sunEntryId: json['sunEntryId'] as String?,
      sunExitId: json['sunExitId'] as String?,
      sunIndex: (json['sunIndex'] as num?)?.toDouble() ?? -1,
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_AttendeesTrackListToJson(
        _$_AttendeesTrackList instance) =>
    <String, dynamic>{
      'name': instance.name,
      'aadhaar': instance.aadhaar,
      'gender': instance.gender,
      'individualGaurdianName': instance.individualGaurdianName,
      'individualId': instance.individualId,
      'id': instance.id,
      'skill': instance.skill,
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
      'skillCodeList': instance.skillCodeList,
      'friIndex': instance.friIndex,
      'satEntryId': instance.satEntryId,
      'satExitId': instance.satExitId,
      'satIndex': instance.satIndex,
      'sunEntryId': instance.sunEntryId,
      'sunExitId': instance.sunExitId,
      'sunIndex': instance.sunIndex,
      'auditDetails': instance.auditDetails,
    };

_$_AttendeeAuditDetails _$$_AttendeeAuditDetailsFromJson(
        Map<String, dynamic> json) =>
    _$_AttendeeAuditDetails(
      createdBy: json['createdBy'] as String?,
      lastModifiedBy: json['lastModifiedBy'] as String?,
      createdTime: json['createdTime'] as int?,
      lastModifiedTime: json['lastModifiedTime'] as int?,
    );

Map<String, dynamic> _$$_AttendeeAuditDetailsToJson(
        _$_AttendeeAuditDetails instance) =>
    <String, dynamic>{
      'createdBy': instance.createdBy,
      'lastModifiedBy': instance.lastModifiedBy,
      'createdTime': instance.createdTime,
      'lastModifiedTime': instance.lastModifiedTime,
    };
