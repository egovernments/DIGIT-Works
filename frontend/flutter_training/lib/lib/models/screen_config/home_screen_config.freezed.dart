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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

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
abstract class _$$_HomeScreenConfigModelCopyWith<$Res>
    implements $HomeScreenConfigModelCopyWith<$Res> {
  factory _$$_HomeScreenConfigModelCopyWith(_$_HomeScreenConfigModel value,
          $Res Function(_$_HomeScreenConfigModel) then) =
      __$$_HomeScreenConfigModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'commonUiConfig') CommonUIConfigModel? commonUiConfig});

  @override
  $CommonUIConfigModelCopyWith<$Res>? get commonUiConfig;
}

/// @nodoc
class __$$_HomeScreenConfigModelCopyWithImpl<$Res>
    extends _$HomeScreenConfigModelCopyWithImpl<$Res, _$_HomeScreenConfigModel>
    implements _$$_HomeScreenConfigModelCopyWith<$Res> {
  __$$_HomeScreenConfigModelCopyWithImpl(_$_HomeScreenConfigModel _value,
      $Res Function(_$_HomeScreenConfigModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonUiConfig = freezed,
  }) {
    return _then(_$_HomeScreenConfigModel(
      commonUiConfig: freezed == commonUiConfig
          ? _value.commonUiConfig
          : commonUiConfig // ignore: cast_nullable_to_non_nullable
              as CommonUIConfigModel?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_HomeScreenConfigModel implements _HomeScreenConfigModel {
  const _$_HomeScreenConfigModel(
      {@JsonKey(name: 'commonUiConfig') this.commonUiConfig});

  factory _$_HomeScreenConfigModel.fromJson(Map<String, dynamic> json) =>
      _$$_HomeScreenConfigModelFromJson(json);

  @override
  @JsonKey(name: 'commonUiConfig')
  final CommonUIConfigModel? commonUiConfig;

  @override
  String toString() {
    return 'HomeScreenConfigModel(commonUiConfig: $commonUiConfig)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_HomeScreenConfigModel &&
            (identical(other.commonUiConfig, commonUiConfig) ||
                other.commonUiConfig == commonUiConfig));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, commonUiConfig);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_HomeScreenConfigModelCopyWith<_$_HomeScreenConfigModel> get copyWith =>
      __$$_HomeScreenConfigModelCopyWithImpl<_$_HomeScreenConfigModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_HomeScreenConfigModelToJson(
      this,
    );
  }
}

abstract class _HomeScreenConfigModel implements HomeScreenConfigModel {
  const factory _HomeScreenConfigModel(
          {@JsonKey(name: 'commonUiConfig')
              final CommonUIConfigModel? commonUiConfig}) =
      _$_HomeScreenConfigModel;

  factory _HomeScreenConfigModel.fromJson(Map<String, dynamic> json) =
      _$_HomeScreenConfigModel.fromJson;

  @override
  @JsonKey(name: 'commonUiConfig')
  CommonUIConfigModel? get commonUiConfig;
  @override
  @JsonKey(ignore: true)
  _$$_HomeScreenConfigModelCopyWith<_$_HomeScreenConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
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
          List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria});
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
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_CommonUIConfigModelCopyWith<$Res>
    implements $CommonUIConfigModelCopyWith<$Res> {
  factory _$$_CommonUIConfigModelCopyWith(_$_CommonUIConfigModel value,
          $Res Function(_$_CommonUIConfigModel) then) =
      __$$_CommonUIConfigModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'CBOHomeScreenConfig')
          List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
      @JsonKey(name: 'CBOMyWorks')
          List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria});
}

