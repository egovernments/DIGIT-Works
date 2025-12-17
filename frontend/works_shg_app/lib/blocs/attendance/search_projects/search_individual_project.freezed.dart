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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

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
abstract class _$$SearchIndividualAttendanceProjectEventImplCopyWith<$Res> {
  factory _$$SearchIndividualAttendanceProjectEventImplCopyWith(
          _$SearchIndividualAttendanceProjectEventImpl value,
          $Res Function(_$SearchIndividualAttendanceProjectEventImpl) then) =
      __$$SearchIndividualAttendanceProjectEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String id, String tenantId});
}

/// @nodoc
class __$$SearchIndividualAttendanceProjectEventImplCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
        _$SearchIndividualAttendanceProjectEventImpl>
    implements _$$SearchIndividualAttendanceProjectEventImplCopyWith<$Res> {
  __$$SearchIndividualAttendanceProjectEventImplCopyWithImpl(
      _$SearchIndividualAttendanceProjectEventImpl _value,
      $Res Function(_$SearchIndividualAttendanceProjectEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? tenantId = null,
  }) {
    return _then(_$SearchIndividualAttendanceProjectEventImpl(
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

class _$SearchIndividualAttendanceProjectEventImpl
    with DiagnosticableTreeMixin
    implements SearchIndividualAttendanceProjectEvent {
  const _$SearchIndividualAttendanceProjectEventImpl(
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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualAttendanceProjectEventImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @override
  int get hashCode => Object.hash(runtimeType, id, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualAttendanceProjectEventImplCopyWith<
          _$SearchIndividualAttendanceProjectEventImpl>
      get copyWith =>
          __$$SearchIndividualAttendanceProjectEventImplCopyWithImpl<
              _$SearchIndividualAttendanceProjectEventImpl>(this, _$identity);

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
      final String tenantId}) = _$SearchIndividualAttendanceProjectEventImpl;

  String get id;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$SearchIndividualAttendanceProjectEventImplCopyWith<
          _$SearchIndividualAttendanceProjectEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchIndividualAttendanceRegisterEventImplCopyWith<$Res> {
  factory _$$SearchIndividualAttendanceRegisterEventImplCopyWith(
          _$SearchIndividualAttendanceRegisterEventImpl value,
          $Res Function(_$SearchIndividualAttendanceRegisterEventImpl) then) =
      __$$SearchIndividualAttendanceRegisterEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String registerNumber, String tenantId});
}

/// @nodoc
class __$$SearchIndividualAttendanceRegisterEventImplCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
        _$SearchIndividualAttendanceRegisterEventImpl>
    implements _$$SearchIndividualAttendanceRegisterEventImplCopyWith<$Res> {
  __$$SearchIndividualAttendanceRegisterEventImplCopyWithImpl(
      _$SearchIndividualAttendanceRegisterEventImpl _value,
      $Res Function(_$SearchIndividualAttendanceRegisterEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? registerNumber = null,
    Object? tenantId = null,
  }) {
    return _then(_$SearchIndividualAttendanceRegisterEventImpl(
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

class _$SearchIndividualAttendanceRegisterEventImpl
    with DiagnosticableTreeMixin
    implements SearchIndividualAttendanceRegisterEvent {
  const _$SearchIndividualAttendanceRegisterEventImpl(
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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualAttendanceRegisterEventImpl &&
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
  _$$SearchIndividualAttendanceRegisterEventImplCopyWith<
          _$SearchIndividualAttendanceRegisterEventImpl>
      get copyWith =>
          __$$SearchIndividualAttendanceRegisterEventImplCopyWithImpl<
              _$SearchIndividualAttendanceRegisterEventImpl>(this, _$identity);

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
      final String tenantId}) = _$SearchIndividualAttendanceRegisterEventImpl;

  String get registerNumber;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$SearchIndividualAttendanceRegisterEventImplCopyWith<
          _$SearchIndividualAttendanceRegisterEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeIndividualAttendanceRegisterEventImplCopyWith<$Res> {
  factory _$$DisposeIndividualAttendanceRegisterEventImplCopyWith(
          _$DisposeIndividualAttendanceRegisterEventImpl value,
          $Res Function(_$DisposeIndividualAttendanceRegisterEventImpl) then) =
      __$$DisposeIndividualAttendanceRegisterEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeIndividualAttendanceRegisterEventImplCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchEventCopyWithImpl<$Res,
        _$DisposeIndividualAttendanceRegisterEventImpl>
    implements _$$DisposeIndividualAttendanceRegisterEventImplCopyWith<$Res> {
  __$$DisposeIndividualAttendanceRegisterEventImplCopyWithImpl(
      _$DisposeIndividualAttendanceRegisterEventImpl _value,
      $Res Function(_$DisposeIndividualAttendanceRegisterEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeIndividualAttendanceRegisterEventImpl
    with DiagnosticableTreeMixin
    implements DisposeIndividualAttendanceRegisterEvent {
  const _$DisposeIndividualAttendanceRegisterEventImpl();

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeIndividualAttendanceRegisterEventImpl);
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
      _$DisposeIndividualAttendanceRegisterEventImpl;
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
abstract class _$$InitialImplCopyWith<$Res> {
  factory _$$InitialImplCopyWith(
          _$InitialImpl value, $Res Function(_$InitialImpl) then) =
      __$$InitialImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$InitialImplCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        _$InitialImpl> implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial with DiagnosticableTreeMixin {
  const _$InitialImpl() : super._();

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$InitialImpl);
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
  const factory _Initial() = _$InitialImpl;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$LoadingImplCopyWith<$Res> {
  factory _$$LoadingImplCopyWith(
          _$LoadingImpl value, $Res Function(_$LoadingImpl) then) =
      __$$LoadingImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$LoadingImplCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        _$LoadingImpl> implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading with DiagnosticableTreeMixin {
  const _$LoadingImpl() : super._();

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$LoadingImpl);
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
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$IndividualLoadedImplCopyWith<$Res> {
  factory _$$IndividualLoadedImplCopyWith(_$IndividualLoadedImpl value,
          $Res Function(_$IndividualLoadedImpl) then) =
      __$$IndividualLoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({AttendanceRegistersModel? individualAttendanceRegisterModel});

  $AttendanceRegistersModelCopyWith<$Res>?
      get individualAttendanceRegisterModel;
}

/// @nodoc
class __$$IndividualLoadedImplCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        _$IndividualLoadedImpl>
    implements _$$IndividualLoadedImplCopyWith<$Res> {
  __$$IndividualLoadedImplCopyWithImpl(_$IndividualLoadedImpl _value,
      $Res Function(_$IndividualLoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualAttendanceRegisterModel = freezed,
  }) {
    return _then(_$IndividualLoadedImpl(
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

class _$IndividualLoadedImpl extends _IndividualLoaded
    with DiagnosticableTreeMixin {
  const _$IndividualLoadedImpl(this.individualAttendanceRegisterModel)
      : super._();

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$IndividualLoadedImpl &&
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
  _$$IndividualLoadedImplCopyWith<_$IndividualLoadedImpl> get copyWith =>
      __$$IndividualLoadedImplCopyWithImpl<_$IndividualLoadedImpl>(
          this, _$identity);

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
      _$IndividualLoadedImpl;
  const _IndividualLoaded._() : super._();

  AttendanceRegistersModel? get individualAttendanceRegisterModel;
  @JsonKey(ignore: true)
  _$$IndividualLoadedImplCopyWith<_$IndividualLoadedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ErrorImplCopyWith<$Res> {
  factory _$$ErrorImplCopyWith(
          _$ErrorImpl value, $Res Function(_$ErrorImpl) then) =
      __$$ErrorImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$AttendanceIndividualProjectSearchStateCopyWithImpl<$Res,
        _$ErrorImpl> implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$ErrorImpl(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$ErrorImpl extends _Error with DiagnosticableTreeMixin {
  const _$ErrorImpl(this.error) : super._();

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ErrorImpl &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      __$$ErrorImplCopyWithImpl<_$ErrorImpl>(this, _$identity);

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
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
