// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'muster_submission.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

MusterSubmissionList _$MusterSubmissionListFromJson(Map<String, dynamic> json) {
  return _MusterSubmissionList.fromJson(json);
}

/// @nodoc
mixin _$MusterSubmissionList {
  @JsonKey(name: 'CBOMusterSubmission')
  List<MusterSubmission>? get musterSubmission =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterSubmissionListCopyWith<MusterSubmissionList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterSubmissionListCopyWith<$Res> {
  factory $MusterSubmissionListCopyWith(MusterSubmissionList value,
          $Res Function(MusterSubmissionList) then) =
      _$MusterSubmissionListCopyWithImpl<$Res, MusterSubmissionList>;
  @useResult
  $Res call(
      {@JsonKey(name: 'CBOMusterSubmission')
          List<MusterSubmission>? musterSubmission});
}

/// @nodoc
class _$MusterSubmissionListCopyWithImpl<$Res,
        $Val extends MusterSubmissionList>
    implements $MusterSubmissionListCopyWith<$Res> {
  _$MusterSubmissionListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterSubmission = freezed,
  }) {
    return _then(_value.copyWith(
      musterSubmission: freezed == musterSubmission
          ? _value.musterSubmission
          : musterSubmission // ignore: cast_nullable_to_non_nullable
              as List<MusterSubmission>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_MusterSubmissionListCopyWith<$Res>
    implements $MusterSubmissionListCopyWith<$Res> {
  factory _$$_MusterSubmissionListCopyWith(_$_MusterSubmissionList value,
          $Res Function(_$_MusterSubmissionList) then) =
      __$$_MusterSubmissionListCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'CBOMusterSubmission')
          List<MusterSubmission>? musterSubmission});
}

/// @nodoc
class __$$_MusterSubmissionListCopyWithImpl<$Res>
    extends _$MusterSubmissionListCopyWithImpl<$Res, _$_MusterSubmissionList>
    implements _$$_MusterSubmissionListCopyWith<$Res> {
  __$$_MusterSubmissionListCopyWithImpl(_$_MusterSubmissionList _value,
      $Res Function(_$_MusterSubmissionList) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterSubmission = freezed,
  }) {
    return _then(_$_MusterSubmissionList(
      musterSubmission: freezed == musterSubmission
          ? _value._musterSubmission
          : musterSubmission // ignore: cast_nullable_to_non_nullable
              as List<MusterSubmission>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterSubmissionList implements _MusterSubmissionList {
  const _$_MusterSubmissionList(
      {@JsonKey(name: 'CBOMusterSubmission')
          final List<MusterSubmission>? musterSubmission})
      : _musterSubmission = musterSubmission;

  factory _$_MusterSubmissionList.fromJson(Map<String, dynamic> json) =>
      _$$_MusterSubmissionListFromJson(json);

  final List<MusterSubmission>? _musterSubmission;
  @override
  @JsonKey(name: 'CBOMusterSubmission')
  List<MusterSubmission>? get musterSubmission {
    final value = _musterSubmission;
    if (value == null) return null;
    if (_musterSubmission is EqualUnmodifiableListView)
      return _musterSubmission;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MusterSubmissionList(musterSubmission: $musterSubmission)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterSubmissionList &&
            const DeepCollectionEquality()
                .equals(other._musterSubmission, _musterSubmission));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_musterSubmission));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterSubmissionListCopyWith<_$_MusterSubmissionList> get copyWith =>
      __$$_MusterSubmissionListCopyWithImpl<_$_MusterSubmissionList>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterSubmissionListToJson(
      this,
    );
  }
}

abstract class _MusterSubmissionList implements MusterSubmissionList {
  const factory _MusterSubmissionList(
          {@JsonKey(name: 'CBOMusterSubmission')
              final List<MusterSubmission>? musterSubmission}) =
      _$_MusterSubmissionList;

  factory _MusterSubmissionList.fromJson(Map<String, dynamic> json) =
      _$_MusterSubmissionList.fromJson;

  @override
  @JsonKey(name: 'CBOMusterSubmission')
  List<MusterSubmission>? get musterSubmission;
  @override
  @JsonKey(ignore: true)
  _$$_MusterSubmissionListCopyWith<_$_MusterSubmissionList> get copyWith =>
      throw _privateConstructorUsedError;
}

MusterSubmission _$MusterSubmissionFromJson(Map<String, dynamic> json) {
  return _MusterSubmission.fromJson(json);
}

/// @nodoc
mixin _$MusterSubmission {
  String get code => throw _privateConstructorUsedError;
  String get value => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterSubmissionCopyWith<MusterSubmission> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterSubmissionCopyWith<$Res> {
  factory $MusterSubmissionCopyWith(
          MusterSubmission value, $Res Function(MusterSubmission) then) =
      _$MusterSubmissionCopyWithImpl<$Res, MusterSubmission>;
  @useResult
  $Res call({String code, String value, bool active});
}

/// @nodoc
class _$MusterSubmissionCopyWithImpl<$Res, $Val extends MusterSubmission>
    implements $MusterSubmissionCopyWith<$Res> {
  _$MusterSubmissionCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? value = null,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_MusterSubmissionCopyWith<$Res>
    implements $MusterSubmissionCopyWith<$Res> {
  factory _$$_MusterSubmissionCopyWith(
          _$_MusterSubmission value, $Res Function(_$_MusterSubmission) then) =
      __$$_MusterSubmissionCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String code, String value, bool active});
}

/// @nodoc
class __$$_MusterSubmissionCopyWithImpl<$Res>
    extends _$MusterSubmissionCopyWithImpl<$Res, _$_MusterSubmission>
    implements _$$_MusterSubmissionCopyWith<$Res> {
  __$$_MusterSubmissionCopyWithImpl(
      _$_MusterSubmission _value, $Res Function(_$_MusterSubmission) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? value = null,
    Object? active = null,
  }) {
    return _then(_$_MusterSubmission(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterSubmission implements _MusterSubmission {
  const _$_MusterSubmission(
      {required this.code, required this.value, required this.active});

  factory _$_MusterSubmission.fromJson(Map<String, dynamic> json) =>
      _$$_MusterSubmissionFromJson(json);

  @override
  final String code;
  @override
  final String value;
  @override
  final bool active;

  @override
  String toString() {
    return 'MusterSubmission(code: $code, value: $value, active: $active)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterSubmission &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.value, value) || other.value == value) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code, value, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterSubmissionCopyWith<_$_MusterSubmission> get copyWith =>
      __$$_MusterSubmissionCopyWithImpl<_$_MusterSubmission>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterSubmissionToJson(
      this,
    );
  }
}

abstract class _MusterSubmission implements MusterSubmission {
  const factory _MusterSubmission(
      {required final String code,
      required final String value,
      required final bool active}) = _$_MusterSubmission;

  factory _MusterSubmission.fromJson(Map<String, dynamic> json) =
      _$_MusterSubmission.fromJson;

  @override
  String get code;
  @override
  String get value;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$_MusterSubmissionCopyWith<_$_MusterSubmission> get copyWith =>
      throw _privateConstructorUsedError;
}
