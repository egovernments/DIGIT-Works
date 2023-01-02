// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_search_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_UserSearchModel _$$_UserSearchModelFromJson(Map<String, dynamic> json) =>
    _$_UserSearchModel(
      user: (json['user'] as List<dynamic>?)
          ?.map((e) => UserRequestModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_UserSearchModelToJson(_$_UserSearchModel instance) =>
    <String, dynamic>{
      'user': instance.user,
    };
