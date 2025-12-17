// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'app_initilization.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$AppInitializationEvent {
  String get selectedLang => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String selectedLang) onapplicationInitializeSetup,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String selectedLang)? onapplicationInitializeSetup,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String selectedLang)? onapplicationInitializeSetup,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(AppInitializationSetupEvent value)
        onapplicationInitializeSetup,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(AppInitializationSetupEvent value)?
        onapplicationInitializeSetup,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(AppInitializationSetupEvent value)?
        onapplicationInitializeSetup,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AppInitializationEventCopyWith<AppInitializationEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AppInitializationEventCopyWith<$Res> {
  factory $AppInitializationEventCopyWith(AppInitializationEvent value,
          $Res Function(AppInitializationEvent) then) =
      _$AppInitializationEventCopyWithImpl<$Res, AppInitializationEvent>;
  @useResult
  $Res call({String selectedLang});
}

/// @nodoc
class _$AppInitializationEventCopyWithImpl<$Res,
        $Val extends AppInitializationEvent>
    implements $AppInitializationEventCopyWith<$Res> {
  _$AppInitializationEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? selectedLang = null,
  }) {
    return _then(_value.copyWith(
      selectedLang: null == selectedLang
          ? _value.selectedLang
          : selectedLang // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$AppInitializationSetupEventImplCopyWith<$Res>
    implements $AppInitializationEventCopyWith<$Res> {
  factory _$$AppInitializationSetupEventImplCopyWith(
          _$AppInitializationSetupEventImpl value,
          $Res Function(_$AppInitializationSetupEventImpl) then) =
      __$$AppInitializationSetupEventImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String selectedLang});
}

/// @nodoc
class __$$AppInitializationSetupEventImplCopyWithImpl<$Res>
    extends _$AppInitializationEventCopyWithImpl<$Res,
        _$AppInitializationSetupEventImpl>
    implements _$$AppInitializationSetupEventImplCopyWith<$Res> {
  __$$AppInitializationSetupEventImplCopyWithImpl(
      _$AppInitializationSetupEventImpl _value,
      $Res Function(_$AppInitializationSetupEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? selectedLang = null,
  }) {
    return _then(_$AppInitializationSetupEventImpl(
      selectedLang: null == selectedLang
          ? _value.selectedLang
          : selectedLang // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$AppInitializationSetupEventImpl
    with DiagnosticableTreeMixin
    implements AppInitializationSetupEvent {
  const _$AppInitializationSetupEventImpl({required this.selectedLang});

  @override
  final String selectedLang;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AppInitializationEvent.onapplicationInitializeSetup(selectedLang: $selectedLang)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'AppInitializationEvent.onapplicationInitializeSetup'))
      ..add(DiagnosticsProperty('selectedLang', selectedLang));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AppInitializationSetupEventImpl &&
            (identical(other.selectedLang, selectedLang) ||
                other.selectedLang == selectedLang));
  }

  @override
  int get hashCode => Object.hash(runtimeType, selectedLang);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AppInitializationSetupEventImplCopyWith<_$AppInitializationSetupEventImpl>
      get copyWith => __$$AppInitializationSetupEventImplCopyWithImpl<
          _$AppInitializationSetupEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String selectedLang) onapplicationInitializeSetup,
  }) {
    return onapplicationInitializeSetup(selectedLang);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String selectedLang)? onapplicationInitializeSetup,
  }) {
    return onapplicationInitializeSetup?.call(selectedLang);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String selectedLang)? onapplicationInitializeSetup,
    required TResult orElse(),
  }) {
    if (onapplicationInitializeSetup != null) {
      return onapplicationInitializeSetup(selectedLang);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(AppInitializationSetupEvent value)
        onapplicationInitializeSetup,
  }) {
    return onapplicationInitializeSetup(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(AppInitializationSetupEvent value)?
        onapplicationInitializeSetup,
  }) {
    return onapplicationInitializeSetup?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(AppInitializationSetupEvent value)?
        onapplicationInitializeSetup,
    required TResult orElse(),
  }) {
    if (onapplicationInitializeSetup != null) {
      return onapplicationInitializeSetup(this);
    }
    return orElse();
  }
}

abstract class AppInitializationSetupEvent implements AppInitializationEvent {
  const factory AppInitializationSetupEvent(
      {required final String selectedLang}) = _$AppInitializationSetupEventImpl;

