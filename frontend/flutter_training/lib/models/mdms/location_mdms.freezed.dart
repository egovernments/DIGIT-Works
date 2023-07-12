// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'location_mdms.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

Location _$LocationFromJson(Map<String, dynamic> json) {
  return _Location.fromJson(json);
}

/// @nodoc
mixin _$Location {
  @JsonKey(name: 'TenantBoundary')
  List<TenantBoundary>? get tenantBoundaryList =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $LocationCopyWith<Location> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LocationCopyWith<$Res> {
  factory $LocationCopyWith(Location value, $Res Function(Location) then) =
      _$LocationCopyWithImpl<$Res, Location>;
  @useResult
  $Res call(
      {@JsonKey(name: 'TenantBoundary')
      List<TenantBoundary>? tenantBoundaryList});
}

/// @nodoc
class _$LocationCopyWithImpl<$Res, $Val extends Location>
    implements $LocationCopyWith<$Res> {
  _$LocationCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantBoundaryList = freezed,
  }) {
    return _then(_value.copyWith(
      tenantBoundaryList: freezed == tenantBoundaryList
          ? _value.tenantBoundaryList
          : tenantBoundaryList // ignore: cast_nullable_to_non_nullable
              as List<TenantBoundary>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_LocationCopyWith<$Res> implements $LocationCopyWith<$Res> {
  factory _$$_LocationCopyWith(
          _$_Location value, $Res Function(_$_Location) then) =
      __$$_LocationCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'TenantBoundary')
      List<TenantBoundary>? tenantBoundaryList});
}

