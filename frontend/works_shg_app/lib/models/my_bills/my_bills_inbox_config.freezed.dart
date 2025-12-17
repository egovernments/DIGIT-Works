// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'my_bills_inbox_config.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

MyBillsInboxConfigList _$MyBillsInboxConfigListFromJson(
    Map<String, dynamic> json) {
  return _MyBillsInboxConfigList.fromJson(json);
}

/// @nodoc
mixin _$MyBillsInboxConfigList {
  @JsonKey(name: 'CBOBillInboxConfig')
  List<MyBillsInboxConfig> get myBillsInboxConfig =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MyBillsInboxConfigListCopyWith<MyBillsInboxConfigList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MyBillsInboxConfigListCopyWith<$Res> {
  factory $MyBillsInboxConfigListCopyWith(MyBillsInboxConfigList value,
          $Res Function(MyBillsInboxConfigList) then) =
      _$MyBillsInboxConfigListCopyWithImpl<$Res, MyBillsInboxConfigList>;
  @useResult
  $Res call(
      {@JsonKey(name: 'CBOBillInboxConfig')
          List<MyBillsInboxConfig> myBillsInboxConfig});
}

/// @nodoc
class _$MyBillsInboxConfigListCopyWithImpl<$Res,
        $Val extends MyBillsInboxConfigList>
    implements $MyBillsInboxConfigListCopyWith<$Res> {
  _$MyBillsInboxConfigListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? myBillsInboxConfig = null,
  }) {
    return _then(_value.copyWith(
      myBillsInboxConfig: null == myBillsInboxConfig
          ? _value.myBillsInboxConfig
          : myBillsInboxConfig // ignore: cast_nullable_to_non_nullable
              as List<MyBillsInboxConfig>,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$MyBillsInboxConfigListImplCopyWith<$Res>
    implements $MyBillsInboxConfigListCopyWith<$Res> {
  factory _$$MyBillsInboxConfigListImplCopyWith(
          _$MyBillsInboxConfigListImpl value,
          $Res Function(_$MyBillsInboxConfigListImpl) then) =
      __$$MyBillsInboxConfigListImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'CBOBillInboxConfig')
          List<MyBillsInboxConfig> myBillsInboxConfig});
}

/// @nodoc
class __$$MyBillsInboxConfigListImplCopyWithImpl<$Res>
    extends _$MyBillsInboxConfigListCopyWithImpl<$Res,
        _$MyBillsInboxConfigListImpl>
    implements _$$MyBillsInboxConfigListImplCopyWith<$Res> {
  __$$MyBillsInboxConfigListImplCopyWithImpl(
      _$MyBillsInboxConfigListImpl _value,
      $Res Function(_$MyBillsInboxConfigListImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? myBillsInboxConfig = null,
  }) {
    return _then(_$MyBillsInboxConfigListImpl(
      myBillsInboxConfig: null == myBillsInboxConfig
          ? _value._myBillsInboxConfig
          : myBillsInboxConfig // ignore: cast_nullable_to_non_nullable
              as List<MyBillsInboxConfig>,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MyBillsInboxConfigListImpl implements _MyBillsInboxConfigList {
  const _$MyBillsInboxConfigListImpl(
      {@JsonKey(name: 'CBOBillInboxConfig')
          required final List<MyBillsInboxConfig> myBillsInboxConfig})
      : _myBillsInboxConfig = myBillsInboxConfig;

  factory _$MyBillsInboxConfigListImpl.fromJson(Map<String, dynamic> json) =>
      _$$MyBillsInboxConfigListImplFromJson(json);

  final List<MyBillsInboxConfig> _myBillsInboxConfig;
  @override
  @JsonKey(name: 'CBOBillInboxConfig')
  List<MyBillsInboxConfig> get myBillsInboxConfig {
    if (_myBillsInboxConfig is EqualUnmodifiableListView)
      return _myBillsInboxConfig;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_myBillsInboxConfig);
  }

  @override
  String toString() {
    return 'MyBillsInboxConfigList(myBillsInboxConfig: $myBillsInboxConfig)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MyBillsInboxConfigListImpl &&
            const DeepCollectionEquality()
                .equals(other._myBillsInboxConfig, _myBillsInboxConfig));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_myBillsInboxConfig));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MyBillsInboxConfigListImplCopyWith<_$MyBillsInboxConfigListImpl>
      get copyWith => __$$MyBillsInboxConfigListImplCopyWithImpl<
          _$MyBillsInboxConfigListImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MyBillsInboxConfigListImplToJson(
      this,
    );
  }
}

abstract class _MyBillsInboxConfigList implements MyBillsInboxConfigList {
  const factory _MyBillsInboxConfigList(
          {@JsonKey(name: 'CBOBillInboxConfig')
              required final List<MyBillsInboxConfig> myBillsInboxConfig}) =
      _$MyBillsInboxConfigListImpl;

