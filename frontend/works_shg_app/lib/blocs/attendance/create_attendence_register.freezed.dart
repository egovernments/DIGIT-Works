// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'create_attendence_register.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$AttendenceRegisterCreateEvent {
  String get tenantId => throw _privateConstructorUsedError;
  String get registerNumber => throw _privateConstructorUsedError;
  String get name => throw _privateConstructorUsedError;
  int get startDate => throw _privateConstructorUsedError;
  int get endDate => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String registerNumber,
            String name, int startDate, int endDate)
        create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendenceRegisterEvent value) create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendenceRegisterEvent value)? create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendenceRegisterEvent value)? create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendenceRegisterCreateEventCopyWith<AttendenceRegisterCreateEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendenceRegisterCreateEventCopyWith<$Res> {
  factory $AttendenceRegisterCreateEventCopyWith(
          AttendenceRegisterCreateEvent value,
          $Res Function(AttendenceRegisterCreateEvent) then) =
      _$AttendenceRegisterCreateEventCopyWithImpl<$Res,
          AttendenceRegisterCreateEvent>;
  @useResult
  $Res call(
      {String tenantId,
      String registerNumber,
      String name,
      int startDate,
      int endDate});
}

/// @nodoc
class _$AttendenceRegisterCreateEventCopyWithImpl<$Res,
        $Val extends AttendenceRegisterCreateEvent>
    implements $AttendenceRegisterCreateEventCopyWith<$Res> {
  _$AttendenceRegisterCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerNumber = null,
    Object? name = null,
    Object? startDate = null,
    Object? endDate = null,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerNumber: null == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      endDate: null == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CreateAttendenceRegisterEventCopyWith<$Res>
    implements $AttendenceRegisterCreateEventCopyWith<$Res> {
  factory _$$CreateAttendenceRegisterEventCopyWith(
          _$CreateAttendenceRegisterEvent value,
          $Res Function(_$CreateAttendenceRegisterEvent) then) =
      __$$CreateAttendenceRegisterEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String registerNumber,
      String name,
      int startDate,
      int endDate});
}