/// @nodoc
class __$$_LocationCopyWithImpl<$Res>
    extends _$LocationCopyWithImpl<$Res, _$_Location>
    implements _$$_LocationCopyWith<$Res> {
  __$$_LocationCopyWithImpl(
      _$_Location _value, $Res Function(_$_Location) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantBoundaryList = freezed,
  }) {
    return _then(_$_Location(
      tenantBoundaryList: freezed == tenantBoundaryList
          ? _value._tenantBoundaryList
          : tenantBoundaryList // ignore: cast_nullable_to_non_nullable
              as List<TenantBoundary>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_Location implements _Location {
  const _$_Location(
      {@JsonKey(name: 'TenantBoundary')
      final List<TenantBoundary>? tenantBoundaryList})
      : _tenantBoundaryList = tenantBoundaryList;

  factory _$_Location.fromJson(Map<String, dynamic> json) =>
      _$$_LocationFromJson(json);

  final List<TenantBoundary>? _tenantBoundaryList;
  @override
  @JsonKey(name: 'TenantBoundary')
  List<TenantBoundary>? get tenantBoundaryList {
    final value = _tenantBoundaryList;
    if (value == null) return null;
    if (_tenantBoundaryList is EqualUnmodifiableListView)
      return _tenantBoundaryList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'Location(tenantBoundaryList: $tenantBoundaryList)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Location &&
            const DeepCollectionEquality()
                .equals(other._tenantBoundaryList, _tenantBoundaryList));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_tenantBoundaryList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LocationCopyWith<_$_Location> get copyWith =>
      __$$_LocationCopyWithImpl<_$_Location>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_LocationToJson(
      this,
    );
  }
}

abstract class _Location implements Location {
  const factory _Location(
      {@JsonKey(name: 'TenantBoundary')
      final List<TenantBoundary>? tenantBoundaryList}) = _$_Location;

  factory _Location.fromJson(Map<String, dynamic> json) = _$_Location.fromJson;

  @override
  @JsonKey(name: 'TenantBoundary')
  List<TenantBoundary>? get tenantBoundaryList;
  @override
  @JsonKey(ignore: true)
  _$$_LocationCopyWith<_$_Location> get copyWith =>
      throw _privateConstructorUsedError;
}

TenantBoundary _$TenantBoundaryFromJson(Map<String, dynamic> json) {
  return _TenantBoundary.fromJson(json);
}

/// @nodoc
mixin _$TenantBoundary {
  @JsonKey(name: 'boundary')
  List<WardBoundary>? get boundaryList => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $TenantBoundaryCopyWith<TenantBoundary> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TenantBoundaryCopyWith<$Res> {
  factory $TenantBoundaryCopyWith(
          TenantBoundary value, $Res Function(TenantBoundary) then) =
      _$TenantBoundaryCopyWithImpl<$Res, TenantBoundary>;
  @useResult
  $Res call({@JsonKey(name: 'boundary') List<WardBoundary>? boundaryList});
}

/// @nodoc
class _$TenantBoundaryCopyWithImpl<$Res, $Val extends TenantBoundary>
    implements $TenantBoundaryCopyWith<$Res> {
  _$TenantBoundaryCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? boundaryList = freezed,
  }) {
    return _then(_value.copyWith(
      boundaryList: freezed == boundaryList
          ? _value.boundaryList
          : boundaryList // ignore: cast_nullable_to_non_nullable
              as List<WardBoundary>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_TenantBoundaryCopyWith<$Res>
    implements $TenantBoundaryCopyWith<$Res> {
  factory _$$_TenantBoundaryCopyWith(
          _$_TenantBoundary value, $Res Function(_$_TenantBoundary) then) =
      __$$_TenantBoundaryCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'boundary') List<WardBoundary>? boundaryList});
}

/// @nodoc
class __$$_TenantBoundaryCopyWithImpl<$Res>
    extends _$TenantBoundaryCopyWithImpl<$Res, _$_TenantBoundary>
    implements _$$_TenantBoundaryCopyWith<$Res> {
  __$$_TenantBoundaryCopyWithImpl(
      _$_TenantBoundary _value, $Res Function(_$_TenantBoundary) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? boundaryList = freezed,
  }) {
    return _then(_$_TenantBoundary(
      boundaryList: freezed == boundaryList
          ? _value._boundaryList
          : boundaryList // ignore: cast_nullable_to_non_nullable
              as List<WardBoundary>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_TenantBoundary implements _TenantBoundary {
  const _$_TenantBoundary(
      {@JsonKey(name: 'boundary') final List<WardBoundary>? boundaryList})
      : _boundaryList = boundaryList;

  factory _$_TenantBoundary.fromJson(Map<String, dynamic> json) =>
      _$$_TenantBoundaryFromJson(json);

  final List<WardBoundary>? _boundaryList;
  @override
  @JsonKey(name: 'boundary')
  List<WardBoundary>? get boundaryList {
    final value = _boundaryList;
    if (value == null) return null;
    if (_boundaryList is EqualUnmodifiableListView) return _boundaryList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'TenantBoundary(boundaryList: $boundaryList)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_TenantBoundary &&
            const DeepCollectionEquality()
                .equals(other._boundaryList, _boundaryList));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_boundaryList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_TenantBoundaryCopyWith<_$_TenantBoundary> get copyWith =>
      __$$_TenantBoundaryCopyWithImpl<_$_TenantBoundary>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_TenantBoundaryToJson(
      this,
    );
  }
}

abstract class _TenantBoundary implements TenantBoundary {
  const factory _TenantBoundary(
          {@JsonKey(name: 'boundary') final List<WardBoundary>? boundaryList}) =
      _$_TenantBoundary;

  factory _TenantBoundary.fromJson(Map<String, dynamic> json) =
      _$_TenantBoundary.fromJson;

  @override
  @JsonKey(name: 'boundary')
  List<WardBoundary>? get boundaryList;
  @override
  @JsonKey(ignore: true)
  _$$_TenantBoundaryCopyWith<_$_TenantBoundary> get copyWith =>
      throw _privateConstructorUsedError;
}

WardBoundary _$WardBoundaryFromJson(Map<String, dynamic> json) {
  return _WardBoundary.fromJson(json);
}

/// @nodoc
mixin _$WardBoundary {
  @JsonKey(name: 'code')
  String? get code => throw _privateConstructorUsedError;
  @JsonKey(name: 'name')
  String? get name => throw _privateConstructorUsedError;
  @JsonKey(name: 'children')
  List<LocalityChild>? get localityChildren =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WardBoundaryCopyWith<WardBoundary> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WardBoundaryCopyWith<$Res> {
  factory $WardBoundaryCopyWith(
          WardBoundary value, $Res Function(WardBoundary) then) =
      _$WardBoundaryCopyWithImpl<$Res, WardBoundary>;
  @useResult
  $Res call(
      {@JsonKey(name: 'code') String? code,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'children') List<LocalityChild>? localityChildren});
}

/// @nodoc
class _$WardBoundaryCopyWithImpl<$Res, $Val extends WardBoundary>
    implements $WardBoundaryCopyWith<$Res> {
  _$WardBoundaryCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? name = freezed,
    Object? localityChildren = freezed,
  }) {
    return _then(_value.copyWith(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      localityChildren: freezed == localityChildren
          ? _value.localityChildren
          : localityChildren // ignore: cast_nullable_to_non_nullable
              as List<LocalityChild>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_WardBoundaryCopyWith<$Res>
    implements $WardBoundaryCopyWith<$Res> {
  factory _$$_WardBoundaryCopyWith(
          _$_WardBoundary value, $Res Function(_$_WardBoundary) then) =
      __$$_WardBoundaryCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'code') String? code,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'children') List<LocalityChild>? localityChildren});
}

/// @nodoc
class __$$_WardBoundaryCopyWithImpl<$Res>
    extends _$WardBoundaryCopyWithImpl<$Res, _$_WardBoundary>
    implements _$$_WardBoundaryCopyWith<$Res> {
  __$$_WardBoundaryCopyWithImpl(
      _$_WardBoundary _value, $Res Function(_$_WardBoundary) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? name = freezed,
    Object? localityChildren = freezed,
  }) {
    return _then(_$_WardBoundary(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      localityChildren: freezed == localityChildren
          ? _value._localityChildren
          : localityChildren // ignore: cast_nullable_to_non_nullable
              as List<LocalityChild>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_WardBoundary implements _WardBoundary {
  const _$_WardBoundary(
      {@JsonKey(name: 'code') this.code,
      @JsonKey(name: 'name') this.name,
      @JsonKey(name: 'children') final List<LocalityChild>? localityChildren})
      : _localityChildren = localityChildren;

  factory _$_WardBoundary.fromJson(Map<String, dynamic> json) =>
      _$$_WardBoundaryFromJson(json);

  @override
  @JsonKey(name: 'code')
  final String? code;
  @override
  @JsonKey(name: 'name')
  final String? name;
  final List<LocalityChild>? _localityChildren;
  @override
  @JsonKey(name: 'children')
  List<LocalityChild>? get localityChildren {
    final value = _localityChildren;
    if (value == null) return null;
    if (_localityChildren is EqualUnmodifiableListView)
      return _localityChildren;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'WardBoundary(code: $code, name: $name, localityChildren: $localityChildren)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_WardBoundary &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.name, name) || other.name == name) &&
            const DeepCollectionEquality()
                .equals(other._localityChildren, _localityChildren));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code, name,
      const DeepCollectionEquality().hash(_localityChildren));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_WardBoundaryCopyWith<_$_WardBoundary> get copyWith =>
      __$$_WardBoundaryCopyWithImpl<_$_WardBoundary>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_WardBoundaryToJson(
      this,
    );
  }
}

abstract class _WardBoundary implements WardBoundary {
  const factory _WardBoundary(
      {@JsonKey(name: 'code') final String? code,
      @JsonKey(name: 'name') final String? name,
      @JsonKey(name: 'children')
      final List<LocalityChild>? localityChildren}) = _$_WardBoundary;

  factory _WardBoundary.fromJson(Map<String, dynamic> json) =
      _$_WardBoundary.fromJson;

  @override
  @JsonKey(name: 'code')
  String? get code;
  @override
  @JsonKey(name: 'name')
  String? get name;
  @override
  @JsonKey(name: 'children')
  List<LocalityChild>? get localityChildren;
  @override
  @JsonKey(ignore: true)
  _$$_WardBoundaryCopyWith<_$_WardBoundary> get copyWith =>
      throw _privateConstructorUsedError;
}

LocalityChild _$LocalityChildFromJson(Map<String, dynamic> json) {
  return _LocalityChild.fromJson(json);
}

/// @nodoc
mixin _$LocalityChild {
  @JsonKey(name: 'code')
  String? get code => throw _privateConstructorUsedError;
  @JsonKey(name: 'name')
  String? get name => throw _privateConstructorUsedError;
  @JsonKey(name: 'latitude')
  String? get latitude => throw _privateConstructorUsedError;
  @JsonKey(name: 'longitude')
  String? get longitude => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $LocalityChildCopyWith<LocalityChild> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LocalityChildCopyWith<$Res> {
  factory $LocalityChildCopyWith(
          LocalityChild value, $Res Function(LocalityChild) then) =
      _$LocalityChildCopyWithImpl<$Res, LocalityChild>;
  @useResult
  $Res call(
      {@JsonKey(name: 'code') String? code,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'latitude') String? latitude,
      @JsonKey(name: 'longitude') String? longitude});
}

/// @nodoc
class _$LocalityChildCopyWithImpl<$Res, $Val extends LocalityChild>
    implements $LocalityChildCopyWith<$Res> {
  _$LocalityChildCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? name = freezed,
    Object? latitude = freezed,
    Object? longitude = freezed,
  }) {
    return _then(_value.copyWith(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      latitude: freezed == latitude
          ? _value.latitude
          : latitude // ignore: cast_nullable_to_non_nullable
              as String?,
      longitude: freezed == longitude
          ? _value.longitude
          : longitude // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_LocalityChildCopyWith<$Res>
    implements $LocalityChildCopyWith<$Res> {
  factory _$$_LocalityChildCopyWith(
          _$_LocalityChild value, $Res Function(_$_LocalityChild) then) =
      __$$_LocalityChildCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'code') String? code,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'latitude') String? latitude,
      @JsonKey(name: 'longitude') String? longitude});
}

/// @nodoc
class __$$_LocalityChildCopyWithImpl<$Res>
    extends _$LocalityChildCopyWithImpl<$Res, _$_LocalityChild>
    implements _$$_LocalityChildCopyWith<$Res> {
  __$$_LocalityChildCopyWithImpl(
      _$_LocalityChild _value, $Res Function(_$_LocalityChild) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? name = freezed,
    Object? latitude = freezed,
    Object? longitude = freezed,
  }) {
    return _then(_$_LocalityChild(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      latitude: freezed == latitude
          ? _value.latitude
          : latitude // ignore: cast_nullable_to_non_nullable
              as String?,
      longitude: freezed == longitude
          ? _value.longitude
          : longitude // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_LocalityChild implements _LocalityChild {
  const _$_LocalityChild(
      {@JsonKey(name: 'code') this.code,
      @JsonKey(name: 'name') this.name,
      @JsonKey(name: 'latitude') this.latitude,
      @JsonKey(name: 'longitude') this.longitude});

  factory _$_LocalityChild.fromJson(Map<String, dynamic> json) =>
      _$$_LocalityChildFromJson(json);

  @override
  @JsonKey(name: 'code')
  final String? code;
  @override
  @JsonKey(name: 'name')
  final String? name;
  @override
  @JsonKey(name: 'latitude')
  final String? latitude;
  @override
  @JsonKey(name: 'longitude')
  final String? longitude;

  @override
  String toString() {
    return 'LocalityChild(code: $code, name: $name, latitude: $latitude, longitude: $longitude)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_LocalityChild &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.latitude, latitude) ||
                other.latitude == latitude) &&
            (identical(other.longitude, longitude) ||
                other.longitude == longitude));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code, name, latitude, longitude);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LocalityChildCopyWith<_$_LocalityChild> get copyWith =>
      __$$_LocalityChildCopyWithImpl<_$_LocalityChild>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_LocalityChildToJson(
      this,
    );
  }
}

abstract class _LocalityChild implements LocalityChild {
  const factory _LocalityChild(
      {@JsonKey(name: 'code') final String? code,
      @JsonKey(name: 'name') final String? name,
      @JsonKey(name: 'latitude') final String? latitude,
      @JsonKey(name: 'longitude') final String? longitude}) = _$_LocalityChild;

  factory _LocalityChild.fromJson(Map<String, dynamic> json) =
      _$_LocalityChild.fromJson;

  @override
  @JsonKey(name: 'code')
  String? get code;
  @override
  @JsonKey(name: 'name')
  String? get name;
  @override
  @JsonKey(name: 'latitude')
  String? get latitude;
  @override
  @JsonKey(name: 'longitude')
  String? get longitude;
  @override
  @JsonKey(ignore: true)
  _$$_LocalityChildCopyWith<_$_LocalityChild> get copyWith =>
      throw _privateConstructorUsedError;
}
