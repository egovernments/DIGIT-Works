import 'package:freezed_annotation/freezed_annotation.dart';

part 'skills.freezed.dart';
part 'skills.g.dart';

@freezed
class SkillsList with _$SkillsList {
  const factory SkillsList({
    @JsonKey(name: 'WageSeekerSkills') List<WageSeekerSkills>? wageSeekerSkills,
  }) = _SkillsList;

  factory SkillsList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$SkillsListFromJson(json);
}

@freezed
class WageSeekerSkills with _$WageSeekerSkills {
  const factory WageSeekerSkills({
    required String code,
    int? amount,
    required bool active,
  }) = _WageSeekerSkills;

  factory WageSeekerSkills.fromJson(Map<String, dynamic> json) =>
      _$WageSeekerSkillsFromJson(json);
}
