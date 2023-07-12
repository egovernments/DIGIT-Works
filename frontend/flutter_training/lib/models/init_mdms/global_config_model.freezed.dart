// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'global_config_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

GlobalConfigModel _$GlobalConfigModelFromJson(Map<String, dynamic> json) {
  return _GlobalConfigModel.fromJson(json);
}

/// @nodoc
mixin _$GlobalConfigModel {
  @JsonKey(name: 'globalConfigs')
  GlobalConfigs? get globalConfigs => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $GlobalConfigModelCopyWith<GlobalConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $GlobalConfigModelCopyWith<$Res> {
  factory $GlobalConfigModelCopyWith(
          GlobalConfigModel value, $Res Function(GlobalConfigModel) then) =
      _$GlobalConfigModelCopyWithImpl<$Res, GlobalConfigModel>;
  @useResult
  $Res call({@JsonKey(name: 'globalConfigs') GlobalConfigs? globalConfigs});

  $GlobalConfigsCopyWith<$Res>? get globalConfigs;
}

/// @nodoc
class _$GlobalConfigModelCopyWithImpl<$Res, $Val extends GlobalConfigModel>
    implements $GlobalConfigModelCopyWith<$Res> {
  _$GlobalConfigModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? globalConfigs = freezed,
  }) {
    return _then(_value.copyWith(
      globalConfigs: freezed == globalConfigs
          ? _value.globalConfigs
          : globalConfigs // ignore: cast_nullable_to_non_nullable
              as GlobalConfigs?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $GlobalConfigsCopyWith<$Res>? get globalConfigs {
    if (_value.globalConfigs == null) {
      return null;
    }

    return $GlobalConfigsCopyWith<$Res>(_value.globalConfigs!, (value) {
      return _then(_value.copyWith(globalConfigs: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_GlobalConfigModelCopyWith<$Res>
    implements $GlobalConfigModelCopyWith<$Res> {
  factory _$$_GlobalConfigModelCopyWith(_$_GlobalConfigModel value,
          $Res Function(_$_GlobalConfigModel) then) =
      __$$_GlobalConfigModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'globalConfigs') GlobalConfigs? globalConfigs});

  @override
  $GlobalConfigsCopyWith<$Res>? get globalConfigs;
}

/// @nodoc
class __$$_GlobalConfigModelCopyWithImpl<$Res>
    extends _$GlobalConfigModelCopyWithImpl<$Res, _$_GlobalConfigModel>
    implements _$$_GlobalConfigModelCopyWith<$Res> {
  __$$_GlobalConfigModelCopyWithImpl(
      _$_GlobalConfigModel _value, $Res Function(_$_GlobalConfigModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? globalConfigs = freezed,
  }) {
    return _then(_$_GlobalConfigModel(
      globalConfigs: freezed == globalConfigs
          ? _value.globalConfigs
          : globalConfigs // ignore: cast_nullable_to_non_nullable
              as GlobalConfigs?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_GlobalConfigModel implements _GlobalConfigModel {
  const _$_GlobalConfigModel(
      {@JsonKey(name: 'globalConfigs') this.globalConfigs});

  factory _$_GlobalConfigModel.fromJson(Map<String, dynamic> json) =>
      _$$_GlobalConfigModelFromJson(json);

  @override
  @JsonKey(name: 'globalConfigs')
  final GlobalConfigs? globalConfigs;

  @override
  String toString() {
    return 'GlobalConfigModel(globalConfigs: $globalConfigs)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_GlobalConfigModel &&
            (identical(other.globalConfigs, globalConfigs) ||
                other.globalConfigs == globalConfigs));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, globalConfigs);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_GlobalConfigModelCopyWith<_$_GlobalConfigModel> get copyWith =>
      __$$_GlobalConfigModelCopyWithImpl<_$_GlobalConfigModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_GlobalConfigModelToJson(
      this,
    );
  }
}

abstract class _GlobalConfigModel implements GlobalConfigModel {
  const factory _GlobalConfigModel(
      {@JsonKey(name: 'globalConfigs')
      final GlobalConfigs? globalConfigs}) = _$_GlobalConfigModel;

  factory _GlobalConfigModel.fromJson(Map<String, dynamic> json) =
      _$_GlobalConfigModel.fromJson;

  @override
  @JsonKey(name: 'globalConfigs')
  GlobalConfigs? get globalConfigs;
  @override
  @JsonKey(ignore: true)
  _$$_GlobalConfigModelCopyWith<_$_GlobalConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

GlobalConfigs _$GlobalConfigsFromJson(Map<String, dynamic> json) {
  return _GlobalConfigs.fromJson(json);
}

/// @nodoc
mixin _$GlobalConfigs {
  @JsonKey(name: 'stateTenantId')
  String get stateTenantId => throw _privateConstructorUsedError;
  @JsonKey(name: 'gmaps_api_key')
  String? get gmaps_api_key => throw _privateConstructorUsedError;
  @JsonKey(name: 'finEnv')
  String? get finEnv => throw _privateConstructorUsedError;
  @JsonKey(name: 'contextPath')
  String? get contextPath => throw _privateConstructorUsedError;
  @JsonKey(name: 'footerLogoURL')
  String? get footerLogoURL => throw _privateConstructorUsedError;
  @JsonKey(name: 'footerBWLogoURL')
  String? get footerBWLogoURL => throw _privateConstructorUsedError;
  @JsonKey(name: 'centralInstanceEnabled')
  bool? get centralInstanceEnabled => throw _privateConstructorUsedError;
  @JsonKey(name: 'assetS3Bucket')
  String? get assetS3Bucket => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $GlobalConfigsCopyWith<GlobalConfigs> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $GlobalConfigsCopyWith<$Res> {
  factory $GlobalConfigsCopyWith(
          GlobalConfigs value, $Res Function(GlobalConfigs) then) =
      _$GlobalConfigsCopyWithImpl<$Res, GlobalConfigs>;
  @useResult
  $Res call(
      {@JsonKey(name: 'stateTenantId') String stateTenantId,
      @JsonKey(name: 'gmaps_api_key') String? gmaps_api_key,
      @JsonKey(name: 'finEnv') String? finEnv,
      @JsonKey(name: 'contextPath') String? contextPath,
      @JsonKey(name: 'footerLogoURL') String? footerLogoURL,
      @JsonKey(name: 'footerBWLogoURL') String? footerBWLogoURL,
      @JsonKey(name: 'centralInstanceEnabled') bool? centralInstanceEnabled,
      @JsonKey(name: 'assetS3Bucket') String? assetS3Bucket});
}

/// @nodoc
class _$GlobalConfigsCopyWithImpl<$Res, $Val extends GlobalConfigs>
    implements $GlobalConfigsCopyWith<$Res> {
  _$GlobalConfigsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? stateTenantId = null,
    Object? gmaps_api_key = freezed,
    Object? finEnv = freezed,
    Object? contextPath = freezed,
    Object? footerLogoURL = freezed,
    Object? footerBWLogoURL = freezed,
    Object? centralInstanceEnabled = freezed,
    Object? assetS3Bucket = freezed,
  }) {
    return _then(_value.copyWith(
      stateTenantId: null == stateTenantId
          ? _value.stateTenantId
          : stateTenantId // ignore: cast_nullable_to_non_nullable
              as String,
      gmaps_api_key: freezed == gmaps_api_key
          ? _value.gmaps_api_key
          : gmaps_api_key // ignore: cast_nullable_to_non_nullable
              as String?,
      finEnv: freezed == finEnv
          ? _value.finEnv
          : finEnv // ignore: cast_nullable_to_non_nullable
              as String?,
      contextPath: freezed == contextPath
          ? _value.contextPath
          : contextPath // ignore: cast_nullable_to_non_nullable
              as String?,
      footerLogoURL: freezed == footerLogoURL
          ? _value.footerLogoURL
          : footerLogoURL // ignore: cast_nullable_to_non_nullable
              as String?,
      footerBWLogoURL: freezed == footerBWLogoURL
          ? _value.footerBWLogoURL
          : footerBWLogoURL // ignore: cast_nullable_to_non_nullable
              as String?,
      centralInstanceEnabled: freezed == centralInstanceEnabled
          ? _value.centralInstanceEnabled
          : centralInstanceEnabled // ignore: cast_nullable_to_non_nullable
              as bool?,
      assetS3Bucket: freezed == assetS3Bucket
          ? _value.assetS3Bucket
          : assetS3Bucket // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_GlobalConfigsCopyWith<$Res>
    implements $GlobalConfigsCopyWith<$Res> {
  factory _$$_GlobalConfigsCopyWith(
          _$_GlobalConfigs value, $Res Function(_$_GlobalConfigs) then) =
      __$$_GlobalConfigsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'stateTenantId') String stateTenantId,
      @JsonKey(name: 'gmaps_api_key') String? gmaps_api_key,
      @JsonKey(name: 'finEnv') String? finEnv,
      @JsonKey(name: 'contextPath') String? contextPath,
      @JsonKey(name: 'footerLogoURL') String? footerLogoURL,
      @JsonKey(name: 'footerBWLogoURL') String? footerBWLogoURL,
      @JsonKey(name: 'centralInstanceEnabled') bool? centralInstanceEnabled,
      @JsonKey(name: 'assetS3Bucket') String? assetS3Bucket});
}

/// @nodoc
class __$$_GlobalConfigsCopyWithImpl<$Res>
    extends _$GlobalConfigsCopyWithImpl<$Res, _$_GlobalConfigs>
    implements _$$_GlobalConfigsCopyWith<$Res> {
  __$$_GlobalConfigsCopyWithImpl(
      _$_GlobalConfigs _value, $Res Function(_$_GlobalConfigs) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? stateTenantId = null,
    Object? gmaps_api_key = freezed,
    Object? finEnv = freezed,
    Object? contextPath = freezed,
    Object? footerLogoURL = freezed,
    Object? footerBWLogoURL = freezed,
    Object? centralInstanceEnabled = freezed,
    Object? assetS3Bucket = freezed,
  }) {
    return _then(_$_GlobalConfigs(
      stateTenantId: null == stateTenantId
          ? _value.stateTenantId
          : stateTenantId // ignore: cast_nullable_to_non_nullable
              as String,
      gmaps_api_key: freezed == gmaps_api_key
          ? _value.gmaps_api_key
          : gmaps_api_key // ignore: cast_nullable_to_non_nullable
              as String?,
      finEnv: freezed == finEnv
          ? _value.finEnv
          : finEnv // ignore: cast_nullable_to_non_nullable
              as String?,
      contextPath: freezed == contextPath
          ? _value.contextPath
          : contextPath // ignore: cast_nullable_to_non_nullable
              as String?,
      footerLogoURL: freezed == footerLogoURL
          ? _value.footerLogoURL
          : footerLogoURL // ignore: cast_nullable_to_non_nullable
              as String?,
      footerBWLogoURL: freezed == footerBWLogoURL
          ? _value.footerBWLogoURL
          : footerBWLogoURL // ignore: cast_nullable_to_non_nullable
              as String?,
      centralInstanceEnabled: freezed == centralInstanceEnabled
          ? _value.centralInstanceEnabled
          : centralInstanceEnabled // ignore: cast_nullable_to_non_nullable
              as bool?,
      assetS3Bucket: freezed == assetS3Bucket
          ? _value.assetS3Bucket
          : assetS3Bucket // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_GlobalConfigs implements _GlobalConfigs {
  const _$_GlobalConfigs(
      {@JsonKey(name: 'stateTenantId') required this.stateTenantId,
      @JsonKey(name: 'gmaps_api_key') this.gmaps_api_key,
      @JsonKey(name: 'finEnv') this.finEnv,
      @JsonKey(name: 'contextPath') this.contextPath,
      @JsonKey(name: 'footerLogoURL') this.footerLogoURL,
      @JsonKey(name: 'footerBWLogoURL') this.footerBWLogoURL,
      @JsonKey(name: 'centralInstanceEnabled') this.centralInstanceEnabled,
      @JsonKey(name: 'assetS3Bucket') this.assetS3Bucket});

  factory _$_GlobalConfigs.fromJson(Map<String, dynamic> json) =>
      _$$_GlobalConfigsFromJson(json);

  @override
  @JsonKey(name: 'stateTenantId')
  final String stateTenantId;
  @override
  @JsonKey(name: 'gmaps_api_key')
  final String? gmaps_api_key;
  @override
  @JsonKey(name: 'finEnv')
  final String? finEnv;
  @override
  @JsonKey(name: 'contextPath')
  final String? contextPath;
  @override
  @JsonKey(name: 'footerLogoURL')
  final String? footerLogoURL;
  @override
  @JsonKey(name: 'footerBWLogoURL')
  final String? footerBWLogoURL;
  @override
  @JsonKey(name: 'centralInstanceEnabled')
  final bool? centralInstanceEnabled;
  @override
  @JsonKey(name: 'assetS3Bucket')
  final String? assetS3Bucket;

  @override
  String toString() {
    return 'GlobalConfigs(stateTenantId: $stateTenantId, gmaps_api_key: $gmaps_api_key, finEnv: $finEnv, contextPath: $contextPath, footerLogoURL: $footerLogoURL, footerBWLogoURL: $footerBWLogoURL, centralInstanceEnabled: $centralInstanceEnabled, assetS3Bucket: $assetS3Bucket)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_GlobalConfigs &&
            (identical(other.stateTenantId, stateTenantId) ||
                other.stateTenantId == stateTenantId) &&
            (identical(other.gmaps_api_key, gmaps_api_key) ||
                other.gmaps_api_key == gmaps_api_key) &&
            (identical(other.finEnv, finEnv) || other.finEnv == finEnv) &&
            (identical(other.contextPath, contextPath) ||
                other.contextPath == contextPath) &&
            (identical(other.footerLogoURL, footerLogoURL) ||
                other.footerLogoURL == footerLogoURL) &&
            (identical(other.footerBWLogoURL, footerBWLogoURL) ||
                other.footerBWLogoURL == footerBWLogoURL) &&
            (identical(other.centralInstanceEnabled, centralInstanceEnabled) ||
                other.centralInstanceEnabled == centralInstanceEnabled) &&
            (identical(other.assetS3Bucket, assetS3Bucket) ||
                other.assetS3Bucket == assetS3Bucket));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      stateTenantId,
      gmaps_api_key,
      finEnv,
      contextPath,
      footerLogoURL,
      footerBWLogoURL,
      centralInstanceEnabled,
      assetS3Bucket);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_GlobalConfigsCopyWith<_$_GlobalConfigs> get copyWith =>
      __$$_GlobalConfigsCopyWithImpl<_$_GlobalConfigs>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_GlobalConfigsToJson(
      this,
    );
  }
}

abstract class _GlobalConfigs implements GlobalConfigs {
  const factory _GlobalConfigs(
          {@JsonKey(name: 'stateTenantId') required final String stateTenantId,
          @JsonKey(name: 'gmaps_api_key') final String? gmaps_api_key,
          @JsonKey(name: 'finEnv') final String? finEnv,
          @JsonKey(name: 'contextPath') final String? contextPath,
          @JsonKey(name: 'footerLogoURL') final String? footerLogoURL,
          @JsonKey(name: 'footerBWLogoURL') final String? footerBWLogoURL,
          @JsonKey(name: 'centralInstanceEnabled')
          final bool? centralInstanceEnabled,
          @JsonKey(name: 'assetS3Bucket') final String? assetS3Bucket}) =
      _$_GlobalConfigs;

  factory _GlobalConfigs.fromJson(Map<String, dynamic> json) =
      _$_GlobalConfigs.fromJson;

  @override
  @JsonKey(name: 'stateTenantId')
  String get stateTenantId;
  @override
  @JsonKey(name: 'gmaps_api_key')
  String? get gmaps_api_key;
  @override
  @JsonKey(name: 'finEnv')
  String? get finEnv;
  @override
  @JsonKey(name: 'contextPath')
  String? get contextPath;
  @override
  @JsonKey(name: 'footerLogoURL')
  String? get footerLogoURL;
  @override
  @JsonKey(name: 'footerBWLogoURL')
  String? get footerBWLogoURL;
  @override
  @JsonKey(name: 'centralInstanceEnabled')
  bool? get centralInstanceEnabled;
  @override
  @JsonKey(name: 'assetS3Bucket')
  String? get assetS3Bucket;
  @override
  @JsonKey(ignore: true)
  _$$_GlobalConfigsCopyWith<_$_GlobalConfigs> get copyWith =>
      throw _privateConstructorUsedError;
}