/// @nodoc
class __$$CreateAttendenceRegisterEventCopyWithImpl<$Res>
    extends _$AttendenceRegisterCreateEventCopyWithImpl<$Res,
        _$CreateAttendenceRegisterEvent>
    implements _$$CreateAttendenceRegisterEventCopyWith<$Res> {
  __$$CreateAttendenceRegisterEventCopyWithImpl(
      _$CreateAttendenceRegisterEvent _value,
      $Res Function(_$CreateAttendenceRegisterEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerNumber = null,
    Object? name = null,
    Object? startDate = null,
    Object? endDate = null,
  }) {
    return _then(_$CreateAttendenceRegisterEvent(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerNumber: null == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      endDate: null == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$CreateAttendenceRegisterEvent
    with DiagnosticableTreeMixin
    implements CreateAttendenceRegisterEvent {
  const _$CreateAttendenceRegisterEvent(
      {required this.tenantId,
      required this.registerNumber,
      required this.name,
      required this.startDate,
      required this.endDate});

  @override
  final String tenantId;
  @override
  final String registerNumber;
  @override
  final String name;
  @override
  final int startDate;
  @override
  final int endDate;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendenceRegisterCreateEvent.create(tenantId: $tenantId, registerNumber: $registerNumber, name: $name, startDate: $startDate, endDate: $endDate)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendenceRegisterCreateEvent.create'))
      ..add(DiagnosticsProperty('tenantId', tenantId))
      ..add(DiagnosticsProperty('registerNumber', registerNumber))
      ..add(DiagnosticsProperty('name', name))
      ..add(DiagnosticsProperty('startDate', startDate))
      ..add(DiagnosticsProperty('endDate', endDate));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateAttendenceRegisterEvent &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerNumber, registerNumber) ||
                other.registerNumber == registerNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenantId, registerNumber, name, startDate, endDate);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateAttendenceRegisterEventCopyWith<_$CreateAttendenceRegisterEvent>
      get copyWith => __$$CreateAttendenceRegisterEventCopyWithImpl<
          _$CreateAttendenceRegisterEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String registerNumber,
            String name, int startDate, int endDate)
        create,
  }) {
    return create(tenantId, registerNumber, name, startDate, endDate);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
  }) {
    return create?.call(tenantId, registerNumber, name, startDate, endDate);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(tenantId, registerNumber, name, startDate, endDate);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendenceRegisterEvent value) create,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendenceRegisterEvent value)? create,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendenceRegisterEvent value)? create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateAttendenceRegisterEvent
    implements AttendenceRegisterCreateEvent {
  const factory CreateAttendenceRegisterEvent(
      {required final String tenantId,
      required final String registerNumber,
      required final String name,
      required final int startDate,
      required final int endDate}) = _$CreateAttendenceRegisterEvent;

  @override
  String get tenantId;
  @override
  String get registerNumber;
  @override
  String get name;
  @override
  int get startDate;
  @override
  int get endDate;
  @override
  @JsonKey(ignore: true)
  _$$CreateAttendenceRegisterEventCopyWith<_$CreateAttendenceRegisterEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$AttendenceRegisterCreateState {
  bool get loading => throw _privateConstructorUsedError;
  AttendenceRegistersModel? get attendenceRegistersModel =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendenceRegisterCreateStateCopyWith<AttendenceRegisterCreateState>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendenceRegisterCreateStateCopyWith<$Res> {
  factory $AttendenceRegisterCreateStateCopyWith(
          AttendenceRegisterCreateState value,
          $Res Function(AttendenceRegisterCreateState) then) =
      _$AttendenceRegisterCreateStateCopyWithImpl<$Res,
          AttendenceRegisterCreateState>;
  @useResult
  $Res call({bool loading, AttendenceRegistersModel? attendenceRegistersModel});

  $AttendenceRegistersModelCopyWith<$Res>? get attendenceRegistersModel;
}

/// @nodoc
class _$AttendenceRegisterCreateStateCopyWithImpl<$Res,
        $Val extends AttendenceRegisterCreateState>
    implements $AttendenceRegisterCreateStateCopyWith<$Res> {
  _$AttendenceRegisterCreateStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendenceRegistersModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendenceRegistersModel: freezed == attendenceRegistersModel
          ? _value.attendenceRegistersModel
          : attendenceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendenceRegistersModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendenceRegistersModelCopyWith<$Res>? get attendenceRegistersModel {
    if (_value.attendenceRegistersModel == null) {
      return null;
    }

    return $AttendenceRegistersModelCopyWith<$Res>(
        _value.attendenceRegistersModel!, (value) {
      return _then(_value.copyWith(attendenceRegistersModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AttendenceRegisterCreateStateCopyWith<$Res>
    implements $AttendenceRegisterCreateStateCopyWith<$Res> {
  factory _$$_AttendenceRegisterCreateStateCopyWith(
          _$_AttendenceRegisterCreateState value,
          $Res Function(_$_AttendenceRegisterCreateState) then) =
      __$$_AttendenceRegisterCreateStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool loading, AttendenceRegistersModel? attendenceRegistersModel});

  @override
  $AttendenceRegistersModelCopyWith<$Res>? get attendenceRegistersModel;
}

/// @nodoc
class __$$_AttendenceRegisterCreateStateCopyWithImpl<$Res>
    extends _$AttendenceRegisterCreateStateCopyWithImpl<$Res,
        _$_AttendenceRegisterCreateState>
    implements _$$_AttendenceRegisterCreateStateCopyWith<$Res> {
  __$$_AttendenceRegisterCreateStateCopyWithImpl(
      _$_AttendenceRegisterCreateState _value,
      $Res Function(_$_AttendenceRegisterCreateState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendenceRegistersModel = freezed,
  }) {
    return _then(_$_AttendenceRegisterCreateState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendenceRegistersModel: freezed == attendenceRegistersModel
          ? _value.attendenceRegistersModel
          : attendenceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendenceRegistersModel?,
    ));
  }
}

/// @nodoc

class _$_AttendenceRegisterCreateState extends _AttendenceRegisterCreateState
    with DiagnosticableTreeMixin {
  const _$_AttendenceRegisterCreateState(
      {this.loading = false, this.attendenceRegistersModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final AttendenceRegistersModel? attendenceRegistersModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendenceRegisterCreateState(loading: $loading, attendenceRegistersModel: $attendenceRegistersModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendenceRegisterCreateState'))
      ..add(DiagnosticsProperty('loading', loading))
      ..add(DiagnosticsProperty(
          'attendenceRegistersModel', attendenceRegistersModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendenceRegisterCreateState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(
                    other.attendenceRegistersModel, attendenceRegistersModel) ||
                other.attendenceRegistersModel == attendenceRegistersModel));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, loading, attendenceRegistersModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendenceRegisterCreateStateCopyWith<_$_AttendenceRegisterCreateState>
      get copyWith => __$$_AttendenceRegisterCreateStateCopyWithImpl<
          _$_AttendenceRegisterCreateState>(this, _$identity);
}

abstract class _AttendenceRegisterCreateState
    extends AttendenceRegisterCreateState {
  const factory _AttendenceRegisterCreateState(
          {final bool loading,
          final AttendenceRegistersModel? attendenceRegistersModel}) =
      _$_AttendenceRegisterCreateState;
  const _AttendenceRegisterCreateState._() : super._();

  @override
  bool get loading;
  @override
  AttendenceRegistersModel? get attendenceRegistersModel;
  @override
  @JsonKey(ignore: true)
  _$$_AttendenceRegisterCreateStateCopyWith<_$_AttendenceRegisterCreateState>
      get copyWith => throw _privateConstructorUsedError;
}