  factory _MyBillsInboxConfigList.fromJson(Map<String, dynamic> json) =
      _$MyBillsInboxConfigListImpl.fromJson;

  @override
  @JsonKey(name: 'CBOBillInboxConfig')
  List<MyBillsInboxConfig> get myBillsInboxConfig;
  @override
  @JsonKey(ignore: true)
  _$$MyBillsInboxConfigListImplCopyWith<_$MyBillsInboxConfigListImpl>
      get copyWith => throw _privateConstructorUsedError;
}

MyBillsInboxConfig _$MyBillsInboxConfigFromJson(Map<String, dynamic> json) {
  return _MyBillsInboxConfig.fromJson(json);
}

/// @nodoc
mixin _$MyBillsInboxConfig {
  String get rejectedCode => throw _privateConstructorUsedError;
  String get approvedCode => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MyBillsInboxConfigCopyWith<MyBillsInboxConfig> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MyBillsInboxConfigCopyWith<$Res> {
  factory $MyBillsInboxConfigCopyWith(
          MyBillsInboxConfig value, $Res Function(MyBillsInboxConfig) then) =
      _$MyBillsInboxConfigCopyWithImpl<$Res, MyBillsInboxConfig>;
  @useResult
  $Res call({String rejectedCode, String approvedCode});
}

/// @nodoc
class _$MyBillsInboxConfigCopyWithImpl<$Res, $Val extends MyBillsInboxConfig>
    implements $MyBillsInboxConfigCopyWith<$Res> {
  _$MyBillsInboxConfigCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? rejectedCode = null,
    Object? approvedCode = null,
  }) {
    return _then(_value.copyWith(
      rejectedCode: null == rejectedCode
          ? _value.rejectedCode
          : rejectedCode // ignore: cast_nullable_to_non_nullable
              as String,
      approvedCode: null == approvedCode
          ? _value.approvedCode
          : approvedCode // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$MyBillsInboxConfigImplCopyWith<$Res>
    implements $MyBillsInboxConfigCopyWith<$Res> {
  factory _$$MyBillsInboxConfigImplCopyWith(_$MyBillsInboxConfigImpl value,
          $Res Function(_$MyBillsInboxConfigImpl) then) =
      __$$MyBillsInboxConfigImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String rejectedCode, String approvedCode});
}

/// @nodoc
class __$$MyBillsInboxConfigImplCopyWithImpl<$Res>
    extends _$MyBillsInboxConfigCopyWithImpl<$Res, _$MyBillsInboxConfigImpl>
    implements _$$MyBillsInboxConfigImplCopyWith<$Res> {
  __$$MyBillsInboxConfigImplCopyWithImpl(_$MyBillsInboxConfigImpl _value,
      $Res Function(_$MyBillsInboxConfigImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? rejectedCode = null,
    Object? approvedCode = null,
  }) {
    return _then(_$MyBillsInboxConfigImpl(
      rejectedCode: null == rejectedCode
          ? _value.rejectedCode
          : rejectedCode // ignore: cast_nullable_to_non_nullable
              as String,
      approvedCode: null == approvedCode
          ? _value.approvedCode
          : approvedCode // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$MyBillsInboxConfigImpl implements _MyBillsInboxConfig {
  const _$MyBillsInboxConfigImpl(
      {required this.rejectedCode, required this.approvedCode});

  factory _$MyBillsInboxConfigImpl.fromJson(Map<String, dynamic> json) =>
      _$$MyBillsInboxConfigImplFromJson(json);

  @override
  final String rejectedCode;
  @override
  final String approvedCode;

  @override
  String toString() {
    return 'MyBillsInboxConfig(rejectedCode: $rejectedCode, approvedCode: $approvedCode)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MyBillsInboxConfigImpl &&
            (identical(other.rejectedCode, rejectedCode) ||
                other.rejectedCode == rejectedCode) &&
            (identical(other.approvedCode, approvedCode) ||
                other.approvedCode == approvedCode));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, rejectedCode, approvedCode);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MyBillsInboxConfigImplCopyWith<_$MyBillsInboxConfigImpl> get copyWith =>
      __$$MyBillsInboxConfigImplCopyWithImpl<_$MyBillsInboxConfigImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$MyBillsInboxConfigImplToJson(
      this,
    );
  }
}

abstract class _MyBillsInboxConfig implements MyBillsInboxConfig {
  const factory _MyBillsInboxConfig(
      {required final String rejectedCode,
      required final String approvedCode}) = _$MyBillsInboxConfigImpl;

  factory _MyBillsInboxConfig.fromJson(Map<String, dynamic> json) =
      _$MyBillsInboxConfigImpl.fromJson;

  @override
  String get rejectedCode;
  @override
  String get approvedCode;
  @override
  @JsonKey(ignore: true)
  _$$MyBillsInboxConfigImplCopyWith<_$MyBillsInboxConfigImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
