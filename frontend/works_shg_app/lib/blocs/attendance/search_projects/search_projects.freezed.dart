// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'search_projects.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$AttendanceProjectsSearchEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id) search,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id)? search,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id)? search,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceProjectsEvent value) search,
    required TResult Function(DisposeAttendanceRegisterEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
    TResult? Function(DisposeAttendanceRegisterEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
    TResult Function(DisposeAttendanceRegisterEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceProjectsSearchEventCopyWith<$Res> {
  factory $AttendanceProjectsSearchEventCopyWith(
          AttendanceProjectsSearchEvent value,
          $Res Function(AttendanceProjectsSearchEvent) then) =
      _$AttendanceProjectsSearchEventCopyWithImpl<$Res,
          AttendanceProjectsSearchEvent>;
}

/// @nodoc
class _$AttendanceProjectsSearchEventCopyWithImpl<$Res,
        $Val extends AttendanceProjectsSearchEvent>
    implements $AttendanceProjectsSearchEventCopyWith<$Res> {
  _$AttendanceProjectsSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchAttendanceProjectsEventCopyWith<$Res> {
  factory _$$SearchAttendanceProjectsEventCopyWith(
          _$SearchAttendanceProjectsEvent value,
          $Res Function(_$SearchAttendanceProjectsEvent) then) =
      __$$SearchAttendanceProjectsEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String id});
}

/// @nodoc
class __$$SearchAttendanceProjectsEventCopyWithImpl<$Res>
    extends _$AttendanceProjectsSearchEventCopyWithImpl<$Res,
        _$SearchAttendanceProjectsEvent>
    implements _$$SearchAttendanceProjectsEventCopyWith<$Res> {
  __$$SearchAttendanceProjectsEventCopyWithImpl(
      _$SearchAttendanceProjectsEvent _value,
      $Res Function(_$SearchAttendanceProjectsEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
  }) {
    return _then(_$SearchAttendanceProjectsEvent(
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchAttendanceProjectsEvent
    with DiagnosticableTreeMixin
    implements SearchAttendanceProjectsEvent {
  const _$SearchAttendanceProjectsEvent({this.id = ''});

  @override
  @JsonKey()
  final String id;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchEvent.search(id: $id)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceProjectsSearchEvent.search'))
      ..add(DiagnosticsProperty('id', id));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchAttendanceProjectsEvent &&
            (identical(other.id, id) || other.id == id));
  }

  @override
  int get hashCode => Object.hash(runtimeType, id);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchAttendanceProjectsEventCopyWith<_$SearchAttendanceProjectsEvent>
      get copyWith => __$$SearchAttendanceProjectsEventCopyWithImpl<
          _$SearchAttendanceProjectsEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id) search,
    required TResult Function() dispose,
  }) {
    return search(id);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id)? search,
    TResult? Function()? dispose,
  }) {
    return search?.call(id);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id)? search,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(id);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceProjectsEvent value) search,
    required TResult Function(DisposeAttendanceRegisterEvent value) dispose,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
    TResult? Function(DisposeAttendanceRegisterEvent value)? dispose,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
    TResult Function(DisposeAttendanceRegisterEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class SearchAttendanceProjectsEvent
    implements AttendanceProjectsSearchEvent {
  const factory SearchAttendanceProjectsEvent({final String id}) =
      _$SearchAttendanceProjectsEvent;

  String get id;
  @JsonKey(ignore: true)
  _$$SearchAttendanceProjectsEventCopyWith<_$SearchAttendanceProjectsEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeAttendanceRegisterEventCopyWith<$Res> {
  factory _$$DisposeAttendanceRegisterEventCopyWith(
          _$DisposeAttendanceRegisterEvent value,
          $Res Function(_$DisposeAttendanceRegisterEvent) then) =
      __$$DisposeAttendanceRegisterEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeAttendanceRegisterEventCopyWithImpl<$Res>
    extends _$AttendanceProjectsSearchEventCopyWithImpl<$Res,
        _$DisposeAttendanceRegisterEvent>
    implements _$$DisposeAttendanceRegisterEventCopyWith<$Res> {
  __$$DisposeAttendanceRegisterEventCopyWithImpl(
      _$DisposeAttendanceRegisterEvent _value,
      $Res Function(_$DisposeAttendanceRegisterEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeAttendanceRegisterEvent
    with DiagnosticableTreeMixin
    implements DisposeAttendanceRegisterEvent {
  const _$DisposeAttendanceRegisterEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'AttendanceProjectsSearchEvent.dispose'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeAttendanceRegisterEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id) search,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id)? search,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id)? search,
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
    required TResult Function(SearchAttendanceProjectsEvent value) search,
    required TResult Function(DisposeAttendanceRegisterEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
    TResult? Function(DisposeAttendanceRegisterEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
    TResult Function(DisposeAttendanceRegisterEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeAttendanceRegisterEvent
    implements AttendanceProjectsSearchEvent {
  const factory DisposeAttendanceRegisterEvent() =
      _$DisposeAttendanceRegisterEvent;
}

/// @nodoc
mixin _$AttendanceProjectsSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            AttendanceRegistersModel? attendanceRegistersModel)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(AttendanceRegistersModel? attendanceRegistersModel)?
        loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(AttendanceRegistersModel? attendanceRegistersModel)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceProjectsSearchStateCopyWith<$Res> {
  factory $AttendanceProjectsSearchStateCopyWith(
          AttendanceProjectsSearchState value,
          $Res Function(AttendanceProjectsSearchState) then) =
      _$AttendanceProjectsSearchStateCopyWithImpl<$Res,
          AttendanceProjectsSearchState>;
}

/// @nodoc
class _$AttendanceProjectsSearchStateCopyWithImpl<$Res,
        $Val extends AttendanceProjectsSearchState>
    implements $AttendanceProjectsSearchStateCopyWith<$Res> {
  _$AttendanceProjectsSearchStateCopyWithImpl(this._value, this._then);

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
    extends _$AttendanceProjectsSearchStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial with DiagnosticableTreeMixin {
  const _$_Initial() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'AttendanceProjectsSearchState.initial'));
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
            AttendanceRegistersModel? attendanceRegistersModel)
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
    TResult? Function(AttendanceRegistersModel? attendanceRegistersModel)?
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
    TResult Function(AttendanceRegistersModel? attendanceRegistersModel)?
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
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends AttendanceProjectsSearchState {
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
    extends _$AttendanceProjectsSearchStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading with DiagnosticableTreeMixin {
  const _$_Loading() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'AttendanceProjectsSearchState.loading'));
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
            AttendanceRegistersModel? attendanceRegistersModel)
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
    TResult? Function(AttendanceRegistersModel? attendanceRegistersModel)?
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
    TResult Function(AttendanceRegistersModel? attendanceRegistersModel)?
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
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends AttendanceProjectsSearchState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({AttendanceRegistersModel? attendanceRegistersModel});

  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel;
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$AttendanceProjectsSearchStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceRegistersModel = freezed,
  }) {
    return _then(_$_Loaded(
      freezed == attendanceRegistersModel
          ? _value.attendanceRegistersModel
          : attendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel {
    if (_value.attendanceRegistersModel == null) {
      return null;
    }

    return $AttendanceRegistersModelCopyWith<$Res>(
        _value.attendanceRegistersModel!, (value) {
      return _then(_value.copyWith(attendanceRegistersModel: value));
    });
  }
}

/// @nodoc

class _$_Loaded extends _Loaded with DiagnosticableTreeMixin {
  const _$_Loaded(this.attendanceRegistersModel) : super._();

  @override
  final AttendanceRegistersModel? attendanceRegistersModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchState.loaded(attendanceRegistersModel: $attendanceRegistersModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceProjectsSearchState.loaded'))
      ..add(DiagnosticsProperty(
          'attendanceRegistersModel', attendanceRegistersModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(
                    other.attendanceRegistersModel, attendanceRegistersModel) ||
                other.attendanceRegistersModel == attendanceRegistersModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, attendanceRegistersModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      __$$_LoadedCopyWithImpl<_$_Loaded>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            AttendanceRegistersModel? attendanceRegistersModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(attendanceRegistersModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(AttendanceRegistersModel? attendanceRegistersModel)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(attendanceRegistersModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(AttendanceRegistersModel? attendanceRegistersModel)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(attendanceRegistersModel);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(this);
    }
    return orElse();
  }
}

abstract class _Loaded extends AttendanceProjectsSearchState {
  const factory _Loaded(
      final AttendanceRegistersModel? attendanceRegistersModel) = _$_Loaded;
  const _Loaded._() : super._();

  AttendanceRegistersModel? get attendanceRegistersModel;
  @JsonKey(ignore: true)
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
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
    extends _$AttendanceProjectsSearchStateCopyWithImpl<$Res, _$_Error>
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
    return 'AttendanceProjectsSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceProjectsSearchState.error'))
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
            AttendanceRegistersModel? attendanceRegistersModel)
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
    TResult? Function(AttendanceRegistersModel? attendanceRegistersModel)?
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
    TResult Function(AttendanceRegistersModel? attendanceRegistersModel)?
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
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return error(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return error?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this);
    }
    return orElse();
  }
}

abstract class _Error extends AttendanceProjectsSearchState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
