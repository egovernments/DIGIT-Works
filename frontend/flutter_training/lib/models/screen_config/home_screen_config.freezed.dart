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
      final CommonUIConfigModel? commonUiConfig}) = _$_HomeScreenConfigModel;

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
  @JsonKey(name: 'HomeScreenCardConfig')
  List<HomeScreenCardConfigModel>? get homeScreenCardConfig =>
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
      {@JsonKey(name: 'HomeScreenCardConfig')
      List<HomeScreenCardConfigModel>? homeScreenCardConfig});
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
    Object? homeScreenCardConfig = freezed,
  }) {
    return _then(_value.copyWith(
      homeScreenCardConfig: freezed == homeScreenCardConfig
          ? _value.homeScreenCardConfig
          : homeScreenCardConfig // ignore: cast_nullable_to_non_nullable
              as List<HomeScreenCardConfigModel>?,
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
      {@JsonKey(name: 'HomeScreenCardConfig')
      List<HomeScreenCardConfigModel>? homeScreenCardConfig});
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
    Object? homeScreenCardConfig = freezed,
  }) {
    return _then(_$_CommonUIConfigModel(
      homeScreenCardConfig: freezed == homeScreenCardConfig
          ? _value._homeScreenCardConfig
          : homeScreenCardConfig // ignore: cast_nullable_to_non_nullable
              as List<HomeScreenCardConfigModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_CommonUIConfigModel implements _CommonUIConfigModel {
  const _$_CommonUIConfigModel(
      {@JsonKey(name: 'HomeScreenCardConfig')
      final List<HomeScreenCardConfigModel>? homeScreenCardConfig})
      : _homeScreenCardConfig = homeScreenCardConfig;

  factory _$_CommonUIConfigModel.fromJson(Map<String, dynamic> json) =>
      _$$_CommonUIConfigModelFromJson(json);

  final List<HomeScreenCardConfigModel>? _homeScreenCardConfig;
  @override
  @JsonKey(name: 'HomeScreenCardConfig')
  List<HomeScreenCardConfigModel>? get homeScreenCardConfig {
    final value = _homeScreenCardConfig;
    if (value == null) return null;
    if (_homeScreenCardConfig is EqualUnmodifiableListView)
      return _homeScreenCardConfig;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'CommonUIConfigModel(homeScreenCardConfig: $homeScreenCardConfig)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CommonUIConfigModel &&
            const DeepCollectionEquality()
                .equals(other._homeScreenCardConfig, _homeScreenCardConfig));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_homeScreenCardConfig));

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
          {@JsonKey(name: 'HomeScreenCardConfig')
          final List<HomeScreenCardConfigModel>? homeScreenCardConfig}) =
      _$_CommonUIConfigModel;

  factory _CommonUIConfigModel.fromJson(Map<String, dynamic> json) =
      _$_CommonUIConfigModel.fromJson;

  @override
  @JsonKey(name: 'HomeScreenCardConfig')
  List<HomeScreenCardConfigModel>? get homeScreenCardConfig;
  @override
  @JsonKey(ignore: true)
  _$$_CommonUIConfigModelCopyWith<_$_CommonUIConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

HomeScreenCardConfigModel _$HomeScreenCardConfigModelFromJson(
    Map<String, dynamic> json) {
  return _HomeScreenCardConfigModel.fromJson(json);
}

/// @nodoc
mixin _$HomeScreenCardConfigModel {
  int get order => throw _privateConstructorUsedError;
  String get header => throw _privateConstructorUsedError;
  String? get displayName => throw _privateConstructorUsedError;
  String? get label => throw _privateConstructorUsedError;
  String? get icon => throw _privateConstructorUsedError;
  List<HomeScreenLinksModel>? get links => throw _privateConstructorUsedError;
  bool? get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $HomeScreenCardConfigModelCopyWith<HomeScreenCardConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $HomeScreenCardConfigModelCopyWith<$Res> {
  factory $HomeScreenCardConfigModelCopyWith(HomeScreenCardConfigModel value,
          $Res Function(HomeScreenCardConfigModel) then) =
      _$HomeScreenCardConfigModelCopyWithImpl<$Res, HomeScreenCardConfigModel>;
  @useResult
  $Res call(
      {int order,
      String header,
      String? displayName,
      String? label,
      String? icon,
      List<HomeScreenLinksModel>? links,
      bool? active});
}

/// @nodoc
class _$HomeScreenCardConfigModelCopyWithImpl<$Res,
        $Val extends HomeScreenCardConfigModel>
    implements $HomeScreenCardConfigModelCopyWith<$Res> {
  _$HomeScreenCardConfigModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? order = null,
    Object? header = null,
    Object? displayName = freezed,
    Object? label = freezed,
    Object? icon = freezed,
    Object? links = freezed,
    Object? active = freezed,
  }) {
    return _then(_value.copyWith(
      order: null == order
          ? _value.order
          : order // ignore: cast_nullable_to_non_nullable
              as int,
      header: null == header
          ? _value.header
          : header // ignore: cast_nullable_to_non_nullable
              as String,
      displayName: freezed == displayName
          ? _value.displayName
          : displayName // ignore: cast_nullable_to_non_nullable
              as String?,
      label: freezed == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String?,
      icon: freezed == icon
          ? _value.icon
          : icon // ignore: cast_nullable_to_non_nullable
              as String?,
      links: freezed == links
          ? _value.links
          : links // ignore: cast_nullable_to_non_nullable
              as List<HomeScreenLinksModel>?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_HomeScreenCardConfigModelCopyWith<$Res>
    implements $HomeScreenCardConfigModelCopyWith<$Res> {
  factory _$$_HomeScreenCardConfigModelCopyWith(
          _$_HomeScreenCardConfigModel value,
          $Res Function(_$_HomeScreenCardConfigModel) then) =
      __$$_HomeScreenCardConfigModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {int order,
      String header,
      String? displayName,
      String? label,
      String? icon,
      List<HomeScreenLinksModel>? links,
      bool? active});
}

/// @nodoc
class __$$_HomeScreenCardConfigModelCopyWithImpl<$Res>
    extends _$HomeScreenCardConfigModelCopyWithImpl<$Res,
        _$_HomeScreenCardConfigModel>
    implements _$$_HomeScreenCardConfigModelCopyWith<$Res> {
  __$$_HomeScreenCardConfigModelCopyWithImpl(
      _$_HomeScreenCardConfigModel _value,
      $Res Function(_$_HomeScreenCardConfigModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? order = null,
    Object? header = null,
    Object? displayName = freezed,
    Object? label = freezed,
    Object? icon = freezed,
    Object? links = freezed,
    Object? active = freezed,
  }) {
    return _then(_$_HomeScreenCardConfigModel(
      order: null == order
          ? _value.order
          : order // ignore: cast_nullable_to_non_nullable
              as int,
      header: null == header
          ? _value.header
          : header // ignore: cast_nullable_to_non_nullable
              as String,
      displayName: freezed == displayName
          ? _value.displayName
          : displayName // ignore: cast_nullable_to_non_nullable
              as String?,
      label: freezed == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String?,
      icon: freezed == icon
          ? _value.icon
          : icon // ignore: cast_nullable_to_non_nullable
              as String?,
      links: freezed == links
          ? _value._links
          : links // ignore: cast_nullable_to_non_nullable
              as List<HomeScreenLinksModel>?,
      active: freezed == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_HomeScreenCardConfigModel implements _HomeScreenCardConfigModel {
  const _$_HomeScreenCardConfigModel(
      {required this.order,
      required this.header,
      this.displayName,
      this.label,
      this.icon,
      final List<HomeScreenLinksModel>? links,
      this.active})
      : _links = links;

  factory _$_HomeScreenCardConfigModel.fromJson(Map<String, dynamic> json) =>
      _$$_HomeScreenCardConfigModelFromJson(json);

  @override
  final int order;
  @override
  final String header;
  @override
  final String? displayName;
  @override
  final String? label;
  @override
  final String? icon;
  final List<HomeScreenLinksModel>? _links;
  @override
  List<HomeScreenLinksModel>? get links {
    final value = _links;
    if (value == null) return null;
    if (_links is EqualUnmodifiableListView) return _links;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final bool? active;

  @override
  String toString() {
    return 'HomeScreenCardConfigModel(order: $order, header: $header, displayName: $displayName, label: $label, icon: $icon, links: $links, active: $active)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_HomeScreenCardConfigModel &&
            (identical(other.order, order) || other.order == order) &&
            (identical(other.header, header) || other.header == header) &&
            (identical(other.displayName, displayName) ||
                other.displayName == displayName) &&
            (identical(other.label, label) || other.label == label) &&
            (identical(other.icon, icon) || other.icon == icon) &&
            const DeepCollectionEquality().equals(other._links, _links) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, order, header, displayName,
      label, icon, const DeepCollectionEquality().hash(_links), active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_HomeScreenCardConfigModelCopyWith<_$_HomeScreenCardConfigModel>
      get copyWith => __$$_HomeScreenCardConfigModelCopyWithImpl<
          _$_HomeScreenCardConfigModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_HomeScreenCardConfigModelToJson(
      this,
    );
  }
}

abstract class _HomeScreenCardConfigModel implements HomeScreenCardConfigModel {
  const factory _HomeScreenCardConfigModel(
      {required final int order,
      required final String header,
      final String? displayName,
      final String? label,
      final String? icon,
      final List<HomeScreenLinksModel>? links,
      final bool? active}) = _$_HomeScreenCardConfigModel;

  factory _HomeScreenCardConfigModel.fromJson(Map<String, dynamic> json) =
      _$_HomeScreenCardConfigModel.fromJson;

  @override
  int get order;
  @override
  String get header;
  @override
  String? get displayName;
  @override
  String? get label;
  @override
  String? get icon;
  @override
  List<HomeScreenLinksModel>? get links;
  @override
  bool? get active;
  @override
  @JsonKey(ignore: true)
  _$$_HomeScreenCardConfigModelCopyWith<_$_HomeScreenCardConfigModel>
      get copyWith => throw _privateConstructorUsedError;
}

HomeScreenLinksModel _$HomeScreenLinksModelFromJson(Map<String, dynamic> json) {
  return _HomeScreenLinksModel.fromJson(json);
}

/// @nodoc
mixin _$HomeScreenLinksModel {
  int get order => throw _privateConstructorUsedError;
  String get key => throw _privateConstructorUsedError;
  String? get displayName => throw _privateConstructorUsedError;
  String? get label => throw _privateConstructorUsedError;
  bool? get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $HomeScreenLinksModelCopyWith<HomeScreenLinksModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $HomeScreenLinksModelCopyWith<$Res> {
  factory $HomeScreenLinksModelCopyWith(HomeScreenLinksModel value,
          $Res Function(HomeScreenLinksModel) then) =
      _$HomeScreenLinksModelCopyWithImpl<$Res, HomeScreenLinksModel>;
  @useResult
  $Res call(
      {int order,
      String key,
      String? displayName,
      String? label,
      bool? active});
}

/// @nodoc
class _$HomeScreenLinksModelCopyWithImpl<$Res,
        $Val extends HomeScreenLinksModel>
    implements $HomeScreenLinksModelCopyWith<$Res> {
  _$HomeScreenLinksModelCopyWithImpl(this._value, this._then);

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
abstract class _$$_HomeScreenLinksModelCopyWith<$Res>
    implements $HomeScreenLinksModelCopyWith<$Res> {
  factory _$$_HomeScreenLinksModelCopyWith(_$_HomeScreenLinksModel value,
          $Res Function(_$_HomeScreenLinksModel) then) =
      __$$_HomeScreenLinksModelCopyWithImpl<$Res>;
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
class __$$_HomeScreenLinksModelCopyWithImpl<$Res>
    extends _$HomeScreenLinksModelCopyWithImpl<$Res, _$_HomeScreenLinksModel>
    implements _$$_HomeScreenLinksModelCopyWith<$Res> {
  __$$_HomeScreenLinksModelCopyWithImpl(_$_HomeScreenLinksModel _value,
      $Res Function(_$_HomeScreenLinksModel) _then)
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
    return _then(_$_HomeScreenLinksModel(
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
class _$_HomeScreenLinksModel implements _HomeScreenLinksModel {
  const _$_HomeScreenLinksModel(
      {required this.order,
      required this.key,
      this.displayName,
      this.label,
      this.active});

  factory _$_HomeScreenLinksModel.fromJson(Map<String, dynamic> json) =>
      _$$_HomeScreenLinksModelFromJson(json);

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
    return 'HomeScreenLinksModel(order: $order, key: $key, displayName: $displayName, label: $label, active: $active)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_HomeScreenLinksModel &&
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
  _$$_HomeScreenLinksModelCopyWith<_$_HomeScreenLinksModel> get copyWith =>
      __$$_HomeScreenLinksModelCopyWithImpl<_$_HomeScreenLinksModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_HomeScreenLinksModelToJson(
      this,
    );
  }
}

abstract class _HomeScreenLinksModel implements HomeScreenLinksModel {
  const factory _HomeScreenLinksModel(
      {required final int order,
      required final String key,
      final String? displayName,
      final String? label,
      final bool? active}) = _$_HomeScreenLinksModel;

  factory _HomeScreenLinksModel.fromJson(Map<String, dynamic> json) =
      _$_HomeScreenLinksModel.fromJson;

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
  _$$_HomeScreenLinksModelCopyWith<_$_HomeScreenLinksModel> get copyWith =>
      throw _privateConstructorUsedError;
}
