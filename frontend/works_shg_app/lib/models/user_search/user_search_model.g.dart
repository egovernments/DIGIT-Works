// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_search_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$UserSearchModelImpl _$$UserSearchModelImplFromJson(
        Map<String, dynamic> json) =>
    _$UserSearchModelImpl(
      user: (json['user'] as List<dynamic>?)
          ?.map((e) => UserRequestModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$UserSearchModelImplToJson(
        _$UserSearchModelImpl instance) =>
    <String, dynamic>{
      'user': instance.user,
    };
