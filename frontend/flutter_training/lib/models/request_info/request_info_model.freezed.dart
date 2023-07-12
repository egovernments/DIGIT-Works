// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'request_info_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

RequestInfoMapperModel _$RequestInfoMapperModelFromJson(
    Map<String, dynamic> json) {
  return _RequestInfoMapperModel.fromJson(json);
}

/// @nodoc
mixin _$RequestInfoMapperModel {
  RequestInfoModel? get requestInfo => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $RequestInfoMapperModelCopyWith<RequestInfoMapperModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $RequestInfoMapperModelCopyWith<$Res> {
  factory $RequestInfoMapperModelCopyWith(RequestInfoMapperModel value,
          $Res Function(RequestInfoMapperModel) then) =
      _$RequestInfoMapperModelCopyWithImpl<$Res, RequestInfoMapperModel>;
  @useResult
  $Res call({RequestInfoModel? requestInfo});

  $RequestInfoModelCopyWith<$Res>? get requestInfo;
}

/// @nodoc
class _$RequestInfoMapperModelCopyWithImpl<$Res,
        $Val extends RequestInfoMapperModel>
    implements $RequestInfoMapperModelCopyWith<$Res> {
  _$RequestInfoMapperModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? requestInfo = freezed,
  }) {
    return _then(_value.copyWith(
      requestInfo: freezed == requestInfo
          ? _value.requestInfo
          : requestInfo // ignore: cast_nullable_to_non_nullable
              as RequestInfoModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $RequestInfoModelCopyWith<$Res>? get requestInfo {
    if (_value.requestInfo == null) {
      return null;
    }

    return $RequestInfoModelCopyWith<$Res>(_value.requestInfo!, (value) {
      return _then(_value.copyWith(requestInfo: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_RequestInfoMapperModelCopyWith<$Res>
    implements $RequestInfoMapperModelCopyWith<$Res> {
  factory _$$_RequestInfoMapperModelCopyWith(_$_RequestInfoMapperModel value,
          $Res Function(_$_RequestInfoMapperModel) then) =
      __$$_RequestInfoMapperModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({RequestInfoModel? requestInfo});

  @override
  $RequestInfoModelCopyWith<$Res>? get requestInfo;
}

/// @nodoc
class __$$_RequestInfoMapperModelCopyWithImpl<$Res>
    extends _$RequestInfoMapperModelCopyWithImpl<$Res,
        _$_RequestInfoMapperModel>
    implements _$$_RequestInfoMapperModelCopyWith<$Res> {
  __$$_RequestInfoMapperModelCopyWithImpl(_$_RequestInfoMapperModel _value,
      $Res Function(_$_RequestInfoMapperModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? requestInfo = freezed,
  }) {
    return _then(_$_RequestInfoMapperModel(
      requestInfo: freezed == requestInfo
          ? _value.requestInfo
          : requestInfo // ignore: cast_nullable_to_non_nullable
              as RequestInfoModel?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_RequestInfoMapperModel implements _RequestInfoMapperModel {
  const _$_RequestInfoMapperModel({this.requestInfo});

  factory _$_RequestInfoMapperModel.fromJson(Map<String, dynamic> json) =>
      _$$_RequestInfoMapperModelFromJson(json);

  @override
  final RequestInfoModel? requestInfo;

  @override
  String toString() {
    return 'RequestInfoMapperModel(requestInfo: $requestInfo)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_RequestInfoMapperModel &&
            (identical(other.requestInfo, requestInfo) ||
                other.requestInfo == requestInfo));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, requestInfo);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_RequestInfoMapperModelCopyWith<_$_RequestInfoMapperModel> get copyWith =>
      __$$_RequestInfoMapperModelCopyWithImpl<_$_RequestInfoMapperModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_RequestInfoMapperModelToJson(
      this,
    );
  }
}

abstract class _RequestInfoMapperModel implements RequestInfoMapperModel {
  const factory _RequestInfoMapperModel({final RequestInfoModel? requestInfo}) =
      _$_RequestInfoMapperModel;

  factory _RequestInfoMapperModel.fromJson(Map<String, dynamic> json) =
      _$_RequestInfoMapperModel.fromJson;

  @override
  RequestInfoModel? get requestInfo;
  @override
  @JsonKey(ignore: true)
  _$$_RequestInfoMapperModelCopyWith<_$_RequestInfoMapperModel> get copyWith =>
      throw _privateConstructorUsedError;
}

RequestInfoModel _$RequestInfoModelFromJson(Map<String, dynamic> json) {
  return _RequestInfoModel.fromJson(json);
}

/// @nodoc
mixin _$RequestInfoModel {
  String? get apiId => throw _privateConstructorUsedError;
  String? get ver => throw _privateConstructorUsedError;
  String? get ts => throw _privateConstructorUsedError;
  String? get action => throw _privateConstructorUsedError;
  String? get did => throw _privateConstructorUsedError;
  String? get key => throw _privateConstructorUsedError;
  String? get msgId => throw _privateConstructorUsedError;
  String? get authToken => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $RequestInfoModelCopyWith<RequestInfoModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $RequestInfoModelCopyWith<$Res> {
  factory $RequestInfoModelCopyWith(
          RequestInfoModel value, $Res Function(RequestInfoModel) then) =
      _$RequestInfoModelCopyWithImpl<$Res, RequestInfoModel>;
  @useResult
  $Res call(
      {String? apiId,
      String? ver,
      String? ts,
      String? action,
      String? did,
      String? key,
      String? msgId,
      String? authToken});
}

/// @nodoc
class _$RequestInfoModelCopyWithImpl<$Res, $Val extends RequestInfoModel>
    implements $RequestInfoModelCopyWith<$Res> {
  _$RequestInfoModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? apiId = freezed,
    Object? ver = freezed,
    Object? ts = freezed,
    Object? action = freezed,
    Object? did = freezed,
    Object? key = freezed,
    Object? msgId = freezed,
    Object? authToken = freezed,
  }) {
    return _then(_value.copyWith(
      apiId: freezed == apiId
          ? _value.apiId
          : apiId // ignore: cast_nullable_to_non_nullable
              as String?,
      ver: freezed == ver
          ? _value.ver
          : ver // ignore: cast_nullable_to_non_nullable
              as String?,
      ts: freezed == ts
          ? _value.ts
          : ts // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      did: freezed == did
          ? _value.did
          : did // ignore: cast_nullable_to_non_nullable
              as String?,
      key: freezed == key
          ? _value.key
          : key // ignore: cast_nullable_to_non_nullable
              as String?,
      msgId: freezed == msgId
          ? _value.msgId
          : msgId // ignore: cast_nullable_to_non_nullable
              as String?,
      authToken: freezed == authToken
          ? _value.authToken
          : authToken // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_RequestInfoModelCopyWith<$Res>
    implements $RequestInfoModelCopyWith<$Res> {
  factory _$$_RequestInfoModelCopyWith(
          _$_RequestInfoModel value, $Res Function(_$_RequestInfoModel) then) =
      __$$_RequestInfoModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? apiId,
      String? ver,
      String? ts,
      String? action,
      String? did,
      String? key,
      String? msgId,
      String? authToken});
}

/// @nodoc
class __$$_RequestInfoModelCopyWithImpl<$Res>
    extends _$RequestInfoModelCopyWithImpl<$Res, _$_RequestInfoModel>
    implements _$$_RequestInfoModelCopyWith<$Res> {
  __$$_RequestInfoModelCopyWithImpl(
      _$_RequestInfoModel _value, $Res Function(_$_RequestInfoModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? apiId = freezed,
    Object? ver = freezed,
    Object? ts = freezed,
    Object? action = freezed,
    Object? did = freezed,
    Object? key = freezed,
    Object? msgId = freezed,
    Object? authToken = freezed,
  }) {
    return _then(_$_RequestInfoModel(
      apiId: freezed == apiId
          ? _value.apiId
          : apiId // ignore: cast_nullable_to_non_nullable
              as String?,
      ver: freezed == ver
          ? _value.ver
          : ver // ignore: cast_nullable_to_non_nullable
              as String?,
      ts: freezed == ts
          ? _value.ts
          : ts // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      did: freezed == did
          ? _value.did
          : did // ignore: cast_nullable_to_non_nullable
              as String?,
      key: freezed == key
          ? _value.key
          : key // ignore: cast_nullable_to_non_nullable
              as String?,
      msgId: freezed == msgId
          ? _value.msgId
          : msgId // ignore: cast_nullable_to_non_nullable
              as String?,
      authToken: freezed == authToken
          ? _value.authToken
          : authToken // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_RequestInfoModel implements _RequestInfoModel {
  const _$_RequestInfoModel(
      {this.apiId,
      this.ver,
      this.ts,
      this.action,
      this.did,
      this.key,
      this.msgId,
      this.authToken});

  factory _$_RequestInfoModel.fromJson(Map<String, dynamic> json) =>
      _$$_RequestInfoModelFromJson(json);

  @override
  final String? apiId;
  @override
  final String? ver;
  @override
  final String? ts;
  @override
  final String? action;
  @override
  final String? did;
  @override
  final String? key;
  @override
  final String? msgId;
  @override
  final String? authToken;

  @override
  String toString() {
    return 'RequestInfoModel(apiId: $apiId, ver: $ver, ts: $ts, action: $action, did: $did, key: $key, msgId: $msgId, authToken: $authToken)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_RequestInfoModel &&
            (identical(other.apiId, apiId) || other.apiId == apiId) &&
            (identical(other.ver, ver) || other.ver == ver) &&
            (identical(other.ts, ts) || other.ts == ts) &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.did, did) || other.did == did) &&
            (identical(other.key, key) || other.key == key) &&
            (identical(other.msgId, msgId) || other.msgId == msgId) &&
            (identical(other.authToken, authToken) ||
                other.authToken == authToken));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, apiId, ver, ts, action, did, key, msgId, authToken);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_RequestInfoModelCopyWith<_$_RequestInfoModel> get copyWith =>
      __$$_RequestInfoModelCopyWithImpl<_$_RequestInfoModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_RequestInfoModelToJson(
      this,
    );
  }
}

abstract class _RequestInfoModel implements RequestInfoModel {
  const factory _RequestInfoModel(
      {final String? apiId,
      final String? ver,
      final String? ts,
      final String? action,
      final String? did,
      final String? key,
      final String? msgId,
      final String? authToken}) = _$_RequestInfoModel;

  factory _RequestInfoModel.fromJson(Map<String, dynamic> json) =
      _$_RequestInfoModel.fromJson;

  @override
  String? get apiId;
  @override
  String? get ver;
  @override
  String? get ts;
  @override
  String? get action;
  @override
  String? get did;
  @override
  String? get key;
  @override
  String? get msgId;
  @override
  String? get authToken;
  @override
  @JsonKey(ignore: true)
  _$$_RequestInfoModelCopyWith<_$_RequestInfoModel> get copyWith =>
      throw _privateConstructorUsedError;
}
