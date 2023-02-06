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
    required TResult Function() search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? search,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceProjectsEvent value) search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
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
}

/// @nodoc

class _$SearchAttendanceProjectsEvent
    with DiagnosticableTreeMixin
    implements SearchAttendanceProjectsEvent {
  const _$SearchAttendanceProjectsEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchEvent.search()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'AttendanceProjectsSearchEvent.search'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchAttendanceProjectsEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() search,
  }) {
    return search();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? search,
  }) {
    return search?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? search,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceProjectsEvent value) search,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
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
  const factory SearchAttendanceProjectsEvent() =
      _$SearchAttendanceProjectsEvent;
}

/// @nodoc
mixin _$AttendanceProjectsSearchState {
  bool get loading => throw _privateConstructorUsedError;
  AttendanceRegistersModel? get attendanceRegistersModel =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendanceProjectsSearchStateCopyWith<AttendanceProjectsSearchState>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceProjectsSearchStateCopyWith<$Res> {
  factory $AttendanceProjectsSearchStateCopyWith(
          AttendanceProjectsSearchState value,
          $Res Function(AttendanceProjectsSearchState) then) =
      _$AttendanceProjectsSearchStateCopyWithImpl<$Res,
          AttendanceProjectsSearchState>;
  @useResult
  $Res call({bool loading, AttendanceRegistersModel? attendanceRegistersModel});

  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel;
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

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendanceRegistersModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendanceRegistersModel: freezed == attendanceRegistersModel
          ? _value.attendanceRegistersModel
          : attendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel {
    if (_value.attendanceRegistersModel == null) {
      return null;
    }

    return $AttendanceRegistersModelCopyWith<$Res>(
        _value.attendanceRegistersModel!, (value) {
      return _then(_value.copyWith(attendanceRegistersModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AttendanceProjectsSearchStateCopyWith<$Res>
    implements $AttendanceProjectsSearchStateCopyWith<$Res> {
  factory _$$_AttendanceProjectsSearchStateCopyWith(
          _$_AttendanceProjectsSearchState value,
          $Res Function(_$_AttendanceProjectsSearchState) then) =
      __$$_AttendanceProjectsSearchStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool loading, AttendanceRegistersModel? attendanceRegistersModel});

  @override
  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel;
}

/// @nodoc
class __$$_AttendanceProjectsSearchStateCopyWithImpl<$Res>
    extends _$AttendanceProjectsSearchStateCopyWithImpl<$Res,
        _$_AttendanceProjectsSearchState>
    implements _$$_AttendanceProjectsSearchStateCopyWith<$Res> {
  __$$_AttendanceProjectsSearchStateCopyWithImpl(
      _$_AttendanceProjectsSearchState _value,
      $Res Function(_$_AttendanceProjectsSearchState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendanceRegistersModel = freezed,
  }) {
    return _then(_$_AttendanceProjectsSearchState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendanceRegistersModel: freezed == attendanceRegistersModel
          ? _value.attendanceRegistersModel
          : attendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ));
  }
}

/// @nodoc

class _$_AttendanceProjectsSearchState extends _AttendanceProjectsSearchState
    with DiagnosticableTreeMixin {
  const _$_AttendanceProjectsSearchState(
      {this.loading = false, this.attendanceRegistersModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final AttendanceRegistersModel? attendanceRegistersModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchState(loading: $loading, attendanceRegistersModel: $attendanceRegistersModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceProjectsSearchState'))
      ..add(DiagnosticsProperty('loading', loading))
      ..add(DiagnosticsProperty(
          'attendanceRegistersModel', attendanceRegistersModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceProjectsSearchState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(
                    other.attendanceRegistersModel, attendanceRegistersModel) ||
                other.attendanceRegistersModel == attendanceRegistersModel));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, loading, attendanceRegistersModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceProjectsSearchStateCopyWith<_$_AttendanceProjectsSearchState>
      get copyWith => __$$_AttendanceProjectsSearchStateCopyWithImpl<
          _$_AttendanceProjectsSearchState>(this, _$identity);
}

abstract class _AttendanceProjectsSearchState
    extends AttendanceProjectsSearchState {
  const factory _AttendanceProjectsSearchState(
          {final bool loading,
          final AttendanceRegistersModel? attendanceRegistersModel}) =
      _$_AttendanceProjectsSearchState;
  const _AttendanceProjectsSearchState._() : super._();

  @override
  bool get loading;
  @override
  AttendanceRegistersModel? get attendanceRegistersModel;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceProjectsSearchStateCopyWith<_$_AttendanceProjectsSearchState>
      get copyWith => throw _privateConstructorUsedError;
}
