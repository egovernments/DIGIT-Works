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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

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
  @JsonKey(name: 'commonUiConfig')
  CommonUIConfigModel? get commonUIConfigModel =>
      throw _privateConstructorUsedError;

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
      {@JsonKey(name: 'common-masters')
          CommonMastersModel? commonMastersModel,
      @JsonKey(name: 'tenant')
          TenantModel? tenant,
      @JsonKey(name: 'commonUiConfig')
          CommonUIConfigModel? commonUIConfigModel});

  $CommonMastersModelCopyWith<$Res>? get commonMastersModel;
  $TenantModelCopyWith<$Res>? get tenant;
  $CommonUIConfigModelCopyWith<$Res>? get commonUIConfigModel;
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
    Object? commonUIConfigModel = freezed,
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
      commonUIConfigModel: freezed == commonUIConfigModel
          ? _value.commonUIConfigModel
          : commonUIConfigModel // ignore: cast_nullable_to_non_nullable
              as CommonUIConfigModel?,
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

  @override
  @pragma('vm:prefer-inline')
  $CommonUIConfigModelCopyWith<$Res>? get commonUIConfigModel {
    if (_value.commonUIConfigModel == null) {
      return null;
    }

    return $CommonUIConfigModelCopyWith<$Res>(_value.commonUIConfigModel!,
        (value) {
      return _then(_value.copyWith(commonUIConfigModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$InitMdmsModelImplCopyWith<$Res>
    implements $InitMdmsModelCopyWith<$Res> {
  factory _$$InitMdmsModelImplCopyWith(
          _$InitMdmsModelImpl value, $Res Function(_$InitMdmsModelImpl) then) =
      __$$InitMdmsModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'common-masters')
          CommonMastersModel? commonMastersModel,
      @JsonKey(name: 'tenant')
          TenantModel? tenant,
      @JsonKey(name: 'commonUiConfig')
          CommonUIConfigModel? commonUIConfigModel});

  @override
  $CommonMastersModelCopyWith<$Res>? get commonMastersModel;
  @override
  $TenantModelCopyWith<$Res>? get tenant;
  @override
  $CommonUIConfigModelCopyWith<$Res>? get commonUIConfigModel;
}

/// @nodoc
class __$$InitMdmsModelImplCopyWithImpl<$Res>
    extends _$InitMdmsModelCopyWithImpl<$Res, _$InitMdmsModelImpl>
    implements _$$InitMdmsModelImplCopyWith<$Res> {
  __$$InitMdmsModelImplCopyWithImpl(
      _$InitMdmsModelImpl _value, $Res Function(_$InitMdmsModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonMastersModel = freezed,
    Object? tenant = freezed,
    Object? commonUIConfigModel = freezed,
  }) {
    return _then(_$InitMdmsModelImpl(
      commonMastersModel: freezed == commonMastersModel
          ? _value.commonMastersModel
          : commonMastersModel // ignore: cast_nullable_to_non_nullable
              as CommonMastersModel?,
      tenant: freezed == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as TenantModel?,
      commonUIConfigModel: freezed == commonUIConfigModel
          ? _value.commonUIConfigModel
          : commonUIConfigModel // ignore: cast_nullable_to_non_nullable
              as CommonUIConfigModel?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$InitMdmsModelImpl implements _InitMdmsModel {
  const _$InitMdmsModelImpl(
      {@JsonKey(name: 'common-masters') this.commonMastersModel,
      @JsonKey(name: 'tenant') this.tenant,
      @JsonKey(name: 'commonUiConfig') this.commonUIConfigModel});

  factory _$InitMdmsModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$InitMdmsModelImplFromJson(json);

  @override
  @JsonKey(name: 'common-masters')
  final CommonMastersModel? commonMastersModel;
  @override
  @JsonKey(name: 'tenant')
  final TenantModel? tenant;
  @override
  @JsonKey(name: 'commonUiConfig')
  final CommonUIConfigModel? commonUIConfigModel;

  @override
  String toString() {
    return 'InitMdmsModel(commonMastersModel: $commonMastersModel, tenant: $tenant, commonUIConfigModel: $commonUIConfigModel)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$InitMdmsModelImpl &&
            (identical(other.commonMastersModel, commonMastersModel) ||
                other.commonMastersModel == commonMastersModel) &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            (identical(other.commonUIConfigModel, commonUIConfigModel) ||
                other.commonUIConfigModel == commonUIConfigModel));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, commonMastersModel, tenant, commonUIConfigModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$InitMdmsModelImplCopyWith<_$InitMdmsModelImpl> get copyWith =>
      __$$InitMdmsModelImplCopyWithImpl<_$InitMdmsModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$InitMdmsModelImplToJson(
      this,
    );
  }
}

abstract class _InitMdmsModel implements InitMdmsModel {
  const factory _InitMdmsModel(
          {@JsonKey(name: 'common-masters')
              final CommonMastersModel? commonMastersModel,
          @JsonKey(name: 'tenant')
              final TenantModel? tenant,
          @JsonKey(name: 'commonUiConfig')
              final CommonUIConfigModel? commonUIConfigModel}) =
      _$InitMdmsModelImpl;

  factory _InitMdmsModel.fromJson(Map<String, dynamic> json) =
      _$InitMdmsModelImpl.fromJson;

  @override
  @JsonKey(name: 'common-masters')
  CommonMastersModel? get commonMastersModel;
  @override
  @JsonKey(name: 'tenant')
  TenantModel? get tenant;
  @override
  @JsonKey(name: 'commonUiConfig')
  CommonUIConfigModel? get commonUIConfigModel;
  @override
  @JsonKey(ignore: true)
  _$$InitMdmsModelImplCopyWith<_$InitMdmsModelImpl> get copyWith =>
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
abstract class _$$TenantModelImplCopyWith<$Res>
    implements $TenantModelCopyWith<$Res> {
  factory _$$TenantModelImplCopyWith(
          _$TenantModelImpl value, $Res Function(_$TenantModelImpl) then) =
      __$$TenantModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'tenants') List<TenantListModel>? tenantListModel,
      @JsonKey(name: 'citymodule') List<CityModuleModel>? cityModuleModel});
}

/// @nodoc
class __$$TenantModelImplCopyWithImpl<$Res>
    extends _$TenantModelCopyWithImpl<$Res, _$TenantModelImpl>
    implements _$$TenantModelImplCopyWith<$Res> {
  __$$TenantModelImplCopyWithImpl(
      _$TenantModelImpl _value, $Res Function(_$TenantModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantListModel = freezed,
    Object? cityModuleModel = freezed,
  }) {
    return _then(_$TenantModelImpl(
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
class _$TenantModelImpl implements _TenantModel {
  const _$TenantModelImpl(
      {@JsonKey(name: 'tenants')
          final List<TenantListModel>? tenantListModel,
      @JsonKey(name: 'citymodule')
          final List<CityModuleModel>? cityModuleModel})
      : _tenantListModel = tenantListModel,
        _cityModuleModel = cityModuleModel;

  factory _$TenantModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$TenantModelImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TenantModelImpl &&
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
  _$$TenantModelImplCopyWith<_$TenantModelImpl> get copyWith =>
      __$$TenantModelImplCopyWithImpl<_$TenantModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$TenantModelImplToJson(
      this,
    );
  }
}

abstract class _TenantModel implements TenantModel {
  const factory _TenantModel(
      {@JsonKey(name: 'tenants')
          final List<TenantListModel>? tenantListModel,
      @JsonKey(name: 'citymodule')
          final List<CityModuleModel>? cityModuleModel}) = _$TenantModelImpl;

  factory _TenantModel.fromJson(Map<String, dynamic> json) =
      _$TenantModelImpl.fromJson;

  @override
  @JsonKey(name: 'tenants')
  List<TenantListModel>? get tenantListModel;
  @override
  @JsonKey(name: 'citymodule')
  List<CityModuleModel>? get cityModuleModel;
  @override
  @JsonKey(ignore: true)
  _$$TenantModelImplCopyWith<_$TenantModelImpl> get copyWith =>
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
abstract class _$$CommonMastersModelImplCopyWith<$Res>
    implements $CommonMastersModelCopyWith<$Res> {
  factory _$$CommonMastersModelImplCopyWith(_$CommonMastersModelImpl value,
          $Res Function(_$CommonMastersModelImpl) then) =
      __$$CommonMastersModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'StateInfo') List<StateInfoListModel>? stateInfoListModel,
      @JsonKey(name: 'AppVersion') List<AppVersionModel>? appVersionModel});
}

/// @nodoc
class __$$CommonMastersModelImplCopyWithImpl<$Res>
    extends _$CommonMastersModelCopyWithImpl<$Res, _$CommonMastersModelImpl>
    implements _$$CommonMastersModelImplCopyWith<$Res> {
  __$$CommonMastersModelImplCopyWithImpl(_$CommonMastersModelImpl _value,
      $Res Function(_$CommonMastersModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? stateInfoListModel = freezed,
    Object? appVersionModel = freezed,
  }) {
    return _then(_$CommonMastersModelImpl(
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
class _$CommonMastersModelImpl implements _CommonMastersModel {
  const _$CommonMastersModelImpl(
      {@JsonKey(name: 'StateInfo')
          final List<StateInfoListModel>? stateInfoListModel,
      @JsonKey(name: 'AppVersion')
          final List<AppVersionModel>? appVersionModel})
      : _stateInfoListModel = stateInfoListModel,
        _appVersionModel = appVersionModel;

  factory _$CommonMastersModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$CommonMastersModelImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CommonMastersModelImpl &&
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
  _$$CommonMastersModelImplCopyWith<_$CommonMastersModelImpl> get copyWith =>
      __$$CommonMastersModelImplCopyWithImpl<_$CommonMastersModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CommonMastersModelImplToJson(
      this,
    );
  }
}

abstract class _CommonMastersModel implements CommonMastersModel {
  const factory _CommonMastersModel(
          {@JsonKey(name: 'StateInfo')
              final List<StateInfoListModel>? stateInfoListModel,
          @JsonKey(name: 'AppVersion')
              final List<AppVersionModel>? appVersionModel}) =
      _$CommonMastersModelImpl;

  factory _CommonMastersModel.fromJson(Map<String, dynamic> json) =
      _$CommonMastersModelImpl.fromJson;

  @override
  @JsonKey(name: 'StateInfo')
  List<StateInfoListModel>? get stateInfoListModel;
  @override
  @JsonKey(name: 'AppVersion')
  List<AppVersionModel>? get appVersionModel;
  @override
  @JsonKey(ignore: true)
  _$$CommonMastersModelImplCopyWith<_$CommonMastersModelImpl> get copyWith =>
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
abstract class _$$AppVersionModelImplCopyWith<$Res>
    implements $AppVersionModelCopyWith<$Res> {
  factory _$$AppVersionModelImplCopyWith(_$AppVersionModelImpl value,
          $Res Function(_$AppVersionModelImpl) then) =
      __$$AppVersionModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? version, String? packageName, String? iOSId});
}

/// @nodoc
class __$$AppVersionModelImplCopyWithImpl<$Res>
    extends _$AppVersionModelCopyWithImpl<$Res, _$AppVersionModelImpl>
    implements _$$AppVersionModelImplCopyWith<$Res> {
  __$$AppVersionModelImplCopyWithImpl(
      _$AppVersionModelImpl _value, $Res Function(_$AppVersionModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? version = freezed,
    Object? packageName = freezed,
    Object? iOSId = freezed,
  }) {
    return _then(_$AppVersionModelImpl(
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
class _$AppVersionModelImpl implements _AppVersionModel {
  const _$AppVersionModelImpl({this.version, this.packageName, this.iOSId});

  factory _$AppVersionModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$AppVersionModelImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AppVersionModelImpl &&
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
  _$$AppVersionModelImplCopyWith<_$AppVersionModelImpl> get copyWith =>
      __$$AppVersionModelImplCopyWithImpl<_$AppVersionModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AppVersionModelImplToJson(
      this,
    );
  }
}

abstract class _AppVersionModel implements AppVersionModel {
  const factory _AppVersionModel(
      {final String? version,
      final String? packageName,
      final String? iOSId}) = _$AppVersionModelImpl;

  factory _AppVersionModel.fromJson(Map<String, dynamic> json) =
      _$AppVersionModelImpl.fromJson;

  @override
  String? get version;
  @override
  String? get packageName;
  @override
  String? get iOSId;
  @override
  @JsonKey(ignore: true)
  _$$AppVersionModelImplCopyWith<_$AppVersionModelImpl> get copyWith =>
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
abstract class _$$StateInfoListModelImplCopyWith<$Res>
    implements $StateInfoListModelCopyWith<$Res> {
  factory _$$StateInfoListModelImplCopyWith(_$StateInfoListModelImpl value,
          $Res Function(_$StateInfoListModelImpl) then) =
      __$$StateInfoListModelImplCopyWithImpl<$Res>;
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
class __$$StateInfoListModelImplCopyWithImpl<$Res>
    extends _$StateInfoListModelCopyWithImpl<$Res, _$StateInfoListModelImpl>
    implements _$$StateInfoListModelImplCopyWith<$Res> {
  __$$StateInfoListModelImplCopyWithImpl(_$StateInfoListModelImpl _value,
      $Res Function(_$StateInfoListModelImpl) _then)
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
    return _then(_$StateInfoListModelImpl(
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
class _$StateInfoListModelImpl implements _StateInfoListModel {
  const _$StateInfoListModelImpl(
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

  factory _$StateInfoListModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$StateInfoListModelImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$StateInfoListModelImpl &&
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
  _$$StateInfoListModelImplCopyWith<_$StateInfoListModelImpl> get copyWith =>
      __$$StateInfoListModelImplCopyWithImpl<_$StateInfoListModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$StateInfoListModelImplToJson(
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
      final String? qrCodeURL}) = _$StateInfoListModelImpl;

  factory _StateInfoListModel.fromJson(Map<String, dynamic> json) =
      _$StateInfoListModelImpl.fromJson;

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
  _$$StateInfoListModelImplCopyWith<_$StateInfoListModelImpl> get copyWith =>
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
abstract class _$$TenantListModelImplCopyWith<$Res>
    implements $TenantListModelCopyWith<$Res> {
  factory _$$TenantListModelImplCopyWith(_$TenantListModelImpl value,
          $Res Function(_$TenantListModelImpl) then) =
      __$$TenantListModelImplCopyWithImpl<$Res>;
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
class __$$TenantListModelImplCopyWithImpl<$Res>
    extends _$TenantListModelCopyWithImpl<$Res, _$TenantListModelImpl>
    implements _$$TenantListModelImplCopyWith<$Res> {
  __$$TenantListModelImplCopyWithImpl(
      _$TenantListModelImpl _value, $Res Function(_$TenantListModelImpl) _then)
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
    return _then(_$TenantListModelImpl(
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
class _$TenantListModelImpl implements _TenantListModel {
  const _$TenantListModelImpl(
      {this.code,
      this.contactNumber,
      this.imageId,
      this.logoId,
      this.pdfContactDetails,
      this.pdfHeader});

  factory _$TenantListModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$TenantListModelImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TenantListModelImpl &&
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
  _$$TenantListModelImplCopyWith<_$TenantListModelImpl> get copyWith =>
      __$$TenantListModelImplCopyWithImpl<_$TenantListModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$TenantListModelImplToJson(
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
      final String? pdfHeader}) = _$TenantListModelImpl;

  factory _TenantListModel.fromJson(Map<String, dynamic> json) =
      _$TenantListModelImpl.fromJson;

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
  _$$TenantListModelImplCopyWith<_$TenantListModelImpl> get copyWith =>
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
abstract class _$$CityModuleModelImplCopyWith<$Res>
    implements $CityModuleModelCopyWith<$Res> {
  factory _$$CityModuleModelImplCopyWith(_$CityModuleModelImpl value,
          $Res Function(_$CityModuleModelImpl) then) =
      __$$CityModuleModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool? active, String? code, String? module, int? order});
}

/// @nodoc
class __$$CityModuleModelImplCopyWithImpl<$Res>
    extends _$CityModuleModelCopyWithImpl<$Res, _$CityModuleModelImpl>
    implements _$$CityModuleModelImplCopyWith<$Res> {
  __$$CityModuleModelImplCopyWithImpl(
      _$CityModuleModelImpl _value, $Res Function(_$CityModuleModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? active = freezed,
    Object? code = freezed,
    Object? module = freezed,
    Object? order = freezed,
  }) {
    return _then(_$CityModuleModelImpl(
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
class _$CityModuleModelImpl implements _CityModuleModel {
  const _$CityModuleModelImpl(
      {this.active, this.code, this.module, this.order});

  factory _$CityModuleModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$CityModuleModelImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CityModuleModelImpl &&
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
  _$$CityModuleModelImplCopyWith<_$CityModuleModelImpl> get copyWith =>
      __$$CityModuleModelImplCopyWithImpl<_$CityModuleModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CityModuleModelImplToJson(
      this,
    );
  }
}

abstract class _CityModuleModel implements CityModuleModel {
  const factory _CityModuleModel(
      {final bool? active,
      final String? code,
      final String? module,
      final int? order}) = _$CityModuleModelImpl;

  factory _CityModuleModel.fromJson(Map<String, dynamic> json) =
      _$CityModuleModelImpl.fromJson;

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
  _$$CityModuleModelImplCopyWith<_$CityModuleModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CommonUIConfigModel _$CommonUIConfigModelFromJson(Map<String, dynamic> json) {
  return _CommonUIConfigModel.fromJson(json);
}

/// @nodoc
mixin _$CommonUIConfigModel {
  @JsonKey(name: 'PrivacyPolicy')
  List<PrivacyPolicyModel>? get privacyPolicyModels =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $CommonUIConfigModelCopyWith<CommonUIConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CommonUIConfigModelCopyWith<$Res> {
  factory $CommonUIConfigModelCopyWith(
          CommonUIConfigModel value, $Res Function(CommonUIConfigModel) then) =
      _$CommonUIConfigModelCopyWithImpl<$Res, CommonUIConfigModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'PrivacyPolicy')
          List<PrivacyPolicyModel>? privacyPolicyModels});
}

/// @nodoc
class _$CommonUIConfigModelCopyWithImpl<$Res, $Val extends CommonUIConfigModel>
    implements $CommonUIConfigModelCopyWith<$Res> {
  _$CommonUIConfigModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? privacyPolicyModels = freezed,
  }) {
    return _then(_value.copyWith(
      privacyPolicyModels: freezed == privacyPolicyModels
          ? _value.privacyPolicyModels
          : privacyPolicyModels // ignore: cast_nullable_to_non_nullable
              as List<PrivacyPolicyModel>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CommonUIConfigModelImplCopyWith<$Res>
    implements $CommonUIConfigModelCopyWith<$Res> {
  factory _$$CommonUIConfigModelImplCopyWith(_$CommonUIConfigModelImpl value,
          $Res Function(_$CommonUIConfigModelImpl) then) =
      __$$CommonUIConfigModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'PrivacyPolicy')
          List<PrivacyPolicyModel>? privacyPolicyModels});
}

/// @nodoc
class __$$CommonUIConfigModelImplCopyWithImpl<$Res>
    extends _$CommonUIConfigModelCopyWithImpl<$Res, _$CommonUIConfigModelImpl>
    implements _$$CommonUIConfigModelImplCopyWith<$Res> {
  __$$CommonUIConfigModelImplCopyWithImpl(_$CommonUIConfigModelImpl _value,
      $Res Function(_$CommonUIConfigModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? privacyPolicyModels = freezed,
  }) {
    return _then(_$CommonUIConfigModelImpl(
      privacyPolicyModels: freezed == privacyPolicyModels
          ? _value._privacyPolicyModels
          : privacyPolicyModels // ignore: cast_nullable_to_non_nullable
              as List<PrivacyPolicyModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$CommonUIConfigModelImpl implements _CommonUIConfigModel {
  const _$CommonUIConfigModelImpl(
      {@JsonKey(name: 'PrivacyPolicy')
          final List<PrivacyPolicyModel>? privacyPolicyModels})
      : _privacyPolicyModels = privacyPolicyModels;

  factory _$CommonUIConfigModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$CommonUIConfigModelImplFromJson(json);

  final List<PrivacyPolicyModel>? _privacyPolicyModels;
  @override
  @JsonKey(name: 'PrivacyPolicy')
  List<PrivacyPolicyModel>? get privacyPolicyModels {
    final value = _privacyPolicyModels;
    if (value == null) return null;
    if (_privacyPolicyModels is EqualUnmodifiableListView)
      return _privacyPolicyModels;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'CommonUIConfigModel(privacyPolicyModels: $privacyPolicyModels)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CommonUIConfigModelImpl &&
            const DeepCollectionEquality()
                .equals(other._privacyPolicyModels, _privacyPolicyModels));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_privacyPolicyModels));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CommonUIConfigModelImplCopyWith<_$CommonUIConfigModelImpl> get copyWith =>
      __$$CommonUIConfigModelImplCopyWithImpl<_$CommonUIConfigModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CommonUIConfigModelImplToJson(
      this,
    );
  }
}

abstract class _CommonUIConfigModel implements CommonUIConfigModel {
  const factory _CommonUIConfigModel(
          {@JsonKey(name: 'PrivacyPolicy')
              final List<PrivacyPolicyModel>? privacyPolicyModels}) =
      _$CommonUIConfigModelImpl;

  factory _CommonUIConfigModel.fromJson(Map<String, dynamic> json) =
      _$CommonUIConfigModelImpl.fromJson;

  @override
  @JsonKey(name: 'PrivacyPolicy')
  List<PrivacyPolicyModel>? get privacyPolicyModels;
  @override
  @JsonKey(ignore: true)
  _$$CommonUIConfigModelImplCopyWith<_$CommonUIConfigModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

PrivacyPolicyModel _$PrivacyPolicyModelFromJson(Map<String, dynamic> json) {
  return _PrivacyPolicyModel.fromJson(json);
}

/// @nodoc
mixin _$PrivacyPolicyModel {
  String? get header => throw _privateConstructorUsedError;
  bool? get active => throw _privateConstructorUsedError;
  String? get module => throw _privateConstructorUsedError;
  List<ContentModel>? get contents => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $PrivacyPolicyModelCopyWith<PrivacyPolicyModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $PrivacyPolicyModelCopyWith<$Res> {
  factory $PrivacyPolicyModelCopyWith(
          PrivacyPolicyModel value, $Res Function(PrivacyPolicyModel) then) =
      _$PrivacyPolicyModelCopyWithImpl<$Res, PrivacyPolicyModel>;
  @useResult
  $Res call(
      {String? header,
      bool? active,
      String? module,
      List<ContentModel>? contents});
}

/// @nodoc
class _$PrivacyPolicyModelCopyWithImpl<$Res, $Val extends PrivacyPolicyModel>
    implements $PrivacyPolicyModelCopyWith<$Res> {
  _$PrivacyPolicyModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? header = freezed,
    Object? active = freezed,
    Object? module = freezed,
    Object? contents = freezed,
  }) {
    return _then(_value.copyWith(
      header: freezed == header
          ? _value.header
          : header // ignore: cast_nullable_to_non_nullable
              as String?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
      module: freezed == module
          ? _value.module
          : module // ignore: cast_nullable_to_non_nullable
              as String?,
      contents: freezed == contents
          ? _value.contents
          : contents // ignore: cast_nullable_to_non_nullable
              as List<ContentModel>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$PrivacyPolicyModelImplCopyWith<$Res>
    implements $PrivacyPolicyModelCopyWith<$Res> {
  factory _$$PrivacyPolicyModelImplCopyWith(_$PrivacyPolicyModelImpl value,
          $Res Function(_$PrivacyPolicyModelImpl) then) =
      __$$PrivacyPolicyModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? header,
      bool? active,
      String? module,
      List<ContentModel>? contents});
}

/// @nodoc
class __$$PrivacyPolicyModelImplCopyWithImpl<$Res>
    extends _$PrivacyPolicyModelCopyWithImpl<$Res, _$PrivacyPolicyModelImpl>
    implements _$$PrivacyPolicyModelImplCopyWith<$Res> {
  __$$PrivacyPolicyModelImplCopyWithImpl(_$PrivacyPolicyModelImpl _value,
      $Res Function(_$PrivacyPolicyModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? header = freezed,
    Object? active = freezed,
    Object? module = freezed,
    Object? contents = freezed,
  }) {
    return _then(_$PrivacyPolicyModelImpl(
      header: freezed == header
          ? _value.header
          : header // ignore: cast_nullable_to_non_nullable
              as String?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
      module: freezed == module
          ? _value.module
          : module // ignore: cast_nullable_to_non_nullable
              as String?,
      contents: freezed == contents
          ? _value._contents
          : contents // ignore: cast_nullable_to_non_nullable
              as List<ContentModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$PrivacyPolicyModelImpl implements _PrivacyPolicyModel {
  const _$PrivacyPolicyModelImpl(
      {this.header,
      this.active,
      this.module,
      final List<ContentModel>? contents})
      : _contents = contents;

  factory _$PrivacyPolicyModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$PrivacyPolicyModelImplFromJson(json);

  @override
  final String? header;
  @override
  final bool? active;
  @override
  final String? module;
  final List<ContentModel>? _contents;
  @override
  List<ContentModel>? get contents {
    final value = _contents;
    if (value == null) return null;
    if (_contents is EqualUnmodifiableListView) return _contents;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'PrivacyPolicyModel(header: $header, active: $active, module: $module, contents: $contents)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$PrivacyPolicyModelImpl &&
            (identical(other.header, header) || other.header == header) &&
            (identical(other.active, active) || other.active == active) &&
            (identical(other.module, module) || other.module == module) &&
            const DeepCollectionEquality().equals(other._contents, _contents));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, header, active, module,
      const DeepCollectionEquality().hash(_contents));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$PrivacyPolicyModelImplCopyWith<_$PrivacyPolicyModelImpl> get copyWith =>
      __$$PrivacyPolicyModelImplCopyWithImpl<_$PrivacyPolicyModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$PrivacyPolicyModelImplToJson(
      this,
    );
  }
}

abstract class _PrivacyPolicyModel implements PrivacyPolicyModel {
  const factory _PrivacyPolicyModel(
      {final String? header,
      final bool? active,
      final String? module,
      final List<ContentModel>? contents}) = _$PrivacyPolicyModelImpl;

  factory _PrivacyPolicyModel.fromJson(Map<String, dynamic> json) =
      _$PrivacyPolicyModelImpl.fromJson;

  @override
  String? get header;
  @override
  bool? get active;
  @override
  String? get module;
  @override
  List<ContentModel>? get contents;
  @override
  @JsonKey(ignore: true)
  _$$PrivacyPolicyModelImplCopyWith<_$PrivacyPolicyModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

ContentModel _$ContentModelFromJson(Map<String, dynamic> json) {
  return _ContentModel.fromJson(json);
}

/// @nodoc
mixin _$ContentModel {
  String? get header => throw _privateConstructorUsedError;
  List<DescriptionModel>? get descriptions =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ContentModelCopyWith<ContentModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ContentModelCopyWith<$Res> {
  factory $ContentModelCopyWith(
          ContentModel value, $Res Function(ContentModel) then) =
      _$ContentModelCopyWithImpl<$Res, ContentModel>;
  @useResult
  $Res call({String? header, List<DescriptionModel>? descriptions});
}

/// @nodoc
class _$ContentModelCopyWithImpl<$Res, $Val extends ContentModel>
    implements $ContentModelCopyWith<$Res> {
  _$ContentModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? header = freezed,
    Object? descriptions = freezed,
  }) {
    return _then(_value.copyWith(
      header: freezed == header
          ? _value.header
          : header // ignore: cast_nullable_to_non_nullable
              as String?,
      descriptions: freezed == descriptions
          ? _value.descriptions
          : descriptions // ignore: cast_nullable_to_non_nullable
              as List<DescriptionModel>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$ContentModelImplCopyWith<$Res>
    implements $ContentModelCopyWith<$Res> {
  factory _$$ContentModelImplCopyWith(
          _$ContentModelImpl value, $Res Function(_$ContentModelImpl) then) =
      __$$ContentModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? header, List<DescriptionModel>? descriptions});
}

/// @nodoc
class __$$ContentModelImplCopyWithImpl<$Res>
    extends _$ContentModelCopyWithImpl<$Res, _$ContentModelImpl>
    implements _$$ContentModelImplCopyWith<$Res> {
  __$$ContentModelImplCopyWithImpl(
      _$ContentModelImpl _value, $Res Function(_$ContentModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? header = freezed,
    Object? descriptions = freezed,
  }) {
    return _then(_$ContentModelImpl(
      header: freezed == header
          ? _value.header
          : header // ignore: cast_nullable_to_non_nullable
              as String?,
      descriptions: freezed == descriptions
          ? _value._descriptions
          : descriptions // ignore: cast_nullable_to_non_nullable
              as List<DescriptionModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$ContentModelImpl implements _ContentModel {
  const _$ContentModelImpl(
      {this.header, final List<DescriptionModel>? descriptions})
      : _descriptions = descriptions;

  factory _$ContentModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$ContentModelImplFromJson(json);

  @override
  final String? header;
  final List<DescriptionModel>? _descriptions;
  @override
  List<DescriptionModel>? get descriptions {
    final value = _descriptions;
    if (value == null) return null;
    if (_descriptions is EqualUnmodifiableListView) return _descriptions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'ContentModel(header: $header, descriptions: $descriptions)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ContentModelImpl &&
            (identical(other.header, header) || other.header == header) &&
            const DeepCollectionEquality()
                .equals(other._descriptions, _descriptions));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, header, const DeepCollectionEquality().hash(_descriptions));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ContentModelImplCopyWith<_$ContentModelImpl> get copyWith =>
      __$$ContentModelImplCopyWithImpl<_$ContentModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$ContentModelImplToJson(
      this,
    );
  }
}

abstract class _ContentModel implements ContentModel {
  const factory _ContentModel(
      {final String? header,
      final List<DescriptionModel>? descriptions}) = _$ContentModelImpl;

  factory _ContentModel.fromJson(Map<String, dynamic> json) =
      _$ContentModelImpl.fromJson;

  @override
  String? get header;
  @override
  List<DescriptionModel>? get descriptions;
  @override
  @JsonKey(ignore: true)
  _$$ContentModelImplCopyWith<_$ContentModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

DescriptionModel _$DescriptionModelFromJson(Map<String, dynamic> json) {
  return _DescriptionModel.fromJson(json);
}

/// @nodoc
mixin _$DescriptionModel {
  String? get text => throw _privateConstructorUsedError;
  String? get type => throw _privateConstructorUsedError;
  bool? get isBold => throw _privateConstructorUsedError;
  List<SubDescriptionModel>? get subDescriptions =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $DescriptionModelCopyWith<DescriptionModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DescriptionModelCopyWith<$Res> {
  factory $DescriptionModelCopyWith(
          DescriptionModel value, $Res Function(DescriptionModel) then) =
      _$DescriptionModelCopyWithImpl<$Res, DescriptionModel>;
  @useResult
  $Res call(
      {String? text,
      String? type,
      bool? isBold,
      List<SubDescriptionModel>? subDescriptions});
}

/// @nodoc
class _$DescriptionModelCopyWithImpl<$Res, $Val extends DescriptionModel>
    implements $DescriptionModelCopyWith<$Res> {
  _$DescriptionModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? text = freezed,
    Object? type = freezed,
    Object? isBold = freezed,
    Object? subDescriptions = freezed,
  }) {
    return _then(_value.copyWith(
      text: freezed == text
          ? _value.text
          : text // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      isBold: freezed == isBold
          ? _value.isBold
          : isBold // ignore: cast_nullable_to_non_nullable
              as bool?,
      subDescriptions: freezed == subDescriptions
          ? _value.subDescriptions
          : subDescriptions // ignore: cast_nullable_to_non_nullable
              as List<SubDescriptionModel>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$DescriptionModelImplCopyWith<$Res>
    implements $DescriptionModelCopyWith<$Res> {
  factory _$$DescriptionModelImplCopyWith(_$DescriptionModelImpl value,
          $Res Function(_$DescriptionModelImpl) then) =
      __$$DescriptionModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? text,
      String? type,
      bool? isBold,
      List<SubDescriptionModel>? subDescriptions});
}

/// @nodoc
class __$$DescriptionModelImplCopyWithImpl<$Res>
    extends _$DescriptionModelCopyWithImpl<$Res, _$DescriptionModelImpl>
    implements _$$DescriptionModelImplCopyWith<$Res> {
  __$$DescriptionModelImplCopyWithImpl(_$DescriptionModelImpl _value,
      $Res Function(_$DescriptionModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? text = freezed,
    Object? type = freezed,
    Object? isBold = freezed,
    Object? subDescriptions = freezed,
  }) {
    return _then(_$DescriptionModelImpl(
      text: freezed == text
          ? _value.text
          : text // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      isBold: freezed == isBold
          ? _value.isBold
          : isBold // ignore: cast_nullable_to_non_nullable
              as bool?,
      subDescriptions: freezed == subDescriptions
          ? _value._subDescriptions
          : subDescriptions // ignore: cast_nullable_to_non_nullable
              as List<SubDescriptionModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$DescriptionModelImpl implements _DescriptionModel {
  const _$DescriptionModelImpl(
      {this.text,
      this.type,
      this.isBold,
      final List<SubDescriptionModel>? subDescriptions})
      : _subDescriptions = subDescriptions;

  factory _$DescriptionModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$DescriptionModelImplFromJson(json);

  @override
  final String? text;
  @override
  final String? type;
  @override
  final bool? isBold;
  final List<SubDescriptionModel>? _subDescriptions;
  @override
  List<SubDescriptionModel>? get subDescriptions {
    final value = _subDescriptions;
    if (value == null) return null;
    if (_subDescriptions is EqualUnmodifiableListView) return _subDescriptions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'DescriptionModel(text: $text, type: $type, isBold: $isBold, subDescriptions: $subDescriptions)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DescriptionModelImpl &&
            (identical(other.text, text) || other.text == text) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.isBold, isBold) || other.isBold == isBold) &&
            const DeepCollectionEquality()
                .equals(other._subDescriptions, _subDescriptions));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, text, type, isBold,
      const DeepCollectionEquality().hash(_subDescriptions));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$DescriptionModelImplCopyWith<_$DescriptionModelImpl> get copyWith =>
      __$$DescriptionModelImplCopyWithImpl<_$DescriptionModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$DescriptionModelImplToJson(
      this,
    );
  }
}

abstract class _DescriptionModel implements DescriptionModel {
  const factory _DescriptionModel(
          {final String? text,
          final String? type,
          final bool? isBold,
          final List<SubDescriptionModel>? subDescriptions}) =
      _$DescriptionModelImpl;

  factory _DescriptionModel.fromJson(Map<String, dynamic> json) =
      _$DescriptionModelImpl.fromJson;

  @override
  String? get text;
  @override
  String? get type;
  @override
  bool? get isBold;
  @override
  List<SubDescriptionModel>? get subDescriptions;
  @override
  @JsonKey(ignore: true)
  _$$DescriptionModelImplCopyWith<_$DescriptionModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

SubDescriptionModel _$SubDescriptionModelFromJson(Map<String, dynamic> json) {
  return _SubDescriptionModel.fromJson(json);
}

/// @nodoc
mixin _$SubDescriptionModel {
  String? get text => throw _privateConstructorUsedError;
  String? get type => throw _privateConstructorUsedError;
  bool? get isBold => throw _privateConstructorUsedError;
  bool? get isSpaceRequired => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $SubDescriptionModelCopyWith<SubDescriptionModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $SubDescriptionModelCopyWith<$Res> {
  factory $SubDescriptionModelCopyWith(
          SubDescriptionModel value, $Res Function(SubDescriptionModel) then) =
      _$SubDescriptionModelCopyWithImpl<$Res, SubDescriptionModel>;
  @useResult
  $Res call({String? text, String? type, bool? isBold, bool? isSpaceRequired});
}

/// @nodoc
class _$SubDescriptionModelCopyWithImpl<$Res, $Val extends SubDescriptionModel>
    implements $SubDescriptionModelCopyWith<$Res> {
  _$SubDescriptionModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? text = freezed,
    Object? type = freezed,
    Object? isBold = freezed,
    Object? isSpaceRequired = freezed,
  }) {
    return _then(_value.copyWith(
      text: freezed == text
          ? _value.text
          : text // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      isBold: freezed == isBold
          ? _value.isBold
          : isBold // ignore: cast_nullable_to_non_nullable
              as bool?,
      isSpaceRequired: freezed == isSpaceRequired
          ? _value.isSpaceRequired
          : isSpaceRequired // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$SubDescriptionModelImplCopyWith<$Res>
    implements $SubDescriptionModelCopyWith<$Res> {
  factory _$$SubDescriptionModelImplCopyWith(_$SubDescriptionModelImpl value,
          $Res Function(_$SubDescriptionModelImpl) then) =
      __$$SubDescriptionModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? text, String? type, bool? isBold, bool? isSpaceRequired});
}

/// @nodoc
class __$$SubDescriptionModelImplCopyWithImpl<$Res>
    extends _$SubDescriptionModelCopyWithImpl<$Res, _$SubDescriptionModelImpl>
    implements _$$SubDescriptionModelImplCopyWith<$Res> {
  __$$SubDescriptionModelImplCopyWithImpl(_$SubDescriptionModelImpl _value,
      $Res Function(_$SubDescriptionModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? text = freezed,
    Object? type = freezed,
    Object? isBold = freezed,
    Object? isSpaceRequired = freezed,
  }) {
    return _then(_$SubDescriptionModelImpl(
      text: freezed == text
          ? _value.text
          : text // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      isBold: freezed == isBold
          ? _value.isBold
          : isBold // ignore: cast_nullable_to_non_nullable
              as bool?,
      isSpaceRequired: freezed == isSpaceRequired
          ? _value.isSpaceRequired
          : isSpaceRequired // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$SubDescriptionModelImpl implements _SubDescriptionModel {
  const _$SubDescriptionModelImpl(
      {this.text, this.type, this.isBold, this.isSpaceRequired});

  factory _$SubDescriptionModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$SubDescriptionModelImplFromJson(json);

  @override
  final String? text;
  @override
  final String? type;
  @override
  final bool? isBold;
  @override
  final bool? isSpaceRequired;

  @override
  String toString() {
    return 'SubDescriptionModel(text: $text, type: $type, isBold: $isBold, isSpaceRequired: $isSpaceRequired)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SubDescriptionModelImpl &&
            (identical(other.text, text) || other.text == text) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.isBold, isBold) || other.isBold == isBold) &&
            (identical(other.isSpaceRequired, isSpaceRequired) ||
                other.isSpaceRequired == isSpaceRequired));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, text, type, isBold, isSpaceRequired);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SubDescriptionModelImplCopyWith<_$SubDescriptionModelImpl> get copyWith =>
      __$$SubDescriptionModelImplCopyWithImpl<_$SubDescriptionModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$SubDescriptionModelImplToJson(
      this,
    );
  }
}

abstract class _SubDescriptionModel implements SubDescriptionModel {
  const factory _SubDescriptionModel(
      {final String? text,
      final String? type,
      final bool? isBold,
      final bool? isSpaceRequired}) = _$SubDescriptionModelImpl;

  factory _SubDescriptionModel.fromJson(Map<String, dynamic> json) =
      _$SubDescriptionModelImpl.fromJson;

  @override
  String? get text;
  @override
  String? get type;
  @override
  bool? get isBold;
  @override
  bool? get isSpaceRequired;
  @override
  @JsonKey(ignore: true)
  _$$SubDescriptionModelImplCopyWith<_$SubDescriptionModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
