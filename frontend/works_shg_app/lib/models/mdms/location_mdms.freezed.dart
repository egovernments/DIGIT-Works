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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

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
abstract class _$$LocationImplCopyWith<$Res>
    implements $LocationCopyWith<$Res> {
  factory _$$LocationImplCopyWith(
          _$LocationImpl value, $Res Function(_$LocationImpl) then) =
      __$$LocationImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'TenantBoundary')
          List<TenantBoundary>? tenantBoundaryList});
}

/// @nodoc
class __$$LocationImplCopyWithImpl<$Res>
    extends _$LocationCopyWithImpl<$Res, _$LocationImpl>
    implements _$$LocationImplCopyWith<$Res> {
  __$$LocationImplCopyWithImpl(
      _$LocationImpl _value, $Res Function(_$LocationImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantBoundaryList = freezed,
  }) {
    return _then(_$LocationImpl(
      tenantBoundaryList: freezed == tenantBoundaryList
          ? _value._tenantBoundaryList
          : tenantBoundaryList // ignore: cast_nullable_to_non_nullable
              as List<TenantBoundary>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$LocationImpl implements _Location {
  const _$LocationImpl(
      {@JsonKey(name: 'TenantBoundary')
          final List<TenantBoundary>? tenantBoundaryList})
      : _tenantBoundaryList = tenantBoundaryList;

  factory _$LocationImpl.fromJson(Map<String, dynamic> json) =>
      _$$LocationImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LocationImpl &&
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
  _$$LocationImplCopyWith<_$LocationImpl> get copyWith =>
      __$$LocationImplCopyWithImpl<_$LocationImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$LocationImplToJson(
      this,
    );
  }
}

abstract class _Location implements Location {
  const factory _Location(
      {@JsonKey(name: 'TenantBoundary')
          final List<TenantBoundary>? tenantBoundaryList}) = _$LocationImpl;

  factory _Location.fromJson(Map<String, dynamic> json) =
      _$LocationImpl.fromJson;

  @override
  @JsonKey(name: 'TenantBoundary')
  List<TenantBoundary>? get tenantBoundaryList;
  @override
  @JsonKey(ignore: true)
  _$$LocationImplCopyWith<_$LocationImpl> get copyWith =>
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
abstract class _$$TenantBoundaryImplCopyWith<$Res>
    implements $TenantBoundaryCopyWith<$Res> {
  factory _$$TenantBoundaryImplCopyWith(_$TenantBoundaryImpl value,
          $Res Function(_$TenantBoundaryImpl) then) =
      __$$TenantBoundaryImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'boundary') List<WardBoundary>? boundaryList});
}

/// @nodoc
class __$$TenantBoundaryImplCopyWithImpl<$Res>
    extends _$TenantBoundaryCopyWithImpl<$Res, _$TenantBoundaryImpl>
    implements _$$TenantBoundaryImplCopyWith<$Res> {
  __$$TenantBoundaryImplCopyWithImpl(
      _$TenantBoundaryImpl _value, $Res Function(_$TenantBoundaryImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? boundaryList = freezed,
  }) {
    return _then(_$TenantBoundaryImpl(
      boundaryList: freezed == boundaryList
          ? _value._boundaryList
          : boundaryList // ignore: cast_nullable_to_non_nullable
              as List<WardBoundary>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$TenantBoundaryImpl implements _TenantBoundary {
  const _$TenantBoundaryImpl(
      {@JsonKey(name: 'boundary') final List<WardBoundary>? boundaryList})
      : _boundaryList = boundaryList;

  factory _$TenantBoundaryImpl.fromJson(Map<String, dynamic> json) =>
      _$$TenantBoundaryImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TenantBoundaryImpl &&
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
  _$$TenantBoundaryImplCopyWith<_$TenantBoundaryImpl> get copyWith =>
      __$$TenantBoundaryImplCopyWithImpl<_$TenantBoundaryImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$TenantBoundaryImplToJson(
      this,
    );
  }
}

abstract class _TenantBoundary implements TenantBoundary {
  const factory _TenantBoundary(
          {@JsonKey(name: 'boundary') final List<WardBoundary>? boundaryList}) =
      _$TenantBoundaryImpl;

  factory _TenantBoundary.fromJson(Map<String, dynamic> json) =
      _$TenantBoundaryImpl.fromJson;

  @override
  @JsonKey(name: 'boundary')
  List<WardBoundary>? get boundaryList;
  @override
  @JsonKey(ignore: true)
  _$$TenantBoundaryImplCopyWith<_$TenantBoundaryImpl> get copyWith =>
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
abstract class _$$WardBoundaryImplCopyWith<$Res>
    implements $WardBoundaryCopyWith<$Res> {
  factory _$$WardBoundaryImplCopyWith(
          _$WardBoundaryImpl value, $Res Function(_$WardBoundaryImpl) then) =
      __$$WardBoundaryImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'code') String? code,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'children') List<LocalityChild>? localityChildren});
}

/// @nodoc
class __$$WardBoundaryImplCopyWithImpl<$Res>
    extends _$WardBoundaryCopyWithImpl<$Res, _$WardBoundaryImpl>
    implements _$$WardBoundaryImplCopyWith<$Res> {
  __$$WardBoundaryImplCopyWithImpl(
      _$WardBoundaryImpl _value, $Res Function(_$WardBoundaryImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? name = freezed,
    Object? localityChildren = freezed,
  }) {
    return _then(_$WardBoundaryImpl(
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
class _$WardBoundaryImpl implements _WardBoundary {
  const _$WardBoundaryImpl(
      {@JsonKey(name: 'code') this.code,
      @JsonKey(name: 'name') this.name,
      @JsonKey(name: 'children') final List<LocalityChild>? localityChildren})
      : _localityChildren = localityChildren;

  factory _$WardBoundaryImpl.fromJson(Map<String, dynamic> json) =>
      _$$WardBoundaryImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WardBoundaryImpl &&
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
  _$$WardBoundaryImplCopyWith<_$WardBoundaryImpl> get copyWith =>
      __$$WardBoundaryImplCopyWithImpl<_$WardBoundaryImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WardBoundaryImplToJson(
      this,
    );
  }
}

abstract class _WardBoundary implements WardBoundary {
  const factory _WardBoundary(
      {@JsonKey(name: 'code')
          final String? code,
      @JsonKey(name: 'name')
          final String? name,
      @JsonKey(name: 'children')
          final List<LocalityChild>? localityChildren}) = _$WardBoundaryImpl;

  factory _WardBoundary.fromJson(Map<String, dynamic> json) =
      _$WardBoundaryImpl.fromJson;

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
  _$$WardBoundaryImplCopyWith<_$WardBoundaryImpl> get copyWith =>
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
abstract class _$$LocalityChildImplCopyWith<$Res>
    implements $LocalityChildCopyWith<$Res> {
  factory _$$LocalityChildImplCopyWith(
          _$LocalityChildImpl value, $Res Function(_$LocalityChildImpl) then) =
      __$$LocalityChildImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'code') String? code,
      @JsonKey(name: 'name') String? name,
      @JsonKey(name: 'latitude') String? latitude,
      @JsonKey(name: 'longitude') String? longitude});
}

/// @nodoc
class __$$LocalityChildImplCopyWithImpl<$Res>
    extends _$LocalityChildCopyWithImpl<$Res, _$LocalityChildImpl>
    implements _$$LocalityChildImplCopyWith<$Res> {
  __$$LocalityChildImplCopyWithImpl(
      _$LocalityChildImpl _value, $Res Function(_$LocalityChildImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? name = freezed,
    Object? latitude = freezed,
    Object? longitude = freezed,
  }) {
    return _then(_$LocalityChildImpl(
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
class _$LocalityChildImpl implements _LocalityChild {
  const _$LocalityChildImpl(
      {@JsonKey(name: 'code') this.code,
      @JsonKey(name: 'name') this.name,
      @JsonKey(name: 'latitude') this.latitude,
      @JsonKey(name: 'longitude') this.longitude});

  factory _$LocalityChildImpl.fromJson(Map<String, dynamic> json) =>
      _$$LocalityChildImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LocalityChildImpl &&
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
  _$$LocalityChildImplCopyWith<_$LocalityChildImpl> get copyWith =>
      __$$LocalityChildImplCopyWithImpl<_$LocalityChildImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$LocalityChildImplToJson(
      this,
    );
  }
}

abstract class _LocalityChild implements LocalityChild {
  const factory _LocalityChild(
          {@JsonKey(name: 'code') final String? code,
          @JsonKey(name: 'name') final String? name,
          @JsonKey(name: 'latitude') final String? latitude,
          @JsonKey(name: 'longitude') final String? longitude}) =
      _$LocalityChildImpl;

  factory _LocalityChild.fromJson(Map<String, dynamic> json) =
      _$LocalityChildImpl.fromJson;

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
  _$$LocalityChildImplCopyWith<_$LocalityChildImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
