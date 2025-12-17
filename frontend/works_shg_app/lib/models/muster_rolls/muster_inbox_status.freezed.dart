// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'muster_inbox_status.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

MusterInboxStatusList _$MusterInboxStatusListFromJson(
    Map<String, dynamic> json) {
  return _MusterInboxStatusList.fromJson(json);
}

/// @nodoc
mixin _$MusterInboxStatusList {
  @JsonKey(name: 'CBOMusterInboxConfig')
  List<MusterInboxStatus>? get musterInboxStatus =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterInboxStatusListCopyWith<MusterInboxStatusList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterInboxStatusListCopyWith<$Res> {
  factory $MusterInboxStatusListCopyWith(MusterInboxStatusList value,
          $Res Function(MusterInboxStatusList) then) =
      _$MusterInboxStatusListCopyWithImpl<$Res, MusterInboxStatusList>;
  @useResult
  $Res call(
      {@JsonKey(name: 'CBOMusterInboxConfig')
          List<MusterInboxStatus>? musterInboxStatus});
}

/// @nodoc
class _$MusterInboxStatusListCopyWithImpl<$Res,
        $Val extends MusterInboxStatusList>
    implements $MusterInboxStatusListCopyWith<$Res> {
  _$MusterInboxStatusListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterInboxStatus = freezed,
  }) {
    return _then(_value.copyWith(
      musterInboxStatus: freezed == musterInboxStatus
          ? _value.musterInboxStatus
          : musterInboxStatus // ignore: cast_nullable_to_non_nullable
              as List<MusterInboxStatus>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$MusterInboxStatusListImplCopyWith<$Res>
    implements $MusterInboxStatusListCopyWith<$Res> {
  factory _$$MusterInboxStatusListImplCopyWith(
          _$MusterInboxStatusListImpl value,
          $Res Function(_$MusterInboxStatusListImpl) then) =
      __$$MusterInboxStatusListImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'CBOMusterInboxConfig')
          List<MusterInboxStatus>? musterInboxStatus});
}

