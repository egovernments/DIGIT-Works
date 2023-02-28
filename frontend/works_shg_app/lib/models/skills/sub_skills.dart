import 'package:freezed_annotation/freezed_annotation.dart';

part 'sub_skills.freezed.dart';
part 'sub_skills.g.dart';

@freezed
class SubSkillsList with _$SubSkillsList {
  const factory SubSkillsList({
    @JsonKey(name: 'WageSeekerSubSkills')
        List<WageSeekerSubSkills>? wageSeekerSubSkills,
  }) = _SubSkillsList;

  factory SubSkillsList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$SubSkillsListFromJson(json);
}

@freezed
class WageSeekerSubSkills with _$WageSeekerSubSkills {
  const factory WageSeekerSubSkills({
    required String name,
    required String code,
    required bool active,
  }) = _WageSeekerSubSkills;

  factory WageSeekerSubSkills.fromJson(Map<String, dynamic> json) =>
      _$WageSeekerSubSkillsFromJson(json);
}
