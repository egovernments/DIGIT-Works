// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'my_works_search_criteria.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

MyWorksSearchCriteriaModel _$MyWorksSearchCriteriaModelFromJson(
    Map<String, dynamic> json) {
  return _MyWorksSearchCriteriaModel.fromJson(json);
}

/// @nodoc
mixin _$MyWorksSearchCriteriaModel {
  @JsonKey(name: 'commonUiConfig')
  CommonUIConfigModel? get commonUiConfig => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MyWorksSearchCriteriaModelCopyWith<MyWorksSearchCriteriaModel>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MyWorksSearchCriteriaModelCopyWith<$Res> {
  factory $MyWorksSearchCriteriaModelCopyWith(MyWorksSearchCriteriaModel value,
          $Res Function(MyWorksSearchCriteriaModel) then) =
      _$MyWorksSearchCriteriaModelCopyWithImpl<$Res,
          MyWorksSearchCriteriaModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'commonUiConfig') CommonUIConfigModel? commonUiConfig});

  $CommonUIConfigModelCopyWith<$Res>? get commonUiConfig;
}

/// @nodoc
class _$MyWorksSearchCriteriaModelCopyWithImpl<$Res,
        $Val extends MyWorksSearchCriteriaModel>
    implements $MyWorksSearchCriteriaModelCopyWith<$Res> {
  _$MyWorksSearchCriteriaModelCopyWithImpl(this._value, this._then);

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
abstract class _$$_MyWorksSearchCriteriaModelCopyWith<$Res>
    implements $MyWorksSearchCriteriaModelCopyWith<$Res> {
  factory _$$_MyWorksSearchCriteriaModelCopyWith(
          _$_MyWorksSearchCriteriaModel value,
          $Res Function(_$_MyWorksSearchCriteriaModel) then) =
      __$$_MyWorksSearchCriteriaModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'commonUiConfig') CommonUIConfigModel? commonUiConfig});

  @override
  $CommonUIConfigModelCopyWith<$Res>? get commonUiConfig;
}

