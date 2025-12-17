// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'role_based_hrms.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$HRMSResponseImpl _$$HRMSResponseImplFromJson(Map<String, dynamic> json) =>
    _$HRMSResponseImpl(
      employees: (json['Employees'] as List<dynamic>?)
          ?.map((e) => HRMSEmployee.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$HRMSResponseImplToJson(_$HRMSResponseImpl instance) =>
    <String, dynamic>{
      'Employees': instance.employees,
    };

_$HRMSEmployeeImpl _$$HRMSEmployeeImplFromJson(Map<String, dynamic> json) =>
    _$HRMSEmployeeImpl(
      id: (json['id'] as num?)?.toInt(),
      uuid: json['uuid'] as String?,
      code: json['code'] as String?,
      isActive: json['isActive'] as bool?,
      dateOfAppointment: (json['dateOfAppointment'] as num?)?.toInt(),
      employeeType: json['employeeType'] as String?,
      employeeUser: json['user'] == null
          ? null
          : EmployeeUser.fromJson(json['user'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$HRMSEmployeeImplToJson(_$HRMSEmployeeImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'uuid': instance.uuid,
      'code': instance.code,
      'isActive': instance.isActive,
      'dateOfAppointment': instance.dateOfAppointment,
      'employeeType': instance.employeeType,
      'user': instance.employeeUser,
    };

_$EmployeeUserImpl _$$EmployeeUserImplFromJson(Map<String, dynamic> json) =>
    _$EmployeeUserImpl(
      correspondenceAddress: json['correspondenceAddress'] as String?,
      mobileNumber: json['mobileNumber'] as String?,
      name: json['name'] as String?,
      userName: json['userName'] as String?,
    );

Map<String, dynamic> _$$EmployeeUserImplToJson(_$EmployeeUserImpl instance) =>
    <String, dynamic>{
      'correspondenceAddress': instance.correspondenceAddress,
      'mobileNumber': instance.mobileNumber,
      'name': instance.name,
      'userName': instance.userName,
    };
