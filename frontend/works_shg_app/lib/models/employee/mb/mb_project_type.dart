import 'package:freezed_annotation/freezed_annotation.dart';

part 'mb_project_type.freezed.dart';
part 'mb_project_type.g.dart';

@freezed
class MBProjectType with _$MBProjectType {
  const factory MBProjectType({
    @JsonKey(name: 'MdmsRes') MdmsRes? mdmsRes,
  }) = _MBProjectType;

  factory MBProjectType.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MBProjectTypeFromJson(json);
}

@freezed
class MdmsRes with _$MdmsRes {
  const factory MdmsRes({
    @JsonKey(name: 'works') MBWorks? mbWorks,
  }) = _MdmsRes;

  factory MdmsRes.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MdmsResFromJson(json);
}



@freezed
class MBWorks with _$MBWorks {
  const factory MBWorks({
    @JsonKey(name: 'ProjectType') List<ProjectType>? projectType,
  }) = _MBWorks;

  factory MBWorks.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MBWorksFromJson(json);
}




@freezed
class ProjectType with _$ProjectType {
  const factory ProjectType({
    @JsonKey(name: 'id') int? id,
    @JsonKey(name: 'name') String? name,
    @JsonKey(name: 'code') String? code,
    @JsonKey(name: 'active') bool? active,

  }) = _ProjectType;

  factory ProjectType.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$ProjectTypeFromJson(json);
}