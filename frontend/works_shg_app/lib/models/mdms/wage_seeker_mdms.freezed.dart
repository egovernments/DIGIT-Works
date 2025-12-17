// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'wage_seeker_mdms.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

WageSeekerMDMS _$WageSeekerMDMSFromJson(Map<String, dynamic> json) {
  return _WageSeekerMDMS.fromJson(json);
}

/// @nodoc
mixin _$WageSeekerMDMS {
  @JsonKey(name: 'common-masters')
  WageSeekerCommonMDMS? get commonMDMS => throw _privateConstructorUsedError;
  @JsonKey(name: 'works')
  WageSeekerWorksMDMS? get worksMDMS => throw _privateConstructorUsedError;
  @JsonKey(name: 'tenant')
  TenantMDMS? get tenantMDMS => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WageSeekerMDMSCopyWith<WageSeekerMDMS> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerMDMSCopyWith<$Res> {
  factory $WageSeekerMDMSCopyWith(
          WageSeekerMDMS value, $Res Function(WageSeekerMDMS) then) =
      _$WageSeekerMDMSCopyWithImpl<$Res, WageSeekerMDMS>;
  @useResult
  $Res call(
      {@JsonKey(name: 'common-masters') WageSeekerCommonMDMS? commonMDMS,
      @JsonKey(name: 'works') WageSeekerWorksMDMS? worksMDMS,
      @JsonKey(name: 'tenant') TenantMDMS? tenantMDMS});

  $WageSeekerCommonMDMSCopyWith<$Res>? get commonMDMS;
  $WageSeekerWorksMDMSCopyWith<$Res>? get worksMDMS;
  $TenantMDMSCopyWith<$Res>? get tenantMDMS;
}

/// @nodoc
class _$WageSeekerMDMSCopyWithImpl<$Res, $Val extends WageSeekerMDMS>
    implements $WageSeekerMDMSCopyWith<$Res> {
  _$WageSeekerMDMSCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonMDMS = freezed,
    Object? worksMDMS = freezed,
    Object? tenantMDMS = freezed,
  }) {
    return _then(_value.copyWith(
      commonMDMS: freezed == commonMDMS
          ? _value.commonMDMS
          : commonMDMS // ignore: cast_nullable_to_non_nullable
              as WageSeekerCommonMDMS?,
      worksMDMS: freezed == worksMDMS
          ? _value.worksMDMS
          : worksMDMS // ignore: cast_nullable_to_non_nullable
              as WageSeekerWorksMDMS?,
      tenantMDMS: freezed == tenantMDMS
          ? _value.tenantMDMS
          : tenantMDMS // ignore: cast_nullable_to_non_nullable
              as TenantMDMS?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $WageSeekerCommonMDMSCopyWith<$Res>? get commonMDMS {
    if (_value.commonMDMS == null) {
      return null;
    }

    return $WageSeekerCommonMDMSCopyWith<$Res>(_value.commonMDMS!, (value) {
      return _then(_value.copyWith(commonMDMS: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $WageSeekerWorksMDMSCopyWith<$Res>? get worksMDMS {
    if (_value.worksMDMS == null) {
      return null;
    }

    return $WageSeekerWorksMDMSCopyWith<$Res>(_value.worksMDMS!, (value) {
      return _then(_value.copyWith(worksMDMS: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $TenantMDMSCopyWith<$Res>? get tenantMDMS {
    if (_value.tenantMDMS == null) {
      return null;
    }

    return $TenantMDMSCopyWith<$Res>(_value.tenantMDMS!, (value) {
      return _then(_value.copyWith(tenantMDMS: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$WageSeekerMDMSImplCopyWith<$Res>
    implements $WageSeekerMDMSCopyWith<$Res> {
  factory _$$WageSeekerMDMSImplCopyWith(_$WageSeekerMDMSImpl value,
          $Res Function(_$WageSeekerMDMSImpl) then) =
      __$$WageSeekerMDMSImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'common-masters') WageSeekerCommonMDMS? commonMDMS,
      @JsonKey(name: 'works') WageSeekerWorksMDMS? worksMDMS,
      @JsonKey(name: 'tenant') TenantMDMS? tenantMDMS});

  @override
  $WageSeekerCommonMDMSCopyWith<$Res>? get commonMDMS;
  @override
  $WageSeekerWorksMDMSCopyWith<$Res>? get worksMDMS;
  @override
  $TenantMDMSCopyWith<$Res>? get tenantMDMS;
}

/// @nodoc
class __$$WageSeekerMDMSImplCopyWithImpl<$Res>
    extends _$WageSeekerMDMSCopyWithImpl<$Res, _$WageSeekerMDMSImpl>
    implements _$$WageSeekerMDMSImplCopyWith<$Res> {
  __$$WageSeekerMDMSImplCopyWithImpl(
      _$WageSeekerMDMSImpl _value, $Res Function(_$WageSeekerMDMSImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonMDMS = freezed,
    Object? worksMDMS = freezed,
    Object? tenantMDMS = freezed,
  }) {
    return _then(_$WageSeekerMDMSImpl(
      commonMDMS: freezed == commonMDMS
          ? _value.commonMDMS
          : commonMDMS // ignore: cast_nullable_to_non_nullable
              as WageSeekerCommonMDMS?,
      worksMDMS: freezed == worksMDMS
          ? _value.worksMDMS
          : worksMDMS // ignore: cast_nullable_to_non_nullable
              as WageSeekerWorksMDMS?,
      tenantMDMS: freezed == tenantMDMS
          ? _value.tenantMDMS
          : tenantMDMS // ignore: cast_nullable_to_non_nullable
              as TenantMDMS?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WageSeekerMDMSImpl implements _WageSeekerMDMS {
  const _$WageSeekerMDMSImpl(
      {@JsonKey(name: 'common-masters') this.commonMDMS,
      @JsonKey(name: 'works') this.worksMDMS,
      @JsonKey(name: 'tenant') this.tenantMDMS});

  factory _$WageSeekerMDMSImpl.fromJson(Map<String, dynamic> json) =>
      _$$WageSeekerMDMSImplFromJson(json);

  @override
  @JsonKey(name: 'common-masters')
  final WageSeekerCommonMDMS? commonMDMS;
  @override
  @JsonKey(name: 'works')
  final WageSeekerWorksMDMS? worksMDMS;
  @override
  @JsonKey(name: 'tenant')
  final TenantMDMS? tenantMDMS;

  @override
  String toString() {
    return 'WageSeekerMDMS(commonMDMS: $commonMDMS, worksMDMS: $worksMDMS, tenantMDMS: $tenantMDMS)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerMDMSImpl &&
            (identical(other.commonMDMS, commonMDMS) ||
                other.commonMDMS == commonMDMS) &&
            (identical(other.worksMDMS, worksMDMS) ||
                other.worksMDMS == worksMDMS) &&
            (identical(other.tenantMDMS, tenantMDMS) ||
                other.tenantMDMS == tenantMDMS));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, commonMDMS, worksMDMS, tenantMDMS);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerMDMSImplCopyWith<_$WageSeekerMDMSImpl> get copyWith =>
      __$$WageSeekerMDMSImplCopyWithImpl<_$WageSeekerMDMSImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WageSeekerMDMSImplToJson(
      this,
    );
  }
}

abstract class _WageSeekerMDMS implements WageSeekerMDMS {
  const factory _WageSeekerMDMS(
      {@JsonKey(name: 'common-masters')
          final WageSeekerCommonMDMS? commonMDMS,
      @JsonKey(name: 'works')
          final WageSeekerWorksMDMS? worksMDMS,
      @JsonKey(name: 'tenant')
          final TenantMDMS? tenantMDMS}) = _$WageSeekerMDMSImpl;

  factory _WageSeekerMDMS.fromJson(Map<String, dynamic> json) =
      _$WageSeekerMDMSImpl.fromJson;

  @override
  @JsonKey(name: 'common-masters')
  WageSeekerCommonMDMS? get commonMDMS;
  @override
  @JsonKey(name: 'works')
  WageSeekerWorksMDMS? get worksMDMS;
  @override
  @JsonKey(name: 'tenant')
  TenantMDMS? get tenantMDMS;
  @override
  @JsonKey(ignore: true)
  _$$WageSeekerMDMSImplCopyWith<_$WageSeekerMDMSImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WageSeekerWorksMDMS _$WageSeekerWorksMDMSFromJson(Map<String, dynamic> json) {
  return _WageSeekerWorksMDMS.fromJson(json);
}

/// @nodoc
mixin _$WageSeekerWorksMDMS {
  @JsonKey(name: 'BankAccType')
  List<BankAccType>? get bankAccType => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WageSeekerWorksMDMSCopyWith<WageSeekerWorksMDMS> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerWorksMDMSCopyWith<$Res> {
  factory $WageSeekerWorksMDMSCopyWith(
          WageSeekerWorksMDMS value, $Res Function(WageSeekerWorksMDMS) then) =
      _$WageSeekerWorksMDMSCopyWithImpl<$Res, WageSeekerWorksMDMS>;
  @useResult
  $Res call({@JsonKey(name: 'BankAccType') List<BankAccType>? bankAccType});
}

/// @nodoc
class _$WageSeekerWorksMDMSCopyWithImpl<$Res, $Val extends WageSeekerWorksMDMS>
    implements $WageSeekerWorksMDMSCopyWith<$Res> {
  _$WageSeekerWorksMDMSCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? bankAccType = freezed,
  }) {
    return _then(_value.copyWith(
      bankAccType: freezed == bankAccType
          ? _value.bankAccType
          : bankAccType // ignore: cast_nullable_to_non_nullable
              as List<BankAccType>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WageSeekerWorksMDMSImplCopyWith<$Res>
    implements $WageSeekerWorksMDMSCopyWith<$Res> {
  factory _$$WageSeekerWorksMDMSImplCopyWith(_$WageSeekerWorksMDMSImpl value,
          $Res Function(_$WageSeekerWorksMDMSImpl) then) =
      __$$WageSeekerWorksMDMSImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'BankAccType') List<BankAccType>? bankAccType});
}

/// @nodoc
class __$$WageSeekerWorksMDMSImplCopyWithImpl<$Res>
    extends _$WageSeekerWorksMDMSCopyWithImpl<$Res, _$WageSeekerWorksMDMSImpl>
    implements _$$WageSeekerWorksMDMSImplCopyWith<$Res> {
  __$$WageSeekerWorksMDMSImplCopyWithImpl(_$WageSeekerWorksMDMSImpl _value,
      $Res Function(_$WageSeekerWorksMDMSImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? bankAccType = freezed,
  }) {
    return _then(_$WageSeekerWorksMDMSImpl(
      bankAccType: freezed == bankAccType
          ? _value._bankAccType
          : bankAccType // ignore: cast_nullable_to_non_nullable
              as List<BankAccType>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WageSeekerWorksMDMSImpl implements _WageSeekerWorksMDMS {
  const _$WageSeekerWorksMDMSImpl(
      {@JsonKey(name: 'BankAccType') final List<BankAccType>? bankAccType})
      : _bankAccType = bankAccType;

  factory _$WageSeekerWorksMDMSImpl.fromJson(Map<String, dynamic> json) =>
      _$$WageSeekerWorksMDMSImplFromJson(json);

  final List<BankAccType>? _bankAccType;
  @override
  @JsonKey(name: 'BankAccType')
  List<BankAccType>? get bankAccType {
    final value = _bankAccType;
    if (value == null) return null;
    if (_bankAccType is EqualUnmodifiableListView) return _bankAccType;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'WageSeekerWorksMDMS(bankAccType: $bankAccType)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerWorksMDMSImpl &&
            const DeepCollectionEquality()
                .equals(other._bankAccType, _bankAccType));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_bankAccType));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerWorksMDMSImplCopyWith<_$WageSeekerWorksMDMSImpl> get copyWith =>
      __$$WageSeekerWorksMDMSImplCopyWithImpl<_$WageSeekerWorksMDMSImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WageSeekerWorksMDMSImplToJson(
      this,
    );
  }
}

abstract class _WageSeekerWorksMDMS implements WageSeekerWorksMDMS {
  const factory _WageSeekerWorksMDMS(
      {@JsonKey(name: 'BankAccType')
          final List<BankAccType>? bankAccType}) = _$WageSeekerWorksMDMSImpl;

  factory _WageSeekerWorksMDMS.fromJson(Map<String, dynamic> json) =
      _$WageSeekerWorksMDMSImpl.fromJson;

  @override
  @JsonKey(name: 'BankAccType')
  List<BankAccType>? get bankAccType;
  @override
  @JsonKey(ignore: true)
  _$$WageSeekerWorksMDMSImplCopyWith<_$WageSeekerWorksMDMSImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

TenantMDMS _$TenantMDMSFromJson(Map<String, dynamic> json) {
  return _TenantMDMS.fromJson(json);
}

/// @nodoc
mixin _$TenantMDMS {
  @JsonKey(name: 'citymodule')
  List<CityModule>? get cityModule => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $TenantMDMSCopyWith<TenantMDMS> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TenantMDMSCopyWith<$Res> {
  factory $TenantMDMSCopyWith(
          TenantMDMS value, $Res Function(TenantMDMS) then) =
      _$TenantMDMSCopyWithImpl<$Res, TenantMDMS>;
  @useResult
  $Res call({@JsonKey(name: 'citymodule') List<CityModule>? cityModule});
}

/// @nodoc
class _$TenantMDMSCopyWithImpl<$Res, $Val extends TenantMDMS>
    implements $TenantMDMSCopyWith<$Res> {
  _$TenantMDMSCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cityModule = freezed,
  }) {
    return _then(_value.copyWith(
      cityModule: freezed == cityModule
          ? _value.cityModule
          : cityModule // ignore: cast_nullable_to_non_nullable
              as List<CityModule>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$TenantMDMSImplCopyWith<$Res>
    implements $TenantMDMSCopyWith<$Res> {
  factory _$$TenantMDMSImplCopyWith(
          _$TenantMDMSImpl value, $Res Function(_$TenantMDMSImpl) then) =
      __$$TenantMDMSImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'citymodule') List<CityModule>? cityModule});
}

/// @nodoc
class __$$TenantMDMSImplCopyWithImpl<$Res>
    extends _$TenantMDMSCopyWithImpl<$Res, _$TenantMDMSImpl>
    implements _$$TenantMDMSImplCopyWith<$Res> {
  __$$TenantMDMSImplCopyWithImpl(
      _$TenantMDMSImpl _value, $Res Function(_$TenantMDMSImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cityModule = freezed,
  }) {
    return _then(_$TenantMDMSImpl(
      cityModule: freezed == cityModule
          ? _value._cityModule
          : cityModule // ignore: cast_nullable_to_non_nullable
              as List<CityModule>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$TenantMDMSImpl implements _TenantMDMS {
  const _$TenantMDMSImpl(
      {@JsonKey(name: 'citymodule') final List<CityModule>? cityModule})
      : _cityModule = cityModule;

  factory _$TenantMDMSImpl.fromJson(Map<String, dynamic> json) =>
      _$$TenantMDMSImplFromJson(json);

  final List<CityModule>? _cityModule;
  @override
  @JsonKey(name: 'citymodule')
  List<CityModule>? get cityModule {
    final value = _cityModule;
    if (value == null) return null;
    if (_cityModule is EqualUnmodifiableListView) return _cityModule;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'TenantMDMS(cityModule: $cityModule)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TenantMDMSImpl &&
            const DeepCollectionEquality()
                .equals(other._cityModule, _cityModule));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_cityModule));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$TenantMDMSImplCopyWith<_$TenantMDMSImpl> get copyWith =>
      __$$TenantMDMSImplCopyWithImpl<_$TenantMDMSImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$TenantMDMSImplToJson(
      this,
    );
  }
}

abstract class _TenantMDMS implements TenantMDMS {
  const factory _TenantMDMS(
          {@JsonKey(name: 'citymodule') final List<CityModule>? cityModule}) =
      _$TenantMDMSImpl;

  factory _TenantMDMS.fromJson(Map<String, dynamic> json) =
      _$TenantMDMSImpl.fromJson;

  @override
  @JsonKey(name: 'citymodule')
  List<CityModule>? get cityModule;
  @override
  @JsonKey(ignore: true)
  _$$TenantMDMSImplCopyWith<_$TenantMDMSImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CityModule _$CityModuleFromJson(Map<String, dynamic> json) {
  return _CityModule.fromJson(json);
}

/// @nodoc
mixin _$CityModule {
  bool? get active => throw _privateConstructorUsedError;
  String? get code => throw _privateConstructorUsedError;
  String? get module => throw _privateConstructorUsedError;
  int? get order => throw _privateConstructorUsedError;
  List<TenantList>? get tenants => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $CityModuleCopyWith<CityModule> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CityModuleCopyWith<$Res> {
  factory $CityModuleCopyWith(
          CityModule value, $Res Function(CityModule) then) =
      _$CityModuleCopyWithImpl<$Res, CityModule>;
  @useResult
  $Res call(
      {bool? active,
      String? code,
      String? module,
      int? order,
      List<TenantList>? tenants});
}

/// @nodoc
class _$CityModuleCopyWithImpl<$Res, $Val extends CityModule>
    implements $CityModuleCopyWith<$Res> {
  _$CityModuleCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? active = freezed,
    Object? code = freezed,
    Object? module = freezed,
    Object? order = freezed,
    Object? tenants = freezed,
  }) {
    return _then(_value.copyWith(
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      module: freezed == module
          ? _value.module
          : module // ignore: cast_nullable_to_non_nullable
              as String?,
      order: freezed == order
          ? _value.order
          : order // ignore: cast_nullable_to_non_nullable
              as int?,
      tenants: freezed == tenants
          ? _value.tenants
          : tenants // ignore: cast_nullable_to_non_nullable
              as List<TenantList>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CityModuleImplCopyWith<$Res>
    implements $CityModuleCopyWith<$Res> {
  factory _$$CityModuleImplCopyWith(
          _$CityModuleImpl value, $Res Function(_$CityModuleImpl) then) =
      __$$CityModuleImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {bool? active,
      String? code,
      String? module,
      int? order,
      List<TenantList>? tenants});
}

/// @nodoc
class __$$CityModuleImplCopyWithImpl<$Res>
    extends _$CityModuleCopyWithImpl<$Res, _$CityModuleImpl>
    implements _$$CityModuleImplCopyWith<$Res> {
  __$$CityModuleImplCopyWithImpl(
      _$CityModuleImpl _value, $Res Function(_$CityModuleImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? active = freezed,
    Object? code = freezed,
    Object? module = freezed,
    Object? order = freezed,
    Object? tenants = freezed,
  }) {
    return _then(_$CityModuleImpl(
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      module: freezed == module
          ? _value.module
          : module // ignore: cast_nullable_to_non_nullable
              as String?,
      order: freezed == order
          ? _value.order
          : order // ignore: cast_nullable_to_non_nullable
              as int?,
      tenants: freezed == tenants
          ? _value._tenants
          : tenants // ignore: cast_nullable_to_non_nullable
              as List<TenantList>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$CityModuleImpl implements _CityModule {
  const _$CityModuleImpl(
      {this.active,
      this.code,
      this.module,
      this.order,
      final List<TenantList>? tenants})
      : _tenants = tenants;

  factory _$CityModuleImpl.fromJson(Map<String, dynamic> json) =>
      _$$CityModuleImplFromJson(json);

  @override
  final bool? active;
  @override
  final String? code;
  @override
  final String? module;
  @override
  final int? order;
  final List<TenantList>? _tenants;
  @override
  List<TenantList>? get tenants {
    final value = _tenants;
    if (value == null) return null;
    if (_tenants is EqualUnmodifiableListView) return _tenants;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'CityModule(active: $active, code: $code, module: $module, order: $order, tenants: $tenants)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CityModuleImpl &&
            (identical(other.active, active) || other.active == active) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.module, module) || other.module == module) &&
            (identical(other.order, order) || other.order == order) &&
            const DeepCollectionEquality().equals(other._tenants, _tenants));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, active, code, module, order,
      const DeepCollectionEquality().hash(_tenants));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CityModuleImplCopyWith<_$CityModuleImpl> get copyWith =>
      __$$CityModuleImplCopyWithImpl<_$CityModuleImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CityModuleImplToJson(
      this,
    );
  }
}

abstract class _CityModule implements CityModule {
  const factory _CityModule(
      {final bool? active,
      final String? code,
      final String? module,
      final int? order,
      final List<TenantList>? tenants}) = _$CityModuleImpl;

  factory _CityModule.fromJson(Map<String, dynamic> json) =
      _$CityModuleImpl.fromJson;

  @override
  bool? get active;
  @override
  String? get code;
  @override
  String? get module;
  @override
  int? get order;
  @override
  List<TenantList>? get tenants;
  @override
  @JsonKey(ignore: true)
  _$$CityModuleImplCopyWith<_$CityModuleImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

TenantList _$TenantListFromJson(Map<String, dynamic> json) {
  return _TenantList.fromJson(json);
}

/// @nodoc
mixin _$TenantList {
  @JsonKey(name: 'code')
  String? get code => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $TenantListCopyWith<TenantList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TenantListCopyWith<$Res> {
  factory $TenantListCopyWith(
          TenantList value, $Res Function(TenantList) then) =
      _$TenantListCopyWithImpl<$Res, TenantList>;
  @useResult
  $Res call({@JsonKey(name: 'code') String? code});
}

/// @nodoc
class _$TenantListCopyWithImpl<$Res, $Val extends TenantList>
    implements $TenantListCopyWith<$Res> {
  _$TenantListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
  }) {
    return _then(_value.copyWith(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$TenantListImplCopyWith<$Res>
    implements $TenantListCopyWith<$Res> {
  factory _$$TenantListImplCopyWith(
          _$TenantListImpl value, $Res Function(_$TenantListImpl) then) =
      __$$TenantListImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'code') String? code});
}

/// @nodoc
class __$$TenantListImplCopyWithImpl<$Res>
    extends _$TenantListCopyWithImpl<$Res, _$TenantListImpl>
    implements _$$TenantListImplCopyWith<$Res> {
  __$$TenantListImplCopyWithImpl(
      _$TenantListImpl _value, $Res Function(_$TenantListImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
  }) {
    return _then(_$TenantListImpl(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$TenantListImpl implements _TenantList {
  const _$TenantListImpl({@JsonKey(name: 'code') this.code});

  factory _$TenantListImpl.fromJson(Map<String, dynamic> json) =>
      _$$TenantListImplFromJson(json);

  @override
  @JsonKey(name: 'code')
  final String? code;

  @override
  String toString() {
    return 'TenantList(code: $code)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TenantListImpl &&
            (identical(other.code, code) || other.code == code));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$TenantListImplCopyWith<_$TenantListImpl> get copyWith =>
      __$$TenantListImplCopyWithImpl<_$TenantListImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$TenantListImplToJson(
      this,
    );
  }
}

abstract class _TenantList implements TenantList {
  const factory _TenantList({@JsonKey(name: 'code') final String? code}) =
      _$TenantListImpl;

  factory _TenantList.fromJson(Map<String, dynamic> json) =
      _$TenantListImpl.fromJson;

  @override
  @JsonKey(name: 'code')
  String? get code;
  @override
  @JsonKey(ignore: true)
  _$$TenantListImplCopyWith<_$TenantListImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

WageSeekerCommonMDMS _$WageSeekerCommonMDMSFromJson(Map<String, dynamic> json) {
  return _WageSeekerCommonMDMS.fromJson(json);
}

/// @nodoc
mixin _$WageSeekerCommonMDMS {
  @JsonKey(name: 'GenderType')
  List<GenderType>? get genderType => throw _privateConstructorUsedError;
  @JsonKey(name: 'WageSeekerSkills')
  List<WageSeekerSkills>? get wageSeekerSkills =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'Relationship')
  List<Relationship>? get relationship => throw _privateConstructorUsedError;
  @JsonKey(name: 'SocialCategory')
  List<SocialCategory>? get socialCategory =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'DocumentType')
  List<DocumentType>? get documentType => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WageSeekerCommonMDMSCopyWith<WageSeekerCommonMDMS> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerCommonMDMSCopyWith<$Res> {
  factory $WageSeekerCommonMDMSCopyWith(WageSeekerCommonMDMS value,
          $Res Function(WageSeekerCommonMDMS) then) =
      _$WageSeekerCommonMDMSCopyWithImpl<$Res, WageSeekerCommonMDMS>;
  @useResult
  $Res call(
      {@JsonKey(name: 'GenderType')
          List<GenderType>? genderType,
      @JsonKey(name: 'WageSeekerSkills')
          List<WageSeekerSkills>? wageSeekerSkills,
      @JsonKey(name: 'Relationship')
          List<Relationship>? relationship,
      @JsonKey(name: 'SocialCategory')
          List<SocialCategory>? socialCategory,
      @JsonKey(name: 'DocumentType')
          List<DocumentType>? documentType});
}

/// @nodoc
class _$WageSeekerCommonMDMSCopyWithImpl<$Res,
        $Val extends WageSeekerCommonMDMS>
    implements $WageSeekerCommonMDMSCopyWith<$Res> {
  _$WageSeekerCommonMDMSCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? genderType = freezed,
    Object? wageSeekerSkills = freezed,
    Object? relationship = freezed,
    Object? socialCategory = freezed,
    Object? documentType = freezed,
  }) {
    return _then(_value.copyWith(
      genderType: freezed == genderType
          ? _value.genderType
          : genderType // ignore: cast_nullable_to_non_nullable
              as List<GenderType>?,
      wageSeekerSkills: freezed == wageSeekerSkills
          ? _value.wageSeekerSkills
          : wageSeekerSkills // ignore: cast_nullable_to_non_nullable
              as List<WageSeekerSkills>?,
      relationship: freezed == relationship
          ? _value.relationship
          : relationship // ignore: cast_nullable_to_non_nullable
              as List<Relationship>?,
      socialCategory: freezed == socialCategory
          ? _value.socialCategory
          : socialCategory // ignore: cast_nullable_to_non_nullable
              as List<SocialCategory>?,
      documentType: freezed == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as List<DocumentType>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$WageSeekerCommonMDMSImplCopyWith<$Res>
    implements $WageSeekerCommonMDMSCopyWith<$Res> {
  factory _$$WageSeekerCommonMDMSImplCopyWith(_$WageSeekerCommonMDMSImpl value,
          $Res Function(_$WageSeekerCommonMDMSImpl) then) =
      __$$WageSeekerCommonMDMSImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'GenderType')
          List<GenderType>? genderType,
      @JsonKey(name: 'WageSeekerSkills')
          List<WageSeekerSkills>? wageSeekerSkills,
      @JsonKey(name: 'Relationship')
          List<Relationship>? relationship,
      @JsonKey(name: 'SocialCategory')
          List<SocialCategory>? socialCategory,
      @JsonKey(name: 'DocumentType')
          List<DocumentType>? documentType});
}

/// @nodoc
class __$$WageSeekerCommonMDMSImplCopyWithImpl<$Res>
    extends _$WageSeekerCommonMDMSCopyWithImpl<$Res, _$WageSeekerCommonMDMSImpl>
    implements _$$WageSeekerCommonMDMSImplCopyWith<$Res> {
  __$$WageSeekerCommonMDMSImplCopyWithImpl(_$WageSeekerCommonMDMSImpl _value,
      $Res Function(_$WageSeekerCommonMDMSImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? genderType = freezed,
    Object? wageSeekerSkills = freezed,
    Object? relationship = freezed,
    Object? socialCategory = freezed,
    Object? documentType = freezed,
  }) {
    return _then(_$WageSeekerCommonMDMSImpl(
      genderType: freezed == genderType
          ? _value._genderType
          : genderType // ignore: cast_nullable_to_non_nullable
              as List<GenderType>?,
      wageSeekerSkills: freezed == wageSeekerSkills
          ? _value._wageSeekerSkills
          : wageSeekerSkills // ignore: cast_nullable_to_non_nullable
              as List<WageSeekerSkills>?,
      relationship: freezed == relationship
          ? _value._relationship
          : relationship // ignore: cast_nullable_to_non_nullable
              as List<Relationship>?,
      socialCategory: freezed == socialCategory
          ? _value._socialCategory
          : socialCategory // ignore: cast_nullable_to_non_nullable
              as List<SocialCategory>?,
      documentType: freezed == documentType
          ? _value._documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as List<DocumentType>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$WageSeekerCommonMDMSImpl implements _WageSeekerCommonMDMS {
  const _$WageSeekerCommonMDMSImpl(
      {@JsonKey(name: 'GenderType')
          final List<GenderType>? genderType,
      @JsonKey(name: 'WageSeekerSkills')
          final List<WageSeekerSkills>? wageSeekerSkills,
      @JsonKey(name: 'Relationship')
          final List<Relationship>? relationship,
      @JsonKey(name: 'SocialCategory')
          final List<SocialCategory>? socialCategory,
      @JsonKey(name: 'DocumentType')
          final List<DocumentType>? documentType})
      : _genderType = genderType,
        _wageSeekerSkills = wageSeekerSkills,
        _relationship = relationship,
        _socialCategory = socialCategory,
        _documentType = documentType;

  factory _$WageSeekerCommonMDMSImpl.fromJson(Map<String, dynamic> json) =>
      _$$WageSeekerCommonMDMSImplFromJson(json);

  final List<GenderType>? _genderType;
  @override
  @JsonKey(name: 'GenderType')
  List<GenderType>? get genderType {
    final value = _genderType;
    if (value == null) return null;
    if (_genderType is EqualUnmodifiableListView) return _genderType;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

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

  final List<Relationship>? _relationship;
  @override
  @JsonKey(name: 'Relationship')
  List<Relationship>? get relationship {
    final value = _relationship;
    if (value == null) return null;
    if (_relationship is EqualUnmodifiableListView) return _relationship;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<SocialCategory>? _socialCategory;
  @override
  @JsonKey(name: 'SocialCategory')
  List<SocialCategory>? get socialCategory {
    final value = _socialCategory;
    if (value == null) return null;
    if (_socialCategory is EqualUnmodifiableListView) return _socialCategory;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<DocumentType>? _documentType;
  @override
  @JsonKey(name: 'DocumentType')
  List<DocumentType>? get documentType {
    final value = _documentType;
    if (value == null) return null;
    if (_documentType is EqualUnmodifiableListView) return _documentType;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'WageSeekerCommonMDMS(genderType: $genderType, wageSeekerSkills: $wageSeekerSkills, relationship: $relationship, socialCategory: $socialCategory, documentType: $documentType)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerCommonMDMSImpl &&
            const DeepCollectionEquality()
                .equals(other._genderType, _genderType) &&
            const DeepCollectionEquality()
                .equals(other._wageSeekerSkills, _wageSeekerSkills) &&
            const DeepCollectionEquality()
                .equals(other._relationship, _relationship) &&
            const DeepCollectionEquality()
                .equals(other._socialCategory, _socialCategory) &&
            const DeepCollectionEquality()
                .equals(other._documentType, _documentType));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_genderType),
      const DeepCollectionEquality().hash(_wageSeekerSkills),
      const DeepCollectionEquality().hash(_relationship),
      const DeepCollectionEquality().hash(_socialCategory),
      const DeepCollectionEquality().hash(_documentType));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerCommonMDMSImplCopyWith<_$WageSeekerCommonMDMSImpl>
      get copyWith =>
          __$$WageSeekerCommonMDMSImplCopyWithImpl<_$WageSeekerCommonMDMSImpl>(
              this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$WageSeekerCommonMDMSImplToJson(
      this,
    );
  }
}

abstract class _WageSeekerCommonMDMS implements WageSeekerCommonMDMS {
  const factory _WageSeekerCommonMDMS(
      {@JsonKey(name: 'GenderType')
          final List<GenderType>? genderType,
      @JsonKey(name: 'WageSeekerSkills')
          final List<WageSeekerSkills>? wageSeekerSkills,
      @JsonKey(name: 'Relationship')
          final List<Relationship>? relationship,
      @JsonKey(name: 'SocialCategory')
          final List<SocialCategory>? socialCategory,
      @JsonKey(name: 'DocumentType')
          final List<DocumentType>? documentType}) = _$WageSeekerCommonMDMSImpl;

  factory _WageSeekerCommonMDMS.fromJson(Map<String, dynamic> json) =
      _$WageSeekerCommonMDMSImpl.fromJson;

  @override
  @JsonKey(name: 'GenderType')
  List<GenderType>? get genderType;
  @override
  @JsonKey(name: 'WageSeekerSkills')
  List<WageSeekerSkills>? get wageSeekerSkills;
  @override
  @JsonKey(name: 'Relationship')
  List<Relationship>? get relationship;
  @override
  @JsonKey(name: 'SocialCategory')
  List<SocialCategory>? get socialCategory;
  @override
  @JsonKey(name: 'DocumentType')
  List<DocumentType>? get documentType;
  @override
  @JsonKey(ignore: true)
  _$$WageSeekerCommonMDMSImplCopyWith<_$WageSeekerCommonMDMSImpl>
      get copyWith => throw _privateConstructorUsedError;
}

GenderType _$GenderTypeFromJson(Map<String, dynamic> json) {
  return _GenderType.fromJson(json);
}

/// @nodoc
mixin _$GenderType {
  String get code => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $GenderTypeCopyWith<GenderType> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $GenderTypeCopyWith<$Res> {
  factory $GenderTypeCopyWith(
          GenderType value, $Res Function(GenderType) then) =
      _$GenderTypeCopyWithImpl<$Res, GenderType>;
  @useResult
  $Res call({String code, bool active});
}

/// @nodoc
class _$GenderTypeCopyWithImpl<$Res, $Val extends GenderType>
    implements $GenderTypeCopyWith<$Res> {
  _$GenderTypeCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$GenderTypeImplCopyWith<$Res>
    implements $GenderTypeCopyWith<$Res> {
  factory _$$GenderTypeImplCopyWith(
          _$GenderTypeImpl value, $Res Function(_$GenderTypeImpl) then) =
      __$$GenderTypeImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String code, bool active});
}

/// @nodoc
class __$$GenderTypeImplCopyWithImpl<$Res>
    extends _$GenderTypeCopyWithImpl<$Res, _$GenderTypeImpl>
    implements _$$GenderTypeImplCopyWith<$Res> {
  __$$GenderTypeImplCopyWithImpl(
      _$GenderTypeImpl _value, $Res Function(_$GenderTypeImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_$GenderTypeImpl(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
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
class _$GenderTypeImpl implements _GenderType {
  const _$GenderTypeImpl({required this.code, required this.active});

  factory _$GenderTypeImpl.fromJson(Map<String, dynamic> json) =>
      _$$GenderTypeImplFromJson(json);

  @override
  final String code;
  @override
  final bool active;

  @override
  String toString() {
    return 'GenderType(code: $code, active: $active)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$GenderTypeImpl &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$GenderTypeImplCopyWith<_$GenderTypeImpl> get copyWith =>
      __$$GenderTypeImplCopyWithImpl<_$GenderTypeImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$GenderTypeImplToJson(
      this,
    );
  }
}

abstract class _GenderType implements GenderType {
  const factory _GenderType(
      {required final String code,
      required final bool active}) = _$GenderTypeImpl;

  factory _GenderType.fromJson(Map<String, dynamic> json) =
      _$GenderTypeImpl.fromJson;

  @override
  String get code;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$GenderTypeImplCopyWith<_$GenderTypeImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

Relationship _$RelationshipFromJson(Map<String, dynamic> json) {
  return _Relationship.fromJson(json);
}

/// @nodoc
mixin _$Relationship {
  String get name => throw _privateConstructorUsedError;
  String get code => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $RelationshipCopyWith<Relationship> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $RelationshipCopyWith<$Res> {
  factory $RelationshipCopyWith(
          Relationship value, $Res Function(Relationship) then) =
      _$RelationshipCopyWithImpl<$Res, Relationship>;
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class _$RelationshipCopyWithImpl<$Res, $Val extends Relationship>
    implements $RelationshipCopyWith<$Res> {
  _$RelationshipCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$RelationshipImplCopyWith<$Res>
    implements $RelationshipCopyWith<$Res> {
  factory _$$RelationshipImplCopyWith(
          _$RelationshipImpl value, $Res Function(_$RelationshipImpl) then) =
      __$$RelationshipImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class __$$RelationshipImplCopyWithImpl<$Res>
    extends _$RelationshipCopyWithImpl<$Res, _$RelationshipImpl>
    implements _$$RelationshipImplCopyWith<$Res> {
  __$$RelationshipImplCopyWithImpl(
      _$RelationshipImpl _value, $Res Function(_$RelationshipImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_$RelationshipImpl(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
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
class _$RelationshipImpl implements _Relationship {
  const _$RelationshipImpl(
      {required this.name, required this.code, required this.active});

  factory _$RelationshipImpl.fromJson(Map<String, dynamic> json) =>
      _$$RelationshipImplFromJson(json);

  @override
  final String name;
  @override
  final String code;
  @override
  final bool active;

  @override
  String toString() {
    return 'Relationship(name: $name, code: $code, active: $active)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$RelationshipImpl &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, name, code, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$RelationshipImplCopyWith<_$RelationshipImpl> get copyWith =>
      __$$RelationshipImplCopyWithImpl<_$RelationshipImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$RelationshipImplToJson(
      this,
    );
  }
}

abstract class _Relationship implements Relationship {
  const factory _Relationship(
      {required final String name,
      required final String code,
      required final bool active}) = _$RelationshipImpl;

  factory _Relationship.fromJson(Map<String, dynamic> json) =
      _$RelationshipImpl.fromJson;

  @override
  String get name;
  @override
  String get code;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$RelationshipImplCopyWith<_$RelationshipImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

SocialCategory _$SocialCategoryFromJson(Map<String, dynamic> json) {
  return _SocialCategory.fromJson(json);
}

/// @nodoc
mixin _$SocialCategory {
  String get name => throw _privateConstructorUsedError;
  String get code => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $SocialCategoryCopyWith<SocialCategory> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $SocialCategoryCopyWith<$Res> {
  factory $SocialCategoryCopyWith(
          SocialCategory value, $Res Function(SocialCategory) then) =
      _$SocialCategoryCopyWithImpl<$Res, SocialCategory>;
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class _$SocialCategoryCopyWithImpl<$Res, $Val extends SocialCategory>
    implements $SocialCategoryCopyWith<$Res> {
  _$SocialCategoryCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$SocialCategoryImplCopyWith<$Res>
    implements $SocialCategoryCopyWith<$Res> {
  factory _$$SocialCategoryImplCopyWith(_$SocialCategoryImpl value,
          $Res Function(_$SocialCategoryImpl) then) =
      __$$SocialCategoryImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class __$$SocialCategoryImplCopyWithImpl<$Res>
    extends _$SocialCategoryCopyWithImpl<$Res, _$SocialCategoryImpl>
    implements _$$SocialCategoryImplCopyWith<$Res> {
  __$$SocialCategoryImplCopyWithImpl(
      _$SocialCategoryImpl _value, $Res Function(_$SocialCategoryImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_$SocialCategoryImpl(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
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
class _$SocialCategoryImpl implements _SocialCategory {
  const _$SocialCategoryImpl(
      {required this.name, required this.code, required this.active});

  factory _$SocialCategoryImpl.fromJson(Map<String, dynamic> json) =>
      _$$SocialCategoryImplFromJson(json);

  @override
  final String name;
  @override
  final String code;
  @override
  final bool active;

  @override
  String toString() {
    return 'SocialCategory(name: $name, code: $code, active: $active)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SocialCategoryImpl &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, name, code, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SocialCategoryImplCopyWith<_$SocialCategoryImpl> get copyWith =>
      __$$SocialCategoryImplCopyWithImpl<_$SocialCategoryImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$SocialCategoryImplToJson(
      this,
    );
  }
}

abstract class _SocialCategory implements SocialCategory {
  const factory _SocialCategory(
      {required final String name,
      required final String code,
      required final bool active}) = _$SocialCategoryImpl;

  factory _SocialCategory.fromJson(Map<String, dynamic> json) =
      _$SocialCategoryImpl.fromJson;

  @override
  String get name;
  @override
  String get code;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$SocialCategoryImplCopyWith<_$SocialCategoryImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

BankAccType _$BankAccTypeFromJson(Map<String, dynamic> json) {
  return _BankAccType.fromJson(json);
}

/// @nodoc
mixin _$BankAccType {
  String get name => throw _privateConstructorUsedError;
  String get code => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $BankAccTypeCopyWith<BankAccType> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BankAccTypeCopyWith<$Res> {
  factory $BankAccTypeCopyWith(
          BankAccType value, $Res Function(BankAccType) then) =
      _$BankAccTypeCopyWithImpl<$Res, BankAccType>;
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class _$BankAccTypeCopyWithImpl<$Res, $Val extends BankAccType>
    implements $BankAccTypeCopyWith<$Res> {
  _$BankAccTypeCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$BankAccTypeImplCopyWith<$Res>
    implements $BankAccTypeCopyWith<$Res> {
  factory _$$BankAccTypeImplCopyWith(
          _$BankAccTypeImpl value, $Res Function(_$BankAccTypeImpl) then) =
      __$$BankAccTypeImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class __$$BankAccTypeImplCopyWithImpl<$Res>
    extends _$BankAccTypeCopyWithImpl<$Res, _$BankAccTypeImpl>
    implements _$$BankAccTypeImplCopyWith<$Res> {
  __$$BankAccTypeImplCopyWithImpl(
      _$BankAccTypeImpl _value, $Res Function(_$BankAccTypeImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_$BankAccTypeImpl(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
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
class _$BankAccTypeImpl implements _BankAccType {
  const _$BankAccTypeImpl(
      {required this.name, required this.code, required this.active});

  factory _$BankAccTypeImpl.fromJson(Map<String, dynamic> json) =>
      _$$BankAccTypeImplFromJson(json);

  @override
  final String name;
  @override
  final String code;
  @override
  final bool active;

  @override
  String toString() {
    return 'BankAccType(name: $name, code: $code, active: $active)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BankAccTypeImpl &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, name, code, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$BankAccTypeImplCopyWith<_$BankAccTypeImpl> get copyWith =>
      __$$BankAccTypeImplCopyWithImpl<_$BankAccTypeImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$BankAccTypeImplToJson(
      this,
    );
  }
}

abstract class _BankAccType implements BankAccType {
  const factory _BankAccType(
      {required final String name,
      required final String code,
      required final bool active}) = _$BankAccTypeImpl;

  factory _BankAccType.fromJson(Map<String, dynamic> json) =
      _$BankAccTypeImpl.fromJson;

  @override
  String get name;
  @override
  String get code;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$BankAccTypeImplCopyWith<_$BankAccTypeImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

DocumentType _$DocumentTypeFromJson(Map<String, dynamic> json) {
  return _DocumentType.fromJson(json);
}

/// @nodoc
mixin _$DocumentType {
  String get name => throw _privateConstructorUsedError;
  String get code => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $DocumentTypeCopyWith<DocumentType> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DocumentTypeCopyWith<$Res> {
  factory $DocumentTypeCopyWith(
          DocumentType value, $Res Function(DocumentType) then) =
      _$DocumentTypeCopyWithImpl<$Res, DocumentType>;
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class _$DocumentTypeCopyWithImpl<$Res, $Val extends DocumentType>
    implements $DocumentTypeCopyWith<$Res> {
  _$DocumentTypeCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$DocumentTypeImplCopyWith<$Res>
    implements $DocumentTypeCopyWith<$Res> {
  factory _$$DocumentTypeImplCopyWith(
          _$DocumentTypeImpl value, $Res Function(_$DocumentTypeImpl) then) =
      __$$DocumentTypeImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String name, String code, bool active});
}

/// @nodoc
class __$$DocumentTypeImplCopyWithImpl<$Res>
    extends _$DocumentTypeCopyWithImpl<$Res, _$DocumentTypeImpl>
    implements _$$DocumentTypeImplCopyWith<$Res> {
  __$$DocumentTypeImplCopyWithImpl(
      _$DocumentTypeImpl _value, $Res Function(_$DocumentTypeImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = null,
    Object? code = null,
    Object? active = null,
  }) {
    return _then(_$DocumentTypeImpl(
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
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
class _$DocumentTypeImpl implements _DocumentType {
  const _$DocumentTypeImpl(
      {required this.name, required this.code, required this.active});

  factory _$DocumentTypeImpl.fromJson(Map<String, dynamic> json) =>
      _$$DocumentTypeImplFromJson(json);

  @override
  final String name;
  @override
  final String code;
  @override
  final bool active;

  @override
  String toString() {
    return 'DocumentType(name: $name, code: $code, active: $active)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DocumentTypeImpl &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, name, code, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$DocumentTypeImplCopyWith<_$DocumentTypeImpl> get copyWith =>
      __$$DocumentTypeImplCopyWithImpl<_$DocumentTypeImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$DocumentTypeImplToJson(
      this,
    );
  }
}

abstract class _DocumentType implements DocumentType {
  const factory _DocumentType(
      {required final String name,
      required final String code,
      required final bool active}) = _$DocumentTypeImpl;

  factory _DocumentType.fromJson(Map<String, dynamic> json) =
      _$DocumentTypeImpl.fromJson;

  @override
  String get name;
  @override
  String get code;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$DocumentTypeImplCopyWith<_$DocumentTypeImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
