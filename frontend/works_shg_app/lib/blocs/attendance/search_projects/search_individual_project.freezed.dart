// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'search_individual_project.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$AttendanceIndividualProjectSearchEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id, String tenantId) individualSearch,
    required TResult Function(String registerNumber, String tenantId)
        individualRegisterSearch,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id, String tenantId)? individualSearch,
    TResult? Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id, String tenantId)? individualSearch,
    TResult Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualAttendanceProjectEvent value)
        individualSearch,
    required TResult Function(SearchIndividualAttendanceRegisterEvent value)
        individualRegisterSearch,
    required TResult Function(DisposeIndividualAttendanceRegisterEvent value)
        dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult? Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult? Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceIndividualProjectSearchEventCopyWith<$Res> {
  factory $AttendanceIndividualProjectSearchEventCopyWith(
          AttendanceIndividualProjectSearchEvent value,
          $Res Function(AttendanceIndividualProjectSearchEvent) then) =
      _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
          AttendanceIndividualProjectSearchEvent>;
}

/// @nodoc
class _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
        $Val extends AttendanceIndividualProjectSearchEvent>
    implements $AttendanceIndividualProjectSearchEventCopyWith<$Res> {
  _$AttendanceIndividualProjectSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchIndividualAttendanceProjectEventCopyWith<$Res> {
  factory _$$SearchIndividualAttendanceProjectEventCopyWith(
          _$SearchIndividualAttendanceProjectEvent value,
          $Res Function(_$SearchIndividualAttendanceProjectEvent) then) =
      __$$SearchIndividualAttendanceProjectEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String id, String tenantId});
}

/// @nodoc
class __$$SearchIndividualAttendanceProjectEventCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
        _$SearchIndividualAttendanceProjectEvent>
    implements _$$SearchIndividualAttendanceProjectEventCopyWith<$Res> {
  __$$SearchIndividualAttendanceProjectEventCopyWithImpl(
      _$SearchIndividualAttendanceProjectEvent _value,
      $Res Function(_$SearchIndividualAttendanceProjectEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? tenantId = null,
  }) {
    return _then(_$SearchIndividualAttendanceProjectEvent(
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchIndividualAttendanceProjectEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualAttendanceProjectEvent {
  const _$SearchIndividualAttendanceProjectEvent(
      {this.id = '', this.tenantId = ''});

  @override
  @JsonKey()
  final String id;
  @override
  @JsonKey()
  final String tenantId;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceIndividualProjectSearchEvent.individualSearch(id: $id, tenantId: $tenantId)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'AttendanceIndividualProjectSearchEvent.individualSearch'))
      ..add(DiagnosticsProperty('id', id))
      ..add(DiagnosticsProperty('tenantId', tenantId));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualAttendanceProjectEvent &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @override
  int get hashCode => Object.hash(runtimeType, id, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualAttendanceProjectEventCopyWith<
          _$SearchIndividualAttendanceProjectEvent>
      get copyWith => __$$SearchIndividualAttendanceProjectEventCopyWithImpl<
          _$SearchIndividualAttendanceProjectEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id, String tenantId) individualSearch,
    required TResult Function(String registerNumber, String tenantId)
        individualRegisterSearch,
    required TResult Function() dispose,
  }) {
    return individualSearch(id, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id, String tenantId)? individualSearch,
    TResult? Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult? Function()? dispose,
  }) {
    return individualSearch?.call(id, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id, String tenantId)? individualSearch,
    TResult Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(id, tenantId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualAttendanceProjectEvent value)
        individualSearch,
    required TResult Function(SearchIndividualAttendanceRegisterEvent value)
        individualRegisterSearch,
    required TResult Function(DisposeIndividualAttendanceRegisterEvent value)
        dispose,
  }) {
    return individualSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult? Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult? Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
  }) {
    return individualSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualAttendanceProjectEvent
    implements AttendanceIndividualProjectSearchEvent {
  const factory SearchIndividualAttendanceProjectEvent(
      {final String id,
      final String tenantId}) = _$SearchIndividualAttendanceProjectEvent;

  String get id;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$SearchIndividualAttendanceProjectEventCopyWith<
          _$SearchIndividualAttendanceProjectEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchIndividualAttendanceRegisterEventCopyWith<$Res> {
  factory _$$SearchIndividualAttendanceRegisterEventCopyWith(
          _$SearchIndividualAttendanceRegisterEvent value,
          $Res Function(_$SearchIndividualAttendanceRegisterEvent) then) =
      __$$SearchIndividualAttendanceRegisterEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String registerNumber, String tenantId});
}

