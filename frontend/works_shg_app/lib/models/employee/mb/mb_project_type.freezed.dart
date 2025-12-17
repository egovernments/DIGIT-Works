// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'mb_project_type.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

MBProjectType _$MBProjectTypeFromJson(Map<String, dynamic> json) {
  return _MBProjectType.fromJson(json);
}

/// @nodoc
mixin _$MBProjectType {
  @JsonKey(name: 'MdmsRes')
  MdmsRes? get mdmsRes => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MBProjectTypeCopyWith<MBProjectType> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MBProjectTypeCopyWith<$Res> {
  factory $MBProjectTypeCopyWith(
          MBProjectType value, $Res Function(MBProjectType) then) =
      _$MBProjectTypeCopyWithImpl<$Res, MBProjectType>;
  @useResult
  $Res call({@JsonKey(name: 'MdmsRes') MdmsRes? mdmsRes});

  $MdmsResCopyWith<$Res>? get mdmsRes;
}

/// @nodoc
class _$MBProjectTypeCopyWithImpl<$Res, $Val extends MBProjectType>
    implements $MBProjectTypeCopyWith<$Res> {
  _$MBProjectTypeCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mdmsRes = freezed,
  }) {
    return _then(_value.copyWith(
      mdmsRes: freezed == mdmsRes
          ? _value.mdmsRes
          : mdmsRes // ignore: cast_nullable_to_non_nullable
              as MdmsRes?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $MdmsResCopyWith<$Res>? get mdmsRes {
    if (_value.mdmsRes == null) {
      return null;
    }

    return $MdmsResCopyWith<$Res>(_value.mdmsRes!, (value) {
      return _then(_value.copyWith(mdmsRes: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$MBProjectTypeImplCopyWith<$Res>
    implements $MBProjectTypeCopyWith<$Res> {
  factory _$$MBProjectTypeImplCopyWith(
          _$MBProjectTypeImpl value, $Res Function(_$MBProjectTypeImpl) then) =
      __$$MBProjectTypeImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'MdmsRes') MdmsRes? mdmsRes});

  @override
  $MdmsResCopyWith<$Res>? get mdmsRes;
}

/// @nodoc
class __$$MBProjectTypeImplCopyWithImpl<$Res>
    extends _$MBProjectTypeCopyWithImpl<$Res, _$MBProjectTypeImpl>
    implements _$$MBProjectTypeImplCopyWith<$Res> {
  __$$MBProjectTypeImplCopyWithImpl(
      _$MBProjectTypeImpl _value, $Res Function(_$MBProjectTypeImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mdmsRes = freezed,
  }) {
    return _then(_$MBProjectTypeImpl(
      mdmsRes: freezed == mdmsRes
          ? _value.mdmsRes
          : mdmsRes // ignore: cast_nullable_to_non_nullable
              as MdmsRes?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MBProjectTypeImpl implements _MBProjectType {
  const _$MBProjectTypeImpl({@JsonKey(name: 'MdmsRes') this.mdmsRes});

  factory _$MBProjectTypeImpl.fromJson(Map<String, dynamic> json) =>
      _$$MBProjectTypeImplFromJson(json);

  @override
  @JsonKey(name: 'MdmsRes')
  final MdmsRes? mdmsRes;

  @override
  String toString() {
    return 'MBProjectType(mdmsRes: $mdmsRes)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MBProjectTypeImpl &&
            (identical(other.mdmsRes, mdmsRes) || other.mdmsRes == mdmsRes));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, mdmsRes);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MBProjectTypeImplCopyWith<_$MBProjectTypeImpl> get copyWith =>
      __$$MBProjectTypeImplCopyWithImpl<_$MBProjectTypeImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MBProjectTypeImplToJson(
      this,
    );
  }
}

abstract class _MBProjectType implements MBProjectType {
  const factory _MBProjectType(
      {@JsonKey(name: 'MdmsRes') final MdmsRes? mdmsRes}) = _$MBProjectTypeImpl;

  factory _MBProjectType.fromJson(Map<String, dynamic> json) =
      _$MBProjectTypeImpl.fromJson;

  @override
  @JsonKey(name: 'MdmsRes')
  MdmsRes? get mdmsRes;
  @override
  @JsonKey(ignore: true)
  _$$MBProjectTypeImplCopyWith<_$MBProjectTypeImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

MdmsRes _$MdmsResFromJson(Map<String, dynamic> json) {
  return _MdmsRes.fromJson(json);
}

/// @nodoc
mixin _$MdmsRes {
  @JsonKey(name: 'works')
  MBWorks? get mbWorks => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MdmsResCopyWith<MdmsRes> get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MdmsResCopyWith<$Res> {
  factory $MdmsResCopyWith(MdmsRes value, $Res Function(MdmsRes) then) =
      _$MdmsResCopyWithImpl<$Res, MdmsRes>;
  @useResult
  $Res call({@JsonKey(name: 'works') MBWorks? mbWorks});

  $MBWorksCopyWith<$Res>? get mbWorks;
}

/// @nodoc
class _$MdmsResCopyWithImpl<$Res, $Val extends MdmsRes>
    implements $MdmsResCopyWith<$Res> {
  _$MdmsResCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mbWorks = freezed,
  }) {
    return _then(_value.copyWith(
      mbWorks: freezed == mbWorks
          ? _value.mbWorks
          : mbWorks // ignore: cast_nullable_to_non_nullable
              as MBWorks?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $MBWorksCopyWith<$Res>? get mbWorks {
    if (_value.mbWorks == null) {
      return null;
    }

    return $MBWorksCopyWith<$Res>(_value.mbWorks!, (value) {
      return _then(_value.copyWith(mbWorks: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$MdmsResImplCopyWith<$Res> implements $MdmsResCopyWith<$Res> {
  factory _$$MdmsResImplCopyWith(
          _$MdmsResImpl value, $Res Function(_$MdmsResImpl) then) =
      __$$MdmsResImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'works') MBWorks? mbWorks});

  @override
  $MBWorksCopyWith<$Res>? get mbWorks;
}

/// @nodoc
class __$$MdmsResImplCopyWithImpl<$Res>
    extends _$MdmsResCopyWithImpl<$Res, _$MdmsResImpl>
    implements _$$MdmsResImplCopyWith<$Res> {
  __$$MdmsResImplCopyWithImpl(
      _$MdmsResImpl _value, $Res Function(_$MdmsResImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mbWorks = freezed,
  }) {
    return _then(_$MdmsResImpl(
      mbWorks: freezed == mbWorks
          ? _value.mbWorks
          : mbWorks // ignore: cast_nullable_to_non_nullable
              as MBWorks?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MdmsResImpl implements _MdmsRes {
  const _$MdmsResImpl({@JsonKey(name: 'works') this.mbWorks});

  factory _$MdmsResImpl.fromJson(Map<String, dynamic> json) =>
      _$$MdmsResImplFromJson(json);

  @override
  @JsonKey(name: 'works')
  final MBWorks? mbWorks;

  @override
  String toString() {
    return 'MdmsRes(mbWorks: $mbWorks)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MdmsResImpl &&
            (identical(other.mbWorks, mbWorks) || other.mbWorks == mbWorks));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, mbWorks);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MdmsResImplCopyWith<_$MdmsResImpl> get copyWith =>
      __$$MdmsResImplCopyWithImpl<_$MdmsResImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MdmsResImplToJson(
      this,
    );
  }
}

abstract class _MdmsRes implements MdmsRes {
  const factory _MdmsRes({@JsonKey(name: 'works') final MBWorks? mbWorks}) =
      _$MdmsResImpl;

  factory _MdmsRes.fromJson(Map<String, dynamic> json) = _$MdmsResImpl.fromJson;

  @override
  @JsonKey(name: 'works')
  MBWorks? get mbWorks;
  @override
  @JsonKey(ignore: true)
  _$$MdmsResImplCopyWith<_$MdmsResImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

MBWorks _$MBWorksFromJson(Map<String, dynamic> json) {
  return _MBWorks.fromJson(json);
}

/// @nodoc
mixin _$MBWorks {
  @JsonKey(name: 'ProjectType')
  List<ProjectType>? get projectType => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MBWorksCopyWith<MBWorks> get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MBWorksCopyWith<$Res> {
  factory $MBWorksCopyWith(MBWorks value, $Res Function(MBWorks) then) =
      _$MBWorksCopyWithImpl<$Res, MBWorks>;
  @useResult
  $Res call({@JsonKey(name: 'ProjectType') List<ProjectType>? projectType});
}

/// @nodoc
class _$MBWorksCopyWithImpl<$Res, $Val extends MBWorks>
    implements $MBWorksCopyWith<$Res> {
  _$MBWorksCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? projectType = freezed,
  }) {
    return _then(_value.copyWith(
      projectType: freezed == projectType
          ? _value.projectType
          : projectType // ignore: cast_nullable_to_non_nullable
              as List<ProjectType>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$MBWorksImplCopyWith<$Res> implements $MBWorksCopyWith<$Res> {
  factory _$$MBWorksImplCopyWith(
          _$MBWorksImpl value, $Res Function(_$MBWorksImpl) then) =
      __$$MBWorksImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'ProjectType') List<ProjectType>? projectType});
}

/// @nodoc
class __$$MBWorksImplCopyWithImpl<$Res>
    extends _$MBWorksCopyWithImpl<$Res, _$MBWorksImpl>
    implements _$$MBWorksImplCopyWith<$Res> {
  __$$MBWorksImplCopyWithImpl(
      _$MBWorksImpl _value, $Res Function(_$MBWorksImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? projectType = freezed,
  }) {
    return _then(_$MBWorksImpl(
      projectType: freezed == projectType
          ? _value._projectType
          : projectType // ignore: cast_nullable_to_non_nullable
              as List<ProjectType>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MBWorksImpl implements _MBWorks {
  const _$MBWorksImpl(
      {@JsonKey(name: 'ProjectType') final List<ProjectType>? projectType})
      : _projectType = projectType;

  factory _$MBWorksImpl.fromJson(Map<String, dynamic> json) =>
      _$$MBWorksImplFromJson(json);

  final List<ProjectType>? _projectType;
  @override
  @JsonKey(name: 'ProjectType')
  List<ProjectType>? get projectType {
    final value = _projectType;
    if (value == null) return null;
    if (_projectType is EqualUnmodifiableListView) return _projectType;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MBWorks(projectType: $projectType)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MBWorksImpl &&
            const DeepCollectionEquality()
                .equals(other._projectType, _projectType));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_projectType));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MBWorksImplCopyWith<_$MBWorksImpl> get copyWith =>
      __$$MBWorksImplCopyWithImpl<_$MBWorksImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MBWorksImplToJson(
      this,
    );
  }
}

abstract class _MBWorks implements MBWorks {
  const factory _MBWorks(
      {@JsonKey(name: 'ProjectType')
          final List<ProjectType>? projectType}) = _$MBWorksImpl;

  factory _MBWorks.fromJson(Map<String, dynamic> json) = _$MBWorksImpl.fromJson;

  @override
  @JsonKey(name: 'ProjectType')
  List<ProjectType>? get projectType;
  @override
  @JsonKey(ignore: true)
  _$$MBWorksImplCopyWith<_$MBWorksImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

ProjectType _$ProjectTypeFromJson(Map<String, dynamic> json) {
  return _ProjectType.fromJson(json);
}

/// @nodoc
mixin _$ProjectType {
  @JsonKey(name: 'id')
  int? get id => throw _privateConstructorUsedError;
  @JsonKey(name: 'name')
  String? get name => throw _privateConstructorUsedError;
  @JsonKey(name: 'code')
  String? get code => throw _privateConstructorUsedError;
  @JsonKey(name: 'active')
  bool? get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ProjectTypeCopyWith<ProjectType> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ProjectTypeCopyWith<$Res> {
  factory $ProjectTypeCopyWith(
          ProjectType value, $Res Function(ProjectType) then) =
      _$ProjectTypeCopyWithImpl<$Res, ProjectType>;
  @useResult
  $Res call(
      {@JsonKey(name: 'id') int? id,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'code') String? code,
      @JsonKey(name: 'active') bool? active});
}

/// @nodoc
class _$ProjectTypeCopyWithImpl<$Res, $Val extends ProjectType>
    implements $ProjectTypeCopyWith<$Res> {
  _$ProjectTypeCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? name = freezed,
    Object? code = freezed,
    Object? active = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$ProjectTypeImplCopyWith<$Res>
    implements $ProjectTypeCopyWith<$Res> {
  factory _$$ProjectTypeImplCopyWith(
          _$ProjectTypeImpl value, $Res Function(_$ProjectTypeImpl) then) =
      __$$ProjectTypeImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'id') int? id,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'code') String? code,
      @JsonKey(name: 'active') bool? active});
}

/// @nodoc
class __$$ProjectTypeImplCopyWithImpl<$Res>
    extends _$ProjectTypeCopyWithImpl<$Res, _$ProjectTypeImpl>
    implements _$$ProjectTypeImplCopyWith<$Res> {
  __$$ProjectTypeImplCopyWithImpl(
      _$ProjectTypeImpl _value, $Res Function(_$ProjectTypeImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? name = freezed,
    Object? code = freezed,
    Object? active = freezed,
  }) {
    return _then(_$ProjectTypeImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$ProjectTypeImpl implements _ProjectType {
  const _$ProjectTypeImpl(
      {@JsonKey(name: 'id') this.id,
      @JsonKey(name: 'name') this.name,
      @JsonKey(name: 'code') this.code,
      @JsonKey(name: 'active') this.active});

  factory _$ProjectTypeImpl.fromJson(Map<String, dynamic> json) =>
      _$$ProjectTypeImplFromJson(json);

  @override
  @JsonKey(name: 'id')
  final int? id;
  @override
  @JsonKey(name: 'name')
  final String? name;
  @override
  @JsonKey(name: 'code')
  final String? code;
  @override
  @JsonKey(name: 'active')
  final bool? active;

  @override
  String toString() {
    return 'ProjectType(id: $id, name: $name, code: $code, active: $active)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ProjectTypeImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id, name, code, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ProjectTypeImplCopyWith<_$ProjectTypeImpl> get copyWith =>
      __$$ProjectTypeImplCopyWithImpl<_$ProjectTypeImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$ProjectTypeImplToJson(
      this,
    );
  }
}

abstract class _ProjectType implements ProjectType {
  const factory _ProjectType(
      {@JsonKey(name: 'id') final int? id,
      @JsonKey(name: 'name') final String? name,
      @JsonKey(name: 'code') final String? code,
      @JsonKey(name: 'active') final bool? active}) = _$ProjectTypeImpl;

  factory _ProjectType.fromJson(Map<String, dynamic> json) =
      _$ProjectTypeImpl.fromJson;

  @override
  @JsonKey(name: 'id')
  int? get id;
  @override
  @JsonKey(name: 'name')
  String? get name;
  @override
  @JsonKey(name: 'code')
  String? get code;
  @override
  @JsonKey(name: 'active')
  bool? get active;
  @override
  @JsonKey(ignore: true)
  _$$ProjectTypeImplCopyWith<_$ProjectTypeImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
