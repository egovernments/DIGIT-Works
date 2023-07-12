import 'package:dart_mappable/dart_mappable.dart';

part 'skill_details_model.mapper.dart';

@MappableClass()
class SkillDetails with SkillDetailsMappable {
  List<IndividualSkill>? individualSkills;
  SkillDetails({this.individualSkills});
}

@MappableClass()
class IndividualSkill with IndividualSkillMappable {
  final String? type;
  final String? level;

  IndividualSkill({this.type, this.level});
}