/// @nodoc
class __$$SearchIndividualAttendanceRegisterEventCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
        _$SearchIndividualAttendanceRegisterEvent>
    implements _$$SearchIndividualAttendanceRegisterEventCopyWith<$Res> {
  __$$SearchIndividualAttendanceRegisterEventCopyWithImpl(
      _$SearchIndividualAttendanceRegisterEvent _value,
      $Res Function(_$SearchIndividualAttendanceRegisterEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? registerNumber = null,
    Object? tenantId = null,
  }) {
    return _then(_$SearchIndividualAttendanceRegisterEvent(
      registerNumber: null == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchIndividualAttendanceRegisterEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualAttendanceRegisterEvent {
  const _$SearchIndividualAttendanceRegisterEvent(
      {this.registerNumber = '', this.tenantId = ''});

  @override
  @JsonKey()
  final String registerNumber;
  @override
  @JsonKey()
  final String tenantId;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceIndividualProjectSearchEvent.individualRegisterSearch(registerNumber: $registerNumber, tenantId: $tenantId)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type',
          'AttendanceIndividualProjectSearchEvent.individualRegisterSearch'))
      ..add(DiagnosticsProperty('registerNumber', registerNumber))
      ..add(DiagnosticsProperty('tenantId', tenantId));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualAttendanceRegisterEvent &&
            (identical(other.registerNumber, registerNumber) ||
                other.registerNumber == registerNumber) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @override
  int get hashCode => Object.hash(runtimeType, registerNumber, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualAttendanceRegisterEventCopyWith<
          _$SearchIndividualAttendanceRegisterEvent>
      get copyWith => __$$SearchIndividualAttendanceRegisterEventCopyWithImpl<
          _$SearchIndividualAttendanceRegisterEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id, String tenantId) individualSearch,
    required TResult Function(String registerNumber, String tenantId)
        individualRegisterSearch,
    required TResult Function() dispose,
  }) {
    return individualRegisterSearch(registerNumber, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id, String tenantId)? individualSearch,
    TResult? Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult? Function()? dispose,
  }) {
    return individualRegisterSearch?.call(registerNumber, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id, String tenantId)? individualSearch,
    TResult Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (individualRegisterSearch != null) {
      return individualRegisterSearch(registerNumber, tenantId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualAttendanceProjectEvent value)
        individualSearch,
    required TResult Function(SearchIndividualAttendanceRegisterEvent value)
        individualRegisterSearch,
    required TResult Function(DisposeIndividualAttendanceRegisterEvent value)
        dispose,
  }) {
    return individualRegisterSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult? Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult? Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
  }) {
    return individualRegisterSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (individualRegisterSearch != null) {
      return individualRegisterSearch(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualAttendanceRegisterEvent
    implements AttendanceIndividualProjectSearchEvent {
  const factory SearchIndividualAttendanceRegisterEvent(
      {final String registerNumber,
      final String tenantId}) = _$SearchIndividualAttendanceRegisterEvent;

  String get registerNumber;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$SearchIndividualAttendanceRegisterEventCopyWith<
          _$SearchIndividualAttendanceRegisterEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeIndividualAttendanceRegisterEventCopyWith<$Res> {
  factory _$$DisposeIndividualAttendanceRegisterEventCopyWith(
          _$DisposeIndividualAttendanceRegisterEvent value,
          $Res Function(_$DisposeIndividualAttendanceRegisterEvent) then) =
      __$$DisposeIndividualAttendanceRegisterEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeIndividualAttendanceRegisterEventCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
        _$DisposeIndividualAttendanceRegisterEvent>
    implements _$$DisposeIndividualAttendanceRegisterEventCopyWith<$Res> {
  __$$DisposeIndividualAttendanceRegisterEventCopyWithImpl(
      _$DisposeIndividualAttendanceRegisterEvent _value,
      $Res Function(_$DisposeIndividualAttendanceRegisterEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeIndividualAttendanceRegisterEvent
    with DiagnosticableTreeMixin
    implements DisposeIndividualAttendanceRegisterEvent {
  const _$DisposeIndividualAttendanceRegisterEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceIndividualProjectSearchEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty(
        'type', 'AttendanceIndividualProjectSearchEvent.dispose'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeIndividualAttendanceRegisterEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id, String tenantId) individualSearch,
    required TResult Function(String registerNumber, String tenantId)
        individualRegisterSearch,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id, String tenantId)? individualSearch,
    TResult? Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id, String tenantId)? individualSearch,
    TResult Function(String registerNumber, String tenantId)?
        individualRegisterSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualAttendanceProjectEvent value)
        individualSearch,
    required TResult Function(SearchIndividualAttendanceRegisterEvent value)
        individualRegisterSearch,
    required TResult Function(DisposeIndividualAttendanceRegisterEvent value)
        dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult? Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult? Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    TResult Function(SearchIndividualAttendanceRegisterEvent value)?
        individualRegisterSearch,
    TResult Function(DisposeIndividualAttendanceRegisterEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeIndividualAttendanceRegisterEvent
    implements AttendanceIndividualProjectSearchEvent {
  const factory DisposeIndividualAttendanceRegisterEvent() =
      _$DisposeIndividualAttendanceRegisterEvent;
}

/// @nodoc
mixin _$AttendanceIndividualProjectSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_IndividualLoaded value) loaded,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_IndividualLoaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_IndividualLoaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceIndividualProjectSearchStateCopyWith<$Res> {
  factory $AttendanceIndividualProjectSearchStateCopyWith(
          AttendanceIndividualProjectSearchState value,
          $Res Function(AttendanceIndividualProjectSearchState) then) =
      _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
          AttendanceIndividualProjectSearchState>;
}

/// @nodoc
class _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        $Val extends AttendanceIndividualProjectSearchState>
    implements $AttendanceIndividualProjectSearchStateCopyWith<$Res> {
  _$AttendanceIndividualProjectSearchStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$_InitialCopyWith<$Res> {
  factory _$$_InitialCopyWith(
          _$_Initial value, $Res Function(_$_Initial) then) =
      __$$_InitialCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_InitialCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        _$_Initial> implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial with DiagnosticableTreeMixin {
  const _$_Initial() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceIndividualProjectSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty(
        'type', 'AttendanceIndividualProjectSearchState.initial'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Initial);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_IndividualLoaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_IndividualLoaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_IndividualLoaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends AttendanceIndividualProjectSearchState {
  const factory _Initial() = _$_Initial;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$_LoadingCopyWith<$Res> {
  factory _$$_LoadingCopyWith(
          _$_Loading value, $Res Function(_$_Loading) then) =
      __$$_LoadingCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_LoadingCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        _$_Loading> implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading with DiagnosticableTreeMixin {
  const _$_Loading() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceIndividualProjectSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty(
        'type', 'AttendanceIndividualProjectSearchState.loading'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Loading);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_IndividualLoaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_IndividualLoaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_IndividualLoaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends AttendanceIndividualProjectSearchState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_IndividualLoadedCopyWith<$Res> {
  factory _$$_IndividualLoadedCopyWith(
          _$_IndividualLoaded value, $Res Function(_$_IndividualLoaded) then) =
      __$$_IndividualLoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({AttendanceRegistersModel? individualAttendanceRegisterModel});

  $AttendanceRegistersModelCopyWith<$Res>?
      get individualAttendanceRegisterModel;
}

/// @nodoc
class __$$_IndividualLoadedCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        _$_IndividualLoaded> implements _$$_IndividualLoadedCopyWith<$Res> {
  __$$_IndividualLoadedCopyWithImpl(
      _$_IndividualLoaded _value, $Res Function(_$_IndividualLoaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualAttendanceRegisterModel = freezed,
  }) {
    return _then(_$_IndividualLoaded(
      freezed == individualAttendanceRegisterModel
          ? _value.individualAttendanceRegisterModel
          : individualAttendanceRegisterModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegistersModelCopyWith<$Res>?
      get individualAttendanceRegisterModel {
    if (_value.individualAttendanceRegisterModel == null) {
      return null;
    }

    return $AttendanceRegistersModelCopyWith<$Res>(
        _value.individualAttendanceRegisterModel!, (value) {
      return _then(_value.copyWith(individualAttendanceRegisterModel: value));
    });
  }
}

/// @nodoc

class _$_IndividualLoaded extends _IndividualLoaded
    with DiagnosticableTreeMixin {
  const _$_IndividualLoaded(this.individualAttendanceRegisterModel) : super._();

  @override
  final AttendanceRegistersModel? individualAttendanceRegisterModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceIndividualProjectSearchState.loaded(individualAttendanceRegisterModel: $individualAttendanceRegisterModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'AttendanceIndividualProjectSearchState.loaded'))
      ..add(DiagnosticsProperty('individualAttendanceRegisterModel',
          individualAttendanceRegisterModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_IndividualLoaded &&
            (identical(other.individualAttendanceRegisterModel,
                    individualAttendanceRegisterModel) ||
                other.individualAttendanceRegisterModel ==
                    individualAttendanceRegisterModel));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, individualAttendanceRegisterModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_IndividualLoadedCopyWith<_$_IndividualLoaded> get copyWith =>
      __$$_IndividualLoadedCopyWithImpl<_$_IndividualLoaded>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(individualAttendanceRegisterModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(individualAttendanceRegisterModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(individualAttendanceRegisterModel);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_IndividualLoaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_IndividualLoaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_IndividualLoaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(this);
    }
    return orElse();
  }
}

abstract class _IndividualLoaded
    extends AttendanceIndividualProjectSearchState {
  const factory _IndividualLoaded(
          final AttendanceRegistersModel? individualAttendanceRegisterModel) =
      _$_IndividualLoaded;
  const _IndividualLoaded._() : super._();

  AttendanceRegistersModel? get individualAttendanceRegisterModel;
  @JsonKey(ignore: true)
  _$$_IndividualLoadedCopyWith<_$_IndividualLoaded> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$_ErrorCopyWith<$Res> {
  factory _$$_ErrorCopyWith(_$_Error value, $Res Function(_$_Error) then) =
      __$$_ErrorCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$_ErrorCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res, _$_Error>
    implements _$$_ErrorCopyWith<$Res> {
  __$$_ErrorCopyWithImpl(_$_Error _value, $Res Function(_$_Error) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$_Error(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$_Error extends _Error with DiagnosticableTreeMixin {
  const _$_Error(this.error) : super._();

  @override
  final String? error;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceIndividualProjectSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'AttendanceIndividualProjectSearchState.error'))
      ..add(DiagnosticsProperty('error', error));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Error &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      __$$_ErrorCopyWithImpl<_$_Error>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            AttendanceRegistersModel? individualAttendanceRegisterModel)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this.error);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_IndividualLoaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return error(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_IndividualLoaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return error?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_IndividualLoaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this);
    }
    return orElse();
  }
}

abstract class _Error extends AttendanceIndividualProjectSearchState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