  @override
  String get selectedLang;
  @override
  @JsonKey(ignore: true)
  _$$AppInitializationSetupEventImplCopyWith<_$AppInitializationSetupEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$AppInitializationState {
  bool get isInitializationCompleted => throw _privateConstructorUsedError;
  InitMdmsModel? get initMdmsModel => throw _privateConstructorUsedError;
  StateInfoListModel? get stateInfoListModel =>
      throw _privateConstructorUsedError;
  List<DigitRowCardModel>? get digitRowCardItems =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AppInitializationStateCopyWith<AppInitializationState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AppInitializationStateCopyWith<$Res> {
  factory $AppInitializationStateCopyWith(AppInitializationState value,
          $Res Function(AppInitializationState) then) =
      _$AppInitializationStateCopyWithImpl<$Res, AppInitializationState>;
  @useResult
  $Res call(
      {bool isInitializationCompleted,
      InitMdmsModel? initMdmsModel,
      StateInfoListModel? stateInfoListModel,
      List<DigitRowCardModel>? digitRowCardItems});

  $InitMdmsModelCopyWith<$Res>? get initMdmsModel;
  $StateInfoListModelCopyWith<$Res>? get stateInfoListModel;
}

/// @nodoc
class _$AppInitializationStateCopyWithImpl<$Res,
        $Val extends AppInitializationState>
    implements $AppInitializationStateCopyWith<$Res> {
  _$AppInitializationStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? isInitializationCompleted = null,
    Object? initMdmsModel = freezed,
    Object? stateInfoListModel = freezed,
    Object? digitRowCardItems = freezed,
  }) {
    return _then(_value.copyWith(
      isInitializationCompleted: null == isInitializationCompleted
          ? _value.isInitializationCompleted
          : isInitializationCompleted // ignore: cast_nullable_to_non_nullable
              as bool,
      initMdmsModel: freezed == initMdmsModel
          ? _value.initMdmsModel
          : initMdmsModel // ignore: cast_nullable_to_non_nullable
              as InitMdmsModel?,
      stateInfoListModel: freezed == stateInfoListModel
          ? _value.stateInfoListModel
          : stateInfoListModel // ignore: cast_nullable_to_non_nullable
              as StateInfoListModel?,
      digitRowCardItems: freezed == digitRowCardItems
          ? _value.digitRowCardItems
          : digitRowCardItems // ignore: cast_nullable_to_non_nullable
              as List<DigitRowCardModel>?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $InitMdmsModelCopyWith<$Res>? get initMdmsModel {
    if (_value.initMdmsModel == null) {
      return null;
    }

    return $InitMdmsModelCopyWith<$Res>(_value.initMdmsModel!, (value) {
      return _then(_value.copyWith(initMdmsModel: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $StateInfoListModelCopyWith<$Res>? get stateInfoListModel {
    if (_value.stateInfoListModel == null) {
      return null;
    }

    return $StateInfoListModelCopyWith<$Res>(_value.stateInfoListModel!,
        (value) {
      return _then(_value.copyWith(stateInfoListModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$AppInitializationStateImplCopyWith<$Res>
    implements $AppInitializationStateCopyWith<$Res> {
  factory _$$AppInitializationStateImplCopyWith(
          _$AppInitializationStateImpl value,
          $Res Function(_$AppInitializationStateImpl) then) =
      __$$AppInitializationStateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {bool isInitializationCompleted,
      InitMdmsModel? initMdmsModel,
      StateInfoListModel? stateInfoListModel,
      List<DigitRowCardModel>? digitRowCardItems});

  @override
  $InitMdmsModelCopyWith<$Res>? get initMdmsModel;
  @override
  $StateInfoListModelCopyWith<$Res>? get stateInfoListModel;
}

/// @nodoc
class __$$AppInitializationStateImplCopyWithImpl<$Res>
    extends _$AppInitializationStateCopyWithImpl<$Res,
        _$AppInitializationStateImpl>
    implements _$$AppInitializationStateImplCopyWith<$Res> {
  __$$AppInitializationStateImplCopyWithImpl(
      _$AppInitializationStateImpl _value,
      $Res Function(_$AppInitializationStateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? isInitializationCompleted = null,
    Object? initMdmsModel = freezed,
    Object? stateInfoListModel = freezed,
    Object? digitRowCardItems = freezed,
  }) {
    return _then(_$AppInitializationStateImpl(
      isInitializationCompleted: null == isInitializationCompleted
          ? _value.isInitializationCompleted
          : isInitializationCompleted // ignore: cast_nullable_to_non_nullable
              as bool,
      initMdmsModel: freezed == initMdmsModel
          ? _value.initMdmsModel
          : initMdmsModel // ignore: cast_nullable_to_non_nullable
              as InitMdmsModel?,
      stateInfoListModel: freezed == stateInfoListModel
          ? _value.stateInfoListModel
          : stateInfoListModel // ignore: cast_nullable_to_non_nullable
              as StateInfoListModel?,
      digitRowCardItems: freezed == digitRowCardItems
          ? _value._digitRowCardItems
          : digitRowCardItems // ignore: cast_nullable_to_non_nullable
              as List<DigitRowCardModel>?,
    ));
  }
}

/// @nodoc

class _$AppInitializationStateImpl extends _AppInitializationState
    with DiagnosticableTreeMixin {
  const _$AppInitializationStateImpl(
      {this.isInitializationCompleted = false,
      this.initMdmsModel,
      this.stateInfoListModel,
      final List<DigitRowCardModel>? digitRowCardItems})
      : _digitRowCardItems = digitRowCardItems,
        super._();

  @override
  @JsonKey()
  final bool isInitializationCompleted;
  @override
  final InitMdmsModel? initMdmsModel;
  @override
  final StateInfoListModel? stateInfoListModel;
  final List<DigitRowCardModel>? _digitRowCardItems;
  @override
  List<DigitRowCardModel>? get digitRowCardItems {
    final value = _digitRowCardItems;
    if (value == null) return null;
    if (_digitRowCardItems is EqualUnmodifiableListView)
      return _digitRowCardItems;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AppInitializationState(isInitializationCompleted: $isInitializationCompleted, initMdmsModel: $initMdmsModel, stateInfoListModel: $stateInfoListModel, digitRowCardItems: $digitRowCardItems)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AppInitializationState'))
      ..add(DiagnosticsProperty(
          'isInitializationCompleted', isInitializationCompleted))
      ..add(DiagnosticsProperty('initMdmsModel', initMdmsModel))
      ..add(DiagnosticsProperty('stateInfoListModel', stateInfoListModel))
      ..add(DiagnosticsProperty('digitRowCardItems', digitRowCardItems));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AppInitializationStateImpl &&
            (identical(other.isInitializationCompleted,
                    isInitializationCompleted) ||
                other.isInitializationCompleted == isInitializationCompleted) &&
            (identical(other.initMdmsModel, initMdmsModel) ||
                other.initMdmsModel == initMdmsModel) &&
            (identical(other.stateInfoListModel, stateInfoListModel) ||
                other.stateInfoListModel == stateInfoListModel) &&
            const DeepCollectionEquality()
                .equals(other._digitRowCardItems, _digitRowCardItems));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      isInitializationCompleted,
      initMdmsModel,
      stateInfoListModel,
      const DeepCollectionEquality().hash(_digitRowCardItems));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AppInitializationStateImplCopyWith<_$AppInitializationStateImpl>
      get copyWith => __$$AppInitializationStateImplCopyWithImpl<
          _$AppInitializationStateImpl>(this, _$identity);
}

abstract class _AppInitializationState extends AppInitializationState {
  const factory _AppInitializationState(
          {final bool isInitializationCompleted,
          final InitMdmsModel? initMdmsModel,
          final StateInfoListModel? stateInfoListModel,
          final List<DigitRowCardModel>? digitRowCardItems}) =
      _$AppInitializationStateImpl;
  const _AppInitializationState._() : super._();

  @override
  bool get isInitializationCompleted;
  @override
  InitMdmsModel? get initMdmsModel;
  @override
  StateInfoListModel? get stateInfoListModel;
  @override
  List<DigitRowCardModel>? get digitRowCardItems;
  @override
  @JsonKey(ignore: true)
  _$$AppInitializationStateImplCopyWith<_$AppInitializationStateImpl>
      get copyWith => throw _privateConstructorUsedError;
}
