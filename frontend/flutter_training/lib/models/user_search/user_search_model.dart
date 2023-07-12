// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:flutter_training/models/user_details/user_details_model.dart';

part 'user_search_model.freezed.dart';
part 'user_search_model.g.dart';

@freezed
class UserSearchModel with _$UserSearchModel {
  const factory UserSearchModel({
    @JsonKey(name: 'user') List<UserRequestModel>? user,
  }) = _UserSearchModel;

  factory UserSearchModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$UserSearchModelFromJson(json);
}
