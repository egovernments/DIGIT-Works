// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'skill_details_model.dart';

class SkillDetailsMapper extends MapperBase<SkillDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {SkillDetailsMapper()},
  )..linkAll({IndividualSkillMapper.container});

  @override
  SkillDetailsMapperElement createElement(MapperContainer container) {
    return SkillDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'SkillDetails';

  static final fromMap = container.fromMap<SkillDetails>;
  static final fromJson = container.fromJson<SkillDetails>;
}

class SkillDetailsMapperElement extends MapperElementBase<SkillDetails> {
  SkillDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  SkillDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  SkillDetails fromMap(Map<String, dynamic> map) => SkillDetails(
      individualSkills: container.$getOpt(map, 'individualSkills'));

  @override
  Function get encoder => encode;
  dynamic encode(SkillDetails v) => toMap(v);
  Map<String, dynamic> toMap(SkillDetails s) => {
        'individualSkills':
            container.$enc(s.individualSkills, 'individualSkills')
      };

  @override
  String stringify(SkillDetails self) =>
      'SkillDetails(individualSkills: ${container.asString(self.individualSkills)})';
  @override
  int hash(SkillDetails self) => container.hash(self.individualSkills);
  @override
  bool equals(SkillDetails self, SkillDetails other) =>
      container.isEqual(self.individualSkills, other.individualSkills);
}

mixin SkillDetailsMappable {
  String toJson() => SkillDetailsMapper.container.toJson(this as SkillDetails);
  Map<String, dynamic> toMap() =>
      SkillDetailsMapper.container.toMap(this as SkillDetails);
  SkillDetailsCopyWith<SkillDetails, SkillDetails, SkillDetails> get copyWith =>
      _SkillDetailsCopyWithImpl(this as SkillDetails, $identity, $identity);
  @override
  String toString() => SkillDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          SkillDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => SkillDetailsMapper.container.hash(this);
}

extension SkillDetailsValueCopy<$R, $Out extends SkillDetails>
    on ObjectCopyWith<$R, SkillDetails, $Out> {
  SkillDetailsCopyWith<$R, SkillDetails, $Out> get asSkillDetails =>
      base.as((v, t, t2) => _SkillDetailsCopyWithImpl(v, t, t2));
}

typedef SkillDetailsCopyWithBound = SkillDetails;

abstract class SkillDetailsCopyWith<$R, $In extends SkillDetails,
    $Out extends SkillDetails> implements ObjectCopyWith<$R, $In, $Out> {
  SkillDetailsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends SkillDetails>(
      Then<SkillDetails, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, IndividualSkill,
          IndividualSkillCopyWith<$R, IndividualSkill, IndividualSkill>>?
      get individualSkills;
  $R call({List<IndividualSkill>? individualSkills});
}

class _SkillDetailsCopyWithImpl<$R, $Out extends SkillDetails>
    extends CopyWithBase<$R, SkillDetails, $Out>
    implements SkillDetailsCopyWith<$R, SkillDetails, $Out> {
  _SkillDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  SkillDetailsCopyWith<$R2, SkillDetails, $Out2>
      chain<$R2, $Out2 extends SkillDetails>(
              Then<SkillDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _SkillDetailsCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, IndividualSkill,
          IndividualSkillCopyWith<$R, IndividualSkill, IndividualSkill>>?
      get individualSkills => $value.individualSkills != null
          ? ListCopyWith(
              $value.individualSkills!,
              (v, t) => v.copyWith.chain<$R, IndividualSkill>($identity, t),
              (v) => call(individualSkills: v))
          : null;
  @override
  $R call({Object? individualSkills = $none}) => $then(SkillDetails(
      individualSkills: or(individualSkills, $value.individualSkills)));
}

class IndividualSkillMapper extends MapperBase<IndividualSkill> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualSkillMapper()},
  );

  @override
  IndividualSkillMapperElement createElement(MapperContainer container) {
    return IndividualSkillMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualSkill';

  static final fromMap = container.fromMap<IndividualSkill>;
  static final fromJson = container.fromJson<IndividualSkill>;
}

class IndividualSkillMapperElement extends MapperElementBase<IndividualSkill> {
  IndividualSkillMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualSkill decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualSkill fromMap(Map<String, dynamic> map) => IndividualSkill(
      type: container.$getOpt(map, 'type'),
      level: container.$getOpt(map, 'level'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualSkill v) => toMap(v);
  Map<String, dynamic> toMap(IndividualSkill i) => {
        'type': container.$enc(i.type, 'type'),
        'level': container.$enc(i.level, 'level')
      };

  @override
  String stringify(IndividualSkill self) =>
      'IndividualSkill(type: ${container.asString(self.type)}, level: ${container.asString(self.level)})';
  @override
  int hash(IndividualSkill self) =>
      container.hash(self.type) ^ container.hash(self.level);
  @override
  bool equals(IndividualSkill self, IndividualSkill other) =>
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.level, other.level);
}

mixin IndividualSkillMappable {
  String toJson() =>
      IndividualSkillMapper.container.toJson(this as IndividualSkill);
  Map<String, dynamic> toMap() =>
      IndividualSkillMapper.container.toMap(this as IndividualSkill);
  IndividualSkillCopyWith<IndividualSkill, IndividualSkill, IndividualSkill>
      get copyWith => _IndividualSkillCopyWithImpl(
          this as IndividualSkill, $identity, $identity);
  @override
  String toString() => IndividualSkillMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualSkillMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualSkillMapper.container.hash(this);
}

extension IndividualSkillValueCopy<$R, $Out extends IndividualSkill>
    on ObjectCopyWith<$R, IndividualSkill, $Out> {
  IndividualSkillCopyWith<$R, IndividualSkill, $Out> get asIndividualSkill =>
      base.as((v, t, t2) => _IndividualSkillCopyWithImpl(v, t, t2));
}

typedef IndividualSkillCopyWithBound = IndividualSkill;

abstract class IndividualSkillCopyWith<$R, $In extends IndividualSkill,
    $Out extends IndividualSkill> implements ObjectCopyWith<$R, $In, $Out> {
  IndividualSkillCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualSkill>(
          Then<IndividualSkill, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? type, String? level});
}

class _IndividualSkillCopyWithImpl<$R, $Out extends IndividualSkill>
    extends CopyWithBase<$R, IndividualSkill, $Out>
    implements IndividualSkillCopyWith<$R, IndividualSkill, $Out> {
  _IndividualSkillCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualSkillCopyWith<$R2, IndividualSkill, $Out2>
      chain<$R2, $Out2 extends IndividualSkill>(
              Then<IndividualSkill, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualSkillCopyWithImpl($value, t, t2);

  @override
  $R call({Object? type = $none, Object? level = $none}) =>
      $then(IndividualSkill(
          type: or(type, $value.type), level: or(level, $value.level)));
}
