// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

part 'user_details_model.freezed.dart';
part 'user_details_model.g.dart';

@freezed
class UserDetailsModel with _$UserDetailsModel {
  const factory UserDetailsModel({
    @JsonKey(name: 'UserRequest') UserRequestModel? userRequestModel,
    String? access_token,
    String? token_type,
    String? refresh_token,
    int? expires_in,
    String? scope,
  }) = _UserDetailsModel;

  factory UserDetailsModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$UserDetailsModelFromJson(json);
}

@freezed
class UserRequestModel with _$UserRequestModel {
  const factory UserRequestModel({
    bool? active,
    int? id,
    String? emailId,
    String? mobileNumber,
    String? name,
    String? scope,
    required String tenantId,
    String? type,
    String? userName,
    String? uuid,
    @JsonKey(name: 'roles') List<RolesModel>? rolesModel,
  }) = _UserRequestModel;

  factory UserRequestModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$UserRequestModelFromJson(json);
}

@freezed
class RolesModel with _$RolesModel {
  const factory RolesModel({
    String? code,
    String? name,
    String? tenantId,
  }) = _RolesModel;

  factory RolesModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$RolesModelFromJson(json);
}
