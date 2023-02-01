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
  String get id => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String id) search,
    required TResult Function(String id) individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id)? search,
    TResult? Function(String id)? individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id)? search,
    TResult Function(String id)? individualSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceProjectsEvent value) search,
    required TResult Function(SearchIndividualAttendanceProjectEvent value)
        individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
    TResult? Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
    TResult Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendanceProjectsSearchEventCopyWith<AttendanceProjectsSearchEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceProjectsSearchEventCopyWith<$Res> {
  factory $AttendanceProjectsSearchEventCopyWith(
          AttendanceProjectsSearchEvent value,
          $Res Function(AttendanceProjectsSearchEvent) then) =
      _$AttendanceProjectsSearchEventCopyWithImpl<$Res,
          AttendanceProjectsSearchEvent>;
  @useResult
  $Res call({String id});
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

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
  }) {
    return _then(_value.copyWith(
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$SearchAttendanceProjectsEventCopyWith<$Res>
    implements $AttendanceProjectsSearchEventCopyWith<$Res> {
  factory _$$SearchAttendanceProjectsEventCopyWith(
          _$SearchAttendanceProjectsEvent value,
          $Res Function(_$SearchAttendanceProjectsEvent) then) =
      __$$SearchAttendanceProjectsEventCopyWithImpl<$Res>;
  @override
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
    required TResult Function(String id) individualSearch,
  }) {
    return search(id);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id)? search,
    TResult? Function(String id)? individualSearch,
  }) {
    return search?.call(id);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id)? search,
    TResult Function(String id)? individualSearch,
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
    required TResult Function(SearchIndividualAttendanceProjectEvent value)
        individualSearch,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
    TResult? Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
    TResult Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
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

  @override
  String get id;
  @override
  @JsonKey(ignore: true)
  _$$SearchAttendanceProjectsEventCopyWith<_$SearchAttendanceProjectsEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchIndividualAttendanceProjectEventCopyWith<$Res>
    implements $AttendanceProjectsSearchEventCopyWith<$Res> {
  factory _$$SearchIndividualAttendanceProjectEventCopyWith(
          _$SearchIndividualAttendanceProjectEvent value,
          $Res Function(_$SearchIndividualAttendanceProjectEvent) then) =
      __$$SearchIndividualAttendanceProjectEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String id});
}