/// @nodoc
class __$$_CommonUIConfigModelCopyWithImpl<$Res>
    extends _$CommonUIConfigModelCopyWithImpl<$Res, _$_CommonUIConfigModel>
    implements _$$_CommonUIConfigModelCopyWith<$Res> {
  __$$_CommonUIConfigModelCopyWithImpl(_$_CommonUIConfigModel _value,
      $Res Function(_$_CommonUIConfigModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cboHomeScreenConfig = freezed,
    Object? cboMyWorksSearchCriteria = freezed,
  }) {
    return _then(_$_CommonUIConfigModel(
      cboHomeScreenConfig: freezed == cboHomeScreenConfig
          ? _value._cboHomeScreenConfig
          : cboHomeScreenConfig // ignore: cast_nullable_to_non_nullable
              as List<CBOHomeScreenConfigModel>?,
      cboMyWorksSearchCriteria: freezed == cboMyWorksSearchCriteria
          ? _value._cboMyWorksSearchCriteria
          : cboMyWorksSearchCriteria // ignore: cast_nullable_to_non_nullable
              as List<CBOMyWorksSearchCriteriaModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_CommonUIConfigModel implements _CommonUIConfigModel {
  const _$_CommonUIConfigModel(
      {@JsonKey(name: 'CBOHomeScreenConfig')
          final List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
      @JsonKey(name: 'CBOMyWorks')
          final List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria})
      : _cboHomeScreenConfig = cboHomeScreenConfig,
        _cboMyWorksSearchCriteria = cboMyWorksSearchCriteria;

  factory _$_CommonUIConfigModel.fromJson(Map<String, dynamic> json) =>
      _$$_CommonUIConfigModelFromJson(json);

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

  @override
  String toString() {
    return 'CommonUIConfigModel(cboHomeScreenConfig: $cboHomeScreenConfig, cboMyWorksSearchCriteria: $cboMyWorksSearchCriteria)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CommonUIConfigModel &&
            const DeepCollectionEquality()
                .equals(other._cboHomeScreenConfig, _cboHomeScreenConfig) &&
            const DeepCollectionEquality().equals(
                other._cboMyWorksSearchCriteria, _cboMyWorksSearchCriteria));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_cboHomeScreenConfig),
      const DeepCollectionEquality().hash(_cboMyWorksSearchCriteria));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_CommonUIConfigModelCopyWith<_$_CommonUIConfigModel> get copyWith =>
      __$$_CommonUIConfigModelCopyWithImpl<_$_CommonUIConfigModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_CommonUIConfigModelToJson(
      this,
    );
  }
}

abstract class _CommonUIConfigModel implements CommonUIConfigModel {
  const factory _CommonUIConfigModel(
      {@JsonKey(name: 'CBOHomeScreenConfig')
          final List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
      @JsonKey(name: 'CBOMyWorks')
          final List<CBOMyWorksSearchCriteriaModel>?
              cboMyWorksSearchCriteria}) = _$_CommonUIConfigModel;

  factory _CommonUIConfigModel.fromJson(Map<String, dynamic> json) =
      _$_CommonUIConfigModel.fromJson;

  @override
  @JsonKey(name: 'CBOHomeScreenConfig')
  List<CBOHomeScreenConfigModel>? get cboHomeScreenConfig;
  @override
  @JsonKey(name: 'CBOMyWorks')
  List<CBOMyWorksSearchCriteriaModel>? get cboMyWorksSearchCriteria;
  @override
  @JsonKey(ignore: true)
  _$$_CommonUIConfigModelCopyWith<_$_CommonUIConfigModel> get copyWith =>
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
abstract class _$$_CBOHomeScreenConfigModelCopyWith<$Res>
    implements $CBOHomeScreenConfigModelCopyWith<$Res> {
  factory _$$_CBOHomeScreenConfigModelCopyWith(
          _$_CBOHomeScreenConfigModel value,
          $Res Function(_$_CBOHomeScreenConfigModel) then) =
      __$$_CBOHomeScreenConfigModelCopyWithImpl<$Res>;
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
class __$$_CBOHomeScreenConfigModelCopyWithImpl<$Res>
    extends _$CBOHomeScreenConfigModelCopyWithImpl<$Res,
        _$_CBOHomeScreenConfigModel>
    implements _$$_CBOHomeScreenConfigModelCopyWith<$Res> {
  __$$_CBOHomeScreenConfigModelCopyWithImpl(_$_CBOHomeScreenConfigModel _value,
      $Res Function(_$_CBOHomeScreenConfigModel) _then)
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
    return _then(_$_CBOHomeScreenConfigModel(
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
class _$_CBOHomeScreenConfigModel implements _CBOHomeScreenConfigModel {
  const _$_CBOHomeScreenConfigModel(
      {required this.order,
      required this.key,
      this.displayName,
      this.label,
      this.active});

  factory _$_CBOHomeScreenConfigModel.fromJson(Map<String, dynamic> json) =>
      _$$_CBOHomeScreenConfigModelFromJson(json);

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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CBOHomeScreenConfigModel &&
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
  _$$_CBOHomeScreenConfigModelCopyWith<_$_CBOHomeScreenConfigModel>
      get copyWith => __$$_CBOHomeScreenConfigModelCopyWithImpl<
          _$_CBOHomeScreenConfigModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_CBOHomeScreenConfigModelToJson(
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
      final bool? active}) = _$_CBOHomeScreenConfigModel;

  factory _CBOHomeScreenConfigModel.fromJson(Map<String, dynamic> json) =
      _$_CBOHomeScreenConfigModel.fromJson;

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
  _$$_CBOHomeScreenConfigModelCopyWith<_$_CBOHomeScreenConfigModel>
      get copyWith => throw _privateConstructorUsedError;
}
