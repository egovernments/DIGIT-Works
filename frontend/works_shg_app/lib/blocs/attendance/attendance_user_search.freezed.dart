// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'attendance_user_search.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$AttendanceUserSearchEvent {
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
    required TResult Function(SearchAttendanceUserEvent value) search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceUserEvent value)? search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceUserEvent value)? search,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceUserSearchEventCopyWith<$Res> {
  factory $AttendanceUserSearchEventCopyWith(AttendanceUserSearchEvent value,
          $Res Function(AttendanceUserSearchEvent) then) =
      _$AttendanceUserSearchEventCopyWithImpl<$Res, AttendanceUserSearchEvent>;
}

/// @nodoc
class _$AttendanceUserSearchEventCopyWithImpl<$Res,
        $Val extends AttendanceUserSearchEvent>
    implements $AttendanceUserSearchEventCopyWith<$Res> {
  _$AttendanceUserSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchAttendanceUserEventCopyWith<$Res> {
  factory _$$SearchAttendanceUserEventCopyWith(
          _$SearchAttendanceUserEvent value,
          $Res Function(_$SearchAttendanceUserEvent) then) =
      __$$SearchAttendanceUserEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$SearchAttendanceUserEventCopyWithImpl<$Res>
    extends _$AttendanceUserSearchEventCopyWithImpl<$Res,
        _$SearchAttendanceUserEvent>
    implements _$$SearchAttendanceUserEventCopyWith<$Res> {
  __$$SearchAttendanceUserEventCopyWithImpl(_$SearchAttendanceUserEvent _value,
      $Res Function(_$SearchAttendanceUserEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$SearchAttendanceUserEvent
    with DiagnosticableTreeMixin
    implements SearchAttendanceUserEvent {
  const _$SearchAttendanceUserEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceUserSearchEvent.search()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'AttendanceUserSearchEvent.search'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchAttendanceUserEvent);
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
    required TResult Function(SearchAttendanceUserEvent value) search,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceUserEvent value)? search,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceUserEvent value)? search,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class SearchAttendanceUserEvent implements AttendanceUserSearchEvent {
  const factory SearchAttendanceUserEvent() = _$SearchAttendanceUserEvent;
}

/// @nodoc
mixin _$AttendanceUserSearchState {
  bool get loading => throw _privateConstructorUsedError;
  UserSearchModel? get userSearchModel => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendanceUserSearchStateCopyWith<AttendanceUserSearchState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceUserSearchStateCopyWith<$Res> {
  factory $AttendanceUserSearchStateCopyWith(AttendanceUserSearchState value,
          $Res Function(AttendanceUserSearchState) then) =
      _$AttendanceUserSearchStateCopyWithImpl<$Res, AttendanceUserSearchState>;
  @useResult
  $Res call({bool loading, UserSearchModel? userSearchModel});

  $UserSearchModelCopyWith<$Res>? get userSearchModel;
}

/// @nodoc
class _$AttendanceUserSearchStateCopyWithImpl<$Res,
        $Val extends AttendanceUserSearchState>
    implements $AttendanceUserSearchStateCopyWith<$Res> {
  _$AttendanceUserSearchStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? userSearchModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      userSearchModel: freezed == userSearchModel
          ? _value.userSearchModel
          : userSearchModel // ignore: cast_nullable_to_non_nullable
              as UserSearchModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $UserSearchModelCopyWith<$Res>? get userSearchModel {
    if (_value.userSearchModel == null) {
      return null;
    }

    return $UserSearchModelCopyWith<$Res>(_value.userSearchModel!, (value) {
      return _then(_value.copyWith(userSearchModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_UserSearchStateCopyWith<$Res>
    implements $AttendanceUserSearchStateCopyWith<$Res> {
  factory _$$_UserSearchStateCopyWith(
          _$_UserSearchState value, $Res Function(_$_UserSearchState) then) =
      __$$_UserSearchStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool loading, UserSearchModel? userSearchModel});

  @override
  $UserSearchModelCopyWith<$Res>? get userSearchModel;
}

/// @nodoc
class __$$_UserSearchStateCopyWithImpl<$Res>
    extends _$AttendanceUserSearchStateCopyWithImpl<$Res, _$_UserSearchState>
    implements _$$_UserSearchStateCopyWith<$Res> {
  __$$_UserSearchStateCopyWithImpl(
      _$_UserSearchState _value, $Res Function(_$_UserSearchState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? userSearchModel = freezed,
  }) {
    return _then(_$_UserSearchState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      userSearchModel: freezed == userSearchModel
          ? _value.userSearchModel
          : userSearchModel // ignore: cast_nullable_to_non_nullable
              as UserSearchModel?,
    ));
  }
}

/// @nodoc

class _$_UserSearchState extends _UserSearchState with DiagnosticableTreeMixin {
  const _$_UserSearchState({this.loading = false, this.userSearchModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final UserSearchModel? userSearchModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceUserSearchState(loading: $loading, userSearchModel: $userSearchModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceUserSearchState'))
      ..add(DiagnosticsProperty('loading', loading))
      ..add(DiagnosticsProperty('userSearchModel', userSearchModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_UserSearchState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(other.userSearchModel, userSearchModel) ||
                other.userSearchModel == userSearchModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, loading, userSearchModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_UserSearchStateCopyWith<_$_UserSearchState> get copyWith =>
      __$$_UserSearchStateCopyWithImpl<_$_UserSearchState>(this, _$identity);
}

abstract class _UserSearchState extends AttendanceUserSearchState {
  const factory _UserSearchState(
      {final bool loading,
      final UserSearchModel? userSearchModel}) = _$_UserSearchState;
  const _UserSearchState._() : super._();

  @override
  bool get loading;
  @override
  UserSearchModel? get userSearchModel;
  @override
  @JsonKey(ignore: true)
  _$$_UserSearchStateCopyWith<_$_UserSearchState> get copyWith =>
      throw _privateConstructorUsedError;
}
