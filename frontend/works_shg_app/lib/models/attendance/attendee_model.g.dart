// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendee_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$AttendeeModelImpl _$$AttendeeModelImplFromJson(Map<String, dynamic> json) =>
    _$AttendeeModelImpl(
      attendanceRegister: (json['attendanceRegister'] as List<dynamic>?)
          ?.map((e) => AttendanceRegister.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$AttendeeModelImplToJson(_$AttendeeModelImpl instance) =>
    <String, dynamic>{
      'attendanceRegister': instance.attendanceRegister,
    };

_$AttendeeTrackListModelImpl _$$AttendeeTrackListModelImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendeeTrackListModelImpl();

Map<String, dynamic> _$$AttendeeTrackListModelImplToJson(
        _$AttendeeTrackListModelImpl instance) =>
    <String, dynamic>{};

_$AttendeesTrackListImpl _$$AttendeesTrackListImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendeesTrackListImpl(
      deenrollment: (json['deenrollment'] as num?)?.toInt(),
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

Map<String, dynamic> _$$AttendeesTrackListImplToJson(
        _$AttendeesTrackListImpl instance) =>
    <String, dynamic>{
      'deenrollment': instance.deenrollment,
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

_$AttendeeAuditDetailsImpl _$$AttendeeAuditDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendeeAuditDetailsImpl(
      createdBy: json['createdBy'] as String?,
      lastModifiedBy: json['lastModifiedBy'] as String?,
      createdTime: (json['createdTime'] as num?)?.toInt(),
      lastModifiedTime: (json['lastModifiedTime'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$AttendeeAuditDetailsImplToJson(
        _$AttendeeAuditDetailsImpl instance) =>
    <String, dynamic>{
      'createdBy': instance.createdBy,
      'lastModifiedBy': instance.lastModifiedBy,
      'createdTime': instance.createdTime,
      'lastModifiedTime': instance.lastModifiedTime,
    };