/// @nodoc
class __$$MusterInboxStatusListImplCopyWithImpl<$Res>
    extends _$MusterInboxStatusListCopyWithImpl<$Res,
        _$MusterInboxStatusListImpl>
    implements _$$MusterInboxStatusListImplCopyWith<$Res> {
  __$$MusterInboxStatusListImplCopyWithImpl(_$MusterInboxStatusListImpl _value,
      $Res Function(_$MusterInboxStatusListImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterInboxStatus = freezed,
  }) {
    return _then(_$MusterInboxStatusListImpl(
      musterInboxStatus: freezed == musterInboxStatus
          ? _value._musterInboxStatus
          : musterInboxStatus // ignore: cast_nullable_to_non_nullable
              as List<MusterInboxStatus>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MusterInboxStatusListImpl implements _MusterInboxStatusList {
  const _$MusterInboxStatusListImpl(
      {@JsonKey(name: 'CBOMusterInboxConfig')
          final List<MusterInboxStatus>? musterInboxStatus})
      : _musterInboxStatus = musterInboxStatus;

  factory _$MusterInboxStatusListImpl.fromJson(Map<String, dynamic> json) =>
      _$$MusterInboxStatusListImplFromJson(json);

  final List<MusterInboxStatus>? _musterInboxStatus;
  @override
  @JsonKey(name: 'CBOMusterInboxConfig')
  List<MusterInboxStatus>? get musterInboxStatus {
    final value = _musterInboxStatus;
    if (value == null) return null;
    if (_musterInboxStatus is EqualUnmodifiableListView)
      return _musterInboxStatus;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MusterInboxStatusList(musterInboxStatus: $musterInboxStatus)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MusterInboxStatusListImpl &&
            const DeepCollectionEquality()
                .equals(other._musterInboxStatus, _musterInboxStatus));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_musterInboxStatus));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MusterInboxStatusListImplCopyWith<_$MusterInboxStatusListImpl>
      get copyWith => __$$MusterInboxStatusListImplCopyWithImpl<
          _$MusterInboxStatusListImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MusterInboxStatusListImplToJson(
      this,
    );
  }
}

abstract class _MusterInboxStatusList implements MusterInboxStatusList {
  const factory _MusterInboxStatusList(
          {@JsonKey(name: 'CBOMusterInboxConfig')
              final List<MusterInboxStatus>? musterInboxStatus}) =
      _$MusterInboxStatusListImpl;

  factory _MusterInboxStatusList.fromJson(Map<String, dynamic> json) =
      _$MusterInboxStatusListImpl.fromJson;

  @override
  @JsonKey(name: 'CBOMusterInboxConfig')
  List<MusterInboxStatus>? get musterInboxStatus;
  @override
  @JsonKey(ignore: true)
  _$$MusterInboxStatusListImplCopyWith<_$MusterInboxStatusListImpl>
      get copyWith => throw _privateConstructorUsedError;
}

MusterInboxStatus _$MusterInboxStatusFromJson(Map<String, dynamic> json) {
  return _MusterInboxStatus.fromJson(json);
}

/// @nodoc
mixin _$MusterInboxStatus {
  String get reSubmitCode => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterInboxStatusCopyWith<MusterInboxStatus> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterInboxStatusCopyWith<$Res> {
  factory $MusterInboxStatusCopyWith(
          MusterInboxStatus value, $Res Function(MusterInboxStatus) then) =
      _$MusterInboxStatusCopyWithImpl<$Res, MusterInboxStatus>;
  @useResult
  $Res call({String reSubmitCode});
}

/// @nodoc
class _$MusterInboxStatusCopyWithImpl<$Res, $Val extends MusterInboxStatus>
    implements $MusterInboxStatusCopyWith<$Res> {
  _$MusterInboxStatusCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? reSubmitCode = null,
  }) {
    return _then(_value.copyWith(
      reSubmitCode: null == reSubmitCode
          ? _value.reSubmitCode
          : reSubmitCode // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$MusterInboxStatusImplCopyWith<$Res>
    implements $MusterInboxStatusCopyWith<$Res> {
  factory _$$MusterInboxStatusImplCopyWith(_$MusterInboxStatusImpl value,
          $Res Function(_$MusterInboxStatusImpl) then) =
      __$$MusterInboxStatusImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String reSubmitCode});
}

/// @nodoc
class __$$MusterInboxStatusImplCopyWithImpl<$Res>
    extends _$MusterInboxStatusCopyWithImpl<$Res, _$MusterInboxStatusImpl>
    implements _$$MusterInboxStatusImplCopyWith<$Res> {
  __$$MusterInboxStatusImplCopyWithImpl(_$MusterInboxStatusImpl _value,
      $Res Function(_$MusterInboxStatusImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? reSubmitCode = null,
  }) {
    return _then(_$MusterInboxStatusImpl(
      reSubmitCode: null == reSubmitCode
          ? _value.reSubmitCode
          : reSubmitCode // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MusterInboxStatusImpl implements _MusterInboxStatus {
  const _$MusterInboxStatusImpl({required this.reSubmitCode});

  factory _$MusterInboxStatusImpl.fromJson(Map<String, dynamic> json) =>
      _$$MusterInboxStatusImplFromJson(json);

  @override
  final String reSubmitCode;

  @override
  String toString() {
    return 'MusterInboxStatus(reSubmitCode: $reSubmitCode)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MusterInboxStatusImpl &&
            (identical(other.reSubmitCode, reSubmitCode) ||
                other.reSubmitCode == reSubmitCode));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, reSubmitCode);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MusterInboxStatusImplCopyWith<_$MusterInboxStatusImpl> get copyWith =>
      __$$MusterInboxStatusImplCopyWithImpl<_$MusterInboxStatusImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MusterInboxStatusImplToJson(
      this,
    );
  }
}

abstract class _MusterInboxStatus implements MusterInboxStatus {
  const factory _MusterInboxStatus({required final String reSubmitCode}) =
      _$MusterInboxStatusImpl;

  factory _MusterInboxStatus.fromJson(Map<String, dynamic> json) =
      _$MusterInboxStatusImpl.fromJson;

  @override
  String get reSubmitCode;
  @override
  @JsonKey(ignore: true)
  _$$MusterInboxStatusImplCopyWith<_$MusterInboxStatusImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