/// @nodoc
class __$$_MyWorksSearchCriteriaModelCopyWithImpl<$Res>
    extends _$MyWorksSearchCriteriaModelCopyWithImpl<$Res,
        _$_MyWorksSearchCriteriaModel>
    implements _$$_MyWorksSearchCriteriaModelCopyWith<$Res> {
  __$$_MyWorksSearchCriteriaModelCopyWithImpl(
      _$_MyWorksSearchCriteriaModel _value,
      $Res Function(_$_MyWorksSearchCriteriaModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? commonUiConfig = freezed,
  }) {
    return _then(_$_MyWorksSearchCriteriaModel(
      commonUiConfig: freezed == commonUiConfig
          ? _value.commonUiConfig
          : commonUiConfig // ignore: cast_nullable_to_non_nullable
              as CommonUIConfigModel?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MyWorksSearchCriteriaModel implements _MyWorksSearchCriteriaModel {
  const _$_MyWorksSearchCriteriaModel(
      {@JsonKey(name: 'commonUiConfig') this.commonUiConfig});

  factory _$_MyWorksSearchCriteriaModel.fromJson(Map<String, dynamic> json) =>
      _$$_MyWorksSearchCriteriaModelFromJson(json);

  @override
  @JsonKey(name: 'commonUiConfig')
  final CommonUIConfigModel? commonUiConfig;

  @override
  String toString() {
    return 'MyWorksSearchCriteriaModel(commonUiConfig: $commonUiConfig)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MyWorksSearchCriteriaModel &&
            (identical(other.commonUiConfig, commonUiConfig) ||
                other.commonUiConfig == commonUiConfig));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, commonUiConfig);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MyWorksSearchCriteriaModelCopyWith<_$_MyWorksSearchCriteriaModel>
      get copyWith => __$$_MyWorksSearchCriteriaModelCopyWithImpl<
          _$_MyWorksSearchCriteriaModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MyWorksSearchCriteriaModelToJson(
      this,
    );
  }
}

abstract class _MyWorksSearchCriteriaModel
    implements MyWorksSearchCriteriaModel {
  const factory _MyWorksSearchCriteriaModel(
          {@JsonKey(name: 'commonUiConfig')
              final CommonUIConfigModel? commonUiConfig}) =
      _$_MyWorksSearchCriteriaModel;

  factory _MyWorksSearchCriteriaModel.fromJson(Map<String, dynamic> json) =
      _$_MyWorksSearchCriteriaModel.fromJson;

  @override
  @JsonKey(name: 'commonUiConfig')
  CommonUIConfigModel? get commonUiConfig;
  @override
  @JsonKey(ignore: true)
  _$$_MyWorksSearchCriteriaModelCopyWith<_$_MyWorksSearchCriteriaModel>
      get copyWith => throw _privateConstructorUsedError;
}

CBOMyWorksSearchCriteriaModel _$CBOMyWorksSearchCriteriaModelFromJson(
    Map<String, dynamic> json) {
  return _CBOMyWorksSearchCriteriaModel.fromJson(json);
}

/// @nodoc
mixin _$CBOMyWorksSearchCriteriaModel {
  List<String>? get searchCriteria => throw _privateConstructorUsedError;
  String? get acceptCode => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $CBOMyWorksSearchCriteriaModelCopyWith<CBOMyWorksSearchCriteriaModel>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CBOMyWorksSearchCriteriaModelCopyWith<$Res> {
  factory $CBOMyWorksSearchCriteriaModelCopyWith(
          CBOMyWorksSearchCriteriaModel value,
          $Res Function(CBOMyWorksSearchCriteriaModel) then) =
      _$CBOMyWorksSearchCriteriaModelCopyWithImpl<$Res,
          CBOMyWorksSearchCriteriaModel>;
  @useResult
  $Res call({List<String>? searchCriteria, String? acceptCode});
}

/// @nodoc
class _$CBOMyWorksSearchCriteriaModelCopyWithImpl<$Res,
        $Val extends CBOMyWorksSearchCriteriaModel>
    implements $CBOMyWorksSearchCriteriaModelCopyWith<$Res> {
  _$CBOMyWorksSearchCriteriaModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? searchCriteria = freezed,
    Object? acceptCode = freezed,
  }) {
    return _then(_value.copyWith(
      searchCriteria: freezed == searchCriteria
          ? _value.searchCriteria
          : searchCriteria // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      acceptCode: freezed == acceptCode
          ? _value.acceptCode
          : acceptCode // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_CBOMyWorksSearchCriteriaModelCopyWith<$Res>
    implements $CBOMyWorksSearchCriteriaModelCopyWith<$Res> {
  factory _$$_CBOMyWorksSearchCriteriaModelCopyWith(
          _$_CBOMyWorksSearchCriteriaModel value,
          $Res Function(_$_CBOMyWorksSearchCriteriaModel) then) =
      __$$_CBOMyWorksSearchCriteriaModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({List<String>? searchCriteria, String? acceptCode});
}

/// @nodoc
class __$$_CBOMyWorksSearchCriteriaModelCopyWithImpl<$Res>
    extends _$CBOMyWorksSearchCriteriaModelCopyWithImpl<$Res,
        _$_CBOMyWorksSearchCriteriaModel>
    implements _$$_CBOMyWorksSearchCriteriaModelCopyWith<$Res> {
  __$$_CBOMyWorksSearchCriteriaModelCopyWithImpl(
      _$_CBOMyWorksSearchCriteriaModel _value,
      $Res Function(_$_CBOMyWorksSearchCriteriaModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? searchCriteria = freezed,
    Object? acceptCode = freezed,
  }) {
    return _then(_$_CBOMyWorksSearchCriteriaModel(
      searchCriteria: freezed == searchCriteria
          ? _value._searchCriteria
          : searchCriteria // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      acceptCode: freezed == acceptCode
          ? _value.acceptCode
          : acceptCode // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_CBOMyWorksSearchCriteriaModel
    implements _CBOMyWorksSearchCriteriaModel {
  const _$_CBOMyWorksSearchCriteriaModel(
      {final List<String>? searchCriteria, this.acceptCode})
      : _searchCriteria = searchCriteria;

  factory _$_CBOMyWorksSearchCriteriaModel.fromJson(
          Map<String, dynamic> json) =>
      _$$_CBOMyWorksSearchCriteriaModelFromJson(json);

  final List<String>? _searchCriteria;
  @override
  List<String>? get searchCriteria {
    final value = _searchCriteria;
    if (value == null) return null;
    if (_searchCriteria is EqualUnmodifiableListView) return _searchCriteria;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final String? acceptCode;

  @override
  String toString() {
    return 'CBOMyWorksSearchCriteriaModel(searchCriteria: $searchCriteria, acceptCode: $acceptCode)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CBOMyWorksSearchCriteriaModel &&
            const DeepCollectionEquality()
                .equals(other._searchCriteria, _searchCriteria) &&
            (identical(other.acceptCode, acceptCode) ||
                other.acceptCode == acceptCode));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType,
      const DeepCollectionEquality().hash(_searchCriteria), acceptCode);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_CBOMyWorksSearchCriteriaModelCopyWith<_$_CBOMyWorksSearchCriteriaModel>
      get copyWith => __$$_CBOMyWorksSearchCriteriaModelCopyWithImpl<
          _$_CBOMyWorksSearchCriteriaModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_CBOMyWorksSearchCriteriaModelToJson(
      this,
    );
  }
}

abstract class _CBOMyWorksSearchCriteriaModel
    implements CBOMyWorksSearchCriteriaModel {
  const factory _CBOMyWorksSearchCriteriaModel(
      {final List<String>? searchCriteria,
      final String? acceptCode}) = _$_CBOMyWorksSearchCriteriaModel;

  factory _CBOMyWorksSearchCriteriaModel.fromJson(Map<String, dynamic> json) =
      _$_CBOMyWorksSearchCriteriaModel.fromJson;

  @override
  List<String>? get searchCriteria;
  @override
  String? get acceptCode;
  @override
  @JsonKey(ignore: true)
  _$$_CBOMyWorksSearchCriteriaModelCopyWith<_$_CBOMyWorksSearchCriteriaModel>
      get copyWith => throw _privateConstructorUsedError;
}

CBOMyServiceRequestsConfig _$CBOMyServiceRequestsConfigFromJson(
    Map<String, dynamic> json) {
  return _CBOMyServiceRequestsConfig.fromJson(json);
}

/// @nodoc
mixin _$CBOMyServiceRequestsConfig {
  String? get editTimeExtReqCode => throw _privateConstructorUsedError;
  String? get editActionCode => throw _privateConstructorUsedError;
  String? get searchCriteria => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $CBOMyServiceRequestsConfigCopyWith<CBOMyServiceRequestsConfig>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CBOMyServiceRequestsConfigCopyWith<$Res> {
  factory $CBOMyServiceRequestsConfigCopyWith(CBOMyServiceRequestsConfig value,
          $Res Function(CBOMyServiceRequestsConfig) then) =
      _$CBOMyServiceRequestsConfigCopyWithImpl<$Res,
          CBOMyServiceRequestsConfig>;
  @useResult
  $Res call(
      {String? editTimeExtReqCode,
      String? editActionCode,
      String? searchCriteria});
}

/// @nodoc
class _$CBOMyServiceRequestsConfigCopyWithImpl<$Res,
        $Val extends CBOMyServiceRequestsConfig>
    implements $CBOMyServiceRequestsConfigCopyWith<$Res> {
  _$CBOMyServiceRequestsConfigCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? editTimeExtReqCode = freezed,
    Object? editActionCode = freezed,
    Object? searchCriteria = freezed,
  }) {
    return _then(_value.copyWith(
      editTimeExtReqCode: freezed == editTimeExtReqCode
          ? _value.editTimeExtReqCode
          : editTimeExtReqCode // ignore: cast_nullable_to_non_nullable
              as String?,
      editActionCode: freezed == editActionCode
          ? _value.editActionCode
          : editActionCode // ignore: cast_nullable_to_non_nullable
              as String?,
      searchCriteria: freezed == searchCriteria
          ? _value.searchCriteria
          : searchCriteria // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_CBOMyServiceRequestsConfigCopyWith<$Res>
    implements $CBOMyServiceRequestsConfigCopyWith<$Res> {
  factory _$$_CBOMyServiceRequestsConfigCopyWith(
          _$_CBOMyServiceRequestsConfig value,
          $Res Function(_$_CBOMyServiceRequestsConfig) then) =
      __$$_CBOMyServiceRequestsConfigCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? editTimeExtReqCode,
      String? editActionCode,
      String? searchCriteria});
}

/// @nodoc
class __$$_CBOMyServiceRequestsConfigCopyWithImpl<$Res>
    extends _$CBOMyServiceRequestsConfigCopyWithImpl<$Res,
        _$_CBOMyServiceRequestsConfig>
    implements _$$_CBOMyServiceRequestsConfigCopyWith<$Res> {
  __$$_CBOMyServiceRequestsConfigCopyWithImpl(
      _$_CBOMyServiceRequestsConfig _value,
      $Res Function(_$_CBOMyServiceRequestsConfig) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? editTimeExtReqCode = freezed,
    Object? editActionCode = freezed,
    Object? searchCriteria = freezed,
  }) {
    return _then(_$_CBOMyServiceRequestsConfig(
      editTimeExtReqCode: freezed == editTimeExtReqCode
          ? _value.editTimeExtReqCode
          : editTimeExtReqCode // ignore: cast_nullable_to_non_nullable
              as String?,
      editActionCode: freezed == editActionCode
          ? _value.editActionCode
          : editActionCode // ignore: cast_nullable_to_non_nullable
              as String?,
      searchCriteria: freezed == searchCriteria
          ? _value.searchCriteria
          : searchCriteria // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_CBOMyServiceRequestsConfig implements _CBOMyServiceRequestsConfig {
  const _$_CBOMyServiceRequestsConfig(
      {this.editTimeExtReqCode, this.editActionCode, this.searchCriteria});

  factory _$_CBOMyServiceRequestsConfig.fromJson(Map<String, dynamic> json) =>
      _$$_CBOMyServiceRequestsConfigFromJson(json);

  @override
  final String? editTimeExtReqCode;
  @override
  final String? editActionCode;
  @override
  final String? searchCriteria;

  @override
  String toString() {
    return 'CBOMyServiceRequestsConfig(editTimeExtReqCode: $editTimeExtReqCode, editActionCode: $editActionCode, searchCriteria: $searchCriteria)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CBOMyServiceRequestsConfig &&
            (identical(other.editTimeExtReqCode, editTimeExtReqCode) ||
                other.editTimeExtReqCode == editTimeExtReqCode) &&
            (identical(other.editActionCode, editActionCode) ||
                other.editActionCode == editActionCode) &&
            (identical(other.searchCriteria, searchCriteria) ||
                other.searchCriteria == searchCriteria));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, editTimeExtReqCode, editActionCode, searchCriteria);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_CBOMyServiceRequestsConfigCopyWith<_$_CBOMyServiceRequestsConfig>
      get copyWith => __$$_CBOMyServiceRequestsConfigCopyWithImpl<
          _$_CBOMyServiceRequestsConfig>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_CBOMyServiceRequestsConfigToJson(
      this,
    );
  }
}

abstract class _CBOMyServiceRequestsConfig
    implements CBOMyServiceRequestsConfig {
  const factory _CBOMyServiceRequestsConfig(
      {final String? editTimeExtReqCode,
      final String? editActionCode,
      final String? searchCriteria}) = _$_CBOMyServiceRequestsConfig;

  factory _CBOMyServiceRequestsConfig.fromJson(Map<String, dynamic> json) =
      _$_CBOMyServiceRequestsConfig.fromJson;

  @override
  String? get editTimeExtReqCode;
  @override
  String? get editActionCode;
  @override
  String? get searchCriteria;
  @override
  @JsonKey(ignore: true)
  _$$_CBOMyServiceRequestsConfigCopyWith<_$_CBOMyServiceRequestsConfig>
      get copyWith => throw _privateConstructorUsedError;
}
