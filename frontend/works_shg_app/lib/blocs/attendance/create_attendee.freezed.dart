// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'create_attendee.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$AttendeeCreateEvent {
  List<Map<String, dynamic>> get attendeeList =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(List<Map<String, dynamic>> attendeeList) create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>> attendeeList)? create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>> attendeeList)? create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendeeEvent value) create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendeeEvent value)? create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendeeEvent value)? create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendeeCreateEventCopyWith<AttendeeCreateEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeeCreateEventCopyWith<$Res> {
  factory $AttendeeCreateEventCopyWith(
          AttendeeCreateEvent value, $Res Function(AttendeeCreateEvent) then) =
      _$AttendeeCreateEventCopyWithImpl<$Res, AttendeeCreateEvent>;
  @useResult
  $Res call({List<Map<String, dynamic>> attendeeList});
}

/// @nodoc
class _$AttendeeCreateEventCopyWithImpl<$Res, $Val extends AttendeeCreateEvent>
    implements $AttendeeCreateEventCopyWith<$Res> {
  _$AttendeeCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendeeList = null,
  }) {
    return _then(_value.copyWith(
      attendeeList: null == attendeeList
          ? _value.attendeeList
          : attendeeList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CreateAttendeeEventCopyWith<$Res>
    implements $AttendeeCreateEventCopyWith<$Res> {
  factory _$$CreateAttendeeEventCopyWith(_$CreateAttendeeEvent value,
          $Res Function(_$CreateAttendeeEvent) then) =
      __$$CreateAttendeeEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({List<Map<String, dynamic>> attendeeList});
}

/// @nodoc
class __$$CreateAttendeeEventCopyWithImpl<$Res>
    extends _$AttendeeCreateEventCopyWithImpl<$Res, _$CreateAttendeeEvent>
    implements _$$CreateAttendeeEventCopyWith<$Res> {
  __$$CreateAttendeeEventCopyWithImpl(
      _$CreateAttendeeEvent _value, $Res Function(_$CreateAttendeeEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendeeList = null,
  }) {
    return _then(_$CreateAttendeeEvent(
      attendeeList: null == attendeeList
          ? _value._attendeeList
          : attendeeList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>,
    ));
  }
}

/// @nodoc

class _$CreateAttendeeEvent implements CreateAttendeeEvent {
  const _$CreateAttendeeEvent(
      {required final List<Map<String, dynamic>> attendeeList})
      : _attendeeList = attendeeList;

  final List<Map<String, dynamic>> _attendeeList;
  @override
  List<Map<String, dynamic>> get attendeeList {
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_attendeeList);
  }

  @override
  String toString() {
    return 'AttendeeCreateEvent.create(attendeeList: $attendeeList)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateAttendeeEvent &&
            const DeepCollectionEquality()
                .equals(other._attendeeList, _attendeeList));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendeeList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateAttendeeEventCopyWith<_$CreateAttendeeEvent> get copyWith =>
      __$$CreateAttendeeEventCopyWithImpl<_$CreateAttendeeEvent>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(List<Map<String, dynamic>> attendeeList) create,
  }) {
    return create(attendeeList);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>> attendeeList)? create,
  }) {
    return create?.call(attendeeList);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>> attendeeList)? create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(attendeeList);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendeeEvent value) create,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendeeEvent value)? create,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendeeEvent value)? create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateAttendeeEvent implements AttendeeCreateEvent {
  const factory CreateAttendeeEvent(
          {required final List<Map<String, dynamic>> attendeeList}) =
      _$CreateAttendeeEvent;

  @override
  List<Map<String, dynamic>> get attendeeList;
  @override
  @JsonKey(ignore: true)
  _$$CreateAttendeeEventCopyWith<_$CreateAttendeeEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$AttendeeCreateState {
  bool get loading => throw _privateConstructorUsedError;
  AttendeeModel? get attendeeModel => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendeeCreateStateCopyWith<AttendeeCreateState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeeCreateStateCopyWith<$Res> {
  factory $AttendeeCreateStateCopyWith(
          AttendeeCreateState value, $Res Function(AttendeeCreateState) then) =
      _$AttendeeCreateStateCopyWithImpl<$Res, AttendeeCreateState>;
  @useResult
  $Res call({bool loading, AttendeeModel? attendeeModel});

  $AttendeeModelCopyWith<$Res>? get attendeeModel;
}

/// @nodoc
class _$AttendeeCreateStateCopyWithImpl<$Res, $Val extends AttendeeCreateState>
    implements $AttendeeCreateStateCopyWith<$Res> {
  _$AttendeeCreateStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendeeModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendeeModel: freezed == attendeeModel
          ? _value.attendeeModel
          : attendeeModel // ignore: cast_nullable_to_non_nullable
              as AttendeeModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendeeModelCopyWith<$Res>? get attendeeModel {
    if (_value.attendeeModel == null) {
      return null;
    }

    return $AttendeeModelCopyWith<$Res>(_value.attendeeModel!, (value) {
      return _then(_value.copyWith(attendeeModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AttendeeCreateStateCopyWith<$Res>
    implements $AttendeeCreateStateCopyWith<$Res> {
  factory _$$_AttendeeCreateStateCopyWith(_$_AttendeeCreateState value,
          $Res Function(_$_AttendeeCreateState) then) =
      __$$_AttendeeCreateStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool loading, AttendeeModel? attendeeModel});

  @override
  $AttendeeModelCopyWith<$Res>? get attendeeModel;
}

/// @nodoc
class __$$_AttendeeCreateStateCopyWithImpl<$Res>
    extends _$AttendeeCreateStateCopyWithImpl<$Res, _$_AttendeeCreateState>
    implements _$$_AttendeeCreateStateCopyWith<$Res> {
  __$$_AttendeeCreateStateCopyWithImpl(_$_AttendeeCreateState _value,
      $Res Function(_$_AttendeeCreateState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendeeModel = freezed,
  }) {
    return _then(_$_AttendeeCreateState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendeeModel: freezed == attendeeModel
          ? _value.attendeeModel
          : attendeeModel // ignore: cast_nullable_to_non_nullable
              as AttendeeModel?,
    ));
  }
}

/// @nodoc

class _$_AttendeeCreateState extends _AttendeeCreateState {
  const _$_AttendeeCreateState({this.loading = false, this.attendeeModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final AttendeeModel? attendeeModel;

  @override
  String toString() {
    return 'AttendeeCreateState(loading: $loading, attendeeModel: $attendeeModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendeeCreateState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(other.attendeeModel, attendeeModel) ||
                other.attendeeModel == attendeeModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, loading, attendeeModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendeeCreateStateCopyWith<_$_AttendeeCreateState> get copyWith =>
      __$$_AttendeeCreateStateCopyWithImpl<_$_AttendeeCreateState>(
          this, _$identity);
}

abstract class _AttendeeCreateState extends AttendeeCreateState {
  const factory _AttendeeCreateState(
      {final bool loading,
      final AttendeeModel? attendeeModel}) = _$_AttendeeCreateState;
  const _AttendeeCreateState._() : super._();

  @override
  bool get loading;
  @override
  AttendeeModel? get attendeeModel;
  @override
  @JsonKey(ignore: true)
  _$$_AttendeeCreateStateCopyWith<_$_AttendeeCreateState> get copyWith =>
      throw _privateConstructorUsedError;
}
