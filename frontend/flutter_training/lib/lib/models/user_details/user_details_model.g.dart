// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_details_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_UserDetailsModel _$$_UserDetailsModelFromJson(Map<String, dynamic> json) =>
    _$_UserDetailsModel(
      userRequestModel: json['UserRequest'] == null
          ? null
          : UserRequestModel.fromJson(
              json['UserRequest'] as Map<String, dynamic>),
      access_token: json['access_token'] as String?,
      token_type: json['token_type'] as String?,
      refresh_token: json['refresh_token'] as String?,
      expires_in: json['expires_in'] as int?,
      scope: json['scope'] as String?,
    );

Map<String, dynamic> _$$_UserDetailsModelToJson(_$_UserDetailsModel instance) =>
    <String, dynamic>{
      'UserRequest': instance.userRequestModel,
      'access_token': instance.access_token,
      'token_type': instance.token_type,
      'refresh_token': instance.refresh_token,
      'expires_in': instance.expires_in,
      'scope': instance.scope,
    };

_$_UserRequestModel _$$_UserRequestModelFromJson(Map<String, dynamic> json) =>
    _$_UserRequestModel(
      active: json['active'] as bool?,
      id: json['id'] as int?,
      emailId: json['emailId'] as String?,
      mobileNumber: json['mobileNumber'] as String?,
      name: json['name'] as String?,
      scope: json['scope'] as String?,
      tenantId: json['tenantId'] as String,
      type: json['type'] as String?,
      userName: json['userName'] as String?,
      uuid: json['uuid'] as String?,
      rolesModel: (json['roles'] as List<dynamic>?)
          ?.map((e) => RolesModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_UserRequestModelToJson(_$_UserRequestModel instance) =>
    <String, dynamic>{
      'active': instance.active,
      'id': instance.id,
      'emailId': instance.emailId,
      'mobileNumber': instance.mobileNumber,
      'name': instance.name,
      'scope': instance.scope,
      'tenantId': instance.tenantId,
      'type': instance.type,
      'userName': instance.userName,
      'uuid': instance.uuid,
      'roles': instance.rolesModel,
    };

_$_RolesModel _$$_RolesModelFromJson(Map<String, dynamic> json) =>
    _$_RolesModel(
      code: json['code'] as String?,
      name: json['name'] as String?,
      tenantId: json['tenantId'] as String?,
    );

Map<String, dynamic> _$$_RolesModelToJson(_$_RolesModel instance) =>
    <String, dynamic>{
      'code': instance.code,
      'name': instance.name,
      'tenantId': instance.tenantId,
    };
