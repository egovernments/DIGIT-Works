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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

SkillsList _$SkillsListFromJson(Map<String, dynamic> json) {
  return _SkillsList.fromJson(json);
}

/// @nodoc
mixin _$SkillsList {
  @JsonKey(name: 'SOR')
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
  $Res call({@JsonKey(name: 'SOR') List<WageSeekerSkills>? wageSeekerSkills});
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
abstract class _$$SkillsListImplCopyWith<$Res>
    implements $SkillsListCopyWith<$Res> {
  factory _$$SkillsListImplCopyWith(
          _$SkillsListImpl value, $Res Function(_$SkillsListImpl) then) =
      __$$SkillsListImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'SOR') List<WageSeekerSkills>? wageSeekerSkills});
}

/// @nodoc
class __$$SkillsListImplCopyWithImpl<$Res>
    extends _$SkillsListCopyWithImpl<$Res, _$SkillsListImpl>
    implements _$$SkillsListImplCopyWith<$Res> {
  __$$SkillsListImplCopyWithImpl(
      _$SkillsListImpl _value, $Res Function(_$SkillsListImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? wageSeekerSkills = freezed,
  }) {
    return _then(_$SkillsListImpl(
      wageSeekerSkills: freezed == wageSeekerSkills
          ? _value._wageSeekerSkills
          : wageSeekerSkills // ignore: cast_nullable_to_non_nullable
              as List<WageSeekerSkills>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$SkillsListImpl implements _SkillsList {
  const _$SkillsListImpl(
      {@JsonKey(name: 'SOR') final List<WageSeekerSkills>? wageSeekerSkills})
      : _wageSeekerSkills = wageSeekerSkills;

  factory _$SkillsListImpl.fromJson(Map<String, dynamic> json) =>
      _$$SkillsListImplFromJson(json);

  final List<WageSeekerSkills>? _wageSeekerSkills;
  @override
  @JsonKey(name: 'SOR')
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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SkillsListImpl &&
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
  _$$SkillsListImplCopyWith<_$SkillsListImpl> get copyWith =>
      __$$SkillsListImplCopyWithImpl<_$SkillsListImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$SkillsListImplToJson(
      this,
    );
  }
}

abstract class _SkillsList implements SkillsList {
  const factory _SkillsList(
      {@JsonKey(name: 'SOR')
          final List<WageSeekerSkills>? wageSeekerSkills}) = _$SkillsListImpl;

  factory _SkillsList.fromJson(Map<String, dynamic> json) =
      _$SkillsListImpl.fromJson;

  @override
  @JsonKey(name: 'SOR')
  List<WageSeekerSkills>? get wageSeekerSkills;
  @override
  @JsonKey(ignore: true)
  _$$SkillsListImplCopyWith<_$SkillsListImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WageSeekerSkills _$WageSeekerSkillsFromJson(Map<String, dynamic> json) {
  return _WageSeekerSkills.fromJson(json);
}

/// @nodoc
mixin _$WageSeekerSkills {
  String? get code => throw _privateConstructorUsedError;
  int? get amount => throw _privateConstructorUsedError;
  bool? get active => throw _privateConstructorUsedError;
  String? get description => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;

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
  $Res call(
      {String? code,
      int? amount,
      bool? active,
      String? description,
      String? id});
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
    Object? code = freezed,
    Object? amount = freezed,
    Object? active = freezed,
    Object? description = freezed,
    Object? id = freezed,
  }) {
    return _then(_value.copyWith(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
      description: freezed == description
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WageSeekerSkillsImplCopyWith<$Res>
    implements $WageSeekerSkillsCopyWith<$Res> {
  factory _$$WageSeekerSkillsImplCopyWith(_$WageSeekerSkillsImpl value,
          $Res Function(_$WageSeekerSkillsImpl) then) =
      __$$WageSeekerSkillsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? code,
      int? amount,
      bool? active,
      String? description,
      String? id});
}

/// @nodoc
class __$$WageSeekerSkillsImplCopyWithImpl<$Res>
    extends _$WageSeekerSkillsCopyWithImpl<$Res, _$WageSeekerSkillsImpl>
    implements _$$WageSeekerSkillsImplCopyWith<$Res> {
  __$$WageSeekerSkillsImplCopyWithImpl(_$WageSeekerSkillsImpl _value,
      $Res Function(_$WageSeekerSkillsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? amount = freezed,
    Object? active = freezed,
    Object? description = freezed,
    Object? id = freezed,
  }) {
    return _then(_$WageSeekerSkillsImpl(
      freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
      freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
      freezed == description
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String?,
      freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WageSeekerSkillsImpl implements _WageSeekerSkills {
  const _$WageSeekerSkillsImpl(
      this.code, this.amount, this.active, this.description, this.id);

  factory _$WageSeekerSkillsImpl.fromJson(Map<String, dynamic> json) =>
      _$$WageSeekerSkillsImplFromJson(json);

  @override
  final String? code;
  @override
  final int? amount;
  @override
  final bool? active;
  @override
  final String? description;
  @override
  final String? id;

  @override
  String toString() {
    return 'WageSeekerSkills(code: $code, amount: $amount, active: $active, description: $description, id: $id)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerSkillsImpl &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.active, active) || other.active == active) &&
            (identical(other.description, description) ||
                other.description == description) &&
            (identical(other.id, id) || other.id == id));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, code, amount, active, description, id);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerSkillsImplCopyWith<_$WageSeekerSkillsImpl> get copyWith =>
      __$$WageSeekerSkillsImplCopyWithImpl<_$WageSeekerSkillsImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WageSeekerSkillsImplToJson(
      this,
    );
  }
}

abstract class _WageSeekerSkills implements WageSeekerSkills {
  const factory _WageSeekerSkills(
      final String? code,
      final int? amount,
      final bool? active,
      final String? description,
      final String? id) = _$WageSeekerSkillsImpl;

  factory _WageSeekerSkills.fromJson(Map<String, dynamic> json) =
      _$WageSeekerSkillsImpl.fromJson;

  @override
  String? get code;
  @override
  int? get amount;
  @override
  bool? get active;
  @override
  String? get description;
  @override
  String? get id;
  @override
  @JsonKey(ignore: true)
  _$$WageSeekerSkillsImplCopyWith<_$WageSeekerSkillsImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