/// @nodoc
class __$$SearchIndividualAttendanceProjectEventCopyWithImpl<$Res>
    extends _$AttendanceProjectsSearchEventCopyWithImpl<$Res,
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
  }) {
    return _then(_$SearchIndividualAttendanceProjectEvent(
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchIndividualAttendanceProjectEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualAttendanceProjectEvent {
  const _$SearchIndividualAttendanceProjectEvent({this.id = ''});

  @override
  @JsonKey()
  final String id;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchEvent.individualSearch(id: $id)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'AttendanceProjectsSearchEvent.individualSearch'))
      ..add(DiagnosticsProperty('id', id));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualAttendanceProjectEvent &&
            (identical(other.id, id) || other.id == id));
  }

  @override
  int get hashCode => Object.hash(runtimeType, id);

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
    required TResult Function(String id) search,
    required TResult Function(String id) individualSearch,
  }) {
    return individualSearch(id);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String id)? search,
    TResult? Function(String id)? individualSearch,
  }) {
    return individualSearch?.call(id);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String id)? search,
    TResult Function(String id)? individualSearch,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(id);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceProjectsEvent value) search,
    required TResult Function(SearchIndividualAttendanceProjectEvent value)
        individualSearch,
  }) {
    return individualSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceProjectsEvent value)? search,
    TResult? Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
  }) {
    return individualSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceProjectsEvent value)? search,
    TResult Function(SearchIndividualAttendanceProjectEvent value)?
        individualSearch,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualAttendanceProjectEvent
    implements AttendanceProjectsSearchEvent {
  const factory SearchIndividualAttendanceProjectEvent({final String id}) =
      _$SearchIndividualAttendanceProjectEvent;

  @override
  String get id;
  @override
  @JsonKey(ignore: true)
  _$$SearchIndividualAttendanceProjectEventCopyWith<
          _$SearchIndividualAttendanceProjectEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$AttendanceProjectsSearchState {
  bool get loading => throw _privateConstructorUsedError;
  AttendanceRegistersModel? get attendanceRegistersModel =>
      throw _privateConstructorUsedError;
  AttendanceRegistersModel? get individualAttendanceRegisterModel =>
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
  $Res call(
      {bool loading,
      AttendanceRegistersModel? attendanceRegistersModel,
      AttendanceRegistersModel? individualAttendanceRegisterModel});

  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel;
  $AttendanceRegistersModelCopyWith<$Res>?
      get individualAttendanceRegisterModel;
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
    Object? individualAttendanceRegisterModel = freezed,
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
      individualAttendanceRegisterModel: freezed ==
              individualAttendanceRegisterModel
          ? _value.individualAttendanceRegisterModel
          : individualAttendanceRegisterModel // ignore: cast_nullable_to_non_nullable
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

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegistersModelCopyWith<$Res>?
      get individualAttendanceRegisterModel {
    if (_value.individualAttendanceRegisterModel == null) {
      return null;
    }

    return $AttendanceRegistersModelCopyWith<$Res>(
        _value.individualAttendanceRegisterModel!, (value) {
      return _then(
          _value.copyWith(individualAttendanceRegisterModel: value) as $Val);
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
  $Res call(
      {bool loading,
      AttendanceRegistersModel? attendanceRegistersModel,
      AttendanceRegistersModel? individualAttendanceRegisterModel});

  @override
  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel;
  @override
  $AttendanceRegistersModelCopyWith<$Res>?
      get individualAttendanceRegisterModel;
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
    Object? individualAttendanceRegisterModel = freezed,
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
      individualAttendanceRegisterModel: freezed ==
              individualAttendanceRegisterModel
          ? _value.individualAttendanceRegisterModel
          : individualAttendanceRegisterModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ));
  }
}

/// @nodoc

class _$_AttendanceProjectsSearchState extends _AttendanceProjectsSearchState
    with DiagnosticableTreeMixin {
  const _$_AttendanceProjectsSearchState(
      {this.loading = false,
      this.attendanceRegistersModel,
      this.individualAttendanceRegisterModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final AttendanceRegistersModel? attendanceRegistersModel;
  @override
  final AttendanceRegistersModel? individualAttendanceRegisterModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceProjectsSearchState(loading: $loading, attendanceRegistersModel: $attendanceRegistersModel, individualAttendanceRegisterModel: $individualAttendanceRegisterModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceProjectsSearchState'))
      ..add(DiagnosticsProperty('loading', loading))
      ..add(DiagnosticsProperty(
          'attendanceRegistersModel', attendanceRegistersModel))
      ..add(DiagnosticsProperty('individualAttendanceRegisterModel',
          individualAttendanceRegisterModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceProjectsSearchState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(
                    other.attendanceRegistersModel, attendanceRegistersModel) ||
                other.attendanceRegistersModel == attendanceRegistersModel) &&
            (identical(other.individualAttendanceRegisterModel,
                    individualAttendanceRegisterModel) ||
                other.individualAttendanceRegisterModel ==
                    individualAttendanceRegisterModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, loading,
      attendanceRegistersModel, individualAttendanceRegisterModel);

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
          final AttendanceRegistersModel? attendanceRegistersModel,
          final AttendanceRegistersModel? individualAttendanceRegisterModel}) =
      _$_AttendanceProjectsSearchState;
  const _AttendanceProjectsSearchState._() : super._();

  @override
  bool get loading;
  @override
  AttendanceRegistersModel? get attendanceRegistersModel;
  @override
  AttendanceRegistersModel? get individualAttendanceRegisterModel;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceProjectsSearchStateCopyWith<_$_AttendanceProjectsSearchState>
      get copyWith => throw _privateConstructorUsedError;
}
