// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'home_screen_config.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

HomeScreenConfigModel _$HomeScreenConfigModelFromJson(
    Map<String, dynamic> json) {
  return _HomeScreenConfigModel.fromJson(json);
}

/// @nodoc
mixin _$HomeScreenConfigModel {
  @JsonKey(name: 'commonUiConfig')
  CommonUIConfigModel? get commonUiConfig => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $HomeScreenConfigModelCopyWith<HomeScreenConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $HomeScreenConfigModelCopyWith<$Res> {
  factory $HomeScreenConfigModelCopyWith(HomeScreenConfigModel value,
          $Res Function(HomeScreenConfigModel) then) =
      _$HomeScreenConfigModelCopyWithImpl<$Res, HomeScreenConfigModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'commonUiConfig') CommonUIConfigModel? commonUiConfig});

  $CommonUIConfigModelCopyWith<$Res>? get commonUiConfig;
}

/// @nodoc
class _$HomeScreenConfigModelCopyWithImpl<$Res,
        $Val extends HomeScreenConfigModel>
    implements $HomeScreenConfigModelCopyWith<$Res> {
  _$HomeScreenConfigModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonUiConfig = freezed,
  }) {
    return _then(_value.copyWith(
      commonUiConfig: freezed == commonUiConfig
          ? _value.commonUiConfig
          : commonUiConfig // ignore: cast_nullable_to_non_nullable
              as CommonUIConfigModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $CommonUIConfigModelCopyWith<$Res>? get commonUiConfig {
    if (_value.commonUiConfig == null) {
      return null;
    }

    return $CommonUIConfigModelCopyWith<$Res>(_value.commonUiConfig!, (value) {
      return _then(_value.copyWith(commonUiConfig: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$HomeScreenConfigModelImplCopyWith<$Res>
    implements $HomeScreenConfigModelCopyWith<$Res> {
  factory _$$HomeScreenConfigModelImplCopyWith(
          _$HomeScreenConfigModelImpl value,
          $Res Function(_$HomeScreenConfigModelImpl) then) =
      __$$HomeScreenConfigModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'commonUiConfig') CommonUIConfigModel? commonUiConfig});

  @override
  $CommonUIConfigModelCopyWith<$Res>? get commonUiConfig;
}

/// @nodoc
class __$$HomeScreenConfigModelImplCopyWithImpl<$Res>
    extends _$HomeScreenConfigModelCopyWithImpl<$Res,
        _$HomeScreenConfigModelImpl>
    implements _$$HomeScreenConfigModelImplCopyWith<$Res> {
  __$$HomeScreenConfigModelImplCopyWithImpl(_$HomeScreenConfigModelImpl _value,
      $Res Function(_$HomeScreenConfigModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonUiConfig = freezed,
  }) {
    return _then(_$HomeScreenConfigModelImpl(
      commonUiConfig: freezed == commonUiConfig
          ? _value.commonUiConfig
          : commonUiConfig // ignore: cast_nullable_to_non_nullable
              as CommonUIConfigModel?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$HomeScreenConfigModelImpl implements _HomeScreenConfigModel {
  const _$HomeScreenConfigModelImpl(
      {@JsonKey(name: 'commonUiConfig') this.commonUiConfig});

  factory _$HomeScreenConfigModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$HomeScreenConfigModelImplFromJson(json);

  @override
  @JsonKey(name: 'commonUiConfig')
  final CommonUIConfigModel? commonUiConfig;

  @override
  String toString() {
    return 'HomeScreenConfigModel(commonUiConfig: $commonUiConfig)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$HomeScreenConfigModelImpl &&
            (identical(other.commonUiConfig, commonUiConfig) ||
                other.commonUiConfig == commonUiConfig));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, commonUiConfig);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$HomeScreenConfigModelImplCopyWith<_$HomeScreenConfigModelImpl>
      get copyWith => __$$HomeScreenConfigModelImplCopyWithImpl<
          _$HomeScreenConfigModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$HomeScreenConfigModelImplToJson(
      this,
    );
  }
}

abstract class _HomeScreenConfigModel implements HomeScreenConfigModel {
  const factory _HomeScreenConfigModel(
          {@JsonKey(name: 'commonUiConfig')
              final CommonUIConfigModel? commonUiConfig}) =
      _$HomeScreenConfigModelImpl;

  factory _HomeScreenConfigModel.fromJson(Map<String, dynamic> json) =
      _$HomeScreenConfigModelImpl.fromJson;

  @override
  @JsonKey(name: 'commonUiConfig')
  CommonUIConfigModel? get commonUiConfig;
  @override
  @JsonKey(ignore: true)
  _$$HomeScreenConfigModelImplCopyWith<_$HomeScreenConfigModelImpl>
      get copyWith => throw _privateConstructorUsedError;
}

CommonUIConfigModel _$CommonUIConfigModelFromJson(Map<String, dynamic> json) {
  return _CommonUIConfigModel.fromJson(json);
}

/// @nodoc
mixin _$CommonUIConfigModel {
  @JsonKey(name: 'CBOHomeScreenConfig')
  List<CBOHomeScreenConfigModel>? get cboHomeScreenConfig =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'CBOMyWorks')
  List<CBOMyWorksSearchCriteriaModel>? get cboMyWorksSearchCriteria =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'CBOMyServiceRequests')
  List<CBOMyServiceRequestsConfig>? get cboMyServiceRequestsConfig =>
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
      {@JsonKey(name: 'CBOHomeScreenConfig')
          List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
      @JsonKey(name: 'CBOMyWorks')
          List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria,
      @JsonKey(name: 'CBOMyServiceRequests')
          List<CBOMyServiceRequestsConfig>? cboMyServiceRequestsConfig});
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
    Object? cboHomeScreenConfig = freezed,
    Object? cboMyWorksSearchCriteria = freezed,
    Object? cboMyServiceRequestsConfig = freezed,
  }) {
    return _then(_value.copyWith(
      cboHomeScreenConfig: freezed == cboHomeScreenConfig
          ? _value.cboHomeScreenConfig
          : cboHomeScreenConfig // ignore: cast_nullable_to_non_nullable
              as List<CBOHomeScreenConfigModel>?,
      cboMyWorksSearchCriteria: freezed == cboMyWorksSearchCriteria
          ? _value.cboMyWorksSearchCriteria
          : cboMyWorksSearchCriteria // ignore: cast_nullable_to_non_nullable
              as List<CBOMyWorksSearchCriteriaModel>?,
      cboMyServiceRequestsConfig: freezed == cboMyServiceRequestsConfig
          ? _value.cboMyServiceRequestsConfig
          : cboMyServiceRequestsConfig // ignore: cast_nullable_to_non_nullable
              as List<CBOMyServiceRequestsConfig>?,
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
      {@JsonKey(name: 'CBOHomeScreenConfig')
          List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
      @JsonKey(name: 'CBOMyWorks')
          List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria,
      @JsonKey(name: 'CBOMyServiceRequests')
          List<CBOMyServiceRequestsConfig>? cboMyServiceRequestsConfig});
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
    Object? cboHomeScreenConfig = freezed,
    Object? cboMyWorksSearchCriteria = freezed,
    Object? cboMyServiceRequestsConfig = freezed,
  }) {
    return _then(_$CommonUIConfigModelImpl(
      cboHomeScreenConfig: freezed == cboHomeScreenConfig
          ? _value._cboHomeScreenConfig
          : cboHomeScreenConfig // ignore: cast_nullable_to_non_nullable
              as List<CBOHomeScreenConfigModel>?,
      cboMyWorksSearchCriteria: freezed == cboMyWorksSearchCriteria
          ? _value._cboMyWorksSearchCriteria
          : cboMyWorksSearchCriteria // ignore: cast_nullable_to_non_nullable
              as List<CBOMyWorksSearchCriteriaModel>?,
      cboMyServiceRequestsConfig: freezed == cboMyServiceRequestsConfig
          ? _value._cboMyServiceRequestsConfig
          : cboMyServiceRequestsConfig // ignore: cast_nullable_to_non_nullable
              as List<CBOMyServiceRequestsConfig>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$CommonUIConfigModelImpl implements _CommonUIConfigModel {
  const _$CommonUIConfigModelImpl(
      {@JsonKey(name: 'CBOHomeScreenConfig')
          final List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
      @JsonKey(name: 'CBOMyWorks')
          final List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria,
      @JsonKey(name: 'CBOMyServiceRequests')
          final List<CBOMyServiceRequestsConfig>? cboMyServiceRequestsConfig})
      : _cboHomeScreenConfig = cboHomeScreenConfig,
        _cboMyWorksSearchCriteria = cboMyWorksSearchCriteria,
        _cboMyServiceRequestsConfig = cboMyServiceRequestsConfig;

  factory _$CommonUIConfigModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$CommonUIConfigModelImplFromJson(json);

  final List<CBOHomeScreenConfigModel>? _cboHomeScreenConfig;
  @override
  @JsonKey(name: 'CBOHomeScreenConfig')
  List<CBOHomeScreenConfigModel>? get cboHomeScreenConfig {
    final value = _cboHomeScreenConfig;
    if (value == null) return null;
    if (_cboHomeScreenConfig is EqualUnmodifiableListView)
      return _cboHomeScreenConfig;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<CBOMyWorksSearchCriteriaModel>? _cboMyWorksSearchCriteria;
  @override
  @JsonKey(name: 'CBOMyWorks')
  List<CBOMyWorksSearchCriteriaModel>? get cboMyWorksSearchCriteria {
    final value = _cboMyWorksSearchCriteria;
    if (value == null) return null;
    if (_cboMyWorksSearchCriteria is EqualUnmodifiableListView)
      return _cboMyWorksSearchCriteria;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<CBOMyServiceRequestsConfig>? _cboMyServiceRequestsConfig;
  @override
  @JsonKey(name: 'CBOMyServiceRequests')
  List<CBOMyServiceRequestsConfig>? get cboMyServiceRequestsConfig {
    final value = _cboMyServiceRequestsConfig;
    if (value == null) return null;
    if (_cboMyServiceRequestsConfig is EqualUnmodifiableListView)
      return _cboMyServiceRequestsConfig;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'CommonUIConfigModel(cboHomeScreenConfig: $cboHomeScreenConfig, cboMyWorksSearchCriteria: $cboMyWorksSearchCriteria, cboMyServiceRequestsConfig: $cboMyServiceRequestsConfig)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CommonUIConfigModelImpl &&
            const DeepCollectionEquality()
                .equals(other._cboHomeScreenConfig, _cboHomeScreenConfig) &&
            const DeepCollectionEquality().equals(
                other._cboMyWorksSearchCriteria, _cboMyWorksSearchCriteria) &&
            const DeepCollectionEquality().equals(
                other._cboMyServiceRequestsConfig,
                _cboMyServiceRequestsConfig));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_cboHomeScreenConfig),
      const DeepCollectionEquality().hash(_cboMyWorksSearchCriteria),
      const DeepCollectionEquality().hash(_cboMyServiceRequestsConfig));

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
      {@JsonKey(name: 'CBOHomeScreenConfig')
          final List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
      @JsonKey(name: 'CBOMyWorks')
          final List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria,
      @JsonKey(name: 'CBOMyServiceRequests')
          final List<CBOMyServiceRequestsConfig>?
              cboMyServiceRequestsConfig}) = _$CommonUIConfigModelImpl;

  factory _CommonUIConfigModel.fromJson(Map<String, dynamic> json) =
      _$CommonUIConfigModelImpl.fromJson;

  @override
  @JsonKey(name: 'CBOHomeScreenConfig')
  List<CBOHomeScreenConfigModel>? get cboHomeScreenConfig;
  @override
  @JsonKey(name: 'CBOMyWorks')
  List<CBOMyWorksSearchCriteriaModel>? get cboMyWorksSearchCriteria;
  @override
  @JsonKey(name: 'CBOMyServiceRequests')
  List<CBOMyServiceRequestsConfig>? get cboMyServiceRequestsConfig;
  @override
  @JsonKey(ignore: true)
  _$$CommonUIConfigModelImplCopyWith<_$CommonUIConfigModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CBOHomeScreenConfigModel _$CBOHomeScreenConfigModelFromJson(
    Map<String, dynamic> json) {
  return _CBOHomeScreenConfigModel.fromJson(json);
}

/// @nodoc
mixin _$CBOHomeScreenConfigModel {
  int get order => throw _privateConstructorUsedError;
  String get key => throw _privateConstructorUsedError;
  String? get displayName => throw _privateConstructorUsedError;
  String? get label => throw _privateConstructorUsedError;
  bool? get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $CBOHomeScreenConfigModelCopyWith<CBOHomeScreenConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CBOHomeScreenConfigModelCopyWith<$Res> {
  factory $CBOHomeScreenConfigModelCopyWith(CBOHomeScreenConfigModel value,
          $Res Function(CBOHomeScreenConfigModel) then) =
      _$CBOHomeScreenConfigModelCopyWithImpl<$Res, CBOHomeScreenConfigModel>;
  @useResult
  $Res call(
      {int order,
      String key,
      String? displayName,
      String? label,
      bool? active});
}

/// @nodoc
class _$CBOHomeScreenConfigModelCopyWithImpl<$Res,
        $Val extends CBOHomeScreenConfigModel>
    implements $CBOHomeScreenConfigModelCopyWith<$Res> {
  _$CBOHomeScreenConfigModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? order = null,
    Object? key = null,
    Object? displayName = freezed,
    Object? label = freezed,
    Object? active = freezed,
  }) {
    return _then(_value.copyWith(
      order: null == order
          ? _value.order
          : order // ignore: cast_nullable_to_non_nullable
              as int,
      key: null == key
          ? _value.key
          : key // ignore: cast_nullable_to_non_nullable
              as String,
      displayName: freezed == displayName
          ? _value.displayName
          : displayName // ignore: cast_nullable_to_non_nullable
              as String?,
      label: freezed == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CBOHomeScreenConfigModelImplCopyWith<$Res>
    implements $CBOHomeScreenConfigModelCopyWith<$Res> {
  factory _$$CBOHomeScreenConfigModelImplCopyWith(
          _$CBOHomeScreenConfigModelImpl value,
          $Res Function(_$CBOHomeScreenConfigModelImpl) then) =
      __$$CBOHomeScreenConfigModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {int order,
      String key,
      String? displayName,
      String? label,
      bool? active});
}

/// @nodoc
class __$$CBOHomeScreenConfigModelImplCopyWithImpl<$Res>
    extends _$CBOHomeScreenConfigModelCopyWithImpl<$Res,
        _$CBOHomeScreenConfigModelImpl>
    implements _$$CBOHomeScreenConfigModelImplCopyWith<$Res> {
  __$$CBOHomeScreenConfigModelImplCopyWithImpl(
      _$CBOHomeScreenConfigModelImpl _value,
      $Res Function(_$CBOHomeScreenConfigModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? order = null,
    Object? key = null,
    Object? displayName = freezed,
    Object? label = freezed,
    Object? active = freezed,
  }) {
    return _then(_$CBOHomeScreenConfigModelImpl(
      order: null == order
          ? _value.order
          : order // ignore: cast_nullable_to_non_nullable
              as int,
      key: null == key
          ? _value.key
          : key // ignore: cast_nullable_to_non_nullable
              as String,
      displayName: freezed == displayName
          ? _value.displayName
          : displayName // ignore: cast_nullable_to_non_nullable
              as String?,
      label: freezed == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$CBOHomeScreenConfigModelImpl implements _CBOHomeScreenConfigModel {
  const _$CBOHomeScreenConfigModelImpl(
      {required this.order,
      required this.key,
      this.displayName,
      this.label,
      this.active});

  factory _$CBOHomeScreenConfigModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$CBOHomeScreenConfigModelImplFromJson(json);

  @override
  final int order;
  @override
  final String key;
  @override
  final String? displayName;
  @override
  final String? label;
  @override
  final bool? active;

  @override
  String toString() {
    return 'CBOHomeScreenConfigModel(order: $order, key: $key, displayName: $displayName, label: $label, active: $active)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CBOHomeScreenConfigModelImpl &&
            (identical(other.order, order) || other.order == order) &&
            (identical(other.key, key) || other.key == key) &&
            (identical(other.displayName, displayName) ||
                other.displayName == displayName) &&
            (identical(other.label, label) || other.label == label) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, order, key, displayName, label, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CBOHomeScreenConfigModelImplCopyWith<_$CBOHomeScreenConfigModelImpl>
      get copyWith => __$$CBOHomeScreenConfigModelImplCopyWithImpl<
          _$CBOHomeScreenConfigModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CBOHomeScreenConfigModelImplToJson(
      this,
    );
  }
}

abstract class _CBOHomeScreenConfigModel implements CBOHomeScreenConfigModel {
  const factory _CBOHomeScreenConfigModel(
      {required final int order,
      required final String key,
      final String? displayName,
      final String? label,
      final bool? active}) = _$CBOHomeScreenConfigModelImpl;

  factory _CBOHomeScreenConfigModel.fromJson(Map<String, dynamic> json) =
      _$CBOHomeScreenConfigModelImpl.fromJson;

  @override
  int get order;
  @override
  String get key;
  @override
  String? get displayName;
  @override
  String? get label;
  @override
  bool? get active;
  @override
  @JsonKey(ignore: true)
  _$$CBOHomeScreenConfigModelImplCopyWith<_$CBOHomeScreenConfigModelImpl>
      get copyWith => throw _privateConstructorUsedError;
}
