// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'init_mdms_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

InitMdmsModel _$InitMdmsModelFromJson(Map<String, dynamic> json) {
  return _InitMdmsModel.fromJson(json);
}

/// @nodoc
mixin _$InitMdmsModel {
  @JsonKey(name: 'common-masters')
  CommonMastersModel? get commonMastersModel =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'tenant')
  TenantModel? get tenant => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $InitMdmsModelCopyWith<InitMdmsModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $InitMdmsModelCopyWith<$Res> {
  factory $InitMdmsModelCopyWith(
          InitMdmsModel value, $Res Function(InitMdmsModel) then) =
      _$InitMdmsModelCopyWithImpl<$Res, InitMdmsModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'common-masters') CommonMastersModel? commonMastersModel,
      @JsonKey(name: 'tenant') TenantModel? tenant});

  $CommonMastersModelCopyWith<$Res>? get commonMastersModel;
  $TenantModelCopyWith<$Res>? get tenant;
}

/// @nodoc
class _$InitMdmsModelCopyWithImpl<$Res, $Val extends InitMdmsModel>
    implements $InitMdmsModelCopyWith<$Res> {
  _$InitMdmsModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonMastersModel = freezed,
    Object? tenant = freezed,
  }) {
    return _then(_value.copyWith(
      commonMastersModel: freezed == commonMastersModel
          ? _value.commonMastersModel
          : commonMastersModel // ignore: cast_nullable_to_non_nullable
              as CommonMastersModel?,
      tenant: freezed == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as TenantModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $CommonMastersModelCopyWith<$Res>? get commonMastersModel {
    if (_value.commonMastersModel == null) {
      return null;
    }

    return $CommonMastersModelCopyWith<$Res>(_value.commonMastersModel!,
        (value) {
      return _then(_value.copyWith(commonMastersModel: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $TenantModelCopyWith<$Res>? get tenant {
    if (_value.tenant == null) {
      return null;
    }

    return $TenantModelCopyWith<$Res>(_value.tenant!, (value) {
      return _then(_value.copyWith(tenant: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_InitMdmsModelCopyWith<$Res>
    implements $InitMdmsModelCopyWith<$Res> {
  factory _$$_InitMdmsModelCopyWith(
          _$_InitMdmsModel value, $Res Function(_$_InitMdmsModel) then) =
      __$$_InitMdmsModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'common-masters') CommonMastersModel? commonMastersModel,
      @JsonKey(name: 'tenant') TenantModel? tenant});

  @override
  $CommonMastersModelCopyWith<$Res>? get commonMastersModel;
  @override
  $TenantModelCopyWith<$Res>? get tenant;
}

/// @nodoc
class __$$_InitMdmsModelCopyWithImpl<$Res>
    extends _$InitMdmsModelCopyWithImpl<$Res, _$_InitMdmsModel>
    implements _$$_InitMdmsModelCopyWith<$Res> {
  __$$_InitMdmsModelCopyWithImpl(
      _$_InitMdmsModel _value, $Res Function(_$_InitMdmsModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonMastersModel = freezed,
    Object? tenant = freezed,
  }) {
    return _then(_$_InitMdmsModel(
      commonMastersModel: freezed == commonMastersModel
          ? _value.commonMastersModel
          : commonMastersModel // ignore: cast_nullable_to_non_nullable
              as CommonMastersModel?,
      tenant: freezed == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as TenantModel?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_InitMdmsModel implements _InitMdmsModel {
  const _$_InitMdmsModel(
      {@JsonKey(name: 'common-masters') this.commonMastersModel,
      @JsonKey(name: 'tenant') this.tenant});

  factory _$_InitMdmsModel.fromJson(Map<String, dynamic> json) =>
      _$$_InitMdmsModelFromJson(json);

  @override
  @JsonKey(name: 'common-masters')
  final CommonMastersModel? commonMastersModel;
  @override
  @JsonKey(name: 'tenant')
  final TenantModel? tenant;

  @override
  String toString() {
    return 'InitMdmsModel(commonMastersModel: $commonMastersModel, tenant: $tenant)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_InitMdmsModel &&
            (identical(other.commonMastersModel, commonMastersModel) ||
                other.commonMastersModel == commonMastersModel) &&
            (identical(other.tenant, tenant) || other.tenant == tenant));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, commonMastersModel, tenant);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_InitMdmsModelCopyWith<_$_InitMdmsModel> get copyWith =>
      __$$_InitMdmsModelCopyWithImpl<_$_InitMdmsModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_InitMdmsModelToJson(
      this,
    );
  }
}

abstract class _InitMdmsModel implements InitMdmsModel {
  const factory _InitMdmsModel(
      {@JsonKey(name: 'common-masters')
      final CommonMastersModel? commonMastersModel,
      @JsonKey(name: 'tenant') final TenantModel? tenant}) = _$_InitMdmsModel;

  factory _InitMdmsModel.fromJson(Map<String, dynamic> json) =
      _$_InitMdmsModel.fromJson;

  @override
  @JsonKey(name: 'common-masters')
  CommonMastersModel? get commonMastersModel;
  @override
  @JsonKey(name: 'tenant')
  TenantModel? get tenant;
  @override
  @JsonKey(ignore: true)
  _$$_InitMdmsModelCopyWith<_$_InitMdmsModel> get copyWith =>
      throw _privateConstructorUsedError;
}

TenantModel _$TenantModelFromJson(Map<String, dynamic> json) {
  return _TenantModel.fromJson(json);
}

/// @nodoc
mixin _$TenantModel {
  @JsonKey(name: 'tenants')
  List<TenantListModel>? get tenantListModel =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'citymodule')
  List<CityModuleModel>? get cityModuleModel =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $TenantModelCopyWith<TenantModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TenantModelCopyWith<$Res> {
  factory $TenantModelCopyWith(
          TenantModel value, $Res Function(TenantModel) then) =
      _$TenantModelCopyWithImpl<$Res, TenantModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'tenants') List<TenantListModel>? tenantListModel,
      @JsonKey(name: 'citymodule') List<CityModuleModel>? cityModuleModel});
}

/// @nodoc
class _$TenantModelCopyWithImpl<$Res, $Val extends TenantModel>
    implements $TenantModelCopyWith<$Res> {
  _$TenantModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantListModel = freezed,
    Object? cityModuleModel = freezed,
  }) {
    return _then(_value.copyWith(
      tenantListModel: freezed == tenantListModel
          ? _value.tenantListModel
          : tenantListModel // ignore: cast_nullable_to_non_nullable
              as List<TenantListModel>?,
      cityModuleModel: freezed == cityModuleModel
          ? _value.cityModuleModel
          : cityModuleModel // ignore: cast_nullable_to_non_nullable
              as List<CityModuleModel>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_TenantModelCopyWith<$Res>
    implements $TenantModelCopyWith<$Res> {
  factory _$$_TenantModelCopyWith(
          _$_TenantModel value, $Res Function(_$_TenantModel) then) =
      __$$_TenantModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'tenants') List<TenantListModel>? tenantListModel,
      @JsonKey(name: 'citymodule') List<CityModuleModel>? cityModuleModel});
}

/// @nodoc
class __$$_TenantModelCopyWithImpl<$Res>
    extends _$TenantModelCopyWithImpl<$Res, _$_TenantModel>
    implements _$$_TenantModelCopyWith<$Res> {
  __$$_TenantModelCopyWithImpl(
      _$_TenantModel _value, $Res Function(_$_TenantModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantListModel = freezed,
    Object? cityModuleModel = freezed,
  }) {
    return _then(_$_TenantModel(
      tenantListModel: freezed == tenantListModel
          ? _value._tenantListModel
          : tenantListModel // ignore: cast_nullable_to_non_nullable
              as List<TenantListModel>?,
      cityModuleModel: freezed == cityModuleModel
          ? _value._cityModuleModel
          : cityModuleModel // ignore: cast_nullable_to_non_nullable
              as List<CityModuleModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_TenantModel implements _TenantModel {
  const _$_TenantModel(
      {@JsonKey(name: 'tenants') final List<TenantListModel>? tenantListModel,
      @JsonKey(name: 'citymodule')
      final List<CityModuleModel>? cityModuleModel})
      : _tenantListModel = tenantListModel,
        _cityModuleModel = cityModuleModel;

  factory _$_TenantModel.fromJson(Map<String, dynamic> json) =>
      _$$_TenantModelFromJson(json);

  final List<TenantListModel>? _tenantListModel;
  @override
  @JsonKey(name: 'tenants')
  List<TenantListModel>? get tenantListModel {
    final value = _tenantListModel;
    if (value == null) return null;
    if (_tenantListModel is EqualUnmodifiableListView) return _tenantListModel;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<CityModuleModel>? _cityModuleModel;
  @override
  @JsonKey(name: 'citymodule')
  List<CityModuleModel>? get cityModuleModel {
    final value = _cityModuleModel;
    if (value == null) return null;
    if (_cityModuleModel is EqualUnmodifiableListView) return _cityModuleModel;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'TenantModel(tenantListModel: $tenantListModel, cityModuleModel: $cityModuleModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_TenantModel &&
            const DeepCollectionEquality()
                .equals(other._tenantListModel, _tenantListModel) &&
            const DeepCollectionEquality()
                .equals(other._cityModuleModel, _cityModuleModel));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_tenantListModel),
      const DeepCollectionEquality().hash(_cityModuleModel));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_TenantModelCopyWith<_$_TenantModel> get copyWith =>
      __$$_TenantModelCopyWithImpl<_$_TenantModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_TenantModelToJson(
      this,
    );
  }
}

abstract class _TenantModel implements TenantModel {
  const factory _TenantModel(
      {@JsonKey(name: 'tenants') final List<TenantListModel>? tenantListModel,
      @JsonKey(name: 'citymodule')
      final List<CityModuleModel>? cityModuleModel}) = _$_TenantModel;

  factory _TenantModel.fromJson(Map<String, dynamic> json) =
      _$_TenantModel.fromJson;

  @override
  @JsonKey(name: 'tenants')
  List<TenantListModel>? get tenantListModel;
  @override
  @JsonKey(name: 'citymodule')
  List<CityModuleModel>? get cityModuleModel;
  @override
  @JsonKey(ignore: true)
  _$$_TenantModelCopyWith<_$_TenantModel> get copyWith =>
      throw _privateConstructorUsedError;
}

CommonMastersModel _$CommonMastersModelFromJson(Map<String, dynamic> json) {
  return _CommonMastersModel.fromJson(json);
}

/// @nodoc
mixin _$CommonMastersModel {
  @JsonKey(name: 'StateInfo')
  List<StateInfoListModel>? get stateInfoListModel =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'AppVersion')
  List<AppVersionModel>? get appVersionModel =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $CommonMastersModelCopyWith<CommonMastersModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CommonMastersModelCopyWith<$Res> {
  factory $CommonMastersModelCopyWith(
          CommonMastersModel value, $Res Function(CommonMastersModel) then) =
      _$CommonMastersModelCopyWithImpl<$Res, CommonMastersModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'StateInfo') List<StateInfoListModel>? stateInfoListModel,
      @JsonKey(name: 'AppVersion') List<AppVersionModel>? appVersionModel});
}

/// @nodoc
class _$CommonMastersModelCopyWithImpl<$Res, $Val extends CommonMastersModel>
    implements $CommonMastersModelCopyWith<$Res> {
  _$CommonMastersModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? stateInfoListModel = freezed,
    Object? appVersionModel = freezed,
  }) {
    return _then(_value.copyWith(
      stateInfoListModel: freezed == stateInfoListModel
          ? _value.stateInfoListModel
          : stateInfoListModel // ignore: cast_nullable_to_non_nullable
              as List<StateInfoListModel>?,
      appVersionModel: freezed == appVersionModel
          ? _value.appVersionModel
          : appVersionModel // ignore: cast_nullable_to_non_nullable
              as List<AppVersionModel>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_CommonMastersModelCopyWith<$Res>
    implements $CommonMastersModelCopyWith<$Res> {
  factory _$$_CommonMastersModelCopyWith(_$_CommonMastersModel value,
          $Res Function(_$_CommonMastersModel) then) =
      __$$_CommonMastersModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'StateInfo') List<StateInfoListModel>? stateInfoListModel,
      @JsonKey(name: 'AppVersion') List<AppVersionModel>? appVersionModel});
}

/// @nodoc
class __$$_CommonMastersModelCopyWithImpl<$Res>
    extends _$CommonMastersModelCopyWithImpl<$Res, _$_CommonMastersModel>
    implements _$$_CommonMastersModelCopyWith<$Res> {
  __$$_CommonMastersModelCopyWithImpl(
      _$_CommonMastersModel _value, $Res Function(_$_CommonMastersModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? stateInfoListModel = freezed,
    Object? appVersionModel = freezed,
  }) {
    return _then(_$_CommonMastersModel(
      stateInfoListModel: freezed == stateInfoListModel
          ? _value._stateInfoListModel
          : stateInfoListModel // ignore: cast_nullable_to_non_nullable
              as List<StateInfoListModel>?,
      appVersionModel: freezed == appVersionModel
          ? _value._appVersionModel
          : appVersionModel // ignore: cast_nullable_to_non_nullable
              as List<AppVersionModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_CommonMastersModel implements _CommonMastersModel {
  const _$_CommonMastersModel(
      {@JsonKey(name: 'StateInfo')
      final List<StateInfoListModel>? stateInfoListModel,
      @JsonKey(name: 'AppVersion')
      final List<AppVersionModel>? appVersionModel})
      : _stateInfoListModel = stateInfoListModel,
        _appVersionModel = appVersionModel;

  factory _$_CommonMastersModel.fromJson(Map<String, dynamic> json) =>
      _$$_CommonMastersModelFromJson(json);

  final List<StateInfoListModel>? _stateInfoListModel;
  @override
  @JsonKey(name: 'StateInfo')
  List<StateInfoListModel>? get stateInfoListModel {
    final value = _stateInfoListModel;
    if (value == null) return null;
    if (_stateInfoListModel is EqualUnmodifiableListView)
      return _stateInfoListModel;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<AppVersionModel>? _appVersionModel;
  @override
  @JsonKey(name: 'AppVersion')
  List<AppVersionModel>? get appVersionModel {
    final value = _appVersionModel;
    if (value == null) return null;
    if (_appVersionModel is EqualUnmodifiableListView) return _appVersionModel;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'CommonMastersModel(stateInfoListModel: $stateInfoListModel, appVersionModel: $appVersionModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CommonMastersModel &&
            const DeepCollectionEquality()
                .equals(other._stateInfoListModel, _stateInfoListModel) &&
            const DeepCollectionEquality()
                .equals(other._appVersionModel, _appVersionModel));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_stateInfoListModel),
      const DeepCollectionEquality().hash(_appVersionModel));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_CommonMastersModelCopyWith<_$_CommonMastersModel> get copyWith =>
      __$$_CommonMastersModelCopyWithImpl<_$_CommonMastersModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_CommonMastersModelToJson(
      this,
    );
  }
}

abstract class _CommonMastersModel implements CommonMastersModel {
  const factory _CommonMastersModel(
      {@JsonKey(name: 'StateInfo')
      final List<StateInfoListModel>? stateInfoListModel,
      @JsonKey(name: 'AppVersion')
      final List<AppVersionModel>? appVersionModel}) = _$_CommonMastersModel;

  factory _CommonMastersModel.fromJson(Map<String, dynamic> json) =
      _$_CommonMastersModel.fromJson;

  @override
  @JsonKey(name: 'StateInfo')
  List<StateInfoListModel>? get stateInfoListModel;
  @override
  @JsonKey(name: 'AppVersion')
  List<AppVersionModel>? get appVersionModel;
  @override
  @JsonKey(ignore: true)
  _$$_CommonMastersModelCopyWith<_$_CommonMastersModel> get copyWith =>
      throw _privateConstructorUsedError;
}

AppVersionModel _$AppVersionModelFromJson(Map<String, dynamic> json) {
  return _AppVersionModel.fromJson(json);
}

/// @nodoc
mixin _$AppVersionModel {
  String? get version => throw _privateConstructorUsedError;
  String? get packageName => throw _privateConstructorUsedError;
  String? get iOSId => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AppVersionModelCopyWith<AppVersionModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AppVersionModelCopyWith<$Res> {
  factory $AppVersionModelCopyWith(
          AppVersionModel value, $Res Function(AppVersionModel) then) =
      _$AppVersionModelCopyWithImpl<$Res, AppVersionModel>;
  @useResult
  $Res call({String? version, String? packageName, String? iOSId});
}

/// @nodoc
class _$AppVersionModelCopyWithImpl<$Res, $Val extends AppVersionModel>
    implements $AppVersionModelCopyWith<$Res> {
  _$AppVersionModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? version = freezed,
    Object? packageName = freezed,
    Object? iOSId = freezed,
  }) {
    return _then(_value.copyWith(
      version: freezed == version
          ? _value.version
          : version // ignore: cast_nullable_to_non_nullable
              as String?,
      packageName: freezed == packageName
          ? _value.packageName
          : packageName // ignore: cast_nullable_to_non_nullable
              as String?,
      iOSId: freezed == iOSId
          ? _value.iOSId
          : iOSId // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AppVersionModelCopyWith<$Res>
    implements $AppVersionModelCopyWith<$Res> {
  factory _$$_AppVersionModelCopyWith(
          _$_AppVersionModel value, $Res Function(_$_AppVersionModel) then) =
      __$$_AppVersionModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? version, String? packageName, String? iOSId});
}

/// @nodoc
class __$$_AppVersionModelCopyWithImpl<$Res>
    extends _$AppVersionModelCopyWithImpl<$Res, _$_AppVersionModel>
    implements _$$_AppVersionModelCopyWith<$Res> {
  __$$_AppVersionModelCopyWithImpl(
      _$_AppVersionModel _value, $Res Function(_$_AppVersionModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? version = freezed,
    Object? packageName = freezed,
    Object? iOSId = freezed,
  }) {
    return _then(_$_AppVersionModel(
      version: freezed == version
          ? _value.version
          : version // ignore: cast_nullable_to_non_nullable
              as String?,
      packageName: freezed == packageName
          ? _value.packageName
          : packageName // ignore: cast_nullable_to_non_nullable
              as String?,
      iOSId: freezed == iOSId
          ? _value.iOSId
          : iOSId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AppVersionModel implements _AppVersionModel {
  const _$_AppVersionModel({this.version, this.packageName, this.iOSId});

  factory _$_AppVersionModel.fromJson(Map<String, dynamic> json) =>
      _$$_AppVersionModelFromJson(json);

  @override
  final String? version;
  @override
  final String? packageName;
  @override
  final String? iOSId;

  @override
  String toString() {
    return 'AppVersionModel(version: $version, packageName: $packageName, iOSId: $iOSId)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AppVersionModel &&
            (identical(other.version, version) || other.version == version) &&
            (identical(other.packageName, packageName) ||
                other.packageName == packageName) &&
            (identical(other.iOSId, iOSId) || other.iOSId == iOSId));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, version, packageName, iOSId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AppVersionModelCopyWith<_$_AppVersionModel> get copyWith =>
      __$$_AppVersionModelCopyWithImpl<_$_AppVersionModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AppVersionModelToJson(
      this,
    );
  }
}

abstract class _AppVersionModel implements AppVersionModel {
  const factory _AppVersionModel(
      {final String? version,
      final String? packageName,
      final String? iOSId}) = _$_AppVersionModel;

  factory _AppVersionModel.fromJson(Map<String, dynamic> json) =
      _$_AppVersionModel.fromJson;

  @override
  String? get version;
  @override
  String? get packageName;
  @override
  String? get iOSId;
  @override
  @JsonKey(ignore: true)
  _$$_AppVersionModelCopyWith<_$_AppVersionModel> get copyWith =>
      throw _privateConstructorUsedError;
}

StateInfoListModel _$StateInfoListModelFromJson(Map<String, dynamic> json) {
  return _StateInfoListModel.fromJson(json);
}

/// @nodoc
mixin _$StateInfoListModel {
  String? get bannerUrl => throw _privateConstructorUsedError;
  String? get code => throw _privateConstructorUsedError;
  bool? get hasLocalisation => throw _privateConstructorUsedError;
  List<Languages>? get languages => throw _privateConstructorUsedError;
  List<LocalizationModules>? get localizationModules =>
      throw _privateConstructorUsedError;
  String? get logoUrl => throw _privateConstructorUsedError;
  String? get logoUrlWhite => throw _privateConstructorUsedError;
  String? get statelogo => throw _privateConstructorUsedError;
  String? get qrCodeURL => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $StateInfoListModelCopyWith<StateInfoListModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $StateInfoListModelCopyWith<$Res> {
  factory $StateInfoListModelCopyWith(
          StateInfoListModel value, $Res Function(StateInfoListModel) then) =
      _$StateInfoListModelCopyWithImpl<$Res, StateInfoListModel>;
  @useResult
  $Res call(
      {String? bannerUrl,
      String? code,
      bool? hasLocalisation,
      List<Languages>? languages,
      List<LocalizationModules>? localizationModules,
      String? logoUrl,
      String? logoUrlWhite,
      String? statelogo,
      String? qrCodeURL});
}

/// @nodoc
class _$StateInfoListModelCopyWithImpl<$Res, $Val extends StateInfoListModel>
    implements $StateInfoListModelCopyWith<$Res> {
  _$StateInfoListModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? bannerUrl = freezed,
    Object? code = freezed,
    Object? hasLocalisation = freezed,
    Object? languages = freezed,
    Object? localizationModules = freezed,
    Object? logoUrl = freezed,
    Object? logoUrlWhite = freezed,
    Object? statelogo = freezed,
    Object? qrCodeURL = freezed,
  }) {
    return _then(_value.copyWith(
      bannerUrl: freezed == bannerUrl
          ? _value.bannerUrl
          : bannerUrl // ignore: cast_nullable_to_non_nullable
              as String?,
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      hasLocalisation: freezed == hasLocalisation
          ? _value.hasLocalisation
          : hasLocalisation // ignore: cast_nullable_to_non_nullable
              as bool?,
      languages: freezed == languages
          ? _value.languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>?,
      localizationModules: freezed == localizationModules
          ? _value.localizationModules
          : localizationModules // ignore: cast_nullable_to_non_nullable
              as List<LocalizationModules>?,
      logoUrl: freezed == logoUrl
          ? _value.logoUrl
          : logoUrl // ignore: cast_nullable_to_non_nullable
              as String?,
      logoUrlWhite: freezed == logoUrlWhite
          ? _value.logoUrlWhite
          : logoUrlWhite // ignore: cast_nullable_to_non_nullable
              as String?,
      statelogo: freezed == statelogo
          ? _value.statelogo
          : statelogo // ignore: cast_nullable_to_non_nullable
              as String?,
      qrCodeURL: freezed == qrCodeURL
          ? _value.qrCodeURL
          : qrCodeURL // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_StateInfoListModelCopyWith<$Res>
    implements $StateInfoListModelCopyWith<$Res> {
  factory _$$_StateInfoListModelCopyWith(_$_StateInfoListModel value,
          $Res Function(_$_StateInfoListModel) then) =
      __$$_StateInfoListModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? bannerUrl,
      String? code,
      bool? hasLocalisation,
      List<Languages>? languages,
      List<LocalizationModules>? localizationModules,
      String? logoUrl,
      String? logoUrlWhite,
      String? statelogo,
      String? qrCodeURL});
}

/// @nodoc
class __$$_StateInfoListModelCopyWithImpl<$Res>
    extends _$StateInfoListModelCopyWithImpl<$Res, _$_StateInfoListModel>
    implements _$$_StateInfoListModelCopyWith<$Res> {
  __$$_StateInfoListModelCopyWithImpl(
      _$_StateInfoListModel _value, $Res Function(_$_StateInfoListModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? bannerUrl = freezed,
    Object? code = freezed,
    Object? hasLocalisation = freezed,
    Object? languages = freezed,
    Object? localizationModules = freezed,
    Object? logoUrl = freezed,
    Object? logoUrlWhite = freezed,
    Object? statelogo = freezed,
    Object? qrCodeURL = freezed,
  }) {
    return _then(_$_StateInfoListModel(
      bannerUrl: freezed == bannerUrl
          ? _value.bannerUrl
          : bannerUrl // ignore: cast_nullable_to_non_nullable
              as String?,
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      hasLocalisation: freezed == hasLocalisation
          ? _value.hasLocalisation
          : hasLocalisation // ignore: cast_nullable_to_non_nullable
              as bool?,
      languages: freezed == languages
          ? _value._languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>?,
      localizationModules: freezed == localizationModules
          ? _value._localizationModules
          : localizationModules // ignore: cast_nullable_to_non_nullable
              as List<LocalizationModules>?,
      logoUrl: freezed == logoUrl
          ? _value.logoUrl
          : logoUrl // ignore: cast_nullable_to_non_nullable
              as String?,
      logoUrlWhite: freezed == logoUrlWhite
          ? _value.logoUrlWhite
          : logoUrlWhite // ignore: cast_nullable_to_non_nullable
              as String?,
      statelogo: freezed == statelogo
          ? _value.statelogo
          : statelogo // ignore: cast_nullable_to_non_nullable
              as String?,
      qrCodeURL: freezed == qrCodeURL
          ? _value.qrCodeURL
          : qrCodeURL // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_StateInfoListModel implements _StateInfoListModel {
  const _$_StateInfoListModel(
      {this.bannerUrl,
      this.code,
      this.hasLocalisation,
      final List<Languages>? languages,
      final List<LocalizationModules>? localizationModules,
      this.logoUrl,
      this.logoUrlWhite,
      this.statelogo,
      this.qrCodeURL})
      : _languages = languages,
        _localizationModules = localizationModules;

  factory _$_StateInfoListModel.fromJson(Map<String, dynamic> json) =>
      _$$_StateInfoListModelFromJson(json);

  @override
  final String? bannerUrl;
  @override
  final String? code;
  @override
  final bool? hasLocalisation;
  final List<Languages>? _languages;
  @override
  List<Languages>? get languages {
    final value = _languages;
    if (value == null) return null;
    if (_languages is EqualUnmodifiableListView) return _languages;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<LocalizationModules>? _localizationModules;
  @override
  List<LocalizationModules>? get localizationModules {
    final value = _localizationModules;
    if (value == null) return null;
    if (_localizationModules is EqualUnmodifiableListView)
      return _localizationModules;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final String? logoUrl;
  @override
  final String? logoUrlWhite;
  @override
  final String? statelogo;
  @override
  final String? qrCodeURL;

  @override
  String toString() {
    return 'StateInfoListModel(bannerUrl: $bannerUrl, code: $code, hasLocalisation: $hasLocalisation, languages: $languages, localizationModules: $localizationModules, logoUrl: $logoUrl, logoUrlWhite: $logoUrlWhite, statelogo: $statelogo, qrCodeURL: $qrCodeURL)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_StateInfoListModel &&
            (identical(other.bannerUrl, bannerUrl) ||
                other.bannerUrl == bannerUrl) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.hasLocalisation, hasLocalisation) ||
                other.hasLocalisation == hasLocalisation) &&
            const DeepCollectionEquality()
                .equals(other._languages, _languages) &&
            const DeepCollectionEquality()
                .equals(other._localizationModules, _localizationModules) &&
            (identical(other.logoUrl, logoUrl) || other.logoUrl == logoUrl) &&
            (identical(other.logoUrlWhite, logoUrlWhite) ||
                other.logoUrlWhite == logoUrlWhite) &&
            (identical(other.statelogo, statelogo) ||
                other.statelogo == statelogo) &&
            (identical(other.qrCodeURL, qrCodeURL) ||
                other.qrCodeURL == qrCodeURL));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      bannerUrl,
      code,
      hasLocalisation,
      const DeepCollectionEquality().hash(_languages),
      const DeepCollectionEquality().hash(_localizationModules),
      logoUrl,
      logoUrlWhite,
      statelogo,
      qrCodeURL);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_StateInfoListModelCopyWith<_$_StateInfoListModel> get copyWith =>
      __$$_StateInfoListModelCopyWithImpl<_$_StateInfoListModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_StateInfoListModelToJson(
      this,
    );
  }
}

abstract class _StateInfoListModel implements StateInfoListModel {
  const factory _StateInfoListModel(
      {final String? bannerUrl,
      final String? code,
      final bool? hasLocalisation,
      final List<Languages>? languages,
      final List<LocalizationModules>? localizationModules,
      final String? logoUrl,
      final String? logoUrlWhite,
      final String? statelogo,
      final String? qrCodeURL}) = _$_StateInfoListModel;

  factory _StateInfoListModel.fromJson(Map<String, dynamic> json) =
      _$_StateInfoListModel.fromJson;

  @override
  String? get bannerUrl;
  @override
  String? get code;
  @override
  bool? get hasLocalisation;
  @override
  List<Languages>? get languages;
  @override
  List<LocalizationModules>? get localizationModules;
  @override
  String? get logoUrl;
  @override
  String? get logoUrlWhite;
  @override
  String? get statelogo;
  @override
  String? get qrCodeURL;
  @override
  @JsonKey(ignore: true)
  _$$_StateInfoListModelCopyWith<_$_StateInfoListModel> get copyWith =>
      throw _privateConstructorUsedError;
}

TenantListModel _$TenantListModelFromJson(Map<String, dynamic> json) {
  return _TenantListModel.fromJson(json);
}

/// @nodoc
mixin _$TenantListModel {
  String? get code => throw _privateConstructorUsedError;
  String? get contactNumber => throw _privateConstructorUsedError;
  String? get imageId => throw _privateConstructorUsedError;
  String? get logoId => throw _privateConstructorUsedError;
  String? get pdfContactDetails => throw _privateConstructorUsedError;
  String? get pdfHeader => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $TenantListModelCopyWith<TenantListModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TenantListModelCopyWith<$Res> {
  factory $TenantListModelCopyWith(
          TenantListModel value, $Res Function(TenantListModel) then) =
      _$TenantListModelCopyWithImpl<$Res, TenantListModel>;
  @useResult
  $Res call(
      {String? code,
      String? contactNumber,
      String? imageId,
      String? logoId,
      String? pdfContactDetails,
      String? pdfHeader});
}

/// @nodoc
class _$TenantListModelCopyWithImpl<$Res, $Val extends TenantListModel>
    implements $TenantListModelCopyWith<$Res> {
  _$TenantListModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? contactNumber = freezed,
    Object? imageId = freezed,
    Object? logoId = freezed,
    Object? pdfContactDetails = freezed,
    Object? pdfHeader = freezed,
  }) {
    return _then(_value.copyWith(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      contactNumber: freezed == contactNumber
          ? _value.contactNumber
          : contactNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      imageId: freezed == imageId
          ? _value.imageId
          : imageId // ignore: cast_nullable_to_non_nullable
              as String?,
      logoId: freezed == logoId
          ? _value.logoId
          : logoId // ignore: cast_nullable_to_non_nullable
              as String?,
      pdfContactDetails: freezed == pdfContactDetails
          ? _value.pdfContactDetails
          : pdfContactDetails // ignore: cast_nullable_to_non_nullable
              as String?,
      pdfHeader: freezed == pdfHeader
          ? _value.pdfHeader
          : pdfHeader // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_TenantListModelCopyWith<$Res>
    implements $TenantListModelCopyWith<$Res> {
  factory _$$_TenantListModelCopyWith(
          _$_TenantListModel value, $Res Function(_$_TenantListModel) then) =
      __$$_TenantListModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? code,
      String? contactNumber,
      String? imageId,
      String? logoId,
      String? pdfContactDetails,
      String? pdfHeader});
}

/// @nodoc
class __$$_TenantListModelCopyWithImpl<$Res>
    extends _$TenantListModelCopyWithImpl<$Res, _$_TenantListModel>
    implements _$$_TenantListModelCopyWith<$Res> {
  __$$_TenantListModelCopyWithImpl(
      _$_TenantListModel _value, $Res Function(_$_TenantListModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = freezed,
    Object? contactNumber = freezed,
    Object? imageId = freezed,
    Object? logoId = freezed,
    Object? pdfContactDetails = freezed,
    Object? pdfHeader = freezed,
  }) {
    return _then(_$_TenantListModel(
      code: freezed == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String?,
      contactNumber: freezed == contactNumber
          ? _value.contactNumber
          : contactNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      imageId: freezed == imageId
          ? _value.imageId
          : imageId // ignore: cast_nullable_to_non_nullable
              as String?,
      logoId: freezed == logoId
          ? _value.logoId
          : logoId // ignore: cast_nullable_to_non_nullable
              as String?,
      pdfContactDetails: freezed == pdfContactDetails
          ? _value.pdfContactDetails
          : pdfContactDetails // ignore: cast_nullable_to_non_nullable
              as String?,
      pdfHeader: freezed == pdfHeader
          ? _value.pdfHeader
          : pdfHeader // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_TenantListModel implements _TenantListModel {
  const _$_TenantListModel(
      {this.code,
      this.contactNumber,
      this.imageId,
      this.logoId,
      this.pdfContactDetails,
      this.pdfHeader});

  factory _$_TenantListModel.fromJson(Map<String, dynamic> json) =>
      _$$_TenantListModelFromJson(json);

  @override
  final String? code;
  @override
  final String? contactNumber;
  @override
  final String? imageId;
  @override
  final String? logoId;
  @override
  final String? pdfContactDetails;
  @override
  final String? pdfHeader;

  @override
  String toString() {
    return 'TenantListModel(code: $code, contactNumber: $contactNumber, imageId: $imageId, logoId: $logoId, pdfContactDetails: $pdfContactDetails, pdfHeader: $pdfHeader)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_TenantListModel &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.contactNumber, contactNumber) ||
                other.contactNumber == contactNumber) &&
            (identical(other.imageId, imageId) || other.imageId == imageId) &&
            (identical(other.logoId, logoId) || other.logoId == logoId) &&
            (identical(other.pdfContactDetails, pdfContactDetails) ||
                other.pdfContactDetails == pdfContactDetails) &&
            (identical(other.pdfHeader, pdfHeader) ||
                other.pdfHeader == pdfHeader));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code, contactNumber, imageId,
      logoId, pdfContactDetails, pdfHeader);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_TenantListModelCopyWith<_$_TenantListModel> get copyWith =>
      __$$_TenantListModelCopyWithImpl<_$_TenantListModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_TenantListModelToJson(
      this,
    );
  }
}

abstract class _TenantListModel implements TenantListModel {
  const factory _TenantListModel(
      {final String? code,
      final String? contactNumber,
      final String? imageId,
      final String? logoId,
      final String? pdfContactDetails,
      final String? pdfHeader}) = _$_TenantListModel;

  factory _TenantListModel.fromJson(Map<String, dynamic> json) =
      _$_TenantListModel.fromJson;

  @override
  String? get code;
  @override
  String? get contactNumber;
  @override
  String? get imageId;
  @override
  String? get logoId;
  @override
  String? get pdfContactDetails;
  @override
  String? get pdfHeader;
  @override
  @JsonKey(ignore: true)
  _$$_TenantListModelCopyWith<_$_TenantListModel> get copyWith =>
      throw _privateConstructorUsedError;
}

CityModuleModel _$CityModuleModelFromJson(Map<String, dynamic> json) {
  return _CityModuleModel.fromJson(json);
}

/// @nodoc
mixin _$CityModuleModel {
  bool? get active => throw _privateConstructorUsedError;
  String? get code => throw _privateConstructorUsedError;
  String? get module => throw _privateConstructorUsedError;
  int? get order => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $CityModuleModelCopyWith<CityModuleModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CityModuleModelCopyWith<$Res> {
  factory $CityModuleModelCopyWith(
          CityModuleModel value, $Res Function(CityModuleModel) then) =
      _$CityModuleModelCopyWithImpl<$Res, CityModuleModel>;
  @useResult
  $Res call({bool? active, String? code, String? module, int? order});
}

/// @nodoc
class _$CityModuleModelCopyWithImpl<$Res, $Val extends CityModuleModel>
    implements $CityModuleModelCopyWith<$Res> {
  _$CityModuleModelCopyWithImpl(this._value, this._then);

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
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_CityModuleModelCopyWith<$Res>
    implements $CityModuleModelCopyWith<$Res> {
  factory _$$_CityModuleModelCopyWith(
          _$_CityModuleModel value, $Res Function(_$_CityModuleModel) then) =
      __$$_CityModuleModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool? active, String? code, String? module, int? order});
}

/// @nodoc
class __$$_CityModuleModelCopyWithImpl<$Res>
    extends _$CityModuleModelCopyWithImpl<$Res, _$_CityModuleModel>
    implements _$$_CityModuleModelCopyWith<$Res> {
  __$$_CityModuleModelCopyWithImpl(
      _$_CityModuleModel _value, $Res Function(_$_CityModuleModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? active = freezed,
    Object? code = freezed,
    Object? module = freezed,
    Object? order = freezed,
  }) {
    return _then(_$_CityModuleModel(
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
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_CityModuleModel implements _CityModuleModel {
  const _$_CityModuleModel({this.active, this.code, this.module, this.order});

  factory _$_CityModuleModel.fromJson(Map<String, dynamic> json) =>
      _$$_CityModuleModelFromJson(json);

  @override
  final bool? active;
  @override
  final String? code;
  @override
  final String? module;
  @override
  final int? order;

  @override
  String toString() {
    return 'CityModuleModel(active: $active, code: $code, module: $module, order: $order)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CityModuleModel &&
            (identical(other.active, active) || other.active == active) &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.module, module) || other.module == module) &&
            (identical(other.order, order) || other.order == order));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, active, code, module, order);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_CityModuleModelCopyWith<_$_CityModuleModel> get copyWith =>
      __$$_CityModuleModelCopyWithImpl<_$_CityModuleModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_CityModuleModelToJson(
      this,
    );
  }
}

abstract class _CityModuleModel implements CityModuleModel {
  const factory _CityModuleModel(
      {final bool? active,
      final String? code,
      final String? module,
      final int? order}) = _$_CityModuleModel;

  factory _CityModuleModel.fromJson(Map<String, dynamic> json) =
      _$_CityModuleModel.fromJson;

  @override
  bool? get active;
  @override
  String? get code;
  @override
  String? get module;
  @override
  int? get order;
  @override
  @JsonKey(ignore: true)
  _$$_CityModuleModelCopyWith<_$_CityModuleModel> get copyWith =>
      throw _privateConstructorUsedError;
}
