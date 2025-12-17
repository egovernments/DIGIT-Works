// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'skill_details_model.dart';

class SkillDetailsMapper extends ClassMapperBase<SkillDetails> {
  SkillDetailsMapper._();

  static SkillDetailsMapper? _instance;
  static SkillDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = SkillDetailsMapper._());
      IndividualSkillMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'SkillDetails';

  static List<IndividualSkill>? _$individualSkills(SkillDetails v) =>
      v.individualSkills;
  static const Field<SkillDetails, List<IndividualSkill>> _f$individualSkills =
      Field('individualSkills', _$individualSkills, opt: true);

  @override
  final MappableFields<SkillDetails> fields = const {
    #individualSkills: _f$individualSkills,
  };

  static SkillDetails _instantiate(DecodingData data) {
    return SkillDetails(individualSkills: data.dec(_f$individualSkills));
  }

  @override
  final Function instantiate = _instantiate;

  static SkillDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<SkillDetails>(map);
  }

  static SkillDetails fromJson(String json) {
    return ensureInitialized().decodeJson<SkillDetails>(json);
  }
}

mixin SkillDetailsMappable {
  String toJson() {
    return SkillDetailsMapper.ensureInitialized()
        .encodeJson<SkillDetails>(this as SkillDetails);
  }

  Map<String, dynamic> toMap() {
    return SkillDetailsMapper.ensureInitialized()
        .encodeMap<SkillDetails>(this as SkillDetails);
  }

  SkillDetailsCopyWith<SkillDetails, SkillDetails, SkillDetails> get copyWith =>
      _SkillDetailsCopyWithImpl(this as SkillDetails, $identity, $identity);
  @override
  String toString() {
    return SkillDetailsMapper.ensureInitialized()
        .stringifyValue(this as SkillDetails);
  }

  @override
  bool operator ==(Object other) {
    return SkillDetailsMapper.ensureInitialized()
        .equalsValue(this as SkillDetails, other);
  }

  @override
  int get hashCode {
    return SkillDetailsMapper.ensureInitialized()
        .hashValue(this as SkillDetails);
  }
}

extension SkillDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, SkillDetails, $Out> {
  SkillDetailsCopyWith<$R, SkillDetails, $Out> get $asSkillDetails =>
      $base.as((v, t, t2) => _SkillDetailsCopyWithImpl(v, t, t2));
}

abstract class SkillDetailsCopyWith<$R, $In extends SkillDetails, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, IndividualSkill,
          IndividualSkillCopyWith<$R, IndividualSkill, IndividualSkill>>?
      get individualSkills;
  $R call({List<IndividualSkill>? individualSkills});
  SkillDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _SkillDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, SkillDetails, $Out>
    implements SkillDetailsCopyWith<$R, SkillDetails, $Out> {
  _SkillDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<SkillDetails> $mapper =
      SkillDetailsMapper.ensureInitialized();
  @override
  ListCopyWith<$R, IndividualSkill,
          IndividualSkillCopyWith<$R, IndividualSkill, IndividualSkill>>?
      get individualSkills => $value.individualSkills != null
          ? ListCopyWith($value.individualSkills!,
              (v, t) => v.copyWith.$chain(t), (v) => call(individualSkills: v))
          : null;
  @override
  $R call({Object? individualSkills = $none}) => $apply(FieldCopyWithData(
      {if (individualSkills != $none) #individualSkills: individualSkills}));
  @override
  SkillDetails $make(CopyWithData data) => SkillDetails(
      individualSkills:
          data.get(#individualSkills, or: $value.individualSkills));

  @override
  SkillDetailsCopyWith<$R2, SkillDetails, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _SkillDetailsCopyWithImpl($value, $cast, t);
}

class IndividualSkillMapper extends ClassMapperBase<IndividualSkill> {
  IndividualSkillMapper._();

  static IndividualSkillMapper? _instance;
  static IndividualSkillMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualSkillMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualSkill';

  static String? _$type(IndividualSkill v) => v.type;
  static const Field<IndividualSkill, String> _f$type =
      Field('type', _$type, opt: true);
  static String? _$level(IndividualSkill v) => v.level;
  static const Field<IndividualSkill, String> _f$level =
      Field('level', _$level, opt: true);

  @override
  final MappableFields<IndividualSkill> fields = const {
    #type: _f$type,
    #level: _f$level,
  };

  static IndividualSkill _instantiate(DecodingData data) {
    return IndividualSkill(type: data.dec(_f$type), level: data.dec(_f$level));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualSkill fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualSkill>(map);
  }

  static IndividualSkill fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualSkill>(json);
  }
}

mixin IndividualSkillMappable {
  String toJson() {
    return IndividualSkillMapper.ensureInitialized()
        .encodeJson<IndividualSkill>(this as IndividualSkill);
  }

  Map<String, dynamic> toMap() {
    return IndividualSkillMapper.ensureInitialized()
        .encodeMap<IndividualSkill>(this as IndividualSkill);
  }

  IndividualSkillCopyWith<IndividualSkill, IndividualSkill, IndividualSkill>
      get copyWith => _IndividualSkillCopyWithImpl(
          this as IndividualSkill, $identity, $identity);
  @override
  String toString() {
    return IndividualSkillMapper.ensureInitialized()
        .stringifyValue(this as IndividualSkill);
  }

  @override
  bool operator ==(Object other) {
    return IndividualSkillMapper.ensureInitialized()
        .equalsValue(this as IndividualSkill, other);
  }

  @override
  int get hashCode {
    return IndividualSkillMapper.ensureInitialized()
        .hashValue(this as IndividualSkill);
  }
}

extension IndividualSkillValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualSkill, $Out> {
  IndividualSkillCopyWith<$R, IndividualSkill, $Out> get $asIndividualSkill =>
      $base.as((v, t, t2) => _IndividualSkillCopyWithImpl(v, t, t2));
}

abstract class IndividualSkillCopyWith<$R, $In extends IndividualSkill, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? type, String? level});
  IndividualSkillCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualSkillCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualSkill, $Out>
    implements IndividualSkillCopyWith<$R, IndividualSkill, $Out> {
  _IndividualSkillCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualSkill> $mapper =
      IndividualSkillMapper.ensureInitialized();
  @override
  $R call({Object? type = $none, Object? level = $none}) =>
      $apply(FieldCopyWithData(
          {if (type != $none) #type: type, if (level != $none) #level: level}));
  @override
  IndividualSkill $make(CopyWithData data) => IndividualSkill(
      type: data.get(#type, or: $value.type),
      level: data.get(#level, or: $value.level));

  @override
  IndividualSkillCopyWith<$R2, IndividualSkill, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _IndividualSkillCopyWithImpl($value, $cast, t);
}
