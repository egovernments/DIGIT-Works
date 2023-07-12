// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'skills.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

SkillsList _$SkillsListFromJson(Map<String, dynamic> json) {
  return _SkillsList.fromJson(json);
}

/// @nodoc
mixin _$SkillsList {
  @JsonKey(name: 'WageSeekerSkills')
  List<WageSeekerSkills>? get wageSeekerSkills =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $SkillsListCopyWith<SkillsList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $SkillsListCopyWith<$Res> {
  factory $SkillsListCopyWith(
          SkillsList value, $Res Function(SkillsList) then) =
      _$SkillsListCopyWithImpl<$Res, SkillsList>;
  @useResult
  $Res call(
      {@JsonKey(name: 'WageSeekerSkills')
          List<WageSeekerSkills>? wageSeekerSkills});
}

/// @nodoc
class _$SkillsListCopyWithImpl<$Res, $Val extends SkillsList>
    implements $SkillsListCopyWith<$Res> {
  _$SkillsListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? wageSeekerSkills = freezed,
  }) {
    return _then(_value.copyWith(
      wageSeekerSkills: freezed == wageSeekerSkills
          ? _value.wageSeekerSkills
          : wageSeekerSkills // ignore: cast_nullable_to_non_nullable
              as List<WageSeekerSkills>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_SkillsListCopyWith<$Res>
    implements $SkillsListCopyWith<$Res> {
  factory _$$_SkillsListCopyWith(
          _$_SkillsList value, $Res Function(_$_SkillsList) then) =
      __$$_SkillsListCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'WageSeekerSkills')
          List<WageSeekerSkills>? wageSeekerSkills});
}

/// @nodoc
class __$$_SkillsListCopyWithImpl<$Res>
    extends _$SkillsListCopyWithImpl<$Res, _$_SkillsList>
    implements _$$_SkillsListCopyWith<$Res> {
  __$$_SkillsListCopyWithImpl(
      _$_SkillsList _value, $Res Function(_$_SkillsList) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? wageSeekerSkills = freezed,
  }) {
    return _then(_$_SkillsList(
      wageSeekerSkills: freezed == wageSeekerSkills
          ? _value._wageSeekerSkills
          : wageSeekerSkills // ignore: cast_nullable_to_non_nullable
              as List<WageSeekerSkills>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_SkillsList implements _SkillsList {
  const _$_SkillsList(
      {@JsonKey(name: 'WageSeekerSkills')
          final List<WageSeekerSkills>? wageSeekerSkills})
      : _wageSeekerSkills = wageSeekerSkills;

  factory _$_SkillsList.fromJson(Map<String, dynamic> json) =>
      _$$_SkillsListFromJson(json);

  final List<WageSeekerSkills>? _wageSeekerSkills;
  @override
  @JsonKey(name: 'WageSeekerSkills')
  List<WageSeekerSkills>? get wageSeekerSkills {
    final value = _wageSeekerSkills;
    if (value == null) return null;
    if (_wageSeekerSkills is EqualUnmodifiableListView)
      return _wageSeekerSkills;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'SkillsList(wageSeekerSkills: $wageSeekerSkills)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_SkillsList &&
            const DeepCollectionEquality()
                .equals(other._wageSeekerSkills, _wageSeekerSkills));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_wageSeekerSkills));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_SkillsListCopyWith<_$_SkillsList> get copyWith =>
      __$$_SkillsListCopyWithImpl<_$_SkillsList>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_SkillsListToJson(
      this,
    );
  }
}

abstract class _SkillsList implements SkillsList {
  const factory _SkillsList(
      {@JsonKey(name: 'WageSeekerSkills')
          final List<WageSeekerSkills>? wageSeekerSkills}) = _$_SkillsList;

  factory _SkillsList.fromJson(Map<String, dynamic> json) =
      _$_SkillsList.fromJson;

  @override
  @JsonKey(name: 'WageSeekerSkills')
  List<WageSeekerSkills>? get wageSeekerSkills;
  @override
  @JsonKey(ignore: true)
  _$$_SkillsListCopyWith<_$_SkillsList> get copyWith =>
      throw _privateConstructorUsedError;
}

WageSeekerSkills _$WageSeekerSkillsFromJson(Map<String, dynamic> json) {
  return _WageSeekerSkills.fromJson(json);
}

/// @nodoc
mixin _$WageSeekerSkills {
  String get code => throw _privateConstructorUsedError;
  int? get amount => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WageSeekerSkillsCopyWith<WageSeekerSkills> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerSkillsCopyWith<$Res> {
  factory $WageSeekerSkillsCopyWith(
          WageSeekerSkills value, $Res Function(WageSeekerSkills) then) =
      _$WageSeekerSkillsCopyWithImpl<$Res, WageSeekerSkills>;
  @useResult
  $Res call({String code, int? amount, bool active});
}

/// @nodoc
class _$WageSeekerSkillsCopyWithImpl<$Res, $Val extends WageSeekerSkills>
    implements $WageSeekerSkillsCopyWith<$Res> {
  _$WageSeekerSkillsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? amount = freezed,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_WageSeekerSkillsCopyWith<$Res>
    implements $WageSeekerSkillsCopyWith<$Res> {
  factory _$$_WageSeekerSkillsCopyWith(
          _$_WageSeekerSkills value, $Res Function(_$_WageSeekerSkills) then) =
      __$$_WageSeekerSkillsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String code, int? amount, bool active});
}

/// @nodoc
class __$$_WageSeekerSkillsCopyWithImpl<$Res>
    extends _$WageSeekerSkillsCopyWithImpl<$Res, _$_WageSeekerSkills>
    implements _$$_WageSeekerSkillsCopyWith<$Res> {
  __$$_WageSeekerSkillsCopyWithImpl(
      _$_WageSeekerSkills _value, $Res Function(_$_WageSeekerSkills) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? amount = freezed,
    Object? active = null,
  }) {
    return _then(_$_WageSeekerSkills(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_WageSeekerSkills implements _WageSeekerSkills {
  const _$_WageSeekerSkills(
      {required this.code, this.amount, required this.active});

  factory _$_WageSeekerSkills.fromJson(Map<String, dynamic> json) =>
      _$$_WageSeekerSkillsFromJson(json);

  @override
  final String code;
  @override
  final int? amount;
  @override
  final bool active;

  @override
  String toString() {
    return 'WageSeekerSkills(code: $code, amount: $amount, active: $active)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_WageSeekerSkills &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code, amount, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_WageSeekerSkillsCopyWith<_$_WageSeekerSkills> get copyWith =>
      __$$_WageSeekerSkillsCopyWithImpl<_$_WageSeekerSkills>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_WageSeekerSkillsToJson(
      this,
    );
  }
}

abstract class _WageSeekerSkills implements WageSeekerSkills {
  const factory _WageSeekerSkills(
      {required final String code,
      final int? amount,
      required final bool active}) = _$_WageSeekerSkills;

  factory _WageSeekerSkills.fromJson(Map<String, dynamic> json) =
      _$_WageSeekerSkills.fromJson;

  @override
  String get code;
  @override
  int? get amount;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$_WageSeekerSkillsCopyWith<_$_WageSeekerSkills> get copyWith =>
      throw _privateConstructorUsedError;
}
